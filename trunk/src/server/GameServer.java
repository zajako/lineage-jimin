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
package server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import bone.server.Config;
import bone.server.L1DatabaseFactory;
import bone.server.GameSystem.CrockSystem;
import bone.server.GameSystem.HomeTownController;
import bone.server.GameSystem.NoticeSystem;
import bone.server.GameSystem.Boss.BossSpawnTimeController;
import bone.server.GameSystem.Boss.L1BossCycle;
import bone.server.GameSystem.Lastabard.LastabardController;
import bone.server.server.GMCommandsConfig;
import bone.server.server.GeneralThreadPool;
import bone.server.server.ObjectIdFactory;
import bone.server.server.Shutdown;
import bone.server.server.TimeController.AuctionTimeController;
import bone.server.server.TimeController.FishingTimeController;
import bone.server.server.TimeController.HouseTaxTimeController;
import bone.server.server.TimeController.LightTimeController;
import bone.server.server.TimeController.LiveTimeController;
import bone.server.server.TimeController.NpcChatTimeController;
import bone.server.server.TimeController.UbTimeController;
import bone.server.server.TimeController.WarTimeController;
import bone.server.server.datatables.CastleTable;
import bone.server.server.datatables.CharacterTable;
import bone.server.server.datatables.ClanTable;
import bone.server.server.datatables.DoorSpawnTable;
import bone.server.server.datatables.DropItemTable;
import bone.server.server.datatables.DropTable;
import bone.server.server.datatables.EvaSystemTable;
import bone.server.server.datatables.FurnitureSpawnTable;
import bone.server.server.datatables.GetBackRestartTable;
import bone.server.server.datatables.IpTable;
import bone.server.server.datatables.ItemTable;
import bone.server.server.datatables.LightSpawnTable;
import bone.server.server.datatables.MapsTable;
import bone.server.server.datatables.MobGroupTable;
import bone.server.server.datatables.ModelSpawnTable;
import bone.server.server.datatables.NPCTalkDataTable;
import bone.server.server.datatables.NpcActionTable;
import bone.server.server.datatables.NpcChatTable;
import bone.server.server.datatables.NpcSpawnTable;
import bone.server.server.datatables.NpcTable;
import bone.server.server.datatables.PetTable;
import bone.server.server.datatables.PetTypeTable;
import bone.server.server.datatables.PolyTable;
import bone.server.server.datatables.ResolventTable;
import bone.server.server.datatables.ShopTable;
import bone.server.server.datatables.SkillsTable;
import bone.server.server.datatables.SoldierTable;
import bone.server.server.datatables.SpawnTable;
import bone.server.server.datatables.SprTable;
import bone.server.server.datatables.UBSpawnTable;
import bone.server.server.model.Dungeon;
import bone.server.server.model.ElementalStoneGenerator;
import bone.server.server.model.Getback;
import bone.server.server.model.L1BugBearRace;
import bone.server.server.model.L1CastleLocation;
import bone.server.server.model.L1Cube;
import bone.server.server.model.L1NpcRegenerationTimer;
import bone.server.server.model.L1World;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.model.gametime.GameTimeClock;
import bone.server.server.model.gametime.RealTimeClock;
import bone.server.server.model.item.L1TreasureBox;
import bone.server.server.model.map.L1WorldMap;
import bone.server.server.model.trap.L1WorldTraps;
import bone.server.server.utils.SQLUtil;
import server.controller.ItemDeleteController;
import server.manager.eva;
import server.system.autoshop.AutoShopManager;
import server.threads.manager.DecoderManager;
import server.threads.pc.AutoSaveThread;
import server.threads.pc.CharacterQuickCheckThread;
import server.threads.pc.DollObserverThread;
import server.threads.pc.HpMpRegenThread;
import server.threads.pc.PremiumAinThread;
import server.threads.pc.SpeedHackThread;

public class GameServer/* extends Thread*/ {
	private static Logger _log = Logger.getLogger(GameServer.class.getName());
	private static GameServer _instance;
	private boolean serverExit;
	private GameServer() {
		//super("GameServer");
	}

	public static GameServer getInstance() {
		if (_instance == null) {
			synchronized (GameServer.class) {
				if(_instance == null)
					_instance = new GameServer();
			}
		}
		return _instance;
	}

	public void setServerExit(boolean flag){
		serverExit = flag;
	}
	public boolean getServerExit(){
		return serverExit;
	}
	public void initialize() throws Exception {
		serverExit = false;
		showGameServerSetting();

		ObjectIdFactory.createInstance();
		L1WorldMap.createInstance();  // FIXME �ν��ϴ�

		initTime();

		CharacterTable.getInstance().loadAllCharName(); // FIXME ���� �޸𸮿� ����� �ʿ䰡 �ֳ�
		CharacterTable.clearOnlineStatus();

		// TODO change following code to be more effective

		// UBŸ�� ��Ʈ�ѷ�
		UbTimeController.getInstance();

		// ���� Ÿ�� ��Ʈ�ѷ�
		WarTimeController.getInstance();

		// ������ �� Ÿ�� ��Ʈ�ѷ�
		if (Config.ELEMENTAL_STONE_AMOUNT > 0) {
			ElementalStoneGenerator.getInstance();
		}

		// Ȩ Ÿ��
		HomeTownController.getInstance();

		// ����Ʈ ��� Ÿ�� ��Ʈ�ѷ�
		AuctionTimeController.getInstance();

		// ����Ʈ ���� Ÿ�� ��Ʈ�ѷ�
		HouseTaxTimeController.getInstance();

		// �����ǿ�ħ Ÿ�� ��Ʈ�ѷ�
		LiveTimeController.getInstance();

		// ���� Ÿ�� ��Ʈ�ѷ�
		FishingTimeController.getInstance();

		NpcChatTimeController.getInstance();

		NpcTable.getInstance();
		//L1DeleteItemOnGround deleteitem = new L1DeleteItemOnGround();
		//deleteitem.initialize();

		if (!NpcTable.getInstance().isInitialized()) {
			throw new Exception("[GameServer] Could not initialize the npc table");
		}

		SpawnTable.getInstance();
		MobGroupTable.getInstance();
		SkillsTable.getInstance();
		PolyTable.getInstance();
		ItemTable.getInstance();
		DropTable.getInstance();
		DropItemTable.getInstance();
		ShopTable.getInstance();
		NPCTalkDataTable.getInstance();
		L1World.getInstance();
		L1WorldTraps.getInstance();
		Dungeon.getInstance();
		NpcSpawnTable.getInstance();
		IpTable.getInstance();
		MapsTable.getInstance();
		UBSpawnTable.getInstance();
		PetTable.getInstance();
		ClanTable.getInstance();
		CastleTable.getInstance();
		L1CastleLocation.setCastleTaxRate(); // CastleTable �ʱ�ȭ ���� �ƴϸ� �� �ȴ�
		GetBackRestartTable.getInstance();
		DoorSpawnTable.getInstance();
		GeneralThreadPool.getInstance();
		L1NpcRegenerationTimer.getInstance();
//		ChatLogTable.getInstance();
//		WeaponSkillTable.getInstance();
		NpcActionTable.load();
		GMCommandsConfig.load();
		Getback.loadGetBack();
		PetTypeTable.load();
		L1TreasureBox.load();
		SprTable.getInstance();
		//RaceTable.getInstance();
		ResolventTable.getInstance();
		FurnitureSpawnTable.getInstance();
		NpcChatTable.getInstance();
//		CrockController.getInstance().start();
		L1Cube.getInstance();
		SoldierTable.getInstance();
		L1BugBearRace.getInstance();
		// ���� ���� Ÿ��
		L1BossCycle.load();
		BossSpawnTimeController.start();

		// ��Ÿ�ٵ� ����
		LastabardController.start();

		// ��������, ������ġ
		//GeneralThreadPool.getInstance().execute(DeathMatch.getInstance());
		//GeneralThreadPool.getInstance().execute(GhostHouse.getInstance());
		//GeneralThreadPool.getInstance().execute(PetRacing.getInstance());

		// ȶ��
		LightSpawnTable.getInstance();
		LightTimeController.start();

		// ���峻�� ���� �ֱ�(������ Ƚ�� ���)
		ModelSpawnTable.getInstance().ModelInsertWorld();

		// ���� �ð����� Ÿ�̸�
//		WarSetTime.start();

		// ���� ����
		NoticeSystem.start();

		// �ð��� �տ�
		CrockSystem.getInstance();
		EvaSystemTable.getInstance();

		if (Config.ALT_HALLOWEENEVENT != true) {
			Halloween();
		}

		// ����ǥ ����
		//RaceTicket();
//		MapFixKeyTable.getInstance();

//		MiniClient Mini = MiniClient.getInstance();
//		Mini.start();
		//�ɸ��� �ڵ����� �����ٷ� �ش� �ð��� �°� ��ü ������ �о �����Ų��.
		//CharacterAutoSaveController chaSave = new CharacterAutoSaveController(Config.AUTOSAVE_INTERVAL * 1000);
		//chaSave.start();


		//CharacterQuitCheckController quick = new CharacterQuitCheckController(10000);
		//quick.start();
		//�ɸ��Ͱ� ���� ������ Action�� �����Ѵ�.
		//DollobserverController dollAction = new DollobserverController(15000);
		//dollAction.start();

		//HpMpRegenController regen = new HpMpRegenController(1000);
		//regen.start();
		AutoSaveThread.getInstance();
		DollObserverThread.getInstance();
		HpMpRegenThread.getInstance();
		SpeedHackThread.getInstance();
		PremiumAinThread.getInstance();
		CharacterQuickCheckThread.getInstance();
		//AutoUpdateThread.getInstance();
		//ExpMonitorThread.getInstance();


		ItemDeleteController idel = new ItemDeleteController(60000);
		idel.start();

		DecoderManager.getInstance();
		//GCThread.getInstance();

		// ������ �÷��� ���� (Null) ��ü�� ����
		System.out.println("[GameServer] �ε� �Ϸ�!");
		System.out.println("=================================================");
		eva.refreshMemory();
		Runtime.getRuntime().addShutdownHook(Shutdown.getInstance());

		//this.start();
	}

	private void initTime() {
		GameTimeClock.init(); // ���� �ð� �ð�
		RealTimeClock.init(); // ���� �ð� �ð�
	}

	private void showGameServerSetting() {
		System.out.println("[GameServer] Starting GameServer"); // (" + serverSocket.toString() + ")");

		double rateXp			= Config.RATE_XP;
		double rateLawful 		= Config.RATE_LAWFUL;
		double rateKarma 		= Config.RATE_KARMA;
		double rateDropItems 	= Config.RATE_DROP_ITEMS;
		double rateDropAdena 	= Config.RATE_DROP_ADENA;

		System.out.println("[GameServer] Exp: x" + rateXp + " / Lawful: x" + rateLawful + " / Adena: x" + rateDropAdena);
		System.out.println("[GameServer] Karma: x" + rateKarma + " / Item: x" + rateDropItems);
		System.out.println("[GameServer] Chatting Level: " + Config.GLOBAL_CHAT_LEVEL);
		System.out.println("[GameServer] Maximum User: " + Config.MAX_ONLINE_USERS + "��");

		System.out.print("[GameServer] PvP mode: ");
		if (Config.ALT_NONPVP) 	System.out.println("On");
		else 					System.out.println("Off");
		System.out.println("=================================================");
	}

	/**
	 * �¶������� �÷��̾� ��ο� ���ؼ� kick, ĳ���� ������ ������ �Ѵ�.
	 */
	public void disconnectAllCharacters() {
		Collection<L1PcInstance> players = L1World.getInstance().getAllPlayers();
		// ��� ĳ���� ����
		for (L1PcInstance pc : players) {
			if (!AutoShopManager.getInstance().isExistAutoShop(pc.getId())) {
				try {
					pc.save();
					pc.saveInventory();
					pc.getNetConnection().setActiveChar(null);
					pc.getNetConnection().kick();
					pc.logout();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			else{
				try {
					pc.save();
					pc.saveInventory();
					pc.logout();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public int saveAllCharInfo() {
		// exception �߻��ϸ� -1 ����, �ƴϸ� ������ �ο� �� ����
		int cnt = 0;
		try {
			for(L1PcInstance pc : L1World.getInstance().getAllPlayers()){
				cnt++;
				pc.save();
				pc.saveInventory();
			}
		}
		catch(Exception e) { return -1; }

		return cnt;
	}

	/**
	 * �¶������� �÷��̾ ���ؼ� kick , ĳ���� ������ ������ �Ѵ�.
	 */
	public void disconnectChar(String name) {
		L1PcInstance pc = L1World.getInstance().getPlayer(name);
		L1PcInstance Player = pc;
		synchronized (pc) {
			pc.getNetConnection().kick();
			Player.logout();
			pc.getNetConnection().quitGame(Player);
		}
	}

	private class ServerShutdownThread extends Thread {
		private final int _secondsCount;

		public ServerShutdownThread(int secondsCount) {
			_secondsCount = secondsCount;
		}

		@Override
		public void run() {
			L1World world = L1World.getInstance();
			try {
				int secondsCount = _secondsCount;
				System.out.println("[GameServer] ��� ��, ������ ���� �մϴ�.");
				System.out.println("[GameServer] ������ ��ҿ��� �α׾ƿ� �� �ּ���.");
				world.broadcastServerMessage("��� ��, ������ ���� �մϴ�.");
				world.broadcastServerMessage("������ ��ҿ��� �α׾ƿ� �� �ּ���.");
				while (0 < secondsCount) {
					if (secondsCount <= 30) {
						System.out.println("[GameServer] ������ " + secondsCount + "�� �Ŀ� ���� �˴ϴ�. ������ �ߴ��� �ּ���.");
						world.broadcastServerMessage("������ " + secondsCount + "�� �Ŀ� ���� �˴ϴ�. ������ �ߴ��� �ּ���.");
					} else {
						if (secondsCount % 60 == 0) {
							System.out.println("[GameServer] ������ " + secondsCount / 60 + "�� �Ŀ� ���� �˴ϴ�.");
							world.broadcastServerMessage("������ " + secondsCount / 60 + "�� �Ŀ� ���� �˴ϴ�.");
						}
					}
					Thread.sleep(1000);
					secondsCount--;
				}
				shutdown();
			} catch (InterruptedException e) {
				System.out.println("[GameServer] ���� ���ᰡ �ߴܵǾ����ϴ�. ������ ���� �������Դϴ�.");
				world.broadcastServerMessage("���� ���ᰡ �ߴܵǾ����ϴ�. ������ ���� �������Դϴ�.");
				return;
			}
		}
	}

	private ServerShutdownThread _shutdownThread = null;

	public synchronized void shutdownWithCountdown(int secondsCount) {
		if (_shutdownThread != null) {
			// �̹� ���ٿ� �䱸�� �ϰ� �ִ�
			// TODO ���� ������ �ʿ������� �𸥴�
			return;
		}
		_shutdownThread = new ServerShutdownThread(secondsCount);
		GeneralThreadPool.getInstance().execute(_shutdownThread);
	}

	public void shutdown() {
		disconnectAllCharacters();
		eva.savelog();
		System.exit(0);
	}

	public synchronized void abortShutdown() {
		if (_shutdownThread == null) {
			// ���ٿ� �䱸�� ���� �ʾҴ�
			// TODO ���� ������ �ʿ������� �𸥴�
			return;
		}

		_shutdownThread.interrupt();
		_shutdownThread = null;
	}

	public void Halloween() {
		Connection con = null;
		PreparedStatement pstm = null;
		PreparedStatement pstm1 = null;
		PreparedStatement pstm2 = null;
		PreparedStatement pstm3 = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("DELETE FROM character_items WHERE item_id IN (20380, 21060, 256) AND enchantlvl < 8");
			pstm1 = con.prepareStatement("DELETE FROM character_elf_warehouse WHERE item_id IN (20380, 21060, 256) AND enchantlvl < 8");
			pstm2 = con.prepareStatement("DELETE FROM clan_warehouse WHERE item_id IN (20380, 21060, 256) AND enchantlvl < 8");
			pstm3 = con.prepareStatement("DELETE FROM character_warehouse WHERE item_id IN (20380, 21060, 256) AND enchantlvl < 8");
			pstm3.execute();
			pstm2.execute();
			pstm1.execute();
			pstm.execute();
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(pstm1);
			SQLUtil.close(pstm2);
			SQLUtil.close(pstm3);
			SQLUtil.close(con);
		}
	}

	public void RaceTicket() {
		Connection con = null;
		PreparedStatement pstm = null;

		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("DELETE FROM character_items WHERE item_id >= 8000000");
			pstm.execute();
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}
}
