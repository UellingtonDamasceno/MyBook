package br.uefs.ecomp.mybook.util;

import br.uefs.ecomp.mybook.exeptions.*;
import java.io.Serializable;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 *
 * @author Uellington Damasceno
 */
public class Grafo implements Serializable {

    private int numVertex;
    private int numAresta;
    private HashSet vertex;
    private HashSet arestas;

    public Grafo() {
        vertex = new HashSet();
        arestas = new HashSet();
    }

    public int getNumVertex() {
        return numVertex;
    }

    public void setNumVertex(int numVertex) {
        this.numVertex = numVertex;
    }

    public int getNumAresta() {
        return numAresta;
    }

    public void setNumAresta(int numAresta) {
        this.numAresta = numAresta;
    }

    public HashSet getVertex() {
        return vertex;
    }

    public void setVertex(HashSet vertex) {
        this.vertex = vertex;
    }

    public HashSet getArestas() {
        return arestas;
    }

    public void setArestas(HashSet arestas) {
        this.arestas = arestas;
    }

    public void addVertex(Object chave) {
        Vertex novaVertex = new Vertex(chave);
        vertex.put(novaVertex, null);
        numVertex++;
    }

    public Iterator vertex() throws HashVaziaException {
        Iterator lVertex;
        lVertex = vertex.iterator();
        return lVertex;
    }

    public void removeVertex(Object chave) throws EntryNaoExisteException, HashVaziaException {
        Vertex vertexOrigemRemover = new Vertex(chave);
        Vertex vertexRemover = (Vertex) vertex.getElement(vertexOrigemRemover);
        HashSet vertexAdjecencia = vertexRemover.getAdjacencia();
        Entry[] entries = vertexAdjecencia.getChaves();

        for (int i = 0; i < vertexAdjecencia.getTamanho(); i++) {
            if (entries[i] == null) {
            } else if (entries[i].getChave() == null && entries[i].getValor() == null) {
            } else {//Remove todas as adjacencias da vertice a ser removida. 
                Vertex vertexAtual = (Vertex) entries[i].getChave();
                HashSet mapaAdjecencia = vertexAtual.getAdjacencia();

                Aresta bordaRemovida = this.getBordas(vertexOrigemRemover, vertexAtual);
                arestas.remove(bordaRemovida);

                mapaAdjecencia.remove(vertexRemover);
            }
        }
        numVertex--;
    }

    private Aresta getBordas(Vertex vertexA, Vertex vertexB) throws EntryNaoExisteException {
        Vertex a = new Vertex(vertexA);
        Vertex b = new Vertex(vertexB);
        Aresta borda = new Aresta(a, b, 0);
        if (arestas.contem(borda)) {
            Aresta aux = (Aresta) arestas.getElement(borda);
            return aux;
        }
        throw new EntryNaoExisteException();
    }

    public Aresta addAresta(Object vertexA, Object vertexB, int pesoAfinidade) throws EntryNaoExisteException, LoopNaoPermitidoException, VertexNaoExisteException {
        Vertex aVertex = new Vertex(vertexA);
        Vertex bVertex = new Vertex(vertexB);
        Vertex pVertex = (Vertex) vertex.getElement(aVertex);
        Vertex sVertex = (Vertex) vertex.getElement(bVertex);
        Aresta novaAresta = new Aresta(pVertex, sVertex, pesoAfinidade);

        if (aVertex.equals(bVertex)) {
            throw new LoopNaoPermitidoException();
        }
        if (!vertex.contem(aVertex) || !vertex.contem(bVertex)) {
            throw new VertexNaoExisteException();
        }
        if (!pVertex.getAdjacencia().contem(sVertex)) {
            pVertex.getAdjacencia().put(sVertex, novaAresta);
        }
        if (!sVertex.getAdjacencia().contem(pVertex)) {
            sVertex.getAdjacencia().put(pVertex, novaAresta);
        }

        if (!arestas.contem(novaAresta)) {
            arestas.put(novaAresta, null);
        }
        return novaAresta;
    }

    public Object getChave(Object chave) throws EntryNaoExisteException {
        return ((Vertex) this.getOneVertex(chave)).getConteudo();
    }

    private Vertex getOneVertex(Object chave) throws EntryNaoExisteException {
        Vertex procurada = new Vertex(chave);
        procurada = (Vertex) vertex.getElement(procurada);
        return procurada;
    }

    public HashSet caminhoMaisCurto(Object chaveInicial) throws HashVaziaException, EntryNaoExisteException {
        HashSet caminhoMaisCurto = new HashSet();
        PriorityQueue fila = new PriorityQueue<>();

        Vertex vertexAtual = (Vertex) this.getOneVertex(chaveInicial);
        fila.add(vertexAtual);
        while (!fila.isEmpty()) {
            EntryDjCastra vertexAnterior = (EntryDjCastra) fila.remove();
            vertexAtual = (Vertex) vertexAnterior.getAtual();

            if (!caminhoMaisCurto.contem(vertexAtual)) {
                caminhoMaisCurto.put(vertexAtual, vertexAnterior);
            }

            Iterator lAdjacencias = vertexAtual.getAdjacencia().iterator();
            while (lAdjacencias.hasNext()) {
                Entry vertexBorda = (Entry) lAdjacencias.next();
                if (vertexBorda != null) {
                    Vertex u = (Vertex) vertexBorda.getChave();

                    int distanciaSup = (int) ((Aresta) vertexBorda.getValor()).getPesoAfinidade();
                    distanciaSup += vertexAnterior.getDistancia();

                    EntryDjCastra entryDjCastraSup;
                    try {
                        entryDjCastraSup = (EntryDjCastra) ((Entry) caminhoMaisCurto.getElement(u)).getChave();
                        if (distanciaSup < entryDjCastraSup.getDistancia()) {
                            fila.add(new EntryDjCastra(u, vertexAtual, distanciaSup));
                        }
                    } catch (EntryNaoExisteException ex) {
                        fila.add(new EntryDjCastra(u, vertexAtual, distanciaSup));
                    }
                }
            }
        }
        return caminhoMaisCurto;
    }

    public void imprme() {
        //System.out.println(this.getArestas());
        System.out.println(this.getVertex());
    }
}
