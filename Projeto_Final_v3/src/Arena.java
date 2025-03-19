/**
 * A classe Arena representa o espaço de jogo onde os objetos como cobra, comida e obstáculos são desenhados.
 * Esta classe é responsável por gerenciar e atualizar a representação visual do jogo.
 *
 * @author Jorge Silva, Paulo Martins, Vasile Karpa
 * @version 1.1 - 12/05/2024
 * @inv A arena deve ter largura e altura superior a 10.
 */

import java.util.List;

import static java.lang.Math.pow;

public class Arena {
    private final char[][] arena;

    /**
     * Constrói uma arena de jogo baseada nas dimensões fornecidas.
     *
     * @param height A altura da arena (número de linhas).
     * @param width  A largura da arena (número de colunas).
     */
    public Arena(int height, int width) {
        // Arena altura*largura=linhas*colunas
        if (height < 10 || width < 10)
            throw new IllegalArgumentException("Tamanho de Arena inválido");
        this.arena = new char[height][width];
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                this.arena[i][j] = '.';
    }

    /**
     * Desenha a cobra na arena, atualizando a representação gráfica dos pontos onde a cobra se localiza.
     *
     * @param snake    A cobra a ser desenhada na arena (tipo Cobra).
     * @param complete Indica se a cobra deve ser desenhada completamente ou apenas os pontos de contorno (tipo booleano).
     * @return Verdadeiro se a cobra foi desenhada com sucesso; falso se algum ponto da cobra está fora dos limites (tipo booleano).
     */
    public boolean drawSnake(Cobra snake, boolean complete) {
        // Primeiro limpa os pontos antigos da cobra
        for (char[] row : this.arena)
            for (int j = 0; j < row.length; j++)
                if (row[j] == 'H' || row[j] == 'T')
                    row[j] = '.';

        List<Ponto> snakePoints = !complete ? snake.getBoundaryIntPoints() : snake.getAllIntPoints();
        int pontosH = !complete ? (snake.getEdge() + 1) * 4 : (int) pow(snake.getEdge() + 1, 2);
        for (int i = 0; i < snakePoints.size(); i++) {
            int x = (int) snakePoints.get(i).getX();
            int y = (int) snakePoints.get(i).getY();
            if (x >= 0 && x < this.arena[0].length && y >= 0 && y < this.arena.length)
                this.arena[y][x] = (i < pontosH) ? 'H' : 'T';
            else
                return false;
        }
        return true;
    }

    /**
     * Desenha a comida na arena. Marca os pontos onde a comida está localizada.
     *
     * @param comida   A comida a ser desenhada na arena (tipo Comida).
     * @param complete Indica se a comida deve ser desenhada completamente ou apenas os pontos de contorno (tipo booleano).
     */
    public void drawFood(Comida comida, boolean complete) {
        Ponto[] pontos = !complete ? comida.getBoundaryIntPoints() : comida.getAllIntPoints();
        for (Ponto ponto : pontos) {
            int x = (int) ponto.getX();
            int y = (int) ponto.getY();
            if (x >= 0 && x < this.arena[0].length && y >= 0 && y < this.arena.length)
                this.arena[y][x] = 'F';
        }
    }

    /**
     * Marca os pontos da comida que foi comida pela cobra na arena, alterando o caractere representativo para '#'.
     *
     * @param comida   A comida que foi comida pela cobra (tipo Comida).
     * @param complete Indica se a comida comida deve ser desenhada completamente ou apenas os pontos de contorno (tipo booleano).
     */
    public void drawEatenFood(Comida comida, boolean complete) {
        Ponto[] pontos = !complete ? comida.getBoundaryIntPoints() : comida.getAllIntPoints();
        for (Ponto ponto : pontos) {
            int x = (int) ponto.getX();
            int y = (int) ponto.getY();
            this.arena[y][x] = '#';
        }
    }

    /**
     * Remove a representação gráfica da comida que foi consumida.
     */
    public void deleteEatenFood() {
        for (char[] row : this.arena)
            for (int j = 0; j < row.length; j++)
                if (row[j] == '#')
                    row[j] = '.';
    }

    /**
     * Desenha um obstáculo na arena, marcando os pontos onde o obstáculo está localizado.
     *
     * @param obstaculo O obstáculo a ser desenhado na arena (tipo Obstaculo).
     * @param complete  Indica se o obstáculo deve ser desenhado completamente ou apenas os pontos de contorno (tipo booleano).
     */
    public void drawObstacle(Obstaculo obstaculo, boolean complete) {
        Ponto[] pontos = !complete ? obstaculo.getBoundaryIntPoints() : obstaculo.getAllIntPoints();
        for (Ponto ponto : pontos) {
            int x = (int) ponto.getX();
            int y = (int) ponto.getY();
            if (x >= 0 && x < this.arena[0].length && y >= 0 && y < this.arena.length)
                this.arena[y][x] = 'O';
        }
    }

    /**
     * Marca os pontos de colisão na arena, onde ocorreram interações indesejadas como colisões com obstáculos.
     *
     * @param collisionPoints Lista de pontos onde ocorreram colisões (tipo List<Ponto>).
     */
    public void drawCollisions(List<Ponto> collisionPoints) {
        for (Ponto collisionPoint : collisionPoints)
            if (collisionPoint.getX() >= 0 && collisionPoint.getX() < arena[0].length &&
                    collisionPoint.getY() >= 0 && collisionPoint.getY() < arena.length)
                arena[(int) collisionPoint.getY()][(int) collisionPoint.getX()] = 'X';
    }

    /**
     * Remove a representação gráfica de um obstáculo na arena.
     *
     * @param obstaculo O obstáculo cuja representação será removida (tipo Obstaculo).
     * @param complete  Indica se o obstáculo deve ser removido completamente ou apenas os pontos de contorno (tipo booleano).
     */
    public void deleteObstacle(Obstaculo obstaculo, boolean complete) {
        Ponto[] pontos = !complete ? obstaculo.getBoundaryIntPoints() : obstaculo.getAllIntPoints();
        for (Ponto ponto : pontos) {
            int x = (int) ponto.getX();
            int y = (int) ponto.getY();
            if (x >= 0 && x < this.arena[0].length && y >= 0 && y < this.arena.length && this.arena[y][x] != 'H')
                this.arena[y][x] = '.';
        }
    }

    /**
     * Retorna uma representação textual da arena, útil para visualização e depuração.
     *
     * @return Uma string representando a configuração atual da arena (tipo String).
     */
    public String printBoard() {
        StringBuilder builder = new StringBuilder();
        for (char[] chars : this.arena) {
            for (char aChar : chars)
                builder.append(aChar).append(" ");
            builder.append("\n");
        }
        return builder.toString().trim();
    }

    /**
     * Retorna a altura da arena.
     *
     * @return A altura da arena (número de linhas)(tipo int).
     */
    public int getHeight() {
        return this.arena.length;
    }

    /**
     * Retorna a largura da arena.
     *
     * @return A largura da arena (número de colunas)(tipo int).
     */
    public int getWidth() {
        return this.arena[0].length;
    }
}
