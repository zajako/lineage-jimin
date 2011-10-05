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

package l1j.server.server;

import static l1j.server.server.model.skill.L1SkillId.ADVANCE_SPIRIT;
import static l1j.server.server.model.skill.L1SkillId.BLESS_WEAPON;
import static l1j.server.server.model.skill.L1SkillId.IRON_SKIN;
import static l1j.server.server.model.skill.L1SkillId.PHYSICAL_ENCHANT_DEX;
import static l1j.server.server.model.skill.L1SkillId.PHYSICAL_ENCHANT_STR;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;

import server.manager.eva;

import l1j.server.L1DatabaseFactory;
import l1j.server.GameSystem.Boss.BossSpawnTimeController;
import l1j.server.server.datatables.HongboTable;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.skill.L1SkillUse;
import l1j.server.server.serverpackets.S_ChatPacket;
import l1j.server.server.serverpackets.S_NpcChatPacket;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.utils.SQLUtil;

//Referenced classes of package l1j.server.server:
//ClientThread, Shutdown, IpTable, MobTable,
//PolyTable, IdFactory


public class UserCommands {

	private static UserCommands _instance;

	private UserCommands() {}

	public static UserCommands getInstance() {
		if (_instance == null) {
			_instance = new UserCommands();
		}
		return _instance;
	}

	public void handleCommands(L1PcInstance pc, String cmdLine) {		
		StringTokenizer token = new StringTokenizer(cmdLine);
		// 최초의 공백까지가 커맨드, 그 이후는 공백을 단락으로 한 파라미터로서 취급한다
		String cmd = token.nextToken();
		String param = "";
		while (token.hasMoreTokens()) {
			param = new StringBuilder(param).append(token.nextToken()).append(' ').toString();
		}
		param = param.trim();
			
		if (cmd.equalsIgnoreCase("도움말")) {
			showHelp(pc);
		} else if (cmd.equalsIgnoreCase("조사")){
			infoitem(pc, param);
		} else if (cmd.equalsIgnoreCase("랭킹")){
			infoRanking(pc);
		} else if (cmd.equalsIgnoreCase("버프")){
			buff(pc);
		} else if (cmd.equalsIgnoreCase(".")){
			tell(pc);
		} else if (cmd.equalsIgnoreCase("홍보인증")){
			HongboTable.getInstance().infoCount(pc);
		} else if (cmd.equalsIgnoreCase("spdlqjqhtm")){
			BossSpawnTimeController.getBossTime(pc);
		} else {
			S_ChatPacket s_chatpacket = new S_ChatPacket(pc, cmdLine, Opcodes.S_OPCODE_NORMALCHAT, 0);
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
						Broadcaster.broadcastPacket(mob, new S_NpcChatPacket(mob, cmdLine, 0));
					}
				}
			}
			eva.LogChatAppend("ⓝ", pc.getName(), cmdLine);			
		}
	}

	private void infoRanking(L1PcInstance pc){
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		PreparedStatement pstm1 = null;
		ResultSet rs1 = null;
		int allRank = 0;
		int classRank = 0;

		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM ( SELECT @RNUM:=@RNUM+1 AS ROWNUM , C.Exp,C.char_name,c.objid,c.type  FROM (SELECT @RNUM:=0) R, characters c  WHERE C.AccessLevel = 0  ORDER BY C.Exp DESC ) A  WHERE objid = ?");
			pstm.setInt(1, pc.getId());
			rs = pstm.executeQuery();
			if (rs.next()){
				allRank = rs.getInt(1);
			}
			
			pstm1 = con.prepareStatement("SELECT * FROM ( SELECT @RNUM:=@RNUM+1 AS ROWNUM , C.Exp,C.char_name,c.objid,c.type  FROM (SELECT @RNUM:=0) R, characters c  WHERE C.AccessLevel = 0  and c.type =? ORDER BY C.Exp DESC ) A  WHERE objid = ?");
			pstm1.setInt(1, pc.getType());
			pstm1.setInt(2, pc.getId());
			rs1 = pstm1.executeQuery();
			
			if (rs1.next()){
				classRank = rs1.getInt(1);
			}
						
			
		} catch (SQLException e) {
			System.out.println("랭킹 조회 실패");
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(rs1);
			SQLUtil.close(pstm1);
			SQLUtil.close(con);
		}
		pc.sendPackets(new S_SystemMessage("\\fY[** "+ pc.getName() +"님의 랭킹 내용 **]"));
		pc.sendPackets(new S_SystemMessage(
				"\\fY전체 : " + allRank +
				"위 // 클래스 : " + classRank +"위"));
	}
	private void showHelp(L1PcInstance pc) {
		pc.sendPackets(new S_SystemMessage("-------------------<유저 명령어>------------------"));
		pc.sendPackets(new S_SystemMessage(".도움말  .버프  .조사  ..(텔렉풀기) .랭킹"));
		pc.sendPackets(new S_SystemMessage(".홍보인증 : 홍보기 돌린 후 해야 정상 적용 됩니다."));
		pc.sendPackets(new S_SystemMessage("무인상점 : 상점 열고 리스 하시면 됩니다."));			
		pc.sendPackets(new S_SystemMessage("--------------------------------------------------"));
	}

	private void tell(L1PcInstance pc) {
		if (!pc.isParalyzed() && !pc.isSleeped() && !pc.isPinkName()) {
			L1Teleport.teleport(pc, pc.getX(), pc.getY(), pc.getMapId(), pc.getMoveState().getHeading(), false);			
		}
	}
	private void buff(L1PcInstance pc){
		if (pc.getLevel() > 55){
			pc.sendPackets(new S_SystemMessage("초보버프는 55레벨까지 사용 가능합니다."));
			return;
		}
		int[] allBuffSkill = { PHYSICAL_ENCHANT_DEX,
				PHYSICAL_ENCHANT_STR, BLESS_WEAPON, ADVANCE_SPIRIT,				
				IRON_SKIN, ADVANCE_SPIRIT};
		L1SkillUse l1skilluse = null;

		l1skilluse = new L1SkillUse();
		for (int i = 0; i < allBuffSkill.length ; i++) {
			l1skilluse.handleCommands(pc, allBuffSkill[i], pc.getId(), pc.getX(), pc.getY(), null, 0, L1SkillUse.TYPE_GMBUFF);
		}
		pc.sendPackets(new S_SystemMessage("초보버프는 55레벨까지 사용 가능합니다."));		
	}
	
	private void infoitem(L1PcInstance pc, String param){
		try {
			StringTokenizer tok = new StringTokenizer(param);
			String charname = tok.nextToken();
			L1PcInstance target = L1World.getInstance().getPlayer(charname);
			if (target != null){
				pc.sendPackets(new S_SystemMessage("\\fY[** "+ target.getName() +"님의 조사 내용 **]"));
				pc.sendPackets(new S_SystemMessage(
						"\\fY+9무기 이상 : " + target.getInventory().getItemEnchantCount(1, 9) +
						"개 // +7방어구 이상 : " + target.getInventory().getItemEnchantCount(2, 7)+"개"));
			} else {
				pc.sendPackets(new S_SystemMessage("현재 플레이 유저 중 조사하신 ["+charname+"] 유저는 없습니다."));
			}
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage(".조사 [캐릭터명] 으로 입력해 주시기 바랍니다."));
		}	
	}
}
