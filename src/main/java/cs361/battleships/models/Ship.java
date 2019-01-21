package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Ship {

	@JsonProperty private List<Square> occupiedSquares;
	@JsonProperty private String kind;
	@JsonProperty private int length;

	public Ship() {
		occupiedSquares = new ArrayList<>();
	}
	
	public Ship(String kind) {
		this.kind = kind;

		if(kind == "Minesweeper") {
			this.length = 2;
		}
		else if(kind == "Destroyer") {
			this.length = 3;
		}
		else if(kind == "Battleship") {
			this.length = 4;
		}
		//TODO implement
	}

	public void addSquares(char x, int y, boolean isVertical) {
		/* col and row are the anchor coord */
		int col = (int) x - 64; //convert xcoord char to an int
		int row = y;

		for (int i = 0; i < this.length; i++) {
			if(isVertical) {
				this.occupiedSquares.add(new Square(row, col));
				row++;
			}
			else {
				this.occupiedSquares.add(new Square(row, col));
				col++;
			}

		}

	}

	public List<Square> getOccupiedSquares() {
		return occupiedSquares;
		//TODO implement
		return null;
	}
}
