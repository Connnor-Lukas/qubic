package dev.ccsio.qubic;

import java.util.List;

/**
 * Final class storing the coordinates of all possible diagonal winning-lines. 
 * Stored as a List of Lists each with 4 Coordinates.
 */
public final class DiagonalsRecord { 
    static List<List<Coordinates>> Diagonals;

    static {
        Diagonals = List.of(
            // middle-diagonals
            List.of(
                new Coordinates(0, 0, 0), 
                new Coordinates(1, 1, 1), 
                new Coordinates(2, 2, 2), 
                new Coordinates(3, 3, 3)
                ),
            List.of(
                new Coordinates(3, 0, 0), 
                new Coordinates(2, 1, 1), 
                new Coordinates(1, 2, 2), 
                new Coordinates(0, 3, 3)
            ),
            List.of(
                new Coordinates(0, 3, 0),
                new Coordinates(1, 2, 1),
                new Coordinates(2, 1, 2),
                new Coordinates(3, 0, 3)
            ),
            List.of(
                new Coordinates(3, 3, 0), 
                new Coordinates(2, 2, 1), 
                new Coordinates(1, 1, 2), 
                new Coordinates(0, 0, 3)
            ),
            // y-axis 
            List.of(
                new Coordinates(0, 0, 0),
                new Coordinates(1, 0, 1),
                new Coordinates(2, 0, 2),
                new Coordinates(3, 0, 3)
            ),
            List.of(
                new Coordinates(0, 1, 0),
                new Coordinates(1, 1, 1),
                new Coordinates(2, 1, 2),
                new Coordinates(3, 1, 3)
            ),
            List.of(
                new Coordinates(0, 2, 0),
                new Coordinates(1, 2, 1),
                new Coordinates(2, 2, 2),
                new Coordinates(3, 2, 3)
            ),
            List.of(
                new Coordinates(0, 3, 0),
                new Coordinates(1, 3, 1),
                new Coordinates(2, 3, 2),
                new Coordinates(3, 3, 3)
            ),
            List.of(
                new Coordinates(0, 0, 3),
                new Coordinates(1, 0, 2),
                new Coordinates(2, 0, 1),
                new Coordinates(3, 0, 0)
            ),
            List.of(
                new Coordinates(0, 1, 3),
                new Coordinates(1, 1, 2),
                new Coordinates(2, 1, 1),
                new Coordinates(3, 1, 0)
            ),
            List.of(
                new Coordinates(0, 2, 3),
                new Coordinates(1, 2, 2),
                new Coordinates(2, 2, 1),
                new Coordinates(3, 2, 0)
            ),
            List.of(
                new Coordinates(0, 3, 3),
                new Coordinates(1, 3, 2),
                new Coordinates(2, 3, 1),
                new Coordinates(3, 3, 0)
            ),
            // x-axis 
            List.of(
                new Coordinates(0, 0, 0),
                new Coordinates(0, 1, 1),
                new Coordinates(0, 2, 2),
                new Coordinates(0, 3, 3)
            ),
            List.of(
                new Coordinates(1, 0, 0),
                new Coordinates(1, 1, 1),
                new Coordinates(1, 2, 2),
                new Coordinates(1, 3, 3)
            ),
            List.of(
                new Coordinates(2, 0, 0),
                new Coordinates(2, 1, 1),
                new Coordinates(2, 2, 2),
                new Coordinates(2, 3, 3)
            ),
            List.of(
                new Coordinates(3, 0, 0),
                new Coordinates(3, 1, 1),
                new Coordinates(3, 2, 2),
                new Coordinates(3, 3, 3)
            ),
            List.of(
                new Coordinates(0, 0, 3),
                new Coordinates(0, 1, 2),
                new Coordinates(0, 2, 1),
                new Coordinates(0, 3, 0)
            ),
            List.of(
                new Coordinates(1, 0, 3),
                new Coordinates(1, 1, 2),
                new Coordinates(1, 2, 1),
                new Coordinates(1, 3, 0)
            ),
            List.of(
                new Coordinates(2, 0, 3),
                new Coordinates(2, 1, 2),
                new Coordinates(2, 2, 1),
                new Coordinates(2, 3, 0)
            ),
            List.of(
                new Coordinates(3, 0, 3),
                new Coordinates(3, 1, 2),
                new Coordinates(3, 2, 1),
                new Coordinates(3, 3, 0)
            ),
            // z-axis 
            List.of(
                new Coordinates(0, 0, 0),
                new Coordinates(1, 1, 0),
                new Coordinates(2, 2, 0),
                new Coordinates(3, 3, 0)
            ),
            List.of(
                new Coordinates(0, 0, 1),
                new Coordinates(1, 1, 1),
                new Coordinates(2, 2, 1),
                new Coordinates(3, 3, 1)
            ),
            List.of(
                new Coordinates(0, 0, 2),
                new Coordinates(1, 1, 2),
                new Coordinates(2, 2, 2),
                new Coordinates(3, 3, 2)
            ),
            List.of(
                new Coordinates(0, 0, 3),
                new Coordinates(1, 1, 3),
                new Coordinates(2, 2, 3),
                new Coordinates(3, 3, 3)
            ),
            List.of(
                new Coordinates(3, 0, 0),
                new Coordinates(2, 1, 0),
                new Coordinates(1, 2, 0),
                new Coordinates(0, 3, 0)
            ),
            List.of(
                new Coordinates(3, 0, 1),
                new Coordinates(2, 1, 1),
                new Coordinates(1, 2, 1),
                new Coordinates(0, 3, 1)
            ),
            List.of(
                new Coordinates(3, 0, 2),
                new Coordinates(2, 1, 2),
                new Coordinates(1, 2, 2),
                new Coordinates(0, 3, 2)
            ),
            List.of(
                new Coordinates(3, 0, 3),
                new Coordinates(2, 1, 3),
                new Coordinates(1, 2, 3),
                new Coordinates(0, 3, 3)
            )
        );
    }

}