/**
 * A classe Comida gerencia a geração de comida em um jogo de cobra. Ela cria comida aleatoriamente
 * no espaço de jogo sem interferir com obstáculos ou a cobra. A comida pode ser de dois tipos,
 * quadrado ou circunferência, dependendo de condições aleatórias.
 *
 * @author Jorge Silva, Paulo Martins, Vasile Karpa
 * @version 1.1 - 12/05/2024
 * @inv A comida deve ser gerada dentro da arena;
 * @inv A comida deve ser gerada no espaços onde é garantida a passagem da cobra.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Comida {
    private Object food;
    private int foodSize;
    private boolean wasGenerated = true;

    /**
     * Construtor da classe Comida. Gera comida na posição válida mais próxima usando as dimensões fornecidas,
     * considerando a presença de obstáculos e a posição atual da cobra.
     *
     * @param height     A altura do espaço do jogo (tipo int).
     * @param width      A largura do espaço do jogo (tipo int).
     * @param obstaculos Lista de obstáculos presentes no espaço do jogo (tipo List<Obstaculo>).
     * @param snake      A cobra cuja posição deve ser evitada ao colocar a comida (tipo Cobra).
     */
    public Comida(int height, int width, List<Obstaculo> obstaculos, Cobra snake) {
        generateFood(height, width, snake, obstaculos);
    }

    /**
     * Gera comida em uma posição válida dentro do espaço do jogo. A comida é posicionada de forma a não
     * colidir com a cobra ou obstáculos e pode ser um quadrado ou uma circunferência, dependendo do resultado
     * de um sorteio.
     *
     * @param height     A altura do espaço do jogo (tipo int).
     * @param width      A largura do espaço do jogo (tipo int).
     * @param snake      A cobra no jogo (tipo Cobra).
     * @param obstaculos Os obstáculos presentes no jogo (tipo List<Obstaculo>).
     */
    private void generateFood(int height, int width, Cobra snake, List<Obstaculo> obstaculos) {
        int movementUnit = snake.getEdge() + 1; // O movimento da cobra é edge + 1
        final Random random = new Random();
        List<Ponto> potentialPositions = new ArrayList<>();

        for (int x = 0; x < width - width % (snake.getEdge() + 1); x += movementUnit)
            for (int y = 0; y < height - height % (snake.getEdge() + 1); y += movementUnit)
                potentialPositions.add(new Ponto(x, y));

        if (potentialPositions.isEmpty()) {
            this.wasGenerated = false;
            this.food = null;
        } else {
            Collections.shuffle(potentialPositions, random);

            for (Ponto pos : potentialPositions) {
                this.foodSize = random.nextInt(snake.getEdge() / 4, snake.getEdge() + 1);
                if (isPositionFree((int) pos.getX(), (int) pos.getY(), snake, obstaculos)) {
                    int x = (int) pos.getX();
                    int y = (int) pos.getY();
                    if (random.nextInt(2) == 0 || snake.getEdge() == 1) { // Quadrado
                        this.food = new Quadrado(new Ponto[]{
                                new Ponto(x, y),
                                new Ponto(x + this.foodSize, y),
                                new Ponto(x + this.foodSize, y + this.foodSize),
                                new Ponto(x, y + this.foodSize)
                        });
                    } else { // Circunferencia
                        double radius;
                        if (this.foodSize == snake.getEdge() && this.foodSize % 2 != 0)
                            this.foodSize--;
                        if (this.foodSize == 1)
                            radius = 1;
                        else
                            radius = this.foodSize / 2.0;
                        this.food = new Circunferencia(new Ponto(x + radius, y + radius), radius);
                    }
                    return; // Comida foi colocada com sucesso
                }
            }
        }
    }

    /**
     * Retorna todos os pontos inteiros que compõem a comida, seja ela um quadrado ou uma circunferência.
     *
     * @return Um array de pontos (Ponto[]) representando todos os pontos inteiros que compõem a comida.
     */
    public Ponto[] getAllIntPoints() {
        if (this.food instanceof Quadrado quadrado)
            return quadrado.getAllIntVertices();
        else if (this.food instanceof Circunferencia circunferencia)
            return circunferencia.getAllIntVertices();
        return new Ponto[0]; // Retorna um array vazio se não houver food definida
    }

    /**
     * Retorna todos os pontos inteiros que compõem as arestas da comida, seja ela um quadrado ou uma circunferência.
     *
     * @return Um array de pontos (Ponto[]) representando todos os pontos inteiros que compõem as arestas da comida.
     */
    public Ponto[] getBoundaryIntPoints() {
        if (this.food instanceof Quadrado quadrado)
            return quadrado.getBoundaryIntVertices();
        else if (this.food instanceof Circunferencia circunferencia)
            return circunferencia.getBoundaryIntVertices();
        return new Ponto[0]; // Retorna um array vazio se não houver food definida
    }

    /**
     * Verifica se a posição especificada está livre de interseções com a cobra ou obstáculos no espaço do jogo.
     *
     * @param x          A coordenada x do ponto inicial da comida (tipo int).
     * @param y          A coordenada y do ponto inicial da comida (tipo int).
     * @param snake      A cobra no jogo (tipo Cobra).
     * @param obstaculos Os obstáculos no jogo (tipo List<Obstaculo>).
     * @return Verdadeiro se a posição está livre; falso caso contrário (tipo booleano).
     */
    private boolean isPositionFree(int x, int y, Cobra snake, List<Obstaculo> obstaculos) {
        for (Ponto p : snake.getAllIntPoints())
            if (p.getX() >= x && p.getX() < x + this.foodSize && p.getY() >= y && p.getY() < y + this.foodSize)
                return false; // Food overlaps with the snake

        if (obstaculos != null)
            for (Obstaculo obs : obstaculos) {
                Ponto[] pontos = obs.getAllIntPoints();
                for (Ponto p : pontos)
                    if (p.getX() >= x && p.getX() < x + this.foodSize && p.getY() >= y && p.getY() < y + this.foodSize)
                        return false; // Food overlaps with an obstacle
            }

        if (snake.getEdge() == this.foodSize)
            return x % snake.getEdge() == 0 && y % snake.getEdge() == 0; // Food must spawn at a multiple of the snake head size

        return true;
    }

    /**
     * Retorna a comida gerada. A comida pode ser um objeto Quadrado ou Circunferencia.
     *
     * @return A comida como um objeto (tipo Object).
     */
    public Object getFood() {
        return this.food;
    }

    /**
     * Retorna o tamanho da aresta da comida gerada.
     *
     * @return Tamanho da aresta da comida (tipo int).
     */
    public int getFoodSize() {
        return this.foodSize;
    }

    /**
     * Indica se a comida foi gerada com sucesso.
     *
     * @return Verdadeiro se a comida foi gerada; falso caso contrário (tipo booleano).
     */
    public boolean wasGenerated() {
        return this.wasGenerated;
    }
}
