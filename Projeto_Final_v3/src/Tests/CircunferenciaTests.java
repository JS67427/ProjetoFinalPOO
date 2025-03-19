import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CircunferenciaTests {
    Circunferencia c1 = new Circunferencia(new Ponto(2, 2), 1);
    Circunferencia c2 = new Circunferencia(new Ponto(3, 5), 3);

    @Test
    void getCenter() {
        assertEquals("(2,2)", this.c1.getCenter().toString());
        assertEquals("(3,5)", this.c2.getCenter().toString());
    }

    @Test
    void getRadius() {
        assertEquals(this.c1.getRadius(), 1);
        assertEquals(this.c2.getRadius(), 3);
    }

    @Test
    void testToString() {
        assertEquals("Circunferencia de raio 1.0 e centro: (2,2)", this.c1.toString());
        assertEquals("Circunferencia de raio 3.0 e centro: (3,5)", this.c2.toString());
    }

    @Test
    void intersects() {
        assertTrue(this.c1.intersects(this.c2));
        assertFalse(this.c1.intersects(new Circunferencia(new Ponto(10, 10), 5)));
    }

    @Test
    void duplicated() {
        assertFalse(this.c1.duplicated(this.c2));
        assertTrue(this.c1.duplicated(new Circunferencia(new Ponto(2, 2), 1)));
    }

    @Test
    void rotate() {
        assertEquals("Circunferencia de raio 1.0 e centro: (0,2)",
                c1.rotate(90, new Ponto(1, 1)).toString());
        assertEquals("Circunferencia de raio 3.0 e centro: (5,3)",
                c2.rotate(180, new Ponto(4, 4)).toString());
    }

    @Test
    void translate() {
        assertEquals("Circunferencia de raio 1.0 e centro: (5,5)",
                c1.translate(3, 3).toString());
        assertEquals("Circunferencia de raio 3.0 e centro: (16,12)",
                c2.translate(13, 7).toString());
    }

    Circunferencia circ = new Circunferencia(new Ponto(4, 4), 1);

    @Test
    void getAllIntVertices() {

        Ponto[] resultArray = this.circ.getAllIntVertices();
        List<Ponto> resultList = new ArrayList<>(Arrays.asList(resultArray));

        List<Ponto> expectedList = Arrays.asList(
                new Ponto(3, 4), new Ponto(4, 3), new Ponto(4, 4),
                new Ponto(4, 5), new Ponto(5, 4)
        );

        assertEquals(expectedList.toString(), resultList.toString());
    }

    @Test
    void getBoundaryIntVertices() {
        Ponto[] resultArray = this.circ.getBoundaryIntVertices();
        List<Ponto> resultList = new ArrayList<>(Arrays.asList(resultArray));

        List<Ponto> expectedList = Arrays.asList(
                new Ponto(3, 4), new Ponto(4, 3),
                new Ponto(4, 5), new Ponto(5, 4)
        );

        assertEquals(expectedList.toString(), resultList.toString());
    }
}