package br.uefs.ecomp.mybook.facade;

import br.uefs.ecomp.mybook.controller.*;
import br.uefs.ecomp.mybook.exeptions.*;
import br.uefs.ecomp.mybook.model.*;
import br.uefs.ecomp.mybook.util.Grafo;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import javafx.collections.ObservableList;

/**
 *
 * @author Uellington Damasceno
 */
public class Facade {
    
    private static Facade facadeMyBook;
    
    private final ControllerArquivos ca;
    private final ControllerUser cu;
    private final ControllerPost cp;
    private final ControllerGrafo cg;
    private final ControllerComentario cc;
    private final ControllerSolicitacao cs;

    private Facade(){
        ca = new ControllerArquivos();
        cu = new ControllerUser();
        cp = new ControllerPost();
        cg = new ControllerGrafo();
        cc = new ControllerComentario();
        cs = new ControllerSolicitacao();
    }
    
    public static synchronized Facade getInstance(){
        return facadeMyBook == null ? new Facade() : facadeMyBook;
    }
       
    public ObservableList carregarPost(User user) throws ListaConteudoVazia{
        LinkedList<Post> postagens = ca.lerTodosArquivosDiretorio(user.caminhoPostagem(), ".txt");
        return cp.carregarPost(postagens);
    }
    
    public ObservableList carregaListaAmigos(User user) throws ListaConteudoVazia, HashVaziaException, EntryNaoExisteException{
        LinkedList<User> amigos = cg.getListaAmigo(user);
        return cu.carregaAmigos(amigos);
    }
    
    public void addNovoUser(String login, String senha, String nome,
                            String email, String dataN, String endereco, 
                            String telefone) throws UserJaExisteExceptions, AtributoVazioException{
        User user = cu.validaUser(login, senha, nome, email, dataN, endereco, telefone);
        Grafo atualiza = cg.addNovoUser(user);        
    }
    
    public void addAmigo(User user, User novoAmigo) throws EntryNaoExisteException, LoopNaoPermitidoException, VertexNaoExisteException, DadoRepetidoExcepetion{
        cu.addAmigo(novoAmigo);
        Grafo atualiza = cg.criaVinculoAmizade(user, novoAmigo);
    }
    
    public void removerAmigo(User amigoRemovido) throws EntryNaoExisteException, HashVaziaException{
        cu.removerAmigo(amigoRemovido);
        Grafo atualiza = cg.removeAmigo(amigoRemovido);
    }
    
    public void publicarNovoPost(User autor, String url, String tituloCabecalho, PostType tipoPost) throws IOException{
        Post novoPost = cp.publicarNovoPost(autor, url, tituloCabecalho, tipoPost);
        ca.escreveObjeto(novoPost.getAutor().caminhoPostagem(), novoPost.toString(), novoPost);
    }
    
    public Iterator listarAmigos(User user) throws HashVaziaException{
        return cg.listarAmigos(user);
    }
    
    public Iterator caminhoMaisCurto(User user) throws HashVaziaException, EntryNaoExisteException{
        return cg.caminhoMaisCurto(user);
    }
    
}
