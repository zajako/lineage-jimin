package bone.server.server.model.skill.skills;

import bone.server.server.model.L1Character;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.serverpackets.S_SkillIconAura;

public class StormEye {

	public static void runSkill(L1Character cha, int buffIconDuration) {
		L1PcInstance pc = (L1PcInstance) cha;
		pc.addBowHitup(2);
		pc.addBowDmgup(3);
		pc.sendPackets(new S_SkillIconAura(155, buffIconDuration));
	}

}
