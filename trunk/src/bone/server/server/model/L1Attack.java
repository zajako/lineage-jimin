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
package bone.server.server.model;

import static bone.server.server.model.skill.L1SkillId.ABSOLUTE_BARRIER;
import static bone.server.server.model.skill.L1SkillId.BERSERKERS;
import static bone.server.server.model.skill.L1SkillId.BOUNCE_ATTACK;
import static bone.server.server.model.skill.L1SkillId.BURNING_SLASH;
import static bone.server.server.model.skill.L1SkillId.BURNING_SPIRIT;
import static bone.server.server.model.skill.L1SkillId.BURNING_WEAPON;
import static bone.server.server.model.skill.L1SkillId.DOUBLE_BRAKE;
import static bone.server.server.model.skill.L1SkillId.DRAGON_SKIN;
import static bone.server.server.model.skill.L1SkillId.EARTH_BIND;
import static bone.server.server.model.skill.L1SkillId.ELEMENTAL_FIRE;
import static bone.server.server.model.skill.L1SkillId.ENCHANT_VENOM;
import static bone.server.server.model.skill.L1SkillId.FEAR;
import static bone.server.server.model.skill.L1SkillId.FEATHER_BUFF_A;
import static bone.server.server.model.skill.L1SkillId.FEATHER_BUFF_B;
import static bone.server.server.model.skill.L1SkillId.FIRE_BLESS;
import static bone.server.server.model.skill.L1SkillId.FIRE_WEAPON;
import static bone.server.server.model.skill.L1SkillId.FREEZING_BLIZZARD;
import static bone.server.server.model.skill.L1SkillId.FREEZING_BREATH;
import static bone.server.server.model.skill.L1SkillId.ICE_LANCE;
import static bone.server.server.model.skill.L1SkillId.IMMUNE_TO_HARM;
import static bone.server.server.model.skill.L1SkillId.IllUSION_AVATAR;
import static bone.server.server.model.skill.L1SkillId.MIRROR_IMAGE;
import static bone.server.server.model.skill.L1SkillId.MOB_BASILL;
import static bone.server.server.model.skill.L1SkillId.MOB_COCA;
import static bone.server.server.model.skill.L1SkillId.PATIENCE;
import static bone.server.server.model.skill.L1SkillId.REDUCTION_ARMOR;
import static bone.server.server.model.skill.L1SkillId.SOUL_OF_FLAME;
import static bone.server.server.model.skill.L1SkillId.SPECIAL_COOKING;
import static bone.server.server.model.skill.L1SkillId.STATUS_CURSE_BARLOG;
import static bone.server.server.model.skill.L1SkillId.STATUS_CURSE_YAHEE;
import static bone.server.server.model.skill.L1SkillId.STATUS_HOLY_MITHRIL_POWDER;
import static bone.server.server.model.skill.L1SkillId.STATUS_HOLY_WATER;
import static bone.server.server.model.skill.L1SkillId.STATUS_HOLY_WATER_OF_EVA;
import static bone.server.server.model.skill.L1SkillId.UNCANNY_DODGE;

import java.util.Random;

import bone.server.Config;
import bone.server.server.ActionCodes;
import bone.server.server.TimeController.WarTimeController;
import bone.server.server.model.Instance.L1DollInstance;
import bone.server.server.model.Instance.L1ItemInstance;
import bone.server.server.model.Instance.L1NpcInstance;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.model.Instance.L1PetInstance;
import bone.server.server.model.Instance.L1SummonInstance;
import bone.server.server.model.gametime.GameTimeClock;
import bone.server.server.model.poison.L1DamagePoison;
import bone.server.server.model.poison.L1ParalysisPoison;
import bone.server.server.model.poison.L1SilencePoison;
import bone.server.server.model.skill.L1SkillDelay;
import bone.server.server.model.skill.L1SkillId;
import bone.server.server.serverpackets.S_AttackMissPacket;
import bone.server.server.serverpackets.S_AttackPacket;
import bone.server.server.serverpackets.S_AttackPacketForNpc;
import bone.server.server.serverpackets.S_DoActionGFX;
import bone.server.server.serverpackets.S_ServerMessage;
import bone.server.server.serverpackets.S_SkillSound;
import bone.server.server.serverpackets.S_UseArrowSkill;
import bone.server.server.serverpackets.S_UseAttackSkill;
import bone.server.server.types.Point;

public class L1Attack {

	private L1PcInstance _pc = null;

	private L1Character _target = null;

	private L1PcInstance _targetPc = null;

	private L1NpcInstance _npc = null;

	private L1NpcInstance _targetNpc = null;

	private final int _targetId;

	private int _targetX;

	private int _targetY;

	private int _statusDamage = 0;

	private static final Random _random = new Random();

	private int _hitRate = 0;

	private int _calcType;

	private static final int PC_PC = 1;

	private static final int PC_NPC = 2;

	private static final int NPC_PC = 3;

	private static final int NPC_NPC = 4;

	private boolean _isHit = false;

	private int _damage = 0;

	private int _drainMana = 0;

	/** ������ ���� **/

	private int _drainHp = 0;

	/** ������ ���� **/

	private int _attckGrfxId = 0;

	private int _attckActId = 0;

	// �����ڰ� �÷��̾��� ����� ���� ����
	private L1ItemInstance weapon = null;

	private int _weaponId = 0;

	private int _weaponType = 0;

	private int _weaponType1 = 0;

	private int _weaponAddHit = 0;

	private int _weaponAddDmg = 0;

	private int _weaponSmall = 0;

	private int _weaponLarge = 0;

	private int _weaponRange = 1;

	private int _weaponBless = 1;

	private int _weaponEnchant = 0;

	private int _weaponMaterial = 0;

	private int _weaponAttrEnchantLevel = 0;

	private int _weaponDoubleDmgChance = 0;
	
	private int _attackType = 0;

	private L1ItemInstance _arrow = null;

	private L1ItemInstance _sting = null;

	private int _leverage = 10; // 1/10��� ǥ���Ѵ�.

	public void setLeverage(int i) {
		_leverage = i;
	}

	private int getLeverage() {
		return _leverage;
	}

	// �����ڰ� �÷��̾��� ����� �������ͽ��� ���� ����
	private static final int[] strHit = { -2, -2, -2, -2, -2, -2, -2,
		-2, -1, -1, 0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 4, 5, 5, 5, 6, 6, 6,
		7, 7, 7, 8, 8, 8, 9, 9, 9, 10, 10, 10, 11, 11, 11, 12, 12, 12,
		13, 13, 13, 14, 14, 14, 15, 15, 15, 16, 16, 16, 17, 17};

	private static final int[] dexHit = { -2, -2, -2, -2, -2, -2, -1, -1, 0, 0,
		1, 1, 2, 2, 3, 3, 4, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16,
		17, 18, 19, 19, 19, 20, 20, 20, 21, 21, 21, 22, 22, 22, 23,
		23, 23, 24, 24, 24, 25, 25, 25, 26, 26, 26, 27, 27, 27, 28 };


	private static final int[] strDmg = new int[128];

	static {
		// STR ������ ����
		for (int str = 0; str <= 8; str++) {
			// 1~8�� -2
			strDmg[str] = -2;
		}
		for (int str = 9; str <= 10; str++) {
			// 9~10�� -1
			strDmg[str] = -1;
		}
		strDmg[11] = 0;
		strDmg[12] = 0;
		strDmg[13] = 1;
		strDmg[14] = 1;
		strDmg[15] = 2;
		strDmg[16] = 2;
		strDmg[17] = 3;
		strDmg[18] = 3;
		strDmg[19] = 4;
		strDmg[20] = 4;
		strDmg[21] = 5;
		strDmg[22] = 5;
		strDmg[23] = 6;
		strDmg[24] = 6;
		strDmg[25] = 6;
		strDmg[26] = 7;
		strDmg[27] = 7;
		strDmg[28] = 7;
		strDmg[29] = 8;
		strDmg[30] = 8;
		strDmg[31] = 9;
		strDmg[32] = 9;
		strDmg[33] = 10;
		strDmg[34] = 11;
		int dmg = 12;
		for (int str = 35; str <= 127; str++) { // 35~127�� 4���٣�1
			if (str % 4 == 1) {
				dmg++;
			}
			strDmg[str] = dmg;
		}
	}

	private static final int[] dexDmg = new int[128];

	static {
		// DEX ������ ����
		for (int dex = 0; dex <= 14; dex++) {
			// 0~14�� 0
			dexDmg[dex] = 0;
		}
		dexDmg[15] = 1;
		dexDmg[16] = 2;
		dexDmg[17] = 3;
		dexDmg[18] = 4;
		dexDmg[19] = 4;
		dexDmg[20] = 4;
		dexDmg[21] = 5;
		dexDmg[22] = 5;
		dexDmg[23] = 5;
		dexDmg[24] = 6;
		dexDmg[25] = 6;
		dexDmg[26] = 6;
		dexDmg[27] = 7;
		dexDmg[28] = 7;
		dexDmg[29] = 7;
		dexDmg[30] = 8;
		dexDmg[31] = 8;
		dexDmg[32] = 8;
		dexDmg[33] = 9;
		dexDmg[34] = 9;
		dexDmg[35] = 9;
		int dmg = 10;
		for (int dex = 36; dex <= 127; dex++) { // 36~127�� 4���٣�1 //#
			if (dex % 4 == 1) {
				dmg++;
			}
			dexDmg[dex] = dmg;
		}
	}

	public void setActId(int actId) {
		_attckActId = actId;
	}

	public void setGfxId(int gfxId) {
		_attckGrfxId = gfxId;
	}

	public int getActId() {
		return _attckActId;
	}

	public int getGfxId() {
		return _attckGrfxId;
	}

	public L1Attack(L1Character attacker, L1Character target) {
		if (attacker instanceof L1PcInstance) {
			_pc = (L1PcInstance) attacker;
			if (target instanceof L1PcInstance) {
				_targetPc = (L1PcInstance) target;
				_calcType = PC_PC;
			} else if (target instanceof L1NpcInstance) {
				_targetNpc = (L1NpcInstance) target;
				_calcType = PC_NPC;
			}
			// ���� ������ ���
			weapon = _pc.getWeapon();
			if (weapon != null) {
				_weaponId = weapon.getItem().getItemId();
				_weaponType = weapon.getItem().getType1();
				_weaponAddHit = weapon.getItem().getHitModifier() + weapon.getHitByMagic();
				_weaponAddDmg = weapon.getItem().getDmgModifier() + weapon.getDmgByMagic();
				_weaponType1 = weapon.getItem().getType();
				_weaponSmall = weapon.getItem().getDmgSmall();
				_weaponLarge = weapon.getItem().getDmgLarge();
				_weaponRange = weapon.getItem().getRange();
				_weaponBless = weapon.getItem().getBless();
				if (_weaponType != 20 && _weaponType != 62) {
					_weaponEnchant = weapon.getEnchantLevel() - weapon.get_durability(); // �ջ�� ���̳ʽ�
				} else {
					_weaponEnchant = weapon.getEnchantLevel();
				}
				_weaponMaterial = weapon.getItem().getMaterial();
				if (_weaponType == 20) { // ȭ���� ���
					_arrow = _pc.getInventory().getArrow();
					if (_arrow != null) {
						_weaponBless = _arrow.getItem().getBless();
						_weaponMaterial = _arrow.getItem().getMaterial();
					}
				}
				if (_weaponType == 62) { // ������ ���
					_sting = _pc.getInventory().getSting();
					if (_sting != null) {
						_weaponBless = _sting.getItem().getBless();
						_weaponMaterial = _sting.getItem().getMaterial();
					}
				}
				_weaponDoubleDmgChance = weapon.getItem().getDoubleDmgChance();
				_weaponAttrEnchantLevel = weapon.getAttrEnchantLevel();
			}
			// �������ͽ��� ���� �߰� ������ ����
			if (_weaponType == 20) { // Ȱ�� ���� DEXġ ����
				_statusDamage = dexDmg[_pc.getAbility().getTotalDex()];
			} else { 
				_statusDamage = strDmg[_pc.getAbility().getTotalStr()];
			}
		} else if (attacker instanceof L1NpcInstance) {
			_npc = (L1NpcInstance) attacker;
			if (target instanceof L1PcInstance) {
				_targetPc = (L1PcInstance) target;
				_calcType = NPC_PC;
			} else if (target instanceof L1NpcInstance) {
				_targetNpc = (L1NpcInstance) target;
				_calcType = NPC_NPC;
			}
		}
		_target = target;
		_targetId = target.getId();
		_targetX = target.getX();
		_targetY = target.getY();
	}

	/* ����������������� ���� ���� ����������������� */

	public boolean calcHit() {
		if (_calcType == PC_PC || _calcType == PC_NPC) {
			if (_weaponRange != -1) {
				if (_pc.getLocation().getTileLineDistance(_target.getLocation()) > _weaponRange+1) {
					_isHit = false;
					return _isHit;
				}
				if(_weaponType1 == 17){
					_isHit = true;
					return _isHit;
				}
			} else {
				if (!_pc.getLocation().isInScreen(_target.getLocation())) {
					_isHit = false;
					return _isHit;
				}
			}
			if (_weaponType == 20 && _weaponId != 190 && _arrow == null) {
				return _isHit = false; // ȭ���� ���� ���� �̽�
			} else if (_weaponType == 62 && _sting == null) {
				return _isHit = false; // ������ ���� ���� �̽�
			} else if (!CharPosUtil.glanceCheck(_pc, _targetX, _targetY)) {
				return _isHit = false; // �����ڰ� �÷��̾��� ���� ��ֹ� ����
			} else if (_weaponId == 247 || _weaponId == 248 || _weaponId == 249) {
				return _isHit = false; // �÷��� ��B~C������ ��ȿ
			} else if (_calcType == PC_PC) {
				if (CharPosUtil.getZoneType(_pc) == 1 
						|| CharPosUtil.getZoneType(_targetPc) == 1) {
					return _isHit = false;					
				}
				return _isHit = calcPcPcHit();
			} else if (_calcType == PC_NPC) {
				return _isHit = calcPcNpcHit();
			}
		} else if (_calcType == NPC_PC) {
			return _isHit = calcNpcPcHit();
		} else if (_calcType == NPC_NPC) {
			return _isHit = calcNpcNpcHit();
		}
		return _isHit;
	}

	// �ܡܡܡ� �÷��̾�κ��� �÷��̾�� ���� ���� �ܡܡܡ�
	/*
	 * PC���� ������ =(PC�� Lv��Ŭ���� ������STR ������DEX ���������� ������DAI�� �ż�/2������ ����)��0.68��10
	 * �̰����� ����� ��ġ�� �ڽ��� �ִ� ����(95%)�� �ִ� ���� �� �� �ִ� ����� PC�� AC �ű�κ��� ����� PC�� AC��  1������ ������ �ڸ������κ���  1��� ����
	 * �ּ� ������5% �ִ� ������95%
	 */
	private boolean calcPcPcHit() {

		_hitRate = _pc.getLevel();
		
		// ����ġ ���� ������ 
		if (_pc.isDarkelf() || _pc.isKnight()) {
			_hitRate += _pc.getLevel()/3;
		} else if (_pc.isDragonknight()) {
			_hitRate += _pc.getLevel()/4;
		} else if (_pc.isElf() || _pc.isIllusionist() || _pc.isCrown()) {
			_hitRate += _pc.getLevel()/5;
		}
		//  ����ġ ���� ������ 

		if (_pc.getAbility().getTotalStr() > 59) {
			_hitRate += (strHit[58]);
		} else {
			_hitRate += (strHit[_pc.getAbility().getTotalStr()-1]);
		}

		if (_pc.getAbility().getTotalDex() > 60) {
			_hitRate += (dexHit[59]);
		} else {
			_hitRate += (dexHit[_pc.getAbility().getTotalDex()-1]);
		}

		if (_weaponType != 20 && _weaponType != 62) {
			_hitRate += _weaponAddHit + _pc.getHitup() + _pc.getHitupByArmor() + (_weaponEnchant / 2);
		} else {
			_hitRate += _weaponAddHit + _pc.getBowHitup() + _pc.getBowHitupByArmor() + _pc.getBowHitupByDoll() + (_weaponEnchant / 2);
		}

		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(MIRROR_IMAGE))
			_hitRate -= 5;
		
		int attackerDice = _random.nextInt(20) + 1 + _hitRate - 10;

		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(UNCANNY_DODGE)) {
			attackerDice -= 6;
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(FEAR)) {
			attackerDice += 5;
		}

		int defenderDice = 0;

		int defenderValue = (int) (_targetPc.getAC().getAc() * 1.5) * -1;

		if (_targetPc.getAC().getAc() >= 0) {
			defenderDice = 10 - _targetPc.getAC().getAc();
		} else if (_targetPc.getAC().getAc() < 0) {
			defenderDice = 10 + _random.nextInt(defenderValue) + 1;
		}

		int fumble = _hitRate - 9;
		int critical = _hitRate + 10;

		if (attackerDice <= fumble) {
			_hitRate = 0;
		} else if (attackerDice >= critical) {
			_hitRate = 100;
		} else {
			if (attackerDice > defenderDice) {
				_hitRate = 100;
			} else if (attackerDice <= defenderDice) {
				_hitRate = 0;
			}
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(ABSOLUTE_BARRIER)
				||_targetPc.getSkillEffectTimerSet().hasSkillEffect(ICE_LANCE)
				||_targetPc.getSkillEffectTimerSet().hasSkillEffect(FREEZING_BREATH)
				||_targetPc.getSkillEffectTimerSet().hasSkillEffect(FREEZING_BLIZZARD)) {
			_hitRate = 0;
		}
		int rnd = _random.nextInt(80) + 1;
		if (_weaponType == 20 && _hitRate > rnd) { // Ȱ�� ���, ��Ʈ ���� ��쿡���� ER������ ȸ�Ǹ� ���� �ǽ��Ѵ�.
			return calcErEvasion();
		}

		return _hitRate >= rnd;
	}

	// �ܡܡܡ� �÷��̾�κ��� NPC ���� ���� ���� �ܡܡܡ�
	private boolean calcPcNpcHit() {
		// NPC���� ������
		// =(PC�� Lv��Ŭ���� ������STR ������DEX ���������� ������DAI�� �ż�/2������ ����)��5��{NPC�� AC��(-5)}
		_hitRate = _pc.getLevel();
		
		// ����ġ ���� ������ 
		if (_pc.isDarkelf() || _pc.isKnight()) {
			_hitRate += _pc.getLevel()/3;
		} else if (_pc.isDragonknight()) {
			_hitRate += _pc.getLevel()/4;
		} else if (_pc.isElf() || _pc.isIllusionist() || _pc.isCrown()) {
			_hitRate += _pc.getLevel()/5;
		}
		//  ����ġ ���� ������ 

		if (_pc.getAbility().getTotalStr() > 59) {
			_hitRate += (strHit[58]);
		} else {
			_hitRate += (strHit[_pc.getAbility().getTotalStr() - 1]);
		}

		if (_pc.getAbility().getTotalDex() > 60) {
			_hitRate += (dexHit[59]);
		} else {
			_hitRate += (dexHit[_pc.getAbility().getTotalDex() - 1]);
		}

		if (_weaponType != 20 && _weaponType != 62) {
			_hitRate += _weaponAddHit + _pc.getHitup() + _pc.getHitupByArmor() + (_weaponEnchant / 2);
		} else {
			_hitRate += _weaponAddHit + _pc.getBowHitup() + _pc.getBowHitupByArmor() + _pc.getBowHitupByDoll() + (_weaponEnchant / 2);
		}

		int attackerDice = _random.nextInt(20) + 1 + _hitRate - 10;

		if (_targetNpc.getSkillEffectTimerSet().hasSkillEffect(FEAR)) {
			attackerDice += 5;
		}

		int defenderDice = 10 - _targetNpc.getAC().getAc();

		int fumble = _hitRate - 9;
		int critical = _hitRate + 10;

		if (attackerDice <= fumble) {
			_hitRate = 0;
		} else if (attackerDice >= critical) {
			_hitRate = 100;
		} else {
			if (attackerDice > defenderDice) {
				_hitRate = 100;
			} else if (attackerDice <= defenderDice) {
				_hitRate = 0;
			}
		}
		int npcId = _targetNpc.getNpcTemplate().get_npcId();
		if (npcId >= 45912 && npcId <= 45915 
				&& !_pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_HOLY_WATER)) {
			_hitRate = 0;
		}
		if (npcId == 45916 
				&& !_pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_HOLY_MITHRIL_POWDER)) {
			_hitRate = 0;
		}
		if (npcId == 45941 
				&& !_pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_HOLY_WATER_OF_EVA)) {
			_hitRate = 0;
		}

		if (!_pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_CURSE_BARLOG)
				&& (npcId == 45752 || npcId == 45753)) {
			_hitRate = 0;
		}
		if (!_pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_CURSE_YAHEE)
				&& (npcId == 45675 || npcId == 81082
						|| npcId == 45625 || npcId == 45674
						|| npcId == 45685)){
			_hitRate = 0;
		}
		if (npcId >= 46068 && npcId <= 46091
				&& _pc.getGfxId().getTempCharGfx() == 6035) {
			_hitRate = 0;
		}
		if (npcId >= 46092 && npcId <= 46106
				&& _pc.getGfxId().getTempCharGfx() == 6034) {
			_hitRate = 0;
		}
		int rnd = _random.nextInt(100) + 1;


		return _hitRate >= rnd;
	}

	// �ܡܡܡ� NPC �κ��� �÷��̾�� ���� ���� �ܡܡܡ�
	private boolean calcNpcPcHit() {
		_hitRate += _npc.getLevel();

		if (_npc instanceof L1PetInstance) {
			_hitRate += ((L1PetInstance) _npc).getHitByWeapon();
		}

		_hitRate += _npc.getHitup();

		int attackerDice = _random.nextInt(20) + 1 + _hitRate - 1;

		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(UNCANNY_DODGE)) {
			attackerDice -= 6;
		}

		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(FEAR)) {
			attackerDice += 5;
		}
		int defenderDice = 0;

		int defenderValue = (_targetPc.getAC().getAc()) * -1;

		if (_targetPc.getAC().getAc() >= 0) {
			defenderDice = 10 - _targetPc.getAC().getAc();
		} else if (_targetPc.getAC().getAc() < 0) {
			defenderDice = 10 + _random.nextInt(defenderValue) + 1;
		}
		int fumble = _hitRate;
		int critical = _hitRate + 19;

		if (attackerDice <= fumble) {
			_hitRate = 0;
		} else if (attackerDice >= critical) {
			_hitRate = 100;
		} else {
			if (attackerDice > defenderDice) {
				_hitRate = 100;
			} else if (attackerDice <= defenderDice) {
				_hitRate = 0;
			}
		}

		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(ABSOLUTE_BARRIER)) {
			_hitRate = 0;
		}

		int rnd = _random.nextInt(70) + 1;
		// NPC�� ���� �������� 10�̻��� ����, 2�̻� ������ �ִ� ���Ȱ�������� �����Ѵ�
		if (_npc.getNpcTemplate().get_ranged() >= 10
				&& _hitRate > rnd
				&& _npc.getLocation().getTileLineDistance(
						new Point(_targetX, _targetY)) >= 3) {
			return calcErEvasion();
		}
		return _hitRate >= rnd;
	}

	// �ܡܡܡ� NPC �κ��� NPC ���� ���� ���� �ܡܡܡ�
	private boolean calcNpcNpcHit() {
		int target_ac = 10 - _targetNpc.getAC().getAc();
		int attacker_lvl = _npc.getNpcTemplate().get_level();

		if (target_ac != 0) {
			_hitRate = (100 / target_ac * attacker_lvl); // �ǰ����� AC = ������ Lv
			// �� �� ������ 100%
		} else {
			_hitRate = 100 / 1 * attacker_lvl;
		}

		if (_npc instanceof L1PetInstance) { // ���� LV1���� �߰� ����+2
			_hitRate += _npc.getLevel() * 2;
			_hitRate += ((L1PetInstance) _npc).getHitByWeapon();
		}

		if (_hitRate < attacker_lvl) {
			_hitRate = attacker_lvl; // ���� ������=L����
		}
		if (_hitRate > 95) {
			_hitRate = 95; // �ְ� �������� 95%
		}
		if (_hitRate < 5) {
			_hitRate = 5; // ������ Lv�� 5 �̸����� ������ 5%
		}

		int rnd = _random.nextInt(100) + 1;
		return _hitRate >= rnd;
	}

	// �ܡܡܡ� ER�� ���� ȸ�� ���� �ܡܡܡ�
	private boolean calcErEvasion() {
		int er = _targetPc.getEr();

		int rnd = _random.nextInt(130) + 1;
		return er < rnd;
	}

	/* ���������������� ������ ���� ���������������� */

	public int calcDamage() {
		switch(_calcType){
		case PC_PC : _damage = calcPcPcDamage(); break;
		case PC_NPC : _damage = calcPcNpcDamage(); break;
		case NPC_PC : _damage = calcNpcPcDamage(); break;
		case NPC_NPC : _damage = calcNpcNpcDamage(); break;
		default : break;
		}
		return _damage;
	}

	// �ܡܡܡ� �÷��̾�κ��� �÷��̾�� ������ ���� �ܡܡܡ�
	public int calcPcPcDamage() {
		int weaponMaxDamage = _weaponSmall + _weaponAddDmg;

		int weaponDamage = 0;

		int doubleChance = _random.nextInt(120) + 1;

		if (_weaponType == 58 && doubleChance <= _weaponDoubleDmgChance) { // ũ�ο� ����
			weaponDamage = weaponMaxDamage;
			_attackType = 2;
			//_pc.sendPackets(new S_SkillSound(_pc.getId(), 3671));
			//Broadcaster.broadcastPacket(_pc, new S_SkillSound(_pc.getId(), 3671));
		} else if (_weaponType == 0) { // �Ǽ�
			weaponDamage = 0;
		} else {
			weaponDamage = _random.nextInt(_weaponSmall) + 1 + _weaponAddDmg;
		}
		if (_pc.getSkillEffectTimerSet().hasSkillEffect(SOUL_OF_FLAME)) {
			if (_weaponType != 20 && _weaponType != 62) {
				weaponDamage = weaponMaxDamage;
			}
		}

		int weaponTotalDamage = weaponDamage + _weaponEnchant;

		if (_weaponType == 54 && doubleChance <= _weaponDoubleDmgChance) { // �̵��� ����
			weaponTotalDamage *= 1.8;
			_attackType = 4;
			//_pc.sendPackets(new S_SkillSound(_pc.getId(), 3398));
			//Broadcaster.broadcastPacket(_pc, new S_SkillSound(_pc.getId(), 3398));
		}

		if (_pc.getSkillEffectTimerSet().hasSkillEffect(DOUBLE_BRAKE) && (_weaponType == 54 || _weaponType == 58)) {
			if ((_random.nextInt(100) + 1) <= 33) {
				weaponTotalDamage *= 1.8;
			}
		}

		double dmg = weaponTotalDamage + _statusDamage;
		
		if (_weaponType != 20 && _weaponType != 62) {			
			dmg += _pc.getDmgup() + _pc.getDmgupByArmor();		
		} else {
			dmg += _pc.getBowDmgup() + _pc.getBowDmgupByArmor() + _pc.getBowDmgupByDoll();
		}

		if (_weaponType == 20) { // Ȱ
			if (_arrow != null) {
				int add_dmg = _arrow.getItem().getDmgSmall();
				if (add_dmg == 0) {
					add_dmg = 1;
				}
				dmg += _random.nextInt(add_dmg) + 1;
			} else if (_weaponId == 190) { // �������� Ȱ
				dmg += _random.nextInt(15) + 1;
			}
		} else if (_weaponType == 62) { // �� ���� ��
			int add_dmg = _sting.getItem().getDmgSmall();
			if (add_dmg == 0) {
				add_dmg = 1;
			}
			dmg = dmg + _random.nextInt(add_dmg) + 1;
		}

		dmg = calcBuffDamage(dmg);
		

		if (_weaponType == 0) { // �Ǽ�
			dmg = (_random.nextInt(5) + 4) / 4;
		} else if (_weaponType == 46){
			dmg += 2;
		} else if (_weaponType == 50){
			dmg += 4;
		}
		if (_weaponType1 == 17) {
			dmg = WeaponSkill.getKiringkuDamage(_pc, _target);
		}
		switch(_weaponId){
		case 2:
		case 200002: dmg = WeaponSkill.getDiceDaggerDamage(_pc, _targetPc, weapon); break;
		case 13:
		case 44:	WeaponSkill.getPoisonSword(_pc, _targetPc); break;
		case 47:	WeaponSkill.getSilenceSword(_pc, _targetPc); break;
		case 54:	dmg += WeaponSkill.getKurtSwordDamage(_pc, _targetPc); break;
		case 58:	dmg += WeaponSkill.getDeathKnightSwordDamage(_pc, _targetPc); break;
		case 76:	dmg += WeaponSkill.getRondeDamage(_pc, _targetPc); break;
		case 121:	dmg += WeaponSkill.getIceQueenStaffDamage(_pc, _target); break;
		case 124: 	dmg += WeaponSkill.getBaphometStaffDamage(_pc, _target); break;
		case 126:
		case 127:	calcStaffOfMana(); break;
		case 203:	dmg += WeaponSkill.getBarlogSwordDamage(_pc, _target); break;
		case 204:
		case 100204:	WeaponSkill.giveFettersEffect(_pc, _targetPc); break;
		case 205: dmg += WeaponSkill.getMoonBowDamage(_pc, _target, _weaponEnchant); break;
		case 256:	dmg += WeaponSkill.getEffectSwordDamage(_pc, _target, 2750); break;
		case 412000:	dmg += WeaponSkill.getEffectSwordDamage(_pc, _target, 10); break;
		case 410000:
		case 410001:
		case 450004:	dmg += WeaponSkill.getChainSwordDamage(_pc, _target); break;
		case 412001:	calcDrainOfHp(dmg);	break;
		case 412004:	dmg += WeaponSkill.getIceSpearDamage(_pc, _target); break;
		case 412005:	dmg += WeaponSkill.geTornadoAxeDamage(_pc, _target); break;
		case 412002: calcDrainOfMana(); break;
		case 412003:	WeaponSkill.getDiseaseWeapon(_pc, _target, 412003); break;
		case 413101:
		case 413102:
		case 413104:
		case 413105:	WeaponSkill.getDiseaseWeapon(_pc, _target, 413101); break;
		case 415010:
		case 415011:
		case 415012:
		case 415013:	dmg += WeaponSkill.getChaserDamage(_pc, _target, 6985);	break;
		case 415015:
		case 415016:	dmg += WeaponSkill.getChaserDamage(_pc, _target, 7179);	break;
		case 413103: calcStaffOfMana(); WeaponSkill.getDiseaseWeapon(_pc, _target, 413101);break;
		}

		for (L1DollInstance doll : _pc.getDollList().values()) {
			if (_weaponType != 20 && _weaponType != 62) {
				dmg += doll.getDamageByDoll();
			}
			doll.attackPoisonDamage(_pc, _targetPc);
		}

		if(_pc.getSkillEffectTimerSet().hasSkillEffect(BURNING_SLASH)){
			dmg += 10;
			_pc.sendPackets(new S_SkillSound(_targetPc.getId(), 6591));
			Broadcaster.broadcastPacket(_pc, new S_SkillSound(_targetPc.getId(), 6591));
			_pc.getSkillEffectTimerSet().killSkillEffectTimer(BURNING_SLASH);
		}

		dmg -= _targetPc.getDamageReductionByArmor(); // ���� �ⱸ�� ���� ������ �氨

		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(SPECIAL_COOKING)){ // ����ȿ丮�� ���� ������ �氨
			dmg -= 5;
		}

		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(REDUCTION_ARMOR)) {
			int targetPcLvl = _targetPc.getLevel();
			if (targetPcLvl < 50) {
				targetPcLvl = 50;
			}
			dmg -= (targetPcLvl - 50) / 5 + 1;
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(IMMUNE_TO_HARM)) {
			dmg /= 1.3;
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(IllUSION_AVATAR)) {
			dmg += dmg / 5;
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(DRAGON_SKIN)) {
			dmg -= 2;
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(PATIENCE)) {
			dmg -= 2;
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(FEATHER_BUFF_A)) {
			dmg -= 3;
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(FEATHER_BUFF_B)) {
			dmg -= 2;
		}

		if (dmg <= 0) {
			_isHit = false;
			_drainHp = 0;
		}

		return (int) dmg;
	}

	private int calcAttrEnchantDmg() {
		int dmg = 0;
		/** �Ӽ���æƮ �߰� Ÿ��ġ */
		switch(_weaponAttrEnchantLevel){
		case 1: case 4: case 7: case 10:	dmg = 1;	break;
		case 2: case 5: case 8: case 11:	dmg = 3;	break;
		case 3: case 6: case 9: case 12:	dmg = 5;	break;
		default:	
			dmg = 0;
		break;
		}
		return dmg;
	}

	// �ܡܡܡ� �÷��̾�κ��� NPC ���� ������ ���� �ܡܡܡ�
	private int calcPcNpcDamage() {
		int weaponMaxDamage = 0;

		int doubleChance = _random.nextInt(100) + 1;

		if (_targetNpc.getNpcTemplate().get_size().equalsIgnoreCase("small") && _weaponSmall > 0) {
			weaponMaxDamage = _weaponSmall;
		} else if (_targetNpc.getNpcTemplate().get_size().equalsIgnoreCase("large") && _weaponLarge > 0) {
			weaponMaxDamage = _weaponLarge;
		}
		
//		weaponMaxDamage += _weaponAddDmg;
		
		int weaponDamage = 0;

		if (_weaponType == 58 && doubleChance <= _weaponDoubleDmgChance) { // ���� ��Ʈ
			weaponDamage = weaponMaxDamage + _weaponAddDmg;
			_attackType = 2;
			//_pc.sendPackets(new S_SkillSound(_pc.getId(), 3671));
			//Broadcaster.broadcastPacket(_pc, new S_SkillSound(_pc.getId(), 3671));
		} else if (_weaponType == 0) { // �Ǽ�
			weaponDamage = 0;
		} else {
			weaponDamage = _random.nextInt(weaponMaxDamage) + 1 + _weaponAddDmg;
		}

		if (_pc.getSkillEffectTimerSet().hasSkillEffect(SOUL_OF_FLAME)) {
			if (_weaponType != 20 && _weaponType != 62) {
				weaponDamage = weaponMaxDamage + _weaponAddDmg;
			}
		}

		int weaponTotalDamage = weaponDamage + _weaponEnchant;

		weaponTotalDamage += calcMaterialBlessDmg(); // ���ູ ������ ���ʽ�

		if (_weaponType == 54 && doubleChance <= _weaponDoubleDmgChance) { // ���� ��Ʈ
			weaponTotalDamage *= 2;
			_attackType = 4;
			//_pc.sendPackets(new S_SkillSound(_pc.getId(), 3398));
			//Broadcaster.broadcastPacket(_pc, new S_SkillSound(_pc.getId(), 3398));
		}

		weaponTotalDamage += calcAttrEnchantDmg();

		if (_pc.getSkillEffectTimerSet().hasSkillEffect(DOUBLE_BRAKE) && (_weaponType == 54 || _weaponType == 58)) {
			if ((_random.nextInt(100) + 1) <= 33) {
				weaponTotalDamage *= 2;
			}
		}

		double dmg = weaponTotalDamage + _statusDamage;
				
		if (_weaponType != 20 && _weaponType != 62) {
			dmg += _pc.getDmgup() + _pc.getDmgupByArmor();
		} else {
			dmg += _pc.getBowDmgup() + _pc.getBowDmgupByArmor() + _pc.getBowDmgupByDoll();
		}

		if (_weaponType == 20) { // Ȱ
			if (_arrow != null) {
				int add_dmg = 0;
				if (_targetNpc.getNpcTemplate().get_size().equalsIgnoreCase("large")) {
					add_dmg = _arrow.getItem().getDmgLarge();
				} else {
					add_dmg = _arrow.getItem().getDmgSmall();
				}
				if (add_dmg == 0) {
					add_dmg = 1;
				}
				if (_targetNpc.getNpcTemplate().is_hard()) {
					add_dmg /= 2;
				}
				dmg = dmg + _random.nextInt(add_dmg) + 1;
			} else if (_weaponId == 190) { // �������� Ȱ
				dmg = dmg + _random.nextInt(15) + 1;
			}
		} else if (_weaponType == 62) { // �� ���� ��
			int add_dmg = 0;
			if (_targetNpc.getNpcTemplate().get_size().equalsIgnoreCase("large")) {
				add_dmg = _sting.getItem().getDmgLarge();
			} else {
				add_dmg = _sting.getItem().getDmgSmall();
			}
			if (add_dmg == 0) {
				add_dmg = 1;
			}
			dmg = dmg + _random.nextInt(add_dmg) + 1;
		}


		dmg = calcBuffDamage(dmg);

		if (_weaponType == 0) { // �Ǽ�
			dmg = (_random.nextInt(5) + 4) / 4;
		}		
		if (_weaponType1 == 17) { // Ű��ũ
			dmg = WeaponSkill.getKiringkuDamage(_pc, _target);
		}
		switch(_weaponId){
		case 13:
		case 44:	WeaponSkill.getPoisonSword(_pc, _target); break;
		case 47:	WeaponSkill.getSilenceSword(_pc, _target); break;
		case 54:	dmg += WeaponSkill.getKurtSwordDamage(_pc, _target); break;
		case 58:	dmg += WeaponSkill.getDeathKnightSwordDamage(_pc, _target); break;
		case 76:	dmg += WeaponSkill.getRondeDamage(_pc, _target); break;
		case 121:	dmg += WeaponSkill.getIceQueenStaffDamage(_pc, _target); break;
		case 124: 	dmg += WeaponSkill.getBaphometStaffDamage(_pc, _target); break;
		case 126:
		case 127:	calcStaffOfMana(); break;
		case 203:	dmg += WeaponSkill.getBarlogSwordDamage(_pc, _target); break;
		case 204:
		case 100204:	WeaponSkill.giveFettersEffect(_pc, _target); break;
		case 205: dmg += WeaponSkill.getMoonBowDamage(_pc, _target, _weaponEnchant); break;
		case 256:	dmg += WeaponSkill.getEffectSwordDamage(_pc, _target, 2750); break;
		case 412000:	dmg += WeaponSkill.getEffectSwordDamage(_pc, _target, 10); break;
		case 410000:
		case 410001:
		case 450004:	dmg += WeaponSkill.getChainSwordDamage(_pc, _target); break;
		case 412001:	calcDrainOfHp(dmg); break;
		case 412004:	dmg += WeaponSkill.getIceSpearDamage(_pc, _target); break;
		case 412005:	dmg += WeaponSkill.geTornadoAxeDamage(_pc, _target); break;
		case 412002: calcDrainOfMana(); break;
		case 412003:	WeaponSkill.getDiseaseWeapon(_pc, _target, 412003); break;
		case 413101:
		case 413102:
		case 413104:
		case 413105:	WeaponSkill.getDiseaseWeapon(_pc, _target, 413101); break;
		case 415010:
		case 415011:
		case 415012:
		case 415013:	dmg += WeaponSkill.getChaserDamage(_pc, _target, 6985);	break;
		case 415015:
		case 415016:	dmg += WeaponSkill.getChaserDamage(_pc, _target, 7179);	break;
		case 413103: calcStaffOfMana(); WeaponSkill.getDiseaseWeapon(_pc, _target, 413101);break;
		}

		for (L1DollInstance doll : _pc.getDollList().values()) {
			if (_weaponType != 20 && _weaponType != 62) {
				dmg += doll.getDamageByDoll();			
			}
			doll.attackPoisonDamage(_pc, _targetNpc);
		}

		if(_pc.getSkillEffectTimerSet().hasSkillEffect(BURNING_SLASH)){
			dmg += 10;
			_pc.sendPackets(new S_SkillSound(_targetNpc.getId(), 6591));
			Broadcaster.broadcastPacket(_pc, new S_SkillSound(_targetNpc.getId(), 6591));
			_pc.getSkillEffectTimerSet().killSkillEffectTimer(BURNING_SLASH);
		}
		
		dmg -= calcNpcDamageReduction();
		if (_targetNpc.getNpcId() == 45640){
			dmg /= 2;
		}
		// �÷��̾�κ��� �ֿϵ���, ��� ����
		boolean isNowWar = false;
		int castleId = L1CastleLocation.getCastleIdByArea(_targetNpc);
		if (castleId > 0) {
			isNowWar = WarTimeController.getInstance().isNowWar(castleId);
		}
		if (!isNowWar) {
			if (_targetNpc instanceof L1PetInstance) {
				dmg /= 8;
			}
			if (_targetNpc instanceof L1SummonInstance) {
				L1SummonInstance summon = (L1SummonInstance) _targetNpc;
				if (summon.isExsistMaster()) {
					dmg /= 8;
				}
			}
		}
		if (dmg <= 0) {
			_isHit = false;
			_drainHp = 0;
		}

		return (int) dmg;
	}

	// �ܡܡܡ� NPC �κ��� �÷��̾�� ������ ���� �ܡܡܡ�
	private int calcNpcPcDamage() {
		int lvl = _npc.getLevel();
		double dmg = 0D;
		if (lvl < 10) {
			dmg = _random.nextInt(lvl) + 10D + _npc.getAbility().getTotalStr() / 2 + 1;
		} else {
			dmg = _random.nextInt(lvl) + _npc.getAbility().getTotalStr() / 2 + 1;
		}

		if (_npc instanceof L1PetInstance) {
			dmg += (lvl / 16); // ���� LV16���� �߰� Ÿ��
			dmg += ((L1PetInstance) _npc).getDamageByWeapon();
		}

		dmg += _npc.getDmgup();

		if (isUndeadDamage()) {
			dmg *= 1.1;
		}

		dmg = dmg * getLeverage() / 10;

		dmg -= calcPcDefense();

		if (_npc.isWeaponBreaked()) { // NPC�� �����극��ũ��.
			dmg /= 2;
		}

		dmg -= (_targetPc.getDamageReductionByArmor()/2); // ���� �ⱸ�� ���� ������ �氨

		for (L1DollInstance doll : _targetPc.getDollList().values()) {
			if (_npc.getNpcTemplate().getBowActId() == 0)
				dmg -= doll.getDamageReductionByDoll();
		}

		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(SPECIAL_COOKING)){ // ����ȿ丮�� ���� ������ �氨
			dmg -= 5;
		}

		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(REDUCTION_ARMOR)) {
			int targetPcLvl = _targetPc.getLevel();
			if (targetPcLvl < 50) {
				targetPcLvl = 50;
			}
			dmg -= (targetPcLvl - 50) / 5 + 1;
		}
		// �ֿϵ���, ������κ��� �÷��̾ ����
		boolean isNowWar = false;
		int castleId = L1CastleLocation.getCastleIdByArea(_targetPc);
		if (castleId > 0) {
			isNowWar = WarTimeController.getInstance().isNowWar(castleId);
		}
		if (!isNowWar) {
			if (_npc instanceof L1PetInstance) {
				dmg /= 8;
			}
			if (_npc instanceof L1SummonInstance) {
				L1SummonInstance summon = (L1SummonInstance) _npc;
				if (summon.isExsistMaster()) {
					dmg /= 8;
				}
			}
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(IllUSION_AVATAR)) {
			dmg += dmg / 5;
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(PATIENCE)) {
			dmg -= 2;
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(DRAGON_SKIN)) {
			dmg -= 2;
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(IMMUNE_TO_HARM)) {
			dmg /= 1.3;
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(FEATHER_BUFF_A)) {
			dmg -= 3;
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(FEATHER_BUFF_B)) {
			dmg -= 2;
		}

		addNpcPoisonAttack(_npc, _targetPc);

		if (_npc instanceof L1PetInstance || _npc instanceof L1SummonInstance) {
			if(CharPosUtil.getZoneType(_targetPc) == 1){
				_isHit = false;
			}
		}

		if (dmg <= 0) {
			_isHit = false;
		}

		return (int) dmg;
	}

	// �ܡܡܡ� NPC �κ��� NPC ���� ������ ���� �ܡܡܡ�
	private int calcNpcNpcDamage() {
		int lvl = _npc.getLevel();
		double dmg = 0;

		if (_npc instanceof L1PetInstance) {
			dmg = _random.nextInt(_npc.getNpcTemplate().get_level()) + _npc.getAbility().getTotalStr() / 2 + 1;
			dmg += (lvl / 16); // ���� LV16���� �߰� Ÿ��
			dmg += ((L1PetInstance) _npc).getDamageByWeapon();
		} else {
			dmg = _random.nextInt(lvl) + _npc.getAbility().getTotalStr() / 2 + 1;
		}

		if (isUndeadDamage()) {
			dmg *= 1.1;
		}

		dmg = dmg * getLeverage() / 10;

		dmg -= calcNpcDamageReduction();

		if (_npc.isWeaponBreaked()) { // NPC�� �����극��ũ��.
			dmg /= 2;
		}
		if (_targetNpc.getNpcId() == 45640){
			dmg /= 2;
		}
		addNpcPoisonAttack(_npc, _targetNpc);

		if (dmg <= 0) {
			_isHit = false;
		}

		return (int) dmg;
	}

	// �ܡܡܡ� �÷��̾��� ������ ��ȭ ���� �ܡܡܡ�
	private double calcBuffDamage(double dmg) {
		if (_pc.getSkillEffectTimerSet().hasSkillEffect(BURNING_SPIRIT) 
				|| (_pc.getSkillEffectTimerSet().hasSkillEffect(ELEMENTAL_FIRE)
						&& _weaponType != 20 && _weaponType != 62 && _weaponType1 != 17)){
			if ((_random.nextInt(100) + 1) <= 33) {
				double tempDmg = dmg;
				if (_pc.getSkillEffectTimerSet().hasSkillEffect(FIRE_WEAPON)) {
					tempDmg -= 4;
				}
				if (_pc.getSkillEffectTimerSet().hasSkillEffect(FIRE_BLESS)) {
					tempDmg -= 4;
				}
				if (_pc.getSkillEffectTimerSet().hasSkillEffect(BURNING_WEAPON)) {
					tempDmg -= 6;
				}
				if (_pc.getSkillEffectTimerSet().hasSkillEffect(BERSERKERS)) {
					tempDmg -= 5;
				}
				double diffDmg = dmg - tempDmg;
				dmg = tempDmg * 1.5 + diffDmg;
			}
		}

		return dmg;
	}

	// �ܡܡܡ� �÷��̾��� AC�� ���� ������ �氨 �ܡܡܡ�
	private int calcPcDefense() {
		int ac = Math.max(0, 10 - _targetPc.getAC().getAc());
		int acDefMax = _targetPc.getClassFeature().getAcDefenseMax(ac);
		return _random.nextInt(acDefMax + 1);
	}

	// �ܡܡܡ� NPC�� ������ ��ҿ� ���� �氨 �ܡܡܡ�
	private int calcNpcDamageReduction() {
		return _targetNpc.getNpcTemplate().get_damagereduction();
	}

	// �ܡܡܡ� ������ ������ �ູ�� ���� �߰� ������ ���� �ܡܡܡ�
	private int calcMaterialBlessDmg() {
		int damage = 0;
		int undead = _targetNpc.getNpcTemplate().get_undead();
		if ((_weaponMaterial == 14 || _weaponMaterial == 17 || _weaponMaterial == 22)
				&& (undead == 1 || undead == 3 || undead == 5)) { // �����̽����������ϸ���, ����, �� ����衤�� ����� ����
			damage += _random.nextInt(20) + 1;
		}
		if ((_weaponMaterial == 17 || _weaponMaterial == 22)
				&& undead == 2) {
			damage += _random.nextInt(3) + 1;
		}
		if (_weaponBless == 0 && (undead == 1 || undead == 2 || undead == 3)) { // �ູ ����, ����, �� ����衤�Ǹ��衤�� ����� ����
			damage += _random.nextInt(4) + 1;
		}
		if (_pc.getWeapon() != null && _weaponType != 20 && _weaponType != 62
				&& weapon.getHolyDmgByMagic() != 0 && (undead == 1 || undead == 3)) {
			damage += weapon.getHolyDmgByMagic();
		}
		return damage;
	}
	
	// �ܡܡܡ� NPC�� �� ������ �߰� ���ݷ��� ��ȭ �ܡܡܡ�
	private boolean isUndeadDamage() {
		boolean flag = false;
		int undead = _npc.getNpcTemplate().get_undead();
		boolean isNight = GameTimeClock.getInstance().getGameTime().isNight();
		if (isNight && (undead == 1 || undead == 3 || undead == 4)) { // 18~6��, ����, �� ����衤�� ����� ����
			flag = true;
		}
		return flag;
	}
	// �ܡܡܡ� PC�� �������� �ΰ� �ܡܡܡ�
	public void addPcPoisonAttack(L1Character attacker, L1Character target) {
		int chance = _random.nextInt(100) + 1;
		if ((_weaponId == 13 || _weaponId == 44
				|| (_weaponId != 0 && _pc.getSkillEffectTimerSet().hasSkillEffect(ENCHANT_VENOM)))
				&& chance <= 10) {			
			L1DamagePoison.doInfection(attacker, target, 3000, 5);
		}
	}	
	// �ܡܡܡ� NPC�� �������� �ΰ� �ܡܡܡ�
	private void addNpcPoisonAttack(L1Character attacker, L1Character target) {
		if (_npc.getNpcTemplate().get_poisonatk() != 0) { // ������ �־�
			if (15 >= _random.nextInt(100) + 1) { // 15%�� Ȯ���� ������
				if (_npc.getNpcTemplate().get_poisonatk() == 1) { // ���
					// 3�� �ֱ⿡ ������ 5
					L1DamagePoison.doInfection(attacker, target, 3000, 5);
				} else if (_npc.getNpcTemplate().get_poisonatk() == 2) { // ħ����
					L1SilencePoison.doInfection(target);
				} else if (_npc.getNpcTemplate().get_poisonatk() == 4) { // ����
					// 20�� �Ŀ� 45�ʰ� ����
					L1ParalysisPoison.doInfection(target, 20000, 45000);
				}
			}
		} else if (_npc.getNpcTemplate().get_paralysisatk() != 0) { /// ���� ���� �־�
		}
	}

	// ����� ������ſ�Ŀ� ��ö�� ������ſ���� MP����� ���� �����
	public void calcStaffOfMana() {	
		int som_lvl = _weaponEnchant + 3; // �ִ� MP������� ����
		if (som_lvl < 0) {
			som_lvl = 0;
		}
		// MP������� ���� ���
		_drainMana = _random.nextInt(som_lvl) + 1;
		// �ִ� MP������� 9�� ����
		if (_drainMana > Config.MANA_DRAIN_LIMIT_PER_SOM_ATTACK) {
			_drainMana = Config.MANA_DRAIN_LIMIT_PER_SOM_ATTACK;
		}
	}

	/** ������ ���� - �ĸ��� ��� **/
	public void calcDrainOfHp(double dmg) {  // ü�� ����� ���� �߰�
		int r = _random.nextInt(100);
		if (r <= 20){
			if(dmg <= 30){
				_drainHp = 1;
			} else if(dmg > 30 && dmg <= 40){
				_drainHp = 2;
			} else if(dmg > 40 && dmg <= 50){
				_drainHp = 3;
			} else if(dmg > 50){
				_drainHp = 4;
			}
		}
		
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(ABSOLUTE_BARRIER)
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(ICE_LANCE)
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(FREEZING_BLIZZARD)
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(FREEZING_BREATH)
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(EARTH_BIND)
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(MOB_BASILL)
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(MOB_COCA)) {
			_drainHp = 0;
		}
	}

	/** ������ ���� - ������ �ܰ� **/
	public void calcDrainOfMana() {  // ���� ����� ���� �߰�
		_drainMana = 1;
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(ABSOLUTE_BARRIER)
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(ICE_LANCE)
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(FREEZING_BLIZZARD)
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(FREEZING_BREATH)
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(EARTH_BIND)
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(MOB_BASILL)
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(MOB_COCA)) {
			_drainMana = 0;
		}
	}

	/* ��������������� ���� ��� �۽� ��������������� */

	public void action() {
		if (_calcType == PC_PC || _calcType == PC_NPC) {
			actionPc();
		} else if (_calcType == NPC_PC || _calcType == NPC_NPC) {
			actionNpc();
		}
	}

	// �ܡܡܡ� �÷��̾��� ���� ��� �۽� �ܡܡܡ�
	private void actionPc() {
		_pc.getMoveState().setHeading(CharPosUtil.targetDirection(_pc, _targetX, _targetY)); // ���⼼Ʈ
		if (_weaponType == 20) {
			if (_arrow != null) {
				_pc.getInventory().removeItem(_arrow, 1);
				if (_pc.getGfxId().getTempCharGfx() == 7968){
					_pc.sendPackets(new S_UseArrowSkill(_pc, _targetId, 7972, _targetX, _targetY, _isHit));
					Broadcaster.broadcastPacket(_pc, new S_UseArrowSkill(_pc, _targetId, 7972, _targetX, _targetY, _isHit));
				}else{
					_pc.sendPackets(new S_UseArrowSkill(_pc, _targetId, 66, _targetX, _targetY, _isHit));
					Broadcaster.broadcastPacket(_pc, new S_UseArrowSkill(_pc, _targetId, 66, _targetX, _targetY, _isHit));					
				}
				if (_isHit) {
					Broadcaster.broadcastPacketExceptTargetSight(_target, new S_DoActionGFX(_targetId, ActionCodes.ACTION_Damage), _pc);
				}				
			} else if (_weaponId == 190) {
				_pc.sendPackets(new S_UseArrowSkill(_pc, _targetId, 2349, _targetX, _targetY, _isHit));
				Broadcaster.broadcastPacket(_pc,new S_UseArrowSkill(_pc, _targetId, 2349, _targetX, _targetY, _isHit));
				if (_isHit) {
					Broadcaster.broadcastPacketExceptTargetSight(_target, new S_DoActionGFX(_targetId, ActionCodes.ACTION_Damage), _pc);
				}
			}
		} else if (_weaponType == 62 && _sting != null) {
			_pc.getInventory().removeItem(_sting, 1);
			if (_pc.getGfxId().getTempCharGfx() == 7968){
				_pc.sendPackets(new S_UseArrowSkill(_pc, _targetId, 7972, _targetX, _targetY, _isHit));
				Broadcaster.broadcastPacket(_pc, new S_UseArrowSkill(_pc, _targetId, 7972, _targetX, _targetY, _isHit));
			}else{
				_pc.sendPackets(new S_UseArrowSkill(_pc, _targetId, 2989, _targetX, _targetY, _isHit));
				Broadcaster.broadcastPacket(_pc, new S_UseArrowSkill(_pc, _targetId, 2989, _targetX, _targetY, _isHit));					
			}
			if (_isHit) {
				Broadcaster.broadcastPacketExceptTargetSight(_target, new S_DoActionGFX(_targetId, ActionCodes.ACTION_Damage), _pc);			
			}						
		} else {
			if (_isHit) {
				_pc.sendPackets(new S_AttackPacket(_pc, _targetId, ActionCodes.ACTION_Attack, _attackType));
				Broadcaster.broadcastPacket(_pc, new S_AttackPacket(_pc, _targetId,ActionCodes.ACTION_Attack, _attackType));
				Broadcaster.broadcastPacketExceptTargetSight(_target, new S_DoActionGFX(_targetId, ActionCodes.ACTION_Damage), _pc);			
			} else {
				if (_targetId > 0) {
					_pc.sendPackets(new S_AttackMissPacket(_pc, _targetId));
					Broadcaster.broadcastPacket(_pc, new S_AttackMissPacket(_pc, _targetId));
				} else {
					_pc.sendPackets(new S_AttackPacket(_pc, 0, ActionCodes.ACTION_Attack));
					Broadcaster.broadcastPacket(_pc, new S_AttackPacket(_pc, 0, ActionCodes.ACTION_Attack));
				}
			}
		}
	}

	// �ܡܡܡ� NPC�� ���� ��� �۽� �ܡܡܡ�
	private void actionNpc() {
		int _npcObjectId = _npc.getId();
		int bowActId = 0;
		int actId = 0;

		_npc.getMoveState().setHeading(CharPosUtil.targetDirection(_npc, _targetX, _targetY)); // ���⼼Ʈ

		// Ÿ�ٰ��� �Ÿ��� 2�̻� ������ ���Ÿ� ����
		boolean isLongRange = (_npc.getLocation().getTileLineDistance(new Point(_targetX, _targetY)) > 1);
		bowActId = _npc.getNpcTemplate().getBowActId();

		if (getActId() > 0) {
			actId = getActId();
		} else {
			actId = ActionCodes.ACTION_Attack;
		}

		if (isLongRange && bowActId > 0) {
			Broadcaster.broadcastPacket(_npc, new S_UseArrowSkill(_npc, _targetId,
					bowActId, _targetX, _targetY, _isHit));
		} else {
			if (_isHit) {
				if (getGfxId() > 0) {
					Broadcaster.broadcastPacket(_npc, new S_UseAttackSkill(_target,	_npcObjectId, getGfxId(), _targetX,	_targetY, actId));
					Broadcaster.broadcastPacketExceptTargetSight(_target, new S_DoActionGFX( _targetId, ActionCodes.ACTION_Damage), _npc);
				} else {
					Broadcaster.broadcastPacket(_npc, new S_AttackPacketForNpc(_target, _npcObjectId, actId));
					Broadcaster.broadcastPacketExceptTargetSight(_target, new S_DoActionGFX(_targetId, ActionCodes.ACTION_Damage), _npc);
				}
			} else {
				if (getGfxId() > 0) {
					Broadcaster.broadcastPacket(_npc, new S_UseAttackSkill(_target, _npcObjectId, getGfxId(), _targetX, _targetY, actId, 0));
				} else {
					Broadcaster.broadcastPacket(_npc, new S_AttackMissPacket(_npc, _targetId, actId));
				}
			}
		}
	}
	/* ���������������� ��� ��� �ݿ� ���������������� */

	public void commit() {
		if (_isHit) {
			if (_calcType == PC_PC || _calcType == NPC_PC) {
				commitPc();
			} else if (_calcType == PC_NPC || _calcType == NPC_NPC) {
				commitNpc();
			}
		}

		// ������ġ �� ������ Ȯ�ο� �޼���
		if (!Config.ALT_ATKMSG) {
			return;
		}
		if (Config.ALT_ATKMSG) {
			if ((_calcType == PC_PC || _calcType == PC_NPC) && !_pc.isGm()) {
				return;
			}
			if ((_calcType == PC_PC || _calcType == NPC_PC)
					&& !_targetPc.isGm()) {
				return;
			}
		}
		String msg0 = "";
		String msg1 = "��";
		String msg2 = "";
		String msg3 = "";
		String msg4 = "";
		if (_calcType == PC_PC || _calcType == PC_NPC) { // ����Ŀ�� PC�� ���
			msg0 = _pc.getName();
		} else if (_calcType == NPC_PC) { // ����Ŀ�� NPC�� ���
			msg0 = _npc.getName();
		}

		if (_calcType == NPC_PC || _calcType == PC_PC) { // Ÿ���� PC�� ���
			msg4 = _targetPc.getName();
			msg2 = "HitR" + _hitRate + "% THP" + _targetPc.getCurrentHp();
		} else if (_calcType == PC_NPC) { // Ÿ���� NPC�� ���
			msg4 = _targetNpc.getName();
			msg2 = "Hit" + _hitRate + "% Hp" + _targetNpc.getCurrentHp();
		}
		msg3 = _isHit ? _damage + "��" : "�̽� �߽��ϴ�.";

		if (_calcType == PC_PC || _calcType == PC_NPC) { // ����Ŀ�� PC�� ���
			_pc.sendPackets(new S_ServerMessage(166, msg0, msg1, msg2, msg3,
					msg4)); // \f1%0%4%1%3 %2
		}
		if (_calcType == NPC_PC || _calcType == PC_PC) { // Ÿ���� PC�� ���
			_targetPc.sendPackets(new S_ServerMessage(166, msg0, msg1, msg2,
					msg3, msg4)); // \f1%0%4%1%3 %2
		}
	}

	// �ܡܡܡ� �÷��̾ ��� ����� �ݿ� �ܡܡܡ�
	private void commitPc() {
		if (_calcType == PC_PC) {
			if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(ICE_LANCE)
					|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(ABSOLUTE_BARRIER)
					|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(FREEZING_BLIZZARD)
					|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(FREEZING_BREATH)
					|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(EARTH_BIND)
					|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(MOB_BASILL) //�ٽǾ󸮱ⵥ����0
					|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(MOB_COCA)) { //��ī�󸮱ⵥ����0
				_damage = 0;
				_drainMana = 0;
				_drainHp = 0;
			}
			if (_drainMana > 0 && _targetPc.getCurrentMp() > 0) {
				if (_drainMana > _targetPc.getCurrentMp()) {
					_drainMana = _targetPc.getCurrentMp();
				}
				short newMp = (short) (_targetPc.getCurrentMp() - _drainMana);
				_targetPc.setCurrentMp(newMp);
				newMp = (short) (_pc.getCurrentMp() + _drainMana);
				_pc.setCurrentMp(newMp);
			}

			/** ������ ���� **/

			if (_drainHp > 0 && _targetPc.getCurrentHp() > 0) {
				if (_drainHp > _targetPc.getCurrentHp()) {
					_drainHp = _targetPc.getCurrentHp();
				}
				short newHp = (short) (_targetPc.getCurrentHp() - _drainHp);
				_targetPc.setCurrentHp(newHp);
				newHp = (short) (_pc.getCurrentHp() + _drainHp);
				_pc.setCurrentHp(newHp);
			}
			/** ������ ���� **/

			damagePcWeaponDurability(); // ���⸦ �ջ��Ų��.

			_targetPc.receiveDamage(_pc, _damage, false);
		} else if (_calcType == NPC_PC) {
			if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(ICE_LANCE)
					|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(ABSOLUTE_BARRIER)
					|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(FREEZING_BLIZZARD)
					|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(FREEZING_BREATH)
					|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(EARTH_BIND)
					|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(MOB_BASILL) //�ٽǾ󸮱ⵥ����0
					|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(MOB_COCA)) { //��ī�󸮱ⵥ����0
				_damage = 0;
			}
			_targetPc.receiveDamage(_npc, _damage, false);
		}
	}

	// �ܡܡܡ� NPC�� ��� ����� �ݿ� �ܡܡܡ�
	private void commitNpc() {
		if (_calcType == PC_NPC) {
			if (_targetNpc.getSkillEffectTimerSet().hasSkillEffect(ICE_LANCE)
					|| _targetNpc.getSkillEffectTimerSet().hasSkillEffect(FREEZING_BLIZZARD)
					|| _targetNpc.getSkillEffectTimerSet().hasSkillEffect(FREEZING_BREATH)
					|| _targetNpc.getSkillEffectTimerSet().hasSkillEffect(EARTH_BIND)
					|| _targetNpc.getSkillEffectTimerSet().hasSkillEffect(MOB_BASILL) //�ٽǾ󸮱ⵥ����0
					|| _targetNpc.getSkillEffectTimerSet().hasSkillEffect(MOB_COCA)) { //��ī�󸮱ⵥ����0
				_damage = 0;
				_drainMana = 0;
				_drainHp = 0;
			}
			if (_drainMana > 0) {
				int drainValue = _targetNpc.drainMana(_drainMana);
				int newMp = _pc.getCurrentMp() + drainValue;
				_pc.setCurrentMp(newMp);

				if (drainValue > 0) {
					int newMp2 = _targetNpc.getCurrentMp() - drainValue;
					_targetNpc.setCurrentMp(newMp2);
				}
			}

			/** ������ ���� **/

			if (_drainHp > 0) {
				int newHp = _pc.getCurrentHp() + _drainHp;
				_pc.setCurrentHp(newHp);
			}
			/** ������ ���� **/

			damageNpcWeaponDurability(); // ���⸦ �ջ��Ų��.

			_targetNpc.receiveDamage(_pc, _damage);
		} else if (_calcType == NPC_NPC) {
			if (_targetNpc.getSkillEffectTimerSet().hasSkillEffect(ICE_LANCE)
					|| _targetNpc.getSkillEffectTimerSet().hasSkillEffect(FREEZING_BLIZZARD)
					|| _targetNpc.getSkillEffectTimerSet().hasSkillEffect(FREEZING_BREATH)
					|| _targetNpc.getSkillEffectTimerSet().hasSkillEffect(EARTH_BIND)
					|| _targetNpc.getSkillEffectTimerSet().hasSkillEffect(MOB_BASILL) //�ٽǾ󸮱ⵥ����0
					|| _targetNpc.getSkillEffectTimerSet().hasSkillEffect(MOB_COCA)) { // //��ī�󸮱ⵥ����0
				_damage = 0;
			}
			_targetNpc.receiveDamage(_npc, _damage);
		}
	}

	/* ���������������� ī���� �ٸ��� ���������������� */

	// ����� ī���� �ٸ������ ���� ��� �۽� �����
	public void actionCounterBarrier() {
		if (_calcType == PC_PC) {
			_pc.getMoveState().setHeading(CharPosUtil.targetDirection(_pc, _targetX, _targetY)); // ���⼼Ʈ
			_pc.sendPackets(new S_AttackMissPacket(_pc, _targetId));
			Broadcaster.broadcastPacket(_pc, new S_AttackMissPacket(_pc, _targetId));
			_pc.sendPackets(new S_DoActionGFX(_pc.getId(), ActionCodes.ACTION_Damage));
			Broadcaster.broadcastPacket(_pc, new S_DoActionGFX(_pc.getId(), ActionCodes.ACTION_Damage));
			_pc.sendPackets(new S_SkillSound(_targetId, 5846)); 
			Broadcaster.broadcastPacket(_pc, new S_SkillSound(_targetId, 5846));

		} else if (_calcType == NPC_PC) {
			int actId = 0;
			_npc.getMoveState().setHeading(CharPosUtil.targetDirection(_npc, _targetX, _targetY)); // ���⼼Ʈ
			if (getActId() > 0) {
				actId = getActId();
			} else {
				actId = ActionCodes.ACTION_Attack;
			}
			if (getGfxId() > 0) {
				Broadcaster.broadcastPacket(_npc, new S_UseAttackSkill(_target, _npc.getId(), getGfxId(), _targetX, _targetY, actId, 0));
			} else {
				Broadcaster.broadcastPacket(_npc, new S_AttackMissPacket(_npc, _targetId, actId));
			}
			Broadcaster.broadcastPacket(_npc, new S_DoActionGFX(_npc.getId(), ActionCodes.ACTION_Damage));
			Broadcaster.broadcastPacket(_npc, new S_SkillSound(_targetId, 5846));
		}
	}

	//	����� ��Ż�ٵ� �ߵ����� ���� ��� �۽� �����
	public void actionMortalBody() {
		if (_calcType == PC_PC) {
			_pc.getMoveState().setHeading(CharPosUtil.targetDirection(_pc, _targetX, _targetY)); // ���⼼Ʈ
			S_UseAttackSkill packet = new S_UseAttackSkill(_pc, _target.getId(), 6519, _targetX, _targetY, ActionCodes.ACTION_Attack, false);
			_pc.sendPackets(packet);
			Broadcaster.broadcastPacket(_pc, packet);
			_pc.sendPackets(new S_DoActionGFX(_pc.getId(), ActionCodes.ACTION_Damage));
			Broadcaster.broadcastPacket(_pc, new S_DoActionGFX(_pc.getId(), ActionCodes.ACTION_Damage));
		} else if (_calcType == NPC_PC) {
			_npc.getMoveState().setHeading(CharPosUtil.targetDirection(_npc, _targetX, _targetY)); // ���⼼Ʈ
			Broadcaster.broadcastPacket(_npc, new S_SkillSound(_target.getId(), 6519));
			Broadcaster.broadcastPacket(_npc, new S_DoActionGFX(_npc.getId(), ActionCodes.ACTION_Damage));
		}
	}

	// ����� ����� ���ݿ� ���ؼ� ī���� �ٸ�� ��ȿ�Ѱ��� �Ǻ� �����
	public boolean isShortDistance() {
		boolean isShortDistance = true;
		if (_calcType == PC_PC) {
			if (_weaponType == 20 || _weaponType == 62) { // Ȱ�̳� ��Ʈ��Ʈ
				isShortDistance = false;
			}
		} else if (_calcType == NPC_PC) {
			boolean isLongRange = (_npc.getLocation().getTileLineDistance(new Point(_targetX, _targetY)) > 1);
			int bowActId = _npc.getNpcTemplate().getBowActId();
			// �Ÿ��� 2�̻�, �������� Ȱ�� �׼� ID�� �ִ� ���� ������
			if (isLongRange && bowActId > 0) {
				isShortDistance = false;
			}
		}
		return isShortDistance;
	}

	// ����� ī���� �ٸ����� �������� �ݿ� �����
	public void commitCounterBarrier() {
		int damage = calcCounterBarrierDamage();
		if (damage == 0) {
			return;
		}
		if (_calcType == PC_PC) {
			_pc.receiveDamage(_targetPc, damage, false);
		} else if (_calcType == NPC_PC) {
			_npc.receiveDamage(_targetPc, damage);
		}
	}

	// ����� ��Ż�ٵ��� �������� �ݿ� �����
	public void commitMortalBody() {
		int damage = 30;
		if (damage == 0) {
			return;
		}
		if (_calcType == PC_PC) {
			_pc.receiveDamage(_targetPc, damage, false);
		} else if (_calcType == NPC_PC) {
			_npc.receiveDamage(_targetPc, damage);
		}
	}

	// �ܡܡܡ� ī���� �ٸ����� �������� ���� �ܡܡܡ�
	private int calcCounterBarrierDamage() {
		int damage = 0;
		L1ItemInstance weapon = null;
		weapon = _targetPc.getWeapon();
		if (weapon != null) {
			if (weapon.getItem().getType() == 3) {
				damage = (weapon.getItem().getDmgLarge() + weapon.getEnchantLevel() + weapon.getItem().getDmgModifier()) * 2;
			}
		}
		return damage;
	}


	/*
	 * ���⸦ �ջ��Ų��. ��NPC�� ���, �ջ� Ȯ����10%�� �Ѵ�. �ູ �����3%�� �Ѵ�.
	 */
	private void damageNpcWeaponDurability() {
		int chance = 3;
		int bchance = 1;

		/*
		 * �ջ����� �ʴ� NPC, �Ǽ�, �ջ����� �ʴ� ���� ���, SOF���� ��� �ƹ��͵� ���� �ʴ´�.
		 */
		if (_calcType != PC_NPC
				|| _targetNpc.getNpcTemplate().is_hard() == false
				|| _weaponType == 0 || weapon.getItem().get_canbedmg() == 0
				|| _pc.getSkillEffectTimerSet().hasSkillEffect(SOUL_OF_FLAME)) {
			return;
		}
		// ����� ���⡤�������� ����
		if ((_weaponBless == 1 || _weaponBless == 2)
				&& ((_random.nextInt(100) + 1) < chance)) {
			// \f1�����%0�� �ջ��߽��ϴ�.
			_pc.sendPackets(new S_ServerMessage(268, weapon.getLogName()));
			_pc.getInventory().receiveDamage(weapon);
		}
		// �ູ�� ����
		if (_weaponBless == 0 && ((_random.nextInt(100) + 1) < bchance)) {
			// \f1�����%0�� �ջ��߽��ϴ�.
			_pc.sendPackets(new S_ServerMessage(268, weapon.getLogName()));
			_pc.getInventory().receiveDamage(weapon);
		}
	}

	/*
	 * �ٿ��Źũ�� ���� ���⸦ �ջ��Ų��. �ٿ��Źũ�� �ջ� Ȯ����10%
	 */
	private void damagePcWeaponDurability() {
		// PvP �̿�, �Ǽ�, Ȱ, �� ���� ��, ��밡 �ٿ��Źũ�̻��, SOF���� ��� �ƹ��͵� ���� �ʴ´�
		if (_calcType != PC_PC || _weaponType == 0 || _weaponType == 20 || _weaponType == 62
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(BOUNCE_ATTACK) == false
				|| _pc.getSkillEffectTimerSet().hasSkillEffect(SOUL_OF_FLAME)
				|| _targetPc.isParalyzed()) {
			return;
		}

		if (_random.nextInt(100) + 1 <= 5) {
			// \f1�����%0�� �ջ��߽��ϴ�.
			_pc.sendPackets(new S_ServerMessage(268, weapon.getLogName()));
			_pc.getInventory().receiveDamage(weapon);
		}
	}
}