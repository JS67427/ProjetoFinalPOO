/**
 * A classe Retangulo representa um retângulo definido por um conjunto de quatro pontos no espaço bidimensional.
 * Esta classe herda da classe Poligono e verifica se os quatro pontos fornecidos formam um retângulo válido,
 * ou seja, verifica se os lados opostos são iguais e paralelos, formando ângulos retos entre si.
 *
 * @author Jorge Silva, Paulo Martins, Vasile Karpa
 * @version 1.1 - 12/05/2024
 * @inv As arestas opostas devem ter o mesmo comprimento;
 * @inv Todos os ângulos devem ser retos.
 */

import java.util.ArrayList;
import java.util.List;

public class Retangulo extends Poligono {

    /**
     * Construtor para a classe Retangulo. Copia os pontos fornecidos para o array de pontos do retângulo,
     * garantindo uma instância imutável dos pontos.
     * Verifica se os pontos fornecidos atendem aos requisitos para formar um retângulo válido.
     *
     * @param pontos Array de pontos (tipo Ponto) que formam o retângulo.
     */
    public Retangulo(Ponto[] pontos) {
        super(pontos);
        isRectangle();
    }

    /**
     * Construtor para a classe Retangulo. Inicializa um retângulo com base numa string contendo as coordenadas
     * dos pontos. Verifica se os pontos fornecidos atendem aos requisitos para formar um retângulo válido.
     *
     * @param s String contendo as coordenadas dos pontos do retângulo (tipo String).
     */
    public Retangulo(String s) {
        super(s);
        isRectangle();
    }

    /**
     * Verifica se os pontos fornecidos formam um retângulo válido.
     * Um retângulo é válido se os lados opostos são iguais e paralelos e se todos os ângulos são retos.
     */
    private void isRectangle() {
        if (this.getVertices().length != 4) {
            System.out.println("Retangulo:vi");
            System.exit(0);
        }

        if (this.getVertices()[0].dist(this.getVertices()[2]) < 1e-9 && this.getVertices()[1].dist(this.getVertices()[3]) < 1e-9) {
            System.out.println("Retangulo:vi");
            System.exit(0);
        }

        if (!isRightAngle(this.getVertices()[0], this.getVertices()[1], this.getVertices()[2]) ||
                !isRightAngle(this.getVertices()[1], this.getVertices()[2], this.getVertices()[3]) ||
                !isRightAngle(this.getVertices()[2], this.getVertices()[3], this.getVertices()[0]) ||
                !isRightAngle(this.getVertices()[3], this.getVertices()[0], this.getVertices()[1])) {
            System.out.println("Retangulo:vi");
            System.exit(0);
        }
    }

    /**
     * Verifica se três pontos formam um ângulo reto, com o ponto p2 sendo o vértice do ângulo.
     * O método calcula o produto escalar dos vetores formados pelos pontos p1, p2 e p3.
     * Um produto escalar igual a zero indica que os vetores são perpendiculares, formando um ângulo reto.
     *
     * @param p1 O primeiro ponto (tipo Ponto) do ângulo, ligando a p2.
     * @param p2 O ponto central (tipo Ponto) do ângulo, onde o ângulo reto é hipoteticamente formado.
     * @param p3 O terceiro ponto (tipo Ponto) do ângulo, ligando a p2.
     * @return Verdadeiro se o ângulo entre os pontos é reto; falso, caso contrário (tipo booleano).
     */
    private boolean isRightAngle(Ponto p1, Ponto p2, Ponto p3) {
        double dx1 = p2.getX() - p1.getX();
        double dy1 = p2.getY() - p1.getY();
        double dx2 = p3.getX() - p2.getX();
        double dy2 = p3.getY() - p2.getY();

        return Math.abs(dx1 * dx2 + dy1 * dy2) < 1e-9;
    }

    /**
     * Gera uma representação em string do retângulo, listando seus vértices.
     *
     * @return Uma representação do retângulo em formato string (tipo String).
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Retangulo: [");
        for (int i = 0; i < this.getVertices().length; i++) {
            s.append(this.getVertices()[i].toString());
            if (i != this.getVertices().length - 1)
                s.append(", ");
        }
        s.append("]");
        return s.toString();
    }

    /**
     * Roda o retângulo em torno de um ponto específico e retorna o novo triângulo.
     *
     * @param ang      O ângulo de rotação em graus (tipo double).
     * @param pontoRot O ponto de rotação (tipo Ponto).
     * @return O retângulo (tipo Retangulo) que é a rotação do original.
     */
    @Override
    public Retangulo rotate(double ang, Ponto pontoRot) {
        if (pontoRot == null)
            pontoRot = centroid();
        Ponto[] p = new Ponto[this.getVertices().length];

        for (int i = 0; i < this.getVertices().length; i++)
            p[i] = getVertices()[i].rotate(ang, pontoRot);

        return new Retangulo(p);
    }

    /**
     * Translada o retângulo pela quantidade especificada pelo ponto de translação.
     *
     * @param x A coordenada x para a translação (tipo double).
     * @param y A coordenada y para a translação (tipo double).
     * @return O novo retângulo (tipo Retangulo) que é a translação do original.
     */
    @Override
    public Retangulo translate(double x, double y) {
        Ponto[] p = new Ponto[this.getVertices().length];

        for (int i = 0; i < this.getVertices().length; i++)
            p[i] = getVertices()[i].translate(x, y);

        return new Retangulo(p);
    }

    /**
     * Calcula a translação necessária para mover o retângulo de modo a que o seu centroide passe a ser o especificado.
     *
     * @param x A coordenada x do novo centroide (tipo double).
     * @param y A coordenada y do novo centroide (tipo double).
     * @return O novo retângulo (tipo Retangulo) com o centroide na posição especificada.
     */
    @Override
    public Retangulo newCentroid(double x, double y) {
        Ponto cent = centroid();
        return translate(x - cent.getX(), y - cent.getY());
    }

    /**
     * Retorna os vértices do retângulo como pontos inteiros. Este método calcula os vértices de um retângulo cujas
     * coordenadas são números inteiros, garantindo que os vértices estejam dentro dos limites máximos e mínimos
     * do retângulo.
     *
     * @return Um array de pontos (tipo Ponto[]) representando os vértices do retângulo em coordenadas inteiras.
     */
    public Ponto[] getAllIntVertices() {
        List<Ponto> pontosInteiros = new ArrayList<>();

        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (Ponto p : this.getVertices()) {
            if (p.getX() < minX)
                minX = (int) p.getX();
            if (p.getX() > maxX)
                maxX = (int) p.getX();
            if (p.getY() < minY)
                minY = (int) p.getY();
            if (p.getY() > maxY)
                maxY = (int) p.getY();
        }

        for (int y = minY; y <= maxY; y++)
            for (int x = minX; x <= maxX; x++)
                pontosInteiros.add(new Ponto(x, y));

        return pontosInteiros.toArray(new Ponto[0]);  // Converte a lista para um array e retorna
    }

    /**
     * Retorna apenas os vértices inteiros que contornam o retângulo, incluindo todos os pontos
     * inteiros ao longo das bordas do retângulo.
     *
     * @return Um array de pontos (tipo Ponto[]) representando os vértices inteiros ao longo das bordas do retângulo.
     */
    public Ponto[] getBoundaryIntVertices() {
        List<Ponto> boundaryPoints = new ArrayList<>();

        Ponto[] vertices = this.getVertices();

        for (int i = 0; i < vertices.length; i++) {
            Ponto start = vertices[i];
            Ponto end = vertices[(i + 1) % vertices.length];

            int startX = (int) start.getX();
            int startY = (int) start.getY();
            int endX = (int) end.getX();
            int endY = (int) end.getY();

            if (startX == endX) // Vertical edge
                for (int y = Math.min(startY, endY); y <= Math.max(startY, endY); y++)
                    boundaryPoints.add(new Ponto(startX, y));
            else// Horizontal edge
                for (int x = Math.min(startX, endX); x <= Math.max(startX, endX); x++)
                    boundaryPoints.add(new Ponto(x, startY));
        }

        return boundaryPoints.toArray(new Ponto[0]);  // Convertendo a lista para um array e retornando
    }
}
