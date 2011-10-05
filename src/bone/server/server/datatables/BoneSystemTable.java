/*

 */
package bone.server.server.datatables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import bone.server.L1DatabaseFactory;
import bone.server.server.templates.L1BoneSystem;
import bone.server.server.utils.SQLUtil;

public class BoneSystemTable {

	private static Logger _log = Logger.getLogger(BoneSystemTable.class.getName());

	private static BoneSystemTable _instance;

	private final Map<Integer, L1BoneSystem> _bonesystem = new ConcurrentHashMap<Integer, L1BoneSystem>();

	public static BoneSystemTable getInstance() {
		if (_instance == null) {
			_instance = new BoneSystemTable();
		}
		return _instance;
	}

	private BoneSystemTable() {
		load();
	}

	private Calendar timestampToCalendar(Timestamp ts) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(ts.getTime());
		return cal;
	}

	private void load() {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM bonesystem");

			rs = pstm.executeQuery();
			L1BoneSystem bone = null;
			while (rs.next()) {
				bone = new L1BoneSystem(rs.getInt(1));
				bone.setBoneTime(timestampToCalendar((Timestamp) rs
						.getObject(3)));
				bone.setOpenLocation(rs.getInt(4));
				bone.setMoveLocation(rs.getInt(5));
				bone.setOpenContinuation(rs.getInt(6));
				
				_bonesystem.put(bone.getSystemTypeId(), bone);
			}
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	/**
	 * 시스템 값을 가져 온다 
	 * @param id 1: 균열
	 * @return
	 */
	public L1BoneSystem getSystem(int id) {
		return _bonesystem.get(id);
	}

	public void updateSystem(L1BoneSystem bone) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("UPDATE bonesystem SET time=?, openLoc=?, moveLoc=?, extend=? WHERE id=?");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String fm = sdf.format(bone.getBoneTime().getTime());
			pstm.setString(1, fm);
			pstm.setInt(2, bone.getOpenLocation());
			pstm.setInt(3, bone.getMoveLocation());
			pstm.setInt(4, bone.getOpenContinuation());
			pstm.setInt(5, bone.getSystemTypeId());
			pstm.execute();

		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

}
