/**
 * 
 * 					Eva Team - http://eva.pe.kr 
 * 				    
 * 					Member	- ���� (Shax2)				 	 
 * 							- �ڵ� (Shyeon0111)
 * 							- �ù��� (Cr7016)
 * 							- �˰� (Zinasura)
 * 							- ������ (Babuboss)
 * 
 */

package bone.server.server;

public class Opcodes {

	public Opcodes() {}


	
	
	
	
	
	
		/** * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * **/
		/**	* * * * * * * * * * * * * * * * Client Packet * * * * * * * * * * * * * * * * * * * * * * **/
		/** * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * **/
	

	
		public static final int C_OPCODE_PETMENU 										= 0x00; // �� �޴�
		public static final int C_OPCODE_SOLDIERBUY										= 0x01; // �뺴 ����Ʈ ����
		public static final int C_OPCODE_NOTICECLICK 									= 0x02; // �������� Ȯ�� ��������
		public static final int C_OPCODE_WAREHOUSEPASSWORD 								= 0x03; // â����
//0x04	
		public static final int C_OPCODE_WAR 											= 0x05; // ����
		public static final int C_OPCODE_BOOKMARKDELETE									= 0x06; // [/��� �� �����Ŭ�� delete]
		public static final int C_OPCODE_FIGHT 											= 0x07; // [/����]
		public static final int C_OPCODE_BOARDDELETE 									= 0x08; // �Խñ� ����
		public static final int C_OPCODE_BOOKMARK 										= 0x09; // [/��� OO]
//0x0A	
		public static final int C_OPCODE_TRADE 											= 0x0B; // [/��ȯ]
		public static final int C_OPCODE_BANCLAN 										= 0x0C; // ���� �߹� ��ɾ�
		public static final int C_OPCODE_NPCTALK 										= 0x0D; // Npc�� ��ȭ�κ�
//0x0E
		public static final int C_OPCODE_MOVECHAR 										= 0x0F; // �̵���û �κ�
		public static final int C_OPCODE_PARTY 											= 0x10; // [/��Ƽ]
		public static final int C_OPCODE_CHANGEHEADING 									= 0x11; // ���� ��ȯ �κ�
//0x12	
		public static final int C_OPCODE_CLIENTVERSION 									= 0x13; //v Ŭ�󿡼� ���� ���� ��û �ϴ� �κ�	
//0x14	
		public static final int C_OPCODE_CHATGLOBAL 									= 0x15; // ��üä��	
		public static final int C_OPCODE_BOARDWRITE 									= 0x16; // �Խ��� ����
		public static final int C_OPCODE_PLEDGE 										= 0x17; // [/����]
		public static final int C_OPCODE_EXCLUDE 										= 0x18; // [/����]
		public static final int C_OPCODE_CALL 											= 0x19; // CALL��ư .����
		public static final int C_OPCODE_BASERESET 										= 0x1A; // ���� �ʱ�ȭ
		public static final int C_OPCODE_SKILLBUY 										= 0x1B; // ��ų ����
//0x1C
//0x1D
		public static final int C_OPCODE_DROPITEM 										= 0x1E; // ������ ������
		public static final int C_OPCODE_BUDDYLIST 										= 0x1F; // ģ������Ʈ
//0x20	
		public static final int C_OPCODE_LEAVECLANE 									= 0x21; // ���� Ż��
		public static final int C_OPCODE_TITLE 											= 0x22; // ȣĪ ��ɾ�
		public static final int C_OPCODE_TRADEADDOK 									= 0x23; // ��ȯ OK
		public static final int C_OPCODE_RETURNTOLOGIN 									= 0x24; // �ٽ� �α�â���� �Ѿ�� 
		public static final int C_OPCODE_WHO 											= 0x25; // [/����]
		public static final int C_OPCODE_PROPOSE 										= 0x26; // [/ûȥ]
		public static final int C_OPCODE_WARTIMELIST 									= 0x27; // ���� �ð� ���� ����Ʈ
		public static final int C_OPCODE_SELECTLIST 									= 0x28; // �긮��Ʈ���� ��ã��
		public static final int C_OPCODE_BOARD 											= 0x29; // �Խ��� �б�
		public static final int C_OPCODE_USEPETITEM 									= 0x2A; // �� �κ��丮 ������ ���
		public static final int C_OPCODE_CHARACTERCONFIG 								= 0x2B; // ĳ���κ���������
		public static final int C_OPCODE_SHOP_N_WAREHOUSE 								= 0x2C; // ���� ��� ó��
		public static final int C_OPCODE_AMOUNT 										= 0x2D; // ������ ������ ���� ����
		public static final int C_OPCODE_HORUNOK										= 0x2E; // ȣ�� ��������
		public static final int C_OPCODE_ENTERPORTAL 									= 0x2F; // ������ ��ư���� ��Ż ����
		public static final int C_OPCODE_SOLDIERGIVE									= 0x30; // �뺴 ����
		public static final int C_OPCODE_TRADEADDCANCEL									= 0x31; // ��ȯ ���
		public static final int C_OPCODE_CLAN 											= 0x32; // ���ù����� ���� ��ũ ��û[������ emblem����]
//0x33
		public static final int C_OPCODE_SHIP											= 0x34; // ��Ÿ�� ������ ����	
		public static final int C_OPCODE_TAXRATE 										= 0x35; // ���� ����
//0x36
		public static final int C_OPCODE_DRAWAL											= 0x3A; // ���� ���[�ڱ��� �����Ѵ�]
		public static final int C_OPCODE_USESKILL 										= 0x3B; // ��ų ��� �κ�
//0x3C
		public static final int C_OPCODE_DOOR 											= 0x3D; // ��¦ Ŭ�� �κ�
//0x3E
//0x3F
		public static final int C_OPCODE_JOINCLAN 										= 0x40; // [/����]
//0x41
		public static final int C_OPCODE_SECURITYSTATUS 								= 0x42; // ���� ġ�� ����[���� ġ���� �����Ѵ�]
		public static final int C_OPCODE_QUITGAME 										= 0x43; // v�α���â���� �� �����Ҷ�	
		public static final int C_OPCODE_FIX_WEAPON_LIST 								= 0x44; // �������
		public static final int C_OPCODE_RANK 											= 0x45; // [/����]
		public static final int C_OPCODE_USEITEM 										= 0x46; // ������ ��� �κ�
//0x47
		public static final int C_OPCODE_TRADEADDITEM 									= 0x48; // ��ȯâ�� ������ �߰�
		public static final int C_OPCODE_RESTART_AFTER_DIE 								= 0x49; // ���߿� �׾ ���� ��������.
		public static final int C_OPCODE_PICKUPITEM 									= 0x4A; // ������ �ݱ�
		public static final int C_OPCODE_ATTR 											= 0x4B; // [ Y , N ] ���� �κ�
		public static final int C_OPCODE_WARTIMESET										= 0x4C; // �����ð� ����
		public static final int C_OPCODE_SELECT_CHARACTER 								= 0x4D; // ����â���� �ɸ� ����
//0x4E
		public static final int C_OPCODE_ARROWATTACK 									= 0x4F; // Ȱ���� �κ�
		public static final int C_OPCODE_SECURITYSTATUSSET 								= 0x50; // ���� ġ�� ���� �����ϱ�
		public static final int C_OPCODE_EXTCOMMAND 									= 0x51; // <��Ʈ+1 ~ 5 ���� �׼� >
//0x52
		public static final int C_OPCODE_ADDBUDDY 										= 0x53; // ģ���߰�
		public static final int C_OPCODE_SOLDIERGIVEOK									= 0x54; // �뺴 �ֱ����
		public static final int C_OPCODE_RESTART 										= 0x55; // ���߿� ����â���� ����	
		public static final int C_OPCODE_ATTACK											= 0x56; // �Ϲݰ��� �κ�	
		public static final int C_OPCODE_SHOP 											= 0x57; // [/���� -> OK]
//0x58
		public static final int C_OPCODE_HORUN 											= 0x59; // ȣ�� Ŭ��
		public static final int C_OPCODE_CHECKPK 										= 0x5A; // [/checkpk]
//0x5B
//0x5C
		public static final int C_OPCODE_BOARDBACK 										= 0x5D; // �Խ��� next
		public static final int C_OPCODE_SELECTTARGET 									= 0x5E; // �� ���� ��ǥ ����
		public static final int C_OPCODE_CREATE_CHARACTER 								= 0x5F; // �ɸ� ����	
//0x60
		public static final int C_OPCODE_EMBLEM											= 0x61; // ���嵥��Ÿ�� ������ ��û��
		public static final int C_OPCODE_EXIT_GHOST 									= 0x62; // ���Ѵ��� ������� Ż��
//0x63
		public static final int C_OPCODE_CHATWHISPER 									= 0x64; // �Ӽ� ä��
		public static final int C_OPCODE_KEEPALIVE 										= 0x65; // 1�и��� �ѹ��� ��
		public static final int C_OPCODE_REPORT											= 0x66; // �ҷ� ���� �Ű�(/�Ű�)
		public static final int C_OPCODE_PRIVATESHOPLIST 								= 0x67; // ���λ��� buy, sell
		public static final int C_OPCODE_BANPARTY 										= 0x68; // ��Ƽ �߹�
//0x69
		public static final int C_OPCODE_TELEPORT										= 0x6A; // �ڷ���Ʈ ���	
		public static final int C_OPCODE_CREATECLAN 									= 0x6B; // ���� â��
//0x6C
		public static final int C_OPCODE_CHAT 											= 0x6D; // �Ϲ� ä��	
		public static final int C_OPCODE_FISHCLICK 										= 0x6E; // ���� ���� Ŭ��
		public static final int C_OPCODE_LOGINTOSERVEROK 								= 0x6F; // [ȯ�漳��->��ê��,��]	
//0x70
//0x71
		public static final int C_OPCODE_DELETECHAR 									= 0x72; // �ɸ��� ����
		public static final int C_OPCODE_LEAVEPARTY 									= 0x73; // ��Ƽ Ż��
		public static final int C_OPCODE_GIVEITEM 										= 0x74; // ������ ������ �ֱ�
//0x75
		public static final int C_OPCODE_DEPOSIT 										= 0x76; // �� ���� �Ա�
		public static final int C_OPCODE_BOARDREAD 										= 0x77; // �Խ��� �б�
		public static final int C_OPCODE_DELBUDDY 										= 0x78; // ģ������
		public static final int C_OPCODE_LOGINPACKET 									= 0x79; //v ���������� ��� ��Ŷ.
		public static final int C_OPCODE_MAIL 											= 0x7A; // ������ Ŭ���� �������� �Դٰ���
		public static final int C_OPCODE_DELETEINVENTORYITEM 							= 0x7B; // �����뿡 ������ ����
//0x7C
		public static final int C_OPCODE_SKILLBUYOK 									= 0x7D; // ��ų ���� OK	
//0x7E
		public static final int C_OPCODE_NPCACTION 										= 0x7F; // Npc ��ȭ �׼� �κ�
//0x80
//0x81
		public static final int C_OPCODE_CREATEPARTY 									= 0x82; // ��Ƽ �ʴ�
		public static final int C_OPCODE_CHATPARTY 										= 0x83; // ä�� ��Ƽ ����Ʈ


	

		/** Client �Ⱦ��� �͵� */
			
		public static final int C_OPCODE_HOTEL_ENTER 									= 0x1001; // ���Ǿ��� ���� ���Խ� 	
		
				
		
		
		/** * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * **/
		/**	* * * * * * * * * * * * * * * * Server Packet * * * * * * * * * * * * * * * * * * * * * * **/
		/** * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * **/
	

		
		public static final int S_OPCODE_EMBLEM											= 0x00; // Ŭ�� ������ ��û
//0x01 ��
		public static final int S_OPCODE_PARALYSIS 										= 0x02; // �ൿ ���� (Ŀ���з� ����)
		public static final int S_OPCODE_ADDITEM 										= 0x03; // ������ ����[������ ���ɴٰ��Ա�]	
		public static final int S_OPCODE_INVLIST 										= 0x04; // �κ��丮�� �����۸���Ʈ
//0x05
		public static final int S_OPCODE_INPUTAMOUNT 									= 0x06; // ������ ������ ���� ����
		public static final int S_OPCODE_TRUETARGET 									= 0x07; // Ʈ��Ÿ��
//0x08 ��
		public static final int S_OPCODE_MPUPDATE 										= 0x09; // MP ������Ʈ
//0x0A
//0x0B ��
		public static final int S_OPCODE_INVIS 											= 0x0C; // ���� ó�� �κ�
		public static final int S_OPCODE_DELETEINVENTORYITEM 							= 0x0D; // �κ��丮 ������ ����
		public static final int S_OPCODE_OWNCHARATTRDEF									= 0x0E; // AC �� �Ӽ���� ����
		public static final int S_OPCODE_SERVERMSG 										= 0x0F; // ���� �޼���[���ߺ�����üũ]
//0x10 ��
//0x11
		public static final int S_OPCODE_YES_NO											= 0x12; // [ Y , N ] �޼���
		public static final int S_OPCODE_IDENTIFYDESC 									= 0x13; // Ȯ���ֹ���
		public static final int S_OPCODE_MSG 											= 0x14; // �ý��� �޼��� (��ê)
		public static final int S_OPCODE_DOACTIONGFX 									= 0x15; // �׼� �κ�(�´¸����)
//0x16 ��
		public static final int S_OPCODE_HORUN		 									= 0x17; // ȣ��
//0x18 ��
		public static final int S_OPCODE_SOLDIERBUYLIST									= 0x19; // �� �뺴 ���� ����Ʈâ
		public static final int S_OPCODE_DEPOSIT 										= 0x1A; // ���� �Ա�
		public static final int S_OPCODE_NOTICE											= 0x1B; // ����
		public static final int S_OPCODE_SOLDIERGIVE									= 0x1C; // ������ �뺴 �ֱ�
		public static final int S_OPCODE_SHOWSHOPBUYLIST 								= 0x1D; // ���� ���� �κ�
		public static final int S_OPCODE_BOARD 											= 0x1E; // �Խ���
		public static final int S_OPCODE_TRADESTATUS 									= 0x1F; // �ŷ� ���, �Ϸ�
		public static final int S_OPCODE_REMOVE_OBJECT 									= 0x20; // ������Ʈ ���� (���etc)
		public static final int S_OPCODE_SHOWHTML 										= 0x21; // NpcŬ�� Html����
		public static final int S_OPCODE_CHANGEHEADING 									= 0x22; // ���� ��ȯ �κ�
		public static final int S_OPCODE_SKILLICONSHIELD 								= 0x23; // ����
//0x24 ��
		public static final int S_OPCODE_WAR 											= 0x25; // ����
		public static final int S_OPCODE_LOGINRESULT 									= 0x26; // �α��� ó���Ǵ��� �亯
		public static final int S_OPCODE_LETTER											= 0x27; // ���� �б�
		public static final int S_OPCODE_HOUSEMAP 										= 0x28; // ����Ʈ ��
		public static final int S_OPCODE_ITEMCOLOR 										= 0x29; // ���� �ֹ���
		public static final int S_OPCODE_SHORTOFMATERIAL								= 0x2A; // ���������� ���� �̽���
		public static final int S_OPCODE_WHISPERCHAT 									= 0x2B; // �ӼӸ�
		public static final int S_OPCODE_BLUEMESSAGE									= 0x2C; // ������ Ƚ�� �޽���[REDMESSAGE]		
//0x2D
		public static final int S_OPCODE_STRUP 											= 0x2E; // ���� ����
		public static final int S_OPCODE_SKILLBRAVE 									= 0x2F; // ���
//0x30 ��
		public static final int S_OPCODE_WEATHER 										= 0x31; // ���� �����ϱ�
		public static final int S_OPCODE_RESURRECTION									= 0x32; // ��Ȱ ó�� �κ�
		public static final int S_OPCODE_DELSKILL 										= 0x33; // ��ų ���� (���ɷ� ����)
		public static final int S_OPCODE_SHOWOBJ 										= 0x34; // ������Ʈ �׸���
		public static final int S_OPCODE_DISCONNECT										= 0x35; // �ش� �ɸ� ���� ����
		public static final int S_OPCODE_SKILLSOUNDGFX 									= 0x36; // ����Ʈ �κ� (���̽�Ʈ��)
//0x37 ��
		public static final int S_OPCODE_SOUND 											= 0x38; // ���� ����Ʈ �κ�
		public static final int S_OPCODE_SKILLHASTE 									= 0x39; // ���̽�Ʈ
		public static final int S_OPCODE_ABILITY 										= 0x3A; // �̹�, �ҹ�  ������ ���
//0x3B ��?
		public static final int S_OPCODE_SERVERVERSION 									= 0x3C; //v ���� ����
		public static final int S_OPCODE_NEWCHARPACK 									= 0x3D; // �ɸ� ���� ����� ������
		public static final int S_OPCODE_NPCSHOUT 										= 0x3E; // ������ ��
		public static final int S_OPCODE_TRADEADDITEM 									= 0x3F; // �ŷ�â ������ �߰� �κ�
		public static final int S_OPCODE_GAMETIME 										= 0x40; // ���� �ð�
		public static final int S_OPCODE_CHANGENAME 									= 0x41; // ������Ʈ ���Ӻ����
		public static final int S_OPCODE_USEMAP											= 0x42; // �������	
//0x43 ��
		public static final int S_OPCODE_SELECTLIST 									= 0x44; // �������
		public static final int S_OPCODE_ADDSKILL 										= 0x45; // ��ų �߰�[������Ŷ�ڽ� ����]
		public static final int S_OPCODE_CURSEBLIND 									= 0x46; // ���ֱ� ȿ��
		public static final int S_OPCODE_PRIVATESHOPLIST								= 0x47; // ���λ��� ��ǰ ����
		public static final int S_OPCODE_SKILLBUY 										= 0x48; // ��ų ���� â
		public static final int S_OPCODE_PACKETBOX 										= 0x49; //v ���� ��Ŷ ���� ���
		public static final int S_OPCODE_DRAWAL											= 0x4A; // ���� ���
		public static final int S_OPCODE_SOLDIERGIVELIST								= 0x4B; // �뺴 �ִ� ���� ����Ʈ
		public static final int S_OPCODE_EXP 											= 0x4C; // ����ġ ����
		public static final int S_OPCODE_CHARAMOUNT 									= 0x4D; //v �ش� ������ �ɸ� ����
		public static final int S_OPCODE_LIQUOR											= 0x4E; // ��
		public static final int S_OPCODE_DRAGONPERL										= 0x4E; // �巡������
		public static final int S_OPCODE_SHOWSHOPSELLLIST 								= 0x4F; // ������ �Ǹ� �κ�
		public static final int S_OPCODE_PETGUI 										= 0x50; //v ���� �ʱ�ȭ ����
		public static final int S_OPCODE_RETURNEDSTAT 									= 0x50; //v ���� �ʱ�ȭ ����
		public static final int S_OPCODE_CHARLIST 										= 0x51; //v �ɸ��͸���Ʈ�� �ɸ�����
		public static final int S_OPCODE_HPMETER 										= 0x52; // �̴� HPǥ�� �κ�
		public static final int S_OPCODE_TAXRATE 										= 0x53; // ���� ����
		public static final int S_OPCODE_SPMR 											= 0x54; // sp�� mr����/
		public static final int S_OPCODE_BOARDREAD 										= 0x55; // �Խ��� �б�
		public static final int S_OPCODE_POISON											= 0x56; // ���� ���� ���� ǥ��
		public static final int S_OPCODE_LIGHT 											= 0x57; // ���
//0x58 ����
//0x59 ����â����
		public static final int S_OPCODE_OWNCHARSTATUS2 								= 0x5A; // �������ͽ� ����(��ũ����,����)
		public static final int S_OPCODE_MAPID 											= 0x5B; // �� ���̵�
		public static final int S_OPCODE_MOVEOBJECT 									= 0x5C; // �̵� ������Ʈ
//0x5D ��
		public static final int S_OPCODE_ATTACKPACKET 									= 0x5E; // ���� ǥ�� �κ�
//0x5F ��
		public static final int S_OPCODE_HOUSELIST 										= 0x60; // ����Ʈ ����Ʈ
		public static final int S_OPCODE_ITEMNAME 										= 0x61; // ������ ���� (Eǥ��)
		public static final int S_OPCODE_TRADE 											= 0x62; // �ŷ�â �κ�
		public static final int S_OPCODE_PINKNAME										= 0x63; // ������
		public static final int S_OPCODE_DETELECHAROK 									= 0x64; // �ɸ� ����
		public static final int S_OPCODE_NEWCHARWRONG 									= 0x65; // ĳ���� ������ ó���κ�
		public static final int S_OPCODE_DEXUP 											= 0x66; // ����
//0x67
//0x68
		public static final int S_OPCODE_CASTLEMASTER 									= 0x69; // ��������� ����
		public static final int S_OPCODE_RANGESKILLS 									= 0x6A; // ���� �������� ��ų
//0x6B
		public static final int S_OPCODE_BOOKMARKS 										= 0x6C; // ��� ����Ʈ
		public static final int S_OPCODE_LAWFUL											= 0x6D; // ���Ǯ
		public static final int S_OPCODE_EFFECTLOCATION									= 0x6E; // Ʈ�� (��ǥ�� ����Ʈ)
		public static final int S_OPCODE_BLESSOFEVA 									= 0x6F; // ���� ������
//0x70
		public static final int S_OPCODE_CHARTITLE 										= 0x71; // ȣĪ ����
		public static final int S_OPCODE_REFRESH_CLAN 									= 0x71; // ������ ������ ������ ���� ��Ŷ
		public static final int S_OPCODE_OWNCHARSTATUS 									= 0x72; // �ɸ� ���� ����
		public static final int S_OPCODE_ITEMSTATUS 									= 0x73; // �κ� ������ ����
		public static final int S_OPCODE_ITEMAMOUNT 									= 0x73; // �κ��� ������ ���� ���� �ٲٱ�(��ܾ��� ITEMSTATUS�� ���̿�)
		public static final int S_OPCODE_WARTIME										= 0x74; // ���� �ð� ����
		public static final int S_OPCODE_UNKNOWN1 										= 0x75; // ���Ӵ��
		public static final int S_OPCODE_POLY 											= 0x76; // ����
//0x77
		public static final int S_OPCODE_NORMALCHAT 									= 0x78; // �Ϲ� ä��
		public static final int S_OPCODE_CHARVISUALUPDATE 								= 0x79; // ���� ��,Ż �κ�
		public static final int S_OPCODE_ATTRIBUTE 										= 0x7A; // ��ġ���� �̵�����&�Ұ��� ���� �κ�
		public static final int S_OPCODE_SHOWRETRIEVELIST 								= 0x7B; // â�� ����Ʈ
//0x7C
		public static final int S_OPCODE_HPUPDATE 										= 0x7D; // HP ������Ʈ
		public static final int S_OPCODE_SELECTTARGET 									= 0x7E; // �� ���� ��ǥ����
//0x7F

		
		
		/** Server Packet �Ⱦ��� �͵� **/
		
		public static final int S_OPCODE_ALLIANCECHAT 									= 0x1000; // ����ä��
		public static final int S_OPCODE_TELEPORT										= 0x1001; // �ڷ���Ʈ
		public static final int S_OPCODE_HOTELENTER										= 0x1002; // ���Ǿ��� ���� ���Խ�>>
		
		
		/** * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * **/
			
}