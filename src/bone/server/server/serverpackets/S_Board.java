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

package bone.server.server.serverpackets;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;

import bone.server.L1DatabaseFactory;
import bone.server.server.Opcodes;
import bone.server.server.datatables.BoardTable;
import bone.server.server.model.Instance.L1NpcInstance;
import bone.server.server.utils.SQLUtil;

public class S_Board extends ServerBasePacket {

	private static final String S_BOARD = "[S] S_Board";

	private static Logger _log = Logger.getLogger(S_Board.class.getName());

	private byte[] _byte = null;

	public S_Board(L1NpcInstance board) {
		if(board.getNpcId()==4212014)
			buildPacket2(board, 0);
		else
			buildPacket(board, 0);
	}

	public S_Board(L1NpcInstance board, int number) {
		buildPacket(board, number);
	}


	private void buildPacket(L1NpcInstance board, int number) {
		int count = 0;
		String[][] db = null;
		int[] id = null;
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			db = new String[8][3];
			id = new int[8];
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM board order by id desc");
			rs = pstm.executeQuery();
			while (rs.next() && count < 8) {
				if(board.getNpcId() != rs.getInt(6)){
					continue;
				}
				if (rs.getInt("id") <= number || number == 0) {
					id[count] = rs.getInt(1);
					db[count][0] = rs.getString(2);// 이름
					db[count][1] = rs.getString(3);// 날짜
					db[count][2] = rs.getString(4);// 제목
					count++;
				}
			}
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}

		writeC(Opcodes.S_OPCODE_BOARD);
		writeC(0);// type
		writeD(board.getId());
		writeC(0xFF); // ?
		writeC(0xFF); // ?
		writeC(0xFF); // ?
		writeC(0x7F); // ?
		writeH(count);
		writeH(300);
		for (int i = 0; i < count; ++i) {
			writeD(id[i]);
			writeS(db[i][0]);// 이름
			writeS(db[i][1]);// 날짜
			writeS(db[i][2]);// 제목
		}
	}

	private void buildPacket2(L1NpcInstance board, int number) {// 드래곤키 알림 게시판
		int count = 0;
		long a = 0;
		String[][] db = null;
		int[] id = null;
		int[] time = null;
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			db = new String[8][2];
			id = new int[8];
			time = new int[8];
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM board order by id desc");
			rs = pstm.executeQuery();
			while (rs.next() && count < 8){
				if(board.getNpcId() != rs.getInt(6)){
					continue;
				}
				a = rs.getTimestamp(7).getTime() - System.currentTimeMillis();
				if(a < 0){
					BoardTable.getInstance().delDayExpire(rs.getInt(8));
					continue;
				}
				if (rs.getInt("id") <= number || number == 0) {
					id[count] = rs.getInt(1);
					db[count][0] = rs.getString(2);// 이름
					db[count][1] = rs.getString(3);// 날짜
					time[count] = (int) a/60000*60;
					count++;
				}
			}
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}

		writeC(Opcodes.S_OPCODE_BOARD);
		writeC(1);// type
		writeD(board.getId());
		writeC(0xFF); // ?
		writeC(0xFF); // ?
		writeC(0xFF); // ?
		writeC(0x7F); // ?
		writeC(count);
		for (int i = 0; i < count; ++i) {
			writeD(id[i]);
			writeS(db[i][0]);// 이름
			writeS(db[i][1]);// 날짜
			writeD(time[i]);
		}
	}
	@Override
	public byte[] getContent() {
		if (_byte == null) {
			_byte = getBytes();
		}
		return _byte;
	}
	
	@Override
	public String getType() {
		return S_BOARD;
	}
}
