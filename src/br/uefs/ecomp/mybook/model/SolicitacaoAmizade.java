package br.uefs.ecomp.mybook.model;



import br.uefs.ecomp.mybook.model.User;
import java.util.Date;

/**
 *
 * @author Uellington Damasceno
 */
public class SolicitacaoAmizade {
    private User remetente;
    private User destinatario;
    private Date data;
    
    public SolicitacaoAmizade(User remetente, User destinatario, Date data){
        this.remetente = remetente;
        this.destinatario = destinatario;
        this.data = data;
    }
    
    public User getRemetente(){
        return remetente;
    }
    
    public User getDestinatario(){
        return destinatario;
    }
    
    public Date getData(){
        return data;
    }
}
