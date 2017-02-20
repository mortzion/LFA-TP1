/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Automato;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.ArrayList;
import javax.vecmath.Point2f;

/**
 *
 * @author Matheus Prachedes Batista & Eymar Ferrario de Lima
 */
public class Transicao1 {
    public static final int TIPO_NORMAL = 1;
    public static final int TIPO_BAIXO = 2;
    public static final int TIPO_CIMA  = 3;
    public static final int TIPO_AUTO = 4;
    
    public static final int REMOVE_CONDICAO = 1;
    public static final int REMOVE_TRANSICAO = 2;
    public static final int NAO_REMOVE = 3;
    
    private static final int ALTURA_SETA = 50;
    
    private static final double DELTA_COLISAO = 1;
    
    private Estado source;
    private Estado target;
    private ArrayList<String> condições = new ArrayList<String>();
    private int tipo;
 
    
    public Transicao1(Estado source, Estado target, String condição){
        this.source = source;
        this.target = target;
        condições.add(condição);
        if(source == target)tipo = TIPO_AUTO;
        else tipo = TIPO_NORMAL;
    }

    public void addCondicao(String novaCondicao){
        condições.add(novaCondicao);
    }
   
    
    public void draw(Graphics2D g) {

        g.setColor(Color.black);        
        float comprimento,anguloElipse;
        float anguloTransformacao,catOposto;
        Point centro = new Point();
        Arc2D.Double seta;
        AffineTransform transform = new AffineTransform();
        Line2D[] pontaSeta;
        
        //Calcula o centro entre os dois estados
        centro.x = Math.abs(target.getX() + source.getX())/2;
        centro.y = Math.abs(target.getY() + source.getY())/2;
        //calcula a distancia entre os dois estados
        comprimento = (float) (Math.sqrt( Math.pow(target.getX()- source.getX(), 2) + Math.pow(target.getY()- source.getY(), 2) ));
        //Calcula o cateto oposto do triangulo formado pelos dois estados
        //o cateto oposto é usado para calcular o angulo de rotação que
        //seria aplicado na transição.
        catOposto = Math.abs(target.getY() - source.getY());
        anguloTransformacao = (float) Math.asin( catOposto  / comprimento );
        
        if(source.getX() < target.getX() && source.getY() < target.getY())anguloTransformacao*=-1;
        if(source.getX() > target.getX() && source.getY() > target.getY())anguloTransformacao*=-1;
     
        
        transform.translate(centro.x, centro.y);
        transform.rotate(-anguloTransformacao);
        transform.translate(-centro.x, -centro.y);
                
        
        switch(tipo){
            case TIPO_NORMAL:
                g.drawLine(source.getX(), source.getY(), target.getX(), target.getY());
                this.drawArrowNew(g, source.getPoint(),target.getPoint(), 6, target.getRay());
                break;

            case TIPO_CIMA:
            case TIPO_BAIXO:
              
                if(tipo == TIPO_CIMA)anguloElipse = -180;
                else anguloElipse = 180;
                
                seta = new Arc2D.Double(centro.x - comprimento/2, centro.y - ALTURA_SETA/2,comprimento,ALTURA_SETA,180,anguloElipse,Arc2D.OPEN);
     
                g.draw(transform.createTransformedShape(seta));
                
                pontaSeta = drawArrowArqueado(g,centro,comprimento);
                g.draw(transform.createTransformedShape(pontaSeta[0]));
                g.draw(transform.createTransformedShape(pontaSeta[1]));
                
                break;
                
            case TIPO_AUTO:
                g.drawOval(target.getX() - target.getRay(), target.getY()-target.getRay()*2, target.getRay()*2,target.getRay()*2);
                pontaSeta = drawArrowAuto();
                g.draw((pontaSeta[0]));
                g.draw((pontaSeta[1]));
                
                break;
            
        }
        drawString(centro,transform,g);
    }
    
    
    public void setTipo(int tipo){
        this.tipo = tipo;
    }
    
    
    public boolean opositeSourceTarget(Transicao1 trans){
        return target == trans.source && source == trans.target;
    }
    
    public boolean sameSourceTarget(Transicao1 trans){
        return target == trans.target && source == trans.source;
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
    
    
     

    private float calcularCatOposto(Point centro) {
        Point2f vetor = new Point2f(target.getX() - centro.x,target.getY()-centro.y);
        float r = (float) Math.sqrt(vetor.getX()*vetor.getX() + vetor.getY()*vetor.getY());
        vetor.x =  (vetor.x /r);
        vetor.y =  (vetor.y /r);
        
        return Math.abs(target.getY() - vetor.y - source.getY());
    }
     
    public Line2D[] drawArrowArqueado(Graphics2D g2, Point centro,float comprimento){
        Point2f intersec,ptoControle;
        Point setaA = new Point(),setaB = new Point();
        
        intersec = calculaIntersec(centro, comprimento,target.getRay());
        ptoControle = calculaIntersec(centro,comprimento,target.getRay() + 5);
        
        //calcula o vetor PtoControle - intersec
        ptoControle.x = (ptoControle.x - intersec.x) ;
        ptoControle.y =  (ptoControle.y - intersec.y);
        //Ajustando o modulo do vetor para 12;
        float r = (float)Math.sqrt(Math.pow(ptoControle.x,2) + Math.pow(ptoControle.y,2));
        ptoControle.x = ptoControle.x/r*10;
        ptoControle.y = ptoControle.y/r*10;
        
        float angulo = (float) Math.toRadians(45);
        setaA.x =  (int) ((ptoControle.x*cos(angulo) - ptoControle.y*sin(angulo)) + intersec.x);
        setaA.y =  (int) ((ptoControle.x*sin(angulo) + ptoControle.y*cos(angulo)) + intersec.y);
        
        angulo = (float)Math.toRadians(-45);
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
    
    public Point2f calculaIntersec(Point centro,float comprimento,float r) {
        Point2f intersec = new Point2f();
        
        float a = comprimento/2;
        float b = ALTURA_SETA/2;
        float h = a;
        if(target.getX() < centro.x) h *=-1;
   
        Equation2ndGrau intersection = new Equation2ndGrau(1 - (b*b)/(a*a), -2*h, b*b + h*h - r*r);
        intersection.calcular();
        
        if((r*r - Math.pow(intersection.result1 - h, 2) < 0))intersec.x =  intersection.result2;
        else intersec.x =intersection.result1;
        
        intersec.y = (float)Math.sqrt(r*r - Math.pow(intersec.x - h,2));
        if(tipo == TIPO_CIMA) intersec.y *=-1;
        return intersec;
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
    
    private Point2f normalizarVetor(Point2f vetor){
        float r = (float) Math.sqrt( vetor.x * vetor.x + vetor.y *vetor.y );
        return new Point2f( vetor.x/r,vetor.y/r );
    }
    private Point2f normalizarVetor(Point vetor){
        float r = (float) Math.sqrt( vetor.x * vetor.x + vetor.y *vetor.y );
        return new Point2f( vetor.x/r,vetor.y/r );
    }

    private void drawString(Point centro, AffineTransform transform, Graphics2D g) {
        int step = 0;
        int i =2;
        AffineTransform preTransform = g.getTransform();
        
        g.setTransform(transform);
        g.setFont(new Font(Font.DIALOG, Font.PLAIN, 17));
        switch(tipo){
            case TIPO_CIMA:
                step = -17;
                break;
            case TIPO_BAIXO:
                step = 17;
                break;
            case TIPO_NORMAL:
                i=1;
                step = -17;
                break;
            case TIPO_AUTO:
                step = -17;
                i = 3;
                g.setTransform(preTransform);              
        }
        
        for(String s : condições){
            g.drawChars(s.toCharArray(), 0, s.length(), centro.x - s.length()*4, centro.y + step*i);
            i++;
        }
        g.setTransform(preTransform);
    }

    public int getTipo() {
        return tipo;
    }
    
    public boolean isTarget(Estado target){
        return target == this.target;
    }
    
    public boolean isSource(Estado source){
        return source == this.source;
    }

    public int colide(int x, int y) {
        if(checaColisaoComCond(x,y)){
            return REMOVE_CONDICAO;
        }
        else{
            return checaColisaoComTrans(x,y);
        }
    }

    private boolean checaColisaoComCond(int x, int y) {
        return false;
    }

    private int checaColisaoComTrans(int x, int y) {
        float a,b,c,d;
        switch(this.tipo){
            case TIPO_NORMAL:
                //Equação da reta : bx + ay + c = 0
                //d é a variavel de decisão
                a = target.getX() - source.getX();
                b = target.getY() - source.getY();
                c = source.getY()*a - source.getX()*b;
                d = (float) (Math.abs(b*x - a*y + c)/Math.sqrt(a*a+b*b));
                
                if(d < 5)return REMOVE_TRANSICAO;
                else return NAO_REMOVE;
                
            case TIPO_AUTO:
                
                break;
            default:
                Point2D ptoTransformado = new Point(x,y);
                //calcula a distancia entre os dois estados
                float comprimento = (float) (Math.sqrt( Math.pow(target.getX()- source.getX(), 2) + Math.pow(target.getY()- source.getY(), 2) ));
                //Calcula o cateto oposto do triangulo formado pelos dois estados
                //o cateto oposto é usado para calcular o angulo de rotação que
                //seria aplicado na transição.
                float catOposto = Math.abs(target.getY() - source.getY());
                float anguloTransformacao = (float) Math.asin( catOposto  / comprimento );
        
                if(source.getX() < target.getX() && source.getY() < target.getY())anguloTransformacao*=-1;
                if(source.getX() > target.getX() && source.getY() > target.getY())anguloTransformacao*=-1;
                
                AffineTransform transform = new AffineTransform();
                transform.rotate(anguloTransformacao);
                transform.translate(-(target.getX() + source.getX())/2, -(target.getY() + source.getY())/2);
                
                ptoTransformado = transform.transform(ptoTransformado, null);
                
                
                if(tipo == TIPO_BAIXO && ptoTransformado.getY()<0)return NAO_REMOVE;
                
                //Equação da elipse xx/aa + yy/bb = 1 
                a = (float)Math.sqrt(Math.pow(source.getX() - target.getX(), 2) + Math.pow(target.getX() - source.getX(),2))/2;
                b = ALTURA_SETA/2;
                //Variavel de decisão
                d = (float)Math.abs(Math.pow(ptoTransformado.getX()/a,2) + Math.pow(ptoTransformado.getY()/b,2) - 1);
                if(d < 5)return REMOVE_TRANSICAO;
                else return NAO_REMOVE;
             
        }
        return NAO_REMOVE;
    }
    
    private class Equation2ndGrau{
        public float a;
        public float b;
        public float c;
        
        public float result1;
        public float result2;
        
        public Equation2ndGrau(float a,float b,float c){
            this.a = a;
            this.b = b;
            this.c = c;
        }
        
        public void calcular(){
            float delta = b*b - 4*a*c;
            delta = (float) Math.sqrt(delta);
            
            result1 = (-b + delta)/(2*a);
            result2 = (-b - delta)/(2*a);
        }
    }
    
}
