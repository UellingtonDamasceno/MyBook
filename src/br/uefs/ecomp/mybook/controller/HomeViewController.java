package br.uefs.ecomp.mybook.controller;

import br.uefs.ecomp.mybook.exeptions.ListaConteudoVazia;
import br.uefs.ecomp.mybook.facade.FacadeBackEnd;
import br.uefs.ecomp.mybook.facade.FacadeFrontEnd;
import br.uefs.ecomp.mybook.model.Post;
import br.uefs.ecomp.mybook.model.SolicitacaoAmizade;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
    private Button bntAmigos;
    @FXML
    private Button bntMeuArquivos;
    @FXML
    private Button bntEditarPerfil;
    @FXML
    private Button bntSolicitacoes;
    @FXML
    private Button bntLogout;
    @FXML
    private Button bntBuscar;
    @FXML
    private TextField txtBarraBusca;
    @FXML
    private Button bntFeed;
    @FXML
    private VBox vBoxVisualizador;

    private FacadeBackEnd facade;
    private FacadeFrontEnd facadeFrontEnd;

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

        try {
            inserirPostagens(facade.carregarPost(facade.getUserOn()));
        } catch (ListaConteudoVazia ex) {

        }
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
        } catch (ListaConteudoVazia ex) {
        }
    }

    @FXML
    private void finalizarSessao(ActionEvent event) {
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

}
