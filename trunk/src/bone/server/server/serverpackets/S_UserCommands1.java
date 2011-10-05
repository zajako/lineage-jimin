
package bone.server.server.serverpackets;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;

import bone.server.L1DatabaseFactory;
import bone.server.server.Opcodes;
import bone.server.server.utils.SQLUtil;

public class S_UserCommands1 extends ServerBasePacket {

	private static final String S_UserCommands1 = "[C] S_UserCommands1";

	private static Logger _log = Logger.getLogger(S_UserCommands1.class.getName());

	private byte[] _byte = null;

	public S_UserCommands1(int number) {
		buildPacket(number);
	}

	private void buildPacket(int number) {
		writeC(Opcodes.S_OPCODE_BOARDREAD);
		writeD(number);//�ѹ�
		writeS(" ��� ");//�۾���?
		writeS(" ����PC ����� ");//��¥?
		writeS("");//����?
		writeS("\n === ����PC Ƽ�� ���� ===\n" +
				 "\n" +
				 " ����PCƼ���� ����ִٸ�\n" +
				 " �ɸ��� ���ᳪ �����Ŀ���\n" +
				 " ���������� �ɸ��Ͱ� ����\n" +
				 " �����ʽ��ϴ�.\n" +
				 " ���Ը�������� �����ɸ���\n" +
				 " �ǹ����� �׻����϶�����\n" +
				 " ������ ���޵����ʽ��ϴ�.\n" +
				 " ������ ���� ����ÿ��� \n" +
				 " ����PC�� ���� �༭����\n" +
				 " �������ô�.\n\n" +
				 " �������϶� â�� �� �ñ���!\n" +
				 "\n" +
				 " === ����PC Ƽ�� ���� ===");

	}

	@Override
	public byte[] getContent() {
		if (_byte == null) {
			_byte = getBytes();
		}
		return _byte;
	}

	public String getType() {
		return S_UserCommands1;
	}
}

