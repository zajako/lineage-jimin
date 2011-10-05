package bone.server.server.model.skill.skills;

import bone.server.server.model.L1Character;
import bone.server.server.model.L1World;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.serverpackets.S_Message_YN;

public class CallClan {

	public static void runSkill(L1Character cha, int _targetID) {
		L1PcInstance pc = (L1PcInstance) cha;
		L1PcInstance clanPc = (L1PcInstance) L1World.getInstance().findObject(_targetID);
		if (clanPc != null) {
			clanPc.setTempID(pc.getId()); 
			clanPc.sendPackets(new S_Message_YN(729, ""));
		}
	}

}
