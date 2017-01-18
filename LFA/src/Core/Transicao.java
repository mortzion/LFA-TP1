/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Core;

/**
 *  Classe que represanta uma transição de um estado para outro de um automato
 * @author Matheus Prachedes Batista & Eymar Ferrario de Lima
 */
public class Transicao {
    /**
     * Caracter que deve ser lido para ocorrer a transição
     * Caso a transição seja vazia, a variavel será nula
     */
    private Character caracter;
    
    /**
     * index do estado destino
     */
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

    /**
     * Verifica se é possivel seguir por essa transição dado a cadeia que está
     * sendo reconhecida e a posição que está sendo lida da cadeia
     * 
     * @param posLeitura posição que está sendo lida da cadeia
     * @param entrada cadeia que está sendo reconhecida
     * @return boolean indicando se é valido seguir por essa transição
     */
    public boolean valida(int posLeitura, String entrada) {
        if(caracter == null){
            if(posLeitura <= entrada.length())return true;
        }else{
            if(posLeitura<entrada.length() && entrada.charAt(posLeitura) == caracter)return true;
        }
        return false;
    }

    /**
     * Metodo que indica quanto deve ser incrementado no index de leitura da cadeia
     * apos o automato seguir por essa transição
     * Basicamente, se a transição é vazia o incremento é 0
     * se não é 1
     * @return 
     */
    public int incremento() {
        if(caracter == null)return 0;
        return 1;
    }
}
