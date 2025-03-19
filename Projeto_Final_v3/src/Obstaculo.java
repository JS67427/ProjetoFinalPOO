/**
 * A classe Obstaculo representa um obstáculo que pode ser colocado na arena do jogo de cobra.
 * Os obstáculos podem ser de várias formas, como quadrados, retângulos ou triângulos.
 *
 * @author Jorge Silva, Paulo Martins, Vasile Karpa
 * @version 1.1 - 12/05/2024
 * @inv O obstáculo não deve ser gerado em cima da cobra.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Obstaculo {
    private Object obstaculo;
    private int obsSize, rectBigSide;
    private Ponto pontoRot;
    private int angRot;
    private final Random r = new Random();

    /**
     * Constrói um obstáculo em uma posição aleatória dentro da arena que não interfira com a cobra.
     *
     * @param width  Largura da arena onde o obstáculo será colocado (tipo int).
     * @param height Altura da arena onde o obstáculo será colocado (tipo int).
     * @param snake  A cobra no jogo, para evitar sobreposição ao criar o obstáculo (tipo Cobra).
     */
    public Obstaculo(int width, int height, Cobra snake) {
        int edge = snake.getEdge();
        int movementUnit = edge + 1;
        List<Ponto> potentialPositions = new ArrayList<>();

        // Gerar todas as posições possíveis dentro da arena considerando o movimento da cobra
        for (int x = 0; x < width - width % movementUnit; x += movementUnit)
            for (int y = 0; y < height - height % movementUnit; y += movementUnit)
                potentialPositions.add(new Ponto(x, y));

        Collections.shuffle(potentialPositions, r); // Embaralhar posições para garantir aleatoriedade

        for (Ponto pos : potentialPositions) {
            this.obsSize = r.nextInt(edge / 4, edge + 1);
            this.obstaculo = createShape(pos);
            if (isPositionFree(snake)) {
                setPontoRot();
                setAngRot();
                return; // Obstáculo foi colocado com sucesso
            }
        }
        this.obstaculo = null;
    }

    /**
     * Define um ponto aleatório para a rotação do obstáculo.
     */
    private void setPontoRot() {
        List<Ponto> potentialRotPoints = new ArrayList<>(List.of(this.getBoundaryIntPoints()));
        potentialRotPoints.add(((Poligono) this.getObstaculo()).centroid());
        this.pontoRot = potentialRotPoints.get(this.r.nextInt(0, potentialRotPoints.size()));
    }

    /**
     * Verifica se o obstáculo está a rodar.
     *
     * @return Verdadeiro se o obstáculo está a rodar; falso caso contrário (tipo booleano).
     */
    public boolean isRotating() {
        return this.angRot != 0;
    }

    /**
     * Define um ângulo aleatório para a rotação do obstáculo.
     */
    private void setAngRot() {
        switch (r.nextInt(5) - 1) {
            case 0 -> angRot = 0;
            case 1 -> angRot = 90;
            case 2 -> angRot = 180;
            case 3 -> angRot = 270;
        }
    }

    /**
     * Cria uma forma geométrica como obstáculo com base na posição e tamanho especificados.
     * A forma pode ser um quadrado, retângulo ou triângulo, escolhido aleatoriamente.
     *
     * @param upLeft O ponto superior esquerdo onde a forma será baseada (tipo Ponto).
     * @return Uma instância de um objeto geométrico (Quadrado, Retangulo ou Triangulo).
     */
    private Object createShape(Ponto upLeft) {
        int chooseShape = r.nextInt(3);
        return switch (chooseShape) {
            case 0 -> createQuadrado(upLeft);
            case 1 -> createRetangulo(upLeft);
            case 2 -> createTriangulo(upLeft);
            default -> null;
        };
    }

    /**
     * Cria um quadrado como obstáculo.
     *
     * @param upLeft O ponto superior esquerdo do quadrado (tipo Ponto).
     * @return Um novo objeto Quadrado.
     */
    private Quadrado createQuadrado(Ponto upLeft) {
        return new Quadrado(new Ponto[]{
                upLeft,
                upLeft.translate(this.obsSize, 0),
                upLeft.translate(this.obsSize, this.obsSize),
                upLeft.translate(0, this.obsSize)
        });
    }

    /**
     * Cria um retângulo como obstáculo.
     *
     * @param upLeft O ponto superior esquerdo do retângulo (tipo Ponto).
     * @return Um novo objeto Retangulo.
     */
    private Retangulo createRetangulo(Ponto upLeft) {
        int length_x = r.nextInt(1, this.obsSize + 1);
        this.rectBigSide = this.obsSize + length_x;
        return new Retangulo(new Ponto[]{
                upLeft,
                upLeft.translate(this.rectBigSide, 0),
                upLeft.translate(this.rectBigSide, this.obsSize),
                upLeft.translate(0, this.obsSize)
        });
    }

    /**
     * Cria um triângulo como obstáculo.
     *
     * @param upLeft O ponto superior esquerdo do triângulo (tipo Ponto).
     * @return Um novo objeto Triangulo.
     */
    private Triangulo createTriangulo(Ponto upLeft) {
        return new Triangulo(new Ponto[]{
                upLeft,
                upLeft.translate(this.obsSize, 0),
                upLeft.translate(0, this.obsSize)
        });
    }

    /**
     * Obtém todos os pontos inteiros que compõem o obstáculo.
     *
     * @return Um array de pontos (Ponto[]) representando todos os pontos inteiros que compõem o obstáculo.
     */
    public Ponto[] getAllIntPoints() {
        if (obstaculo instanceof Quadrado quadrado)
            return quadrado.getAllIntVertices();
        else if (obstaculo instanceof Retangulo retangulo)
            return retangulo.getAllIntVertices();
        else if (obstaculo instanceof Triangulo triangulo)
            return triangulo.getAllIntVertices();
        return new Ponto[0];
    }

    /**
     * Obtém todos os pontos inteiros que compõem as arestas do obstáculo.
     *
     * @return Um array de pontos (Ponto[]) representando todos os pontos inteiros que compõem as arestas do obstáculo.
     */
    public Ponto[] getBoundaryIntPoints() {
        if (obstaculo instanceof Quadrado quadrado)
            return quadrado.getBoundaryIntVertices();
        else if (obstaculo instanceof Retangulo retangulo)
            return retangulo.getBoundaryIntVertices();
        else if (obstaculo instanceof Triangulo triangulo)
            return triangulo.getBoundaryIntVertices();
        return new Ponto[0];
    }

    /**
     * Roda o obstáculo em torno de um ponto por um ângulo, ambos aleatórios.
     */
    public void rotate() {
        if (this.angRot != 0)
            if (obstaculo instanceof Quadrado quadrado)
                this.obstaculo = quadrado.rotate(this.angRot, this.pontoRot);
            else if (obstaculo instanceof Retangulo retangulo)
                this.obstaculo = retangulo.rotate(this.angRot, this.pontoRot);
            else if (obstaculo instanceof Triangulo triangulo)
                this.obstaculo = triangulo.rotate(this.angRot, this.pontoRot);
    }

    /**
     * Verifica se a posição escolhida para o obstáculo não interfere com a cobra.
     *
     * @param snake A cobra atual no jogo, para evitar sobreposição (tipo Cobra).
     * @return Verdadeiro se a posição estiver livre, falso se sobrepõe à cobra (tipo booleano).
     */
    private boolean isPositionFree(Cobra snake) {
        Ponto[] shapePoints = getAllIntPoints(); // Obter pontos do obstáculo a ser colocado
        List<Ponto> snakePoints = snake.getAllIntPoints();
        for (Ponto sp : snakePoints)
            for (Ponto shapePoint : shapePoints)
                if (sp.equals(shapePoint))
                    return false; // Obstáculo sobreporia a cobra
        return true; // Nenhuma sobreposição encontrada
    }

    /**
     * Obtém o obstáculo atual.
     *
     * @return O objeto obstáculo, que pode ser um Quadrado, Retangulo ou Triangulo (tipo Object).
     */
    public Object getObstaculo() {
        return this.obstaculo;
    }

    /**
     * Retorna o tamanho da aresta do obstáculo gerado.
     *
     * @return Tamanho da aresta do obstáculo (tipo int).
     */
    public int getObsSize() {
        return obsSize;
    }

    /**
     * Retorna o tamanho da aresta maior do retângulo gerado.
     *
     * @return Tamanho da aresta maior do retângulo (tipo int).
     */
    public int getRectBigSide() {
        return rectBigSide;
    }
}
