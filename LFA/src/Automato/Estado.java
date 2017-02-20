/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Automato;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author Matheus Prachedes Batista & Eymar Ferrario de Lima
 */
public class Estado extends Entidade{
    private int x;
    private int y;
    private String nome;
    private boolean isfinal = true;
    private boolean isInicial = false;
    public static final int ray = 20;
    private String label = "";
    private String saida = "";
    
    public Estado(int x, int y,String nome){
        init(x,y,"",nome);
    }
    
    public Estado(int x, int y, String saida,String nome){
        init(x,y,saida,nome);
    }
    
    private void init(int x, int y, String saida,String nome){
        updatePos(x, y);
        this.nome = nome;
        this.isInicial = false;
        this.isfinal = false;
        this.saida = saida;
    }
    
    public String getLabel(){
        return label;
    }
    public boolean isInicial(){
        return isInicial;
    }
    public String getSaida(){
        return saida;
    }
    public void setLabel(String label){
        this.label = label;
    }
    
    public String getNome(){
        return nome;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public int getRay(){
        return ray;
    }
    
    public Point getPoint(){
        return new Point(x,y);
    }
    
    
    public void updatePos(int x,int y){
        this.x = x;
        this.y = y;
    }
    
    public void draw(Graphics2D g){
        g.setColor(Color.black);
        g.fillOval( x-1 - ray , y-1 - ray, ray*2 + 2, ray*2 + 2);
        
        if(selected == false)g.setColor(Color.yellow);
        else g.setColor(Color.blue);
        g.fillOval(x-ray, y-ray, ray*2, ray*2);
        if(this.label.length()!=0){
            drawLabel(label,g,x,y);
        }
        if(this.saida.length()!=0){
            drawLabel(saida,g,x+ray,y-ray*2-5);
        }
        g.setColor(Color.black);
        g.drawChars(nome.toCharArray(), 0, nome.length(), x-6 , y+3);
        
        if(this.isfinal)g.drawOval(x - (ray*3)/4 -1, y-(ray*3)/4-1,(ray*6)/4+1 , (ray*6)/4+1);
        if(this.isInicial){
            g.drawLine(x-ray, y, x-ray-15, y-15);
            g.drawLine(x-ray, y, x-ray-15, y+15);
            g.drawLine(x-ray-15, y-15, x-ray-15, y+15);
        }
    }
    

    
    public boolean colide(int x, int y) {
        double r =Math.sqrt(Math.pow(x- this.x,2) + Math.pow(y-this.y, 2)); 
        if( r  < this.ray + 5 ){
            return true;
        }
        return false;
    }

    @Override
    public boolean colide(Point p) {
        return colide(p.x,p.y);
    }
    
    public void setInicial(boolean isInicial){
        this.isInicial = isInicial;
    }
    
    public void setFinal(boolean isFinal) {
        this.isfinal = isFinal;
    }

    public boolean isFinal() {
        return isfinal;
    }

    
    public void drawLabel(String label,Graphics g,int x, int y){
        int largura = 20 + label.length()*10;
        Font f = g.getFont();
        g.setFont(new Font(Font.MONOSPACED,Font.PLAIN,f.getSize()+5));
        g.fillRect(x-largura/2, y-10+ray+5,largura,20);
        g.setColor(Color.black);
        g.drawRect(x-largura/2, y-10+ray+5, largura, 20);
        g.drawString(label,x-largura/2+7, y+10-5+ray+5);
        g.setFont(f);
    }
}

