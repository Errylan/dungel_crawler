package dungeon;

import java.util.Scanner;

public class jogo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Olá! Bem-Vindo(a) ao Labirinto de Dungeon Crawler!");
        System.out.print("\nDigite seu nome de usuário: ");
        String usuario = sc.nextLine();
        System.out.println("\nOlá, " + usuario + "!");

        int opcaoMenu = 0 ;
        do {
            System.out.println("\n///// MENU PRINCIPAL ///");
            System.out.println("\n Escolha uma opção:");
            System.out.println("1 - Novo Jogo");
            System.out.println("2 - Tutorial");
            System.out.println("3 - Sair");

            switch (opcaoMenu) {
            case 1:
                System.out.println("\n///// MENU DE DIFICULDADE ///");
            System.out.println("\nEscolha a dificuldade:");
            System.out.println("1 - Fácil");
            System.out.println("2 - Médio");
            System.out.println("3 - Difícil");
            System.out.println("4 - Sair");
                break;
            case 2:
                break;
            default:
                return;
        }

            

            opcaoMenu = sc.nextInt();

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
        } while (opcaoMenu < 1 || opcaoMenu > 3);

       
    }
}

