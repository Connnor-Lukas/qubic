package dev.ccsio.qubic.game;

import dev.ccsio.qubic.types.Coordinates;
import java.util.Random;

public class OpponentAlgorithm {
    static int max_difficulty = 0;
    int difficulty;
    GameBoard gameBoard;
    
    public OpponentAlgorithm(int difficulty) {
        if (difficulty <= max_difficulty) {
            this.difficulty = difficulty;
        } else {
            throw new IllegalArgumentException(
                "OpponentAlgorithm difficulty has to be " + max_difficulty + " or less.");
        }   
    }

    public Coordinates getMove(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        
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

    private Coordinates makeRandomMove() {
        Random randomCoordinateGenerator = new Random();
        int x;
        int y;
        int z;
        Coordinates coordinates;

        while (true) {
            x = randomCoordinateGenerator.nextInt(4);
            y = randomCoordinateGenerator.nextInt(4);
            z = randomCoordinateGenerator.nextInt(4);
            coordinates = new Coordinates(x, y, z);

            if (gameBoard.canPlaceMark(coordinates, 1)) {
                return coordinates;
            }
        }
        
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
