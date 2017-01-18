/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Core;

import java.util.ArrayList;

/**
 *  Classe que representa um não terminal de uma gramatica
 * @author Matheus Prachedes Batista & Eymar Ferrario de Lima
 */
public class NaoTerminal {
    /**
     * Indica qual é o caracter não terminal
     */
    public char naoTerminal;
    
    /**
     * Vetor que possui todas as derivações deste não terminal
     */
    public ArrayList<Derivacao> derivacoes;
    
    public NaoTerminal(char naoTerminal){
        this.naoTerminal = naoTerminal;
        derivacoes = new ArrayList<Derivacao>();
    }

    public NaoTerminal(char naoTerminal, String derivacao) {
        this.naoTerminal = naoTerminal;
        derivacoes = new ArrayList<Derivacao>();
        addDerivacao(derivacao);
    }
    
    public char getNaoTerminal(){
        return naoTerminal;
    }
    
    public void addDerivacao(String derivacao, int indexNaoTerminal){
        derivacoes.add(new Derivacao(derivacao,indexNaoTerminal));
    }
    public void addDerivacao(String derivacao){
        int index = 0;
        for(int i=0;i<derivacao.length();i++){
            if(Character.isUpperCase(derivacao.charAt(i))){
                derivacoes.add(new Derivacao(derivacao,i));
                return;
            }
        }
        derivacoes.add(new Derivacao(derivacao,-1));
    }
    
    public ArrayList<Derivacao> getDerivacoes(){
        return derivacoes;
    }
}
