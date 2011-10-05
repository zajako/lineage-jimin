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
				pc.sendPackets(new S_ServerMessage(74, "[Command] " + name)); // \f1%0�� ����� �� �����ϴ�.
				return true;
			}

			Class<?> cls = Class.forName(complementClassName(command. getExecutorClassName()));
			L1CommandExecutor exe = (L1CommandExecutor)cls.getMethod("getInstance").invoke(null);
			exe.execute(pc, name, arg);
			bone.LogCommandAppend(pc.getName(), name, arg);
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
			gm.sendPackets(new S_ServerMessage(74, "[Command] " + cmd));
			return;
		}
		bone.LogCommandAppend(gm.getName(), cmd, param);
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
		} else if (cmd.equalsIgnoreCase("�˻�")) {  // #### �ɸ��˻�
			chainfo(gm, param);
		} else if (cmd.equalsIgnoreCase("����")) {    // #### ����������
			nocall(gm, param);
		} else if (cmd.equalsIgnoreCase("����")) {  // ##### ���� �˻� �߰� ########
            account_Cha(gm, param) ;
		} else if (cmd.equalsIgnoreCase("����")) {
			Clear(gm);
		} else if (cmd.equalsIgnoreCase("�����")) {
			hun(gm, param);
		} else if (cmd.equalsIgnoreCase("ä��Ǯ��")) { //////////�����Ѱ��߰�
			chatx(gm, param);
		} else if (cmd.equalsIgnoreCase("�˻�")) { // ########## �˻� �߰� ##########
			searchDatabase(gm, param);
		} else if (cmd.equalsIgnoreCase("��")) {
			spawnmodel(gm, param);
		} else if (cmd.equalsIgnoreCase("������")){
            reloadDB(gm, param); //�߰�
		} else if (cmd.equalsIgnoreCase("��ü����")) {
            	allpresent(gm, param);
		} else if (cmd.equalsIgnoreCase("��ȣ����")) {
		    changePassword(gm, param);
		} else if (cmd.equalsIgnoreCase("����")){
			BossSpawnTimeController.getBossTime(gm);
		} else if (cmd.equalsIgnoreCase("�����")) {
			if (!_lastCommands.containsKey(gm.getId())) {
				gm.sendPackets(new S_ServerMessage(74, "[Command] " + cmd)); // \f1%0�� ����� �� �����ϴ�.
				return;
			}
			redo(gm, param);			
			return;
		} else {
			gm.sendPackets(new S_SystemMessage("[Command] " + cmd + " �� �������� �ʽ��ϴ�. "));
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
		pc.sendPackets(new S_SystemMessage(".���� .�����߹� .�߹� .�����з� .������� .���� .ų"));
		pc.sendPackets(new S_SystemMessage(".���� .�̹��� .�κ��̹��� .��ġ .��ų����"));
		pc.sendPackets(new S_SystemMessage(".���� .���� .��Ȱ .�ù��� .��ü���� .�׺� .����"));
		pc.sendPackets(new S_SystemMessage(".�Ƶ��� .������ .��Ʈ������ .������ .��ü����"));
		pc.sendPackets(new S_SystemMessage(".ä�� .ä�� .���� .���� .û�� .���� .��ȯ. ��Ƽ��ȯ"));
		pc.sendPackets(new S_SystemMessage(".ȨŸ�� .��ȯ .��� .��ų������ .�ӵ� .�ǹ� .�׼�"));
		pc.sendPackets(new S_SystemMessage(".�̵� .��ġ .���� .���� .���ε�Ʈ�� .��Ʈ�� .����"));
		pc.sendPackets(new S_SystemMessage(".����Ʈ�� .����� .���� .����� .��ȯ .��"));
		pc.sendPackets(new S_SystemMessage(".������ .���� .�˻� .���� .���� .ä��Ǯ�� .��� "));
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
	
	private static String encodePassword1(String rawPassword) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		return rawPassword;
	}
	
	/* ���� ��ȣ ���� �޼ҵ� */
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
				gm.sendPackets(new S_SystemMessage("��ȣ �������� Account: [" + login + "] Password: [" + passwd + "]"));
				gm.sendPackets(new S_SystemMessage(pc.getName() + "�� ��ȣ ������ ���������� �Ϸ�Ǿ����ϴ�."));
				pc.sendPackets(new S_SystemMessage("������ ���� ������ ���ŵǾ����ϴ�."));
			}
			rs.close();
			pstm.close();
			statement.close();
			con.close();
		}catch (Exception e){
		}
	}
	
	/*
	 * �Է¹��� ��ȣ�� �ѱ��� ���Ե��� �ʾҴ��� Ȯ���� �ִ� �޼ҵ� 
	 * ������ ��ȣ�� �ѱ۷� �ٲ������� Ŭ���̾�Ʈ������ �Է��� ����� ����. 
	 */
	 private static boolean isDisitAlpha1(String str){
		 boolean check = true;
		 for(int i = 0; i < str.length(); i++){
			 if(!Character.isDigit(str.charAt(i)) // ���ڰ� �ƴ϶��
					 && Character.isLetterOrDigit(str.charAt(i)) // Ư�����ڶ��
					 && !Character.isUpperCase(str.charAt(i)) // �빮�ڰ� �ƴ϶��
					 && !Character.isLowerCase(str.charAt(i))) { // �ҹ��ڰ� �ƴ϶��
				 
				 check = false;
				 break;
			 }
		 }
		 return check;
	 }

	 /* 
	  * ��ȣ ���濡 �ʿ��� ������ �Է¹޴´�.
	  */
	 private void changePassword(L1PcInstance gm, String param){
		 try{
			 StringTokenizer tok = new StringTokenizer(param);
			 String user = tok.nextToken();
			 String passwd = tok.nextToken();
			 
			 if (passwd.length() < 4){
				 gm.sendPackets(new S_SystemMessage("�Է��Ͻ� ��ȣ�� �ڸ����� �ʹ� ª���ϴ�."));
				 gm.sendPackets(new S_SystemMessage("�ּ� 4�� �̻� �Է��� �ֽʽÿ�."));
				 return;
			 }
			 
			 if (passwd.length() > 12){
				 gm.sendPackets(new S_SystemMessage("�Է��Ͻ� ��ȣ�� �ڸ����� �ʹ� ��ϴ�."));
				 gm.sendPackets(new S_SystemMessage("�ִ� 12�� ���Ϸ� �Է��� �ֽʽÿ�."));
				 return;
			 }
			 
			 if (isDisitAlpha(passwd) == false){
				 gm.sendPackets(new S_SystemMessage("��ȣ�� ������ �ʴ� ���ڰ� ���ԵǾ����ϴ�."));
				 return;
			 }
			 
			 L1PcInstance target = L1World.getInstance().getPlayer(user);
			 if (target != null){
				 to_Change_Passwd(gm, target, passwd);
			 }else{
				 gm.sendPackets(new S_SystemMessage("�׷� �̸��� ���� ĳ���ʹ� �����ϴ�."));
			 }
		 }catch (Exception e){
			 gm.sendPackets(new S_SystemMessage(".��ȣ���� ĳ���� ��ȣ �� �Է����ּ���."));
		 }
	 }
	 
	 private void reloadDB(L1PcInstance gm, String cmd){
		 try{
			 DropTable.reload();
			 ShopTable.reload();
			 ItemTable.reload();
			 
			 gm.sendPackets(new S_SystemMessage("Table Update Complete..."));
		 }catch (Exception e){
			 gm.sendPackets(new S_SystemMessage(".������ ��� �Է��� �ּ���."));
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
	
	private void searchDatabase(L1PcInstance gm, String param) {
		try {
			StringTokenizer tok = new StringTokenizer(param);
			int type = Integer.parseInt(tok.nextToken());
			String name = tok.nextToken();
			searchObject(gm, type, name);
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".�˻� [0~4] [name]�� �Է� ���ּ���."));
			gm.sendPackets(new S_SystemMessage("0=etcitem, 1=weapon, 2=armor, 3=npc, 4=polymorphs"));   
			gm.sendPackets(new S_SystemMessage("name�� ��Ȯ�� �𸣰ų� ������� �Ǿ��ִ� ����"));
			gm.sendPackets(new S_SystemMessage("'%'�� ���̳� �ڿ� �ٿ� ���ʽÿ�."));
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
			case 0: // ����
				statement = con.prepareStatement("select item_id, name from etcitem where name Like '" + name + "'");
				break;
			case 1: // ����
				statement = con.prepareStatement("select item_id, name from weapon where name Like '" + name + "'");
				break;
			case 2: // ��
				statement = con.prepareStatement("select item_id, name from armor where name Like '" + name + "'");
				break;
			case 3: // ���Ǿ�
				statement = con.prepareStatement("select npcid, name from npc where name Like '" + name + "'");
				break;
			case 4: // ����
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
			gm.sendPackets(new S_SystemMessage("�� [" + count + "]���� �����Ͱ� �˻��Ǿ����ϴ�."));
		}
		catch (Exception e){
		}
	}
	
	//��ü����
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
						
						pc.sendPackets(new S_SystemMessage("��ڴԲ��� ��ü�������� ������["+ item.getViewName()
								+"]�� �־����ϴ�."));
					}
				}
			}
		}catch (Exception exception){
			gm.sendPackets(new S_SystemMessage(".��ü���� ������ID ��þƮ�� �����ۼ��� �Է��� �ּ���."));
		}
	}
	
	//ä��Ǯ��
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
				gm.sendPackets(new S_SystemMessage("�ش�ĳ���� ä���� ���� �߽��ϴ�.."));
			}
		}catch (Exception e){
			gm.sendPackets(new S_SystemMessage(".ä��Ǯ�� ĳ���͸� �̶�� �Է��� �ּ���."));
		}
	}
	
	//����ݽý���
	private void hun(L1PcInstance gm, String param){
		try{
			String huns = param;
			StringTokenizer stringtokenizer = new StringTokenizer(huns);
			String user = stringtokenizer.nextToken();
			String hunsc = stringtokenizer.nextToken();
			
			L1PcInstance target = L1World.getInstance().getPlayer(user);
			int count = Integer.parseInt(hunsc);
			if (target.getHuntCount() == 0 && gm.getInventory().consumeItem(560009, count)) { //��Ÿ ����
				target.setHuntCount(1);
				target.setHuntCount(count);
				target.save();
				
				L1World.getInstance().broadcastPacketToAll(
						new S_ServerMessage(166, "\\" + gm.getName() +"����","" + target.getName() + "�� �� ���ǸӴ�" + count + "���� �ɾ����ϴ�."));		
			}else{
				gm.sendPackets(new S_SystemMessage("�Ӵϰ� ���ų�" + target.getName() + "���� �� ������� �ɷ��ֽ��ϴ�."));
			}
		}catch (Exception e){
			gm.sendPackets(new S_SystemMessage((new StringBuilder()).append(".����� ĳ���� ����[���ǸӴ�]�� �Է��� �ּ���.").toString()));
		}
	}
	
	//���������� .����
	private void nocall(L1PcInstance gm, String param){
		try{
			StringTokenizer tokenizer = new StringTokenizer(param);
			String pcName = tokenizer.nextToken();
			
			L1PcInstance target = null;
			target = L1World.getInstance().getPlayer(pcName);
			if (target != null) { //Ÿ��
				L1Teleport.teleport(target, 33440, 32795, (short) 4, 5, true); /// ���Ե� ���� (������������������)
			}else{
				gm.sendPackets(new S_SystemMessage("���������� �ʴ� ���� ID �Դϴ�!"));
			}
		}catch (Exception e){
			gm.sendPackets(new S_SystemMessage(".���� [ĳ���͸�] ���� �Է��� �ּ���!"));
		}
	}
	
	//.����
	private void chainfo(L1PcInstance gm, String param) {
		try {
			StringTokenizer stringtokenizer = new StringTokenizer(param);
			String s = stringtokenizer.nextToken();
			gm.sendPackets(new S_Chainfo(1, s));
		} catch (Exception exception21) {
			gm.sendPackets(new S_SystemMessage(".�� [ĳ���͸�]�� �Է� ���ּ���."));
		}
	}
	
	//���� ������ �ִ� ĳ�� �˻� .����
	private void account_Cha( L1PcInstance gm, String param ){
		try{
			StringTokenizer tok = new StringTokenizer(param) ;
			String name = tok.nextToken();
			account_Cha2(gm, name);
		}catch (Exception e){
			 gm.sendPackets(new S_SystemMessage(".���� [���̵�]�� �Է����ּ���!"));
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
               java.sql.Connection con0 = null ; // �̸����� objid�� �˻��ϱ� ����
               con0 = L1DatabaseFactory.getInstance().getConnection() ;
               PreparedStatement statement0 = null ;
               statement0 = con0.prepareStatement("select account_name, Clanname  from characters where char_name = '"+ s_name + "'") ;
               ResultSet rs0 = statement0.executeQuery() ;
               while (rs0.next()) {
                      s_account = rs0.getString(1) ;
                      s_clan = rs0.getString(2) ;
                      gm.sendPackets(new S_SystemMessage("ĳ����" + s_name + "/����:" + s_account
                                   + "/Ŭ����:" + s_clan)) ;
                      count0++ ;
               }
               java.sql.Connection con = null ;
               con = L1DatabaseFactory.getInstance().getConnection() ;
               PreparedStatement statement = null ;
               statement = con.prepareStatement("select " + "char_name," + "level,"
                            + "Clanname," + "BonusStatus," + "OnlineStatus," + "MaxHp,"
                            + "MaxMp " + "from characters where account_name = '" + s_account
                            + "'") ;
               gm.sendPackets(new S_SystemMessage(">>> ���� ���� ã��")) ;
               ResultSet rs = statement.executeQuery() ;
               while (rs.next()) {
                      s_name = rs.getString(1) ;
                      s_level = rs.getString(2) ;
                      s_clan = rs.getString(3) ;
                      s_bonus = rs.getString(4) ;
                      s_online = rs.getString(5) ;
                      s_hp = rs.getString(6) ;
                      s_mp = rs.getString(7) ;
                      gm.sendPackets(new S_SystemMessage("[" + s_online + "]��[" + s_level
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
               gm.sendPackets(new S_SystemMessage("<<����ã��>>�� [" + count
                            + "]�� �˻�{1:������/0:��������----")) ;
        } catch (Exception e) {}
    }
	
	private void autoshop(L1PcInstance gm, String param){
		if(param.equalsIgnoreCase("��")){
			AutoShopManager.getInstance().isAutoShop(true);
			gm.sendPackets(new S_SystemMessage("[Command] ���λ��� ��"));
		} else if(param.equalsIgnoreCase("��")){
			AutoShopManager.getInstance().isAutoShop(false);
			gm.sendPackets(new S_SystemMessage("[Command] ���λ��� ��"));
		} else {
			gm.sendPackets(new S_SystemMessage("[Command] .���λ��� [�� or ��] �Է�"));
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
