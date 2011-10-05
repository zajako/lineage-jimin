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

import bone.server.server.model.Instance.L1DollInstance;
import bone.server.server.model.Instance.L1ItemInstance;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.model.Instance.L1PetInstance;
import bone.server.server.serverpackets.S_OwnCharStatus;
import bone.server.server.serverpackets.S_ServerMessage;
import bone.server.server.serverpackets.S_SkillIconGFX;
import server.LineageClient;
import server.manager.eva;

//Referenced classes of package bone.server.server.clientpackets:
//ClientBasePacket

public class C_DeleteInventoryItem extends ClientBasePacket {

	private static final String C_DELETE_INVENTORY_ITEM = "[C] C_DeleteInventoryItem";

	public C_DeleteInventoryItem(byte[] decrypt, LineageClient client) {
		super(decrypt);

		int itemObjectId = readD();
		L1PcInstance pc = client.getActiveChar();
		L1ItemInstance item = pc.getInventory().getItem(itemObjectId);

		// �����Ϸ��� �� �������� ������ ���� ���
		if (item == null) {	return; }
		if (item.getItem().isCantDelete()) {
			pc.sendPackets(new S_ServerMessage(125));
			return;
		}		
		if (item.isEquipped()) {
			// \f1������ �� ���� �������̳� ��� �ϰ� �ִ� �������� ���� �� �����ϴ�.
			pc.sendPackets(new S_ServerMessage(125));
			return;
		}		
		if(item.getBless() >= 128){
			pc.sendPackets(new S_ServerMessage(210, item.getItem().getName())); // \f1%0�� �����ų� �Ǵ� Ÿ�ο��� ������ �� �� �����ϴ�.
			return;
		}
		
		Object[] dolllist = pc.getDollList().values().toArray();
		L1DollInstance doll = null;
		for (Object dollObject : dolllist) {
			if (dollObject instanceof L1DollInstance) {
				doll = (L1DollInstance) dollObject;
				if (item.getId() == doll.getItemObjId()) {
					// \f1%0�� �����ų� �Ǵ� Ÿ�ο��� ������ �� �� �����ϴ�.
					pc.sendPackets(new S_ServerMessage(210, item.getItem().getName()));
					return;
				}
			}
		}

		Object[] petlist = pc.getPetList().values().toArray();
		L1PetInstance pet = null;
		for (Object petObject : petlist) {
			if (petObject instanceof L1PetInstance) {
				pet = (L1PetInstance) petObject;
				if (item.getId() == pet.getItemObjId()) {
					// \f1%0�� �����ų� �Ǵ� Ÿ�ο��� ������ �� �� �����ϴ�.
					pc.sendPackets(new S_ServerMessage(210, item.getItem().getName()));
					return;
				}
			}
		}
		eva.LogObserverAppend("�ۻ���", pc.getName(), item,item.getCount(), itemObjectId);
		pc.getInventory().removeItem(item, item.getCount());
		pc.getLight().turnOnOffLight();		
	}

	@Override
	public String getType() {
		return C_DELETE_INVENTORY_ITEM;
	}
}