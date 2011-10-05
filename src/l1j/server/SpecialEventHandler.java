package l1j.server;

import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.model.skill.L1SkillUse;
import l1j.server.server.serverpackets.S_CharVisualUpdate;
import l1j.server.server.serverpackets.S_OwnCharAttrDef;
import l1j.server.server.serverpackets.S_OwnCharStatus2;
import l1j.server.server.serverpackets.S_ReturnedStat;
import l1j.server.server.serverpackets.S_SPMR;
import l1j.server.server.serverpackets.S_SystemMessage;
import static l1j.server.server.model.skill.L1SkillId.*;

enum SpecialEvent { BugRace, AllBuf, InfinityFight, DoNotChatEveryone, DoChatEveryone};

//게임 내, 전체 이벤트에 대한 처리를 담당
public class SpecialEventHandler {

	private static volatile SpecialEventHandler uniqueInstance = null;

	private boolean CheckBugrace = false; 
	
	private SpecialEventHandler() {}

	public static SpecialEventHandler getInstance() {
		if(uniqueInstance == null) {
			synchronized (SpecialEventHandler.class) {
				if(uniqueInstance == null) {
					uniqueInstance = new SpecialEventHandler();
				}
			}
		}

		return uniqueInstance;
	}
	public void doBugRace() {
		if(!CheckBugrace)
			CheckBugrace = true;
		else return;
		//BugRaceController.getInstance().BugRaceRestart = true;
	}

	public void doAllBuf() {
		int[] allBuffSkill = { DECREASE_WEIGHT, PHYSICAL_ENCHANT_DEX,
				PHYSICAL_ENCHANT_STR, BLESS_WEAPON, ADVANCE_SPIRIT,				
				IRON_SKIN, ADVANCE_SPIRIT, NATURES_TOUCH, 
				CONCENTRATION, PATIENCE, INSIGHT};
		L1SkillUse l1skilluse = null;
		for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
			if(pc.isPrivateShop()){
				continue;
			}
			l1skilluse = new L1SkillUse();
			for (int i = 0; i < allBuffSkill.length ; i++) {
				l1skilluse.handleCommands(pc, allBuffSkill[i], pc.getId(), pc.getX(), pc.getY(), null, 0, L1SkillUse.TYPE_GMBUFF);
			}
			pc.sendPackets(new S_SystemMessage("운영자에게 버프를 받았습니다. "));
		}
	}

	public void doNotChatEveryone() {
		L1World.getInstance().set_worldChatElabled(false);
		L1World.getInstance().broadcastPacketToAll(new S_SystemMessage("운영자에 의해 월드채팅이 금지되었습니다."));
	}

	public void doChatEveryone() {
		L1World.getInstance().set_worldChatElabled(true);
		L1World.getInstance().broadcastPacketToAll(new S_SystemMessage("운영자에 의해 월드채팅이 실행되었습니다."));
	}

	public void ReturnStats(L1PcInstance pc) {
		
		pc.getAbility().initStat(pc.getClassId());
		L1SkillUse l1skilluse = new L1SkillUse();
		l1skilluse.handleCommands(pc, L1SkillId.CANCELLATION, pc.getId(), pc.getX(), pc.getY(),
				null, 0, L1SkillUse.TYPE_LOGIN);

		if(pc.getWeapon() != null) {
			pc.getInventory().setEquipped(pc.getWeapon(), false, false, false);
		}
		pc.sendPackets(new S_SPMR(pc));
		pc.sendPackets(new S_CharVisualUpdate(pc));
		pc.sendPackets(new S_OwnCharStatus2(pc));

		for (L1ItemInstance armor : pc.getInventory().getItems()) {
			for (int type = 0; type <= 12; type++) {
				if (armor != null) {
					pc.getInventory().setEquipped(armor, false, false, false);
				}
			}
		}
		pc.setReturnStat(pc.getExp());
		pc.sendPackets(new S_SPMR(pc));
		pc.sendPackets(new S_OwnCharAttrDef(pc));
		pc.sendPackets(new S_OwnCharStatus2(pc));
		pc.sendPackets(new S_ReturnedStat(pc, S_ReturnedStat.START));
		try {
			pc.save();
		} catch (Exception e) {}
	}

}
