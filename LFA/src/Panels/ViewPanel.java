/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Panels;

import Automato.Entidade;
import Automato.Estado;
import Automato.Label;
import Automato.Transicao.Transicao;
import Automato.Transicao.TransicaoArco;
import Automato.Transicao.TransicaoAuto;
import Automato.Transicao.TransicaoReta;
import Automato.Transicao1;
import Core.AutomatoFinito;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ViewPanel extends JPanel {

        public ArrayList<Estado> estados = new ArrayList<Estado>();
        public ArrayList<Transicao> trans = new ArrayList<>();
        public ArrayList<Label> labels = new ArrayList<>();
        public Entidade entidadeSelecionada = null;
        public Estado estadoSelecionado = null;
        public Label labelSelecionada = null;
        public Point transSource = new Point();
        public Point transTarget = new Point();
        public boolean creatingTrans = false;
        
        public ViewPanel() {
            super();
            this.setBackground(java.awt.Color.white);
            this.setLayout(new FlowLayout(FlowLayout.LEFT));
            this.setSize(800,600);
            this.setPreferredSize(this.getSize());
        }

        @Override
        public void paintComponent(java.awt.Graphics g) {
            super.paintComponent(g);

            java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;
            ////configuração do rendering para obeter melhor qualidade
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
            g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
            g2.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
            
            if (this.imageBuffer == null) {
                this.imageBuffer = new BufferedImage(this.getWidth(),
                        this.getHeight(), BufferedImage.TYPE_INT_RGB);

                java.awt.Graphics2D g2Buffer = this.imageBuffer.createGraphics();
                g2Buffer.setColor(this.getBackground());
                g2Buffer.fillRect(0, 0, (int)getPreferredSize().width, (int)getPreferredSize().height);

                g2Buffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                this.draw(g2Buffer);
                g2Buffer.dispose();
            }

            if (this.imageBuffer != null) {
                g2.drawImage(this.imageBuffer, 0, 0, null);
            }
        }
        
        public BufferedImage imageBuffer;
        
        public void cleanImage() {
            this.imageBuffer = null;
        }
        
        public void draw(Graphics2D g) {
            g.setColor(Color.white);
            g.fillRect(0, 0, this.imageBuffer.getWidth(), this.imageBuffer.getHeight());
        
            for(Transicao t : trans){
                t.draw(g);
            }
            
            for(Estado e : estados){
                e.draw(g);
            }
            
            for(Label l : labels){
                l.draw(g);
            }
            
            if(creatingTrans == true){
                g.setColor(Color.black);
                g.drawLine(transSource.x, transSource.y, transTarget.x, transTarget.y);
            }
        }

        public void addTrans(Estado source, Estado target, String parametro){
            for(int i=trans.size()-1;i>=0;i--){
                Transicao t = trans.get(i);
                if(t.sameSourceTarget(source,target)){
                    t.addCondicao(parametro);
                    return;
                }
                if(t.opositeSourceTarget(source,target) && t.getTipo() == Transicao.TIPO_NORMAL){
                    trans.set(i, new TransicaoArco(t,Transicao.TIPO_CIMA));
                    trans.add(new TransicaoArco(source,target,parametro,Transicao.TIPO_BAIXO));
                    return;
                }
               
            }
            if(source != target)trans.add(new TransicaoReta(source,target,parametro));
            else trans.add(new TransicaoAuto(source,target,parametro));
        }
        
        public void removerEntidade(int x, int y){
            int retorno;
            for(int i= labels.size()-1;i>=0;i--){
                if(labels.get(i).colide(x,y)){
                    labels.remove(i);
                    return;
                }
            }
            for(int i = estados.size()-1; i>=0;i--){
                if(estados.get(i).colide(x, y)){
                    removeEstado(i);
                    return;
                }
            }
            for(int i=trans.size()-1;i>=0;i--){
                retorno = trans.get(i).colide(x, y);
                if(retorno == Transicao1.NAO_REMOVE)continue;
                if(retorno == Transicao1.REMOVE_TRANSICAO){
                    if(trans.get(i).getTipo() == Transicao1.TIPO_BAIXO || trans.get(i).getTipo() == Transicao1.TIPO_CIMA){
                        
                        for(int j=0;j<trans.size();j++){
                            Transicao t = trans.get(j);
                            if(t.opositeSourceTarget(trans.get(i).getSource(),trans.get(i).getTarget()))trans.set(j, new TransicaoReta(t));
                        }
                        
                    }
                    trans.remove(i);
                }
                return;
            }
        }
        
        public Estado getEstadoColidido(int x,int y){
            for(Estado e : estados){
                if(e.colide(x,y)){                 
                    return e;
                }
            }
            return null;
        }

        public void setInicial(Estado estadoSelecionado){
            for(Estado s : estados){
                s.setInicial(false);
            }
            estadoSelecionado.setInicial(true);
           
        }
        
        public void setFinal(Estado estadoSelecionado) {
            estadoSelecionado.setFinal(!estadoSelecionado.isFinal());
            
        }

        

        public void updatePosSelecionado(Point point,JScrollPane scroll) {
            Dimension dim = this.getPreferredSize();
            if(point.x + Estado.ray > this.getPreferredSize().width){
                dim.width += Estado.ray*2;
            }
            if(point.y + Estado.ray > this.getPreferredSize().height){
                dim.height += Estado.ray*2;
            }
            this.setPreferredSize(dim);
            entidadeSelecionada.updatePos(point.x, point.y);
        }

        public void removeEstado(int index) {
            Estado e = estados.get(index);
            for(int i = trans.size()-1;i>=0;i--){
                Transicao t = trans.get(i);
                if(t.isSource(e) || t.isTarget(e)){
                    trans.remove(t);
                }
            }
            estados.remove(index);
        }
        public void removerEstado(Estado estadoSelecionado) {
            estados.remove(estadoSelecionado);
            for(int i= trans.size()-1;i>=0;i--){
                Transicao t = trans.get(i);
                if(t.isSource(estadoSelecionado) || t.isTarget(estadoSelecionado)){
                    trans.remove(t);
                }
            }
        }

        public Label getLabelColidida(int x, int y) {
            for(Label l : labels){
                if(l.colide(x, y))return l;
            }
            return null;
        }

        public Entidade getEntidadeColidido(int x, int y) {
            for(int i=labels.size()-1;i>=0;i--){
                if(labels.get(i).colide(x,y))return labels.get(i);
            }
            for(int i=estados.size()-1;i>=0;i--){
                if(estados.get(i).colide(x,y))return estados.get(i);
            }
            return null;
        }

        public boolean verificaEstados() {
            boolean hasFinal,hasInicial;
            hasFinal = hasInicial = false;
            for(Estado e : estados){
                if(e.isFinal()){
                    hasFinal = true;
                }
                if(e.isInicial()){
                    if(hasInicial)return false;
                    hasInicial = true;
                }
            }
            if(hasInicial == true && hasFinal == true)return true;
            return false;
        }

        public void montarAutomato(AutomatoFinito automato) {
            automato.clear();
            ArrayList<Transicao> copia = (ArrayList<Transicao>) trans.clone();
            for(Estado e :estados){
                automato.addEstado(e.isFinal());
            }
            for(int i=0;i<estados.size();i++){
                Estado e = estados.get(i);
                
                for(int j=copia.size()-1;j>=0;j--){
                    Transicao t = copia.get(j);
                    if(t.isSource(e)){
                        automato.addTransicao(i, estados.indexOf(t.getTarget()) ,t.getCondicoes());
                        copia.remove(j);
                    }
                }
                if(e.isInicial())automato.setInicial(i);
            }
            
        }
    }