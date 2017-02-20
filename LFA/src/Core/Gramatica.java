/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Core;

import java.util.ArrayList;

/**
 *  Classe que representa uma gramatica e verifica se uma cadeia é reconhecida
 * pela mesma.
 * Todos os reconhecedores (Automato e Gramatica) implementam a interface
 * ReceonhecedorCadeia que é usada pela GUI de multiplas entradas.
 * @author Matheus Prachedes Batista & Eymar Ferrario de Lima
 */
public class Gramatica implements ReconhecedorCadeia{
    /**
     * Vetor que representa os não terminais da gramatica, a não terminal inicialç
     * é o não terminal que está na primeira posição do vetor
     */
    private ArrayList<NaoTerminal> naoTerminais;
    
    /**
     * Vetor usado para impedir loops infinitos durante a verificação de uma
     * cadeia por exemplo o caso: S->A, A->S
     */
    private ArrayList<Integer> tamanhosAntigos; 
    

    
    public Gramatica(){
        naoTerminais = new ArrayList<NaoTerminal>();
        tamanhosAntigos = new ArrayList<Integer>();
    }
    
    
    public void clear(){
        naoTerminais.clear();
        tamanhosAntigos.clear();
    }
    
    public ArrayList<NaoTerminal> getNaoTerminais(){
        return naoTerminais;
    }
    
    public void addDerivacao(char naoTerminal, String derivacao){
        int indexNaoTerminal = buscaNaoTerminal(naoTerminal);
        if(indexNaoTerminal == -1){
            naoTerminais.add(new NaoTerminal(naoTerminal,derivacao));
            tamanhosAntigos.add(-1);
        }
        else naoTerminais.get(indexNaoTerminal).addDerivacao(derivacao);
    }
    
    public void addNaoTerminal(char naoTerminal, String derivacao){
        NaoTerminal nt = new NaoTerminal(naoTerminal);
        nt.addDerivacao(derivacao);
        naoTerminais.add(nt);
        tamanhosAntigos.add(-1);
    }
    
    public void addNaoTerminal(char naoTerminal) {
        naoTerminais.add(new NaoTerminal(naoTerminal));
        tamanhosAntigos.add(-1);
    }
    
    public boolean verificar(String entrada){
        if(recursao(entrada,String.valueOf(naoTerminais.get(0).getNaoTerminal()),0)){
            return true;
        }
        return false;
    }

    public int buscaNaoTerminal(char naoTerminal){
        for(int i=0;i<naoTerminais.size();i++){
            NaoTerminal nt = naoTerminais.get(i);
            if(nt.getNaoTerminal() == naoTerminal)return i;
        }
        return -1;
    }
    /**
     * Função recursiva que verifica se a gramatica reconhece a string dada
     * 
     * @param cadeia Cadeia a ser reconhecida
     * @param cadeiaAtual Cadeia atual, pode possui um não terminal
     * @param indexNaoTerminal index da posição do não terminal na cadeiaAtual
     * @return 
     */
    private boolean recursao(String cadeia, String cadeiaAtual, int indexNaoTerminal) {
        //Caso a cadeia seja igual, retorna true, cadeia reconhecida
        if(cadeia.equals(cadeiaAtual))return true;
        //Caso o inicio e o fim da cadeia não bata, retonar false, as derivações escolhidas até agora estão erradas.
        if(verificarIgualdade(cadeia,cadeiaAtual,indexNaoTerminal)==false)return false;
        int naoTerminal = buscaNaoTerminal(cadeiaAtual.charAt(indexNaoTerminal));
        int tamanhoAntigo = tamanhosAntigos.get(naoTerminal);
        NaoTerminal nt =  naoTerminais.get(naoTerminal); 
        //Caso eu esteja voltando para o mesmo não terminal sem aumentar o tamanho da cadeia retorna falso
        //pois o algoritmo está andando em circulo ex: S -> A e A -> S 
        if(tamanhoAntigo == cadeiaAtual.length())return false;
        ArrayList<Derivacao> derivacoes = nt.getDerivacoes();
        tamanhosAntigos.set(naoTerminal, cadeiaAtual.length());
        for(Derivacao d : derivacoes){
            String novaCadeia = cadeiaAtual.replace(String.valueOf(nt.getNaoTerminal()), d.getDerivacao());
            //a nova cadeia é a cadeiaAtual mudando o naoTerminal pela derivacao
            //o novo indexNaoTerminal é a soma do valor do indexNaoTerminal atual
            //Mais o valor da soma do indexNaoTerminal dentro da string de derivação
            if(d.getIndexNaoTerminal() == -1){
                if(cadeia.equals(novaCadeia))return true;
            }
            else{
                if(recursao(cadeia,novaCadeia,indexNaoTerminal + d.getIndexNaoTerminal())){
                    return true;
                }
            }
        }
        tamanhosAntigos.set(naoTerminal, tamanhoAntigo);
        return false;
    }
    
    /**
     * Verifica se o inicio e o fim da cadeiaAtual e a cadeia que está sendo testada
     * são iguais, usando o nãoTerminal como sendo um ponto de separação entre o 
     * inicio e o fim
     * 
     * @param cadeia
     * @param cadeiaAtual
     * @param indexNaoTerminal
     * @return 
     */
    private boolean verificarIgualdade(String cadeia, String cadeiaAtual, int indexNaoTerminal) {
        String esquerda="",direita="";
        for(int i=0;i<indexNaoTerminal;i++){
            esquerda+=cadeiaAtual.charAt(i);
        }
        for(int i=indexNaoTerminal+1;i<cadeiaAtual.length();i++){
            direita+=cadeiaAtual.charAt(i);
        }
        if(!cadeia.startsWith(esquerda))return false;
        if(!cadeia.endsWith(direita))return false;
        return true;
    }

    @Override
    public void resetar() {
        for(int i=0;i<this.tamanhosAntigos.size();i++){
            tamanhosAntigos.set(i, -1);
        }
    }

    public Core.AutomatoFinito converterAF() {
        Core.AutomatoFinito automato = new AutomatoFinito();
        for(NaoTerminal nt : naoTerminais){
            automato.addEstado(false, "");
        }
        automato.setInicial(0);
        automato.addEstado(true, "");
        
        for(int i=0;i<naoTerminais.size();i++){
            NaoTerminal nt = naoTerminais.get(i);
            for(Derivacao d : nt.getDerivacoes()){
                
                //Derivacao não possui não terminal
                if(d.getIndexNaoTerminal() == -1){
                    //Derivação não é vazia
                    if(d.getDerivacao().length() == 1){
                        automato.addTransicao(i,naoTerminais.size(),d.getDerivacao(),"");
                    }else{
                        automato.addTransicao(i, naoTerminais.size(),"λ", "");
                    }
                }
                else{
                    //Se a derivação possui um terminal
                    if(d.getDerivacao().length() > 1){
                        automato.addTransicao(i, indexNaoTerminal(d), d.getDerivacao(),"");
                    }else{
                        automato.addTransicao(i, indexNaoTerminal(d),"λ","");
                    }
                }
            }
        }
        return automato;
    }

    private int indexNaoTerminal(Derivacao d) {
        for(int i=0;i<naoTerminais.size();i++){
            NaoTerminal nt = naoTerminais.get(i);
            if(nt.getNaoTerminal() == d.getDerivacao().charAt(d.getIndexNaoTerminal())){
                return i;
            }
        }
        return -1;
    }

    public void tornarInicial(int inicial) {
        NaoTerminal novoInicial = naoTerminais.get(inicial);
        naoTerminais.set(inicial, naoTerminais.get(0));
        naoTerminais.set(0, novoInicial);
    }

   

    
}
