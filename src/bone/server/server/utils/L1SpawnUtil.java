/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.   See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 *
 * http://www.gnu.org/copyleft/gpl.html
 */
package bone.server.server.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

import bone.server.server.ObjectIdFactory;
import bone.server.server.datatables.NpcTable;
import bone.server.server.model.L1NpcDeleteTimer;
import bone.server.server.model.L1World;
import bone.server.server.model.Instance.L1FieldObjectInstance;
import bone.server.server.model.Instance.L1MonsterInstance;
import bone.server.server.model.Instance.L1NpcInstance;
import bone.server.server.model.Instance.L1PcInstance;

public class L1SpawnUtil {
	private static Logger _log = Logger.getLogger(L1SpawnUtil.class.getName());

	public static void spawn(L1PcInstance pc, int npcId, int randomRange, int timeMillisToDelete, boolean isUsePainwand) {
		try {
			L1NpcInstance npc = NpcTable.getInstance().newNpcInstance(npcId);
			npc.setId(ObjectIdFactory.getInstance().nextId());
			npc.setMap(pc.getMapId());
			if (randomRange == 0) {
				npc.getLocation().set(pc.getLocation());
				npc.getLocation().forward(pc.getMoveState().getHeading());
			} else {
				int tryCount = 0;
				do {
					tryCount++;
					npc.setX(pc.getX() + (int) (Math.random() * randomRange) - (int) (Math.random() * randomRange));
					npc.setY(pc.getY() + (int) (Math.random() * randomRange) - (int) (Math.random() * randomRange));
					if (npc.getMap(). isInMap(npc.getLocation()) && npc.getMap(). isPassable(npc.getLocation())) {
						break;
					}
					Thread.sleep(1);
				} while (tryCount < 50);

				if (tryCount >= 50) {
					npc.getLocation().set(pc.getLocation());
					npc.getLocation().forward(pc.getMoveState().getHeading());
				}
			}

			npc.setHomeX(npc.getX());
			npc.setHomeY(npc.getY());
			npc.getMoveState().setHeading(pc.getMoveState().getHeading());
			if(isUsePainwand) {
				if(npc instanceof L1MonsterInstance) {
					L1MonsterInstance mon = (L1MonsterInstance)npc;
					mon.set_storeDroped(2);
				}
			}
			
			L1World.getInstance().storeObject(npc);
			L1World.getInstance().addVisibleObject(npc);

			npc.getLight().turnOnOffLight();
			npc.startChat(L1NpcInstance.CHAT_TIMING_APPEARANCE); // 채팅 개시
			if (0 < timeMillisToDelete) {
				L1NpcDeleteTimer timer = new L1NpcDeleteTimer(npc, timeMillisToDelete);
				timer.begin();
			}
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
	}
	
	/**
	 * 엔피씨를 스폰한다
	 * @param x
	 * @param y
	 * @param map
	 * @param npcId
	 * @param randomRange
	 * @param timeMillisToDelete
	 * @param movemap (이동시킬 맵을 설정한다 - 안타레이드)
	 */
	public static void spawn2(int x, int y, short map, int npcId, int randomRange, int timeMillisToDelete, int movemap) {
		try {
			L1NpcInstance npc = NpcTable.getInstance().newNpcInstance(npcId);
			npc.setId(ObjectIdFactory.getInstance().nextId());
			npc.setMap(map);
			if (randomRange == 0) {
				npc.getLocation().set(x,y,map);
				npc.getLocation().forward(5);
			} else {
				int tryCount = 0;
				do {
					tryCount++;
					npc.setX(x + (int) (Math.random() * randomRange) - (int) (Math.random() * randomRange));
					npc.setY(y + (int) (Math.random() * randomRange) - (int) (Math.random() * randomRange));
					if (npc.getMap(). isInMap(npc.getLocation()) && npc.getMap(). isPassable(npc.getLocation())) {
						break;
					}
					Thread.sleep(1);
				} while (tryCount < 50);

				if (tryCount >= 50) {
					npc.getLocation().set(x,y,map);
					npc.getLocation().forward(5);
				}
			}

			npc.setHomeX(npc.getX());
			npc.setHomeY(npc.getY());
			npc.getMoveState().setHeading(5);

			if(npcId == 4212015){
				L1FieldObjectInstance fobj = (L1FieldObjectInstance)npc;
				fobj.setMoveMapId(movemap);
			}

			L1World.getInstance().storeObject(npc);
			L1World.getInstance().addVisibleObject(npc); 

			npc.getLight().turnOnOffLight();
			npc.startChat(L1NpcInstance.CHAT_TIMING_APPEARANCE); // 채팅 개시
			if (0 < timeMillisToDelete) {
				L1NpcDeleteTimer timer = new L1NpcDeleteTimer(npc, timeMillisToDelete);
				timer.begin();
			}
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
	}
}
