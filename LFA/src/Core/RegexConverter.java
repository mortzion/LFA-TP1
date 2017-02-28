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
public class RegexConverter {
    
    public AutomatoFinito converter(String entrada){
        Estado inicioCaminhoAtual = new Estado(true);
        Estado estadoAtual = inicioCaminhoAtual;
        AutomatoFinito resultado = new AutomatoFinito(inicioCaminhoAtual);
        int posLeitura = 0;
        while(posLeitura < entrada.length()){
            if(charAt(entrada,posLeitura) == '('){
                String novaEntrada = lerParent(entrada,posLeitura+1);
                AutomatoFinito autRecur = converter(novaEntrada);
                posLeitura = posLeitura + novaEntrada.length()+2;
                juntarInicial(estadoAtual,autRecur);
                Estado terminal = recuperarFinal(autRecur);
                    
                if(charAt(entrada,posLeitura) == '*'){
                    terminal.addTransicao(estadoAtual, null, "");
                    estadoAtual.addTransicao(terminal, null, "");
                    posLeitura++;
                }
                estadoAtual.setFinal(false);
                estadoAtual = terminal;
                mergeEstados(resultado,autRecur);
                continue;
            }
            if(charAt(entrada,posLeitura) == '|'){
                inicioCaminhoAtual = estadoAtual = pipe(inicioCaminhoAtual,resultado);
            }else{
                if(charAt(entrada,posLeitura+1)=='*'){
                    estadoAtual.addTransicao(estadoAtual, charAt(entrada,posLeitura),"");
                    posLeitura++;
                }else{
                    Estado novoEstado = new Estado(true);
                    estadoAtual.setFinal(false);
                    estadoAtual.addTransicao(novoEstado, charAt(entrada,posLeitura), "");
                    estadoAtual = novoEstado;
                    resultado.addEstado(novoEstado);
                }
            }
            posLeitura++;
        }
        juntarFinais(resultado);
        return resultado;
    }

    private char charAt(String entrada, int i){
        if(i >= entrada.length())return 0;
        return entrada.charAt(i);
    }
    
    private String lerParent(String entrada, int i) {
        char atual;
        int cont = 1;
        String novaString = "";
        while(true){
            atual = charAt(entrada,i++);
            if(atual == '(')cont++;
            else if(atual == ')')cont--;
            if(cont == 0)return novaString;
            novaString+=atual;
        }
    }

    private Estado pipe(Estado inicioCaminho, AutomatoFinito resultado) {
        Estado novoEstado = new Estado(true);
        inicioCaminho.addTransicao(novoEstado,null,"");
        resultado.addEstado(novoEstado);
        return novoEstado;
        
    }

    private Estado caracter(Estado estadoAtual,String entrada, int posLeitura) {
        if(charAt(entrada,posLeitura+1)=='*'){
            estadoAtual.addTransicao(estadoAtual, charAt(entrada,posLeitura),"");
            return estadoAtual;
        }else{
            Estado novoEstado = new Estado(true);
            estadoAtual.setFinal(false);
            estadoAtual.addTransicao(novoEstado, charAt(entrada,posLeitura), "");
            return novoEstado;
        }
    }

    private void juntarFinais(AutomatoFinito resultado) {
        int cont=0;
        for(Estado e : resultado.getEstados()){
            if(e.isFinal())cont++;
            if(cont>1)continue;
        }
        if(cont == 1)return;
        Estado novoFinal = new Estado(true);
        for(Estado e : resultado.getEstados()){
            if(e.isFinal()){
                e.setFinal(false);
                e.addTransicao(novoFinal, null,"");
            }
        }
        resultado.addEstado(novoFinal);
    }

    private void juntarInicial(Estado estadoAtual, AutomatoFinito autRecur) {
        Estado inicial = autRecur.inicial;
        for(Transicao t : inicial.getTransicoes()){
            if(t.getEstadoDestino() == inicial)estadoAtual.addTransicao(estadoAtual,null,"");
            else estadoAtual.addTransicao(t.getEstadoDestino(), t.getCaracter(), "");
        }
        for(Estado e : autRecur.getEstados()){
            for(Transicao t : e.getTransicoes()){
                if(t.getEstadoDestino() == inicial)t.setEstadoDestino(estadoAtual);
            }
        }
        autRecur.remover(inicial);
    }

    private Estado recuperarFinal(AutomatoFinito autRecur) {
        for(int i=autRecur.estados.size()-1;i>=0;i--){
            Estado e = autRecur.getEstados().get(i);
            if(e.isFinal())return e;
        }
        return null;
    }

    private void mergeEstados(AutomatoFinito resultado, AutomatoFinito autRecur) {
        for(Estado e : autRecur.estados){
            resultado.addEstado(e);
        }
    }
            
            
}
