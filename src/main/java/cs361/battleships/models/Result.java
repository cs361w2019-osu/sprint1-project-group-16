package cs361.battleships.models;

public class Result {
	private AtackStatus result;
	private Ship ship;
	private Square square;

	/*********************************************************************
	 ** Function: Result constructor
	 ** Description: Constructs result Class
	 ** Parameters: Starting result, starting ship and starting square
	 ** Pre-
	 Conditions: None
	 ** Post-
	 Conditions: None
	 *********************************************************************/
/*
	public Result(AtackStatus startresult,Ship startship, Square startsquare ){
		this.result = startresult;
		this.ship = startship;
		this.square = startsquare;
	}
*/

	public AtackStatus getResult() {
		return this.result;
	}

	public void setResult(AtackStatus result) {
		this.result = result;
	}

	public Ship getShip() {
		return this.ship;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}

	public Square getLocation() {
		return this.square;
	}

	public void setLocation(Square square) {
		this.square = square;
	}
}
