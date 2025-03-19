/**
 * A classe Reta representa uma reta definida por dois pontos distintos no espaço bidimensional.
 * Esta verifica se os dois pontos fornecidos são diferentes, garantindo assim que formam uma reta válida.
 *
 * @author Jorge Silva, Paulo Martins, Vasile Karpa
 * @version 1.1 - 29/03/2024
 * @inv Os pontos recebidos devem formar uma reta, isto é, não devemos ter 2 pontos iguais.
 */

public class Reta {
    private final Ponto r1, r2;

    /**
     * Construtor para a classe Reta. Verifica se os três pontos fornecidos formam uma linha reta.
     *
     * @param p1 Primeiro ponto (tipo Ponto) da reta.
     * @param p2 Segundo ponto (tipo Ponto) da reta.
     */
    public Reta(Ponto p1, Ponto p2) {
        if (Math.abs(p1.dist(p2)) < 1e-9) {
            System.out.println("Reta:vi");
            System.exit(0);
        }
        this.r1 = p1;
        this.r2 = p2;
    }

    /**
     * Verifica se um ponto externo é colinear com os dois pontos que definem esta reta.
     *
     * @param p O ponto externo (tipo Ponto) a ser avaliado em relação à reta.
     * @return Verdadeiro se o ponto externo e os dois pontos da reta forem colineares;
     * falso, caso contrário (tipo booleano).
     */
    public boolean isCollinear(Ponto p) {
        if (Math.abs(this.r1.dist(p)) < 1e-9 || Math.abs(this.r2.dist(p)) < 1e-9)
            return true;

        double slope1 = calculateSlope(this.r1, this.r2);
        double slope2 = calculateSlope(this.r1, p);

        return Math.abs(slope1 - slope2) < 1e-9;
    }

    /**
     * Calcula o declive entre dois pontos.
     *
     * @param p1 Primeiro ponto (tipo Ponto) para o cálculo do declive.
     * @param p2 Segundo ponto (tipo Ponto) para o cálculo do declive.
     * @return O declive (tipo decimal) entre o ponto p1 e o ponto p2.
     */
    private double calculateSlope(Ponto p1, Ponto p2) {
        // Avoid division by zero by checking if the x-coordinates are equal
        if (Math.abs(p1.getX() - p2.getX()) < 1e-9)
            return Double.MAX_VALUE; // Use a large value to represent an infinite slope
        return (p2.getY() - p1.getY()) / (p2.getX() - p1.getX());
    }

    /**
     * Retorna o primeiro ponto da reta.
     *
     * @return O primeiro ponto (tipo Ponto) da reta.
     */
    public Ponto getPonto1() {
        return this.r1;
    }

    /**
     * Retorna o segundo ponto da reta.
     *
     * @return O segundo ponto (tipo Ponto) da reta.
     */
    public Ponto getPonto2() {
        return this.r2;
    }
}
