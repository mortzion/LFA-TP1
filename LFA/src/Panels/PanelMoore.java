/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Panels;

import Automato.Estado;
import Core.AutomatoFinito;
import Core.MaquinaMealy;
import Core.MaquinaMoore;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import lfa.Main;

/**
 *
 * @author Matheus Prachedes Batista
 */
public class PanelMoore extends Automato{

    public PanelMoore(Main pai) {
        super(pai);
        ((JMenuItem)(menuEstado.getSubElements()[0])).setEnabled(false);
        
        needFinal = false;
    }
    
    

    @Override
    protected Estado criarEstado(int x, int y) {
        String s = JOptionPane.showInputDialog("Saida do estado","λ");
        if(s != null){
            return new Estado(x,y,s,"E" + cont++);
        }
        return null;
    }
    
    @Override
    protected void testeRapido() {
        AutomatoFinito automato = new MaquinaMoore();
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
        AutomatoFinito automato = new MaquinaMoore();
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
        JOptionPane.showMessageDialog(this, "Teste estado por estado não está disponivel para Maquinas de Moore.");
    }
    
    @Override
    protected void converterGR(){
        JOptionPane.showMessageDialog(this, "Conversão não disponivel para Maquinas de Moore");
    }
}
