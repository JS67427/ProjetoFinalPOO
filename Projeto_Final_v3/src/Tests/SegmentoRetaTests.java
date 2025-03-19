import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SegmentoRetaTests {
    private final SegmentoReta segReta = new SegmentoReta(new Ponto(45, 67), new Ponto(79, 73));

    @Test
    void getPonto1() {
        assertEquals("(45,67)", this.segReta.getPonto1().toString());
    }

    @Test
    void getPonto2() {
        assertEquals("(79,73)", this.segReta.getPonto2().toString());
    }

    @Test
    void intersects() {
        assertFalse(new SegmentoReta(new Ponto(1, 1), new Ponto(3, 3)).intersects
                (new SegmentoReta(new Ponto(2, 2), new Ponto(2, 4))));

        assertFalse(new SegmentoReta(new Ponto(7, 6), new Ponto(10, 4)).intersects
                (new SegmentoReta(new Ponto(7, 6), new Ponto(9, 6))));

        assertTrue(new SegmentoReta(new Ponto(1, 1), new Ponto(2, 2)).intersects
                (new SegmentoReta(new Ponto(2, 1), new Ponto(1, 2))));
    }

    @Test
    void testEquals() {
        assertFalse(new SegmentoReta(new Ponto(1, 1), new Ponto(3, 3)).equals
                (new SegmentoReta(new Ponto(2, 2), new Ponto(3, 3))));
        assertTrue(new SegmentoReta(new Ponto(2, 3), new Ponto(4, 5)).equals
                (new SegmentoReta(new Ponto(4, 5), new Ponto(2, 3))));
    }
}
