package dev.ccsio.qubic;

/**
 * A custom class for coordinates.
 */
public class Coordinates {
    int x;
    int y;
    int z;

    /**
     * The Constructor method.
     * @param x - The x-coordinate on the GameBoard.
     * @param y - The y-coordinate on the GameBoard.
     * @param z - THe z-coordinate on the GameBoard.
     */
    public Coordinates(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
