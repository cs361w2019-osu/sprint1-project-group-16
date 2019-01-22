package cs361.battleships.models;

@SuppressWarnings("unused")
public class Square {

	private char row ;
	private int column;

	public Square() {
	}

	public Square(int column, char row) {
		this.row = row;
		this.column = column;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}
	
	public char getRow() {
		return row;
	}

	public void setRow(char row) {
		this.row = row;
	}
}
