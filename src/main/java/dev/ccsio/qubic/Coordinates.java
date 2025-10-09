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

    // ensures that Coordinates with the same x, y, z are equal objects.
    @Override
    public boolean equals(Object o) {
        // same reference   
        if (this == o) {
            return true; 
        }

        // not same type 
        if (!(o instanceof Coordinates)) {
            return false;
        }

        Coordinates other = (Coordinates) o;  
        return x == other.x && y == other.y && z == other.z; // value equality  
    }
  
    @Override  
    public String toString() {  
        return "Coordinates(" + x + "," + y + "," + z + ")";  
    }
}
