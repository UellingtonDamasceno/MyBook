package br.uefs.ecomp.mybook.controller;

import br.uefs.ecomp.mybook.exeptions.AmizadeJaExisteException;
import br.uefs.ecomp.mybook.exeptions.UserJaExisteExceptions;
import br.uefs.ecomp.mybook.exeptions.EntryNaoExisteException;
import br.uefs.ecomp.mybook.exeptions.HashVaziaException;
import br.uefs.ecomp.mybook.exeptions.LoopNaoPermitidoException;
import br.uefs.ecomp.mybook.exeptions.VertexNaoExisteException;
import br.uefs.ecomp.mybook.model.*;
import br.uefs.ecomp.mybook.util.Aresta;
import br.uefs.ecomp.mybook.util.Grafo;
import br.uefs.ecomp.mybook.util.HashSet;
import br.uefs.ecomp.mybook.util.Vertex;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author Uellington Damasceno
 */
public class ControllerGrafo {

    Grafo redeSocial;
    //Map<Vertex, Map<Vertex, Aresta>> rede = new HashMap<>();

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

    public Grafo atualizarVinculoAmizade(User userPost, User userOn, int valor) throws EntryNaoExisteException{
        Vertex a = new Vertex(userPost);
        Vertex b = new Vertex(userOn);
        ((Aresta)redeSocial.getArestas().get(new Aresta(a, b))).atualizaAfinidade(valor);
        return redeSocial;
    }
    
    public void validaAmizade(User a, User b) throws AmizadeJaExisteException{
        Vertex vertexA = new Vertex(a);
        Vertex vertexB = new Vertex(b);
        Aresta vinculo = new Aresta(vertexA, vertexB);
        if(redeSocial.getArestas().contem(vinculo)){
            throw new AmizadeJaExisteException();
        }
    }
    
    public LinkedList<User> getListaAmigo(User user) throws HashVaziaException, EntryNaoExisteException {
        return ((Vertex) redeSocial.getVertex().get(user)).getAdjacencia().toList();
    }

    public Iterator listarAmigos(User user) throws HashVaziaException {
        return redeSocial.getArestas().iterator();
    }

    public Iterator caminhoMaisCurto(Object inicio) throws HashVaziaException, EntryNaoExisteException {
        return redeSocial.caminhoMaisCurto(inicio).iterator();
    }

    public LinkedList getCaminhoPostagemAmigos(User user) throws EntryNaoExisteException, HashVaziaException {
        LinkedList<String> diretorioPostagem = new LinkedList();
        for (User amigo : getListaAmigo(user)) {
            diretorioPostagem.add(amigo.caminhoPostagem());
        }
        return diretorioPostagem;
    }
}
