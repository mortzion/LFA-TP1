/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Panels;

import Core.ReconhecedorCadeia;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;

/**
 *
 * @author mortz
 */
public class MultiplasEntradas extends javax.swing.JDialog {

    private ArrayList<PanelTextField> entradas;
    private ReconhecedorCadeia reconhecedor;
    private boolean caseSensitive;
    private boolean possuiSaida;

    public MultiplasEntradas(java.awt.Frame parent, boolean modal, ReconhecedorCadeia reconhecedor,boolean caseSensitive,boolean possuiSaida) {
        super(parent, modal);
        initComponents();
        jPanel1.setLayout(new GridLayout(0,1));
        entradas = new ArrayList<PanelTextField>();
        entradas.add(new PanelTextField(this));
        jPanel1.add(entradas.get(0));
        jScrollPane1.revalidate();
        jScrollPane1.repaint();
        this.reconhecedor = reconhecedor;
        this.caseSensitive = caseSensitive;
        this.possuiSaida = possuiSaida;
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Multiplas entradas");

        jButton1.setText("Verificar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cancelar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 378, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 266, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jPanel1);

        jLabel1.setText("Entrada");

        jLabel2.setText("Saida");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(120, 120, 120)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        boolean erro = false;
        for(PanelTextField ptf : entradas){
            if(caseSensitive == true){
                String cadeia = ptf.getCadeia();
                if(!cadeia.equals(cadeia.toLowerCase())){
                    erro = true;
                    continue;
                }
            }
            reconhecedor.resetar();
            if(!possuiSaida){
                if(reconhecedor.verificar(ptf.getCadeia())){
                    ptf.setBackgroundTextField(Color.green);
                }else{
                    ptf.setBackgroundTextField(Color.red);
                }
            }else{
                ptf.setSaida(((Core.AutomatoFinito)reconhecedor).getSaida());
            }
        }
        if(erro)JOptionPane.showMessageDialog(this, "Não é permitido entrada com um caracter maiusculo");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    public void addEntrada(PanelTextField pte){
        int index = entradas.indexOf(pte);
        for(PanelTextField ptf : entradas){
            ptf.setBackgroundTextField(Color.white);
        }
        if(index == entradas.size()-1){
            entradas.add(new PanelTextField(this));
            jPanel1.add(entradas.get(entradas.size()-1));
            jScrollPane1.validate();
            JScrollBar jsb = jScrollPane1.getVerticalScrollBar();
            jsb.setValue(jsb.getMaximum());
            jScrollPane1.repaint();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
