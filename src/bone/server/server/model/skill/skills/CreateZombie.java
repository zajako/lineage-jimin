package bone.server.server.model.skill.skills;

import bone.server.server.model.L1Character;
import bone.server.server.model.Instance.L1NpcInstance;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.model.Instance.L1SummonInstance;
import bone.server.server.serverpackets.S_ServerMessage;

public class CreateZombie {
	public static void runSkill(L1PcInstance _user, L1PcInstance _player, L1Character _target, L1NpcInstance _targetNpc) {
		int petcost = 0;
		Object[] petlist = _user.getPetList().values().toArray();
		for (Object pet : petlist) {
			petcost += ((L1NpcInstance) pet).getPetcost();
		}
		int charisma = _user.getAbility().getTotalCha();
		if (_player.isElf()) { 
			charisma += 12;
		} else if (_player.isWizard()) { 
			charisma += 6;
		}
		charisma -= petcost;
		if (charisma >= 6) { 
			L1SummonInstance summon = new L1SummonInstance(_targetNpc, _user, true);
			_target = summon; 
		} else {
			_player.sendPackets(new S_ServerMessage(319)); 
		}
	}
}
