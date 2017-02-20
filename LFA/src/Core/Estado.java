/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Core;

import java.util.ArrayList;

/**
 *  Classe que representa um estado de um Automato
 * @author Matheus Prachedes Batista & Eymar Ferrario de Lima
 */
public class Estado {
    /**
     * Boolean que indica se o estado é final
     */
    private boolean terminal;
    
    /**
     * Vetor de transições que possuem este estado como origem
     */
    private ArrayList<Transicao> transicoes;
    private String saida;

    Estado(boolean terminal) {
        this.terminal = terminal;
        transicoes = new ArrayList<>();
        saida = "";
    }
    
    Estado(boolean terminal,String saida){
        this.terminal = terminal;
        transicoes = new ArrayList<>();
        this.saida = saida;
    }
    
    public String getSaida(){
        return saida;
    }
    
    public ArrayList<Transicao> getTransicoes(){
        return transicoes;
    } 

    public void addTransicao(int estadoDestino, Character condicao,String saida){
        transicoes.add(new Transicao(condicao,estadoDestino,saida));
    }
    
    public boolean isFinal() {
        return terminal;
    }

    public void setFinal() {
        this.terminal = !terminal;
    }

    public void setFinal(boolean b) {
        this.terminal = b;
    }

   
    
    public void juntarTransicoes() {
//        if(transicoes.size() == 0)return;
//        Character ch = transicoes.get(0).getCaracter();
//        String trans;
//        if(ch == null)trans = "λ";
//        else trans = ch.toString();
//        for(int i=1;i<transicoes.size();i++){
//            trans = transicoes.get(i).concat(trans);
        for(int i=0;i<transicoes.size();i++){
            Transicao atual = transicoes.get(i);
            for(int j=transicoes.size()-1;j>i;j--){
                Transicao merge = transicoes.get(j);
                if(atual.getEstadoDestino() == merge.getEstadoDestino()){
                    atual.setTransicao(atual.getTransicao() + "|" + merge.getTransicao());
                    transicoes.remove(j);
                }
            }
        }
        //}
    }

    public String getAutoTrans(int i) {
        for(Transicao t : transicoes){
            if(t.getEstadoDestino() == i){
                String retorno = t.getTransicao();
                if(retorno.length()==0)return "";
                if(retorno.length()==1)return retorno+"*";
                else return "(" + retorno + ")*";
            }
        }
        return "";
    }

    public void addExpressao(int estadoDestino, String novaTransicao) {
        for(Transicao t : transicoes){
            if(t.getEstadoDestino() == estadoDestino){
                String transicao = t.getTransicao();
                if(transicao.length() == 0)t.setTransicao("λ|"+novaTransicao);
                else t.setTransicao(transicao+"|"+novaTransicao);
                return;
            }
        }
        transicoes.add(new Transicao(novaTransicao,estadoDestino));
    }

   
}
