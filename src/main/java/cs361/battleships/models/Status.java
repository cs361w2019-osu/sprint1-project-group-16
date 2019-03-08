package cs361.battleships.models;

public enum Status {

        /**
         * The result if an attack results if there isn't a ship in the square
         */
        EMPTY,

        /**
         * The result if an attack results if there is a ship in the square.
         */
        SHIP,

        /**
         * The result if the coordinates given are invalid.
         */
        INVALID,

    /**
     * The result if an attack results in a miss.
     */
    MISS,

    /**
     * The result if an attack results in a hit on an enemy ship.
     */
    HIT,

    /**
     * THe result if an attack sinks the enemy ship
     */
    SUNK,

    /**
     *	The result if an attack sinks a ship by hitting captains quarters
     */
    CQHIT,

    /**
     * The results if an attack results in the defeat of the opponent (a
     * surrender).
     */
    SURRENDER,

}

