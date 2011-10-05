package bone.server.server.model.skill.skills;

import bone.server.server.model.L1Character;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.serverpackets.S_Strup;

public class PhysicalEnchantStr {

	public static void runSkill(L1Character cha, int buffIconDuration) {
		L1PcInstance pc = (L1PcInstance) cha;
		pc.getAbility().addAddedStr((byte) 5);
		pc.sendPackets(new S_Strup(pc, 5, buffIconDuration));
	}

}
