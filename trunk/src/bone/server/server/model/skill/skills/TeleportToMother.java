package bone.server.server.model.skill.skills;

import bone.server.server.model.L1Character;
import bone.server.server.model.L1Teleport;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.serverpackets.S_Paralysis;
import bone.server.server.serverpackets.S_ServerMessage;

public class TeleportToMother {
	public static void runSkill(L1Character cha) {
		L1PcInstance pc = (L1PcInstance) cha;
		if (pc.getMap().isEscapable() || pc.isGm()) {
			L1Teleport.teleport(pc, 33051, 32337, (short) 4, 5, true);
		} else {
			pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_TELEPORT_UNLOCK, false));
			pc.sendPackets(new S_ServerMessage(647));
		}
	}
}
