package bone.server.server.model.Instance;

import java.util.ArrayList;

import bone.server.server.model.Broadcaster;
import bone.server.server.model.L1Attack;
import bone.server.server.serverpackets.S_ChangeHeading;
import bone.server.server.templates.L1Npc;
import bone.server.server.utils.CalcExp;

public class L1ScarecrowInstance extends L1NpcInstance {

	private static final long serialVersionUID = 1L;

	public L1ScarecrowInstance(L1Npc template) {
		super(template);
	}

	@Override
	public void onAction(L1PcInstance player) {
		L1Attack attack = new L1Attack(player, this);
		if (attack.calcHit()) {
			if (player.getLevel() < 5) {
				ArrayList<L1PcInstance> targetList = new ArrayList<L1PcInstance>();

				targetList.add(player);
				ArrayList<Integer> hateList = new ArrayList<Integer>();
				hateList.add(1);
				CalcExp.calcExp(player, getId(), targetList, hateList, getExp());
			}
			
			int heading = getMoveState().getHeading(); 
			if (heading < 7) 	heading++;
			else 				heading = 0;
			getMoveState().setHeading(heading);
			Broadcaster.broadcastPacket(this, new S_ChangeHeading(this));
		}
		attack.action();
	}

	@Override
	public void onTalkAction(L1PcInstance l1pcinstance) {}

	public void onFinalAction() {}

	public void doFinalAction() {}
}
