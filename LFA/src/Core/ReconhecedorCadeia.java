/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

/**
 *  Interface que todas as classes reconhecedoras de cadeias devem implementar
 *  Usada pela GUI para verificar se uma cadeia é reconhecida pelo determinado
 *  reconhecedor. Deste modo a implementação da GUI é independente de qual 
 * reconhecedor é usado
 * @author Matheus Prachedes Batista & Eymar Ferrario de Lima
 */
public interface ReconhecedorCadeia{
    /**
     * Método usado para verificar se uma cadeia é aceita pelo reconhecedor
     * @param cadeia cadeia a ser verificada
     * @return boolean indicando se a cadeia foi aceita
     */
    public abstract boolean verificar(String cadeia);
    
    /**
     * Método usado para resetar os dados internos do reconhecedor para evitar
     * conflitos com calculos futuros
     */
    public abstract void resetar();
}
