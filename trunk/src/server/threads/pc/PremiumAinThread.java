package server.threads.pc;

import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.Config;
import l1j.server.server.model.CharPosUtil;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.gametime.RealTime;
import l1j.server.server.model.gametime.RealTimeClock;
import l1j.server.server.model.item.L1ItemId;
import l1j.server.server.serverpackets.S_ServerMessage;

public class PremiumAinThread extends Thread{

	private static PremiumAinThread _instance;
	private static Logger _log = Logger.getLogger(PremiumAinThread.class.getName());

	public static PremiumAinThread getInstance(){
		if (_instance == null){
			_instance = new PremiumAinThread();
			_instance.start();
		}
		return _instance;
	}
	public PremiumAinThread(){

	}


	public void run(){
		System.out.println(PremiumAinThread.class.getName()  + " 시작");
		while(true){
			try {
				for(L1PcInstance _client : L1World.getInstance().getAllPlayers()){
					if(_client == null || _client.getNetConnection() == null){
						continue;
					}else{
						try {				
							int tc = _client.getTimeCount();
							if (tc >= 11) giveFeather(_client);
							else _client.setTimeCount(tc+1);

							if(_client.getLevel() >= 49){
								int sc = _client.getSafeCount();
								if(CharPosUtil.getZoneType(_client) == 1 && !_client.isPrivateShop()) {
									if(sc >= 14){
										if(_client.getAinHasad() <= 1999999)
											_client.calAinHasad(10000 * 2);
										_client.setSafeCount(0);
									} else {
										_client.setSafeCount(sc+1);
									}
								} else {
									if(sc > 0)
										_client.setSafeCount(0);
								}
							}

							if(_client.getMapId() >= 53 && _client.getMapId() <= 56)// 기란던전
								GungeonTimeCheck(_client);

							int keycount = _client.getInventory().countItems(L1ItemId.DRAGON_KEY);
							if(keycount > 0)
								DragonkeyTimeCheck(_client, keycount);


						} catch (Exception e) {
							_log.warning("Primeum give failure.");
							_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
							throw e;
						}
					}
				}
				Thread.sleep(60000);
			} catch (Exception e) {
				_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
				//cancel();
			}
		}
	}

	private void giveFeather(L1PcInstance pc) {

		pc.setTimeCount(0);
		int featherCount = 0;
		
		if (Config.GAME_SERVER_TYPE == 1){
			featherCount = 600;
		}else{
			if(pc.isPrivateShop()){
				featherCount = 1;
			}
			else{
				featherCount = 18;
			}
		}
		featherCount *= pc.getadFeature();
		
		pc.getInventory().storeItem(41159, featherCount); // 신비한 날개깃털 지급 
		pc.sendPackets(new S_ServerMessage(403, "$5116 ("+featherCount+")"));
	}
	
	private void DragonkeyTimeCheck(L1PcInstance pc, int count) {
		long nowtime = System.currentTimeMillis();
		if(count == 1){
			L1ItemInstance item = pc.getInventory().findItemId(L1ItemId.DRAGON_KEY);
			if(nowtime > item.getEndTime().getTime())
				pc.getInventory().removeItem(item);
		}else{
			L1ItemInstance[] itemList = pc.getInventory().findItemsId(L1ItemId.DRAGON_KEY);
			for (int i = 0; i < itemList.length; i++) {
				if(nowtime > itemList[i].getEndTime().getTime())
					pc.getInventory().removeItem(itemList[i]);		
			}
		}
	}

	private void GungeonTimeCheck(L1PcInstance pc) {
		RealTime time = RealTimeClock.getInstance().getRealTime();
		int entertime = pc.getGdungeonTime() % 1000;
		int enterday = pc.getGdungeonTime() / 1000;
		int dayofyear = time.get(Calendar.DAY_OF_YEAR);

		if(dayofyear == 365)
			dayofyear += 1;

		if(entertime > 180){
			// 메세지를 주고
			L1Teleport.teleport(pc, 33419, 32810, (short) 4, 5, true);
		} else if(enterday < dayofyear){
			pc.setGdungeonTime(time.get(Calendar.DAY_OF_YEAR) * 1000);
		} else {
			if(entertime > 169){
				int a = 180 - entertime;
				pc.sendPackets(new S_ServerMessage(1527, ""+a+""));// 체류시간이  %분 남았다.
			}
			pc.setGdungeonTime(pc.getGdungeonTime() + 1);
		}
	}
}
