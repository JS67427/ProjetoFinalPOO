import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TrianguloTests {
    Triangulo t1 = new Triangulo("2 1 4 1 3 4");
    Triangulo t2 = new Triangulo("1 1 2 3 2 1");

    @Test
    void testToString() {
        assertEquals(t1.toString(), "Triangulo: [(2,1), (4,1), (3,4)]");
        assertEquals(t2.toString(), "Triangulo: [(1,1), (2,3), (2,1)]");
    }

    @Test
    void rotate() {
        assertEquals("Triangulo: [(4,3), (2,3), (3,0)]",
                t1.rotate(180, null).toString());
        assertEquals("Triangulo: [(2,2), (4,1), (2,1)]",
                t2.rotate(-90, new Ponto(2, 1)).toString());
    }

    @Test
    void translate() {
        assertEquals("Triangulo: [(3,2), (5,2), (4,5)]",
                t1.translate(1, 1).toString());
        assertEquals("Triangulo: [(5,8), (6,10), (6,8)]",
                t2.translate(4, 7).toString());
    }

    @Test
    void newCentroid() {
        assertEquals("Triangulo: [(4,4), (6,4), (5,7)]",
                t1.newCentroid(5, 5).toString());
        assertEquals("Triangulo: [(22,14), (23,16), (23,14)]",
                t2.newCentroid(23, 15).toString());
    }

    @Test
    void getAllIntVertices() {
        Triangulo triangulo = new Triangulo("0 0 3 0 3 3");
        Ponto[] result = triangulo.getAllIntVertices();

        Ponto[] expected = {
                new Ponto(0, 0), new Ponto(1, 0), new Ponto(2, 0), new Ponto(3, 0),
                new Ponto(3, 0), new Ponto(3, 1), new Ponto(3, 2), new Ponto(3, 3),
                new Ponto(3, 3), new Ponto(2, 2), new Ponto(1, 1), new Ponto(0, 0)
        };

        assertEquals(Arrays.toString(expected), Arrays.toString(result));
    }

    @Test
    void getBoundaryIntVertices() {
        Triangulo triangulo = new Triangulo("0 0 3 0 3 3");
        Ponto[] result = triangulo.getBoundaryIntVertices();

        Ponto[] expected = {
                new Ponto(0, 0), new Ponto(1, 0), new Ponto(2, 0), new Ponto(3, 0),
                new Ponto(3, 0), new Ponto(3, 1), new Ponto(3, 2), new Ponto(3, 3),
                new Ponto(3, 3), new Ponto(2, 2), new Ponto(1, 1), new Ponto(0, 0)
        };

        assertEquals(Arrays.toString(expected), Arrays.toString(result));
    }
}