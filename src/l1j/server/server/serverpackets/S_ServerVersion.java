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
package l1j.server.server.serverpackets;

import l1j.server.server.Opcodes;
import l1j.server.server.model.gametime.GameTimeClock;

public class S_ServerVersion extends ServerBasePacket {
	private static final String S_SERVER_VERSION = "[S] ServerVersion";

	public S_ServerVersion() {
		int time = GameTimeClock.getInstance().getGameTime().getSeconds();
		time = time - (time % 300);
		writeC(Opcodes.S_OPCODE_SERVERVERSION);
		/*
		writeC(Opcodes.S_OPCODE_SERVERVERSION);
		// Auth Check client Version
		// 1 = Check
		// 0 = no check
		// > 1 no check
		// type : boolean
		writeC(0x00);

		// your server id, first id = 2
		// id = 0, ????
		// id = 1, ????
		writeC(0x02);

		// all version
		// If the user level is a administrator,
		// inputs /ver to be able to print out all version in game
		// If the user level isn't a administrator
		// inputs /ver to be able to print out client version in game
		writeD(0x00009D7C); // server verion
		writeD(0x0000791A); // cache verion
		writeD(0x0000791A); // auth verion
		writeD(0x00009DD1); // npc verion

		writeD(0x882a2cc6);

		writeC(0x00); // unknown
		writeC(0x00); // unknown

		// Country
		// 0.US 3.Taiwan 4.Janpan 5.China
		writeC(Config.CLIENT_LANGUAGE);
		*/

//		0000 : 16 00 18 1e c7 a8 00 a7 c6 a8 00 36 11 de 77 4d    ...........6..wM
//		0010 : 79 a8 00 c2 f5 f7 4d 00 00 00 03 00 00 00 33 16    y.....M.......3.

		writeC(0x00); // must be
		writeC(0xc8); // low version
		writeD(0x00a8a319); // server verion 19 a3 a8 00
		writeD(0x00a8a067); // cache verion 67 a0 a8 00
		writeD(0x77de1136); // auth verion 36 11 de 77
		writeD(0x00a8794d); // npc verion 4d 79 a8 00
		writeD(time); // 로그인시의 시간 설정 b2 c7 d4 4d
		writeC(0x00); // unk 1
		writeC(0x00); // unk 2
		writeC(0x00); // 0:영어 8:일본어 //writeC(0x00);
		//writeH(0xab2e);
		//writeH(0x8806);
		//writeH(0x0002);
	}

	@Override
	public byte[] getContent() {
		return getBytes();
	}
	@Override
	public String getType() {
		return S_SERVER_VERSION;
	}
}
