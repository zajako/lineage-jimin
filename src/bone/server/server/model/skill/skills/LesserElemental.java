package bone.server.server.model.skill.skills;

import java.util.Random;

import bone.server.server.datatables.NpcTable;
import bone.server.server.model.L1Character;
import bone.server.server.model.Instance.L1NpcInstance;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.model.Instance.L1SummonInstance;
import bone.server.server.serverpackets.S_ServerMessage;
import bone.server.server.templates.L1Npc;

public class LesserElemental {

	public static void runSkill(L1Character cha) {

		L1PcInstance pc = (L1PcInstance) cha;
		int attr = pc.getElfAttr();
		if (attr != 0) { 
			if (pc.getMap().isRecallPets() || pc.isGm()) {
				int petcost = 0;
				Object[] petlist = pc.getPetList().values().toArray();
				for (Object pet : petlist) {
					petcost += ((L1NpcInstance) pet).getPetcost();
				}

				if (petcost == 0) { 
					int summonid = 0;
					int summons[];
					summons = new int[] { 45306, 45303, 45304, 45305 };
					int npcattr = 1;
					for (int i = 0; i < summons.length; i++) {
						if (npcattr == attr) {
							summonid = summons[i];
							i = summons.length;
						}
						npcattr *= 2;
					}
					if (summonid == 0) {
						Random random = new Random();
						int k3 = random.nextInt(4);
						summonid = summons[k3];
					}

					L1Npc npcTemp = NpcTable.getInstance().getTemplate(summonid);
					L1SummonInstance summon = new L1SummonInstance(npcTemp, pc);
					summon.setPetcost(pc.getAbility().getTotalCha() + 7); 
				}
			} else {
				pc.sendPackets(new S_ServerMessage(79));
			}
		}
	}
}
