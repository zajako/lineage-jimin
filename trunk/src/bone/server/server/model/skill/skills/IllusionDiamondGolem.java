package bone.server.server.model.skill.skills;

import bone.server.server.model.L1Character;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.serverpackets.S_OwnCharAttrDef;

public class IllusionDiamondGolem {

	public static void RunSkill(L1Character cha) {
		L1PcInstance pc = (L1PcInstance) cha;
		pc.getAC().addAc(-20);
		pc.sendPackets(new S_OwnCharAttrDef(pc));
	}

}
