import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class RetanguloTests {
    Retangulo ret1 = new Retangulo("1 1 2 1 2 2 1 2");
    Retangulo ret2 = new Retangulo("3 3 5 3 5 5 3 5");

    @Test
    void testToString() {
        assertEquals(ret1.toString(), "Retangulo: [(1,1), (2,1), (2,2), (1,2)]");
        assertEquals(ret2.toString(), "Retangulo: [(3,3), (5,3), (5,5), (3,5)]");
    }

    @Test
    void rotate() {
        Retangulo r1 = new Retangulo("1 1 3 1 3 5 1 5");
        assertEquals("Retangulo: [(4,2), (4,4), (0,4), (0,2)]",
                r1.rotate(90, null).toString());
        Retangulo r2 = new Retangulo("1 1 3 1 3 5 1 5");
        assertEquals("Retangulo: [(3,3), (3,1), (7,1), (7,3)]",
                r2.rotate(-90, new Ponto(3, 1)).toString());
    }

    @Test
    void translate() {
        Retangulo r1 = new Retangulo("1 1 5 1 5 2 1 2 ");
        assertEquals("Retangulo: [(0,4), (4,4), (4,5), (0,5)]",
                r1.translate(-1, 3).toString());
        Retangulo r2 = new Retangulo("1 1 2 1 2 2 1 2");
        assertEquals("Retangulo: [(4,6), (5,6), (5,7), (4,7)]",
                r2.translate(3, 5).toString());
    }

    @Test
    void newCentroid() {
        Retangulo r1 = new Retangulo("1 1 5 1 5 3 1 3");
        assertEquals("Retangulo: [(1,2), (5,2), (5,4), (1,4)]",
                r1.newCentroid(3, 3).toString());
        assertEquals("Retangulo: [(12,6), (16,6), (16,8), (12,8)]",
                r1.newCentroid(14, 7).toString());
    }

    @Test
    void getAllIntVertices() {
        Retangulo rectangle = new Retangulo("0 0 3 0 3 3 0 3");
        Ponto[] result = rectangle.getAllIntVertices();

        Ponto[] expected = {
                new Ponto(0, 0), new Ponto(1, 0), new Ponto(2, 0), new Ponto(3, 0),
                new Ponto(0, 1), new Ponto(1, 1), new Ponto(2, 1), new Ponto(3, 1),
                new Ponto(0, 2), new Ponto(1, 2), new Ponto(2, 2), new Ponto(3, 2),
                new Ponto(0, 3), new Ponto(1, 3), new Ponto(2, 3), new Ponto(3, 3)
        };

        assertEquals(Arrays.toString(expected), Arrays.toString(result));
    }

    @Test
    void getBoundaryIntVertices() {
        Retangulo rectangle = new Retangulo("0 0 3 0 3 3 0 3");
        Ponto[] result = rectangle.getBoundaryIntVertices();

        Ponto[] expected = {
                new Ponto(0, 0), new Ponto(1, 0), new Ponto(2, 0), new Ponto(3, 0),
                new Ponto(3, 0), new Ponto(3, 1), new Ponto(3, 2), new Ponto(3, 3),
                new Ponto(0, 3), new Ponto(1, 3), new Ponto(2, 3), new Ponto(3, 3),
                new Ponto(0, 0), new Ponto(0, 1), new Ponto(0, 2), new Ponto(0, 3)
        };

        assertEquals(Arrays.toString(expected), Arrays.toString(result));
    }
}