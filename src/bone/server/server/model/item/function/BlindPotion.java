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
import static bone.server.server.model.skill.L1SkillId.DECAY_POTION;
import static bone.server.server.model.skill.L1SkillId.STATUS_FLOATING_EYE;
import bone.server.server.clientpackets.ClientBasePacket;
import bone.server.server.model.L1Character;
import bone.server.server.model.Instance.L1ItemInstance;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.serverpackets.S_CurseBlind;
import bone.server.server.serverpackets.S_ServerMessage;
import bone.server.server.templates.L1Item;

@SuppressWarnings("serial")
public class BlindPotion extends L1ItemInstance{
	
	public BlindPotion(L1Item item){
		super(item);
	}

	@Override
	public void clickItem(L1Character cha, ClientBasePacket packet){
		if(cha instanceof L1PcInstance){
			L1PcInstance pc = (L1PcInstance)cha;
			L1ItemInstance useItem = pc.getInventory().getItem(this.getId());
			useBlindPotion(pc);
			pc.getInventory().removeItem(useItem, 1);
		}
	}
	
	private void useBlindPotion(L1PcInstance pc) {
		if (pc.getSkillEffectTimerSet().hasSkillEffect(DECAY_POTION)) {
			pc.sendPackets(new S_ServerMessage(698)); // \f1마력에 의해 아무것도 마실 수가 없습니다.
			return;
		}

		// 아브소르트바리아의 해제
		pc.cancelAbsoluteBarrier();

		int time = 450;
		if (pc.getSkillEffectTimerSet().hasSkillEffect(CURSE_BLIND)) {
			pc.getSkillEffectTimerSet().killSkillEffectTimer(CURSE_BLIND);
		} else if (pc.getSkillEffectTimerSet().hasSkillEffect(DARKNESS)) {
			pc.getSkillEffectTimerSet().killSkillEffectTimer(DARKNESS);
		}

		if (pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_FLOATING_EYE)) {
			pc.sendPackets(new S_CurseBlind(2));
		} else {
			pc.sendPackets(new S_CurseBlind(1));
		}

		pc.getSkillEffectTimerSet().setSkillEffect(CURSE_BLIND, time * 1000);
	}
}

