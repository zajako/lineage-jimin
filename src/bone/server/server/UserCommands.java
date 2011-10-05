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


import bone.server.server.serverpackets.S_Message_YN;//혈맹파티

import bone.server.server.model.L1Inventory;
import bone.server.server.model.Instance.L1ItemInstance;
import bone.server.server.model.skill.L1SkillId;
import static bone.server.server.model.skill.L1SkillId.ADVANCE_SPIRIT;
import static bone.server.server.model.skill.L1SkillId.BLESS_WEAPON;
import static bone.server.server.model.skill.L1SkillId.IRON_SKIN;
import static bone.server.server.model.skill.L1SkillId.PHYSICAL_ENCHANT_DEX;
import static bone.server.server.model.skill.L1SkillId.PHYSICAL_ENCHANT_STR;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.sql.SQLException;
import java.util.StringTokenizer;

import server.manager.bone;

import bone.server.L1DatabaseFactory;
//import bone.server.GameSystem.Boss.BossSpawnTimeController;
import bone.server.server.datatables.ItemTable;
import bone.server.server.datatables.NpcTable;
import bone.server.server.datatables.CharacterTable;
import bone.server.server.model.Broadcaster;
import bone.server.server.model.L1Object;
import bone.server.server.model.L1Teleport;
import bone.server.server.model.L1World;
import bone.server.server.model.Instance.L1MonsterInstance;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.model.skill.L1SkillUse;
import bone.server.server.serverpackets.S_Age;
import bone.server.server.serverpackets.S_Disconnect;
import bone.server.server.serverpackets.S_ChatPacket;
import bone.server.server.serverpackets.S_NpcChatPacket;
import bone.server.server.serverpackets.S_PacketBox;
import bone.server.server.serverpackets.S_SystemMessage;
import bone.server.server.utils.SQLUtil;
import bone.server.server.datatables.AutoAdvertising;
import java.util.ArrayList;
import bone.server.server.serverpackets.S_UserCommands;
import bone.server.server.serverpackets.S_UserCommands3;

import java.io.UnsupportedEncodingException; //## A112 암호변경 명령어 추가위해 임포트
import java.security.MessageDigest; //## A112 암호변경 명령어 추가위해 임포트
import bone.server.Base64; //## A112 암호변경 명령어 추가위해 임포트
import java.security.NoSuchAlgorithmException;
import bone.server.server.serverpackets.S_Serchdrop;
import bone.server.server.serverpackets.S_Serchdrop2; 

import bone.server.server.datatables.CharacterTable;
import bone.server.server.BadNamesList;
import bone.server.server.clientpackets.C_CreateNewCharacter;
import bone.server.server.serverpackets.S_ServerMessage;
import bone.server.server.templates.L1Item;



//Referenced classes of package bone.server.server:
//ClientThread, Shutdown, IpTable, MobTable,
//PolyTable, IdFactory


public class UserCommands {

	private static UserCommands _instance;
	private L1PcInstance gm;

	private UserCommands() {}

	public static UserCommands getInstance() {
		if (_instance == null) {
			_instance = new UserCommands();
		}
		return _instance;
	}

	public void handleCommands(L1PcInstance pc, String cmdLine) {		
		StringTokenizer token = new StringTokenizer(cmdLine);
		// 최초의 공백까지가 커맨드, 그 이후는 공백을 단락으로 한 파라미터로서 취급한다
		String cmd = token.nextToken();
		String param = "";
		while (token.hasMoreTokens()) {
			param = new StringBuilder(param).append(token.nextToken()).append(' ').toString();
		}
		param = param.trim();




		if (cmd.equalsIgnoreCase("도움말") || cmd.equalsIgnoreCase("서버정보")) {
			showHelp(pc);
		//} else if (cmd.equalsIgnoreCase("조사")){
		//	infoitem(pc, param);
		} else if (cmd.equalsIgnoreCase("버프")){
			buff(pc);
		} else if (cmd.equalsIgnoreCase("소원")) { hope(pc, param);
		} else if (cmd.equalsIgnoreCase(".") || cmd.equalsIgnoreCase("텔렉풀기")) {
			tell(pc);
		} else if (cmd.equalsIgnoreCase("홍보인증") || cmd.equalsIgnoreCase("홍보확인") || cmd.equalsIgnoreCase("홍보정산")) {//프리팝홍보기
			showPrStatus(pc);
		} else if (cmd.equalsIgnoreCase("비번변경") || cmd.equalsIgnoreCase("암호변경")) {
			changePassword(pc, param);
		} else if (cmd.equalsIgnoreCase("드랍") || cmd.equalsIgnoreCase("아이템")) {
			serchdroplist(pc, param) ;
		} else if (cmd.equalsIgnoreCase("몹드랍") || cmd.equalsIgnoreCase("몬스터")) {
			serchdroplist2(pc, param) ;
		} else if (cmd.equalsIgnoreCase("수배") || cmd.equalsIgnoreCase("현상수배")) {
			Hunt(pc, param);
		} else if (cmd.equalsIgnoreCase("퀴즈설정") || cmd.equalsIgnoreCase("퀴즈") || cmd.equalsIgnoreCase("인증코드")) {
			quize(pc, param);
		} else if (cmd.equalsIgnoreCase("퀴즈인증") || cmd.equalsIgnoreCase("인증")) {
			quize1(pc, param);
		} else if (cmd.equalsIgnoreCase("봉인해제신청") || cmd.equalsIgnoreCase("봉인해제주문서")) {
			Sealedoff(pc, param);
		} else if (cmd.equalsIgnoreCase("나이설정")) {
			age(pc, param);
		} else if (cmd.equalsIgnoreCase("족보설정")) {
			age2(pc, param);
		} else if(cmd.equalsIgnoreCase("혈맹파티")){
			ClanParty(pc);
		} else if(cmd.equalsIgnoreCase("무인상점") || cmd.equalsIgnoreCase("상점실행")) {
			supportHelp(pc);		
		} else if (cmd.equalsIgnoreCase("랭킹확인")) {
			rank(pc,param);
		} else if (cmd.equalsIgnoreCase("랭킹검색")) {
			rank1(pc,param);
		} else if(cmd.equalsIgnoreCase("정보")){
			check(pc);
		} else if(cmd.equalsIgnoreCase("인형정보") || cmd.equalsIgnoreCase("인형")) {
			showHelp1(pc);
		} else if(cmd.equalsIgnoreCase("오토루팅")) { //미완성
			autoroot(pc, cmd, param);
		} else if(cmd.equalsIgnoreCase("멘트") || cmd.equalsIgnoreCase("오토멘트"))  {
			ment(pc, cmd, param); //by 사부 멘트 명령어 
		} else if(cmd.equalsIgnoreCase("케릭명변경") || cmd.equalsIgnoreCase("캐릭명변경")) {
			charname(pc, param);

		} else {
			S_ChatPacket s_chatpacket = new S_ChatPacket(pc, cmdLine, Opcodes.S_OPCODE_NORMALCHAT, 0);
			if (!pc.getExcludingList().contains(pc.getName())) {
				pc.sendPackets(s_chatpacket);
			}
			for (L1PcInstance listner : L1World.getInstance().getRecognizePlayer(pc)) {
				if (!listner.getExcludingList().contains(pc.getName())) {
					listner.sendPackets(s_chatpacket);
				}
			}
			// 돕펠 처리
			L1MonsterInstance mob = null;
			for (L1Object obj : pc.getNearObjects().getKnownObjects()) {
				if (obj instanceof L1MonsterInstance) {
					mob = (L1MonsterInstance) obj;
					if (mob.getNpcTemplate().is_doppel() && mob.getName().equals(pc.getName())) {
						Broadcaster.broadcastPacket(mob, new S_NpcChatPacket(mob, cmdLine, 0));
					}
				}
			}
			bone.LogChatAppend("ⓝ", pc.getName(), cmdLine);			
		}
	}


	private void serchdroplist(L1PcInstance gm, String param) {
		try {
			StringTokenizer tok = new StringTokenizer(param);
			String nameid = tok.nextToken();

			int itemid = 0;
			try {
				itemid = Integer.parseInt(nameid);
			} catch (NumberFormatException e) {
				itemid = ItemTable.getInstance().findItemIdByNameWithoutSpace(nameid);
				if (itemid == 0) {
					gm.sendPackets(new S_SystemMessage("해당 아이템이 발견되지 않았습니다."));
					return;
				}
			}
			gm.sendPackets(new S_Serchdrop(itemid));
		} catch (Exception e) {
			//   _log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			gm.sendPackets(new S_SystemMessage(".드랍리스트 [itemid 또는 name]를 입력해 주세요."));
			gm.sendPackets(new S_SystemMessage("아이템 name을 공백없이 정확히 입력해야 합니다."));
			gm.sendPackets(new S_SystemMessage("ex) .드랍 마법서(디스인티그레이트) -- > 검색 O"));
			gm.sendPackets(new S_SystemMessage("ex) .드랍 디스 -- > 검색 X"));
		}
	} //추가


	private void hope(L1PcInstance pc, String cmd) {        //드래곤볼
		for (L1Object obj : L1World.getInstance().getVisibleObjects(pc, 10)) { // 5 범위 내에 오브젝트를 찾아서
			if (obj instanceof L1MonsterInstance){ // 몬스터라면
				L1MonsterInstance mon = (L1MonsterInstance) obj;
				if(mon.getNpcTemplate().get_npcId() == 45000172)  
					if (mon.getCurrentHp() > 0)
						try {
							StringTokenizer st = new StringTokenizer(cmd);
							String nameid = st.nextToken();  
							int count = 1;
							int enchant = 0;
							int itemid = 0;
							try {
								itemid = Integer.parseInt(nameid);
							} catch (NumberFormatException e) {
								itemid = ItemTable.getInstance(). findItemIdByNameWithoutSpace(nameid);
								if (itemid == 0) {
									L1World.getInstance().broadcastPacketToAll(new S_PacketBox(S_PacketBox.GREEN_MESSAGE,
									"그런 아이템은 이 세상에 없다...소원을 빌때는 이름을 붙여 써라..서둘러라..시간이 없..다"));
									return; 
								}
							}    
							L1Item temp = ItemTable.getInstance(). getTemplate(itemid);
							if (temp != null) {         
								L1ItemInstance item = null;
								int createCount;
								for (createCount = 0; createCount < count; createCount++) {
									item = ItemTable.getInstance(). createItem(itemid);
									item.setEnchantLevel(enchant);          
									if (pc.getInventory(). checkAddItem(item, 1) == L1Inventory.OK) {
										pc.sendPackets(new S_ChatPacket(pc, ""+nameid+" 을(를) 저에게 만들어 주세요!!" , Opcodes.S_OPCODE_NORMALCHAT, 2));
										Thread.sleep(5000);
										pc.getInventory(). storeItem(item);
										L1World.getInstance().broadcastPacketToAll(new S_PacketBox(S_PacketBox.GREEN_MESSAGE, 
												""+pc.getName()+"에게.."+nameid+"을!.... 너의 소원은 이루어졌다..그럼 이만...."));
										for (L1Object obj1 : L1World.getInstance().getVisibleObjects(pc, 20)) { // 20 범위 내에 오브젝트를 찾아서
											if (obj1 instanceof L1MonsterInstance){ // 몬스터라면
												L1MonsterInstance mon1 = (L1MonsterInstance) obj;
												if(mon1.getNpcTemplate().get_npcId() == 45000172 ){
													mon1.receiveDamage(gm, 90000); // 데미지          
												}    
											}
										}          

									} else {
										break;
									}
								} 
							} else {
								L1World.getInstance().broadcastPacketToAll(new S_PacketBox(S_PacketBox.GREEN_MESSAGE, 
								" 그런 아이템은 이 세상에 없다...소원을 빌때는 이름을 붙여써라..서둘러라..시간이 없..다"));
							}
						} catch (Exception e) {    
							pc.sendPackets(new S_SystemMessage(
							".소원 [아이템이름] 이라고 입력. ex).소원 싸울아비장검"));
						}
			}

		}
	}

	private void check(L1PcInstance pc) {
		try {
			long curtime = System.currentTimeMillis() / 1000;
			if (pc.getQuizTime() + 20 > curtime ) {
				pc.sendPackets(new S_SystemMessage("20초간의 지연시간이 필요합니다."));
				return;
			}
			int entertime = pc.getGdungeonTime() % 1000;
			int a = 180 - entertime;
			int hpr = pc.getHpr() + pc.getInventory(). hpRegenPerTick();
			int mpr = pc.getMpr() + pc.getInventory(). mpRegenPerTick();	

			pc.sendPackets(new S_SystemMessage("===================( 나의 정보 )===================="));				
			pc.sendPackets(new S_SystemMessage("\\fY(피틱:" + hpr + ')' + "(엠틱:" + mpr + ')' +"(기감:"+a+"분)(PK횟수:" + pc.get_PKcount() + ")(엘릭:"+ pc.getAbility().getElixirCount()+ "개)"));
			pc.sendPackets(new S_SystemMessage("===================================================="));
			pc.setQuizTime(curtime);
		} catch (Exception e) {
		}
	}
	private void showHelp(L1PcInstance pc) {
		pc.sendPackets(new S_UserCommands(1));
	}

	private void showHelp1(L1PcInstance pc) {
		pc.sendPackets(new S_UserCommands3(1));
	}

	private void supportHelp(L1PcInstance pc) {
		pc.sendPackets(new S_SystemMessage("\\fU상점을 켜신후 리스하시면 무인상점이 됩니다."));
	}	

	private void tell(L1PcInstance pc) { 
		try {
			if (pc.isPinkName())
			{
				pc.sendPackets(new S_SystemMessage("전투중이라 사용할 수 없습니다."));
				return;
			}
			long curtime = System.currentTimeMillis() / 1000;
			if (pc.getQuizTime() + 20 > curtime ) {
				pc.sendPackets(new S_SystemMessage("20초간의 지연시간이 필요합니다."));
				return;
			}
			L1Teleport.teleport(pc, pc.getX(), pc.getY(), pc.getMapId(), pc.getMoveState().getHeading(), false);
			pc.sendPackets(new S_SystemMessage("주변 오브젝트를 재로딩 하였습니다."));
			pc.setQuizTime(curtime);
		} catch (Exception exception35) {}
	}


	private void charname(L1PcInstance pc, String cmd) {
		try {
			StringTokenizer tok = new StringTokenizer(cmd);
			String chaName = tok.nextToken();
			if (pc.getLevel() > 80){
				pc.sendPackets(new S_SystemMessage("\\fU레벨80이상은 캐릭명을 변경할수 없습니다."));
				return;
			}
			if (pc.getClanid() > 0){
				pc.sendPackets(new S_SystemMessage("\\fU혈맹탈퇴후 캐릭명을 변경할수 있습니다."));
				return;
			}
			if (!pc.getInventory().checkItem(467009, 1)) { // 있나 체크
				pc.sendPackets(new S_SystemMessage("\\fU케릭명 변경 증표가 없습니다."));
				return; 
			}
			for (int i = 0;i<chaName.length();i++) {  
				if (chaName.charAt(i) == 'ㄱ' || chaName.charAt(i) == 'ㄲ' || chaName.charAt(i) == 'ㄴ' || chaName.charAt(i) == 'ㄷ' ||    //한문자(char)단위로 비교.
						chaName.charAt(i) == 'ㄸ' || chaName.charAt(i) == 'ㄹ' || chaName.charAt(i) == 'ㅁ' || chaName.charAt(i) == 'ㅂ' ||    //한문자(char)단위로 비교 
						chaName.charAt(i) == 'ㅃ' || chaName.charAt(i) == 'ㅅ' || chaName.charAt(i) == 'ㅆ' || chaName.charAt(i) == 'ㅇ' ||    //한문자(char)단위로 비교
						chaName.charAt(i) == 'ㅈ' || chaName.charAt(i) == 'ㅉ' || chaName.charAt(i) == 'ㅊ' || chaName.charAt(i) == 'ㅋ' ||    //한문자(char)단위로 비교.
						chaName.charAt(i) == 'ㅌ' || chaName.charAt(i) == 'ㅍ' || chaName.charAt(i) == 'ㅎ' || chaName.charAt(i) == 'ㅛ' ||    //한문자(char)단위로 비교.
						chaName.charAt(i) == 'ㅕ' || chaName.charAt(i) == 'ㅑ' || chaName.charAt(i) == 'ㅐ' || chaName.charAt(i) == 'ㅔ' ||    //한문자(char)단위로 비교.
						chaName.charAt(i) == 'ㅗ' || chaName.charAt(i) == 'ㅓ' || chaName.charAt(i) == 'ㅏ' || chaName.charAt(i) == 'ㅣ' ||    //한문자(char)단위로 비교.
						chaName.charAt(i) == 'ㅠ' || chaName.charAt(i) == 'ㅜ' || chaName.charAt(i) == 'ㅡ' || chaName.charAt(i) == 'ㅒ' ||    //한문자(char)단위로 비교.
						chaName.charAt(i) == 'ㅖ' || chaName.charAt(i) == 'ㅢ' || chaName.charAt(i) == 'ㅟ' || chaName.charAt(i) == 'ㅝ' ||    //한문자(char)단위로 비교.
						chaName.charAt(i) == 'ㅞ' || chaName.charAt(i) == 'ㅙ' || chaName.charAt(i) == 'ㅚ' || chaName.charAt(i) == 'ㅘ' ||    //한문자(char)단위로 비교.
						chaName.charAt(i) == '씹' || chaName.charAt(i) == '좃' || chaName.charAt(i) == '좆' || chaName.charAt(i) == 'ㅤ'){
					pc.sendPackets(new S_SystemMessage("사용할수없는 케릭명입니다."));
					return; 
				}
			}
			if (chaName.length() == 0) {
				pc.sendPackets(new S_SystemMessage("변경할 케릭명을 입력하세요."));
				return;
			}
			if (BadNamesList.getInstance().isBadName(chaName)) {
				pc.sendPackets(new S_SystemMessage("사용할 수 없는 케릭명입니다."));
				return;
			}
			if (isInvalidName(chaName)) {
				pc.sendPackets(new S_SystemMessage("사용할 수 없는 케릭명입니다."));
				return;
			}
			if (CharacterTable.doesCharNameExist(chaName)) {
				pc.sendPackets(new S_SystemMessage("동일한 케릭명이 존재합니다."));
				return;
			}
			String oldname = pc.getName();

			chaname(chaName,oldname);
			chanameok(pc);
			pc.getInventory().consumeItem(467009, 1); // 소모
		} catch (Exception e){
			pc.sendPackets(new S_SystemMessage("[.케릭명변경] [바꾸실아이디] 입력해주세요."));
		}
	}


	/** 변경 가능한지 검사한다 시작**/
	private static boolean isAlphaNumeric(String s) {
		boolean flag = true;
		char ac[] = s.toCharArray();
		int i = 0;
		do {
			if (i >= ac.length) {
				break;
			}
			if (!Character.isLetterOrDigit(ac[i])) {
				flag = false;
				break;
			}
			i++;
		} while (true);
		return flag;
	}

	private static boolean isInvalidName(String name) {
		int numOfNameBytes = 0;
		try {
			numOfNameBytes = name.getBytes("EUC-KR").length;
		} catch (UnsupportedEncodingException e) {
			//	_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			return false;
		}

		if (isAlphaNumeric(name)) {
			return false;
		}
		if (5 < (numOfNameBytes - name.length()) || 12 < numOfNameBytes) {
			return false;
		}

		if (BadNamesList.getInstance().isBadName(name)) {
			return false;
		}
		return true;
	}
	/** 변경 가능한지 검사한다 끝**/
	private void chanameok(L1PcInstance pc){
		pc.sendPackets(new S_Disconnect());
	}

	private void chaname(String chaName,String oldname) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("UPDATE characters SET char_name=? WHERE char_name=?");
			pstm.setString(1, chaName);
			pstm.setString(2, oldname);
			pstm.execute();
		} catch (Exception e) {

		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}



	public void autoroot(L1PcInstance pc, String cmd, String param) {
		if (param.equalsIgnoreCase("끔")) { // 오토루팅 켬끔 명령어
			pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.STATUS_AUTOROOT, 0);
			pc.sendPackets(new S_SystemMessage("\\fY오토루팅을 해제합니다. ")); 


		} else if (param.equalsIgnoreCase("켬")) { // 오토루팅 켬끔 명령어
			pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.STATUS_AUTOROOT);
			pc.sendPackets(new S_SystemMessage("\\fY오토루팅을 활성화합니다. "));

		} else { // 오토루팅 켬끔 명령어
			pc.sendPackets(new S_SystemMessage(cmd + " [켬,끔] 라고 입력해 주세요. "));
		}
	}

	public void ment(L1PcInstance pc, String cmd, String param) { // 멘트
		if (param.equalsIgnoreCase("끔")) {
			pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.STATUS_MENT, 0);
			pc.sendPackets(new S_SystemMessage("\\fY오토루팅 멘트를 끕니다."));
		} else if (param.equalsIgnoreCase("켬")) {
			pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.STATUS_MENT);
			pc.sendPackets(new S_SystemMessage("\\fY오토루팅 멘트를 켭니다."));

		} else {
			pc.sendPackets(new S_SystemMessage(cmd + " [켬,끔] 라고 입력해 주세요. "));
		}
	}







	private void serchdroplist2(L1PcInstance gm, String param) {
		try {
			StringTokenizer tok = new StringTokenizer(param);
			String nameid = tok.nextToken();

			int npcid = 0;
			try {
				npcid = Integer.parseInt(nameid);
			} catch (NumberFormatException e) {
				npcid = NpcTable.getInstance().findNpcIdByName(nameid);
				if (npcid == 0) {
					gm.sendPackets(new S_SystemMessage("해당 몬스터가 발견되지 않았습니다."));
					return;
				}
			}
			gm.sendPackets(new S_Serchdrop2(npcid));
		} catch (Exception e) {
			//   _log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			gm.sendPackets(new S_SystemMessage(".몹드랍 [몬스터이름]을 입력해 주세요."));
			gm.sendPackets(new S_SystemMessage(".몹이름입력시 공백없이 정확히 입력해야 합니다."));
		}
	}

	private static boolean isDisitAlpha(String str) {  
		boolean check = true;
		for(int i = 0; i < str.length(); i++) {
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

	private static String encodePassword(String rawPassword)
	throws NoSuchAlgorithmException, UnsupportedEncodingException {
		byte buf[] = rawPassword.getBytes("UTF-8");
		buf = MessageDigest.getInstance("SHA").digest(buf);

		return Base64.encodeBytes(buf);
	}

	private void to_Change_Passwd(L1PcInstance pc, String passwd) {
		try {
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
				pc.sendPackets(new S_SystemMessage("\\fY-변경정보- 계정명:[" + login + "] 비밀번호:[" + passwd + "]"));
				pc.sendPackets(new S_SystemMessage("[" + pc.getName() + "]\\fY님의 암호변경이 성공적으로 완료되었습니다."));
			}
			rs.close();
			pstm.close();
			statement.close();
			con.close();
		} catch (Exception e) {
		}
	}

	private void changePassword(L1PcInstance pc, String param){
		try {
			StringTokenizer tok = new StringTokenizer(param);
			String passwd = tok.nextToken();

			Account account = Account.load(pc.getAccountName()); //추가 
			if(account.getquize() != null){
				pc.sendPackets(new S_SystemMessage(".퀴즈인증 [설정한퀴즈] \\fY인증후 다시입력하세요."));
				return;
			} // 암호변경시 퀴즈가 설정되어 있지 않다면 바꿀 수 없도록.

			if (passwd.length() < 4) {
				pc.sendPackets(new S_SystemMessage("최소 4자 이상 입력해 주십시오."));
				return;
			}

			if (passwd.length() > 10) {
				pc.sendPackets(new S_SystemMessage("최대 10자 이하로 입력해 주십시오."));
				return;
			}

			if (isDisitAlpha(passwd) == false) {
				pc.sendPackets(new S_SystemMessage("암호에 허용되지 않는 문자가 포함 되어 있습니다."));
				return;
			}

			to_Change_Passwd(pc, passwd);
		}
		catch (Exception e) {
			pc.sendPackets(new S_SystemMessage(".암호변경 [변경하실 암호]를 입력하세요."));
		}
	}

	private static boolean isDisitAlaha(String str) {
		boolean check = true;
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i)) // 숫자가 아니라면
					&& Character.isLetterOrDigit(str.charAt(i)) // 특수문자라면
					&& !Character.isUpperCase(str.charAt(i)) // 대문자가 아니라면
					&& Character.isWhitespace(str.charAt(i)) // 공백이라면
					&& !Character.isLowerCase(str.charAt(i))) { // 소문자가 아니라면
				check = false;
				break;
			}
		}
		return check;
	}	  	

	private void quize(L1PcInstance pc, String param) {
		try {
			StringTokenizer tok = new StringTokenizer(param);
			//String user = tok.nextToken();
			String quize = tok.nextToken();
			Account account = Account.load(pc.getAccountName());

			if (quize.length() < 4) {
				pc.sendPackets(new S_SystemMessage("최소 4자 이상 입력해 주십시오."));
				return;
			}

			if (quize.length() > 12) {
				pc.sendPackets(new S_SystemMessage("최대 12자 이하로 입력해 주십시오."));
				return;
			}
			if (isDisitAlpha(quize) == false) {
				pc.sendPackets(new S_SystemMessage("퀴즈에 허용되지 않는 문자가 포함되었습니다."));
				return;
			}

			if(account.getquize() != null){
				pc.sendPackets(new S_SystemMessage("이미 퀴즈가 설정되어 있습니다."));
				return;
			}
			account.setquize(quize);
			Account.updateQuize(account);
			pc.sendPackets(new S_SystemMessage("\\fT퀴즈암호 (" + quize + ") 가 설정되었습니다.\\fY[잊지마세요!]"));
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage(".퀴즈설정 [설정하실퀴즈]를 입력해주세요."));
		}
	}
	private void quize1(L1PcInstance pc, String cmd) {
		try {
			StringTokenizer tok = new StringTokenizer(cmd);
			String quize2 = tok.nextToken();
			Account account = Account.load(pc.getAccountName());

			if (quize2.length() < 4) {
				pc.sendPackets(new S_SystemMessage("최소 4자 이상 입력해 주십시오."));
				return;
			}

			if (quize2.length() > 12) {
				pc.sendPackets(new S_SystemMessage("최대 12자 이하로 입력해 주십시오."));
				return;
			}

			if(account.getquize() == null || account.getquize() == ""){
				pc.sendPackets(new S_SystemMessage("퀴즈가 설정되어 있지 않습니다.\\fY [.퀴즈설정] [암호]"));
				return;
			}
			if (!quize2.equals(account.getquize())) {
				pc.sendPackets(new S_SystemMessage("퀴즈가 일치하지 않습니다.\\fY잊었나요?? 건의게시판 문의"));
				return;
			}
			if (isDisitAlpha(quize2) == false ) {
				pc.sendPackets(new S_SystemMessage("퀴즈에 허용되지 않는 문자가 포함되었습니다."));
				return;
			}
			account.setquize(null);
			Account.updateQuize(account);
			pc.sendPackets(new S_SystemMessage("퀴즈인증이 완료 되었습니다.\\fY새로운 퀴즈를 설정하세요"));
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage(".퀴즈인증 [설정하신퀴즈]를 입력하세요."));
		}
	}



	/**
	 *  여기는 유저 커맨드 실행 함수
	 **/
	private void showPrStatus(L1PcInstance pc){
		try {

			String logid = pc.getAccountName();//account 불러오기
			ArrayList<Integer> prDetails = new ArrayList<Integer>();

			//홍보 카운트 불러오기
			prDetails = AutoAdvertising.getInstance().getPrDetails(logid);
			//int _pr=0;
			int _prCount=0;

			if(!prDetails.isEmpty()){ //만약 값이 존재한다면
				//_pr = prDetails.get(0); //홍보기 실행의 유무
				_prCount = prDetails.get(1);//홍보횟수 
			}

			if(_prCount >= 2) {
				pc.sendPackets(new S_SystemMessage(pc.getName() + "님의 홍보 포인트는 [" + _prCount + "]포인트 입니다."));
				pc.sendPackets(new S_SystemMessage("홍보 포인트 정산을 시작하겠습니다."));				
				pc.getInventory().storeItem(5555, 40); // 깃털 지급 ( 포인트  * 5 )
				pc.sendPackets(new S_SystemMessage("홍보정산: 홍보상자 40개를 받았습니다."));
				_prCount = _prCount - 2;
				AutoAdvertising.getInstance().storePrCount(logid, _prCount); //홍보횟수 Update
				pc.sendPackets(new S_SystemMessage("홍보 포인트 정산이 완료되었습니다."));
			}else{
				pc.sendPackets(new S_SystemMessage(pc.getName() + "님의 홍보 포인트는 [" + _prCount + "]포인트 입니다."));
				pc.sendPackets(new S_SystemMessage("홍보 포인트 정산은 [2]포인트 이상만 가능합니다."));
				pc.sendPackets(new S_SystemMessage("반자동 홍보를 하시면 포인트를 획득할수 있습니다."));
			}
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage("\\fT[.홍보정산]을 입력하십시오."));
		}
	}
	private void Hunt(L1PcInstance pc, String cmd) {
		try { 
			StringTokenizer st = new StringTokenizer(cmd);
			String char_name = st.nextToken();
			int price = Integer.parseInt(st.nextToken());
			String story = st.nextToken();

			L1PcInstance target = null;
			target = L1World.getInstance().getPlayer(char_name);
			if (target != null) {
				if (target.isGm()){ return;}
				if (char_name.equals(pc.getName())) {
					pc.sendPackets(new S_SystemMessage("자신에게 현상금을 걸수 없습니다."));
					return;}
				if (target.getHuntCount() == 1) {
					pc.sendPackets(new S_SystemMessage("이미 수배 되어있습니다"));
					return;
				}
				if (price < 300000) {
					pc.sendPackets(new S_SystemMessage("최소 금액은 30만 아데나입니다"));
					return;
				}
				if (!(pc.getInventory().checkItem(40308, price))) {
					pc.sendPackets(new S_SystemMessage("아데나가 부족합니다"));
					return;
				}
				if (story.length() > 20) {
					pc.sendPackets(new S_SystemMessage("이유는 짧게 20글자로 입력하세요"));
					return;
				}
				target.setHuntCount(1);
				target.setHuntPrice(target.getHuntPrice() + price);
				target.setReasonToHunt(story);
				target.save();
				L1World.getInstance().broadcastPacketToAll(new S_SystemMessage("\\fY이유: " + story + " "));
				L1World.getInstance().broadcastPacketToAll(new S_SystemMessage("\\fY"+pc.getName()+ "님께서 "+target.getName()+ "님에게"));
				L1World.getInstance().broadcastServerMessage("\\fY현상금 (" + target.getHuntPrice()+")아데나를 겁니다.");
				pc.getInventory().consumeItem(40308, price);
			} else {
				pc.sendPackets(new S_SystemMessage("접속중이지 않습니다."));
			}
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage(".수배 [캐릭터명] [금액] [이유]"));
		}
	}

	//나이입력 By 모카
	private void age(L1PcInstance pc, String cmd) {
		try {
			StringTokenizer tok = new StringTokenizer(cmd);
			String AGE = tok.nextToken();
			int AGEint = Integer.parseInt(AGE);

			if (AGEint > 99) {
				pc.sendPackets(new S_SystemMessage("입력하신 나이는 올바른 값이 아닙니다."));
				return;
			}

			pc.setAge(AGEint);
			pc.save();
			pc.sendPackets(new S_SystemMessage(pc.getName()+" 님의 나이 (" + AGEint
					+ ")가 설정되었습니다."));
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage("  사용 예) .나이 28"));
		}
	}

	private void age2(L1PcInstance pc, String cmd) {
		try {
			StringTokenizer tok = new StringTokenizer(cmd);
			String AGE2 = tok.nextToken();
			int AGEint2 = Integer.parseInt(AGE2);

			switch (AGEint2) {
			case 1:
				pc.sendPackets(new S_Age(pc , 1));  
				break;
			case 2:
				pc.sendPackets(new S_Age(pc , 2));  
				break;
			case 3:
				pc.sendPackets(new S_Age(pc , 3));  
				break;
			case 4:
				pc.sendPackets(new S_Age(pc , 4));  
				break;
			default:
				pc.sendPackets(new S_SystemMessage("  .족보 1~4 만 가능합니다."));
			}
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage("  사용 예) .족보 2"));
		}
	}

	//족보 By 모카

	/** 혈맹 파티 신청 명령어 **/ 
	public void ClanParty (L1PcInstance pc){
		int ClanId = pc.getClanid();
		if (ClanId != 0 && (pc.getClanRank() == 3 || pc.isCrown())){ //Clan[O] [군주,수호기사]
			for (L1PcInstance SearchBlood : L1World.getInstance().getAllPlayers()) {
				if(SearchBlood.getClanid()!= ClanId || SearchBlood.isPrivateShop()
						|| SearchBlood.isInParty()){ // 클랜이 같지않다면[X], 이미파티중이면[X], 상점중[X]
					continue; // 포문탈출
				} else if(SearchBlood.getName() != pc.getName()){
					pc.setPartyType(1); // 파티타입 설정
					SearchBlood.setPartyID(pc.getId()); // 파티아이디 설정
					SearchBlood.sendPackets(new S_Message_YN(954, pc.getName())); // 분패파티 신청
					pc.sendPackets(new S_SystemMessage("당신은 ["+SearchBlood.getName()+"]에게 파티를 신청했습니다."));
				}
			}
		} else { // 클랜이 없거나 군주 또는 수호기사 [X]
			pc.sendPackets(new S_SystemMessage("혈맹의 군주, 수호기사만 사용할수 있습니다."));
		}
	} 



	private void Sealedoff(L1PcInstance pc, String param) {
		try {
			StringTokenizer tok = new StringTokenizer(param);
			int sealCount = Integer.parseInt(tok.nextToken(), 10);
			Account account = Account.load(pc.getAccountName()); //추가 
			if(account.getquize() != null){
				pc.sendPackets(new S_SystemMessage(".퀴즈인증 [설정한퀴즈] \\fY인증후 다시입력하세요."));
				return;
			} // 봉인해제시 퀴즈가 설정되어 있지 않다면 신청불가능 하도록

			if (pc.getInventory().getWeight240() == 240){
				pc.sendPackets(new S_SystemMessage("아이템이 너무 무거워, 더 이상 가질 수 없습니다."));
				return;
			}
			if(sealCount > 15) {
				pc.sendPackets(new S_SystemMessage("봉인해제주문서 15개 이상 신청 할 수 없습니다."));
				return;				
			}
			if (pc.getInventory().checkItem(50021, 1)) {
				pc.sendPackets(new S_SystemMessage("봉인해제주문서를 모두다 사용후 신청하세요."));
				return;				
			}
			createNewItem(pc, 50021, sealCount);
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage(".봉인해제신청 [신청할 장수] 을 입력하세요."));
		}
	}

	private void rank(L1PcInstance pc, String  param) {
		Connection con = null;
		int q = 0;
		int i = 0;
		int j = 0;
		int n = pc.getType();
		//int x = pc.getExp();
		int objid = pc.getId();

		String type = null;
		try {
			switch(pc.getType()) {
			case 0:
				type = "군주";
				break;
			case 1:
				type = "기사";
				break;
			case 2:
				type = "요정";
				break;
			case 3:
				type = "마법사";
				break;
			case 4:
				type = "다크엘프";
				break;
			case 5:
				type = "용기사";
				break;
			case 6:
				type = "환술사";
				break;
			}

			if (!(pc.getInventory().checkItem(40308, 10000)))
			{
				pc.sendPackets(new S_SystemMessage("아데나가 부족하여 사용할 수 없습니다."));
				return;
			}
			pc.getInventory().consumeItem(40308, 10000);

			con = L1DatabaseFactory.getInstance().getConnection();
			Statement pstm = con.createStatement();
			ResultSet rs = pstm.executeQuery("SELECT objid FROM characters WHERE AccessLevel = 0 order by Exp desc");
			Statement pstm2 = con.createStatement();
			ResultSet rs2 = pstm2.executeQuery("SELECT `Exp`,`char_name` FROM `characters` WHERE AccessLevel = 0 ORDER BY `Exp` DESC limit 1");

			if(pc.getType()==0){
				Statement pstm3 = con.createStatement();  
				ResultSet rs3 = pstm3.executeQuery("SELECT objid FROM characters WHERE type = 0 and AccessLevel = 0 order by Exp desc");
				while(rs3.next()){
					j++;
					if(objid == rs3.getInt(1))
						break;
				}
				rs3.close(); 
				pstm3.close();
			}else if(pc.getType()==1){
				Statement pstm3 = con.createStatement();  
				ResultSet rs3 = pstm3.executeQuery("SELECT objid FROM characters WHERE type = 1 and AccessLevel = 0 order by Exp desc");
				while(rs3.next()){
					j++;
					if(objid == rs3.getInt(1))
						break;
				}
				rs3.close(); 
				pstm3.close();
			}else if(pc.getType()==2){
				Statement pstm3 = con.createStatement();  
				ResultSet rs3 = pstm3.executeQuery("SELECT objid FROM characters WHERE type = 2 and AccessLevel = 0 order by Exp desc");
				while(rs3.next()){
					j++;
					if(objid == rs3.getInt(1))
						break;
				}
				rs3.close(); 
				pstm3.close();
			}else if(pc.getType()==3){
				Statement pstm3 = con.createStatement();  
				ResultSet rs3 = pstm3.executeQuery("SELECT objid FROM characters WHERE type = 3 and AccessLevel = 0 order by Exp desc");
				while(rs3.next()){
					j++;
					if(objid == rs3.getInt(1))
						break;
				}
				rs3.close(); 
				pstm3.close();
			}else if(pc.getType()==4){
				Statement pstm3 = con.createStatement();  
				ResultSet rs3 = pstm3.executeQuery("SELECT objid FROM characters WHERE type = 4 and AccessLevel = 0 order by Exp desc");
				while(rs3.next()){
					j++;
					if(objid == rs3.getInt(1))
						break;
				}
				rs3.close(); 
				pstm3.close();
			}else if(pc.getType()==5){
				Statement pstm3 = con.createStatement();  
				ResultSet rs3 = pstm3.executeQuery("SELECT objid FROM characters WHERE type = 5 and AccessLevel = 0 order by Exp desc");
				while(rs3.next()){
					j++;
					if(objid == rs3.getInt(1))
						break;
				}
				rs3.close(); 
				pstm3.close();
			}else if(pc.getType()==6){
				Statement pstm3 = con.createStatement();  
				ResultSet rs3 = pstm3.executeQuery("SELECT objid FROM characters WHERE type = 6 and AccessLevel = 0 order by Exp desc");
				while(rs3.next()){
					j++;
					if(objid == rs3.getInt(1))
						break;
				}
				rs3.close(); 
				pstm3.close();
			}

			while (rs2.next()) {
				i++;
				pc.sendPackets(new S_SystemMessage("\\fY현재 [테스트]서버 1위는 ["+rs2.getString("char_name")+"]님 이십니다."));
			}
			while(rs.next()){
				q++;
				if(objid == rs.getInt(1))
					break;
			}
			pc.sendPackets(new S_SystemMessage("서버순위:" + q + "위, 클래스순위:"+ j +"위"));
			rs.close(); 
			pstm.close();
			rs2.close(); 
			pstm2.close();

			con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void rank1(L1PcInstance pc, String  param) {
		Connection con = null;
		int q = 0;
		int i = 0;
		int j = 0;
		String type = null;
		String type1 = null;
		String Clan = null;
		String poly = null;


		try {
			StringTokenizer stringtokenizer = new StringTokenizer(param);
			String para1 = stringtokenizer.nextToken();  
			L1PcInstance TargetPc = L1World.getInstance().getPlayer(para1); 
			int objid = TargetPc.getId();
			int ClanID = TargetPc.getClanid();  
			if (param != null) {

				switch(pc.getType()) {
				case 0:
					type = "군주";
					break;
				case 1:
					type = "기사";
					break;
				case 2:
					type = "요정";
					break;
				case 3:
					type = "마법사";
					break;
				case 4:
					type = "다크엘프";
					break;
				case 5:
					type = "용기사";
					break;
				case 6:
					type = "환술사";
					break;
				}

				if (!(pc.getInventory().checkItem(40308, 30000)))
				{
					pc.sendPackets(new S_SystemMessage("아데나가 부족하여 사용할 수 없습니다."));
					return;
				}
				pc.getInventory().consumeItem(40308, 30000);

				con = L1DatabaseFactory.getInstance().getConnection();
				Statement pstm = con.createStatement();
				ResultSet rs = pstm.executeQuery("SELECT objid FROM characters WHERE AccessLevel = 0 order by Exp desc");

				if(TargetPc.getType()==0){
					Statement pstm2 = con.createStatement();  
					ResultSet rs2 = pstm2.executeQuery("SELECT objid FROM characters WHERE type = 0 and AccessLevel = 0 order by Exp desc");
					while(rs2.next()){
						j++;
						if(objid == rs2.getInt(1))
							break;
					}
					rs2.close(); 
					pstm2.close();
				}else if(TargetPc.getType()==1){
					Statement pstm2 = con.createStatement();  
					ResultSet rs2 = pstm2.executeQuery("SELECT objid FROM characters WHERE type = 1 and AccessLevel = 0 order by Exp desc");
					while(rs2.next()){
						j++;
						if(objid == rs2.getInt(1))
							break;
					}
					rs2.close(); 
					pstm2.close();
				}else if(TargetPc.getType()==2){
					Statement pstm2 = con.createStatement();  
					ResultSet rs2 = pstm2.executeQuery("SELECT objid FROM characters WHERE type = 2 and AccessLevel = 0 order by Exp desc");
					while(rs2.next()){
						j++;
						if(objid == rs2.getInt(1))
							break;
					}
					rs2.close(); 
					pstm2.close();
				}else if(TargetPc.getType()==3){
					Statement pstm2 = con.createStatement();  
					ResultSet rs2 = pstm2.executeQuery("SELECT objid FROM characters WHERE type = 3 and AccessLevel = 0 order by Exp desc");
					while(rs2.next()){
						j++;
						if(objid == rs2.getInt(1))
							break;
					}
					rs2.close(); 
					pstm2.close();
				}else if(TargetPc.getType()==4){
					Statement pstm2 = con.createStatement();  
					ResultSet rs2 = pstm2.executeQuery("SELECT objid FROM characters WHERE type = 4 and AccessLevel = 0 order by Exp desc");
					while(rs2.next()){
						j++;
						if(objid == rs2.getInt(1))
							break;
					}
					rs2.close(); 
					pstm2.close();
				}else if(TargetPc.getType()==5){
					Statement pstm2 = con.createStatement();  
					ResultSet rs2 = pstm2.executeQuery("SELECT objid FROM characters WHERE type = 5 and AccessLevel = 0 order by Exp desc");
					while(rs2.next()){
						j++;
						if(objid == rs2.getInt(1))
							break;
					}
					rs2.close(); 
					pstm2.close();
				}else if(TargetPc.getType()==6){
					Statement pstm2 = con.createStatement();  
					ResultSet rs2 = pstm2.executeQuery("SELECT objid FROM characters WHERE type = 6 and AccessLevel = 0 order by Exp desc");
					while(rs2.next()){
						j++;
						if(objid == rs2.getInt(1))
							break;
					}
					rs2.close(); 
					pstm2.close();
				}

				while (rs.next()) {

					q++;
					if(objid == rs.getInt(1))
						break;
				} 

				if(TargetPc.getType()== 0){
					type1 ="군주";
				}else if(TargetPc.getType()== 1){
					type1 ="기사";
				}else if(TargetPc.getType()== 2){
					type1 ="요정";
				}else if(TargetPc.getType()== 3){
					type1 ="마법사";
				}else if(TargetPc.getType()== 4){
					type1 ="다크엘프";
				}else if(TargetPc.getType()== 5){
					type1 ="용기사";
				}else if(TargetPc.getType()== 6){
					type1 ="환술사";
				}

				if(ClanID != 0){
					Clan = TargetPc.getClanname();
				}else{
					Clan = "-";
				}
				if(TargetPc.getLevel()<= 39){
					poly = "해골";
				}else if(TargetPc.getLevel()>= 40&&TargetPc.getLevel()<= 44){
					poly = "서큐버스";
				}else if(TargetPc.getLevel()>= 45&&TargetPc.getLevel()<= 49){
					poly = "카스파";
				}else if(TargetPc.getLevel()== 50){
					poly = "바포메트";
				}else if(TargetPc.getLevel()== 51){
					poly = "커츠";
				}else if(TargetPc.getLevel()>= 52&&TargetPc.getLevel()<= 54){
					poly = "데스나이트";
				}else if(TargetPc.getLevel()>= 55&&TargetPc.getLevel()<= 59){
					poly = "다크나이트";
				}else if(TargetPc.getLevel()>= 60&&TargetPc.getLevel()<= 64){
					poly = "실버나이트";
				}else if(TargetPc.getLevel()>= 65&&TargetPc.getLevel()<= 69){
					poly = "소드마스터";
				}else if(TargetPc.getLevel()>= 70&&TargetPc.getLevel()<= 74){
					poly = "아크나이트";
				}else if(TargetPc.getLevel()>= 75&&TargetPc.getLevel()<= 100){
					poly = "골드아크";
				}  

				pc.sendPackets(new S_SystemMessage("("+TargetPc.getName()+") 서버순위:"+q+"위, 클래스순위:"+j+"위 \n변신레벨:"+ poly +", 클래스:"+type1+", 혈맹:"+Clan+""));
				rs.close(); 
				pstm.close();
				con.close();
			}else if(param == pc.getName()){
				pc.sendPackets(new S_SystemMessage("케릭터가 월드내에 존재 하지 않습니다")); 
			}
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage(".랭킹검색 [케릭터명]으로 입력해 주십시요."));
		}

	}

	private boolean createNewItem(L1PcInstance pc, int item_id, int count) {
		L1ItemInstance item = ItemTable.getInstance().createItem(item_id);
		item.setCount(count);
		if (item != null) {
			if (pc.getInventory().checkAddItem(item, count) == L1Inventory.OK) {
				pc.getInventory().storeItem(item);
			} else { // 가질 수  없는 경우는 지면에 떨어뜨리는 처리의 캔슬은 하지 않는다(부정 방지)
				L1World.getInstance().getInventory(pc.getX(), pc.getY(),pc.getMapId()).storeItem(item);
			}
			pc.sendPackets(new S_ServerMessage(403, item.getLogName())); // %0를 손에 넣었습니다.
			return true;
		} else {
			return false;
		}
	}



	private void buff(L1PcInstance pc) {
		int[] allBuffSkill = { PHYSICAL_ENCHANT_DEX, PHYSICAL_ENCHANT_STR, BLESS_WEAPON,
				ADVANCE_SPIRIT };
		if (pc.isDead()) return;

		long curtime = System.currentTimeMillis() / 1000;
		if (pc.getQuizTime() + 20 > curtime ) {
			pc.sendPackets(new S_SystemMessage("20초간의 지연시간이 필요합니다."));
			return;
		}
		if (pc.getLevel() <= 60) { 
			try {
				L1SkillUse l1skilluse = new L1SkillUse();
				for (int i = 0; i < allBuffSkill.length ; i++) {
					l1skilluse.handleCommands(pc, allBuffSkill[i], pc.getId(), pc.getX(), pc.getY(), null, 0, L1SkillUse.TYPE_GMBUFF);
					pc.setQuizTime(curtime);
				} 				
			} catch (Exception e) {
			}
		} else {
			pc.sendPackets(new S_SystemMessage("60레벨 이후는 버프를 받을수 없습니다."));
		}
	} 



	private void infoitem(L1PcInstance pc, String param){
		try {
			StringTokenizer tok = new StringTokenizer(param);
			String charname = tok.nextToken();


			L1PcInstance target = L1World.getInstance().getPlayer(charname);

			if(!pc.isGm() && (charname.compareTo("메티스")==0)){
				pc.sendPackets(new S_SystemMessage("운영자님을 감히 조사를 할수 없습니다."));
				return;
			}
			if (target != null){
				pc.sendPackets(new S_SystemMessage(""+ target.getName() +"님의 정보"));
				pc.sendPackets(new S_SystemMessage("\\fY+9무기 이상 : " + target.getInventory().getItemEnchantCount(1, 9) +"개 = +7방어구 이상 : " + target.getInventory().getItemEnchantCount(2, 7)+"개"));
			} else {
				pc.sendPackets(new S_SystemMessage("["+charname+"]님은 미접속중입니다."));
			}
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage(".조사 [캐릭터명] 으로 입력해 주시기 바랍니다."));
		}	
	}
}


