package br.uefs.ecomp.mybook.controller;

import br.uefs.ecomp.mybook.exeptions.AmizadeJaExisteException;
import br.uefs.ecomp.mybook.exeptions.DadoRepetidoException;
import br.uefs.ecomp.mybook.facade.FacadeBackEnd;
import br.uefs.ecomp.mybook.facade.FacadeFrontEnd;
import br.uefs.ecomp.mybook.model.User;
import br.uefs.ecomp.mybook.util.Injetavel;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
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
public class UserViewRetanguloController implements Initializable, Injetavel {

    User userDestinatario;
    
    @FXML
    private ImageView ImageViewUser;
    @FXML
    private Label lblNomeUser;
    @FXML
    private Label lblEmailUser;
    @FXML
    private Button bntSolicitacao;
    @FXML
    private Label lblSolicitao;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    @FXML
    private void abrirAmigo(MouseEvent event) {
    }

    @Override
    public void injetarObjeto(Object objeto) {
        if (objeto instanceof User) {
            System.out.println("Entrou aki");
            User user = (User) objeto;
            //ImageViewUser.setImage(new Image(user.getCaminhoImagem()));
            lblNomeUser.setText(user.getNome());
            lblEmailUser.setText(user.getEmail());
            userDestinatario = user;
        }
    }

    @FXML
    private void enviarSolicitacao(ActionEvent event) {
        try {
            FacadeBackEnd.getInstance().enviarSolicitacao(userDestinatario);
            bntSolicitacao.setVisible(false);
            lblSolicitao.setText("Solicitação enviada com sucesso!");
        } catch (IOException ex) {
            System.out.println("Erro ao enviar solicitação");
        } catch (DadoRepetidoException ex) {
            System.out.println("Solicitação já enviada");
        } catch (AmizadeJaExisteException ex) {
            System.out.println("Já são amigos!");
        }

    }

}
