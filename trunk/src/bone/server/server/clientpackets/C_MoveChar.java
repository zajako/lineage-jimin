/* This program is free software; you can redistribute it and/or modify
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

import static bone.server.server.model.Instance.L1PcInstance.REGENSTATE_MOVE;

import server.LineageClient;
import server.manager.bone;

//import java.util.Random; // �ð��� �տ� - ƼĮ�� �ּ�

import bone.server.GameSystem.CrockSystem;
import bone.server.GameSystem.PetRacing;
import bone.server.server.datatables.BoneSystemTable;
import bone.server.server.model.Broadcaster;
import bone.server.server.model.Dungeon;
import bone.server.server.model.DungeonRandom;
import bone.server.server.model.L1Teleport;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.model.skill.L1SkillId;
import bone.server.server.model.trap.L1WorldTraps;
import bone.server.server.serverpackets.S_MoveCharPacket;
import bone.server.server.templates.L1BoneSystem;

//Referenced classes of package bone.server.server.clientpackets:
//ClientBasePacket

public class C_MoveChar extends ClientBasePacket {

	private static final byte HEADING_TABLE_X[] = { 0, 1, 1, 1, 0, -1, -1, -1 };	
	private static final byte HEADING_TABLE_Y[] = { -1, -1, 0, 1, 1, 1, 0, -1 };

	// �̵�
	public C_MoveChar(byte decrypt[], LineageClient client) throws Exception {
		super(decrypt);
		int locx = readH();
		int locy = readH();
		int heading = readC();
		boolean ck = false;
		L1PcInstance pc = client.getActiveChar();
		if (pc == null) return;
		if (pc.isTeleport()) { return; }
		/** �վ���� */
		if (!pc.getMap().ismPassable(locx, locy, heading)){
			ck = true;
		}

		pc.getSkillEffectTimerSet().killSkillEffectTimer(L1SkillId.MEDITATION);
		pc.setCallClanId(0);

		if (!pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.ABSOLUTE_BARRIER)) { // �ƺ�Ҹ�Ʈ�ٸ������� �ƴϴ�
			pc.setRegenState(REGENSTATE_MOVE);
		}
		pc.getMap().setPassable(pc.getLocation(), true);

		locx += HEADING_TABLE_X[heading];
		locy += HEADING_TABLE_Y[heading];

		if (Dungeon.getInstance().dg(locx, locy, pc.getMap().getId(), pc)) { // ���� ������ �ڷ���Ʈ ���� ���
			return;
		}		  

		if (DungeonRandom.getInstance().dg(locx, locy, pc.getMap().getId(),	pc)) { // �ڷ���Ʈó�� ������ �ڷ���Ʈ ����
			return;
		}
		if (ck){
			L1Teleport.teleport(pc, pc.getX(), pc.getY(),pc.getMapId(),pc.getMoveState().getHeading(), false);
			bone.LogBugAppend("���:�վ�", pc, 2);		
			return;
		}
		pc.getLocation().set(locx, locy);
		pc.getMoveState().setHeading(heading);
		Broadcaster.broadcastPacket(pc, new S_MoveCharPacket(pc));

		if(CrockSystem.getInstance().isOpen()){
			L1BoneSystem bone = BoneSystemTable.getInstance().getSystem(1);
			int[] loc = CrockSystem.getInstance().loc();
			if(Math.abs(loc[0]-pc.getX())<=1 && Math.abs(loc[1] - pc.getY())<=1 && loc[2] == pc.getMap().getId()) {
				switch(bone.getMoveLocation()) {
				case 0: return;
				case 1: L1Teleport.teleport(pc, 32639, 32876, (short) 780, 2, false); break;// �׺�
				case 2: L1Teleport.teleport(pc, 32793, 32754, (short) 783, 2, false); break;// ƼĮ
				}			
			}
		}
		if(pc.isPetRacing())	PetRacing.getInstance().RacingCheckPoint(pc);
		L1WorldTraps.getInstance().onPlayerMoved(pc);		
		pc.getMap().setPassable(pc.getLocation(), false);

	}
}