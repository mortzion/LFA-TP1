/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Matheus Prachedes Batista
 */
public class Sequence extends RegEx {
    private RegEx primeira;
    private RegEx segunda;
    
    public Sequence(RegEx primeira, RegEx segunda){
        this.primeira = primeira;
        this.segunda = segunda;
    }
}
