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

import java.util.HashMap;
import java.util.Map;

import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.model.poison.L1Poison;
import bone.server.server.model.skill.L1SkillId;
import bone.server.server.serverpackets.S_Poison;
import bone.server.server.serverpackets.S_RemoveObject;
import bone.server.server.utils.IntRange;

//Referenced classes of package bone.server.server.model:
//L1Object, Die, L1PcInstance, L1MonsterInstance,
//L1World, ActionFailed

public class L1Character extends L1Object {
	private static final long serialVersionUID = 1L;
	
	// �ɸ��� �⺻
	//private BasicProperty basic;
	
	private String _name; 
	private String _title;
	
	private int _level; 
	private int _exp;

	private int _lawful; 
	private int _karma;
		
	private int _currentHp;
	private int _trueMaxHp;
	private short _maxHp;
	
	private int _currentMp;
	private int _trueMaxMp;
	private short _maxMp;
	
	private L1Poison _poison = null;
	private boolean _paralyzed;
	private boolean _sleeped;
	private L1Paralysis _paralysis;
	private boolean _isDead; 

	protected GfxId gfx;					// �ɸ��� �׷��� ID
	private MoveState moveState;		// �̵��ӵ�, �ٶ󺸴� ����
	protected Light light;				// �ɸ��� ����  ��
	protected Ability ability; 			// �ɷ�ġ, SP, MagicBonus
	protected Resistance resistance;	// ���� (����, ��, ��, �ٶ�, ��, ����, ����, ����, ��ȭ)
	protected AC ac;					// AC ���
		
	private NearObjects nearObjects;	// ���� ��ü �� �÷��̾��
	private SkillEffectTimerSet skillEffectTimerSet;	// ��ų Ÿ�̸� 

	// �𸣴°�
	private boolean _isSkillDelay;
	private int _addAttrKind; 
	private int actionStatus; 

	// ������
	private int _dmgup; 
	private int _trueDmgup; 
	private int _bowDmgup; 
	private int _trueBowDmgup; 
	private int _hitup; 
	private int _trueHitup; 
	private int _bowHitup;
	private int _trueBowHitup; 
	
	private final Map<Integer, L1ItemDelay.ItemDelayTimer> _itemdelay = new HashMap<Integer, L1ItemDelay.ItemDelayTimer>();

	public L1Character() {
		_level = 1;
		ability = new Ability(this); 
		resistance = new Resistance(this);
		ac = new AC();
		moveState = new MoveState();
		light = new Light(this);
		nearObjects = new NearObjects();
		gfx = new GfxId();
		skillEffectTimerSet = new SkillEffectTimerSet(this);
	}

	/**
	 * ĳ���͸� ��Ȱ��Ų��.
	 * 
	 * @param hp
	 *            ��Ȱ ���� HP
	 */
	public void resurrect(int hp) {
		if (!isDead()) return;
		if (hp <= 0)   hp = 1;

		setCurrentHp(hp);
		setDead(false);
		setActionStatus(0);
		L1PolyMorph.undoPoly(this);

		for (L1PcInstance pc : L1World.getInstance().getRecognizePlayer(this)) {
			pc.sendPackets(new S_RemoveObject(this));
			pc.getNearObjects().removeKnownObject(this);
			pc.updateObject();
		}
	}

	/**
	 * ĳ������ ������ HP�� �����ش�.
	 * 
	 * @return ������ HP
	 */
	public int getCurrentHp() {	return _currentHp; }

	/**
	 * ĳ������ HP�� �����Ѵ�.
	 * 
	 * @param i ĳ������ ���ο� HP
	 */
	public void setCurrentHp(int i) {
		if (i >= getMaxHp()) {
			i = getMaxHp();
		}
		if (i < 0) i = 0;

		_currentHp = i;
	}

	/**
	 * ĳ������ ������ MP�� �����ش�.
	 * 
	 * @return ������ MP
	 */
	public int getCurrentMp() {
		return _currentMp;
	}

	/**
	 * ĳ������ MP�� �����Ѵ�.
	 * 
	 * @param i ĳ������ ���ο� MP
	 */
	public void setCurrentMp(int i) {
		if (i >= getMaxMp()) {
			i = getMaxMp();
		}
		if (i < 0) i = 0;

		_currentMp = i;
	}

	/**
	 * ĳ������ ����¸� �����ش�.
	 * 
	 * @return ����¸� ��Ÿ���� ��. ������̸� true.
	 */
	public boolean isSleeped() {
		return _sleeped;
	}

	/**
	 * ĳ������ ����¸� �����Ѵ�.
	 * 
	 * @param sleeped
	 *            ����¸� ��Ÿ���� ��. ������̸� true.
	 */
	public void setSleeped(boolean sleeped) {
		_sleeped = sleeped;
	}

	/**
	 * ĳ������ ���� ���¸� �����ش�.
	 * 
	 * @return ���� ���¸� ��Ÿ���� ��. ���� �����̸� true.
	 */
	public boolean isParalyzed() {
		return _paralyzed;
	}

	/**
	 * ĳ������ ���� ���¸� �����ش�.
	 * 
	 * @return ���� ���¸� ��Ÿ���� ��. ���� �����̸� true.
	 */
	public void setParalyzed(boolean paralyzed) {
		_paralyzed = paralyzed;
	}

	public L1Paralysis getParalysis() {
		return _paralysis;
	}

	public void setParalaysis(L1Paralysis p) {
		_paralysis = p;
	}

	public void cureParalaysis() {
		if (_paralysis != null) {
			_paralysis.cure();
		}
	}

	/**
	 * ĳ������ ����� �����ش�.
	 * 
	 * @return ĳ������ ����� ��Ÿ����, L1Inventory ������Ʈ.
	 */
	public L1Inventory getInventory() {
		return null;
	}

	/**
	 * ĳ���Ϳ�, skill delay �߰�
	 * 
	 * @param flag
	 */
	public void setSkillDelay(boolean flag) {
		_isSkillDelay = flag;
	}

	/**
	 * ĳ������ �� ���¸� �����ش�.
	 * 
	 * @return ��ų �������ΰ�.
	 */
	public boolean isSkillDelay() {
		return _isSkillDelay;
	}

	/**
	 * ĳ���Ϳ�, Item delay �߰�
	 * 
	 * @param delayId
	 *            ������ ���� ID.  ����� �������̸� 0, �κ�����Ƽũ��ũ, �ٸ��α׺����ũ��ũ�̸� 1.
	 * @param timer
	 *            ���� �ð��� ��Ÿ����, L1ItemDelay.ItemDelayTimer ������Ʈ.
	 */
	public void addItemDelay(int delayId, L1ItemDelay.ItemDelayTimer timer) {
		_itemdelay.put(delayId, timer);
	}

	/**
	 * ĳ���ͷκ���, Item delay ����
	 * 
	 * @param delayId
	 *            ������ ���� ID.  ����� �������̸� 0, �κ�����Ƽũ��ũ, �ٸ��α׺����ũ��ũ�̸� 1.
	 */
	public void removeItemDelay(int delayId) {
		_itemdelay.remove(delayId);
	}

	/**
	 * ĳ���Ϳ�, Item delay �� ������
	 * 
	 * @param delayId
	 *            �����ϴ� ������ ���� ID.  ����� �������̸� 0, �κ�����Ƽũ��ũ, �ٸ��α׺����
	 *            Ŭ��ũ�̸� 1.
	 * @return ������ ������ ������ true, ������ false.
	 */
	public boolean hasItemDelay(int delayId) {
		return _itemdelay.containsKey(delayId);
	}

	/**
	 * ĳ������ item delay �ð��� ��Ÿ����, L1ItemDelay.ItemDelayTimer�� �����ش�.
	 * 
	 * @param delayId
	 *            �����ϴ� ������ ���� ID.  ����� �������̸� 0, �κ�����Ƽũ��ũ, �ٸ��α׺����
	 *            Ŭ��ũ�̸� 1.
	 * @return ������ ���� �ð��� ��Ÿ����, L1ItemDelay.ItemDelayTimer.
	 */
	public L1ItemDelay.ItemDelayTimer getItemDelayTimer(int delayId) {
		return _itemdelay.get(delayId);
	}

	/**
	 * ĳ���Ϳ�, ���� �߰��Ѵ�.
	 * 
	 * @param poison
	 *            ���� ��Ÿ����, L1Poison ������Ʈ.
	 */
	public void setPoison(L1Poison poison) {
		_poison = poison;
	}

	/**
	 * ĳ������ ���� ġ���Ѵ�.
	 */
	public void curePoison() {
		if (_poison == null) {
			return;
		}
		_poison.cure();
	}

	/**
	 * ĳ������ �����¸� �����ش�.
	 * 
	 * @return ĳ������ ���� ��Ÿ����, L1Poison ������Ʈ.
	 */
	public L1Poison getPoison() {
		return _poison;
	}

	/**
	 * ĳ���Ϳ� ���� ȿ���� �ΰ��Ѵ�
	 * 
	 * @param effectId
	 * @see S_Poison#S_Poison(int, int)
	 */
	public void setPoisonEffect(int effectId) {
		Broadcaster.broadcastPacket(this, new S_Poison(getId(), effectId));
	}

	public int getExp() 		{ return _exp; }
	public void setExp(int exp) { _exp = exp;  }

	public String getName() 		{ return _name; }
	public void setName(String s) 	{ _name = s; 	}

	public String getTitle() { return _title; }
	public void setTitle(String s) { _title = s; }
	
	public synchronized int getLevel() 				{ return _level; 		}
	public synchronized void setLevel(long level) 	{ _level = (int) level; }

	public short getMaxHp() 	 { return _maxHp; 			 }
	public void addMaxHp(int i)  { setMaxHp(_trueMaxHp + i); }
	public void setMaxHp(int hp) {
		_trueMaxHp = hp;
		_maxHp = (short) IntRange.ensure(_trueMaxHp, 1, 32767);
		_currentHp = Math.min(_currentHp, _maxHp);
	}

	public short getMaxMp() 	 { return _maxMp; }
	public void setMaxMp(int mp) {
		_trueMaxMp = mp;
		_maxMp = (short) IntRange.ensure(_trueMaxMp, 0, 32767);
		_currentMp = Math.min(_currentMp, _maxMp);
	}

	public void addMaxMp(int i) { setMaxMp(_trueMaxMp + i); 		 }
	public void healHp(int pt)  { setCurrentHp(getCurrentHp() + pt); }
	
	public int getAddAttrKind() 	  { return _addAttrKind; }
	public void setAddAttrKind(int i) { _addAttrKind = i; 	 }

	public int getDmgup() {	return _dmgup; } 
	public void addDmgup(int i) {
		_trueDmgup += i;
		if      (_trueDmgup >=  127) { _dmgup = 127;		} 
		else if (_trueDmgup <= -128) { _dmgup = -128; 		} 
		else 						 { _dmgup = _trueDmgup; }
	}

	public int getBowDmgup() { return _bowDmgup; } 
	public void addBowDmgup(int i) {
		_trueBowDmgup += i;
		if 		(_trueBowDmgup >=  127) { _bowDmgup = 127;			 }	 
		else if (_trueBowDmgup <= -128) { _bowDmgup = -128;			 } 
		else 							{ _bowDmgup = _trueBowDmgup; }
	}

	public int getHitup() {	return _hitup; } 
	public void addHitup(int i) {
		_trueHitup += i;
		if 		(_trueHitup >=  127) { _hitup = 127;  		}	 
		else if (_trueHitup <= -128) { _hitup = -128; 		} 
		else 						 { _hitup = _trueHitup;	}
	}

	public int getBowHitup() { return _bowHitup; } 
	public void addBowHitup(int i) {
		_trueBowHitup += i;
		if 		(_trueBowHitup >=  127) { _bowHitup = 127;  		 } 
		else if (_trueBowHitup <= -128) { _bowHitup = -128;			 } 
		else 							{ _bowHitup = _trueBowHitup; }
	}
	
	public boolean isDead() { return _isDead; }
	public void setDead(boolean flag) { _isDead = flag;	}

	public int getActionStatus() { return actionStatus; }
	public void setActionStatus(int i) { actionStatus = i;	}


	public int getLawful() { return _lawful; }
	public void setLawful(int i) { _lawful = i; }

	public synchronized void addLawful(int i) {
		_lawful += i;
		if 		(_lawful >  32767) { _lawful = 32767;  } 
		else if (_lawful < -32768) { _lawful = -32768; }
	}
	public int checkMove() {
		if (getMap().isPassable(getLocation())) {
			return 1;
		} else {
			return 0;
		}
	}
	/** Kill & Death �ý��� **/
	private int _Kills;

	public int getKills() {
		return _Kills;
	}
	public void setKills(int Kills) {
		_Kills = Kills;
	} 
	private int _Deaths;

	public int getDeaths() {
		return _Deaths;
	}
	public void setDeaths(int Deaths) {
		_Deaths = Deaths;
	}
	/** ĳ������ ��ȣ���� �����ش�.	 */
	public int getKarma() {	return _karma; }
	/** ĳ������ ��ȣ���� �����Ѵ�.	 */
	public void setKarma(int karma) { _karma = karma; }

	public GfxId getGfxId()				{ return gfx;			}
	public NearObjects getNearObjects()	{ return nearObjects;	}
	public Light getLight() 			{ return light; 		} 
	public Ability getAbility() 		{ return ability; 		}
	public Resistance getResistance() 	{ return resistance; 	}
	public AC getAC()					{ return ac;			}
	public MoveState getMoveState()		{ return moveState;		}
	public SkillEffectTimerSet getSkillEffectTimerSet() { return skillEffectTimerSet; }
	
	public boolean isInvisble() {
		return (getSkillEffectTimerSet().hasSkillEffect(L1SkillId.INVISIBILITY) || 
				getSkillEffectTimerSet().hasSkillEffect(L1SkillId.BLIND_HIDING));
	}
}
