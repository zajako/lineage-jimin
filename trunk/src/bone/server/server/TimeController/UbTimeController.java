/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 *
 * http://www.gnu.org/copyleft/gpl.html
 */
package bone.server.server.TimeController;

import java.util.logging.Logger;

import bone.server.server.datatables.UBTable;
import bone.server.server.model.L1UltimateBattle;

public class UbTimeController extends Thread {
	private static Logger _log = Logger.getLogger(UbTimeController.class.getName());

	private static UbTimeController _instance;

	public static UbTimeController getInstance() {
		if (_instance == null) {
			_instance = new UbTimeController();
			_instance.start();
		}
		return _instance;
	}

	public void run() {
		System.out.println(UbTimeController.class.getName()  + " 시작");
		try {
			while (true) {
				checkUbTime(); // UB개시 시간을 체크
				Thread.sleep(15000);
			}
		} catch (Exception e1) {
			_log.warning(e1.getMessage());
		}
	}

	private void checkUbTime() {
		for (L1UltimateBattle ub : UBTable.getInstance().getAllUb()) {
			if (ub.checkUbTime() && !ub.isActive()) {
				ub.start();
			}
		}
	}
}
