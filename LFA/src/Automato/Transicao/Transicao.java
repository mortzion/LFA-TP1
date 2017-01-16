/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Automato.Transicao;

import Automato.Estado;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

/**
 *
 * @author Matheus Prachedes Batista
 */
public abstract class Transicao {
    public static final int REMOVE_CONDICAO = 1;
    public static final int REMOVE_TRANSICAO = 2;
    public static final int NAO_REMOVE = 3;
    
    public static final int TIPO_NORMAL = 1;
    public static final int TIPO_BAIXO = 2;
    public static final int TIPO_CIMA  = 3;
    public static final int TIPO_AUTO = 4;
    
    protected static final int ALTURA_SETA = 50;
    
    protected static final double DELTA_COLISAO = 5;
    
    protected Estado source;
    protected Estado target;
    protected ArrayList<String> condicoes;
    protected int tipo;
    
    public Transicao(Estado source ,Estado target,String condicao){
        this.source = source;
        this.target = target;
        condicoes = new ArrayList<>();
        condicoes.add(condicao);
    }
    
    public Transicao(Transicao t, int tipo){
        this.source = t.source;
        this.target = t.target;
        this.condicoes = t.condicoes;
        this.tipo = tipo;
    }
    
    public Transicao(Transicao t){
        this.source = t.source;
        this.target = t.target;
        this.condicoes = t.condicoes;
    }
    
    public ArrayList<String> getCondicoes(){
        return condicoes;
    }
    
    public Estado getSource(){
        return source;
    }
    
    public Estado getTarget(){
        return target;
    }
    
    public boolean sameSourceTarget(Estado source, Estado target){
        if(source == this.source && target == this.target)return true;
        return false;
    }
    
    public boolean opositeSourceTarget(Estado source, Estado target){
        if(source == this.target && target == this.source)return true;
        return false;
    }
    
    public void addCondicao(String condicao){
        if(!condicoes.contains(condicao))condicoes.add(condicao);
    }
    
    public abstract void draw(Graphics2D g);
     
    public int getTipo(){
         return tipo;
    }
    
    public boolean isTarget(Estado target){
        return this.target == target;
    }
    
    public boolean isSource(Estado source){
        return this.source == source;
    }
    
    protected abstract boolean colideComCond(int x,int y);
    protected abstract int colideComTransicao(int x,int y);
    protected abstract void drawString(Point centro, AffineTransform transform, Graphics2D g);
    public int colide(int x,int y){
        if(colideComCond(x, y)){
            if(condicoes.isEmpty()){
                return REMOVE_TRANSICAO;
            }return REMOVE_CONDICAO;
        }
        return colideComTransicao(x,y);
    }
}
