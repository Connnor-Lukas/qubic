package dev.ccsio.qubic.game.algorithms;

import dev.ccsio.qubic.types.Coordinates;
import java.util.List;

public class DefensiveMove extends AlgorithmBase {

    public DefensiveMove(int oaMark) {
        this.oaMark = oaMark;
    }

    public Coordinates chooseMove() {
        int sum;
        Coordinates lastEmptyCoordinates = new Coordinates(-1, -1, -1);
        for (List<Coordinates> line : opponentWinOptions.getWinningLinesWithCoordinate(opponentLastMove)) {
            sum = 0;
            for (Coordinates coordinates : line) {
                int val = gameBoard.getValueAt(coordinates);
                switch (whoIsThis(val)) {
                    case OPPONENT:
                        sum++;
                        break;
                    case NOBODY:
                        lastEmptyCoordinates = coordinates;
                        break;
                    default:
                        break;
                }
            }

            if (sum == 2) {
                // System.out.println(lastEmptyCoordinates);
                return lastEmptyCoordinates;
            }
        }


        if (lastEmptyCoordinates.getX() != -oaMark) {
            return lastEmptyCoordinates;
        } else {
            while (true) {
                Coordinates temp = Coordinates.random();
                if (gameBoard.getValueAt(temp) == 0) {
                    return temp;
                }
            }
        }
    }
}
