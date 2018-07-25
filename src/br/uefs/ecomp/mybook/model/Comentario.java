package br.uefs.ecomp.mybook.model;

/**
 *
 * @author Uellington Damasceno
 */
public class Comentario {
    private User autor;
    private String comentario;
    private String data;
   
    public Comentario(User autor, String comentario, String data){
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
        
}
