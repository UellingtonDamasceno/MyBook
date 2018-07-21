package br.uefs.ecomp.mybook.facade;

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

    private final ArquivosController ca;
   // private final ControllerSessao sessao;
    private final UserController cu;
    private final PostController cp;
    private final GrafoController cg;
    private final SolicitacaoController cs;

    private Facade() {
        ca = new ArquivosController();
        //sessao = new ControllerSessao();
        cu = new UserController();
        cp = new PostController();
        cg = new GrafoController();
        cs = new SolicitacaoController();
    }

    public static synchronized Facade getInstance() {
        return facadeMyBook == null ? new Facade() : facadeMyBook;
    }

    //**********************************Ações iniciais
    public void inicializar() throws IOException{
        ca.criaDiretorio("profiles");
        ca.criaDiretorio("resources");
        ca.criaArquivo("resources", "grafo.txt");
    }
    
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
    
    //**************************************/Ações relacionadas a sessão
    
    //***************************************Ações relacionadas ao grafo
    public User addNovoUser(String login, String senha, String nome,
            String email, String dataN, String endereco,
            String telefone) throws UserJaExisteExceptions, AtributoVazioException, IOException {
        User user = cu.validaUser(login, senha, nome, email, dataN, endereco, telefone);

//        File diretorioPerfil  = ca.criaDiretorio("profiles\\"+user.getLogin());
//        ca.criaDiretorio(diretorioPerfil.toString()+"\\"+"feed");
//        ca.criaDiretorio(diretorioPerfil.toString()+"\\"+"timeLine");
//        ca.criaDiretorio(diretorioPerfil.toString()+"\\"+"solicitacoes");
//        ca.escreveObjeto(diretorioPerfil.toString(), "perfil", user);
//        
        Grafo atualiza = cg.addNovoUser(user);
        ca.escreveObjeto("resources\\", "grafo", atualiza);
        return user;
    }

    public Iterator getMaisPopulares(){
        return null;
    }
    
    public Iterator listarAmigos(User user) throws HashVaziaException {
        return cg.listarAmigos(user);
    }

    public Iterator caminhoMaisCurto(User user) throws HashVaziaException, EntryNaoExisteException {
        return cg.caminhoMaisCurto(user);
    }

    //************************************Ações relacionadas ao usuario
    public void addAmigo(User user, User novoAmigo) throws EntryNaoExisteException, LoopNaoPermitidoException, VertexNaoExisteException, DadoRepetidoException, IOException {
        cu.addAmigo(novoAmigo);
        Grafo atualiza = cg.criaVinculoAmizade(user, novoAmigo);
        ca.escreveObjeto("resources\\", "grafo", atualiza);
        System.out.println("Amigo add com sucesso!");
    }

    public void removerAmigo(User amigoRemovido) throws EntryNaoExisteException, HashVaziaException, IOException {
        cu.removerAmigo(amigoRemovido);
        Grafo atualiza = cg.removeAmigo(amigoRemovido);
        ca.escreveObjeto("resources\\", "grafo", atualiza);
        System.out.println("Amigo removido com sucesso!");
    }

    //***********************************Ações relacionadas ao Post
    public int publicarNovoPost(User autor, String url, String tituloCabecalho, PostType tipoPost) throws IOException, PostJaExisteException, EntryNaoExisteException, HashVaziaException, ListaDiretorioVaziaException {
        Post novoPost = cp.publicarNovoPost(autor, url, tituloCabecalho, tipoPost, ControllerData.getDateTime());
        ca.escreveObjeto(novoPost.getAutor().caminhoPostagem(), novoPost.toString(), novoPost);
        LinkedList diretorios = cg.getCaminhoPostagemAmigos(autor);
        return ca.escreveVariosDiretorios(diretorios, novoPost.toString(), novoPost);
    }

    public int removerPost(Post post) throws ArquivoInexistenteException, EntryNaoExisteException, HashVaziaException, ListaDiretorioVaziaException {
        cp.removerPost(post);
        LinkedList diretorio = cg.getCaminhoPostagemAmigos(post.getAutor());
        return ca.deletarVariosArquivos(diretorio, post.toString());
    }

    public int compartilharPost(Post post, User user) throws PostJaExisteException, EntryNaoExisteException, HashVaziaException, ListaDiretorioVaziaException {
        cp.compartilharPost(post);//Publica na timeline do user. 
        cg.atualizarVinculoAmizade(post.getAutor(), user, 1);//incrementa o nivel de amizade;
        LinkedList diretorios = cg.getCaminhoPostagemAmigos(user);
        return ca.escreveVariosDiretorios(diretorios, post.toString(), post);//Pecorre os diretorios de amigos criando a postagem.
    }

    public void curtirPost(Post post, User user) throws EntryNaoExisteException, IOException {
        cp.curtirPost(post);
        Grafo atualiza = cg.atualizarVinculoAmizade(post.getAutor(), user, 1);
        ca.escreveObjeto("resources\\", "grafo", atualiza);
    }

    public void deslikePost(Post post, User user) throws EntryNaoExisteException, IOException {
        cp.deslikePost(post);
        Grafo atualiza = cg.atualizarVinculoAmizade(post.getAutor(), user, -1);
        ca.escreveObjeto("resources\\", "grafo", atualiza);
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
    public void aceitarSolicitacao(SolicitacaoAmizade solicitacao) throws SolicitacaoInexistenteException, EntryNaoExisteException, LoopNaoPermitidoException, 
                                                                    VertexNaoExisteException, DadoRepetidoException, ArquivoInexistenteException, IOException {
        addAmigo(solicitacao.getDestinatario(), solicitacao.getRemetente());
        ca.deletarArquivo(solicitacao.enderecoDestinatario(), solicitacao.toString());
    }

    public void recusarSolicitacao(SolicitacaoAmizade solicitacao) throws ArquivoInexistenteException, SolicitacaoInexistenteException {
        cs.apagarSolicitacao(solicitacao);
        ca.deletarArquivo(solicitacao.enderecoDestinatario(), solicitacao.toString());
    }

    public SolicitacaoAmizade enviarSolicitacao(User remetente, User destinatario) throws IOException, DadoRepetidoException, AmizadeJaExisteException {
        SolicitacaoAmizade nova = cs.validaSolicitacao(remetente, destinatario);
        cg.validaAmizade(remetente, destinatario);
        ca.escreveObjeto(destinatario.getCaminhoSolicitacao(), nova.toString(), (Serializable) nova);
        return nova;
    }    
}
