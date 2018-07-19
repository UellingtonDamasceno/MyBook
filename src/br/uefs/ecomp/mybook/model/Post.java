package br.uefs.ecomp.mybook.model;

import br.uefs.ecomp.mybook.exeptions.ComentarioNaoEncontradoException;
import br.uefs.ecomp.mybook.exeptions.AtributoVazioException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Uellington Damasceno
 */
public class Post implements Serializable {

    private final User autor;
    private String url;
    private String dataHora;
    private PostType tipoPost;
    private final String tituloCabecalho;
    private final ObservableList comentarios;
    private int like;
    private int deslike;

    public Post(User autor, String url, String tituloCabecalho, PostType tipoPost, String dataHora) {
        this.autor = autor;
        this.url = url;
        this.tipoPost = tipoPost;
        this.tituloCabecalho = tituloCabecalho;
        comentarios = FXCollections.observableList(new LinkedList());
        this.dataHora = dataHora;
    }

    public PostType getTipoPost() {
        return tipoPost;
    }

    public User getAutor() {
        return autor;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTipoPost(PostType tipoPost) {
        this.tipoPost = tipoPost;
    }

    public ObservableList getComentarios() {
        return comentarios;
    }

    public int getLike() {
        return like;
    }

    public int getDeslike() {
        return deslike;
    }

    public void addLike() {
        like++;
    }

    public void addDeslike() {
        deslike++;
    }

    public void limparComentarios() {
        if (!comentarios.isEmpty()) {
            comentarios.clear();
        }
    }

    public void addComentario(Comentario novoComentario) {
        comentarios.add(0, novoComentario);
    }

    public void removerComentario(Comentario comentarioRemovido) throws ComentarioNaoEncontradoException {
        if (!comentarios.contains(comentarioRemovido)) {
            throw new ComentarioNaoEncontradoException();
        } else {
            comentarios.remove(comentarioRemovido);
        }
    }

    public boolean temTitulo() {
        return tituloCabecalho.isEmpty();
    }

    public void editarComentario(Comentario comentarioEditar, String conteudo, String dataEdicao) throws AtributoVazioException, ComentarioNaoEncontradoException {
        if (conteudo.isEmpty()) {
            throw new AtributoVazioException();
        } else if (!comentarios.contains(comentarioEditar)) {
            throw new ComentarioNaoEncontradoException();
        } else {
            int index = comentarios.indexOf(comentarioEditar);
            Comentario editavel = (Comentario) comentarios.get(index);
            editavel.setData(dataEdicao);
            editavel.setComentario(conteudo);
        }
    }

    @Override
    public String toString() {
        return this.tituloCabecalho;
    }

}
