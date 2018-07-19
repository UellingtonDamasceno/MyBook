package br.uefs.ecomp.mybook.view;

import br.uefs.ecomp.mybook.controller.ControllerArquivos;
import br.uefs.ecomp.mybook.exeptions.ListaConteudoVazia;
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
        ControllerArquivos ca = new ControllerArquivos();

        System.out.println(this.getClass().getName());
         LinkedList users = new LinkedList();
        for (int i = 0; i < 10; i++) {
           users.add(new User("1", "2", String.valueOf(i+8), "4", "5", "6", "7"));
        }
        ca.escreveObjeto("", "Users.txt", users);
       
        try {
            users = (LinkedList) ca.lerTodosArquivosDiretorio("", ".txt");
            System.out.println(users);
        } catch (ListaConteudoVazia ex) {
            System.out.println("Lista Vazia");
        }
        
        System.exit(0);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
