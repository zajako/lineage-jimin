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

package bone.server.server.serverpackets;

import java.util.List;
import bone.server.server.Opcodes;
import bone.server.server.datatables.ShopTable;
import bone.server.server.model.L1Object;
import bone.server.server.model.L1World;
import bone.server.server.model.Instance.L1NpcInstance;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.model.shop.L1AssessedItem;
import bone.server.server.model.shop.L1Shop;
import bone.server.server.serverpackets.S_NoSell;

// Referenced classes of package bone.server.server.serverpackets:
// ServerBasePacket, S_SystemMessage

public class S_ShopBuyList extends ServerBasePacket {

	private static final String S_SHOP_BUY_LIST = "[S] S_ShopBuyList";

	public S_ShopBuyList(int objid, L1PcInstance pc) {
		L1Object object = L1World.getInstance().findObject(objid);
		if (!(object instanceof L1NpcInstance)) {
			return;
		}
		L1NpcInstance npc = (L1NpcInstance) object;
		int npcId = npc.getNpcTemplate().get_npcId();
		L1Shop shop = ShopTable.getInstance().get(npcId);
		if (shop == null) {
			pc.sendPackets(new S_NoSell(npc));
			return;
		}

		List<L1AssessedItem> assessedItems = shop.assessItems(pc.getInventory());
		if (npcId == 70035 || npcId == 70041 || npcId == 70042) {
			assessedItems = shop.assessTickets(pc.getInventory());
		} else {
			assessedItems = shop.assessItems(pc.getInventory());
		}
		/*List<L1AssessedItem> assessedItems = shop
				.assessItems(pc.getInventory());*/
		if (assessedItems.isEmpty()) {
			pc.sendPackets(new S_NoSell(npc));
			return;
		}

		writeC(Opcodes.S_OPCODE_SHOWSHOPSELLLIST);
		writeD(objid);
		writeH(assessedItems.size());

		for (L1AssessedItem item : assessedItems) {
			writeD(item.getTargetId());
			writeD(item.getAssessedPrice());
		}
	}

	@Override
	public byte[] getContent() {
		return getBytes();
	}
	@Override
	public String getType() {
		return S_SHOP_BUY_LIST;
	}
}