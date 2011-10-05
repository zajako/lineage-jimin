package bone.server.server.model.skill.skills;

import bone.server.server.model.L1Character;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.serverpackets.S_ShowPolyList;

public class ShapeChange {
	public static void runSkill(L1Character cha) {
		L1PcInstance pc = (L1PcInstance) cha;
		pc.sendPackets(new S_ShowPolyList(pc.getId()));
	}
}
