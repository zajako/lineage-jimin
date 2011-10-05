package bone.server.server.model.skill.skills;

import static bone.server.server.model.skill.L1SkillId.EARTH_BIND;
import static bone.server.server.model.skill.L1SkillId.FREEZING_BLIZZARD;
import static bone.server.server.model.skill.L1SkillId.FREEZING_BREATH;
import static bone.server.server.model.skill.L1SkillId.ICE_LANCE;
import bone.server.server.model.L1Character;
import bone.server.server.model.L1CurseParalysis;
import bone.server.server.model.Instance.L1MonsterInstance;
import bone.server.server.model.Instance.L1PcInstance;

public class CurseParalyze {

	public static void runSkill(L1Character cha) {
		if (!cha.getSkillEffectTimerSet().hasSkillEffect(EARTH_BIND) && !cha.getSkillEffectTimerSet().hasSkillEffect(ICE_LANCE)
				&& !cha.getSkillEffectTimerSet().hasSkillEffect(FREEZING_BLIZZARD)
				&& !cha.getSkillEffectTimerSet().hasSkillEffect(FREEZING_BREATH)) {
			if (cha instanceof L1PcInstance) {
				L1CurseParalysis.curse(cha, 8000, 16000);
			} else if (cha instanceof L1MonsterInstance) {
				L1CurseParalysis.curse(cha, 0, 16000);
			}
		}
	}
}