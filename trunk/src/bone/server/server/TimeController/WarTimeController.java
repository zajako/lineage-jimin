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

import java.util.Calendar;

import bone.server.Config;
import bone.server.server.datatables.CastleTable;
//import bone.server.server.datatables.CharSoldierTable;
import bone.server.server.datatables.CharacterTable;
import bone.server.server.datatables.DoorSpawnTable;
import bone.server.server.model.L1CastleLocation;
import bone.server.server.model.L1Clan;
import bone.server.server.model.L1Object;
import bone.server.server.model.L1Teleport;
import bone.server.server.model.L1WarSpawn;
import bone.server.server.model.L1World;
import bone.server.server.model.Instance.L1CrownInstance;
import bone.server.server.model.Instance.L1DoorInstance;
import bone.server.server.model.Instance.L1FieldObjectInstance;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.model.Instance.L1TowerInstance;
import bone.server.server.model.gametime.RealTimeClock;
import bone.server.server.serverpackets.S_PacketBox;
import bone.server.server.templates.L1Castle;

public class WarTimeController extends Thread {
	private static WarTimeController _instance;
	private L1Castle[] _l1castle = new L1Castle[8];
	private Calendar[] _war_start_time = new Calendar[8];
	private Calendar[] _war_end_time = new Calendar[8];
	private boolean[] _is_now_war = new boolean[8];

	private WarTimeController() {
		for (int i = 0; i < _l1castle.length; i++) {
			_l1castle[i] = CastleTable.getInstance().getCastleTable(i + 1);
			_war_start_time[i] = _l1castle[i].getWarTime();
			_war_end_time[i] = (Calendar) _l1castle[i].getWarTime().clone();
			_war_end_time[i].add(Config.ALT_WAR_TIME_UNIT, Config.ALT_WAR_TIME);
		}
	}

	public static WarTimeController getInstance() {
		if (_instance == null) {
			_instance = new WarTimeController();
			_instance.start();
		}
		return _instance;
	}

	public void run() {
		System.out.println(WarTimeController.class.getName()  + " ����");
		try {
			while (true) {
				checkWarTime(); // ���� �ð��� üũ
				Thread.sleep(1000);
			}
		} catch (Exception e1) {
		}
	}

/*	public Calendar getRealTime() {
		TimeZone _tz = TimeZone.getTimeZone(Config.TIME_ZONE);
		Calendar cal = Calendar.getInstance(_tz);
		return cal;
	}*/
	

	public boolean isNowWar(int castle_id) {
		return _is_now_war[castle_id - 1];
	}

	public void checkCastleWar(L1PcInstance player) {
		for (int i = 0; i < 8; i++) {
			if (_is_now_war[i]) {
				player.sendPackets(new S_PacketBox(S_PacketBox.MSG_WAR_GOING, i + 1)); // %s�� �������� �������Դϴ�.
			}
		}
	}

	private void checkWarTime() {
		L1WarSpawn warspawn = null;
		Calendar Rtime = RealTimeClock.getInstance().getRealTimeCalendar();
/*		System.out.print(Rtime.get(Calendar.DAY_OF_MONTH));
		System.out.print("��"+Rtime.get(Calendar.HOUR_OF_DAY));
		System.out.print("��"+Rtime.get(Calendar.MINUTE));
		System.out.println("��"+Rtime.get(Calendar.SECOND)+"��");*/
		for (int i = 0; i < 8; i++) {
			if (_war_start_time[i].before(Rtime) // ���� ����
					&& _war_end_time[i].after(Rtime)) {
				if (_is_now_war[i] == false) {
					_is_now_war[i] = true;
					// �⸦ spawn �Ѵ�
					warspawn = new L1WarSpawn();
					warspawn.SpawnFlag(i + 1);
					// ������ ������ �ݴ´�
					for (L1DoorInstance door : DoorSpawnTable.getInstance().getDoorList()) {
						if (L1CastleLocation.checkInWarArea(i + 1, door)) {
							door.setAutoStatus(0);// �ڵ������� ����
							door.repairGate();
						}
					}
					if (_l1castle[i].getCastleSecurity() == 1)
						securityStart(_l1castle[i]);// ġ�Ȱ���
					L1World.getInstance().broadcastPacketToAll(new S_PacketBox(S_PacketBox.MSG_WAR_BEGIN, i + 1)); // %s�� �������� ���۵Ǿ����ϴ�.
					int[] loc = new int[3];
					L1Clan clan = null;
					for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
						int castleId = i + 1;
						if (L1CastleLocation.checkInWarArea(castleId, pc) && !pc.isGm()) {
							clan = L1World.getInstance().getClan(pc.getClanname());
							if (clan != null) {
								if (clan.getCastleId() == castleId) {
									continue;
								}
							}
							loc = L1CastleLocation.getGetBackLoc(castleId);
							L1Teleport.teleport(pc, loc[0], loc[1], (short) loc[2], 5, true);
						}
					}
				}
			} else if (_war_end_time[i].before(Rtime)) { // ���� ����
				if (_is_now_war[i] == true) {					
					_is_now_war[i] = false;
					L1World.getInstance().broadcastPacketToAll(
							new S_PacketBox(S_PacketBox.MSG_WAR_END, i + 1)); // %s�� �������� �����߽��ϴ�.
					_war_start_time[i].add(Config.ALT_WAR_INTERVAL_UNIT, Config.ALT_WAR_INTERVAL);
					_war_end_time[i].add(Config.ALT_WAR_INTERVAL_UNIT, Config.ALT_WAR_INTERVAL);
					_l1castle[i].setTaxRate(10); // ����10����
//					_l1castle[i].setPublicMoney(0); // ����Ŭ����
					CastleTable.getInstance().updateCastle(_l1castle[i]);
					int castle_id = i + 1;
//					CharSoldierTable.getInstance().delCastleSoldier(castle_id);// �뺴 Ŭ���� 
					L1FieldObjectInstance flag = null;
					L1CrownInstance crown = null;
					L1TowerInstance tower = null;
					for (L1Object l1object : L1World.getInstance().getObject()) {
						// ���� ������� �⸦ �����
						if (l1object instanceof L1FieldObjectInstance) {
							flag = (L1FieldObjectInstance) l1object;
							if (L1CastleLocation.checkInWarArea(castle_id, flag)) {
								flag.deleteMe();
							}
						}
						// ũ����� �ִ� ����, ũ����� ���� Ÿ���� spawn �Ѵ�
						if (l1object instanceof L1CrownInstance) {
							crown = (L1CrownInstance) l1object;
							if (L1CastleLocation.checkInWarArea(castle_id, crown)) {
								crown.deleteMe();
							}
						}
						if (l1object instanceof L1TowerInstance) {
							tower = (L1TowerInstance) l1object;
							if (L1CastleLocation.checkInWarArea(castle_id, tower)) {
								tower.deleteMe();
							}
						}
					}

					warspawn = new L1WarSpawn();
					warspawn.SpawnTower(castle_id);
					for (L1DoorInstance door : DoorSpawnTable.getInstance().getDoorList()) {
						if (L1CastleLocation.checkInWarArea(castle_id, door)) {
							door.repairGate();
						}
					}					
				}
			}
		}
	}
	
	private void securityStart(L1Castle castle) {
		int castleId = castle.getId();
		int a = 0, b = 0, c = 0, d = 0, e = 0;
		int[] loc = new int[3];
		L1Clan clan = null;

		switch (castleId) {
		case 1: 
		case 2: 
		case 3: 
		case 4: a=52; b=248; c=249; d=250; e=251; break;
		case 5: 
		case 6: 
		case 7: 
			default : break;
		}

		for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
			if ((pc.getMapId() == a || pc.getMapId() == b
					|| pc.getMapId() == c || pc.getMapId() == d
					|| pc.getMapId() == e) && !pc.isGm()) {
				clan = L1World.getInstance().getClan(pc.getClanname());
				if (clan != null) {
					if (clan.getCastleId() == castleId) {
						continue;
					}
				}
				loc = L1CastleLocation.getGetBackLoc(castleId);
				L1Teleport.teleport(pc, loc[0], loc[1], (short) loc[2], 5, true);
			}
		}
		castle.setCastleSecurity(0);
		CastleTable.getInstance().updateCastle(castle);
		CharacterTable.getInstance().updateLoc(castleId, a, b, c, d, e);
	}

}
