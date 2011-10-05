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

package bone.server.server.clientpackets;

import java.util.ArrayList;

import server.LineageClient;
import server.message.ServerMessage;
import server.system.autoshop.AutoShopManager;

import bone.server.Config;
import bone.server.server.ActionCodes;
import bone.server.server.model.Broadcaster;
import bone.server.server.model.L1World;
import bone.server.server.model.Instance.L1DollInstance;
import bone.server.server.model.Instance.L1ItemInstance;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.model.Instance.L1PetInstance;
import bone.server.server.model.skill.L1SkillId;
import bone.server.server.serverpackets.S_Disconnect;
import bone.server.server.serverpackets.S_DoActionGFX;
import bone.server.server.serverpackets.S_DoActionShop;
import bone.server.server.serverpackets.S_OwnCharStatus;
import bone.server.server.serverpackets.S_ServerMessage;
import bone.server.server.serverpackets.S_SkillIconGFX;
import bone.server.server.serverpackets.S_SystemMessage;
import bone.server.server.templates.L1PrivateShopBuyList;
import bone.server.server.templates.L1PrivateShopSellList;

// Referenced classes of package bone.server.server.clientpackets:
// ClientBasePacket

public class C_Shop extends ClientBasePacket {

	private static final String C_SHOP = "[C] C_Shop";

	public C_Shop(byte abyte0[], LineageClient clientthread) {
		super(abyte0);

		L1PcInstance pc = clientthread.getActiveChar();
		if (pc.getAccessLevel() == Config.GMCODE){return;}
		if (pc.isGhost()) {	return; }
		if (pc.isInvisble()) {
			pc.sendPackets(new S_ServerMessage(755));
			return;
		}
		int mapId = pc.getMapId();
		if (mapId != 340 && mapId != 350 && mapId != 360 && mapId != 370) {
			pc.sendPackets(new S_ServerMessage(876));
			return;
		}
		if (pc.getGfxId().getTempCharGfx() != pc.getClassId() &&
				pc.getSkillEffectTimerSet().getSkillEffectTimeSec(L1SkillId.SHAPE_CHANGE) <=0){
			pc.sendPackets(new S_SystemMessage("변신 아이템을 해제 해 주시기 바랍니다."));
			return;
		}
		ArrayList<L1PrivateShopSellList> sellList = pc.getSellList();
		ArrayList<L1PrivateShopBuyList> buyList = pc.getBuyList();
		L1ItemInstance checkItem;
		boolean tradable = true;

		int type = readC();
		if (type == 0) { // 개시
			int sellTotalCount = readH();
			int sellObjectId;
			int sellPrice;
			int sellCount;
			Object[] petlist = null;
			Object[] dolllist = null;
			for (int i = 0; i < sellTotalCount; i++) {
				sellObjectId = readD();
				sellPrice = readD();
				sellCount = readD();

				/** 개인상점 오류 수정 */
				if(sellTotalCount > 7){ 
					pc.sendPackets(new S_SystemMessage("물품등록은 7개까지만 가능합니다.")); 
					return;
				}

				// 거래 가능한 아이템이나 체크
				checkItem = pc.getInventory().getItem(sellObjectId);
				if (sellObjectId != checkItem.getId()) {
					pc.sendPackets(new S_Disconnect());
					return;
				}
				if (!checkItem.isStackable() && sellCount != 1) {
					pc.sendPackets(new S_Disconnect());
					return;
				}
				if (sellCount > checkItem.getCount()) {
					sellCount = checkItem.getCount();
				}
				if (checkItem.getCount() < sellCount || checkItem.getCount() <= 0 || sellCount <= 0) {
					sellList.clear();  
					buyList.clear();				     
					return;
				}
				if (checkItem.getItem().getItemId() == 423012 || checkItem.getItem().getItemId() == 423013){	// 10주년티
					pc.sendPackets(new S_ServerMessage(210, checkItem.getItem().getName())); // \f1%0은 버리거나 또는 타인에게 양일을 할 수 없습니다.
					return;
				}
				if(checkItem.getBless() >= 128){
					pc.sendPackets(new S_ServerMessage(ServerMessage.CANNOT_DROP_OR_TRADE, checkItem.getItem().getName())); 
					return;
				}
				if (!checkItem.getItem().isTradable()) {
					tradable = false;
					pc.sendPackets(new S_ServerMessage(166, checkItem.getItem().getName(), "거래 불가능합니다. "));
				}
				
				if ((checkItem.getItem().getType2() == 1 || checkItem.getItem().getType2() == 2) 
						&& pc.getMapId() != 350) {
					sellList.clear();
					buyList.clear();
					pc.setPrivateShop(false);
					pc.sendPackets(new S_DoActionGFX(pc.getId(), ActionCodes.ACTION_Idle));
					Broadcaster.broadcastPacket(pc, new S_DoActionGFX(pc.getId(), ActionCodes.ACTION_Idle));
					pc.sendPackets(new S_SystemMessage("장비류는 기란 시장에서만 가능합니다."));
					return;
				}
				if (checkItem.getItem().getType2() == 0 && pc.getMapId() != 360) {
					sellList.clear();
					buyList.clear();
					pc.setPrivateShop(false);
					pc.sendPackets(new S_DoActionGFX(pc.getId(), ActionCodes.ACTION_Idle));
					Broadcaster.broadcastPacket(pc, new S_DoActionGFX(pc.getId(), ActionCodes.ACTION_Idle));
					pc.sendPackets(new S_SystemMessage("잡템류는 오렌 시장에서만 가능합니다."));
					return;
				}
				
				dolllist = pc.getDollList().values().toArray();
				for (Object dollObject : dolllist) {
					if (dollObject instanceof L1DollInstance) {
						L1DollInstance doll = (L1DollInstance) dollObject;
						if (checkItem.getId() == doll.getItemObjId()) {
							tradable = false;
							pc.sendPackets(new S_ServerMessage(166, checkItem.getItem().getName(), "거래 불가능합니다. "));
							break;
						}
					}
				}
				
				petlist = pc.getPetList().values().toArray();
				for (Object petObject : petlist) {
					if (petObject instanceof L1PetInstance) {
						L1PetInstance pet = (L1PetInstance) petObject;
						if (checkItem.getId() == pet.getItemObjId()) {
							tradable = false;
							pc.sendPackets(new S_ServerMessage(166, checkItem.getItem().getName(), "거래 불가능합니다. "));
							break;
						}
					}
				}
				L1PrivateShopSellList pssl = new L1PrivateShopSellList();
				pssl.setItemObjectId(sellObjectId);
				pssl.setSellPrice(sellPrice);
				pssl.setSellTotalCount(sellCount);
				sellList.add(pssl);
			}
			int buyTotalCount = readH();
			int buyObjectId;
			int buyPrice;
			int buyCount;
			for (int i = 0; i < buyTotalCount; i++) {
				buyObjectId = readD();
				buyPrice = readD();
				buyCount = readD();				
				if(sellTotalCount > 7){ 
					pc.sendPackets(new S_SystemMessage("물품등록은 7개까지만 가능합니다.")); 
					return;
				}

				checkItem = pc.getInventory().getItem(buyObjectId);

				if (buyObjectId != checkItem.getId()) {
					pc.sendPackets(new S_Disconnect());
					return;
				}
				if (!checkItem.isStackable() && buyCount != 1) {
					pc.sendPackets(new S_Disconnect());
					return;
				}
				if (buyCount <= 0 || checkItem.getCount() <= 0) {
					pc.sendPackets(new S_Disconnect());
					return;
				}
				//이부분 일단 주석 처리 구매 카운터가 산다는건데 이것때문에 값이 변경되는듯..
				/*
				if (buyCount > checkItem.getCount()) {
					buyCount = checkItem.getCount();
				}*/

				if (!checkItem.getItem().isTradable()) {
					tradable = false;
					pc.sendPackets(new S_ServerMessage(166, checkItem.getItem().getName(), "거래 불가능합니다. "));
				}
				petlist = pc.getPetList().values().toArray();
				for (Object petObject : petlist) {
					if (petObject instanceof L1PetInstance) {
						L1PetInstance pet = (L1PetInstance) petObject;
						if (checkItem.getId() == pet.getItemObjId()) {
							tradable = false;
							pc.sendPackets(new S_ServerMessage(166, checkItem.getItem().getName(), "거래 불가능합니다. "));
							break;
						}
					}
				}
				L1PrivateShopBuyList psbl = new L1PrivateShopBuyList();
				psbl.setItemObjectId(buyObjectId);
				psbl.setBuyPrice(buyPrice);
				psbl.setBuyTotalCount(buyCount);
				buyList.add(psbl);
			}
			if (sellTotalCount == 0 && buyTotalCount == 0){
				pc.sendPackets(new S_ServerMessage(908));
				pc.setPrivateShop(false);
				pc.sendPackets(new S_DoActionGFX(pc.getId(), ActionCodes.ACTION_Idle));
				Broadcaster.broadcastPacket(pc, new S_DoActionGFX(pc.getId(),ActionCodes.ACTION_Idle));
				return;
			}
			if (!tradable) { // 거래 불가능한 아이템이 포함되어 있는 경우, 개인 상점 종료
				sellList.clear();
				buyList.clear();
				pc.setPrivateShop(false);
				pc.sendPackets(new S_DoActionGFX(pc.getId(), ActionCodes.ACTION_Idle));
				Broadcaster.broadcastPacket(pc, new S_DoActionGFX(pc.getId(),ActionCodes.ACTION_Idle));
				return;
			}
			byte[] chat = readByte();
			pc.setShopChat(chat);
			pc.setPrivateShop(true);
			pc.sendPackets(new S_DoActionShop(pc.getId(),ActionCodes.ACTION_Shop, chat));
			Broadcaster.broadcastPacket(pc, new S_DoActionShop(pc.getId(),ActionCodes.ACTION_Shop, chat));
		} else if (type == 1) { // 종료
			if (isTwoLogin(pc)) return;
			sellList.clear();
			buyList.clear();
			pc.setPrivateShop(false);
			pc.sendPackets(new S_DoActionGFX(pc.getId(), ActionCodes.ACTION_Idle));
			Broadcaster.broadcastPacket(pc, new S_DoActionGFX(pc.getId(), ActionCodes.ACTION_Idle));
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
	@Override
	public String getType() {
		return C_SHOP;
	}
}
