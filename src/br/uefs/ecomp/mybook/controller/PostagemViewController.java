package br.uefs.ecomp.mybook.controller;

import br.uefs.ecomp.mybook.exeptions.ArquivoInexistenteException;
import br.uefs.ecomp.mybook.exeptions.EntryNaoExisteException;
import br.uefs.ecomp.mybook.exeptions.HashVaziaException;
import br.uefs.ecomp.mybook.exeptions.ListaDiretorioVaziaException;
import br.uefs.ecomp.mybook.exeptions.PostInexistenteException;
import br.uefs.ecomp.mybook.exeptions.PostJaExisteException;
import br.uefs.ecomp.mybook.facade.FacadeBackEnd;
import br.uefs.ecomp.mybook.model.Post;
import br.uefs.ecomp.mybook.model.User;
import br.uefs.ecomp.mybook.util.Injetavel;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Uellington Damasceno
 */
public class PostagemViewController implements Initializable, Injetavel {

    @FXML
    private ImageView imgUserIcon;
    @FXML
    private Label lblAutorPost;
    @FXML
    private Label lblDataPost;
    @FXML
    private BorderPane localPostagem;
    @FXML
    private Label lblTituloPost;
    @FXML
    private Pagination pagComentario;
    @FXML
    private TextField txtAreaComentario;
    @FXML
    private Label lblLike;
    @FXML
    private Label lblDeslikes;
    @FXML
    private Label lblComentarios;
    @FXML
    private Label lblCompartilhamentos;

    private FacadeBackEnd facade;
    private Post post;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        facade = FacadeBackEnd.getInstance();
        // TODO
    }

    @FXML
    private void EditarPost(ActionEvent event) {
    }

    @FXML
    private void RemoverPost(ActionEvent event) {
        try {
            facade.removerPost(post);
        } catch (ArquivoInexistenteException ex) {

        } catch (EntryNaoExisteException ex) {

        } catch (HashVaziaException ex) {

        } catch (ListaDiretorioVaziaException ex) {

        }
    }

    @FXML
    private void DeslikePublicacao(ActionEvent event) {
        try {
            facade.deslikePost(post);
        } catch (EntryNaoExisteException ex) {

        } catch (IOException ex) {

        }
    }

    @FXML
    private void CurtirPublicacao(ActionEvent event) {
        try {
            facade.curtirPost(post);
        } catch (IOException ex) {

        } catch (EntryNaoExisteException ex) {

        }
    }

    @FXML
    private void EnviarComentario(ActionEvent event) {
        try {
            facade.addComentario(post, txtAreaComentario.getText());
        } catch (IOException ex) {
        } catch (PostInexistenteException ex) {
        }
    }

    private void carregaComentarios() {
        int numPaginas = (post.getNumComentarios());
        pagComentario.setPageCount(numPaginas);
        pagComentario.setPageFactory((Integer pageIndex) -> {
            return new Label((String) post.getComentarios().get(pageIndex));
        });
    }

    private void verificaTipoPost() {
        Node conteudoPost = null;
        switch (post.getTipoPost()) {
            case TEXTO: {
                lblTituloPost.setText(post.getTituloPost());
                break;
            }
            case VIDEO: {
                conteudoPost = new Label("Carregando video");
                break;
            }
            case IMAGEM: {
                conteudoPost = new ImageView(new Image(post.getUrl()));
                break;
            }
        }
        localPostagem.getChildren().add(conteudoPost);
    }

    @FXML
    private void compartilharPost(ActionEvent event) {
        try {
            facade.compartilharPost(post);
        } catch (PostJaExisteException ex) {

        } catch (EntryNaoExisteException ex) {

        } catch (HashVaziaException ex) {

        } catch (ListaDiretorioVaziaException ex) {

        }
    }

    @Override
    public void injetarObjeto(Object objeto) {
        if (objeto instanceof Post) {
            Post postInjetar = (Post) objeto;
            this.post = postInjetar;
            User autor = postInjetar.getAutor();
            imgUserIcon.setImage(new Image(autor.getCaminhoImagem()));
            lblAutorPost.setText(autor.getNome());
            lblDataPost.setText(postInjetar.getData());
            verificaTipoPost();
            //lblLike.textProperty().bindBidirectional(new SimpleStringProperty());
            lblLike.setText("Like: " + postInjetar.getLike());
            lblDeslikes.setText("Deslikes: " + postInjetar.getDeslike());
            lblCompartilhamentos.setText("Comentarios: " + postInjetar.getCompatilhamentos());
            lblComentarios.setText("Shares: " + postInjetar.getNumComentarios());
            carregaComentarios();
        }
    }
}
