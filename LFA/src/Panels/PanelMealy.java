/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Panels;

import Automato.TransicaoMealy;
import Core.AutomatoFinito;
import Core.MaquinaMealy;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import lfa.Main;

/**
 *
 * @author Matheus Prachedes Batista
 */
public class PanelMealy extends Automato{
    public PanelMealy(Main pai) {
        super(pai);
        ((JMenuItem)(menuEstado.getSubElements()[0])).setEnabled(false);
        jButton2.setEnabled(false);
        jButton2.setBackground(new Color(233,233,233));
        needFinal = false;
    }

    @Override
    protected void criarTransicao(Point point) {
        TransicaoMealy t = new TransicaoMealy(this.pai,true,this,point);
    }

    @Override
    protected void testeRapido() {
        AutomatoFinito automato = new MaquinaMealy();
        if(view.verificaEstados(needFinal)){
            view.montarAutomato(automato);
            String cadeia = JOptionPane.showInputDialog(getParent(),"Insira a cadeia que irá ser reconhecida");
            if(cadeia == null)return;
            automato.verificar(cadeia);
            JOptionPane.showMessageDialog(this.getParent(), "Saida: " + automato.getSaida());
        }else{
            JOptionPane.showMessageDialog(this.getParent(),"O autômato parece não estar completo.\n"
                    + "É necessário exatamente 1 estado inicial.");
        }
    }
    @Override
    protected void testeMultiplos(){
        AutomatoFinito automato = new MaquinaMealy();
        if(view.verificaEstados(needFinal)){
            view.montarAutomato(automato);
            MultiplasEntradas me = new MultiplasEntradas(pai, true, automato,false,true);
            me.setVisible(true);
            me.toFront();
        }else{
            JOptionPane.showMessageDialog(this.getParent(),"O autômato parece não estar completo.\n"
                    + "É necessario exatamente 1 estado inicial.");
        }
    }
    
    @Override
    protected void testeEstadoPorEstado(){
        JOptionPane.showMessageDialog(this, "Teste estado por estado não está disponivel para Maquinas de Mealy.");
    }
    
    @Override
    protected void converterGR(){
        JOptionPane.showMessageDialog(this, "Conversão não disponivel para Maquinas de Mealy");
    }
}
