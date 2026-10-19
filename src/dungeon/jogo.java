package dungeon;

import java.util.Scanner;

public class jogo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Olá! Bem-Vindo(a) ao Labirinto de Dungeon Crawler!");
        System.out.print("\nDigite seu nome de usuário: ");
        String usuario = sc.nextLine().trim();
        if (usuario.isEmpty()) {
            usuario = "Jogador";
        }
        System.out.println("\nOlá, " + usuario + "!");

        boolean continuar = true;
        while (continuar) {
            System.out.println("\n///// MENU PRINCIPAL ///");
            System.out.println("\n Escolha uma opção:");
            System.out.println("1 - Novo Jogo");
            System.out.println("2 - Tutorial");
            System.out.println("3 - Sair");

            int opcaoMenu = lerInteiro(sc);

            switch (opcaoMenu) {
                case 1:
                    iniciarNovoJogo(sc, usuario);
                    break;

                case 2:
                    exibirTutorial();
                    break;

                case 3:
                    System.out.print("Tem certeza que deseja sair? (S/N): ");
                    String confirmacao = sc.nextLine().trim();
                    if (confirmacao.equalsIgnoreCase("s")) {
                        System.out.println("Até a próxima, " + usuario + "!");
                        continuar = false;
                    } else {
                        System.out.println("Voltando ao menu.");
                    }
                    break;

                default:
                    System.out.println("Opção inválida. Escolha de 1 a 3.");
                    break;
            }
        }

        sc.close();
    }

    private static void iniciarNovoJogo(Scanner sc, String usuario) {
        System.out.println("\n///// MENU DE DIFICULDADE ///");
        System.out.println("\nEscolha a dificuldade:");
        System.out.println("1 - Fácil");
        System.out.println("2 - Médio");
        System.out.println("3 - Difícil");
        System.out.println("4 - Sair");

        int opcaoDificuldade = lerInteiro(sc);
        Dificuldade dificuldadeEscolhida;

        switch (opcaoDificuldade) {
            case 1:
                dificuldadeEscolhida = Dificuldade.FACIL;
                System.out.println("Você escolheu : FÁCIL");
                break;
            case 2:
                dificuldadeEscolhida = Dificuldade.MEDIO;
                System.out.println("Você escolheu : MÉDIO");
                break;
            case 3:
                dificuldadeEscolhida = Dificuldade.DIFICIL;
                System.out.println("Você escolheu : DIFÍCIL");
                break;
            case 4:
                System.out.println("Você saiu do menu de dificuldades");
                return;
            default:
                System.out.println("Opção inválida. Escolha de 1 a 4.");
                return;
        }

        Labirinto labirinto = new Labirinto(
                dificuldadeEscolhida.getLargura(),
                dificuldadeEscolhida.getAltura()
        );
        labirinto.gerarLabirinto();
        labirinto.removerParedesExtras(dificuldadeEscolhida.getChanceRemoverParede());

        Jogador jogador = new Jogador();
        int movimentos = 0;
        String aviso = "";
        long inicio = System.currentTimeMillis();
        long tempoPausado = 0;

        // Loop da partida
        boolean emPartida = true;
        while (emPartida) {
            System.out.println();
            labirinto.exibirLabirinto(jogador.getPosicaoAtual());
            System.out.println("Movimentos: " + movimentos);
            if (!aviso.isEmpty()) {
                System.out.println(aviso);
                aviso = "";
            }
            System.out.print("Mover (W/A/S/D) ou P para pausar: ");
            String entrada = sc.nextLine().trim();

            if (entrada.equalsIgnoreCase("p")) {
                long inicioPausa = System.currentTimeMillis();
                emPartida = exibirPausa(sc);
                tempoPausado += System.currentTimeMillis() - inicioPausa;
            } else if (entrada.length() == 1 && "wasd".indexOf(Character.toLowerCase(entrada.charAt(0))) >= 0) {
                if (jogador.tentarMover(entrada.charAt(0), labirinto)) {
                    movimentos++;
                    if (labirinto.ehSaida(jogador.getPosicaoAtual())) {
                        long tempoSegundos = (System.currentTimeMillis() - inicio - tempoPausado) / 1000;
                        long pontuacao = Math.max(0L, 1000L - movimentos * 10L - tempoSegundos);
                        System.out.println();
                        labirinto.exibirLabirinto(jogador.getPosicaoAtual());
                        System.out.println("\nParabéns, " + usuario + "! Você encontrou a saída em "
                                + movimentos + " movimentos e " + tempoSegundos + " segundos!");
                        System.out.println("Pontuação: " + pontuacao);
                        emPartida = false;
                    }
                } else {
                    aviso = "Caminho bloqueado! Tente outra direção.";
                }
            } else {
                aviso = "Comando inválido. Use W, A, S, D ou P.";
            }
        }
    }

    // Lê um número inteiro sem quebrar o programa se o usuário digitar letras.
    private static int lerInteiro(Scanner sc) {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1; // cai no "default" (opção inválida) do switch
        }
    }

    private static void exibirTutorial() {
        System.out.println("\n///// TUTORIAL /////");
        System.out.println("O labirinto mostra o inicio em verde e a saida em vermelho.");
        System.out.println("Você é representado por @@.");
        System.out.println("Use W, A, S e D para mover para cima, esquerda, baixo e direita.");
        System.out.println("Digite P durante a partida para pausar.");
    }

    // Retorna true para continuar a partida e false para sair dela.
    private static boolean exibirPausa(Scanner sc) {
        while (true) {
            System.out.println("\n///// MENU DE PAUSA /////");
            System.out.println("1 - Voltar à partida");
            System.out.println("2 - Tutorial");
            System.out.println("3 - Sair da partida");
            System.out.print("Escolha uma opção: ");

            int opcaoPausa = lerInteiro(sc);

            switch (opcaoPausa) {
                case 1:
                    return true;
                case 2:
                    exibirTutorial();
                    break;
                case 3:
                    System.out.print("Tem certeza que deseja sair da partida? (S/N): ");
                    String confirmacao = sc.nextLine().trim();
                    if (confirmacao.equalsIgnoreCase("s")) {
                        return false;
                    }
                    System.out.println("Voltando ao menu de pausa.");
                    break;
                default:
                    System.out.println("Opção inválida. Escolha de 1 a 3.");
            }
        }
    }
}