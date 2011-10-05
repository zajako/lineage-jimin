package bone.server.server.model.skill.skills;

import bone.server.server.model.L1Character;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.serverpackets.S_SkillIconAura;

public class StormShot {

	public static void runSkill(L1Character cha, int buffIconDuration) {
		L1PcInstance pc = (L1PcInstance) cha;
		pc.addBowDmgup(5);
		pc.addBowHitup(-3);
		pc.sendPackets(new S_SkillIconAura(165, buffIconDuration));
	}

}
