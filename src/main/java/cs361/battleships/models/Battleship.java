package cs361.battleships.models;

public class Battleship extends Ship{

    public Battleship(){

        super("BATTLESHIP");
        this.size = 4;
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
