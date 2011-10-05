package bone.server.server.model.skill.skills;

import bone.server.server.model.L1Character;
import bone.server.server.model.L1EffectSpawn;

public class LifeStream {

	public static void runSkill(int _targetX, int _targetY, L1Character _user, int buffDuration) {
		L1EffectSpawn.getInstance().spawnEffect(81169, 
				buffDuration * 1000, _targetX, _targetY, _user.getMapId());
	}

}
