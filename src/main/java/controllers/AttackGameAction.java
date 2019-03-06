package controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs361.battleships.models.Game;

public class AttackGameAction {

    @JsonProperty private Game game;
    @JsonProperty private int x;
    @JsonProperty private char y;
    @JsonProperty private String type;

    Game getGame() {
        return game;
    }

    int getActionRow() {
        return x;
    }

    char getActionColumn() {
        return y;
    }

    String getActionType() { return type; }
}
