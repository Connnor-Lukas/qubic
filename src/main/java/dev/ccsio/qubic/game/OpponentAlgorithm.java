package dev.ccsio.qubic.game;

import dev.ccsio.qubic.game.algorithms.*;
import dev.ccsio.qubic.types.Coordinates;

import java.util.*;

public class OpponentAlgorithm {
    static int max_difficulty = 3;
    AlgorithmBase algorithm;

    public OpponentAlgorithm(int difficulty, int oaMark) {
        if (difficulty > max_difficulty || difficulty < 0) {
            throw new IllegalArgumentException(
                "OpponentAlgorithm difficulty has to be " + max_difficulty + " or less, and greater than 0."
            );
        }

        switch(difficulty) {
            case 0:
                algorithm = new RandomMove(oaMark);
                break;
            case 1:
                algorithm = new DefensiveMove(oaMark);
                break;
            case 2:
                algorithm = new StrategicMove(oaMark);
                break;
            case 3:
                algorithm = new CruelMove(oaMark);
                break;
            default:
                break;
        }
    }

    public Coordinates getMove(GameBoard gameBoard) {
        return algorithm.getMove(gameBoard);
    }
}
