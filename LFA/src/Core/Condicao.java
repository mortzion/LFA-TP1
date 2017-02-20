/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Core;

import Automato.Transicao.*;

/**
 *
 * @author Matheus Prachedes Batista
 */
public class Condicao {
    private String condicao;
    private String saida;
    
    public Condicao(String condicao, String saida){
        this.condicao = condicao;
        this.saida = saida;
    }
    
    public Condicao(String condicao){
        this.condicao = condicao;
        this.saida = "";
    }
    
    public String getCondicao(){
        return condicao;
    }
    
    public String getSaida(){
        return saida;
    }
}
