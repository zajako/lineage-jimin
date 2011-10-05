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
 * Author: ChrisLiu.2007.06.30
 */
package bone.server.server.serverpackets;

import bone.server.server.Opcodes;

public class S_ShowSummonList extends ServerBasePacket {
	private static final String S_ShowSummonList = "[S] S_ShowSummonList";

	public S_ShowSummonList(int objid) {
		writeC(Opcodes.S_OPCODE_SHOWHTML);
		writeD(objid);
		writeS("summonlist");
	}

	@Override
	public byte[] getContent() {
		return _bao.toByteArray();
	}
	@Override
	public String getType() {
		return S_ShowSummonList;
	}
}
