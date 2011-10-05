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
package l1j.server.server.clientpackets;

import java.util.ArrayList;

import l1j.server.Config;
import l1j.server.Warehouse.ClanWarehouse;
import l1j.server.Warehouse.ElfWarehouse;
import l1j.server.Warehouse.PackageWarehouse;
import l1j.server.Warehouse.PrivateWarehouse;
import l1j.server.Warehouse.Warehouse;
import l1j.server.Warehouse.WarehouseManager;
import l1j.server.server.datatables.ShopTable;
import l1j.server.server.model.L1BugBearRace;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1Inventory;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1DollInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1PetInstance;
import l1j.server.server.model.item.L1ItemId;
import l1j.server.server.model.shop.L1Shop;
import l1j.server.server.model.shop.L1ShopBuyOrderList;
import l1j.server.server.model.shop.L1ShopSellOrderList;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.templates.L1PrivateShopBuyList;
import l1j.server.server.templates.L1PrivateShopSellList;
import l1j.server.server.serverpackets.S_Disconnect;

import server.LineageClient;
import server.manager.eva;
import server.system.autoshop.AutoShopManager;

public class C_ShopAndWarehouse extends ClientBasePacket {
	private final int TYPE_BUY_SHP = 0;  // 상점 or 개인 상점 사기
	private final int TYPE_SEL_SHP = 1;  // 상점 or 개인 상점 팔기
	private final int TYPE_PUT_PWH = 2;  // 개인 창고 맡기기
	private final int TYPE_GET_PWH = 3;  // 개인 창고 찾기
	private final int TYPE_PUT_CWH = 4;  // 혈맹 창고 맡기기
	private final int TYPE_GET_CWH = 5;  // 혈맹 창고 찾기
	private final int TYPE_PUT_EWH = 8;  // 엘프 창고 맡기기
	private final int TYPE_GET_EWH = 9;  // 엘프 창고 찾기
	private final int TYPE_GET_MWH = 10; // 패키지 창고 찾기
	
	public C_ShopAndWarehouse(byte abyte0[], LineageClient clientthread) throws Exception {
		super(abyte0);
		int npcObjectId = readD();
		int resultType = readC();
		int size = readC();
		@SuppressWarnings("unused")
		int unknown = readC();

		if(size < 0) return;
		
		L1PcInstance pc = clientthread.getActiveChar();
		if (isTwoLogin(pc)) return;
		if(pc.getOnlineStatus() == 0){
			clientthread.kick();
			clientthread.close();
			return;
        }
		
		int npcId = 0;
		String npcImpl = "";
		boolean isPrivateShop = false;
		L1Object findObject = L1World.getInstance().findObject(npcObjectId);
		if (findObject != null) { // 3셀
			int diffLocX = Math.abs(pc.getX() - findObject.getX());
			int diffLocY = Math.abs(pc.getY() - findObject.getY());
			if (diffLocX > 3 || diffLocY > 3) {
				return;
			}
			
			if (findObject instanceof L1NpcInstance) {
				L1NpcInstance targetNpc = (L1NpcInstance) findObject;
				npcId = targetNpc.getNpcTemplate().get_npcId();
				npcImpl = targetNpc.getNpcTemplate().getImpl();
			} 
			else if (findObject instanceof L1PcInstance) {
				isPrivateShop = true;
			}
		}
		
		switch(resultType) {
		case TYPE_BUY_SHP : // 상점 or 개인 상점 사기
			if (npcId == 70035 || npcId == 70041 || npcId == 70042) {
				int status = L1BugBearRace.getInstance().getBugRaceStatus();
				boolean chk = L1BugBearRace.getInstance().buyTickets;
			     if (status != L1BugBearRace.STATUS_READY || chk == false) {
			    	 return;
			     }
			}
			if(size != 0 && npcImpl.equalsIgnoreCase("L1Merchant")){
				buyItemFromShop(pc, npcId, size);
				break;
			}
			if(size != 0 && isPrivateShop){						     
				buyItemFromPrivateShop(pc, findObject, size);
				break;
			}
		case TYPE_SEL_SHP : // 상점 or 개인 상점 팔기
			if(size != 0 && npcImpl.equalsIgnoreCase("L1Merchant")) 
				sellItemToShop(pc, npcId, size);
			if(size != 0 && isPrivateShop)							 
				sellItemToPrivateShop(pc, findObject, size);
			break;
		case TYPE_PUT_PWH : // 개인 창고 맡기기
			if(size != 0 && npcImpl.equalsIgnoreCase("L1Dwarf"))
				putItemToPrivateWarehouse(pc, size);
			break;
		case TYPE_GET_PWH : // 개인 창고 찾기
			if(size != 0 && npcImpl.equalsIgnoreCase("L1Dwarf")) 
				getItemToPrivateWarehouse(pc, size);
			break;
		case TYPE_PUT_CWH : // 혈맹 창고 맡기기
			if(npcImpl.equalsIgnoreCase("L1Dwarf"))
				putItemToClanWarehouse(pc, size);
			break;
		case TYPE_GET_CWH : // 혈맹 창고 찾기
			if(npcImpl.equalsIgnoreCase("L1Dwarf")) 
				getItemToClanWarehouse(pc, size);
			break;
		case TYPE_PUT_EWH : // 엘프 창고 맡기기
			if(size != 0 && npcImpl.equalsIgnoreCase("L1Dwarf")) 
				putItemToElfWarehouse(pc, size);
			break;
		case TYPE_GET_EWH : // 엘프 창고 찾기
			if(size != 0 && npcImpl.equalsIgnoreCase("L1Dwarf")) 
				getItemToElfWarehouse(pc, size);
			break;
		case TYPE_GET_MWH : // 패키지 창고 찾기
			if(size != 0 && npcImpl.equalsIgnoreCase("L1Dwarf")) 
				getItemToPackageWarehouse(pc, size);
			break;
		default:		
		}
	}
	
	private boolean isTwoLogin(L1PcInstance c) {
		for(L1PcInstance target : L1World.getInstance().getAllPlayersToArray()){
			int count = 0;
			if(c.getId() == target.getId()){
				count++;
				if(count > 1){
					c.getNetConnection().kick();
					c.getNetConnection().close();
					target.getNetConnection().kick();
					target.getNetConnection().close();
					return true;
				}
			}
			else if(c.getId() != target.getId()){
				if(c.getAccountName().equalsIgnoreCase(target.getAccountName())) {
					if(!AutoShopManager.getInstance().isExistAutoShop(target.getId())){
						c.getNetConnection().kick();
						c.getNetConnection().close();
						target.getNetConnection().kick();
						target.getNetConnection().close();
						return true;
					}			
				}
			}
		}
		return false;
	}
	private void doNothingClanWarehouse(L1PcInstance pc) {
		if(pc == null) return;
		
		L1Clan clan = L1World.getInstance().getClan(pc.getClanname());
		if(clan == null) return;
		
		ClanWarehouse clanWarehouse = WarehouseManager.getInstance().getClanWarehouse(clan.getClanName());
		if(clanWarehouse == null) return;
		
		clanWarehouse.unlock(pc.getId());
	}

	private void getItemToPackageWarehouse(L1PcInstance pc, int size) {
		int objectId, count;
		L1ItemInstance item = null;
		PackageWarehouse w = WarehouseManager.getInstance().getPackageWarehouse(pc.getAccountName());
		if(w == null) return;
		
		for (int i = 0; i < size; i++) {
			objectId = readD();
			count = readD();		
			item = w.getItem(objectId);

			if(!isAvailableTrade(pc, objectId, item, count)) break;
			if(count > item.getCount()) count = item.getCount();
			if(!isAvailablePcWeight(pc, item, count)) break;
								
			w.tradeItem(item, count, pc.getInventory());
			eva.LogWareHouseAppend("패키지:", pc.getName(), "", item, count, objectId);
			}
	}

	private void getItemToElfWarehouse(L1PcInstance pc, int size) {
		if(pc.getLevel() < 5 || !pc.isElf()) return;
		
		L1ItemInstance item;
		ElfWarehouse elfwarehouse = WarehouseManager.getInstance().getElfWarehouse(pc.getAccountName());
		if(elfwarehouse == null) return;
		
		for (int i = 0, objectId, count; i < size; i++) {
			objectId = readD();
			count = readD();
			item = elfwarehouse.getItem(objectId);

			if(!isAvailableTrade(pc, objectId, item, count)) break;			
			if (count > item.getCount()) count = item.getCount();
			if(!isAvailablePcWeight(pc, item, count)) break;
			
			if (pc.getInventory().consumeItem(40494, 2)) {
				elfwarehouse.tradeItem(item, count, pc.getInventory());
				eva.LogWareHouseAppend("요정:찾", pc.getName(), "", item, count, objectId);
			} else {
				pc.sendPackets(new S_ServerMessage(337,"$767"));
				break;
			}
		}
	}

	private void putItemToElfWarehouse(L1PcInstance pc, int size) {
		if(pc.getLevel() < 5 || !pc.isElf()) return;
		
		L1Object object = null;
		L1ItemInstance item = null;
		ElfWarehouse elfwarehouse = WarehouseManager.getInstance().getElfWarehouse(pc.getAccountName());
		if(elfwarehouse == null) return;
		
		for (int i = 0, objectId, count ; i < size ; i++) {
			objectId = readD();
			count = readD();
			
			object = pc.getInventory(). getItem(objectId);
			item = (L1ItemInstance) object;

			// 운영자 창고 사용 금지
			if (pc.getAccessLevel() == Config.GMCODE) break;			
			if(!isAvailableTrade(pc, objectId, item, count)) break;
			if(count > item.getCount()) count = item.getCount();

			if (!item.getItem().isTradable()) {
				pc.sendPackets(new S_ServerMessage(210, item.getItem(). getName())); // \f1%0은 버리거나 또는 타인에게 양일을 할 수 없습니다.
				break;
			}
			
			if(!checkPetList(pc, item)) break;
			if(!checkDollList(pc, item)) break;
			if(!isAvailableWhCount(elfwarehouse, pc, item, count)) break;
			
			pc.getInventory().tradeItem(objectId, count, elfwarehouse);
			pc.getLight().turnOnOffLight();
			eva.LogWareHouseAppend("요정:맡", pc.getName(), "", item, count, objectId);			
		}
	}

	private void getItemToClanWarehouse(L1PcInstance pc, int size) {
		if(pc.getLevel() < 5) return;
		
		if(size == 0) {
			doNothingClanWarehouse(pc);
			return;
		}
		
		L1Clan clan = L1World.getInstance().getClan(pc.getClanname());
		
		if(!isAvailableClan(pc, clan)) return;
		
		L1ItemInstance item;
		ClanWarehouse clanWarehouse = WarehouseManager.getInstance().getClanWarehouse(clan.getClanName());
		if(clanWarehouse == null) return;
		for (int i = 0, objectId, count; i < size; i++) {
			objectId = readD();
			count = readD();
			item = clanWarehouse.getItem(objectId);   

			if(!isAvailableTrade(pc, objectId, item, count)) break;
			if(!hasAdena(pc)) break;			
			if(count >= item.getCount()) count = item.getCount();			
			if(!isAvailablePcWeight(pc, item, count)) break;
			
			clanWarehouse.tradeItem(item, count, pc.getInventory());			
			eva.LogWareHouseAppend("혈맹:찾", pc.getName(), pc.getClanname(), item, count, objectId);
		}
		clanWarehouse.unlock(pc.getId());
	}

	private void putItemToClanWarehouse(L1PcInstance pc, int size) {
		if(size == 0) {
			doNothingClanWarehouse(pc);
			return;
		}
		
		L1Clan clan = L1World.getInstance().getClan(pc.getClanname());
		
		if(!isAvailableClan(pc, clan)) return;
		
		L1Object object = null;
		L1ItemInstance item = null;
		ClanWarehouse clanWarehouse = WarehouseManager.getInstance().getClanWarehouse(clan.getClanName());
		if(clanWarehouse == null) return;
		
		for (int i = 0, objectId, count; i < size; i++) {
			objectId = readD();
			count = readD();
			
			object = pc.getInventory().getItem(objectId);
			item = (L1ItemInstance) object;
			
			if(item == null) break;
			if(count > item.getCount())	count = item.getCount();
			if(pc.getAccessLevel() == Config.GMCODE) break; // 운영자 창고 사용 금지
			if(!isAvailableTrade(pc, objectId, item, count)) break;
			
			if (item.getItem().getItemId() == 423012 || item.getItem().getItemId() == 423013){	// 10주년티
				pc.sendPackets(new S_ServerMessage(210, item.getItem().getName())); // \f1%0은 버리거나 또는 타인에게 양일을 할 수 없습니다.
				return;
			}
			if(item.getBless() >= 128 || !item.getItem().isTradable()){ 
				// \f1%0은 버리거나 또는 타인에게 양도 할 수 없습니다.
				pc.sendPackets(new S_ServerMessage(210, item.getItem().getName())); 
				break;
			}

			if(!checkPetList(pc, item)) break;
			if(!isAvailableWhCount(clanWarehouse, pc, item, count)) break;
			
			pc.getInventory().tradeItem(objectId, count, clanWarehouse);
			pc.getLight().turnOnOffLight();
			
			eva.LogWareHouseAppend("혈맹:맡", pc.getName(), pc.getClanname(), item, count, objectId);
		}
		clanWarehouse.unlock(pc.getId());
	}

	private void getItemToPrivateWarehouse(L1PcInstance pc, int size) {
		L1ItemInstance item = null;
		PrivateWarehouse warehouse = WarehouseManager.getInstance().getPrivateWarehouse(pc.getAccountName());
		if(warehouse == null) return;
		
		for (int i = 0, objectId, count ; i < size; i++) {
			objectId = readD();
			count = readD();			
			item = warehouse.getItem(objectId);  
			
			if(!isAvailableTrade(pc, objectId, item, count)) break;			
			if (count > item.getCount()) count = item.getCount();
			if(!isAvailablePcWeight(pc, item, count)) break;
			if(!hasAdena(pc)) break;
			
			warehouse.tradeItem(item, count, pc.getInventory());	
			eva.LogWareHouseAppend("일반:찾", pc.getName(), "", item, count, objectId);
		}
	}

	private void putItemToPrivateWarehouse(L1PcInstance pc, int size) {
		L1Object object = null;
		L1ItemInstance item = null;
		PrivateWarehouse warehouse = WarehouseManager.getInstance().getPrivateWarehouse(pc.getAccountName());
		if(warehouse == null) return;
		
		for(int i = 0, objectId, count; i < size; i++) {
			objectId = readD();
			count = readD();
			
			object = pc.getInventory().getItem(objectId);
			item = (L1ItemInstance) object;
			
			if (!item.getItem().isTradable()) {
				// \f1%0은 버리거나 또는 타인에게 양도 할 수 없습니다.
				pc.sendPackets(new S_ServerMessage(210, item.getItem().getName()));
				break;
			}
			
			if(item == null) break;
			if(pc.getAccessLevel() == Config.GMCODE) return; // 운영자 창고 사용 금지
			if(!isAvailableTrade(pc, objectId, item, count)) break;			
			if(!checkPetList(pc, item)) break;
			if(!isAvailableWhCount(warehouse, pc, item, count)) break;			
			if(count > item.getCount()) count = item.getCount();
			
			pc.getInventory().tradeItem(objectId, count, warehouse);					
			pc.getLight().turnOnOffLight();
			
			eva.LogWareHouseAppend("일반:맡", pc.getName(), "", item, count, objectId);
		}
	} 

	private void sellItemToPrivateShop(L1PcInstance pc, L1Object findObject, int size) {
		L1PcInstance targetPc = null;

		if (findObject instanceof L1PcInstance) {
			targetPc = (L1PcInstance) findObject;
			if (targetPc == null) 	
				return;
		}
		if (targetPc.isTradingInPrivateShop()) return;
		
		targetPc.setTradingInPrivateShop(true);
		
		L1PrivateShopBuyList psbl;
		L1ItemInstance item = null;
		boolean[] isRemoveFromList = new boolean[8];
		ArrayList<L1PrivateShopBuyList> buyList = targetPc.getBuyList();
		
		synchronized (buyList) {
			int order, itemObjectId, count, buyPrice, buyTotalCount, buyCount;
			for (int i = 0; i < size; i++) {
				itemObjectId = readD();				
				count = readCH();
				order = readC();
				
				item = pc.getInventory().getItem(itemObjectId);
	
				if(!isAvailableTrade(pc, itemObjectId, item, count)) break;				
				if(item.getBless() >= 128){
					// \f1%0은 버리거나 또는 타인에게 양도 할 수 없습니다.
					pc.sendPackets(new S_ServerMessage(210, item.getItem().getName())); 
					break;
				}
	
				psbl = (L1PrivateShopBuyList) buyList.get(order);
				buyPrice = psbl.getBuyPrice();
				buyTotalCount = psbl.getBuyTotalCount(); // 살 예정의 개수
				buyCount = psbl.getBuyCount(); // 산 누계
				if (count > buyTotalCount - buyCount) 
					count = buyTotalCount - buyCount;
				
				if (item.isEquipped()) {
					pc.sendPackets(new S_ServerMessage(905)); // 장비 하고 있는 아이템은 판매할 수 없습니다.
					break;
				}
				
				if(!isAvailablePcWeight(pc, item, count)) break;
				if(isOverMaxAdena(targetPc, buyPrice, count)) return;
				
				if (count >= item.getCount()) count = item.getCount();
					
				if (!targetPc.getInventory().checkItem(L1ItemId.ADENA, count * buyPrice)) {
					targetPc.sendPackets(new S_ServerMessage(189)); // \f1아데나가 부족합니다.
					break;
				}
			
				L1ItemInstance adena = targetPc.getInventory().findItemId(L1ItemId.ADENA);
				if (adena == null) break;
				
				targetPc.getInventory().tradeItem(adena, count * buyPrice, pc.getInventory());
				pc.getInventory().tradeItem(item, count, targetPc.getInventory());
				psbl.setBuyCount(count + buyCount);
				buyList.set(order, psbl);
				
				if (psbl.getBuyCount() == psbl.getBuyTotalCount()) { // 살 예정의 개수를 샀다
					isRemoveFromList[order] = true;
				}
				
				try {
					pc.saveInventory();
					targetPc.saveInventory();
				} catch (Exception e) {
					//_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
				}
			}
			// 매점한 아이템을 리스트의 말미로부터 삭제
			for (int i = 7; i >= 0; i--) {
				if (isRemoveFromList[i]) {
					buyList.remove(i);
				}
			}  
			targetPc.setTradingInPrivateShop(false);
		}
	}

	private void sellItemToShop(L1PcInstance pc, int npcId, int size) {
		L1Shop shop = ShopTable.getInstance().get(npcId);
		L1ShopSellOrderList orderList = shop.newSellOrderList(pc);
		int itemNumber; long itemcount;

		for (int i = 0; i < size; i++) {
			itemNumber = readD();
			itemcount = readD();
			if(itemcount <= 0){
				return;
			}
			orderList.add(itemNumber, (int)itemcount , pc);	
		}
		int bugok = orderList.BugOk();
		if (bugok == 0){
			shop.buyItems(orderList);
		}		
	}

	private void buyItemFromPrivateShop(L1PcInstance pc, L1Object findObject, int size) {
		L1PcInstance targetPc = null;
		if (findObject instanceof L1PcInstance) {
			targetPc = (L1PcInstance) findObject;			
			if (targetPc == null) 
				return;
		}
		if (targetPc.isTradingInPrivateShop()) return;

		ArrayList<L1PrivateShopSellList> sellList = targetPc.getSellList();
		
		synchronized (sellList) {
			// 품절이 발생해, 열람중의 아이템수와 리스트수가 다르다
			if (pc.getPartnersPrivateShopItemCount() != sellList.size()) return;
			if (pc.getPartnersPrivateShopItemCount() < sellList.size())  return;

			targetPc.setTradingInPrivateShop(true);
			
			L1ItemInstance item;
			L1PrivateShopSellList pssl;
			boolean[] isRemoveFromList = new boolean [8];
			int order, count, price, sellCount, sellPrice, itemObjectId, sellTotalCount;
			for (int i = 0 ; i < size ; i++) { // 구입 예정의 상품
				order = readD();
				count = readD();
				
				pssl = (L1PrivateShopSellList) sellList.get(order);
				itemObjectId = pssl.getItemObjectId();
				sellPrice = pssl.getSellPrice();
				sellTotalCount = pssl.getSellTotalCount(); // 팔 예정의 개수
				sellCount = pssl.getSellCount(); // 판 누계
				item = targetPc.getInventory().getItem(itemObjectId);
				
				if (item == null) break;
				if (item.isEquipped()) {
			        pc.sendPackets(new S_ServerMessage(905, "")); // 장비 하고 있는 아이템 구매 못하게.
			        break;
			    }
				
				if (count > sellTotalCount - sellCount) count = sellTotalCount - sellCount;
				if (count == 0) break;

				if(!isAvailablePcWeight(pc, item, count)) break;
				if(isOverMaxAdena(pc, sellPrice, count)) break; 
				
				price = count * sellPrice;	 
				if (price <= 0 ||price > 2000000000) break;
				
				if(!isAvailableTrade(pc, targetPc, itemObjectId, item, count)) break;
				
				if (count >= item.getCount()) count = item.getCount();	
				if (item.getCount() > 9999) break;
				
				if (!pc.getInventory().checkItem(L1ItemId.ADENA, price)) {
					pc.sendPackets(new S_ServerMessage(189)); // \f1아데나가 부족합니다.
					break;
				}
				
				L1ItemInstance adena = pc.getInventory().findItemId(L1ItemId.ADENA);
				
				if (targetPc == null || adena == null) break;
				if (targetPc.getInventory().tradeItem(item, count, pc.getInventory()) == null) break;
				
				pc.getInventory().tradeItem(adena, price, targetPc.getInventory());
				
				// %1%o	%0에 판매했습니다.
				String message = item.getItem().getName() + " (" + String.valueOf(count) + ")";
				targetPc.sendPackets(new S_ServerMessage(877, pc.getName(), message));
				
				pssl.setSellCount(count + sellCount);
				sellList.set(order, pssl);
				
				writeLogbuyPrivateShop(pc, targetPc, item, count, price);
				
				if (pssl.getSellCount() == pssl.getSellTotalCount()) // 해당 템을 다 팔았다
					isRemoveFromList[order] = true;				
				try {
					pc.saveInventory();
					targetPc.saveInventory();
				} catch (Exception e) {}
			}
			
			// 품절된 아이템을 리스트의 말미로부터 삭제
			for (int i = 7; i >= 0; i--) {
				if (isRemoveFromList[i]) {
					sellList.remove(i);
				}
			}
			targetPc.setTradingInPrivateShop(false);
		}
	}

	private void buyItemFromShop(L1PcInstance pc, int npcId, int size) {
		L1Shop shop = ShopTable.getInstance().get(npcId);
		L1ShopBuyOrderList orderList = shop.newBuyOrderList();
		int itemNumber; long itemcount;
	
		for (int i = 0; i < size; i++) {
			itemNumber = readD();
			itemcount = readD();
			if(itemcount <= 0) {
				return;
			}
			orderList.add(itemNumber, (int)itemcount , pc);	
		}
		int bugok = orderList.BugOk();
		if (bugok == 0){
			shop.sellItems(pc, orderList);
		}
	}

	/**
	 * 월드상에 있는 모든 캐릭의 계정을 비교해 같은 계정이 있다면 true 없다면 false
	 * @param c L1PcInstance
	 * @return 있다면 true
	 */
	private void writeLogbuyPrivateShop(L1PcInstance pc, L1PcInstance targetPc, L1ItemInstance item, int count, int price) {
		String itemadena = item.getName()+"("+price+")";
		eva.LogShopAppend("상점", pc.getName(), targetPc.getName(), item.getEnchantLevel(), itemadena, item.getBless(), count, item.getId());
	}

	private boolean isOverMaxAdena(L1PcInstance pc, int sellPrice, int count) {
		for (int j = 0; j < count; j++) { // 오버플로우를 체크
			if (sellPrice * j > 2000000000) {
				// 총판 매가격은 %d아데나를 초과할 수 없습니다.
				pc.sendPackets(new S_ServerMessage(904, "2000000000"));
				return true;
			}
		}
		return false;
	}
	
	private boolean checkDollList(L1PcInstance pc, L1ItemInstance item) {
		Object[] dolllist = pc.getDollList().values().toArray();
		for (Object dollObject : dolllist) {
			if (dollObject instanceof L1DollInstance) {
				L1DollInstance doll = (L1DollInstance) dollObject;
				if (item.getId() == doll.getItemObjId()) {
					// \f1%0은 버리거나 또는 타인에게 양일을 할 수 없습니다.
					pc.sendPackets(new S_ServerMessage(210, item.getItem().getName()));
					return false;
				}
			}
		}
		return true;
	}
	
	private boolean checkPetList(L1PcInstance pc, L1ItemInstance item) {
		Object[] petlist = pc.getPetList().values().toArray();
		for (Object petObject : petlist) {
			if (petObject instanceof L1PetInstance) {
				L1PetInstance pet = (L1PetInstance) petObject;
				if (item.getId() == pet.getItemObjId()) {
					// \f1%0은 버리거나 또는 타인에게 양도 할 수 없습니다.
					pc.sendPackets(new S_ServerMessage(210, item.getItem().getName()));
					return false;
				}
			}
		}
		return true;
	}

	private boolean isAvailableWhCount(Warehouse warehouse, L1PcInstance pc, L1ItemInstance item, int count) {
		if (warehouse.checkAddItemToWarehouse(item, count) == L1Inventory.SIZE_OVER) {
			 // \f1상대가 물건을 너무 가지고 있어 거래할 수 없습니다.
			pc.sendPackets(new S_ServerMessage(75));
			return false;
		}
		return true;
	}

	private boolean isAvailableClan(L1PcInstance pc, L1Clan clan) {
		if (pc.getClanid() == 0 || clan == null) {
			// \f1혈맹 창고를 사용하려면  혈맹에 가입하지 않으면 안됩니다.
			pc.sendPackets(new S_ServerMessage(208));
			return false;
		}
		return true;
	}

	private boolean isAvailablePcWeight(L1PcInstance pc, L1ItemInstance item, int count) {
		if (pc.getInventory().checkAddItem(item, count) != L1Inventory.OK) {
			 // \f1 가지고 있는 것이 무거워서 거래할 수 없습니다.
			pc.sendPackets(new S_ServerMessage(270));
			return false;
		}
		return true;
	}

	private boolean hasAdena(L1PcInstance pc) {
		if (!pc.getInventory().consumeItem(L1ItemId.ADENA, 30)) {
			// \f1아데나가 부족합니다.
			pc.sendPackets(new S_ServerMessage(189)); 
			return false;
		}
		return true;
	}

	private boolean isAvailableTrade(L1PcInstance pc, int objectId, L1ItemInstance item, int count) {
		boolean result = true;
		
		if(item == null)											result = false; 
		if(objectId != item.getId())								result = false;
		if(!item.isStackable() && count != 1)						result = false;
		if(item.getCount() <= 0 || item.getCount() > 2000000000)	result = false;
		if(count <= 0 ||count > 2000000000)							result = false;
		
		if(!result) {
			pc.sendPackets(new S_Disconnect());
		}

		return result;
	}
	
	private boolean isAvailableTrade(L1PcInstance pc, L1PcInstance targetPc, int itemObjectId, L1ItemInstance item, int count) {
		boolean result = true;
		
		if (itemObjectId != item.getId() || !item.isStackable() && count != 1 ) 	result = false;
		if (count <= 0 || item.getCount() <= 0 || item.getCount() < count) 			result = false;
		
		if(!result) {
			pc.sendPackets(new S_Disconnect());
			targetPc.sendPackets(new S_Disconnect());
		}
		
		return result;
	}
	
	@Override
	public String getType() {
		return "[C] C_Result";
	}
}