package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Ship {

	@JsonProperty private List<Square> occupiedSquares;
	@JsonProperty private String shipType;
	@JsonProperty private int length;

	public Ship() {
		occupiedSquares = new ArrayList<>();
	}
	
	public Ship(String kind) {
		occupiedSquares = new ArrayList<>();
		this.shipType = kind.toLowerCase();


//		System.out.println("shipType: " + this.shipType );
		if(this.shipType.equals("minesweeper")) {
			this.length = 2;
//			System.out.println("shipType: " + shipType );
		}
		else if(this.shipType.equals("destroyer")) {
			this.length = 3;
		}
		else if(this.shipType.equals("battleship")) {
			this.length = 4;
		}
		//TODO implement
	}

	public boolean addSquares(int x, char y, boolean isVertical) {
		System.out.println("addSquares");
		System.out.println("this.length:" + this.length);
		/* col and row are the anchor coord */
		int col = x;
		char row = y;

		for (int i = 0; i < this.length; i++) {
			if(isVertical) {
				System.out.println("isVertical");
				this.occupiedSquares.add(new Square(col, row));
				row++;
			}
			else {
				this.occupiedSquares.add(new Square(col, row));
				col++;
			}

		}

		if(this.isOutOfBounds()) {
			System.out.println("isOutOfBounds");
			return false;
		}
		return true;



	}

	/* Returns true if the squares to be added to a ship object are out of bounds */
	private boolean isOutOfBounds() {
		int col;
		char row;
//		System.out.println("Made it this far");

		for (Square tmpSq : this.occupiedSquares) {

			col = tmpSq.getColumn();
			row = tmpSq.getRow();

			if (row > 74 || row < 65) {
				return true;
			}
			if (col > 10 || col < 1) {
				return true;
			}
		}
		return false;
	}

	public List<Square> getOccupiedSquares() {
		return occupiedSquares;
		//TODO implement
	}

	public String getShipType() {
		return this.shipType;
	}
}
