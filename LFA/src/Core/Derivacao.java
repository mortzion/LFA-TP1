/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Core;

/**
 *
 * @author Matheus Prachedes Batista
 */
public class Derivacao {
    private String derivacao;
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
