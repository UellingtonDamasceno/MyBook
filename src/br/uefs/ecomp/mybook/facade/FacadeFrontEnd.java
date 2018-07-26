package br.uefs.ecomp.mybook.facade;

import br.uefs.ecomp.mybook.controller.CenaController;
import br.uefs.ecomp.mybook.controller.*;
import br.uefs.ecomp.mybook.model.Post;
import br.uefs.ecomp.mybook.model.SolicitacaoAmizade;
import br.uefs.ecomp.mybook.model.User;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 *
 * @author Uellington Damasceno
 */
public class FacadeFrontEnd {

    private static FacadeFrontEnd facadeFrontEnd;

    private PalcoController palcoController;
    private HomeViewController home;
    private LoginViewController login;
    private CenaController cenas;

    private FacadeFrontEnd() {
        cenas = new CenaController();
    }

    public static synchronized FacadeFrontEnd getInstance() {
        return (facadeFrontEnd == null) ? facadeFrontEnd = new FacadeFrontEnd() : facadeFrontEnd;
    }

    public void inicializa(Stage palcoPrincipal) throws IOException {
        palcoController = new PalcoController(palcoPrincipal);

        FXMLLoader loader = palcoController.getLoaderFXML("view/LoginView.fxml");
        login = loader.getController();
        cenas.setCenaLogin(loader.load());
    }

    public Parent novoLogin(User userOn) throws IOException {
        FXMLLoader loader = palcoController.getLoaderFXML("view/Home.fxml");
        
        cenas.setCenaHome(loader.load());
        
        home = loader.getController();
        home.injetarUser(userOn);

        return cenas.getCenaHome();
    }
    
    public Parent getLoginExistente(){
        return cenas.getCenaLogin();
    }
    
    public Parent getHomeExistente(){
        return cenas.getCenaHome();
    }
        
    public Parent carregarPost(Post post) throws IOException {
        return palcoController.injetarConteudo(post);
    }

    public Parent carregarSolicitacao(SolicitacaoAmizade solicitacao) throws IOException {
        return palcoController.injetarConteudo(solicitacao);
    }

    public void mudaCena(Parent conteudo) {
        palcoController.mudaCena(conteudo);
    }
}
