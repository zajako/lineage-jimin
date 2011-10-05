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

package l1j.server.server;

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

import server.manager.eva;

import l1j.server.Base64;
import l1j.server.L1DatabaseFactory;
import l1j.server.SpecialEventHandler;
import l1j.server.GameSystem.Boss.BossSpawnTimeController;
import l1j.server.server.command.L1Commands;
import l1j.server.server.command.executor.L1CommandExecutor;
import l1j.server.server.datatables.ModelSpawnTable;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.serverpackets.S_Test;
import l1j.server.server.templates.L1Command;
import l1j.server.server.utils.SQLUtil;

//Referenced classes of package l1j.server.server:
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
		return "l1j.server.server.command.executor." + className;
	}
	
	private boolean executeDatabaseCommand(L1PcInstance pc, String name,
			String arg) {
		try {
			L1Command command = L1Commands.get(name);
			if (command == null) {
				return false;
			}
			if (pc.getAccessLevel() < command.getLevel()) {
				pc.sendPackets(new S_ServerMessage(74, "[Command] 커멘드 " + name)); // \f1%0은 사용할 수 없습니다.
				return true;
			}

			Class<?> cls = Class.forName(complementClassName(command. getExecutorClassName()));
			L1CommandExecutor exe = (L1CommandExecutor)cls.getMethod("getInstance").invoke(null);
			exe.execute(pc, name, arg);
			eva.LogCommandAppend(pc.getName(), name, arg);
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
			gm.sendPackets(new S_ServerMessage(74, "[Command] 커맨드 " + cmd));
			return;
		}
		eva.LogCommandAppend(gm.getName(), cmd, param);
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
		} else if (cmd.equalsIgnoreCase("정리")) {
			Clear(gm);
		} else if (cmd.equalsIgnoreCase("불")) {
			spawnmodel(gm, param);
		} else if (cmd.equalsIgnoreCase("보스")){
			BossSpawnTimeController.getBossTime(gm);
		} else if (cmd.equalsIgnoreCase("재실행")) {
			if (!_lastCommands.containsKey(gm.getId())) {
				gm.sendPackets(new S_ServerMessage(74, "[Command] 커맨드 " + cmd)); // \f1%0은 사용할 수 없습니다.
				return;
			}
			redo(gm, param);			
			return;
		} else {
			gm.sendPackets(new S_SystemMessage("[Command] 커멘드 " + cmd + " 는 존재하지 않습니다. "));
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
		pc.sendPackets(new S_SystemMessage(".종료 .영구추방 .추방 .계정압류 .밴아이피 .킬"));
		pc.sendPackets(new S_SystemMessage(".엔피씨이미지 .인벤이미지 .스폰 .배치 .스킬보기"));
		pc.sendPackets(new S_SystemMessage(".버프 .변신 .소생 .올버프 .전체버프 .겜블 .설문"));
		pc.sendPackets(new S_SystemMessage(".아데나 .아이템 .세트아이템 .선물 .렙선물 .액션 "));
		pc.sendPackets(new S_SystemMessage(".채팅 .채금 .셋팅 .서먼 .청소 .날씨 .소환. 파티소환"));
		pc.sendPackets(new S_SystemMessage(".홈타운 .귀환 .출두 .스킬마스터 .레벨 .속도업"));
		pc.sendPackets(new S_SystemMessage(".이동 .위치 .누구 .정보 .피바 .감시 .투명 .불투명"));
		pc.sendPackets(new S_SystemMessage(".리로드트랩 .쇼트랩 .리셋트랩 .실행 .재실행  .고스폰"));		
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

	private void packetbox(L1PcInstance pc, String param) {
		try {
			StringTokenizer st = new StringTokenizer(param);
			int id = Integer.parseInt(st.nextToken(), 10);			
			pc.sendPackets(new S_PacketBox(id));
		} catch (Exception exception) {
			pc.sendPackets(new S_SystemMessage("[Command] .패킷박스 [id] 입력"));
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
