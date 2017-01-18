/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Automato;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author Matheus Prachedes Batista & Eymar Ferrario de Lima
 */
public class Label extends Entidade{
    private int x;
    private int y;
    private String label;
    private static final int ALTURA = 20;
    
    public Label(int x, int y, String label){
        this.x = x;
        this.y = y;
        this.label = label;
    }
    
    public boolean colide(int x, int y){
        int largura = 20+label.length()*5;
        if(x < this.x-largura/2 || x > this.x+largura/2 )return false;
        if(y < this.y-ALTURA/2 || y > this.y+ALTURA/2)return false;
        return true;
    }
    
    @Override
    public boolean colide(Point p) {
        return colide(p.x,p.y);
    }
    
    public void updatePos(int x, int y){
        this.x = x;
        this.y = y;
        
    }
    
    public void draw(Graphics g){
        int largura = 20 + label.length()*10;
        Font f = g.getFont();
        g.setFont(new Font(Font.MONOSPACED,Font.PLAIN,f.getSize()+5));
        if(!selected)g.setColor(Color.yellow);
        else g.setColor(Color.blue);
        g.fillRect(x-largura/2, y-ALTURA/2,largura,ALTURA);
        g.setColor(Color.black);
        g.drawRect(x-largura/2, y-ALTURA/2, largura, ALTURA);
        g.drawString(label,x-largura/2+7, y+ALTURA/2-5);
        g.setFont(f);
    }

    public void setLabel(String text) {
        this.label = text;
    }
    public String getLabel(){
        return this.label;
    }

    
    
}
