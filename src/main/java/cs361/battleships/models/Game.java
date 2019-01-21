package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Random;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;

import static cs361.battleships.models.AtackStatus.*;

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

        boolean opponentPlacedSuccessfully;
        do {
            // AI places random ships, so it might try and place overlapping ships
            // let it try until it gets it right
            opponentPlacedSuccessfully = opponentsBoard.placeShip(ship, randRow(), randCol(), randVertical());
        } while (!opponentPlacedSuccessfully);

        return true;
    }

    /*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
    public boolean attack(int x, char  y) {
        Result playerAttack = opponentsBoard.attack(x, y);
        if (playerAttack.getResult() == INVALID) {
            return false;
        }

        Result opponentAttackResult;
        do {
            // AI does random attacks, so it might attack the same spot twice
            // let it try until it gets it right
            opponentAttackResult = playersBoard.attack(randRow(), randCol());
        } while(opponentAttackResult.getResult() != INVALID);

        return true;
    }

    /*********************************************************************
     ** Function: randCol
     ** Description: Returns a column character, Gets a random number between 0-9, then checks an array of the alphabet
     * It returns the character based on the number.
     ** Parameters:  None
     ** Pre-
     Conditions: None
     ** Post-
     Conditions: None
     *********************************************************************/
    private char randCol() {
        Random rand = new Random();
        int randnum = rand.nextInt(9);
        String colAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char col = colAlphabet.charAt(randnum);
        return col;
    }
    /*********************************************************************
     ** Function: randRow
     ** Description: Returns a random number between 1 and 9
     ** Parameters:  None
     ** Pre-
     Conditions: None
     ** Post-
     Conditions: None
     *********************************************************************/
    private int randRow() {
        Random rand = new Random();
        int rownum = rand.nextInt(9);
        return rownum;
    }

    private boolean randVertical() {
        // TODO implement
        return false;
    }
}
