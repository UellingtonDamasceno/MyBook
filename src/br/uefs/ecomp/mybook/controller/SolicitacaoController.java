package br.uefs.ecomp.mybook.controller;

import br.uefs.ecomp.mybook.exeptions.SolicitacaoInexistenteException;
import br.uefs.ecomp.mybook.exeptions.DadoRepetidoException;
import br.uefs.ecomp.mybook.model.SolicitacaoAmizade;
import br.uefs.ecomp.mybook.model.User;
import java.util.LinkedList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Uellington Damasceno
 */
public class SolicitacaoController {

    private ObservableList<SolicitacaoAmizade> listaSolicitacoes;

    public SolicitacaoController() {
        listaSolicitacoes = FXCollections.observableList(new LinkedList());
    }

    public ObservableList carregarSolicitacoes(LinkedList solicitacoes) {
        listaSolicitacoes.addAll(solicitacoes);
        return listaSolicitacoes;
    }

    public SolicitacaoAmizade validaSolicitacao(User remetente, User destinatario) throws DadoRepetidoException {
        SolicitacaoAmizade nova = new SolicitacaoAmizade(remetente, destinatario);
        if (listaSolicitacoes.contains(nova)) {
            throw new DadoRepetidoException();
        } else {
            return nova;
        }
    }

    public void apagarSolicitacao(SolicitacaoAmizade solicitacao) throws SolicitacaoInexistenteException {
        if (listaSolicitacoes.contains(solicitacao)) {
            throw new SolicitacaoInexistenteException();
        }
        listaSolicitacoes.remove(solicitacao);
    }
}
