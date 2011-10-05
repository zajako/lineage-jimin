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
import bone.server.server.model.L1CastleLocation;
import bone.server.server.model.L1HouseLocation;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.serverpackets.S_ServerMessage;
import bone.server.server.templates.L1BookMark;

public class C_AddBookmark extends ClientBasePacket {

	private static final String C_ADD_BOOKMARK = "[C] C_AddBookmark";

	public C_AddBookmark(byte[] decrypt, LineageClient client) {
		super(decrypt);
		String s = readS();

		L1PcInstance pc = client.getActiveChar();
		if (pc.isGhost()) {
			return;
		}

		if (pc.getMap().isMarkable() || pc.isGm()) {
			if ((L1CastleLocation.checkInAllWarArea(pc.getX(), pc.getY(), pc.getMapId())
					|| L1HouseLocation.isInHouse(pc.getX(), pc.getY(), pc.getMapId()))					
					|| ((pc.getX() >= 33514 && pc.getX() <= 33809) && (pc.getY() >= 32216 && pc.getY() <= 32457) && pc.getMapId() == 4)
					|| ((pc.getX() >= 34211 && pc.getX() <= 34287) && (pc.getY() >= 33103 && pc.getY() <= 33492) && pc.getMapId() == 4)
					&& !pc.isGm()) {
				pc.sendPackets(new S_ServerMessage(214)); // \f1여기를 기억할 수가 없습니다.
			} else {
				L1BookMark.addBookmark(pc, s);
			}
		} else {
			pc.sendPackets(new S_ServerMessage(214)); // \f1여기를 기억할 수가 없습니다.
		}
	}

	@Override
	public String getType() {
		return C_ADD_BOOKMARK;
	}
}
