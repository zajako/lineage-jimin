/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
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

import static bone.server.server.model.skill.L1SkillId.ERASE_MAGIC;
import static bone.server.server.model.skill.L1SkillId.EXP_POTION;
import static bone.server.server.model.skill.L1SkillId.POLLUTE_WATER;
import static bone.server.server.model.skill.L1SkillId.STATUS_CASHSCROLL;
import static bone.server.server.model.skill.L1SkillId.STATUS_CASHSCROLL2;
import static bone.server.server.model.skill.L1SkillId.STATUS_CASHSCROLL3;
import static bone.server.server.model.skill.L1SkillId.STATUS_HOLY_MITHRIL_POWDER;
import static bone.server.server.model.skill.L1SkillId.STATUS_HOLY_WATER;
import static bone.server.server.model.skill.L1SkillId.STATUS_HOLY_WATER_OF_EVA;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.logging.Logger;

import server.LineageClient;

import bone.server.Config;
import bone.server.L1DatabaseFactory;
import bone.server.GameSystem.CrockSystem;
import bone.server.server.ActionCodes;
import bone.server.server.datatables.ItemTable;
import bone.server.server.datatables.MapFixKeyTable;
import bone.server.server.datatables.NpcTable;
import bone.server.server.datatables.PetTable;
import bone.server.server.model.Broadcaster;
import bone.server.server.model.CharPosUtil;
import bone.server.server.model.L1CastleLocation;
import bone.server.server.model.L1EffectSpawn;
import bone.server.server.model.L1Inventory;
import bone.server.server.model.L1ItemDelay;
import bone.server.server.model.L1Object;
import bone.server.server.model.L1PcInventory;
import bone.server.server.model.L1Quest;
import bone.server.server.model.L1Teleport;
import bone.server.server.model.L1World;
import bone.server.server.model.Instance.L1DoorInstance;
import bone.server.server.model.Instance.L1EffectInstance;
import bone.server.server.model.Instance.L1GuardianInstance;
import bone.server.server.model.Instance.L1ItemInstance;
import bone.server.server.model.Instance.L1MonsterInstance;
import bone.server.server.model.Instance.L1NpcInstance;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.model.Instance.L1PetInstance;
import bone.server.server.model.item.L1ItemId;
import bone.server.server.model.poison.L1DamagePoison;
import bone.server.server.model.skill.L1SkillId;
import bone.server.server.model.skill.L1SkillUse;
import bone.server.server.serverpackets.S_AttackPacket;
import bone.server.server.serverpackets.S_ChangeName;
import bone.server.server.serverpackets.S_ChangeShape;
import bone.server.server.serverpackets.S_EffectLocation;
import bone.server.server.serverpackets.S_HPUpdate;
import bone.server.server.serverpackets.S_ItemName;
import bone.server.server.serverpackets.S_Liquor;
import bone.server.server.serverpackets.S_MPUpdate;
import bone.server.server.serverpackets.S_NPCTalkReturn;
import bone.server.server.serverpackets.S_PacketBox;
import bone.server.server.serverpackets.S_SPMR;
import bone.server.server.serverpackets.S_ServerMessage;
import bone.server.server.serverpackets.S_SkillSound;
import bone.server.server.serverpackets.S_Sound;
import bone.server.server.serverpackets.S_SystemMessage;
import bone.server.server.serverpackets.S_UseMap;
import bone.server.server.templates.L1EtcItem;
import bone.server.server.templates.L1Item;
import bone.server.server.templates.L1Npc;
import bone.server.server.templates.L1Pet;
import bone.server.server.utils.L1SpawnUtil;


//Referenced classes of package bone.server.server.clientpackets:
//ClientBasePacket

public class C_ItemUSe extends ClientBasePacket {

	private static final String C_ITEM_USE = "[C] C_ItemUSe";
	private static Logger _log = Logger.getLogger(C_ItemUSe.class.getName());

	private static Random _random = new Random();

	Calendar currentDate = Calendar.getInstance();
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd h:mm:ss a");
	String time = dateFormat.format(currentDate.getTime());

	public C_ItemUSe(byte abyte0[], LineageClient client) throws Exception {
		super(abyte0);
		int itemObjid = readD();

		L1PcInstance pc = client.getActiveChar();
		if (pc.isGhost()) {
			return;
		}
		L1ItemInstance useItem = pc.getInventory().getItem(itemObjid);

		if (useItem.getItem().getUseType() == -1) { // none:����� �� ���� ������
			pc.sendPackets(new S_ServerMessage(74, useItem.getLogName())); // \f1%0�� ����� �� �����ϴ�.
			return;
		}
		if (pc.isTeleport()) { // �ڷ���Ʈ ó����
			return;
		}
		//������� ���� �߰�
		L1PcInstance jonje = L1World.getInstance().getPlayer(pc.getName());
		if (jonje == null && pc.getAccessLevel() != 200) {
			pc.sendPackets(new S_SystemMessage("������� ��������! �������ϼ���"));
			client.kick();
			return;
		}

		if (useItem == null && pc.isDead() == true) {
			return;
		}
		if (!pc.getMap().isUsableItem()) {
			pc.sendPackets(new S_ServerMessage(563)); // \f1 ���⿡���� ����� �� �����ϴ�.
			return;
		}
		int itemId;
		try {
			itemId = useItem.getItem().getItemId();
		} catch (Exception e) {
			return;
		}
		if(useItem.isWorking()){
			if (pc.getCurrentHp() > 0) {
				useItem.clickItem(pc, this);
			}
			return;
		}
		int l = 0;
		int spellsc_objid = 0;
		int spellsc_x = 0;
		int spellsc_y = 0;

		int use_type = useItem.getItem().getUseType();
		if (itemId == 41029 // ��ȯ���� ����
				|| itemId == 40317 || itemId == 41036
				|| itemId == L1ItemId.LOWER_OSIRIS_PRESENT_PIECE_DOWN
				|| itemId == L1ItemId.HIGHER_OSIRIS_PRESENT_PIECE_DOWN
				|| itemId == L1ItemId.LOWER_TIKAL_PRESENT_PIECE_DOWN
				|| itemId == L1ItemId.HIGHER_TIKAL_PRESENT_PIECE_DOWN
				|| itemId == L1ItemId.TIMECRACK_CORE
				|| itemId == 40964 || itemId == 41030
				|| itemId == 40925 || itemId == 40926 || itemId == 40927 // ��ȭ���ź����� �Ϻ�
				|| itemId == 40928 || itemId == 40929) {
			l = readD();
		} else if (use_type == 30 || itemId == 40870 || itemId == 40879) { // spell_buff
			spellsc_objid = readD();
		} else if (use_type == 5 || use_type == 17) { // spell_long  spell_short
			spellsc_objid = readD();
			spellsc_x = readH();
			spellsc_y = readH();
		} else {
			l = readC();
		}

		if (pc.getCurrentHp() > 0) {
			int delay_id = 0;
			if (useItem.getItem().getType2() == 0) { // �������� ���� ������
				delay_id = ((L1EtcItem) useItem.getItem()).get_delayid();
			}
			if (delay_id != 0) { // ���� ���� �־�
				if (pc.hasItemDelay(delay_id) == true) {
					return;
				}
			}

			L1ItemInstance l1iteminstance1 = pc.getInventory().getItem(l);
			_log.finest("request item use (obj) = " + itemObjid + " action = " + l);
			if (useItem.getItem().getType2() == 0) { // �������� ���� ������
				int item_minlvl = ((L1EtcItem) useItem.getItem()).getMinLevel();
				int item_maxlvl = ((L1EtcItem) useItem.getItem()).getMaxLevel();

				if (item_minlvl != 0 && item_minlvl > pc.getLevel()
						&& !pc.isGm()) {
					pc.sendPackets(new S_ServerMessage(318, String
							.valueOf(item_minlvl)));
					// �� ��������%0���� �̻��� ���� ������ ����� �� �����ϴ�.
					return;
				} else if (item_maxlvl != 0 && item_maxlvl < pc.getLevel()
						&& !pc.isGm()) {
					pc.sendPackets(new S_ServerMessage(673, String
							.valueOf(item_maxlvl)));
					// �� ��������%d���� �̻� ����� �� �ֽ��ϴ�.
					return;
				}
				if ((itemId == 40576 && !pc.isElf())
						|| (itemId == 40577 && !pc.isWizard()) // ��ȥ�� ������ ����(��)
						|| (itemId == 40578 && !pc.isKnight())) { // ��ȥ�� ������
																	// ����(����)
					pc.sendPackets(new S_ServerMessage(264)); // \f1����� Ŭ���������� ��
																// �������� ����� ��
																// �����ϴ�.
					return;
				}

				if (itemId == 40003) {
					for (L1ItemInstance lightItem : pc.getInventory()
							.getItems()) {
						if (lightItem.getItem().getItemId() == 40002) {
							lightItem.setRemainingTime(useItem.getItem()
									.getLightFuel());
							pc.sendPackets(new S_ItemName(lightItem));
							pc.sendPackets(new S_ServerMessage(230));
							break;
						}
					}
					pc.getInventory().removeItem(useItem, 1);
				} else if (itemId == L1ItemId.INCRESE_HP_SCROLL
						|| itemId == L1ItemId.INCRESE_MP_SCROLL
						|| itemId == L1ItemId.INCRESE_ATTACK_SCROLL
						|| itemId == L1ItemId.CHUNSANG_HP_SCROLL
						|| itemId == L1ItemId.CHUNSANG_MP_SCROLL
						|| itemId == L1ItemId.CHUNSANG_ATTACK_SCROLL) {
					useCashScroll(pc, itemId);
					pc.getInventory().removeItem(useItem, 1);
				} else if (itemId == 40858) { // liquor(��)
					pc.setDrink(true);
					pc.sendPackets(new S_Liquor(pc.getId()));
					pc.getInventory().removeItem(useItem, 1);
				} else if (itemId == L1ItemId.EXP_POTION
						|| itemId == L1ItemId.EXP_POTION2) { // õ���ǹ���
					UseExpPotion(pc, itemId);
					pc.getInventory().removeItem(useItem, 1);
				} else if (itemId == L1ItemId.POTION_OF_CURE_POISON
						|| itemId == 40507) { // �ü� �Ϻ�, ��Ʈ�� ����
					if (pc.getSkillEffectTimerSet().hasSkillEffect(71) == true) { // ����������
																					// ����
						pc.sendPackets(new S_ServerMessage(698)); // ���¿� ���� �ƹ��͵�
																	// ���� ����
																	// �����ϴ�.
					} else {
						pc.cancelAbsoluteBarrier(); // �ƺ�Ҹ�Ʈ�ٸ����� ����
						pc.sendPackets(new S_SkillSound(pc.getId(), 192));
						Broadcaster.broadcastPacket(pc,
								new S_SkillSound(pc.getId(), 192));
						if (itemId == L1ItemId.POTION_OF_CURE_POISON) {
							pc.getInventory().removeItem(useItem, 1);
						} else if (itemId == 40507) {
							pc.getInventory().removeItem(useItem, 1);
						}
						pc.curePoison();
					}
				} else if (itemId == 40066 || itemId == 41413) { // ��, ����
					pc.sendPackets(new S_ServerMessage(338, "$1084")); // �����%0��
																		// ȸ���� ��
																		// ���Դϴ�.
					pc.setCurrentMp(pc.getCurrentMp()
							+ (7 + _random.nextInt(6))); // 7~12
					pc.getInventory().removeItem(useItem, 1);
				} else if (itemId == 40067 || itemId == 41414) { // ����, ����
					pc.sendPackets(new S_ServerMessage(338, "$1084")); // �����%0��
																		// ȸ���� ��
																		// ���Դϴ�.
					pc.setCurrentMp(pc.getCurrentMp()
							+ (15 + _random.nextInt(16))); // 15~30
					pc.getInventory().removeItem(useItem, 1);
				} else if (itemId == 410002) { // ������ ������
					pc.sendPackets(new S_ServerMessage(338, "$1084")); // �����%0��
																		// ȸ���� ��
																		// ���Դϴ�.
					pc.setCurrentMp(pc.getCurrentMp() + 44);
					pc.getInventory().removeItem(useItem, 1);
				} else if (itemId == 40735) { // ����� ����
					pc.sendPackets(new S_ServerMessage(338, "$1084")); // �����%0��
																		// ȸ���� ��
																		// ���Դϴ�.
					pc.setCurrentMp(pc.getCurrentMp() + 60);
					pc.getInventory().removeItem(useItem, 1);
				} else if (itemId == 40042) { // ���Ǹ� �Ϻ�
					pc.sendPackets(new S_ServerMessage(338, "$1084")); // �����%0��
																		// ȸ���� ��
																		// ���Դϴ�.
					pc.setCurrentMp(pc.getCurrentMp() + 50);
					pc.getInventory().removeItem(useItem, 1);
				} else if (itemId == 41404) { // ����ũ�� ����
					pc.sendPackets(new S_ServerMessage(338, "$1084")); // �����%0��
																		// ȸ���� ��
																		// ���Դϴ�.
					pc.setCurrentMp(pc.getCurrentMp()
							+ (80 + _random.nextInt(21))); // 80~100
					pc.getInventory().removeItem(useItem, 1);
				} else if (itemId == 41412) { // ���� ����
					pc.sendPackets(new S_ServerMessage(338, "$1084")); // �����%0��
																		// ȸ���� ��
																		// ���Դϴ�.
					pc.setCurrentMp(pc.getCurrentMp()
							+ (5 + _random.nextInt(16))); // 5~20
					pc.getInventory().removeItem(useItem, 1);
				} else if (itemId == 40317) { // ����
					// ���⳪ ���� �ⱸ�� ��츸
					if (l1iteminstance1.getItem().getType2() != 0
							&& l1iteminstance1.get_durability() > 0) {
						String msg0;
						pc.getInventory().recoveryDamage(l1iteminstance1);
						msg0 = l1iteminstance1.getLogName();
						if (l1iteminstance1.get_durability() == 0) {
							pc.sendPackets(new S_ServerMessage(464, msg0)); // %0%s��
																			// ��ǰ
																			// ����
																			// ���°�
																			// �Ǿ����ϴ�.
						} else {
							pc.sendPackets(new S_ServerMessage(463, msg0)); // %0
																			// ���°�
																			// ���������ϴ�.
						}
					} else {
						pc.sendPackets(new S_ServerMessage(79)); // \f1 �ƹ��͵�
																	// �Ͼ��
																	// �ʾҽ��ϴ�.
					}
					pc.getInventory().removeItem(useItem, 1);
				} else if (itemId == L1ItemId.LOWER_OSIRIS_PRESENT_PIECE_DOWN
						|| itemId == L1ItemId.HIGHER_OSIRIS_PRESENT_PIECE_DOWN) { // ���ø�����
																					// ����
																					// (��)
					int itemId2 = l1iteminstance1.getItem().getItemId();
					if (itemId == L1ItemId.LOWER_OSIRIS_PRESENT_PIECE_DOWN
							&& itemId2 == L1ItemId.LOWER_OSIRIS_PRESENT_PIECE_UP) {
						if (pc.getInventory().checkItem(
								L1ItemId.LOWER_OSIRIS_PRESENT_PIECE_UP)) {
							pc.getInventory().removeItem(l1iteminstance1, 1);
							pc.getInventory().removeItem(useItem, 1);
							pc.getInventory().storeItem(
									L1ItemId.CLOSE_LOWER_OSIRIS_PRESENT, 1);
						}
					} else if (itemId == L1ItemId.HIGHER_OSIRIS_PRESENT_PIECE_DOWN
							&& itemId2 == L1ItemId.HIGHER_OSIRIS_PRESENT_PIECE_UP) {
						if (pc.getInventory().checkItem(
								L1ItemId.HIGHER_OSIRIS_PRESENT_PIECE_UP)) {
							pc.getInventory().removeItem(l1iteminstance1, 1);
							pc.getInventory().removeItem(useItem, 1);
							pc.getInventory().storeItem(
									L1ItemId.CLOSE_HIGHER_OSIRIS_PRESENT, 1);
						}
					} else {
						pc.sendPackets(new S_ServerMessage(79)); // \f1 �ƹ��͵�
																	// �Ͼ��
																	// �ʾҽ��ϴ�.
					}
				} else if (itemId == L1ItemId.LOWER_TIKAL_PRESENT_PIECE_DOWN
						|| itemId == L1ItemId.HIGHER_TIKAL_PRESENT_PIECE_DOWN) { // ���ø�����
																					// ����
																					// (��)
					int itemId2 = l1iteminstance1.getItem().getItemId();
					if (itemId == L1ItemId.LOWER_TIKAL_PRESENT_PIECE_DOWN
							&& itemId2 == L1ItemId.LOWER_TIKAL_PRESENT_PIECE_UP) {
						if (pc.getInventory().checkItem(
								L1ItemId.LOWER_TIKAL_PRESENT_PIECE_UP)) {
							pc.getInventory().removeItem(l1iteminstance1, 1);
							pc.getInventory().removeItem(useItem, 1);
							pc.getInventory().storeItem(
									L1ItemId.CLOSE_LOWER_TIKAL_PRESENT, 1);
						}
					} else if (itemId == L1ItemId.HIGHER_TIKAL_PRESENT_PIECE_DOWN
							&& itemId2 == L1ItemId.HIGHER_TIKAL_PRESENT_PIECE_UP) {
						if (pc.getInventory().checkItem(
								L1ItemId.HIGHER_TIKAL_PRESENT_PIECE_UP)) {
							pc.getInventory().removeItem(l1iteminstance1, 1);
							pc.getInventory().removeItem(useItem, 1);
							pc.getInventory().storeItem(
									L1ItemId.CLOSE_HIGHER_TIKAL_PRESENT, 1);
						}
					} else {
						pc.sendPackets(new S_ServerMessage(79)); // \f1 �ƹ��͵�
																	// �Ͼ��
																	// �ʾҽ��ϴ�.
					}
				} else if (itemId == L1ItemId.ANCIENT_ROYALSEAL) { // �°��� ����
					if (client.getAccount().getCharSlot() < 8) {
						client.getAccount().setCharSlot(client,
								client.getAccount().getCharSlot() + 1);
						pc.getInventory().removeItem(useItem, 1);
					} else {
						pc.sendPackets(new S_ServerMessage(79)); // \f1 �ƹ��͵�
																	// �Ͼ��
																	// �ʾҽ��ϴ�.
					}
				} else if (itemId == L1ItemId.TIMECRACK_CORE) { // �տ��� ��
					int itemId2 = l1iteminstance1.getItem().getItemId();
					if (itemId2 == L1ItemId.CLOSE_LOWER_OSIRIS_PRESENT) {
						if (pc.getInventory().checkItem(
								L1ItemId.CLOSE_LOWER_OSIRIS_PRESENT)) {
							pc.getInventory().removeItem(l1iteminstance1, 1);
							pc.getInventory().removeItem(useItem, 1);
							pc.getInventory().storeItem(
									L1ItemId.OPEN_LOWER_OSIRIS_PRESENT, 1);
						}
					} else if (itemId2 == L1ItemId.CLOSE_HIGHER_OSIRIS_PRESENT) {
						if (pc.getInventory().checkItem(
								L1ItemId.CLOSE_HIGHER_OSIRIS_PRESENT)) {
							pc.getInventory().removeItem(l1iteminstance1, 1);
							pc.getInventory().removeItem(useItem, 1);
							pc.getInventory().storeItem(
									L1ItemId.OPEN_HIGHER_OSIRIS_PRESENT, 1);
						}
					} else if (itemId2 == L1ItemId.CLOSE_LOWER_TIKAL_PRESENT) {
						if (pc.getInventory().checkItem(
								L1ItemId.CLOSE_LOWER_TIKAL_PRESENT)) {
							pc.getInventory().removeItem(l1iteminstance1, 1);
							pc.getInventory().removeItem(useItem, 1);
							pc.getInventory().storeItem(
									L1ItemId.OPEN_LOWER_TIKAL_PRESENT, 1);
						}
					} else if (itemId2 == L1ItemId.CLOSE_HIGHER_TIKAL_PRESENT) {
						if (pc.getInventory().checkItem(
								L1ItemId.CLOSE_HIGHER_TIKAL_PRESENT)) {
							pc.getInventory().removeItem(l1iteminstance1, 1);
							pc.getInventory().removeItem(useItem, 1);
							pc.getInventory().storeItem(
									L1ItemId.OPEN_HIGHER_TIKAL_PRESENT, 1);
						}
					} else {
						pc.sendPackets(new S_ServerMessage(79)); // \f1 �ƹ��͵�
																	// �Ͼ��
																	// �ʾҽ��ϴ�.
					}
				} else if (itemId == 40097 || itemId == 40119
						|| itemId == 140119 || itemId == 140329) { // ���ֽ�ũ��,
																	// ���ֹ��� ����
					L1Item template = null;
					for (L1ItemInstance eachItem : pc.getInventory().getItems()) {
						if (eachItem.getItem().getBless() != 2) {
							continue;
						}
						if (!eachItem.isEquipped()
								&& (itemId == 40119 || itemId == 40097)) {
							// n���ִ� ��� �ϰ� �ִ� �� �ۿ� ���� ���� �ʴ´�
							continue;
						}
						int id_normal = eachItem.getItemId() - 200000;
						template = ItemTable.getInstance().getTemplate(
								id_normal);
						if (template == null) {
							continue;
						}
						if (pc.getInventory().checkItem(id_normal)
								&& template.isStackable()) {
							pc.getInventory().storeItem(id_normal,
									eachItem.getCount());
							pc.getInventory().removeItem(eachItem,
									eachItem.getCount());
						} else {
							eachItem.setItem(template);
							pc.getInventory().updateItem(eachItem,
									L1PcInventory.COL_ITEMID);
							pc.getInventory().saveItem(eachItem,
									L1PcInventory.COL_ITEMID);
							eachItem.setBless(eachItem.getBless() - 1);
							pc.getInventory().updateItem(eachItem,
									L1PcInventory.COL_BLESS);
							pc.getInventory().saveItem(eachItem,
									L1PcInventory.COL_BLESS);
						}
					}
					pc.getInventory().removeItem(useItem, 1);
					pc.sendPackets(new S_ServerMessage(155)); // \f1�������� ���� �� ��
																// �����ϴ�.
				} else if (itemId == 41036) { // Ǯ
					int diaryId = l1iteminstance1.getItem().getItemId();
					if (diaryId >= 41038 && 41047 >= diaryId) {
						if ((_random.nextInt(99) + 1) <= Config.CREATE_CHANCE_DIARY) {
							createNewItem(pc, diaryId + 10, 1);
						} else {
							pc.sendPackets(new S_ServerMessage(158,
									l1iteminstance1.getName())); // \f1%0�� �����ϰ�
																	// ���� �ʰ�
																	// �Ǿ����ϴ�.
						}
						pc.getInventory().removeItem(l1iteminstance1, 1);
						pc.getInventory().removeItem(useItem, 1);
					} else {
						pc.sendPackets(new S_ServerMessage(79)); // \f1 �ƹ��͵�
																	// �Ͼ��
																	// �ʾҽ��ϴ�.
					}
				} else if (itemId == 40964) { // �渶�� ����
					int historybookId = l1iteminstance1.getItem().getItemId();
					if (historybookId >= 41011 && 41018 >= historybookId) {
						if ((_random.nextInt(99) + 1) <= Config.CREATE_CHANCE_HISTORY_BOOK) {
							createNewItem(pc, historybookId + 8, 1);
						} else {
							pc.sendPackets(new S_ServerMessage(158,
									l1iteminstance1.getName()));
						}
						pc.getInventory().removeItem(l1iteminstance1, 1);
						pc.getInventory().removeItem(useItem, 1);
					} else {
						pc.sendPackets(new S_ServerMessage(79));
					}
				} else if (itemId == 40925) { // ��ȭ�� �Ϻ�
					int earingId = l1iteminstance1.getItem().getItemId();
					if (earingId >= 40987 && 40989 >= earingId) { // �������� �� �� ��
						if (_random.nextInt(100) < Config.CREATE_CHANCE_RECOLLECTION) {
							createNewItem(pc, earingId + 186, 1);
						} else {
							pc.sendPackets(new S_ServerMessage(158,
									l1iteminstance1.getName())); // \f1%0�� �����ϰ�
																	// ���� �ʰ�
																	// �Ǿ����ϴ�.
						}
						pc.getInventory().removeItem(l1iteminstance1, 1);
						pc.getInventory().removeItem(useItem, 1);
					} else {
						pc.sendPackets(new S_ServerMessage(79)); // \f1 �ƹ��͵�
																	// �Ͼ��
																	// �ʾҽ��ϴ�.
					}
				} else if (itemId >= 40926 && 40929 >= itemId) {
					// ���ź����� �Ϻ�(1~4 �ܰ�)
					int earing2Id = l1iteminstance1.getItem().getItemId();
					int potion1 = 0;
					int potion2 = 0;
					if (earing2Id >= 41173 && 41184 >= earing2Id) {
						// �� ����
						if (itemId == 40926) {
							potion1 = 247;
							potion2 = 249;
						} else if (itemId == 40927) {
							potion1 = 249;
							potion2 = 251;
						} else if (itemId == 40928) {
							potion1 = 251;
							potion2 = 253;
						} else if (itemId == 40929) {
							potion1 = 253;
							potion2 = 255;
						}
						if (earing2Id >= (itemId + potion1)
								&& (itemId + potion2) >= earing2Id) {
							if ((_random.nextInt(99) + 1) < Config.CREATE_CHANCE_MYSTERIOUS) {
								createNewItem(pc, (earing2Id - 12), 1);
								pc.getInventory()
										.removeItem(l1iteminstance1, 1);
								pc.getInventory().removeItem(useItem, 1);
							} else {
								pc.sendPackets(new S_ServerMessage(160,
										l1iteminstance1.getName()));
								// \f1%0��%2 �����ϰ�%1 �������ϴٸ�, ������ �����ϰ� ��ҽ��ϴ�.
								pc.getInventory().removeItem(useItem, 1);
							}
						} else {
							pc.sendPackets(new S_ServerMessage(79)); // \f1 �ƹ��͵�
																		// �Ͼ��
																		// �ʾҽ��ϴ�.
						}
					} else {
						pc.sendPackets(new S_ServerMessage(79)); // \f1 �ƹ��͵�
																	// �Ͼ��
																	// �ʾҽ��ϴ�.
					}
				} else if (itemId == 41029) { // ��ȯ�� ����
					int dantesId = l1iteminstance1.getItem().getItemId();
					if (dantesId >= 41030 && 41034 >= dantesId) { // ��ȯ���� �ھ ��
																	// �ܰ�
						if ((_random.nextInt(99) + 1) < Config.CREATE_CHANCE_DANTES) {
							createNewItem(pc, dantesId + 1, 1);
						} else {
							pc.sendPackets(new S_ServerMessage(158,
									l1iteminstance1.getName())); // \f1%0�� �����ϰ�
																	// ���� �ʰ�
																	// �Ǿ����ϴ�.
						}
						pc.getInventory().removeItem(l1iteminstance1, 1);
						pc.getInventory().removeItem(useItem, 1);
					} else {
						pc.sendPackets(new S_ServerMessage(79)); // \f1 �ƹ��͵�
																	// �Ͼ��
																	// �ʾҽ��ϴ�.
					}
					// ���� ��ũ��
				} else if ((itemId >= 40859 && itemId <= 40898)
						&& itemId != 40863) { // 40863�� �ڷ���Ʈ ��ũ�ѷμ� ó���ȴ�
					if (spellsc_objid == pc.getId()
							&& useItem.getItem().getUseType() != 30) {
						// spell_buff
						pc.sendPackets(new S_ServerMessage(281)); // \f1������ ��ȿ��
																	// �Ǿ����ϴ�.
						return;
					}
					pc.getInventory().removeItem(useItem, 1);
					if (spellsc_objid == 0
							&& useItem.getItem().getUseType() != 0
							&& useItem.getItem().getUseType() != 26
							&& useItem.getItem().getUseType() != 27) {
						return;
						// Ÿ���� ���� ��쿡 handleCommands�۰� �Ǳ� (����)������ ���⼭ return
						// handleCommands ������ �Ǵܣ�ó���ؾ� �� �κ������� �𸥴�
					}
					pc.cancelAbsoluteBarrier(); // �ƺ�Ҹ�Ʈ�ٸ����� ����
					int skillid = itemId - 40858;
					L1SkillUse l1skilluse = new L1SkillUse();
					l1skilluse.handleCommands(client.getActiveChar(), skillid,
							spellsc_objid, spellsc_x, spellsc_y, null, 0,
							L1SkillUse.TYPE_SPELLSC);

				} else if (itemId >= 40373 && itemId <= 40384 // ���� ����
						|| itemId >= 40385 && itemId <= 40390) {
					pc.sendPackets(new S_UseMap(pc, useItem.getId(), useItem
							.getItem().getItemId()));
				} else if (itemId == 40314 || itemId == 40316) { // ���� �ƹ·�Ʈ
					if (pc.getInventory().checkItem(41160)) { // ��ȯ�� �Ǹ�
						if (withdrawPet(pc, itemObjid)) {
							pc.getInventory().consumeItem(41160, 1);
						}
					} else {
						pc.sendPackets(new S_ServerMessage(79)); // \f1 �ƹ��͵�
																	// �Ͼ��
																	// �ʾҽ��ϴ�.
					}
				} else if (itemId == 40315) { // ���� �Ǹ�
					pc.sendPackets(new S_Sound(437));
					Broadcaster.broadcastPacket(pc, new S_Sound(437));
					Object[] petList = pc.getPetList().values().toArray();
					for (Object petObject : petList) {
						if (petObject instanceof L1PetInstance) { // ��
							L1PetInstance pet = (L1PetInstance) petObject;
							pet.call();
						}
					}
				} else if (itemId == 40493) { // ���� �÷�
					pc.sendPackets(new S_Sound(165));
					Broadcaster.broadcastPacket(pc, new S_Sound(165));
					L1GuardianInstance guardian = null;
					for (L1Object visible : pc.getNearObjects()
							.getKnownObjects()) {
						if (visible instanceof L1GuardianInstance) {
							guardian = (L1GuardianInstance) visible;
							if (guardian.getNpcTemplate().get_npcId() == 70850) { // ��
								if (createNewItem(pc, 88, 1)) {
									pc.getInventory().removeItem(useItem, 1);
								}
							}
						}
					}
				} else if (itemId == 40325) {
					if (pc.getInventory().checkItem(40318, 1)) {
						int gfxid = 3237 + _random.nextInt(2);
						pc.sendPackets(new S_SkillSound(pc.getId(), gfxid));
						Broadcaster.broadcastPacket(pc,
								new S_SkillSound(pc.getId(), gfxid));
						pc.getInventory().consumeItem(40318, 1);
					} else {
						pc.sendPackets(new S_ServerMessage(79)); // \f1 �ƹ��͵�
																	// �Ͼ��
																	// �ʾҽ��ϴ�.
					}
				} else if (itemId == 40326) {
					if (pc.getInventory().checkItem(40318, 1)) {
						int gfxid = 3229 + _random.nextInt(3);
						pc.sendPackets(new S_SkillSound(pc.getId(), gfxid));
						Broadcaster.broadcastPacket(pc,
								new S_SkillSound(pc.getId(), gfxid));
						pc.getInventory().consumeItem(40318, 1);
					} else {
						pc.sendPackets(new S_ServerMessage(79)); // \f1 �ƹ��͵�
																	// �Ͼ��
																	// �ʾҽ��ϴ�.
					}
				} else if (itemId == 40327) {
					if (pc.getInventory().checkItem(40318, 1)) {
						int gfxid = 3241 + _random.nextInt(4);
						pc.sendPackets(new S_SkillSound(pc.getId(), gfxid));
						Broadcaster.broadcastPacket(pc,
								new S_SkillSound(pc.getId(), gfxid));
						pc.getInventory().consumeItem(40318, 1);
					} else {
						pc.sendPackets(new S_ServerMessage(79)); // \f1 �ƹ��͵�
																	// �Ͼ��
																	// �ʾҽ��ϴ�.
					}
				} else if (itemId == 40328) {
					if (pc.getInventory().checkItem(40318, 1)) {
						int gfxid = 3204 + _random.nextInt(6);
						pc.sendPackets(new S_SkillSound(pc.getId(), gfxid));
						Broadcaster.broadcastPacket(pc,
								new S_SkillSound(pc.getId(), gfxid));
						pc.getInventory().consumeItem(40318, 1);
					} else {
						// \f1 �ƹ��͵� �Ͼ�� �ʾҽ��ϴ�.
						pc.sendPackets(new S_ServerMessage(79));
					}
				} else if (itemId == L1ItemId.CHARACTER_REPAIR_SCROLL) {
					Connection connection = null;
					connection = L1DatabaseFactory.getInstance()
							.getConnection();
					PreparedStatement preparedstatement = connection
							.prepareStatement("UPDATE characters SET LocX=33087, LocY=33399, MapID=4 WHERE account_name=?");
					preparedstatement.setString(1, client.getAccountName());
					preparedstatement.execute();
					preparedstatement.close();
					connection.close();
					pc.getInventory().removeItem(useItem, 1);
					pc.sendPackets(new S_SystemMessage(
							"��� �ɸ����� ��ǥ�� ���������� ���� �Ǿ����ϴ�."));
				} else if (itemId >= 40903 && itemId <= 40908) { // ���� ��ȥ ����
					L1PcInstance partner = null;
					boolean partner_stat = false;
					if (pc.getPartnerId() != 0) { // ��ȥ��
						partner = (L1PcInstance) L1World.getInstance()
								.findObject(pc.getPartnerId());
						if (partner != null && partner.getPartnerId() != 0
								&& pc.getPartnerId() == partner.getId()
								&& partner.getPartnerId() == pc.getId()) {
							partner_stat = true;
						}
					} else {
						pc.sendPackets(new S_ServerMessage(662)); // \f1����� ��ȥ����
																	// �ʾҽ��ϴ�.
						return;
					}

					if (useItem.getChargeCount() <= 0) {
						return;
					}
					if (pc.getMapId() == 666) {
						return;
					}
					if (partner_stat) {
						boolean castle_area = L1CastleLocation
								.checkInAllWarArea(partner.getX(),
										partner.getY(), partner.getMapId());

						if ((partner.getMapId() == 0 || partner.getMapId() == 4 || partner
								.getMapId() == 304) && castle_area == false) {
							useItem.setChargeCount(useItem.getChargeCount() - 1);
							pc.getInventory().updateItem(useItem,
									L1PcInventory.COL_CHARGE_COUNT);
							L1Teleport
									.teleport(pc, partner.getX(),
											partner.getY(), partner.getMapId(),
											5, true);
						} else {
							pc.sendPackets(new S_ServerMessage(547)); // \f1�����
																		// ��Ʈ�ʴ�
																		// ����
																		// ����� ��
																		// �� ����
																		// ������
																		// �÷������Դϴ�.
						}
					} else {
						pc.sendPackets(new S_ServerMessage(546)); // \f1����� ��Ʈ�ʴ�
																	// ���� �÷��̸�
																	// �ϰ� ����
																	// �ʽ��ϴ�.
					}
				} else if (itemId == 40555) { // ����� ���� Ű
					// ���� ��
					if (pc.isKnight()
							&& (pc.getX() >= 32806 && pc.getX() <= 32814)
							&& (pc.getY() >= 32798 && pc.getY() <= 32807)
							&& pc.getMapId() == 13) {
						L1Teleport.teleport(pc, 32815, 32810, (short) 13, 5,
								false);
					} else {
						pc.sendPackets(new S_ServerMessage(79)); // \f1 �ƹ��͵�
																	// �Ͼ��
																	// �ʾҽ��ϴ�.
					}
				} else if (itemId == 40417) { // �����ǰ���
					if ((pc.getX() >= 32667 && pc.getX() <= 32673)
							&& (pc.getY() >= 32978 && pc.getY() <= 32984)
							&& pc.getMapId() == 440) {
						L1Teleport.teleport(pc, 32922, 32812, (short) 430, 5,
								true);
					} else {
						pc.sendPackets(new S_ServerMessage(79)); // \f1 �ƹ��͵�
																	// �Ͼ��
																	// �ʾҽ��ϴ�.
					}
				} else if (itemId == 40566) { // �ź����� ��
					// ����� ž�� ������ ���ʿ� �ִ� ���� �������� ��ǥ
					if (pc.isElf()
							&& (pc.getX() >= 33971 && pc.getX() <= 33975)
							&& (pc.getY() >= 32324 && pc.getY() <= 32328)
							&& pc.getMapId() == 4
							&& !pc.getInventory().checkItem(40548)) { // ������ ����
						boolean found = false;
						L1MonsterInstance mob = null;
						for (L1Object obj : L1World.getInstance()
								.getVisibleObjects(4).values()) {
							if (obj instanceof L1MonsterInstance) {
								mob = (L1MonsterInstance) obj;
								if (mob != null) {
									if (mob.getNpcTemplate().get_npcId() == 45300) {
										found = true;
										break;
									}
								}
							}
						}
						if (found) {
							pc.sendPackets(new S_ServerMessage(79)); // \f1 �ƹ��͵�
																		// �Ͼ��
																		// �ʾҽ��ϴ�.
						} else {
							L1SpawnUtil.spawn(pc, 45300, 0, 0, false); // �������
																		// ����
						}
					} else {
						pc.sendPackets(new S_ServerMessage(79)); // \f1 �ƹ��͵�
																	// �Ͼ��
																	// �ʾҽ��ϴ�.
					}
				} else if (itemId == 40557) {
					if (pc.getX() == 32620 && pc.getY() == 32641
							&& pc.getMapId() == 4) {
						L1NpcInstance object = null;
						for (L1Object obj : L1World.getInstance().getObject()) {
							if (obj instanceof L1NpcInstance) {
								object = (L1NpcInstance) obj;
								if (object.getNpcTemplate().get_npcId() == 45883) {
									pc.sendPackets(new S_ServerMessage(79));
									return;
								}
							}

						}
						L1SpawnUtil.spawn(pc, 45883, 0, 300000, false);
					} else {
						pc.sendPackets(new S_ServerMessage(79));
					}
				} else if (itemId == 40563) {
					if (pc.getX() == 32730 && pc.getY() == 32426
							&& pc.getMapId() == 4) {
						L1NpcInstance object = null;
						for (L1Object obj : L1World.getInstance().getObject()) {
							if (obj instanceof L1NpcInstance) {
								object = (L1NpcInstance) obj;
								if (object.getNpcTemplate().get_npcId() == 45884) {
									pc.sendPackets(new S_ServerMessage(79));
									return;
								}
							}

						}
						L1SpawnUtil.spawn(pc, 45884, 0, 300000, false);
					} else {
						pc.sendPackets(new S_ServerMessage(79));
					}
				} else if (itemId == 40561) {
					if (pc.getX() == 33046 && pc.getY() == 32806
							&& pc.getMapId() == 4) {
						L1NpcInstance object = null;
						for (L1Object obj : L1World.getInstance().getObject()) {
							if (obj instanceof L1NpcInstance) {
								object = (L1NpcInstance) obj;
								if (object.getNpcTemplate().get_npcId() == 45885) {
									pc.sendPackets(new S_ServerMessage(79));
									return;
								}
							}

						}
						L1SpawnUtil.spawn(pc, 45885, 0, 300000, false);
					} else {
						pc.sendPackets(new S_ServerMessage(79));
					}
				} else if (itemId == 40560) {
					if (pc.getX() == 32580 && pc.getY() == 33260
							&& pc.getMapId() == 4) {
						L1NpcInstance object = null;
						for (L1Object obj : L1World.getInstance().getObject()) {
							if (obj instanceof L1NpcInstance) {
								object = (L1NpcInstance) obj;
								if (object.getNpcTemplate().get_npcId() == 45886) {
									pc.sendPackets(new S_ServerMessage(79));
									return;
								}
							}

						}
						L1SpawnUtil.spawn(pc, 45886, 0, 300000, false);
					} else {
						pc.sendPackets(new S_ServerMessage(79));
					}
				} else if (itemId == 40562) {
					if (pc.getX() == 33447 && pc.getY() == 33476
							&& pc.getMapId() == 4) {
						L1NpcInstance object = null;
						for (L1Object obj : L1World.getInstance().getObject()) {
							if (obj instanceof L1NpcInstance) {
								object = (L1NpcInstance) obj;
								if (object.getNpcTemplate().get_npcId() == 45887) {
									pc.sendPackets(new S_ServerMessage(79));
									return;
								}
							}

						}
						L1SpawnUtil.spawn(pc, 45887, 0, 300000, false);
					} else {
						pc.sendPackets(new S_ServerMessage(79));
					}
				} else if (itemId == 40559) {
					if (pc.getX() == 34215 && pc.getY() == 33195
							&& pc.getMapId() == 4) {
						L1NpcInstance object = null;
						for (L1Object obj : L1World.getInstance().getObject()) {
							if (obj instanceof L1NpcInstance) {
								object = (L1NpcInstance) obj;
								if (object.getNpcTemplate().get_npcId() == 45888) {
									pc.sendPackets(new S_ServerMessage(79));
									return;
								}
							}

						}
						L1SpawnUtil.spawn(pc, 45888, 0, 300000, false);
					} else {
						pc.sendPackets(new S_ServerMessage(79));
					}
				} else if (itemId == 40558) {
					if (pc.getX() == 33513 && pc.getY() == 32890
							&& pc.getMapId() == 4) {
						L1NpcInstance object = null;
						for (L1Object obj : L1World.getInstance().getObject()) {
							if (obj instanceof L1NpcInstance) {
								object = (L1NpcInstance) obj;
								if (object.getNpcTemplate().get_npcId() == 45889) {
									pc.sendPackets(new S_ServerMessage(79));
									return;
								}
							}

						}
						L1SpawnUtil.spawn(pc, 45889, 0, 300000, false);
					} else {
						pc.sendPackets(new S_ServerMessage(79));
					}
				} else if (itemId == 40572) {
					if (pc.getX() == 32778 && pc.getY() == 32738
							&& pc.getMapId() == 21) {
						L1Teleport.teleport(pc, 32781, 32728, (short) 21, 5,
								true);
					} else if (pc.getX() == 32781 && pc.getY() == 32728
							&& pc.getMapId() == 21) {
						L1Teleport.teleport(pc, 32778, 32738, (short) 21, 5,
								true);
					} else {
						pc.sendPackets(new S_ServerMessage(79));
					}
				} else if (itemId == 40009) {// �߹渷��
					int chargeCount = useItem.getChargeCount();
					if (chargeCount <= 0) {
						pc.sendPackets(new S_ServerMessage(79));// \f1 �ƹ��͵� �Ͼ��
																// �ʾҽ��ϴ�.
						return;
					}

					L1Object target = L1World.getInstance().findObject(
							spellsc_objid);
					if (target != null) {
						int heding = CharPosUtil.targetDirection(pc, spellsc_x,
								spellsc_y);
						pc.getMoveState().setHeading(heding);
						pc.sendPackets(new S_AttackPacket(pc, 0,
								ActionCodes.ACTION_Wand));
						Broadcaster.broadcastPacket(pc, new S_AttackPacket(pc,
								0, ActionCodes.ACTION_Wand));

						if (target instanceof L1PcInstance) {
							L1PcInstance cha = (L1PcInstance) target;
							if (cha.getLevel() <= 60) {
								if (!L1CastleLocation.checkInAllWarArea(
										cha.getX(), cha.getY(), cha.getMapId())
										&& (CharPosUtil.getZoneType(cha) == 0 || CharPosUtil
												.getZoneType(cha) == -1)) {
									L1Teleport.teleport(cha, pc.getLocation(),
											pc.getMoveState().getHeading(),
											false);
								}
							}
							if (cha.getSkillEffectTimerSet().hasSkillEffect(
									ERASE_MAGIC)) {
								cha.getSkillEffectTimerSet().removeSkillEffect(
										ERASE_MAGIC);
							}
						}
					}

					useItem.setChargeCount(useItem.getChargeCount() - 1);
					pc.getInventory().updateItem(useItem,
							L1PcInventory.COL_CHARGE_COUNT);

				} else if (itemId == L1ItemId.ICECAVE_KEY) {
					L1Object t = L1World.getInstance()
							.findObject(spellsc_objid);
					L1DoorInstance door = (L1DoorInstance) t;
					if (pc.getLocation()
							.getTileLineDistance(door.getLocation()) > 3) {
						return;
					}
					if (door.getDoorId() >= 5000 && door.getDoorId() <= 5009) {
						if (door != null
								&& door.getOpenStatus() == ActionCodes.ACTION_Close) {
							door.open();
							pc.getInventory().removeItem(useItem, 1);
						}
					}
				} else if (itemId >= 40289 && itemId <= 40297) { // ������ ž11~91��
																	// ����
					useToiTeleportAmulet(pc, itemId, useItem);
				} else if (itemId >= 40280 && itemId <= 40288) {
					// ���ε� ������ ž 11~91�� ����
					pc.getInventory().removeItem(useItem, 1);
					L1ItemInstance item = pc.getInventory().storeItem(
							itemId + 9, 1);
					if (item != null) {
						pc.sendPackets(new S_ServerMessage(403, item
								.getLogName()));
					}
				} else if (itemId == 40070) {
					pc.sendPackets(new S_ServerMessage(76, useItem.getLogName()));
					pc.getInventory().removeItem(useItem, 1);
				} else if (itemId == 41301) { // ���̴׷����ͽ�
					int chance = _random.nextInt(10);
					if (chance >= 0 && chance < 5) {
						UseHeallingPotion(pc, 15, 189);
					} else if (chance >= 5 && chance < 9) {
						createNewItem(pc, 40019, 1);
					} else if (chance >= 9) {
						int gemChance = _random.nextInt(3);
						if (gemChance == 0) {
							createNewItem(pc, 40045, 1);
						} else if (gemChance == 1) {
							createNewItem(pc, 40049, 1);
						} else if (gemChance == 2) {
							createNewItem(pc, 40053, 1);
						}
					}
					pc.getInventory().removeItem(useItem, 1);
				} else if (itemId == 41302) { // ���̴ױ׸��ͽ�
					int chance = _random.nextInt(3);
					if (chance >= 0 && chance < 5) {
						UseHeallingPotion(pc, 15, 189);
					} else if (chance >= 5 && chance < 9) {
						createNewItem(pc, 40018, 1);
					} else if (chance >= 9) {
						int gemChance = _random.nextInt(3);
						if (gemChance == 0) {
							createNewItem(pc, 40047, 1);
						} else if (gemChance == 1) {
							createNewItem(pc, 40051, 1);
						} else if (gemChance == 2) {
							createNewItem(pc, 40055, 1);
						}
					}
					pc.getInventory().removeItem(useItem, 1);
				} else if (itemId == 41303) { // ���̴׺긣�ͽ�
					int chance = _random.nextInt(3);
					if (chance >= 0 && chance < 5) {
						UseHeallingPotion(pc, 15, 189);
					} else if (chance >= 5 && chance < 9) {
						createNewItem(pc, 40015, 1);
					} else if (chance >= 9) {
						int gemChance = _random.nextInt(3);
						if (gemChance == 0) {
							createNewItem(pc, 40046, 1);
						} else if (gemChance == 1) {
							createNewItem(pc, 40050, 1);
						} else if (gemChance == 2) {
							createNewItem(pc, 40054, 1);
						}
					}
					pc.getInventory().removeItem(useItem, 1);
				} else if (itemId == 41304) { // ���̴�ȭ��Ʈ�ͽ�
					int chance = _random.nextInt(3);
					if (chance >= 0 && chance < 5) {
						UseHeallingPotion(pc, 15, 189);
					} else if (chance >= 5 && chance < 9) {
						createNewItem(pc, 40021, 1);
					} else if (chance >= 9) {
						int gemChance = _random.nextInt(3);
						if (gemChance == 0) {
							createNewItem(pc, 40044, 1);
						} else if (gemChance == 1) {
							createNewItem(pc, 40048, 1);
						} else if (gemChance == 2) {
							createNewItem(pc, 40052, 1);
						}
					}
					pc.getInventory().removeItem(useItem, 1);
				} else if (itemId == 40615) { // �׸����� ���� 2���� ����
					if ((pc.getX() >= 32701 && pc.getX() <= 32705)
							&& (pc.getY() >= 32894 && pc.getY() <= 32898)
							&& pc.getMapId() == 522) { // �׸����� ���� 1F
						L1Teleport.teleport(pc,
								((L1EtcItem) useItem.getItem()).get_locx(),
								((L1EtcItem) useItem.getItem()).get_locy(),
								((L1EtcItem) useItem.getItem()).get_mapid(), 5,
								true);
					} else {
						// \f1 �ƹ��͵� �Ͼ�� �ʾҽ��ϴ�.
						pc.sendPackets(new S_ServerMessage(79));
					}
				} else if (itemId == 40616 || itemId == 40782
						|| itemId == 40783) { // �׸����� ���� 3���� ����
					if ((pc.getX() >= 32698 && pc.getX() <= 32702)
							&& (pc.getY() >= 32894 && pc.getY() <= 32898)
							&& pc.getMapId() == 523) { // �׸����� ���� 2��
						L1Teleport.teleport(pc,
								((L1EtcItem) useItem.getItem()).get_locx(),
								((L1EtcItem) useItem.getItem()).get_locy(),
								((L1EtcItem) useItem.getItem()).get_mapid(), 5,
								true);
					} else {
						// \f1 �ƹ��͵� �Ͼ�� �ʾҽ��ϴ�.
						pc.sendPackets(new S_ServerMessage(79));
					}
				} else if (itemId == 40692) { // �ϼ��� ������ ����
					if (pc.getInventory().checkItem(40621)) {
						// \f1 �ƹ��͵� �Ͼ�� �ʾҽ��ϴ�.
						pc.sendPackets(new S_ServerMessage(79));
					} else if ((pc.getX() >= 32856 && pc.getX() <= 32858)
							&& (pc.getY() >= 32857 && pc.getY() <= 32858)
							&& pc.getMapId() == 443) { // �������� ���� ���� 3��
						L1Teleport.teleport(pc,
								((L1EtcItem) useItem.getItem()).get_locx(),
								((L1EtcItem) useItem.getItem()).get_locy(),
								((L1EtcItem) useItem.getItem()).get_mapid(), 5,
								true);
					} else {
						// \f1 �ƹ��͵� �Ͼ�� �ʾҽ��ϴ�.
						pc.sendPackets(new S_ServerMessage(79));
					}
				} else if (itemId == 41146) { // ��θ���� �ʴ���
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei001"));
				} else if (itemId == 41209) { // ���Ƿ����� �Ƿڼ�
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei002"));
				} else if (itemId == 41210) { // ������
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei003"));
				} else if (itemId == 41211) { // ���
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei004"));
				} else if (itemId == 41212) { // Ư�� ĵ��
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei005"));
				} else if (itemId == 41213) { // Ƽ���� �ٽ���
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei006"));
				} else if (itemId == 41214) { // ���� ����
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei012"));
				} else if (itemId == 41215) { // ���� ����
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei010"));
				} else if (itemId == 41216) { // ���� ����
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei011"));
				} else if (itemId == 41222) { // ������
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei008"));
				} else if (itemId == 41223) { // ������ ����
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei007"));
				} else if (itemId == 41224) { // ����
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei009"));
				} else if (itemId == 41225) { // �ɽ�Ų�� ���ּ�
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei013"));
				} else if (itemId == 41226) { // �İ��� ��
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei014"));
				} else if (itemId == 41227) { // �˷����� �Ұ���
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei033"));
				} else if (itemId == 41228) { // �����ڻ��� ����
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei034"));
				} else if (itemId == 41229) { // ���̸����� �Ӹ�
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei025"));
				} else if (itemId == 41230) { // �������� ����
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei020"));
				} else if (itemId == 41231) { // ��Ƽ���� ����
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei021"));
				} else if (itemId == 41233) { // �����̿��� ����
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei019"));
				} else if (itemId == 41234) { // ���� ���� ����
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei023"));
				} else if (itemId == 41235) { // ���ǥ
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei024"));
				} else if (itemId == 41236) { // ����í�� ��
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei026"));
				} else if (itemId == 41237) { // ���̸��� ������ũ�� ��
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei027"));
				} else if (itemId == 41239) { // ��Ʈ���� ����
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei018"));
				} else if (itemId == 41240) { // ��ٿ��� ����
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei022"));
				} else if (itemId == 41060) { // �볪���� ��õ��
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "nonames"));
				} else if (itemId == 41061) { // ������� ������������ ���� �δٸ���ī��
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "kames"));
				} else if (itemId == 41062) { // ������� �������ΰ� ���� �׸�����ũ��
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "bakumos"));
				} else if (itemId == 41063) { // ������� ���������� ���� �δٸ����ī
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "bukas"));
				} else if (itemId == 41064) { // ������� ��������ũ ���� �׸����Ŀ��
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "huwoomos"));
				} else if (itemId == 41065) { // ������� ������������� ��Ʈ�ٳ��
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "noas"));
				} else if (itemId == 41356) { // �ķ��� �ڿ� ����Ʈ
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "rparum3"));
				} else if (itemId == 40701) { // ���� ������ ����
					if (pc.getQuest().get_step(L1Quest.QUEST_LUKEIN1) == 1) {
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(),
								"firsttmap"));
					} else if (pc.getQuest().get_step(L1Quest.QUEST_LUKEIN1) == 2) {
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(),
								"secondtmapa"));
					} else if (pc.getQuest().get_step(L1Quest.QUEST_LUKEIN1) == 3) {
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(),
								"secondtmapb"));
					} else if (pc.getQuest().get_step(L1Quest.QUEST_LUKEIN1) == 4) {
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(),
								"secondtmapc"));
					} else if (pc.getQuest().get_step(L1Quest.QUEST_LUKEIN1) == 5) {
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(),
								"thirdtmapd"));
					} else if (pc.getQuest().get_step(L1Quest.QUEST_LUKEIN1) == 6) {
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(),
								"thirdtmape"));
					} else if (pc.getQuest().get_step(L1Quest.QUEST_LUKEIN1) == 7) {
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(),
								"thirdtmapf"));
					} else if (pc.getQuest().get_step(L1Quest.QUEST_LUKEIN1) == 8) {
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(),
								"thirdtmapg"));
					} else if (pc.getQuest().get_step(L1Quest.QUEST_LUKEIN1) == 9) {
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(),
								"thirdtmaph"));
					} else if (pc.getQuest().get_step(L1Quest.QUEST_LUKEIN1) == 10) {
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(),
								"thirdtmapi"));
					}
				} else if (itemId == 40663) { // �Ƶ��� ����
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "sonsletter"));
				} else if (itemId == 40630) { // �𿡰��� ���� �ϱ�
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "diegodiary"));
				} else if (itemId == 41340) { // �뺴���� Ƽ��
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "tion"));
				} else if (itemId == 41317) { // ������ ��õ��
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "rarson"));
				} else if (itemId == 41318) { // ���� �޸�
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "kuen"));
				} else if (itemId == 41329) { // ������ ���� �Ƿڼ�
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "anirequest"));
				} else if (itemId == 41346) { // �κ��ʵ��� �޸� 1
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(),
							"robinscroll"));
				} else if (itemId == 41347) { // �κ��ʵ��� �޸� 2
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(),
							"robinscroll2"));
				} else if (itemId == 41348) { // �κ��ʵ��� �Ұ���
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "robinhood"));
				} else if (itemId == 41007) {
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "erisscroll"));
				} else if (itemId == 41009) {
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(),
							"erisscroll2"));
				} else if (itemId == 41019) {
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(),
							"lashistory1"));
				} else if (itemId == 41020) {
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(),
							"lashistory2"));
				} else if (itemId == 41021) {
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(),
							"lashistory3"));
				} else if (itemId == 41022) {
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(),
							"lashistory4"));
				} else if (itemId == 41023) {
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(),
							"lashistory5"));
				} else if (itemId == 41024) {
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(),
							"lashistory6"));
				} else if (itemId == 41025) {
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(),
							"lashistory7"));
				} else if (itemId == 41026) {
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(),
							"lashistory8"));
				} else if (itemId == 210087) { // �������� ù ��° ���ɼ�
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "first_p"));
				} else if (itemId == 210093) { // �Ƿ����� ù ��° ����
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "silrein1lt"));
				} else if (itemId == L1ItemId.TIKAL_CALENDAR) {
					if (CrockSystem.getInstance().isOpen())
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(),
								"tcalendaro"));
					else
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(),
								"tcalendarc"));
				} else if (itemId == 41208) { // �� ���� ��ȥ
					if ((pc.getX() >= 32844 && pc.getX() <= 32845)
							&& (pc.getY() >= 32693 && pc.getY() <= 32694)
							&& pc.getMapId() == 550) { // ���� ����:������
						L1Teleport.teleport(pc,
								((L1EtcItem) useItem.getItem()).get_locx(),
								((L1EtcItem) useItem.getItem()).get_locy(),
								((L1EtcItem) useItem.getItem()).get_mapid(), 5,
								true);
					} else {
						// \f1 �ƹ��͵� �Ͼ�� �ʾҽ��ϴ�.
						pc.sendPackets(new S_ServerMessage(79));
					}
				} else if (itemId == 40700) { // �ǹ� �÷�
					pc.sendPackets(new S_Sound(10));
					Broadcaster.broadcastPacket(pc, new S_Sound(10));
					if ((pc.getX() >= 32619 && pc.getX() <= 32623)
							&& (pc.getY() >= 33120 && pc.getY() <= 33124)
							&& pc.getMapId() == 440) { // ���� �ø������ݸ��� ������ ��ǥ
						boolean found = false;
						L1MonsterInstance mon = null;
						for (L1Object obj : L1World.getInstance().getObject()) {
							if (obj instanceof L1MonsterInstance) {
								mon = (L1MonsterInstance) obj;
								if (mon != null) {
									if (mon.getNpcTemplate().get_npcId() == 45875) {
										found = true;
										break;
									}
								}
							}

						}
						if (found) {
						} else {
							L1SpawnUtil.spawn(pc, 45875, 0, 0, false);
						}
					}
				} else if (itemId == 41121) {
					if (pc.getQuest().get_step(L1Quest.QUEST_SHADOWS) == L1Quest.QUEST_END
							|| pc.getInventory().checkItem(41122, 1)) {
						pc.sendPackets(new S_ServerMessage(79));
					} else {
						createNewItem(pc, 41122, 1);
					}
				} else if (itemId == 41130) {
					if (pc.getQuest().get_step(L1Quest.QUEST_DESIRE) == L1Quest.QUEST_END
							|| pc.getInventory().checkItem(41131, 1)) {
						pc.sendPackets(new S_ServerMessage(79));
					} else {
						createNewItem(pc, 41131, 1);
					}
				} else if (itemId == 42501) { // ���� ��ũ
					if (pc.getCurrentMp() < 10) {
						pc.sendPackets(new S_ServerMessage(278)); // \f1MP�� ������
																	// ������ ����� ��
																	// ���� �ʽ��ϴ�.
						return;
					}
					pc.setCurrentMp(pc.getCurrentMp() - 10);
					L1Teleport.teleport(pc, spellsc_x, spellsc_y,
							pc.getMapId(), pc.getMoveState().getHeading(),
							true, L1Teleport.CHANGE_POSITION);
				} else if (itemId == 50101) { // ��ġ����
					IdentMapWand(pc, spellsc_x, spellsc_y);
				} else if (itemId == 50102) { // ��ġ���渷��
					MapFixKeyWand(pc, spellsc_x, spellsc_y);
				} else if (itemId == L1ItemId.CHANGING_PETNAME_SCROLL) {
					if (l1iteminstance1.getItem().getItemId() == 40314
							|| l1iteminstance1.getItem().getItemId() == 40316) {
						L1Pet petTemplate = PetTable.getInstance().getTemplate(
								l1iteminstance1.getId());
						L1Npc l1npc = NpcTable.getInstance().getTemplate(
								petTemplate.get_npcid());
						if (petTemplate == null) {
							throw new NullPointerException();
						}
						petTemplate.set_name(l1npc.get_name());
						PetTable.getInstance().storePet(petTemplate);
						L1ItemInstance item = pc.getInventory().getItem(
								l1iteminstance1.getId());
						pc.getInventory().updateItem(item);
						pc.getInventory().removeItem(useItem, 1);
						pc.sendPackets(new S_ServerMessage(1322, l1npc
								.get_name()));
						pc.sendPackets(new S_ChangeName(
								petTemplate.get_objid(), l1npc.get_name()));
						Broadcaster.broadcastPacket(pc, new S_ChangeName(
								petTemplate.get_objid(), l1npc.get_name()));
					} else {
						pc.sendPackets(new S_ServerMessage(1164));
					}
				} else if (itemId == 41260) { // ��
					for (L1Object object : L1World.getInstance()
							.getVisibleObjects(pc, 3)) {
						if (object instanceof L1EffectInstance) {
							if (((L1NpcInstance) object).getNpcTemplate()
									.get_npcId() == 81170) {
								pc.sendPackets(new S_ServerMessage(1162)); // ����
																			// ������
																			// ��ں���
																			// �ֽ��ϴ�.
								return;
							}
						}
					}
					int[] loc = new int[2];
					loc = CharPosUtil.getFrontLoc(pc.getX(), pc.getY(), pc
							.getMoveState().getHeading());
					L1EffectSpawn.getInstance().spawnEffect(81170, 600000,
							loc[0], loc[1], pc.getMapId());
					pc.getInventory().removeItem(useItem, 1);
				} else if (itemId == 41345) { // �꼺�� ����
					L1DamagePoison.doInfection(pc, pc, 3000, 5);
					pc.getInventory().removeItem(useItem, 1);
				} else if (itemId == 41315) { // ����
					if (pc.getSkillEffectTimerSet().hasSkillEffect(
							STATUS_HOLY_WATER_OF_EVA)) {
						pc.sendPackets(new S_ServerMessage(79)); // \f1 �ƹ��͵�
																	// �Ͼ��
																	// �ʾҽ��ϴ�.
						return;
					}
					if (pc.getSkillEffectTimerSet().hasSkillEffect(
							STATUS_HOLY_MITHRIL_POWDER)) {
						pc.getSkillEffectTimerSet().removeSkillEffect(
								STATUS_HOLY_MITHRIL_POWDER);
					}
					pc.getSkillEffectTimerSet().setSkillEffect(
							STATUS_HOLY_WATER, 900 * 1000);
					pc.sendPackets(new S_SkillSound(pc.getId(), 190));
					Broadcaster.broadcastPacket(pc, new S_SkillSound(
							pc.getId(), 190));
					pc.sendPackets(new S_ServerMessage(1141));
					pc.getInventory().removeItem(useItem, 1);
				} else if (itemId == 41316) { // �ż��� �̽������Ŀ��
					if (pc.getSkillEffectTimerSet().hasSkillEffect(
							STATUS_HOLY_WATER_OF_EVA)) {
						pc.sendPackets(new S_ServerMessage(79)); // \f1 �ƹ��͵�
																	// �Ͼ��
																	// �ʾҽ��ϴ�.
						return;
					}
					if (pc.getSkillEffectTimerSet().hasSkillEffect(
							STATUS_HOLY_WATER)) {
						pc.getSkillEffectTimerSet().removeSkillEffect(
								STATUS_HOLY_WATER);
					}
					pc.getSkillEffectTimerSet().setSkillEffect(
							STATUS_HOLY_MITHRIL_POWDER, 900 * 1000);
					pc.sendPackets(new S_SkillSound(pc.getId(), 190));
					Broadcaster.broadcastPacket(pc, new S_SkillSound(
							pc.getId(), 190));
					pc.sendPackets(new S_ServerMessage(1142));
					pc.getInventory().removeItem(useItem, 1);
				} else if (itemId == 41354) { // �ż��� ������ ��
					if (pc.getSkillEffectTimerSet().hasSkillEffect(
							STATUS_HOLY_WATER)
							|| pc.getSkillEffectTimerSet().hasSkillEffect(
									STATUS_HOLY_MITHRIL_POWDER)) {
						pc.sendPackets(new S_ServerMessage(79)); // \f1 �ƹ��͵�
																	// �Ͼ��
																	// �ʾҽ��ϴ�.
						return;
					}
					pc.getSkillEffectTimerSet().setSkillEffect(
							STATUS_HOLY_WATER_OF_EVA, 900 * 1000);
					pc.sendPackets(new S_SkillSound(pc.getId(), 190));
					Broadcaster.broadcastPacket(pc, new S_SkillSound(
							pc.getId(), 190));
					pc.sendPackets(new S_ServerMessage(1140));
					pc.getInventory().removeItem(useItem, 1);
				} else if (itemId == L1ItemId.CHANGING_SEX_POTION) { // ����ȯ ����
					int[] MALE_LIST = new int[] { 0, 61, 138, 734, 2786, 6658,
							6671 };
					int[] FEMALE_LIST = new int[] { 1, 48, 37, 1186, 2796,
							6661, 6650 };
					if (pc.get_sex() == 0) {
						pc.set_sex(1);
						pc.setClassId(FEMALE_LIST[pc.getType()]);
					} else {
						pc.set_sex(0);
						pc.setClassId(MALE_LIST[pc.getType()]);
					}
					pc.getGfxId().setTempCharGfx(pc.getClassId());
					pc.sendPackets(new S_ChangeShape(pc.getId(), pc
							.getClassId()));
					Broadcaster.broadcastPacket(pc,
							new S_ChangeShape(pc.getId(), pc.getClassId()));
					pc.getInventory().removeItem(useItem, 1);
					/************************** ��Ÿ�� ������ New System *****************************/
					/*
					 * } else if (itemId == L1ItemId.DRAGON_KEY){
					 * if(useItem.getEndTime().getTime() <
					 * System.currentTimeMillis()){
					 * pc.getInventory().removeItem(useItem); pc.sendPackets(new
					 * S_SystemMessage("��� �ð��� ���� ���� �մϴ�."));// ������ ���׸� ���
					 * return; } AntarasRaidSystem.getInstance().startRaid(pc);
					 */
				} else if (itemId == L1ItemId.DRAGON_JEWEL_BOX) {
					int[] DRAGONSCALE = new int[] { 40393, 40394, 40395, 40396 };
					int bonus = _random.nextInt(100) + 1;
					int rullet = _random.nextInt(100) + 1;
					L1ItemInstance bonusitem = null;
					pc.getInventory().storeItem(L1ItemId.DRAGON_DIAMOND, 1);
					pc.sendPackets(new S_ServerMessage(403, "$7969"));
					pc.getInventory().removeItem(useItem, 1);
					if (bonus <= 3) {
						bonusitem = pc.getInventory().storeItem(
								DRAGONSCALE[rullet % DRAGONSCALE.length], 1);
						pc.sendPackets(new S_ServerMessage(403, bonusitem
								.getItem().getNameId()));
					} else if (bonus >= 4 && bonus <= 8) {
						bonusitem = pc.getInventory().storeItem(
								L1ItemId.DRAGON_PEARL, 1);
						pc.sendPackets(new S_ServerMessage(403, bonusitem
								.getItem().getNameId()));
					} else if (bonus >= 9 && bonus <= 15) {
						bonusitem = pc.getInventory().storeItem(
								L1ItemId.DRAGON_SAPHIRE, 1);
						pc.sendPackets(new S_ServerMessage(403, bonusitem
								.getItem().getNameId()));
					} else if (bonus >= 16 && bonus <= 25) {
						bonusitem = pc.getInventory().storeItem(
								L1ItemId.DRAGON_RUBY, 1);
						pc.sendPackets(new S_ServerMessage(403, bonusitem
								.getItem().getNameId()));
					} else {
					}
				} else if (itemId == L1ItemId.DRAGON_EMERALD_BOX) {
					int[] DRAGONSCALE = new int[] { 40393, 40394, 40395, 40396 };
					int bonus = _random.nextInt(100) + 1;
					int rullet = _random.nextInt(100) + 1;
					L1ItemInstance bonusitem = null;
					pc.getInventory().storeItem(L1ItemId.DRAGON_EMERALD, 1);
					pc.sendPackets(new S_ServerMessage(403, "$11518"));
					pc.getInventory().removeItem(useItem, 1);
					if (bonus <= 3) {
						bonusitem = pc.getInventory().storeItem(
								DRAGONSCALE[rullet % DRAGONSCALE.length], 1);
						pc.sendPackets(new S_ServerMessage(403, bonusitem
								.getItem().getNameId()));
					} else if (bonus >= 4 && bonus <= 8) {
						bonusitem = pc.getInventory().storeItem(
								L1ItemId.DRAGON_PEARL, 1);
						pc.sendPackets(new S_ServerMessage(403, bonusitem
								.getItem().getNameId()));
					} else if (bonus >= 9 && bonus <= 15) {
						bonusitem = pc.getInventory().storeItem(
								L1ItemId.DRAGON_SAPHIRE, 1);
						pc.sendPackets(new S_ServerMessage(403, bonusitem
								.getItem().getNameId()));
					} else if (bonus >= 16 && bonus <= 25) {
						bonusitem = pc.getInventory().storeItem(
								L1ItemId.DRAGON_RUBY, 1);
						pc.sendPackets(new S_ServerMessage(403, bonusitem
								.getItem().getNameId()));
					} else {
					}
				} else if (itemId == 437010 || itemId == 437013
						|| itemId == 437012 || itemId == 437036) {
					if (pc.getLevel() < 49) {
						pc.sendPackets(new S_ServerMessage(318, "49"));
						return;
					}

					int hasad = 0;
					int skill = 0;
					int packet = 0;
					int msg = 0;

					if (itemId == L1ItemId.DRAGON_DIAMOND) {
						if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DRAGON_EMERALD_YES) == true) {
							pc.sendPackets(new S_ServerMessage(2146));
							return;
						}
						hasad = 1000000;
						skill = 7785;
						packet = 0x01;
						msg = 2142;
					} else if (itemId == L1ItemId.DRAGON_SAPHIRE) {
						if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DRAGON_EMERALD_YES) == true) {
							pc.sendPackets(new S_ServerMessage(2146));
							return;
						}
						hasad = 500000;
						skill = 7785;
						packet = 0x01;
						msg = 2142;
					} else if (itemId == L1ItemId.DRAGON_RUBY) {
						if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DRAGON_EMERALD_YES) == true) {
							pc.sendPackets(new S_ServerMessage(2146));
							return;
						}
						hasad = 300000;
						skill = 7785;
						packet = 0x01;
						msg = 2142;
					} else if (itemId == L1ItemId.DRAGON_EMERALD) {
						if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DRAGON_EMERALD_NO) == true) {
							pc.sendPackets(new S_ServerMessage(2145));
							return;
						} else if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DRAGON_EMERALD_YES) == true) {
							pc.sendPackets(new S_ServerMessage(2147));
							return;
						}
						hasad = 1000000;
						skill = 7786;
						packet = 0x02;
						msg = 2140;
					}
					pc.calAinHasad(hasad);
					pc.getSkillEffectTimerSet().setSkillEffect(skill, 10800 * 1000);
					pc.sendPackets(new S_PacketBox(S_PacketBox.AINHASAD, pc.getAinHasad()));
					pc.sendPackets(new S_PacketBox(S_PacketBox.EMERALD_EVA,	packet, 10800));
					pc.sendPackets(new S_ServerMessage(msg));
					pc.getInventory().removeItem(useItem, 1);
				} else if (itemId == L1ItemId.FORTUNE_COOKIE) {
					pc.getInventory().removeItem(useItem, 1);
					pc.getInventory().storeItem(437022, 1);
					pc.sendPackets(new S_ServerMessage(403, "$8540"));
					int chance = _random.nextInt(42);
					if (chance == 0) {
						pc.getInventory().storeItem(437027, 1);
					} else if (chance >= 1 && chance <= 10) {

						pc.getInventory().storeItem(437028, 1);
					} else if (chance >= 11 && chance <= 40) {
						pc.getInventory().storeItem(437029, 1);
					} else if (chance == 41) {
						pc.getInventory().storeItem(437030, 1);
					}
					pc.sendPackets(new S_ServerMessage(403, "$8539"));
				} else if (itemId == 440001) { // �Ⱘ �ð� ���� �ֹ��� �߰�
					int gTime = pc.getGdungeonTime() % 1000;
					int setTime = 0;
					int calgtime = gTime - 59;
					if (calgtime > 0) {
						setTime = pc.getGdungeonTime() - 59;
						pc.getInventory().removeItem(useItem, 1);
						pc.setGdungeonTime(setTime);
						pc.sendPackets(new S_SystemMessage(
								"������� �ð� ���� �ֹ���(1�ð�)��  ��� �Ͽ����ϴ�."));
					} else {
						pc.sendPackets(new S_SystemMessage(
								"���ð��� 1�ð� ���Ϸ� ��� �� �������ϴ�."));
					}
				} else if (itemId == 440002) {
					int gTime = pc.getGdungeonTime() % 1000;
					int setTime = 0;
					int calgtime = gTime - 119;
					if (calgtime > 0) {
						setTime = pc.getGdungeonTime() - 119;
						pc.getInventory().removeItem(useItem, 1);
						pc.setGdungeonTime(setTime);
						pc.sendPackets(new S_SystemMessage(
								"������� �ð� ���� �ֹ���(2�ð�)��  ��� �Ͽ����ϴ�."));
					} else {
						pc.sendPackets(new S_SystemMessage(
								"���ð��� 2�ð� ���Ϸ� ��� �� �������ϴ�."));
					}
				} else if (itemId == 440003) {
					int gTime = pc.getGdungeonTime() % 1000;
					int setTime = 0;
					int calgtime = gTime - 179;
					if (calgtime > 0) {
						setTime = pc.getGdungeonTime() - 179;
						pc.getInventory().removeItem(useItem, 1);
						pc.setGdungeonTime(setTime);
						pc.sendPackets(new S_SystemMessage(
								"������� �ð� ���� �ֹ���(3�ð�)��  ��� �Ͽ����ϴ�."));
					} else {
						pc.sendPackets(new S_SystemMessage(
								"���ð��� 3�ð� ���Ϸ� ��� �� �������ϴ�."));
					}
				} else {
					int locX = ((L1EtcItem) useItem.getItem()).get_locx();
					int locY = ((L1EtcItem) useItem.getItem()).get_locy();
					short mapId = ((L1EtcItem) useItem.getItem()).get_mapid();
					if (locX != 0 && locY != 0) {
						if (pc.getMap().isEscapable() || pc.isGm()) {
							L1Teleport.teleport(pc, locX, locY, mapId, pc
									.getMoveState().getHeading(), true);
							pc.getInventory().removeItem(useItem, 1);
						} else {
							pc.sendPackets(new S_ServerMessage(647));
						}
						pc.cancelAbsoluteBarrier();
					} else {
						if (useItem.getCount() < 1) {
							pc.sendPackets(new S_ServerMessage(329, useItem
									.getLogName()));
						} else {
							pc.sendPackets(new S_ServerMessage(74, useItem
									.getLogName()));
						}
					}
				}
			}
			L1ItemDelay.onItemUse(pc, useItem); // ������ ���� ����
		}
	}

	private void UseHeallingPotion(L1PcInstance pc, int healHp, int gfxid) {
		if (pc.getSkillEffectTimerSet().hasSkillEffect(71) == true) { // ���������� ����
			pc.sendPackets(new S_ServerMessage(698)); // ���¿� ���� �ƹ��͵� ���� ���� �����ϴ�.
			return;
		}

		// �ۼַ�Ʈ�������� ����
		pc.cancelAbsoluteBarrier();

		pc.sendPackets(new S_SkillSound(pc.getId(), gfxid));
		Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), gfxid));
		pc.sendPackets(new S_ServerMessage(77)); // \f1����� ���������ϴ�.
		healHp *= (_random.nextGaussian() / 5.0D) + 1.0D;
		if (pc.getSkillEffectTimerSet().hasSkillEffect(POLLUTE_WATER)) { // ����Ʈ��Ÿ���� ȸ����1/2��
			healHp /= 2;
		}
		pc.setCurrentHp(pc.getCurrentHp() + healHp);
	}

	// õ���� ����
	private void UseExpPotion(L1PcInstance pc , int item_id) {
		if (pc.getSkillEffectTimerSet().hasSkillEffect(71) == true) { // ���������� ����
			pc.sendPackets(new S_ServerMessage(698, "")); // ���¿� ���� �ƹ��͵� ���� ���� �����ϴ�.
			return;
		}
		pc.cancelAbsoluteBarrier();

		int time = 0;
		if (item_id == L1ItemId.EXP_POTION
				|| item_id == L1ItemId.EXP_POTION2) { // ����ġ ��� ����
			time = 1800;
		}

		pc.getSkillEffectTimerSet().setSkillEffect(EXP_POTION, time * 1000);
		pc.sendPackets(new S_SkillSound(pc.getId(), 7013));
		Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId() , 7013));
		pc.sendPackets(new S_ServerMessage(1313));
	}

	private void useCashScroll(L1PcInstance pc, int item_id) {
		int time = 3600;
		int scroll = 0;

		if (pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_CASHSCROLL)){
			pc.getSkillEffectTimerSet().killSkillEffectTimer(STATUS_CASHSCROLL);
			pc.addHpr(-4);
			pc.addMaxHp(-50);
			pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
			if (pc.isInParty()) {
				pc.getParty().updateMiniHP(pc);
			}
			pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
		}
		if (pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_CASHSCROLL2)){
			pc.getSkillEffectTimerSet().killSkillEffectTimer(STATUS_CASHSCROLL2);
			pc.addMpr(-4);
			pc.addMaxMp(-40);
			pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
		}
		if (pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_CASHSCROLL3)){
			pc.getSkillEffectTimerSet().killSkillEffectTimer(STATUS_CASHSCROLL3);
			pc.addDmgup(-3);
			pc.addHitup(-3);
			pc.getAbility().addSp(-3);
			pc.sendPackets(new S_SPMR(pc));
		}

		if (item_id == L1ItemId.INCRESE_HP_SCROLL || item_id == L1ItemId.CHUNSANG_HP_SCROLL) {
			scroll = 6993;
			pc.addHpr(4);
			pc.addMaxHp(50);
			pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
			if (pc.isInParty()) {
				pc.getParty().updateMiniHP(pc);
			}
			pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
		} else if (item_id == L1ItemId.INCRESE_MP_SCROLL || item_id == L1ItemId.CHUNSANG_MP_SCROLL) {
			scroll = 6994;
			pc.addMpr(4);
			pc.addMaxMp(40);
			pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
		} else if (item_id == L1ItemId.INCRESE_ATTACK_SCROLL || item_id == L1ItemId.CHUNSANG_ATTACK_SCROLL) {
			scroll = 6995;
			pc.addDmgup(3);
			pc.addHitup(3);
			pc.getAbility().addSp(3);
			pc.sendPackets(new S_SPMR(pc));
		}

		pc.sendPackets(new S_SkillSound(pc.getId(), scroll));
		Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), scroll));
		pc.getSkillEffectTimerSet().setSkillEffect(scroll, time * 1000);
	}

	private boolean createNewItem(L1PcInstance pc, int item_id, int count) {
		L1ItemInstance item = ItemTable.getInstance().createItem(item_id);
		item.setCount(count);
		if (item != null) {
			if (pc.getInventory().checkAddItem(item, count) == L1Inventory.OK) {
				pc.getInventory().storeItem(item);
			} else { // ���� ��  ���� ���� ���鿡 ����߸��� ó���� ĵ���� ���� �ʴ´�(���� ����)
				L1World.getInstance().getInventory(pc.getX(), pc.getY(),
						pc.getMapId()).storeItem(item);
			}
			pc.sendPackets(new S_ServerMessage(403, item.getLogName())); // %0�� �տ� �־����ϴ�.
			return true;
		} else {
			return false;
		}
	}

	private void useToiTeleportAmulet(L1PcInstance pc, int itemId, L1ItemInstance item) {
		boolean isTeleport = false;
		if (itemId == 40289 || itemId == 40293) { // 11,51Famulet
			if (pc.getX() >= 32816 && pc.getX() <= 32821 && pc.getY() >= 32778
					&& pc.getY() <= 32783 && pc.getMapId() == 101) {
				isTeleport = true;
			}
		} else if (itemId == 40290 || itemId == 40294) { // 21,61Famulet
			if (pc.getX() >= 32815 && pc.getX() <= 32820 && pc.getY() >= 32815
					&& pc.getY() <= 32820 && pc.getMapId() == 101) {
				isTeleport = true;
			}
		} else if (itemId == 40291 || itemId == 40295) { // 31,71Famulet
			if (pc.getX() >= 32779 && pc.getX() <= 32784 && pc.getY() >= 32778
					&& pc.getY() <= 32783 && pc.getMapId() == 101) {
				isTeleport = true;
			}
		} else if (itemId == 40292 || itemId == 40296) { // 41,81Famulet
			if (pc.getX() >= 32779 && pc.getX() <= 32784 && pc.getY() >= 32815
					&& pc.getY() <= 32820 && pc.getMapId() == 101) {
				isTeleport = true;
			}
		} else if (itemId == 40297) { // 91Famulet
			if (pc.getX() >= 32706 && pc.getX() <= 32710 && pc.getY() >= 32909
					&& pc.getY() <= 32913 && pc.getMapId() == 190) {
				isTeleport = true;
			}
		}

		if (isTeleport) {
			L1Teleport.teleport(pc, item.getItem().get_locx(), item.getItem()
					.get_locy(), item.getItem().get_mapid(), 5, true);
		} else {
			pc.sendPackets(new S_ServerMessage(79)); // \f1 �ƹ��͵� �Ͼ�� �ʾҽ��ϴ�.
		}
	}

	private boolean withdrawPet(L1PcInstance pc, int itemObjectId) {
		if (!pc.getMap().isTakePets()) {
			pc.sendPackets(new S_ServerMessage(563)); // \f1 ���⿡���� ����� �� �����ϴ�.
			return false;
		}

		int petCost = 0;
		Object[] petList = pc.getPetList().values().toArray();
		for (Object pet : petList) {
			if (pet instanceof L1PetInstance) {
				if (((L1PetInstance) pet).getItemObjId() == itemObjectId) { // �̹� ������ �ִ� �ֿϵ���
					return false;
				}
			}
			petCost += ((L1NpcInstance) pet).getPetcost();
		}
		int charisma = pc.getAbility().getTotalCha();
		if (pc.isCrown()) { // CROWN
			charisma += 6;
		} else if (pc.isElf()) { // ELF
			charisma += 12;
		} else if (pc.isWizard()) { // WIZ
			charisma += 6;
		} else if (pc.isDarkelf()) { // DE
			charisma += 6;
		} else if (pc.isDragonknight()) { // ����
			charisma += 6;
		} else if (pc.isIllusionist()) { // ȯ����
			charisma += 6;
		}

		charisma -= petCost;
		int petCount = charisma / 6;
		if (petCount <= 0) {
			pc.sendPackets(new S_ServerMessage(489)); // ���������� �ϴ� �ֿϵ����� �ʹ� �����ϴ�.
			return false;
		}

		L1Pet l1pet = PetTable.getInstance().getTemplate(itemObjectId);
		if (l1pet != null) {
			L1Npc npcTemp = NpcTable.getInstance().getTemplate(l1pet.get_npcid());
			L1PetInstance pet = new L1PetInstance(npcTemp, pc, l1pet);
			pet.setPetcost(6);
			pet.getSkillEffectTimerSet().setSkillEffect(L1SkillId.STATUS_PET_FOOD, pet.getFoodTime()*1000);
		}
		return true;
	}

	private void IdentMapWand(L1PcInstance pc, int locX, int locY){
        pc.sendPackets(new S_SystemMessage("Gab :" + pc.getMap().getOriginalTile(locX, locY) + ",x :" + locX + ",y :"
                + locY + ", mapId :" + pc.getMapId()));
        if (pc.getMap().isCloseZone(locX, locY)){
            pc.sendPackets(new S_EffectLocation(locX, locY, 10));
            Broadcaster.broadcastPacket(pc , new S_EffectLocation(locX, locY, 10));
            pc.sendPackets(new S_SystemMessage("������ �ν���"));
        }
    }

    private void MapFixKeyWand(L1PcInstance pc, int locX, int locY){
        String key = new StringBuilder().append(pc.getMapId()).append(locX).append(locY).toString();
        if (!pc.getMap().isCloseZone(locX, locY)){
            if (!MapFixKeyTable.getInstance().isLockey(key)){
                MapFixKeyTable.getInstance().storeLocFix(locX, locY, pc.getMapId());
                pc.sendPackets(new S_EffectLocation(locX, locY, 1815));
                Broadcaster.broadcastPacket(pc , new S_EffectLocation(locX, locY, 1815));
                pc.sendPackets(new S_SystemMessage("key�߰� ,x :" + locX + ",y :" + locY + ", mapId :" + pc.getMapId()));
            }
        }else{
            pc.sendPackets(new S_SystemMessage("������ǥ�� ���� �ƴմϴ�."));

            if (MapFixKeyTable.getInstance().isLockey(key)){
                MapFixKeyTable.getInstance().deleteLocFix(locX, locY, pc.getMapId());
                pc.sendPackets(new S_EffectLocation(locX, locY, 10));
                Broadcaster.broadcastPacket(pc , new S_EffectLocation(locX, locY, 10));
                pc.sendPackets(new S_SystemMessage("key���� ,x :" + locX + ",y :" + locY + ", mapId :" + pc.getMapId()));
            }
        }
    }

	@Override
	public String getType() {
		return C_ITEM_USE;
	}
}