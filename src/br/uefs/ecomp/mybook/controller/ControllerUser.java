package br.uefs.ecomp.mybook.controller;

import br.uefs.ecomp.mybook.model.User;
import java.util.LinkedList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Uellington Damasceno
 */
public class ControllerUser {

    private ObservableList<User> listaAmigos;

    public ControllerUser() {
        listaAmigos = FXCollections.observableList(new LinkedList());
    }

    public ObservableList carregaAmigos(LinkedList amigos) {
        listaAmigos.addAll(amigos);
        return listaAmigos;
    }

    public void addAmigo(User novoAmigo) throws DadoRepetidoExcepetion {
        if (novoAmigo == null) {
            throw new IllegalArgumentException();
        } else if (listaAmigos.contains(novoAmigo)) {
            throw new DadoRepetidoExcepetion();
        } else {
            listaAmigos.add(novoAmigo);
        }
    }

    public void removerAmigo(User amigoRemover) {
        listaAmigos.remove(amigoRemover);
    }

    public User validaUser(String login, String senha, String nome,
            String email, String dataN, String endereco,
            String telefone) throws AtributoVazioException {
        
        User novoUser = new User(login, senha, nome, email, dataN, endereco, telefone);
        if(novoUser.ehValido()){
            return novoUser;
        }else{
            throw new AtributoVazioException();
        }
    }

}
