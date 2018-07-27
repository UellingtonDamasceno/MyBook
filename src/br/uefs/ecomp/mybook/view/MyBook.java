package br.uefs.ecomp.mybook.view;

import br.uefs.ecomp.mybook.exeptions.ArquivoVazioException;
import br.uefs.ecomp.mybook.facade.FacadeBackEnd;
import br.uefs.ecomp.mybook.facade.FacadeFrontEnd;
import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
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

        try {
            facadeBackEnd.carregarGrafo();
        } catch (ArquivoVazioException ex) {
            System.out.println("Arquivo vazio!");
        }
        facadeBackEnd.getRedeSocial().imprime();
        facadeFrontEnd.inicializa(primaryStage);
        primaryStage.setScene(new Scene(facadeFrontEnd.getTelaLogin()));
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
