package br.uefs.ecomp.mybook.controller;

import br.uefs.ecomp.mybook.exeptions.ArquivoInexistenteException;
import br.uefs.ecomp.mybook.exeptions.ListaAmigosVaziaException;
import br.uefs.ecomp.mybook.exeptions.ListaConteudoVazia;
import br.uefs.ecomp.mybook.exeptions.ArquivoVazioException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Uellington Damasceno
 */
public class ControllerArquivos {

    public ControllerArquivos() {

    }

    public void escreveObjeto(String caminhoDiretorio, String nomeArquivo, Serializable objeto) throws FileNotFoundException, IOException {
        String nome = validaNomeArquivo(nomeArquivo);
        String diretorio = criaDiretorio(caminhoDiretorio).getAbsolutePath();

        File arquivoDestino = new File(diretorio + "\\" + nome);
        if (!arquivoDestino.exists()) {
            arquivoDestino.createNewFile();
        } else {
            apagaRegistros(arquivoDestino);
        }
        ObjectOutputStream saida;
        saida = new ObjectOutputStream(new FileOutputStream(arquivoDestino));
        saida.writeObject(objeto);
        saida.close();
    }

    public Object lerObjeto(String caminhoDiretorio, String nomeArquivo) throws IOException, ClassNotFoundException, ArquivoVazioException {
        File arquivo = new File(criaDiretorio(caminhoDiretorio) + nomeArquivo);
        if (!arquivo.exists()) {
            throw new IOException();
        }
        if (arquivo.length() > 0) {
            ObjectInputStream entrada;
            entrada = new ObjectInputStream(new FileInputStream(arquivo));
            Object conteudo = entrada.readObject();
            entrada.close();
            return conteudo;
        }
        throw new ArquivoVazioException();
    }

    private File criaDiretorio(String nome) {
        File diretorio = new File(nome);
        if (!diretorio.exists()) {
            diretorio.mkdir();
        }
        return diretorio;
    }

    private void apagaRegistros(File arquivo) throws FileNotFoundException, IOException {
        Writer clean = new BufferedWriter(new FileWriter(arquivo));
        clean.close();
    }

    private File[] pegarArquivos(String caminhoDiretorio, final String extensaoFiltro) {
        File diretorio = (caminhoDiretorio.isEmpty()) ? new File("").getAbsoluteFile() : new File(caminhoDiretorio);
        FileFilter filtro = null;
        if (extensaoFiltro != null) {
            filtro = (File nomeDoCaminho) -> nomeDoCaminho.getAbsolutePath().endsWith(extensaoFiltro);
        }
        return diretorio.listFiles(filtro);
    }

    public List lerTodosArquivosDiretorio(String caminhoDiretorio, String extensaoFiltro) throws ListaConteudoVazia {
        File[] arquivosDiretorio = this.pegarArquivos(caminhoDiretorio, extensaoFiltro);
        LinkedList listaConteudo = new LinkedList();
        for (File atual : arquivosDiretorio) {
            try {
                listaConteudo.add(lerObjeto(atual));
            } catch (IOException | ClassNotFoundException | ArquivoVazioException ex) {
            }
        }
        if (listaConteudo.isEmpty()) {
            throw new ListaConteudoVazia();
        }
        return listaConteudo;
    }

    private Object lerObjeto(File arquivo) throws IOException, ClassNotFoundException, ArquivoVazioException {
        if (!arquivo.exists()) {
            throw new IOException();
        }
        if (arquivo.length() > 0) {
            ObjectInputStream entrada;
            entrada = new ObjectInputStream(new FileInputStream(arquivo));
            Object conteudo = entrada.readObject();
            entrada.close();
            return conteudo;
        }
        throw new ArquivoVazioException();
    }

    private String validaNomeArquivo(String nome) {
        System.out.println(nome);
        if (!nome.contains(".txt")) {
            nome += ".txt";
        }
        nome = nome.replaceAll("[^A-Za-z0-9áàâãçaéèêíìîóòôúçñÁÀÂÉÈÊÍÌÓÔÚÇÑ.\\s ]", "");
        System.out.println(nome);
        return nome;
    }

    public boolean deletarArquivo(String caminho, String nomeArquivo) throws ArquivoInexistenteException {
        File arquivoDeletado = new File(caminho + "\\" + nomeArquivo);
        if (!arquivoDeletado.exists()) {
            throw new ArquivoInexistenteException();
        } else {
            return arquivoDeletado.delete();
        }
    }

    public int escreveVariosDiretorios(List<String> diretorios, String nomeArquivo, Object post) throws ListaAmigosVaziaException {
        if (!diretorios.isEmpty()) {
            int erros[] = new int[1];
            diretorios.forEach((diretorio) -> {
                File arquivoPost = new File(diretorio + "\\" + nomeArquivo);
                if (!arquivoPost.exists()) {
                    try {
                        escreveObjeto(diretorio, nomeArquivo, (Serializable) post);
                    } catch (IOException ex) {
                        erros[0]++;
                    }
                }
            });
            return erros[0];
        }else{
            throw new ListaAmigosVaziaException();
        }
    }
}
