package dev.ccsio.qubic.game.algorithms;

import dev.ccsio.qubic.types.Coordinates;
import java.util.HashSet;
import java.util.Random;

public class RandomMove extends AlgorithmBase {
    HashSet<Coordinates> availableCoordinates;

    public RandomMove(int oaMark) {
        this.oaMark = oaMark;

        availableCoordinates = new HashSet<>();
        // populate available Coordinates
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                for (int z = 0; z < 4; z++) {
                    availableCoordinates.add(new Coordinates(x, y, z));
                }
            }
        }
    }

    protected Coordinates chooseMove() {
        Coordinates instantWin = checkInstantWin();
        if (instantWin != null) {
            return instantWin;
        }

        Random randomGenerator = new Random();

        // get random Coordinates from Available Coordinates
        int stop = randomGenerator.nextInt(availableCoordinates.size());
        int i = 0;
        for (Coordinates coordinates : availableCoordinates) {
            if (i == stop) {
                updateWithNewCoordinates(coordinates);
                return coordinates;
            }
            i++;
        }
        return null;
    }

    @Override
    protected void updateWithNewCoordinates(Coordinates coordinates) {
        super.updateWithNewCoordinates(coordinates);
        availableCoordinates.remove(coordinates);
    }

    @Override
    protected void updateAfterOpponentsMove() {
        super.updateAfterOpponentsMove();
        availableCoordinates.remove(opponentLastMove);
    }
}
