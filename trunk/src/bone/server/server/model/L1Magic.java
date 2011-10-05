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

import java.util.Random;

import bone.server.Config;
import bone.server.server.ActionCodes;
import bone.server.server.TimeController.WarTimeController;
import bone.server.server.datatables.SkillsTable;
import bone.server.server.model.Instance.L1DollInstance;
import bone.server.server.model.Instance.L1ItemInstance;
import bone.server.server.model.Instance.L1NpcInstance;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.model.Instance.L1PetInstance;
import bone.server.server.model.Instance.L1SummonInstance;
import bone.server.server.serverpackets.S_DoActionGFX;
import bone.server.server.serverpackets.S_ServerMessage;
import bone.server.server.serverpackets.S_SkillSound;
import bone.server.server.templates.L1Skills;
import bone.server.server.utils.CalcStat;
import static bone.server.server.model.skill.L1SkillId.*;

public class L1Magic {

	private int _calcType;

	private final int PC_PC = 1;

	private final int PC_NPC = 2;

	private final int NPC_PC = 3;

	private final int NPC_NPC = 4;

	private L1PcInstance _pc = null;

	private L1PcInstance _targetPc = null;

	private L1NpcInstance _npc = null;

	private L1NpcInstance _targetNpc = null;

	private int _leverage = 13; 
	
	private L1Skills _skill;

	private static Random _random = new Random();

	public void setLeverage(int i) {
		_leverage = i;
	}

	private int getLeverage() {
		return _leverage;
	}

	public L1Magic(L1Character attacker, L1Character target) {
		if (attacker instanceof L1PcInstance) {
			if (target instanceof L1PcInstance) {
				_calcType = PC_PC;
				_pc = (L1PcInstance) attacker;
				_targetPc = (L1PcInstance) target;
			} else {
				_calcType = PC_NPC;
				_pc = (L1PcInstance) attacker;
				_targetNpc = (L1NpcInstance) target;
			}
		} else {
			if (target instanceof L1PcInstance) {
				_calcType = NPC_PC;
				_npc = (L1NpcInstance) attacker;
				_targetPc = (L1PcInstance) target;
			} else {
				_calcType = NPC_NPC;
				_npc = (L1NpcInstance) attacker;
				_targetNpc = (L1NpcInstance) target;
			}
		}
	}

	@SuppressWarnings("unused")
	private int getSpellPower() {
		int spellPower = 0;
		if (_calcType == PC_PC || _calcType == PC_NPC) {
			spellPower = _pc.getAbility().getSp();
		} else if (_calcType == NPC_PC || _calcType == NPC_NPC) {
			spellPower = _npc.getAbility().getSp();
		}
		return spellPower;
	}

	private int getMagicLevel() {
		int magicLevel = 0;
		if (_calcType == PC_PC || _calcType == PC_NPC) {
			magicLevel = _pc.getAbility().getMagicLevel();
		} else if (_calcType == NPC_PC || _calcType == NPC_NPC) {
			magicLevel = _npc.getAbility().getMagicLevel();
		}
		return magicLevel;
	}

	private int getMagicBonus() {
		int magicBonus = 0;
		if (_calcType == PC_PC || _calcType == PC_NPC) {
			magicBonus = _pc.getAbility().getMagicBonus();
		} else if (_calcType == NPC_PC || _calcType == NPC_NPC) {
			magicBonus = _npc.getAbility().getMagicBonus();
		}
		return magicBonus;
	}

	private int getLawful() {
		int lawful = 0;
		if (_calcType == PC_PC || _calcType == PC_NPC) {
			lawful = _pc.getLawful();
		} else if (_calcType == NPC_PC || _calcType == NPC_NPC) {
			lawful = _npc.getLawful();
		}
		return lawful;
	}

	private int getTargetMr() {
		int mr = 0;
		if (_calcType == PC_PC || _calcType == NPC_PC) {
			mr = _targetPc.getResistance().getEffectedMrBySkill();
		} else {
			mr = _targetNpc.getResistance().getEffectedMrBySkill();
		}
		return mr;
	}

	/* ■■■■■■■■■■■■■■ 성공 판정 ■■■■■■■■■■■■■ */
	// ●●●● 확률계 마법의 성공 판정 ●●●●
	// 계산방법
	// 공격측 포인트：LV + ((MagicBonus * 3) * 마법 고유 계수)
	// 방어측 포인트：((LV / 2) + (MR * 3)) / 2
	// 공격 성공율：공격측 포인트 - 방어측 포인트
	public boolean calcProbabilityMagic(int skillId) {
		int probability = 0;
		boolean isSuccess = false;

		if (_pc != null && _pc.isGm()) {
			return true;
		}

		if (_calcType == PC_NPC && _targetNpc != null) {
			int npcId = _targetNpc.getNpcTemplate().get_npcId();
			if (npcId >= 45912 && npcId <= 45915 
					&& !_pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_HOLY_WATER)) {
				return false;
			}
			if (npcId == 45916 && !_pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_HOLY_MITHRIL_POWDER)) {
				return false;
			}
			if (npcId == 45941 && !_pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_HOLY_WATER_OF_EVA)) {
				return false;
			}
			if (!_pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_CURSE_BARLOG) && (npcId == 45752 || npcId == 45753)) {
				return false;
			}
			if (!_pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_CURSE_YAHEE)
					&& (npcId == 45675
							|| npcId == 81082
							|| npcId == 45625
							|| npcId == 45674
							|| npcId == 45685)){
				return false;
			}
			if (npcId >= 46068 && npcId <= 46091 && _pc.getGfxId().getTempCharGfx() == 6035) {
				return false;
			}
			if (npcId >= 46092 && npcId <= 46106 && _pc.getGfxId().getTempCharGfx() == 6034) {
				return false;
			}
		}

		if (!checkZone(skillId)) {
			return false;
		}

		if (skillId == CANCELLATION) {
			if (_calcType == PC_PC && _pc != null && _targetPc != null) {
				if (_pc.getId() == _targetPc.getId()) {
					return true;
				}
				if (_pc.getClanid() > 0
						&& (_pc.getClanid() == _targetPc.getClanid())) {
					return true;
				}
				if (_pc.isInParty()) {
					if (_pc.getParty().isMember(_targetPc)) {
						return true;
					}
				}

				if (CharPosUtil.getZoneType(_pc) == 1 || CharPosUtil.getZoneType(_targetPc) == 1) {
					return false;
				}
			}
			if (_calcType == PC_NPC
					|| _calcType == NPC_PC || _calcType == NPC_NPC) {
				return true;
			}
		}

		if (_calcType == PC_NPC && _targetNpc.getNpcTemplate().isCantResurrect()) { // 50렙 이상 npc 에게 아래 마법 안걸림:즉 보스몬스터에게 사용불가
			if (skillId == WEAPON_BREAK || skillId == SLOW
					|| skillId == CURSE_PARALYZE || skillId == MANA_DRAIN
					|| skillId == WEAKNESS || skillId == SILENCE
					|| skillId == DISEASE || skillId == DECAY_POTION
					|| skillId == MASS_SLOW || skillId == ENTANGLE
					|| skillId == ERASE_MAGIC
					|| skillId == AREA_OF_SILENCE || skillId == WIND_SHACKLE
					|| skillId == STRIKER_GALE || skillId == SHOCK_STUN
					|| skillId == FOG_OF_SLEEPING || skillId == ICE_LANCE
					|| skillId == FREEZING_BLIZZARD	|| skillId == POLLUTE_WATER
					|| skillId == RETURN_TO_NATURE
					|| skillId == THUNDER_GRAB || skillId == FREEZING_BREATH) {
				return false;
			}
		}

		// 아스바인드중은 WB, 왈가닥 세레이션 이외 무효
		if (_calcType == PC_PC || _calcType == NPC_PC) {
			if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(EARTH_BIND)) {
				_skill = SkillsTable.getInstance().getTemplate(skillId);
				if (skillId != WEAPON_BREAK && skillId != CANCELLATION // 확률계
						&& _skill.getType() != L1Skills.TYPE_HEAL // 힐 계
						&& _skill.getType() != L1Skills.TYPE_CHANGE) { // 버프계
					return false;
				}
			}
		} else {
			if (_targetNpc.getSkillEffectTimerSet().hasSkillEffect(EARTH_BIND)) {
				if (skillId != WEAPON_BREAK && skillId != CANCELLATION) {
					return false;  
				}   
			} 
		}

		// 100% 확률을 가지는 스킬
		if(skillId == SMASH || skillId == MIND_BREAK
				|| skillId == AM_BREAK || skillId == IllUSION_AVATAR){
			return true;
		}
		probability = calcProbability(skillId);
		int rnd = 0;
		
		switch(skillId){
		case DECAY_POTION: case SILENCE: case CURSE_PARALYZE:
		case CANCELLATION: case SLOW:  case DARKNESS:
		case WEAKNESS: case CURSE_POISON: case CURSE_BLIND:
		case WEAPON_BREAK: case MANA_DRAIN:
			if (_calcType == PC_PC) {
				rnd = _random.nextInt(_targetPc.getResistance().getEffectedMrBySkill()) + 1;			
			}else if(_calcType == PC_NPC){
				rnd = _random.nextInt(_targetNpc.getResistance().getEffectedMrBySkill()) + 1;
			}else{
				rnd = _random.nextInt(100)+1;
			}
			break;
		default:
			rnd = _random.nextInt(100) + 1;
			if (probability > 90) probability = 90;
		break;
		}	
		if (probability >= rnd) {
			isSuccess = true;
		} else {
			isSuccess = false;
		}

		if (!Config.ALT_ATKMSG) {
			return isSuccess;
		}
		if (Config.ALT_ATKMSG) {
			if ((_calcType == PC_PC || _calcType == PC_NPC) && !_pc.isGm()) {
				return isSuccess;
			}
			if ((_calcType == PC_PC || _calcType == NPC_PC) && !_targetPc.isGm()) {
				return isSuccess;
			}
		}

		String msg0 = "";
		String msg1 = "왜";
		String msg2 = "";
		String msg3 = "";
		String msg4 = "";

		if (_calcType == PC_PC || _calcType == PC_NPC) {
			msg0 = _pc.getName();
		} else if (_calcType == NPC_PC) { 
			msg0 = _npc.getName();
		}

		msg2 = "probability:" + probability + "%";
		if (_calcType == NPC_PC || _calcType == PC_PC) { 
			msg4 = _targetPc.getName();
		} else if (_calcType == PC_NPC) { 
			msg4 = _targetNpc.getName();
		}
		if (isSuccess == true) {
			msg3 = "성공";
		} else {
			msg3 = "실패";
		}

		if (_calcType == PC_PC || _calcType == PC_NPC) { 
			_pc.sendPackets(new S_ServerMessage(166, msg0, msg1, msg2, msg3,
					msg4));
		}
		if (_calcType == NPC_PC || _calcType == PC_PC) { 
			_targetPc.sendPackets(new S_ServerMessage(166, msg0, msg1, msg2,
					msg3, msg4)); 
		}

		return isSuccess;
	}

	private boolean checkZone(int skillId) {
		if (_pc != null && _targetPc != null) {
			if (CharPosUtil.getZoneType(_pc) == 1 || CharPosUtil.getZoneType(_targetPc) == 1) { 
				if (skillId == WEAPON_BREAK || skillId == SLOW
						|| skillId == CURSE_PARALYZE || skillId == MANA_DRAIN
						|| skillId == DARKNESS || skillId == WEAKNESS
						|| skillId == DISEASE || skillId == DECAY_POTION
						|| skillId == MASS_SLOW || skillId == ENTANGLE
						|| skillId == ERASE_MAGIC || skillId == EARTH_BIND
						|| skillId == AREA_OF_SILENCE || skillId == WIND_SHACKLE
						|| skillId == STRIKER_GALE || skillId == SHOCK_STUN
						|| skillId == FOG_OF_SLEEPING || skillId == ICE_LANCE
						|| skillId == FREEZING_BLIZZARD || skillId == HORROR_OF_DEATH
						|| skillId == POLLUTE_WATER || skillId == FEAR
						|| skillId == ELEMENTAL_FALL_DOWN || skillId == GUARD_BREAK
						|| skillId == RETURN_TO_NATURE || skillId == FREEZING_BREATH
						|| skillId == PHANTASM || skillId == JOY_OF_PAIN
						|| skillId == CONFUSION || skillId == SILENCE
						|| skillId == CURSE_POISON
						) {
					return false;
				}
			}
		}
		return true;
	}

	private int calcProbability(int skillId) {
		L1Skills l1skills = SkillsTable.getInstance().getTemplate(skillId);
		int attackLevel = 0;
		int defenseLevel = 0;
		int probability = 0;
		int attackInt = 0;
		int defenseMr = 0;

		if (_calcType == PC_PC || _calcType == PC_NPC) {
			attackLevel = _pc.getLevel();
			attackInt = _pc.getAbility().getTotalInt();
		} else {
			attackLevel = _npc.getLevel();
			attackInt = _npc.getAbility().getTotalInt();
		}

		if (_calcType == PC_PC || _calcType == NPC_PC) {
			defenseLevel = _targetPc.getLevel();
			defenseMr = _targetPc.getResistance().getEffectedMrBySkill();
		} else {
			defenseLevel = _targetNpc.getLevel();
			defenseMr = _targetNpc.getResistance().getEffectedMrBySkill();
			if (skillId == RETURN_TO_NATURE) {
				if (_targetNpc instanceof L1SummonInstance) {
					L1SummonInstance summon = (L1SummonInstance) _targetNpc;
					defenseLevel = summon.getMaster().getLevel();
				}
			}
		}

		switch(skillId){		
		case EARTH_BIND:case ERASE_MAGIC: 
			int levelbonus = 0;
			probability = (int) l1skills.getProbabilityValue();
			if ( attackLevel >= defenseLevel){
				levelbonus = (attackLevel-defenseLevel) * 2;
			}
			else{
				levelbonus = -(defenseLevel - attackLevel) * 2;
			}
			probability +=levelbonus;
			break;
		case ELEMENTAL_FALL_DOWN: case RETURN_TO_NATURE: case ENTANGLE:
		case AREA_OF_SILENCE: case WIND_SHACKLE:
		case STRIKER_GALE: case POLLUTE_WATER:
			probability = (int) (30 + (attackLevel - defenseLevel) * 2); 
			if (_calcType == PC_PC || _calcType == PC_NPC) {
				probability += 2 * _pc.getBaseMagicHitUp();
			}
			break;
		case SHOCK_STUN:			
			probability = (int)(l1skills.getProbabilityValue() + (attackLevel - defenseLevel) * 2.2);			
			if (_calcType == PC_PC || _calcType == PC_NPC) {
				probability += 2 * _pc.getBaseMagicHitUp();
			}
			break;
		case COUNTER_BARRIER:
			probability = l1skills.getProbabilityValue() + attackLevel - defenseLevel;  
			if (_calcType == PC_PC || _calcType == PC_NPC) {
				probability += 2 * _pc.getBaseMagicHitUp();
			}
			break;		 
		case MORTAL_BODY:
			probability = 10;
			if (_calcType == PC_PC || _calcType == PC_NPC) {
				probability += 2 * _pc.getBaseMagicHitUp();
			}
			break;
		case THUNDER_GRAB:
			probability = 60;
			if (_calcType == PC_PC || _calcType == PC_NPC) {
				probability += 2 * _pc.getBaseMagicHitUp();
			}
			break;
		case PANIC:
			probability = (int) (((l1skills.getProbabilityDice()) / 10D) * (attackLevel - defenseLevel)) + l1skills.getProbabilityValue();  
			if (_calcType == PC_PC || _calcType == PC_NPC) {
				probability += 2 * _pc.getBaseMagicHitUp();
			}
			break;
		case DECAY_POTION: case SILENCE: case CURSE_PARALYZE:
		case CANCELLATION: case SLOW:  case DARKNESS:
		case WEAKNESS: case CURSE_POISON: case CURSE_BLIND:
		case WEAPON_BREAK:
			if (attackInt > 25) attackInt = 25;
			probability = (int) ((attackInt - (defenseMr / 5.95)) * l1skills.getProbabilityValue());
			if (_calcType == PC_PC || _calcType == PC_NPC) {
				if (_pc.isElf())
					probability /= 3;		
			}
			if (probability < 0) probability = 0;			
			break;
		case MANA_DRAIN:
			if (attackInt > 25) attackInt = 25;
			probability = (int)(attackInt-(defenseMr/6.7)) * l1skills.getProbabilityValue();
			if (probability < 0) probability = 3 ;
			if (_calcType == PC_PC || _calcType == PC_NPC) {
				probability += _pc.getBaseMagicHitUp();
			}
			break;
		case GUARD_BREAK:
		case FEAR:
		case HORROR_OF_DEATH:
			Random random = new Random();
			int dice = l1skills.getProbabilityDice();
			int value = l1skills.getProbabilityValue();
			int diceCount = 0;
			diceCount = getMagicBonus() + getMagicLevel();

			if (diceCount < 1) {
				diceCount = 1;
			}

			for (int i = 0; i < diceCount; i++) {
				probability += (random.nextInt(dice) + 1 + value);
			}

			probability = probability * getLeverage() / 10;

			if (_calcType == PC_PC || _calcType == PC_NPC) {
				probability += 2 * _pc.getBaseMagicHitUp();
			}

			if (probability >= getTargetMr()) {
				probability = 100;				
			} else {
				probability = 0;
			}
			break;
		case CONFUSION: case BONE_BREAK:
		case PHANTASM:
			probability = 30;
			break;
		case JOY_OF_PAIN:
			probability = 80;
			break;
		default:{
			int dice1 = l1skills.getProbabilityDice();			
			int diceCount1 = 0;
			if (_calcType == PC_PC || _calcType == PC_NPC) {
				if (_pc.isWizard()) {
					diceCount1 = getMagicBonus() + getMagicLevel() + 1;
				} else if (_pc.isElf()) {
					diceCount1 = getMagicBonus() + getMagicLevel() - 1;
				} else if (_pc.isDragonknight()) {
					diceCount1 = getMagicBonus() + getMagicLevel();
				} else {
					diceCount1 = getMagicBonus() + getMagicLevel() - 1;
				}
			} else {
				diceCount1 = getMagicBonus() + getMagicLevel();
			}
			if (diceCount1 < 1) {
				diceCount1 = 1;
			}
			for (int i = 0; i < diceCount1; i++) {
				probability += (_random.nextInt(dice1) + 1);
			}
			probability = probability * getLeverage() / 10;
			probability -= getTargetMr();

			if (skillId == TAMING_MONSTER) {
				double probabilityRevision = 1;
				if ((_targetNpc.getMaxHp() * 1 / 4) > _targetNpc.getCurrentHp()) {
					probabilityRevision = 1.3;
				} else if ((_targetNpc.getMaxHp() * 2 / 4) > _targetNpc.getCurrentHp()) {
					probabilityRevision = 1.2;
				} else if ((_targetNpc.getMaxHp() * 3 / 4) > _targetNpc.getCurrentHp()) {
					probabilityRevision = 1.1;
				}
				probability *= probabilityRevision;
			}
		}
		break;
		}

		switch(skillId){
		case EARTH_BIND:
			if (_calcType == PC_PC || _calcType == NPC_PC) {	probability -= _targetPc.getResistance().getHold();	} break;
		case SHOCK_STUN: case 30081:
			if (_calcType == PC_PC || _calcType == NPC_PC) {	probability -= 2 * _targetPc.getResistance().getStun();	} break;
		case CURSE_PARALYZE:
			if (_calcType == PC_PC || _calcType == NPC_PC) {	probability -= _targetPc.getResistance().getPetrifaction();	} break;
		case FOG_OF_SLEEPING:
			if (_calcType == PC_PC || _calcType == NPC_PC) {	probability -= _targetPc.getResistance().getSleep();	} break;
		case ICE_LANCE:
		case FREEZING_BLIZZARD:
			if (_calcType == PC_PC || _calcType == NPC_PC) {	probability -= _targetPc.getResistance().getFreeze();	} break;
		case CURSE_BLIND:
		case DARKNESS:
		case DARK_BLIND:
			if (_calcType == PC_PC || _calcType == NPC_PC) {	probability -= _targetPc.getResistance().getBlind();}break;
		default: break;
		}
		return probability;
	}


	public int calcMagicDamage(int skillId) {
		int damage = 0;
		if (_calcType == PC_PC || _calcType == NPC_PC) {
			damage = calcPcMagicDamage(skillId);
		} else if (_calcType == PC_NPC || _calcType == NPC_NPC) {
			damage = calcNpcMagicDamage(skillId);
		}

		/** 파번은 마방 공식 제외 (임시) */
		if(skillId != FINAL_BURN){
			damage = calcMrDefense(damage);
		} else if(skillId == FINAL_BURN && _targetPc != null) { // final burn's temporary damage 
			if(_targetPc.getResistance().getEffectedMrBySkill() <= 50)		
				damage = _pc.getCurrentMp() + _random.nextInt(_pc.getCurrentMp()/2+1);
			else if(_targetPc.getResistance().getEffectedMrBySkill() > 50 && _targetPc.getResistance().getEffectedMrBySkill() < 100)
				damage = _pc.getCurrentMp() - _random.nextInt(_pc.getCurrentMp()/2+1);
			else if(_targetPc.getResistance().getEffectedMrBySkill() > 100)
				damage = _random.nextInt(_pc.getCurrentMp()/2+1);
		}


		if (_calcType == PC_PC || _calcType == NPC_PC) {
			if (damage > _targetPc.getCurrentHp()) {
				damage = _targetPc.getCurrentHp();
			}
		} else {
			if (damage > _targetNpc.getCurrentHp()) {
				damage = _targetNpc.getCurrentHp();
			}
		}
		return damage;
	}

	public int calcPcFireWallDamage() {
		int dmg = 0;
		double attrDeffence = calcAttrResistance(L1Skills.ATTR_FIRE);
		L1Skills l1skills = SkillsTable.getInstance().getTemplate(FIRE_WALL);
		dmg = (int) ((1.0 - attrDeffence) * l1skills.getDamageValue());

		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(ABSOLUTE_BARRIER)
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(ICE_LANCE)
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(FREEZING_BLIZZARD)
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(FREEZING_BREATH)
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(EARTH_BIND)
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(MOB_BASILL)
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(MOB_COCA)) {
			dmg = 0;
		}

		if (dmg < 0) { dmg = 0; }

		return dmg;
	}

	public int calcNpcFireWallDamage() {
		int dmg = 0;
		double attrDeffence = calcAttrResistance(L1Skills.ATTR_FIRE);
		L1Skills l1skills = SkillsTable.getInstance().getTemplate(FIRE_WALL);
		dmg = (int) ((1.0 - attrDeffence) * l1skills.getDamageValue());

		if (_targetNpc.getSkillEffectTimerSet().hasSkillEffect(ICE_LANCE)
				|| _targetNpc.getSkillEffectTimerSet().hasSkillEffect(FREEZING_BLIZZARD)
				|| _targetNpc.getSkillEffectTimerSet().hasSkillEffect(FREEZING_BREATH)
				|| _targetNpc.getSkillEffectTimerSet().hasSkillEffect(EARTH_BIND)
				|| _targetNpc.getSkillEffectTimerSet().hasSkillEffect(MOB_BASILL)
				|| _targetNpc.getSkillEffectTimerSet().hasSkillEffect(MOB_COCA)) {
			dmg = 0;
		}

		if (dmg < 0) { dmg = 0; }

		return dmg;
	}

	private int calcPcMagicDamage(int skillId) {
		int dmg = 0;
		if (skillId == FINAL_BURN) {
			if (_calcType == PC_PC || _calcType == PC_NPC) {
				dmg = _pc.getCurrentMp();
			} else {
				dmg = _npc.getCurrentMp();
			}
		} else {
			//			if (_calcType == PC_PC) {
			dmg = calcMagicDiceDamage(skillId);
			dmg = (dmg * getLeverage()) / 10;
			//			}else if (_calcType == NPC_PC) {
			//			dmg = calcMagicDiceDamage(skillId);
			//			dmg = (dmg * getLeverage()) / 10;	
			//			}
		}

		dmg -= _targetPc.getDamageReductionByArmor(); 

		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(SPECIAL_COOKING)){ // 스페셜요리에 의한 데미지 경감
			dmg -= 5;
		}

		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(REDUCTION_ARMOR)) {
			int targetPcLvl = _targetPc.getLevel();
			if (targetPcLvl < 50) {
				targetPcLvl = 50;
			}
			dmg -= (targetPcLvl - 50) / 5 + 1;
		}

		if (_calcType == NPC_PC) {
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
			//Object[] dollList = _targetPc.getDollList().values().toArray(); // 마법 인형에 의한 추가 방어
			//L1DollInstance doll = null;
			for (L1DollInstance doll : _targetPc.getDollList().values()) {
				//doll = (L1DollInstance) dollObject;
				dmg -= doll.getDamageReductionByDoll();
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
			dmg /= 1.5;
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(MOB_BASILL)
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(MOB_COCA)
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(ICE_LANCE)
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(FREEZING_BLIZZARD)
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(FREEZING_BREATH)
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(EARTH_BIND)){
			dmg = 0;
		}		
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(COUNTER_MIRROR)) {
			if (_calcType == PC_PC) {
				if (_targetPc.getAbility().getTotalWis() >= _random.nextInt(100)) {
					_pc.sendPackets(new S_DoActionGFX(_pc.getId(), ActionCodes.ACTION_Damage));
					Broadcaster.broadcastPacket(_pc, new S_DoActionGFX(_pc.getId(), ActionCodes.ACTION_Damage));
					_targetPc.sendPackets(new S_SkillSound(_targetPc.getId(), 4395));
					Broadcaster.broadcastPacket(_targetPc, new S_SkillSound(_targetPc.getId(), 4395));
					_pc.receiveDamage(_targetPc, dmg, false);
					dmg = 0;
					_targetPc.getSkillEffectTimerSet().killSkillEffectTimer(COUNTER_MIRROR);
				}
			} else if (_calcType == NPC_PC) {
				int npcId = _npc.getNpcTemplate().get_npcId();
				if (npcId == 45681 || npcId == 45682 || npcId == 45683 || npcId == 45684) {
				} else if (!_npc.getNpcTemplate().get_IsErase()) {
				} else {
					if (_targetPc.getAbility().getTotalWis() >= _random.nextInt(100)) {
						Broadcaster.broadcastPacket(_npc, new S_DoActionGFX(_npc.getId(), ActionCodes.ACTION_Damage));
						_targetPc.sendPackets(new S_SkillSound(_targetPc.getId(), 4395));
						Broadcaster.broadcastPacket(_targetPc, new S_SkillSound(_targetPc.getId(), 4395));
						_npc.receiveDamage(_targetPc, dmg);
						dmg = 0;
						_targetPc.getSkillEffectTimerSet().killSkillEffectTimer(COUNTER_MIRROR);
					}
				}
			}
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(FEATHER_BUFF_A)) {
			dmg -= 3;
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(FEATHER_BUFF_B)) {
			dmg -= 2;
		}

		if (dmg < 0) { dmg = 0; }

		return dmg;
	}

	private int calcNpcMagicDamage(int skillId) {
		int dmg = 0;
		if (skillId == FINAL_BURN) {
			if (_calcType == PC_PC || _calcType == PC_NPC) {
				dmg = _pc.getCurrentMp();
			} else {
				dmg = _npc.getCurrentMp();
			}
		} else {
			dmg = calcMagicDiceDamage(skillId);
			dmg = (dmg * getLeverage()) / 10;
		}
		if (_targetNpc.getNpcId() == 45640){
			dmg /= 2;
		}
		if (_calcType == PC_NPC) { 
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
		}

		if (_calcType == PC_NPC && _targetNpc != null) {
			int npcId = _targetNpc.getNpcTemplate().get_npcId();
			if (npcId >= 45912 && npcId <= 45915 && !_pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_HOLY_WATER)) {
				dmg = 0;
			}
			if (npcId == 45916 && !_pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_HOLY_MITHRIL_POWDER)) {
				dmg = 0;
			}
			if (npcId == 45941&& !_pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_HOLY_WATER_OF_EVA)) {
				dmg = 0;
			}
			if (!_pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_CURSE_BARLOG) && (npcId == 45752 || npcId == 45753)) {
				dmg = 0;
			}
			if (!_pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_CURSE_YAHEE)
					&& (npcId == 45675
							|| npcId == 81082
							|| npcId == 45625
							|| npcId == 45674
							|| npcId == 45685)){
				dmg = 0;
			}
			if (npcId >= 46068 && npcId <= 46091 && _pc.getGfxId().getTempCharGfx() == 6035) {
				dmg = 0;
			}
			if (npcId >= 46092 && npcId <= 46106 && _pc.getGfxId().getTempCharGfx() == 6034) {
				dmg = 0;
			}			
		}
		return dmg;
	}

	private int calcMagicDiceDamage(int skillId) {
		L1Skills l1skills = SkillsTable.getInstance().getTemplate(skillId);
		int dice = l1skills.getDamageDice();
		int diceCount = l1skills.getDamageDiceCount();
		int value = l1skills.getDamageValue();
		int magicDamage = 0;
		int charaIntelligence = 0;
		Random random = new Random();

		for (int i = 0; i < diceCount; i++) {
			magicDamage += (_random.nextInt(dice) + 1);
		}
		magicDamage += value;

		if (_calcType == PC_PC || _calcType == PC_NPC) {
			int weaponAddDmg = 0; 
			L1ItemInstance weapon = _pc.getWeapon();
			if (weapon != null) {
				weaponAddDmg = weapon.getItem().getMagicDmgModifier();
			}
			magicDamage += weaponAddDmg;
			magicDamage += random.nextInt(_pc.ability.getInt()) * 0.5;
		}

		if (_calcType == PC_PC || _calcType == PC_NPC) {
			//int spByItem = _pc.getAbility().getSp() - _pc.getAbility().getTrueSp(); 
			charaIntelligence = _pc.getAbility().getSp();
		} else if (_calcType == NPC_PC || _calcType == NPC_NPC) {
			int spByItem = _npc.getAbility().getSp() - _npc.getAbility().getTrueSp();
			charaIntelligence = _npc.getAbility().getTotalInt() + spByItem - 12;
		}
		if (charaIntelligence < 1) {
			charaIntelligence = 1;
		}

		double attrDeffence = calcAttrResistance(l1skills.getAttr());

		double coefficient = (1.0 - attrDeffence + charaIntelligence * 3.2 / 32.0);
		if (coefficient < 0) {
			coefficient = 0;
		}

		magicDamage *= coefficient;
		
		/** 치명타 발생 부분 추가 - By 시니 -*/

		double criticalCoefficient = 1.5;
		int rnd = random.nextInt(100) + 1;
		if (_calcType == PC_PC || _calcType == PC_NPC) {
			int propCritical  = CalcStat.calcBaseMagicCritical(_pc.getType(), _pc.ability.getBaseInt());
			if(criticalOccur(propCritical)){
				magicDamage *= 1.5;
			}	
			
		} else if (_calcType == NPC_PC || _calcType == NPC_NPC) {
			if (rnd <= 15) {
				magicDamage *= criticalCoefficient;
			}
		}

		if (_calcType == PC_PC || _calcType == PC_NPC) {
			magicDamage += _pc.getBaseMagicDmg();
		}
		return magicDamage;
	}

	public int calcHealing(int skillId) {		
		L1Skills l1skills = SkillsTable.getInstance().getTemplate(skillId);
		int dice = l1skills.getDamageDice();
		int value = l1skills.getDamageValue();
		int magicDamage = 0;

		int magicBonus = getMagicBonus();
		if (magicBonus > 10) {
			magicBonus = 10;
		}

		int diceCount = value + magicBonus;
		for (int i = 0; i < diceCount; i++) {
			magicDamage += (_random.nextInt(dice) + 1);
		}

		double alignmentRevision = 1.0;
		if (getLawful() > 0) {
			alignmentRevision += (getLawful() / 32768.0);
		}

		magicDamage *= alignmentRevision;

		magicDamage = (magicDamage * getLeverage()) / 10;

		return magicDamage;
	}

	/**
	 * MR에 의한 마법 데미지 감소를 처리 한다
	 * 수정일자 : 2009.04.15
	 * 수정자 : 손영신
	 * @param dmg
	 * @return dmg
	 */

	public int calcMrDefense(int dmg) {

		int MagicResistance = 0 ;			//마법저항
		int RealMagicResistance = 0; 		//적용되는 마법저항값
		double calMr = 0.00D;				//마방계산
		double baseMr = 0.00D;
		if (_calcType == PC_PC || _calcType == NPC_PC){
			MagicResistance = _targetPc.getResistance().getEffectedMrBySkill();
		} else{
			MagicResistance = _targetNpc.getResistance().getEffectedMrBySkill();
		}

		RealMagicResistance = MagicResistance - _random.nextInt(5) + 1;

		if(_calcType == PC_PC){
			baseMr = (_random.nextInt(1000) + 98000) / 100000D;

			if(MagicResistance <= 100 ){
				calMr = baseMr - (MagicResistance * 470)/ 100000D; 	
			} else if (MagicResistance>100) {
				calMr = baseMr - (MagicResistance * 470)/ 100000D + ((MagicResistance - 100) * 0.004);
			}					
		}
		else{
			calMr = (200 - RealMagicResistance) / 250.00D;
		}

		dmg *= calMr;  
		
		if(dmg < 0){ dmg = 0; }

		return dmg;
	}

	private boolean criticalOccur(int prop)
	{
		boolean ok = false;
		int num = _random.nextInt(100) + 1;
		
		if(prop == 0)
		{
			return false;
		}
		if (num <= prop)
		{
			ok = true;
		}
		return ok;
	}
	private double calcAttrResistance(int attr) {
		int resist = 0;
		if (_calcType == PC_PC || _calcType == NPC_PC) {
			switch(attr){
			case L1Skills.ATTR_EARTH: resist = _targetPc.getResistance().getEarth(); break;
			case L1Skills.ATTR_FIRE: resist = _targetPc.getResistance().getFire(); break;
			case L1Skills.ATTR_WATER: resist = _targetPc.getResistance().getWater(); break;
			case L1Skills.ATTR_WIND: resist = _targetPc.getResistance().getWind(); break;
			}
		} else if (_calcType == PC_NPC || _calcType == NPC_NPC) {
		}

		int resistFloor = (int) (0.32 * Math.abs(resist));
		if (resist >= 0) {
			resistFloor *= 1;
		} else {
			resistFloor *= -1;
		}

		double attrDeffence = resistFloor / 32.0;

		return attrDeffence;
	}


	public void commit(int damage, int drainMana) {
		if (_calcType == PC_PC || _calcType == NPC_PC) {
			commitPc(damage, drainMana);
		} else if (_calcType == PC_NPC || _calcType == NPC_NPC) {
			commitNpc(damage, drainMana);
		}

		if (!Config.ALT_ATKMSG) {
			return;
		}
		if (Config.ALT_ATKMSG) {
			if ((_calcType == PC_PC || _calcType == PC_NPC) && !_pc.isGm()) {
				return;
			}
			if ((_calcType == PC_PC || _calcType == NPC_PC) && !_targetPc.isGm()) {
				return;
			}
		}

		String msg0 = "";
		String msg1 = "왜";
		String msg2 = "";
		String msg3 = "";
		String msg4 = "";

		if (_calcType == PC_PC || _calcType == PC_NPC) {
			msg0 = _pc.getName();
		} else if (_calcType == NPC_PC) { 
			msg0 = _npc.getName();
		}

		if (_calcType == NPC_PC || _calcType == PC_PC) {
			msg4 = _targetPc.getName();
			msg2 = "THP" + _targetPc.getCurrentHp();
		} else if (_calcType == PC_NPC) { 
			msg4 = _targetNpc.getName();
			msg2 = "THp" + _targetNpc.getCurrentHp();
		}

		msg3 = damage + "주었다";

		if (_calcType == PC_PC || _calcType == PC_NPC) { 
			_pc.sendPackets(new S_ServerMessage(166, msg0, msg1, msg2, msg3,
					msg4)); 
		}
		if (_calcType == NPC_PC || _calcType == PC_PC) { 
			_targetPc.sendPackets(new S_ServerMessage(166, msg0, msg1, msg2,
					msg3, msg4)); 
		}
	}

	private void commitPc(int damage, int drainMana) {
		if (_calcType == PC_PC) {
			if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(ABSOLUTE_BARRIER)
					|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(ICE_LANCE)
					|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(FREEZING_BLIZZARD)
					|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(FREEZING_BREATH)
					|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(EARTH_BIND)
					|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(MOB_BASILL)
					|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(MOB_COCA)) {
				damage = 0;
				drainMana = 0;
			}
			if (drainMana > 0 && _targetPc.getCurrentMp() > 0) {
				if (drainMana > _targetPc.getCurrentMp()) {
					drainMana = _targetPc.getCurrentMp();
				}
				int newMp = _pc.getCurrentMp() + drainMana;
				_pc.setCurrentMp(newMp);
			}
			_targetPc.receiveManaDamage(_pc, drainMana);
			_targetPc.receiveDamage(_pc, damage, true);
		} else if (_calcType == NPC_PC) {
			_targetPc.receiveDamage(_npc, damage, true);
		}
	}

	private void commitNpc(int damage, int drainMana) {
		if (_calcType == PC_NPC) {
			if (_targetNpc.getSkillEffectTimerSet().hasSkillEffect(ICE_LANCE)
					|| _targetNpc.getSkillEffectTimerSet().hasSkillEffect(FREEZING_BLIZZARD)
					|| _targetNpc.getSkillEffectTimerSet().hasSkillEffect(FREEZING_BREATH)
					|| _targetNpc.getSkillEffectTimerSet().hasSkillEffect(EARTH_BIND)
					|| _targetNpc.getSkillEffectTimerSet().hasSkillEffect(MOB_BASILL)
					|| _targetNpc.getSkillEffectTimerSet().hasSkillEffect(MOB_COCA)) {
				damage = 0;
				drainMana = 0;
			}
			if (drainMana > 0) {
				int drainValue = _targetNpc.drainMana(drainMana);
				int newMp = _pc.getCurrentMp() + drainValue;
				_pc.setCurrentMp(newMp);
			}
			_targetNpc.ReceiveManaDamage(_pc, drainMana);
			_targetNpc.receiveDamage(_pc, damage);
		} else if (_calcType == NPC_NPC) {
			_targetNpc.receiveDamage(_npc, damage);
		}
	}
}
