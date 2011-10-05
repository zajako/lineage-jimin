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

import static l1j.server.server.model.skill.L1SkillId.POLLUTE_WATER;

import java.util.Random;

import l1j.server.server.clientpackets.ClientBasePacket;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1Character;
import l1j.server.server.model.L1ItemDelay;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.L1ItemId;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.templates.L1EtcItem;
import l1j.server.server.templates.L1Item;

@SuppressWarnings("serial")
public class HealingPotion extends L1ItemInstance{
	private static Random _random = new Random();
	
	public HealingPotion(L1Item item){
		super(item);
	}

	@Override
	public void clickItem(L1Character cha, ClientBasePacket packet){
		if(cha instanceof L1PcInstance){
			L1PcInstance pc = (L1PcInstance)cha;
			L1ItemInstance useItem = pc.getInventory().getItem(this.getId());
			int itemId = useItem.getItemId();
			int delay_id = 0;
			delay_id = ((L1EtcItem) useItem.getItem()).get_delayid();
			
			if (delay_id != 0) { // 지연 설정 있어
				if (pc.hasItemDelay(delay_id) == true) {
					return;
				}
			}
			switch(itemId){
			case 40010:
			case 40019:
			case 40029:
				UseHeallingPotion(pc, 15, 189);
				break;
			case 40011:
			case 40020:
				UseHeallingPotion(pc, 45, 194);
				break;
			case 40012:
			case 40021:
			case 435000:
				UseHeallingPotion(pc, 75, 197);
				break;
			case 40022:
			case 41141:
				UseHeallingPotion(pc, 20, 189);
				break;
			case 40023:
				UseHeallingPotion(pc, 30, 194);
				break;
			case 40024:
				UseHeallingPotion(pc, 55, 197);
				break;
			case 40026:
			case 40027:
			case 40028:
				UseHeallingPotion(pc, 25, 189);
				break;
			case 40043:
				UseHeallingPotion(pc, 600, 189);
				break;
			case 40058:
				UseHeallingPotion(pc, 30, 189);
				break;
			case 40071:
				UseHeallingPotion(pc, 70, 197);
				break;
			case 40506:
				UseHeallingPotion(pc, 70, 197);
				break;
			case 40734:
				UseHeallingPotion(pc, 50, 189);
				break;
			case 40930:
				UseHeallingPotion(pc, 70, 189);
				break;
			case 41298:
				UseHeallingPotion(pc, 4, 189);
				break;
			case 41299:
				UseHeallingPotion(pc, 15, 194);
				break;
			case 41300:
				UseHeallingPotion(pc, 35, 197);
				break;
			case 41337:
			case 140012:
				UseHeallingPotion(pc, 85, 197);
				break;
			case 41403:
				UseHeallingPotion(pc, 300, 189);
				break;
			case 41411:
			case 240010:
				UseHeallingPotion(pc, 10, 189);
				break;
			case 41417:
			case 41418:
			case 41419:
			case 41420:
			case 41421:
				UseHeallingPotion(pc, 90, 197);
				break;
			case 140010:
				UseHeallingPotion(pc, 25, 189);
				break;
			case 140011:
				UseHeallingPotion(pc, 55, 194);
				break;
			case 140506:
				UseHeallingPotion(pc, 80, 197);				
				break;
			case L1ItemId.MYSTERY_THICK_HEALING_POTION:
				UseHeallingPotion(pc, 51, 189);
				break;
			}
			pc.getInventory().removeItem(useItem, 1);
			L1ItemDelay.onItemUse(pc, useItem); // 아이템 지연 개시
		}
	}
	
	private void UseHeallingPotion(L1PcInstance pc, int healHp, int gfxid) {
		if (pc.getSkillEffectTimerSet().hasSkillEffect(71) == true) { // 디케이포션 상태
			pc.sendPackets(new S_ServerMessage(698)); // 마력에 의해 아무것도 마실 수가 없습니다.
			return;
		}

		// 앱솔루트베리어의 해제
		pc.cancelAbsoluteBarrier();

		pc.sendPackets(new S_SkillSound(pc.getId(), gfxid));
		Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), gfxid));
		pc.sendPackets(new S_ServerMessage(77)); // \f1기분이 좋아졌습니다.
		healHp *= (_random.nextGaussian() / 5.0D) + 1.0D;
		if (pc.getSkillEffectTimerSet().hasSkillEffect(POLLUTE_WATER)) { // 포르트워타중은 회복량1/2배
			healHp /= 2;
		}
		pc.setCurrentHp(pc.getCurrentHp() + healHp);
	}
}

