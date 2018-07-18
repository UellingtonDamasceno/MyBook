package br.uefs.ecomp.mybook.model;

import java.util.Date;

/**
 *
 * @author Uellington Damasceno
 */
public class Comentario {
    private User autor;
    private String comentario;
    private Date data;
   
    public Comentario(User autor, String comentario, Date data){
        this.autor = autor;
        this.comentario = comentario;
        this.data = data;
    }

    public User getAutor() {
        return autor;
    }

    public void setAutor(User autor) {
        this.autor = autor;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
    
}
