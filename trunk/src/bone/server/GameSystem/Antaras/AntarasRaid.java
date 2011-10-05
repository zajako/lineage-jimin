/* Eva Pack -http://eva.gg.gg
 * ���� ������� ��Ÿ�� ���̵� �ý���
 * Eva ShaSha
 */

package bone.server.GameSystem.Antaras;

import java.sql.Timestamp;
import java.util.ArrayList;

import bone.server.GameSystem.Antaras.AntarasRaidSystem.AntarasMsgTimer;
import bone.server.server.GeneralThreadPool;
import bone.server.server.model.Instance.L1PcInstance;



public class AntarasRaid {

	private final ArrayList<L1PcInstance> _roomlist1 = new ArrayList<L1PcInstance>();
	private final ArrayList<L1PcInstance> _roomlist2 = new ArrayList<L1PcInstance>();
	private final ArrayList<L1PcInstance> _roomlist3 = new ArrayList<L1PcInstance>();
	private final ArrayList<L1PcInstance> _roomlist4 = new ArrayList<L1PcInstance>();
	private final ArrayList<L1PcInstance> _antalist = new ArrayList<L1PcInstance>();
	
	private int _step = 0;
	private int _id;
	private Timestamp _endtime;
	private boolean _isAntaras = false;
	public AntarasRaid(int id){
		_id = id;
		_endtime = new Timestamp(System.currentTimeMillis()+7200000);// 2�ð� ��
	}

	public void timeOverRun(int type) {
		switch(type){
		case 0:// 4�� �� 20�� Ÿ�� �����
			tel4roomOut();
			break;
		case 1:// 1��° �� 2�� ���� �� ��ȯ
			_step = 1;
			AntarasMsgTimer room1 = new AntarasMsgTimer(_id, 1);
			GeneralThreadPool.getInstance().execute(room1);
			break;
		case 2:// 2��° �� 2�� ���� �� ��ȯ
			_step = 2;
			AntarasMsgTimer room2 = new AntarasMsgTimer(_id, 2);
			GeneralThreadPool.getInstance().execute(room2);
			break;
		case 3:// 3��° �� 2�� ���� �� ��ȯ
			_step = 3;
			AntarasMsgTimer room3 = new AntarasMsgTimer(_id, 3);
			GeneralThreadPool.getInstance().execute(room3);
			break;
		case 4:// 4��° �� 2�� ���� �� ��ȯ
			_step = 4;
			AntarasMsgTimer room4 = new AntarasMsgTimer(_id, 4);
			GeneralThreadPool.getInstance().execute(room4);
			break;
		case 5:// ��Ÿ�� 
			_isAntaras = true;
			//�� ��ȯ 
			break;
		case 6:// ��Ÿ�濡 �ִ� ��� ��� �ڽ�Ų��
			break;
		}
	}

	private void tel4roomOut() {
		for(int i = 0; i > _roomlist1.size(); i++){
			L1PcInstance pc = _roomlist1.get(i);
			// ��Ÿ�� �ƴѰ� ��ǥ ��Ƽ� �������� ��Ű�� 
		}
	}

	/** ���� ������ �� �� �����´� */
	public int getRoomNum(){
		int room = 5;
		if(_roomlist1.size() <= 0 && _step < 1){
			room = 0;
		} else if(_roomlist1.size() <= 8 && _step < 1){
			room = 1;
		} else if(_roomlist2.size() <= 8 && _step < 2){
			room = 2;
		} else if(_roomlist3.size() <= 8 && _step < 3){
			room = 3;
		} else if(_roomlist4.size() <= 8 && _step < 4){
			room = 4;
		}
		return room;
	}



	/** ���̵� ���� �ܰ踦 �����Ѵ� */
	public void setStep(int step){
		_step = step;
	}

	/** �����ȿ� �ִ� ���� ����Ʈ�� ���Ѵ� */
	public void addUser4room(L1PcInstance pc, int room){
		switch(room){
		case 0:
		case 1:
			_roomlist1.add(pc);
			break;
		case 2:
			_roomlist2.add(pc);
			break;
		case 3:
			_roomlist3.add(pc);
			break;
		case 4:
			_roomlist4.add(pc);
			break;
		default: break;
		}
	}
	
	/** �ش� ����Ʈ�� �Ѱ��ش� */
	public ArrayList<L1PcInstance> getRoomList(int num){
		switch(num){
		case 1:
			return _roomlist1;
		case 2:
			return _roomlist2;
		case 3:
			return _roomlist2;
		case 4:
			return _roomlist2;
			default: return null;
		}
	}
	
	/** ��Ÿ�󽺷�� ������ ���� ���� �����´� */
	public int countLairUser(){
		return _antalist.size();
	}
	
	/** ��Ÿ�󽺷�� ������ ������ �ִ´� */
	public void addLairUser(L1PcInstance pc){
		_antalist.add(pc);
	}
	
	/** ��Ÿ�󽺰� ������ �˷��ش� */
	public boolean isAntaras(){
		return _isAntaras;
	}
	
	public int getAntaId(){ return _id; }
	public Timestamp getEndTime(){ return _endtime; }
}
