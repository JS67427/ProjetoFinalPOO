import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CobraTests {

    @Test
    void setDir() {
        Cobra cobra = new Cobra(100, 100, 5);

        // Valid directions
        assertTrue(cobra.setDir(90));
        assertTrue(cobra.setDir(180));
        cobra.grow();
        assertFalse(cobra.setDir(0));  // Should fail because it tries to reverse direction

        // Invalid direction
        Exception exception = assertThrows(IllegalArgumentException.class, () -> cobra.setDir(45));
        assertEquals("Direção inválida!", exception.getMessage());
    }

    @Test
    void move() {
        Cobra cobra = new Cobra(100, 100, 4);
        cobra.setDir(0);  // Moving right
        List<Ponto> was = cobra.getAllIntPoints();
        cobra.move();
        List<Ponto> is = cobra.getAllIntPoints();
        for (int i = 0; i < was.size(); i++)
            assertEquals(0, was.get(i).translate(cobra.getEdge() + 1, 0).dist(is.get(i)));
    }

    @Test
    void grow() {
        Cobra cobra = new Cobra(100, 100, 5);
        int initialSize = cobra.getAllIntPoints().size();
        cobra.grow();
        assertEquals(initialSize * 2, cobra.getAllIntPoints().size());
    }


    @Test
    void getAllIntPoints() {
        Cobra cobra = new Cobra(100, 100, 5);
        cobra.grow();
        List<Ponto> points = cobra.getAllIntPoints();
        assertEquals(72, points.size());
    }

    @Test
    void getBoundaryIntPoints() {
        Cobra cobra = new Cobra(100, 100, 5);
        cobra.grow();
        List<Ponto> points = cobra.getBoundaryIntPoints();
        assertEquals(48, points.size());
    }

    @Test
    void containsFood() {
        //Os construtores geram a cobra e a comida de maneira aleatoriamente
    }

    @Test
    void getEdge() {
        Cobra cobra = new Cobra(100, 100, 5);
        assertEquals(5, cobra.getEdge());
    }


    @Test
    void findIntersections() {
        Cobra cobra = new Cobra(100, 100, 5);
        //List<Obstaculo> obstacles = Arrays.asList(new Obstaculo(...));
        //List<Ponto> intersections = cobra.findIntersections(obstacles);
        //assertNotNull(intersections);
        //assertTrue(intersections.isEmpty());
    }
}