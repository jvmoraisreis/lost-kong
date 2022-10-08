package br.ufla.gac106.s2022_1.lostKong;

import br.ufla.gac106.s2022_1.baseJogo.EntidadeGrafica;

public class Item extends EntidadeGrafica{
    private String nome;
    private String descricao;
    private boolean carregavel;
    private boolean itemDeAtaque;

    //Construtor da classe
    public Item(String nome, String descricao, boolean carregavel, boolean itemDeAtaque) {
        super("imagens/" + nome + ".jpg");
        this.descricao = descricao;
        this.nome = nome;
        this.carregavel = carregavel;
        this.itemDeAtaque = itemDeAtaque;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean ehCarregavel() {
        return this.carregavel;
    }

    public boolean ehDeAtaque() {
        return this.itemDeAtaque;
    }   
}
