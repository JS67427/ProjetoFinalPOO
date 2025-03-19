/**
 * A classe Triangulo representa um triângulo definido por um conjunto de três pontos no espaço bidimensional.
 * Esta classe herda da classe Poligono e verifica se os três pontos fornecidos formam um triângulo válido,
 * ou seja, verifica se os pontos não são colineares, garantindo que formem um triângulo.
 *
 * @author Jorge Silva, Paulo Martins, Vasile Karpa
 * @version 1.1 - 12/05/2024
 * @inv O triângulo deve conter apenas 3 pontos.
 */

import java.util.ArrayList;
import java.util.List;

public class Triangulo extends Poligono {

    /**
     * Construtor para a classe Triangulo. Copia os pontos fornecidos para o array de pontos do triângulo,
     * garantindo uma instância imutável dos pontos.
     * Verifica se os pontos fornecidos atendem aos requisitos para formar um triângulo válido.
     *
     * @param pontos Array de pontos (tipo Ponto) que formam o triângulo.
     */
    public Triangulo(Ponto[] pontos) {
        super(pontos);
        isTriangle();
    }

    /**
     * Construtor para a classe Triangulo. Inicializa um triângulo com base numa string contendo as coordenadas
     * dos pontos. Verifica se os pontos fornecidos atendem aos requisitos para formar um triângulo válido.
     *
     * @param s String contendo as coordenadas dos pontos do triângulo (tipo String).
     */
    public Triangulo(String s) {
        super(s);
        isTriangle();
    }

    /**
     * Verifica se um conjunto de pontos forma um triângulo válido.
     */
    private void isTriangle() {
        if (this.getVertices().length != 3) {
            System.out.println("Triangulo:vi");
            System.exit(0);
        }
    }

    /**
     * Gera uma representação em string do triângulo, listando seus vértices.
     *
     * @return Uma representação do triângulo em formato string (tipo String).
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Triangulo: [");
        for (int i = 0; i < this.getVertices().length; i++) {
            s.append(this.getVertices()[i].toString());
            if (i != this.getVertices().length - 1)
                s.append(", ");
        }
        s.append("]");
        return s.toString();
    }

    /**
     * Roda o triângulo em torno de um ponto específico.
     *
     * @param ang      O ângulo de rotação em graus (tipo double).
     * @param pontoRot O ponto de rotação (tipo Ponto).
     * @return O novo triângulo (tipo Triangulo) que é a rotação do original.
     */
    @Override
    public Triangulo rotate(double ang, Ponto pontoRot) {
        if (pontoRot == null)
            pontoRot = centroid();
        Ponto[] p = new Ponto[this.getVertices().length];

        for (int i = 0; i < this.getVertices().length; i++)
            p[i] = getVertices()[i].rotate(ang, pontoRot);

        return new Triangulo(p);
    }

    /**
     * Translada o triângulo pela quantidade especificada pelo ponto de translação.
     *
     * @param x A coordenada x para a translação (tipo double).
     * @param y A coordenada y para a translação (tipo double).
     * @return O novo triângulo (tipo Triangulo) que é a translação do original.
     */
    @Override
    public Triangulo translate(double x, double y) {
        Ponto[] p = new Ponto[this.getVertices().length];

        for (int i = 0; i < this.getVertices().length; i++)
            p[i] = getVertices()[i].translate(x, y);

        return new Triangulo(p);
    }

    /**
     * Calcula a translação necessária para mover o triângulo de modo a que o seu centroide passe a ser o especificado.
     *
     * @param x A coordenada x do novo centroide (tipo double).
     * @param y A coordenada y do novo centroide (tipo double).
     * @return O novo triângulo (tipo Triangulo) com o centroide na posição especificada.
     */
    @Override
    public Triangulo newCentroid(double x, double y) {
        Ponto cent = centroid();
        return translate(x - cent.getX(), y - cent.getY());
    }

    /**
     * Retorna todos os pontos inteiros que contornam o triângulo.
     * Isso inclui todos os pontos inteiros ao longo das bordas do triângulo.
     *
     * @return Um array de pontos (tipo Ponto[]) representando os vértices inteiros ao longo das bordas do triângulo.
     */
    public Ponto[] getAllIntVertices() {
        List<Ponto> boundaryPoints = new ArrayList<>();
        Ponto[] vertices = this.getVertices();

        // Processa cada par de vértices para adicionar pontos das bordas
        for (int i = 0; i < vertices.length; i++) {
            Ponto start = vertices[i];
            Ponto end = vertices[(i + 1) % vertices.length]; // Loop circular para conectar o último vértice ao primeiro
            addEdgePoints(boundaryPoints, start, end);
        }

        return boundaryPoints.toArray(new Ponto[0]); // Convertendo a lista para um array e retornando
    }

    /**
     * Retorna apenas os vértices inteiros que contornam o triângulo, incluindo todos os pontos
     * inteiros ao longo das bordas do triângulo.
     *
     * @return Um array de pontos (tipo Ponto[]) representando os vértices inteiros ao longo das bordas do triângulo.
     */
    public Ponto[] getBoundaryIntVertices() {
        List<Ponto> boundaryPoints = new ArrayList<>();
        Ponto[] vertices = this.getVertices();

        // Processa cada par de vértices para adicionar pontos das bordas
        for (int i = 0; i < vertices.length; i++) {
            Ponto start = vertices[i];
            Ponto end = vertices[(i + 1) % vertices.length]; // Loop circular para conectar o último vértice ao primeiro
            addEdgePoints(boundaryPoints, start, end);
        }

        return boundaryPoints.toArray(new Ponto[0]); // Convertendo a lista para um array e retornando
    }

    /**
     * Adiciona pontos inteiros ao longo da borda entre dois pontos dados à lista fornecida.
     * Os pontos são calculados usando o algoritmo de Bresenham para linhas.
     *
     * @param points Lista (tipo List<Ponto>) para adicionar os pontos calculados.
     * @param start  Ponto de início da borda (tipo Ponto).
     * @param end    Ponto de fim da borda (tipo Ponto).
     */
    private void addEdgePoints(List<Ponto> points, Ponto start, Ponto end) {
        int x0 = (int) Math.round(start.getX());
        int y0 = (int) Math.round(start.getY());
        int x1 = (int) Math.round(end.getX());
        int y1 = (int) Math.round(end.getY());

        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);

        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;

        int err = dx - dy;

        while (true) {
            points.add(new Ponto(x0, y0));
            if (x0 == x1 && y0 == y1) break;

            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x0 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y0 += sy;
            }
        }
    }
}
