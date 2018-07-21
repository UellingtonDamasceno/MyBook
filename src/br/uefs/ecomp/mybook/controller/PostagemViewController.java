package br.uefs.ecomp.mybook.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Pagination;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Uellington Damasceno
 */
public class PostagemViewController implements Initializable {

    @FXML
    private ImageView imgUserIcon;
    @FXML
    private Label lblAutorPost;
    @FXML
    private Label lblDataPost;
    @FXML
    private MenuBar menuBarOpcoes;
    @FXML
    private MenuItem mItemEditar;
    @FXML
    private MenuItem mItemRemover;
    @FXML
    private BorderPane localPostagem;
    @FXML
    private Label lblTituloPost;
    @FXML
    private Button bntDeslike;
    @FXML
    private Button bntLike;
    @FXML
    private Pagination pagComentario;
    @FXML
    private Button bntComentario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void EditarPost(ActionEvent event) {
    }

    @FXML
    private void RemoverPost(ActionEvent event) {
    }

    @FXML
    private void DeslikePublicacao(ActionEvent event) {
    }

    @FXML
    private void CurtirPublicacao(ActionEvent event) {
    }

    @FXML
    private void comentar(ActionEvent event) {
    }
    
}
