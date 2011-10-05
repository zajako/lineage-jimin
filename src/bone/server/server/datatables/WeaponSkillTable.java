package bone.server.server.datatables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import bone.server.L1DatabaseFactory;
import bone.server.server.model.WeaponSkill;
import bone.server.server.utils.SQLUtil;
import bone.server.server.utils.collections.Maps;

public class WeaponSkillTable {
	private static Logger _log = Logger.getLogger(WeaponSkillTable.class.getName());

	private static WeaponSkillTable _instance;

	private final Map<Integer, WeaponSkill> _weaponIdIndex = Maps.newMap();

	public static WeaponSkillTable getInstance() {
		if (_instance == null) {
			_instance = new WeaponSkillTable();
		}
		return _instance;
	}

	private WeaponSkillTable() {
		loadWeaponSkill();
	}

	private void loadWeaponSkill() {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {

			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM weapon_skill");
			rs = pstm.executeQuery();
			fillWeaponSkillTable(rs);
		}
		catch (SQLException e) {
			_log.log(Level.SEVERE, "error while creating weapon_skill table", e);
		}
		finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	private void fillWeaponSkillTable(ResultSet rs) throws SQLException {
		while (rs.next()) {
			int weaponId = rs.getInt("weapon_id");
			int probability = rs.getInt("probability");
			int fixDamage = rs.getInt("fix_damage");
			int randomDamage = rs.getInt("random_damage");
			int area = rs.getInt("area");
			int skillId = rs.getInt("skill_id");
			int skillTime = rs.getInt("skill_time");
			int effectId = rs.getInt("effect_id");
			int effectTarget = rs.getInt("effect_target");
			boolean isArrowType = rs.getBoolean("arrow_type");
			int attr = rs.getInt("attr");
			WeaponSkill weaponSkill = new WeaponSkill(weaponId, probability, fixDamage, randomDamage, area, skillId, skillTime, effectId,
					effectTarget, isArrowType, attr);
			_weaponIdIndex.put(weaponId, weaponSkill);
		}
		_log.config(_weaponIdIndex.size() + "개의 마법검 목록을 로드합니다!");
	}

	public WeaponSkill getTemplate(int weaponId) {
		return _weaponIdIndex.get(weaponId);
	}

}
