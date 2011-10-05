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

package l1j.server.server.model;

import java.util.EnumMap;

import l1j.server.Config;
import l1j.server.server.datatables.SprTable;
import l1j.server.server.model.Instance.L1PcInstance;

/**
 * 가속기의 사용을 체크하는 클래스.
 */
public class AcceleratorChecker {

	private final L1PcInstance _pc;

	private int _injusticeCount;

	private int _justiceCount;

	private static final int INJUSTICE_COUNT_LIMIT = Config.INJUSTICE_COUNT;

	private static final int JUSTICE_COUNT_LIMIT = Config.JUSTICE_COUNT;

	// 실제로는 이동과 공격의 패킷 간격은 spr의 이론치보다5%만큼 늦다.
	// 그것을 고려해―5로 하고 있다.
	private static final double CHECK_STRICTNESS = (Config.CHECK_STRICTNESS - 5) / 100D;

	private static final double HASTE_RATE = 0.745;

	private static final double WAFFLE_RATE = 0.874;
	
	private final EnumMap<ACT_TYPE, Long> _actTimers =	new EnumMap<ACT_TYPE, Long>(ACT_TYPE.class);

	private final EnumMap<ACT_TYPE, Long> _checkTimers = new EnumMap<ACT_TYPE, Long>(ACT_TYPE.class);

	public static enum ACT_TYPE { MOVE, ATTACK, SPELL_DIR, SPELL_NODIR }

	// 체크의 결과
	public static final int R_OK = 0;

	public static final int R_DETECTED = 1;

	public static final int R_DISCONNECTED = 2;

	public AcceleratorChecker(L1PcInstance pc) {
		_pc = pc;
		_injusticeCount = 0;
		_justiceCount = 0;
		long now = System.currentTimeMillis();
		for (ACT_TYPE each : ACT_TYPE.values()) {
			_actTimers.put(each, now);
			_checkTimers.put(each, now);
		}
	}

	/**
	 * 액션의 간격이 부정하지 않을까 체크해, 적당 처리를 실시한다.
	 * 
	 * @param type -
	 *            체크하는 액션의 타입
	 * @return 문제가 없었던 경우는 0, 부정할 경우는 1, 부정 동작이 일정 회수에 이르렀기 때문에 플레이어를 절단 했을 경우는 2를 돌려준다.
	 */
	public int checkInterval(ACT_TYPE type) {
		int result = R_OK;
		long now = System.currentTimeMillis();
		long interval = now - _actTimers.get(type);
		int rightInterval = getRightInterval(type);
		
		interval *= CHECK_STRICTNESS;
		if (_pc.isGm()){
			return R_OK;
		}
		
		if (_pc.getGfxId().getTempCharGfx() == 6284){	// 유령의집호박
			_injusticeCount = 0;
			_justiceCount = 0;
			return R_OK;
		}
		if (0 < interval && interval < rightInterval) {			
			_injusticeCount++;
			if (_injusticeCount == 1){
				//System.out.println("interval / rightInterval : "+interval+" / "+rightInterval+" / "+_pc.getMoveState().getMoveSpeed());
			}
			_justiceCount = 0;
			if (_injusticeCount >= INJUSTICE_COUNT_LIMIT) {
				//doDisconnect(type.toString());		
				_injusticeCount = 0;
				return R_DISCONNECTED;
			}
			result = R_DETECTED;
		} else if (interval >= rightInterval) {
			_justiceCount++;
			if (_justiceCount >= JUSTICE_COUNT_LIMIT) {
				_injusticeCount = 0;
				_justiceCount = 0;
			}
		}

		// 검증용
//		double rate = (double) interval / rightInterval;
//		System.out.println(String.format("%s: %d / %d = %.2f (o-%d x-%d)",
//		type.toString(), interval, rightInterval, rate,
//		_justiceCount, _injusticeCount));

		_actTimers.put(type, now);
		return result;
	}
/*
	private void doDisconnect(String type) {
		if (!(_pc.getAccessLevel() == Config.GMCODE)) {
			L1Teleport.teleport(_pc, _pc.getSpeedHackX(), _pc.getSpeedHackY(), _pc.getSpeedHackMapid(), _pc.getSpeedHackHeading(), false);
			//_pc.sendPackets(new S_ServerMessage(945)); // 위법 프로그램이 발견되었으므로, 종료합니다.
			//_pc.sendPackets(new S_Disconnect());
		} else {
			// GM는 절단 하지 않는다
			_pc.sendPackets(new S_SystemMessage(
			"[스핵 절단] 캐릭명 - "+_pc.getName()+" / 변신 - "+_pc.getGfxId().getTempCharGfx()+" / 타입 -"+type));
			_injusticeCount = 0;
		}

	}
*/
	/**
	 * PC 상태로부터 지정된 종류의 액션의 올바른 인터벌(ms)을 계산해, 돌려준다.
	 * 
	 * @param type -
	 *            액션의 종류
	 * @param _pc -
	 *            조사하는 PC
	 * @return 올바른 인터벌(ms)
	 */
	private int getRightInterval(ACT_TYPE type) {
		int interval;

		switch (type) {
		case ATTACK:
			interval = SprTable.getInstance().getAttackSpeed(
					_pc.getGfxId().getTempCharGfx(), _pc.getCurrentWeapon() + 1);
			break;
		case MOVE:
			interval = SprTable.getInstance().getMoveSpeed(
					_pc.getGfxId().getTempCharGfx(), _pc.getCurrentWeapon());
			break;
		case SPELL_DIR:
			interval = SprTable.getInstance().getDirSpellSpeed(
					_pc.getGfxId().getTempCharGfx());
			break;
		case SPELL_NODIR:
			interval = SprTable.getInstance().getNodirSpellSpeed(
					_pc.getGfxId().getTempCharGfx());
			break;
		default:
			return 0;
		}
		if (_pc.isHaste()) {
			interval *= HASTE_RATE;
		}
		if (type.equals(ACT_TYPE.MOVE) && _pc.isFastMovable()
				|| type.equals(ACT_TYPE.MOVE) && _pc.isUgdraFruit()) {
			interval *= HASTE_RATE;
		}
		
		if (type.equals(ACT_TYPE.ATTACK) && _pc.isBloodLust()){ // 블러드러스트			
			interval *= HASTE_RATE;
		}
		if (_pc.isBrave()) {
			interval *= HASTE_RATE;
		}		
		if (_pc.isElfBrave()) {
			interval *= WAFFLE_RATE;
		}
		return interval;
	}
}
