package bone.server.server.model;

import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.serverpackets.S_Party;

public class L1PartyRefresh extends TimerTask {
	private static Logger _log = Logger.getLogger(L1PartyRefresh.class.getName());

	private final L1PcInstance _pc;

	public L1PartyRefresh(L1PcInstance pc) {
		_pc = pc;
	}

	@Override
	public void run() {
		try {
			if (_pc.isDead() || _pc.getParty() == null) {
				_pc.stopRP();
				return;
			}
			rp();
		} catch (Throwable e) {
			_pc.stopRP();
			_log.log(Level.WARNING, e.getLocalizedMessage(), e);
		}
	}

	public void rp() {
		_pc.sendPackets(new S_Party(0x6e, _pc));
	}

}
