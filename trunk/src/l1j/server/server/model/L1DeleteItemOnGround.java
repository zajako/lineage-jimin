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
package l1j.server.server.model;

import java.util.Collection;
import java.util.logging.Logger;

import l1j.server.Config;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.model.Instance.L1ItemInstance;

// Referenced classes of package l1j.server.server.model:
// L1DeleteItemOnGround

public class L1DeleteItemOnGround {
	private DeleteTimer _deleteTimer;

	private static final Logger _log = Logger
			. getLogger(L1DeleteItemOnGround.class.getName());

	public L1DeleteItemOnGround() {
	}

	private class DeleteTimer implements Runnable {
		public DeleteTimer() {
		}

		@Override
		public void run() {
			//1�д� ������� ���� 
			int time = 1 * 60 * 1000;
			for (;;) {
				try {
					Thread.sleep(time);
				} catch (Exception exception) {
					_log.warning("L1DeleteItemOnGround error: " + exception);
					break;
				}
/*				L1World.getInstance().broadcastPacketToAll(
						new S_ServerMessage(166, "���� �ʻ��� ������",
								"10�� �Ŀ� �����˴ϴ�")); 
				try {
					Thread.sleep(10000);
				} catch (Exception exception) {
					_log.warning("L1DeleteItemOnGround error: " + exception);
					break;
				}*/
				deleteItem();
//				L1World.getInstance().broadcastPacketToAll(
//						new S_ServerMessage(166, "���� �ʻ��� ������", "�����Ǿ����ϴ�"));
			}
		}
	}

	public void initialize() {
		if (! Config.ALT_ITEM_DELETION_TYPE.equalsIgnoreCase("auto")) {
			return;
		}

		_deleteTimer = new DeleteTimer();
		GeneralThreadPool.getInstance(). execute(_deleteTimer); // Ÿ�̸� ����
	}

	private void deleteItem() {
		int numOfDeleted = 0;
		L1Inventory groundInventory = null;
		Collection<L1Object> objs = L1World.getInstance().getObject();
		for (L1Object obj1 : objs) {
			if(obj1 instanceof L1ItemInstance){
				L1ItemInstance obj = (L1ItemInstance)obj1;
				if (obj.getX() == 0 && obj.getY() == 0) { // ������� �������� �ƴϰ�, �������� ������
					continue;
				}
				if (obj.getItem().getItemId() == 40515) { // ������ ��
					continue;
				}
				if (obj.getMapId() == 88 || obj.getMapId() == 98 || obj.getMapId() == 91 || obj.getMapId() == 92 || obj.getMapId() == 95) { // ���Ѵ���
					continue;
				}
				if (L1HouseLocation.isInHouse(obj.getX(), obj.getY(), obj
						. getMapId())) { // ����Ʈ��
					continue;
				}

				//players = L1World.getInstance()
				//		.getVisiblePlayer(obj, Config.ALT_ITEM_DELETION_RANGE);
				if(obj.get_DeleteItemTime() > 5){
					groundInventory.removeItem(obj);
					numOfDeleted++;
				}
				else{
					obj.add_DeleteItemTime();
				}
				/*if (players.isEmpty()) { // ���� �������� �÷��̾ ������ ����
					groundInventory = L1World
							. getInstance()
							. getInventory(obj.getX(), obj.getY(), obj.getMapId());
					groundInventory.removeItem(obj);
					numOfDeleted++;
				}*/			
			}			
		}
		objs = null;
		_log.fine("���� �ʻ��� �������� �ڵ� ����. ������: " + numOfDeleted);
	}
}
