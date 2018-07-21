package br.uefs.ecomp.mybook.facade;

import br.uefs.ecomp.mybook.model.User;

/**
 *
 * @author Uellington Damasceno
 */
public class Sessao {
    
    User userLogado;
    
    public Sessao(User userLogado){
        this.userLogado = userLogado;
    }
}
