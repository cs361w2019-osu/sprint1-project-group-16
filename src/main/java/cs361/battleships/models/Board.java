package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

public class Board {


	// Each board object owns a private List of the ships placed on the board
	@JsonProperty private List<Ship> shipList;
	@JsonProperty private List<Result> resultList;



	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Board() {
		// TODO Implement
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
		// TODO Implement
		return false;
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	/**************************************************************************
	 *	Function: Attack action
	 *	Author: Douglas Wilson, wilsondo@oregonstate.edu
	 *	Description: Attacks a square with the given coordinates.
	 *	Param: The row x and the col y.
	 ***********************************************************************/
	public Result attack(int x, char y) {
		//TODO Implement
		Result stat = new Result();
		int colNum =Character.getNumericValue(y);

		List<Ship> tempShip = getShips();

		if(x>9 || colNum>74){
			stat.setResult(AtackStatus.INVALID);
			return stat;
		}
		else if(x < 0 || colNum < 65){
			stat.setResult(AtackStatus.INVALID);
			return stat;
		}
		//Run a for loop going through all the ships in the list, and then all the squares in the ship, checking if coordinates match.
		for (int i = 0; i < tempShip.size(); i++) {
			List<Square> targetSquares = tempShip.get(i).getOccupiedSquares();
			for(int j = 0; j < targetSquares.size(); j++){
				int row = targetSquares.get(j).getRow();
				int col = targetSquares.get(j).getColumn();
				if(row == x && col == y){
					stat.setResult(AtackStatus.HIT);
					stat.setShip(tempShip.get(i));
					stat.setLocation(targetSquares.get(j));
					return stat;
				}
			}
		}


			stat.setResult(AtackStatus.MISS);



		return stat;
	}

	/**************************************************************************
	 *	Function: getShips
	 *	Author: Douglas Wilson, wilsondo@oregonstate.edu
	 *	Description: Returns the private ships list
	 *	Param: NONE
	 ***********************************************************************/
	public List<Ship> getShips() {
		return shipList;
	}
	/**************************************************************************
	 *	Function: setShips
	 *	Author: Douglas Wilson, wilsondo@oregonstate.edu
	 *	Description: Sets the ships list as the parameter given
	 *	Param: Sets shipList as ship
	 ***********************************************************************/
	public void setShips(List<Ship> ships) {
		this.shipList = ships;
	}
	/**************************************************************************
	 *	Function: getAttacks
	 *	Author: Douglas Wilson, wilsondo@oregonstate.edu
	 *	Description: Returns List of Results
	 *	Param: NONE
	 ***********************************************************************/
	public List<Result> getAttacks() {
		return resultList;
	}
	/**************************************************************************
	 *	Function: setAttacks
	 *	Author: Douglas Wilson, wilsondo@oregonstate.edu
	 *	Description: Sets list of results
	 *	Param: list of results
	 ***********************************************************************/
	public void setAttacks(List<Result> attacks) {
		this.resultList = attacks;
	}
}
