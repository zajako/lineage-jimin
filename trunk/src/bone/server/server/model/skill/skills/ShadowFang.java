package bone.server.server.model.skill.skills;

import bone.server.server.model.L1Character;
import bone.server.server.model.Instance.L1ItemInstance;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.model.skill.L1SkillId;
import bone.server.server.serverpackets.S_ServerMessage;

public class ShadowFang {

	public static void runSkill(L1Character cha, int _itemobjid, int buffDuration) {
		L1PcInstance pc = (L1PcInstance) cha;
		L1ItemInstance item = pc.getInventory().getItem(_itemobjid);
		if (item != null && item.getItem().getType2() == 1) {
			item.setSkillWeaponEnchant(pc, L1SkillId.SHADOW_FANG, buffDuration * 1000);
		} else {
			pc.sendPackets(new S_ServerMessage(79));
		}
	}

}
