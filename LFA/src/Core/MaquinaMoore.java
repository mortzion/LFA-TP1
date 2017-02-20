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
public class MaquinaMoore extends Core.AutomatoFinito{

    @Override
    protected boolean proximoEstado(int estadoAtual, String entrada, int posLeitura) {
        Estado e = estados.get(estadoAtual);
        char charLido;
        saida = saida + e.getSaida();
        if(posLeitura == entrada.length())return true;
        charLido = entrada.charAt(posLeitura);
        
        for(Transicao t : e.getTransicoes()){
            if(t.getCaracter() == charLido){
                return proximoEstado(t.getEstadoDestino(),entrada,posLeitura+t.incremento());
            }
        }
        return false;
    }
    
    @Override
    public void clear() {
        super.clear(); //To change body of generated methods, choose Tools | Templates.
        saida = "";
    }
    
    
    
}
