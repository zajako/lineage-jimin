package server.manager;

import java.awt.TextArea;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import bone.server.ServerChat;
import bone.server.SpecialEventHandler;
import bone.server.server.Account;
import bone.server.server.datatables.DropItemTable;
import bone.server.server.datatables.DropTable;
import bone.server.server.datatables.IpTable;
import bone.server.server.datatables.MapFixKeyTable;
import bone.server.server.datatables.PolyTable;
import bone.server.server.datatables.ResolventTable;
import bone.server.server.model.L1World;
import bone.server.server.model.Instance.L1ItemInstance;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.model.item.L1TreasureBox;
import bone.server.server.model.skill.L1SkillId;
import bone.server.server.serverpackets.S_Disconnect;
import bone.server.server.serverpackets.S_ServerMessage;
import bone.server.server.serverpackets.S_SkillIconGFX;
import bone.server.server.utils.SystemUtil;

import server.GameServer;
import server.Server;
import server.system.autoshop.AutoShopManager;

/**
 * @author jimin
 */
@SuppressWarnings("serial")
public class bone extends javax.swing.JFrame {

	private static final Object lock = new Object();
	private static String date = "";
	private static String time = "";

	public static final String NoServerStartMSG 	= "������ ������� �ʾҽ��ϴ�.";
	public static final String realExitServer 		= "������ �����Ͻðڽ��ϱ�?";
	public static final String blankSetUser 		= "������ �������� �ʾҽ��ϴ�.";
	public static final String characterSaveFail	= "ĳ���� ���� ���� ����";
	public static final String characterSaveSuccess	= "ĳ���� ���� ���� ����";
	public static final String NoConnectUser 		= " ĳ���ʹ� ������ ���� �ʽ��ϴ�.";

	public static final String[] eventlist 			= {"��ü����","ä�ñ���", "ä��Ǯ��", "�������"};
	public static final String[] reloadlist 		= {"Drop","DropItem","Poly", "Resolvent", "TreasureBox","MapFixKey"};

	public static boolean isServerStarted;
	public static int userCount;
	/** Creates new form bone */
	public bone() {					

		initComponents();
		isServerStarted = true;
		this.setLocation(100, 100);
		Server.createServer().start();
	}

	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new bone().setVisible(true);
			}
		});
	}

	private void initComponents() {



		textSetUser = new java.awt.TextField();				
		buttonEvent = new java.awt.Button();
		jTabbedPane1 = new javax.swing.JTabbedPane();
		textServer = new java.awt.TextArea();
		textChat = new java.awt.TextArea();
		textWareHouse = new java.awt.TextArea();
		textTrade = new java.awt.TextArea();
		textEnchant = new java.awt.TextArea();
		textObserver = new java.awt.TextArea();
		textBug = new java.awt.TextArea();
		textCommand = new java.awt.TextArea();
		textChatUser = new java.awt.TextField();

		lblMemory = new java.awt.Label();

		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		setTitle("Bone Server Manager");

		comboEventList = new java.awt.Choice();
		for (int i=0,a=eventlist.length;i<a;i++){
			comboEventList.addItem(eventlist[i]);
		}
		buttonEvent = new java.awt.Button();
		buttonEvent.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				buttonEventClicked(evt);
			}
		});

		comboReloadList = new java.awt.Choice();
		for (int i=0,a=reloadlist.length;i<a;i++){
			comboReloadList.addItem(reloadlist[i]);
		}
		buttonReload = new java.awt.Button();
		buttonReload.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				buttonReloadClicked(evt);
			}
		});

		textChatSend = new java.awt.TextField();
		textChatSend.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				chatKeyPressed(evt);
			}		
		});	
		buttonSetUserNG = new java.awt.Button();
		buttonSetUserNG.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				buttonUserNGClicked(evt);
			}
		});
		buttonSetUserBAN = new java.awt.Button();
		buttonSetUserBAN.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				buttonUserBANClicked(evt);
			}
		});
		buttonServer = new java.awt.Button();		
		buttonServer.setLabel("��������");
		buttonServer.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				buttonServerClicked(evt);
			}
		});
		buttonSetting = new java.awt.Button();
		buttonSetting.setLabel("��������");
		buttonSetting.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				buttonSettingClicked(evt);
			}
		});
		buttonSave = new java.awt.Button();
		buttonSave.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				buttonSaveClicked(evt);
			}
		});
		buttongoitem = new java.awt.Button();
		buttongoitem.setLabel("�����ֱ�");
		buttongoitem.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				buttongoitemClicked(evt);
			}
		});
		buttonPoly = new java.awt.Button();
		buttonPoly.setLabel("���Ž�Ű��");
		buttonPoly.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				buttonPolyClicked(evt);
			}
		});

		buttonSave.setLabel("��������");
		buttonReload.setLabel("���ε����");
		buttonSetUserNG.setLabel("ä��(10)");
		buttonSetUserBAN.setLabel("���߰�");
		buttonEvent.setLabel("�̺�Ʈ����");

		jTabbedPane1.addTab("����", textServer);
		jTabbedPane1.addTab("ä��", textChat);
		jTabbedPane1.addTab("â��", textWareHouse);
		jTabbedPane1.addTab("�ŷ�", textTrade);
		jTabbedPane1.addTab("��æƮ", textEnchant);
		jTabbedPane1.addTab("����", textObserver);
		jTabbedPane1.addTab("����", textBug);
		jTabbedPane1.addTab("Ŀ�ǵ�", textCommand);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
								.addComponent(buttonEvent, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
								.addComponent(comboEventList, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
								.addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
										.addComponent(buttonSetting, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(buttonServer, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
										.addComponent(buttonSave, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
										.addComponent(buttongoitem, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
										.addComponent(buttonPoly, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
										.addComponent(comboReloadList, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
										.addComponent(buttonReload, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
										.addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
												.addComponent(buttonSetUserNG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(buttonSetUserBAN, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE))
												.addComponent(textSetUser, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(lblMemory, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(layout.createSequentialGroup()
																.addComponent(textChatUser, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(textChatSend, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE))
																.addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE))
																.addContainerGap())
		);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
								.addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
								.addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
										.addComponent(buttonServer, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(buttonSetting, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(buttonSave, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(buttongoitem, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(buttonPoly, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(44, 44, 44)
										.addComponent(comboEventList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(buttonEvent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(28, 28, 28)
										.addComponent(comboReloadList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(buttonReload, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
										.addComponent(textSetUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(buttonSetUserBAN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(buttonSetUserNG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(textChatUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(textChatSend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(lblMemory, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
														.addContainerGap())
		);
		pack();        
	}

	/***** �α� ���� �κ� ******/
	public static int userCount(int i){		
		userCount += i;
		return userCount;
	}
	public static void refreshMemory(){
		lblMemory.setText(" �޸� : "+SystemUtil.getUsedMemoryMB()+" MB");
	}	
	public static void LogServerAppend(String s, String s1){
		// �����α�â : 2s
		textServer.append("\n"+getLogTime() + "��"+s+"��"+s1);
	}
	public static void LogServerAppend(String s, L1PcInstance pc, String ip, int i){
		// �����α�â : s - ����,����
		textServer.append("\n" + getLogTime() + "��" + s + "��" 
				+ pc.getName() + ":"	+ pc.getAccountName() + "��" + ip 
				+ "��U:" + userCount(i));
	}
	public static void LogChatAppend(String s, String pcname, String text){
		// ����ä��â s: �Ϲ�(-), ��Ƽ(#), �׷�(*), ��ü(&)
		textChat.append("\n" + getLogTime() + "��" + s + "��" + pcname + "��:" + text);		
	}
	public static void LogChatAppend(String s, String pcname, String c, String text, String sel){
		// ����ä��â s: ����(@), �Ӹ�(>)
		textChat.append("\n" + getLogTime() + "��" + s + "��" + pcname + sel + c + "��:" + text);		
	}
	public static void LogWareHouseAppend(String s, String pcname, String clanname, L1ItemInstance item, int count, int obj){
		// â��α� : �ð� s 
		if (item.getItem().getType2() == 0 && item.getItem().getlogcheckitem() == 0) return;

		textWareHouse.append("\n" + getLogTime() +"��"+ s + "��" + pcname);
		if (!clanname.equalsIgnoreCase("")) textWareHouse.append("@" + clanname);
		textWareHouse.append("��");
		if (item.getEnchantLevel() > 0){textWareHouse.append("+" + item.getEnchantLevel());}
		else if (item.getEnchantLevel() <0){textWareHouse.append(""+item.getEnchantLevel());}		
		textWareHouse.append(item.getName()+"��B:"+item.getBless()+"��C:"+count+"��O:"+obj);
	}
	public static void LogTradeAppend(String s, String pcname, String targetname, int enchant, String itemname, int bless, int count, int obj){
		// ��ȯ �α�
		textTrade.append("\n" + getLogTime() +"��"+ s + "��" + pcname + "��" + targetname + "��");		
		if (enchant > 0){textTrade.append("+" + enchant);}
		else if (enchant <0){textTrade.append("-" + enchant);}		
		textTrade.append(itemname+"��B:"+bless+"��C:"+count+"��O:"+obj);
	}
	public static void LogShopAppend(String s, String pcname, String targetname, int enchant, String itemname, int bless, int count, int obj){
		// ���� �α� : �ð� ���� p t ������(�ŷ��Ƶ���)
		textTrade.append("\n" + getLogTime() +"��"+ s + "��" + pcname + "��" + targetname + "��");		
		if (enchant > 0){textTrade.append("+" + enchant);}
		else if (enchant <0){textTrade.append("-" + enchant);}		
		textTrade.append(itemname+"��B:"+bless+"��C:"+count+"��O:"+obj);
	}
	public static void LogEnchantAppend(String s, String pcname, String enchantlvl, String itemname, int obj){
		// ��æ �α�
		textEnchant.append("\n" + getLogTime() +"��"+ s + "��" + pcname + "��" + enchantlvl + "��" + itemname + "��O:" + obj);
	}
	public static void LogObserverAppend(String s, String pcname, L1ItemInstance item,int count, int obj){
		// ���÷α�
		textObserver.append("\n" + getLogTime() +"��"+ s + "��" + pcname);		
		textObserver.append("��");
		if (item.getEnchantLevel() > 0){textObserver.append("+" + item.getEnchantLevel());}
		else if (item.getEnchantLevel() <0){textObserver.append(""+item.getEnchantLevel());}		
		textObserver.append(item.getName()+"��C:"+count+"��O:"+obj);
	}
	public static void LogBugAppend(String s, L1PcInstance pc, int type){
		// ���׷α�
		textBug.append("\n"+getLogTime()+"��"+s+"��"+pc.getName());
		switch (type) {
		case 1:	// ����
			textBug.append("��P:"+pc.getGfxId().getTempCharGfx()+"��R:"+pc.getSpeedRightInterval()+"��>I:"+pc.getSpeedInterval());			
			break;
		case 2: // �վ�
			textBug.append("��x,y,map:"+pc.getLocation().getX()+","+pc.getLocation().getY()+","+pc.getLocation().getMapId());
			break;
		default:
			break;
		}

	}
	public static void LogCommandAppend(String pcname, String cmd, String arg){
		// Ŀ�ǵ� �α�
		textCommand.append("\n"+getLogTime()+"��"+pcname+"��C:"+cmd+" "+arg);
	}

	/***** �̺�Ʈ *****/
	private void buttonServerClicked(java.awt.event.MouseEvent evt) {
		// ���� ���� Click
		if (isServerStarted){
			if (QMsg(realExitServer) == 0){
				GameServer.getInstance().setServerExit(true);
				GameServer.getInstance().shutdownWithCountdown(30);}		
		}else{	errorMsg(NoServerStartMSG);}
	}
	private void buttonSettingClicked(java.awt.event.MouseEvent evt) {
		// ���� ���� Click
		if (isServerStarted) 	new boneSet();
		else 					errorMsg(NoServerStartMSG);
	}
	private void buttonSaveClicked(java.awt.event.MouseEvent evt) {
		if (isServerStarted){
			int count = GameServer.getInstance().saveAllCharInfo();
			if(count > -1){ // exception �߻��ϸ� -1 ���� 		
				L1World.getInstance().broadcastServerMessage("[Character Save Complete..]");
				infoMsg(characterSaveSuccess+"��C:"+count+"");
			}else{
				errorMsg(characterSaveFail);
			}
		}else{errorMsg(NoServerStartMSG);}
	}
	private void buttongoitemClicked(java.awt.event.MouseEvent evt) {
		// �����ֱ� Click
		if (isServerStarted) 	new goitem();
		else 					errorMsg(NoServerStartMSG);
	}
	private void buttonPolyClicked(java.awt.event.MouseEvent evt) {
		// ���Ž�Ű�� Click
		if (isServerStarted) 	new Poly();
		else 					errorMsg(NoServerStartMSG);
	}
	private void buttonEventClicked(java.awt.event.MouseEvent evt) {
		// �̺�Ʈ���� Click
		if (isServerStarted){
			if (comboEventList != null){
				if (comboEventList.getSelectedItem().equalsIgnoreCase("��ü����")){
					SpecialEventHandler.getInstance().doAllBuf();
				}
				else if (comboEventList.getSelectedItem().equalsIgnoreCase("���λ��� �ѱ�")){
					AutoShopManager.getInstance().isAutoShop(true);
				}
				else if (comboEventList.getSelectedItem().equalsIgnoreCase("���λ��� ����")){
					AutoShopManager.getInstance().isAutoShop(false);
				}
				else if (comboEventList.getSelectedItem().equalsIgnoreCase("ä�ñ���")){
					SpecialEventHandler.getInstance().doNotChatEveryone();
				}
				else if (comboEventList.getSelectedItem().equalsIgnoreCase("ä��Ǯ��")){
					SpecialEventHandler.getInstance().doChatEveryone();
				}
				else if (comboEventList.getSelectedItem().equalsIgnoreCase("�������")){
					SpecialEventHandler.getInstance().doBugRace();
				}
				textServer.append("\n"+getLogTime()+"��[ServerEvent]��" + comboEventList.getSelectedItem());
			}
		}else{	errorMsg(NoServerStartMSG);}
	}
	private void buttonReloadClicked(java.awt.event.MouseEvent evt) {
		// ���ε� Click
		if (isServerStarted){
			if (comboReloadList != null){
				if (comboReloadList.getSelectedItem().equalsIgnoreCase("Drop")){
					DropTable.reload();
				}
				else if(comboReloadList.getSelectedItem().equalsIgnoreCase("DropItem")){
					DropItemTable.reload();
				}
				else if(comboReloadList.getSelectedItem().equalsIgnoreCase("Poly")){
					PolyTable.reload();
				}
				else if(comboReloadList.getSelectedItem().equalsIgnoreCase("Resolvent")){
					ResolventTable.reload();
				}
				else if(comboReloadList.getSelectedItem().equalsIgnoreCase("TreasureBox")){
					L1TreasureBox.load();
				}
				else if(comboReloadList.getSelectedItem().equalsIgnoreCase("MapFixKey")){
					MapFixKeyTable.reload();
				}
				textServer.append("\n"+getLogTime()+"��[ServerReload]��" + comboReloadList.getSelectedItem());
			}
		}else{	errorMsg(NoServerStartMSG);}
	}
	private void buttonUserNGClicked(java.awt.event.MouseEvent evt) {
		// ä�� (�⺻10��)
		if (isServerStarted){
			if (!textSetUser.getText().equalsIgnoreCase("")){
				L1PcInstance pc = L1World.getInstance().getPlayer(textSetUser.getText());
				if (pc != null){
					pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.STATUS_CHAT_PROHIBITED, 10 * 60 * 1000);
					pc.sendPackets(new S_SkillIconGFX(36, 10 * 60));
					pc.sendPackets(new S_ServerMessage(286, String.valueOf(10))); // \f3���ӿ� �������� �ʴ� �ൿ�̱� (����)������, ����%0�а� ä���� �����մϴ�.
					LogCommandAppend("�����", "ä��", "10");
					textSetUser.setText("");
				}else{errorMsg(textSetUser.getText()+NoConnectUser);}
			}else{errorMsg(blankSetUser);}
		}else{errorMsg(NoServerStartMSG);}
	}
	private void buttonUserBANClicked(java.awt.event.MouseEvent evt) {
		// ��
		if (isServerStarted){
			if (!textSetUser.getText().equalsIgnoreCase("")){
				L1PcInstance pc = L1World.getInstance().getPlayer(textSetUser.getText());
				IpTable iptable = IpTable.getInstance();
				if (pc != null){
					Account.ban(pc.getAccountName());	// ������ BAN��Ų��.
					iptable.banIp(pc.getNetConnection().getIp()); // BAN ����Ʈ�� IP�� �߰��Ѵ�.					
					pc.sendPackets(new S_Disconnect());
					LogCommandAppend("�����", "�����߹�", pc.getName());
					textSetUser.setText("");
				}else{errorMsg(textSetUser.getText()+NoConnectUser);}
			}else{errorMsg(blankSetUser);}
		}else{errorMsg(NoServerStartMSG);}
	}
	private void chatKeyPressed(KeyEvent evt) {
		// ���� ä��
		if (isServerStarted){
			if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
				// Ư�������� ���õǾ� ���� �ʴٸ�			
				if (textChatUser.getText().equalsIgnoreCase("")){
					ServerChat.getInstance().sendChatToAll("[�����] "+textChatSend.getText());
					LogChatAppend("��", "�����", textChatSend.getText());
				}else{
					boolean result = ServerChat.getInstance().whisperToPlayer(textChatUser.getText(), textChatSend.getText());
					if (result)
						LogChatAppend("��", "�����",textChatUser.getText(), textChatSend.getText(), ">");
					else
						errorMsg(textChatUser.getText()+NoConnectUser);
				}
				textChatSend.setText("");
			}			
		}else{errorMsg(NoServerStartMSG);}
	}

	/***** �޼��� ó�� �κ� *****/
	private int QMsg(String s){			// Question Window
		int result = JOptionPane.showConfirmDialog(this, s,"Server Message", 2 ,JOptionPane.INFORMATION_MESSAGE);
		return result;
	}
	private void infoMsg(String s)	{	// Message Window(INFOMATION)
		JOptionPane.showMessageDialog(this, s, "Server Message", javax.swing.JOptionPane.INFORMATION_MESSAGE);
	}
	private void errorMsg(String s) {	// Message Window(ERROR)
		JOptionPane.showMessageDialog(this, s, "Server Message", javax.swing.JOptionPane.ERROR_MESSAGE);
	}

	/***** ���� �ð� �������� *****/
	private static String getLogTime(){
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd HH:mm:ss"); 
		String time = dateFormat.format(currentDate.getTime());
		return time;
	}
	/***** �α� ���� *****/
	public static void savelog(){
		File f = null;
		String sTemp = "";
		synchronized(lock){
			sTemp = getDate();
			StringTokenizer s = new StringTokenizer(sTemp, " ");
			date = s.nextToken();
			time = s.nextToken();
			f = new File("ServerLog/"+date);
			if(!f.exists()) f.mkdir();
			flush(textServer, "[" + time + "] 1����", date);
			flush(textChat, "[" + time + "] 2ä��", date);
			flush(textBug, "[" + time + "] 3����", date);
			flush(textTrade, "[" + time + "] 4�ŷ�", date);
			flush(textWareHouse, "[" + time + "] 5â��", date);
			flush(textObserver, "[" + time + "] 6����", date);
			flush(textEnchant, "[" + time + "] 7��æƮ", date);			
			flush(textCommand, "[" + time + "] 8Ŀ�ǵ�", date);
			sTemp = null;
			date = null;
			time = null;
		}
	}

	public static void flush(TextArea text, String FileName, String date){
		try{
			RandomAccessFile rnd = new RandomAccessFile("ServerLog/"+ date + "/" + FileName + ".txt", "rw");
			rnd.write(text.getText().getBytes());
			rnd.close();
		}catch(Exception e){}
	}

	// ��¥����(yyyy-MM-dd) �ð�(hh-mm)
	private static String getDate(){
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd hh-ss", Locale.KOREA);
		return s.format(Calendar.getInstance().getTime());
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private java.awt.Button buttonEvent;
	private java.awt.Button buttonReload;
	private java.awt.Button buttonSave;
	private java.awt.Button buttonServer;
	private java.awt.Button buttonSetUserBAN;
	private java.awt.Button buttonSetUserNG;
	private java.awt.Button buttonSetting;
	private java.awt.Button buttongoitem;
	private java.awt.Button buttonPoly;
	public static java.awt.Label lblMemory;
	private java.awt.Choice comboEventList;
	private java.awt.Choice comboReloadList;
	private javax.swing.JTabbedPane jTabbedPane1;
	public static java.awt.TextArea textBug;
	public static java.awt.TextArea textChat;
	public static java.awt.TextField textChatSend;
	public static java.awt.TextField textChatUser;
	public static java.awt.TextArea textCommand;
	public static java.awt.TextArea textEnchant;
	public static java.awt.TextArea textObserver;
	public static java.awt.TextArea textServer;
	public static java.awt.TextField textSetUser;
	public static java.awt.TextArea textTrade;
	public static java.awt.TextArea textWareHouse;
	// End of variables declaration//GEN-END:variables

}
