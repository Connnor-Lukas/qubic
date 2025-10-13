package dev.ccsio.qubic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Final class storing the coordinates of all possible diagonal winning-lines.
 * Stored as a List of Lists each with 4 Coordinates.
 * As well as a dictionary linking each coordinate to its diagonal.
 */
public final class DiagonalsRecord { 
    static List<List<Coordinates>> DiagonalsList;
    static Map<Coordinates, List<Integer>> DiagonalsMap;

    static {
        DiagonalsList = List.of(
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
        generateMap();
    }

    private static void generateMap() {
        DiagonalsMap = new HashMap<>();

        for (int diagonalIndex = 0; diagonalIndex < DiagonalsList.size(); diagonalIndex++) {
            List<Coordinates> diagonal = DiagonalsList.get(diagonalIndex);
            
            // Map each coordinate to its diagonal index
            for (Coordinates coord : diagonal) {
                List<Integer> coordinateIndexes = DiagonalsMap.get(coord);
                if (coordinateIndexes == null) {
                    coordinateIndexes = new ArrayList<>();
                }
                coordinateIndexes.add(diagonalIndex);
                DiagonalsMap.put(coord, coordinateIndexes);
            }
        }
    }

    public static List<List<Coordinates>> getDiagonals(Coordinates coordinate) {
        List<List<Coordinates>> result = new ArrayList<>();
        for (int index : DiagonalsMap.get(coordinate)) {
            result.add(DiagonalsList.get(index));
        }
        return result;
    }
}