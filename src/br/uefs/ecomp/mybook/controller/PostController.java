/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uefs.ecomp.mybook.controller;

import br.uefs.ecomp.mybook.exeptions.AtributoVazioException;
import br.uefs.ecomp.mybook.exeptions.PostInexistenteException;
import br.uefs.ecomp.mybook.exeptions.PostJaExisteException;
import br.uefs.ecomp.mybook.model.Comentario;
import br.uefs.ecomp.mybook.exeptions.ComentarioNaoEncontradoException;
import br.uefs.ecomp.mybook.model.Post;
import br.uefs.ecomp.mybook.model.PostType;
import br.uefs.ecomp.mybook.model.User;
import java.util.LinkedList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Uellington Damasceno
 */
public class PostController {

    private ObservableList<Post> listaPost;

    public PostController() {
        listaPost = FXCollections.observableList(new LinkedList());
    }

    public ObservableList carregarPost(LinkedList posts) {
        listaPost.addAll(posts);
        return listaPost;
    }

    public Post publicarNovoPost(User autor, String url, String titulo, PostType tipo, String data) throws PostJaExisteException {
        Post novo = new Post(autor, url, titulo, tipo, data);
        if (!listaPost.contains(novo)) {
            listaPost.add(0, novo);
            return novo;
        }
        throw new PostJaExisteException();
    }

    public Post novoComentario(Post post, User autor, String conteudoComentario, String data) throws PostInexistenteException {
        if (listaPost.contains(post)) {
            Comentario novo = new Comentario(autor, conteudoComentario, data);
            int idPost = listaPost.indexOf(post);
            Post add = listaPost.get(idPost);
            add.addComentario(novo);
            return add;
        } else {
            throw new PostInexistenteException();
        }

    }

    public void editarComentario(Post post, Comentario antigo, String conteudo, String dataEdicao) throws AtributoVazioException, ComentarioNaoEncontradoException {
        post.editarComentario(antigo, conteudo, dataEdicao);
    }

    public void removerPost(Post post) {
        listaPost.remove(post);
    }

    public Post compartilharPost(Post post, User userLogado, String horaCompartilhamento) throws PostJaExisteException {
        if (!listaPost.contains(post)) {
            post.addCompartilhamento();
            
            Post compartilhado = post;
            compartilhado.setAutor(userLogado);
            compartilhado.setData(horaCompartilhamento);
            compartilhado.limparComentarios();
            System.out.println("Post compartilhado: "+compartilhado);
            System.out.println("Post original: "+post);
            System.out.println("User original: "+post.getAutor());
            System.out.println("User compartilhador: "+compartilhado.getAutor());
            listaPost.add(0, compartilhado);
            return compartilhado;
        } else {
            throw new PostJaExisteException();
        }
    }

    public void curtirPost(Post post) {
        post.addLike();
    }

    public void deslikePost(Post post) {
        post.addDeslike();
    }

    public Post removerComentario(Post post, Comentario comentario) throws PostInexistenteException, ComentarioNaoEncontradoException {
        if (!listaPost.contains(post)) {
            throw new PostInexistenteException();
        }
        int index = listaPost.indexOf(post);
        Post editavel = listaPost.get(index);
        editavel.removerComentario(comentario);
        return editavel;
    }

}
