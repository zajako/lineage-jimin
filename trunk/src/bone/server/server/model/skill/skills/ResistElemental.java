package bone.server.server.model.skill.skills;

import bone.server.server.model.L1Character;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.serverpackets.S_OwnCharAttrDef;

public class ResistElemental {

	public static void runSkill(L1Character cha) {
		L1PcInstance pc = (L1PcInstance) cha;
		pc.getResistance().addAllNaturalResistance(10);
		pc.sendPackets(new S_OwnCharAttrDef(pc));
	}

}
