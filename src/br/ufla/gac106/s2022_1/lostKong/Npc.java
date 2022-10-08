package br.ufla.gac106.s2022_1.lostKong;

import java.util.ArrayList;

public class Npc extends Personagem {
    private String dialogo; //texto que o NPC fala normalmente.
    private String itemEsperado; //nome do item que o npc espera receber
    private String dialogoPosItem; //texto que o NPC fala quando já recebeu o item esperado.
    private boolean itemRecebido; //variável para controlar se o NPC ja recebeu o item esperado ou não.
    

    //Construtor
    public Npc(String dialogo,String dialogoPosItem, String itemEsperado,String nome, Item itemCarregado, int hp) {
        super(nome,hp);
        this.dialogo = dialogo;
        this.itemEsperado = itemEsperado;
        this.dialogoPosItem = dialogoPosItem;
        this.itemRecebido = false;
        adicionarItem(itemCarregado);
    }

    public boolean getItemRecebido() {
        return this.itemRecebido;
    }

    /*
     * retorna a fala que o Npc terá
     */
    public String getDialogo() {
        return dialogo;
    }

    public String getDialogoPosItem () {
        return dialogoPosItem;
    }

    /*
     * Caso receba o item esperado, retorna o item que está carregando removendo o mesmo da mochila.
     */
    public Item entregarItem(Item item) {
        if (itemRecebido == false) {
            ArrayList<String> itens = super.listarItens(); // Recebe um ArrayList com os nomes dos itens que o npc está carregando.
            String itemCarregado = itens.get(0); 

            if(item.getNome().equals(itemEsperado)) {
                super.adicionarItem(item);
                itemRecebido = true;
                return removerItem(itemCarregado);
            }
        }
        
        return null;
    }

    @Override
    public void atacar(Personagem p){}
    @Override
    public void setAssustado(){}
}
