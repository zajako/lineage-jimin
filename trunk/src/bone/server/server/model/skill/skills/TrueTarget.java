package bone.server.server.model.skill.skills;

import bone.server.server.model.L1Character;
import bone.server.server.model.L1World;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.serverpackets.S_TrueTarget;

public class TrueTarget {

	public static void runSkill(L1Character _user, String _message, L1Character _target, int _targetID) {
		if (_user instanceof L1PcInstance) {
			L1PcInstance pri = (L1PcInstance) _user;
			pri.sendPackets(new S_TrueTarget(_targetID, pri.getId(), _message));
			for (L1PcInstance pc : L1World.getInstance().getRecognizePlayer(_target)) {
				if(pri.getClanid() == pc.getClanid()){
					pc.sendPackets(new S_TrueTarget(_targetID, pc .getId(), _message));
				}
			}
		}
	}
}
