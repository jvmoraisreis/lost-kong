package br.ufla.gac106.s2022_1.baseJogo;
import java.util.Scanner;


public class Terminal implements InterfaceUsuario{
        /**
     * Exibe uma nova mensagem para o jogador
     * 
     * @param mensagem Mensagem a ser exibida para o usuário.
     */
    public void exibirMensagem(String mensagem){
        System.out.println(mensagem);
    }
    
    /**
     * Acrescenta informação à uma mensagem anterior
     * 
     * @param mensagem Informação a ser acrecentada na mensagem anterior
     */
    public void continuarMensagem(String mensagem){}  
    
    /**
     * Obtém um comando do jogador. Deve ser chamado apenas na classe Analisador.
     */
    public String obterComando(){
        Scanner entrada = new Scanner(System.in);
        // guardará uma linha inteira
        String linha;
        
        // imprime o prompt
        System.out.print("> ");
        
        // obtém uma linha de comando do usuário
        linha = entrada.nextLine();
        return linha;
    }
    
    /**
     * Obtém uma informação do usuário como String. 
     * 
     * Obs.: não deve ser usado para comandos. No caso de comandos use 'obterComando'.
     * 
     * @param instrucao Mensagem de instrução para o usuário (diz qual informação está sendo solicitada)
     */
    public String obterInformacao(String instrucao){return null;}
    
    /**
     * Informa a Interface do Usuário que o jogador mudou de ambiente.
     * 
     * @param ambiente Objeto do novo ambiente atual.
     */
    public void ambienteAtualMudou(EntidadeGrafica ambiente){}
    
    /**
     * Informa a Interface do Usuário que o jogador pegou um item (ou similar).
     * 
     * @param item Objeto do item que o usuário pegou.
     */
    public void jogadorPegouItem(EntidadeGrafica item){}
    
    /**
     * Informa a Interface do Usuário que o jogador não tem mais um item (ou similar).
     * 
     * @param item Objeto do item que o usuário não tem mais.
     */
    public void jogadorDescartouItem(EntidadeGrafica item){}
    
}
