package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class ShotTest {

    @Test
    public void testShot() {
        Board board = new Board();
        board.placeShip(new Ship("MINESWEEPER"), 5, 'C', true);
        Result testRes = new Result();

        testRes = board.attack(0, 'A');
        if(testRes.getResult().INVALID == AtackStatus.INVALID)
            System.out.println("GOOD");
        else
            System.out.println("FAIL");

        testRes = board.attack(5, 'Z');
        if(testRes.getResult().INVALID == AtackStatus.INVALID)
            System.out.println("GOOD");
        else
            System.out.println("FAIL");

        testRes = board.attack(8, 'H');
        if(testRes.getResult().MISS == AtackStatus.MISS)
            System.out.println("GOOD");
        else
            System.out.println("FAIL");

        testRes = board.attack(2, 'A');
        if(testRes.getResult().MISS == AtackStatus.MISS)
            System.out.println("GOOD");
        else
            System.out.println("FAIL");

        testRes = board.attack(5, 'C');
        if(testRes.getResult().HIT == AtackStatus.HIT)
            System.out.println("GOOD");
        else
            System.out.println("FAIL");

    }
}





