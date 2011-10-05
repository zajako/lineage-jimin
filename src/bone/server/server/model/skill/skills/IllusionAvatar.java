package bone.server.server.model.skill.skills;

import bone.server.server.model.L1Character;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.serverpackets.S_SPMR;

public class IllusionAvatar {

	public static void runSkill(L1Character cha) {
		L1PcInstance pc = (L1PcInstance) cha;
		pc.addDmgup(10);
		pc.getAbility().addSp(6);
		pc.sendPackets(new S_SPMR(pc));
	}

}
