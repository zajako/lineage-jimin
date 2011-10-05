
package bone.server.server.serverpackets;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;

import bone.server.L1DatabaseFactory;
import bone.server.server.Opcodes;
import bone.server.server.utils.SQLUtil;

public class S_Chainfo extends ServerBasePacket {

	private static final String S_Chainfo = "[C] S_Chainfo";

	private static Logger _log = Logger.getLogger(S_Chainfo.class.getName());

	private byte[] _byte = null;

	public S_Chainfo(int number, String cha) {
		buildPacket(number, cha);
	}

	private void buildPacket(int number, String cha) {
		Connection con = null;
		Connection con1 = null;
		Connection con2 = null;
		PreparedStatement pstm = null;
		PreparedStatement pstm1 = null;
		PreparedStatement pstm2 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		String info1 = null;//������� �����������
		String clas = null;
		String ggg = null;
		int oo = 0;
		String ggg1 = null;
		int oo1 = 0;
		String ggg2 = null;
		int oo2 = 0;
		String ggg3 = null;
		int oo3 = 0;
		String ggg4 = null;
		int oo4 = 0;
		String ggg5 = null;
		int oo5 = 0;
		String ggg6 = null;
		int oo6 = 0;
		String ggg7 = null;
		int oo7 = 0;
		String ggg8 = null;
		int oo8 = 0;
		String ggg9 = null;
		int oo9 = 0;
		int rol= 0;
		int	info2 = 0;
		int	info3 = 0;
		int	info4 = 0;
		int	info5 = 0;
		int	info6 = 0;
		int	info7 = 0;
		int	info8 = 0;
		int	info9 = 0;
		int	info10 = 0;
		int	info11 = 0;//�������
		int x = 0;
		int ddd = 357859160;
		
		try{
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM characters WHERE char_name=?");
			pstm.setString(1, cha);
			rs = pstm.executeQuery();
			
			while (rs.next()) {
			    info1 = rs.getString(2);//�ɸ��� ������Ʈ ���̵�� �˻� <--�̰� �Ʒ� �����۰˻��Ҷ� �ʿ���
				info2 = rs.getInt(4);//��
				info3 =	rs.getInt(7);//����
				info4 =	rs.getInt(9);//����
				info5 =	rs.getInt(11);//AC
				info6 =	rs.getInt(12);//��
				info7 =	rs.getInt(13);//��
				info8 =	rs.getInt(14);//����
				info9 =	rs.getInt(15);//ī��
				info10 =	rs.getInt(16);//��Ʈ
				info11 = rs.getInt(17);//����
				//info11 = rs.getInt(18);//Ŭ����
			}

			con2 = L1DatabaseFactory.getInstance().getConnection();
			pstm2 = con2.prepareStatement("SELECT * FROM character_items WHERE char_id=? AND item_name=?");
			pstm2.setString(1, info1);
			pstm2.setString(2, "�Ƶ���");
			rs2 = pstm2.executeQuery();
			while (rs2.next()) {
			rol = rs2.getInt(5);
			}
			con1 = L1DatabaseFactory.getInstance().getConnection();
			
			pstm1 = con1.prepareStatement("SELECT `enchantlvl`,`item_name` FROM `character_items` WHERE char_id=? ORDER BY `enchantlvl` DESC LIMIT 10");
			pstm1.setString(1, info1);//�������˻��� �ɸ� ������Ʈ�� �ٽ��ѹ� ���� �˻� 
			rs1 = pstm1.executeQuery();
			while (rs1.next()) {
				++x;
				if(x == 1){
					ggg = rs1.getString("item_name");//�������̸�
					oo = rs1.getInt("enchantlvl");//�̰� ��þƮ����
				}
				else if(x == 2){
					ggg1 = rs1.getString("item_name");//�������̸�
					oo1 = rs1.getInt("enchantlvl");//�̰� ��þƮ����
				}
				else if(x == 3){
					ggg2 = rs1.getString("item_name");//�������̸�
					oo2 = rs1.getInt("enchantlvl");//�̰� ��þƮ����
				}
				else if(x == 4){
					ggg3 = rs1.getString("item_name");//�������̸�
					oo3 = rs1.getInt("enchantlvl");//�̰� ��þƮ����
				}
				else if(x == 5){
					ggg4 = rs1.getString("item_name");//�������̸�
					oo4 = rs1.getInt("enchantlvl");//�̰� ��þƮ����
				}
				else if(x == 6){
					ggg5 = rs1.getString("item_name");//�������̸�
					oo5 = rs1.getInt("enchantlvl");//�̰� ��þƮ����
				}
				else if(x == 7){
					ggg6 = rs1.getString("item_name");//�������̸�
					oo6 = rs1.getInt("enchantlvl");//�̰� ��þƮ����
				}
				else if(x == 8){
					ggg7 = rs1.getString("item_name");//�������̸�
					oo7 = rs1.getInt("enchantlvl");//�̰� ��þƮ����
				}
				else if(x == 9){
					ggg8 = rs1.getString("item_name");//�������̸�
					oo8 = rs1.getInt("enchantlvl");//�̰� ��þƮ����
				}
				else if(x == 10){
					ggg9 = rs1.getString("item_name");//�������̸�
					oo9 = rs1.getInt("enchantlvl");//�̰� ��þƮ����
				}
			}
			
			if(info11 == 0){
				clas = "����";
			}
			else if(info11 == 1){
                clas = "���";
			}
			else if(info11 == 2){
                clas = "����";
			}
			else if(info11 == 3){
                clas = "������";
			}
			else if(info11 == 4){
                clas = "��ũ����";
			}
            writeC(Opcodes.S_OPCODE_BOARDREAD);
			writeD(number);//�ѹ�
			writeS("ĳ���� ����");//�۾���?
			writeS("");//��¥?
			writeS(cha);//����?
			writeS(" <"+cha+" ����>\n ����:"+info2+"\n HP:"+info3+"  MP:"+info4+" AC:"+info5+"\n STR:"+info6+"  CON:"+info7+"  DEX:" +info8+"\n CHA:"+info9+"  INT:"+info10+"  WIS:"+info11+"\n <������ ���>\n �Ƶ���:"+rol+"\n +"+oo+" "+ggg+"\n +"+oo1+" "+ggg1+"\n +"+oo2+" "+ggg2+"\n +"+oo3+" "+ggg3+"\n +"+oo4+" "+ggg4+"\n +"+oo5+" "+ggg5+"\n +"+oo6+" "+ggg6+"\n +"+oo7+" "+ggg7+"\n +"+oo8+" "+ggg8+"\n +"+oo9+" "+ggg9);// ������ �ɸ� ������ �̶� �������̸� ��þ ���� ������ �ǰ����� 

		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
			SQLUtil.close(rs1);
			SQLUtil.close(pstm1);
			SQLUtil.close(con1);
			SQLUtil.close(rs2);
			SQLUtil.close(pstm2);
			SQLUtil.close(con2);
		}
	}

	@Override
	public byte[] getContent() {
		if (_byte == null) {
			_byte = getBytes();
		}
		return _byte;
	}

	public String getType() {
		return S_Chainfo;
	}
}

