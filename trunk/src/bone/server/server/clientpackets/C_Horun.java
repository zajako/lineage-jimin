
package bone.server.server.clientpackets;

import server.LineageClient;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.serverpackets.S_Horun;

public class C_Horun extends ClientBasePacket {

	private static final String C_HORUN = "[C] C_Horun";

	public C_Horun(byte abyte0[], LineageClient clientthread) throws Exception {
		super(abyte0);

		int i = readD();
		
		L1PcInstance pc = clientthread.getActiveChar();
		
		if (pc == null || pc.isGhost()) {	return; }
		
		pc.sendPackets(new S_Horun(i, pc));
	}

	@Override
	public String getType() {
		return C_HORUN;
	}

}
