package br.ufla.gac106.s2022_1.lostKong;

import java.util.Stack;


public class Jogador extends Personagem {
    private static Jogador jogadorUnico;
    private int movimentosDisponiveis; //numero de cenários que  o jogador pode visitar 
    private Ambiente ambienteAtual;

    private Stack<Ambiente> ambientesVisitados;

    private Jogador(String nome, int hp) {
        super(nome,hp);
        this.movimentosDisponiveis = 40;
        this.ambienteAtual = null;
        ambientesVisitados = new Stack<>();
    }

    public static Jogador getJogador(String nome, int hp) {
        if(jogadorUnico == null) {
            jogadorUnico = new Jogador(nome, hp);
        }
        return jogadorUnico;
    }

    public void addAmbiente(Ambiente ambiente) {
        ambientesVisitados.push(ambiente);
    }

    public Ambiente getAmbienteAtual() {
        return this.ambienteAtual;
    }

    public void setAmbienteAtual(Ambiente ambiente) {
        this.ambienteAtual = ambiente;
    }

    public Ambiente pegarUltimoAmbiente() {
        return ambientesVisitados.pop();
    }

    public boolean visitouAlgumAmbiente() {
        if(ambientesVisitados.empty()) {
            return false;
        }
        return true;
    }

    public void decrementarMovimentosDisponiveis() {
        this.movimentosDisponiveis--;
    }

    public int getMovimentosDisponiveis() {
        return this.movimentosDisponiveis;
    }

    public boolean estaVivo() {
        if(getHP() <= 0) {
            return false;
        }
        return true;
    }

    @Override
    public void atacar(Personagem p){ //Obs: O parâmetro recebido por essa função será um inimigo 
        p.setAssustado();
    }

    @Override
    public void setAssustado(){}

}

