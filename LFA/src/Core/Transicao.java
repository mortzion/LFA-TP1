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
public class Transicao {
    private Character caracter;
    private int estadoDestino;
    
    public Transicao(Character caracter, int estadoDestino){
        this.caracter = caracter;
        this.estadoDestino = estadoDestino;
    }
    
    public Character getCaracter(){
        return caracter;
    }
    public int getEstadoDestino(){
        return estadoDestino;
    }
}
