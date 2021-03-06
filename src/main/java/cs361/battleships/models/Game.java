package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Random;

public class Game {

    @JsonProperty private Board playersBoard = new Board();
    @JsonProperty private Board opponentsBoard = new Board();


    /*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
    public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
        boolean successful = playersBoard.placeShip(ship, x, y, isVertical);
        if (!successful)
            return false;

        ShipShop ss = new ShipShop();
        Ship oppShip = ss.makeShip(ship.getKind());
        boolean opponentPlacedSuccessfully;
        do {
            // AI places random ships, so it might try and addSquares overlapping ships
            // let it try until it gets it right
            oppShip.getOccupiedSquares().clear();
            opponentPlacedSuccessfully = opponentsBoard.placeShip(oppShip, randRow(), randCol(), randVertical());
        } while (!opponentPlacedSuccessfully);

        return true;
    }

    /*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
    public boolean attack(int x, char  y) {
        Result playerAttack = opponentsBoard.attack(x, y);
//        System.out.format("playerattack.getStatus(): %s", playerAttack.getStatus());
        if (playerAttack.getStatus() == Status.INVALID) {
            return false;
        }

        Result opponentAttackResult;
        do {
            // AI does random attacks, so it might attack the same spot twice
            // let it try until it gets it right
            opponentAttackResult = playersBoard.attack(randRow(), randCol());
        } while(opponentAttackResult.getStatus() == Status.INVALID);

        return true;
    }

    // perform a sonar action on the opponents board
    public boolean sonarPing(int x, char y){

        // make sure at least one ship is sunk
        boolean shipSunk = false;
        for(Ship ship : opponentsBoard.getShips()){
            if(ship.isSunk()){
                shipSunk = true;
            }
        }

        // if a ship has been sunk, call sonarping on the opponents board
        if(shipSunk){
            boolean result = opponentsBoard.sonarPing(x, y);
            if(!result){
                return false;
            }

            // continue with normal opponent attack move
            Result opponentAttackResult;
            do {
                // AI does random attacks, so it might attack the same spot twice
                // let it try until it gets it right
                opponentAttackResult = playersBoard.attack(randRow(), randCol());
            } while(opponentAttackResult.getStatus() == Status.INVALID);

            return true;
        }
        else{
            return false;

        }

    }

    private char randCol() {
        int random = new Random().nextInt(10);
        return (char) ('A' + random);
    }

    private int randRow() {
        return  new Random().nextInt(10) + 1;
    }

    private boolean randVertical() {
        return new Random().nextBoolean();
    }


//    used for GameTest.java
    public Board getBoard(String which){
        switch(which){
            case "player":
                return playersBoard;
            case "opponent":
                return opponentsBoard;
            default:
                return null;
        }
    }

    public boolean run(UserAction act){
        System.out.print("revieved action");
        return true;
    }
}
