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
package bone.server.server.model.Instance;

import bone.server.GameSystem.Antaras.AntarasRaid;
import bone.server.GameSystem.Antaras.AntarasRaidSystem;
import bone.server.GameSystem.Antaras.AntarasRaidTimer;
import bone.server.server.model.L1Teleport;
import bone.server.server.model.L1World;
import bone.server.server.serverpackets.S_RemoveObject;
import bone.server.server.serverpackets.S_ServerMessage;
import bone.server.server.templates.L1Npc;

public class L1FieldObjectInstance extends L1NpcInstance {

	private static final long serialVersionUID = 1L;

	private int moveMapId;
	
	public L1FieldObjectInstance(L1Npc template) {
		super(template);
	}

	@Override
	public void onAction(L1PcInstance pc) {}

	@Override
	public void onTalkAction(L1PcInstance pc) {
		int npcid = getNpcTemplate().get_npcId();
		switch(npcid){
		case 4212015:
			telAntarasRaidMap(pc, moveMapId);
			break;
		case 4500101:
			tel4room(pc, moveMapId);
			break;
		case 4500102:
			L1Teleport.teleport(pc, 32600, 32741, (short) moveMapId, 5, true);
			break;
		case 4500103:
			telAntarasLair(pc, moveMapId);
			default: break;
		}
	}

	@Override
	public void deleteMe() {
		_destroyed = true;
		if (getInventory() != null) {
			getInventory().clearItems();
		}
		L1World.getInstance().removeVisibleObject(this);
		L1World.getInstance().removeObject(this);
		for (L1PcInstance pc : L1World.getInstance().getRecognizePlayer(this)) {
			pc.getNearObjects().removeKnownObject(this);
			pc.sendPackets(new S_RemoveObject(this));
		}
		getNearObjects().removeAllKnownObjects();
	}

	/**
	 * 지정된 맵의 32명이 넘는지 체크해서 텔시킨다
	 * @param pc
	 * @param mapid
	 */
	private void telAntarasRaidMap(L1PcInstance pc, int mapid){
		int count = 0;
		for(L1PcInstance player : L1World.getInstance().getAllPlayers()){
			if(player.getMapId() == mapid){
				count += 1;
				if(count > 31)
					return;
			}
		}
		L1Teleport.teleport(pc, 32600, 32741, (short) mapid, 5, true);
	}

	/**
	 * 이동할 맵을 설정한다.
	 * @param id
	 */
	public void setMoveMapId(int id){
		moveMapId = id;
	}

	/***/
	private void tel4room(L1PcInstance pc, int mapid){
		AntarasRaid ar = AntarasRaidSystem.getInstance().getAR(mapid);
		int room = ar.getRoomNum();
				
		switch(room){
		case 0:
			AntarasRaidTimer art = new AntarasRaidTimer(ar, 0, 1320*1000);// 22분 체크
			art.begin();
		case 1:
			AntarasRaidTimer roomstart1 = new AntarasRaidTimer(ar, 1, 120*1000);// 2분 체크
			roomstart1.begin();
			L1Teleport.teleport(pc, 32935, 32611, (short) mapid, 5, true);
			break;
		case 2:
			AntarasRaidTimer roomstart2 = new AntarasRaidTimer(ar, 2, 120*1000);// 2분 체크
			roomstart2.begin();
			L1Teleport.teleport(pc, 32935, 32803, (short) mapid, 5, true);
			break;
		case 3:
			AntarasRaidTimer roomstart3 = new AntarasRaidTimer(ar, 3, 120*1000);// 2분 체크
			roomstart3.begin();
			L1Teleport.teleport(pc, 32807, 32803, (short) mapid, 5, true);
			break;
		case 4:
			AntarasRaidTimer roomstart4 = new AntarasRaidTimer(ar, 4, 120*1000);// 2분 체크
			roomstart4.begin();
			L1Teleport.teleport(pc, 32679, 32803, (short) mapid, 5, true);
			break;
		case 5:
			pc.sendPackets(new S_ServerMessage(1599));
			return;
			default: break;
		}
		ar.addUser4room(pc, room);
	}
	
	private void telAntarasLair(L1PcInstance pc, int moveMapId2) {
		int count = 0;
		AntarasRaid ar = AntarasRaidSystem.getInstance().getAR(moveMapId2);
		count = ar.countLairUser(); 
		if(count >= 32)
			return;
		if(ar.isAntaras()){
			pc.sendPackets(new S_ServerMessage(1537));// 드래곤이 깨서 진입 못한다
			return;
		}
		if(count == 0){
			AntarasRaidTimer antastart = new AntarasRaidTimer(ar, 5, 120*1000);// 2분 체크
			antastart.begin();
			AntarasRaidTimer antaendtime = new AntarasRaidTimer(ar, 6, 1320*1000);// 22분 체크
			antaendtime.begin();
		}
	}

}
