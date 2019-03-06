package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import static java.lang.Math.abs;

public class Board {

	@JsonProperty private List<Ship> ships;
	@JsonProperty private List<Result> attacks;
	@JsonProperty private List<SonarResult> sonars;
	@JsonProperty private List<SonarResult> sonarAmmo;


	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Board() {
		this.ships = new ArrayList<>();
		this.attacks = new ArrayList<>();
		this.sonars = new ArrayList<>();
		this.sonarAmmo = new ArrayList<>();
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
		if (ships.stream().anyMatch(s -> s.getKind().equals(ship.getKind()))) {
			return false;
		}

		ship.addSquares(y, x, isVertical);
//
		if (ships.stream().anyMatch(s -> s.overlaps(ship))) {
			return false;
		}
		if (ship.getOccupiedSquares().stream().anyMatch(s -> s.isOutOfBounds())) {
			return false;
		}
		ships.add(ship);

		return true;
	}

	public boolean sonarPing(int row, char column){

		// the player has used up all sonarAmmo
		if(sonarAmmo.size() == 2){ return false; }

		int y = row-1;
		int x = ((int)column)-65;


		// sonarPing is a 5x5 square centered at x,y
		for(int m = -2; m <= 2; m++){
			for(int i = x-(2-abs(m)); i <= x+(2-abs(m)); i++){
				int j = y + m;
				if( i >= 0 && j >= 0 &&  i < 10 && j < 10){

					boolean shipFound = false;
					SonarResult result =  new SonarResult();

					for(Ship ship : this.ships) {

						for(Square sq : ship.getOccupiedSquares()){

							if(sq.getColumn() == (char)(i + 65) && sq.getRow() == j+1){

								sq.reveal();
								result.setLocation(sq);
								result.setStatus(SonarStatus.SHIP);
								shipFound = true;
							}
						}
					}
					if(!shipFound){

						Square current = new Square(j+1, ((char)(i +65)));
						result.setLocation(current);
						result.setStatus(SonarStatus.EMPTY);
					}
					sonars.add(result);
				}
			}
		}
		return true;
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Result attack(int x, char y) {

		Result attackResult = attack(new Square(x, y));
		attacks.add(attackResult);
		return attackResult;
	}

	private Result attack(Square s) {
		var shipsAtLocation = ships.stream().filter(ship -> ship.isAtLocation(s)).collect(Collectors.toList());


		for(Result atk : attacks) {
			if (atk.getLocation().equals(s)) {
				if (shipsAtLocation.size() == 0) {
					return new Result(s, null, AtackStatus.INVALID);
				}
				return new Result(s, shipsAtLocation.get(0), AtackStatus.INVALID);
			}
		}

		if (shipsAtLocation.size() == 0) {

			return new Result(s, null, AtackStatus.MISS);
		}

		var hitShip = shipsAtLocation.get(0);

		Square hitSquare = null;
		for(Square sq : hitShip.getOccupiedSquares()) {
			if (sq.getRow() == s.getRow() && sq.getColumn() == s.getColumn()) {
				hitSquare = sq;
			}
		}

		var attackResult = hitShip.attack(hitSquare);

		if (attackResult.getResult() == AtackStatus.SUNK) {
			if (ships.stream().allMatch(Ship::isSunk)) {
				attackResult.setResult(AtackStatus.SURRENDER);
			}
		}
		return attackResult;
	}

	List<Ship> getShips() {
		return ships;
	}
}
