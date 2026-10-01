package dungeon;

import java.util.Scanner;

public class jogo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Olá! Bem-Vindo(a) ao Labirinto de Dungeon Crawler!");
        System.out.print("\nDigite seu nome de usuário: ");
        String usuario = sc.nextLine();
        System.out.println("\nOlá, " + usuario + "!");

        int opcao;
        do {
            System.out.println("\n///// MENU DE DIFICULDADE ///");
            System.out.println("\nEscolha a dificuldade:");
            System.out.println("1 - Fácil");
            System.out.println("2 - Médio");
            System.out.println("3 - Difícil");
            System.out.println("4 - Sair");

            while (!sc.hasNextInt()) {
                System.out.println("Digite o número de uma opção.");
                sc.nextLine();
            }

            opcao = sc.nextInt();

            if (opcao == 4) {
                System.out.print("Tem certeza que deseja sair? (S/N): ");
                String confirmacao = sc.next();
                if (confirmacao.equalsIgnoreCase("s")) {
                    System.out.println("Até a próxima, " + usuario + "!");
                    return;
                }
                System.out.println("Voltando ao menu.");
                opcao = 0;
            } else if (opcao < 1 || opcao > 4) {
                System.out.println("Opção inválida. Escolha de 1 a 4.");
            }
        } while (opcao < 1 || opcao > 3);

        Dificuldade dificuldade;
        switch (opcao) {
            case 1:
                System.out.println("Você escolheu: 1- Fácil");
                dificuldade = Dificuldade.FACIL;
                break;
            case 2:
                System.out.println("Você escolheu: 2 -Médio");
                dificuldade = Dificuldade.MEDIO;
                break;
            case 3:
                System.out.println("Você escolheu: 3- Difícil");
                dificuldade = Dificuldade.DIFICIL;
                break;
            default:
                return;
        }

        labirinto lab = new labirinto(dificuldade.getLargura(), dificuldade.getAltura());
        lab.gerarLabirinto();
        lab.removerParedesExtras(dificuldade.getChanceRemoverParede());
        lab.exibirLabirinto();
    }
}

