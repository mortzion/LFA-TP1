/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Automato.Transicao;

import Automato.Estado;
import static Automato.Transicao1.NAO_REMOVE;
import static Automato.Transicao1.REMOVE_TRANSICAO;
import static Automato.Transicao1.TIPO_AUTO;
import static Automato.Transicao1.TIPO_BAIXO;
import static Automato.Transicao1.TIPO_CIMA;
import static Automato.Transicao1.TIPO_NORMAL;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import Core.Condicao;

/**
 *
 * @author Matheus Prachedes Batista & Eymar Ferrario de Lima
 */
public class TransicaoReta extends Transicao {
    private float anguloTransformacao = 0;
    private static int STEP = -17;
    private static int INICIAL = 1;
    public TransicaoReta(Estado source, Estado target, String condicao){
        super(source,target,condicao);
        this.tipo = TIPO_NORMAL;
    }

    public TransicaoReta(Estado source, Estado target, String condicao,String saida){
        super(source,target,condicao,saida);
        this.tipo = TIPO_NORMAL;
    }
    
    public TransicaoReta(Transicao t){
        super(t);
        this.tipo = TIPO_NORMAL;
    }
    
    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.black);
        g.drawLine(source.getX(), source.getY(), target.getX(), target.getY());
        this.drawArrowNew(g, source.getPoint(),target.getPoint(), 6, target.getRay());
        Point centro = new Point((target.getX() + source.getX())/2,(target.getY() + source.getY())/2);
        
        float comprimento = (float) (Math.sqrt( Math.pow(target.getX()- source.getX(), 2) + Math.pow(target.getY()- source.getY(), 2) ));
        //Calcula o cateto oposto do triangulo formado pelos dois estados
        //o cateto oposto é usado para calcular o angulo de rotação que
        //seria aplicado na transição.
        float catOposto = Math.abs(target.getY() - source.getY());
        this.anguloTransformacao = (float) Math.asin( catOposto  / comprimento );
        
        if(source.getX() < target.getX() && source.getY() < target.getY())anguloTransformacao*=-1;
        if(source.getX() > target.getX() && source.getY() > target.getY())anguloTransformacao*=-1;
     
        AffineTransform transform = new AffineTransform();
        transform.translate(centro.x, centro.y);
        transform.rotate(-anguloTransformacao);
        transform.translate(-centro.x, -centro.y);
        drawString(centro, transform, g);
    }

    

    @Override
    protected int colideComTransicao(int x, int y) {
        //Equação da reta : bx + ay + c = 0
        //d é a variavel de decisão
        int a = target.getX() - source.getX();
        int b = target.getY() - source.getY();
        int c = source.getY()*a - source.getX()*b;
        float d = (float) (Math.abs(b*x - a*y + c)/Math.sqrt(a*a+b*b));

        if(d < DELTA_COLISAO)return REMOVE_TRANSICAO;
        else return NAO_REMOVE;
    }

    private void drawArrowNew(Graphics2D g2, Point s, Point t, int size, int deslocamento) {
        float r = (float) Math.sqrt(Math.pow(s.x - t.x, 2) + Math.pow(s.y - t.y, 2));
        float cos = (t.x - s.x) / r;
        float sen = (t.y - s.y) / r;                
        
        int xAB = size + deslocamento;
        int yA = size;
        int yB = -size;
        
        Point pa = new Point(Math.round( xAB * -cos - yA * -sen )+t.x, Math.round( xAB * -sen + yA * -cos )+t.y);
        Point pb = new Point(Math.round( xAB * -cos - yB * -sen )+t.x, Math.round( xAB * -sen + yB * -cos )+t.y);
        Point pc = new Point(Math.round( deslocamento * -cos)+t.x, Math.round( deslocamento * -sen)+t.y);
        
        g2.drawLine(pc.x, pc.y, pa.x, pa.y);
        g2.drawLine(pc.x, pc.y, pb.x, pb.y);
    }
    
    @Override
    protected void drawString(Point centro, AffineTransform transform, Graphics2D g) {
        int i = INICIAL;
        
        AffineTransform preTransform = g.getTransform();
        g.setTransform(transform);
        
        Font prefont = g.getFont();
        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 17));
        for(Condicao c : condicoes){
            String s = c.getCondicao();
            if(c.getSaida().length() != 0)s = s + " : " + c.getSaida();
            g.drawChars(s.toCharArray(), 0, s.length(), centro.x - s.length()*4, centro.y + STEP*i);
            i++;
        }
        g.setFont(prefont);
        g.setTransform(preTransform);
    }
    
    @Override
    protected boolean colideComCond(int x, int y) {
        AffineTransform transform = new AffineTransform();
        float centroX = (source.getX() + target.getX())/2;
        float centroY = (source.getY() + target.getY())/2;
        
        transform.translate(centroX,centroY);
        transform.rotate(anguloTransformacao);
        transform.translate(-centroX,-centroY);
        
        Point2D clique = transform.transform(new Point(x,y),null);
       
        int i = INICIAL;
        for(Condicao c : condicoes){
            String s = c.getCondicao();
            if(c.getSaida() != "")s = s + " : " + c.getSaida();
            if(clique.getX() > centroX - s.length()*4 && clique.getX() < centroX + s.length()*4){
                if(clique.getY() < centroY + STEP*i + 10 && clique.getY() > centroY + STEP*i - 10 ){
                    condicoes.remove(s);
                    return true;
                }
            }
            i++;
        }
        
        return false;
    }
}
