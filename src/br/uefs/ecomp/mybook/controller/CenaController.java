package br.uefs.ecomp.mybook.controller;

import javafx.scene.Parent;

/**
 *
 * @author Uellington Damasceno
 */
public class CenaController {
    private Parent cenaLogin;
    private Parent cenaHome;
    
    public CenaController(){
    }

    public Parent getCenaLogin() {
        return cenaLogin;
    }

    public void setCenaLogin(Parent cenaLogin) {
        this.cenaLogin = cenaLogin;
    }

    public Parent getCenaHome() {
        return cenaHome;
    }

    public void setCenaHome(Parent cenaHome) {
        this.cenaHome = cenaHome;
    }
    
}
