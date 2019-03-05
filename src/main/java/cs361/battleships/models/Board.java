package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Board {

	@JsonProperty private List<Ship> ships;
	@JsonProperty private List<Result> attacks;
//	@JsonProperty private List<Result> sonars;


	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Board() {
		this.ships = new ArrayList<>();
		this.attacks = new ArrayList<>();
//		sonars = new ArrayList<>();
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

//	public Result sonarPing(int x, char y){
//
//		Result sonarResult = sonarPing(new Square(x, y));
//		sonars.add(sonarResult);
//		return sonarResult;
//	}

//Should only see if there is a ship there or not, it will go through all of the squares in a higher class
//	private Result sonarPing(Square s){
//		if (sonars.stream().anyMatch(r -> r.getLocation().equals(s))) {
//			var sonarResult = new Result(s);
//			sonarResult.setSonarStatus(SonarStatus.INVALID);
//			return sonarResult;
//		}
//		var shipsAtLocation = ships.stream().filter(ship -> ship.isAtLocation(s)).collect(Collectors.toList());
//
//		if (shipsAtLocation.size() == 0) {
//			var sonarResult = new Result(s);
//			return sonarResult;
//		}
//
//		var scanShip = shipsAtLocation.get(0);
//		var sonarResult = scanShip.sonar(s.getRow(), s.getColumn());
//		sonarResult.getSonarResult();
//
//		//In this case the sonarResult should show that the square has a ship in it
//		return sonarResult;
//	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Result attack(int x, char y) {

		Result attackResult = attack(new Square(x, y));
		System.out.format("attackResult.getresult: %s\n", attackResult.getResult());
		attacks.add(attackResult);
		return attackResult;
	}

	private Result attack(Square s) {
//		if (attacks.stream().anyMatch(r -> r.getLocation().equals(s))) {
//			return new Result(s, null, AtackStatus.INVALID);
//		}
		var shipsAtLocation = ships.stream().filter(ship -> ship.isAtLocation(s)).collect(Collectors.toList());
		// send attack result = miss , default value of Result() constructor
//		System.out.format("hasCQ: %b", s.getCQ());


		if (shipsAtLocation.size() == 0) {
			return new Result(s, null, AtackStatus.MISS);
		}

//		var tmpShip = shipsAtLocation.get(0);
//		System.out.format("ship type hit: %s", tmpShip.getKind());


		var hitShip = shipsAtLocation.get(0);
		System.out.format("-ship at location: %s\n", hitShip.getKind());

		Square hitSquare = null;
		for(Square sq : hitShip.getOccupiedSquares()) {
			if (sq.getRow() == s.getRow() && sq.getColumn() == s.getColumn()) {
				hitSquare = sq;
			}
		}

		var attackResult = hitShip.attack(hitSquare);

		if (attackResult.getResult() == AtackStatus.SUNK) {
			if (ships.stream().allMatch(ship -> ship.isSunk())) {
				attackResult.setResult(AtackStatus.SURRENDER);
			}
		}
		return attackResult;
	}

	List<Ship> getShips() {
		return ships;
	}
}
