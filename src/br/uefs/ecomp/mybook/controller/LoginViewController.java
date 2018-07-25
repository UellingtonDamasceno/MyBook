package br.uefs.ecomp.mybook.controller;

import br.uefs.ecomp.mybook.exeptions.AtributoVazioException;
import br.uefs.ecomp.mybook.exeptions.EmailOuSenhaInvalidoException;
import br.uefs.ecomp.mybook.exeptions.EntryNaoExisteException;
import br.uefs.ecomp.mybook.exeptions.SemUsuariosCadastradosException;
import br.uefs.ecomp.mybook.facade.FacadeBackEnd;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Uellington Damasceno
 */
public class LoginViewController implements Initializable {

    @FXML
    private VBox vboxBaseInfoGit;
    @FXML
    private Label lblCadastro;
    @FXML
    private TextField txtLogin;
    @FXML
    private PasswordField txtSenha;

    FacadeBackEnd facade;
    @FXML
    private Label lblDesenvolvido;
    @FXML
    private Label lblGitUellington;
    @FXML
    private Label lblGitIvan;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        facade = FacadeBackEnd.getInstance();
    }

    @FXML
    private void entrar(ActionEvent event) {
        try {
            facade.login(txtLogin.getText(), txtSenha.getText());
        } catch (EntryNaoExisteException ex) {
            System.out.println("User não encontrado!");
        } catch (EmailOuSenhaInvalidoException ex) {
            System.out.println("Email ou senha invalido");
        } catch (AtributoVazioException ex) {
            System.out.println("Existe algum campo vazio!");
        } catch (SemUsuariosCadastradosException ex) {
            System.out.println("Sem user na rede!");
        }

    }

    @FXML
    private void exibirLinkGit(MouseEvent event) {
        lblDesenvolvido.setVisible(true);
        lblGitUellington.setVisible(true);
        lblGitIvan.setVisible(true);
    }

    @FXML
    private void ocutarLinkGit(MouseEvent event) {
        lblDesenvolvido.setVisible(false);
        lblGitUellington.setVisible(false);
        lblGitIvan.setVisible(false);
    }

    @FXML
    private void sublinheCadastre(MouseEvent event) {
        lblCadastro.setUnderline(true);
    }

    @FXML
    private void mudaTelaCadasto(MouseEvent event) {
    }

    @FXML
    private void tirarSublinhado(MouseEvent event) {
        lblCadastro.setUnderline(false);
    }

}
