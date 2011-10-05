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


import bone.server.server.serverpackets.S_Message_YN;//������Ƽ

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

import java.io.UnsupportedEncodingException; //## A112 ��ȣ���� ��ɾ� �߰����� ����Ʈ
import java.security.MessageDigest; //## A112 ��ȣ���� ��ɾ� �߰����� ����Ʈ
import bone.server.Base64; //## A112 ��ȣ���� ��ɾ� �߰����� ����Ʈ
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
		// ������ ��������� Ŀ�ǵ�, �� ���Ĵ� ������ �ܶ����� �� �Ķ���ͷμ� ����Ѵ�
		String cmd = token.nextToken();
		String param = "";
		while (token.hasMoreTokens()) {
			param = new StringBuilder(param).append(token.nextToken()).append(' ').toString();
		}
		param = param.trim();




		if (cmd.equalsIgnoreCase("����") || cmd.equalsIgnoreCase("��������")) {
			showHelp(pc);
		//} else if (cmd.equalsIgnoreCase("����")){
		//	infoitem(pc, param);
		} else if (cmd.equalsIgnoreCase("����")){
			buff(pc);
		} else if (cmd.equalsIgnoreCase("�ҿ�")) { hope(pc, param);
		} else if (cmd.equalsIgnoreCase(".") || cmd.equalsIgnoreCase("�ڷ�Ǯ��")) {
			tell(pc);
		} else if (cmd.equalsIgnoreCase("ȫ������") || cmd.equalsIgnoreCase("ȫ��Ȯ��") || cmd.equalsIgnoreCase("ȫ������")) {//������ȫ����
			showPrStatus(pc);
		} else if (cmd.equalsIgnoreCase("�������") || cmd.equalsIgnoreCase("��ȣ����")) {
			changePassword(pc, param);
		} else if (cmd.equalsIgnoreCase("���") || cmd.equalsIgnoreCase("������")) {
			serchdroplist(pc, param) ;
		} else if (cmd.equalsIgnoreCase("�����") || cmd.equalsIgnoreCase("����")) {
			serchdroplist2(pc, param) ;
		} else if (cmd.equalsIgnoreCase("����") || cmd.equalsIgnoreCase("�������")) {
			Hunt(pc, param);
		} else if (cmd.equalsIgnoreCase("�����") || cmd.equalsIgnoreCase("����") || cmd.equalsIgnoreCase("�����ڵ�")) {
			quize(pc, param);
		} else if (cmd.equalsIgnoreCase("��������") || cmd.equalsIgnoreCase("����")) {
			quize1(pc, param);
		} else if (cmd.equalsIgnoreCase("����������û") || cmd.equalsIgnoreCase("���������ֹ���")) {
			Sealedoff(pc, param);
		} else if (cmd.equalsIgnoreCase("���̼���")) {
			age(pc, param);
		} else if (cmd.equalsIgnoreCase("��������")) {
			age2(pc, param);
		} else if(cmd.equalsIgnoreCase("������Ƽ")){
			ClanParty(pc);
		} else if(cmd.equalsIgnoreCase("���λ���") || cmd.equalsIgnoreCase("��������")) {
			supportHelp(pc);		
		} else if (cmd.equalsIgnoreCase("��ŷȮ��")) {
			rank(pc,param);
		} else if (cmd.equalsIgnoreCase("��ŷ�˻�")) {
			rank1(pc,param);
		} else if(cmd.equalsIgnoreCase("����")){
			check(pc);
		} else if(cmd.equalsIgnoreCase("��������") || cmd.equalsIgnoreCase("����")) {
			showHelp1(pc);
		} else if(cmd.equalsIgnoreCase("�������")) { //�̿ϼ�
			autoroot(pc, cmd, param);
		} else if(cmd.equalsIgnoreCase("��Ʈ") || cmd.equalsIgnoreCase("�����Ʈ"))  {
			ment(pc, cmd, param); //by ��� ��Ʈ ��ɾ� 
		} else if(cmd.equalsIgnoreCase("�ɸ�����") || cmd.equalsIgnoreCase("ĳ������")) {
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
			// ���� ó��
			L1MonsterInstance mob = null;
			for (L1Object obj : pc.getNearObjects().getKnownObjects()) {
				if (obj instanceof L1MonsterInstance) {
					mob = (L1MonsterInstance) obj;
					if (mob.getNpcTemplate().is_doppel() && mob.getName().equals(pc.getName())) {
						Broadcaster.broadcastPacket(mob, new S_NpcChatPacket(mob, cmdLine, 0));
					}
				}
			}
			bone.LogChatAppend("��", pc.getName(), cmdLine);			
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
					gm.sendPackets(new S_SystemMessage("�ش� �������� �߰ߵ��� �ʾҽ��ϴ�."));
					return;
				}
			}
			gm.sendPackets(new S_Serchdrop(itemid));
		} catch (Exception e) {
			//   _log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			gm.sendPackets(new S_SystemMessage(".�������Ʈ [itemid �Ǵ� name]�� �Է��� �ּ���."));
			gm.sendPackets(new S_SystemMessage("������ name�� ������� ��Ȯ�� �Է��ؾ� �մϴ�."));
			gm.sendPackets(new S_SystemMessage("ex) .��� ������(����Ƽ�׷���Ʈ) -- > �˻� O"));
			gm.sendPackets(new S_SystemMessage("ex) .��� �� -- > �˻� X"));
		}
	} //�߰�


	private void hope(L1PcInstance pc, String cmd) {        //�巡�ﺼ
		for (L1Object obj : L1World.getInstance().getVisibleObjects(pc, 10)) { // 5 ���� ���� ������Ʈ�� ã�Ƽ�
			if (obj instanceof L1MonsterInstance){ // ���Ͷ��
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
									"�׷� �������� �� ���� ����...�ҿ��� ������ �̸��� �ٿ� ���..���ѷ���..�ð��� ��..��"));
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
										pc.sendPackets(new S_ChatPacket(pc, ""+nameid+" ��(��) ������ ����� �ּ���!!" , Opcodes.S_OPCODE_NORMALCHAT, 2));
										Thread.sleep(5000);
										pc.getInventory(). storeItem(item);
										L1World.getInstance().broadcastPacketToAll(new S_PacketBox(S_PacketBox.GREEN_MESSAGE, 
												""+pc.getName()+"����.."+nameid+"��!.... ���� �ҿ��� �̷������..�׷� �̸�...."));
										for (L1Object obj1 : L1World.getInstance().getVisibleObjects(pc, 20)) { // 20 ���� ���� ������Ʈ�� ã�Ƽ�
											if (obj1 instanceof L1MonsterInstance){ // ���Ͷ��
												L1MonsterInstance mon1 = (L1MonsterInstance) obj;
												if(mon1.getNpcTemplate().get_npcId() == 45000172 ){
													mon1.receiveDamage(gm, 90000); // ������          
												}    
											}
										}          

									} else {
										break;
									}
								} 
							} else {
								L1World.getInstance().broadcastPacketToAll(new S_PacketBox(S_PacketBox.GREEN_MESSAGE, 
								" �׷� �������� �� ���� ����...�ҿ��� ������ �̸��� �ٿ����..���ѷ���..�ð��� ��..��"));
							}
						} catch (Exception e) {    
							pc.sendPackets(new S_SystemMessage(
							".�ҿ� [�������̸�] �̶�� �Է�. ex).�ҿ� �ο�ƺ����"));
						}
			}

		}
	}

	private void check(L1PcInstance pc) {
		try {
			long curtime = System.currentTimeMillis() / 1000;
			if (pc.getQuizTime() + 20 > curtime ) {
				pc.sendPackets(new S_SystemMessage("20�ʰ��� �����ð��� �ʿ��մϴ�."));
				return;
			}
			int entertime = pc.getGdungeonTime() % 1000;
			int a = 180 - entertime;
			int hpr = pc.getHpr() + pc.getInventory(). hpRegenPerTick();
			int mpr = pc.getMpr() + pc.getInventory(). mpRegenPerTick();	

			pc.sendPackets(new S_SystemMessage("===================( ���� ���� )===================="));				
			pc.sendPackets(new S_SystemMessage("\\fY(��ƽ:" + hpr + ')' + "(��ƽ:" + mpr + ')' +"(�Ⱘ:"+a+"��)(PKȽ��:" + pc.get_PKcount() + ")(����:"+ pc.getAbility().getElixirCount()+ "��)"));
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
		pc.sendPackets(new S_SystemMessage("\\fU������ �ѽ��� �����Ͻø� ���λ����� �˴ϴ�."));
	}	

	private void tell(L1PcInstance pc) { 
		try {
			if (pc.isPinkName())
			{
				pc.sendPackets(new S_SystemMessage("�������̶� ����� �� �����ϴ�."));
				return;
			}
			long curtime = System.currentTimeMillis() / 1000;
			if (pc.getQuizTime() + 20 > curtime ) {
				pc.sendPackets(new S_SystemMessage("20�ʰ��� �����ð��� �ʿ��մϴ�."));
				return;
			}
			L1Teleport.teleport(pc, pc.getX(), pc.getY(), pc.getMapId(), pc.getMoveState().getHeading(), false);
			pc.sendPackets(new S_SystemMessage("�ֺ� ������Ʈ�� ��ε� �Ͽ����ϴ�."));
			pc.setQuizTime(curtime);
		} catch (Exception exception35) {}
	}


	private void charname(L1PcInstance pc, String cmd) {
		try {
			StringTokenizer tok = new StringTokenizer(cmd);
			String chaName = tok.nextToken();
			if (pc.getLevel() > 80){
				pc.sendPackets(new S_SystemMessage("\\fU����80�̻��� ĳ������ �����Ҽ� �����ϴ�."));
				return;
			}
			if (pc.getClanid() > 0){
				pc.sendPackets(new S_SystemMessage("\\fU����Ż���� ĳ������ �����Ҽ� �ֽ��ϴ�."));
				return;
			}
			if (!pc.getInventory().checkItem(467009, 1)) { // �ֳ� üũ
				pc.sendPackets(new S_SystemMessage("\\fU�ɸ��� ���� ��ǥ�� �����ϴ�."));
				return; 
			}
			for (int i = 0;i<chaName.length();i++) {  
				if (chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��' ||    //�ѹ���(char)������ ��.
						chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��' ||    //�ѹ���(char)������ �� 
						chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��' ||    //�ѹ���(char)������ ��
						chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��' ||    //�ѹ���(char)������ ��.
						chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��' ||    //�ѹ���(char)������ ��.
						chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��' ||    //�ѹ���(char)������ ��.
						chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��' ||    //�ѹ���(char)������ ��.
						chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��' ||    //�ѹ���(char)������ ��.
						chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��' ||    //�ѹ���(char)������ ��.
						chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��' ||    //�ѹ���(char)������ ��.
						chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��'){
					pc.sendPackets(new S_SystemMessage("����Ҽ����� �ɸ����Դϴ�."));
					return; 
				}
			}
			if (chaName.length() == 0) {
				pc.sendPackets(new S_SystemMessage("������ �ɸ����� �Է��ϼ���."));
				return;
			}
			if (BadNamesList.getInstance().isBadName(chaName)) {
				pc.sendPackets(new S_SystemMessage("����� �� ���� �ɸ����Դϴ�."));
				return;
			}
			if (isInvalidName(chaName)) {
				pc.sendPackets(new S_SystemMessage("����� �� ���� �ɸ����Դϴ�."));
				return;
			}
			if (CharacterTable.doesCharNameExist(chaName)) {
				pc.sendPackets(new S_SystemMessage("������ �ɸ����� �����մϴ�."));
				return;
			}
			String oldname = pc.getName();

			chaname(chaName,oldname);
			chanameok(pc);
			pc.getInventory().consumeItem(467009, 1); // �Ҹ�
		} catch (Exception e){
			pc.sendPackets(new S_SystemMessage("[.�ɸ�����] [�ٲٽǾ��̵�] �Է����ּ���."));
		}
	}


	/** ���� �������� �˻��Ѵ� ����**/
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
	/** ���� �������� �˻��Ѵ� ��**/
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
		if (param.equalsIgnoreCase("��")) { // ������� �Բ� ��ɾ�
			pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.STATUS_AUTOROOT, 0);
			pc.sendPackets(new S_SystemMessage("\\fY��������� �����մϴ�. ")); 


		} else if (param.equalsIgnoreCase("��")) { // ������� �Բ� ��ɾ�
			pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.STATUS_AUTOROOT);
			pc.sendPackets(new S_SystemMessage("\\fY��������� Ȱ��ȭ�մϴ�. "));

		} else { // ������� �Բ� ��ɾ�
			pc.sendPackets(new S_SystemMessage(cmd + " [��,��] ��� �Է��� �ּ���. "));
		}
	}

	public void ment(L1PcInstance pc, String cmd, String param) { // ��Ʈ
		if (param.equalsIgnoreCase("��")) {
			pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.STATUS_MENT, 0);
			pc.sendPackets(new S_SystemMessage("\\fY������� ��Ʈ�� ���ϴ�."));
		} else if (param.equalsIgnoreCase("��")) {
			pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.STATUS_MENT);
			pc.sendPackets(new S_SystemMessage("\\fY������� ��Ʈ�� �մϴ�."));

		} else {
			pc.sendPackets(new S_SystemMessage(cmd + " [��,��] ��� �Է��� �ּ���. "));
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
					gm.sendPackets(new S_SystemMessage("�ش� ���Ͱ� �߰ߵ��� �ʾҽ��ϴ�."));
					return;
				}
			}
			gm.sendPackets(new S_Serchdrop2(npcid));
		} catch (Exception e) {
			//   _log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			gm.sendPackets(new S_SystemMessage(".����� [�����̸�]�� �Է��� �ּ���."));
			gm.sendPackets(new S_SystemMessage(".���̸��Է½� ������� ��Ȯ�� �Է��ؾ� �մϴ�."));
		}
	}

	private static boolean isDisitAlpha(String str) {  
		boolean check = true;
		for(int i = 0; i < str.length(); i++) {
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
				pc.sendPackets(new S_SystemMessage("\\fY-��������- ������:[" + login + "] ��й�ȣ:[" + passwd + "]"));
				pc.sendPackets(new S_SystemMessage("[" + pc.getName() + "]\\fY���� ��ȣ������ ���������� �Ϸ�Ǿ����ϴ�."));
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

			Account account = Account.load(pc.getAccountName()); //�߰� 
			if(account.getquize() != null){
				pc.sendPackets(new S_SystemMessage(".�������� [����������] \\fY������ �ٽ��Է��ϼ���."));
				return;
			} // ��ȣ����� ��� �����Ǿ� ���� �ʴٸ� �ٲ� �� ������.

			if (passwd.length() < 4) {
				pc.sendPackets(new S_SystemMessage("�ּ� 4�� �̻� �Է��� �ֽʽÿ�."));
				return;
			}

			if (passwd.length() > 10) {
				pc.sendPackets(new S_SystemMessage("�ִ� 10�� ���Ϸ� �Է��� �ֽʽÿ�."));
				return;
			}

			if (isDisitAlpha(passwd) == false) {
				pc.sendPackets(new S_SystemMessage("��ȣ�� ������ �ʴ� ���ڰ� ���� �Ǿ� �ֽ��ϴ�."));
				return;
			}

			to_Change_Passwd(pc, passwd);
		}
		catch (Exception e) {
			pc.sendPackets(new S_SystemMessage(".��ȣ���� [�����Ͻ� ��ȣ]�� �Է��ϼ���."));
		}
	}

	private static boolean isDisitAlaha(String str) {
		boolean check = true;
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i)) // ���ڰ� �ƴ϶��
					&& Character.isLetterOrDigit(str.charAt(i)) // Ư�����ڶ��
					&& !Character.isUpperCase(str.charAt(i)) // �빮�ڰ� �ƴ϶��
					&& Character.isWhitespace(str.charAt(i)) // �����̶��
					&& !Character.isLowerCase(str.charAt(i))) { // �ҹ��ڰ� �ƴ϶��
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
				pc.sendPackets(new S_SystemMessage("�ּ� 4�� �̻� �Է��� �ֽʽÿ�."));
				return;
			}

			if (quize.length() > 12) {
				pc.sendPackets(new S_SystemMessage("�ִ� 12�� ���Ϸ� �Է��� �ֽʽÿ�."));
				return;
			}
			if (isDisitAlpha(quize) == false) {
				pc.sendPackets(new S_SystemMessage("��� ������ �ʴ� ���ڰ� ���ԵǾ����ϴ�."));
				return;
			}

			if(account.getquize() != null){
				pc.sendPackets(new S_SystemMessage("�̹� ��� �����Ǿ� �ֽ��ϴ�."));
				return;
			}
			account.setquize(quize);
			Account.updateQuize(account);
			pc.sendPackets(new S_SystemMessage("\\fT�����ȣ (" + quize + ") �� �����Ǿ����ϴ�.\\fY[����������!]"));
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage(".����� [�����Ͻ�����]�� �Է����ּ���."));
		}
	}
	private void quize1(L1PcInstance pc, String cmd) {
		try {
			StringTokenizer tok = new StringTokenizer(cmd);
			String quize2 = tok.nextToken();
			Account account = Account.load(pc.getAccountName());

			if (quize2.length() < 4) {
				pc.sendPackets(new S_SystemMessage("�ּ� 4�� �̻� �Է��� �ֽʽÿ�."));
				return;
			}

			if (quize2.length() > 12) {
				pc.sendPackets(new S_SystemMessage("�ִ� 12�� ���Ϸ� �Է��� �ֽʽÿ�."));
				return;
			}

			if(account.getquize() == null || account.getquize() == ""){
				pc.sendPackets(new S_SystemMessage("��� �����Ǿ� ���� �ʽ��ϴ�.\\fY [.�����] [��ȣ]"));
				return;
			}
			if (!quize2.equals(account.getquize())) {
				pc.sendPackets(new S_SystemMessage("��� ��ġ���� �ʽ��ϴ�.\\fY�ؾ�����?? ���ǰԽ��� ����"));
				return;
			}
			if (isDisitAlpha(quize2) == false ) {
				pc.sendPackets(new S_SystemMessage("��� ������ �ʴ� ���ڰ� ���ԵǾ����ϴ�."));
				return;
			}
			account.setquize(null);
			Account.updateQuize(account);
			pc.sendPackets(new S_SystemMessage("���������� �Ϸ� �Ǿ����ϴ�.\\fY���ο� ��� �����ϼ���"));
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage(".�������� [�����Ͻ�����]�� �Է��ϼ���."));
		}
	}



	/**
	 *  ����� ���� Ŀ�ǵ� ���� �Լ�
	 **/
	private void showPrStatus(L1PcInstance pc){
		try {

			String logid = pc.getAccountName();//account �ҷ�����
			ArrayList<Integer> prDetails = new ArrayList<Integer>();

			//ȫ�� ī��Ʈ �ҷ�����
			prDetails = AutoAdvertising.getInstance().getPrDetails(logid);
			//int _pr=0;
			int _prCount=0;

			if(!prDetails.isEmpty()){ //���� ���� �����Ѵٸ�
				//_pr = prDetails.get(0); //ȫ���� ������ ����
				_prCount = prDetails.get(1);//ȫ��Ƚ�� 
			}

			if(_prCount >= 2) {
				pc.sendPackets(new S_SystemMessage(pc.getName() + "���� ȫ�� ����Ʈ�� [" + _prCount + "]����Ʈ �Դϴ�."));
				pc.sendPackets(new S_SystemMessage("ȫ�� ����Ʈ ������ �����ϰڽ��ϴ�."));				
				pc.getInventory().storeItem(5555, 40); // ���� ���� ( ����Ʈ  * 5 )
				pc.sendPackets(new S_SystemMessage("ȫ������: ȫ������ 40���� �޾ҽ��ϴ�."));
				_prCount = _prCount - 2;
				AutoAdvertising.getInstance().storePrCount(logid, _prCount); //ȫ��Ƚ�� Update
				pc.sendPackets(new S_SystemMessage("ȫ�� ����Ʈ ������ �Ϸ�Ǿ����ϴ�."));
			}else{
				pc.sendPackets(new S_SystemMessage(pc.getName() + "���� ȫ�� ����Ʈ�� [" + _prCount + "]����Ʈ �Դϴ�."));
				pc.sendPackets(new S_SystemMessage("ȫ�� ����Ʈ ������ [2]����Ʈ �̻� �����մϴ�."));
				pc.sendPackets(new S_SystemMessage("���ڵ� ȫ���� �Ͻø� ����Ʈ�� ȹ���Ҽ� �ֽ��ϴ�."));
			}
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage("\\fT[.ȫ������]�� �Է��Ͻʽÿ�."));
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
					pc.sendPackets(new S_SystemMessage("�ڽſ��� ������� �ɼ� �����ϴ�."));
					return;}
				if (target.getHuntCount() == 1) {
					pc.sendPackets(new S_SystemMessage("�̹� ���� �Ǿ��ֽ��ϴ�"));
					return;
				}
				if (price < 300000) {
					pc.sendPackets(new S_SystemMessage("�ּ� �ݾ��� 30�� �Ƶ����Դϴ�"));
					return;
				}
				if (!(pc.getInventory().checkItem(40308, price))) {
					pc.sendPackets(new S_SystemMessage("�Ƶ����� �����մϴ�"));
					return;
				}
				if (story.length() > 20) {
					pc.sendPackets(new S_SystemMessage("������ ª�� 20���ڷ� �Է��ϼ���"));
					return;
				}
				target.setHuntCount(1);
				target.setHuntPrice(target.getHuntPrice() + price);
				target.setReasonToHunt(story);
				target.save();
				L1World.getInstance().broadcastPacketToAll(new S_SystemMessage("\\fY����: " + story + " "));
				L1World.getInstance().broadcastPacketToAll(new S_SystemMessage("\\fY"+pc.getName()+ "�Բ��� "+target.getName()+ "�Կ���"));
				L1World.getInstance().broadcastServerMessage("\\fY����� (" + target.getHuntPrice()+")�Ƶ����� �̴ϴ�.");
				pc.getInventory().consumeItem(40308, price);
			} else {
				pc.sendPackets(new S_SystemMessage("���������� �ʽ��ϴ�."));
			}
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage(".���� [ĳ���͸�] [�ݾ�] [����]"));
		}
	}

	//�����Է� By ��ī
	private void age(L1PcInstance pc, String cmd) {
		try {
			StringTokenizer tok = new StringTokenizer(cmd);
			String AGE = tok.nextToken();
			int AGEint = Integer.parseInt(AGE);

			if (AGEint > 99) {
				pc.sendPackets(new S_SystemMessage("�Է��Ͻ� ���̴� �ùٸ� ���� �ƴմϴ�."));
				return;
			}

			pc.setAge(AGEint);
			pc.save();
			pc.sendPackets(new S_SystemMessage(pc.getName()+" ���� ���� (" + AGEint
					+ ")�� �����Ǿ����ϴ�."));
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage("  ��� ��) .���� 28"));
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
				pc.sendPackets(new S_SystemMessage("  .���� 1~4 �� �����մϴ�."));
			}
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage("  ��� ��) .���� 2"));
		}
	}

	//���� By ��ī

	/** ���� ��Ƽ ��û ��ɾ� **/ 
	public void ClanParty (L1PcInstance pc){
		int ClanId = pc.getClanid();
		if (ClanId != 0 && (pc.getClanRank() == 3 || pc.isCrown())){ //Clan[O] [����,��ȣ���]
			for (L1PcInstance SearchBlood : L1World.getInstance().getAllPlayers()) {
				if(SearchBlood.getClanid()!= ClanId || SearchBlood.isPrivateShop()
						|| SearchBlood.isInParty()){ // Ŭ���� �����ʴٸ�[X], �̹���Ƽ���̸�[X], ������[X]
					continue; // ����Ż��
				} else if(SearchBlood.getName() != pc.getName()){
					pc.setPartyType(1); // ��ƼŸ�� ����
					SearchBlood.setPartyID(pc.getId()); // ��Ƽ���̵� ����
					SearchBlood.sendPackets(new S_Message_YN(954, pc.getName())); // ������Ƽ ��û
					pc.sendPackets(new S_SystemMessage("����� ["+SearchBlood.getName()+"]���� ��Ƽ�� ��û�߽��ϴ�."));
				}
			}
		} else { // Ŭ���� ���ų� ���� �Ǵ� ��ȣ��� [X]
			pc.sendPackets(new S_SystemMessage("������ ����, ��ȣ��縸 ����Ҽ� �ֽ��ϴ�."));
		}
	} 



	private void Sealedoff(L1PcInstance pc, String param) {
		try {
			StringTokenizer tok = new StringTokenizer(param);
			int sealCount = Integer.parseInt(tok.nextToken(), 10);
			Account account = Account.load(pc.getAccountName()); //�߰� 
			if(account.getquize() != null){
				pc.sendPackets(new S_SystemMessage(".�������� [����������] \\fY������ �ٽ��Է��ϼ���."));
				return;
			} // ���������� ��� �����Ǿ� ���� �ʴٸ� ��û�Ұ��� �ϵ���

			if (pc.getInventory().getWeight240() == 240){
				pc.sendPackets(new S_SystemMessage("�������� �ʹ� ���ſ�, �� �̻� ���� �� �����ϴ�."));
				return;
			}
			if(sealCount > 15) {
				pc.sendPackets(new S_SystemMessage("���������ֹ��� 15�� �̻� ��û �� �� �����ϴ�."));
				return;				
			}
			if (pc.getInventory().checkItem(50021, 1)) {
				pc.sendPackets(new S_SystemMessage("���������ֹ����� ��δ� ����� ��û�ϼ���."));
				return;				
			}
			createNewItem(pc, 50021, sealCount);
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage(".����������û [��û�� ���] �� �Է��ϼ���."));
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
				type = "����";
				break;
			case 1:
				type = "���";
				break;
			case 2:
				type = "����";
				break;
			case 3:
				type = "������";
				break;
			case 4:
				type = "��ũ����";
				break;
			case 5:
				type = "����";
				break;
			case 6:
				type = "ȯ����";
				break;
			}

			if (!(pc.getInventory().checkItem(40308, 10000)))
			{
				pc.sendPackets(new S_SystemMessage("�Ƶ����� �����Ͽ� ����� �� �����ϴ�."));
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
				pc.sendPackets(new S_SystemMessage("\\fY���� [�׽�Ʈ]���� 1���� ["+rs2.getString("char_name")+"]�� �̽ʴϴ�."));
			}
			while(rs.next()){
				q++;
				if(objid == rs.getInt(1))
					break;
			}
			pc.sendPackets(new S_SystemMessage("��������:" + q + "��, Ŭ��������:"+ j +"��"));
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
					type = "����";
					break;
				case 1:
					type = "���";
					break;
				case 2:
					type = "����";
					break;
				case 3:
					type = "������";
					break;
				case 4:
					type = "��ũ����";
					break;
				case 5:
					type = "����";
					break;
				case 6:
					type = "ȯ����";
					break;
				}

				if (!(pc.getInventory().checkItem(40308, 30000)))
				{
					pc.sendPackets(new S_SystemMessage("�Ƶ����� �����Ͽ� ����� �� �����ϴ�."));
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
					type1 ="����";
				}else if(TargetPc.getType()== 1){
					type1 ="���";
				}else if(TargetPc.getType()== 2){
					type1 ="����";
				}else if(TargetPc.getType()== 3){
					type1 ="������";
				}else if(TargetPc.getType()== 4){
					type1 ="��ũ����";
				}else if(TargetPc.getType()== 5){
					type1 ="����";
				}else if(TargetPc.getType()== 6){
					type1 ="ȯ����";
				}

				if(ClanID != 0){
					Clan = TargetPc.getClanname();
				}else{
					Clan = "-";
				}
				if(TargetPc.getLevel()<= 39){
					poly = "�ذ�";
				}else if(TargetPc.getLevel()>= 40&&TargetPc.getLevel()<= 44){
					poly = "��ť����";
				}else if(TargetPc.getLevel()>= 45&&TargetPc.getLevel()<= 49){
					poly = "ī����";
				}else if(TargetPc.getLevel()== 50){
					poly = "������Ʈ";
				}else if(TargetPc.getLevel()== 51){
					poly = "Ŀ��";
				}else if(TargetPc.getLevel()>= 52&&TargetPc.getLevel()<= 54){
					poly = "��������Ʈ";
				}else if(TargetPc.getLevel()>= 55&&TargetPc.getLevel()<= 59){
					poly = "��ũ����Ʈ";
				}else if(TargetPc.getLevel()>= 60&&TargetPc.getLevel()<= 64){
					poly = "�ǹ�����Ʈ";
				}else if(TargetPc.getLevel()>= 65&&TargetPc.getLevel()<= 69){
					poly = "�ҵ帶����";
				}else if(TargetPc.getLevel()>= 70&&TargetPc.getLevel()<= 74){
					poly = "��ũ����Ʈ";
				}else if(TargetPc.getLevel()>= 75&&TargetPc.getLevel()<= 100){
					poly = "����ũ";
				}  

				pc.sendPackets(new S_SystemMessage("("+TargetPc.getName()+") ��������:"+q+"��, Ŭ��������:"+j+"�� \n���ŷ���:"+ poly +", Ŭ����:"+type1+", ����:"+Clan+""));
				rs.close(); 
				pstm.close();
				con.close();
			}else if(param == pc.getName()){
				pc.sendPackets(new S_SystemMessage("�ɸ��Ͱ� ���峻�� ���� ���� �ʽ��ϴ�")); 
			}
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage(".��ŷ�˻� [�ɸ��͸�]���� �Է��� �ֽʽÿ�."));
		}

	}

	private boolean createNewItem(L1PcInstance pc, int item_id, int count) {
		L1ItemInstance item = ItemTable.getInstance().createItem(item_id);
		item.setCount(count);
		if (item != null) {
			if (pc.getInventory().checkAddItem(item, count) == L1Inventory.OK) {
				pc.getInventory().storeItem(item);
			} else { // ���� ��  ���� ���� ���鿡 ����߸��� ó���� ĵ���� ���� �ʴ´�(���� ����)
				L1World.getInstance().getInventory(pc.getX(), pc.getY(),pc.getMapId()).storeItem(item);
			}
			pc.sendPackets(new S_ServerMessage(403, item.getLogName())); // %0�� �տ� �־����ϴ�.
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
			pc.sendPackets(new S_SystemMessage("20�ʰ��� �����ð��� �ʿ��մϴ�."));
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
			pc.sendPackets(new S_SystemMessage("60���� ���Ĵ� ������ ������ �����ϴ�."));
		}
	} 



	private void infoitem(L1PcInstance pc, String param){
		try {
			StringTokenizer tok = new StringTokenizer(param);
			String charname = tok.nextToken();


			L1PcInstance target = L1World.getInstance().getPlayer(charname);

			if(!pc.isGm() && (charname.compareTo("��Ƽ��")==0)){
				pc.sendPackets(new S_SystemMessage("��ڴ��� ���� ���縦 �Ҽ� �����ϴ�."));
				return;
			}
			if (target != null){
				pc.sendPackets(new S_SystemMessage(""+ target.getName() +"���� ����"));
				pc.sendPackets(new S_SystemMessage("\\fY+9���� �̻� : " + target.getInventory().getItemEnchantCount(1, 9) +"�� = +7�� �̻� : " + target.getInventory().getItemEnchantCount(2, 7)+"��"));
			} else {
				pc.sendPackets(new S_SystemMessage("["+charname+"]���� ���������Դϴ�."));
			}
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage(".���� [ĳ���͸�] ���� �Է��� �ֽñ� �ٶ��ϴ�."));
		}	
	}
}


