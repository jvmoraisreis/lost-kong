package br.ufla.gac106.s2022_1.lostKong;
import java.lang.instrument.Instrumentation;

import br.ufla.gac106.s2022_1.baseJogo.InterfaceUsuario;
import br.ufla.gac106.s2022_1.baseJogo.Terminal;

/**
 * Esta classe é parte da aplicacao "World of Zuul".
 * "World of Zuul" é um jogo de aventura muito simples, baseado em texto.  
 * 
 * Esse analisador lê a entrada do usuario e tenta interpretá-la como um comando "Adventure". 
 * Cada vez que é chamado, ele lê uma linha do terminal e tenta interpretar a linha como um 
 * comando de duas palavras. Ele retorna o comando como um objeto da classe Comando.
 *
 * O analisador tem um conjunto de palavras de comando conhecidas. Ele compara a entrada do 
 * usuário com os comandos conhecidos, e se a entrada não é um dos comandos conhecidos, ele 
 * retorna um objeto comando que é marcado como um comando desconhecido.
 * 
 * @author  Michael Kölling and David J. Barnes (traduzido e adaptado por Julio César Alves)
 */
public class Analisador  {
    // guarda todas as palavras de comando validas
    private PalavrasComando palavrasDeComando;  
    private static Analisador instanciaUnica;  

    private InterfaceUsuario tela;
    

    /**
     * Cria um analisador para ler do terminal.
     */
    private Analisador(InterfaceUsuario tela)  {
        palavrasDeComando = new PalavrasComando();
        this.tela = tela;
    }

    public static Analisador getAnalisador(InterfaceUsuario tela) {
        if (instanciaUnica == null) {
            instanciaUnica = new Analisador(tela);
        }
        return instanciaUnica;
    }

    /**
     * @return O próximo comando do usuario
     */
    public Comando pegarComando()  {

        String linha = tela.obterComando();

        // guardará até duas palavras usadas no comando
        String palavra1 = null;
        String palavra2 = null;


        // quebra o comando do usuário em várias palavras, usando espaços em branco como separadores. 
        // Exemplo: se ele digitar "ir norte", o comando vai gerar um vetor com as duas palavras ["ir", "norte"].
        String[] palavras = linha.trim().split("\\s+");

        // guarda a primeira e a segunda palavras digitadas pelo usuário, se houverem.
        if (palavras.length >= 1) {
            palavra1 = palavras[0];
        }
        if (palavras.length >= 2) {
            palavra2 = palavras[1];
        }

        // Agora verifica se esta palavra é conhecida. Se for, cria um comando com ela.
        // Se não, cria um comando "null" (para comando desconhecido)
        if(palavrasDeComando.ehComando(palavra1)) {
            return new Comando(palavra1, palavra2);
        }
        else {
            return new Comando(null, palavra2); 
        }
    }

    public String getComandos() {
        return PalavrasComando.getComandos();
    }
}
