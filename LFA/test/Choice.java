/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Matheus Prachedes Batista
 */
public class Choice extends RegEx{
    private RegEx nula;
    private RegEx alternativa;
    
    public Choice(RegEx nula, RegEx alternativa){
        this.nula = nula;
        this.alternativa = alternativa;
    }
}
