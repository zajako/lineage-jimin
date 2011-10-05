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

import bone.server.server.clientpackets.ClientBasePacket;
import bone.server.server.model.L1Character;
import bone.server.server.model.Instance.L1ItemInstance;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.serverpackets.S_ItemName;
import bone.server.server.templates.L1Item;

@SuppressWarnings("serial")
public class Light extends L1ItemInstance{
	
	public Light(L1Item item){
		super(item);
	}

	@Override
	public void clickItem(L1Character cha, ClientBasePacket packet){
		if(cha instanceof L1PcInstance){
			L1PcInstance pc = (L1PcInstance)cha;
			L1ItemInstance useItem = pc.getInventory().getItem(this.getId());
			int itemId = useItem.getItemId();
			if (useItem.getRemainingTime() <= 0 && itemId != 40004) {
				return;
			}
			if (useItem.isNowLighting()) {
				useItem.setNowLighting(false);
				pc.getLight().turnOnOffLight();
			} else {
				useItem.setNowLighting(true);
				pc.getLight().turnOnOffLight();
			}
			pc.sendPackets(new S_ItemName(useItem));
		}
	}
}

