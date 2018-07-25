package br.uefs.ecomp.mybook.controller;

import br.uefs.ecomp.mybook.model.User;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Uellington Damasceno
 */
public class SessaoController {

    private User userOn;

    public SessaoController() {
    }

    public User getUserOn() {
        return userOn;
    }

    public void setUserOn(User userOn) {
        this.userOn = userOn;
    }

    public String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
