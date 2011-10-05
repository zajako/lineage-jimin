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

import static bone.server.server.model.skill.L1SkillId.CURSE_BLIND;
import static bone.server.server.model.skill.L1SkillId.DARKNESS;
import static bone.server.server.model.skill.L1SkillId.STATUS_FLOATING_EYE;
import bone.server.server.TimeController.LiveTimeController;
import bone.server.server.clientpackets.ClientBasePacket;
import bone.server.server.model.L1Character;
import bone.server.server.model.L1Cooking;
import bone.server.server.model.Instance.L1ItemInstance;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.model.item.L1ItemId;
import bone.server.server.serverpackets.S_CurseBlind;
import bone.server.server.serverpackets.S_PacketBox;
import bone.server.server.serverpackets.S_ServerMessage;
import bone.server.server.templates.L1Item;

@SuppressWarnings("serial")
public class Food extends L1ItemInstance{

	public Food(L1Item item){
		super(item);
	}

	@Override
	public void clickItem(L1Character cha, ClientBasePacket packet){
		if(cha instanceof L1PcInstance){
			L1PcInstance pc = (L1PcInstance)cha;
			L1ItemInstance useItem = pc.getInventory().getItem(this.getId());
			int itemId = useItem.getItemId();
			switch(itemId){
			case 40056:
			case 40057:
			case 40059:
			case 40060:
			case 40061:
			case 40062:
			case 40063:
			case 40064:
			case 40065:
			case 40069:
			case 40072:
			case 40073:
			case 41266:
			case 41267:
			case 41274:
			case 41275:
			case 41276:
			case 41296:
			case 41297:
			case 41252:
			case 49040:
			case 49041:
			case 49042:
			case 49043:
			case 49044:
			case 49045:
			case 49046:
			case 49047:
			case 140061:
			case 140062:
			case 140065:
			case 140069:
			case 140072:
			case 436000:
			case 437022:
				pc.getInventory().removeItem(useItem, 1);
				// XXX 음식 마다의 만복도가 차이가 나지 않는다
				if (pc.get_food() < 225) { // 100%
					int chargeCount = 1;
					if(itemId == 436000){
						chargeCount = 5;
					}
					pc.set_food(pc.get_food() + 10 * chargeCount);
					if (pc.get_food() > 225) {
						pc.set_food(225);
						LiveTimeController.getInstance().addMember(pc);
					}
					pc.sendPackets(new S_PacketBox(S_PacketBox.FOOD, pc.get_food()));
				} else if (pc.get_food() > 225) {
					pc.set_food(225);
				}
				if (itemId == 40057) { // 후로팅아이육
					pc.getSkillEffectTimerSet().setSkillEffect(STATUS_FLOATING_EYE, 0);
					if (pc.getSkillEffectTimerSet().hasSkillEffect(CURSE_BLIND) || pc.getSkillEffectTimerSet().hasSkillEffect(DARKNESS)) {
						pc.sendPackets(new S_CurseBlind(2));
					}
					pc.sendPackets(new S_ServerMessage(152)); // \f1이상하게도 감각이 예민해진것 같습니다.
				}
				pc.sendPackets(new S_ServerMessage(76, useItem.getItem().getNameId()));
				break;
			}
			if ((itemId >= 41277 && itemId <= 41292)
					|| (itemId >= 49049 && itemId <= 49064)
					|| (itemId >= L1ItemId.NORMAL_COOKFOOD_3RD_START && itemId <= L1ItemId.SPECIAL_COOKFOOD_3RD_END)){ // 요리아이템
				L1Cooking.useCookingItem(pc, useItem);
			}
		}
	}
}

