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
  선물주기닉네임 = new java.awt.TextField();
  label2 = new java.awt.Label();
  아이템번호 = new java.awt.TextField();
  label3 = new java.awt.Label();
  인첸트레벨 = new java.awt.TextField();
  label4 = new java.awt.Label();
  아이템갯수 = new java.awt.TextField();

  setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
  setTitle("선물주기");
  
  label1.setText("닉네임");
  선물주기닉네임.setText("");
  label2.setText("아이템번호");
  아이템번호.setText("");
  label3.setText("인첸트레벨");
  인첸트레벨.setText("");
  label4.setText("아이템갯수");
  아이템갯수.setText("");
  buttongoitem = new java.awt.Button();
  buttongoitem.setLabel("선물주기");  
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
              .addComponent(선물주기닉네임, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
              .addComponent(인첸트레벨, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE))
              .addGap(22, 22, 22)
              .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addComponent(아이템번호, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                  .addComponent(아이템갯수, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)))
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
        .addComponent(선물주기닉네임, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addComponent(아이템번호, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(인첸트레벨, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(아이템갯수, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addComponent(buttongoitem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
  );

  pack();
 }

 private void buttongoitemClicked(MouseEvent evt) {
    String name2 = 선물주기닉네임.getText();
    
    int itemid = Integer.parseInt(아이템번호.getText());
   int enchant = Integer.parseInt(인첸트레벨.getText());
   int count = Integer.parseInt(아이템갯수.getText());
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
         pc.sendPackets(new S_SystemMessage(temp.getName()+ "을(를) 생성해 주었습니다."));
         bone.LogServerAppend(temp.getName()+ "을(를) 생성해 주었습니다.","확인바람");
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
        pc.sendPackets(new S_SystemMessage(temp.getName()+ "(을)를" + createCount + "개 생성했습니다."));
        bone.LogServerAppend(temp.getName()+ "을(를)" + createCount + "개 생성했습니다.","확인바람");
       }
       }
       }
       else if (temp == null){
    	   bone.LogServerAppend("[선물주기 실패] 그런 아이템은 존재하지 않습니다.","확인바람");
     }
     }
   }
  JOptionPane.showMessageDialog(this, name2+"에게 "+temp.getName()+"  선물.", " Server Message", javax.swing.JOptionPane.INFORMATION_MESSAGE);
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
 private java.awt.TextField 선물주기닉네임;
 private java.awt.TextField 아이템갯수;
 private java.awt.TextField 아이템번호;
 private java.awt.TextField 인첸트레벨;
 
 // End of variables declaration//GEN-END:variables

}