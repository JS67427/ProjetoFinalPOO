/**
 * A classe Poligono representa um polígono definido por um conjunto de pontos no espaço bidimensional.
 * Esta verifica se os pontos fornecidos formam um polígono válido, garantindo que são três ou mais pontos,
 * que não são colineares e que os segmentos de reta formados pelos pontos não se interceptam.
 *
 * @author Jorge Silva, Paulo Martins, Vasile Karpa
 * @version 1.5 - 22/05/2024
 * @inv Os pontos recebidos devem formar um polígono;
 * @inv Devem ser 3 ou mais pontos;
 * @inv Os pontos não devem ser colineares;
 * @inv Os segmentos de reta formados pelos pontos não devem se interceptar.
 * @see www.omnicalculator.com/pt/matematica/calculadora-centroide#qual-e-a-formula-do-centroide
 * @see www.math.stackexchange.com/questions/1177255/rotation-of-an-element-around-a-pivot-using-movements-and-rotations
 */

public class Poligono {
    private final Ponto[] pointsPol;

    /**
     * Construtor para a classe Poligono. Copia os pontos fornecidos para o array de pontos do polígono,
     * garantindo uma instância imutável dos pontos.
     * Verifica se os pontos fornecidos atendem aos requisitos para formar um polígono válido.
     *
     * @param pontos Array de pontos (tipo Ponto) que formam o polígono.
     */
    public Poligono(Ponto[] pontos) {
        this.pointsPol = new Ponto[pontos.length];
        System.arraycopy(pontos, 0, this.pointsPol, 0, pontos.length);
        isPolygon();
    }

    /**
     * Construtor para a classe Poligono. Inicializa um polígono com base numa string contendo as coordenadas
     * dos pontos. Verifica se os pontos fornecidos atendem aos requisitos para formar um polígono válido.
     *
     * @param s String que contém as coordenadas dos pontos do polígono (tipo String).
     */
    public Poligono(String s) {
        String[] partes = s.split(" ");
        boolean inputPar = partes.length % 2 == 0;
        pointsPol = new Ponto[inputPar ? partes.length / 2 : (partes.length - 1) / 2];
        if (!inputPar && Integer.parseInt(partes[0]) != (partes.length - 1) / 2) {
            System.out.println("Poligono:vi");
            System.exit(0);
        } else {
            int cont = 0;
            for (int i = inputPar ? 0 : 1; i < partes.length; i += 2)
                this.pointsPol[cont++] = new Ponto(Integer.parseInt(partes[i]), Integer.parseInt(partes[i + 1]));
        }
        isPolygon();
    }

    /**
     * Verifica se um conjunto de pontos forma um polígono válido.
     */
    private void isPolygon() {
        if (this.pointsPol.length < 3) {
            System.out.println(pointsPol.length);
            System.out.println(pointsPol.toString());
            System.out.println("Poligono:vi");
            System.exit(0);
        }
        for (int i = 0; i < this.pointsPol.length; i++)
            if (new Reta(this.pointsPol[i], this.pointsPol[(i + 1) % this.pointsPol.length]).isCollinear(this.pointsPol[(i + 2) % this.pointsPol.length])) {
                System.out.println("Poligono:vi");
                System.exit(0);
            }

        for (int i = 0; i < this.pointsPol.length; i++)
            for (int j = 0; j < this.pointsPol.length; j++) {
                // Skip adjacent segments and the segment itself
                if (Math.abs(i - j) <= 1 || Math.abs(i - j) == this.pointsPol.length - 1) continue;

                SegmentoReta s1 = new SegmentoReta(this.pointsPol[i], this.pointsPol[(i + 1) % this.pointsPol.length]);
                SegmentoReta s2 = new SegmentoReta(this.pointsPol[j], this.pointsPol[(j + 1) % this.pointsPol.length]);

                if (s1.intersects(s2)) {
                    System.out.println("Poligono:vi");
                    System.exit(0);
                }
            }
    }

    /**
     * Retorna os segmentos de reta que compõem este polígono.
     *
     * @return Array de segmentos de reta (tipo SegmentoReta[]) que compõem o polígono.
     */
    private SegmentoReta[] getPolygonSegments() {
        SegmentoReta[] segments = new SegmentoReta[this.pointsPol.length];
        for (int i = 0; i < this.pointsPol.length; i++)
            segments[i] = new SegmentoReta(this.pointsPol[i], this.pointsPol[(i + 1) % this.pointsPol.length]);
        return segments;
    }

    /**
     * Retorna os vértices do polígono.
     *
     * @return Array de pontos (tipo Ponto[]) que representam os vértices do polígono.
     */
    public Ponto[] getVertices() {
        return this.pointsPol;
    }

    /**
     * Verifica se este polígono intersecta outro polígono.
     * A interseção é determinada verificando se algum dos segmentos de reta que formam este polígono
     * intersecta qualquer um dos segmentos de reta que formam o outro polígono.
     *
     * @param other O outro polígono (tipo Poligono) com o qual a interseção será verificada.
     * @return Verdadeiro se houver pelo menos uma interseção entre um segmento deste polígono
     * e um segmento do outro polígono; falso, caso contrário (tipo booleano).
     */
    public boolean intersects(Poligono other) {
        for (SegmentoReta segmentThis : this.getPolygonSegments())
            for (SegmentoReta segmentOther : other.getPolygonSegments())
                if (segmentThis.intersects(segmentOther))
                    return true;
        return false;
    }

    /**
     * Gera uma representação em string do polígono, listando seus vértices.
     *
     * @return Uma representação do polígono em formato string (tipo String).
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Poligono de " + this.pointsPol.length + " vertices: [");
        for (int i = 0; i < this.pointsPol.length; i++) {
            s.append(this.pointsPol[i].toString());
            if (i != this.pointsPol.length - 1)
                s.append(", ");
        }
        s.append("]");
        return s.toString();
    }

    /**
     * Verifica se este polígono e outro polígono têm os mesmos segmentos de reta, indicando que são duplicados.
     *
     * @param other O outro polígono (tipo Poligono) para comparar com este polígono.
     * @return Verdadeiro se todos os segmentos deste polígono corresponderem aos segmentos do outro polígono,
     * indicando que os dois polígonos são idênticos; falso, caso contrário (tipo booleano).
     */
    public boolean duplicated(Poligono other) {
        if (this.pointsPol.length != other.pointsPol.length)
            return false;

        int dup = 0;
        for (SegmentoReta segmentThis : this.getPolygonSegments())
            for (SegmentoReta segmentOther : other.getPolygonSegments())
                if (segmentThis.equals(segmentOther)) {
                    dup++;
                    break;
                }
        return dup == this.pointsPol.length;
    }

    /**
     * Calcula o centroide do polígono.
     *
     * @return O ponto que representa o centroide do polígono (tipo Ponto).
     */
    public Ponto centroid() {
        double sumX = 0, sumY = 0;
        for (Ponto ponto : this.pointsPol) {
            sumX += ponto.getX();
            sumY += ponto.getY();
        }
        return new Ponto(sumX / this.pointsPol.length, sumY / this.pointsPol.length);
    }

    /**
     * Roda o polígono em torno de um ponto específico.
     *
     * @param ang      O ângulo de rotação em graus (tipo double).
     * @param pontoRot O ponto de rotação (tipo Ponto).
     * @return O novo polígono (tipo Poligono) que é a rotação do original.
     */
    public Poligono rotate(double ang, Ponto pontoRot) {
        if (pontoRot == null)
            pontoRot = centroid();
        Ponto[] p = new Ponto[this.pointsPol.length];

        for (int i = 0; i < this.pointsPol.length; i++)
            p[i] = pointsPol[i].rotate(ang, pontoRot);

        return new Poligono(p);
    }

    /**
     * Translada o polígono pela quantidade especificada pelo ponto de translação.
     *
     * @param x A coordenada x para a translação (tipo double).
     * @param y A coordenada y para a translação (tipo double).
     * @return O novo polígono (tipo Poligono) que é a translação do original.
     */
    public Poligono translate(double x, double y) {
        Ponto[] p = new Ponto[this.pointsPol.length];

        for (int i = 0; i < this.pointsPol.length; i++)
            p[i] = pointsPol[i].translate(x, y);

        return new Poligono(p);
    }

    /**
     * Calcula a translação necessária para mover o poligono de modo a que o seu centroide passe a ser o especificado.
     *
     * @param x A coordenada x do novo centroide (tipo double).
     * @param y A coordenada y do novo centroide (tipo double).
     * @return O novo polígono (tipo Poligono) com o centroide na posição especificada.
     */
    public Poligono newCentroid(double x, double y) {
        Ponto cent = centroid();
        return translate(x - cent.getX(), y - cent.getY());
    }

    /**
     * Retorna o ponto superior esquerdo (top-left) do polígono.
     *
     * @return O ponto superior esquerdo do polígono (tipo Ponto).
     */
    public Ponto getTopLeft() {
        Ponto topLeft = new Ponto(Double.MAX_VALUE, Double.MAX_VALUE);
        for (Ponto vertex : this.pointsPol)
            if (vertex.getX() < topLeft.getX() && vertex.getY() < topLeft.getY())
                topLeft = vertex;
        return topLeft;
    }
}