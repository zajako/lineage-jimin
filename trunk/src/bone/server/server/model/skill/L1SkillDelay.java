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
package bone.server.server.model.skill;

import bone.server.server.GeneralThreadPool;
import bone.server.server.model.L1Character;

// Referenced classes of package bone.server.server.model:
// L1SkillDelay

public class L1SkillDelay {


	private L1SkillDelay() {
	}

	static class SkillDelayTimer implements Runnable {
		private L1Character _cha;

		public SkillDelayTimer(L1Character cha, int time) {
			_cha = cha;
		}

		@Override
		public void run() {
			stopDelayTimer();
		}

		public void stopDelayTimer() {
			_cha.setSkillDelay(false);
		}
	}

	public static void onSkillUse(L1Character cha, int time) {
		cha.setSkillDelay(true);
		GeneralThreadPool.getInstance().schedule(new SkillDelayTimer(cha, time), time);
	}

}
