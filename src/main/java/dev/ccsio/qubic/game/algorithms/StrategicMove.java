package dev.ccsio.qubic.game.algorithms;

import dev.ccsio.qubic.game.GameBoard;
import dev.ccsio.qubic.game.WinningLinesRecord;
import dev.ccsio.qubic.types.Coordinates;
import dev.ccsio.qubic.types.GameStateNode;
import java.util.List;

public class StrategicMove extends AlgorithmBase {
    static WinningLinesRecord winningLines = new WinningLinesRecord();
    Coordinates bestMove;

    public StrategicMove(int oaMark) {
        this.oaMark = oaMark;
    }

    protected Coordinates chooseMove() {
        /* int val = */ minimax(
            new GameStateNode(gameBoard, opponentLastMove),
            3,
            Integer.MIN_VALUE,
            Integer.MAX_VALUE,
            true
        );

        // System.out.println("Minimax return val: " + val);
        // System.out.println("bestMove: " + bestMove);

        return bestMove;
    }

    private int minimax(
        GameStateNode gameState,
        int depth,
        int alpha,
        int beta,
        Boolean maximizingPlayer
    ) {

        // if (depth >= 2) {
        //     System.out.println("Node Eval: " + evaluateGameState(gameState.gameBoard) + " at depth: " + depth);
        // }

        if (depth == 0 || gameState.isWinningState()) {
            int eval = evaluateGameState(gameState.gameBoard);
            gameState.evalValue = eval;

            if (eval == 100000) {
                return eval + depth;
            } else if (eval == -100000) {
                return eval - depth;
            }

            return eval;
        }

        if (maximizingPlayer) {
            Coordinates bestMove = null;  //
            int maxEval = Integer.MIN_VALUE;
            for (Coordinates moveOption : gameState.gameBoard.availableCoordinates()) {
                GameBoard hypotheticalGameBoard = gameState.gameBoard.deepCopy();
                hypotheticalGameBoard.placeMark(moveOption, oaMark);  // maximizingPlayer

                int eval = minimax(
                        new GameStateNode(hypotheticalGameBoard, moveOption), depth - 1, alpha, beta, false);

                if (eval > maxEval) {
                    maxEval = eval;
                    bestMove = moveOption;
                }

                alpha = Math.max(alpha, eval);
                if (beta <= alpha) {
                    break;
                }

            }

            if (depth == 3) { // if root
                this.bestMove = bestMove;
            }
            return maxEval;

        } else {
            int minEval = Integer.MAX_VALUE;
            for (Coordinates moveOption : gameState.gameBoard.availableCoordinates()) {
                GameBoard hypotheticalGameBoard = gameState.gameBoard.deepCopy();
                hypotheticalGameBoard.placeMark(moveOption, -oaMark);  // not maximizingPlayer

                int eval = minimax(
                        new GameStateNode(hypotheticalGameBoard, moveOption), depth - 1, alpha, beta, true);
                minEval = Math.min(minEval, eval);

                beta = Math.min(beta, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return minEval;
        }

    }

    public int evaluateGameState(GameBoard gameBoard) {
        int playerScore = 0;
        int algorithmScore = 0;
        for (List<Coordinates> list : winningLines.getWinningLines()) {
            int sumPlayer = 0;
            int sumAlgorithm = 0;
            for (Coordinates coordinates : list) {
                int value = gameBoard.getValueAt(coordinates);
                if (value == -oaMark) {
                    sumPlayer++;
                } else if (value == oaMark) {
                    sumAlgorithm++;
                }
            }

            if (sumAlgorithm == 4) {
                return 100000;
            } else if (sumPlayer == 4) {
                return -100000;
            } else if (sumPlayer != 0 && sumAlgorithm == 0) {
                playerScore += (int) Math.pow(sumPlayer, 4);
            } else if (sumAlgorithm != 0 && sumPlayer == 0) {
                algorithmScore += (int) Math.pow(sumAlgorithm, 4);
            }
        }
        return algorithmScore - playerScore;
    }
}
