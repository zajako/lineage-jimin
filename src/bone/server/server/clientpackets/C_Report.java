/*
 * make by Eva Team (http://eva.gg.gg)
 *	/½Å°í 
 * 
 */

package bone.server.server.clientpackets;

import java.util.logging.Logger;

import server.LineageClient;
import bone.server.server.model.Instance.L1PcInstance;

// Referenced classes of package bone.server.server.clientpackets:
// ClientBasePacket

public class C_Report extends ClientBasePacket {

	private static final String C_REPORT = "[C] C_Report";
	@SuppressWarnings("unused")
	private static Logger _log = Logger.getLogger(C_Report.class.getName());

	public C_Report(byte abyte0[], LineageClient clientthread) throws Exception {
		super(abyte0);

		int type = readC();
		int objid = readD();
		
		L1PcInstance pc = clientthread.getActiveChar();
		
	}

	@Override
	public String getType() {
		return C_REPORT;
	}
}
