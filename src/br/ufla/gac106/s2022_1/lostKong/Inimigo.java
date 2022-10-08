package br.ufla.gac106.s2022_1.lostKong;

public class Inimigo extends Personagem {
    private String descricao;
    private boolean assustado; //Reação do inimigo quando atacado pelo jogador
    private int dano;

    public Inimigo(String nome, String descricao,int dano, int hp, Item itemCarregado) {
        super(nome,hp);
        this.descricao = descricao;
        this.dano = dano;
        this.assustado = false;
        adicionarItem(itemCarregado);
    }

    @Override
    public void setAssustado() {
        this.assustado = true;
    }

    public boolean getAssustado() {
        return this.assustado;
    }

    public int getDano() {
        return this.dano;
    }

    public String getDescricao() {
        return this.descricao;
    }
    
    @Override
    public void atacar(Personagem p){
        p.reduzirHp(dano);
    }
}
