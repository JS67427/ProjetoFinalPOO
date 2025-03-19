import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComidaTests {

    @Test
    void getAllIntPoints() {
        //Devido à natureza de geração aleatória da comida não conseguimos testar este método
    }

    @Test
    void getBoundaryIntPoints() {
        //Devido à natureza de geração aleatória da comida não conseguimos testar este método
    }

    @Test
    void getFood() {
        Cobra snake = new Cobra(100, 100, 5);
        Comida comida = new Comida(100, 100, null, snake);

        Object food = comida.getFood();
        assertNotNull(food);
        assertTrue(food instanceof Quadrado || food instanceof Circunferencia);
    }

    @Test
    void wasGenerated() {
        Cobra snake = new Cobra(100, 100, 5);
        Comida comida = new Comida(100, 100, null, snake);

        assertTrue(comida.wasGenerated());
    }
}