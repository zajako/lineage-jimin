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
		case 0: // 혈맹에 속한 인원수의 변동이 있었을 경우
			if (clan == null || pc == null){return;}
			//pc.sendPackets(new S_PacketBox(pc, S_PacketBox.PLEDGE_ONE));
			pc.sendPackets(new S_PacketBox(pc, S_PacketBox.PLEDGE_TWO)); // 임시
			break;
		case 1://계급
			if (clan == null || pc == null){return;}
			String name = readS();
			L1PcInstance targetPc = L1World.getInstance().getPlayer(name);
			if (rank < 1 && 3 < rank) {
				// 랭크를 변경하는 사람의 이름과 랭크를 입력해 주세요. [랭크=가디안, 일반, 견습]
				pc.sendPackets(new S_ServerMessage(781));
				return;
			}

			if (pc.isCrown()) { // 군주
				if (pc.getId() != clan.getLeaderId()) { // 혈맹주
					pc.sendPackets(new S_ServerMessage(785)); // 당신은 이제 군주가 아닙니다.
					return;
				}
			} else {
				pc.sendPackets(new S_ServerMessage(518)); // 이 명령은 혈맹의 군주만을 이용할 수 있습니다.
				return;
			}

			if (targetPc != null) { // 온라인중
				if (pc.getClanid() == targetPc.getClanid()) { // 같은 크란
					try {
						targetPc.setClanRank(rank);
						targetPc.save(); // DB에 캐릭터 정보를 기입한다
						String rankString = "$772";
						if (rank == L1Clan.CLAN_RANK_PROBATION) {
							rankString = "$774";
						} else if (rank == L1Clan.CLAN_RANK_PUBLIC) {
							rankString = "$773";
						} else if (rank == L1Clan.CLAN_RANK_GUARDIAN) {
							rankString = "$772";
						}
						targetPc.sendPackets(new S_ServerMessage(784, rankString)); // 당신의 랭크가%s로 변경되었습니다.
					} catch (Exception e) {
						_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
					}
				} else {
					pc.sendPackets(new S_ServerMessage(414)); // 같은 혈맹원이 아닙니다.
					return;
				}
			} else { // 오프 라인중
				L1PcInstance restorePc = CharacterTable.getInstance().restoreCharacter(name);
				if (restorePc != null
						&& restorePc.getClanid() == pc.getClanid()) { // 같은 크란
					try {
						restorePc.setClanRank(rank);
						restorePc.save(); // DB에 캐릭터 정보를 기입한다
					} catch (Exception e) {
						_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
					}
				} else {
					pc.sendPackets(new S_ServerMessage(109, name)); // %0라는 이름의 사람은 없습니다.
					return;
				}
			}
			break;

		case 2://목록
			if (clan == null || pc == null){return;}
			if (pc.getClan().getAlliance() != null) {
				pc.sendPackets(new S_PacketBox(pc, S_PacketBox.ALLIANCE_LIST));
			} else {
				pc.sendPackets(new S_ServerMessage(1233));
			}
			break;
		case 3://가입
			if (clan == null || pc == null){return;}
			L1PcInstance alliancePc = FaceToFace.faceToFace(pc);
			if (pc.getLevel() < 25 || !pc.isCrown()) {
				pc.sendPackets(new S_ServerMessage(1206));// 25레벨이상 혈맹 군주만 동맹신청을 할 수 있습니다. 또한 연합 군주는 동맹을 맺을 수 없습니다.
				return;
			}
			if (pc.getClan().getAlliance() != null) {
				pc.sendPackets(new S_ServerMessage(1202));// 이미 동맹에 가입된 상태입니다.
				return;
			}
			for (L1War war : L1World.getInstance().getWarList()) {
				if (war.CheckClanInWar(clanname)) {
					pc.sendPackets(new S_ServerMessage(1234)); // 전쟁중에는 동맹에 가입할 수 없습니다.
					return;
				}
			}
			if(alliancePc == null) {
				pc.sendPackets(new S_ServerMessage(93)); // 거기에는 아무도 없습니다.
				return;
			}
			if(!alliancePc.isCrown()) {
				pc.sendPackets(new S_ServerMessage(92, alliancePc.getName())); // \f1%0은 프린스나 프린세스가 아닙니다.
				return;
			}
			if(alliancePc.getClan() == null || alliancePc.getClanid() == 0){
				pc.sendPackets(new S_ServerMessage(90, alliancePc.getName())); // \f1%0은 혈맹을 창설하고 있지 않는 상태입니다.
				return;
			}
			alliancePc.setTempID(pc.getId());
			alliancePc.sendPackets(new S_Message_YN(223, pc.getName()));

			break;
		case 4://탈퇴
			if (clan == null || pc == null){return;}
			if(pc.getClan() == null || !pc.isCrown()
					|| pc.getClan().getAlliance() == null) {
				return;
			}
			for (L1War war : L1World.getInstance().getWarList()) {
				if (war.CheckClanInWar(clanname)) {
					pc.sendPackets(new S_ServerMessage(1203)); // 전쟁중에는 동맹을 탈퇴할 수 없습니다.
					return;
				}
			}
			if (clan.getAlliance() != null) {
				pc.sendPackets(new S_Message_YN(1210, "")); //정말로 동맹을 탈퇴하시겠습니까? (Y/N)
			} else {
				pc.sendPackets(new S_ServerMessage(1233)); // 동맹이 없습니다.
			}
			break;
		case 5: // 생존의외침
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
				pc.sendPackets(new S_SystemMessage("배고픔 게이지가 100% 미만이면 사용할수 없습니다."));
				return;
			}else if (time < 1) {
				pc.sendPackets(new S_ServerMessage(1974)); // 아직 생존의 외침을 사용할수 없습니다.
					return;
				}else if (EnchantLevel < 0){
				pc.sendPackets(new S_ServerMessage(79)); // - 마이너스 무기로 사용할때
					return;
				}else if (time >=1 && time <= 29){ // 30분이 안되었을때
					gfx = 8683;
					gfx2 = 829;
					addhp = hpPer * time; // 이러면 시간당 피 퍼센트
				}else if (time >= 30) {
					if (EnchantLevel >= 0 && EnchantLevel <= 6) {
						addhp = hpPer * (_random.nextInt(21) + 20); // 20~40까지 랜덤한값이 나옴
						gfx = 8684;
						gfx2 = 8907;
					}else if (EnchantLevel >= 7 && EnchantLevel <= 8) {
						addhp = hpPer * (_random.nextInt(21) + 30); // 30~50까지 랜덤한값이 나옴
						gfx = 8685;
						gfx2 = 8909;
					}else if (EnchantLevel >= 9 && EnchantLevel <= 10) {
						addhp = hpPer * (_random.nextInt(11) + 50); // 50~60까지 랜덤한값이 나옴
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
		case 6: // 허세부리기
			if (pc.getWeapon() == null){
				pc.sendPackets(new S_ServerMessage(1973));
				return;
			}

			int gfx3 = 0;
			weapon = pc.getWeapon();
			int EnchantLevel2 = weapon.getEnchantLevel();

			if (EnchantLevel2 < 0){
				pc.sendPackets(new S_ServerMessage(79)); // - 마이너스 무기로 사용할때
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
