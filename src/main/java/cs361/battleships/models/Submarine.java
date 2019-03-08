package cs361.battleships.models;

public class Submarine extends Ship{

    public Submarine(){

        super("SUBMARINE");
        this.size = 4; //Its size is really 5, but one not in a row
    }

    @Override
    public AtackStatus handleCQ(Square sq){
        sq.setHits(sq.getHits() +1);
        //Nothing to implement bomb immunity
        if(sq.getHits() == 1){
            return AtackStatus.CQHIT;
        }
        if(sq.getHits() == 2){
            this.sunk = true;
            return AtackStatus.SUNK;
        }

        return AtackStatus.MISS;
    }

    @Override
    void addSquares(char col, int row, boolean isVertical, boolean addCQ) {
        for (int i=0; i<size; i++) {
            if (isVertical) {
                occupiedSquares.add(new Square(row+i, col));
                if(i==3) {
                    occupiedSquares.add(new Square(row + i, col + 1));
                }
            } else {
                occupiedSquares.add(new Square(row, (char) (col + i)));
                if(i==3) {
                    occupiedSquares.add(new Square(row + 1), (char) (col + i));
                }
            }
        }
        if(addCQ){
            occupiedSquares.get(size-1).setCQ(true); //Sub has captains' quarters at end
        }

    }


}
