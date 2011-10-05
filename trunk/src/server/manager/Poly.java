package server.manager;

import java.awt.event.MouseEvent;

import bone.server.server.model.L1World;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.serverpackets.S_ChangeShape;

@SuppressWarnings("serial")
public class Poly extends javax.swing.JFrame {
		
	/** Creates new form Poly */
	public Poly(){
		initComponents();
		setLocation(200, 100);
		setVisible(true);
	}
	
	private void initComponents(){

		label1 = new java.awt.Label();
		닉네임 = new java.awt.TextField();
		label2 = new java.awt.Label();
		변신번호 = new java.awt.TextField();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("변신시키기");
		
		label1.setText("닉네임");
		닉네임.setText("");
		label2.setText("변신번호");
		변신번호.setText("");
		buttonPoly = new java.awt.Button();
		buttonPoly.setLabel("변신시키기");		
		buttonPoly.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {				
				buttonPolyClicked(evt);
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
												.addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
														.addComponent(닉네임, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE))
														.addGap(22, 22, 22)
														.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																.addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																		.addComponent(변신번호, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)))
																		.addGroup(layout.createSequentialGroup()
																				.addGap(117, 117, 117)
																				.addComponent(buttonPoly, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
																				.addContainerGap())
		);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(닉네임, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(변신번호, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(19, 19, 19)
										.addComponent(buttonPoly, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);

		pack();
	}

	private void buttonPolyClicked(MouseEvent evt) {
		
		String kname1 = 닉네임.getText();
		
		for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
			
			if(pc.getName().equalsIgnoreCase(kname1)){
				
				int Polyid = Integer.parseInt(변신번호.getText());
				pc.setTempCharGfxAtDead(Polyid);
				pc.sendPackets(new S_ChangeShape(pc.getId(), Polyid));
				//pc.broadcastPacket(this, new S_ChangeShape(pc.getId(), 0));
				pc.getInventory().takeoffEquip(Polyid);
				bone.LogServerAppend("변신 시켜 주었습니다..","확인바람");
			}else if(!pc.getName().equalsIgnoreCase(kname1)){
				
				bone.LogServerAppend("변신실패 했습니다...","확인바람");
			}
		}
	}

	@SuppressWarnings("unused")
	private static Poly getInstance() {
		// TODO Auto-generated method stub
		return null;
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private java.awt.Button buttonPoly;
	private java.awt.Label label1;
	private java.awt.Label label2;
	private java.awt.TextField 닉네임;
	private java.awt.TextField 변신번호;
	// End of variables declaration//GEN-END:variables

}
