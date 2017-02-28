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
public class MaquinaMealy extends Core.AutomatoFinito{

    
    @Override
    protected boolean proximoEstado(Estado estadoAtual, String entrada, int posLeitura) {
        char charLido;
        if(posLeitura == entrada.length())return true;
        charLido = entrada.charAt(posLeitura);
        
        for(Transicao t : estadoAtual.getTransicoes()){
            if(t.getCaracter() == charLido){
                saida = saida + t.getSaida();
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
