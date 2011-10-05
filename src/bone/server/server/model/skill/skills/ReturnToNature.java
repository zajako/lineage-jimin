package bone.server.server.model.skill.skills;

import bone.server.Config;
import bone.server.server.model.Broadcaster;
import bone.server.server.model.L1Character;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.model.Instance.L1SummonInstance;
import bone.server.server.serverpackets.S_ServerMessage;
import bone.server.server.serverpackets.S_SkillSound;

public class ReturnToNature {
	public static void runSkill(L1Character cha, L1Character _user, L1PcInstance _player) {
		if (Config.RETURN_TO_NATURE	&& cha instanceof L1SummonInstance) {
			L1SummonInstance summon = (L1SummonInstance) cha;
			Broadcaster.broadcastPacket(summon, new S_SkillSound(summon.getId(), 2245));
			summon.returnToNature();
		} else {
			if (_user instanceof L1PcInstance) {
				_player.sendPackets(new S_ServerMessage(79));
			}
		}
	}
}
