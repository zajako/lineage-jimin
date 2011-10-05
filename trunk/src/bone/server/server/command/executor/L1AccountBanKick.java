/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.   See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 *
 * http://www.gnu.org/copyleft/gpl.html
 */
package bone.server.server.command.executor;

//import java.util.logging.Logger;

import bone.server.server.Account;
import bone.server.server.model.L1World;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.serverpackets.S_Disconnect;
import bone.server.server.serverpackets.S_SystemMessage;

public class L1AccountBanKick implements L1CommandExecutor {
	//private static Logger _log = Logger.getLogger(L1AccountBanKick.class.getName());

	private L1AccountBanKick() {
	}

	public static L1CommandExecutor getInstance() {
		return new L1AccountBanKick();
	}

	@Override
	public void execute(L1PcInstance pc, String cmdName, String arg) {
		try {
			L1PcInstance target = L1World.getInstance(). getPlayer(arg);

			if (target != null) {
				// ��ī��Ʈ�� BAN �Ѵ�
				Account.ban(target.getAccountName());
				pc.sendPackets(new S_SystemMessage(target.getName() + " �� �����з� �Ͽ����ϴ�."));
				target.sendPackets(new S_Disconnect());
			} else {
				pc.sendPackets(new S_SystemMessage("�׷��� �̸��� ĳ���ʹ� ���峻���� �������� �ʽ��ϴ�. "));
			}
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage(cmdName + " [ĳ���͸�] ���� �Է��� �ּ���. "));
		}
	}
}