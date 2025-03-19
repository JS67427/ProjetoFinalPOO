/**
 * A classe Circunferencia representa uma circunferência no plano bidimensional.
 * Esta é definida por um centro e um raio, e inclui métodos para manipulação e verificação geométrica.
 *
 * @author Jorge Silva, Paulo Martins, Vasile Karpa
 * @version 1.1 - 12/05/2024
 * @inv O raio deve ser superior a 0.
 */

import java.util.ArrayList;
import java.util.List;

public class Circunferencia {
    private final Ponto center;
    private final double radius;

    /**
     * Construtor para a classe Circunferencia. Verifica se o raio é válido e inicializa a circunferência.
     *
     * @param center O ponto que representa o centro da circunferência (tipo Ponto).
     * @param radius O raio da circunferência (tipo double). Deve ser maior que zero.
     */
    public Circunferencia(Ponto center, double radius) {
        if (radius <= 0)
            throw new IllegalArgumentException("O raio deve ser positivo.");
        this.center = center;
        this.radius = radius;
    }

    /**
     * Retorna o centro da circunferência.
     *
     * @return O centro da circunferência (tipo Ponto).
     */
    public Ponto getCenter() {
        return this.center;
    }

    /**
     * Retorna o raio da circunferência.
     *
     * @return O raio da circunferência (tipo double).
     */
    public double getRadius(){
        return this.radius;
    }

    /**
     * Gera uma representação em string da circunfência, listando o seu raio e o seu centro.
     *
     * @return Uma representação do polígono em formato string (tipo String).
     */
    @Override
    public String toString() {
        return "Circunferencia de raio " + this.radius + " e centro: " + this.center.toString();
    }

    /**
     * Verifica se esta circunferência intersecta outra circunferência.
     *
     * @param other A outra circunferência (tipo Circunferencia) com a qual a interseção será verificada.
     * @return Verdadeiro se as circunferências se intersectam, falso caso contrário (tipo booleano).
     */
    public boolean intersects(Circunferencia other) {
        double distanciaCentros = this.center.dist(other.center);
        return distanciaCentros <= (this.radius + other.radius);
    }

    /**
     * Verifica se esta circunferência e outra circunferência têm o mesmo centro e raio, indicando que são duplicadas.
     *
     * @param other A outra circunferência (tipo Circunferencia) para comparar com este polígono.
     * @return Verdadeiro se todos os segmentos deste polígono corresponderem aos segmentos do outro polígono,
     *         indicando que os dois polígonos são idênticos; falso, caso contrário (tipo booleano).
     */
    public boolean duplicated(Circunferencia other) {
        return this.center.dist(other.center)<1e-9 && this.radius==other.radius;
    }

    /**
     * Roda a circunferência em torno de um ponto específico.
     *
     * @param ang O ângulo de rotação em graus (tipo double).
     * @param pontoRot O ponto de rotação (tipo Ponto).
     * @return Uma nova circunferência (tipo Circunferencia) que é a rotação da original.
     */
    public Circunferencia rotate(double ang, Ponto pontoRot) {
        return new Circunferencia(this.center.rotate(ang, pontoRot), this.radius);
    }

    /**
     * Translada a circunferência pela quantidade especificada.
     *
     * @param x Deslocamento em x (tipo double).
     * @param y Deslocamento em y (tipo double).
     */
    public Circunferencia translate(double x, double y) {
        return new Circunferencia(this.center.translate(x,y),this.radius);
    }

    /**
     * Retorna todos os pontos com coordenadas inteiras que estão dentro da circunferência.
     *
     * @return Um array de pontos (tipo Ponto[]) dentro da circunferência.
     */
    public Ponto[] getAllIntVertices() {
        List<Ponto> pontosInteiros = new ArrayList<>();

        // Determinando os limites para verificação
        int minX = (int) Math.floor(this.center.getX() - this.radius);
        int maxX = (int) Math.ceil(this.center.getX() + this.radius);
        int minY = (int) Math.floor(this.center.getY() - this.radius);
        int maxY = (int) Math.ceil(this.center.getY() + this.radius);

        // Iterando sobre a grade de pontos dentro dos limites
        for (int x = minX; x <= maxX; x++)
            for (int y = minY; y <= maxY; y++)
                if (Math.pow(x - this.center.getX(), 2) + Math.pow(y - this.center.getY(), 2) <= Math.pow(this.radius, 2))
                    pontosInteiros.add(new Ponto(x, y));

        return pontosInteiros.toArray(new Ponto[0]);
    }

    /**
     * Retorna todos os pontos com coordenadas inteiras que estão exatamente no contorno da circunferência.
     *
     * @return Um array de pontos (tipo Ponto[]) que estão no contorno da circunferência.
     */
    public Ponto[] getBoundaryIntVertices() {
        List<Ponto> boundaryPoints = new ArrayList<>();

        // Determinando os limites para verificação
        int minX = (int) Math.floor(this.center.getX() - this.radius);
        int maxX = (int) Math.ceil(this.center.getX() + this.radius);
        int minY = (int) Math.floor(this.center.getY() - this.radius);
        int maxY = (int) Math.ceil(this.center.getY() + this.radius);

        double radiusSquared = this.radius * this.radius;
        double epsilon = 0.5; // Margem de erro para determinar se um ponto está no contorno

        // Iterando sobre a grade de pontos dentro dos limites
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                double distSquared = Math.pow(x - this.center.getX(), 2) + Math.pow(y - this.center.getY(), 2);
                // Verifica se o ponto está no contorno usando epsilon para margem de erro
                if (Math.abs(distSquared - radiusSquared) <= epsilon) {
                    boundaryPoints.add(new Ponto(x, y));
                }
            }
        }

        return boundaryPoints.toArray(new Ponto[0]);
    }
}
