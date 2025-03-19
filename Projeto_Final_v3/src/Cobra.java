/**
 * A classe Cobra representa a entidade cobra em um jogo de cobra. Ela é responsável por gerenciar o estado e o movimento
 * da cobra, incluindo crescimento, movimento e verificação de colisões.
 *
 * @author Jorge Silva, Paulo Martins, Vasile Karpa
 * @version 1.2 - 22/05/2024
 * @inv A aresta da cabeça da cobra deve ser de tamanho superior a 1;
 * @inv A aresta da cabeça da cobra deve ser de tamanho inferior a 1/5 do tamanho da arena.
 */

import java.util.*;

public class Cobra {
    private final List<Quadrado> snake = new ArrayList<>();
    private final int edge;
    private int dir = -1;

    /**
     * Constrói uma cobra inicialmente com um quadrado, posicionada aleatoriamente dentro do espaço de jogo.
     *
     * @param height Altura do espaço de jogo (tipo int).
     * @param width  Largura do espaço de jogo (tipo int).
     * @param edge   Tamanho de cada segmento da cobra (tipo int).
     * @throws IllegalArgumentException Se o tamanho da cobra for inválido.
     */
    public Cobra(int height, int width, int edge) {
        if (edge < 1)
            throw new IllegalArgumentException("Cobra não pode ter tamanho nulo ou negativo");
        if (height / 5 < edge || width / 5 < edge)
            throw new IllegalArgumentException("Cobra não pode ter tamanho maior que 1/5 da arena");
        this.edge = edge;

        int movementUnit = edge + 1;
        Random r = new Random();
        int x = r.nextInt((width / movementUnit)) * movementUnit;
        int y = r.nextInt((height / movementUnit)) * movementUnit;

        this.snake.add(new Quadrado(new Ponto[]{
                new Ponto(x, y),
                new Ponto(x + edge, y),
                new Ponto(x + edge, y + edge),
                new Ponto(x, y + edge)
        }));
    }

    /**
     * Define a direção da cobra se a nova direção for válida e não reverter diretamente para a oposta.
     *
     * @param dir Nova direção em graus (0, 90, 180, 270) (tipo int).
     * @return Verdadeiro se a direção foi atualizada com sucesso, falso caso contrário (tipo booleano).
     * @throws IllegalArgumentException Se a direção fornecida for inválida.
     */
    public boolean setDir(int dir) {
        if (!(dir == 0 || dir == 90 || dir == 180 || dir == 270))
            throw new IllegalArgumentException("Direção inválida!");
        if (this.snake.size() > 1) {
            if (!(this.dir == 0 && dir == 180 || this.dir == 180 && dir == 0 ||
                    this.dir == 90 && dir == 270 || this.dir == 270 && dir == 90)) {
                this.dir = dir;
                return true;
            }
        } else {
            this.dir = dir;
            return true;
        }
        return false;
    }

    /**
     * Move a cobra na direção atual. A cabeça da cobra é movida para a nova posição e a cauda é ajustada
     * conforme necessário, a menos que a cobra esteja prestes a comer sua própria cauda.
     *
     * @throws IllegalArgumentException Se a cobra tentar comer a própria cauda.
     */
    public void move() {
        int x = 0, y = 0;
        //0: esq-dir; 90: baixo-cima; 180: dir-esq; 270: cima-baixo
        switch (this.dir) {
            case 0:
                x = this.edge + 1;
                break;
            case 90:
                y = -this.edge - 1;
                break;
            case 180:
                x = -this.edge - 1;
                break;
            case 270:
                y = this.edge + 1;
                break;
        }
        Quadrado temp = this.snake.get(0).translate(x, y);
        if (willEatTail(temp))
            throw new IllegalArgumentException("Não podes comer a tua cauda!");

        this.snake.remove(this.snake.size() - 1);
        this.snake.add(0, temp);
    }

    /**
     * Aumenta o tamanho da cobra adicionando um novo segmento no final.
     */
    public void grow() {
        Quadrado temp = this.snake.get(this.snake.size() - 1);
        this.snake.add(temp);
    }

    /**
     * Verifica se a nova posição da cabeça da cobra resultaria em comer a sua própria cauda.
     *
     * @param temp Quadrado representando a nova posição da cabeça da cobra (tipo Quadrado).
     * @return Verdadeiro se a cabeça sobrepor qualquer parte da cauda, falso caso contrário (tipo booleano).
     */
    private boolean willEatTail(Quadrado temp) {
        if (this.snake.size() >= 4)
            for (int i = 3; i <= this.snake.size() - 1; i++)
                if (temp.duplicated(this.snake.get(i)))
                    return true;
        return false;
    }

    /**
     * Obtém todos os pontos inteiros que compõem a cobra.
     *
     * @return Lista de pontos (List<Ponto>) que representam todos os segmentos da cobra.
     */
    public List<Ponto> getAllIntPoints() {
        List<Ponto> allPoints = new ArrayList<>();
        for (Quadrado quad : this.snake)
            Collections.addAll(allPoints, quad.getAllIntVertices());
        return allPoints;
    }

    /**
     * Obtém todos os pontos inteiros que compõem as arestas da cobra.
     *
     * @return Lista de pontos (List<Ponto>) que representam todas as arestas da cobra.
     */
    public List<Ponto> getBoundaryIntPoints() {
        List<Ponto> allPoints = new ArrayList<>();
        for (Quadrado quad : this.snake)
            Collections.addAll(allPoints, quad.getBoundaryIntVertices());
        return allPoints;
    }

    /**
     * Verifica se a cabeça da cobra contém a comida.
     *
     * @param c Comida a ser verificada (tipo Comida).
     * @return Verdadeiro se a cabeça da cobra contém a comida, falso caso contrário (tipo booleano).
     */
    public boolean containsFood(Comida c) {
        if (c.getFood() instanceof Quadrado quadrado)
            return this.snake.get(0).containsQuadrado(quadrado);
        else if (c.getFood() instanceof Circunferencia circunferencia)
            return this.snake.get(0).containsCircunferencia(circunferencia);
        return false;
    }

    /**
     * Coleta todos os pontos inteiros de todos os obstáculos presentes no jogo. Este método é útil para verificar
     * colisões entre a cobra e os obstáculos.
     *
     * @param obstaculos Lista de obstáculos no jogo (tipo List<Obstaculo>).
     * @return Lista de pontos que representam todos os obstáculos no jogo (List<Ponto>).
     */
    private List<Ponto> getAllObstaclePoints(List<Obstaculo> obstaculos) {
        List<Ponto> pontosObstaculo = new ArrayList<>();
        for (Obstaculo obstaculo : obstaculos)
            Collections.addAll(pontosObstaculo, obstaculo.getAllIntPoints());
        return pontosObstaculo;
    }

    /**
     * Identifica interseções entre a cobra e obstáculos no espaço do jogo.
     *
     * @param obstaculos Lista de obstáculos no jogo (tipo List<Obstaculo>).
     * @return Lista de pontos onde ocorrem interseções (List<Ponto>).
     */
    public List<Ponto> findIntersections(List<Obstaculo> obstaculos) {
        List<Ponto> pontosCobra = this.getAllIntPoints();
        List<Ponto> pontosObstaculo = getAllObstaclePoints(obstaculos);
        List<Ponto> intersections = new ArrayList<>();

        for (Ponto pontoCobra : pontosCobra)
            for (Ponto pontoObstaculo : pontosObstaculo)
                if (pontoCobra.dist(pontoObstaculo) < 1e-9)
                    intersections.add(pontoCobra);

        return intersections;
    }

    public List<Obstaculo> getIntersectedObstacles(List<Obstaculo> obstaculos) {
        List<Obstaculo> intersectedObstacles = new ArrayList<>();

        for (Quadrado quadrado : this.snake)
            for (Obstaculo obstaculo : obstaculos)
                if (obstaculo.getObstaculo() instanceof Poligono poligono)
                    if (quadrado.intersects(poligono))
                        intersectedObstacles.add(obstaculo);

        return intersectedObstacles;
    }

    /**
     * Retorna a cobraList<Quadrado>.
     *
     * @return A cobra como uma lista (tipo List<Quadrado>).
     */
    public List<Quadrado> getSnake() {
        return this.snake;
    }

    /**
     * Retorna o tamanho da borda de cada segmento da cobra.
     *
     * @return Tamanho da borda (tipo int).
     */
    public int getEdge() {
        return this.edge;
    }

    /**
     * Retorna a direção atual da cabeça da cobra.
     *
     * @return A direção atual da cabeça da cobra (tipo int).
     */
    public int getDir() {
        return dir;
    }

    /**
     * Verifica se a direção fornecida é segura para a cobra.
     *
     * @param newDir       A nova direção proposta (tipo int).
     * @param arenaWidth   A largura da arena (tipo int).
     * @param arenaHeight  A altura da arena (tipo int).
     * @return Verdadeiro se a direção for segura; falso caso contrário (tipo booleano).
     */
    private boolean isSafeDir(int newDir, int arenaWidth, int arenaHeight) {
        // Verifica se a nova direção é uma volta de 180 graus
        if ((this.dir == 0 && newDir == 180) || (this.dir == 180 && newDir == 0) ||
                (this.dir == 90 && newDir == 270) || (this.dir == 270 && newDir == 90))
            return false;

        // Calcula a nova posição da cabeça da cobra se a nova direção for adotada
        int x = 0, y = 0;
        switch (newDir) {
            case 0:
                x = this.edge + 1;
                break;
            case 90:
                y = -this.edge - 1;
                break;
            case 180:
                x = -this.edge - 1;
                break;
            case 270:
                y = this.edge + 1;
                break;
        }

        Quadrado newHead = this.snake.get(0).translate(x, y);

        // Verifica se a nova posição da cabeça da cobra está fora da arena
        if (newHead.getVertices()[0].getX() < 0 || newHead.getVertices()[0].getY() < 0 ||
                newHead.getVertices()[2].getX() > arenaWidth || newHead.getVertices()[2].getY() > arenaHeight)
            return false;

        // Verifica se a nova posição da cabeça da cobra colide com a cauda
        return !willEatTail(newHead);
    }

    /**
     * Define uma nova direção segura para a cobra.
     *
     * @param newDir       A nova direção proposta (tipo int).
     * @param arenaWidth   A largura da arena (tipo int).
     * @param arenaHeight  A altura da arena (tipo int).
     */
    public void setSafeDir(int newDir, int arenaWidth, int arenaHeight) {
        // Verifica se a nova direção é segura
        if (isSafeDir(newDir, arenaWidth, arenaHeight)) {
            this.dir = newDir;
            return;
        }

        // Tenta encontrar uma direção segura entre as outras três
        int[] directions = {0, 90, 180, 270};
        for (int dir : directions)
            if (dir != newDir && isSafeDir(dir, arenaWidth, arenaHeight)) {
                this.dir = dir;
                return;
            }

        // Mantém a direção atual se nenhuma direção segura for encontrada
        this.dir = newDir;
    }
}
