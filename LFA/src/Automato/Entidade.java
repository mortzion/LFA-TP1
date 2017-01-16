/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Automato;

import java.awt.Point;

/**
 *
 * @author Matheus Prachedes Batista
 */
public abstract class Entidade {
    protected boolean selected;
    public abstract boolean colide(int x,int y);
    public abstract boolean colide(Point p);
    public abstract void updatePos(int x,int y);
    
    public boolean isSelecioando(){
        return selected;
    }
    
    public void setSelecionado(boolean selecionado){
        this.selected = selecionado;
    }
}
