package dev.ccsio.qubic.game;

import dev.ccsio.qubic.types.Coordinates;;

public class OpponentAlgorithm {
    int difficulty;

    public OpponentAlgorithm(int difficulty) {
        this.difficulty = difficulty;
    }

    public Coordinates getMove(GameBoard gameBoard) {
        switch (difficulty) {
            case 0:
                makeRandomMove();
                break;
            case 1:
                makeStraightsMove();
                break;
            case 2:
                makeTacticalMove();
                break;
            default:
                break;
        }
        
    }

    private Coordinates makeRandomMove() {
        return new Coordinates(0, 0, 0);
    }

    private Coordinates makeStraightsMove() {
        return new Coordinates(0, 0, 0);
    }

    private Coordinates makeTacticalMove() {
        return new Coordinates(0, 0, 0);
    }
    
}
