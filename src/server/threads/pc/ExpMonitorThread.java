package server.threads.pc;

import java.util.logging.Level;
import java.util.logging.Logger;

import bone.server.server.model.Broadcaster;
import bone.server.server.model.L1World;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.serverpackets.S_Lawful;

public class ExpMonitorThread extends Thread{
	
	private static ExpMonitorThread _instance;
	private static Logger _log = Logger.getLogger(ExpMonitorThread.class.getName());
	public static ExpMonitorThread getInstance(){
		if (_instance == null){
			_instance = new ExpMonitorThread();
			_instance.start();
		}
		return _instance;
	}	
	
	
	public ExpMonitorThread(){

	}
	
	public void run(){
		System.out.println(ExpMonitorThread.class.getName()  + " Ω√¿€");
		while(true){
			try {
				for(L1PcInstance pc : L1World.getInstance().getAllPlayers()){
					if(pc == null || pc.getNetConnection() == null || pc.isPrivateShop()){
						continue;
					}
					else{
						if (pc.getold_lawful() != pc.getLawful()) {
							pc.setold_lawful(pc.getLawful());
							S_Lawful s_lawful = new S_Lawful(pc.getId(), pc.getold_lawful());
							pc.sendPackets(s_lawful);
							Broadcaster.broadcastPacket(pc, s_lawful);
						}

						if (pc.getold_exp() != pc.getExp()) {
							pc.setold_exp(pc.getExp());
							pc.onChangeExp();
						}
					}
					
				}
				Thread.sleep(500);
			}
			catch(Exception e) {
				_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			}
		}
		
	}

}
