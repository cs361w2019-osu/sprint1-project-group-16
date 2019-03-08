package cs361.battleships.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.abs;

public class Sonar extends Weapon {
    private Result targetResult;
    private List<Result> sonarResults;

    public Sonar(){
        super("sonar", "science", 2);
        this.unlocked = false;
        sonarResults = new ArrayList<>();
    }

    public Result getTargetResult(){
        return this.targetResult;
    }

    public List<Result> getSonarResult(){
        return this.sonarResults;
    }

    public void unlock(){}

    @Override
    public boolean use(Ship _ship, Board board, Square s){

        if(this.getAmmo() == 0){ return false; }
        this.setAmmo(this.getAmmo()-1);

        int y = s.getRow()-1;
        int x = ((int)s.getColumn())-65;

        // sonarPing is a 5x5 square centered at x,y
            for(int m = -2; m <= 2; m++){
            for(int i = x-(2-abs(m)); i <= x+(2-abs(m)); i++){
                int j = y + m;
                if( i >= 0 && j >= 0 &&  i < 10 && j < 10){

                    boolean shipFound = false;
                    Result result =  new Result();

                    for(Ship ship : board.getShips()) {

                        for(Square sq : ship.getOccupiedSquares()){

                            if(sq.getColumn() == (char)(i + 65) && sq.getRow() == j+1){

                                sq.reveal();
                                result.setLocation(sq);
                                result.setStatus(Status.SHIP);
                                shipFound = true;
                            }
                        }
                    }

                    if(!shipFound){

                        Square current = new Square(j+1, ((char)(i +65)));
                        result.setLocation(current);
                        result.setStatus(Status.EMPTY);
                    }

                    if( j+1 == s.getRow() && ((char)i + 65) == s.getColumn()){
                        targetResult = result;
//                        sonarTarget.add(result);
                    }
                    sonarResults.add(result);
//                    sonars.add(result);
                }
            }
        }
            return true;
    }

}
