package br.ufla.gac106.s2022_1.lostKong;
import java.io.IOException;
import java.util.ArrayList;

import br.ufla.gac106.s2022_1.baseJogo.InterfaceUsuario;
/**
 * Essa é a classe principal da aplicacao "World of Zull".
 * "World of Zuul" é um jogo de aventura muito simples, baseado em texto.
 * 
 * Usuários podem caminhar em um cenário. E é tudo! Ele realmente precisa ser 
 * estendido para fazer algo interessante!
 * 
 * Para jogar esse jogo, crie uma instancia dessa classe e chame o método "jogar".
 * 
 * Essa classe principal cria e inicializa todas as outras: ela cria os ambientes, 
 * cria o analisador e começa o jogo. Ela também avalia e  executa os comandos que 
 * o analisador retorna.
 * 
 * @author  Michael Kölling and David J. Barnes (traduzido e adaptado por Julio César Alves)
 */

public class Jogo {
    // analisador de comandos do jogo
    private Analisador analisador; 
    // atributo que representa o jogador
    private Jogador jogador;
    private boolean venceuJogo;
    private InterfaceUsuario tela;
  
    /**
     * Cria o jogo e incializa seu mapa interno.
     */
    public Jogo(InterfaceUsuario tela) {
        this.tela = tela;
        jogador = Jogador.getJogador("monk", 100);
        criarAmbientes();
        analisador = Analisador.getAnalisador(tela);
        this.venceuJogo = false;
    }

    /**
     * Cria todos os ambientes e liga as saidas deles, também adiciona itens e personagens aos ambientes
     */
    private void criarAmbientes() {
        Ambiente cachoeira, grandeArvore, campoAberto, caverna, penhasco, topoDaArvore, beiraDoRio, florestaNegra, acampamento, cemiterio, tenda; 



        // Istancia os itens que estarão presentes nos ambientes
        Item esqueleto = new Item("esqueleto", "Um esqueleto desgastado do que parece ter sido uma vez um grande rinoceronte.", false, false);
        Item dente = new Item("dente","Um dente afiado, em formato de lâmina, parece que pertenceu a algum grande predador.",true, false);
        Item chave = new Item("chave","Uma chave que parece pertencer a algum dos caçadores",true,false);
        Item carne = new Item("carne","Um pedaço de carne que pode ser oferecido a algum animal.",true,false);
        Item tocha = new Item("tocha","Uma tocha acesa, seria útil para espantar algum predador.",true,true);

        Inimigo serpente = new Inimigo("Serpente", "uma enorme serpente branca", 25,100, chave);
        

        // Npc que estará presente em um dos ambientes e com algum item.
        Npc onca = new Npc("Estou faminto, procurando por qualquer tipo de alimento!","Não estou mais com fome!", "carne", "filhote de onça", dente, 200);

        // Cria os ambientes
        cachoeira = new Ambiente("cachoeira","Você escuta o barulho de água, ao olhar entre as folhas você se depara com uma enorme cachoeira!");
        grandeArvore = new Ambiente("grandeArvore","Na sua frente, está uma enorme árvore com folhagem densa que te atrapalha a enxergar o topo.");
        campoAberto = new Ambiente("campoAberto", "Você chega a um campo aberto, e percebe que existe um arbusto se movimentando levemente!");
        caverna = new Ambiente("caverna", "Você está em uma caverna com pouca luz, porém, parece sentir o cheiro de algo!.",carne);
        penhasco = new Ambiente("penhasco", "Você chega no topo de um penhasco e se depara com uma linda vista do bosque!");
        topoDaArvore = new Ambiente("topoDaArvore","Após uma longa escalada você chega ao topo, dali é possível avistar o outro lado do rio! Você percebe que existe um cipó preso em um dos galhos da árvore. Algum objeto afiado seria útil aqui!");
        beiraDoRio = new Ambiente("beiraDoRio", "Você chega na beira de um enorme rio, a correnteza está forte, parece que não é possível voltar de onde você veio.");
        florestaNegra = new Ambiente("florestaNegra", "Você se depara com uma  densa floresta na qual as folhas das árvores impedem a passagem da luz do sol, enxergar aqui é quase impossível.");
        cemiterio = new Ambiente("cemiterio", "Você se depara com um ambiente sujo, com diversos ossos que parecem ser de animais mortos. Cuidado ao prosseguir seu caminho.", esqueleto);
        acampamento = new Ambiente ("acampamento","Você chega no que parece ser uma base estabelecida por caçadores, de longe você avista diversas tendas e gaiolas vazias, sua família pode estar por perto.", tocha);
        tenda = new Ambiente("tenda", "Após cruzar o acampamento com cautela, você chega em uma grande tenda, onde se depara com sua família presa em uma das gaiolas.");
            
        // Inicializa as saidas dos ambientes
        cachoeira.ajustarSaida("leste",caverna);
        cachoeira.ajustarSaida("oeste", campoAberto);
        cachoeira.ajustarSaida("sul", penhasco);
        cachoeira.ajustarSaida("norte", grandeArvore);

        grandeArvore.ajustarSaida("sul", cachoeira);
        grandeArvore.ajustarSaida("cima", topoDaArvore);

        penhasco.ajustarSaida("norte", cachoeira);

        campoAberto.ajustarSaida("leste", cachoeira);

        caverna.ajustarSaida("oeste", cachoeira);

        topoDaArvore.ajustarSaida("baixo", grandeArvore);
        topoDaArvore.ajustarSaidaBloqueada("cipo", beiraDoRio,"dente");

        beiraDoRio.ajustarSaida("norte", cemiterio);
        
        cemiterio.ajustarSaida("oeste", acampamento);
        cemiterio.ajustarSaida("sul", beiraDoRio);
        cemiterio.ajustarSaida("leste", florestaNegra);

        florestaNegra.ajustarSaida("oeste", cemiterio);

        acampamento.ajustarSaida("norte", tenda);
        acampamento.ajustarSaida("leste", cemiterio);
        
        tenda.ajustarSaida("sul", acampamento);

        // Define qual ambiente terá um npc
        campoAberto.ajustarNpc(onca);

        florestaNegra.ajustarInimigo(serpente);

        jogador.setAmbienteAtual(cachoeira);
        tela.ambienteAtualMudou(cachoeira);
    }

    /**
     *  Rotina principal do jogo. Fica em loop ate terminar o jogo.
     */
    public void jogar()  {
        imprimirBoasVindas();
        String texto = "";

        // Entra no loop de comando principal. Aqui nós repetidamente lemos comandos e 
        // os executamos até o jogo terminar.
        boolean terminado = false;

        // Se o comando sair for chamado ou o número de movimentos do jogador chegar a 0 o jogo é encerrado        
        while (!terminado && jogador.getMovimentosDisponiveis()  > 0 && jogador.estaVivo() && !venceuJogo) { 
            Comando comando = analisador.pegarComando();
            terminado = processarComando(comando);
        }
        if (jogador.getMovimentosDisponiveis() == 0) {
            texto +="Seu número de movimentos se esgotou. Você não encontrou sua família a tempo :( \nTente novamente! \n";
        }
        if(!jogador.estaVivo()) {
            texto += "Sua vida chegou ao fim :( \nTente novamente! \n";
        }
        if (venceuJogo) {
            texto += "Parabéns! Você resgatou sua família e venceu o jogo!\n";
        }

        texto += "Obrigado por jogar. Até mais!";
        tela.exibirMensagem(texto);
    }

    /**
     * Imprime a mensagem de abertura para o jogador.
     */
    private void imprimirBoasVindas() {
        String texto = "";

        texto += "Bem-vindo ao LostKong! \nLostKong, é uma modificação de World of Zuul, um novo jogo de aventura ainda em desenvolvimento, porém, incrivelmente legal. \nVocê é um filhote de macaco nascido e crescido em uma floresta distante, que teve sua família levada por caçadores. \nSeu objetivo final é encontrar e salvar sua família, mas para isso, você deve primeiro achar uma maneira de chegar ao outro lado do rio, que foi o último local em que você os avistou! \nDigite 'ajuda' se voce precisar de ajuda. \n";

        texto += (jogador.getAmbienteAtual().exibirAmbienteAtual());
        tela.exibirMensagem(texto);
    }

    /**
     * Dado um comando, processa-o (ou seja, executa-o)
     * @param comando O Comando a ser processado.
     * @return true se o comando finaliza o jogo.
     */
    private boolean processarComando(Comando comando)  {
        boolean querSair = false;
        String texto = "";

        if(comando.ehDesconhecido()) {
            texto += "Eu nao entendi o que voce disse...";
            tela.exibirMensagem(texto);
            return false;
        }

        String palavraDeComando = comando.getPalavraDeComando();
        if (palavraDeComando.equals("ajuda")) {
            imprimirAjuda();
        }
        else if (palavraDeComando.equals("ir")) {
            irParaAmbiente(comando);
        }
        else if (palavraDeComando.equals("observar")) {
            observar();
        } 
        else if (palavraDeComando.equals("pegar")) {
            pegar(comando);
        } 
        else if (palavraDeComando.equals("usar")) {
            usar(comando);
        } 
        else if (palavraDeComando.equals("largar")) {
            largar(comando);
        } 
        else if (palavraDeComando.equals("entregar")) {
            entregar(comando);
        } 
        else if (palavraDeComando.equals("atacar")) {
            atacar();
        } 
        else if (palavraDeComando.equals("fugir")) {
            fugir(comando);
        } 
        else if (palavraDeComando.equals("sair")) {
            querSair = sair(comando);
        }

        return querSair;
    }

    /*
     * Caso exista um inimigo no ambiente atual, retorna ao ultimo ambiente visitado pelo jogador
     */
    private void fugir(Comando comando) {
        String texto = "";

        if(jogador.getAmbienteAtual().existeInimigo()){
            if(jogador.visitouAlgumAmbiente()) {
                jogador.setAmbienteAtual(jogador.pegarUltimoAmbiente());
                jogador.decrementarMovimentosDisponiveis();
                texto += "Você fugiu para " + jogador.getAmbienteAtual().getNome();
            }
        }
        else {
            texto += ("Você não tem porque fugir.");
        }

        tela.exibirMensagem(texto);
    }

    /*
     * Verifica se o jogador possui o item a ser largado e caso possua, o remove da mochila do jogador e o adiciona ao ambiente atual.
     */
    private void largar(Comando comando) {
        String texto = "";
        // se não há segunda palavra, não sabemos o que largar...
        if(!comando.temSegundaPalavra()) {            
            texto += "Largar o quê?";
            tela.exibirMensagem(texto);
            return;
        }

        String segundaPalavra = comando.getSegundaPalavra();

        if(!jogador.possuiItem(segundaPalavra)){
            texto+= "Você não possui este item.";
        } 

        else {
            Item item = jogador.removerItem(segundaPalavra);
            jogador.getAmbienteAtual().adicionarItem(item);

            tela.jogadorDescartouItem(item);
            texto += "Você largou " + segundaPalavra +"!";
        }
        tela.exibirMensagem(texto);
    }

    /*
     * Verifica se o jogador possui o item a ser entregue e se existe um npc no ambiente que queira este item, caso sim, entrega o item ao npc e recebe um novo.
     */
    private void entregar(Comando comando) {
        String texto = "";

        // se não há segunda palavra, não sabemos o que entregar...
        if(!comando.temSegundaPalavra()) {            
            texto += ("Entregar o quê?");
            tela.exibirMensagem(texto);
            return;
        }

        String segundaPalavra = comando.getSegundaPalavra();

        if(!jogador.getAmbienteAtual().existeNpc()) {
            texto += "Não há ninguém para entregar o item.";
        }
        else {
            Item item = jogador.removerItem(segundaPalavra);
            try{
                Item item2 = jogador.getAmbienteAtual().entregarItemDesejado(item);
                if(item2 != null) {
                    jogador.adicionarItem(item2);
                    texto += "Você entregou o(a) " + item.getNome() + " e recebeu um(a) " + item2.getNome () + " em troca! \n" + item2.getDescricao();
                    tela.jogadorDescartouItem(item);
                    tela.jogadorPegouItem(item2);
                }
                else {
                    jogador.adicionarItem(item);
                    texto += "Não é o item esperado.";
                }
            }
            catch(NullPointerException e) {
                texto = "Você não possui este item.";
            }
        }
        tela.exibirMensagem(texto);
    }

    /*
     * Verifica se o jogador possui o item que deseja usar, e se o item possui alguma utilidade no ambiente, caso sim, usa o item 
     */
    private void usar(Comando comando) {
        String texto = "";
        // se não há segunda palavra, não sabemos o que usar...
        if(!comando.temSegundaPalavra()) {            
            texto += "Usar o quê?";
            tela.exibirMensagem(texto);
            return;
        }

        String segundaPalavra = comando.getSegundaPalavra();

        if(!jogador.possuiItem(segundaPalavra)){
            texto += "Você não possui este item.";
            tela.exibirMensagem(texto);
        } 
        else {
            //Se o jogador usar a chave na tenda ele vence o jogo
            if(segundaPalavra.equals("chave") && jogador.getAmbienteAtual().getNome().equals("tenda")) {
                finalizarJogo();
            }
            else {
                Item item = jogador.removerItem(segundaPalavra);
                if(jogador.getAmbienteAtual().usarItem(item)) {
                    texto += "Passagem desbloqueada!";
                    tela.jogadorDescartouItem(item);
                    tela.exibirMensagem(texto);
                }
                else{
                    jogador.adicionarItem(item);
                    texto += "Nada acontece...";
                    tela.exibirMensagem(texto);
                }
            }
        }
    }

    private void finalizarJogo() {
        this.venceuJogo = true;
        salvarDados();
    }

    /*
     * Se o jogador atingiu um novo récorde, grava ele no arquivo
     */
    private void salvarDados() {
        int movimentosRealizados = 40 - jogador.getMovimentosDisponiveis();//Número de movimentos feitos para terminar o jogo
        String pontos = "" + movimentosRealizados; 
        String texto = "";

        try{
            int dados = Arquivo.carregarDados();
            
            if((movimentosRealizados < dados)) {
                Arquivo.salvarDados(pontos);
                texto += "Muito bem! Você atingiu um novo recorde! " + movimentosRealizados + " movimentos utilizados.";
            }
            else {
                texto += "Você terminou o jogo realizando " + movimentosRealizados + " movimentos. O recorde é de  " + dados;
            }
        }
        catch(IOException e) {
            texto = "Falha ao salvar arquivo";
        }
        tela.exibirMensagem(texto);
    }

    /**
     * Exibe a localização atual e as informações do ambiente.
    */
    private void observar() {
        String mensagem = "";
        mensagem += jogador.getAmbienteAtual().getDescricaoLonga() + "\n";
        if(jogador.listarItens().isEmpty()) {
            mensagem += "Você não possui itens. \n";
        }
        else{
            mensagem += "Os seus itens são: ";
            ArrayList<String> itens = jogador.listarItens();
            String texto = "";
            for (String string : itens) {
                texto += string + "; ";
            }
            mensagem += texto + "\n";
        }
        mensagem += "Sua vida atual é: " + jogador.getHP() + "\nVocê tem " + jogador.getMovimentosDisponiveis() + " movimentos restantes.";

        tela.exibirMensagem(mensagem);
    }

    /**
     * Exibe informações de ajuda.
     * Aqui nós imprimimos algo bobo e enigmático e a lista de  palavras de comando
     */
    private void imprimirAjuda()  {
        String texto = "";

        texto += "Você esta perdido. Você esta sozinho. Você caminha pela selva. \n";
        texto += "Suas palavras de comando sao: \n";
        texto += "  " + analisador.getComandos();

        tela.exibirMensagem(texto);
    }

    /** 
     * Tenta ir em uma direcao. Se existe uma saída para lá entra no novo ambiente, 
     * caso contrário imprime mensagem de erro.
     */
    private void irParaAmbiente(Comando comando)  {
        String texto = "";
        // se não há segunda palavra, não sabemos pra onde ir...
        if(!comando.temSegundaPalavra()) {            
            texto += "Ir pra onde?";
            tela.exibirMensagem(texto);
            return;
        }

        String direcao = comando.getSegundaPalavra();

        // Tenta sair do ambiente atual
        Ambiente proximoAmbiente = jogador.getAmbienteAtual().getSaida(direcao);

        if (proximoAmbiente == null) {
            texto += "Nao há passagem, ou está bloqueado!";
        }
        else {
            if (jogador.getMovimentosDisponiveis() > 0) {
                jogador.addAmbiente(jogador.getAmbienteAtual());
                jogador.setAmbienteAtual(proximoAmbiente);
                tela.ambienteAtualMudou(proximoAmbiente);
                texto += jogador.getAmbienteAtual().exibirAmbienteAtual();
                jogador.decrementarMovimentosDisponiveis();
                if(jogador.getAmbienteAtual().existeInimigo()) {
                    texto += jogador.getAmbienteAtual().textoInimigo();
                }
            }
        }
        tela.exibirMensagem(texto);
    }

    /** 
     * "Sair" foi digitado. Verifica o resto do comando pra ver se nós queremos 
     * realmente sair do jogo.
     * @return true, se este comando sai do jogo, false, caso contrário.
     */
    private boolean sair(Comando comando)  {
        String texto = "";
        if(comando.temSegundaPalavra()) {
            texto += "Sair o que?";
            tela.exibirMensagem(texto);
            return false;
        }
        else {
            return true;  // sinaliza que nós realmente queremos sair
        }
    }

    /*
     * Verifica se o ambiente possui um item, caso possua, verifica se é o que o jogador deseja pegar, caso sim, verifica se o jogador possui espaço na mochila, se possuir o item é adicionado a mochila.
     */
    private void pegar(Comando comando) {
        String texto = "";
        if(!comando.temSegundaPalavra()) {
            // se não há segunda palavra, não sabemos o que pegar...
            texto += "Pegar o que?";
        }
        else {
            if(jogador.getAmbienteAtual().existeItem()){
                String nomeItem = comando.getSegundaPalavra();
                Item item =jogador.getAmbienteAtual().consultarItem(nomeItem);

                if(item != null) {
                    if(item.ehCarregavel()){
                        if(jogador.adicionarItem(jogador.getAmbienteAtual().coletarItem(nomeItem))){
                            texto = "Você pegou o(a) " + item.getNome();
                            tela.jogadorPegouItem(item);
                        }
                        else {
                            texto = "Sua mochila está cheia!";
                        }
                    }
                    else {
                        texto = "Você não pode carregar este item.";
                    }
                }
                else {
                    texto = "Este item não está presente no ambiente.";
                }
            }
            else {
                texto ="Não há nada aqui.";
            }
        }
        tela.exibirMensagem(texto);
    }

    private void atacar() {
        String texto = "";
        if(!jogador.getAmbienteAtual().existeInimigo()) {
            texto = "Não existe um inimigo para atacar aqui.";
        }
        else {
            Inimigo inimigo = jogador.getAmbienteAtual().getInimigo();
            if(!jogador.possuiItemDeAtaque()) {
                inimigo.atacar(jogador);
                texto += "Você não possui um item para atacar. E tomou uma mordida da(o) " + inimigo.getNome() + " que causou " + inimigo.getDano() + " de dano! \n" + "A sua vida atual é : " + jogador.getHP() + "\nNão tente atacar sem um item para isso!";
            }
            else{
                jogador.atacar(inimigo);
                if(inimigo.getAssustado()) {
                    String nomeItem = inimigo.listarItens().get(0);
                    jogador.getAmbienteAtual().adicionarItem(inimigo.removerItem(nomeItem));
                    texto += "Você ataca o(a)" + inimigo.getNome() + " e ele(a) foge assutado deixando cair um(a) " + nomeItem;
                    jogador.getAmbienteAtual().removerInimigo();
                }
            }
        }
        tela.exibirMensagem(texto);
    }
}