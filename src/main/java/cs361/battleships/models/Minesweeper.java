package cs361.battleships.models;

public class Minesweeper extends Ship{

    public Minesweeper(){

        super("MINESWEEPER");
        this.size = 2;
    }

    @Override
    public AtackStatus handleCQ(Square sq){
        this.sunk = true;
        return AtackStatus.SUNK;
    }
}
