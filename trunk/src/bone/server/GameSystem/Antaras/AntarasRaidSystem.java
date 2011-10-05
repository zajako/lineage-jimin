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
	1588 �������� ��ħ : �� �ڸ����� �̰��� ��Ű�� ���� ������ ���Դϴ�.
	1589 �������� ��ħ : �ڸ����� ������ �԰� ��� �����ε�, ������ �� ���������� �ſ� ������ ���� ���ϰ� �ֽ��ϴ�.
	1590 �������� ��ħ : ���� ��Ÿ���� �Ƚ��� ��Ű�� ���� ����� ���Ľ��� �İ��� ���ϵ� �Դϴ�.
	1591 �������� ��ħ : �ƾ�..�׵��� �Խ��ϴ�. ��� �����Ͻñ� �ٶ��ϴ�.!!

	1592 �������� ��ħ : �����Ⱑ ��Ÿ�����ϴ�.! �׸� �����ľ� �̰��� ����� �� �ֽ��ϴ�.

	1593 �������� ��ħ : ��Ÿ���� ���� ������ ���߰� �� ������ ź�� �Ͽ����ϴ�.!!
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
	 * �� �� ���̵� �����´�
	 * @return
	 */
	public int blankMapId(){
		int mapid = 0;
		if(_list.size() == 0)
			return 1005;
		mapid = _map.get(1);
		_map.remove(1);
		System.out.println("�ʾ��̵�"+mapid);
		return mapid;
	}

	public int countRaidPotal(){
		return _list.size();
	}

	/**
	 * 
	 */
}
