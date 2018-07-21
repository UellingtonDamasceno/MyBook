package br.uefs.ecomp.mybook.view;

import br.uefs.ecomp.mybook.exeptions.AtributoVazioException;
import br.uefs.ecomp.mybook.exeptions.UserJaExisteExceptions;
import br.uefs.ecomp.mybook.facade.Facade;
import br.uefs.ecomp.mybook.model.User;
import java.io.IOException;
import java.util.LinkedList;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Uellington Damasceno
 */
public class MyBook extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Facade facade = Facade.getInstance();
       
        System.exit(0);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
