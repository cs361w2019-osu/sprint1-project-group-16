package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Ship {

	@JsonProperty private List<Square> occupiedSquares;
	@JsonProperty private String kind;
	@JsonProperty private int length;

	public Ship() {
		occupiedSquares = new ArrayList<>();
	}
	
	public Ship(String kind) {
		this.kind = kind;
		if(kind == "Minesweeper") {
			this.length = 2;
			for (int i = 0; i < this.length; i++) {
				this.occupiedSquares.add(new Square());
			}
		}
		else if(kind == "Destroyer") {
			this.length = 3;
			for(int i = 0; i < this.length; i++) {
				this.occupiedSquares.add(new Square());
			}
		}
		//TODO implement
	}

	private addSquares(int length) {
		this.length = length

	}

	public List<Square> getOccupiedSquares() {
		//TODO implement
		return null;
	}
}
