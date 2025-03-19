import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PontoTests {
    @Test
    void dist() {
        assertEquals(1, new Ponto(1, 1).dist(new Ponto(2, 1)));
        assertEquals(3, new Ponto(1, 1).dist(new Ponto(1, 4)));
        assertEquals(177.2004514666935, new Ponto(25, 30).dist(new Ponto(75, 200)));
    }

    @Test
    void getX() {
        assertEquals(2, new Ponto(2, 1).getX());
        assertEquals(45, new Ponto(45, 67).getX());
    }

    @Test
    void getY() {
        assertEquals(5, new Ponto(1, 5).getY());
        assertEquals(73, new Ponto(79, 73).getY());
    }

    @Test
    void testEquals() {
        assertTrue(new Ponto(1, 1).equals(new Ponto(1, 1)));
        assertFalse(new Ponto(1, 1).equals(new Ponto(1, 2)));
    }

    @Test
    void testToString() {
        assertEquals("(1,5)", new Ponto(1, 5).toString());
        assertEquals("(79,73)", new Ponto(79, 73).toString());
    }

    @Test
    void rotate() {
        Ponto centro = new Ponto(1, 1);
        Ponto ponto = new Ponto(2, 1);

        // Rotação de 90 graus em torno do ponto (1,1)
        Ponto resultado90 = ponto.rotate(90, centro);
        assertEquals(new Ponto(1, 2).toString(), resultado90.toString());

        // Rotação de 180 graus em torno do ponto (1,1)
        Ponto resultado180 = ponto.rotate(180, centro);
        assertEquals(new Ponto(0, 1).toString(), resultado180.toString());

        // Rotação de 270 graus em torno do ponto (1,1)
        Ponto resultado270 = ponto.rotate(270, centro);
        assertEquals(new Ponto(1, 0).toString(), resultado270.toString());

        // Rotação de 360 graus em torno do ponto (1,1)
        Ponto resultado360 = ponto.rotate(360, centro);
        assertEquals(new Ponto(2, 1).toString(), resultado360.toString());
    }

    @Test
    void translate() {
        Ponto ponto = new Ponto(1, 1);

        // Translação por (1, 0)
        Ponto resultadoX = ponto.translate(1, 0);
        assertEquals(new Ponto(2, 1).toString(), resultadoX.toString());

        // Translação por (0, 1)
        Ponto resultadoY = ponto.translate(0, 1);
        assertEquals(new Ponto(1, 2).toString(), resultadoY.toString());

        // Translação por (-1, -1)
        Ponto resultadoNegativo = ponto.translate(-1, -1);
        assertEquals(new Ponto(0, 0).toString(), resultadoNegativo.toString());
    }
}
