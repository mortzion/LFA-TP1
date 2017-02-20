/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Automato.Transicao;

import Automato.Estado;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import Core.Condicao;

/**
 *
 * @author Matheus Prachedes Batista & Eymar Ferrario de Lima
 */
public class TransicaoAuto extends Transicao {
    private static int STEP = -17;
    private static int INICIAL = 3;
    
    public TransicaoAuto(Estado source, Estado target, String condicao){
        super(source,target,condicao);
        this.tipo = TIPO_AUTO;
    }
    public TransicaoAuto(Estado source, Estado target, String condicao,String saida){
        super(source,target,condicao,saida);
        this.tipo = TIPO_AUTO;
    }
    
    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.black);
        g.drawOval(target.getX() - target.getRay(), target.getY()-target.getRay()*2, target.getRay()*2,target.getRay()*2);
        Line2D[] pontaSeta = drawArrowAuto();
        g.draw((pontaSeta[0]));
        g.draw((pontaSeta[1]));
        this.drawString(new Point((target.getX() + source.getX())/2,(target.getY() + source.getY())/2), null, g);
    }

    @Override
    protected boolean colideComCond(int x, int y) {
        AffineTransform transform = new AffineTransform();
        float centroX = (source.getX() + target.getX())/2;
        float centroY = (source.getY() + target.getY())/2;
        
        
        int i = INICIAL;
        for(Condicao c : condicoes){
            String s = c.getCondicao();
            if(c.getSaida() != "")s = s + " : " + c.getSaida();
            if(x > centroX - s.length()*4 && x < centroX + s.length()*4){
                if(y < centroY + STEP*i + 10 && y > centroY + STEP*i - 10 ){
                    condicoes.remove(s);
                    return true;
                }
            }
            i++;
        }
        
        return false;
    }

    @Override
    protected int colideComTransicao(int x, int y) {
        x = x - (source.getX());
        y = y - (source.getY() - source.getRay());
        float d = Math.abs(x*x+y*y-source.getRay() * source.getRay());
        if(d < DELTA_COLISAO)return REMOVE_TRANSICAO;
        return NAO_REMOVE;
    }

    @Override
    protected void drawString(Point centro, AffineTransform transform, Graphics2D g) {
        int i = INICIAL;
        Font f = g.getFont();
        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 17));
        for(Condicao c : condicoes){
            String s = c.getCondicao();
            if(c.getSaida().length() != 0)s = s + " : " + c.getSaida();
            g.drawChars(s.toCharArray(), 0, s.length(), centro.x - s.length()*4, centro.y + STEP*i);
            i++;
        }
        g.setFont(f);
    }

    private Line2D[] drawArrowAuto() {
        Point intersec = new Point((int)(10*Math.sqrt(3)) + target.getX(),target.getY() - 10);
        Point pa = new Point(intersec.x +13,intersec.y-5);
        Point pb = new Point(intersec.x-5,intersec.y-13);
        
        Line2D[] arrow = new Line2D[2];
        arrow[0] = new Line2D.Double(pa,intersec);
        arrow[1] = new Line2D.Double(pb,intersec);
        return arrow;
    }

    

}
