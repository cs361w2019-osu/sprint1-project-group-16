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

	/**************************************************************************
	*	Function: Ship Class Constructor
	*	Author: Daniel Green, greendan@oregonstate.edu
	*	Description: Constructs a Ship object, usually called by Board.placeShip()
	*	method.
	*	Param: A string containing the type of Ship to be constructed
	***********************************************************************/

	public Ship(String kind) {
		occupiedSquares = new ArrayList<>();
		this.shipType = kind.toLowerCase();

		if(this.shipType.equals("minesweeper")) {
			this.length = 2;
		}
		else if(this.shipType.equals("destroyer")) {
			this.length = 3;
		}
		else if(this.shipType.equals("battleship")) {
			this.length = 4;
		}
	}

	/**************************************************************************
	*	Function: Ship.addSquares(int, char, boolean)
	*	Author: Daniel Green, greendan@oregonstate.edu
	*	Description: Adds Square objects to Ship.occupiedSquares. Calls Square
	*	constructor and performs bounds checking on the input coords.
	*	Param: int x: column number (1-10), int y: row character (A-J)
	*	boolean isVertical: boolean condition of the ships verticalness.
	*	Return: boolean value indicating whether the sqaures could be added.
	***********************************************************************/

	public boolean addSquares(int x, char y, boolean isVertical) {

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

	/**************************************************************************
	*	Function: Ship.isOutOfBounds()
	*	Author: Daniel Green, greendan@oregonstate.edu
	*	Description: Checks the row and column value of each square object
	*	contained in Ship.occupiedSquares.
	*	Param:
	*	Return: Boolean value indicating whether any of the squares are out of bounds.
	***********************************************************************/
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

	/* Function: Getter for Ship.occupiedSquares */
	public List<Square> getOccupiedSquares() {
		return occupiedSquares;
		//TODO implement
	}

	/* Function: Getter for Ship.shipType */
	public String getShipType() {
		return this.shipType;
	}
}
