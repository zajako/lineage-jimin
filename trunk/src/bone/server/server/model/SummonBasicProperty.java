package bone.server.server.model;

import bone.server.server.model.Instance.L1NpcInstance;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.model.Instance.L1SummonInstance;
import bone.server.server.serverpackets.S_HPMeter;

public class SummonBasicProperty extends NpcBasicProperty {
	public SummonBasicProperty(L1NpcInstance character) {
		super(character);
	}
	
	public void setCurrentHp(int i) {
		super.setCurrentHp(i);
		
		if (((L1SummonInstance)character).getMaster() instanceof L1PcInstance) {
			int HpRatio = 100 * getCurrentHp() / getMaxHp();
			L1PcInstance Master = (L1PcInstance) ((L1SummonInstance)character).getMaster();
			Master.sendPackets(new S_HPMeter(character.getId(), HpRatio));
		}
	}
}
