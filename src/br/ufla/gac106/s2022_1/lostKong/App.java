package br.ufla.gac106.s2022_1.lostKong;

import br.ufla.gac106.s2022_1.baseJogo.InterfaceUsuario;
import br.ufla.gac106.s2022_1.baseJogo.Tela;
import br.ufla.gac106.s2022_1.baseJogo.Terminal;

public class App {
    public static void main(String[] args) throws Exception {
        InterfaceUsuario terminal = new Tela("Lost Kong");
        Jogo jogo = new Jogo(terminal);		
		jogo.jogar();
    }
}
