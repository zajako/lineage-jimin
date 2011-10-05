package bone.server.server.model.skill.skills;

import static bone.server.server.model.skill.L1SkillId.CURSE_BLIND;
import static bone.server.server.model.skill.L1SkillId.DARKNESS;
import static bone.server.server.model.skill.L1SkillId.STATUS_CURSE_PARALYZED;
import static bone.server.server.model.skill.L1SkillId.STATUS_CURSE_PARALYZING;
import bone.server.server.model.L1Character;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.serverpackets.S_CurseBlind;

public class RemoveCure {

	public static void runSkill(L1Character cha) {
		cha.curePoison();
		if (cha.getSkillEffectTimerSet().hasSkillEffect(STATUS_CURSE_PARALYZING) || cha.getSkillEffectTimerSet().hasSkillEffect(STATUS_CURSE_PARALYZED)) {
			cha.cureParalaysis();
		}
		if (cha.getSkillEffectTimerSet().hasSkillEffect(CURSE_BLIND) || cha.getSkillEffectTimerSet().hasSkillEffect(DARKNESS)) {
			if (cha instanceof L1PcInstance) {
				L1PcInstance pc = (L1PcInstance) cha;
				pc.sendPackets(new S_CurseBlind(0));
			}
		}
	}
}
