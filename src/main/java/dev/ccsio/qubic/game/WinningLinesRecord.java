package dev.ccsio.qubic.game;

import java.nio.charset.CoderResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dev.ccsio.qubic.types.Coordinates;

public class WinningLinesRecord {
    List<List<Coordinates>> WinningLinesList;

    public WinningLinesRecord() {
        WinningLinesList = new ArrayList<>(List.of(
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
        ));

        addStraights();
    }

    private void addStraights() {
        // add straights
        List<Coordinates> list = new ArrayList<>();

        // z-y plane
        for (int z = 0; z < 4; z++) {
            for (int y = 0; y < 4; y++) {
                for (int x = 0; x < 4; x++) {
                    list.add(new Coordinates(x, y, z));
                }
                WinningLinesList.add(new ArrayList<Coordinates>(list));
                list.clear();
            }
        }

        // x-z plane
        for (int z = 0; z < 4; z++) {
            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {
                    list.add(new Coordinates(x, y, z));
                }
                WinningLinesList.add(new ArrayList<Coordinates>(list));
                list.clear();
            }
        }

        // x-y plane
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                for (int z = 0; z < 4; z++) {
                    list.add(new Coordinates(x, y, z));
                }
                WinningLinesList.add(new ArrayList<Coordinates>(list));
                list.clear();
            }
        }
    }

    public void deleteLines(Coordinates coordinate) {
        this.WinningLinesList.removeIf(line -> line.contains(coordinate));
    }
    
    public List<List<Coordinates>> getWinningLines() {
        return WinningLinesList;
    }

}
