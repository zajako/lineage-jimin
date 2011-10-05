package bone.server.GameSystem.Antaras;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.model.map.L1WorldMap;
import bone.server.server.serverpackets.S_ServerMessage;
import bone.server.server.utils.L1SpawnUtil;

public class AntarasRaidSystem {
	private static Logger _log = Logger.getLogger(AntarasRaidSystem.class.getName());

	private static AntarasRaidSystem _instance;
	private final Map<Integer, AntarasRaid> _list = new ConcurrentHashMap<Integer, AntarasRaid>();
	private final ArrayList<Integer> _map = new ArrayList<Integer>();

	public static AntarasRaidSystem getInstance() {
		if (_instance == null) {
			_instance = new AntarasRaidSystem();
		}
		return _instance;
	}

	public AntarasRaidSystem(){
		for(int i = 1005; i < 1016; i++ ){
			_map.add(i);
		}
	}

	static class AntarasMsgTimer implements Runnable {
		private int _mapid = 0;
		private int _type = 0;
		public AntarasMsgTimer(int mapid, int type){
			_mapid = mapid;
			_type = type;
		}

		@Override
		public void run() {
			try {
				ArrayList<L1PcInstance> roomlist = AntarasRaidSystem.getInstance().getAR(_mapid).getRoomList(_type);
				for(int i =0; i < roomlist.size(); i++){
					roomlist.get(i).sendPackets(new S_ServerMessage(1588));
				}
				Thread.sleep(2000);
				for(int i =0; i < roomlist.size(); i++){
					roomlist.get(i).sendPackets(new S_ServerMessage(1589));
				}
				Thread.sleep(2000);
				for(int i =0; i < roomlist.size(); i++){
					roomlist.get(i).sendPackets(new S_ServerMessage(1590));
				}
				Thread.sleep(2000);
				for(int i =0; i < roomlist.size(); i++){
					roomlist.get(i).sendPackets(new S_ServerMessage(1591));
				}
				Thread.sleep(10000);
				AntarasRaidSpawn.getInstance().fillSpawnTable(_mapid, _type);
			} catch (Exception exception) {}
		}
	}
/*
	1588 난쟁이의 외침 : 곧 코마들이 이곳을 지키기 위해 몰려올 것입니다.
	1589 난쟁이의 외침 : 코마들은 광물을 먹고 사는 종족인데, 지능은 좀 떨어지지만 매우 강력한 힘을 지니고 있습니다.
	1590 난쟁이의 외침 : 지룡 안타라스의 안식을 지키기 위해 암흑용 할파스가 파견한 수하들 입니다.
	1591 난쟁이의 외침 : 아아..그들이 왔습니다. 모두 조심하시기 바랍니다.!!

	1592 난쟁이의 외침 : 문지기가 나타났습니다.! 그를 물리쳐야 이곳을 통과할 수 있습니다.

	1593 난쟁이의 외침 : 안타라스의 검은 숨결을 멈추게 한 용사들이 탄생 하였습니다.!!
*/

	public void startRaid(L1PcInstance pc){
		int id = blankMapId();
		if(id != 1005)
			L1WorldMap.getInstance().cloneMap(1005, id);
		AntarasRaid ar = new AntarasRaid(id);
		AntarasRaidSpawn.getInstance().fillSpawnTable(0, id);
		L1SpawnUtil.spawn2(pc.getX(), pc.getY(), pc.getMapId(), 4212015, 0, 7200*1000, id);
		_list.put(id, ar);
	}

	public AntarasRaid getAR(int id){
		return _list.get(id);
	}

	/**
	 * 빈 맵 아이디를 가져온다
	 * @return
	 */
	public int blankMapId(){
		int mapid = 0;
		if(_list.size() == 0)
			return 1005;
		mapid = _map.get(1);
		_map.remove(1);
		System.out.println("맵아이디"+mapid);
		return mapid;
	}

	public int countRaidPotal(){
		return _list.size();
	}

	/**
	 * 
	 */
}
