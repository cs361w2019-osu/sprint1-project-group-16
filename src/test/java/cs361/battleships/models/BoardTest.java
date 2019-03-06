package cs361.battleships.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BoardTest {

    private Board board;

    @Before
    public void setUp() {
        board = new Board();
    }

    @Test
    public void testInvalidPlacement() {
        assertFalse(board.placeShip(new Minesweeper(), 11, 'C', true));
    }

    @Test
    public void testPlaceMinesweeper() {
        assertTrue(board.placeShip(new Minesweeper(), 1, 'A', true));
    }

    @Test
    public void testAttackEmptySquare() {
        board.placeShip(new Minesweeper(), 1, 'A', true);
        Result result = board.attack(2, 'E');
        assertEquals(AtackStatus.MISS, result.getResult());
    }

    @Test
    public void testAttackShip() {
        Ship minesweeper = new Minesweeper();
        board.placeShip(minesweeper, 1, 'A', true);
        minesweeper = board.getShips().get(0);
        Result result = board.attack(1, 'A');
        assertEquals(AtackStatus.HIT, result.getResult());
        assertEquals(minesweeper, result.getShip());
    }

    @Test
    public void testAttackSameSquareMultipleTimes() {
        Ship minesweeper = new Minesweeper();
        board.placeShip(minesweeper, 1, 'A', true);
        board.attack(1, 'A');
        Result result = board.attack(1, 'A');
        assertEquals(AtackStatus.INVALID, result.getResult());
    }

    @Test
    public void testAttackSameEmptySquareMultipleTimes() {
        Result initialResult = board.attack(1, 'A');
        assertEquals(AtackStatus.MISS, initialResult.getResult());
        Result result = board.attack(1, 'A');
        assertEquals(AtackStatus.INVALID, result.getResult());
    }

    @Test
    public void testSurrender() {
        board.placeShip(new Minesweeper(), 1, 'A', true);
        board.attack(1, 'A');
        var result = board.attack(2, 'A');
        assertEquals(AtackStatus.SURRENDER, result.getResult());
    }

    @Test
    public void testPlaceMultipleShipsOfSameType() {
        assertTrue(board.placeShip(new Minesweeper(), 1, 'A', true));
        assertFalse(board.placeShip(new Minesweeper(), 5, 'D', true));

    }

    @Test
    public void testCantPlaceMoreThan3Ships() {
        assertTrue(board.placeShip(new Minesweeper(), 1, 'A', true));
        assertTrue(board.placeShip(new Battleship(), 5, 'D', true));
        assertTrue(board.placeShip(new Destroyer(), 6, 'A', false));
        assertFalse(board.placeShip(new Minesweeper(), 8, 'A', false));

    }

    @Test
    public void testHitCQSink() {
//        minesweeper.addCapQrts();
        board.placeShip(new Minesweeper(), 1, 'A', true);
        Ship minesweeper = board.getShips().get(0);
        minesweeper.addCapQrts();
        board.attack(1, 'A');
        assertTrue(minesweeper.isSunk());
    }
}
