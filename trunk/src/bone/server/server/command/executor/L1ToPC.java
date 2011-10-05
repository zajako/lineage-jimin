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

import java.util.logging.Logger;

import bone.server.server.model.L1Teleport;
import bone.server.server.model.L1World;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.serverpackets.S_SystemMessage;

public class L1ToPC implements L1CommandExecutor {
	@SuppressWarnings("unused")
	private static Logger _log = Logger.getLogger(L1ToPC.class.getName());

	private L1ToPC() {
	}

	public static L1CommandExecutor getInstance() {
		return new L1ToPC();
	}

	@Override
	public void execute(L1PcInstance pc, String cmdName, String arg) {
		try {
			L1PcInstance target = L1World.getInstance(). getPlayer(arg);

			if (target != null) {
				L1Teleport.teleport(pc, target.getX(), target.getY(), target
						. getMapId(), 5, false);
			} else {
				pc.sendPackets(new S_SystemMessage((new StringBuilder())
						. append(arg). append(" : �ش� ĳ���ʹ� �����ϴ�. "). toString()));
			}
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage(cmdName + " [ĳ���͸�]���� �Է��� �ּ���. "));
		}
	}
}
