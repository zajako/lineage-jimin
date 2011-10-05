package bone.server.server.model;

import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.model.item.L1ItemId;
import bone.server.server.serverpackets.S_ServerMessage;

public class AHRegeneration extends TimerTask {
	private static Logger _log = Logger.getLogger(AHRegeneration.class
			.getName());

	private final L1PcInstance _pc;

	public AHRegeneration(L1PcInstance pc) {
		_pc = pc;
	}

	@Override
	public void run() {
		try {
			if (_pc.isDead()) {
				return;
			}
			regenItem();
		} catch (Throwable e) {
			_log.log(Level.WARNING, e.getLocalizedMessage(), e);
		}
	}

	public void regenItem() {		
		_pc.getInventory().storeItem(L1ItemId.MIRACLE_FRAGMENT,1);
		_pc.sendPackets(new S_ServerMessage(403, "$6383"));
	}	
}
