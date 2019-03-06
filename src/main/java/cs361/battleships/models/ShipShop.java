package cs361.battleships.models;

public class ShipShop {

    public ShipShop(){}

    public Ship makeShip(String kind){
        switch (kind) {
            case "MINESWEEPER":
                return new Minesweeper();
            case "DESTROYER":
                return new Destroyer();
            case "BATTLESHIP":
                return new Battleship();
            default:
                return null;
        }
    }
}
