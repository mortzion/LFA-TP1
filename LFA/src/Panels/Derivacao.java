/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Panels;

import java.awt.Color;
import javax.swing.JPanel;

/**
 *
 * @author mortz
 */
public class Derivacao extends javax.swing.JPanel {

    private Gramatica pai;
    private int tamText1=0;
    private int tamText2=0;
    public Derivacao(Gramatica pai) {
        initComponents();
        this.pai = pai;
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField1 = new javax.swing.JTextField();

        setMaximumSize(new java.awt.Dimension(181, 20));

        jTextField2.setEditable(false);
        jTextField2.setText("->");
        jTextField2.setEnabled(false);
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jTextField3.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextField3CaretUpdate(evt);
            }
        });
        jTextField3.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                jTextField3CaretPositionChanged(evt);
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                jTextField3InputMethodTextChanged(evt);
            }
        });
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });
        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField3KeyTyped(evt);
            }
        });

        jTextField1.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextField1CaretUpdate(evt);
            }
        });
        jTextField1.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                jTextField1CaretPositionChanged(evt);
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                jTextField1InputMethodTextChanged(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField3CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextField3CaretUpdate
        test();
    }//GEN-LAST:event_jTextField3CaretUpdate

    private void jTextField1CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextField1CaretUpdate
        test();
    }//GEN-LAST:event_jTextField1CaretUpdate

    private void jTextField3InputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jTextField3InputMethodTextChanged
       
    }//GEN-LAST:event_jTextField3InputMethodTextChanged

    private void jTextField1InputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jTextField1InputMethodTextChanged
        
    }//GEN-LAST:event_jTextField1InputMethodTextChanged

    private void jTextField1CaretPositionChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jTextField1CaretPositionChanged

    }//GEN-LAST:event_jTextField1CaretPositionChanged

    private void jTextField3CaretPositionChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jTextField3CaretPositionChanged
    
    }//GEN-LAST:event_jTextField3CaretPositionChanged

    private void jTextField3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyTyped
      
    }//GEN-LAST:event_jTextField3KeyTyped

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
       
    }//GEN-LAST:event_jTextField1KeyTyped


    public char getNaoTerminal(){
        return jTextField1.getText().charAt(0);
    }
    
    public String getDerivacao(){
        return jTextField3.getText();
    }
    
    public boolean validar(){
        if(jTextField1.getText().length() != 1 || !Character.isUpperCase(jTextField1.getText().charAt(0)))return false;
        if(!jTextField1.getText().isEmpty()&& jTextField3.getText().matches("[a-z]*[A-Z]?[a-z]*"))return true;
        return false;
    }
    private void test(){
        if(jTextField1.getText().length()!=tamText1 || jTextField3.getText().length()!=tamText2){
            tamText1 = jTextField1.getText().length();
            tamText2 = jTextField3.getText().length();
            if(this.jTextField3.getText().matches("[a-z]*[A-Z]?[a-z]*") == false)jTextField3.setBackground(Color.red);
            else jTextField3.setBackground(Color.white);
            
            if(this.jTextField1.getText().matches("[A-Z]?") == false)jTextField1.setBackground(Color.red);
            else jTextField1.setBackground(Color.white);
            
            if(this.jTextField1.getText().isEmpty() && this.jTextField3.getText().isEmpty()){
                pai.removerDerivacao(this);
            }
            else{
                pai.criarDerivacao(this);
            }
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}
