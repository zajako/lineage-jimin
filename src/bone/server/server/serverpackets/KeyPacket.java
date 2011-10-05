package bone.server.server.serverpackets;

public class KeyPacket extends ServerBasePacket{
	private byte[] _byte = null;
//	12 00 37 E4 82 E9 61 E8  26 E3 48 16 7F 01 00 00  
//	00 00
	public KeyPacket(){
		byte[] _byte1 = {  (byte) 0x37,
				(byte) 0xE4, (byte) 0x82, (byte) 0xE9, (byte) 0x61,
				(byte) 0xE8, (byte) 0x26, (byte) 0xE3, (byte) 0x48, (byte) 0x16, (byte) 0x7F,
				(byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 };
		for(int i=0;i<_byte1.length; i++){
			writeC(_byte1[i]);
		}

	}


	@Override
	public byte[] getContent() {
		if (_byte == null) {
			_byte = getBytes();
		}
		return _byte;
	}
}
