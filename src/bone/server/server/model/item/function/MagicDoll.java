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

package l1j.server.server.model.item.function;

import l1j.server.Config;
import l1j.server.server.clientpackets.ClientBasePacket;
import l1j.server.server.datatables.NpcTable;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1Character;
import l1j.server.server.model.Instance.L1DollInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.L1ItemId;
import l1j.server.server.serverpackets.S_OwnCharStatus;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillIconGFX;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.templates.L1Item;
import l1j.server.server.templates.L1Npc;

@SuppressWarnings("serial")
public class MagicDoll extends L1ItemInstance{
	
	public MagicDoll(L1Item item){
		super(item);
	}

	@Override
	public void clickItem(L1Character cha, ClientBasePacket packet){
		if(cha instanceof L1PcInstance){
			L1PcInstance pc = (L1PcInstance)cha;
			int itemId = this.getItemId();
			useMagicDoll(pc, itemId, this.getId());
		}
	}
	
	private void useMagicDoll(L1PcInstance pc, int itemId, int itemObjectId) {
		if(pc.isInvisble()){
			return;
		}
		boolean isAppear = true;		
		
		L1DollInstance doll = null;		
		Object[] dollList = pc.getDollList().values().toArray();
		for (Object dollObject : dollList) {
			doll = (L1DollInstance) dollObject;
			if (doll.getItemObjId() == itemObjectId) { // 이미 꺼내고 있는 매직 실업 수당
				isAppear = false;
				break;
			}
		}

		if (isAppear) {
			
			int npcId = 0;
			int dollType = 0;
			int consumecount = 0;
			int dollTime = 0;
			
			switch(itemId){
			case L1ItemId.DOLL_BUGBEAR: 
				npcId = 80106; dollType = L1DollInstance.DOLLTYPE_BUGBEAR; consumecount = 50; dollTime = 1800; break;
			case L1ItemId.DOLL_SUCCUBUS: 
				npcId = 80107; dollType = L1DollInstance.DOLLTYPE_SUCCUBUS; consumecount = 50; dollTime = 1800; break;
			case L1ItemId.DOLL_WAREWOLF: 
				npcId = 80108; dollType = L1DollInstance.DOLLTYPE_WAREWOLF; consumecount = 50; dollTime = 1800; break;
			case L1ItemId.DOLL_STONEGOLEM: 
				npcId = 4500150; dollType = L1DollInstance.DOLLTYPE_STONEGOLEM; consumecount = 50; dollTime = 1800; break;
			case L1ItemId.DOLL_ELDER: 
				npcId = 4500151; dollType = L1DollInstance.DOLLTYPE_ELDER; consumecount = 50; dollTime = 1800; break;
			case L1ItemId.DOLL_CRUSTACEA: 
				npcId = 4500152; dollType = L1DollInstance.DOLLTYPE_CRUSTACEA; consumecount = 50; dollTime = 1800; break;			
			case L1ItemId.DOLL_SEADANCER: 
				npcId = 4500153; dollType = L1DollInstance.DOLLTYPE_SEADANCER; consumecount = 50; dollTime = 1800; break;
			case L1ItemId.DOLL_SNOWMAN: 
				npcId = 4500154; dollType = L1DollInstance.DOLLTYPE_SNOWMAN; consumecount = 50; dollTime = 1800; break;
			case L1ItemId.DOLL_COCATRIS:
				npcId = 4500155; dollType = L1DollInstance.DOLLTYPE_COCATRIS; consumecount = 50; dollTime = 1800; break;
			case L1ItemId.DOLL_DRAGON_M:
				npcId = 4500156; dollType = L1DollInstance.DOLLTYPE_DRAGON_M; consumecount = 500; dollTime = 18000; break;				
			case L1ItemId.DOLL_DRAGON_W:
				npcId = 4500157; dollType = L1DollInstance.DOLLTYPE_DRAGON_W; consumecount = 500; dollTime = 18000; break;				
			case L1ItemId.DOLL_HIGH_DRAGON_M:
				npcId = 4500158; dollType = L1DollInstance.DOLLTYPE_HIGH_DRAGON_M; consumecount = 500; dollTime = 18000; break;				
			case L1ItemId.DOLL_HIGH_DRAGON_W:
				npcId = 4500159; dollType = L1DollInstance.DOLLTYPE_HIGH_DRAGON_W; consumecount = 500; dollTime = 18000; break;
			case L1ItemId.DOLL_LAMIA:
				npcId = 4500160; dollType = L1DollInstance.DOLLTYPE_LAMIA; consumecount = 50; dollTime = 1800; break;
			case L1ItemId.DOLL_SPATOI:
				npcId = 4500161; dollType = L1DollInstance.DOLLTYPE_SPATOI; consumecount = 50; dollTime = 1800; break;
			case 437018:
				npcId = 4000009; dollType = L1DollInstance.DOLLTYPE_HELPER; consumecount = 50; dollTime = 1800; break;
			}
			
			if (!pc.getInventory().checkItem(41246, consumecount)) {
				pc.sendPackets(new S_ServerMessage(337, "$5240"));
				return;
			}
			if (dollList.length >= Config.MAX_DOLL_COUNT) {
				// \f1 더 이상의 monster를 조종할 수 없습니다.
				pc.sendPackets(new S_ServerMessage(319));
				return;
			}
			if (itemId == 437018 && pc.getLevel() > 60){
				pc.sendPackets(new S_SystemMessage("쫄법사 인형은 60까지 사용할 수 있습니다."));
				return;
			}
			L1Npc template = NpcTable.getInstance().getTemplate(npcId);
			doll = new L1DollInstance(template, pc, dollType, itemObjectId, dollTime * 1000);
			pc.sendPackets(new S_SkillSound(doll.getId(), 5935));
			Broadcaster.broadcastPacket(pc, new S_SkillSound(doll.getId(), 5935));
			pc.sendPackets(new S_SkillIconGFX(56, dollTime));
			pc.sendPackets(new S_OwnCharStatus(pc));
			pc.getInventory().consumeItem(41246, consumecount);			
		} else {
			doll.deleteDoll();
			pc.sendPackets(new S_SkillIconGFX(56, 0));
			pc.sendPackets(new S_OwnCharStatus(pc));			
		}
	}
}

