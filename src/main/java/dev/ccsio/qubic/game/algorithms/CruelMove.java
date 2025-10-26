package dev.ccsio.qubic.game.algorithms;

import dev.ccsio.qubic.types.Coordinates;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CruelMove extends AlgorithmBase {
    Map<List<Coordinates>, Integer> weightedWinningLines;

    public CruelMove(int oaMark) {
        this.oaMark = oaMark;
    }

    public Coordinates chooseMove() {
        Coordinates instantWin = checkInstantWin();
        if (instantWin != null) {
            return instantWin;
        }

        refreshWinningMap();

        Map<Coordinates, Integer> coordinateWeight = new HashMap<>();
        for (List<Coordinates> line : weightedWinningLines.keySet()) {
            for (Coordinates coordinates : line) {
                if (gameBoard.getValueAt(coordinates) != 0) continue;
                if (coordinateWeight.containsKey(coordinates)) {
                    coordinateWeight.put(coordinates, coordinateWeight.get(coordinates) + weightedWinningLines.get(line));
                } else {
                    coordinateWeight.put(coordinates, weightedWinningLines.get(line));
                }
            }
        }

        System.out.println("New Hashmap Refresh");
        for (List<Coordinates> line : weightedWinningLines.keySet()) {
            int lineWeight = weightedWinningLines.get(line);
            if (lineWeight == 0) continue;
            System.out.println("Line Weight : " + lineWeight);
            for (Coordinates coordinates : line) {
                System.out.println(coordinates + " -> " + coordinateWeight.get(coordinates));
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("Board at (3,1,0): " + gameBoard.getValueAt(new Coordinates(3,1,0)));
        System.out.println("Board at (3,1,1): " + gameBoard.getValueAt(new Coordinates(3,1,1)));
        System.out.println("Board at (3,1,2): " + gameBoard.getValueAt(new Coordinates(3,1,2)));
        System.out.println("Board at (3,1,3): " + gameBoard.getValueAt(new Coordinates(3,1,3)));
        System.out.println();

        int maxWeight = 0;
        List<Coordinates> highestWeightList = new ArrayList<>();
        for (Coordinates coordinate : coordinateWeight.keySet()) {
            int temp = coordinateWeight.get(coordinate);
            if (temp > maxWeight) {
                maxWeight = temp;
                highestWeightList = new ArrayList<>();
                highestWeightList.add(coordinate);
            } else if (temp == maxWeight) {
                highestWeightList.add(coordinate);
            }
        }

        Coordinates bestCoordinate = null;
        int maxFutureMovesBlock = 0;
        for (Coordinates coordinates : highestWeightList) {
            int temp = opponentWinOptions.countWinningLinesWithCoordinate(coordinates);
            if  (temp > maxFutureMovesBlock) {
                maxFutureMovesBlock = temp;
                bestCoordinate = coordinates;
            }
        }

        return bestCoordinate;
    }

    private void refreshWinningMap() {
        weightedWinningLines = new HashMap<>();
        for (List<Coordinates> line : opponentWinOptions.getWinningLines()) {
            int sum = 0;
            for (Coordinates coordinates : line) {
                sum += gameBoard.getValueAt(coordinates) * -oaMark;
            }
            if (sum == 0) {
                weightedWinningLines.put(line, 0);
            } else {
                weightedWinningLines.put(line, (int) Math.pow(4, sum));
            }
        }
    }
}
