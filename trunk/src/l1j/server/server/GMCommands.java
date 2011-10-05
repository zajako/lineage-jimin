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
				pc.sendPackets(new S_ServerMessage(74, "[Command] Ŀ��� " + name)); // \f1%0�� ����� �� �����ϴ�.
				return true;
			}

			Class<?> cls = Class.forName(complementClassName(command. getExecutorClassName()));
			L1CommandExecutor exe = (L1CommandExecutor)cls.getMethod("getInstance").invoke(null);
			exe.execute(pc, name, arg);
			eva.LogCommandAppend(pc.getName(), name, arg);
			_log.info('('+ pc.getName() + ")�� " + name + " " + arg + "��� ��ɾ ����߽��ϴ�. ");
			return true;
		} catch (Exception e) {
			_log.log(Level.SEVERE, "error gm command", e);
		}
		return false;
	}

	public void handleCommands(L1PcInstance gm, String cmdLine) {
		StringTokenizer token = new StringTokenizer(cmdLine);
		// ������ ��������� Ŀ�ǵ�, �� ���Ĵ� ������ �ܶ����� �� �Ķ���ͷμ� ����Ѵ�
		String cmd = token.nextToken();
		String param = "";
		while (token.hasMoreTokens()) {
			param = new StringBuilder(param).append(token.nextToken()).append(
			' ').toString();
		}
		param = param.trim();

		// ����Ÿ���̽�ȭ �� Ŀ���
		if (executeDatabaseCommand(gm, cmd, param)) {
			if (!cmd.equalsIgnoreCase("�����")) {
				_lastCommands.put(gm.getId(), cmdLine);
			}
			return;
		}

		if (gm.getAccessLevel() < 200) {
			gm.sendPackets(new S_ServerMessage(74, "[Command] Ŀ�ǵ� " + cmd));
			return;
		}
		eva.LogCommandAppend(gm.getName(), cmd, param);
		// GM�� �����ϴ� Ŀ�ǵ�� ���⿡ ����
		if (cmd.equalsIgnoreCase("����")) {
			showHelp(gm);		
		} else if (cmd.equalsIgnoreCase("��Ŷ�ڽ�")) {
			packetbox(gm, param);
		} else if (cmd.equalsIgnoreCase("�����߰�")) {
			addaccount(gm, param);
		} else if (cmd.equalsIgnoreCase("����")){				
			SpecialEventHandler.getInstance().doBugRace();
		} else if (cmd.equalsIgnoreCase("��ü����")){				
			SpecialEventHandler.getInstance().doAllBuf();
		} else if (cmd.equalsIgnoreCase("�������")){
			changepassword(gm, param);
		} else if (cmd.equalsIgnoreCase("�ڵ�")) {
			CodeTest(gm, param);
		} else if (cmd.equalsIgnoreCase("����")) {
			Clear(gm);
		} else if (cmd.equalsIgnoreCase("��")) {
			spawnmodel(gm, param);
		} else if (cmd.equalsIgnoreCase("����")){
			BossSpawnTimeController.getBossTime(gm);
		} else if (cmd.equalsIgnoreCase("�����")) {
			if (!_lastCommands.containsKey(gm.getId())) {
				gm.sendPackets(new S_ServerMessage(74, "[Command] Ŀ�ǵ� " + cmd)); // \f1%0�� ����� �� �����ϴ�.
				return;
			}
			redo(gm, param);			
			return;
		} else {
			gm.sendPackets(new S_SystemMessage("[Command] Ŀ��� " + cmd + " �� �������� �ʽ��ϴ�. "));
		}
	}

	private void spawnmodel(L1PcInstance gm, String param) {
		StringTokenizer st = new StringTokenizer(param);
		int type = Integer.parseInt(st.nextToken(), 10);		
		ModelSpawnTable.getInstance().insertmodel(gm, type);
		gm.sendPackets(new S_SystemMessage("[Command] �� �־���"));
	}

	private void showHelp(L1PcInstance pc) {
		pc.sendPackets(new S_SystemMessage("-------------------<GM ��ɾ�>--------------------"));
		pc.sendPackets(new S_SystemMessage(".���� .�����߹� .�߹� .�����з� .������� .ų"));
		pc.sendPackets(new S_SystemMessage(".���Ǿ��̹��� .�κ��̹��� .���� .��ġ .��ų����"));
		pc.sendPackets(new S_SystemMessage(".���� .���� .�һ� .�ù��� .��ü���� .�׺� .����"));
		pc.sendPackets(new S_SystemMessage(".�Ƶ��� .������ .��Ʈ������ .���� .������ .�׼� "));
		pc.sendPackets(new S_SystemMessage(".ä�� .ä�� .���� .���� .û�� .���� .��ȯ. ��Ƽ��ȯ"));
		pc.sendPackets(new S_SystemMessage(".ȨŸ�� .��ȯ .��� .��ų������ .���� .�ӵ���"));
		pc.sendPackets(new S_SystemMessage(".�̵� .��ġ .���� .���� .�ǹ� .���� .���� .������"));
		pc.sendPackets(new S_SystemMessage(".���ε�Ʈ�� .��Ʈ�� .����Ʈ�� .���� .�����  .����"));		
		pc.sendPackets(new S_SystemMessage("--------------------------------------------------"));
	}

	private static Map<Integer, String> _lastCommands = new HashMap<Integer, String>();

	private void redo(L1PcInstance pc, String arg) {
		try {
			String lastCmd = _lastCommands.get(pc.getId());
			if (arg.isEmpty()) {
				pc.sendPackets(new S_SystemMessage("[Command] Ŀ�ǵ� " + lastCmd
						+ " ��(��) ������մϴ�."));
				handleCommands(pc, lastCmd);
			} else {				
				StringTokenizer token = new StringTokenizer(lastCmd);
				String cmd = token.nextToken() + " " + arg;
				pc.sendPackets(new S_SystemMessage("[Command] Ŀ�ǵ� " + cmd + " ��(��) ������մϴ�."));
				handleCommands(pc, cmd);
			}
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			pc.sendPackets(new S_SystemMessage("[Command] .����� Ŀ�ǵ忡��"));
		}
	}

	private void packetbox(L1PcInstance pc, String param) {
		try {
			StringTokenizer st = new StringTokenizer(param);
			int id = Integer.parseInt(st.nextToken(), 10);			
			pc.sendPackets(new S_PacketBox(id));
		} catch (Exception exception) {
			pc.sendPackets(new S_SystemMessage("[Command] .��Ŷ�ڽ� [id] �Է�"));
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
				gm.sendPackets(new S_SystemMessage("[Command] �̹� ������ �ֽ��ϴ�."));
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
				gm.sendPackets(new S_SystemMessage("[Command] ���� �߰��� �Ϸ�Ǿ����ϴ�."));				
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
			if(!Character.isDigit(str.charAt(i))	// ���ڰ� �ƴ϶��
					&& Character.isLetterOrDigit(str.charAt(i))	// Ư�����ڶ��
					&& !Character.isUpperCase(str.charAt(i))	// �빮�ڰ� �ƴ϶��
					&& !Character.isLowerCase(str.charAt(i))) {	// �ҹ��ڰ� �ƴ϶��
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
				gm.sendPackets(new S_SystemMessage("[Command] �Է��Ͻ� �������� �ڸ����� �ʹ� ª���ϴ�."));
				gm.sendPackets(new S_SystemMessage("[Command] �ּ� 4�� �̻� �Է��� �ֽʽÿ�."));
				return;
			}
			if (passwd.length() < 4) {
				gm.sendPackets(new S_SystemMessage("[Command] �Է��Ͻ� ��ȣ�� �ڸ����� �ʹ� ª���ϴ�."));
				gm.sendPackets(new S_SystemMessage("[Command] �ּ� 4�� �̻� �Է��� �ֽʽÿ�."));
				return;
			}

			if (passwd.length() > 12) {
				gm.sendPackets(new S_SystemMessage("[Command] �Է��Ͻ� ��ȣ�� �ڸ����� �ʹ� ��ϴ�."));
				gm.sendPackets(new S_SystemMessage("[Command] �ִ� 12�� ���Ϸ� �Է��� �ֽʽÿ�."));
				return;
			}

			if (isDisitAlpha(passwd) == false) {
				gm.sendPackets(new S_SystemMessage("[Command] ��ȣ�� ������ �ʴ� ���ڰ� ���� �Ǿ� �ֽ��ϴ�."));
				return;
			}
			AddAccount(gm, user, passwd,"127.0.0.1","127.0.0.1");
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage("[Command] .�����߰� [������] [��ȣ] �Է�"));
		}
	}

	private void changepassword(L1PcInstance gm, String param){
		try {
			StringTokenizer tok = new StringTokenizer(param);
			String user = tok.nextToken();
			String oldpasswd = tok.nextToken();
			String newpasswd = tok.nextToken();

			if (user.length() < 4) {
				gm.sendPackets(new S_SystemMessage("[Command] �Է��Ͻ� �������� �ڸ����� �ʹ� ª���ϴ�."));
				gm.sendPackets(new S_SystemMessage("[Command] �ּ� 4�� �̻� �Է��� �ֽʽÿ�."));
				return;
			}
			if (newpasswd.length() < 4) {
				gm.sendPackets(new S_SystemMessage("[Command] �Է��Ͻ� ��ȣ�� �ڸ����� �ʹ� ª���ϴ�."));
				gm.sendPackets(new S_SystemMessage("[Command] �ּ� 4�� �̻� �Է��� �ֽʽÿ�."));
				return;
			}
			if (newpasswd.length() > 12) {
				gm.sendPackets(new S_SystemMessage("[Command] �Է��Ͻ� ��ȣ�� �ڸ����� �ʹ� ��ϴ�."));
				gm.sendPackets(new S_SystemMessage("[Command] �ִ� 12�� ���Ϸ� �Է��� �ֽʽÿ�."));
				return;
			}

			if (isDisitAlpha(newpasswd) == false) {
				gm.sendPackets(new S_SystemMessage("[Command] ��ȣ�� ������ �ʴ� ���ڰ� ���� �Ǿ� �ֽ��ϴ�."));
				return;
			}
			chkpassword(gm, user, oldpasswd, newpasswd);
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage("[Command] .������� [����] [������] [�ٲܺ��] �Է�"));
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
				gm.sendPackets(new S_SystemMessage("[Command] �Է��Ͻ� ������ �������� ���� ���� �ʽ��ϴ�."));
				return;
			}

			if (!isPasswordTrue(password,oldpassword)){				
				//System.out.println("���� ��� : " + oldpassword+" - üũ ��� : "+password);
				gm.sendPackets(new S_SystemMessage("[Command] ���� �������� ��й�ȣ�� ��ġ���� �ʽ��ϴ�. "));
				gm.sendPackets(new S_SystemMessage("[Command] �ٽ� Ȯ���Ͻð� ������ �ּ���."));
				return;
			} else {
				String sqlstr = "UPDATE accounts SET password=password(?) WHERE login=?";
				pstm = con.prepareStatement(sqlstr);
				pstm.setString(1, newpassword);
				pstm.setString(2, account);
				pstm.execute();
				gm.sendPackets(new S_SystemMessage("[Command] ������ : " + account+" / �ٲ��й�ȣ : " + newpassword));
				gm.sendPackets(new S_SystemMessage("[Command] ��й�ȣ ������ ���������� �Ϸ�Ǿ����ϴ�."));				
			}
			rs.close();
			pstm.close();
			statement.close();
			con.close();
		} catch (Exception e) {
		}
	}
	//�н����� �´��� ���� ����  
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
			if ( _rtnPwd.equals(Password)) { // �����ϴٸ�
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
			// �� �׽�Ʈ�� �ڵ尡 ������ ���� ��Ŷ �κ� 
			pc.sendPackets(new S_Test(pc,codetest));
			//pc.sendPackets(new S_PacketBox(S_PacketBox.ICON_AURA, codetest, time)); // �׽�,��� ���� �׽�Ʈ
			//pc.sendPackets(new S_CastleMaster(codetest, pc.getId())); // �հ� �׽�Ʈ
			//pc.sendPackets(new S_StatusReset(pc, codetest, 1)); // ���� �ʱ�ȭ �׽�Ʈ
		} catch (Exception exception) {
			pc.sendPackets(new S_SystemMessage("[Command] .�ڵ� [����] �Է�"));
		}
	}
	
	private void Clear(L1PcInstance gm) {
		for (L1Object obj : L1World.getInstance().getVisibleObjects(gm, 10)) { // 10 ���� ���� ������Ʈ�� ã�Ƽ�
			if (obj instanceof L1MonsterInstance){ // ���Ͷ��
				L1NpcInstance npc = (L1NpcInstance) obj;
				npc.receiveDamage(gm, 50000); // ������
				if (npc.getCurrentHp() <= 0){
					gm.sendPackets(new S_SkillSound(obj.getId() , 1815)); // ��
					Broadcaster.broadcastPacket(gm, new S_SkillSound(obj.getId() , 1815));
				}else{
					gm.sendPackets(new S_SkillSound(obj.getId() , 1815));
					Broadcaster.broadcastPacket(gm, new S_SkillSound(obj.getId() , 1815));
				}
			}else if (obj instanceof L1PcInstance){ // pc���
				L1PcInstance player = (L1PcInstance) obj;
				player.receiveDamage(player, 0, false); // ������
					if (player.getCurrentHp() <= 0){
						gm.sendPackets(new S_SkillSound(obj.getId() , 1815)); // ��
						Broadcaster.broadcastPacket(gm, new S_SkillSound(obj.getId() , 1815));
					}else{
						gm.sendPackets(new S_SkillSound(obj.getId() , 1815));
						Broadcaster.broadcastPacket(gm, new S_SkillSound(obj.getId() , 1815));
					}
				}
			}
		}
}
