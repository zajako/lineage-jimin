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

import static bone.server.server.model.Instance.L1PcInstance.REGENSTATE_ATTACK;

import server.LineageClient;
import bone.server.server.ActionCodes;
import bone.server.server.model.Broadcaster;
import bone.server.server.model.CharPosUtil;
import bone.server.server.model.L1Character;
import bone.server.server.model.L1Object;
import bone.server.server.model.L1World;
import bone.server.server.model.Instance.L1ItemInstance;
import bone.server.server.model.Instance.L1LittleBugInstance;
import bone.server.server.model.Instance.L1NpcInstance;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.model.skill.L1SkillId;
import bone.server.server.serverpackets.S_AttackPacket;
import bone.server.server.serverpackets.S_ServerMessage;
import bone.server.server.serverpackets.S_SkillSound;
import bone.server.server.serverpackets.S_UseArrowSkill;

//Referenced classes of package bone.server.server.clientpackets:
//ClientBasePacket

public class C_Attack extends ClientBasePacket {

	private int _targetX = 0;
	private int _targetY = 0;
	public C_Attack(byte[] decrypt, LineageClient client) {
		super(decrypt);
		int targetId = readD();
		int x = readH();
		int y = readH();
		_targetX = x;
		_targetY = y;

		L1PcInstance pc = client.getActiveChar();
		L1Object target = L1World.getInstance().findObject(targetId);

		if (pc.isGhost() || pc.isDead() || pc.isTeleport()) { return; }
		if (pc.isInvisble()) { return; }
		if (pc.isInvisDelay()) { return; }

		if (pc.getInventory().getWeight240() >= 200) {
			pc.sendPackets(new S_ServerMessage(110)); // \f1아이템이 너무 무거워 전투할 수가 없습니다.
			return;
		}
		if (target instanceof L1LittleBugInstance){
			return;
		}
		if (target instanceof L1NpcInstance) {
			int hiddenStatus = ((L1NpcInstance)target).getHiddenStatus();
			if (hiddenStatus == L1NpcInstance.HIDDEN_STATUS_SINK || hiddenStatus == L1NpcInstance.HIDDEN_STATUS_FLY) {
				return;
			}
		}
		/*
		if (Config.CHECK_ATTACK_INTERVAL) {
			int result;
			result = pc.getAcceleratorChecker().checkInterval(AcceleratorChecker.ACT_TYPE.ATTACK);
			if (result == AcceleratorChecker.R_DISCONNECTED) {				
				eva.LogBugAppend("스핵:AW-"+(pc.getCurrentWeapon()+1), pc, 1);
				L1SkillUse l1skilluse = new L1SkillUse();
				l1skilluse.handleCommands(pc, L1SkillId.EARTH_BIND, pc.getId(), pc.getX(), pc.getY(), null, 0, L1SkillUse.TYPE_NORMAL);
				pc.sendPackets(new S_SystemMessage("허용된 값을 초과하여 행동이 제한됩니다."));
				pc.addSpeedHackCount(1);				
				return;
			}
		}
		 */
		// 공격 액션을 취할 수 있는 경우의 처리
		if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.ABSOLUTE_BARRIER)) { // 아브소르트바리아의 해제
			pc.getSkillEffectTimerSet().killSkillEffectTimer(L1SkillId.ABSOLUTE_BARRIER);
			//pc.startHpRegeneration();
			//pc.startMpRegeneration();
			pc.startHpRegenerationByDoll();
			pc.startMpRegenerationByDoll();
		}
		if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.MEDITATION)){
			pc.getSkillEffectTimerSet().killSkillEffectTimer(L1SkillId.MEDITATION);
		}

		pc.delInvis();
		pc.setRegenState(REGENSTATE_ATTACK);

		if (target != null && !((L1Character) target).isDead()) {
			target.onAction(pc);
		} else { // 하늘 공격
			// TODO 활로 지면에 하늘 공격했을 경우는 화살이 날지 않으면 안 된다
			int weaponId = 0;
			int weaponType = 0;
			L1ItemInstance weapon = pc.getWeapon();
			L1ItemInstance arrow = null;
			L1ItemInstance sting = null;						
			if (weapon != null) {
				weaponId = weapon.getItem().getItemId();
				weaponType = weapon.getItem().getType1();
				if (weaponType == 20) {
					arrow = pc.getInventory().getArrow();
				}
				if (weaponType == 62) {
					sting = pc.getInventory().getSting();
				}
			}
			pc.getMoveState().setHeading(CharPosUtil.targetDirection(pc, x, y)); // 방향세트
			if (weaponType == 20 && (weaponId == 190 || arrow != null)) {
				calcOrbit(pc.getX(), pc.getY(), pc.getMoveState().getHeading());
				if (arrow != null) { 
					if (pc.getGfxId().getTempCharGfx() == 7968){
						pc.sendPackets(new S_UseArrowSkill(pc, 0, 7972, _targetX, _targetY, true));
						Broadcaster.broadcastPacket(pc, new S_UseArrowSkill(pc, 0, 7972, _targetX, _targetY, true));
					}else{
						pc.sendPackets(new S_UseArrowSkill(pc, 0, 66, _targetX, _targetY, true));
						Broadcaster.broadcastPacket(pc, new S_UseArrowSkill(pc, 0, 66, _targetX, _targetY, true));
					}
					pc.getInventory().removeItem(arrow, 1);
				} else if (weaponId == 190) { 
					pc.sendPackets(new S_UseArrowSkill(pc, 0, 2349, _targetX, _targetY, true));
					Broadcaster.broadcastPacket(pc, new S_UseArrowSkill(pc, 0, 2349, _targetX, _targetY, true));
				}
			} else if (weaponType == 62 && sting != null) {
				calcOrbit(pc.getX(), pc.getY(), pc.getMoveState().getHeading());
				if (pc.getGfxId().getTempCharGfx() == 7968){
					pc.sendPackets(new S_UseArrowSkill(pc, 0, 7972, _targetX, _targetY, true));
					Broadcaster.broadcastPacket(pc, new S_UseArrowSkill(pc, 0, 7972, _targetX, _targetY, true));
				}else{
					pc.sendPackets(new S_UseArrowSkill(pc, 0, 2989, _targetX, _targetY, true));
					Broadcaster.broadcastPacket(pc, new S_UseArrowSkill(pc, 0, 2989, _targetX, _targetY, true));
				}
				pc.getInventory().removeItem(sting, 1);
			} else {
				pc.sendPackets(new S_AttackPacket(pc, 0, ActionCodes.ACTION_Attack));
				Broadcaster.broadcastPacket(pc, new S_AttackPacket(pc, 0, ActionCodes.ACTION_Attack));
			}
		}
		if (pc.getWeapon().getItem().getType() == 17) {
			if (pc.getWeapon().getItemId() == 410003) {
				pc.sendPackets(new S_SkillSound(pc.getId(), 6983));
				Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), 6983));
			} else {
				pc.sendPackets(new S_SkillSound(pc.getId(), 7049));
				Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), 7049));
			}
		}
	}

	private void calcOrbit(int cX, int cY, int head) {
		final byte HEADING_X[] = { 0, 1, 1, 1, 0, -1, -1, -1 };	
		final byte HEADING_Y[] = { -1, -1, 0, 1, 1, 1, 0, -1 };
		float disX = Math.abs(cX - _targetX);
		float disY = Math.abs(cY - _targetY);
		float dis = Math.max(disX, disY);
		float avgX = 0;
		float avgY = 0;

		if (dis == 0) {
			avgX = HEADING_X[head];
			avgY = HEADING_Y[head];
		} else {
			avgX = disX / dis;
			avgY = disY / dis;
		}

		int addX = (int) Math.floor((avgX * 15) + 0.59f);
		int addY = (int) Math.floor((avgY * 15) + 0.59f);

		if (cX > _targetX) { addX *= -1; }
		if (cY > _targetY) { addY *= -1; }

		_targetX = _targetX + addX;
		_targetY = _targetY + addY;
	}
}
