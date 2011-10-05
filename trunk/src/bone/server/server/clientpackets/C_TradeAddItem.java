/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be trading_partnerful,
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

import server.LineageClient;
import bone.server.server.model.L1Inventory;
import bone.server.server.model.L1Object;
import bone.server.server.model.L1Trade;
import bone.server.server.model.L1World;
import bone.server.server.model.Instance.L1BuffNpcInstance;
import bone.server.server.model.Instance.L1DollInstance;
import bone.server.server.model.Instance.L1ItemInstance;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.model.Instance.L1PetInstance;
import bone.server.server.serverpackets.S_ServerMessage;
//Referenced classes of package bone.server.server.clientpackets:
//ClientBasePacket

public class C_TradeAddItem extends ClientBasePacket {
	private static final String C_TRADE_ADD_ITEM = "[C] C_TradeAddItem";

	public C_TradeAddItem(byte abyte0[], LineageClient client) throws Exception {

		super(abyte0);

		int itemid = readD();
		int itemcount = readD();
		L1PcInstance pc = client.getActiveChar();
		L1Trade trade = new L1Trade();	
		L1ItemInstance item = pc.getInventory().getItem(itemid);
		
		if (itemid != item.getId()) {return;}
		if (!item.isStackable() && itemcount != 1) {return;}
		if (itemcount <= 0 || item.getCount() <= 0) {return;}
		if (itemcount > item.getCount()) {itemcount = item.getCount();}
		if (itemcount > 2000000000)  {return;}		
		if (item.getItem().getItemId() == 423012 || item.getItem().getItemId() == 423013){	// 10주년티
			pc.sendPackets(new S_ServerMessage(210, item.getItem().getName())); // \f1%0은 버리거나 또는 타인에게 양일을 할 수 없습니다.
			return;
		}
		if (!item.getItem().isTradable()) {
			pc.sendPackets(new S_ServerMessage(210, item.getItem().getName())); // \f1%0은 버리거나 또는 타인에게 양일을 할 수 없습니다.
			return;
		}
		if(item.getBless() >= 128){
			pc.sendPackets(new S_ServerMessage(210, item.getItem().getName())); // \f1%0은 버리거나 또는 타인에게 양일을 할 수 없습니다.
			return;
		}
		if(item.isEquipped()){
			pc.sendPackets(new S_ServerMessage(906));
			return;
		}
		
		Object[] dolllist = pc.getDollList().values().toArray();
		L1DollInstance doll = null;
		for (Object dollObject : dolllist) {
			if (dollObject instanceof L1DollInstance) {
				doll = (L1DollInstance) dollObject;
				if (item.getId() == doll.getItemObjId()) {
					// \f1%0은 버리거나 또는 타인에게 양일을 할 수 없습니다.
					pc.sendPackets(new S_ServerMessage(210, item.getItem().getName()));
					return;
				}
			}
		}
		
		Object[] petlist = pc.getPetList().values().toArray();
		L1PetInstance pet = null;
		for (Object petObject : petlist) {
			if (petObject instanceof L1PetInstance) {
				pet = (L1PetInstance) petObject;
				if (item.getId() == pet.getItemObjId()) {
					// \f1%0은 버리거나 또는 타인에게 양일을 할 수 없습니다.
					pc.sendPackets(new S_ServerMessage(210, item.getItem().getName()));
					return;
				}
			}
		}
		
		L1Object tradingPartner = L1World.getInstance().findObject(pc.getTradeID());
		if (tradingPartner == null) { return; }
		if(tradingPartner instanceof L1PcInstance){
			L1PcInstance target = (L1PcInstance)tradingPartner;
			if (pc.getTradeOk() || target.getTradeOk()) { return; }
			if (target.getInventory().checkAddItem(item, itemcount)	!= L1Inventory.OK) {
				target.sendPackets(new S_ServerMessage(270));
				pc.sendPackets(new S_ServerMessage(271));
				return;
			}
		}else if(tradingPartner instanceof L1BuffNpcInstance){
			L1BuffNpcInstance target = (L1BuffNpcInstance)tradingPartner;
			if (pc.getTradeOk() || target.getTradeOk()) { return; }
		}
		trade.TradeAddItem(pc, itemid, itemcount);
	}

	@Override
	public String getType() {
		return C_TRADE_ADD_ITEM;
	}
}
