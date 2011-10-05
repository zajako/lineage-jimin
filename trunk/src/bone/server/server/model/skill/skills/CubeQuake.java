package bone.server.server.model.skill.skills;

import static bone.server.server.model.skill.L1SkillId.CUBE_QUAKE;
import bone.server.server.model.L1Character;
import bone.server.server.model.L1Cube;
import bone.server.server.model.L1EffectSpawn;
import bone.server.server.model.Instance.L1EffectInstance;
import bone.server.server.model.Instance.L1PcInstance;

public class CubeQuake {

	public static void runSkill(L1Character _user, L1PcInstance _player, int buffDuration) {
		L1EffectInstance effect = L1EffectSpawn.getInstance().spawnEffect(4500502,
				buffDuration * 1000, _user.getX(), _user.getY(), _user.getMapId());
		_player.getSkillEffectTimerSet().setSkillEffect(CUBE_QUAKE, buffDuration * 1000);
		effect.setCubeTime(4);
		effect.setCubePc(_player);
		L1Cube.getInstance().add(1, effect);
		
	}

}
