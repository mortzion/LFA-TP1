/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Core;

import java.util.ArrayList;

/**
 *
 * @author Matheus Prachedes Batista
 */
public class Estado {
    private boolean terminal;
    private ArrayList<Transicao> transicoes;

    Estado(boolean terminal) {
        this.terminal = terminal;
        transicoes = new ArrayList<>();
    }
    
    public ArrayList<Transicao> getTransicoes(){
        return transicoes;
    } 

    public void addTransicao(int estadoDestino, Character condicao){
        transicoes.add(new Transicao(condicao,estadoDestino));
    }
    
    public boolean isFinal() {
        return terminal;
    }

    public void setFinal() {
        this.terminal = !terminal;
    }
}
