package br.uefs.ecomp.mybook.facade;

import br.uefs.ecomp.mybook.controller.*;
import br.uefs.ecomp.mybook.model.Post;
import br.uefs.ecomp.mybook.model.SolicitacaoAmizade;
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
    
    private FacadeFrontEnd(){
    }
    
    public static synchronized FacadeFrontEnd getInstance(){
        return (facadeFrontEnd == null) ? facadeFrontEnd = new FacadeFrontEnd() : facadeFrontEnd;
    }
    
    public void inicializa(Stage palcoPrincipal) throws IOException{
        palcoController = new PalcoController(palcoPrincipal);
        FXMLLoader loader = palcoController.getLoaderFXML("Home.fxml");
        home = loader.getController();
        loader = palcoController.getLoaderFXML("LoginView.fxml");
        login = loader.getController();
    }
        
    public Parent carregarPost(Post post) throws IOException{
        return palcoController.InjetarConteudo(post);
    }
    
    public Parent carregarSolicitacao(SolicitacaoAmizade solicitacao) throws IOException{
        return palcoController.InjetarConteudo(solicitacao);
    }
    
    public void mudaCena(Parent conteudo){
        palcoController.mudaCena(conteudo);
    }
}
