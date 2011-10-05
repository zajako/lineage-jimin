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
		writeD(number);//넘버
		writeS(" 운영자 ");//글쓴이?
		writeS(" 인형 옵션 설명 ");//날짜?
		writeS("");//제목?
		  writeS("\n========[인형 정보]==========\n" +
				 "\n" +
				 "[버그베어:무게20% 감소           ]\n" +
				 "[에티:Ac-5 결빙내성+5       ]\n" +
				 "[해츨링:엠틱+4 무게5%      ]\n" +
				 "[라미아:피틱 +50   엠틱+50   ]\n" +
				 "[서큐.장로:발동시 엠 +15    ]\n" +
				 "[시안.늑인:발동시 추타 +15  ]\n" +
				 "[코카트리스:공성+3 활추타+5 엠틱+20 ]\n" +
				 "[돌골램:확률적 리덕30 엠틱+5]\n" +
				 "[시댄서:발동시 피회복 30 증가]\n" +
				 "[스파토이:근거리 추타+3 스턴내성+5]\n" +
				 "[허수아비:피+100 엠+100 스턴내성+5]\n" +
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

