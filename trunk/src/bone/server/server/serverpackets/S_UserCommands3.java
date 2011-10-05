package bone.server.server.serverpackets;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;

import bone.server.L1DatabaseFactory;
import bone.server.server.Opcodes;
import bone.server.server.utils.SQLUtil;

public class S_UserCommands3 extends ServerBasePacket {

	private static final String S_UserCommands3 = "[C] S_UserCommands3";

	private static Logger _log = Logger.getLogger(S_UserCommands3.class.getName());

	private byte[] _byte = null;

	public S_UserCommands3(int number) {
		buildPacket(number);
	}

	private void buildPacket(int number) {
		writeC(Opcodes.S_OPCODE_BOARDREAD);
		writeD(number);//�ѹ�
		writeS(" ��� ");//�۾���?
		writeS(" ���� �ɼ� ���� ");//��¥?
		writeS("");//����?
		  writeS("\n========[���� ����]==========\n" +
				 "\n" +
				 "[���׺���:����20% ����           ]\n" +
				 "[��Ƽ:Ac-5 �������+5       ]\n" +
				 "[������:��ƽ+4 ����5%      ]\n" +
				 "[��̾�:��ƽ +50   ��ƽ+50   ]\n" +
				 "[��ť.���:�ߵ��� �� +15    ]\n" +
				 "[�þ�.����:�ߵ��� ��Ÿ +15  ]\n" +
				 "[��īƮ����:����+3 Ȱ��Ÿ+5 ��ƽ+20 ]\n" +
				 "[����:Ȯ���� ����30 ��ƽ+5]\n" +
				 "[�ô�:�ߵ��� ��ȸ�� 30 ����]\n" +
				 "[��������:�ٰŸ� ��Ÿ+3 ���ϳ���+5]\n" +
				 "[����ƺ�:��+100 ��+100 ���ϳ���+5]\n" +
				 "\n\n" +
				 "=============================");
	}

	@Override
	public byte[] getContent() {
		if (_byte == null) {
			_byte = getBytes();
		}
		return _byte;
	}

	public String getType() {
		return S_UserCommands3;
	}
}

