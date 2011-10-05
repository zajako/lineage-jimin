/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * evaSet.java
 *
 * Created on 2009. 8. 29, 오후 5:09:49
 */

package server.manager;

import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import bone.server.Config;

@SuppressWarnings("serial")
public class evaSet extends javax.swing.JFrame {
		
	/** Creates new form evaSet */
	public evaSet() {		
		initComponents();
		setLocation(200, 200);
		setVisible(true);
		
	}
	
	private void initComponents() {

		label1 = new java.awt.Label();
		textMaxUser = new java.awt.TextField();
		label2 = new java.awt.Label();
		textChatLevel = new java.awt.TextField();
		label3 = new java.awt.Label();
		textExp = new java.awt.TextField();
		label4 = new java.awt.Label();
		textAdena = new java.awt.TextField();
		label5 = new java.awt.Label();
		textItem = new java.awt.TextField();
		label6 = new java.awt.Label();
		textKarma = new java.awt.TextField();
		label7 = new java.awt.Label();
		textLawful = new java.awt.TextField();
		label8 = new java.awt.Label();
		textWeight = new java.awt.TextField();
		label9 = new java.awt.Label();
		textWeapon = new java.awt.TextField();
		label10 = new java.awt.Label();
		textArmor = new java.awt.TextField();		

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		label1.setText("최대유저");
		textMaxUser.setText(""+Config.MAX_ONLINE_USERS);
		label2.setText("채팅레벨");
		textChatLevel.setText(""+Config.GLOBAL_CHAT_LEVEL);
		label3.setText("경험치");
		textExp.setText(""+Config.RATE_XP);
		label4.setText("아데나");
		textAdena.setText(""+Config.RATE_DROP_ADENA);
		label5.setText("아이템");
		textItem.setText(""+Config.RATE_DROP_ITEMS);
		label6.setText("우호도");
		textKarma.setText(""+Config.RATE_KARMA);
		label7.setText("라우풀");
		textLawful.setText(""+Config.RATE_LAWFUL);
		label8.setText("무게");
		textWeight.setText(""+Config.RATE_WEIGHT_LIMIT);
		label9.setText("무기인챈");
		textWeapon.setText(""+Config.ENCHANT_CHANCE_WEAPON);
		label10.setText("아머인챈");
		textArmor.setText(""+Config.ENCHANT_CHANCE_ARMOR);
		buttonSetting = new java.awt.Button();
		buttonSetting.setLabel("세팅설정");		
		buttonSetting.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				buttonSettingClicked(evt);
			}
		});
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addContainerGap()
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(label5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(label7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(label9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
														.addComponent(textWeapon, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
														.addComponent(textLawful, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(textItem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(textExp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(textMaxUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
														.addGap(22, 22, 22)
														.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																.addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
																.addComponent(label8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
																.addComponent(label10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
																.addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
																.addComponent(label6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																		.addComponent(textKarma, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
																		.addComponent(textAdena, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
																		.addComponent(textWeight, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
																		.addComponent(textArmor, javax.swing.GroupLayout.PREFERRED_SIZE, 63, Short.MAX_VALUE)
																		.addComponent(textChatLevel, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)))
																		.addGroup(layout.createSequentialGroup()
																				.addGap(117, 117, 117)
																				.addComponent(buttonSetting, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
																				.addContainerGap())
		);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(textChatLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(textMaxUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(textAdena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(textExp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(label5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(label6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(textKarma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(textItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(label7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(label8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(textWeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(textLawful, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																.addComponent(label9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
																.addComponent(label10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
																.addComponent(textArmor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
																.addComponent(textWeapon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
																.addGap(19, 19, 19)
																.addComponent(buttonSetting, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
																.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);

		pack();
	}

	private void buttonSettingClicked(MouseEvent evt) {
		Config.GLOBAL_CHAT_LEVEL = Short.parseShort(textChatLevel.getText());
		Config.MAX_ONLINE_USERS = Short.parseShort(textMaxUser.getText());
		Config.RATE_XP = Double.parseDouble(textExp.getText());
		Config.RATE_DROP_ADENA = Double.parseDouble(textAdena.getText());
		Config.RATE_DROP_ITEMS = Double.parseDouble(textItem.getText());
		Config.RATE_KARMA = Double.parseDouble(textKarma.getText());
		Config.RATE_LAWFUL = Double.parseDouble(textLawful.getText());
		Config.RATE_WEIGHT_LIMIT = Double.parseDouble(textWeight.getText());
		Config.ENCHANT_CHANCE_WEAPON = Integer.parseInt(textWeapon.getText());
		Config.ENCHANT_CHANCE_ARMOR = Integer.parseInt(textArmor.getText());
		
		eva.LogServerAppend("[ServerSetting]", "확인하려면 설정 눌러보기");
		JOptionPane.showMessageDialog(this, "정상적으로 배율이 변경되었습니다.", " Server Message", javax.swing.JOptionPane.INFORMATION_MESSAGE);
		this.setVisible(false);
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private java.awt.Button buttonSetting;
	private java.awt.Label label1;
	private java.awt.Label label10;
	private java.awt.Label label2;
	private java.awt.Label label3;
	private java.awt.Label label4;
	private java.awt.Label label5;
	private java.awt.Label label6;
	private java.awt.Label label7;
	private java.awt.Label label8;
	private java.awt.Label label9;
	private java.awt.TextField textAdena;
	private java.awt.TextField textArmor;
	private java.awt.TextField textChatLevel;
	private java.awt.TextField textExp;
	private java.awt.TextField textItem;
	private java.awt.TextField textKarma;
	private java.awt.TextField textLawful;
	private java.awt.TextField textWeapon;
	private java.awt.TextField textWeight;
	private java.awt.TextField textMaxUser;
	// End of variables declaration//GEN-END:variables

}
