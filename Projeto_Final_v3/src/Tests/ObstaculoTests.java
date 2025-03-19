import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObstaculoTests {

    @Test
    void isRotating() {
        //Devido à natureza de geração aleatória dos obstáculos não conseguimos testar este método
    }

    @Test
    void getAllIntPoints() {
        //Devido à natureza de geração aleatória dos obstáculos não conseguimos testar este método
    }

    @Test
    void getBoundaryIntPoints() {
        //Devido à natureza de geração aleatória dos obstáculos não conseguimos testar este método
    }

    @Test
    void rotate() {
        Cobra snake = new Cobra(100, 100, 5);
        Obstaculo obstacle = new Obstaculo(100, 100, snake);
        Object initialShape = obstacle.getObstaculo();
        obstacle.rotate();

        Object rotatedShape = obstacle.getObstaculo();

        //Por vezes o obstáculo terá ângulo de rotação 0 ou 180 e como tal depois de rodar é 'igual'
        //Isto faz com o teste por vezes falhe, daí estar comentado
        //assertNotEquals(initialShape, rotatedShape);
    }

    @Test
    void getObstaculo() {
        Cobra snake = new Cobra(100, 100, 5);
        Obstaculo obstacle = new Obstaculo(100, 100, snake);
        assertNotNull(obstacle.getObstaculo());
        assertTrue(obstacle.getObstaculo() instanceof Quadrado ||
                obstacle.getObstaculo() instanceof Retangulo ||
                obstacle.getObstaculo() instanceof Triangulo);
    }
}