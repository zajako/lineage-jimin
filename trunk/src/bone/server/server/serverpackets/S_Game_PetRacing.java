package bone.server.server.serverpackets;

import bone.server.GameSystem.PetRacing;
import bone.server.server.Opcodes;
import bone.server.server.model.Instance.L1PcInstance;

public class S_Game_PetRacing extends ServerBasePacket {

	private static final String S_GameList = "S_Game_PetRacing";

	private byte[] _byte = null;
	// ��ŷ
	public S_Game_PetRacing(int rankvalue){
		writeC(Opcodes.S_OPCODE_PACKETBOX);
		writeC(0x42);
		writeH(PetRacing.getInstance().getPlayMemberCount()); // �����ڼ�
        writeH(rankvalue); // ���
		for(L1PcInstance pc : PetRacing.getInstance().getRank()){
			writeS(pc.getName());
		}
	}	
	// LAB ����
	public S_Game_PetRacing(int maxLab, int nowLab) {
		writeC(Opcodes.S_OPCODE_PACKETBOX);
		writeC(0x43);
		writeH(maxLab);	// �ִ�LAB
		writeH(nowLab);		// ����LAB

	}
	
	@Override
	public byte[] getContent() {
		if (_byte == null) {
			_byte = getBytes();
		}
		return _byte;
	}

	public String getType() {
		return S_GameList;
	}
}
