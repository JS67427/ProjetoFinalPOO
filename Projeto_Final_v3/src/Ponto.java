/**
 * A classe Ponto representa um ponto no espaço bidimensional e garante que este pertence sempre ao primeiro quadrante
 *
 * @author Jorge Silva, Paulo Martins, Vasile Karpa
 * @version 1.1 - 29/03/2024
 * @inv Os pontos devem pertencer ao 1.º quadrante.
 */

public class Ponto {
    private final double x, y;

    /**
     * Construtor para a classe Ponto. Garante que o ponto esta no primeiro quadrante.
     *
     * @param x Coordenada x do ponto (tipo decimal).
     * @param y Coordenada y do ponto (tipo decimal).
     */
    public Ponto(double x, double y) {
        /*if(x<0 || y<0) {
            System.out.println(x + "," + y);
            System.out.println("Ponto:vi");
            System.exit(0);
        }*/
        this.x = x;
        this.y = y;
    }

    /**
     * Método para calcular a distância euclidiana entre este ponto e outro ponto.
     *
     * @param that O outro ponto (tipo Ponto) com o qual a distância será calculada.
     * @return A distância euclidiana (tipo decimal) entre este ponto e o ponto 'that'.
     */
    public double dist(Ponto that) {
        double dx = this.x - that.x;
        double dy = this.y - that.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Método que retorna a coordenada x deste ponto.
     *
     * @return A coordenada x (tipo decimal) deste ponto.
     */
    public double getX() {
        return this.x;
    }

    /**
     * Método que retorna a coordenada y deste ponto.
     *
     * @return A coordenada y (tipo decimal) deste ponto.
     */
    public double getY() {
        return this.y;
    }

    /**
     * Verifica se este ponto é igual a outro ponto.
     * Dois pontos são considerados iguais se suas coordenadas x e y são as mesmas.
     *
     * @param other O outro ponto (tipo Ponto) a ser comparado com este ponto.
     * @return Verdadeiro se as coordenadas deste ponto e do ponto 'other' são iguais;
     * falso, caso contrário (tipo booleano).
     */
    public boolean equals(Ponto other) {
        return (Math.abs(this.x - other.x) < 1e-9 && Math.abs(this.y - other.y) < 1e-9);
    }


    /**
     * Gera uma representação em string deste ponto, mostrando suas coordenadas.
     *
     * @return Uma representação das coordenadas deste ponto em formato string (tipo String).
     */
    public String toString() {
        return "(" + (int) x + "," + (int) y + ")";
    }

    /**
     * Roda o ponto original em torno de um ponto específico.
     *
     * @param ang      O ângulo de rotação em graus (tipo double).
     * @param pontoRot O ponto de rotação (tipo Ponto).
     * @return O novo ponto (tipo Ponto) que é a rotação do original.
     */
    public Ponto rotate(double ang, Ponto pontoRot) {
        double angRad = Math.toRadians(ang);
        double dx = this.x - pontoRot.getX();
        double dy = this.y - pontoRot.getY();

        double xRodado = dx * Math.cos(angRad) - dy * Math.sin(angRad) + pontoRot.getX();
        double yRodado = dx * Math.sin(angRad) + dy * Math.cos(angRad) + pontoRot.getY();
        return new Ponto(Math.round(xRodado), Math.round(yRodado));
    }

    /**
     * Translada o ponto original por um deslocamento especificado nas direções x e y.
     *
     * @param dx O deslocamento na direção x (tipo decimal).
     * @param dy O deslocamento na direção y (tipo decimal).
     * @return O novo ponto (tipo Ponto) que é a translação do original.
     */
    public Ponto translate(double dx, double dy) {
        return new Ponto(this.x + dx, this.y + dy);
    }
}