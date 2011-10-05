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

package bone.server.server.clientpackets;

import server.LineageClient;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.serverpackets.S_ServerMessage;

// Referenced classes of package bone.server.server.clientpackets:
// ClientBasePacket

public class C_BanParty extends ClientBasePacket {

	private static final String C_BAN_PARTY = "[C] C_BanParty";

	public C_BanParty(byte decrypt[], LineageClient client) throws Exception {
		super(decrypt);
		String s = readS();

		L1PcInstance pc = client.getActiveChar();
		if (!pc.getParty().isLeader(pc)) {
			pc.sendPackets(new S_ServerMessage(427)); // 파티의 리더만을 추방할 수 있습니다.
			return;
		}

		for (L1PcInstance member : pc.getParty().getMembers()) {
			if (member.getName().toLowerCase().equals(s.toLowerCase())) {
				pc.getParty().leaveMember(member);
				return;
			}
		}
		// 발견되지 않았다
		pc.sendPackets(new S_ServerMessage(426, s)); // %0는 파티 멤버가 아닙니다.
	}

	@Override
	public String getType() {
		return C_BAN_PARTY;
	}

}
