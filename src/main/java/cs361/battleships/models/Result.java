package cs361.battleships.models;

public class Result {
	private AtackStatus result;
	private Ship ship;
	private Square square;


	/*********************************************************************
	 ** Function: getResult
	 ** Description: Returns AtackStatus
	 ** Parameters: NONE
	 ** Dev: Douglas Wilson, Wilsondo@oregonstate.edu
	 *********************************************************************/
	public AtackStatus getResult() {
		return this.result;
	}
	/*********************************************************************
	 ** Function: setResult
	 ** Description: Sets atackStatus
	 ** Parameters: AtackStatus
	 ** Dev: Douglas Wilson, Wilsondo@oregonstate.edu
	 *********************************************************************/
	public void setResult(AtackStatus result) {
		this.result = result;
	}
	/*********************************************************************
	 ** Function: getShip
	 ** Description: Returns ship
	 ** Parameters: NONE
	 ** Dev: Douglas Wilson, Wilsondo@oregonstate.edu
	 *********************************************************************/
	public Ship getShip() {
		return this.ship;
	}
	/*********************************************************************
	 ** Function: setShip
	 ** Description: Sets ship
	 ** Parameters: Ship
	 ** Dev: Douglas Wilson, Wilsondo@oregonstate.edu
	 *********************************************************************/
	public void setShip(Ship ship) {
		this.ship = ship;
	}
	/*********************************************************************
	 ** Function: getLocation
	 ** Description: returns a square
	 ** Parameters: NONE
	 ** Dev: Douglas Wilson, Wilsondo@oregonstate.edu
	 *********************************************************************/
	public Square getLocation() {
		return this.square;
	}
	/*********************************************************************
	 ** Function: setLocation
	 ** Description: Sets the square (where the shot went)
	 ** Parameters: Square
	 ** Dev: Douglas Wilson, Wilsondo@oregonstate.edu
	 *********************************************************************/
	public void setLocation(Square square) {
		this.square = square;
	}
}
