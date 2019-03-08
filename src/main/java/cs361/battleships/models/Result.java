package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Result {

	@JsonProperty protected Status status;
	@JsonProperty protected Square location;
	@JsonProperty protected Ship ship;

	@SuppressWarnings("unused")
	public Result() {
	}

	public Result(Square location, Ship ship, Status status) {
		this.status = status;
		this.location = location;
		this.ship = ship;
	}

	public Status getStatus(){
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Ship getShip() {
		return ship;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}

	public Square getLocation() {
		return location;
	}

	public void setLocation(Square location) {
		this.location = location;
	}
}
