package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.common.collect.Sets;
import com.mchange.v1.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, property="@class")
public abstract class Ship {

	@JsonProperty protected String kind;
	@JsonProperty protected List<Square> occupiedSquares;
	@JsonProperty protected int size;
	@JsonProperty protected boolean isSunk;

	public Ship() {
		this.occupiedSquares = new ArrayList<>();
	}
	
	public Ship(String kind) {
		this();
		this.kind = kind;
		this.isSunk = false;
		switch(kind) {
			case "MINESWEEPER":
				size = 2;
				break;
			case "DESTROYER":
				size = 3;
				break;
			case "BATTLESHIP":
				size = 4;
				break;
		}
	}

	public List<Square> getOccupiedSquares() {
		return occupiedSquares;
	}


	public void addSquares(char col, int row, boolean isVertical) {
		for (int i=0; i<size; i++) {
			if (isVertical) {
				occupiedSquares.add(new Square(row+i, col));
			} else {
				occupiedSquares.add(new Square(row, (char) (col + i)));
			}
		}

		occupiedSquares.get(size-2).setCQ(true);
	}

	public boolean overlaps(Ship other) {
		Set<Square> thisSquares = Set.copyOf(getOccupiedSquares());
		Set<Square> otherSquares = Set.copyOf(other.getOccupiedSquares());
		Sets.SetView<Square> intersection = Sets.intersection(thisSquares, otherSquares);
		return intersection.size() != 0;
	}

	public boolean isAtLocation(Square location) {
		return getOccupiedSquares().stream().anyMatch(s -> s.equals(location));
	}

	public String getKind() {
		return kind;
	}

	// function will be overridden by sub classes
	public abstract AtackStatus handleCQ(Square sq);

	//Same as attack method, but does things for sonar instead, basically if there is a ship there, it will return there is a ship there, the squares start out empty for sonar
//	public Result sonar(int x, char y){
//
//		var sonarLocation = new Square(x ,y);
//		var square = getOccupiedSquares().stream().filter(s -> s.equals(sonarLocation)).findFirst();
//		if (!square.isPresent()) {
//			return new Result(sonarLocation);
//		}
//
//		var sonarSquare = square.get();
//		//IF true, it is already revealed, if this doesn't work, just going to have to code to ignore it
//		/*
//		if (sonarSquare.isRevealed()) {
//			var result = new Result(sonarSquare);
//			result.setResult(SonarStatus.INVALID);
//			return result;
//		}
//		*/
//
//		sonarSquare.revealed();
//
//		var result = new Result(sonarSquare);
//		result.setSonarStatus(SonarStatus.FULL);
//
//		return result;
//
//	}

	/**
	 * Attacks square in ship
	 * @param hitSq to attack
	 * @return result of attack
	 */
	public Result attack(Square hitSq) {
		AtackStatus status; // status after attack action

		// if there is a ship at this squares location and it
		// is already sunk, status = MISS
		if(this.isSunk){
			status = AtackStatus.MISS;
		}
		// if the attacked square is a captiansQuarters call the subclass's
		// handleCQ function.
		else if (hitSq.getCQ()){
			System.out.print("is Captain Quarters\n");
			status = this.handleCQ(hitSq);
		}
		// If the square has already been hit and it wasn't a CQ square
		else if (hitSq.getHits() >=1) {
			status = AtackStatus.INVALID;
		}
		// else a non-CQ ship square occupies this space. Increment the squares'
		// hit count and set ship::isSunk to true. Iterate through all squares
		// occupied by the ship, if any have zero hits the ship is not yet sunk.
		else {
			hitSq.setHits(hitSq.getHits() + 1);
			isSunk = true;
			for (Square shipSquare : occupiedSquares){
				if (shipSquare.getHits() == 0){
					isSunk = false;
				}
			}

			if (isSunk){
				status = AtackStatus.SUNK;
			}
			else {
				status = AtackStatus.HIT;
			}
		}

		// make copy of attacked square and return a new Result from the attack.
		Square sq = new Square(hitSq);
		return new Result(sq, this, status);
	}

//		var square = getOccupiedSquares().stream().filter(s -> s.equals(attackedLocation)).findFirst();
//
//		if (!square.isPresent()) {
//			return new Result(attackedLocation);
//		}
//
//		var attackedSquare = square.get();
//		if (attackedSquare.isHit()) {
//			var result = new Result(attackedLocation);
//			result.setResult(AtackStatus.INVALID);
//			return result;
//		}
//
//		if (attackedSquare.getCQ()){
//			System.out.format("captains quarters hit");
//			attackedSquare.setCQHits(attackedSquare.getCQHits()+1);
//			if(attackedSquare.getCQHits() >= attackedSquare.getMaxHits()){
//				System.out.format("ship sunk due to captians quarters");
//				var result = new Result(attackedLocation);
//				result.setShip(this);
//				result.setResult(AtackStatus.CQSUNK);
//				return result;
//			}
//		}
//
//		attackedSquare.hit();
//
//		var result = new Result(attackedLocation);
//		result.setShip(this);
//		if (isSunk()) {
//			result.setResult(AtackStatus.SUNK);
//		} else {
//			result.setResult(AtackStatus.HIT);
//		}
//		return result;
//	}

	@JsonIgnore
	public boolean isSunk() {
		return getOccupiedSquares().stream().allMatch(s -> s.isHit());
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Ship)) {
			return false;
		}
		var otherShip = (Ship) other;

		return this.kind.equals(otherShip.kind)
				&& this.size == otherShip.size
				&& this.occupiedSquares.equals(otherShip.occupiedSquares);
	}

	@Override
	public int hashCode() {
		return 33 * kind.hashCode() + 23 * size + 17 * occupiedSquares.hashCode();
	}

	@Override
	public String toString() {
		return kind + occupiedSquares.toString();
	}
}
