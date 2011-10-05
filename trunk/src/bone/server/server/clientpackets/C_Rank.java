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

package bone.server.server.clientpackets;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.LineageClient;

import bone.server.server.TimeController.LiveTimeController;
import bone.server.server.datatables.CharacterTable;
import bone.server.server.model.Broadcaster;
import bone.server.server.model.L1Clan;
import bone.server.server.model.L1War;
import bone.server.server.model.L1World;
import bone.server.server.model.Instance.L1ItemInstance;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.serverpackets.S_Message_YN;
import bone.server.server.serverpackets.S_PacketBox;
import bone.server.server.serverpackets.S_ServerMessage;
import bone.server.server.serverpackets.S_SkillSound;
import bone.server.server.serverpackets.S_SystemMessage;
import bone.server.server.utils.FaceToFace;

// Referenced classes of package bone.server.server.clientpackets:
// ClientBasePacket

public class C_Rank extends ClientBasePacket {

	private static final String C_RANK = "[C] C_Rank";
	private static Logger _log = Logger.getLogger(C_Rank.class.getName());

	private L1ItemInstance weapon;
	private static final Random _random = new Random();

	public C_Rank(byte abyte0[], LineageClient clientthread) throws Exception {
		super(abyte0);

		int type = readC(); // ?
		int rank = readC();

		L1PcInstance pc = clientthread.getActiveChar();
		L1Clan clan = L1World.getInstance().getClan(pc.getClanname());
		String clanname = pc.getClanname();

		switch (type) {
		case 0: // ���Ϳ� ���� �ο����� ������ �־��� ���
			if (clan == null || pc == null){return;}
			//pc.sendPackets(new S_PacketBox(pc, S_PacketBox.PLEDGE_ONE));
			pc.sendPackets(new S_PacketBox(pc, S_PacketBox.PLEDGE_TWO)); // �ӽ�
			break;
		case 1://���
			if (clan == null || pc == null){return;}
			String name = readS();
			L1PcInstance targetPc = L1World.getInstance().getPlayer(name);
			if (rank < 1 && 3 < rank) {
				// ��ũ�� �����ϴ� ����� �̸��� ��ũ�� �Է��� �ּ���. [��ũ=�����, �Ϲ�, �߽�]
				pc.sendPackets(new S_ServerMessage(781));
				return;
			}

			if (pc.isCrown()) { // ����
				if (pc.getId() != clan.getLeaderId()) { // ������
					pc.sendPackets(new S_ServerMessage(785)); // ����� ���� ���ְ� �ƴմϴ�.
					return;
				}
			} else {
				pc.sendPackets(new S_ServerMessage(518)); // �� ����� ������ ���ָ��� �̿��� �� �ֽ��ϴ�.
				return;
			}

			if (targetPc != null) { // �¶�����
				if (pc.getClanid() == targetPc.getClanid()) { // ���� ũ��
					try {
						targetPc.setClanRank(rank);
						targetPc.save(); // DB�� ĳ���� ������ �����Ѵ�
						String rankString = "$772";
						if (rank == L1Clan.CLAN_RANK_PROBATION) {
							rankString = "$774";
						} else if (rank == L1Clan.CLAN_RANK_PUBLIC) {
							rankString = "$773";
						} else if (rank == L1Clan.CLAN_RANK_GUARDIAN) {
							rankString = "$772";
						}
						targetPc.sendPackets(new S_ServerMessage(784, rankString)); // ����� ��ũ��%s�� ����Ǿ����ϴ�.
					} catch (Exception e) {
						_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
					}
				} else {
					pc.sendPackets(new S_ServerMessage(414)); // ���� ���Ϳ��� �ƴմϴ�.
					return;
				}
			} else { // ���� ������
				L1PcInstance restorePc = CharacterTable.getInstance().restoreCharacter(name);
				if (restorePc != null
						&& restorePc.getClanid() == pc.getClanid()) { // ���� ũ��
					try {
						restorePc.setClanRank(rank);
						restorePc.save(); // DB�� ĳ���� ������ �����Ѵ�
					} catch (Exception e) {
						_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
					}
				} else {
					pc.sendPackets(new S_ServerMessage(109, name)); // %0��� �̸��� ����� �����ϴ�.
					return;
				}
			}
			break;

		case 2://���
			if (clan == null || pc == null){return;}
			if (pc.getClan().getAlliance() != null) {
				pc.sendPackets(new S_PacketBox(pc, S_PacketBox.ALLIANCE_LIST));
			} else {
				pc.sendPackets(new S_ServerMessage(1233));
			}
			break;
		case 3://����
			if (clan == null || pc == null){return;}
			L1PcInstance alliancePc = FaceToFace.faceToFace(pc);
			if (pc.getLevel() < 25 || !pc.isCrown()) {
				pc.sendPackets(new S_ServerMessage(1206));// 25�����̻� ���� ���ָ� ���ͽ�û�� �� �� �ֽ��ϴ�. ���� ���� ���ִ� ������ ���� �� �����ϴ�.
				return;
			}
			if (pc.getClan().getAlliance() != null) {
				pc.sendPackets(new S_ServerMessage(1202));// �̹� ���Ϳ� ���Ե� �����Դϴ�.
				return;
			}
			for (L1War war : L1World.getInstance().getWarList()) {
				if (war.CheckClanInWar(clanname)) {
					pc.sendPackets(new S_ServerMessage(1234)); // �����߿��� ���Ϳ� ������ �� �����ϴ�.
					return;
				}
			}
			if(alliancePc == null) {
				pc.sendPackets(new S_ServerMessage(93)); // �ű⿡�� �ƹ��� �����ϴ�.
				return;
			}
			if(!alliancePc.isCrown()) {
				pc.sendPackets(new S_ServerMessage(92, alliancePc.getName())); // \f1%0�� �������� ���������� �ƴմϴ�.
				return;
			}
			if(alliancePc.getClan() == null || alliancePc.getClanid() == 0){
				pc.sendPackets(new S_ServerMessage(90, alliancePc.getName())); // \f1%0�� ������ â���ϰ� ���� �ʴ� �����Դϴ�.
				return;
			}
			alliancePc.setTempID(pc.getId());
			alliancePc.sendPackets(new S_Message_YN(223, pc.getName()));

			break;
		case 4://Ż��
			if (clan == null || pc == null){return;}
			if(pc.getClan() == null || !pc.isCrown()
					|| pc.getClan().getAlliance() == null) {
				return;
			}
			for (L1War war : L1World.getInstance().getWarList()) {
				if (war.CheckClanInWar(clanname)) {
					pc.sendPackets(new S_ServerMessage(1203)); // �����߿��� ������ Ż���� �� �����ϴ�.
					return;
				}
			}
			if (clan.getAlliance() != null) {
				pc.sendPackets(new S_Message_YN(1210, "")); //������ ������ Ż���Ͻðڽ��ϱ�? (Y/N)
			} else {
				pc.sendPackets(new S_ServerMessage(1233)); // ������ �����ϴ�.
			}
			break;
		case 5: // �����ǿ�ħ
			if (pc.getWeapon() == null){
				pc.sendPackets(new S_ServerMessage(1973));
				return;
			}

			int gfx = 0;
			int gfx2 = 0;
			int hpPer = pc.getMaxHp() / 100;
			int addhp = 0;
			int time = pc.getLive() / 60;
			weapon = pc.getWeapon();
			int EnchantLevel = weapon.getEnchantLevel();

			if(pc.get_food() < 225){
				pc.sendPackets(new S_SystemMessage("����� �������� 100% �̸��̸� ����Ҽ� �����ϴ�."));
				return;
			}else if (time < 1) {
				pc.sendPackets(new S_ServerMessage(1974)); // ���� ������ ��ħ�� ����Ҽ� �����ϴ�.
					return;
				}else if (EnchantLevel < 0){
				pc.sendPackets(new S_ServerMessage(79)); // - ���̳ʽ� ����� ����Ҷ�
					return;
				}else if (time >=1 && time <= 29){ // 30���� �ȵǾ�����
					gfx = 8683;
					gfx2 = 829;
					addhp = hpPer * time; // �̷��� �ð��� �� �ۼ�Ʈ
				}else if (time >= 30) {
					if (EnchantLevel >= 0 && EnchantLevel <= 6) {
						addhp = hpPer * (_random.nextInt(21) + 20); // 20~40���� �����Ѱ��� ����
						gfx = 8684;
						gfx2 = 8907;
					}else if (EnchantLevel >= 7 && EnchantLevel <= 8) {
						addhp = hpPer * (_random.nextInt(21) + 30); // 30~50���� �����Ѱ��� ����
						gfx = 8685;
						gfx2 = 8909;
					}else if (EnchantLevel >= 9 && EnchantLevel <= 10) {
						addhp = hpPer * (_random.nextInt(11) + 50); // 50~60���� �����Ѱ��� ����
						gfx = 8773;
						gfx2 = 8910;
					}else if (EnchantLevel >= 11) {
						addhp = hpPer * 70;
						gfx = 8686;
						gfx2 = 8908;
					}
				}

			pc.setCurrentHp(pc.getCurrentHp() + addhp);
			pc.sendPackets(new S_SkillSound(pc.getId(), gfx));
			Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), gfx));
			pc.sendPackets(new S_SkillSound(pc.getId(), gfx2));
			Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), gfx2));
			pc.set_food(0);
			pc.setLive(0);
			pc.sendPackets(new S_PacketBox(S_PacketBox.FOOD, 0 ));
			LiveTimeController.getInstance().removeMember(pc);
			break;
		case 6: // �㼼�θ���
			if (pc.getWeapon() == null){
				pc.sendPackets(new S_ServerMessage(1973));
				return;
			}

			int gfx3 = 0;
			weapon = pc.getWeapon();
			int EnchantLevel2 = weapon.getEnchantLevel();

			if (EnchantLevel2 < 0){
				pc.sendPackets(new S_ServerMessage(79)); // - ���̳ʽ� ����� ����Ҷ�
				return;
			}else if (EnchantLevel2 >= 0 && EnchantLevel2 <= 6) {
				gfx3 = 8684;
			}else if (EnchantLevel2 >= 7 && EnchantLevel2 <= 8) {
				gfx3 = 8685;
			}else if (EnchantLevel2 >= 9 && EnchantLevel2 <= 10) {
				gfx3 = 8773;
			}else if (EnchantLevel2 >= 11) {
				gfx3 = 8686;
			}
			pc.sendPackets(new S_SkillSound(pc.getId(), gfx3));
			Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), gfx3));
			break;
		}
	}

	@Override
	public String getType() {
		return C_RANK;
	}
}
