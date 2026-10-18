package dungeon;

import java.util.Scanner;

public class jogo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Olá! Bem-Vindo(a) ao Labirinto de Dungeon Crawler!");
        System.out.print("\nDigite seu nome de usuário: ");
        String usuario = sc.nextLine();
        System.out.println("\nOlá, " + usuario + "!");

        int opcaoMenu = 0;
        boolean continuar = true;
        do{
            System.out.println("\n///// MENU PRINCIPAL ///");
            System.out.println("\n Escolha uma opção:");
            System.out.println("1 - Novo Jogo");
            System.out.println("2 - Tutorial");
            System.out.println("3 - Sair");

        opcaoMenu = sc.nextInt();
        


            switch (opcaoMenu) {
            case 1:
                System.out.println("\n///// MENU DE DIFICULDADE ///");
            System.out.println("\nEscolha a dificuldade:");
            System.out.println("1 - Fácil");
            System.out.println("2 - Médio");
            System.out.println("3 - Difícil");
            System.out.println("4 - Sair");

            int opcaoDificuldade = sc.nextInt();
            Dificuldade dificuldadeEscolhida;
            boolean dificuldadeValida = false

            switch (opcaoDificuldade) {
                case 1:
                    dificuldadeEscolhida = true Dificuldade.FACIL;
                    System.out.println("Você escolheu : FÁCIL");
                    break;
                case 2:
                    dificuldadeEscolhida = true Dificuldade.MEDIO;
                    System.out.println("Você escolheu : MÉDIO");
                    break;
                case 3:
                    dificuldadeEscolhida = true Dificuldade.DIFICIL;
                    System.out.println("Você escolheu : DIFÍCIL");
                    break;
                case 4:
                    System.out.println("Você saiu do menu de dificuldades");
                    break;
                default:
                    System.out.println("Opção inválida. Escolha de 1 a 4.");
                    break;
            }


                break;
            case 2:
                exibirTutorial();

                break;

            case 3: 
                if (opcaoMenu == 3) {
                System.out.print("Tem certeza que deseja sair? (S/N): ");
                String confirmacao = sc.next();
                if (confirmacao.equalsIgnoreCase("s")) {
                    System.out.println("Até a próxima, " + usuario + "!");
                    return;
                }
                System.out.println("Voltando ao menu.");
                opcaoMenu = 0;
            } else if (opcaoMenu < 1 || opcaoMenu > 4) {
                System.out.println("Opção inválida. Escolha de 1 a 3.");
            }
            default:
                return ;
            }
        } while (continuar);
    }

    private static void exibirTutorial() {
       System.out.println("\n///// TUTORIAL /////");
        System.out.println("O labirinto mostra o inicio em verde e a saida em vermelho.");
        System.out.println("Use W, A, S e D para tentar mover para cima, esquerda, baixo e direita.");


    }

        private static boolean exibirPausa(Scanner sc) {
            while (true) {
                System.out.println("\n///// MENU DE PAUSA /////");
                System.out.println("1 - Voltar à partida");
                System.out.println("2 - Tutorial");
                System.out.println("3 - Sair da partida");
                System.out.print("Escolha uma opção: ");

                int opcaoPausa = sc.nextInt();

                switch (opcaoPausa) {
                    case 1:
                        return true;
                    case 2:
                        exibirTutorial();
                        break;
                    case 3:
                        System.out.print("Tem certeza que deseja sair da partida? (S/N): ");
                        String confirmacao = sc.next();
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
