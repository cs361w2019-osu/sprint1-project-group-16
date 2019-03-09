package cs361.battleships.models;

public class Submarine extends Ship {
    public Submarine(){
        super("SUBMARINE");
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

    @Override
    void addSquares(char col, int row, boolean isVertical, boolean addCQ) {
        for (int i = 0; i < size; i++) {
            if (isVertical) {
                occupiedSquares.add(new Square(row + i, col));
                if (i == 3) {
                    occupiedSquares.add(new Square(row + i, (char) (col + 1)));
                }
            } else {
                occupiedSquares.add(new Square(row, (char) (col + i)));
                if (i == 3) {
                    occupiedSquares.add(new Square(row + 1, (char) (col + i)));
                }
            }
        }
        if (addCQ) {
            occupiedSquares.get(size - 1).setCQ(true); //Sub has captains' quarters at end
        }
    }

    public void setUnderwater(boolean underwater){
        this.underwater = underwater;
    }
}
