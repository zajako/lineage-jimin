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

package bone.server.server.clientpackets;

import server.LineageClient;
import server.manager.eva;
import bone.server.Config;
//import bone.server.channel.ChatMonitorChannel;
import bone.server.server.GMCommands;
import bone.server.server.Opcodes;
import bone.server.server.UserCommands;
import bone.server.server.model.Broadcaster;
import bone.server.server.model.L1Clan;
import bone.server.server.model.L1Object;
import bone.server.server.model.L1World;
import bone.server.server.model.Instance.L1MonsterInstance;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.model.skill.L1SkillId;
import bone.server.server.serverpackets.S_ChatPacket;
import bone.server.server.serverpackets.S_NpcChatPacket;
import bone.server.server.serverpackets.S_PacketBox;
import bone.server.server.serverpackets.S_ServerMessage;

//Referenced classes of package bone.server.server.clientpackets:
//ClientBasePacket

//chat opecode type
//통상 0x44 0x00
//절규(! ) 0x44 0x00
//속삭임(") 0x56 charname
//전체(&) 0x72 0x03
//트레이드($) 0x44 0x00
//PT(#) 0x44 0x0b
//혈맹(@) 0x44 0x04
//연합(%) 0x44 0x0d
//CPT(*) 0x44 0x0e

public class C_Chat extends ClientBasePacket {

	private static final String C_CHAT = "[C] C_Chat";

	public C_Chat(byte abyte0[], LineageClient clientthread) {
		super(abyte0);
		L1PcInstance pc = clientthread.getActiveChar();
		int chatType = readC();
		String chatText = readS();

		if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.SILENCE)
				|| pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.AREA_OF_SILENCE)
				|| pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_POISON_SILENCE)) {
			return;
		}
		if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_CHAT_PROHIBITED)) { // 채팅 금지중
			pc.sendPackets(new S_ServerMessage(242)); // 현재 채팅 금지중입니다.
			return;
		}

		if (pc.isDeathMatch() && !pc.isGhost()) {
			pc.sendPackets(new S_ServerMessage(912)); // 채팅을 할 수 없습니다.
			return;
		}

		switch(chatType){
			case 0 :{
				if (pc.isGhost() && !(pc.isGm() || pc.isMonitor())) {
					return;
				}
				// GM커멘드
				if (chatText.startsWith(".")){
					if (pc.getAccessLevel() == Config.GMCODE || pc.getAccessLevel() == 1){
						String cmd = chatText.substring(1);
						GMCommands.getInstance().handleCommands(pc, cmd);
						return;	
					} else {			
						String cmd = chatText.substring(1);
						UserCommands.getInstance().handleCommands(pc, cmd);
						return;
					}
				}

				if (chatText.startsWith("$")) {
					String text = chatText.substring(1);
					chatWorld(pc, text, 12);
					if (!pc.isGm()) {
						pc.checkChatInterval();
					}
					return;
				}

				S_ChatPacket s_chatpacket = new S_ChatPacket(pc, chatText, Opcodes.S_OPCODE_NORMALCHAT, 0);
				if (!pc.getExcludingList().contains(pc.getName())) {
					pc.sendPackets(s_chatpacket);
				}
				for (L1PcInstance listner : L1World.getInstance().getRecognizePlayer(pc)) {
					if (!listner.getExcludingList().contains(pc.getName())) {
						listner.sendPackets(s_chatpacket);
					}
				}
				// 돕펠 처리
				L1MonsterInstance mob = null;
				for (L1Object obj : pc.getNearObjects().getKnownObjects()) {
					if (obj instanceof L1MonsterInstance) {
						mob = (L1MonsterInstance) obj;
						if (mob.getNpcTemplate().is_doppel() && mob.getName().equals(pc.getName())) {
							Broadcaster.broadcastPacket(mob, new S_NpcChatPacket(mob, chatText, 0));
						}
					}
				}
				eva.LogChatAppend("ⓝ", pc.getName(), chatText);
			}
				break;
			case 2 :{
				if (pc.isGhost()) {
					return;
				}
//				ChatLogTable.getInstance().storeChat(pc, null, chatText, chatType);
				S_ChatPacket s_chatpacket = new S_ChatPacket(pc, chatText, Opcodes.S_OPCODE_NORMALCHAT, 2);
				if (!pc.getExcludingList().contains(pc.getName())) {
					pc.sendPackets(s_chatpacket);
				}
				for (L1PcInstance listner : L1World.getInstance().getVisiblePlayer(pc, 50)) {
					if (!listner.getExcludingList().contains(pc.getName())) {
						listner.sendPackets(s_chatpacket);
					}
				}
				eva.LogChatAppend("ⓝ", pc.getName(), chatText);
				// 돕펠 처리
				L1MonsterInstance mob = null;
				for (L1Object obj : pc.getNearObjects().getKnownObjects()) {
					if (obj instanceof L1MonsterInstance) {
						mob = (L1MonsterInstance) obj;
						if (mob.getNpcTemplate().is_doppel() && mob.getName().equals(pc.getName())) {
							for (L1PcInstance listner : L1World.getInstance().getVisiblePlayer(mob, 50)) {
								listner.sendPackets(new S_NpcChatPacket(mob, chatText, 2));
							}
						}
					}
				}
			}
				break;
			case 3 :{ chatWorld(pc, chatText, chatType); }break;
			case 4 : {
				if (pc.getClanid() != 0) { // 크란 소속중
					L1Clan clan = L1World.getInstance().getClan(pc.getClanname());
					int rank = pc.getClanRank();
					if (clan != null && (rank == L1Clan.CLAN_RANK_PUBLIC
									|| rank == L1Clan.CLAN_RANK_GUARDIAN || rank == L1Clan.CLAN_RANK_PRINCE)) {
//						ChatLogTable.getInstance().storeChat(pc, null, chatText, chatType);
						S_ChatPacket s_chatpacket = new S_ChatPacket(pc, chatText, Opcodes.S_OPCODE_MSG, 4);

						// monitoring
//						ChatMonitorChannel.getInstance().sendMsg(ChatMonitorChannel.CHAT_MONITOR_CLAN, chatText, pc);
						eva.LogChatAppend("＠", pc.getName(),pc.getClanname(), chatText,"@");					
						for (L1PcInstance listner : clan.getOnlineClanMember()) {
							if (!listner.getExcludingList().contains(pc.getName())) {
								listner.sendPackets(s_chatpacket);
							}
						}
					}
				}
			}
			break;
			case 11 : {
				if (pc.isInParty()) { // 파티중
//					ChatLogTable.getInstance().storeChat(pc, null, chatText, chatType);
					S_ChatPacket s_chatpacket = new S_ChatPacket(pc, chatText, Opcodes.S_OPCODE_MSG, 11);
					for (L1PcInstance listner : pc.getParty().getMembers()) {
						if (!listner.getExcludingList().contains(pc.getName())) {
							listner.sendPackets(s_chatpacket);
						}
					}
//					monitoring
//					ChatMonitorChannel.getInstance().sendMsg(ChatMonitorChannel.CHAT_MONITOR_PARTY, chatText, pc);
				}
				eva.LogChatAppend("＃", pc.getName(), chatText);				
			}
			break;
			case 12 : { chatWorld(pc, chatText, chatType); } break;
			case 13 : { // 연합 채팅
				if (pc.getClanid() != 0) { // 혈맹 소속중
					L1Clan clan = L1World.getInstance().getClan(pc.getClanname());
					int rank = pc.getClanRank();
					if (clan != null && (rank == L1Clan.CLAN_RANK_GUARDIAN || rank == L1Clan.CLAN_RANK_PRINCE)) {
//						ChatLogTable.getInstance().storeChat(pc, null, chatText, chatType);
						S_ChatPacket s_chatpacket = new S_ChatPacket(pc, chatText, Opcodes.S_OPCODE_MSG, 4);
						for (L1PcInstance listner : clan.getOnlineClanMember()) {
							int listnerRank = listner.getClanRank();
							if (!listner.getExcludingList().contains(pc.getName()) 
									&& (listnerRank == L1Clan.CLAN_RANK_GUARDIAN || listnerRank == L1Clan.CLAN_RANK_PRINCE)) {
								listner.sendPackets(s_chatpacket);
							}
						}
					}
					// monitoring
					// ChatMonitorChannel.getInstance().sendMsg(ChatMonitorChannel.CHAT_MONITOR_CLAN, chatText, pc);
				}
				eva.LogChatAppend("＃", pc.getName(), chatText);
			}
			break;
			case 14 : { // 채팅 파티
				if (pc.isInChatParty()) { // 채팅 파티중
//					ChatLogTable.getInstance().storeChat(pc, null, chatText, chatType);
					S_ChatPacket s_chatpacket = new S_ChatPacket(pc, chatText, Opcodes.S_OPCODE_NORMALCHAT, 14);
					for (L1PcInstance listner : pc.getChatParty().getMembers()) {
						if (!listner.getExcludingList().contains(pc.getName())) {
							listner.sendPackets(s_chatpacket);
						}
					}
				}
				// monitoring
				//ChatMonitorChannel.getInstance().sendMsg(ChatMonitorChannel.CHAT_MONITOR_PARTY, chatText, pc);
				eva.LogChatAppend("＊", pc.getName(), chatText);
			}
			break;
			case 15 :
				if (pc.getClan() != null && pc.getClan().getAlliance() != null) {
					pc.getClan().getAlliance().AllianceChat(pc, chatText);
				}
			break;
		}
		if (!pc.isGm()) {
			pc.checkChatInterval();
		}
	}

	private void chatWorld(L1PcInstance pc, String chatText, int chatType) {
		if (pc.isGm() || pc.getAccessLevel() == 1) {
			L1World.getInstance().broadcastPacketToAll(new S_ChatPacket(pc, chatText, Opcodes.S_OPCODE_MSG, chatType));
			eva.LogChatAppend("＆", pc.getName(), chatText);			
		} else if (pc.getLevel() >= Config.GLOBAL_CHAT_LEVEL) {
			if (L1World.getInstance().isWorldChatElabled()) {
				if (pc.get_food() >= 12) { //5%겟지?
//					ChatLogTable.getInstance().storeChat(pc, null, chatText, chatType);
					pc.sendPackets(new S_PacketBox(S_PacketBox.FOOD, pc.get_food()));					
					eva.LogChatAppend("＆", pc.getName(), chatText);
					for (L1PcInstance listner : L1World.getInstance().getAllPlayers()) {
						if (!listner.getExcludingList().contains(pc.getName())) {
							if (listner.isShowTradeChat() && chatType == 12) {
								listner.sendPackets(new S_ChatPacket(pc, chatText, Opcodes.S_OPCODE_MSG, chatType));
							} else if (listner.isShowWorldChat() && chatType == 3) {
								listner.sendPackets(new S_ChatPacket(pc, chatText, Opcodes.S_OPCODE_MSG, chatType));
							}
						}
					}
				} else {
					pc.sendPackets(new S_ServerMessage(462));
				}
			} else {
				pc.sendPackets(new S_ServerMessage(510)); 
			}
		} else {
			pc.sendPackets(new S_ServerMessage(195, String.valueOf(Config.GLOBAL_CHAT_LEVEL))); 
		}
	}

	@Override
	public String getType() {
		return C_CHAT;
	}
}
