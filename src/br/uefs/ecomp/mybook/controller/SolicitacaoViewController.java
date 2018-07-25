package br.uefs.ecomp.mybook.controller;

import br.uefs.ecomp.mybook.model.SolicitacaoAmizade;
import br.uefs.ecomp.mybook.util.Injetavel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Uellington Damasceno
 */
public class SolicitacaoViewController implements Initializable, Injetavel {

    @FXML
    private ImageView imgViewUser;
    @FXML
    private Button bntAceitarSolicitacao;
    @FXML
    private Button bntRecusarSolicitacao;
    @FXML
    private Label lblNomeAutor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void aceitarSolicitacao(MouseEvent event) {
    }

    @FXML
    private void recusarSolicitacao(MouseEvent event) {
    }

    @FXML
    private void abrirPerfilUser(MouseEvent event) {
    }

    @Override
    public void injetarObjeto(Object objeto) {
        if (objeto instanceof SolicitacaoAmizade) {
            SolicitacaoAmizade solicitacao = (SolicitacaoAmizade) objeto;
            lblNomeAutor.setText(solicitacao.getRemetente().getNome());
            imgViewUser.setImage(new Image(solicitacao.getRemetente().getCaminhoImagem()));
        }
    }
}
