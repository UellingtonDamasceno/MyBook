package br.uefs.ecomp.mybook.controller;

import br.uefs.ecomp.mybook.exeptions.ListaConteudoVaziaException;
import br.uefs.ecomp.mybook.facade.FacadeBackEnd;
import br.uefs.ecomp.mybook.facade.FacadeFrontEnd;
import br.uefs.ecomp.mybook.model.Post;
import br.uefs.ecomp.mybook.model.SolicitacaoAmizade;
import br.uefs.ecomp.mybook.model.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Uellington Damasceno
 */
public class HomeViewController implements Initializable {

    @FXML
    private Button bntEditarImagem;
    @FXML
    private ImageView imageViewUser;
    @FXML
    private Button bntBuscar;
    @FXML
    private TextField txtBarraBusca;
    @FXML
    private VBox vBoxVisualizador;

    private FacadeBackEnd facade;
    private FacadeFrontEnd facadeFrontEnd;
    @FXML
    private Label lblNomeUser;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        facade = FacadeBackEnd.getInstance();
        facadeFrontEnd = FacadeFrontEnd.getInstance();
    }

    @FXML
    private void EditarImagem(ActionEvent event) {

    }

    @FXML
    private void setVisibleBotaoEditar(DragEvent event) {
    }

    @FXML
    private void verAmigos(ActionEvent event) {
    }

    @FXML
    private void verMeusArquivos(ActionEvent event) {
        removerItemVisualizador((Parent) vBoxVisualizador.getChildren().remove(0));
    }

    @FXML
    private void editarPerfil(ActionEvent event) {
    }

    @FXML
    private void verSolicitacoes(ActionEvent event) {
        try {
            inserirSolicitacao(facade.carregarSolicitacoesAmizade(facade.getUserOn()));
        } catch (ListaConteudoVaziaException ex) {
        }
    }

    @FXML
    private void finalizarSessao(ActionEvent event) {
        try {
            facadeFrontEnd.mudaCena(facadeFrontEnd.getLoginExistente());
            facade.logout();
        } catch (IOException ex) {
        }
    }

    @FXML
    private void buscarUser(ActionEvent event) {
    }

    @FXML
    private void verFeed(ActionEvent event) {
    }

    private void inserirSolicitacao(ObservableList solicitacoes) {
        solicitacoes.forEach((Object solicitacao) -> {
            try {
                vBoxVisualizador.getChildren().add(facadeFrontEnd.carregarSolicitacao((SolicitacaoAmizade) solicitacao));
            } catch (IOException ex) {
            }
        });
    }

    private void inserirPostagens(ObservableList posts) {
        posts.forEach((Object post) -> {
            try {
                vBoxVisualizador.getChildren().add(facadeFrontEnd.carregarPost((Post) post));
            } catch (IOException ex) {
            }
        });
    }

    public void removerItemVisualizador(Parent itemRemovido) {
        if (vBoxVisualizador.getChildren().contains(itemRemovido)) {
            vBoxVisualizador.getChildren().remove(itemRemovido);
        }
    }

    public void injetarUser(User userOn) {
        //imageViewUser.setImage(new Image(userOn.getCaminhoImagem()));
        lblNomeUser.setText(userOn.getNome());
        
        try {
            inserirPostagens(facade.carregarPost(userOn));
        } catch (ListaConteudoVaziaException ex) {
            System.out.println("Publicação vazia");
        }
    }
}
