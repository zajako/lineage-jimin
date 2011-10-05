package bone.server.server.model.skill.skills;

import bone.server.server.model.L1Character;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.serverpackets.S_SkillIconWindShackle;

public class WindShackle {

	public static void runSkill(L1Character cha, int buffIconDuration) {
		if (cha instanceof L1PcInstance) {
			L1PcInstance pc = (L1PcInstance) cha;
			pc.sendPackets(new S_SkillIconWindShackle(pc.getId(), buffIconDuration));
		}
	}

}
