/* This program is free software; you can redistribute it and/or modify
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

import static bone.server.server.model.skill.L1SkillId.ADDITIONAL_FIRE;
import static bone.server.server.model.skill.L1SkillId.BERSERKERS;
import static bone.server.server.model.skill.L1SkillId.CLEAR_MIND;
import static bone.server.server.model.skill.L1SkillId.CONCENTRATION;
import static bone.server.server.model.skill.L1SkillId.COOKING_1_0_N;
import static bone.server.server.model.skill.L1SkillId.COOKING_1_0_S;
import static bone.server.server.model.skill.L1SkillId.COOKING_1_14_N;
import static bone.server.server.model.skill.L1SkillId.COOKING_1_14_S;
import static bone.server.server.model.skill.L1SkillId.COOKING_1_16_N;
import static bone.server.server.model.skill.L1SkillId.COOKING_1_16_S;
import static bone.server.server.model.skill.L1SkillId.COOKING_1_22_N;
import static bone.server.server.model.skill.L1SkillId.COOKING_1_22_S;
import static bone.server.server.model.skill.L1SkillId.COOKING_1_6_N;
import static bone.server.server.model.skill.L1SkillId.COOKING_1_6_S;
import static bone.server.server.model.skill.L1SkillId.COOKING_1_8_N;
import static bone.server.server.model.skill.L1SkillId.COOKING_1_8_S;
import static bone.server.server.model.skill.L1SkillId.STATUS_COMA_3;
import static bone.server.server.model.skill.L1SkillId.STATUS_COMA_5;
import static bone.server.server.model.skill.L1SkillId.DECAY_POTION;
import static bone.server.server.model.skill.L1SkillId.DECREASE_WEIGHT;
import static bone.server.server.model.skill.L1SkillId.DISEASE;
import static bone.server.server.model.skill.L1SkillId.DRAGON_SKIN;
import static bone.server.server.model.skill.L1SkillId.DRESS_EVASION;
import static bone.server.server.model.skill.L1SkillId.ELEMENTAL_FALL_DOWN;
import static bone.server.server.model.skill.L1SkillId.ELEMENTAL_FIRE;
import static bone.server.server.model.skill.L1SkillId.ELEMENTAL_PROTECTION;
import static bone.server.server.model.skill.L1SkillId.ERASE_MAGIC;
import static bone.server.server.model.skill.L1SkillId.EXP_POTION;
import static bone.server.server.model.skill.L1SkillId.FEAR;
import static bone.server.server.model.skill.L1SkillId.FEATHER_BUFF_A;
import static bone.server.server.model.skill.L1SkillId.FEATHER_BUFF_B;
import static bone.server.server.model.skill.L1SkillId.FEATHER_BUFF_C;
import static bone.server.server.model.skill.L1SkillId.FEATHER_BUFF_D;
import static bone.server.server.model.skill.L1SkillId.GUARD_BREAK;
import static bone.server.server.model.skill.L1SkillId.HORROR_OF_DEATH;
import static bone.server.server.model.skill.L1SkillId.INSIGHT;
import static bone.server.server.model.skill.L1SkillId.MORTAL_BODY;
import static bone.server.server.model.skill.L1SkillId.NATURES_TOUCH;
import static bone.server.server.model.skill.L1SkillId.PANIC;
import static bone.server.server.model.skill.L1SkillId.PATIENCE;
import static bone.server.server.model.skill.L1SkillId.POLLUTE_WATER;
import static bone.server.server.model.skill.L1SkillId.RESIST_ELEMENTAL;
import static bone.server.server.model.skill.L1SkillId.RESIST_MAGIC;
import static bone.server.server.model.skill.L1SkillId.SHAPE_CHANGE;
import static bone.server.server.model.skill.L1SkillId.SILENCE;
import static bone.server.server.model.skill.L1SkillId.SOUL_OF_FLAME;
import static bone.server.server.model.skill.L1SkillId.SPECIAL_COOKING;
import static bone.server.server.model.skill.L1SkillId.STATUS_BLUE_POTION;
import static bone.server.server.model.skill.L1SkillId.STATUS_BLUE_POTION2;
import static bone.server.server.model.skill.L1SkillId.STATUS_BLUE_POTION3;
import static bone.server.server.model.skill.L1SkillId.STATUS_BRAVE;
import static bone.server.server.model.skill.L1SkillId.STATUS_CASHSCROLL;
import static bone.server.server.model.skill.L1SkillId.STATUS_CASHSCROLL2;
import static bone.server.server.model.skill.L1SkillId.STATUS_CASHSCROLL3;
import static bone.server.server.model.skill.L1SkillId.STATUS_CHAT_PROHIBITED;
import static bone.server.server.model.skill.L1SkillId.STATUS_ELFBRAVE;
import static bone.server.server.model.skill.L1SkillId.STATUS_FRUIT;
import static bone.server.server.model.skill.L1SkillId.STATUS_HASTE;
import static bone.server.server.model.skill.L1SkillId.STATUS_TIKAL_BOSSDIE;
import static bone.server.server.model.skill.L1SkillId.STRIKER_GALE;
import static bone.server.server.model.skill.L1SkillId.VENOM_RESIST;
import static bone.server.server.model.skill.L1SkillId.WEAKNESS;
import static bone.server.server.model.skill.L1SkillId.WIND_SHACKLE;
import static bone.server.server.model.skill.L1SkillId.DRAGON_EMERALD_NO;
import static bone.server.server.model.skill.L1SkillId.DRAGON_EMERALD_YES;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import bone.server.Config;
import bone.server.L1DatabaseFactory;
import bone.server.SpecialEventHandler;
import bone.server.GameSystem.CrockSystem;
import bone.server.server.ActionCodes;
import bone.server.server.TimeController.LiveTimeController;
import bone.server.server.TimeController.WarTimeController;
import bone.server.server.datatables.CharacterTable;
import bone.server.server.datatables.GetBackRestartTable;
import bone.server.server.datatables.SkillsTable;
import bone.server.server.model.Broadcaster;
import bone.server.server.model.Getback;
import bone.server.server.model.L1CastleLocation;
import bone.server.server.model.L1Clan;
import bone.server.server.model.L1Cooking;
import bone.server.server.model.L1PolyMorph;
import bone.server.server.model.L1War;
import bone.server.server.model.L1World;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.model.Instance.L1SummonInstance;
import bone.server.server.model.map.L1Map;
import bone.server.server.model.map.L1WorldMap;
import bone.server.server.model.skill.L1SkillUse;
import bone.server.server.serverpackets.S_AddSkill;
import bone.server.server.serverpackets.S_Bookmarks;
import bone.server.server.serverpackets.S_CharacterConfig;
import bone.server.server.serverpackets.S_ElfIcon;
import bone.server.server.serverpackets.S_Emblem;
import bone.server.server.serverpackets.S_HPUpdate;
import bone.server.server.serverpackets.S_InvList;
import bone.server.server.serverpackets.S_MPUpdate;
import bone.server.server.serverpackets.S_MapID;
import bone.server.server.serverpackets.S_OwnCharPack;
import bone.server.server.serverpackets.S_OwnCharStatus;
import bone.server.server.serverpackets.S_OwnCharStatus2;
import bone.server.server.serverpackets.S_PacketBox;
import bone.server.server.serverpackets.S_ReturnedStat;
import bone.server.server.serverpackets.S_SPMR;
import bone.server.server.serverpackets.S_ServerMessage;
import bone.server.server.serverpackets.S_SkillBrave;
import bone.server.server.serverpackets.S_SkillHaste;
import bone.server.server.serverpackets.S_SkillIconGFX;
import bone.server.server.serverpackets.S_SummonPack;
import bone.server.server.serverpackets.S_SystemMessage;
import bone.server.server.serverpackets.S_UnityIcon;
import bone.server.server.serverpackets.S_Unknown1;
import bone.server.server.serverpackets.S_War;
import bone.server.server.serverpackets.S_Weather;
import bone.server.server.serverpackets.S_bonusstats;
import bone.server.server.templates.L1BookMark;
import bone.server.server.templates.L1GetBackRestart;
import bone.server.server.templates.L1Skills;
import bone.server.server.utils.SQLUtil;
import server.LineageClient;
import server.manager.eva;
import server.system.autoshop.AutoShop;
import server.system.autoshop.AutoShopManager;

//Referenced classes of package bone.server.server.clientpackets:
//ClientBasePacket

public class C_SelectCharacter extends ClientBasePacket {
	private static final String C_LOGIN_TO_SERVER = "[C] C_LoginToServer";
	private static Logger _log = Logger.getLogger(C_SelectCharacter.class.getName());

	private int[] omanLocX = { 32781, 32818, 32818, 32818 };
	private int[] omanLocY = { 32781, 32781, 32818, 32781 };

	Random ran = new Random();

	public C_SelectCharacter(byte abyte0[], LineageClient client) throws FileNotFoundException, Exception {
		super(abyte0);
		String charName = readS();

		if (client.getActiveChar() != null) { // restart ���°� �ƴ϶� �Ҹ���?
			client.kick();
			client.close();
			return;
		}

		AutoShopManager shopManager = AutoShopManager.getInstance();
		AutoShop shopPlayer = shopManager.getShopPlayer(charName);
		if (shopPlayer != null){
			shopPlayer.logout();
			shopManager.remove(shopPlayer);
			shopPlayer = null;
		}

		L1PcInstance pc = L1PcInstance.load(charName);

		String accountName = client.getAccountName();
		if (pc == null || !accountName.equals(pc.getAccountName())) {
			_log.info("�α��� ��û ��ȿ: char=" + charName + " account=" + accountName + " host=" + client.getHostname());
			client.kick();
			client.close();
			return;
		}

		_log.info("ĳ���� �α���: char=" + charName + " account=" + accountName	+ " host=" + client.getHostname());
		eva.LogServerAppend("����", pc, client.getIp(), 1);

		int currentHpAtLoad = pc.getCurrentHp();
		int currentMpAtLoad = pc.getCurrentMp();

		pc.clearSkillMastery();
		pc.setOnlineStatus(1);
		CharacterTable.updateOnlineStatus(pc);
		L1World.getInstance().storeObject(pc);

		pc.setNetConnection(client);

		client.setActiveChar(pc);


		//if(pc.isGm())ChatMonitorChannel.getInstance().join(pc);

		/*############### Point System ###############
   			int at = client.getAccount().getAccountTime() * 60000;
			if(at > 0) {
				long la = client.getAccount().getLastActive().getTime();
				long sum = la + at;
				pc.setLimitPointTime(sum/1000);
				pc.setPointUser(true);
			}
		############### Point System ###############*/

		pc.sendPackets(new S_Unknown1());

		items(pc);
		bookmarks(pc);
		skills(pc);

		// restartó�� getback_restart ���̺�� �����ǰ� ������(��) �̵���Ų��
		GetBackRestartTable gbrTable = GetBackRestartTable.getInstance();
		L1GetBackRestart[] gbrList = gbrTable.getGetBackRestartTableList();
		for (L1GetBackRestart gbr : gbrList) {
			if (pc.getMapId() == gbr.getArea()) {
				pc.setX(gbr.getLocX());
				pc.setY(gbr.getLocY());
				pc.setMap(gbr.getMapId());
				break;
			}
		}

		L1Map map = L1WorldMap.getInstance().getMap(pc.getMapId());
		// altsettings.properties�� GetBack�� true��� �Ÿ��� �̵���Ų��
		int tile = map.getTile(pc.getX(), pc.getY());
		if (Config.GET_BACK || !map.isInMap(pc.getX(), pc.getY())
				|| tile == 0 || tile == 4 || tile == 12) {
			int[] loc = Getback.GetBack_Location(pc, true);
			pc.setX(loc[0]);
			pc.setY(loc[1]);
			pc.setMap((short) loc[2]);
		}

		if (pc.getMapId() == 101){// ������ ž ��ȯ �����ϰ�� 1������ ���õǾ�����
			int rnd = ran.nextInt(omanLocX.length);
			pc.setX(omanLocX[rnd]);
			pc.setY(omanLocY[rnd]);
			pc.setMap((short) 101);
		}
		// �������� �⳻�� �־��� ���, ���� ������ �ƴ� ���� ��ȯ��Ų��.
		int castle_id = L1CastleLocation.getCastleIdByArea(pc);
		if (0 < castle_id) {
			if (WarTimeController.getInstance().isNowWar(castle_id)) {
				L1Clan clan = L1World.getInstance().getClan(pc.getClanname());
				if (clan != null) {
					if (clan.getCastleId() != castle_id) {
						// ���� ũ���� �ƴϴ�
						int[] loc = new int[3];
						loc = L1CastleLocation.getGetBackLoc(castle_id);
						pc.setX(loc[0]);
						pc.setY(loc[1]);
						pc.setMap((short) loc[2]);
					}
				} else {
					// ũ���� �Ҽ��� ���� ���� ��ȯ
					int[] loc = new int[3];
					loc = L1CastleLocation.getGetBackLoc(castle_id);
					pc.setX(loc[0]);
					pc.setY(loc[1]);
					pc.setMap((short) loc[2]);
				}
			}
		}

		L1World.getInstance().addVisibleObject(pc);

		pc.beginGameTimeCarrier();

		S_OwnCharStatus s_owncharstatus = new S_OwnCharStatus(pc);
		pc.sendPackets(s_owncharstatus);

		S_MapID s_mapid = new S_MapID(pc.getMapId(), pc.getMap().isUnderwater());
		pc.sendPackets(s_mapid);

		S_OwnCharPack s_owncharpack = new S_OwnCharPack(pc);
		pc.sendPackets(s_owncharpack);

		buff(client, pc);

		pc.sendPackets(new S_Weather(L1World.getInstance().getWeather()));

		// ������ �� �Ʒ� ��Ŷ ���̿� S_OPCODE_CASTLEMASTER ��Ŷ�� �´� (�� 9��)
		pc.sendCastleMaster();

		pc.sendPackets(new S_SPMR(pc));

		pc.sendVisualEffectAtLogin(); // ��, ���� ���� �ð� ȿ���� ǥ��
		pc.sendPackets(new S_ReturnedStat(pc, 4));

		if(pc.getLevel()>=49)
			hasadbuff(pc);

		pc.getLight().turnOnOffLight();

		//������� ���� �߰�
		L1PcInstance jonje = L1World.getInstance().getPlayer(pc.getName());
		if (jonje == null ) {
			pc.sendPackets(new S_SystemMessage("������� ��������! �������ϼ���"));
			client.kick();
			return;
		}

		if (pc.getClanid() > 0) {
			pc.sendPackets(new S_Emblem(pc.getClanid()));
		}

		if (pc.getCurrentHp() > 0) {
			pc.setDead(false);
			pc.setActionStatus(0);
		} else {
			pc.setDead(true);
			pc.setActionStatus(ActionCodes.ACTION_Die);
		}

		if (pc.getLevel() >= 51 && pc.getLevel() - 50 > pc.getAbility().getBonusAbility() && pc.getAbility().getAmount() < 150) {
			pc.sendPackets(new S_bonusstats(pc.getId(), 1));
		}

		pc.sendPackets(new S_OwnCharStatus2(pc));

		if (pc.getReturnStat() != 0){
			SpecialEventHandler.getInstance().ReturnStats(pc);
		}

		pc.sendPackets(new S_PacketBox(pc, S_PacketBox.KARMA));
		//pc.sendPackets(new S_PacketBox(S_PacketBox.LOGIN_UNKNOWN1)); // ������ �Ǵµ�?
		//pc.sendPackets(new S_PacketBox(S_PacketBox.LOGIN_UNKNOWN2));

		if (Config.CHARACTER_CONFIG_IN_SERVER_SIDE) {
			pc.sendPackets(new S_CharacterConfig(pc.getId()));
		}

		//�����ǿ�ħ
		pc.setLive(0);
		if (pc.get_food() == 225) {
			LiveTimeController.getInstance().addMember(pc);
		}

		serchSummon(pc);

		WarTimeController.getInstance().checkCastleWar(pc);

		if (pc.getClanid() != 0) { // ũ�� �Ҽ���
			L1Clan clan = L1World.getInstance().getClan(pc.getClanname());
			if (clan != null) {
				if (pc.getClanid() == clan.getClanId() && // ũ���� �ػ���, ����, ������ ũ���� â���Ǿ��� ���� ��å
						pc.getClanname().toLowerCase().equals(clan.getClanName().toLowerCase())) {
					for (L1PcInstance clanMember : clan.getOnlineClanMember()) {
						if (clanMember.getId() != pc.getId()) {
							// ����, ���Ϳ���%0%s�� ���ӿ� �����߽��ϴ�.
							clanMember.sendPackets(new S_ServerMessage(843, pc.getName()));
						}
					}

					// ������ ����Ʈ�� ���
					for (L1War war : L1World.getInstance().getWarList()) {
						boolean ret = war.CheckClanInWar(pc.getClanname());
						if (ret) { // ���￡ ������
							String enemy_clan_name = war.GetEnemyClanName(pc.getClanname());
							if (enemy_clan_name != null) {
								// ����� ������ ����_���Ͱ� �������Դϴ�.
								pc.sendPackets(new S_War(8, pc.getClanname(), enemy_clan_name));
							}
							break;
						}
					}
				} else {
					pc.setClanid(0);
					pc.setClanname("");
					pc.setClanRank(0);
					pc.save(); // DB�� ĳ���� ������ �����Ѵ�
				}
			}
		}

		if (pc.getPartnerId() != 0) { // ��ȥ��
			L1PcInstance partner = (L1PcInstance) L1World.getInstance()
			.findObject(pc.getPartnerId());
			if (partner != null && partner.getPartnerId() != 0) {
				if (pc.getPartnerId() == partner.getId() && partner.getPartnerId() == pc.getId()) {
					pc.sendPackets(new S_ServerMessage(548)); // ����� ��Ʈ�ʴ� ���� �������Դϴ�.
					partner.sendPackets(new S_ServerMessage(549)); // ����� ��Ʈ�ʴ� ��� �α����߽��ϴ�.
				}
			}
		}
		if (currentHpAtLoad > pc.getCurrentHp()) {
			pc.setCurrentHp(currentHpAtLoad);
		}
		if (currentMpAtLoad > pc.getCurrentMp()) {
			pc.setCurrentMp(currentMpAtLoad);
		}
		pc.startObjectAutoUpdate();
		client.CharReStart(false);
		pc.beginExpMonitor();
		pc.save(); // DB�� ĳ���� ������ �����Ѵ�

		pc.sendPackets(new S_OwnCharStatus(pc));

		if (pc.getHellTime() > 0) {
			pc.beginHell(false);
		}

		if (CrockSystem.getInstance().isContinuationTime())
			pc.sendPackets(new S_ServerMessage(1466));
		pc.setadFeature(1);
		pc.CheckStatus();
		CheckTwoLogin(pc);

	}
	private void CheckTwoLogin(L1PcInstance c) {
		for(L1PcInstance target : L1World.getInstance().getAllPlayersToArray()){
			int count = 0;
			if(c.getId() == target.getId()){
				count++;
				if(count > 1){
					c.getNetConnection().kick();
					c.getNetConnection().close();
					target.getNetConnection().kick();
					target.getNetConnection().close();
				}
			}
			if(c.getId() != target.getId()){
				if(c.getAccountName().equalsIgnoreCase(target.getAccountName())) {
					if(!AutoShopManager.getInstance().isExistAutoShop(target.getId())){
						c.getNetConnection().kick();
						c.getNetConnection().close();
						target.getNetConnection().kick();
						target.getNetConnection().close();
					}
					else{
						// �ñų��� ������ ���ð����Ƿ� ;;
						target.getNetConnection().kick();
						//target.getNetConnection().close();
					}
				}
			}

		}
	}

	private void hasadbuff(L1PcInstance pc) {
		if(pc.getAinHasad() >= 2000000){
			pc.setAinHasad(2000000);
			pc.sendPackets(new S_PacketBox(S_PacketBox.AINHASAD, pc.getAinHasad()));
			return;
		}

		int temp = (int)((System.currentTimeMillis() - pc.getLogOutTime().getTime())/900000);
		int sum = pc.getAinHasad()+(temp*10000);
		if(sum >= 2000000)
			pc.setAinHasad(2000000);
		else
			pc.setAinHasad(sum);

		pc.sendPackets(new S_PacketBox(S_PacketBox.AINHASAD, pc.getAinHasad()));
	}

	private void items(L1PcInstance pc) {
		// DB�κ��� ĳ���Ϳ� â���� �������� �о���δ�
		CharacterTable.getInstance().restoreInventory(pc);
		pc.sendPackets(new S_InvList(pc));
	}

	private void bookmarks(L1PcInstance pc) {

		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM character_teleport WHERE char_id=? ORDER BY name ASC");
			pstm.setInt(1, pc.getId());
			rs = pstm.executeQuery();

			L1BookMark bookmark = null;
			while (rs.next()) {
				bookmark = new L1BookMark();
				bookmark.setId(rs.getInt("id"));
				bookmark.setCharId(rs.getInt("char_id"));
				bookmark.setName(rs.getString("name"));
				bookmark.setLocX(rs.getInt("locx"));
				bookmark.setLocY(rs.getInt("locy"));
				bookmark.setMapId(rs.getShort("mapid"));
				bookmark.setRandomX(rs.getShort("randomX"));
				bookmark.setRandomY(rs.getShort("randomY"));
				S_Bookmarks s_bookmarks = new S_Bookmarks(bookmark.getName(),
						bookmark.getMapId(), bookmark.getId());
				pc.addBookMark(bookmark);
				pc.sendPackets(s_bookmarks);
			}
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	private void skills(L1PcInstance pc) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM character_skills WHERE char_obj_id=?");
			pstm.setInt(1, pc.getId());
			rs = pstm.executeQuery();

			int i = 0;
			int lv1 = 0;
			int lv2 = 0;
			int lv3 = 0;
			int lv4 = 0;
			int lv5 = 0;
			int lv6 = 0;
			int lv7 = 0;
			int lv8 = 0;
			int lv9 = 0;
			int lv10 = 0;
			int lv11 = 0;
			int lv12 = 0;
			int lv13 = 0;
			int lv14 = 0;
			int lv15 = 0;
			int lv16 = 0;
			int lv17 = 0;
			int lv18 = 0;
			int lv19 = 0;
			int lv20 = 0;
			int lv21 = 0;
			int lv22 = 0;
			int lv23 = 0;
			int lv24 = 0;
			int lv25 = 0;
			int lv26= 0;
			int lv27 = 0;
			int lv28 = 0;
			L1Skills l1skills = null;
			while (rs.next()) {
				int skillId = rs.getInt("skill_id");
				l1skills = SkillsTable.getInstance().getTemplate(skillId);
				if (l1skills.getSkillLevel() == 1) 	{lv1 |= l1skills.getId();}
				if (l1skills.getSkillLevel() == 2) 	{lv2 |= l1skills.getId();}
				if (l1skills.getSkillLevel() == 3) 	{lv3 |= l1skills.getId();}
				if (l1skills.getSkillLevel() == 4) 	{lv4 |= l1skills.getId();}
				if (l1skills.getSkillLevel() == 5) 	{lv5 |= l1skills.getId();}
				if (l1skills.getSkillLevel() == 6) 	{lv6 |= l1skills.getId();}
				if (l1skills.getSkillLevel() == 7) 	{lv7 |= l1skills.getId();}
				if (l1skills.getSkillLevel() == 8) 	{lv8 |= l1skills.getId();}
				if (l1skills.getSkillLevel() == 9) 	{lv9 |= l1skills.getId();}
				if (l1skills.getSkillLevel() == 10) {lv10 |= l1skills.getId();}
				if (l1skills.getSkillLevel() == 11) {lv11 |= l1skills.getId();}
				if (l1skills.getSkillLevel() == 12) {lv12 |= l1skills.getId();}
				if (l1skills.getSkillLevel() == 13) {lv13 |= l1skills.getId();}
				if (l1skills.getSkillLevel() == 14) {lv14 |= l1skills.getId();}
				if (l1skills.getSkillLevel() == 15) {lv15 |= l1skills.getId();}
				if (l1skills.getSkillLevel() == 16) {lv16 |= l1skills.getId();}
				if (l1skills.getSkillLevel() == 17) {lv17 |= l1skills.getId();}
				if (l1skills.getSkillLevel() == 18) {lv18 |= l1skills.getId();}
				if (l1skills.getSkillLevel() == 19) {lv19 |= l1skills.getId();}
				if (l1skills.getSkillLevel() == 20) {lv20 |= l1skills.getId();}
				if (l1skills.getSkillLevel() == 21) {lv21 |= l1skills.getId();}
				if (l1skills.getSkillLevel() == 22) {lv22 |= l1skills.getId();}
				if (l1skills.getSkillLevel() == 23) {lv23 |= l1skills.getId();}
				if (l1skills.getSkillLevel() == 24) {lv24 |= l1skills.getId();}
				if (l1skills.getSkillLevel() == 25) {lv25 |= l1skills.getId();}
				if (l1skills.getSkillLevel() == 26) {lv26 |= l1skills.getId();}
				if (l1skills.getSkillLevel() == 27) {lv27 |= l1skills.getId();}
				if (l1skills.getSkillLevel() == 28) {lv28 |= l1skills.getId();}

				i = lv1 + lv2 + lv3 + lv4 + lv5 + lv6 + lv7 + lv8 + lv9 + lv10
				+ lv11 + lv12 + lv13 + lv14 + lv15 + lv16 + lv17 + lv18
				+ lv19 + lv20 + lv21 + lv22 + lv23 + lv24 + lv25 + lv26 + lv27 + lv28;

				pc.setSkillMastery(skillId);
			}
			if (i > 0) {
				pc.sendPackets(new S_AddSkill(lv1, lv2, lv3, lv4, lv5, lv6,
						lv7, lv8, lv9, lv10, lv11, lv12, lv13, lv14, lv15,
						lv16, lv17, lv18, lv19, lv20, lv21, lv22, lv23, lv24, lv25, lv26, lv27, lv28));
			}
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	private void serchSummon(L1PcInstance pc) {
		for (L1SummonInstance summon : L1World.getInstance().getAllSummons()) {
			if (summon.getMaster().getId() == pc.getId()) {
				summon.setMaster(pc);
				pc.addPet(summon);
				for (L1PcInstance visiblePc : L1World.getInstance().getVisiblePlayer(summon)) {
					visiblePc.sendPackets(new S_SummonPack(summon, visiblePc));
				}
			}
		}
	}

	private void buff(LineageClient clientthread, L1PcInstance pc) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {

			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM character_buff WHERE char_obj_id=?");
			pstm.setInt(1, pc.getId());
			rs = pstm.executeQuery();
			int icon[] = {	0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
							0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
							0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
							0, 0, 0, 0, 0};

			while (rs.next()) {
				int skillid = rs.getInt("skill_id");
				int remaining_time = rs.getInt("remaining_time");

				if (skillid >= COOKING_1_0_N && skillid <= COOKING_1_6_N
						|| skillid >= COOKING_1_8_N && skillid <= COOKING_1_14_N
						|| skillid >= COOKING_1_16_N && skillid <= COOKING_1_22_N
						|| skillid >= COOKING_1_0_S && skillid <= COOKING_1_6_S
						|| skillid >= COOKING_1_8_S && skillid <= COOKING_1_14_S
						|| skillid >= COOKING_1_16_S && skillid <= COOKING_1_22_S) { // �丮(����Ʈ�� �����ϴ�)
					L1Cooking.eatCooking(pc, skillid, remaining_time);
					continue;
				}

				int time = (int)((System.currentTimeMillis() - pc.getLogOutTime().getTime()) / 1000);

				switch(skillid) {
				case DECREASE_WEIGHT:
					icon[0] = remaining_time/16;
					break;
				case WEAKNESS:// ��ũ�Ͻ� //
					icon[4] = remaining_time/4;
					pc.addDmgup(-5);
					pc.addHitup(-1);
					break;
				case BERSERKERS:// ����Ŀ�� //
					icon[7] = remaining_time/4;
					pc.getAC().addAc(10);
					pc.addDmgup(5);
					pc.addHitup(2);
					break;
				case DISEASE:// ������ //
					icon[5] = remaining_time/4;
					pc.addDmgup(-6);
					pc.getAC().addAc(12);
					break;
				case SILENCE:
					icon[2] = remaining_time/4;
					break;
				case SHAPE_CHANGE:
					int poly_id = rs.getInt("poly_id");
					L1PolyMorph.doPoly(pc, poly_id, remaining_time, L1PolyMorph.MORPH_BY_LOGIN);
					continue;
				case DECAY_POTION:
					icon[1] = remaining_time/4;
					break;
				case VENOM_RESIST:// ���� ������Ʈ //
					icon[3] = remaining_time/4;
					break;
				case DRESS_EVASION:// �巹�� �̺����� //
					icon[6] = remaining_time/4;
					break;
				case RESIST_MAGIC:// ������Ʈ ����
					pc.getResistance().addMr(10);
					pc.sendPackets(new S_ElfIcon(remaining_time/16, 0, 0, 0));
					break;
				case ELEMENTAL_FALL_DOWN:
					icon[12] = remaining_time/4;
					int playerAttr = pc.getElfAttr();
					int i = -50;
					switch (playerAttr) {
					case 0: pc.sendPackets(new S_ServerMessage(79)); break;
					case 1: pc.getResistance().addEarth(i); pc.setAddAttrKind(1); break;
					case 2: pc.getResistance().addFire(i); pc.setAddAttrKind(2); break;
					case 4: pc.getResistance().addWater(i); pc.setAddAttrKind(4); break;
					case 8: pc.getResistance().addWind(i); pc.setAddAttrKind(8); break;
					default: break;
					}
					break;
				case CLEAR_MIND:// Ŭ���� ���ε�
					pc.getAbility().addAddedWis((byte) 3);
					pc.resetBaseMr();
					pc.sendPackets(new S_ElfIcon(0, remaining_time/16, 0, 0));
					break;
				case RESIST_ELEMENTAL:// ������Ʈ ������Ż
					pc.getResistance().addAllNaturalResistance(10);
					pc.sendPackets(new S_ElfIcon(0, 0, remaining_time/16, 0));
					break;
				case ELEMENTAL_PROTECTION:// �����ؼ� ���� ������Ż
					int attr = pc.getElfAttr();
					if (attr == 1) {
						pc.getResistance().addEarth(50);
					} else if (attr == 2) {
						pc.getResistance().addFire(50);
					} else if (attr == 4) {
						pc.getResistance().addWater(50);
					} else if (attr == 8) {
						pc.getResistance().addWind(50);
					}
					pc.sendPackets(new S_ElfIcon(0, 0, 0, remaining_time/16));
					break;
				case ERASE_MAGIC:
					icon[10] = remaining_time/4;
					break;
				case NATURES_TOUCH:// �����Ľ� ��ġ //
					icon[8] = remaining_time/4;
					break;
				case WIND_SHACKLE:
					icon[9] = remaining_time/4;
					break;
				case ELEMENTAL_FIRE:
					icon[13] = remaining_time/4;
					break;
				case POLLUTE_WATER:// ����Ʈ ���� //
					icon[16] = remaining_time/4;
					break;
				case STRIKER_GALE:// ��Ʈ����Ŀ ���� //
					icon[14] = remaining_time/4;
					break;
				case SOUL_OF_FLAME:// �ҿ� ���� ������ //
					icon[15] = remaining_time/4;
					break;
				case ADDITIONAL_FIRE:
					icon[11] = remaining_time/16;
					break;
				case DRAGON_SKIN:// �巡�� ��Ų //
					icon[29] = remaining_time/16;
					break;
				case GUARD_BREAK:// ���� �극��ũ //
					icon[28] = remaining_time/4;
					pc.getAC().addAc(15);
					break;
				case FEAR:// �Ǿ� //
					icon[26] = remaining_time/4;
					break;
				case MORTAL_BODY:// ��Ż�ٵ� //
					icon[24] = remaining_time/4;
					break;
				case HORROR_OF_DEATH:// ȣ�� ���� ���� //
					icon[25] = remaining_time/4;
					pc.getAbility().addAddedStr((byte) -10);
					pc.getAbility().addAddedInt((byte) -10);
					break;
				case CONCENTRATION:
					icon[21] = remaining_time/16;
					break;
				case PATIENCE:// ���̼ǽ� //
					icon[27] = remaining_time/4;
					break;
				case INSIGHT:
					icon[22] = remaining_time/16;
					pc.getAbility().addAddedStr((byte) 1);
					pc.getAbility().addAddedDex((byte) 1);
					pc.getAbility().addAddedCon((byte) 1);
					pc.getAbility().addAddedInt((byte) 1);
					pc.getAbility().addAddedWis((byte) 1);
					pc.getAbility().addAddedCha((byte) 1);
					pc.resetBaseMr();
					break;
				case PANIC:
					icon[23] = remaining_time/16;
					pc.getAbility().addAddedStr((byte) -1);
					pc.getAbility().addAddedDex((byte) -1);
					pc.getAbility().addAddedCon((byte) -1);
					pc.getAbility().addAddedInt((byte) -1);
					pc.getAbility().addAddedWis((byte) -1);
					pc.getAbility().addAddedCha((byte) -1);
					pc.resetBaseMr();
					break;
				case STATUS_BRAVE:
					pc.sendPackets(new S_SkillBrave(pc.getId(), 1, remaining_time));
					Broadcaster.broadcastPacket(pc, new S_SkillBrave(pc.getId(), 1, 0));
					pc.getMoveState().setBraveSpeed(1);
					break;
				case STATUS_HASTE:
					pc.sendPackets(new S_SkillHaste(pc.getId(), 1, remaining_time));
					Broadcaster.broadcastPacket(pc, new S_SkillHaste(pc.getId(), 1, 0));
					pc.getMoveState().setMoveSpeed(1);
					break;
				case STATUS_BLUE_POTION:
				case STATUS_BLUE_POTION2:
				case STATUS_BLUE_POTION3:
					pc.sendPackets(new S_SkillIconGFX(34, remaining_time));
					break;
				case STATUS_ELFBRAVE:
					pc.sendPackets(new S_SkillBrave(pc.getId(), 3, remaining_time));
					Broadcaster.broadcastPacket(pc, new S_SkillBrave(pc.getId(), 3, 0));
					pc.getMoveState().setBraveSpeed(1);
					break;
				case STATUS_CHAT_PROHIBITED:
					pc.sendPackets(new S_SkillIconGFX(36, remaining_time));
					break;
				case STATUS_TIKAL_BOSSDIE:
					icon[20] = (remaining_time+8)/16;
					new L1SkillUse().handleCommands(clientthread.getActiveChar(),
							skillid, pc.getId(), pc.getX(), pc.getY(), null, remaining_time, L1SkillUse.TYPE_LOGIN);
					break;
				case STATUS_COMA_3:// �ڸ� 3
					icon[31] = (remaining_time + 16) / 32;
					icon[32] = 40;
					new L1SkillUse().handleCommands(clientthread.getActiveChar(),
							skillid, pc.getId(), pc.getX(), pc.getY(), null, remaining_time, L1SkillUse.TYPE_LOGIN);
					break;
				case STATUS_COMA_5:// �ڸ� 5
					icon[31] = (remaining_time + 16) / 32;
					icon[32] = 41;
					new L1SkillUse().handleCommands(clientthread.getActiveChar(),
							skillid, pc.getId(), pc.getX(), pc.getY(), null, remaining_time, L1SkillUse.TYPE_LOGIN);
					break;
				case SPECIAL_COOKING:
					if(pc.getSkillEffectTimerSet().hasSkillEffect(SPECIAL_COOKING)){
						if(pc.getSkillEffectTimerSet().getSkillEffectTimeSec(SPECIAL_COOKING) < remaining_time){
							pc.getSkillEffectTimerSet().setSkillEffect(SPECIAL_COOKING, remaining_time * 1000);
						}
					}
					continue;
				case STATUS_CASHSCROLL:// ü�������ֹ��� //
					icon[18] = remaining_time/16;
					pc.addHpr(4);
					pc.addMaxHp(50);
					pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
					if (pc.isInParty()) {
						pc.getParty().updateMiniHP(pc);
					}
					pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
					break;
				case STATUS_CASHSCROLL2:// ���������ֹ��� //
					icon[18] = remaining_time/16;
					icon[19] = 1;
					pc.addMpr(4);
					pc.addMaxMp(40);
					pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
					break;
				case STATUS_CASHSCROLL3:
					icon[18] = remaining_time/16;
					icon[19] = 2;
					pc.addDmgup(3);
					pc.addHitup(3);
					pc.getAbility().addSp(3);
					pc.sendPackets(new S_SPMR(pc));
					break;
				case STATUS_FRUIT:// ���׵�� //
					icon[30] = remaining_time/4;
					break;
				case EXP_POTION:
					icon[17] = remaining_time/16;
					break;
				case FEATHER_BUFF_A:// ��� ���� ���� ���� // �ſ�����
					icon[33] = remaining_time/16;
					icon[34] = 70;
					pc.addDmgup(2);
					pc.addHitup(2);
					pc.getAbility().addSp(2);
					pc.sendPackets(new S_SPMR(pc));
					pc.addHpr(3);
					pc.addMaxHp(50);
					pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
					if (pc.isInParty()) {
						pc.getParty().updateMiniHP(pc);
					}
					pc.addMpr(3);
					pc.addMaxMp(30);
					pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
					break;
				case FEATHER_BUFF_B:// ��� ���� ���� ���� // ����
					icon[33] = remaining_time/16;
					icon[34] = 71;
					pc.addHitup(2);
					pc.getAbility().addSp(1);
					pc.sendPackets(new S_SPMR(pc));
					pc.addMaxHp(50);
					pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
					if (pc.isInParty()) {
						pc.getParty().updateMiniHP(pc);
					}
					pc.addMaxMp(30);
					pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
					break;
				case FEATHER_BUFF_C:// ��� ���� ���� ���� // ����
					icon[33] = remaining_time/16;
					icon[34] = 72;
					pc.addMaxHp(50);
					pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
					if (pc.isInParty()) {
						pc.getParty().updateMiniHP(pc);
					}
					pc.addMaxMp(30);
					pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
					pc.getAC().addAc(-2);
					break;
				case FEATHER_BUFF_D:// ��� ���� ���� ���� // ����
					icon[33] = remaining_time/16;
					icon[34] = 73;
					pc.getAC().addAc(-1);
					break;
				case DRAGON_EMERALD_NO:
					int emerald_no = remaining_time - time;
					remaining_time = emerald_no;
					pc.sendPackets(new S_PacketBox(S_PacketBox.EMERALD_EVA, 0x01, remaining_time));
					break;
				case DRAGON_EMERALD_YES:
					int emerald_yes = remaining_time - time;
					remaining_time = emerald_yes;
					pc.sendPackets(new S_PacketBox(S_PacketBox.EMERALD_EVA, 0x02, remaining_time));
					break;
				default:
					new L1SkillUse().handleCommands(clientthread.getActiveChar(),
							skillid, pc.getId(), pc.getX(), pc.getY(), null, remaining_time, L1SkillUse.TYPE_LOGIN);
				continue;
				}
				pc.getSkillEffectTimerSet().setSkillEffect(skillid, remaining_time * 1000);
			}

			pc.sendPackets(new S_UnityIcon(
					icon[0], icon[1], icon[2], icon[3], icon[4], icon[5], icon[6], icon[7], icon[8], icon[9], icon[10],
					icon[11], icon[12], icon[13], icon[14], icon[15], icon[16], icon[17], icon[18], icon[19], icon[20],
					icon[21], icon[22], icon[23], icon[24], icon[25], icon[26], icon[27], icon[28], icon[29], icon[30],
					icon[31], icon[32], icon[33], icon[34]));
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	@Override
	public String getType() {
		return C_LOGIN_TO_SERVER;
	}
}