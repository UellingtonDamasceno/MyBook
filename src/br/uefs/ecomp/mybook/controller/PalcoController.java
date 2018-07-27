package br.uefs.ecomp.mybook.controller;

import br.uefs.ecomp.mybook.model.Post;
import br.uefs.ecomp.mybook.model.SolicitacaoAmizade;
import br.uefs.ecomp.mybook.util.Injetavel;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Uellington Damasceno
 */
public class PalcoController {

    private final Stage palcoPrincipal;
    private final String URL;

    public PalcoController(Stage palcoPrincipal) {
        this.URL = "/br/uefs/ecomp/mybook/";
        this.palcoPrincipal = palcoPrincipal;
        this.palcoPrincipal.setTitle("MyBook");
        this.palcoPrincipal.getIcons().add(new Image(URL + "imagens/book.png"));
        this.palcoPrincipal.setResizable(false);
    }

    public FXMLLoader getLoaderFXML(String FXML) throws IOException {
        return new FXMLLoader(getClass().getResource(URL + FXML));
    }

    public Parent injetarConteudo(Object objeto) throws IOException {
        FXMLLoader loader = getLoaderFXML(identificaClasse(objeto));
        Parent temp = loader.load();
        Injetavel controller = loader.getController();
        controller.injetarObjeto(objeto);
        return temp;
    }


    public void mudaCena(Parent conteudo) {        
        Scene novaCena = new Scene(conteudo);
        palcoPrincipal.setScene(novaCena);
        palcoPrincipal.show();
    }

    private void criaAlerta(String titulo, String mensagem) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(titulo);
        a.setContentText(mensagem);
        a.show();
    }

    private String identificaClasse(Object objeto) {
        if(objeto instanceof SolicitacaoAmizade){
            return "view/Solicitacao.fxml";
        }else if(objeto instanceof Post){
            return "view/PostagemView.fxml";
        }else{
            return "view/UserViewRetangulo.fxml";
        }
    }
}
