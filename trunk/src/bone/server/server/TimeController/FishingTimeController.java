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
package bone.server.server.TimeController;

import java.util.ArrayList;
import java.util.List;

import bone.server.server.model.Broadcaster;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.serverpackets.S_CharVisualUpdate;

import bone.server.server.serverpackets.S_PacketBox;
import bone.server.server.serverpackets.S_ServerMessage;

public class FishingTimeController extends Thread {
	private static FishingTimeController _instance;
	private final List<L1PcInstance> _fishingList =
			new ArrayList<L1PcInstance>();

	public static FishingTimeController getInstance() {
		if (_instance == null) {
			_instance = new FishingTimeController();
			_instance.start();
		}
		return _instance;
	}
	
	public void run() {
		System.out.println(FishingTimeController.class.getName()  + " ����");
		try {
			while (true) {
				Thread.sleep(300);
				fishing();
			}
		} catch (Exception e1) {
		}
	}

	public void addMember(L1PcInstance pc) {
		if (pc == null || _fishingList.contains(pc)) {
			return;
		}
		_fishingList.add(pc);
	}

	public void removeMember(L1PcInstance pc) {
		if (pc == null || !_fishingList.contains(pc)) {
			return;
		}
		_fishingList.remove(pc);
	}

	private void fishing() {
		if (_fishingList.size() > 0) {
			long currentTime = System.currentTimeMillis();
			L1PcInstance pc = null;
			for (int i = 0; i < _fishingList.size(); i++) {
				pc = _fishingList.get(i);
				if (pc.isFishing()) {
					long time = pc.getFishingTime();
					if (currentTime <= (time + 1000)
							&& currentTime >= (time - 1000)
							&& !pc.isFishingReady()) {
						pc.setFishingReady(true);
// pc.sendPackets(new S_Fishing());
						pc.sendPackets(new S_PacketBox(S_PacketBox.FISHING));
					} else if (currentTime > (time + 1000)) {
						pc.setFishingTime(0);
						pc.setFishingReady(false);
						pc.setFishing(false);
						pc.sendPackets(new S_CharVisualUpdate(pc));
						Broadcaster.broadcastPacket(pc, new S_CharVisualUpdate(pc));
						pc.sendPackets(new S_ServerMessage(1163, ""));  // ���ð� �����߽��ϴ�.
						removeMember(pc);
					}
				}
			}
		}
	}

}
