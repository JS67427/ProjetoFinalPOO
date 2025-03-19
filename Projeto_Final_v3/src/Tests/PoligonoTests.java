import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PoligonoTests {
    Poligono pol1 = new Poligono("4 5 5 8 5 8 7 5 7");
    Poligono pol2 = new Poligono("4 7 1 9 1 9 3 7 3");
    Poligono pol3 = new Poligono("4 5 5 8 5 8 7 5 7");
    Poligono pol4 = new Poligono("4 7 4 9 4 9 6 7 6");
    Poligono pol1Dup = new Poligono("4 8 7 5 7 5 5 8 5");

    @Test
    void getVertices() {
        assertEquals("[(5,5), (8,5), (8,7), (5,7)]", Arrays.toString(pol1.getVertices()));
        assertEquals("[(7,1), (9,1), (9,3), (7,3)]", Arrays.toString(pol2.getVertices()));
    }

    @Test
    void intersects() {
        assertFalse(pol1.intersects(pol2));
        assertTrue(pol3.intersects(pol4));
    }

    @Test
    void testToString() {
        assertEquals("Poligono de 4 vertices: [(5,5), (8,5), (8,7), (5,7)]", pol1.toString());
        assertEquals("Poligono de 4 vertices: [(7,1), (9,1), (9,3), (7,3)]", pol2.toString());
    }

    @Test
    void duplicated() {
        assertTrue(pol1.duplicated(pol1Dup));
        assertFalse(pol1.duplicated(pol2));
    }

    @Test
    void centroid() {
        assertEquals("(6,6)", pol1.centroid().toString());
        assertEquals("(8,2)", pol2.centroid().toString());
    }

    @Test
    void rotate() {
        Poligono p1 = new Poligono("4 1 1 3 1 3 5 1 5");
        assertEquals("Poligono de 4 vertices: [(4,2), (4,4), (0,4), (0,2)]",
                p1.rotate(90, null).toString());
        Poligono p2 = new Poligono("4 7 4 9 4 9 6 7 6");
        assertEquals("Poligono de 4 vertices: [(9,4), (9,6), (7,6), (7,4)]",
                p2.rotate(90, null).toString());
    }

    @Test
    void translate() {
        Poligono p1 = new Poligono("4 1 2 5 6 8 7 12 14");
        assertEquals("Poligono de 4 vertices: [(0,5), (4,9), (7,10), (11,17)]",
                p1.translate(-1, 3).toString());
        Poligono p2 = new Poligono("4 1 1 2 1 2 2 1 2");
        assertEquals("Poligono de 4 vertices: [(4,6), (5,6), (5,7), (4,7)]",
                p2.translate(3, 5).toString());
    }

    @Test
    void newCentroid() {
        Poligono p1 = new Poligono("4 1 1 5 1 5 3 1 3");
        assertEquals("Poligono de 4 vertices: [(1,2), (5,2), (5,4), (1,4)]",
                p1.newCentroid(3, 3).toString());
        Poligono p2 = new Poligono("3 1 0 3 0 2 3");
        assertEquals("Poligono de 3 vertices: [(1,2), (3,2), (2,5)]",
                p2.newCentroid(2, 3).toString());
    }
}