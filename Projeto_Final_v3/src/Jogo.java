/**
 * A classe Jogo gere as operações principais do jogo de cobra, incluindo a configuração inicial e o loop de jogo.
 * Esta trata de ler as entradas do utilizador, desenhar elementos na arena,
 * e manter o controle do estado do jogo até o seu término.
 *
 * @author Jorge Silva, Paulo Martins, Vasile Karpa
 * @version 1.1 - 12/05/2024
 */

import java.util.*;

public class Jogo {
    /**
     * Calcula o tamanho ótimo da arena baseado no tamanho da cabeça da cobra e no tamanho mínimo desejado para a arena.
     * Garante que a arena seja dimensionada corretamente para permitir um movimento uniforme da cobra.
     *
     * @param n Tamanho da cabeça da cobra.
     * @param m Tamanho mínimo desejado para a dimensão da arena.
     * @return Tamanho ótimo calculado.
     */
    public static int OptimalSizeArena(int n, int m) {
        int headpoints = n + 1;
        return (m % headpoints == 0) ? m : (m / headpoints + 1) * headpoints;
    }

    /**
     * Inicia o jogo de cobra, configurando o ambiente de jogo, criando e manipulando elementos como a cobra, comida,
     * e obstáculos. Gerencia o fluxo do jogo e as interações do usuário até o término do jogo, incluindo colisões, pontuação,
     * e a exibição dos resultados finais.
     *
     * @param args Array de strings dos argumentos da linha de comando (não utilizado).
     */
    public static void jogo(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Selecione o modo de rasterização contorno (0) ou complete (1): ");
        int scanIntTemp = scanner.nextInt();
        boolean complete;
        if (scanIntTemp == 0)
            complete = false;
        else if (scanIntTemp == 1)
            complete = true;
        else
            throw new IllegalArgumentException("Modo inválido");

        System.out.print("Quer que sejam gerados obstáculos aleatórios? Não (0) - Sim (1): ");
        scanIntTemp = scanner.nextInt();
        boolean wantsObstacles;
        if (scanIntTemp == 0)
            wantsObstacles = false;
        else if (scanIntTemp == 1)
            wantsObstacles = true;
        else
            throw new IllegalArgumentException("Opção inválida");

        System.out.print("Insira o comprimento e a altura da arena (mínimo 10x10).\nComprimento:");
        int width = scanner.nextInt();
        System.out.print("Altura:");
        int height = scanner.nextInt();

        System.out.print("Insira o comprimento da aresta da cabeça da cobra: ");
        int head = scanner.nextInt();
        Cobra snake = new Cobra(height, width, head);

        System.out.println("A otimizar espaço de arena para melhor jogabilidade...");
        Arena arena = new Arena(OptimalSizeArena(head, height), OptimalSizeArena(head, width));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Ocorreu um erro!");
            Thread.currentThread().interrupt();
        }

        System.out.println("Otimizado!");
        scanner.nextLine(); //impede que o sejam renderizadas 2 arenas de imediato

        int currentScore = 0;
        Pontuacao score = new Pontuacao();
        Controlos controls = new Controlos();

        List<Obstaculo> allObstacles = new ArrayList<>();
        if (wantsObstacles)
            for (int i = 0;
                 i < Math.min(width, height) / (Math.pow(10, String.valueOf(Math.min(width, height)).length() - 1));
                 i++)
                allObstacles.add(new Obstaculo(arena.getWidth(), arena.getHeight(), snake));

        Comida food = new Comida(arena.getHeight(), arena.getWidth(), allObstacles, snake);

        boolean gameover = false;
        while (!gameover) {
            arena.deleteEatenFood();
            if (!(arena.drawSnake(snake, complete)))
                gameover = true;
            else {
                if (wantsObstacles) {
                    for (Obstaculo o : allObstacles) {
                        if (o.isRotating()) {
                            arena.deleteObstacle(o, complete);
                            o.rotate();
                            arena.drawObstacle(o, complete);
                        } else
                            arena.drawObstacle(o, complete);
                    }

                    List<Ponto> Collision = snake.findIntersections(allObstacles);
                    if (!(Collision.isEmpty())) {
                        arena.drawCollisions(Collision);
                        gameover = true;
                    }
                }

                arena.drawFood(food, complete);
                if (snake.containsFood(food)) {
                    arena.drawEatenFood(food, complete);
                    snake.grow();
                    currentScore += food.getAllIntPoints().length;
                    food = new Comida(arena.getHeight(), arena.getWidth(), allObstacles, snake);
                    if (!food.wasGenerated()) {
                        gameover = true;
                        currentScore = Integer.MAX_VALUE;
                    }
                }

                System.out.println(arena.printBoard());
                System.out.println("Dir H: "+snake.getDir()+"\t Pontos: " + currentScore);
            }
            if (gameover) {
                System.out.println("GAME OVER!");
                System.out.print("Digite seu nome (sem espaços): ");
                String playerName = scanner.next();
                score.adicionarPontuacao(currentScore, playerName);
                System.out.println("Introduza quantos Recordes de Jogo quer ver: ");
                int Nresultados = scanner.nextInt();
                scanner.nextLine();
                score.imprimirTopNPontuacoes(Nresultados);
                System.exit(0);
            }
            System.out.println("'WASD' para mover arena cobra.\n'Q' para sair imediatamente.");
            String controlInput = scanner.nextLine().toUpperCase();
            controls.ProcessarControlos(snake, controlInput);
        }
    }
}