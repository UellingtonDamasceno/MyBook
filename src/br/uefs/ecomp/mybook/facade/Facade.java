package br.uefs.ecomp.mybook.facade;

import br.uefs.ecomp.mybook.exeptions.AtributoVazioException;
import br.uefs.ecomp.mybook.exeptions.ArquivoInexistenteException;
import br.uefs.ecomp.mybook.exeptions.AmizadeJaExisteException;
import br.uefs.ecomp.mybook.exeptions.SolicitacaoInexistenteException;
import br.uefs.ecomp.mybook.exeptions.*;
import br.uefs.ecomp.mybook.controller.*;
import br.uefs.ecomp.mybook.model.*;
import br.uefs.ecomp.mybook.util.Grafo;
import java.io.IOException;
import java.io.Serializable;
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
    private final ControllerSolicitacao cs;

    private Facade() {
        ca = new ControllerArquivos();
        cu = new ControllerUser();
        cp = new ControllerPost();
        cg = new ControllerGrafo();
        cs = new ControllerSolicitacao();
    }

    public static synchronized Facade getInstance() {
        return facadeMyBook == null ? new Facade() : facadeMyBook;
    }

    //**********************************Ações iniciais
    public ObservableList carregarPost(User user) throws ListaConteudoVazia {
        LinkedList<Post> postagens = (LinkedList) ca.lerTodosArquivosDiretorio(user.caminhoPostagem(), ".txt");
        return cp.carregarPost(postagens);
    }

    public ObservableList carregaListaAmigos(User user) throws ListaConteudoVazia, HashVaziaException, EntryNaoExisteException {
        LinkedList<User> amigos = cg.getListaAmigo(user);
        return cu.carregaAmigos(amigos);
    }

    public ObservableList carregarSolicitacoesAmizade(User user) throws ListaConteudoVazia {
        LinkedList<SolicitacaoAmizade> solicitacoes = (LinkedList) ca.lerTodosArquivosDiretorio(user.getCaminhoSolicitacao(), ".txt");
        return cs.carregarSolicitacoes(solicitacoes);
    }

    //***************************************Ações relacionadas ao grafo
    public void addNovoUser(String login, String senha, String nome,
            String email, String dataN, String endereco,
            String telefone) throws UserJaExisteExceptions, AtributoVazioException {
        User user = cu.validaUser(login, senha, nome, email, dataN, endereco, telefone);
        Grafo atualiza = cg.addNovoUser(user);
    }

    public Iterator listarAmigos(User user) throws HashVaziaException {
        return cg.listarAmigos(user);
    }

    public Iterator caminhoMaisCurto(User user) throws HashVaziaException, EntryNaoExisteException {
        return cg.caminhoMaisCurto(user);
    }

    //************************************Ações relacionadas ao usuario
    public void addAmigo(User user, User novoAmigo) throws EntryNaoExisteException, LoopNaoPermitidoException, VertexNaoExisteException, DadoRepetidoException {
        cu.addAmigo(novoAmigo);
        Grafo atualiza = cg.criaVinculoAmizade(user, novoAmigo);
    }

    public void removerAmigo(User amigoRemovido) throws EntryNaoExisteException, HashVaziaException {
        cu.removerAmigo(amigoRemovido);
        Grafo atualiza = cg.removeAmigo(amigoRemovido);
    }

    //***********************************Ações relacionadas ao Post
    public void publicarNovoPost(User autor, String url, String tituloCabecalho, PostType tipoPost) throws IOException, PostJaExisteException {
        Post novoPost = cp.publicarNovoPost(autor, url, tituloCabecalho, tipoPost, ControllerData.getDateTime());
        ca.escreveObjeto(novoPost.getAutor().caminhoPostagem(), novoPost.toString(), novoPost);
    }

    public boolean removerPost(Post post) throws ArquivoInexistenteException {
        cp.removerPost(post);
        return ca.deletarArquivo(post.getUrl(), post.toString());
    }

    public int compartilharPost(Post post, User user) throws PostJaExisteException, EntryNaoExisteException, HashVaziaException, ListaAmigosVaziaException {
        cp.compartilharPost(post);//Publica na timeline do user. 
        cg.atualizarVinculoAmizade(post.getAutor(), user, 1);//incrementa o nivel de amizade;
        LinkedList diretorios = cg.getCaminhoPostagemAmigos(user);
        return ca.escreveVariosDiretorios(diretorios, post.toString(), post);//Pecorre os diretorios de amigos criando a postagem.
    }

    public void curtirPost(Post post, User user) throws EntryNaoExisteException {
        cp.curtirPost(post);
        Grafo atualiza = cg.atualizarVinculoAmizade(post.getAutor(), user, 1);
    }

    public void deslikePost(Post post, User user) throws EntryNaoExisteException {
        cp.deslikePost(post);
        Grafo atualiza = cg.atualizarVinculoAmizade(post.getAutor(), user, -1);
    }

    public void addComentario(Post post, String comentario) throws IOException, PostInexistenteException {
        Post novo = cp.novoComentario(post, comentario, ControllerData.getDateTime());
        ca.escreveObjeto(post.getUrl(), post.toString(), post);
    }

    public void editarComentario(Post post, Comentario antigo, String conteudo) throws AtributoVazioException, ComentarioNaoEncontradoException, IOException {
        cp.editarComentario(post, antigo, conteudo, ControllerData.getDateTime());
        ca.escreveObjeto(post.getUrl(), post.toString(), post);
    }

    public void removerComenterio(Post post, Comentario comentario) throws PostInexistenteException, ComentarioNaoEncontradoException, IOException {
        Post atualizado = cp.removerComentario(post, comentario);
        ca.escreveObjeto(atualizado.getUrl(), atualizado.toString(), post);
    }

    //************************************Ações relacionada as solicitações de amizade
    public void aceitarSolicitacao(SolicitacaoAmizade solicitacao) throws SolicitacaoInexistenteException, EntryNaoExisteException, LoopNaoPermitidoException, VertexNaoExisteException, DadoRepetidoException, ArquivoInexistenteException {
        addAmigo(solicitacao.getDestinatario(), solicitacao.getRemetente());
        ca.deletarArquivo(solicitacao.getDestinatario().getCaminhoSolicitacao(), solicitacao.toString());
    }

    public void recusarSolicitacao(SolicitacaoAmizade solicitacao) throws ArquivoInexistenteException, SolicitacaoInexistenteException {
        cs.apagarSolicitacao(solicitacao);
        ca.deletarArquivo(solicitacao.getDestinatario().getCaminhoSolicitacao(), solicitacao.toString());
    }

    public void enviarSolicitacao(User remetente, User destinatario) throws IOException, DadoRepetidoException, AmizadeJaExisteException {
        SolicitacaoAmizade nova = cs.validaSolicitacao(remetente, destinatario);
        cg.validaAmizade(remetente, destinatario);
        ca.escreveObjeto(destinatario.getCaminhoSolicitacao(), nova.toString(), (Serializable) nova);
    }
}
