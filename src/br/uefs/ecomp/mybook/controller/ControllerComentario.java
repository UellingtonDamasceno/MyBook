package br.uefs.ecomp.mybook.controller;

import br.uefs.ecomp.mybook.model.Comentario;
import br.uefs.ecomp.mybook.model.Post;

/**
 *
 * @author Uellington Damasceno
 */
public class ControllerComentario {

    public ControllerComentario() {
    }

    public void fazerComentario(Post post, Comentario comentario) {
        post.addComentario(comentario);
    }

    public void editarComentario(Post post, Comentario anterior, Comentario atual) {
        post.editarComentario(anterior, atual);
    }

    public void removerComentario(Post post, Comentario comentario) {
        post.removerComentario(comentario);
    }

}
