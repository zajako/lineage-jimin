package l1j.server.server.model;

import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_SkillSound; //## [A142] MP ȸ���� ����Ʈ ���̵���

public class HpRegenerationByDoll extends TimerTask {
	private static Logger _log = Logger.getLogger(HpRegenerationByDoll.class
			.getName());

	private final L1PcInstance _pc;

	public HpRegenerationByDoll(L1PcInstance pc) {
		_pc = pc;
	}

	@Override
	public void run() {
		try {
			if (_pc.isDead()) {
				return;
			}
			regenHp();
		} catch (Throwable e) {
			_log.log(Level.WARNING, e.getLocalizedMessage(), e);
		}
	}

	public void regenHp() {

		int newHp = _pc.getCurrentHp() + 40;
		if (newHp < 0) {
			newHp = 0;
		}
		_pc.setCurrentHp(newHp);
		_pc.sendPackets(new S_SkillSound(_pc.getId(), 1608)); 
		Broadcaster.broadcastPacket(_pc, new S_SkillSound(_pc.getId(), 1608));
	}
}
