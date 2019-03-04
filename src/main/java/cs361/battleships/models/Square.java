package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("unused")
public class Square {

	@JsonProperty private int row;
	@JsonProperty private char column;
	@JsonProperty private boolean hit = false;
	@JsonProperty private boolean revealed = false;
	private int cqHits;
	private boolean isCQ;
	private int maxHits;

	public Square() {
	}

	public Square(int row, char column) {
		this.row = row;
		this.column = column;
		this.isCQ = false;
		this.cqHits = 0;
	}

	public boolean getCQ(){
		return this.isCQ;
	}

	public void setCQ(boolean bool){
		this.isCQ = bool;
	}

	public int getCQHits(){
		return this.cqHits;
	}

	public void setCQHits(int hits){
		this.cqHits = hits;
	}

	public char getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}

	public int getMaxHits(){
		return maxHits;
	}

	public void setMaxHits(int hits){
		maxHits = hits;
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

	public boolean isHit() {
		return hit;
	}

	public void hit() {

		hit = true;

	}

	public boolean isRevealed(){return revealed;}

	public void revealed(){
		revealed = true;
	}

	@Override
	public String toString() {
		return "(" + row + ", " + column + ')';
	}
}
