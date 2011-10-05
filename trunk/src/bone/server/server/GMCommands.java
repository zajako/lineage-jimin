/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 *
 * http://www.gnu.org/copyleft/gpl.html
 */

package bone.server.server;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.manager.bone;
import server.system.autoshop.AutoShopManager;

import bone.server.Base64;
import bone.server.L1DatabaseFactory;
import bone.server.SpecialEventHandler;
import bone.server.GameSystem.Boss.BossSpawnTimeController;
import bone.server.server.command.L1Commands;
import bone.server.server.command.executor.L1CommandExecutor;
import bone.server.server.datatables.DropTable;
import bone.server.server.datatables.ItemTable;
import bone.server.server.datatables.ModelSpawnTable;
import bone.server.server.datatables.ShopTable;
import bone.server.server.model.Broadcaster;
import bone.server.server.model.L1Inventory;
import bone.server.server.model.L1Object;
import bone.server.server.model.L1Teleport;
import bone.server.server.model.L1World;
import bone.server.server.model.Instance.L1ItemInstance;
import bone.server.server.model.Instance.L1MonsterInstance;
import bone.server.server.model.Instance.L1NpcInstance;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.model.skill.L1SkillId;
import bone.server.server.serverpackets.S_Chainfo;
import bone.server.server.serverpackets.S_PacketBox;
import bone.server.server.serverpackets.S_ServerMessage;
import bone.server.server.serverpackets.S_SkillIconGFX;
import bone.server.server.serverpackets.S_SkillSound;
import bone.server.server.serverpackets.S_SystemMessage;
import bone.server.server.serverpackets.S_Test;
import bone.server.server.templates.L1Command;
import bone.server.server.utils.SQLUtil;

//Referenced classes of package bone.server.server:
//ClientThread, Shutdown, IpTable, MobTable,
//PolyTable, IdFactory


public class GMCommands {

	private static Logger _log = Logger.getLogger(GMCommands.class.getName());	
	private static GMCommands _instance;

	private GMCommands() {
	}

	public static GMCommands getInstance() {
		if (_instance == null) {
			_instance = new GMCommands();
		}
		return _instance;
	}

	private String complementClassName(String className) {
		if (className.contains(".")) {
			return className;
		}
		return "bone.server.server.command.executor." + className;
	}
	
	private boolean executeDatabaseCommand(L1PcInstance pc, String name,
			String arg) {
		try {
			L1Command command = L1Commands.get(name);
			if (command == null) {
				return false;
			}
			if (pc.getAccessLevel() < command.getLevel()) {
				pc.sendPackets(new S_ServerMessage(74, "[Command] " + name)); // \f1%0은 사용할 수 없습니다.
				return true;
			}

			Class<?> cls = Class.forName(complementClassName(command. getExecutorClassName()));
			L1CommandExecutor exe = (L1CommandExecutor)cls.getMethod("getInstance").invoke(null);
			exe.execute(pc, name, arg);
			bone.LogCommandAppend(pc.getName(), name, arg);
			_log.info('('+ pc.getName() + ")가 " + name + " " + arg + "운영자 명령어를 사용했습니다. ");
			return true;
		} catch (Exception e) {
			_log.log(Level.SEVERE, "error gm command", e);
		}
		return false;
	}

	public void handleCommands(L1PcInstance gm, String cmdLine) {
		StringTokenizer token = new StringTokenizer(cmdLine);
		// 최초의 공백까지가 커맨드, 그 이후는 공백을 단락으로 한 파라미터로서 취급한다
		String cmd = token.nextToken();
		String param = "";
		while (token.hasMoreTokens()) {
			param = new StringBuilder(param).append(token.nextToken()).append(
			' ').toString();
		}
		param = param.trim();

		// 데이타베이스화 된 커멘드
		if (executeDatabaseCommand(gm, cmd, param)) {
			if (!cmd.equalsIgnoreCase("재실행")) {
				_lastCommands.put(gm.getId(), cmdLine);
			}
			return;
		}

		if (gm.getAccessLevel() < 200) {
			gm.sendPackets(new S_ServerMessage(74, "[Command] " + cmd));
			return;
		}
		bone.LogCommandAppend(gm.getName(), cmd, param);
		// GM에 개방하는 커맨드는 여기에 쓴다
		if (cmd.equalsIgnoreCase("도움말")) {
			showHelp(gm);		
		} else if (cmd.equalsIgnoreCase("패킷박스")) {
			packetbox(gm, param);
		} else if (cmd.equalsIgnoreCase("계정추가")) {
			addaccount(gm, param);
		} else if (cmd.equalsIgnoreCase("버경")){				
			SpecialEventHandler.getInstance().doBugRace();
		} else if (cmd.equalsIgnoreCase("전체버프")){				
			SpecialEventHandler.getInstance().doAllBuf();
		} else if (cmd.equalsIgnoreCase("비번변경")){
			changepassword(gm, param);
		} else if (cmd.equalsIgnoreCase("코드")) {
			CodeTest(gm, param);
		} else if (cmd.equalsIgnoreCase("검사")) {  // #### 케릭검사
			chainfo(gm, param);
		} else if (cmd.equalsIgnoreCase("가라")) {    // #### 마을보내기
			nocall(gm, param);
		} else if (cmd.equalsIgnoreCase("계정")) {  // ##### 계정 검색 추가 ########
            account_Cha(gm, param) ;
		} else if (cmd.equalsIgnoreCase("정리")) {
			Clear(gm);
		} else if (cmd.equalsIgnoreCase("현상금")) {
			hun(gm, param);
		} else if (cmd.equalsIgnoreCase("채금풀기")) { //////////적당한곳추가
			chatx(gm, param);
		} else if (cmd.equalsIgnoreCase("검색")) { // ########## 검색 추가 ##########
			searchDatabase(gm, param);
		} else if (cmd.equalsIgnoreCase("불")) {
			spawnmodel(gm, param);
		} else if (cmd.equalsIgnoreCase("나비켓")){
            reloadDB(gm, param); //추가
		} else if (cmd.equalsIgnoreCase("전체선물")) {
            	allpresent(gm, param);
		} else if (cmd.equalsIgnoreCase("암호변경")) {
		    changePassword(gm, param);
		} else if (cmd.equalsIgnoreCase("보스")){
			BossSpawnTimeController.getBossTime(gm);
		} else if (cmd.equalsIgnoreCase("재실행")) {
			if (!_lastCommands.containsKey(gm.getId())) {
				gm.sendPackets(new S_ServerMessage(74, "[Command] " + cmd)); // \f1%0은 사용할 수 없습니다.
				return;
			}
			redo(gm, param);			
			return;
		} else {
			gm.sendPackets(new S_SystemMessage("[Command] " + cmd + " 는 존재하지 않습니다. "));
		}
	}

	private void spawnmodel(L1PcInstance gm, String param) {
		StringTokenizer st = new StringTokenizer(param);
		int type = Integer.parseInt(st.nextToken(), 10);		
		ModelSpawnTable.getInstance().insertmodel(gm, type);
		gm.sendPackets(new S_SystemMessage("[Command] 불 넣었다"));
	}

	private void showHelp(L1PcInstance pc) {
		pc.sendPackets(new S_SystemMessage("-------------------<GM 명령어>--------------------"));
		pc.sendPackets(new S_SystemMessage(".종료 .영구추방 .추방 .계정압류 .밴아이피 .감시 .킬"));
		pc.sendPackets(new S_SystemMessage(".레벨 .이미지 .인벤이미지 .배치 .스킬보기"));
		pc.sendPackets(new S_SystemMessage(".버프 .변신 .부활 .올버프 .전체버프 .겜블 .설문"));
		pc.sendPackets(new S_SystemMessage(".아데나 .아이템 .세트아이템 .렙선물 .전체선물"));
		pc.sendPackets(new S_SystemMessage(".채팅 .채금 .셋팅 .서먼 .청소 .날씨 .소환. 파티소환"));
		pc.sendPackets(new S_SystemMessage(".홈타운 .귀환 .출두 .스킬마스터 .속도 .피바 .액션"));
		pc.sendPackets(new S_SystemMessage(".이동 .위치 .누구 .정보 .리로드트랩 .쇼트랩 .실행"));
		pc.sendPackets(new S_SystemMessage(".리셋트랩 .재실행 .고스폰 .현상금 .소환 .힐"));
		pc.sendPackets(new S_SystemMessage(".나비켓 .정리 .검사 .계정 .가라 .채금풀기 .출두 "));
		pc.sendPackets(new S_SystemMessage("--------------------------------------------------"));
	}

	private static Map<Integer, String> _lastCommands = new HashMap<Integer, String>();

	private void redo(L1PcInstance pc, String arg) {
		try {
			String lastCmd = _lastCommands.get(pc.getId());
			if (arg.isEmpty()) {
				pc.sendPackets(new S_SystemMessage("[Command] 커맨드 " + lastCmd
						+ " 을(를) 재실행합니다."));
				handleCommands(pc, lastCmd);
			} else {				
				StringTokenizer token = new StringTokenizer(lastCmd);
				String cmd = token.nextToken() + " " + arg;
				pc.sendPackets(new S_SystemMessage("[Command] 커맨드 " + cmd + " 을(를) 재실행합니다."));
				handleCommands(pc, cmd);
			}
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			pc.sendPackets(new S_SystemMessage("[Command] .재실행 커맨드에러"));
		}
	}
	
	private static String encodePassword1(String rawPassword) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		return rawPassword;
	}
	
	/* 실제 암호 변경 메소드 */
	private void to_Change_Passwd(L1PcInstance gm, L1PcInstance pc, String passwd){
		try{
			String login = null;
			String password = null;
			java.sql.Connection con = null;
			con = L1DatabaseFactory.getInstance().getConnection();
			PreparedStatement statement = null;
			PreparedStatement pstm = null;
			password = encodePassword(passwd);
			
			statement = con.prepareStatement("select account_name from characters where char_name Like '" + pc.getName() + "'");
			ResultSet rs = statement.executeQuery();
			
			while (rs.next()){
				login = rs.getString(1);
				pstm = con.prepareStatement("UPDATE accounts SET password=? WHERE login Like '" + login + "'");
				pstm.setString(1, password);
				pstm.execute();
				gm.sendPackets(new S_SystemMessage("암호 변경정보 Account: [" + login + "] Password: [" + passwd + "]"));
				gm.sendPackets(new S_SystemMessage(pc.getName() + "의 암호 변경이 성공적으로 완료되었습니다."));
				pc.sendPackets(new S_SystemMessage("귀하의 계정 정보가 갱신되었습니다."));
			}
			rs.close();
			pstm.close();
			statement.close();
			con.close();
		}catch (Exception e){
		}
	}
	
	/*
	 * 입력받은 암호에 한글이 포함되지 않았는지 확인해 주는 메소드 
	 * 실제로 암호가 한글로 바뀌어버리면 클라이언트에서는 입력할 방법이 없다. 
	 */
	 private static boolean isDisitAlpha1(String str){
		 boolean check = true;
		 for(int i = 0; i < str.length(); i++){
			 if(!Character.isDigit(str.charAt(i)) // 숫자가 아니라면
					 && Character.isLetterOrDigit(str.charAt(i)) // 특수문자라면
					 && !Character.isUpperCase(str.charAt(i)) // 대문자가 아니라면
					 && !Character.isLowerCase(str.charAt(i))) { // 소문자가 아니라면
				 
				 check = false;
				 break;
			 }
		 }
		 return check;
	 }

	 /* 
	  * 암호 변경에 필요한 변수를 입력받는다.
	  */
	 private void changePassword(L1PcInstance gm, String param){
		 try{
			 StringTokenizer tok = new StringTokenizer(param);
			 String user = tok.nextToken();
			 String passwd = tok.nextToken();
			 
			 if (passwd.length() < 4){
				 gm.sendPackets(new S_SystemMessage("입력하신 암호의 자릿수가 너무 짧습니다."));
				 gm.sendPackets(new S_SystemMessage("최소 4자 이상 입력해 주십시오."));
				 return;
			 }
			 
			 if (passwd.length() > 12){
				 gm.sendPackets(new S_SystemMessage("입력하신 암호의 자릿수가 너무 깁니다."));
				 gm.sendPackets(new S_SystemMessage("최대 12자 이하로 입력해 주십시오."));
				 return;
			 }
			 
			 if (isDisitAlpha(passwd) == false){
				 gm.sendPackets(new S_SystemMessage("암호에 허용되지 않는 문자가 포함되었습니다."));
				 return;
			 }
			 
			 L1PcInstance target = L1World.getInstance().getPlayer(user);
			 if (target != null){
				 to_Change_Passwd(gm, target, passwd);
			 }else{
				 gm.sendPackets(new S_SystemMessage("그런 이름을 가진 캐릭터는 없습니다."));
			 }
		 }catch (Exception e){
			 gm.sendPackets(new S_SystemMessage(".암호변경 캐릭명 암호 로 입력해주세요."));
		 }
	 }
	 
	 private void reloadDB(L1PcInstance gm, String cmd){
		 try{
			 DropTable.reload();
			 ShopTable.reload();
			 ItemTable.reload();
			 
			 gm.sendPackets(new S_SystemMessage("Table Update Complete..."));
		 }catch (Exception e){
			 gm.sendPackets(new S_SystemMessage(".나비켓 라고 입력해 주세요."));
		 }
	 }

	private void packetbox(L1PcInstance pc, String param) {
		try {
			StringTokenizer st = new StringTokenizer(param);
			int id = Integer.parseInt(st.nextToken(), 10);			
			pc.sendPackets(new S_PacketBox(id));
		} catch (Exception exception) {
			pc.sendPackets(new S_SystemMessage("[Command] .패킷박스 [id] 입력"));
		}
	}
	
	private void searchDatabase(L1PcInstance gm, String param) {
		try {
			StringTokenizer tok = new StringTokenizer(param);
			int type = Integer.parseInt(tok.nextToken());
			String name = tok.nextToken();
			searchObject(gm, type, name);
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".검색 [0~4] [name]을 입력 해주세요."));
			gm.sendPackets(new S_SystemMessage("0=etcitem, 1=weapon, 2=armor, 3=npc, 4=polymorphs"));   
			gm.sendPackets(new S_SystemMessage("name을 정확히 모르거나 띄워쓰기 되어있는 경우는"));
			gm.sendPackets(new S_SystemMessage("'%'를 앞이나 뒤에 붙여 쓰십시오."));
		}
	}
	private void searchObject(L1PcInstance gm, int type, String name){
		try{
			String str1 = null;
			String str2 = null;
			int count = 0;
			java.sql.Connection con = null;
			con = L1DatabaseFactory.getInstance().getConnection();
			PreparedStatement statement = null;

			switch (type){
			case 0: // 잡템
				statement = con.prepareStatement("select item_id, name from etcitem where name Like '" + name + "'");
				break;
			case 1: // 무기
				statement = con.prepareStatement("select item_id, name from weapon where name Like '" + name + "'");
				break;
			case 2: // 방어구
				statement = con.prepareStatement("select item_id, name from armor where name Like '" + name + "'");
				break;
			case 3: // 엔피씨
				statement = con.prepareStatement("select npcid, name from npc where name Like '" + name + "'");
				break;
			case 4: // 변신
				statement = con.prepareStatement("select polyid, name from polymorphs where name Like '" + name + "'");
				break;
			default:
				break;
			}
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				str1 = rs.getString(1);
				str2 = rs.getString(2);
				gm.sendPackets(new S_SystemMessage("id : [" + str1 + "], name : [" + str2 + "]"));
				count++;
			}
			rs.close();
			statement.close();
			con.close();
			gm.sendPackets(new S_SystemMessage("총 [" + count + "]개의 데이터가 검색되었습니다."));
		}
		catch (Exception e){
		}
	}
	
	//전체선물
	private void allpresent(L1PcInstance gm, String param){
		try{
			StringTokenizer kwang = new StringTokenizer(param);
			int itemid = Integer.parseInt(kwang.nextToken(), 10);
			int enchant = Integer.parseInt(kwang.nextToken(), 10);
			int count = Integer.parseInt(kwang.nextToken(), 10);
			
			for (L1PcInstance pc : L1World.getInstance().getAllPlayers()){
				if(!pc.isPrivateShop()){
					if (pc.isGhost() == false){
						L1ItemInstance item = ItemTable.getInstance().createItem(itemid);
						item.setCount(count);
						item.setEnchantLevel(enchant);
						
						if (item != null){
							if (pc.getInventory().checkAddItem(item, count) == L1Inventory.OK){
								pc.getInventory().storeItem(item);
							}
						}
						
						pc.sendPackets(new S_SystemMessage("운영자님께서 전체유저에게 선물로["+ item.getViewName()
								+"]를 주었습니다."));
					}
				}
			}
		}catch (Exception exception){
			gm.sendPackets(new S_SystemMessage(".전체선물 아이템ID 인첸트수 아이템수로 입력해 주세요."));
		}
	}
	
	//채금풀기
	private void chatx(L1PcInstance gm, String param){
		try{
			StringTokenizer tokenizer = new StringTokenizer(param);
			String pcName = tokenizer.nextToken();
			
			L1PcInstance target = null;
			target = L1World.getInstance().getPlayer(pcName);
			
			if (target!= null){
				target.getSkillEffectTimerSet().killSkillEffectTimer(L1SkillId.STATUS_CHAT_PROHIBITED);
				target.sendPackets(new S_SkillIconGFX(36, 0));
				target.sendPackets(new S_ServerMessage(288));
				gm.sendPackets(new S_SystemMessage("해당캐릭의 채금을 해제 했습니다.."));
			}
		}catch (Exception e){
			gm.sendPackets(new S_SystemMessage(".채금풀기 캐릭터명 이라고 입력해 주세요."));
		}
	}
	
	//현상금시스템
	private void hun(L1PcInstance gm, String param){
		try{
			String huns = param;
			StringTokenizer stringtokenizer = new StringTokenizer(huns);
			String user = stringtokenizer.nextToken();
			String hunsc = stringtokenizer.nextToken();
			
			L1PcInstance target = L1World.getInstance().getPlayer(user);
			int count = Integer.parseInt(hunsc);
			if (target.getHuntCount() == 0 && gm.getInventory().consumeItem(560009, count)) { //산타 코인
				target.setHuntCount(1);
				target.setHuntCount(count);
				target.save();
				
				L1World.getInstance().broadcastPacketToAll(
						new S_ServerMessage(166, "\\" + gm.getName() +"님이","" + target.getName() + "님 목에 해피머니" + count + "개를 걸었습니다."));		
			}else{
				gm.sendPackets(new S_SystemMessage("머니가 없거나" + target.getName() + "님의 목에 현상금이 걸려있습니다."));
			}
		}catch (Exception e){
			gm.sendPackets(new S_SystemMessage((new StringBuilder()).append(".현상금 캐릭명 갯수[해피머니]로 입력해 주세요.").toString()));
		}
	}
	
	//마을보내기 .가라
	private void nocall(L1PcInstance gm, String param){
		try{
			StringTokenizer tokenizer = new StringTokenizer(param);
			String pcName = tokenizer.nextToken();
			
			L1PcInstance target = null;
			target = L1World.getInstance().getPlayer(pcName);
			if (target != null) { //타겟
				L1Teleport.teleport(target, 33440, 32795, (short) 4, 5, true); /// 가게될 지점 (유저가떨어지는지점)
			}else{
				gm.sendPackets(new S_SystemMessage("접속중이지 않는 유저 ID 입니다!"));
			}
		}catch (Exception e){
			gm.sendPackets(new S_SystemMessage(".가라 [캐릭터명] 으로 입력해 주세요!"));
		}
	}
	
	//.가라
	private void chainfo(L1PcInstance gm, String param) {
		try {
			StringTokenizer stringtokenizer = new StringTokenizer(param);
			String s = stringtokenizer.nextToken();
			gm.sendPackets(new S_Chainfo(1, s));
		} catch (Exception exception21) {
			gm.sendPackets(new S_SystemMessage(".검 [캐릭터명]을 입력 해주세요."));
		}
	}
	
	//같은 계정에 있는 캐릭 검사 .계정
	private void account_Cha( L1PcInstance gm, String param ){
		try{
			StringTokenizer tok = new StringTokenizer(param) ;
			String name = tok.nextToken();
			account_Cha2(gm, name);
		}catch (Exception e){
			 gm.sendPackets(new S_SystemMessage(".계정 [아이디]로 입력해주세요!"));
		}
	}
	
	private void account_Cha2( L1PcInstance gm, String param ) {
        try {
               String s_account = null ;
               String s_name = param ;
               String s_level = null ;
               String s_clan = null ;
               String s_bonus = null ;
               String s_online = null ;
               String s_hp = null ;
               String s_mp = null ;
               int count = 0 ;
               int count0 = 0 ;
               java.sql.Connection con0 = null ; // 이름으로 objid를 검색하기 위해
               con0 = L1DatabaseFactory.getInstance().getConnection() ;
               PreparedStatement statement0 = null ;
               statement0 = con0.prepareStatement("select account_name, Clanname  from characters where char_name = '"+ s_name + "'") ;
               ResultSet rs0 = statement0.executeQuery() ;
               while (rs0.next()) {
                      s_account = rs0.getString(1) ;
                      s_clan = rs0.getString(2) ;
                      gm.sendPackets(new S_SystemMessage("캐릭명" + s_name + "/계정:" + s_account
                                   + "/클랜명:" + s_clan)) ;
                      count0++ ;
               }
               java.sql.Connection con = null ;
               con = L1DatabaseFactory.getInstance().getConnection() ;
               PreparedStatement statement = null ;
               statement = con.prepareStatement("select " + "char_name," + "level,"
                            + "Clanname," + "BonusStatus," + "OnlineStatus," + "MaxHp,"
                            + "MaxMp " + "from characters where account_name = '" + s_account
                            + "'") ;
               gm.sendPackets(new S_SystemMessage(">>> 같은 계정 찾기")) ;
               ResultSet rs = statement.executeQuery() ;
               while (rs.next()) {
                      s_name = rs.getString(1) ;
                      s_level = rs.getString(2) ;
                      s_clan = rs.getString(3) ;
                      s_bonus = rs.getString(4) ;
                      s_online = rs.getString(5) ;
                      s_hp = rs.getString(6) ;
                      s_mp = rs.getString(7) ;
                      gm.sendPackets(new S_SystemMessage("[" + s_online + "]랩[" + s_level
                                   + "]hp:" + s_hp + "]mp:" + s_mp + "]b:" + s_bonus + "][" + s_name
                                   + "]")) ;
                      count++ ;
               }
               rs0.close() ;
               statement0.close() ;
               con0.close() ;
               rs.close() ;
               statement.close() ;
               con.close() ;
               gm.sendPackets(new S_SystemMessage("<<계정찾기>>총 [" + count
                            + "]개 검색{1:게임중/0:오프라인----")) ;
        } catch (Exception e) {}
    }
	
	private void autoshop(L1PcInstance gm, String param){
		if(param.equalsIgnoreCase("켬")){
			AutoShopManager.getInstance().isAutoShop(true);
			gm.sendPackets(new S_SystemMessage("[Command] 무인상점 켬"));
		} else if(param.equalsIgnoreCase("끔")){
			AutoShopManager.getInstance().isAutoShop(false);
			gm.sendPackets(new S_SystemMessage("[Command] 무인상점 끔"));
		} else {
			gm.sendPackets(new S_SystemMessage("[Command] .무인상점 [켬 or 끔] 입력"));
		}
	}
	
	private static String encodePassword(String rawPassword) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		byte buf[] = rawPassword.getBytes("UTF-8");
		buf = MessageDigest.getInstance("SHA").digest(buf);
		return Base64.encodeBytes(buf);
	}

	private void AddAccount(L1PcInstance gm, String account, String passwd, String Ip, String Host) {
		try {
			String login = null;
			String password = null;
			java.sql.Connection con = null;
			con = L1DatabaseFactory.getInstance().getConnection();
			PreparedStatement statement = null;
			PreparedStatement pstm = null;

			password = encodePassword(passwd);

			statement = con.prepareStatement("select * from accounts where login Like '" + account + "'");
			ResultSet rs = statement.executeQuery();

			if(rs.next())login = rs.getString(1);			
			if (login != null){
				gm.sendPackets(new S_SystemMessage("[Command] 이미 계정이 있습니다."));
				return;
			} else {
				String sqlstr = "INSERT INTO accounts SET login=?,password=?,lastactive=?,access_level=?,ip=?,host=?,banned=?,charslot=?,gamepassword=?,notice=?";
				pstm = con.prepareStatement(sqlstr);
				pstm.setString(1, account);
				pstm.setString(2, password);
				pstm.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
				pstm.setInt(4, 0);
				pstm.setString(5, Ip);
				pstm.setString(6, Host);
				pstm.setInt(7, 0);
				pstm.setInt(8, 6);
				pstm.setInt(9, 0);
				pstm.setInt(10, 0);
				pstm.execute();
				gm.sendPackets(new S_SystemMessage("[Command] 계정 추가가 완료되었습니다."));				
			}

			rs.close();
			pstm.close();
			statement.close();
			con.close();
		} catch (Exception e) {
		}
	}

	private static boolean isDisitAlpha(String str) {  
		boolean check = true;
		for(int i = 0; i < str.length(); i++) {
			if(!Character.isDigit(str.charAt(i))	// 숫자가 아니라면
					&& Character.isLetterOrDigit(str.charAt(i))	// 특수문자라면
					&& !Character.isUpperCase(str.charAt(i))	// 대문자가 아니라면
					&& !Character.isLowerCase(str.charAt(i))) {	// 소문자가 아니라면
				check = false;
				break;
			}
		}
		return check;
	}

	private void addaccount(L1PcInstance gm, String param){
		try {
			StringTokenizer tok = new StringTokenizer(param);
			String user = tok.nextToken();
			String passwd = tok.nextToken();

			if (user.length() < 4) {
				gm.sendPackets(new S_SystemMessage("[Command] 입력하신 계정명의 자릿수가 너무 짧습니다."));
				gm.sendPackets(new S_SystemMessage("[Command] 최소 4자 이상 입력해 주십시오."));
				return;
			}
			if (passwd.length() < 4) {
				gm.sendPackets(new S_SystemMessage("[Command] 입력하신 암호의 자릿수가 너무 짧습니다."));
				gm.sendPackets(new S_SystemMessage("[Command] 최소 4자 이상 입력해 주십시오."));
				return;
			}

			if (passwd.length() > 12) {
				gm.sendPackets(new S_SystemMessage("[Command] 입력하신 암호의 자릿수가 너무 깁니다."));
				gm.sendPackets(new S_SystemMessage("[Command] 최대 12자 이하로 입력해 주십시오."));
				return;
			}

			if (isDisitAlpha(passwd) == false) {
				gm.sendPackets(new S_SystemMessage("[Command] 암호에 허용되지 않는 문자가 포함 되어 있습니다."));
				return;
			}
			AddAccount(gm, user, passwd,"127.0.0.1","127.0.0.1");
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage("[Command] .계정추가 [계정명] [암호] 입력"));
		}
	}

	private void changepassword(L1PcInstance gm, String param){
		try {
			StringTokenizer tok = new StringTokenizer(param);
			String user = tok.nextToken();
			String oldpasswd = tok.nextToken();
			String newpasswd = tok.nextToken();

			if (user.length() < 4) {
				gm.sendPackets(new S_SystemMessage("[Command] 입력하신 계정명의 자릿수가 너무 짧습니다."));
				gm.sendPackets(new S_SystemMessage("[Command] 최소 4자 이상 입력해 주십시오."));
				return;
			}
			if (newpasswd.length() < 4) {
				gm.sendPackets(new S_SystemMessage("[Command] 입력하신 암호의 자릿수가 너무 짧습니다."));
				gm.sendPackets(new S_SystemMessage("[Command] 최소 4자 이상 입력해 주십시오."));
				return;
			}
			if (newpasswd.length() > 12) {
				gm.sendPackets(new S_SystemMessage("[Command] 입력하신 암호의 자릿수가 너무 깁니다."));
				gm.sendPackets(new S_SystemMessage("[Command] 최대 12자 이하로 입력해 주십시오."));
				return;
			}

			if (isDisitAlpha(newpasswd) == false) {
				gm.sendPackets(new S_SystemMessage("[Command] 암호에 허용되지 않는 문자가 포함 되어 있습니다."));
				return;
			}
			chkpassword(gm, user, oldpasswd, newpasswd);
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage("[Command] .비번변경 [계정] [현재비번] [바꿀비번] 입력"));
		}
	}
	private void chkpassword(L1PcInstance gm, String account, String oldpassword, String newpassword) {
		try {					
			String password = null;
			java.sql.Connection con = null;
			con = L1DatabaseFactory.getInstance().getConnection();
			PreparedStatement statement = null;			
			PreparedStatement pstm = null;


			statement = con.prepareStatement("select password from accounts where login='" + account + "'");			
			ResultSet rs = statement.executeQuery();


			if(rs.next())password = rs.getString(1);	
			if (password == null){
				gm.sendPackets(new S_SystemMessage("[Command] 입력하신 계정은 서버내에 존재 하지 않습니다."));
				return;
			}

			if (!isPasswordTrue(password,oldpassword)){				
				//System.out.println("현재 비번 : " + oldpassword+" - 체크 비번 : "+password);
				gm.sendPackets(new S_SystemMessage("[Command] 기존 계정명의 비밀번호가 일치하지 않습니다. "));
				gm.sendPackets(new S_SystemMessage("[Command] 다시 확인하시고 실행해 주세요."));
				return;
			} else {
				String sqlstr = "UPDATE accounts SET password=password(?) WHERE login=?";
				pstm = con.prepareStatement(sqlstr);
				pstm.setString(1, newpassword);
				pstm.setString(2, account);
				pstm.execute();
				gm.sendPackets(new S_SystemMessage("[Command] 계정명 : " + account+" / 바뀐비밀번호 : " + newpassword));
				gm.sendPackets(new S_SystemMessage("[Command] 비밀번호 변경이 정상적으로 완료되었습니다."));				
			}
			rs.close();
			pstm.close();
			statement.close();
			con.close();
		} catch (Exception e) {
		}
	}
	//패스워드 맞는지 여부 리턴  
	public static boolean isPasswordTrue(String Password,String oldPassword) { 
		String _rtnPwd = null;
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT password(?) as pwd ");

			pstm.setString(1, oldPassword);
			rs = pstm.executeQuery();
			if (rs.next()){
				_rtnPwd = rs.getString("pwd");  
			}
			if ( _rtnPwd.equals(Password)) { // 동일하다면
				return true;
			}else
				return false;
		}catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}return false;
	}

	private void CodeTest(L1PcInstance pc, String param) {
		try {
			StringTokenizer st = new StringTokenizer(param);
			int codetest = Integer.parseInt(st.nextToken(), 10);
			//pc.sendPackets(new S_ServerMessage(161,"$580","$245", "$247"));
			//int time = 1020;
			// ↓ 테스트할 코드가 보여질 전달 패킷 부분 
			pc.sendPackets(new S_Test(pc,codetest));
			//pc.sendPackets(new S_PacketBox(S_PacketBox.ICON_AURA, codetest, time)); // 그신,욕망 버프 테스트
			//pc.sendPackets(new S_CastleMaster(codetest, pc.getId())); // 왕관 테스트
			//pc.sendPackets(new S_StatusReset(pc, codetest, 1)); // 스텟 초기화 테스트
		} catch (Exception exception) {
			pc.sendPackets(new S_SystemMessage("[Command] .코드 [숫자] 입력"));
		}
	}
	
	private void Clear(L1PcInstance gm) {
		for (L1Object obj : L1World.getInstance().getVisibleObjects(gm, 10)) { // 10 범위 내에 오브젝트를 찾아서
			if (obj instanceof L1MonsterInstance){ // 몬스터라면
				L1NpcInstance npc = (L1NpcInstance) obj;
				npc.receiveDamage(gm, 50000); // 데미지
				if (npc.getCurrentHp() <= 0){
					gm.sendPackets(new S_SkillSound(obj.getId() , 1815)); // 디스
					Broadcaster.broadcastPacket(gm, new S_SkillSound(obj.getId() , 1815));
				}else{
					gm.sendPackets(new S_SkillSound(obj.getId() , 1815));
					Broadcaster.broadcastPacket(gm, new S_SkillSound(obj.getId() , 1815));
				}
			}else if (obj instanceof L1PcInstance){ // pc라면
				L1PcInstance player = (L1PcInstance) obj;
				player.receiveDamage(player, 0, false); // 데미지
					if (player.getCurrentHp() <= 0){
						gm.sendPackets(new S_SkillSound(obj.getId() , 1815)); // 디스
						Broadcaster.broadcastPacket(gm, new S_SkillSound(obj.getId() , 1815));
					}else{
						gm.sendPackets(new S_SkillSound(obj.getId() , 1815));
						Broadcaster.broadcastPacket(gm, new S_SkillSound(obj.getId() , 1815));
					}
				}
			}
		}
}
