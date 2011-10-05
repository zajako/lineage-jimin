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

package bone.server.server;

import static bone.server.server.Opcodes.C_OPCODE_ADDBUDDY;
import static bone.server.server.Opcodes.C_OPCODE_AMOUNT;
import static bone.server.server.Opcodes.C_OPCODE_ARROWATTACK;
import static bone.server.server.Opcodes.C_OPCODE_ATTACK;
import static bone.server.server.Opcodes.C_OPCODE_ATTR;
import static bone.server.server.Opcodes.C_OPCODE_BANCLAN;
import static bone.server.server.Opcodes.C_OPCODE_BANPARTY;
import static bone.server.server.Opcodes.C_OPCODE_BASERESET;
import static bone.server.server.Opcodes.C_OPCODE_BOARD;
import static bone.server.server.Opcodes.C_OPCODE_BOARDBACK;
import static bone.server.server.Opcodes.C_OPCODE_BOARDDELETE;
import static bone.server.server.Opcodes.C_OPCODE_BOARDREAD;
import static bone.server.server.Opcodes.C_OPCODE_BOARDWRITE;
import static bone.server.server.Opcodes.C_OPCODE_BOOKMARK;
import static bone.server.server.Opcodes.C_OPCODE_BOOKMARKDELETE;
import static bone.server.server.Opcodes.C_OPCODE_BUDDYLIST;
import static bone.server.server.Opcodes.C_OPCODE_CALL;
import static bone.server.server.Opcodes.C_OPCODE_CHANGEHEADING;
import static bone.server.server.Opcodes.C_OPCODE_CHARACTERCONFIG;
import static bone.server.server.Opcodes.C_OPCODE_CHAT;
import static bone.server.server.Opcodes.C_OPCODE_CHATGLOBAL;
import static bone.server.server.Opcodes.C_OPCODE_CHATPARTY;
import static bone.server.server.Opcodes.C_OPCODE_CHATWHISPER;
import static bone.server.server.Opcodes.C_OPCODE_CHECKPK;
import static bone.server.server.Opcodes.C_OPCODE_CLAN;
import static bone.server.server.Opcodes.C_OPCODE_CLIENTVERSION;
import static bone.server.server.Opcodes.C_OPCODE_CREATECLAN;
import static bone.server.server.Opcodes.C_OPCODE_CREATEPARTY;
import static bone.server.server.Opcodes.C_OPCODE_CREATE_CHARACTER;
import static bone.server.server.Opcodes.C_OPCODE_DELBUDDY;
import static bone.server.server.Opcodes.C_OPCODE_DELETECHAR;
import static bone.server.server.Opcodes.C_OPCODE_DELETEINVENTORYITEM;
import static bone.server.server.Opcodes.C_OPCODE_DEPOSIT;
import static bone.server.server.Opcodes.C_OPCODE_DOOR;
import static bone.server.server.Opcodes.C_OPCODE_DRAWAL;
import static bone.server.server.Opcodes.C_OPCODE_DROPITEM;
import static bone.server.server.Opcodes.C_OPCODE_EMBLEM;
import static bone.server.server.Opcodes.C_OPCODE_ENTERPORTAL;
import static bone.server.server.Opcodes.C_OPCODE_EXCLUDE;
import static bone.server.server.Opcodes.C_OPCODE_EXIT_GHOST;
import static bone.server.server.Opcodes.C_OPCODE_EXTCOMMAND;
import static bone.server.server.Opcodes.C_OPCODE_FIGHT;
import static bone.server.server.Opcodes.C_OPCODE_FISHCLICK;
import static bone.server.server.Opcodes.C_OPCODE_FIX_WEAPON_LIST;
import static bone.server.server.Opcodes.C_OPCODE_GIVEITEM;
import static bone.server.server.Opcodes.C_OPCODE_HORUN;
import static bone.server.server.Opcodes.C_OPCODE_HORUNOK;
import static bone.server.server.Opcodes.C_OPCODE_HOTEL_ENTER;
import static bone.server.server.Opcodes.C_OPCODE_JOINCLAN;
import static bone.server.server.Opcodes.C_OPCODE_KEEPALIVE;
import static bone.server.server.Opcodes.C_OPCODE_LEAVECLANE;
import static bone.server.server.Opcodes.C_OPCODE_LEAVEPARTY;
import static bone.server.server.Opcodes.C_OPCODE_LOGINPACKET;
import static bone.server.server.Opcodes.C_OPCODE_LOGINTOSERVEROK;
import static bone.server.server.Opcodes.C_OPCODE_MAIL;
import static bone.server.server.Opcodes.C_OPCODE_MOVECHAR;
import static bone.server.server.Opcodes.C_OPCODE_NOTICECLICK;
import static bone.server.server.Opcodes.C_OPCODE_NPCACTION;
import static bone.server.server.Opcodes.C_OPCODE_NPCTALK;
import static bone.server.server.Opcodes.C_OPCODE_PARTY;
import static bone.server.server.Opcodes.C_OPCODE_PETMENU;
import static bone.server.server.Opcodes.C_OPCODE_PICKUPITEM;
import static bone.server.server.Opcodes.C_OPCODE_PLEDGE;
import static bone.server.server.Opcodes.C_OPCODE_PRIVATESHOPLIST;
import static bone.server.server.Opcodes.C_OPCODE_PROPOSE;
import static bone.server.server.Opcodes.C_OPCODE_QUITGAME;
import static bone.server.server.Opcodes.C_OPCODE_RANK;
import static bone.server.server.Opcodes.C_OPCODE_REPORT;
import static bone.server.server.Opcodes.C_OPCODE_RESTART;
import static bone.server.server.Opcodes.C_OPCODE_RESTART_AFTER_DIE;
import static bone.server.server.Opcodes.C_OPCODE_RETURNTOLOGIN;
import static bone.server.server.Opcodes.C_OPCODE_SECURITYSTATUS;
import static bone.server.server.Opcodes.C_OPCODE_SECURITYSTATUSSET;
import static bone.server.server.Opcodes.C_OPCODE_SELECTLIST;
import static bone.server.server.Opcodes.C_OPCODE_SELECTTARGET;
import static bone.server.server.Opcodes.C_OPCODE_SELECT_CHARACTER;
import static bone.server.server.Opcodes.C_OPCODE_SHIP;
import static bone.server.server.Opcodes.C_OPCODE_SHOP;
import static bone.server.server.Opcodes.C_OPCODE_SHOP_N_WAREHOUSE;
import static bone.server.server.Opcodes.C_OPCODE_SKILLBUY;
import static bone.server.server.Opcodes.C_OPCODE_SKILLBUYOK;
import static bone.server.server.Opcodes.C_OPCODE_SOLDIERBUY;
import static bone.server.server.Opcodes.C_OPCODE_SOLDIERGIVE;
import static bone.server.server.Opcodes.C_OPCODE_SOLDIERGIVEOK;
import static bone.server.server.Opcodes.C_OPCODE_TAXRATE;
import static bone.server.server.Opcodes.C_OPCODE_TELEPORT;
import static bone.server.server.Opcodes.C_OPCODE_TITLE;
import static bone.server.server.Opcodes.C_OPCODE_TRADE;
import static bone.server.server.Opcodes.C_OPCODE_TRADEADDCANCEL;
import static bone.server.server.Opcodes.C_OPCODE_TRADEADDITEM;
import static bone.server.server.Opcodes.C_OPCODE_TRADEADDOK;
import static bone.server.server.Opcodes.C_OPCODE_USEITEM;
import static bone.server.server.Opcodes.C_OPCODE_USEPETITEM;
import static bone.server.server.Opcodes.C_OPCODE_USESKILL;
import static bone.server.server.Opcodes.C_OPCODE_WAR;
import static bone.server.server.Opcodes.C_OPCODE_WAREHOUSEPASSWORD;
import static bone.server.server.Opcodes.C_OPCODE_WARTIMELIST;
import static bone.server.server.Opcodes.C_OPCODE_WARTIMESET;
import static bone.server.server.Opcodes.C_OPCODE_WHO;

import java.util.logging.Logger;

import server.LineageClient;

import bone.server.server.clientpackets.C_AddBookmark;
import bone.server.server.clientpackets.C_AddBuddy;
import bone.server.server.clientpackets.C_Amount;
import bone.server.server.clientpackets.C_Attack;
import bone.server.server.clientpackets.C_Attr;
import bone.server.server.clientpackets.C_AuthLogin;
import bone.server.server.clientpackets.C_BanClan;
import bone.server.server.clientpackets.C_BanParty;
import bone.server.server.clientpackets.C_Board;
import bone.server.server.clientpackets.C_BoardBack;
import bone.server.server.clientpackets.C_BoardDelete;
import bone.server.server.clientpackets.C_BoardRead;
import bone.server.server.clientpackets.C_BoardWrite;
import bone.server.server.clientpackets.C_Buddy;
import bone.server.server.clientpackets.C_CallPlayer;
import bone.server.server.clientpackets.C_ChangeHeading;
import bone.server.server.clientpackets.C_CharcterConfig;
import bone.server.server.clientpackets.C_Chat;
import bone.server.server.clientpackets.C_ChatParty;
import bone.server.server.clientpackets.C_ChatWhisper;
import bone.server.server.clientpackets.C_CheckPK;
import bone.server.server.clientpackets.C_Clan;
import bone.server.server.clientpackets.C_CreateClan;
import bone.server.server.clientpackets.C_CreateNewCharacter;
import bone.server.server.clientpackets.C_CreateParty;
import bone.server.server.clientpackets.C_DelBuddy;
import bone.server.server.clientpackets.C_DeleteBookmark;
import bone.server.server.clientpackets.C_DeleteChar;
import bone.server.server.clientpackets.C_DeleteInventoryItem;
import bone.server.server.clientpackets.C_Deposit;
import bone.server.server.clientpackets.C_Door;
import bone.server.server.clientpackets.C_Drawal;
import bone.server.server.clientpackets.C_DropItem;
import bone.server.server.clientpackets.C_Emblem;
import bone.server.server.clientpackets.C_EnterPortal;
import bone.server.server.clientpackets.C_Exclude;
import bone.server.server.clientpackets.C_ExitGhost;
import bone.server.server.clientpackets.C_ExtraCommand;
import bone.server.server.clientpackets.C_Fight;
import bone.server.server.clientpackets.C_FishClick;
import bone.server.server.clientpackets.C_FixWeaponList;
import bone.server.server.clientpackets.C_GiveItem;
import bone.server.server.clientpackets.C_Horun;
import bone.server.server.clientpackets.C_HorunOK;
import bone.server.server.clientpackets.C_HotelEnter;
import bone.server.server.clientpackets.C_ItemUSe;
import bone.server.server.clientpackets.C_JoinClan;
import bone.server.server.clientpackets.C_KeepALIVE;
import bone.server.server.clientpackets.C_LeaveClan;
import bone.server.server.clientpackets.C_LeaveParty;
import bone.server.server.clientpackets.C_LoginToServerOK;
import bone.server.server.clientpackets.C_MailBox;
import bone.server.server.clientpackets.C_MoveChar;
import bone.server.server.clientpackets.C_NPCAction;
import bone.server.server.clientpackets.C_NPCTalk;
import bone.server.server.clientpackets.C_NoticeClick;
import bone.server.server.clientpackets.C_Party;
import bone.server.server.clientpackets.C_PetMenu;
import bone.server.server.clientpackets.C_PickUpItem;
import bone.server.server.clientpackets.C_Pledge;
import bone.server.server.clientpackets.C_Propose;
import bone.server.server.clientpackets.C_Rank;
import bone.server.server.clientpackets.C_Report;
import bone.server.server.clientpackets.C_Restart;
import bone.server.server.clientpackets.C_RestartAfterDie;
import bone.server.server.clientpackets.C_ReturnStaus;
import bone.server.server.clientpackets.C_ReturnToLogin;
import bone.server.server.clientpackets.C_SecurityStatus;
import bone.server.server.clientpackets.C_SecurityStatusSet;
import bone.server.server.clientpackets.C_SelectCharacter;
import bone.server.server.clientpackets.C_SelectList;
import bone.server.server.clientpackets.C_SelectTarget;
import bone.server.server.clientpackets.C_ServerVersion;
import bone.server.server.clientpackets.C_Ship;
import bone.server.server.clientpackets.C_Shop;
import bone.server.server.clientpackets.C_ShopAndWarehouse;
import bone.server.server.clientpackets.C_ShopList;
import bone.server.server.clientpackets.C_SkillBuy;
import bone.server.server.clientpackets.C_SkillBuyOK;
import bone.server.server.clientpackets.C_SoldierBuy;
import bone.server.server.clientpackets.C_SoldierGive;
import bone.server.server.clientpackets.C_SoldierGiveOK;
import bone.server.server.clientpackets.C_TaxRate;
import bone.server.server.clientpackets.C_Teleport;
import bone.server.server.clientpackets.C_Title;
import bone.server.server.clientpackets.C_Trade;
import bone.server.server.clientpackets.C_TradeAddItem;
import bone.server.server.clientpackets.C_TradeCancel;
import bone.server.server.clientpackets.C_TradeOK;
import bone.server.server.clientpackets.C_UsePetItem;
import bone.server.server.clientpackets.C_UseSkill;
import bone.server.server.clientpackets.C_War;
import bone.server.server.clientpackets.C_WarTimeList;
import bone.server.server.clientpackets.C_WarTimeSet;
import bone.server.server.clientpackets.C_WarehousePassword;
import bone.server.server.clientpackets.C_Who;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.serverpackets.S_Notice;

//Referenced classes of package bone.server.server:
//Opcodes, LoginController, ClientThread, Logins

public class PacketHandler {
	
	@SuppressWarnings("unused")
	private static Logger _log = Logger.getLogger(PacketHandler.class.getName());

	public PacketHandler(LineageClient clientthread) {
		_client = clientthread;
	}

	public void handlePacket(byte abyte0[], L1PcInstance object) throws Exception {
		int i = abyte0[0] & 0xff;
		switch (i) {
		case C_OPCODE_EXCLUDE: new C_Exclude(abyte0, _client); break;
		case C_OPCODE_CHARACTERCONFIG: new C_CharcterConfig(abyte0, _client); break;
		case C_OPCODE_DOOR: new C_Door(abyte0, _client); break;
		case C_OPCODE_TITLE: new C_Title(abyte0, _client); break;
		case C_OPCODE_BOARDDELETE: new C_BoardDelete(abyte0, _client); break;
		case C_OPCODE_PLEDGE: new C_Pledge(abyte0, _client); break;
		case C_OPCODE_CHANGEHEADING: new C_ChangeHeading(abyte0, _client); break;
		case C_OPCODE_NPCACTION: new C_NPCAction(abyte0, _client); break;
		case C_OPCODE_USESKILL: new C_UseSkill(abyte0, _client); break;
		case C_OPCODE_EMBLEM: new C_Emblem(abyte0, _client); break;
		case C_OPCODE_TRADEADDCANCEL: new C_TradeCancel(abyte0, _client); break;
		case C_OPCODE_WARTIMELIST: new C_WarTimeList(abyte0, _client); break;
		case C_OPCODE_BOOKMARK: new C_AddBookmark(abyte0, _client); break;
		case C_OPCODE_CREATECLAN: new C_CreateClan(abyte0, _client); break;
		case C_OPCODE_CLIENTVERSION: new C_ServerVersion(abyte0, _client); break;
		case C_OPCODE_PROPOSE: new C_Propose(abyte0, _client); break;
		case C_OPCODE_SKILLBUY: new C_SkillBuy(abyte0, _client); break;
		case C_OPCODE_BOARDBACK: new C_BoardBack(abyte0, _client); break;
		case C_OPCODE_SHOP: new C_Shop(abyte0, _client); break;
		case C_OPCODE_BOARDREAD: new C_BoardRead(abyte0, _client); break;
		case C_OPCODE_TRADE: new C_Trade(abyte0, _client); break;
		case C_OPCODE_DELETECHAR: new C_DeleteChar(abyte0, _client); break;
		case C_OPCODE_KEEPALIVE: new C_KeepALIVE(abyte0, _client); break;
		case C_OPCODE_ATTR: new C_Attr(abyte0, _client); break;
		case C_OPCODE_LOGINPACKET: new C_AuthLogin(abyte0, _client); break;
		case C_OPCODE_SHOP_N_WAREHOUSE: new C_ShopAndWarehouse(abyte0, _client); break;
		case C_OPCODE_DEPOSIT: new C_Deposit(abyte0, _client); break;
		case C_OPCODE_DRAWAL: new C_Drawal(abyte0, _client); break;
		case C_OPCODE_LOGINTOSERVEROK: new C_LoginToServerOK(abyte0, _client); break;
		case C_OPCODE_SKILLBUYOK: new C_SkillBuyOK(abyte0, _client); break;
		case C_OPCODE_TRADEADDITEM: new C_TradeAddItem(abyte0, _client); break;
		case C_OPCODE_ADDBUDDY: new C_AddBuddy(abyte0, _client); break;
		case C_OPCODE_RETURNTOLOGIN: new C_ReturnToLogin(abyte0, _client); break;
		case C_OPCODE_CHAT: new C_Chat(abyte0, _client); break;
		case C_OPCODE_TRADEADDOK: new C_TradeOK(abyte0, _client); break;
		case C_OPCODE_CHECKPK: new C_CheckPK(abyte0, _client); break;
		case C_OPCODE_TAXRATE: new C_TaxRate(abyte0, _client); break;
		case C_OPCODE_RESTART: new C_Restart(abyte0, _client);/* new C_NoticeClick(_client);*/ break;
		case C_OPCODE_BUDDYLIST: new C_Buddy(abyte0, _client); break;
		case C_OPCODE_DROPITEM: new C_DropItem(abyte0, _client); break;
		case C_OPCODE_LEAVEPARTY: new C_LeaveParty(abyte0, _client); break;
		case C_OPCODE_ATTACK: case C_OPCODE_ARROWATTACK: new C_Attack(abyte0, _client); break;
			// 캐릭터의 쇼트 컷이나 목록 상태가 플레이중에 변동했을 경우에
			// 쇼트 컷이나 목록 상태를 부가해 클라이언트로부터 송신되어 온다
			// 보내져 오는 타이밍은 클라이언트 종료시
		case C_OPCODE_QUITGAME: break;
		case C_OPCODE_BANCLAN: new C_BanClan(abyte0, _client); break;
		case C_OPCODE_BOARD: new C_Board(abyte0, _client); break;
		case C_OPCODE_DELETEINVENTORYITEM: new C_DeleteInventoryItem(abyte0, _client); break;
		case C_OPCODE_CHATWHISPER: new C_ChatWhisper(abyte0, _client); break;
		case C_OPCODE_PARTY: new C_Party(abyte0, _client); break;
		case C_OPCODE_PICKUPITEM: new C_PickUpItem(abyte0, _client); break;
		case C_OPCODE_WHO: new C_Who(abyte0, _client); break;
		case C_OPCODE_GIVEITEM: new C_GiveItem(abyte0, _client); break;
		case C_OPCODE_MOVECHAR: new C_MoveChar(abyte0, _client); break;
		case C_OPCODE_BOOKMARKDELETE: new C_DeleteBookmark(abyte0, _client); break;
		case C_OPCODE_RESTART_AFTER_DIE: new C_RestartAfterDie(abyte0, _client); break;
		case C_OPCODE_LEAVECLANE: new C_LeaveClan(abyte0, _client); break;
		case C_OPCODE_NPCTALK: new C_NPCTalk(abyte0, _client); break;
		case C_OPCODE_BANPARTY: new C_BanParty(abyte0, _client); break;
		case C_OPCODE_DELBUDDY: new C_DelBuddy(abyte0, _client); break;
		case C_OPCODE_WAR: new C_War(abyte0, _client); break;
		case C_OPCODE_SELECT_CHARACTER: new C_SelectCharacter(abyte0, _client); break;
		case C_OPCODE_PRIVATESHOPLIST: new C_ShopList(abyte0, _client); break;
		case C_OPCODE_CHATGLOBAL: new C_Chat(abyte0, _client); break;
		case C_OPCODE_JOINCLAN: new C_JoinClan(abyte0, _client); break;
		case C_OPCODE_NOTICECLICK:
			if(S_Notice.NoticeCount(_client.getAccountName()) > 0){
				_client.sendPacket(new S_Notice(_client.getAccountName(), _client));
			}else{
				new C_NoticeClick(_client);
			}
			break;
		case C_OPCODE_CREATE_CHARACTER: new C_CreateNewCharacter(abyte0, _client); break;
		case C_OPCODE_EXTCOMMAND: new C_ExtraCommand(abyte0, _client); break;
		case C_OPCODE_BOARDWRITE: new C_BoardWrite(abyte0, _client); break;
		case C_OPCODE_USEITEM: new C_ItemUSe(abyte0, _client); break;
		case C_OPCODE_CREATEPARTY: new C_CreateParty(abyte0, _client); break;
		case C_OPCODE_ENTERPORTAL: new C_EnterPortal(abyte0, _client); break;
		case C_OPCODE_AMOUNT: new C_Amount(abyte0, _client); break;
		case C_OPCODE_FIX_WEAPON_LIST: new C_FixWeaponList(abyte0, _client); break;
		case C_OPCODE_SELECTLIST: new C_SelectList(abyte0, _client); break;
		case C_OPCODE_EXIT_GHOST: new C_ExitGhost(abyte0, _client); break;
		case C_OPCODE_CALL: new C_CallPlayer(abyte0, _client); break;
		case C_OPCODE_FISHCLICK: new C_FishClick(abyte0, _client); break;
		case C_OPCODE_SELECTTARGET: new C_SelectTarget(abyte0, _client); break;
		case C_OPCODE_PETMENU: new C_PetMenu(abyte0, _client); break;
		case C_OPCODE_USEPETITEM: new C_UsePetItem(abyte0, _client); break;
		case C_OPCODE_TELEPORT: new C_Teleport(abyte0, _client); break;
		case C_OPCODE_RANK: new C_Rank(abyte0, _client); break;
		case C_OPCODE_CHATPARTY: new C_ChatParty(abyte0, _client); break;
		case C_OPCODE_FIGHT: new C_Fight(abyte0, _client); break;
		case C_OPCODE_SHIP: new C_Ship(abyte0, _client); break;
		case C_OPCODE_MAIL: new C_MailBox(abyte0, _client); break;
		case C_OPCODE_BASERESET: new C_ReturnStaus(abyte0, _client); break;
		case C_OPCODE_WAREHOUSEPASSWORD: new C_WarehousePassword(abyte0, _client); break; // 창고 비번
		case C_OPCODE_HORUN: new C_Horun(abyte0, _client); break;
		case C_OPCODE_HORUNOK: new C_HorunOK(abyte0, _client); break;
		case C_OPCODE_SOLDIERBUY: new C_SoldierBuy(abyte0, _client); break;
		case C_OPCODE_SOLDIERGIVE: new C_SoldierGive(abyte0, _client); break;
		case C_OPCODE_SOLDIERGIVEOK: new C_SoldierGiveOK(abyte0, _client); break;
		case C_OPCODE_WARTIMESET: new C_WarTimeSet(abyte0, _client); break;
		case C_OPCODE_CLAN: new C_Clan(abyte0, _client); break;
		case C_OPCODE_SECURITYSTATUS: new C_SecurityStatus(abyte0, _client); break;
		case C_OPCODE_SECURITYSTATUSSET: new C_SecurityStatusSet(abyte0, _client); break;
		case C_OPCODE_REPORT: new C_Report(abyte0, _client); break;
		case C_OPCODE_HOTEL_ENTER: new C_HotelEnter(abyte0, _client); break;
		default:
//		String s = Integer.toHexString(abyte0[0] & 0xff);
//		_log.warning("용도 불명 작동코드:데이터 내용");
//		_log.warning((new StringBuilder()).append("작동코드(16진수) : ").append(s).toString());
//		System.out.println((new StringBuilder()).append("작동코드(10진수) : ").append(i).toString());
//		_client.sendPacket(new S_SystemMessage("작동코드(10진수) : "+i));
//		_log.warning((new StringBuilder()).append("작동코드(10진수) : ").append(i).toString());
//		_log.warning(new ByteArrayUtil(abyte0).dumpToString());
		break;
		}
//		_log.warning((new StringBuilder()).append("작동코드").append(i).toString());
//		 System.out.println(DataToPacket(abyte0, abyte0.length)); // 사용 처리
	}
	
	
	public String DataToPacket(byte[] data, int len) { 
		StringBuffer result = new StringBuffer();
		int counter = 0;
		for (int i=0;i< len;i++) { 
			if (counter % 16 == 0) { 
				result.append(HexToDex(i,4)+": ");
			}
			result.append(HexToDex(data[i] & 0xff, 2) + " ");
			counter++;
			if(counter == 16) { 
				result.append("   ");
				int charpoint = i-15;
				for (int a=0; a<16;a++) { 
					int t1 = data[charpoint++]; 
					if(t1 > 0x1f && t1 < 0x80) { 
						result.append((char)t1); 
					} else { 
						result.append('.'); 
					} 
				}
				result.append("\n"); counter = 0; 
			} 
		}
		int rest = data.length % 16;
		if (rest > 0 ) { 
			for (int i=0; i<17-rest;i++ ) { 
				result.append("   "); 
			}
			int charpoint = data.length-rest;
			for (int a=0; a<rest;a++) { 
				int t1 = data[charpoint++]; 
				if(t1 > 0x1f && t1 < 0x80) { 
					result.append((char)t1); 
				} else { 
					result.append('.'); 
				} 
			}
			result.append("\n");
		}
		return result.toString();
	}
	
	private String HexToDex(int data, int digits) {
	  String number = Integer.toHexString(data);
	  for (int i=number.length(); i< digits; i++) number = "0" + number;
	  return number;
	}

	private final LineageClient _client;
}