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

package l1j.server.server.model.Instance;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import l1j.server.GameSystem.CrockSystem;
import l1j.server.server.clientpackets.ClientBasePacket;
import l1j.server.server.datatables.NpcTable;
import l1j.server.server.datatables.PetTable;
import l1j.server.server.model.L1Character;
import l1j.server.server.model.L1EquipmentTimer;
import l1j.server.server.model.L1ItemOwnerTimer;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1PcInventory;
import l1j.server.server.model.item.L1ItemId;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.serverpackets.S_OwnCharStatus;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.templates.L1Armor;
import l1j.server.server.templates.L1Item;
import l1j.server.server.templates.L1Npc;
import l1j.server.server.templates.L1Pet;
import l1j.server.server.utils.BinaryOutputStream;

//Referenced classes of package l1j.server.server.model:
//L1Object, L1PcInstance

public class L1ItemInstance extends L1Object {

	private static final long serialVersionUID = 1L; 
	private static final SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm", Locale.KOREA);
	
	private int _count;
	private int _itemId;
	private L1Item _item;
	private boolean _isEquipped = false;
	private int _enchantLevel;
	private int _attrenchantLevel;
	private boolean _isIdentified = false;
	private int _durability;
	private int _chargeCount;
	private int _remainingTime;
	private Timestamp _lastUsed = null;
	private int bless;
	private int _lastWeight;
	private final LastStatus _lastStatus = new LastStatus();
	private L1PcInstance _pc;
	private boolean _isRunning = false;
	private EnchantTimer _timer;
	private Timestamp _buyTime = null;
	private Timestamp _endTime = null;

	public L1ItemInstance() {
		_count = 1;
		_enchantLevel = 0;
	}

	public L1ItemInstance(L1Item item, int count) {
		this();
		setItem(item);
		setCount(count);
	}

	public L1ItemInstance(L1Item item) {
		this(item, 1);
	}
	
	public void clickItem(L1Character cha, ClientBasePacket packet){
	}

	public boolean isIdentified() {	return _isIdentified;	}
	public void setIdentified(boolean identified) {	_isIdentified = identified;	}

	public String getName() {	return _item.getName();	}

	public int getCount() {	return _count;	}
	public void setCount(int count) {	_count = count;	}

	public boolean isEquipped() {	return _isEquipped;	}
	public void setEquipped(boolean equipped) {	_isEquipped = equipped;	}

	public L1Item getItem() {	return _item;	}
	public void setItem(L1Item item) {
		_item = item;
		_itemId = item.getItemId();
	}

	public int getItemId() {	return _itemId;	}
	public void setItemId(int itemId) {	_itemId = itemId;	}

	public boolean isStackable() {	return _item.isStackable();	}

	@Override
	public void onAction(L1PcInstance player) {	}

	public int getEnchantLevel() {	return _enchantLevel;	}
	public void setEnchantLevel(int enchantLevel) {	_enchantLevel = enchantLevel;	}

	public int getAttrEnchantLevel() {	return _attrenchantLevel;	}
	public void setAttrEnchantLevel(int attrenchantLevel) {	_attrenchantLevel = attrenchantLevel;	}

	public int get_gfxid() {	return _item.getGfxId();	}

	public int get_durability() {	return _durability;	}

	public int getChargeCount() {	return _chargeCount;	}
	public void setChargeCount(int i) {	_chargeCount = i;	}

	public int getRemainingTime() {	return _remainingTime;	}
	public void setRemainingTime(int i) {	_remainingTime = i;	}

	public void setLastUsed(Timestamp t) {	_lastUsed = t;	}
	public Timestamp getLastUsed() {	return _lastUsed;	}

	public int getBless() {	return bless;	}
	public void setBless(int i) {	bless = i;	}

	public int getLastWeight() {	return _lastWeight;	}
	public void setLastWeight(int weight) {	_lastWeight = weight;	}

	public Timestamp getBuyTime() { return _buyTime; }
	public void setBuyTime(Timestamp t) { _buyTime = t; }
	
	public Timestamp getEndTime() { return _endTime; }
	public void setEndTime(Timestamp t) { _endTime = t; } 
	
	public int getMr() {
		int mr = _item.get_mdef();
		int itemid = getItemId();
		if (itemid == 20011 || itemid == 20110 || itemid == 120011
				|| itemid == 420108 || itemid == 420109 || itemid == 420110	|| itemid == 420111
				|| itemid == 425108) {
			mr += getEnchantLevel();
		}
		if (itemid == 20056 || itemid == 120056 || itemid == 220056) {
			mr += getEnchantLevel() * 2;
		}
		if (itemid == 20078) {
			mr += getEnchantLevel() * 3;
		}
		return mr;
	}

	public void set_durability(int i) {
		if (i < 0) {
			i = 0;
		}

		if (i > 127) {
			i = 127;
		}
		_durability = i;
	}

	public int getWeight() {
		if (getItem().getWeight() == 0) {
			return 0;
		} else {
			return Math.max(getCount() * getItem().getWeight() / 1000, 1);
		}
	}


	public class LastStatus {
		public int count;
		public int itemId;
		public boolean isEquipped = false;
		public int enchantLevel;
		public boolean isIdentified = true;
		public int durability;
		public int chargeCount;
		public int remainingTime;
		public Timestamp lastUsed = null;
		public int bless;
		public int attrenchantLevel;

		public void updateAll() {
			count = getCount();
			itemId = getItemId();
			isEquipped = isEquipped();
			isIdentified = isIdentified();
			enchantLevel = getEnchantLevel();
			durability = get_durability();
			chargeCount = getChargeCount();
			remainingTime = getRemainingTime();
			lastUsed = getLastUsed();
			bless = getBless();
			attrenchantLevel = getAttrEnchantLevel();
		}

		public void updateCount() {	count = getCount();	}

		public void updateItemId() {	itemId = getItemId();	}

		public void updateEquipped() {	isEquipped = isEquipped();	}

		public void updateIdentified() {	isIdentified = isIdentified();	}

		public void updateEnchantLevel() {	enchantLevel = getEnchantLevel();	}

		public void updateDuraility() {	durability = get_durability();	}

		public void updateChargeCount() {	chargeCount = getChargeCount();	}

		public void updateRemainingTime() {	remainingTime = getRemainingTime();	}

		public void updateLastUsed() {	lastUsed = getLastUsed();	}

		public void updateBless() {	bless = getBless();	}

		public void updateAttrEnchantLevel() {	attrenchantLevel = getAttrEnchantLevel();	}
	}

	public LastStatus getLastStatus() {
		return _lastStatus;
	}

	public int getRecordingColumns() {
		int column = 0;

		if (getCount() != _lastStatus.count) {
			column += L1PcInventory.COL_COUNT;
		}
		if (getItemId() != _lastStatus.itemId) {
			column += L1PcInventory.COL_ITEMID;
		}
		if (isEquipped() != _lastStatus.isEquipped) {
			column += L1PcInventory.COL_EQUIPPED;
		}
		if (getEnchantLevel() != _lastStatus.enchantLevel) {
			column += L1PcInventory.COL_ENCHANTLVL;
		}
		if (get_durability() != _lastStatus.durability) {
			column += L1PcInventory.COL_DURABILITY;
		}
		if (getChargeCount() != _lastStatus.chargeCount) {
			column += L1PcInventory.COL_CHARGE_COUNT;
		}
		if (getLastUsed() != _lastStatus.lastUsed) {
			column += L1PcInventory.COL_DELAY_EFFECT;
		}
		if (isIdentified() != _lastStatus.isIdentified) {
			column += L1PcInventory.COL_IS_ID;
		}
		if (getRemainingTime() != _lastStatus.remainingTime) {
			column += L1PcInventory.COL_REMAINING_TIME;
		}
		if (getBless() != _lastStatus.bless) {
			column += L1PcInventory.COL_BLESS;
		}
		if (getAttrEnchantLevel() != _lastStatus.attrenchantLevel) {
			column += L1PcInventory.COL_ATTRENCHANTLVL;
		}
		return column;
	}

	public String getNumberedViewName(int count) {
		StringBuilder name = new StringBuilder(getNumberedName(count));
		int itemType2 = getItem().getType2();
		int itemId = getItem().getItemId();

		if (itemId == 40314 || itemId == 40316) { 
			L1Pet pet = PetTable.getInstance().getTemplate(getId());
			if (pet != null) {
				L1Npc npc = NpcTable.getInstance().getTemplate(pet.get_npcid());
//				name.append("[Lv." + pet.get_level() + " "
//				+ npc.get_nameid() + "]");
				name.append("[Lv." + pet.get_level() + " " + pet.get_name() + "]HP" + pet.get_hp() + " " + npc.get_nameid());
			}
		}

		if (getItem().getType2() == 0 && getItem().getType() == 2) { // light
			if (isNowLighting()) {
				name.append(" ($10)");
			}
			if (itemId == 40001 || itemId == 40002) {
				if (getRemainingTime() <= 0) {
					name.append(" ($11)");
				}
			}
		}

		if (getItem().getItemId() == L1ItemId.TEBEOSIRIS_KEY || getItem().getItemId() == L1ItemId.TIKAL_KEY) {
			name.append(" [" + CrockSystem.getInstance().OpenTime() + "]");
		}

		if (getItem().getItemId() == L1ItemId.DRAGON_KEY) {// 드래곤 키 
			name.append(" [" + sdf.format(getEndTime().getTime()) + "]");
		}
		
		if (getItem().getItemId() == 40309) {
			name.append(" " + getRoundId() + "-" + (getTicketId() + 1));
		}
		
		if (isEquipped()) {
			if (itemType2 == 1) {
				name.append(" ($9)"); 
			} else if (itemType2 == 2 && !getItem().isUseHighPet()) {
				name.append(" ($117)"); 
			}
		}
		return name.toString();
	}

	public String getViewName() {
		return getNumberedViewName(_count);
	}

	public String getLogName() {
		return getNumberedName(_count);
	}

	public String getNumberedName(int count) {
		StringBuilder name = new StringBuilder();

		if (isIdentified()) {
			if (getItem().getType2() == 1 || getItem().getType2() == 2) {
				switch(getAttrEnchantLevel()){
				case 1: name.append("$6115 "); break;
				case 2: name.append("$6116 "); break;
				case 3: name.append("$6117 "); break;
				case 4: name.append("$6118 "); break;
				case 5: name.append("$6119 "); break;
				case 6: name.append("$6120 "); break;
				case 7: name.append("$6121 "); break;
				case 8: name.append("$6122 "); break;
				case 9: name.append("$6123 "); break;
				case 10: name.append("$6124 "); break;
				case 11: name.append("$6125 "); break;
				case 12: name.append("$6126 "); break;
				default: break;
				}
				if (getEnchantLevel() >= 0) {
					name.append("+" + getEnchantLevel() + " ");
				} else if (getEnchantLevel() < 0) {
					name.append(String.valueOf(getEnchantLevel()) + " ");
				}
			}
		}
		name.append(_item.getNameId());
		if (isIdentified()) {
			if (getItem().getMaxChargeCount() > 0) {
				name.append(" (" + getChargeCount() + ")");
			}
			if (getItem().getItemId() == 20383) {
				name.append(" (" + getChargeCount() + ")");
			}
			if (getItem().getMaxUseTime() > 0 && getItem().getType2() != 0) {
				name.append(" [" + getRemainingTime() + "]");
			}
		}

		if (count > 1) {
			name.append(" (" + count + ")");
		}

		return name.toString();
	}

	public byte[] getStatusBytes() {
		int itemType2 = getItem().getType2();
		int itemId = getItemId();
		BinaryOutputStream os = new BinaryOutputStream();

		if (itemType2 == 0) { // etcitem
			switch (getItem().getType()) {
			case 2: // light
				os.writeC(22); 
				os.writeH(getItem().getLightRange());
				break;
			case 7: // food
				os.writeC(21);
				os.writeH(getItem().getFoodVolume());
				break;
			case 0: // arrow
			case 15: // sting
				os.writeC(1); 
				os.writeC(getItem().getDmgSmall());
				os.writeC(getItem().getDmgLarge());
				break;
			default:
				os.writeC(23);
			break;
			}
			os.writeC(getItem().getMaterial());
			os.writeD(getWeight());

		} else if (itemType2 == 1 || itemType2 == 2) { // weapon | armor
			if (itemType2 == 1) { // weapon
				os.writeC(1);
				os.writeC(getItem().getDmgSmall());
				os.writeC(getItem().getDmgLarge());
				os.writeC(getItem().getMaterial());
				os.writeD(getWeight());
			} else if (itemType2 == 2) { // armor
				// AC
				os.writeC(19);
				int ac = ((L1Armor) getItem()).get_ac();
				if (ac < 0) ac = ac - ac - ac;
				os.writeC(ac);
				os.writeC(getItem().getMaterial());
				os.writeC(getItem().getGrade());
				os.writeD(getWeight());
			}
			if (getEnchantLevel() != 0
					&& !(itemType2 == 2 && getItem().getGrade() >= 0)) {
				os.writeC(2);
				os.writeC(getEnchantLevel());
			}
			if (get_durability() != 0) {
				os.writeC(3);
				os.writeC(get_durability());
			}
			if (getItem().isTwohandedWeapon()) {
				os.writeC(4);
			}
			// 공격 성공
			if (getItem().getHitModifier() != 0) {
				os.writeC(5);
				os.writeC(getItem().getHitModifier());
			}
			// 추가 타격
			if (getItem().getDmgModifier() != 0) {
				os.writeC(6);
				os.writeC(getItem().getDmgModifier());
			}
			if (getItem().getHitup() != 0) {
				os.writeC(5);
				os.writeC(getItem().getHitup());
			}
			if (getItem().getDmgup() != 0) {
				os.writeC(6);
				os.writeC(getItem().getDmgup());
			}
			if (getItem().getBowHitup() != 0) {
				os.writeC(24);
				os.writeC(getItem().getBowHitup());				
			}

			if (getItem().getBowDmgup() != 0) {
				os.writeC(35);
				os.writeC(getItem().getBowDmgup());
			}			
			if (itemId == 126 || itemId == 127) { 
				os.writeC(16);
			}
			if (itemId == 412001){
				os.writeC(34);
			}
			// STR~CHA
			if (getItem().get_addstr() != 0) {
				os.writeC(8);
				os.writeC(getItem().get_addstr());
			}
			if (getItem().get_adddex() != 0) {
				os.writeC(9);
				os.writeC(getItem().get_adddex());
			}
			if (getItem().get_addcon() != 0) {
				os.writeC(10);
				os.writeC(getItem().get_addcon());
			}
			if (getItem().get_addwis() != 0) {
				os.writeC(11);
				os.writeC(getItem().get_addwis());
			}
			if (getItem().get_addint() != 0) {
				os.writeC(12);
				os.writeC(getItem().get_addint());
			}
			if (getItem().get_addcha() != 0) {
				os.writeC(13);
				os.writeC(getItem().get_addcha());
			}
			// HP, MP
			if (getItem().get_addhp() != 0) {
				os.writeC(14);
				os.writeH(getItem().get_addhp());
			}
			if (getItem().get_addmp() != 0) {
				os.writeC(32);
				os.writeC(getItem().get_addmp());
			}
			// MR
			if (getMr() != 0) {
				os.writeC(15);
				os.writeH(getMr());
			}
			// SP
			if (getItem().get_addsp() != 0) {
				os.writeC(17);
				os.writeC(getItem().get_addsp());
			}
			if (getItem().isHasteItem()) {
				os.writeC(18);
			}

			int bit = 0;
			bit |= getItem().isUseRoyal()   ? 1 : 0;
			bit |= getItem().isUseKnight()  ? 2 : 0;
			bit |= getItem().isUseElf()     ? 4 : 0;
			bit |= getItem().isUseMage()    ? 8 : 0;
			bit |= getItem().isUseDarkelf() ? 16 : 0;
			bit |= getItem().isUseDragonKnight() ? 32 : 0;
			bit |= getItem().isUseBlackwizard() ? 64 : 0;
			bit |= getItem().isUseHighPet() ? 128 : 0;
			os.writeC(7);
			os.writeC(bit);

			if (getItem().get_defense_fire() != 0) {
				os.writeC(27);
				os.writeC(getItem().get_defense_fire());
			}
			if (getItem().get_defense_water() != 0) {
				os.writeC(28);
				os.writeC(getItem().get_defense_water());
			}
			if (getItem().get_defense_wind() != 0) {
				os.writeC(29);
				os.writeC(getItem().get_defense_wind());
			}
			if (getItem().get_defense_earth() != 0) {
				os.writeC(30);
				os.writeC(getItem().get_defense_earth());
			}
			
			if (getItem().get_regist_freeze() != 0) {
				os.writeC(15);
				os.writeH(getItem().get_regist_freeze());
				os.writeC(33);
				os.writeC(1);
			}
			if (getItem().get_regist_stone() != 0) {
				os.writeC(15);
				os.writeH(getItem().get_regist_stone());
				os.writeC(33);
				os.writeC(2);
			}
			if (getItem().get_regist_sleep() != 0) {
				os.writeC(15);
				os.writeH(getItem().get_regist_sleep());
				os.writeC(33);
				os.writeC(3);
			}
			if (getItem().get_regist_blind() != 0) {
				os.writeC(15);
				os.writeH(getItem().get_regist_blind());
				os.writeC(33);
				os.writeC(4);
			}
			if (getItem().get_regist_stun() != 0) {
				os.writeC(15);
				os.writeH(getItem().get_regist_stun());
				os.writeC(33);
				os.writeC(5);
			}
			if (getItem().get_regist_sustain() != 0) {
				os.writeC(15);
				os.writeH(getItem().get_regist_sustain());
				os.writeC(33);
				os.writeC(6);
			}
//			if (getItem.getLuck() != 0) {
//			os.writeC(20);
//			os.writeC(val);
//			}
//			if (getItem.getDesc() != 0) {
//			os.writeC(25);
//			os.writeH(val); // desc.tbl ID
//			}
//			if (getItem.getLevel() != 0) {
//			os.writeC(26);
//			os.writeH(val);
//			}
		}
		return os.getBytes();
	}

	class EnchantTimer extends TimerTask {

		public EnchantTimer() {
		}

		@Override
		public void run() {
			try {
				int type = getItem().getType();
				int type2 = getItem().getType2();
				int itemId = getItem().getItemId();
				if (_pc != null && _pc.getInventory().checkItem(itemId)) {
					if (type == 2 && type2 == 2 && isEquipped()) {
						_pc.getAC().addAc(3);
						_pc.sendPackets(new S_OwnCharStatus(_pc));
					}
				}
				setAcByMagic(0);
				setDmgByMagic(0);
				setHolyDmgByMagic(0);
				setHitByMagic(0);
				_pc.sendPackets(new S_ServerMessage(308, getLogName()));
				_isRunning = false;
				_timer = null;
			} catch (Exception e) {
			}
		}
	}

	private int _acByMagic = 0;
	private int _hitByMagic = 0;
	private int _holyDmgByMagic = 0;
	private int _dmgByMagic = 0;

	public int getAcByMagic() {	return _acByMagic;	}
	public void setAcByMagic(int i) {	_acByMagic = i;	}

	public int getDmgByMagic() {	return _dmgByMagic;	}
	public void setDmgByMagic(int i) {	_dmgByMagic = i;	}

	public int getHolyDmgByMagic() {	return _holyDmgByMagic;	}
	public void setHolyDmgByMagic(int i) {	_holyDmgByMagic = i;	}

	public int getHitByMagic() {	return _hitByMagic;	}
	public void setHitByMagic(int i) {	_hitByMagic = i;	}

	public void setSkillArmorEnchant(L1PcInstance pc, int skillId, int skillTime) {
		int type = getItem().getType();
		int type2 = getItem().getType2();
		if (_isRunning) {
			_timer.cancel();
			int itemId = getItem().getItemId();
			if (pc != null && pc.getInventory().checkItem(itemId)) {
				if (type == 2 && type2 == 2 && isEquipped()) {
					pc.getAC().addAc(3);
					pc.sendPackets(new S_OwnCharStatus(pc));
				}
			}
			setAcByMagic(0);
			_isRunning = false;
			_timer = null;
		}

		if (type == 2 && type2 == 2 && isEquipped()) {
			pc.getAC().addAc(-3);
			pc.sendPackets(new S_OwnCharStatus(pc));
		}
		setAcByMagic(3);
		_pc = pc;
		_timer = new EnchantTimer();
		(new Timer()).schedule(_timer, skillTime);
		_isRunning = true;
	}

	public void setSkillWeaponEnchant(L1PcInstance pc, int skillId, int skillTime) {
		if (getItem().getType2() != 1) {
			return;
		}
		if (_isRunning) {
			_timer.cancel();
			setDmgByMagic(0);
			setHolyDmgByMagic(0);
			setHitByMagic(0);
			_isRunning = false;
			_timer = null;
		}

		switch(skillId) {
		case L1SkillId.HOLY_WEAPON:
			setHolyDmgByMagic(1);
			setHitByMagic(1);
			break;

		case L1SkillId.ENCHANT_WEAPON:
			setDmgByMagic(2);
			break;

		case L1SkillId.BLESS_WEAPON:
			setDmgByMagic(2);
			setHitByMagic(2);
			break;

		case L1SkillId.SHADOW_FANG:
			setDmgByMagic(5);
			break;

		default:
			break;
		}

		_pc = pc;
		_timer = new EnchantTimer();
		(new Timer()).schedule(_timer, skillTime);
		_isRunning = true;
	}
	
	public void startItemOwnerTimer(L1PcInstance pc) {
		setItemOwner(pc);
		L1ItemOwnerTimer timer = new L1ItemOwnerTimer(this, 10000);
		timer.begin();
	}
	private L1EquipmentTimer _equipmentTimer;

	public void startEquipmentTimer(L1PcInstance pc) {
		if (getRemainingTime() > 0) {
			_equipmentTimer = new L1EquipmentTimer(pc, this);
			Timer timer = new Timer(true);
			timer.scheduleAtFixedRate(_equipmentTimer, 1000, 1000);
		}
	}

	public void stopEquipmentTimer() {
		if (getRemainingTime() > 0) {
			_equipmentTimer.cancel();
			_equipmentTimer = null;
		}
	}
	private L1PcInstance _itemOwner;

	public L1PcInstance getItemOwner() {	return _itemOwner;	}
	public void setItemOwner(L1PcInstance pc) {	_itemOwner = pc;	}

	private boolean _isNowLighting = false;

	public boolean isNowLighting() {	return _isNowLighting;	}
	public void setNowLighting(boolean flag) {	_isNowLighting = flag;	}
	private int _secondId;

	public int getSecondId() {
		return _secondId;
	}

	public void setSecondId(int i) {
		_secondId = i;
	}

	private int _roundId;

	public int getRoundId() {
		return _roundId;
	}

	public void setRoundId(int i) {
		_roundId = i;
	}

	private int _ticketId = -1; // 티겟 번호

	public int getTicketId() {
		return _ticketId;
	}

	public void setTicketId(int i) {
		_ticketId = i;
	} 
	private int _DropMobId = 0;

	public int isDropMobId() {	return _DropMobId;	}
	public void setDropMobId(int i) {	_DropMobId = i;	}
	
	private boolean _isWorking = false;

	public boolean isWorking() {	return _isWorking;	}
	public void setWorking(boolean flag) {	_isWorking = flag;	}
	
	//아이템을 분당체크해서 삭제하기 위해서 추가!!
	private int _deleteItemTime = 0;
	public int get_DeleteItemTime(){
		return _deleteItemTime;
	}
	public void add_DeleteItemTime(){
		_deleteItemTime++;
	}
	public void init_DeleteItemTime(){
		_deleteItemTime = 0;
	}
}
