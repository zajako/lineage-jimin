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
package l1j.server.server.TimeController;

import java.util.ArrayList;
import java.util.List;

import l1j.server.server.model.Instance.L1PcInstance;

public class LiveTimeController extends Thread {
	private static LiveTimeController _instance;
	private final List<L1PcInstance> _LiveList =new ArrayList<L1PcInstance>();

	public static LiveTimeController getInstance() {
		if (_instance == null) {
			_instance = new LiveTimeController();
			_instance.start();
		}
		return _instance;
	}

	public void run() {
		System.out.println(LiveTimeController.class.getName()  + " 시작");
		try {
			while (true) {
				Thread.sleep(1000); // 1초
				Live();// 1초마다 실행
			}
		} catch (Exception e1) {
		}
	}

	public void addMember(L1PcInstance pc) {
		if (pc == null || pc.get_food() < 225 || _LiveList.contains(pc)) {
			return;
		}
		_LiveList.add(pc);
	}

	public void removeMember(L1PcInstance pc) {
		if (pc == null || !_LiveList.contains(pc)) {
			return;
		}
		_LiveList.remove(pc);
	}
	private void Live() {

		for (L1PcInstance pc : _LiveList){
			if (pc == null || pc.get_food() != 225 || pc.getLive() >= 1800){
				removeMember(pc);
				return;
			}
			pc.addLive(1);
		}

	}

}