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

package bone.server.server.model.Instance;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ScheduledFuture;

import bone.server.server.GeneralThreadPool;
import bone.server.server.ObjectIdFactory;
import bone.server.server.model.Broadcaster;
import bone.server.server.model.L1Character;
import bone.server.server.model.L1World;
import bone.server.server.model.poison.L1DamagePoison;
import bone.server.server.model.skill.L1SkillUse;
import bone.server.server.serverpackets.S_DollPack;
import bone.server.server.serverpackets.S_SkillSound;
import bone.server.server.templates.L1Npc;

public class L1DollInstance extends L1NpcInstance {
	private static final long serialVersionUID = 1L;

	public static final int DOLLTYPE_BUGBEAR = 0;
	public static final int DOLLTYPE_SUCCUBUS = 1;
	public static final int DOLLTYPE_WAREWOLF = 2;
	public static final int DOLLTYPE_STONEGOLEM = 3;
	public static final int DOLLTYPE_ELDER = 4;
	public static final int DOLLTYPE_CRUSTACEA = 5;
	public static final int DOLLTYPE_SEADANCER = 6;
	public static final int DOLLTYPE_SNOWMAN = 7;
	public static final int DOLLTYPE_COCATRIS = 8;
	public static final int DOLLTYPE_DRAGON_M = 9;
	public static final int DOLLTYPE_DRAGON_W = 10;
	public static final int DOLLTYPE_HIGH_DRAGON_M = 11;
	public static final int DOLLTYPE_HIGH_DRAGON_W = 12;	
	public static final int DOLLTYPE_LAMIA = 13;
	public static final int DOLLTYPE_HELPER = 20;
	public static final int DOLLTYPE_SPATOI = 21;
	
	//public static final int DOLL_TIME = 1800000;

	private static Random _random = new Random();
	private int _dollType;
	private int _itemObjId;
	private ScheduledFuture<?> _future = null;
	
	private static int Buff[] = {26, 42, 43, 79};	//덱스, 힘, 헤이, 어벤

	// 타겟이 없는 경우의 처리
	@Override
	public boolean noTarget() {
		if (_master.isDead()) {
			deleteDoll();
			return true;
		} else if (_master != null && _master.getMapId() == getMapId()) {
			if (getLocation().getTileLineDistance(_master.getLocation()) > 2) {
				int dir = moveDirection(_master.getX(), _master.getY());
				if (dir == -1) {
					teleport(_master.getX(), _master.getY(), getMoveState().getHeading());
					setDirectionMove(dir);
					setSleepTime(calcSleepTime(getPassispeed(), MOVE_SPEED));
				} else {
					setDirectionMove(dir);
					setSleepTime(calcSleepTime(getPassispeed(), MOVE_SPEED));
				}
			}
		} else {
			deleteDoll();
			return true;
		}
		return false;
	}

	// 시간 계측용
	class DollTimer implements Runnable {
		@Override
		public void run() {
			if (_destroyed) { // 이미 파기되어 있지 않은가 체크
				return;
			}
			deleteDoll();
		}
	}
	
	class HelpTimer implements Runnable {
		@Override
		public void run() {
			if (_destroyed) { // 이미 파기되어 있지 않은가 체크
				return;
			}
			getHelperAction();
		}
	}

	public L1DollInstance(L1Npc template, L1PcInstance master, int dollType, int itemObjId, int dollTime) {
		super(template);
		setId(ObjectIdFactory.getInstance().nextId());

		setDollType(dollType);
		setItemObjId(itemObjId);
		GeneralThreadPool.getInstance().schedule(new DollTimer(), dollTime);

		setMaster(master);
		setX(master.getX() + _random.nextInt(5) - 2);
		setY(master.getY() + _random.nextInt(5) - 2);
		setMap(master.getMapId());
		getMoveState().setHeading(5);
		setLightSize(template.getLightSize());

		L1World.getInstance().storeObject(this);
		L1World.getInstance().addVisibleObject(this);
		for (L1PcInstance pc : L1World.getInstance().getRecognizePlayer(this)) {
			onPerceive(pc);
		}
		master.addDoll(this);
		if (!isAiRunning()) {
			startAI();
		}
		if (isMpRegeneration()) {
			master.startMpRegenerationByDoll();
		}
		if (isHpRegeneration()) {
			master.startHpRegenerationByDoll();
		}
		
		int type = getDollType();
		if (type == DOLLTYPE_SNOWMAN) {
			master.getAC().addAc(-3);
			_master.getResistance().addFreeze(7);
		}
		if (type == DOLLTYPE_COCATRIS){
			_master.addBowHitupByDoll(1);
			_master.addBowDmgupByDoll(1);
		}
		if (type == DOLLTYPE_DRAGON_M
				|| type == DOLLTYPE_DRAGON_W
				|| type == DOLLTYPE_HIGH_DRAGON_M
				|| type == DOLLTYPE_HIGH_DRAGON_W){
			_master.addMpr(5);
		}
		if (type == DOLLTYPE_LAMIA){
			_master.addMpr(4);
		}
		if (type == DOLLTYPE_SPATOI){
			_master.addDmgup(2);
			_master.getResistance().addStun(10);
		}
		startHelpTimer();
	}

	public void deleteDoll() {
		if (isMpRegeneration()) {
			((L1PcInstance) _master).stopMpRegenerationByDoll();			
		}else if (isHpRegeneration()) {
			((L1PcInstance) _master).stopHpRegenerationByDoll();
		}
		int type = getDollType();
		if (type == DOLLTYPE_SNOWMAN){ 
			_master.getAC().addAc(3);
			_master.getResistance().addFreeze(-7);
		}
		if (type == DOLLTYPE_COCATRIS){
			_master.addBowHitupByDoll(-1);
			_master.addBowDmgupByDoll(-1);
		}
		if (type == DOLLTYPE_DRAGON_M
				|| type == DOLLTYPE_DRAGON_W
				|| type == DOLLTYPE_HIGH_DRAGON_M
				|| type == DOLLTYPE_HIGH_DRAGON_W){
			_master.addMpr(-5);
		}
		if (type == DOLLTYPE_LAMIA){
			_master.addMpr(-4);
		}
		if (type == DOLLTYPE_SPATOI){
			_master.addDmgup(-2);
			_master.getResistance().addStun(-10);
		}
		stopHelpTimer();
		_master.sendPackets(new S_SkillSound(getId(), 5936));
		Broadcaster.broadcastPacket(_master, new S_SkillSound(getId(), 5936));
		_master.getDollList().remove(getId());
		deleteMe();
	}

	@Override
	public void onPerceive(L1PcInstance perceivedFrom) {
		perceivedFrom.getNearObjects().addKnownObject(this);
		perceivedFrom.sendPackets(new S_DollPack(this, perceivedFrom));
	}

	@Override
	public void onItemUse() {
		if (!isActived()) {
			// 100%의 확률로 헤이 파업 일부 사용
			useItem(USEITEM_HASTE, 100);
		}
	}

	@Override
	public void onGetItem(L1ItemInstance item) {
		if (getNpcTemplate().get_digestitem() > 0) {
			setDigestItem(item);
		}
		if (Arrays.binarySearch(haestPotions, item.getItem().getItemId()) >= 0) {
			useItem(USEITEM_HASTE, 100);
		}
	}

	public int getDollType() {	return _dollType;	}
	public void setDollType(int i) {	_dollType = i;	}

	public int getItemObjId() {	return _itemObjId;	}
	public void setItemObjId(int i) {	_itemObjId = i;	}

	public int getDamageByDoll() {
		int damage = 0;
		int type = getDollType();
		if (type == DOLLTYPE_WAREWOLF || type == DOLLTYPE_CRUSTACEA) {
			int chance = _random.nextInt(100) + 1;
			if (chance <= 3) {
				damage = 15;
				if (_master instanceof L1PcInstance) {
					L1PcInstance pc = (L1PcInstance) _master;
					pc.sendPackets(new S_SkillSound(_master.getId(), 6319));
				}
				Broadcaster.broadcastPacket(_master, new S_SkillSound(_master.getId(), 6319));
			}
		}
		return damage;
	}

	public void attackPoisonDamage(L1PcInstance pc, L1Character cha){
		int type = getDollType();
		if (type == DOLLTYPE_LAMIA){
			int chance = _random.nextInt(100) + 1;
			if (10 >= chance) {
				L1DamagePoison.doInfection(pc, cha, 3000, 10);
			}
		}
	}
	public int getDamageReductionByDoll() {
		int DamageReduction = 0;
		if (getDollType() == DOLLTYPE_STONEGOLEM) {
			int chance = _random.nextInt(100) + 1;
			if (chance <= 3) {
				DamageReduction = 30;
				if (_master instanceof L1PcInstance) {
					L1PcInstance pc = (L1PcInstance) _master;
					pc.sendPackets(new S_SkillSound(_master.getId(), 6320));
				}
				Broadcaster.broadcastPacket(_master, new S_SkillSound(_master.getId(), 6320));
			}
		}
		return DamageReduction;
	}

	public boolean isMpRegeneration() {
		boolean isMpRegeneration = false;
		int type = getDollType();
		switch(type){
		case DOLLTYPE_SUCCUBUS:
		case DOLLTYPE_ELDER:
			isMpRegeneration = true;
			break;
		}		
		return isMpRegeneration;
	}

	public boolean isHpRegeneration() {
		boolean isHpRegeneration = false;
		int type = getDollType();
		if (type == DOLLTYPE_SEADANCER) {
			isHpRegeneration = true;
		}
		return isHpRegeneration;
	}

	public int getWeightReductionByDoll() {
		int weightReduction = 0;
		int type = getDollType();
		switch(type){		
		case DOLLTYPE_BUGBEAR:
		case DOLLTYPE_DRAGON_M:
		case DOLLTYPE_DRAGON_W:
		case DOLLTYPE_HIGH_DRAGON_M:
		case DOLLTYPE_HIGH_DRAGON_W:
			weightReduction = 10;
			break;
		}
		return weightReduction;
	}
	public int getMpRegenerationValues(){
		int regenMp = 0;
		int type = getDollType();
		switch(type){
		case DOLLTYPE_SUCCUBUS:
		case DOLLTYPE_ELDER:
			regenMp = 15;
			break;		
		}
		return regenMp;
	}
	private void getHelperAction(){
		if(_master.getCurrentHp() < _master.getMaxHp()/2){
			new L1SkillUse().handleCommands(null, 35, _master.getId(), _master.getX(), 
					_master.getY(), null, 0, L1SkillUse.TYPE_NORMAL, this);
			return;
		}
		for(int i = 0; i < Buff.length; i++){
			if(!_master.getSkillEffectTimerSet().hasSkillEffect(Buff[i])){
				new L1SkillUse().handleCommands(null, Buff[i], _master.getId(), _master.getX(), 
						_master.getY(), null, 0, L1SkillUse.TYPE_NORMAL, this);
				break;
			}
		}
	}
	
	public void startHelpTimer() {
		if(getDollType() != DOLLTYPE_HELPER)
			return;
		_future = GeneralThreadPool.getInstance().scheduleAtFixedRate(new HelpTimer(), 4000, 4000);
	}
	
	public void stopHelpTimer(){
		if(getDollType() != DOLLTYPE_HELPER)
			return;
		if (_future != null) {
			_future.cancel(false);
		}
	}
}
