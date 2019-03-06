package cs361.battleships.models;

public enum SonarStatus {

	/**
	 * The result if an attack results if there isn't a ship in the square
	 */
	SHIP,

	/**
	 * The result if an attack results if there is a ship in the square.
	 */
	EMPTY,

	/**
	 * The result if the coordinates given are invalid.
	 */
	INVALID,
}
