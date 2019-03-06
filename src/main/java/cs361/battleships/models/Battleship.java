package cs361.battleships.models;

public class Battleship extends Ship{

    public Battleship(){

        super("BATTLESHIP");
        this.size = 4;
    }

    @Override
    public AtackStatus handleCQ(Square sq){
        sq.setHits(sq.getHits() +1);

        if(sq.getHits() == 1){
            return AtackStatus.CQHIT;
        }
        if(sq.getHits() == 2){
            this.sunk = true;
            return AtackStatus.SUNK;
        }

        return AtackStatus.MISS;
    }
}
