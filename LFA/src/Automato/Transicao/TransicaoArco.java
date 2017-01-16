/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Automato.Transicao;

import Automato.Estado;
import Automato.Transicao1;
import static Automato.Transicao1.NAO_REMOVE;
import static Automato.Transicao1.REMOVE_TRANSICAO;
import static Automato.Transicao1.TIPO_BAIXO;
import static Automato.Transicao1.TIPO_CIMA;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import javax.vecmath.Point2f;

/**
 *
 * @author Matheus Prachedes Batista
 */
public class TransicaoArco extends Transicao{
    
    private double anguloTransformacao = 0;
    private static int INICIAL = 2;

    public TransicaoArco(Estado source, Estado target, String condicao,int tipo) {
        super(source, target, condicao);
        this.tipo = tipo;
    }

    public TransicaoArco(Transicao t, int tipo) {
        super(t, tipo);
    }
    
    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.black);
        int anguloElipse;
        Arc2D.Double seta;
        Point centro = new Point();
        AffineTransform transform = new AffineTransform();
        Line2D[] pontaSeta;
        
        //Calcula o centro entre os dois estados
        centro.x = Math.abs(target.getX() + source.getX())/2;
        centro.y = Math.abs(target.getY() + source.getY())/2;
        //calcula a distancia entre os dois estados
        double comprimento =  (Math.sqrt( Math.pow(target.getX()- source.getX(), 2) + Math.pow(target.getY()- source.getY(), 2) ));
        //Calcula o cateto oposto do triangulo formado pelos dois estados
        //o cateto oposto é usado para calcular o angulo de rotação que
        //seria aplicado na transição.
        int catOposto = Math.abs(target.getX() - source.getX());
        anguloTransformacao =  Math.acos( catOposto  / comprimento );
        
        if(source.getX() < target.getX() && source.getY() < target.getY())anguloTransformacao*=-1;
        if(source.getX() > target.getX() && source.getY() > target.getY())anguloTransformacao*=-1;
        
        transform.translate(centro.x, centro.y);
        transform.rotate(-anguloTransformacao);
        transform.translate(-centro.x, -centro.y);
     
        if(tipo == TIPO_CIMA)anguloElipse = -180;
        else anguloElipse = 180;

        seta = new Arc2D.Double(centro.x - comprimento/2, centro.y - ALTURA_SETA/2,comprimento,ALTURA_SETA,180,anguloElipse,Arc2D.OPEN);

        g.draw(transform.createTransformedShape(seta));

        pontaSeta = drawArrowArqueado(g,centro,comprimento);
        g.draw(transform.createTransformedShape(pontaSeta[0]));
        g.draw(transform.createTransformedShape(pontaSeta[1]));

        this.drawString(centro, transform, g);
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
        int step=0;
        int i = INICIAL;
        
        switch(tipo){
            case TIPO_CIMA:
                step = -17;
                i = INICIAL;
                break;
            case TIPO_BAIXO:
                step = 17;
                i = INICIAL+1;
                break;
        }
        
        for(String s : condicoes){
            if(clique.getX() > centroX - s.length()*4 && clique.getX() < centroX + s.length()*4){
                if(clique.getY() < centroY + step*i + 10 && clique.getY() > centroY + step*i - 10 ){
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
        
        AffineTransform transform = new AffineTransform();
        transform.rotate(anguloTransformacao);
        transform.translate(-(target.getX() + source.getX())/2, -(target.getY() + source.getY())/2);

        Point2D ptoTransformado = transform.transform(new Point(x,y), null);


        if(tipo == TIPO_BAIXO && ptoTransformado.getY()<0)return NAO_REMOVE;

        //Equação da elipse xx/aa + yy/bb = 1 
        double a = Math.sqrt(Math.pow(source.getX() - target.getX(), 2) + Math.pow(target.getX() - source.getX(),2))/2;
        double b = ALTURA_SETA/2;
        //Variavel de decisão
        double d = Math.abs(Math.pow(ptoTransformado.getX()/a,2) + Math.pow(ptoTransformado.getY()/b,2) - 1);
        if(d < DELTA_COLISAO)return REMOVE_TRANSICAO;
        else return NAO_REMOVE;
    }

    @Override
    protected void drawString(Point centro, AffineTransform transform, Graphics2D g) {
        int step  = 0;
        int i =INICIAL;
        AffineTransform preTransform = g.getTransform();
        
        g.setTransform(transform);
        Font f = g.getFont();
        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 17));
        
        switch(tipo){
            case TIPO_CIMA:
                step = -17;
                break;
            case TIPO_BAIXO:
                step = 17;
                i = i+1;
                break;
        }
        
        for(String s : condicoes){
            g.drawChars(s.toCharArray(), 0, s.length(), centro.x - s.length()*4, centro.y + step*i);
            i++;
        }
        g.setFont(f);
        g.setTransform(preTransform);
    }

    private Line2D[] drawArrowArqueado(Graphics2D g, Point centro, double comprimento) {
        Point2f intersec,ptoControle;
        Point setaA = new Point(),setaB = new Point();
        
        intersec = calculaIntersec(centro, comprimento,target.getRay());
        ptoControle = calculaIntersec(centro,comprimento,target.getRay() + 5);
        
        //calcula o vetor PtoControle - intersec
        ptoControle.x = (ptoControle.x - intersec.x) ;
        ptoControle.y =  (ptoControle.y - intersec.y);
        //Ajustando o modulo do vetor para 12;
        double r = Math.sqrt(Math.pow(ptoControle.x,2) + Math.pow(ptoControle.y,2));
        ptoControle.x = (float)(ptoControle.x/r*10);
        ptoControle.y = (float)(ptoControle.y/r*10);
        
        double angulo =  Math.toRadians(45);
        setaA.x =  (int) ((ptoControle.x*cos(angulo) - ptoControle.y*sin(angulo)) + intersec.x);
        setaA.y =  (int) ((ptoControle.x*sin(angulo) + ptoControle.y*cos(angulo)) + intersec.y);
        
        angulo = Math.toRadians(-45);
        setaB.x =  (int) (ptoControle.x*cos(angulo) - ptoControle.y*sin(angulo)+intersec.x);
        setaB.y =  (int) (ptoControle.x*sin(angulo) + ptoControle.y*cos(angulo)+intersec.y);
        
        //Transladando os pontos na origem para a posição na elipse
        Line2D[] seta = new Line2D[2];
        setaA.translate(centro.x,  (int)centro.y);
        setaB.translate(centro.x,  (int)centro.y);
        intersec.x += centro.x;
        intersec.y += centro.y;
        
        
        seta[0] = new Line2D.Float(setaA,new Point((int)intersec.x,(int)intersec.y));
        seta[1] = new Line2D.Float(setaB,new Point((int)intersec.x,(int)intersec.y));
        return seta;
    }

    private Point2f calculaIntersec(Point centro, double comprimento, int r) {
        Point2f intersec = new Point2f();
        
        double a = comprimento/2;
        double b = ALTURA_SETA/2;
        double h = a;
        if(target.getX() < centro.x) h *=-1;
   
        Equation2ndGrau intersection = new Equation2ndGrau(1 - (b*b)/(a*a), -2*h, b*b + h*h - r*r);
        intersection.calcular();
        
        if((r*r - Math.pow(intersection.result1 - h, 2) < 0))intersec.x = (float) intersection.result2;
        else intersec.x = (float)intersection.result1;
        
        intersec.y = (float)Math.sqrt(r*r - Math.pow(intersec.x - h,2));
        if(tipo == TIPO_CIMA) intersec.y *=-1;
        return intersec;
    }
    
    private class Equation2ndGrau{
        public double a;
        public double b;
        public double c;
        
        public double result1;
        public double result2;
        
        public Equation2ndGrau(double a,double b,double c){
            this.a = a;
            this.b = b;
            this.c = c;
        }
        
        public void calcular(){
            double delta = b*b - 4*a*c;
            delta =  Math.sqrt(delta);
            
            result1 = (-b + delta)/(2*a);
            result2 = (-b - delta)/(2*a);
        }
    }
}
