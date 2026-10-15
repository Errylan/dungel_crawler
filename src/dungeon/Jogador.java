package dungeon;

public class Jogador {
    private Posicao posicaoAtual;

    public Jogador(){
        // Inicia jogador na posição inicial 0,0
        this.posicaoAtual = new Posicao(0,0);
    }

    public Posicao getPosicaoAtual() {
        return posicaoAtual;
    }

    /**
     * Tenta mover o jogador para uma nova posição. Vai retornar TRUE se ele conseguir se mover e FALSE se for bloqueado
     */

    public boolean tentarMover(char direcao, Labirinto labirinto){
        int novaLinha = posicaoAtual.getLinha();
        int novaColuna = posicaoAtual.getColuna();

        switch (Character.toUpperCase(direcao)) {
            case 'W': novaLinha--; break;
            case 'S': novaLinha++; break;
            case 'A': novaColuna--; break;
            case 'D': novaColuna++; break;
            default: return false;
        }

        Posicao novaPosicao = new Posicao(novaLinha, novaColuna);

        if (!labirinto.ehCaminho(novaPosicao)){
            posicaoAtual.setLinha(novaLinha);
            posicaoAtual.setColuna(novaColuna);
            return true; //movimento bem-sucedido
        }

        return false; //bateu na parede, movimento inválido!!
    }
}