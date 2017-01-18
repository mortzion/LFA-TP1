/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Core;

/**
 *  Classe que representa uma regra de derivação
 * @author Matheus Prachedes Batista & Eymar Ferrario de Lima
 */
public class Derivacao {
    /**
     * String resultada da derivação
     */
    private String derivacao;
    
    /**
     * Index do não terminal na string de derivação
     */
    private int indexNaoTerminal;

    public Derivacao(String derivacao, int indexNaoTerminal){
        this.derivacao = derivacao;
        this.indexNaoTerminal = indexNaoTerminal;
    }
    
    public String getDerivacao(){
        return derivacao;
    }
    
    public int getIndexNaoTerminal(){
        return indexNaoTerminal;
    }
    
}
