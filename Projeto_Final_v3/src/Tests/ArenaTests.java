import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArenaTests {

    @Test
    void drawSnake() {
        //Devido à natureza de funcionamento da arena não conseguimos testar este método
    }

    @Test
    void drawFood() {
        //Devido à natureza de funcionamento da arena não conseguimos testar este método
    }

    @Test
    void drawEatenFood() {
        //Devido à natureza de funcionamento da arena não conseguimos testar este método
    }

    @Test
    void deleteEatenFood() {
        //Devido à natureza de funcionamento da arena não conseguimos testar este método
    }

    @Test
    void drawObstacle() {
        //Devido à natureza de funcionamento da arena não conseguimos testar este método
    }

    @Test
    void drawCollisions() {
        //Devido à natureza de funcionamento da arena não conseguimos testar este método
    }

    @Test
    void deleteObstacle() {
        //Devido à natureza de funcionamento da arena não conseguimos testar este método
    }

    @Test
    void printBoard() {
        Arena arena = new Arena(10, 10);
        String board = arena.printBoard();
        assertNotNull(board);
        assertTrue(board.contains("."));
    }

    @Test
    void getHeight() {
        Arena arena = new Arena(15, 10);
        assertEquals(15, arena.getHeight());
    }

    @Test
    void getWidth() {
        Arena arena = new Arena(15, 10);
        assertEquals(10, arena.getWidth());
    }
}