/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uefs.ecomp.mybook.controller;

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
public class ControllerPost {

    private ObservableList listaPost;

    public ControllerPost() {
        listaPost = FXCollections.observableList(new LinkedList());
    }

    public ObservableList carregarPost(LinkedList posts) {
        listaPost.addAll(posts);
        return listaPost;
    }

    public Post publicarNovoPost(User autor, String url, String titulo, PostType tipo) {
        Post novo = new Post(autor, url, titulo, tipo);
        listaPost.add(0, novo);
        return novo;
    }

    public void removerPost(Post post) {
        listaPost.remove(post);
    }
    
    public void compartilharPost(Post compartilhado) {
        listaPost.add(0, compartilhado);
    }
}
