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
    
    /**
     * index do Estado inicial
     */
    protected Estado inicial;
    
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
        charLidos = new ArrayList<>();    
    }

    public AutomatoFinito(Estado e) {
        saida = "";
        estados = new ArrayList<>();
        caminho = new ArrayList<>();  
        charLidos = new ArrayList<>();  
        estados.add(e);
        inicial = e;
    }
    
    public void clear(){
        estados.clear();
        for(Estado e : estados){
            e.setCaminhoVazio(-1);
        }
    }
    
    /**
     * Adciona um estado ao automato
     * 
     * @param terminal Identifica se o estado é final 
     */
    public void addEstado(boolean terminal,String saida){
        estados.add(new Estado(terminal,saida));
    }
    
    /**
     * Marca um estado como sendo final
     * 
     * @param idEstado index do estado
     */
    public void setFinal(int idEstado){
        estados.get(idEstado).setFinal();
    }
    
    public void addEstado(Estado e){
        this.estados.add(e);
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
            e.addTransicao(estados.get(estadoDestino), null, saida);
        }else{
            e.addTransicao(estados.get(estadoDestino), condicao.charAt(0), saida);
        }
        
    }
    
    /**
     * Marca um estado como inicial
     * 
     * @param inicial index do estado inicial 
     */
    public void setInicial(int inicial){
        this.inicial = estados.get(inicial);
    }
    
    public void setInicial(Estado inicial){
        this.inicial = inicial;
    }
    
    /**
     * Verifica se uma cadeia é aceita pelo automato
     * 
     * @param entrada String que será verificada
     * @return boolean indicando se aceitou ou não a cadeia 
     */
    public boolean verificar(String entrada){
        Estado estadoAtual = inicial;
        caminho.clear();
        charLidos.clear();
        caminho.add(estados.indexOf(estadoAtual));
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
    protected boolean proximoEstado(Estado estadoAtual, String entrada, int posLeitura){
        int caminhoAntigo;
        if(estadoAtual.isFinal() == true && entrada.length() == posLeitura){
            return true;
        }
        //if(entrada.length() < posLeitura)return false;
        ArrayList<Transicao> trans = estadoAtual.getTransicoes();
        
        caminhoAntigo = estadoAtual.getCaminhoVazio();
        estadoAtual.setCaminhoVazio(posLeitura);
        for(int i=0;i<trans.size();i++){
            Transicao t = trans.get(i);
            caminho.add(estados.indexOf(t.getEstadoDestino()));
            charLidos.add(t.getCaracter());
            if(t.valida(posLeitura,entrada)){
                if(proximoEstado(t.getEstadoDestino(),entrada,posLeitura + t.incremento())){
                    return true;
                }
            }
            charLidos.remove(charLidos.size()-1);
            caminho.remove(caminho.size()-1);
        }
        estadoAtual.setCaminhoVazio(caminhoAntigo);
        return false;
    } 

    @Override
    public void resetar() {
        for(Estado e : estados){
            e.setCaminhoVazio(-1);
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
                gramatica.addDerivacao((char)('A' + i), derivacao + (char)('A' + estados.indexOf(t.getEstadoDestino())));
            }
            if(estados.get(i).isFinal())gramatica.addDerivacao((char)('A' + i), "");
        }
        gramatica.tornarInicial(estados.indexOf(inicial));
        return gramatica;
    }
    
    public String converterRegex(){
        int numFinais=0;
        for(Estado e : estados){
            e.juntarTransicoes();
            
        }
        
        Estado novoFinal = new Estado(true);
        for(int i=0;i<estados.size();i++){
            Estado e = estados.get(i);
            if(e.isFinal()){
                e.setFinal(false);
                e.addTransicao(novoFinal,null,"");
            }
        }
        estados.add(novoFinal);
        for(int i=0;i<estados.size();i++){
            Estado atual = estados.get(i);
            String loop = atual.getAutoTrans();
            if(atual.isFinal()==false && inicial != atual){
                ArrayList<Transicao> transOut = atual.getTransicoes();
                for(int j=0;j<estados.size();j++){
                    if(i==j)continue;
                    Estado in = estados.get(j);
                    ArrayList<Transicao> transIn = in.getTransicoes();
                    
                    for(int indexIn = 0;indexIn<transIn.size();indexIn++){
                        Transicao rin = transIn.get(indexIn);
                        if(rin.getEstadoDestino()!=atual)continue;
                        for(int indexOut = 0;indexOut<transOut.size();indexOut++){
                            Transicao rout = transOut.get(indexOut);
                            if(rout.getEstadoDestino()==atual)continue;
                            in.addExpressao(rout.getEstadoDestino(), rin.getTransicao()+loop+rout.getTransicao());
                        }
                    }
                }
                transOut.clear();
            }
        }
        for(Transicao t : inicial.getTransicoes()){
            if(t.getEstadoDestino().isFinal()){
                String loop = inicial.getAutoTrans();
                String out = t.getTransicao();
                if(out.contains("|"))out = "(" + out+")";
                return loop+out;
            }
        }
        return "";
    }

    private ArrayList<Transicao> transicoesDestino(Estado origem) {
        ArrayList<Transicao> transicoes = new ArrayList<>();
        for(int j = 0; j<estados.size();j++){
            Estado destino = estados.get(j);
            if(origem == destino)continue;
            for(Transicao t : destino.getTransicoes()){
                if(t.getEstadoDestino() == origem)transicoes.add(t);
            }
        }
        return transicoes;
    }

    public void remover(Estado e) {
        estados.remove(e);
    }
}
