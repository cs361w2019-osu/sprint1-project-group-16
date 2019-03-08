package controllers;

import com.google.inject.Singleton;
import cs361.battleships.models.*;
import cs361.battleships.models.Bomb;
import ninja.Context;
import ninja.Result;
import ninja.Results;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class ApplicationController {
    private Map<Integer, AttackGameAction> history;
    private int actionCnt;

    private void addAction(AttackGameAction g){
        this.history.put(this.actionCnt, g);
        actionCnt++;
    }

    private AttackGameAction getLastAction(){
        if(this.actionCnt >= 0){
            return this.history.get(this.actionCnt-1);
        }
        else{
            return null;
        }
    }

    public Result index() {
        return Results.html();
    }

    public Result newGame() {
        Game g = new Game();
        g.getBoard("player").init();
        g.getBoard("opponent").init();
        history = new HashMap<>();
        actionCnt = 0;
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
        addAction(g);

        boolean actionResult = false;
        var action = g.getActionType();

            switch(action){
                case "sonar":
                    actionResult = game.sonarPing(g.getActionRow(), g.getActionColumn());
                    break;
                case "attack":
                    actionResult = game.attack(g.getActionRow(), g.getActionColumn());
                    break;
                default:
            }
//        if(g.getActionType().equals("sonar")){
//            System.out.print("sonar\n");
//            actionResult = game.sonarPing(g.getActionRow(), g.getActionColumn());
//        }
//        else if(g.getActionType().equals("attack")){
//            System.out.print("attack\n");
//            actionResult = game.attack(g.getActionRow(), g.getActionColumn());
//        }

        if (actionResult) {
            return Results.json().render(game);
        } else {
            System.out.format("Bad request");
            return Results.badRequest();
        }
    }
}
