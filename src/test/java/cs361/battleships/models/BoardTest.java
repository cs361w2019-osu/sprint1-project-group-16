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
//        board.init();
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
        assertEquals(Status.MISS, result.getStatus());
    }

    @Test
    public void testAttackShip() {
        Ship minesweeper = new Minesweeper();
        board.placeShip(minesweeper, 1, 'A', true);
        minesweeper = board.getShips().get(0);
        minesweeper.setCapQrts(false);
        Result result = board.attack(1, 'A');
        assertEquals(Status.HIT, result.getStatus());
        assertEquals(minesweeper, result.getShip());
    }

    @Test
    public void testAttackSameSquareMultipleTimes() {
        Ship minesweeper = new Minesweeper();
        board.placeShip(minesweeper, 1, 'A', true);
        board.attack(1, 'A');
        Result result = board.attack(1, 'A');
        assertEquals(Status.INVALID, result.getStatus());
    }

    @Test
    public void testAttackSameEmptySquareMultipleTimes() {
        Result initialResult = board.attack(1, 'A');
        assertEquals(Status.MISS, initialResult.getStatus());
        Result result = board.attack(1, 'A');
        assertEquals(Status.INVALID, result.getStatus());
    }

    @Test
    public void testSurrender() {
        assertTrue(board.placeShip(new Minesweeper(), 1, 'A', true));
        board.getShips().get(0).setCapQrts(false);
        var result = board.attack(1, 'A');
        result = board.attack(2, 'A');
        assertEquals(Status.SURRENDER, result.getStatus());
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
        minesweeper.setCapQrts(true);
        board.attack(1, 'A');
        assertTrue(minesweeper.isSunk());
    }

    @Test
    public void testCantUseMoreThanTwoSonarPing(){
        board.sonarPing(1, 'A');
        board.sonarPing(3, 'C');
        assertFalse(board.sonarPing(5, 'E'));
    }

    @Test
    public void testPingFindsShip(){
        Ship ship = new Battleship();
        board.placeShip(ship, 5, 'E', true);
        board.sonarPing(5, 'E');
        for(Result sr : board.getSonars()){
            if(sr.getStatus() == Status.SHIP){
                assertTrue(ship.isAtLocation(sr.getLocation()));
            }
        }
    }

    @Test
    public void testPingDoesntFindShip(){
        boolean foundShip = false;
        board.sonarPing(5, 'E');
        for(Result sr : board.getSonars()){
            if(sr.getStatus() == Status.SHIP){
                foundShip = true;
            }
        }
        assertFalse(foundShip);
    }

    @Test
    public void testSpaceLaserNotUnlocked(){
        Ship ship = new Battleship();
        board.placeShip(ship, 5, 'E', true);
        var result = board.spacelaser(5, 'E');
        assertEquals(result.getStatus(), Status.INVALID);
    }

    @Test
    public void testSpaceLaserAttack(){
        Ship ship1 = new Minesweeper();
        ship1.sunk = true;
        Ship ship2 = new Destroyer();
        board.placeShip(ship1, 5, 'E', true);
        board.placeShip(ship2, 1, 'A', false);
        var result = board.spacelaser(1, 'A');
        assertEquals(Status.HIT, result.getStatus());
    }

    @Test
    public void testSpaceLaserHitsUnderWaterShip(){
        Ship ship1 = new Minesweeper();
        ship1.sunk = true;
        Ship submarine = new Submarine();
        submarine.setUnderwater(true);

        board.placeShip(ship1, 5, 'E', true);
        board.placeShip(submarine, 1, 'A', false);

        var result = board.spacelaser(1, 'A');
        assertEquals(Status.HIT, result.getStatus());
    }

    @Test
    public void testSpaceLaserHitsSubAndShip(){
        Ship ship1 = new Minesweeper();
        ship1.sunk = true;
        Ship submarine = new Submarine();
        submarine.setUnderwater(true);

        board.placeShip(ship1, 5, 'E', true);
        board.placeShip(submarine, 5, 'E', true);
        assertTrue(ship1.overlaps(submarine));

        board.spacelaser(5, 'E');

    }


    @Test
    public void testSubmarineOverlapsShip(){
        Ship ship1 = new Battleship();
        Ship submarine = new Submarine();
        Ship sunkShip = new Minesweeper();

        submarine.setUnderwater(true);
        sunkShip.sunk = true;

        board.placeShip(sunkShip, 1, 'A', true);
        board.placeShip(ship1, 5, 'E', true);
        assertTrue(board.placeShip(submarine, 5, 'E', true));
        assertTrue(ship1.overlaps(submarine));
        var result = board.spacelaser(5, 'E');
        assertEquals(result.getStatus(), Status.HIT);

        boolean ship1Hit = false;
        boolean subHit = false;
        for(Square sq : ship1.getOccupiedSquares()){
            if(sq.getHits() > 0){ ship1Hit = true;}
        }
        for(Square sq : submarine.getOccupiedSquares()){
            if(sq.getHits() > 0){ subHit = true; }
        }

        assertTrue(ship1Hit);
        assertTrue(subHit);

    }
}
