package dungeon;

import java.util.*;

// Classe para gerar e exibir um labirinto
public class labirinto {
    private int largura; //escolhi usar private pois se for public, qualquer um poderia alterar a largura e altura do labirinto, o que poderia quebrar a lógica do programa
    private int altura; //mesma logica
    private int[][] mapa; //mesma logica
    private Random random = new Random(); //mesma logica

    // 0 = parede, 1 = caminho, 2 = saída, 3 = início
    private static final String RESET = "\u001B[0m"; //como apenas exibirLabirinto precisa dessas cores, não faz sentido deixar publicas, pois ninguem mais vai usar
    private static final String VERMELHO = "\u001B[41m"; //mesma logica
    private static final String VERDE = "\u001B[42m"; //mesma logica

    // Construtor que inicializa o labirinto com a largura e altura especificadas
    public labirinto(int largura, int altura) {
        this.largura = largura; //Copia o valor recebido no parâmetro para o atributo da classe
        this.altura = altura; //mesma logica
        this.mapa = new int[altura * 2 + 1][largura * 2 + 1]; //Cria a matriz que representa o labirinto, com altura * 2 + 1 linhas e largura * 2 + 1 colunas.
    }                                                         // Todas as posições começam com 0, que significa parede eu escolhi o * 2 + 1 pois o desenho precisa de espaço tanto para as células quanto para as paredes entre elas.

    // Método público para gerar o labirinto usando DFS (Depth-First Search)
    public void gerarLabirinto() {
        boolean[][] visitado = new boolean[altura][largura]; //cria uma matriz de booleanos para marcar quais células já foram visitadas durante a geração do labirinto todos false e a medida na qual o dfs vai percorrendo, vai marcando true nas células visitadas
        dfs(0, 0, visitado); //inicia a DFS a partir da célula (0, 0)

        mapa[1][1] = 3; // Define a célula inicial do labirinto como 3 (início)

        int[] celulaMaisDistante = encontrarCelulaMaisDistante(0, 0); // Encontra a célula mais distante da origem (0, 0) usando BFS (Breadth-First Search)
        int saidaLinha = celulaMaisDistante[0] * 2 + 1; // Converte as coordenadas da célula mais distante para as coordenadas do mapa
        int saidaColuna = celulaMaisDistante[1] * 2 + 1; //mesma logica
        mapa[saidaLinha][saidaColuna] = 2; //marca a célula mais distante como 2 (saída) garante que a saída estará sempre no ponto mais distante do início, aumentando a dificuldade do labirinto.
    }

    // Método público que quem for fazer a dificuldade pode chamar,
    // passando a probabilidade de criar atalhos (0.0 = nenhum atalho, 1.0 = muitos)
    public void removerParedesExtras(double chanceRemoverParede) { 
        if (chanceRemoverParede <= 0) return; // Se a chance de remover paredes for 0 ou negativa, não faz nada

        for (int i = 1; i < mapa.length - 1; i++) { // Percorre todas as células do labirinto, exceto as bordas pois se as bordas forem removidas, o labirinto não terá mais limites e o jogador poderá sair do mapa.
            for (int j = 1; j < mapa[0].length - 1; j++) { // mesma logica
                boolean eParede = (i % 2 == 0) ^ (j % 2 == 0);// descobre se a célula atual é uma parede (0) e se está em uma posição de parede (linhas ou colunas pares) o ^ é o operador XOR, que retorna true se apenas um dos operandos for true, ou seja, se a célula está em uma linha ou coluna par, mas não em ambas.
                if (mapa[i][j] == 0 && eParede) { // Se a célula atual é uma parede (0) e está em uma posição de parede, decide aleatoriamente se remove a parede com base na chance fornecida
                    if (random.nextDouble() < chanceRemoverParede) {  // Gera um número aleatório entre 0.0 e 1.0 e compara com a chance de remover parede se for menor que a chance, remove a parede, transformando-a em caminho (1)
                        mapa[i][j] = 1; // Remove a parede, transformando-a em caminho (1)
                    }
                }
            }
        }
    }
    // Método privado que implementa a DFS para gerar o labirinto
    private void dfs(int linha, int coluna, boolean[][] visitado) { // recebe uma cordenada de célula (linha, coluna) e a matriz de visitados
        visitado[linha][coluna] = true; // Marca a célula atual como visitada para fazer com que o algoritmo não volte para ela e crie loops no labirinto
        mapa[linha * 2 + 1][coluna * 2 + 1] = 1; // Marca a célula atual como caminho (1) no mapa, convertendo as coordenadas da célula para as coordenadas do mapa. O * 2 + 1 é usado para garantir que a célula esteja em uma posição de caminho (linhas e colunas ímpares).

        int[][] direcoes = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // as direções de movimento possiveis
        List<int[]> direcoesEmbaralhadas = new ArrayList<>(Arrays.asList(direcoes)); // converte o array de direções em uma lista para poder embaralhar
        Collections.shuffle(direcoesEmbaralhadas, random); // embaralha a lista de direções para garantir que o labirinto seja gerado de forma aleatória

        for (int[] dir : direcoesEmbaralhadas) { // Para cada direção embaralhada, calcula a nova posição da célula adjacente
            int novaLinha = linha + dir[0]; // Calcula a nova linha da célula adjacente
            int novaColuna = coluna + dir[1]; // mesma logica

            if (novaLinha >= 0 && novaLinha < altura &&
                novaColuna >= 0 && novaColuna < largura &&
                !visitado[novaLinha][novaColuna]) { // são tres condições para que a DFS continue: a nova célula deve estar dentro dos limites do labirinto, e não deve ter sido visitada ainda.

                int paredeLinha = linha * 2 + 1 + dir[0]; // Calcula a posição da parede entre a célula atual e a célula adjacente, convertendo as coordenadas da célula para as coordenadas do mapa. O * 2 + 1 é usado para garantir que a parede esteja em uma posição de parede (linhas e colunas pares).
                int paredeColuna = coluna * 2 + 1 + dir[1]; // mesma logica
                mapa[paredeLinha][paredeColuna] = 1; // Remove a parede entre as células, transformando-a em caminho (1)

                dfs(novaLinha, novaColuna, visitado); // Chama recursivamente a DFS para a célula adjacente, continuando a geração do labirinto
            }
        }
    }
    // Método privado que encontra a célula mais distante da origem usando BFS (Breadth-First Search)
    private int[] encontrarCelulaMaisDistante(int origemLinha, int origemColuna) { // recebe a cordenada da célula de origem (linha, coluna) e devolve um array
        boolean[][] visitado = new boolean[altura][largura]; // nova matriz de visitados para a BFS tendo a mesma logica da DFS, mas agora para a BFS
        Queue<int[]> fila = new LinkedList<>(); // cria uma fila BFS, essa fila e a que diferencia a BFS da DFS, pois a DFS usa pilha e a BFS usa fila
        fila.add(new int[]{origemLinha, origemColuna});
        visitado[origemLinha][origemColuna] = true; // marca a célula de origem como visitada para que a BFS não volte para ela e crie loops no labirinto

        int[] ultimaCelula = {origemLinha, origemColuna}; // inicializa a última célula visitada como a célula de origem, essa variável vai ser atualizada a cada iteração da BFS, e no final vai conter a célula mais distante da origem
        int[][] direcoes = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // as direções de movimento possiveis, mesma logica da DFS

        while (!fila.isEmpty()) { // enquanto a fila não estiver vazia, continua a BFS
            int[] atual = fila.poll(); // remove a célula atual da fila e a armazena na variável atual
            ultimaCelula = atual; // atualiza a última célula visitada como a célula atual

            for (int[] dir : direcoes) {
                int novaLinha = atual[0] + dir[0];
                int novaColuna = atual[1] + dir[1]; // para cada direção, calcula a nova posição da célula adjacente

                if (novaLinha >= 0 && novaLinha < altura &&
                    novaColuna >= 0 && novaColuna < largura &&
                    !visitado[novaLinha][novaColuna] &&
                    existeCaminho(atual[0], atual[1], novaLinha, novaColuna)) { // são quatro condições para que a BFS continue: a nova célula deve estar dentro dos limites do labirinto, não deve ter sido visitada ainda, e deve existir um caminho entre a célula atual e a célula adjacente (ou seja, não deve haver uma parede entre elas).

                    visitado[novaLinha][novaColuna] = true; 
                    fila.add(new int[]{novaLinha, novaColuna}); // marca o vizinho e o coloca no fim da fila, para ser explorado na próxima onda.
                }
            }
        }
        return ultimaCelula; // retorna a última célula visitada, que é a célula mais distante da origem
    }
    // Método privado que verifica se existe um caminho entre duas células adjacentes
    private boolean existeCaminho(int linha1, int coluna1, int linha2, int coluna2) {
        int paredeLinha = (linha1 * 2 + 1 + linha2 * 2 + 1) / 2;
        int paredeColuna = (coluna1 * 2 + 1 + coluna2 * 2 + 1) / 2;
        return mapa[paredeLinha][paredeColuna] != 0;
    }
    // Método público que exibe o labirinto no console com cores para saída e início
    public void exibirLabirinto() {
        for (int i = 0; i < mapa.length; i++) { // percorre todas as linhas do mapa
            for (int j = 0; j < mapa[0].length; j++) { // percorre todas as colunas do mapa
                if (mapa[i][j] == 2) {
                    System.out.print(VERMELHO + "  " + RESET); // se a célula atual é a saída (2), imprime um bloco vermelho
                } else if (mapa[i][j] == 3) {
                    System.out.print(VERDE + "  " + RESET); // se a célula atual é o início (3), imprime um bloco verde
                } else if (mapa[i][j] == 1) {
                    System.out.print("  "); // se a célula atual é um caminho (1), imprime um espaço em branco
                } else {
                    System.out.print("██"); // se a célula atual é uma parede (0), imprime um bloco preto
                }
            }
            System.out.println(); // após percorrer todas as colunas de uma linha, imprime uma nova linha para separar as linhas do labirinto
        }
    }
}