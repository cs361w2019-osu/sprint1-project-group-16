package cs361.battleships.models;

public class Minesweeper extends Ship{

    public Minesweeper(){
        super("MINESWEEPER");
    }

    @Override
    public AtackStatus handleCQ(Square sq){
        this.isSunk = true;
        return AtackStatus.SUNK;
    }
}
