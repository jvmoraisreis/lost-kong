package br.ufla.gac106.s2022_1.lostKong;
import java.util.HashMap;
import java.util.ArrayList;

import br.ufla.gac106.s2022_1.baseJogo.EntidadeGrafica;
/**
 * Classe Ambiente - um ambiente em um jogo adventure.
 *
 * Esta classe é parte da aplicação "World of Zuul".
 * "World of Zuul" é um jogo de aventura muito simples, baseado em texto.  
 *
 * Um "Ambiente" representa uma localização no cenário do jogo. Ele é conectado aos 
 * outros ambientes através de saídas. As saídas são nomeadas como norte, sul, leste 
 * e oeste. Para cada direção, o ambiente guarda uma referência para o ambiente vizinho, 
 * ou null se não há saída naquela direção.
 * 
 * @author  Michael Kölling and David J. Barnes (traduzido e adaptado por Julio César Alves)
 */

public class Ambiente extends EntidadeGrafica  {
    // nome do ambiente
    private String nome;
    // descrição do ambiente
    private String descricao;
    // ambientes vizinhos de acordo com a direção
    private HashMap<String, Ambiente> saidas;
    // itens presente no ambiente
    private ArrayList<Item> itens;
    // nome da saida bloqueada
    private String saidaBloqueada;
    // chave para desbloquear uma saída bloqueada, caso exista alguma
    private String chaveDoAmbiente;
    // Npc presente no ambiente 
    private Npc npc;
    //Inimigo presente no ambiente
    private Inimigo inimigo;
    //Obs: Decidimos não usar o polimorfismo nas classes Npc e Inimigo aqui, pois isso complicaria um pouco as classes da Herança e não se pagaria.
    

    /**
     * Cria um ambiente com a "descricao" passada. Inicialmente, ele não tem saidas. 
     * "descricao" eh algo como "uma cozinha" ou "um jardim aberto".
     * @param descricao A descrição do ambiente.
     */
    public Ambiente(String nome, String descricao)  {
        super("imagens/" + nome + ".jpg");
        this.nome = nome;
        this.descricao = descricao;
        this.chaveDoAmbiente = null;
        this.npc = null;

        saidas = new HashMap<>();
        itens = new ArrayList<>();
    }

    /*
     * Construtor com atributo item.
     */
    public Ambiente(String nome, String descricao, Item item)  {
        this(nome,descricao);
        itens.add(item);
    }

    /*
     * Retorna o nome do ambiente.
     */
    public String getNome() {
        return nome;
    }

    /*
     * Adiciona um personagem ao ambiente
     */
    void ajustarNpc(Npc npc) {
        this.npc = npc;
    }
    
    /*
     * Define uma saida bloqueada e o item que a desbloqueia
     */
    public void ajustarSaidaBloqueada(String direcao, Ambiente ambiente, String item) {
        ajustarSaida(direcao, ambiente);
        this.saidaBloqueada = direcao;
        this.chaveDoAmbiente = item;
    }

    /*
     * Adiciona um item largado ao ambiente 
     */
    public void adicionarItem(Item item) {
        itens.add(item);
    }

    /*
     * Indica se o ambiente tem algum item
     */
    public boolean existeItem() {
        if(itens.size() > 0) {
            return true;
        }
        return false;
    }

    /*
     * Retorna um item caso ele exista
     */
    public Item consultarItem(String nome) {
        if(existeItem()) {
            for(Item i : itens) {
                if(i.getNome().equals(nome)) {
                    return i;
                }
            } 
        }
        return null;
    }

    /*
     * Verifica se existe um item no ambiente, caso sim, remove o item do ambiente e o retorna
     */
    public Item coletarItem(String nome) {
        if(existeItem()){
            Item itemAux = consultarItem(nome);
            itens.remove(itemAux);
            return itemAux;
        }
        return null;
    }

    /*
     * Se o item utilizado for a chave do ambiente, desbloqueia uma saída do ambiente
     */
    public boolean usarItem(Item item) {
        if(chaveDoAmbiente != null){
            if(chaveDoAmbiente.equals(item.getNome())) {
                saidaBloqueada = null;
                return true;
            }
        }
        return false;
    }

    /*
     * Verifica se existe Inimigo no ambiente
     */
    public boolean existeInimigo() {
        if(inimigo != null) {
            return true;
        }
        return false;
    }

    /*
     * Retorna o inimigo do ambiente, caso não exista retorna null
     */
    public Inimigo getInimigo() {
        if(existeInimigo()) {
            return this.inimigo;
        }
        return null;
    }

    /*
     * Remove o inimigo do ambiente
     */
    public void removerInimigo() {
        if(existeInimigo()) {
            this.inimigo = null; 
        }
    }

    /*
     * Adiciona um Inimigo ao ambiente
     */
    void ajustarInimigo(Inimigo inimigo) {
        this.inimigo = inimigo;
    }

    public String textoInimigo() {  
        String texto = "Um(a) "+ inimigo.getNome() +" avança ferozmente em sua direção! \n";
        return texto;
    }

    /*
     * Retorna um abiente, após selecionada uma direção de saída
     */ 
    public Ambiente getSaida(String direcao) {
        if(saidaBloqueada != null){
            if(saidaBloqueada.equals(direcao)) {
                return null;
            }
        }
        return saidas.get(direcao);
    } 

    /**
     * Define a saída do ambiente. Cada direção ou leva a um outro ambiente ou é null 
     * (indicando que não tem nenhuma saída para lá).
     */
    public void ajustarSaida(String direcao, Ambiente ambiente)  {
        saidas.put(direcao, ambiente);
    }

    /**
     * @return A descrição do ambiente.
     */
    public String getDescricao() {
        return descricao;
    }

    /*
     * Retorna todas as saídas possíveis do ambiente
     */
    public String getSaidas() {
        String textoSaidas = "Saidas: ";
        for(String direcao : saidas.keySet()) {
            textoSaidas = textoSaidas + direcao + " ";
        }
        return textoSaidas;
    }

    /*
     * Exibe a descrição do ambiente e suas saídas possíveis
     */
    public String exibirAmbienteAtual() {
        String descricaoLonga = "";
        descricaoLonga += getDescricao() + "\n" ;
        if(getSaidas() != null){
            descricaoLonga += getSaidas();
        }
        descricaoLonga += "\n";
        return descricaoLonga;
    }

    /*
     * Verifica se existe algum npc no ambiente
     */
    public boolean existeNpc() {
        if(npc != null) {
            return true;
        }
        return false;
    }

    /*
     * Entrega um item ao Npc que está no ambiente
     */
    public Item entregarItemDesejado(Item item) {
        if(item != null) {
            return npc.entregarItem(item);
        }
        else {
            throw new NullPointerException();
        }
    }


    /*
     * Exibe a descrição longa do ambiente, que contém todos os dados, como por exemplo, se existe algum item ou Npc nesse ambiente, além da localização atual.
     */
    public String getDescricaoLonga() {
        String descricaoLonga = exibirAmbienteAtual() + "\n";
        if(existeItem()) {
            for(Item i : itens) {
                descricaoLonga += "Você vê um(a) "+ i.getNome() + "! \n" +  i.getDescricao() + "\n";
            }
        }
        if(!existeItem()) {
            descricaoLonga +="Não há itens aqui.\n";
        }
        if(existeNpc()) {
            descricaoLonga += "Você se depara com um(a) " + npc.getNome() + ", e ele diz: \n";

            if (npc.getItemRecebido()) {
                descricaoLonga += npc.getDialogoPosItem();
            }
            else {
                descricaoLonga += npc.getDialogo();
            }
        }
        if(existeInimigo()){
            descricaoLonga += "Você está frente a frente com " + inimigo.getDescricao();
        }
        return descricaoLonga;
    }
}
