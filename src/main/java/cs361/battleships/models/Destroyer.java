package cs361.battleships.models;

public class Destroyer extends Ship {

    public Destroyer() {
        super("DESTROYER");
        this.size = 3;
        this.underwater = false;
    }

    @Override
    public Status handleCQ(Square sq){
        sq.setHits(sq.getHits() +1);

        if(sq.getHits() == 1){
            return Status.CQHIT;
        }
        if(sq.getHits() == 2){
            this.sunk = true;
            return Status.SUNK;
        }

        return Status.MISS;
    }

    public void setUnderwater(boolean underwater){
        this.underwater = false;
    }
}