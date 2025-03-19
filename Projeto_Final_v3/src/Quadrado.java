/**
 * A classe Quadrado representa um quadrado definido por um conjunto de quatro pontos no espaço bidimensional.
 * Esta classe herda da classe Retangulo e verifica se os quatro pontos fornecidos formam um quadrado válido,
 * garantindo que todos os lados sejam iguais e formem ângulos retos entre si.
 *
 * @author Jorge Silva, Paulo Martins, Vasile Karpa
 * @version 1.1 - 12/05/2024
 * @inv Todas as arestas devem ter o mesmo comprimento.
 */

public class Quadrado extends Retangulo {
    /**
     * Construtor para a classe Quadrado. Copia os pontos fornecidos para o array de pontos do quadrado,
     * garantindo uma instância imutável dos pontos.
     * Verifica se os pontos fornecidos atendem aos requisitos para formar um quadrado válido.
     *
     * @param pontos Array de pontos (tipo Ponto) que formam o quadrado.
     */
    public Quadrado(Ponto[] pontos) {
        super(pontos);
        isSquare();
    }

    /**
     * Construtor para a classe Quadrado. Inicializa um quadrado com base numa string contendo as coordenadas
     * dos pontos. Verifica se os pontos fornecidos atendem aos requisitos para formar um quadrado válido.
     *
     * @param s String contendo as coordenadas dos pontos do quadrado (tipo String).
     */
    public Quadrado(String s) {
        super(s);
        isSquare();
    }

    /**
     * Verifica se os pontos fornecidos formam um quadrado válido.
     * Um quadrado é válido se todos os quatro lados são iguais e formam ângulos retos.
     */
    private void isSquare() {
        double dist1 = this.getVertices()[0].dist(this.getVertices()[1]);
        double dist2 = this.getVertices()[1].dist(this.getVertices()[2]);
        double dist3 = this.getVertices()[2].dist(this.getVertices()[3]);
        double dist4 = this.getVertices()[3].dist(this.getVertices()[0]);
        if (!(Math.abs(dist1 - dist2) < 1e-9 && Math.abs(dist2 - dist3) < 1e-9 && Math.abs(dist3 - dist4) < 1e-9)) {
            System.out.println("Quadrado:vi");
            System.exit(0);
        }
    }

    /**
     * Gera uma representação em string do quadrado, listando seus vértices.
     *
     * @return Uma representação do quadrado em formato string (tipo String).
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Quadrado: [");
        for (int i = 0; i < this.getVertices().length; i++) {
            s.append(this.getVertices()[i].toString());
            if (i != this.getVertices().length - 1)
                s.append(", ");
        }
        s.append("]");
        return s.toString();
    }

    /**
     * Roda o quadrado em torno de um ponto específico e retorna o novo triângulo.
     *
     * @param ang      O ângulo de rotação em graus (tipo double).
     * @param pontoRot O ponto de rotação (tipo Ponto).
     * @return O quadrado (tipo Quadrado) que é a rotação do original.
     */
    @Override
    public Quadrado rotate(double ang, Ponto pontoRot) {
        if (pontoRot == null)
            pontoRot = centroid();
        Ponto[] p = new Ponto[this.getVertices().length];

        for (int i = 0; i < this.getVertices().length; i++)
            p[i] = getVertices()[i].rotate(ang, pontoRot);

        return new Quadrado(p);
    }

    /**
     * Translada o quadrado pela quantidade especificada pelo ponto de translação.
     *
     * @param x A coordenada x para a translação (tipo double).
     * @param y A coordenada y para a translação (tipo double).
     * @return O novo quadrado (tipo Quadrado) que é a translação do original.
     */
    @Override
    public Quadrado translate(double x, double y) {
        Ponto[] p = new Ponto[this.getVertices().length];

        for (int i = 0; i < this.getVertices().length; i++)
            p[i] = getVertices()[i].translate(x, y);

        return new Quadrado(p);
    }

    /**
     * Calcula a translação necessária para mover o quadrado de modo a que o seu centroide passe a ser o especificado.
     *
     * @param x A coordenada x do novo centroide (tipo double).
     * @param y A coordenada y do novo centroide (tipo double).
     * @return O novo quadrado (tipo Retangulo) com o centroide na posição especificada.
     */
    @Override
    public Quadrado newCentroid(double x, double y) {
        Ponto cent = centroid();
        return translate(x - cent.getX(), y - cent.getY());
    }

    /**
     * Verifica se este quadrado contém completamente outro quadrado. Um quadrado é considerado contido
     * se todos os seus vértices estão dentro dos limites deste quadrado.
     *
     * @param other O outro quadrado a ser verificado (tipo Quadrado).
     * @return Verdadeiro se este quadrado contém completamente o outro; falso caso contrário (tipo booleano).
     */
    public boolean containsQuadrado(Quadrado other) {
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (Ponto p : this.getVertices()) {
            if (p.getX() < minX) minX = (int) p.getX();
            if (p.getX() > maxX) maxX = (int) p.getX();
            if (p.getY() < minY) minY = (int) p.getY();
            if (p.getY() > maxY) maxY = (int) p.getY();
        }

        // Verifica cada vértice do outro quadrado
        for (Ponto p : other.getVertices()) {
            int x = (int) p.getX();
            int y = (int) p.getY();
            if (x < minX || x > maxX || y < minY || y > maxY)
                return false; // Se qualquer vértice estiver fora dos limites, o quadrado não está contido
        }

        return true; // Todos os vértices estão dentro dos limites
    }

    /**
     * Verifica se este quadrado contém completamente uma circunferência. A circunferência é considerada contida
     * se todos os pontos extremos do seu raio estão dentro dos limites deste quadrado.
     *
     * @param circunferencia A circunferência a ser verificada (tipo Circunferencia).
     * @return Verdadeiro se a circunferência está completamente contida; falso caso contrário (tipo booleano).
     */
    public boolean containsCircunferencia(Circunferencia circunferencia) {
        Ponto centro = circunferencia.getCenter();
        double raio = circunferencia.getRadius();

        // Determina os limites extremos da circunferência
        double top = centro.getY() - raio;
        double bottom = centro.getY() + raio;
        double left = centro.getX() - raio;
        double right = centro.getX() + raio;

        // Determina os limites do quadrado
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (Ponto p : this.getVertices()) {
            if (p.getX() < minX) minX = (int) p.getX();
            if (p.getX() > maxX) maxX = (int) p.getX();
            if (p.getY() < minY) minY = (int) p.getY();
            if (p.getY() > maxY) maxY = (int) p.getY();
        }

        // Verifica se todos os pontos extremos da circunferência estão dentro do quadrado
        return left >= minX && right <= maxX && top >= minY && bottom <= maxY;
    }
}
