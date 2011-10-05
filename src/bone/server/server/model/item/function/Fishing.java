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

package bone.server.server.model.item.function;

import java.util.Random;

import bone.server.server.ActionCodes;
import bone.server.server.TimeController.FishingTimeController;
import bone.server.server.clientpackets.ClientBasePacket;
import bone.server.server.model.Broadcaster;
import bone.server.server.model.L1Character;
import bone.server.server.model.Instance.L1ItemInstance;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.serverpackets.S_Fishing;
import bone.server.server.serverpackets.S_ServerMessage;
import bone.server.server.templates.L1Item;

@SuppressWarnings("serial")
public class Fishing extends L1ItemInstance{
	
	private static Random _random = new Random();
	
	public Fishing(L1Item item){
		super(item);
	}

	@Override
	public void clickItem(L1Character cha, ClientBasePacket packet){
		if(cha instanceof L1PcInstance){
			L1PcInstance pc = (L1PcInstance)cha;
			int itemId = this.getItemId();
			startFishing(pc, itemId, packet.readH(),  packet.readH());
		}
	}
	
	private void startFishing(L1PcInstance pc, int itemId, int fishX, int fishY) {
		if (pc.getMapId() != 5124 || fishX <= 32789 || fishX >= 32813
				|| fishY <= 32786 || fishY >= 32812) {
			// 여기에 낚싯대를 던질 수 없습니다.
			pc.sendPackets(new S_ServerMessage(1138));
			return;
		}

		int rodLength = 0;
		if (itemId == 41293) {
			rodLength = 5;
		} else if (itemId == 41294) {
			rodLength = 3;
		}
		if (pc.getMap().isFishingZone(fishX, fishY)) {
			if (pc.getMap().isFishingZone(fishX + 1, fishY)
					&& pc.getMap().isFishingZone(fishX - 1, fishY)
					&& pc.getMap().isFishingZone(fishX, fishY + 1)
					&& pc.getMap().isFishingZone(fishX, fishY - 1)) {
				if (fishX > pc.getX() + rodLength
						|| fishX < pc.getX() - rodLength) {
					// 여기에 낚싯대를 던질 수 없습니다.
					pc.sendPackets(new S_ServerMessage(1138));
				} else if (fishY > pc.getY() + rodLength
						|| fishY < pc.getY() - rodLength) {
					// 여기에 낚싯대를 던질 수 없습니다.
					pc.sendPackets(new S_ServerMessage(1138));
				} else if (pc.getInventory().consumeItem(41295, 1)) { // 먹이
					pc.sendPackets(new S_Fishing(pc.getId(), ActionCodes.ACTION_Fishing, fishX, fishY));
					Broadcaster.broadcastPacket(pc, new S_Fishing(pc.getId(), ActionCodes.ACTION_Fishing, fishX, fishY));
					pc.setFishing(true);
					long time = System.currentTimeMillis() + 10000 +
					_random.nextInt(5) * 1000;
					pc.setFishingTime(time);
					FishingTimeController.getInstance().addMember(pc);
				} else {
					// 낚시를 하기 위해서는 먹이가 필요합니다.
					pc.sendPackets(new S_ServerMessage(1137));
				}
			} else {
				// 여기에 낚싯대를 던질 수 없습니다.
				pc.sendPackets(new S_ServerMessage(1138));
			}
		} else {
			// 여기에 낚싯대를 던질 수 없습니다.
			pc.sendPackets(new S_ServerMessage(1138));
		}
	}

}

