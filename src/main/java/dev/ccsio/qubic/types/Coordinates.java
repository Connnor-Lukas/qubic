package dev.ccsio.qubic.types;

import java.util.Random;

/**
 * A custom class for coordinates.
 */
public class Coordinates {
    static Random random = new Random();
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

    public static Coordinates random() {
        return new Coordinates(
            random.nextInt(0, 4),
            random.nextInt(0, 4),
            random.nextInt(0, 4)
        );
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    // ensures that Coordinates with the same x, y, z are equal objects.
    @Override
    public boolean equals(Object o) {
        // Check if same coordinate object
        if (this == o) {
            return true; 
        }

        // Check if object is a coordinate
        if (!(o instanceof Coordinates other)) {
            return false;
        }

        // Check coordinate values are equal
        return x == other.x && y == other.y && z == other.z; // value equality
    }
  
    @Override  
    public String toString() {  
        return "Coordinates(" + x + "," + y + "," + z + ")";  
    }

    @Override
    public int hashCode() {
        return x * 16 + y * 4 + z;
    }
}
