package br.uefs.ecomp.mybook.facade;

import br.uefs.ecomp.mybook.controller.SessaoController;
import br.uefs.ecomp.mybook.exeptions.EmailOuSenhaInvalidoException;
import br.uefs.ecomp.mybook.exeptions.SemUsuariosCadastradosException;
import br.uefs.ecomp.mybook.exeptions.*;
import br.uefs.ecomp.mybook.controller.*;
import br.uefs.ecomp.mybook.model.*;
import br.uefs.ecomp.mybook.util.Grafo;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import javafx.collections.ObservableList;

/**
 *
 * @author Uellington Damasceno
 */
public class FacadeBackEnd {

    private static FacadeBackEnd facadeMyBook;

    private final ArquivosController controllerArquivos;
    private final SessaoController controllerSessao;
    private final UserController controllerUser;
    private final PostController controllerPostagem;
    private final GrafoController controllerGrafo;
    private final SolicitacaoController controllerSolicitacao;
    
    private FacadeBackEnd() {
        controllerArquivos = new ArquivosController();
        controllerSessao = new SessaoController();
        controllerUser = new UserController();
        controllerPostagem = new PostController();
        controllerGrafo = new GrafoController();
        controllerSolicitacao = new SolicitacaoController();
    }

    public static synchronized FacadeBackEnd getInstance() {
        return (facadeMyBook == null) ? facadeMyBook = new FacadeBackEnd() : facadeMyBook;
    }

    //**********************************Ações iniciais
    public void inicializar() throws IOException {
        controllerArquivos.criaDiretorio("profiles");
        controllerArquivos.criaDiretorio("resources");
        controllerArquivos.criaArquivo("resources", "grafo.txt");
    }

    public User getUserOn() {
        return controllerSessao.getUserOn();
    }

    public void carregarGrafo() throws IOException, ClassNotFoundException, ArquivoVazioException {
        Grafo redeSocial = (Grafo) controllerArquivos.lerObjeto("resources", "grafo");
        controllerGrafo.carregarGrafo(redeSocial);
    }

    public ObservableList carregarPost(User user) throws ListaConteudoVaziaException {
        LinkedList<Post> postagens = (LinkedList) controllerArquivos.lerTodosArquivosDiretorio(user.caminhoPostagem(), ".txt");
        return controllerPostagem.carregarPost(postagens);
    }

    public ObservableList carregaListaAmigos(User user) throws ListaConteudoVaziaException, HashVaziaException, EntryNaoExisteException {
        LinkedList<User> amigos = controllerGrafo.getListaAmigo(user);
        return controllerUser.carregaAmigos(amigos);
    }

    public ObservableList carregarSolicitacoesAmizade(User user) throws ListaConteudoVaziaException {
        LinkedList<SolicitacaoAmizade> solicitacoes = (LinkedList) controllerArquivos.lerTodosArquivosDiretorio(user.getCaminhoSolicitacao(), ".txt");
        return controllerSolicitacao.carregarSolicitacoes(solicitacoes);
    }

    //**************************************/Ações relacionadas a sessão
    public User login(String login, String senha) throws EntryNaoExisteException, EmailOuSenhaInvalidoException,
                                                AtributoVazioException, SemUsuariosCadastradosException {

        User validado = controllerUser.validaUser(login, senha);
        User userOn = controllerGrafo.verificaCredenciais(validado);
        controllerSessao.setUserOn(userOn);
        return userOn;
    }
    
    public void logout() throws IOException{
        controllerSessao.setUserOn(null);
        controllerArquivos.escreveObjeto("resources", "grafo", controllerGrafo.redeSocial());
    }
    //***************************************Ações relacionadas ao grafo
    public User addNovoUser(String login, String senha, String nome,
                            String email, String dataN, String endereco,
                            String telefone) throws AtributoVazioException,
                            IOException, UserJaExisteExceptions {
        
        User user = controllerUser.validaUser(login, senha, nome, email, dataN, endereco, telefone);
        Grafo atualiza = controllerGrafo.addNovoUser(user);
    
        File diretorioPerfil = controllerArquivos.criaDiretorio("profiles\\" + user.getLogin());
        controllerArquivos.criaDiretorio(diretorioPerfil.toString() + "\\" + "feed");
        controllerArquivos.criaDiretorio(diretorioPerfil.toString() + "\\" + "timeLine");
        controllerArquivos.criaDiretorio(diretorioPerfil.toString() + "\\" + "solicitacoes");
        controllerArquivos.escreveObjeto(diretorioPerfil.toString(), "perfil", user);

        controllerArquivos.escreveObjeto("resources", "grafo", atualiza);
        return user;
    }

    public Iterator getMaisPopulares() {
        return null;
    }

    public Iterator listarAmigos(User user) throws HashVaziaException, EntryNaoExisteException {
        return controllerGrafo.listarAmigos(user);
    }

    public Iterator caminhoMaisCurto(User user) throws HashVaziaException, EntryNaoExisteException {
        return controllerGrafo.caminhoMaisCurto(user);
    }

    //************************************Ações relacionadas ao usuario
    public void addAmigo(User user, User novoAmigo) throws EntryNaoExisteException, LoopNaoPermitidoException,
                                                    VertexNaoExisteException, DadoRepetidoException, IOException {
        
        controllerUser.addAmigo(novoAmigo);
        Grafo atualiza = controllerGrafo.criaVinculoAmizade(user, novoAmigo);
        controllerArquivos.escreveObjeto("resources", "grafo", atualiza);
    
    }

    public void removerAmigo(User amigoRemovido) throws EntryNaoExisteException, HashVaziaException, IOException {
        controllerUser.removerAmigo(amigoRemovido);
        Grafo atualiza = controllerGrafo.removeAmigo(amigoRemovido);
        controllerArquivos.escreveObjeto("resources", "grafo", atualiza);
    }

    //***********************************Ações relacionadas ao Post
    public int publicarNovoPost(String url, String tituloCabecalho, PostType tipoPost) throws IOException, PostJaExisteException,
                                                                                    EntryNaoExisteException, HashVaziaException, 
                                                                                    ListaDiretorioVaziaException {
        
        User autor = getUserOn();
        Post novoPost = controllerPostagem.publicarNovoPost(autor, url, tituloCabecalho, tipoPost, controllerSessao.getDateTime());
        controllerArquivos.escreveObjeto(novoPost.getAutor().caminhoPostagem(), novoPost.toString(), novoPost);
        LinkedList diretorios = controllerGrafo.getCaminhoPostagemAmigos(autor);
        return controllerArquivos.escreveVariosDiretorios(diretorios, novoPost.toString(), novoPost);
    
    }

    public int removerPost(Post post) throws ArquivoInexistenteException, EntryNaoExisteException, 
                                        HashVaziaException, ListaDiretorioVaziaException {
        controllerPostagem.removerPost(post);
        LinkedList diretorio = controllerGrafo.getCaminhoPostagemAmigos(post.getAutor());
        return controllerArquivos.deletarVariosArquivos(diretorio, post.toString());
    
    }

    public int compartilharPost(Post post) throws PostJaExisteException, EntryNaoExisteException,
                                            HashVaziaException, ListaDiretorioVaziaException {
        User userLogado = getUserOn();
        Post compartilhado = controllerPostagem.compartilharPost(post, userLogado, controllerSessao.getDateTime());//Publica na timeline do user. 
        controllerGrafo.atualizarVinculoAmizade(post.getAutor(), userLogado, 1);//incrementa o nivel de amizade;
        LinkedList diretorios = controllerGrafo.getCaminhoPostagemAmigos(userLogado);
        return controllerArquivos.escreveVariosDiretorios(diretorios, post.toString(), compartilhado);//Pecorre os diretorios de amigos criando a postagem.
    
    }

    public void curtirPost(Post post) throws EntryNaoExisteException, IOException {
        controllerPostagem.curtirPost(post);
        Grafo atualiza = controllerGrafo.atualizarVinculoAmizade(post.getAutor(), controllerSessao.getUserOn(), 1);
        controllerArquivos.escreveObjeto("resources", "grafo", atualiza);
    }

    public void deslikePost(Post post) throws EntryNaoExisteException, IOException {
        controllerPostagem.deslikePost(post);
        Grafo atualiza = controllerGrafo.atualizarVinculoAmizade(post.getAutor(), controllerSessao.getUserOn(), -1);
        controllerArquivos.escreveObjeto("resources", "grafo", atualiza);
    }

    public void addComentario(Post post, String comentario) throws IOException, PostInexistenteException {
        User logado = getUserOn();
        Post novo = controllerPostagem.novoComentario(post, logado, comentario, controllerSessao.getDateTime());
        controllerArquivos.escreveObjeto(novo.getUrl(), novo.toString(), novo);
    }

    public void editarComentario(Post post, Comentario antigo, String conteudo) throws AtributoVazioException,
                                                                                ComentarioNaoEncontradoException, IOException {
        
        controllerPostagem.editarComentario(post, antigo, conteudo, controllerSessao.getDateTime());
        controllerArquivos.escreveObjeto(post.getUrl(), post.toString(), post);
    }

    public void removerComenterio(Post post, Comentario comentario) throws PostInexistenteException, ComentarioNaoEncontradoException, IOException {
        Post atualizado = controllerPostagem.removerComentario(post, comentario);
        controllerArquivos.escreveObjeto(atualizado.getUrl(), atualizado.toString(), post);
    }

    //************************************Ações relacionada as solicitações de amizade
    public void aceitarSolicitacao(SolicitacaoAmizade solicitacao) throws SolicitacaoInexistenteException, EntryNaoExisteException, LoopNaoPermitidoException,
                                                                    VertexNaoExisteException, DadoRepetidoException, ArquivoInexistenteException, IOException {
        
        addAmigo(solicitacao.getDestinatario(), solicitacao.getRemetente());
        controllerArquivos.deletarArquivo(solicitacao.enderecoDestinatario(), solicitacao.toString());
    }

    public void recusarSolicitacao(SolicitacaoAmizade solicitacao) throws ArquivoInexistenteException, SolicitacaoInexistenteException {
        controllerSolicitacao.apagarSolicitacao(solicitacao);
        controllerArquivos.deletarArquivo(solicitacao.enderecoDestinatario(), solicitacao.toString());
    }

    public SolicitacaoAmizade enviarSolicitacao(User destinatario) throws IOException, DadoRepetidoException, AmizadeJaExisteException {
        User remetente = getUserOn();
        SolicitacaoAmizade nova = controllerSolicitacao.validaSolicitacao(remetente, destinatario);
        controllerGrafo.validaAmizade(remetente, destinatario);
        controllerArquivos.escreveObjeto(destinatario.getCaminhoSolicitacao(), nova.toString(), (Serializable) nova);
        return nova;
    }
    
}
