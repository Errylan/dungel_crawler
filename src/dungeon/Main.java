package dungeon;

public class Main {
    public static void main(String[] args) {
        int largura = 5;
        int altura = 5;
        double chanceRemoverParede = 0.1;

        labirinto lab = new labirinto(largura, altura);
        lab.gerarLabirinto();
        lab.removerParedesExtras(chanceRemoverParede);
        lab.exibirLabirinto();
    }
}