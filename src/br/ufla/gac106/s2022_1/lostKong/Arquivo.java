package br.ufla.gac106.s2022_1.lostKong;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Arquivo {
    
    public static void salvarDados(String dados) throws IOException {
        try(FileWriter arq = new FileWriter("recorde.txt")) {
            arq.write(dados);
            arq.close();
        }
        catch(IOException e) {
            throw e;
        }
    }

    public static int carregarDados() throws IOException {
        try {
            BufferedReader arq = new BufferedReader(new FileReader("recorde.txt"));
            String linha = arq.readLine();

            if(linha == null) {
                arq.close();
                return 40;
            }

            int dado = Integer.parseInt(linha);
            arq.close();
            return dado;
        }
        catch(IOException e) {
            throw e;
        }
    }
}