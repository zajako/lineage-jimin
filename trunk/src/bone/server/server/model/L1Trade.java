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
package bone.server.server.model;

import java.util.List;

import bone.server.GameSystem.BuffSystem;
import bone.server.server.datatables.CastleTable;
import bone.server.server.model.Instance.L1BuffNpcInstance;
import bone.server.server.model.Instance.L1ItemInstance;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.serverpackets.S_NpcChatPacket;
import bone.server.server.serverpackets.S_TradeAddItem;
import bone.server.server.serverpackets.S_TradeStatus;
import bone.server.server.templates.L1Castle;
import server.manager.bone;

// Referenced classes of package bone.server.server.model:
// L1Trade

public class L1Trade {
	private static L1Trade _instance;

	public L1Trade() {}
	public static L1Trade getInstance() {
		if (_instance == null) {
			_instance = new L1Trade();
		}
		return _instance;
	}

	public void TradeAddItem(L1PcInstance player, int itemid, int itemcount) {
		L1Object trading_partner = L1World.getInstance().findObject(player.getTradeID());
		L1ItemInstance l1iteminstance = player.getInventory().getItem(itemid);
		if (l1iteminstance != null && trading_partner != null) {
			if(trading_partner instanceof L1PcInstance){
				L1PcInstance tradepc = (L1PcInstance)trading_partner;
				if (!l1iteminstance.isEquipped()) {
					if (l1iteminstance.getCount() < itemcount || 0 >= itemcount) { //허상버그 관련 추가
						player.sendPackets(new S_TradeStatus(1));
						tradepc.sendPackets(new S_TradeStatus(1));
						player.setTradeOk(false);
						tradepc.setTradeOk(false);
						player.setTradeID(0);
						tradepc.setTradeID(0);
						return;
					}
					player.getInventory().tradeItem(l1iteminstance, itemcount, player.getTradeWindowInventory());
					player.sendPackets(new S_TradeAddItem(l1iteminstance, itemcount, 0));
					tradepc.sendPackets(new S_TradeAddItem(l1iteminstance, itemcount, 1));
				}	
			}else if(trading_partner instanceof L1BuffNpcInstance){
				L1BuffNpcInstance tradenpc = (L1BuffNpcInstance)trading_partner;
				if(l1iteminstance.getCount() < itemcount || 0 >= itemcount){ //허상버그 관련 추가
					player.sendPackets(new S_TradeStatus(1));
					player.setTradeOk(false);
					tradenpc.setTradeOk(false);
					player.setTradeID(0);
					tradenpc.setTradeID(0);
					return;
				}
				if (l1iteminstance.getItemId() == 40308 && itemcount == 30000) {
					player.getInventory().tradeItem(l1iteminstance, itemcount, player.getTradeWindowInventory());
					player.sendPackets(new S_TradeAddItem(l1iteminstance, itemcount, 0));	
				}else{
					player.sendPackets(new S_TradeStatus(1));
					player.setTradeOk(false);
					tradenpc.setTradeOk(false);
					player.setTradeID(0);
					tradenpc.setTradeID(0);
					Broadcaster.broadcastPacket(tradenpc, new S_NpcChatPacket(tradenpc, "비용은 30000아덴 입니다.", 0));
				}
			}
		}
	}

	public void TradeOK(L1PcInstance pc) {
		int cnt;
		L1Object trading_partner = L1World.getInstance().findObject(pc.getTradeID());
		if (trading_partner != null) {
			if(trading_partner instanceof L1PcInstance){
				L1PcInstance tradepc = (L1PcInstance)trading_partner;
				List<?> player_tradelist = pc.getTradeWindowInventory().getItems();
				int player_tradecount = pc.getTradeWindowInventory().getSize();
	
				List<?> trading_partner_tradelist = tradepc.getTradeWindowInventory().getItems();
				int trading_partner_tradecount = tradepc.getTradeWindowInventory().getSize();
				L1ItemInstance pcitem = null;
				for (cnt = 0; cnt < player_tradecount; cnt++) {
					pcitem = (L1ItemInstance) player_tradelist.get(0);
					pc.getTradeWindowInventory().tradeItem(pcitem, pcitem.getCount(), tradepc.getInventory());
					bone.LogTradeAppend("교환", pc.getName(), tradepc.getName(), pcitem.getEnchantLevel(), pcitem.getName(), 
							pcitem.getBless(), pcitem.getCount(), pcitem.getId());
				}
				L1ItemInstance tradepcitem = null;
				for (cnt = 0; cnt < trading_partner_tradecount; cnt++) {
					tradepcitem = (L1ItemInstance) trading_partner_tradelist.get(0);
					tradepc.getTradeWindowInventory().tradeItem(tradepcitem, tradepcitem.getCount(), pc.getInventory());					
					bone.LogTradeAppend("교환", tradepc.getName(), pc.getName(), tradepcitem.getEnchantLevel(), tradepcitem.getName(), 
							tradepcitem.getBless(), tradepcitem.getCount(), tradepcitem.getId());
				}
	
				pc.sendPackets(new S_TradeStatus(0));
				tradepc.sendPackets(new S_TradeStatus(0));
				pc.setTradeOk(false);
				tradepc.setTradeOk(false);
				pc.setTradeID(0);
				tradepc.setTradeID(0);
				pc.getLight().turnOnOffLight();
				tradepc.getLight().turnOnOffLight();
			}else if(trading_partner instanceof L1BuffNpcInstance){
				L1BuffNpcInstance tradenpc = (L1BuffNpcInstance)trading_partner;
				List<?> player_tradelist = pc.getTradeWindowInventory().getItems();
				int player_tradecount = pc.getTradeWindowInventory().getSize();
				
				L1ItemInstance pcitem = null;
				for (cnt = 0; cnt < player_tradecount; cnt++) {
					pcitem = (L1ItemInstance) player_tradelist.get(0);
					L1Castle castle = CastleTable.getInstance().getCastleTable(4);
					L1Castle castle2 = CastleTable.getInstance().getCastleTable(6);
					int money = castle.getPublicReadyMoney() + pcitem.getCount()/3;
					int money2 = castle2.getPublicReadyMoney() + pcitem.getCount()/10;
					castle.setPublicReadyMoney(money);
					castle2.setPublicReadyMoney(money2);
					pc.getTradeWindowInventory().consumeItem(pcitem.getItemId(), pcitem.getCount());
				}
				pc.sendPackets(new S_TradeStatus(0));
				pc.setTradeOk(false);
				tradenpc.setTradeOk(false);
				pc.setTradeID(0);
				tradenpc.setTradeID(0);
				pc.getLight().turnOnOffLight();
				tradenpc.getLight().turnOnOffLight();
				new BuffSystem(tradenpc, pc);
			}
		}
	}

	public void TradeCancel(L1PcInstance pc) {
		int cnt;
		L1Object trading_partner = L1World.getInstance().findObject(pc.getTradeID());
		if (trading_partner != null) {
			if(trading_partner instanceof L1PcInstance){
				L1PcInstance tradepc = (L1PcInstance)trading_partner;
				List<?> player_tradelist = pc.getTradeWindowInventory().getItems();
				int player_tradecount = pc.getTradeWindowInventory().getSize();

				List<?> trading_partner_tradelist = tradepc.getTradeWindowInventory().getItems();
				int trading_partner_tradecount = tradepc.getTradeWindowInventory().getSize();
				L1ItemInstance pcitem = null;
				for (cnt = 0; cnt < player_tradecount; cnt++) {
					pcitem = (L1ItemInstance) player_tradelist.get(0);
					pc.getTradeWindowInventory().tradeItem(pcitem, pcitem.getCount(), pc.getInventory());
				}
				L1ItemInstance tradepcitem = null;
				for (cnt = 0; cnt < trading_partner_tradecount; cnt++) {
					tradepcitem = (L1ItemInstance) trading_partner_tradelist.get(0);
					tradepc.getTradeWindowInventory().tradeItem(tradepcitem, tradepcitem.getCount(), tradepc.getInventory());
				}

				pc.sendPackets(new S_TradeStatus(1));
				tradepc.sendPackets(new S_TradeStatus(1));
				pc.setTradeOk(false);
				tradepc.setTradeOk(false);
				pc.setTradeID(0);
				tradepc.setTradeID(0);	
			}else if(trading_partner instanceof L1BuffNpcInstance){
				L1BuffNpcInstance tradenpc = (L1BuffNpcInstance)trading_partner;
				List<?> player_tradelist = pc.getTradeWindowInventory().getItems();
				int player_tradecount = pc.getTradeWindowInventory().getSize();

				L1ItemInstance pcitem = null;
				for (cnt = 0; cnt < player_tradecount; cnt++) {
					pcitem = (L1ItemInstance) player_tradelist.get(0);
					pc.getTradeWindowInventory().tradeItem(pcitem, pcitem.getCount(), pc.getInventory());
				}
				pc.sendPackets(new S_TradeStatus(1));
				pc.setTradeOk(false);
				tradenpc.setTradeOk(false);
				pc.setTradeID(0);
				tradenpc.setTradeID(0);
			}
		}
	}
}