package controllers;

import com.google.inject.Singleton;
import cs361.battleships.models.Game;
import cs361.battleships.models.Ship;
import cs361.battleships.models.ShipShop;
import ninja.Context;
import ninja.Result;
import ninja.Results;

@Singleton
public class ApplicationController {

    public Result index() {
        return Results.html();
    }

    public Result newGame() {
        Game g = new Game();
        return Results.json().render(g);
    }

    public Result placeShip(Context context, PlacementGameAction g) {
        Game game = g.getGame();
        ShipShop ss = new ShipShop();
        Ship ship = ss.makeShip(g.getShipType());
        boolean result = game.placeShip(ship, g.getActionRow(), g.getActionColumn(), g.isVertical());
        if (result) {
            return Results.json().render(game);
        } else {
            return Results.badRequest();
        }
    }

    public Result attack(Context context, AttackGameAction g) {
        Game game = g.getGame();
        boolean actionResult = false;

        if(g.getActionType().equals("sonar")){
            System.out.print("sonar\n");
            actionResult = game.sonarPing(g.getActionRow(), g.getActionColumn());
        }
        else if(g.getActionType().equals("attack")){
            System.out.print("attack\n");
            actionResult = game.attack(g.getActionRow(), g.getActionColumn());
        }

        if (actionResult) {
            return Results.json().render(game);
        } else {
            System.out.format("Bad request");
            return Results.badRequest();
        }
    }
}
