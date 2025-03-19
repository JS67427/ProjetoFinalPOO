/**
 * A classe SegmentoReta representa um segmento de reta definido por dois pontos distintos no espaço bidimensional.
 * Esta verifica se os dois pontos fornecidos são diferentes, garantindo assim que formam um segmento de reta válido.
 *
 * @author Jorge Silva, Paulo Martins, Vasile Karpa
 * @version 1.1 - 29/03/2024
 * @inv Os pontos recebidos devem formar um segmento de reta, isto é, não devemos ter 2 pontos iguais.
 * @see "https://www.cdn.geeksforgeeks.org/check-if-two-given-line-segments-intersect/"
 * @see "https://stackoverflow.com/questions/3838329/how-can-i-check-if-two-segments-intersect"
 */

public class SegmentoReta {
    private final Ponto p1, p2;

    /**
     * Construtor para a classe SegmentoReta. Verifica se os dois pontos fornecidos formam um segmento de reta válido.
     *
     * @param p1 Primeiro ponto (tipo Ponto) do segmento de reta.
     * @param p2 Segundo ponto (tipo Ponto) do segmento de reta.
     */
    public SegmentoReta(Ponto p1, Ponto p2) {
        if (Math.abs(p1.dist(p2)) < 1e-9) {
            System.out.println("Segmento:vi");
            System.exit(0);
        }
        this.p1 = p1;
        this.p2 = p2;
    }

    /**
     * Retorna o primeiro ponto do segmento de reta.
     *
     * @return O primeiro ponto (tipo Ponto) do segmento de reta.
     */
    public Ponto getPonto1() {
        return this.p1;
    }

    /**
     * Retorna o segundo ponto do segmento de reta.
     *
     * @return O segundo ponto (tipo Ponto) do segmento de reta.
     */
    public Ponto getPonto2() {
        return this.p2;
    }

    /**
     * Calcula a direção da curva formada por três pontos.
     *
     * @param a Primeiro ponto (tipo Ponto) na curva.
     * @param b Segundo ponto (tipo Ponto) na curva.
     * @param c Terceiro ponto (tipo Ponto)na curva.
     * @return Um inteiro que representa a direção da curva formada pelos três pontos:
     * 0 para colinear, 1 para horário e 2 para anti-horário.
     */
    private int direction(Ponto a, Ponto b, Ponto c) {
        double val = (b.getY() - a.getY()) * (c.getX() - b.getX()) - (b.getX() - a.getX()) * (c.getY() - b.getY());
        if (val == 0) return 0; // Collinear
        return (val > 0) ? 1 : 2; // Clock or counterclock wise
    }

    /**
     * Verifica se um ponto está no segmento de reta formado por outros dois pontos.
     *
     * @param p Primeiro ponto (tipo Ponto) do segmento de reta.
     * @param q Ponto a ser verificado (tipo Ponto).
     * @param r Segundo ponto (tipo Ponto) do segmento de reta.
     * @return Verdadeiro se o ponto q estiver no segmento de reta formado
     * pelos pontos p e r; falso, caso contrário (tipo booleano).
     */
    private boolean onSegment(Ponto p, Ponto q, Ponto r) {
        return new Reta(p, r).isCollinear(q);
    }

    /**
     * Verifica se dois segmentos de reta se intersectam.
     *
     * @param other O outro segmento de reta (tipo SegmentoReta) a ser verificado.
     * @return Verdadeiro se os dois segmentos de reta se intersectam; falso, caso contrário (tipo booleano).
     */
    public boolean intersects(SegmentoReta other) {
        // If one edge of one segment touches any part of the other and the other edge doesnt touch,
        // automatically it is assumed that it is either collinear or overlaping
        if (onSegment(this.p1, other.p1, this.p2) && !onSegment(this.p1, other.p2, this.p2)) return false;
        if (onSegment(this.p1, other.p2, this.p2) && !onSegment(this.p1, other.p1, this.p2)) return false;
        if (onSegment(other.p1, this.p1, other.p2) && !onSegment(other.p1, this.p2, other.p2)) return false;
        if (onSegment(other.p1, this.p2, other.p2) && !onSegment(other.p1, this.p1, other.p2)) return false;

        int dir1 = direction(this.p1, this.p2, other.p1);
        int dir2 = direction(this.p1, this.p2, other.p2);
        int dir3 = direction(other.p1, other.p2, this.p1);
        int dir4 = direction(other.p1, other.p2, this.p2);

        return dir1 != dir2 && dir3 != dir4;
    }

    /**
     * Verifica se dois segmentos de reta são iguais. Dois segmentos de reta são considerados iguais
     * se eles têm os mesmos pontos finais, independentemente da ordem dos pontos.
     *
     * @param other O outro segmento de reta (tipo SegmentoReta) a ser comparado com este.
     * @return Verdadeiro se os dois segmentos de reta são iguais; falso, caso contrário (tipo booleano).
     */
    public boolean equals(SegmentoReta other) {
        return (this.p1.equals(other.p1) && this.p2.equals(other.p2)) ||
                (this.p1.equals(other.p2) && this.p2.equals(other.p1));
    }
}