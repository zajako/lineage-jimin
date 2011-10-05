package bone.server.server.model.skill.skills;

import bone.server.server.model.L1Character;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.serverpackets.S_Dexup;

public class DressDexerity {

	public static void runSkill(L1Character cha, int buffIconDuration) {
		L1PcInstance pc = (L1PcInstance) cha;
		pc.getAbility().addAddedDex((byte) 2);
		pc.sendPackets(new S_Dexup(pc, 2, buffIconDuration));
	}

}
