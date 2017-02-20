/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Classe usada para representar um automato e verificar se uma string é aceita
 * pelo mesmo.
 * Todos os reconhecedores (Automato e Gramatica) implementam a interface
 * ReceonhecedorCadeia que é usada pela GUI de multiplas entradas.
 * @author Matheus Prachedes Batista & Eymar Ferrario de Lima
 */
public class AutomatoFinito implements ReconhecedorCadeia{  
    /**
     * ArrayList contendo todos os estados do automato
     */
    protected ArrayList<Estado> estados;
    
    /**
     * Vetor usado para impedir loops infinitos durante a verificação de uma
     * cadeia
     */
    protected ArrayList<Integer> caminhoVazio;
    
    /**
     * index do Estado inicial
     */
    protected int inicial;
    
    /**
     * Vetor usado para recuperar o caminho seguido pelo automato para reconhecer
     * uma cadeia
     */
    protected ArrayList<Integer> caminho;
    
    /**
     * Vetor usado para recuperar os caracteres lidos por cada estado no caminho
     * seguido pelo automato para reconhecer uma cadeia
     * Importante pelo fato de que nem todo estado no caminho irá reconher um
     * caracter
     */
    protected ArrayList<Character> charLidos;
    protected String saida;
    public AutomatoFinito(){
        saida = "";
        estados = new ArrayList<>();
        caminho = new ArrayList<>();
        caminhoVazio = new ArrayList<>();   
        charLidos = new ArrayList<>();    
    }
    
    public void clear(){
        estados.clear();
        caminhoVazio.clear();
    }
    
    /**
     * Adciona um estado ao automato
     * 
     * @param terminal Identifica se o estado é final 
     */
    public void addEstado(boolean terminal,String saida){
        estados.add(new Estado(terminal,saida));
        caminhoVazio.add(new Integer(-1));
    }
    
    /**
     * Marca um estado como sendo final
     * 
     * @param idEstado index do estado
     */
    public void setFinal(int idEstado){
        estados.get(idEstado).setFinal();
    }
    
    /**
     * Adciona uma transição entre dois estados com todas as condições possiveis
     * 
     * @param estado index do estado de origem
     * @param estadoDestino index do estado de destino
     * @param condicoes vetor de condiçõs para a transição
     */
    public void addTransicao(int estado, int estadoDestino, ArrayList<Condicao> condicoes){
//        Estado e = estados.get(estado);
        for(Condicao c : condicoes){
            addTransicao(estado,estadoDestino,c.getCondicao(),c.getSaida());
//            String s = c.getCondicao();
//            if(s.startsWith("λ")){
//                e.addTransicao(estadoDestino, null,c.getSaida());
//            }else{
//                e.addTransicao(estadoDestino, s.charAt(0),c.getSaida());
//            }
        }
        
    }
    
    public void addTransicao(int estado, int estadoDestino, String condicao, String saida){
        Estado e = estados.get(estado);
        if(condicao.startsWith("λ")){
            e.addTransicao(estadoDestino, null, saida);
        }else{
            e.addTransicao(estadoDestino, condicao.charAt(0), saida);
        }
        
    }
    
    /**
     * Marca um estado como inicial
     * 
     * @param inicial index do estado inicial 
     */
    public void setInicial(int inicial){
        this.inicial = inicial;
    }
    
    /**
     * Verifica se uma cadeia é aceita pelo automato
     * 
     * @param entrada String que será verificada
     * @return boolean indicando se aceitou ou não a cadeia 
     */
    public boolean verificar(String entrada){
        int estadoAtual = inicial;
        caminho.clear();
        charLidos.clear();
        caminho.add(estadoAtual);
        if(proximoEstado(estadoAtual, entrada, 0)){
            return true;
        }
        return false;
    }
    
    public ArrayList<Integer> getCaminho(){
        return caminho;
    }
    public ArrayList<Character> getCharLidos(){
        return charLidos;
    }
    
    /**
     * Função recursiva usada para verificar a cadeia
     * Foi baseada na busca em profundidade, mas com algumas alterações
     * 
     * @param estadoAtual index do Estado atual
     * @param entrada cadeia sendo reconhecida
     * @param posLeitura posição de leitura na cadeia
     * @return boolean indicando se o estado atual validou a entrada
     */
    protected boolean proximoEstado(int estadoAtual, String entrada, int posLeitura){
        int caminhoAntigo;
        Estado e = estados.get(estadoAtual);
        if(e.isFinal() == true && entrada.length() == posLeitura){
            return true;
        }
        //if(entrada.length() < posLeitura)return false;
        ArrayList<Transicao> trans = e.getTransicoes();
        
        caminhoAntigo = caminhoVazio.get(estadoAtual);
        caminhoVazio.set(estadoAtual, posLeitura);
        for(int i=0;i<trans.size();i++){
            Transicao t = trans.get(i);
            caminho.add(t.getEstadoDestino());
            charLidos.add(t.getCaracter());
            if(t.valida(posLeitura,entrada)){
                if(proximoEstado(t.getEstadoDestino(),entrada,posLeitura + t.incremento())){
                    return true;
                }
            }
            charLidos.remove(charLidos.size()-1);
            caminho.remove(caminho.size()-1);
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

    public String getSaida() {
        return saida;
    }
    
    public ArrayList<Estado> getEstados(){
        return estados;
    }
    
    public Core.Gramatica converterGR(){
        Core.Gramatica gramatica = new Gramatica();
        for(int i=0;i<estados.size();i++){
            gramatica.addNaoTerminal((char)('A' + i));
        }
        for(int i=0;i<estados.size();i++){
            for(Transicao t : estados.get(i).getTransicoes()){
                String derivacao = "";
                if(t.getCaracter() != null)derivacao+=t.getCaracter();
                gramatica.addDerivacao((char)('A' + i), derivacao + (char)('A' + t.getEstadoDestino()));
            }
            if(estados.get(i).isFinal())gramatica.addDerivacao((char)('A' + i), "");
        }
        gramatica.tornarInicial(this.inicial);
        return gramatica;
    }
    
    public String converterRegex(){
        int numFinais=0;
        if(estados.get(inicial).isFinal())numFinais=2;
        for(Estado e : estados){
            if(e.isFinal())numFinais++;
            e.juntarTransicoes();
            
        }
        if(numFinais>1){
            for(int i=0;i<estados.size();i++){
                Estado e = estados.get(i);
                if(e.isFinal()){
                    e.setFinal(false);
                    e.addTransicao(estados.size(),null,"");
                }
            }
            estados.add(new Estado(true));    
        }
        for(int i=0;i<estados.size();i++){
            Estado atual = estados.get(i);
            String loop = atual.getAutoTrans(i);
            if(atual.isFinal()==false && inicial != i){
                ArrayList<Transicao> transOut = atual.getTransicoes();
                for(int j=0;j<estados.size();j++){
                    if(i==j)continue;
                    Estado in = estados.get(j);
                    ArrayList<Transicao> transIn = in.getTransicoes();
                    
                    for(int indexIn = 0;indexIn<transIn.size();indexIn++){
                        Transicao rin = transIn.get(indexIn);
                        if(rin.getEstadoDestino()!=i)continue;
                        for(int indexOut = 0;indexOut<transOut.size();indexOut++){
                            Transicao rout = transOut.get(indexOut);
                            if(rout.getEstadoDestino()==i)continue;
                            in.addExpressao(rout.getEstadoDestino(), rin.getTransicao()+loop+rout.getTransicao());
                        }
                    }
                }
                transOut.clear();
            }
        }
        for(Transicao t : estados.get(inicial).getTransicoes()){
            if(estados.get(t.getEstadoDestino()).isFinal()){
                String loop = estados.get(inicial).getAutoTrans(inicial);
                String out = t.getTransicao();
                if(out.contains("|"))out = "(" + out+")";
                return loop+out;
            }
        }
        return "";
    }

    private ArrayList<Transicao> transicoesDestino(int i) {
        ArrayList<Transicao> transicoes = new ArrayList<>();
        for(int j = 0; j<estados.size();j++){
            Estado e = estados.get(j);
            if(j == i)continue;
            for(Transicao t : e.getTransicoes()){
                if(t.getEstadoDestino() == i)transicoes.add(t);
            }
        }
        return transicoes;
    }
}
