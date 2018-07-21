/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uefs.ecomp.mybook.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;

/**
 * FXML Controller class
 *
 * @author Uellington Damasceno
 */
public class HomeViewController implements Initializable {

    @FXML
    private ImageView imageViewLogo;
    @FXML
    private Button bntEditarImagem;
    @FXML
    private ImageView imageViewUser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void EditarImagem(ActionEvent event) {
    }

    @FXML
    private void setVisibleBotaoEditar(DragEvent event) {
    }
    
}
