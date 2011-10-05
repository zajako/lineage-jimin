package bone.server.server.model.skill.skills;

import static bone.server.server.model.skill.L1SkillId.STATUS_FLOATING_EYE;
import bone.server.server.model.L1Character;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.serverpackets.S_CurseBlind;

public class Darkness {

	public static void runSkill(L1Character cha) {
		if (cha instanceof L1PcInstance) {
			L1PcInstance pc = (L1PcInstance) cha;
			if (pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_FLOATING_EYE)) {
				pc.sendPackets(new S_CurseBlind(2));
			} else {
				pc.sendPackets(new S_CurseBlind(1));
			}
		}
	}
}
