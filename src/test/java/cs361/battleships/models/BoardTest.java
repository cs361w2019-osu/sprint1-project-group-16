package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class BoardTest {

    @Test
    public void testInvalidPlacement() {
        Board board = new Board();
        assertFalse(board.placeShip(new Ship("MINESWEEPER"), 11, 'C', true));
    }
}

    @Test
    public void testShot() {
        Board board = new Board();
        assertFalse(board.attack(0, 'A'));
        assertFalse(board.attack(8, 'H'));
        assertFalse(board.attack(4, 'D'));

    }
}


