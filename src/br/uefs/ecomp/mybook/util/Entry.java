package br.uefs.ecomp.mybook.util;

/**
 *
 * @author Uellington Damasceno
 */
public class Entry {

    private Object chave;
    private Object valor;

    public Entry(Object chave, Object valor) {
        this.chave = chave;
        this.valor = valor;
    }

    public Object getChave() {
        return chave;
    }

    public void setChave(Object chave) {
        this.chave = chave;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof Entry) {
            Entry outra = (Entry) o;
            if (chave != null && outra.getChave() != null) {
                return chave.equals(outra.getChave());
            }
            if (chave == null && outra.getChave() == null) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return chave.hashCode();
    }
    
    @Override
    public String toString(){
        return this.getChave().toString();
    }

}
