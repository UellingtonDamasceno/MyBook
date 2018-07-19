package br.uefs.ecomp.mybook.util;

import java.util.Objects;

/**
 *
 * @author Uellington Damasceno
 */
public class Aresta {
    private final Vertex A;
    private final Vertex B;
    private int pesoAfinidade;
    
    public Aresta(Vertex a, Vertex b){
        A = a;
        B = b;
    }
    
    public Aresta(Vertex a, Vertex b, int distancia){
        this(a, b);
        this.pesoAfinidade = distancia;
    }
    
    public Vertex getA() {
        return A;
    }

    public Vertex getB() {
        return B;
    }

    public int getPesoAfinidade() {
        return pesoAfinidade;
    }
        
    public void atualizaAfinidade(int valor){
        pesoAfinidade += valor;
    }
    
    @Override
    public boolean equals(Object o){
        if(o instanceof Aresta){
            Aresta outra = (Aresta) o;
            if((outra.getA() == null && outra.getB() == null) && (this.getA() == null && this.getB() == null)){
                return true;
            }else{
                return (this.hashCode() == outra.hashCode());
            }
        }
        return false;
    }


    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.A);
        hash = 79 * hash + Objects.hashCode(this.B);
        return hash;
    }
    
    public String toString(){
        return (A.toString() + " " + this.getPesoAfinidade() + " " + B.toString());
    }
}
