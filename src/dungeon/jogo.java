package dungeon;

import java.util.Scanner;

public class jogo {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Ola! Bem-vindo(a) ao Labirinto de Dungeon Crawler!");
            System.out.print("\nDigite seu nome de usuario: ");
            String usuario = scanner.nextLine().trim();
            if (usuario.isEmpty()) {
                usuario = "Jogador";
            }

            System.out.println("\nOla, " + usuario + "!");
            executarMenuPrincipal(scanner, usuario);
        }
    }

    private static void executarMenuPrincipal(Scanner scanner, String usuario) {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n///// MENU PRINCIPAL /////");
            System.out.println("1 - Novo jogo");
            System.out.println("2 - Tutorial");
            System.out.println("3 - Sair");

            int opcao = lerInteiro(scanner, "Escolha uma opcao: ");
            switch (opcao) {
                case 1:
                    iniciarNovoJogo(scanner);
                    break;
                case 2:
                    exibirTutorial();
                    break;
                case 3:
                    System.out.println("Ate a proxima, " + usuario + "!");
                    continuar = false;
                    break;
                default:
                    System.out.println("Opcao invalida. Escolha 1, 2 ou 3.");
                    break;
            }
        }
    }

    private static void iniciarNovoJogo(Scanner scanner) {
        System.out.println("\n///// MENU DE DIFICULDADE /////");
        System.out.println("1 - Facil");
        System.out.println("2 - Medio");
        System.out.println("3 - Dificil");
        System.out.println("4 - Voltar");

        int opcao = lerInteiro(scanner, "Escolha a dificuldade: ");
        Dificuldade dificuldade;
        switch (opcao) {
            case 1:
                dificuldade = Dificuldade.FACIL;
                break;
            case 2:
                dificuldade = Dificuldade.MEDIO;
                break;
            case 3:
                dificuldade = Dificuldade.DIFICIL;
                break;
            case 4:
                System.out.println("Voce saiu do menu de dificuldades.");
                return;
            default:
                System.out.println("Opcao invalida. Escolha de 1 a 4.");
                return;
        }

        System.out.println("\nDificuldade escolhida: " + nomeDaDificuldade(dificuldade));
        executarPartida(scanner, dificuldade);
    }

    private static void executarPartida(Scanner scanner, Dificuldade dificuldade) {
        Labirinto labirinto = new Labirinto(dificuldade.getLargura(), dificuldade.getAltura());
        labirinto.gerarLabirinto();
        labirinto.removerParedesExtras(dificuldade.getChanceRemoverParede());

        Jogador jogador = new Jogador();
        boolean partidaAtiva = true;
        System.out.println("\n--- PARTIDA INICIADA ---");
        System.out.println("Use W, A, S ou D para mover, P para pausar e Q para sair.");

        while (partidaAtiva) {
            System.out.println();
            labirinto.exibirLabirinto(jogador.getPosicaoAtual());
            System.out.println("Posicao: (" + jogador.getPosicaoAtual().getLinha()
                    + ", " + jogador.getPosicaoAtual().getColuna() + ")");

            String comando = lerTexto(scanner, "Comando: ");
            if (comando.isEmpty()) {
                continue;
            }

            char tecla = Character.toUpperCase(comando.charAt(0));
            switch (tecla) {
                case 'P':
                    exibirPausa(scanner);
                    break;
                case 'Q':
                    partidaAtiva = false;
                    System.out.println("Partida encerrada. Voltando ao menu principal.");
                    break;
                case 'W':
                case 'A':
                case 'S':
                case 'D':
                    if (!jogador.tentarMover(tecla, labirinto)) {
                        System.out.println("Movimento bloqueado (parede ou limite do labirinto).");
                    } else if (labirinto.ehSaida(jogador.getPosicaoAtual())) {
                        labirinto.exibirLabirinto(jogador.getPosicaoAtual());
                        System.out.println("\nVoce encontrou a saida! Parabens!");
                        partidaAtiva = false;
                    }
                    break;
                default:
                    System.out.println("Comando invalido. Use W, A, S, D, P ou Q.");
                    break;
            }
        }
    }

    private static void exibirPausa(Scanner scanner) {
        System.out.println("\n///// JOGO PAUSADO /////");
        System.out.println("1 - Continuar");
        System.out.println("2 - Tutorial");
        System.out.println("3 - Sair da partida");

        int opcao = lerInteiro(scanner, "Escolha uma opcao: ");
        switch (opcao) {
            case 1:
                break;
            case 2:
                exibirTutorial();
                break;
            case 3:
                System.out.println("Para sair da partida, use Q no proximo comando.");
                break;
            default:
                System.out.println("Opcao invalida.");
                break;
        }
    }

    private static void exibirTutorial() {
        System.out.println("\n///// TUTORIAL /////");
        System.out.println("O labirinto mostra o inicio em verde e a saida em vermelho.");
        System.out.println("O jogador e representado por @@.");
        System.out.println("Use W, A, S e D para mover.");
        System.out.println("Use P para pausar e Q para sair da partida.");
    }

    private static int lerInteiro(Scanner scanner, String mensagem) {
        while (true) {
            String entrada = lerTexto(scanner, mensagem);
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Digite um numero valido.");
            }
        }
    }

    private static String lerTexto(Scanner scanner, String mensagem) {
        System.out.print(mensagem);
        if (!scanner.hasNextLine()) {
            return "3";
        }
        return scanner.nextLine().trim();
    }

    private static String nomeDaDificuldade(Dificuldade dificuldade) {
        switch (dificuldade) {
            case FACIL:
                return "FACIL";
            case MEDIO:
                return "MEDIO";
            case DIFICIL:
                return "DIFICIL";
            default:
                throw new IllegalArgumentException("Dificuldade desconhecida: " + dificuldade);
        }
    }
}
