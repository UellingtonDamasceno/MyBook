package br.uefs.ecomp.mybook.model;

import java.util.Date;

/**
 *
 * @author Uellington Damasceno
 */
public class SolicitacaoAmizade {

    private final User remetente;
    private final User destinatario;
    private final Date data;

    public SolicitacaoAmizade(User remetente, User destinatario) {
        this(remetente, destinatario, null);
    }

    public SolicitacaoAmizade(User remetente, User destinatario, Date data) {
        this.remetente = remetente;
        this.destinatario = destinatario;
        this.data = data;
    }

    public User getRemetente() {
        return remetente;
    }

    public User getDestinatario() {
        return destinatario;
    }

    public Date getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SolicitacaoAmizade)) {
            SolicitacaoAmizade outra = (SolicitacaoAmizade) o;
            if (outra.getDestinatario().equals(this.destinatario) && outra.getRemetente().equals(this.remetente)) {
                return true;
            }
        }
        return false;
    }
}
