package bone.server.server.model.skill.skills;

import bone.server.server.model.L1Character;
import bone.server.server.model.L1Magic;
import bone.server.server.model.Instance.L1NpcInstance;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.model.skill.L1SkillId;

public class CounterDetection {

	public static int runSkill(L1Magic _magic, L1Character cha, L1PcInstance _player) {
		int dmg = 0;
		
		if (cha instanceof L1PcInstance) {
			dmg = _magic.calcMagicDamage(L1SkillId.COUNTER_DETECTION);
		} else if (cha instanceof L1NpcInstance) {
			L1NpcInstance npc = (L1NpcInstance) cha;
			int hiddenStatus = npc.getHiddenStatus();
			if (hiddenStatus == L1NpcInstance.HIDDEN_STATUS_SINK) {
				npc.appearOnGround(_player);
			} else {
				dmg = 0;
			}
		} else {
			dmg = 0;
		}
		
		return dmg;
	}

}
