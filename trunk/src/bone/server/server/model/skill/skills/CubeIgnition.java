package bone.server.server.model.skill.skills;

import static bone.server.server.model.skill.L1SkillId.CUBE_IGNITION;
import bone.server.server.model.L1Character;
import bone.server.server.model.L1Cube;
import bone.server.server.model.L1EffectSpawn;
import bone.server.server.model.Instance.L1EffectInstance;
import bone.server.server.model.Instance.L1PcInstance;

public class CubeIgnition {
	public static void runSkill(L1Character user, L1PcInstance player, int skillBuffDuration) {
		L1EffectInstance effect = L1EffectSpawn.getInstance().spawnEffect(4500501,
				skillBuffDuration * 1000, user.getX(), user.getY(), user.getMapId());
		player.getSkillEffectTimerSet().setSkillEffect(CUBE_IGNITION, skillBuffDuration * 1000);
		effect.setCubeTime(4);
		effect.setCubePc(player);
		L1Cube.getInstance().add(0, effect);
	}

}
