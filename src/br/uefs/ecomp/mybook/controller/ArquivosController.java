package br.uefs.ecomp.mybook.controller;

import br.uefs.ecomp.mybook.exeptions.ArquivoInexistenteException;
import br.uefs.ecomp.mybook.exeptions.ListaDiretorioVaziaException;
import br.uefs.ecomp.mybook.exeptions.ListaConteudoVaziaException;
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
public class ArquivosController {

    public ArquivosController() {

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
        File arquivo = new File(criaDiretorio(caminhoDiretorio) + "\\"+ validaNomeArquivo(nomeArquivo));
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

    public File criaDiretorio(String nome) {
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

    private File[] pegarArquivos(String caminhoDiretorio, final String extensaoFiltro) throws ListaConteudoVaziaException {
        File diretorio = (caminhoDiretorio.isEmpty()) ? new File("").getAbsoluteFile() : new File(caminhoDiretorio);
        FileFilter filtro = null;
        if (extensaoFiltro != null) {
            filtro = (File nomeDoCaminho) -> nomeDoCaminho.getAbsolutePath().endsWith(extensaoFiltro);
        }
        if(diretorio.getTotalSpace() == 0){
            throw new ListaConteudoVaziaException();
        }
        return diretorio.listFiles(filtro);
    }

    public List lerTodosArquivosDiretorio(String caminhoDiretorio, String extensaoFiltro) throws ListaConteudoVaziaException {
        File[] arquivosDiretorio = this.pegarArquivos(caminhoDiretorio, extensaoFiltro);
        LinkedList listaConteudo = new LinkedList();
        for (File atual : arquivosDiretorio) {
            try {
                listaConteudo.add(lerObjeto(atual));
            } catch (IOException | ClassNotFoundException | ArquivoVazioException ex) {}
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
        if (!nome.contains(".txt")) {
            nome += ".txt";
        }
        nome = nome.replaceAll("[^A-Za-z0-9áàâãçaéèêíìîóòôúçñÁÀÂÉÈÊÍÌÓÔÚÇÑ.\\s ]", "");
        return nome;
    }

    public boolean deletarArquivo(String caminho, String nomeArquivo) throws ArquivoInexistenteException {
        File arquivoDeletado = new File(caminho + "\\" + validaNomeArquivo(nomeArquivo));
        if (!arquivoDeletado.exists()) {
            throw new ArquivoInexistenteException();
        } else {
            System.out.println("Deletado com sucesso!!!");
            return arquivoDeletado.delete();
        }
    }

    public int escreveVariosDiretorios(List<String> diretorios, String nomeArquivo, Object post) throws ListaDiretorioVaziaException {
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
        } else {
            throw new ListaDiretorioVaziaException();
        }
    }

    public int deletarVariosArquivos(List<String> diretorios, String nomeArquivo) throws ListaDiretorioVaziaException {
        if (!diretorios.isEmpty()) {
            int erros[] = new int[1];
            diretorios.forEach((String diretorio) -> {
                try {
                    deletarArquivo(diretorio, nomeArquivo);
                } catch (ArquivoInexistenteException ex) {
                    erros[0]++;
                }
            });
            return erros[0];
        } else {
            throw new ListaDiretorioVaziaException();
        }
    }
    
    public File criaArquivo(String caminho, String nomeArquivo) throws IOException{
        File diretorio = criaDiretorio(caminho);
        File arquivo = new File(diretorio.toString()+"\\"+validaNomeArquivo(nomeArquivo));
        if(!arquivo.exists()){
            arquivo.createNewFile();
            
        }
        return arquivo;
    }
}
