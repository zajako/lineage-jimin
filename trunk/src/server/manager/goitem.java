package server.manager;

import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import bone.server.server.datatables.ItemTable;
import bone.server.server.model.L1Inventory;
import bone.server.server.model.L1World;
import bone.server.server.model.Instance.L1ItemInstance;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.serverpackets.S_SystemMessage;
import bone.server.server.templates.L1Item;

@SuppressWarnings("serial")
public class goitem extends javax.swing.JFrame {
  
 /** Creates new form goitem */
 public goitem() {  
  initComponents();
  setLocation(200, 100);
  setVisible(true);
   
 }
 
 private void initComponents() {

  label1 = new java.awt.Label();
  �����ֱ�г��� = new java.awt.TextField();
  label2 = new java.awt.Label();
  �����۹�ȣ = new java.awt.TextField();
  label3 = new java.awt.Label();
  ��þƮ���� = new java.awt.TextField();
  label4 = new java.awt.Label();
  �����۰��� = new java.awt.TextField();

  setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
  setTitle("�����ֱ�");
  
  label1.setText("�г���");
  �����ֱ�г���.setText("");
  label2.setText("�����۹�ȣ");
  �����۹�ȣ.setText("");
  label3.setText("��þƮ����");
  ��þƮ����.setText("");
  label4.setText("�����۰���");
  �����۰���.setText("");
  buttongoitem = new java.awt.Button();
  buttongoitem.setLabel("�����ֱ�");  
  buttongoitem.addMouseListener(new java.awt.event.MouseAdapter() {
   public void mouseClicked(java.awt.event.MouseEvent evt) {
    buttongoitemClicked(evt);
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
            .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
              .addComponent(�����ֱ�г���, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
              .addComponent(��þƮ����, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE))
              .addGap(22, 22, 22)
              .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addComponent(�����۹�ȣ, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                  .addComponent(�����۰���, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)))
                  .addGroup(layout.createSequentialGroup()
                    .addGap(117, 117, 117)
                    .addComponent(buttongoitem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap())
  );
  layout.setVerticalGroup(
    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
    .addGroup(layout.createSequentialGroup()
      .addContainerGap()
      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addComponent(�����ֱ�г���, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addComponent(�����۹�ȣ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(��þƮ����, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(�����۰���, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addComponent(buttongoitem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
  );

  pack();
 }

 private void buttongoitemClicked(MouseEvent evt) {
    String name2 = �����ֱ�г���.getText();
    
    int itemid = Integer.parseInt(�����۹�ȣ.getText());
   int enchant = Integer.parseInt(��þƮ����.getText());
   int count = Integer.parseInt(�����۰���.getText());
   L1Item temp = ItemTable.getInstance().getTemplate(itemid);
   
   for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
     if(pc.getName().equalsIgnoreCase(name2)){
       if (temp != null) {
         L1ItemInstance item = ItemTable.getInstance().createItem(itemid);
       item.setEnchantLevel(enchant);
       item.setCount(count);
       int createCount;
       if (pc.getInventory().checkAddItem(item, count) == L1Inventory.OK) {
         pc.getInventory().storeItem(item);
         pc.sendPackets(new S_SystemMessage(temp.getName()+ "��(��) ������ �־����ϴ�."));
         bone.LogServerAppend(temp.getName()+ "��(��) ������ �־����ϴ�.","Ȯ�ιٶ�");
       }
       else {
         L1ItemInstance item1 = null;
       for (createCount = 0; createCount < count; createCount++) {
         item1 = ItemTable.getInstance().createItem(itemid);
        item1.setEnchantLevel(enchant);
        if (pc.getInventory().checkAddItem(item, 1) == L1Inventory.OK) {
         pc.getInventory().storeItem(item);
        } 
        else break;
       }
       
       if (createCount > 0){
        pc.sendPackets(new S_SystemMessage(temp.getName()+ "(��)��" + createCount + "�� �����߽��ϴ�."));
        bone.LogServerAppend(temp.getName()+ "��(��)" + createCount + "�� �����߽��ϴ�.","Ȯ�ιٶ�");
       }
       }
       }
       else if (temp == null){
    	   bone.LogServerAppend("[�����ֱ� ����] �׷� �������� �������� �ʽ��ϴ�.","Ȯ�ιٶ�");
     }
     }
   }
  JOptionPane.showMessageDialog(this, name2+"���� "+temp.getName()+"  ����.", " Server Message", javax.swing.JOptionPane.INFORMATION_MESSAGE);
  this.setVisible(false);
 }

 @SuppressWarnings("unused")
 private static goitem getInstance() {
  // TODO Auto-generated method stub
  return null;
 }

 // Variables declaration - do not modify//GEN-BEGIN:variables
 private java.awt.Button buttongoitem;
 private java.awt.Label label1;
 private java.awt.Label label2;
 private java.awt.Label label3;
 private java.awt.Label label4;
 private java.awt.TextField �����ֱ�г���;
 private java.awt.TextField �����۰���;
 private java.awt.TextField �����۹�ȣ;
 private java.awt.TextField ��þƮ����;
 
 // End of variables declaration//GEN-END:variables

}