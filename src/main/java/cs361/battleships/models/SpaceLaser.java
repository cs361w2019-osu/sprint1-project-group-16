package cs361.battleships.models;

import java.util.List;
import java.util.stream.Collectors;

public class SpaceLaser extends Weapon {
    private Result result;

    public SpaceLaser(){
        super("spacelaser", "offensive", 101);
        this.unlocked = false;
    }

    @Override
    public boolean use(Ship ship, Board board, Square s){
        if(!this.unlocked){
            result = new Result(s, ship, Status.INVALID);
            return false;
        }

        for(Square sq : ship.getOccupiedSquares()){
            if(sq.getRow() == s.getRow() && sq.getColumn() == s.getColumn()){
                if(sq.getHits() > 0){
                    result = new Result(s, ship, Status.INVALID);
                    return false;
                }
                else{
                    result = ship.attack(sq);
                    return true;
                }
            }
        }
        result = new Result(s, ship, Status.INVALID);
        return false;
    }

    public void unlock(){
        this.unlocked = true;
    }

    public Result getTargetResult(){
        return result;
    }

    public List<Result> getSonarResult(){
        return null;
    }
}
