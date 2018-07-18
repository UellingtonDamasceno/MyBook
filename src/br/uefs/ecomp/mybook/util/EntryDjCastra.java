package br.uefs.ecomp.mybook.util;

import java.io.Serializable;

/**
 *
 * @author Uellington Damasceno e Ivanildo Gomes
 */
public class EntryDjCastra implements Comparable, Serializable{
    private Vertex ant;
    private Vertex atual;
    private int distancia;
    
    public EntryDjCastra(Vertex ant, Vertex atual, int distancia){
        this.ant = ant;
        this.atual = atual;
        this.distancia = distancia;
    }

    public Vertex getAnt() {
        return ant;
    }

    public void setAnt(Vertex ant) {
        this.ant = ant;
    }

    public Vertex getAtual() {
        return atual;
    }

    public void setAtual(Vertex atual) {
        this.atual = atual;
    }
    
    public int getDistancia(){
        return distancia;
    }
    
    @Override
    public int compareTo(Object o) {
        EntryDjCastra outra = (EntryDjCastra) o;
        return this.distancia - outra.distancia;
    }   
}
