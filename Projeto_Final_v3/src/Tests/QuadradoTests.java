import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuadradoTests {
    Quadrado quad1 = new Quadrado("1 1 2 1 2 2 1 2");
    Quadrado quad2 = new Quadrado("3 3 5 3 5 5 3 5");

    @Test
    void testToString() {
        assertEquals("Quadrado: [(1,1), (2,1), (2,2), (1,2)]", quad1.toString());
        assertEquals("Quadrado: [(3,3), (5,3), (5,5), (3,5)]", quad2.toString());
    }

    @Test
    void rotate() {
        assertEquals("Quadrado: [(2,2), (2,1), (3,1), (3,2)]",
                quad1.rotate(-90, new Ponto(2, 1)).toString());
        assertEquals("Quadrado: [(4,3), (5,4), (4,5), (3,4)]",
                quad2.rotate(45, null).toString());
    }

    @Test
    void translate() {
        assertEquals("Quadrado: [(6,8), (7,8), (7,9), (6,9)]",
                quad1.translate(5, 7).toString());
        assertEquals("Quadrado: [(18,34), (20,34), (20,36), (18,36)]",
                quad2.translate(15, 31).toString());
    }

    @Test
    void newCentroid() {
        assertEquals("Quadrado: [(4,4), (5,4), (5,5), (4,5)]",
                quad1.newCentroid(5, 5).toString());
        assertEquals("Quadrado: [(20,5), (22,5), (22,7), (20,7)]",
                quad2.newCentroid(21, 6).toString());
    }

    @Test
    void containsQuadrado() {
        Quadrado outer = new Quadrado("1 1 4 1 4 4 1 4");
        Quadrado innerCompletelyInside = new Quadrado("2 2 3 2 3 3 2 3");
        Quadrado innerPartiallyInside = new Quadrado("3 3 5 3 5 5 3 5");
        Quadrado innerCompletelyOutside = new Quadrado("5 5 6 5 6 6 5 6");

        assertTrue(outer.containsQuadrado(innerCompletelyInside));
        assertFalse(outer.containsQuadrado(innerPartiallyInside));
        assertFalse(outer.containsQuadrado(innerCompletelyOutside));
    }

    @Test
    void containsCircunferencia() {
        Quadrado square = new Quadrado("0 0 10 0 10 10 0 10");
        Circunferencia fullyInside = new Circunferencia(new Ponto(5, 5), 3);
        Circunferencia crossingBoundary = new Circunferencia(new Ponto(5, 5), 6);
        Circunferencia partiallyInside = new Circunferencia(new Ponto(9, 9), 3);
        Circunferencia fullyOutside = new Circunferencia(new Ponto(20, 20), 5);

        assertTrue(square.containsCircunferencia(fullyInside));
        assertFalse(square.containsCircunferencia(crossingBoundary));
        assertFalse(square.containsCircunferencia(partiallyInside));
        assertFalse(square.containsCircunferencia(fullyOutside));
    }
}