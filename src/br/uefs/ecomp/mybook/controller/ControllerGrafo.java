package br.uefs.ecomp.mybook.controller;

import br.uefs.ecomp.mybook.exeptions.UserJaExisteExceptions;
import br.uefs.ecomp.mybook.exeptions.EntryNaoExisteException;
import br.uefs.ecomp.mybook.exeptions.HashVaziaException;
import br.uefs.ecomp.mybook.exeptions.LoopNaoPermitidoException;
import br.uefs.ecomp.mybook.exeptions.VertexNaoExisteException;
import br.uefs.ecomp.mybook.model.*;
import br.uefs.ecomp.mybook.util.Aresta;
import br.uefs.ecomp.mybook.util.Grafo;
import br.uefs.ecomp.mybook.util.Vertex;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/**
 *
 * @author Uellington Damasceno
 */
public class ControllerGrafo {

    Grafo redeSocial;
    Map<Vertex, Map<Vertex, Aresta>> rede = new HashMap<>();

    public ControllerGrafo() {
        redeSocial = new Grafo();
    }

    public Grafo criaVinculoAmizade(User user, User amigo) throws EntryNaoExisteException, LoopNaoPermitidoException, VertexNaoExisteException {
//        Vertex vertexA = new Vertex(user);
//        Vertex vertexB = new Vertex(amigo);
//        
//        
//        rede.put(vertexA, new HashMap<>());
//        rede.put(vertexB, new HashMap<>());
//        
//        rede.get(vertexA).put(vertexB, new Aresta(vertexA, vertexB, 0));
//        rede.get(vertexB).put(vertexA, new Aresta(vertexB, vertexA, 0));
        redeSocial.addBorda(user, amigo, 0);
        return redeSocial;
    }

    public Grafo removeAmigo(User amigoRemovido) throws EntryNaoExisteException, HashVaziaException {
        Vertex vertexAremover = new Vertex(amigoRemovido);
        
        redeSocial.removeVertex(amigoRemovido);
        return redeSocial;
    }

    public Grafo addNovoUser(User novo) throws UserJaExisteExceptions {
        if (!redeSocial.getVertex().contem(novo)) {
            redeSocial.addVertex(novo);
        } else {
            throw new UserJaExisteExceptions();
        }
        return redeSocial;
    }

    public LinkedList getListaAmigo(User user) throws HashVaziaException, EntryNaoExisteException {
        return ((Vertex) redeSocial.getVertex().get(user)).getAdjacencia().toList();
    }

    public Iterator listarAmigos(User user) throws HashVaziaException {
        return redeSocial.getArestas().iterator();
    }

    public Iterator caminhoMaisCurto(Object inicio) throws HashVaziaException, EntryNaoExisteException {
        return redeSocial.caminhoMaisCurto(inicio).iterator();
    }
}
