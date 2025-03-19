/**
 * A classe Main é o ponto de entrada do programa, onde o utilizador escolhe entre dois modos de jogo:
 * rasterização ou gráfico. Dependendo da escolha, o método correspondente da classe Jogo é chamado
 * ou a interface gráfica do usuário é inicializada.
 *
 * @author Jorge Silva, Paulo Martins, Vasile Karpa
 * @version 1.1 - 22/05/2024
 */

import javax.swing.*;
import java.util.Scanner;

public class Main {
    /**
     * O método main é o ponto de entrada para o programa. Ele pergunta ao usuário qual modo ele deseja escolher
     * e chama o método jogo da classe Jogo ou inicializa a GUI do jogo com base na escolha.
     *
     * @param args Argumentos da linha de comando que podem ser passados ao iniciar o programa.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Escolha o modo que quer, rasterização(0) ou gráfico(1): ");
        int escolha = scanner.nextInt();

        if (escolha == 0)
            Jogo.jogo(args);else if (escolha == 1)
            SwingUtilities.invokeLater(JogoGUI::new);
        else
            System.out.println("Escolha inválida. Por favor, execute o programa novamente e escolha 0 ou 1.");

        scanner.close();
    }
}
