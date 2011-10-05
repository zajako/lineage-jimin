package bone.server.server.model.skill.skills;

import bone.server.server.model.Broadcaster;
import bone.server.server.model.L1Character;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.serverpackets.S_SkillBrave;

public class WindWalk {
	public static void runSkill(L1Character cha, int buffIconDuration) {
		L1PcInstance pc = (L1PcInstance) cha;
		pc.getMoveState().setBraveSpeed(4);
		pc.sendPackets(new S_SkillBrave(pc.getId(), 4, buffIconDuration));
		Broadcaster.broadcastPacket(pc, new S_SkillBrave(pc.getId(), 4, 0));
	}
}
