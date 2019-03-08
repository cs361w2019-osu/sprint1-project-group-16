package cs361.battleships.models;

import controllers.PlacementGameAction;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GameTest {

//    private PlacementGameAction g;
    private Game game;

    @Before
    public void setUp() {
        game = new Game();
    }

    @Test
    public void testPlaceShip(){
        ShipShop shipshop = new ShipShop();
        Ship ship = shipshop.makeShip("MINESWEEPER");
        assertTrue(game.placeShip(ship, 1, 'A', true));
    }

    @Test
    public void testValidAttack(){
        ShipShop shipshop = new ShipShop();
        Ship ship = shipshop.makeShip("MINESWEEPER");
        assertTrue(game.placeShip(ship, 1, 'A', true));
        assertTrue(game.attack(1, 'A'));
    }

    @Test
    public void testOverlapPlacement(){
        ShipShop shipshop = new ShipShop();
        Ship ship1 = shipshop.makeShip("MINESWEEPER");
        Ship ship2 = shipshop.makeShip("BATTLESHIP");
        assertTrue(game.placeShip(ship1, 1, 'A', true));
        assertFalse(game.placeShip(ship2, 1, 'A', false));
    }

    @Test
    public void testSonarPingNoSunkShip(){
        ShipShop shipshop = new ShipShop();
        Ship ship1 = shipshop.makeShip("MINESWEEPER");
        assertTrue(game.placeShip(ship1, 1, 'A', true));
        assertFalse(game.sonarPing(1, 'A'));
    }

    @Test
    public void testSonarPingFindsShip(){
        ShipShop shipshop = new ShipShop();
        Ship ship1 = shipshop.makeShip("MINESWEEPER");
        Ship ship2 = shipshop.makeShip("BATTLESHIP");
        Ship ship3 = shipshop.makeShip("DESTROYER");

        assertTrue(game.placeShip(ship1, 1, 'A', true));
        assertTrue(game.placeShip(ship2, 5, 'D', false));
        assertTrue(game.placeShip(ship3, 6, 'G', false));

        Board b = game.getBoard("opponent");
        b.getShips().get(0).sunk = true;
        assertTrue(game.sonarPing(5, 'D'));
        assertTrue(game.sonarPing(8, 'A'));

        Result result = null;
        for(Result s : b.getSonars()){
            if(s.getStatus() == Status.SHIP){
                result = s;
            }
        }
        assertEquals(result.getStatus(), Status.SHIP);
    }
}
