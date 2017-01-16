/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Core;

import java.util.ArrayList;

/**
 *
 * @author Matheus Prachedes Batista
 */
public class Gramatica implements ReconhecedorCadeia{
    private ArrayList<NaoTerminal> naoTerminais;
    private char naoTerminalInicial;
    private ArrayList<Integer> tamanhosAntigos; 
            
    public Gramatica(){
        naoTerminais = new ArrayList<NaoTerminal>();
        tamanhosAntigos = new ArrayList<Integer>();
    }
    
    
    public void clear(){
        naoTerminais.clear();
        tamanhosAntigos.clear();
    }
    
    public void setInicial(char inicial){
        this.naoTerminalInicial = inicial;
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
    
    
    public boolean verificar(String entrada){
        return recursao(entrada,String.valueOf(naoTerminais.get(0).getNaoTerminal()),0);
    }

    public int buscaNaoTerminal(char naoTerminal){
        for(int i=0;i<naoTerminais.size();i++){
            NaoTerminal nt = naoTerminais.get(i);
            if(nt.getNaoTerminal() == naoTerminal)return i;
        }
        return -1;
    }
    
    private boolean recursao(String cadeia, String cadeiaAtual, int indexNaoTerminal) {
        //Caso a cadeia seja igual, retorna true, cadeia reconhecida
        if(cadeia.equals(cadeiaAtual))return true;
        //Caso o inicio e o fim da cadeia não bata, retonar false, as derivações escolhidas até agora estão erradas.
        if(verificarIgualdade(cadeia,cadeiaAtual,indexNaoTerminal)==false)return false;
        int naoTerminal = buscaNaoTerminal(cadeiaAtual.charAt(indexNaoTerminal));
        int tamanhoAntigo = tamanhosAntigos.get(naoTerminal);
        NaoTerminal nt =  naoTerminais.get(naoTerminal); 
        //Caso eu esteja voltando para o mesmo não terminal sem aumentar o tamanho da cadeia retorna falso
        //pois o algoritmo está andando em circulo ex: S -> S (S deriva em S)
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

    private boolean verificarIgualdade(String cadeia, String cadeiaAtual, int indexNaoTerminal) {
        String esquerda="",direita="";
        for(int i=0;i<indexNaoTerminal;i++){
            esquerda+=cadeiaAtual.charAt(i);
        }
        for(int i=cadeiaAtual.length()-1;i>indexNaoTerminal;i--){
            direita+=cadeiaAtual.charAt(i);
        }
        if(!cadeia.startsWith(esquerda))return false;
        if(!cadeia.endsWith(direita))return false;
        return true;
//        if(cadeia.length() < indexNaoTerminal)return false;
//        for(int i=0;i<indexNaoTerminal;i++){
//            if(cadeia.charAt(i) != cadeiaAtual.charAt(i))return false;
//        }
//        for(int i=cadeia.length()-1;i>indexNaoTerminal;i--){
//            if(cadeia.charAt(i) != cadeiaAtual.charAt(i))return false;
//        }
//        return true;
    }

    @Override
    public void resetar() {
        for(int i=0;i<this.tamanhosAntigos.size();i++){
            tamanhosAntigos.set(i, -1);
        }
    }
    
}
