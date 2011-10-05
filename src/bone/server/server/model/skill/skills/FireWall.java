package bone.server.server.model.skill.skills;

import bone.server.server.model.L1Character;
import bone.server.server.model.L1EffectSpawn;

public class FireWall {

	public static void runSkill(L1Character _user, int _targetX, int _targetY) {
		L1EffectSpawn.getInstance().doSpawnFireWall(_user, _targetX, _targetY);
	}

}
