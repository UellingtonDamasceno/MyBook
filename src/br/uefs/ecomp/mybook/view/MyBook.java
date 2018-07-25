package br.uefs.ecomp.mybook.view;

import br.uefs.ecomp.mybook.exeptions.ArquivoVazioException;
import br.uefs.ecomp.mybook.facade.FacadeBackEnd;
import br.uefs.ecomp.mybook.facade.FacadeFrontEnd;
import java.io.IOException;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Uellington Damasceno
 */
public class MyBook extends Application {
     
    @Override
    public void start(Stage primaryStage) throws IOException, ClassNotFoundException {
        FacadeBackEnd facadeBackEnd = FacadeBackEnd.getInstance();
        FacadeFrontEnd facadeFrontEnd = FacadeFrontEnd.getInstance();
        
        facadeBackEnd.inicializar();
        facadeFrontEnd.inicializa(primaryStage);
        
        try {
            facadeBackEnd.carregarGrafo();
        } catch (ArquivoVazioException ex) {
            System.out.println("Arquivo vazio!");
        }
        
        primaryStage.getIcons().add(new Image("/br/uefs/ecomp/mybook/imagens/book.png"));
        primaryStage.setTitle("MyBook");
        primaryStage.setResizable(false);
        primaryStage.show();
        //System.exit(0);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }


}
