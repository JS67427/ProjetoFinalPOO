import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RetaTests {
    private final Reta reta = new Reta(new Ponto(45, 67), new Ponto(79, 73));

    @Test
    void isCollinear() {
        assertTrue(this.reta.isCollinear(new Ponto(45, 67)));
        assertTrue(this.reta.isCollinear(new Ponto(62, 70)));
        assertTrue(this.reta.isCollinear(new Ponto(79, 73)));
        assertFalse(this.reta.isCollinear(new Ponto(46, 70)));
    }

    @Test
    void getPonto1() {
        assertEquals("(45,67)", this.reta.getPonto1().toString());
    }

    @Test
    void getPonto2() {
        assertEquals("(79,73)", this.reta.getPonto2().toString());
    }
}
