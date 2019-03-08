package cs361.battleships.models;

import java.util.List;
import java.util.stream.Collectors;

public class Bomb extends Weapon {
    private Result result;

    Bomb(){
        super("bomb", "offensive", 101);
        this.unlocked = true;
    }

    @Override
    public boolean use(Ship _ship, Board board, Square s){

        var shipsAtLocation = board.getShips().stream().filter(ship -> ship.isAtLocation(s)).collect(Collectors.toList());

        if(board.getAttacks().size() > 0) {
            for (Result atk : board.getAttacks()) {
                if (atk.getLocation().equals(s)) {
                    if (shipsAtLocation.size() == 0) {
                        result = new Result(s, null, Status.INVALID);
                        return false;
                    }
                    result = new Result(s, shipsAtLocation.get(0), Status.INVALID);
                    return false;
                }
            }
        }

        if (shipsAtLocation.size() == 0) {
            result =  new Result(s, null, Status.MISS);
            return true;
        }

        var hitShip = shipsAtLocation.get(0);

        Square hitSquare = null;
        for(Square sq : hitShip.getOccupiedSquares()) {
            if (sq.getRow() == s.getRow() && sq.getColumn() == s.getColumn()) {
                hitSquare = sq;
            }
        }

        result = hitShip.attack(hitSquare);

        if (result.getStatus() == Status.SUNK) {
            if (board.getShips().stream().allMatch(Ship::isSunk)) {
                result.setStatus(Status.SURRENDER);
            }
        }

        return true;
    }

    public Result getTargetResult(){
        return this.result;
    }

    public List<Result> getSonarResult(){
        return null;
    }

    public void unlock(){}
}
