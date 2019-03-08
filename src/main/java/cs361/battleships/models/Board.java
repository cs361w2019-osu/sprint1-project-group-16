package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.stream.Collectors;
import static java.lang.Math.abs;

public class Board {

	@JsonProperty private List<Ship> ships;
	@JsonProperty private List<Result> attacks;
	@JsonProperty private List<Result> sonars;
	@JsonProperty private List<Result> sonarTarget;
	@JsonProperty private Map<String, Weapon> weapons;

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Board() {
		this.ships = new ArrayList<>();
		this.attacks = new ArrayList<>();
		this.sonars = new ArrayList<>();
		this.sonarTarget = new ArrayList<>();
		this.weapons = new HashMap<>();

		this.weapons.put("sonar", new Sonar());
		this.weapons.put("bomb", new Bomb());
		this.weapons.put("spacelaser", new SpaceLaser());
	}

	public void init(){

	}

	private void addToSonarList(List<Result> result){
		List<Result> tmp = new ArrayList<>();
		tmp.addAll(result);
		tmp.addAll(sonars);
		sonars = tmp;
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
		if (ships.stream().anyMatch(s -> s.getKind().equals(ship.getKind()))) {
			return false;
		}

		ship.addSquares(y, x, isVertical, true);

		if (ships.stream().anyMatch(s -> s.overlaps(ship))) {
			if(ship.underwater){
				ships.add(ship);
				return true;
			}
			return false;
		}
		if (ship.getOccupiedSquares().stream().anyMatch(Square::isOutOfBounds)) {
			return false;
		}
		ships.add(ship);

		return true;
	}

	public boolean sonarPing(int row, char column){
		var sonar = weapons.get("sonar");
		var sonarSuccess = sonar.use(null,this, new Square(row, column));

		if(!sonarSuccess){
			return false;
		}
		var targetResult = sonar.getTargetResult();
		sonarTarget.add(targetResult);

		var sonarResults = sonar.getSonarResult();
		addToSonarList(sonarResults);

		return true;
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Result attack(int x, char y) {
		var bomb = weapons.get("bomb");
		bomb.use(null,this, new Square(x, y));
		attacks.add(bomb.getTargetResult());
		return attacks.get(attacks.size()-1);
	}


	public Result spacelaser(int x, char y) {
		var spacelaser = weapons.get("spacelaser");

		for (Ship ship : this.getShips()) {
			if (ship.isSunk()) {
				spacelaser.unlock();
			}
		}

		for (Ship ship : this.getShips()) {
			if (ship.isAtLocation(new Square(x, y))) {
				spacelaser.use(ship, this, new Square(x, y));
				attacks.add(spacelaser.getTargetResult());
			}
		}

		return attacks.get(attacks.size()-1);
	}

	List<Ship> getShips() {
		return ships;
	}

	List<Result> getAttacks(){
		return this.attacks;
	}

	List<Result> getSonars(){
		return this.sonars;
	}
}
