package bone.server.GameSystem;

import java.util.Calendar;

import bone.server.server.GeneralThreadPool;
import bone.server.server.model.L1World;
import bone.server.server.model.gametime.BaseTime;
import bone.server.server.model.gametime.RealTimeClock;
import bone.server.server.model.gametime.TimeListener;
import bone.server.server.serverpackets.S_SystemMessage;

public class NoticeSystem implements TimeListener{
	private static NoticeSystem _instance;
	
	public static void start() {
		if (_instance == null) {
			_instance = new NoticeSystem();
		}
		_instance.some();
		RealTimeClock.getInstance().addListener(_instance);
	}

	private void some() {}

	private final int ubMsg = 1;
	
	static class NoticeTimer implements Runnable {
		private int _type = 0;
		private String _msg = null;
		public NoticeTimer(int type, String MSG) {
			_type = type;
			_msg = MSG;
		}

		@Override
		public void run() {
			try {
				switch (_type) {
				case 1:
					L1World.getInstance().set_worldChatElabled(false);
					L1World.getInstance().broadcastPacketToAll(new S_SystemMessage("안녕하세요. 리니지입니다."));
					Thread.sleep(1000);
					L1World.getInstance().broadcastPacketToAll(new S_SystemMessage("잠시후 "+_msg+"의 콜롯세움에서"));
					Thread.sleep(1000);
					L1World.getInstance().broadcastPacketToAll(new S_SystemMessage("무한대전이 진행되오니 많은 참여 바랍니다."));
					Thread.sleep(1000);
					L1World.getInstance().broadcastPacketToAll(new S_SystemMessage("감사합니다."));
					Thread.sleep(1000);
					L1World.getInstance().set_worldChatElabled(true);
					break;
				case 2:
					break;
					default : break;
				}
			} catch (Exception exception) {}
		}
	}


	@Override
	public void onDayChanged(BaseTime time) {}
	@Override
	public void onHourChanged(BaseTime time) {}
	
	@Override	
	public void onMinuteChanged(BaseTime time) {
		int rm = time.get(Calendar.MINUTE);
		int rh = time.get(Calendar.HOUR_OF_DAY);
		
		if (rm == 43) ubStartMSG(rh);
	}

	@Override
	public void onMonthChanged(BaseTime time) {}
		
	private void ubStartMSG(int hour) {
		String MSG = null;

		switch (hour) {
		case 0: MSG = "말하는 섬, $2931, $1242"; break;	
		case 2:
		case 8:
		case 14:
		case 20: MSG = "$1242"; break;
		case 3:
		case 6:
		case 9:
		case 12:
		case 15:
		case 18:
		case 21: MSG = "말하는 섬, $2931"; break;	
		case 1:
		case 4:
		case 7:
		case 10:
		case 13:
		case 16:
		case 19: MSG = "은기사, 글루딘"; break;
		case 22: MSG = "은기사, 글루딘, $1242"; break;
		default : return;
		}
		NoticeTimer nt = new NoticeTimer(ubMsg, MSG);
		GeneralThreadPool.getInstance().execute(nt);
	}

}
