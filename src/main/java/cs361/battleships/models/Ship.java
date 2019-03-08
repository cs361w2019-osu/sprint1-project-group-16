package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, property="@class")
public abstract class Ship {

	@JsonProperty private List<Square> occupiedSquares;
	@JsonProperty private String kind;
	@JsonProperty protected int size;
	@JsonProperty boolean sunk;

	public Ship() { this.occupiedSquares = new ArrayList<>(); }
	
	public Ship(String kind) {
		this();
		this.kind = kind;
		this.sunk = false;
	}

	List<Square> getOccupiedSquares() {
		return occupiedSquares;
	}
	String getKind() {
		return this.kind;
	}
	boolean isSunk() { return this.sunk; }
	boolean hasCQ() {
		for (Square sq : this.getOccupiedSquares()) {
			if (sq.isCQ()) {
				return true;
			}
		}
		return false;
	}


	void addSquares(char col, int row, boolean isVertical, boolean addCQ) {
		for (int i=0; i<size; i++) {
			if (isVertical) {
				occupiedSquares.add(new Square(row+i, col));
			} else {
				occupiedSquares.add(new Square(row, (char) (col + i)));
			}
		}
		if(addCQ){
			occupiedSquares.get(size-2).setCQ(true);
		}

	}

	void setCapQrts(boolean cq){
		this.occupiedSquares.get(size-2).setCQ(cq);
	}

	boolean overlaps(Ship other) {
		if(other.getKind() == "SUBMARINE" || this.getKind() == "SUBMARINE") { // Submarines can be in the same square as other ships
			return false;
		}
		Set<Square> thisSquares = Set.copyOf(getOccupiedSquares());
		Set<Square> otherSquares = Set.copyOf(other.getOccupiedSquares());
		Sets.SetView<Square> intersection = Sets.intersection(thisSquares, otherSquares);
		return intersection.size() != 0;
	}

	boolean isAtLocation(Square location) {
		return getOccupiedSquares().stream().anyMatch(s -> s.equals(location));
	}



	// function will be overridden by sub classes
	public abstract AtackStatus handleCQ(Square sq);

	/**
	 * Attacks square in ship
	 * @param hitSq to attack
	 * @return result of attack
	 */
	public Result attack(Square hitSq) {
		AtackStatus status; // status after attack action

		// if there is a ship at this squares location and it
		// is already sunk, status = MISS
		if(this.sunk){
			status = AtackStatus.MISS;
		}
		// if the attacked square is a captiansQuarters call the subclass's
		// handleCQ function.
		else if (hitSq.isCQ()){
			status = this.handleCQ(hitSq);
		}
		// If the square has already been hit and it wasn't a CQ square
		else if (hitSq.getHits() >=1) {
			status = AtackStatus.INVALID;
		}
		// else a non-CQ ship square occupies this space. Increment the squares'
		// hit count and set ship::sunk to true. Iterate through all squares
		// occupied by the ship, if any have zero hits the ship is not yet sunk.
		else {
			hitSq.setHits(hitSq.getHits() + 1);
			this.sunk = true;
			for (Square shipSquare : this.getOccupiedSquares()){
				if (shipSquare.getHits() == 0){
					this.sunk = false;
				}
			}

			if (this.isSunk()){
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
//		if (attackedSquare.isCQ()){
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
//		if (sunk()) {
//			result.setResult(AtackStatus.SUNK);
//		} else {
//			result.setResult(AtackStatus.HIT);
//		}
//		return result;
//	}



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
