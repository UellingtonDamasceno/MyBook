/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uefs.ecomp.mybook.util;

import java.util.Objects;

/**
 *
 * @author Uellington Damasceno
 */
public class Vertex {
    
    private Object conteudo;
    private HashSet adjacencia;
    
    public Vertex(Object obj){
        this.conteudo = obj;
        this.adjacencia = new HashSet();
    }

    public Object getConteudo() {
        return conteudo;
    }

    public void setConteudo(Object conteudo) {
        this.conteudo = conteudo;
    }

    public HashSet getAdjacencia() {
        return adjacencia;
    }

    public void setAdjacencia(HashSet adjacencia) {
        this.adjacencia = adjacencia;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + Objects.hashCode(this.conteudo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Vertex){
            Vertex outra = (Vertex) obj;
            if(this.conteudo.equals(outra.getConteudo())){
                return true;
            }
        }
        return false;
    }
}
