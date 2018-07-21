package br.uefs.ecomp.mybook.util;

import java.io.Serializable;

/**
 *
 * @author Uellington Damasceno
 */
public class Vertex implements Serializable{
    
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
        return conteudo.hashCode();
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
    
    @Override
    public String toString(){
        return conteudo.toString();
    }
}
