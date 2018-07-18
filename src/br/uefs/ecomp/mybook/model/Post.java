package br.uefs.ecomp.mybook.model;

import java.io.Serializable;
import java.util.LinkedList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Uellington Damasceno
 */
public class Post implements Serializable{
    
    private final User autor;
    private String url;
    private PostType tipoPost;
    private final String tituloCabecalho;
    private final ObservableList comentarios;
    private int like;
    private int deslike;
    
    public Post(User autor, String url, String tituloCabecalho, PostType tipoPost){
        this.autor = autor;
        this.url = url;
        this.tipoPost = tipoPost;
        this.tituloCabecalho = tituloCabecalho;
        comentarios = FXCollections.observableList(new LinkedList());
    }
    
    public PostType getTipoPost(){
        return tipoPost;
    }
    
    public User getAutor(){
        return autor;
    }
    
    public String getUrl(){
        return url;
    }
    
    public void setUrl(String url){
        this.url = url;
    }
    
    public void setTipoPost(PostType tipoPost){
        this.tipoPost = tipoPost;
    }
    
    public ObservableList getComentarios(){
        return comentarios;
    }
    
    public int getLike(){
        return like;
    }
    
    public int getDeslike(){
        return deslike;
    }
    
    public void addLike(){
        like++;
    }
    
    public void addDeslike(){
        deslike++;
    }
    
    public void addComentario(Comentario novoComentario){
        comentarios.add(novoComentario);
    }
    
    public void removerComentario(Comentario comentarioRemovido){
        comentarios.remove(comentarioRemovido);
    }
    
    public boolean temTitulo(){
        return tituloCabecalho.isEmpty();
    }

    public void editarComentario(Comentario anterior, Comentario atual) {
        int index = comentarios.indexOf(anterior);
        comentarios.set(index, atual);
    }
    
    @Override
    public String toString(){
        return this.tituloCabecalho;
    }
}
