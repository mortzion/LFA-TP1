/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Core;

import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author Matheus Prachedes Batista
 */
public class AutomatoFinito implements ReconhecedorCadeia{  
    private ArrayList<Estado> estados;
    private ArrayList<Integer> caminhoVazio;
    private int inicial;
    private Stack<Integer> caminho;
    
    public AutomatoFinito(){
        estados = new ArrayList<>();
        caminho = new Stack<>();
        caminhoVazio = new ArrayList<>();   
    }
    
    public void clear(){
        estados.clear();
        caminhoVazio.clear();
    }
    
    public void addEstado(boolean terminal){
        estados.add(new Estado(terminal));
        caminhoVazio.add(new Integer(-1));
    }
    
    public void setFinal(int idEstado){
        estados.get(idEstado).setFinal();
    }
    
    public void addTransicao(int estado, int estadoDestino, ArrayList<String> condicoes){
        Estado e = estados.get(estado);
        for(String s : condicoes){
            if(s.startsWith("λ")){
                e.addTransicao(estadoDestino, null);
            }else{
                e.addTransicao(estadoDestino, s.charAt(0));
            }
        }
    }
    
    public void setInicial(int inicial){
        this.inicial = inicial;
    }
    
    public boolean verificar(String entrada){
        int estadoAtual = inicial;
        caminho.clear();
        caminho.add(estadoAtual);
        if(proximoEstado(estadoAtual, entrada, 0))return true;
        return false;
    }
    
    public Stack<Integer> getCaminho(){
        return caminho;
    }
    
    private boolean proximoEstado(int estadoAtual, String entrada, int posLeitura){
        int caminhoAntigo;
        Estado e = estados.get(estadoAtual);
        if(e.isFinal() == true && entrada.length() == posLeitura)return true;
        if(entrada.length() < posLeitura)return false;
        ArrayList<Transicao> trans = e.getTransicoes();
        
        caminhoAntigo = caminhoVazio.get(estadoAtual);
        caminhoVazio.set(estadoAtual, posLeitura);
        for(int i=0;i<trans.size();i++){
            Transicao t = trans.get(i);
            caminho.add(t.getEstadoDestino());
            try{
            if(t.getCaracter() != null && t.getCaracter() == entrada.charAt(posLeitura)){
                if(proximoEstado(t.getEstadoDestino(),entrada,posLeitura+1))return true;
            }else{
                if(t.getCaracter() == null && caminhoVazio.get(t.getEstadoDestino()) != posLeitura){
                    if(proximoEstado(t.getEstadoDestino(),entrada,posLeitura))return true;
                }
            }
            }catch(IndexOutOfBoundsException exception){}
            caminho.pop();
        }
        caminhoVazio.set(estadoAtual,caminhoAntigo);
        return false;
    } 

    @Override
    public void resetar() {
        for(int i=0;i<caminhoVazio.size();i++){
            caminhoVazio.set(i, -1);
        }
        caminho.clear();
    }
    
}
