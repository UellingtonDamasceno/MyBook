package br.uefs.ecomp.mybook.controller;

import br.uefs.ecomp.mybook.model.User;
import java.util.LinkedList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Uellington Damasceno
 */
public class ControllerSolicitacao {
    
    private ObservableList listaSolicitacoes;
    
    public ControllerSolicitacao() {
        listaSolicitacoes = FXCollections.observableList(new LinkedList());
    }
    
    public ObservableList carregarSolicitacoes(LinkedList solicitacoes){
        listaSolicitacoes.addAll(solicitacoes);
        return listaSolicitacoes;
    }
    
    public void enviarSolicitacaoAmizade(User destinatario) {

    }

    public void aceitarSolicitacaoAmizade() {
        
    }

    public void removerSolicitacaoAmizade() {
        
    }

}
