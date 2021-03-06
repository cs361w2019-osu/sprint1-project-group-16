package cs361.battleships.models;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ShipTest {

    @Test
    public void testPlaceMinesweeperHorizontaly() {
        Ship minesweeper = new Minesweeper();
        minesweeper.addSquares('A', 1, false, false);
        List<Square> occupiedSquares = minesweeper.getOccupiedSquares();
        ArrayList<Object> expected = new ArrayList<>();
        expected.add(new Square(1, 'A'));
        expected.add(new Square(1, 'B'));
        assertEquals(expected, occupiedSquares);
    }

    @Test
    public void testPlaceMinesweeperVertically() {
        Ship minesweeper = new Minesweeper();
        minesweeper.addSquares('A', 1, true, false);
        List<Square> occupiedSquares = minesweeper.getOccupiedSquares();
        ArrayList<Object> expected = new ArrayList<>();
        expected.add(new Square(1, 'A'));
        expected.add(new Square(2, 'A'));
        assertEquals(expected, occupiedSquares);
    }

    @Test
    public void testPlaceDestroyerHorizontaly() {
        Ship minesweeper = new Destroyer();
        minesweeper.addSquares('A', 1, false, false);
        List<Square> occupiedSquares = minesweeper.getOccupiedSquares();
        ArrayList<Object> expected = new ArrayList<>();
        expected.add(new Square(1, 'A'));
        expected.add(new Square(1, 'B'));
        expected.add(new Square(1, 'C'));
        assertEquals(expected, occupiedSquares);
    }

    @Test
    public void testPlaceDestroyerVertically() {
        Ship minesweeper = new Destroyer();
        minesweeper.addSquares('A', 1, true, false);
        List<Square> occupiedSquares = minesweeper.getOccupiedSquares();
        ArrayList<Object> expected = new ArrayList<>();
        expected.add(new Square(1, 'A'));
        expected.add(new Square(2, 'A'));
        expected.add(new Square(3, 'A'));
        assertEquals(expected, occupiedSquares);
    }

    @Test
    public void testPlaceBattleshipHorizontaly() {
        Ship minesweeper = new Battleship();
        minesweeper.addSquares('A', 1, false, false);
        List<Square> occupiedSquares = minesweeper.getOccupiedSquares();
        ArrayList<Object> expected = new ArrayList<>();
        expected.add(new Square(1, 'A'));
        expected.add(new Square(1, 'B'));
        expected.add(new Square(1, 'C'));
        expected.add(new Square(1, 'D'));
        assertEquals(expected, occupiedSquares);
    }

    @Test
    public void testPlaceBattleshipVertically() {
        Ship minesweeper = new Battleship();
        minesweeper.addSquares('A', 1, true, false);
        List<Square> occupiedSquares = minesweeper.getOccupiedSquares();
        ArrayList<Object> expected = new ArrayList<>();
        expected.add(new Square(1, 'A'));
        expected.add(new Square(2, 'A'));
        expected.add(new Square(3, 'A'));
        expected.add(new Square(4, 'A'));
        assertEquals(expected, occupiedSquares);
    }

    @Test
    public void testShipOverlaps() {
        Ship minesweeper1 = new Minesweeper();
        minesweeper1.addSquares('A', 1, true, false);

        Ship minesweeper2 = new Minesweeper();
        minesweeper2.addSquares('A', 1, true, false);

        assertTrue(minesweeper1.overlaps(minesweeper2));
    }

    @Test
    public void testShipsDontOverlap() {
        Ship minesweeper1 = new Minesweeper();
        minesweeper1.addSquares('A', 1, true, false);

        Ship minesweeper2 = new Minesweeper();
        minesweeper2.addSquares('C', 2, true, false);

        assertFalse(minesweeper1.overlaps(minesweeper2));
    }

    @Test
    public void testIsAtLocation() {
        Ship minesweeper = new Battleship();
        minesweeper.addSquares('A', 1, true, false);

        assertTrue(minesweeper.isAtLocation(new Square(1, 'A')));
        assertTrue(minesweeper.isAtLocation(new Square(2, 'A')));
    }

    @Test
    public void testHit() {
        Ship minesweeper = new Battleship();
        minesweeper.addSquares('A', 1, true, false);
        List<Square> squares = minesweeper.getOccupiedSquares();
        Result result = minesweeper.attack(squares.get(0));
        assertEquals(Status.HIT, result.getStatus());
        assertEquals(minesweeper, result.getShip());
        assertEquals(new Square(1, 'A'), result.getLocation());
    }

    @Test
    public void testSink() {
        Ship minesweeper = new Minesweeper();
        minesweeper.addSquares('A', 1, true, false);

        List<Square> squares = minesweeper.getOccupiedSquares();
        minesweeper.attack(squares.get(0));
        Result result = minesweeper.attack(squares.get(1));

        assertEquals(Status.SUNK, result.getStatus());
        assertEquals(minesweeper, result.getShip());
        assertEquals(new Square(2, 'A'), result.getLocation());
    }

    @Test
    public void testOverlapsBug() {
        Ship minesweeper = new Minesweeper();
        Ship destroyer = new Destroyer();
        minesweeper.addSquares('C', 5, false, false);
        destroyer.addSquares('C', 5, false, false);
        assertTrue(minesweeper.overlaps(destroyer));
    }

    @Test
    public void testAttackSameSquareTwice() {
        Ship minesweeper = new Minesweeper();
        minesweeper.addSquares('A', 1, true, false);
        List<Square> squares = minesweeper.getOccupiedSquares();
        var result = minesweeper.attack(squares.get(0));
        assertEquals(Status.HIT, result.getStatus());
        result = minesweeper.attack(squares.get(0));
        assertEquals(Status.INVALID, result.getStatus());
    }

    @Test
    public void testEquals() {
        Ship minesweeper1 = new Minesweeper();
        minesweeper1.addSquares('A', 1, true, false);
        Ship minesweeper2 = new Minesweeper();
        minesweeper2.addSquares('A', 1, true, false);
        assertEquals(minesweeper1, minesweeper2);
        assertEquals(minesweeper1.hashCode(), minesweeper2.hashCode());
    }

    @Test
    public void testMinesweeperHasCaptainsQuarters() {
        Ship minesweeper1 = new Minesweeper();
        minesweeper1.addSquares('A', 1, true, true);
        minesweeper1.setCapQrts(true);
        assertTrue(minesweeper1.hasCQ());
    }

    @Test
    public void testDestroyerHasCaptainsQuarters() {
        Ship minesweeper1 = new Destroyer();
        minesweeper1.addSquares('A', 1, true, true);
        minesweeper1.setCapQrts(true);
        assertTrue(minesweeper1.hasCQ());
    }

    @Test
    public void testBattleshipHasCaptainsQuarters() {
        Ship minesweeper1 = new Battleship();
        minesweeper1.addSquares('A', 1, true, true);
        minesweeper1.setCapQrts(true);
        assertTrue(minesweeper1.hasCQ());
    }

    @Test
    public void testMinesweeperCQHitSink() {
        Ship minesweeper1 = new Minesweeper();
        minesweeper1.addSquares('A', 1, true, false);
        minesweeper1.setCapQrts(true);
        List<Square> squares = minesweeper1.getOccupiedSquares();
        Square cq = null;
        for(Square sq : squares){
            if(sq.isCQ()){
                cq = new Square(sq);
                break;
            }
        }
        minesweeper1.attack(cq);
        assertTrue(minesweeper1.isSunk());
    }

    @Test
    public void testDestroyerCQHitOnceNotSunk() {
        Ship minesweeper1 = new Destroyer();
        minesweeper1.addSquares('A', 1, true, true);
        minesweeper1.setCapQrts(true);
        List<Square> squares = minesweeper1.getOccupiedSquares();
        Square cq = null;
        for(Square sq : squares){
            if(sq.isCQ()){
                cq = new Square(sq);
                break;
            }
        }
        minesweeper1.attack(cq);
        assertTrue(!minesweeper1.isSunk());
    }

    @Test
    public void testDestroyerCQHitSink() {
        Ship minesweeper1 = new Destroyer();
        minesweeper1.addSquares('A', 1, true, true);
        minesweeper1.setCapQrts(true);
        List<Square> squares = minesweeper1.getOccupiedSquares();
        Square cq = null;
        for(Square sq : squares){
            if(sq.isCQ()){
                cq = new Square(sq);
                break;
            }
        }
        minesweeper1.attack(cq);
        minesweeper1.attack(cq);
        assertTrue(minesweeper1.isSunk());
    }

    @Test
    public void testBattleshipCQHitOnceNotSunk() {
        Ship minesweeper1 = new Battleship();
        minesweeper1.addSquares('A', 1, true, true);
        minesweeper1.setCapQrts(true);
        List<Square> squares = minesweeper1.getOccupiedSquares();
        Square cq = null;
        for(Square sq : squares){
            if(sq.isCQ()){
                cq = new Square(sq);
                break;
            }
        }
        minesweeper1.attack(cq);
        assertTrue(!minesweeper1.isSunk());
    }

    @Test
    public void testBattleshipCQHitSink() {
        Ship minesweeper1 = new Battleship();
        minesweeper1.addSquares('A', 1, true, true);
        minesweeper1.setCapQrts(true);
        List<Square> squares = minesweeper1.getOccupiedSquares();
        Square cq = null;
        for(Square sq : squares){
            if(sq.isCQ()){
                cq = new Square(sq);
                break;
            }
        }
        minesweeper1.attack(cq);
        minesweeper1.attack(cq);
        assertTrue(minesweeper1.isSunk());
    }

    @Test
    public void testSubmarineOverlapsBattleship(){
        Ship battle = new Battleship();
        Ship sub = new Submarine();
        battle.addSquares('A', 1, true, false);
        sub.addSquares('A', 1, false, false);
        assertTrue(battle.overlaps(sub));
    }


}
