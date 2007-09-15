/*
 * ContactPanel.java
 *
 * Created on 26. červenec 2007, 11:50
 */

package esmska;

import javax.swing.DefaultComboBoxModel;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;
import operators.Operator;
import operators.OperatorEnum;
import persistence.Contact;

/**
 *
 * @author  ripper
 */
public class ContactPanel extends javax.swing.JPanel {
    MainFrame main;
    
    /** Creates new form ContactPanel */
    public ContactPanel() {
        this.main = MainFrame.getInstance();
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();
        nameTextField.requestFocusInWindow();
        numberTextField = new javax.swing.JTextField();
        operatorComboBox = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();

        jLabel1.setText("Jm\u00e9no");

        jLabel2.setText("\u010c\u00edslo");

        jLabel3.setText("Oper\u00e1tor");

        numberTextField.setColumns(9);
        numberTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                numberTextFieldKeyReleased(evt);
            }
        });

        operatorComboBox.setModel(new DefaultComboBoxModel(OperatorEnum.getAsList().toArray()));
        operatorComboBox.setRenderer(main.operatorComboBox.getRenderer());

        jLabel4.setText("+420");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                    .addComponent(operatorComboBox, 0, 174, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numberTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(numberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(operatorComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    /** @returns if the form is valid */
    public boolean validateForm() {
        if (!FormChecker.checkContactName(nameTextField.getText())) {
            nameTextField.requestFocusInWindow();
            return false;
        }
        if (!FormChecker.checkSMSNumber(numberTextField.getText())) {
            numberTextField.requestFocusInWindow();
            return false;
        }
        return true;
    }
    
    private void numberTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_numberTextFieldKeyReleased
        //guess operator
        Operator op = OperatorEnum.getOperator(numberTextField.getText());
        if (op != null) {
            for (int i=0; i<operatorComboBox.getItemCount(); i++) {
                if (operatorComboBox.getItemAt(i).getClass().equals(op.getClass())) {
                    operatorComboBox.setSelectedIndex(i);
                    break;
                }
            }
        }
    }//GEN-LAST:event_numberTextFieldKeyReleased
    
    public void setContact(Contact contact) {
        if (contact == null) {
            nameTextField.setText(null);
            numberTextField.setText(null);
            operatorComboBox.setSelectedItem(0);
        } else {
            nameTextField.setText(contact.getName());
            numberTextField.setText(contact.getNumber());
            operatorComboBox.setSelectedItem(contact.getOperator());
        }
    }
    
    public Contact getContact() {
        Contact c = new Contact();
        c.setName(nameTextField.getText());
        c.setNumber(numberTextField.getText());
        c.setOperator((Operator) operatorComboBox.getSelectedItem());
        c.setCountryCode("+420");
        return c;
    }
    
    public void prepareForShow() {
        nameTextField.requestFocusInWindow();
        nameTextField.selectAll();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JTextField numberTextField;
    private javax.swing.JComboBox operatorComboBox;
    // End of variables declaration//GEN-END:variables
    
}
