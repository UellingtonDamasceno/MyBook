package br.uefs.ecomp.mybook.util;

import br.uefs.ecomp.mybook.exeptions.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author Uellington Damasceno
 */
public class HashSet {

    private int tamanho;
    private final double LOAD_FACTOR = 0.5;
    private final Entry EMPATY = new Entry(null, null);
    private Entry[] chaves;

    public HashSet() {
        this(31);
    }

    public HashSet(int tamanho) {
        chaves = new Entry[tamanho];
    }

    public int getTamanho() {
        return tamanho;
    }
    
    public Entry[] getChaves(){
        return chaves;
    }
    
    public boolean estaVazio() {
        return tamanho == 0;
    }

    private boolean isEmpaty(int pos) {
        return chaves[pos] == null || chaves[pos].equals(EMPATY);
    }

    private void resize() {
        Entry[] aux = chaves;
        chaves = new Entry[chaves.length * 2];
        for (Entry atual : aux) {
            if (atual != null && !atual.equals(EMPATY)) {
                put(atual.getChave(), atual.getValor());
            }
        }
    }

    private int procuraPosicao(int posicao, Entry procurada) {
        int primeiroVazio = -1;
        while (chaves[posicao] != null && !chaves[posicao].equals(procurada)) {
            if (primeiroVazio == -1 && !chaves[posicao].equals(EMPATY)) {
                primeiroVazio = posicao;
            }
            posicao = (posicao + 1) % chaves.length;
        }
        if (chaves[posicao] == null && primeiroVazio != -1) {
            return primeiroVazio;
        } else {
            return posicao;
        }
    }

    private void remove(int hashCode, Object chave) {
        for (int i = hashCode; chaves[i] != null; i = (i++) % chaves.length) {
            if (chaves[i].equals(EMPATY)) {
                //Usado para evitar NullPointerException.
            } else if (chaves[i].getChave().equals(chave)) {
                chaves[i] = EMPATY;
                tamanho--;
            }
        }
    }

    public void put(Object chave, Object valor) {
        Entry entry = new Entry(chave, valor);
        int posicao = procuraPosicao(gerarHashCode(chave), entry);

        if (isEmpaty(posicao)) {
            chaves[posicao] = entry;
            tamanho++;

            if (tamanho / (double) chaves.length > LOAD_FACTOR) {
                resize();
            }
        }
    }

    public void remove(Object chave) throws EntryNaoExisteException, HashVaziaException {
        if (estaVazio()) {
            throw new HashVaziaException();
        }
        int hashCode = gerarHashCode(chave);
        if (chaves[hashCode] == null) {
            throw new EntryNaoExisteException();
        } else if (chaves[hashCode].getChave().equals(chave)) {
            chaves[hashCode] = EMPATY;
            tamanho--;
        } else {
            remove(hashCode, chave);
        }
    }

    public boolean contem(Object chave) {
        int hashCode = gerarHashCode(chave);
        for (int i = hashCode; chaves[i] != null; i = (i++) % chaves.length) {
            Entry atual = chaves[i];
            if (atual == null) {
                return false;
            }
             if (!atual.equals(EMPATY) && atual.getChave().equals(chave)) {
                return true;
            }
        }
        return false;
    }

    public Object getElement(Object chave) throws EntryNaoExisteException {
        return ((Entry) this.get(chave)).getChave();
    }

    public Object get(Object chave) throws EntryNaoExisteException {
        int hashCode = gerarHashCode(chave);
        for (int i = hashCode; chaves[i] != null; i = (i++) % chaves.length) {
            Entry atual = chaves[i];
            if (atual == null) {
                throw new EntryNaoExisteException();
            } else if (atual.equals(EMPATY)) {
                //Usado para evitar NullPointerException.
            } else if (atual.getChave().equals(chave)) {
                return atual;
            }
        }
        throw new EntryNaoExisteException();
    }

    public Iterator iterator() throws HashVaziaException {
        return toList().iterator();
    }
        
    public LinkedList toList() throws HashVaziaException {
        if (!estaVazio()) {
            LinkedList listaAux = new LinkedList();
            for (Entry atual : chaves) {
                listaAux.add(atual.getChave());
            }
            return listaAux;
        }
        throw new HashVaziaException();
    }

    private int gerarHashCode(Object chave) {
        return Math.abs(chave.hashCode() % chaves.length);
    }
    
    @Override
    public String toString(){
        return Arrays.toString(this.getChaves());
    }
}
