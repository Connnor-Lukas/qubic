package dev.ccsio.qubic.game;

import dev.ccsio.qubic.types.Coordinates;
import dev.ccsio.qubic.types.MoveHistory;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class OpponentAlgorithm {
    static int max_difficulty = 0;
    int difficulty;
    GameBoard gameBoard;
    WinningLinesRecord selfWinOptions;
    WinningLinesRecord opponentWinOptions;
    HashSet<Coordinates> availableCoordinates;
    
    public OpponentAlgorithm(int difficulty) {
        if (difficulty <= max_difficulty) {
            this.difficulty = difficulty;
        } else {
            throw new IllegalArgumentException(
                "OpponentAlgorithm difficulty has to be " + max_difficulty + " or less.");
        }

        selfWinOptions = new WinningLinesRecord();
        opponentWinOptions = new WinningLinesRecord();
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

    public Coordinates getMove(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        updateAfterOpponentsMove();

        switch (difficulty) {
            case 0:
                return makeRandomMove();
            case 1:
                return makeStraightMove();
            case 2:
                return makeTacticalMove();
            default:
                break;
        }
        return null;
    }

    private Coordinates checkSelfWinInOne() {
        for (List<Coordinates> list : selfWinOptions.getWinningLines()) {
            int sum = 0;
            int emptyIdx = -1;
            for (int i = 0; i < 4; i++) {
                if (gameBoard.getValueAt(list.get(i)) == 1) {
                    sum += 1;
                } else {
                    emptyIdx = i;
                }
            }

            if (sum == 3) {
                updateWithNewCoordinates(list.get(emptyIdx));
                return list.get(emptyIdx);
            }
        }
        return null;
    }

    private Coordinates checkOpponentWinInOne() {
        for (List<Coordinates> list : opponentWinOptions.getWinningLines()) {
            int sum = 0;
            int emptyIdx = -1;
            for (int i = 0; i < 4; i++) {
                if (gameBoard.getValueAt(list.get(i)) == -1) {
                    sum += 1;
                } else {
                    emptyIdx = i;
                }
            }

            if (sum == 3) {
                updateWithNewCoordinates(list.get(emptyIdx));
                return list.get(emptyIdx);
            }
        }
        return null;
    }

    private void updateWithNewCoordinates(Coordinates coordinates) {
        opponentWinOptions.deleteLines(coordinates);
        availableCoordinates.remove(coordinates);
    }

    private void updateAfterOpponentsMove() {
        Coordinates coordinates = LinkedHistory.getMoveHistory(
            gameBoard).getLastMove().coordinates();

        selfWinOptions.deleteLines(coordinates);
        availableCoordinates.remove(coordinates);
    }

    private Coordinates makeRandomMove() {
        System.out.println(availableCoordinates.size());
        Random randomGenerator = new Random();

        Coordinates selfWin = checkSelfWinInOne();
        Coordinates opponentWin = checkOpponentWinInOne();

        if (selfWin != null) {
            return selfWin;
        } else if (opponentWin != null) {
            return opponentWin;
        } else {
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
        }
        return null;
    }

    private Coordinates makeStraightMove() {
        return new Coordinates(0, 0, 0);
        // blocks, and tries to make a straight, first move is random
    }

    private Coordinates makeTacticalMove() {
        return new Coordinates(0, 0, 0);
        // blocks, tries to predict player's next move, calculates first move
    }
    
}
