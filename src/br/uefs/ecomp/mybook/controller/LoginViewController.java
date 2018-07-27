package br.uefs.ecomp.mybook.controller;

import br.uefs.ecomp.mybook.exeptions.AtributoVazioException;
import br.uefs.ecomp.mybook.exeptions.EmailOuSenhaInvalidoException;
import br.uefs.ecomp.mybook.exeptions.EntryNaoExisteException;
import br.uefs.ecomp.mybook.exeptions.SemUsuariosCadastradosException;
import br.uefs.ecomp.mybook.exeptions.UserJaExisteExceptions;
import br.uefs.ecomp.mybook.facade.FacadeBackEnd;
import br.uefs.ecomp.mybook.facade.FacadeFrontEnd;
import br.uefs.ecomp.mybook.model.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Uellington Damasceno
 */
public class LoginViewController implements Initializable {

    FacadeBackEnd facadeBackEnd;
    FacadeFrontEnd facadeFrontEnd;
    
    @FXML private VBox vboxBaseInfoGit;
    @FXML private Label lblCadastro;
    @FXML private TextField txtLogin;
    @FXML private PasswordField txtSenha;
    @FXML private Label lblDesenvolvido;
    @FXML private Label lblGitUellington;
    @FXML private Label lblGitIvan;
    @FXML private AnchorPane paneLogin;
    @FXML private AnchorPane paneCadastro;
    @FXML private TextField txtNome;
    @FXML private TextField txtEmail;
    @FXML private TextField txtEndereco;
    @FXML private DatePicker datePickerDataN;
    @FXML private TextField txtTelefone;
    @FXML private TextField txtLoginCadastro;
    @FXML private PasswordField txtSenhaCadastro; 
    @FXML private Label lblInfo;
    @FXML
    private StackPane spLoginCadastro;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        facadeBackEnd = FacadeBackEnd.getInstance();
        facadeFrontEnd = FacadeFrontEnd.getInstance();
    }

    @FXML
    private void entrar(ActionEvent event) {
        lblInfo.setText("");
        try {
            User userOn = facadeBackEnd.login(txtLogin.getText(), txtSenha.getText());
            Parent home = facadeFrontEnd.novoLogin(userOn);
            facadeFrontEnd.mudaCena(home);
        } catch (EntryNaoExisteException ex) {
            lblInfo.setText("Usuario não cadastrado!");
        } catch (EmailOuSenhaInvalidoException ex) {
            lblInfo.setText("Email ou senha invalido!");
        } catch (AtributoVazioException ex) {
            lblInfo.setText("Preencha todos os campos!");
        } catch (SemUsuariosCadastradosException ex) {
            lblInfo.setText("Não existe usuários cadastrados!");
        } catch (IOException ex) {
            System.out.println("Erro ao ler arquivo");
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
        paneLogin.setVisible(false);
        paneCadastro.setVisible(true);
    }

    @FXML
    private void tirarSublinhado(MouseEvent event) {
        lblCadastro.setUnderline(false);
    }

    @FXML
    private void addNovoUser(ActionEvent event) {
        String login = txtLoginCadastro.getText();
        String senha = txtSenhaCadastro.getText();
        String nome = txtNome.getText();
        String email = txtEmail.getText();
        String dataN = datePickerDataN.getValue().toString();
        String endereco = txtEndereco.getText();
        String telefone = txtTelefone.getText();
        try {
            facadeBackEnd.addNovoUser(login, senha, nome, email, dataN, endereco, telefone);
            paneLogin.setVisible(true);
            paneCadastro.setVisible(false);
        } catch (AtributoVazioException ex) {
            System.out.println("Algum campo está vazio!");
        } catch (IOException ex) {
            System.out.println("Falha ao escrever grafo!");
        } catch (UserJaExisteExceptions ex) {
            System.out.println("Usuario Cadastrado!");
        }
    }

    @FXML
    private void cancelarCadastro(ActionEvent event) {
        lblInfo.setText("");
        paneLogin.setVisible(true);
        paneCadastro.setVisible(false);
    }

}
