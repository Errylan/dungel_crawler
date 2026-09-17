package dungeon;

import java.util.*;

public class labirinto {
    private int largura;
    private int altura;
    private int[][] mapa;
    private Random random = new Random();

    // 0 = parede, 1 = caminho, 2 = saída, 3 = início
    private static final String RESET = "\u001B[0m";
    private static final String VERMELHO = "\u001B[41m";
    private static final String VERDE = "\u001B[42m";

    public labirinto(int largura, int altura) {
        this.largura = largura;
        this.altura = altura;
        this.mapa = new int[altura * 2 + 1][largura * 2 + 1];
    }

    public void gerarLabirinto() {
        boolean[][] visitado = new boolean[altura][largura];
        dfs(0, 0, visitado);

        mapa[1][1] = 3;

        int[] celulaMaisDistante = encontrarCelulaMaisDistante(0, 0);
        int saidaLinha = celulaMaisDistante[0] * 2 + 1;
        int saidaColuna = celulaMaisDistante[1] * 2 + 1;
        mapa[saidaLinha][saidaColuna] = 2;
    }

    // Método público que quem for fazer a dificuldade pode chamar,
    // passando a probabilidade de criar atalhos (0.0 = nenhum atalho, 1.0 = muitos)
    public void removerParedesExtras(double chanceRemoverParede) {
        if (chanceRemoverParede <= 0) return;

        for (int i = 1; i < mapa.length - 1; i++) {
            for (int j = 1; j < mapa[0].length - 1; j++) {
                boolean éParede = (i % 2 == 0) ^ (j % 2 == 0);
                if (mapa[i][j] == 0 && éParede) {
                    if (random.nextDouble() < chanceRemoverParede) {
                        mapa[i][j] = 1;
                    }
                }
            }
        }
    }

    private void dfs(int linha, int coluna, boolean[][] visitado) {
        visitado[linha][coluna] = true;
        mapa[linha * 2 + 1][coluna * 2 + 1] = 1;

        int[][] direcoes = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        List<int[]> direcoesEmbaralhadas = new ArrayList<>(Arrays.asList(direcoes));
        Collections.shuffle(direcoesEmbaralhadas, random);

        for (int[] dir : direcoesEmbaralhadas) {
            int novaLinha = linha + dir[0];
            int novaColuna = coluna + dir[1];

            if (novaLinha >= 0 && novaLinha < altura &&
                novaColuna >= 0 && novaColuna < largura &&
                !visitado[novaLinha][novaColuna]) {

                int paredeLinha = linha * 2 + 1 + dir[0];
                int paredeColuna = coluna * 2 + 1 + dir[1];
                mapa[paredeLinha][paredeColuna] = 1;

                dfs(novaLinha, novaColuna, visitado);
            }
        }
    }

    private int[] encontrarCelulaMaisDistante(int origemLinha, int origemColuna) {
        boolean[][] visitado = new boolean[altura][largura];
        Queue<int[]> fila = new LinkedList<>();
        fila.add(new int[]{origemLinha, origemColuna});
        visitado[origemLinha][origemColuna] = true;

        int[] ultimaCelula = {origemLinha, origemColuna};
        int[][] direcoes = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        while (!fila.isEmpty()) {
            int[] atual = fila.poll();
            ultimaCelula = atual;

            for (int[] dir : direcoes) {
                int novaLinha = atual[0] + dir[0];
                int novaColuna = atual[1] + dir[1];

                if (novaLinha >= 0 && novaLinha < altura &&
                    novaColuna >= 0 && novaColuna < largura &&
                    !visitado[novaLinha][novaColuna] &&
                    existeCaminho(atual[0], atual[1], novaLinha, novaColuna)) {

                    visitado[novaLinha][novaColuna] = true;
                    fila.add(new int[]{novaLinha, novaColuna});
                }
            }
        }
        return ultimaCelula;
    }

    private boolean existeCaminho(int linha1, int coluna1, int linha2, int coluna2) {
        int paredeLinha = (linha1 * 2 + 1 + linha2 * 2 + 1) / 2;
        int paredeColuna = (coluna1 * 2 + 1 + coluna2 * 2 + 1) / 2;
        return mapa[paredeLinha][paredeColuna] != 0;
    }

    public void exibirLabirinto() {
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[0].length; j++) {
                if (mapa[i][j] == 2) {
                    System.out.print(VERMELHO + "  " + RESET);
                } else if (mapa[i][j] == 3) {
                    System.out.print(VERDE + "  " + RESET);
                } else if (mapa[i][j] == 1) {
                    System.out.print("  ");
                } else {
                    System.out.print("██");
                }
            }
            System.out.println();
        }
    }
}