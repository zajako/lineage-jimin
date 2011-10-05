package bone.server.server.serverpackets;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;

import bone.server.L1DatabaseFactory;
import bone.server.server.Opcodes;
import bone.server.server.utils.SQLUtil;

public class S_UserCommands extends ServerBasePacket {

	private static final String S_UserCommands = "[C] S_UserCommands";

	private static Logger _log = Logger.getLogger(S_UserCommands.class.getName());

	private byte[] _byte = null;

	public S_UserCommands(int number) {
		buildPacket(number);
	}

	private void buildPacket(int number) {
		writeC(Opcodes.S_OPCODE_BOARDREAD);
		writeD(number);//�ѹ�
		writeS(" ��� ");//�۾���?
		writeS(" ��ɾ� ���� ");//��¥?
		writeS("");//����?
		  writeS("[====================] [==]\n" +
			     "[    .����             ] [��]\n" +
				 "[    .����             ] [��]\n" +
				 "[    .����             ] [��]\n" +
				 "[    .����             ] [��]\n" +
				 "[    .���             ] [��]\n" +
				 "[    .�����          ] [��]\n" +
				 "[    .���λ���       ] [��]\n" +
				 "[    .�ڷ�Ǯ��       ] [��]\n" +
				 "[    .������Ƽ       ] [��]\n" +
				 "[    .��������       ] [��]\n" +
				 "[    .��ȣ����       ] []\n" +
				 "[    .�����       ] \n" +
				 "[    .���̼���       ] \n" +
				 "[    .��������       ] \n" +
				 "[    .������Ƽ       ] \n" +
				 "[    .��ŷȮ��       ] \n" +
				 "[    .��ŷ�˻�       ] \n" +
				 "[    .��������       ] [��]\n" +
				 "[    .�����Ʈ       ] [��]\n" +
				 "[    .ȫ������       ] [��]\n" +
				 "[    .ȫ��Ȯ��       ] [��]\n" +
				 "[    .ȫ������       ] [��]\n" +
				 "[    .����������û ] [��]\n" +
				 "[====================] [==] ");

	}

	@Override
	public byte[] getContent() {
		if (_byte == null) {
			_byte = getBytes();
		}
		return _byte;
	}

	public String getType() {
		return S_UserCommands;
	}
}

