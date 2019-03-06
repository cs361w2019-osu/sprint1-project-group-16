package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("unused")
public class Square {

	@JsonProperty private int row;
	@JsonProperty private char column;
	@JsonProperty private boolean hit;
	@JsonProperty private boolean revealed;
	@JsonProperty private int hits;
	@JsonProperty private boolean isCQ;

	public Square() {
	}

	public Square(int row, char column) {
		this.row = row;
		this.column = column;
		this.hit = false;
		this.revealed = false;
		this.hits = 0;
		this.isCQ = false;
	}

	public Square(Square squareCopy) {
		this.row = squareCopy.row;
		this.column = squareCopy.column;
		this.hit  = squareCopy.hit;
		this.revealed = squareCopy.revealed;
		this.hits = squareCopy.hits;
		this.isCQ = squareCopy.isCQ;
	}

	public int getRow() {
		return row;
	}
	public char getColumn() {
		return column;
	}
	public boolean isHit() {
		return hit;
	}
	public void hit() { hit = true; }
	public boolean isRevealed(){return revealed;}
	public int getHits(){
		return this.hits;
	}
	public void setHits(int hits){
		this.hits = hits;
	}

	public void reveal(){
		revealed = true;
	}
	public boolean isCQ(){
		return this.isCQ;
	}
	public void setCQ(boolean cqStatus){
		this.isCQ = cqStatus;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof Square) {
			return ((Square) other).row == this.row && ((Square) other).column == this.column;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return 31 * row + column;
	}

	@JsonIgnore
	public boolean isOutOfBounds() {
		return row > 10 || row < 1 || column > 'J' || column < 'A';
	}
















	@Override
	public String toString() {
		return "(" + row + ", " + column + ')';
	}
}
