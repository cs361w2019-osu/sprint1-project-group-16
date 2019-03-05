package cs361.battleships.models;

public class Destroyer extends Ship {

    public Destroyer() {
        super("DESTROYER");
    }

    @Override
    public AtackStatus handleCQ(Square sq){
        sq.setHits(sq.getHits() +1);

        if(sq.getHits() == 1){
            return AtackStatus.CQHIT;
        }
        if(sq.getHits() == 2){
            this.isSunk = true;
            return AtackStatus.SUNK;
        }

        return AtackStatus.MISS;
    }
}