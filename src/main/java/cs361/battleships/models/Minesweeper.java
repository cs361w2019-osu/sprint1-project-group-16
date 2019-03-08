package cs361.battleships.models;

public class Minesweeper extends Ship{

    public Minesweeper(){

        super("MINESWEEPER");
        this.size = 2;
        this.underwater = false;
    }

    @Override
    public Status handleCQ(Square sq){
        this.sunk = true;
        return Status.SUNK;
    }

    public void setUnderwater(boolean underwater){
        this.underwater = false;
    }
}
