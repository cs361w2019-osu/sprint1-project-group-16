package cs361.battleships.models;

public class ShipShop {

    private Ship newShip;

    public ShipShop(){}

    public Ship makeShip(String kind){
        if(kind.equals("MINESWEEPER")){
            newShip = new Minesweeper();
        }

        else if(kind.equals("DESTROYER")){
            newShip = new Destroyer();
        }

        else if(kind.equals("BATTLESHIP")){
            newShip = new Battleship();
        }
        return newShip;
    }
}
