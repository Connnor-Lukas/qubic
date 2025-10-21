package dev.ccsio.qubic.game;

import dev.ccsio.qubic.types.Coordinates;
import dev.ccsio.qubic.types.GameStateNode;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class OpponentAlgorithm {
    static int max_difficulty = 2;
    static WinningLinesRecord winningLines = new WinningLinesRecord();

    Coordinates bestMove;
    GameStateNode gameStates;
    int difficulty;
    GameBoard gameBoard;
    List<List<Coordinates>> activeSelfWinningLines;
    List<List<Coordinates>> activeOpponentWinningLines;

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

    /**
     * Removes opponent's latest move from available coordinates.
     */
    private void updateAfterOpponentsMove() {
        Coordinates coordinates = LinkedHistory.getMoveHistory(
            gameBoard).getLastMove().coordinates();

        selfWinOptions.deleteLines(coordinates);
        availableCoordinates.remove(coordinates);
    }

    private Coordinates makeRandomMove() {
        // System.out.println(availableCoordinates.size());
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
        Coordinates latestCoordinates = LinkedHistory.getMoveHistory(
                gameBoard).getLastMove().coordinates();

        int val = minimax(
            new GameStateNode(gameBoard, latestCoordinates), 
            3, Integer.MIN_VALUE, Integer.MAX_VALUE, true
            );
        // System.out.println("Minimax return val: " + val);

        // System.out.println("bestMove: " + bestMove);
        return bestMove;
    }

    private int minimax(
        GameStateNode gameState, int depth, int alpha, int beta, Boolean maximizingPlayer) {
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
            for (Coordinates moveOption : gameState.gameBoard.availableCoordinates())  {
                GameBoard hypotheticalGameBoard = gameState.gameBoard.deepCopy();
                hypotheticalGameBoard.placeMark(moveOption, 1);  // maximizingPlayer

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
            for (Coordinates moveOption : gameState.gameBoard.availableCoordinates())  {
                GameBoard hypotheticalGameBoard = gameState.gameBoard.deepCopy();
                hypotheticalGameBoard.placeMark(moveOption, -1);  // not maximizingPlayer

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
                if (value == -1) {
                    sumPlayer++;
                } else if (value == 1) {
                    sumAlgorithm++;
                }
            }

            if (sumAlgorithm == 4) {
                return 100000;
            } else if (sumPlayer == 4) {
                return -100000;
            } else if (sumPlayer != 0 && sumAlgorithm == 0) {
                playerScore += Math.pow(sumPlayer, 4);
            } else if (sumAlgorithm != 0 && sumPlayer == 0) {
                algorithmScore += Math.pow(sumAlgorithm, 4);
            }
        }
        return algorithmScore - playerScore;
    }
    
}
