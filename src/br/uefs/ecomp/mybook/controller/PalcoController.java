package br.uefs.ecomp.mybook.controller;

import br.uefs.ecomp.mybook.model.Post;
import br.uefs.ecomp.mybook.model.SolicitacaoAmizade;
import br.uefs.ecomp.mybook.util.Injetavel;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 *
 * @author Uellington Damasceno
 */
public class PalcoController {

    private final Stage palcoPrincipal;
    private final String URL;
    
    public PalcoController(Stage palcoPrincipal) {
        this.URL = "br/uefs/ecomp/mybook/view/";
        this.palcoPrincipal = palcoPrincipal;
    }

    public FXMLLoader getLoaderFXML(String FXML) throws IOException {
        FXMLLoader loader = FXMLLoader.load(getClass().getResource(URL+FXML));
        return loader;
    }

    public Parent InjetarConteudo(Object objeto) throws IOException {
        FXMLLoader loader = FXMLLoader.load(getClass().getResource(URL+identificaClasse(objeto)));
        Injetavel postController = loader.getController();
        postController.injetarObjeto(objeto);
        return loader.load();
    }

    public Parent getNovaSolicitacao() {
        return carregarCena("Solicitacao.fxml");
    }

    public Parent getNovaPublicacao() {
        return carregarCena("PostagemView.fxml");
    }
    
    private Parent carregarCena(String nomeClasseCena) {
        try {
            Parent cena = FXMLLoader.load(getClass().getResource(nomeClasseCena));
            return cena;
        } catch (IOException ex) {
            criaAlerta("Falha no carregamento", "Erro ao carregar cena: " + nomeClasseCena);
            return null;
        }
    }

    public void mudaCena(Parent conteudo) {
        Scene novaCena = new Scene(conteudo);
        palcoPrincipal.setScene(novaCena);
    }

    private void criaAlerta(String titulo, String mensagem) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(titulo);
        a.setContentText(mensagem);
        a.show();
    }

    private String identificaClasse(Object objeto) {
        return (objeto instanceof SolicitacaoAmizade) ? "Solicitacao.fxml" : "PostagemView.fxml";
    }
}
