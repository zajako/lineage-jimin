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

package l1j.server.server.model.Instance;

import l1j.server.server.datatables.HouseTable;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_Board;
import l1j.server.server.serverpackets.S_BoardRead;
import l1j.server.server.serverpackets.S_NPCTalkReturn;
import l1j.server.server.templates.L1House;
import l1j.server.server.templates.L1Npc;

public class L1BoardInstance extends L1NpcInstance {
	//private GameServerSetting _GameServerSetting = GameServerSetting.getInstance();
	private static final long serialVersionUID = 1L;

	public L1BoardInstance(L1Npc template) {
		super(template);
	}

	@Override
	public void onAction(L1PcInstance player) {
		 if (getNpcTemplate().get_npcId() == 4500200 || getNpcTemplate().get_npcId() == 4500201){
				String htmlid = null;
				String[] htmldata = null;
				for (L1Object object : L1World.getInstance().getVisibleObjects(this, 5)) {
					if(object instanceof L1HousekeeperInstance){
						L1HousekeeperInstance keeper = (L1HousekeeperInstance) object;
						int npcid = keeper.getNpcTemplate().get_npcId();
						L1House targetHouse = null;
						for (L1House house : HouseTable.getInstance().getHouseTableList()) {
							if (npcid == house.getKeeperId()) {
								targetHouse = house;
								break;
							}
						}

						boolean isOccupy = false;
						String clanName = null;
						String leaderName = null;
						for (L1Clan targetClan : L1World.getInstance().getAllClans()) {
							if (targetHouse.getHouseId() == targetClan.getHouseId()) {
								isOccupy = true;
								clanName = targetClan.getClanName();
								leaderName = targetClan.getLeaderName();
								break;
							}
						}

						if (isOccupy) {
							htmlid = "agname";
							htmldata = new String[] { clanName, leaderName,
									targetHouse.getHouseName() };
						} else { 
							htmlid = "agnoname";
							htmldata = new String[] { targetHouse.getHouseName() };
						}
					}

					if (htmlid != null) {
						if (htmldata != null) {
							player.sendPackets(new S_NPCTalkReturn(getId(), htmlid, htmldata));
							break;
						} else {
							player.sendPackets(new S_NPCTalkReturn(getId(), htmlid));
							break;
						}
					}
				}
		} else {
			player.sendPackets(new S_Board(this));
		}
	}

	public void onAction(L1PcInstance player, int number) {
		player.sendPackets(new S_Board(this, number));
	}

	public void onActionRead(L1PcInstance player, int number) {
		player.sendPackets(new S_BoardRead(number));
	}
}
