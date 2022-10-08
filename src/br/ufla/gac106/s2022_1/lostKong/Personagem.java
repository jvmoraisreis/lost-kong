package br.ufla.gac106.s2022_1.lostKong;
import java.util.ArrayList;

public abstract class Personagem {
    private String nome;
    private ArrayList<Item> mochila;
    private int capacidade; //Define quantos itens o jogador pode levar na mochila
    private int hp; //vida do personagem

    //Construtor da classe
    public Personagem(String nome, int hp) {
        this.nome = nome;
        this.capacidade = 5;
        mochila = new ArrayList<>();
        this.hp = hp;
    }

    public String getNome() {
        return this.nome;
    }

    public int getHP() {
        return this.hp;
    }
    /*
     * Adciona um item a mochila.
     */
    public boolean adicionarItem(Item item) {
        if(mochila.size() < capacidade) {
            mochila.add(item);
            return true;
        }
        return false;
    }

    /*
     * Recebe um nome e verifica se o item existe na mochila, caso exista, retorna o item  
     */
    private Item buscarItem(String nome) {
        for(Item i: mochila) {
            if(i.getNome().equals(nome)) {
                return i;
            }
        }
        return null;
    }

    /*
     * Recebe um nome, e verifica se existe um item com esse nome na mochila
     */
    public boolean possuiItem(String nome) {
        Item item = buscarItem(nome);
        if(item != null) {
            return true;
        }
        return false;
    }

    /*
     * Recebe o nome de um item, caso ele esteja na mochila o remove e o retorna
     */
    public Item removerItem(String nome) {
        Item item = buscarItem(nome);
        if(item != null) {
            Item aux = item;
            mochila.remove(item);
            return aux;
        }
        return null;
    }

    /*
     * Retorna uma String com a lista de itens que estão na mochila
     * Obs: Retorna um ArrayList ao invés de uma String para que possa ser útil nas duas subclasses.
     */
    public ArrayList<String> listarItens() {
        ArrayList<String> texto = new ArrayList<>();
        if(mochila.size() > 0) {
            for(Item i: mochila) {
                texto.add (i.getNome());
            }
            return texto;
        }
        return texto;
    }

    public boolean possuiItemDeAtaque() {
        for(Item i: mochila) {
            if(i.ehDeAtaque()) {
                return true;
            }
        }
        return false;
    }

    public void reduzirHp(int dano) {
        this.hp -= dano;
    } 

    public abstract void atacar(Personagem p); // Método utilizado pelas sub-classes Jogador e Inimigo
    public abstract void setAssustado(); //Método utilizado pela sub-classe Inimigo

}
