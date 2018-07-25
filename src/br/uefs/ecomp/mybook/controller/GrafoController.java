package br.uefs.ecomp.mybook.controller;

import br.uefs.ecomp.mybook.exeptions.EmailOuSenhaInvalidoException;
import br.uefs.ecomp.mybook.exeptions.SemUsuariosCadastradosException;
import br.uefs.ecomp.mybook.exeptions.AmizadeJaExisteException;
import br.uefs.ecomp.mybook.exeptions.UserJaExisteExceptions;
import br.uefs.ecomp.mybook.exeptions.EntryNaoExisteException;
import br.uefs.ecomp.mybook.exeptions.HashVaziaException;
import br.uefs.ecomp.mybook.exeptions.LoopNaoPermitidoException;
import br.uefs.ecomp.mybook.exeptions.VertexNaoExisteException;
import br.uefs.ecomp.mybook.model.*;
import br.uefs.ecomp.mybook.util.Aresta;
import br.uefs.ecomp.mybook.util.Grafo;
import br.uefs.ecomp.mybook.util.Vertex;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author Uellington Damasceno
 */
public class GrafoController {

    private Grafo redeSocial;
    
    public GrafoController() {
        redeSocial = new Grafo();
    }
    
    public void carregarGrafo(Grafo grafo){
        this.redeSocial = grafo;
    }
    
    public Grafo criaVinculoAmizade(User user, User amigo) throws LoopNaoPermitidoException, VertexNaoExisteException, EntryNaoExisteException {
        Aresta a = redeSocial.addAresta(user, amigo, 0);
        return redeSocial;
    }

    public Grafo removeAmigo(User amigoRemovido) throws EntryNaoExisteException, HashVaziaException {
        redeSocial.removeVertex(amigoRemovido);
        return redeSocial;
    }

    public Grafo addNovoUser(User novo) throws UserJaExisteExceptions {
        if (!redeSocial.getVertex().contem(new Vertex(novo))) {
            redeSocial.addVertex(novo);
        } else {
            throw new UserJaExisteExceptions();
        }
        return redeSocial;
    }

    public Grafo atualizarVinculoAmizade(User userPost, User userOn, int valor) throws EntryNaoExisteException {
        Vertex a = new Vertex(userPost);
        Vertex b = new Vertex(userOn);
        ((Aresta) redeSocial.getArestas().get(new Aresta(a, b))).atualizaAfinidade(valor);
        return redeSocial;
    }

    public void validaAmizade(User a, User b) throws AmizadeJaExisteException {
        Vertex vertexA = new Vertex(a);
        Vertex vertexB = new Vertex(b);
        Aresta vinculo = new Aresta(vertexA, vertexB);
        if (redeSocial.getArestas().contem(vinculo)) {
            throw new AmizadeJaExisteException();
        }
    }

    public LinkedList<User> getListaAmigo(User user) throws HashVaziaException, EntryNaoExisteException {
        return ((Vertex) redeSocial.getVertex().getElement(new Vertex(user))).getAdjacencia().toList();
    }

    public Iterator listarAmigos(User user) throws HashVaziaException, EntryNaoExisteException {
        return getListaAmigo(user).iterator();
    }

    public Iterator caminhoMaisCurto(Object inicio) throws HashVaziaException, EntryNaoExisteException {
        return redeSocial.caminhoMaisCurto(new Vertex(inicio)).iterator();
    }

    public LinkedList getCaminhoPostagemAmigos(User user) throws EntryNaoExisteException, HashVaziaException {
        LinkedList<String> diretorioPostagem = new LinkedList();
        for (User amigo : getListaAmigo(user)) {
            diretorioPostagem.add(amigo.caminhoPostagem());
        }
        return diretorioPostagem;
    }

    public Grafo redeSocial() {
        return redeSocial;
    }
    
    public User verificaCredenciais(User user) throws EntryNaoExisteException, EmailOuSenhaInvalidoException, SemUsuariosCadastradosException {
        if(redeSocial.estaVazio()){
            throw new SemUsuariosCadastradosException();
        }
        Vertex pontoUser = new Vertex(user);
        if(redeSocial.getVertex().contem(pontoUser)){
            Vertex userRede = (Vertex)redeSocial.getVertex().getElement(pontoUser);
            User verificado = (User) userRede.getConteudo();
            if(verificado.getSenha().equals(user.getSenha())){
                return verificado;
            }
        }
        throw new EmailOuSenhaInvalidoException();
    }
}