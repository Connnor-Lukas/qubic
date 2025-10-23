package dev.ccsio.qubic.game;

import dev.ccsio.qubic.types.Coordinates;
import dev.ccsio.qubic.types.GameStateNode;

import java.util.*;

public class OpponentAlgorithm {
    // VariableMark Implementation
    private final int oaMark;
    enum MarkIs {
        SELF, OPPONENT, NOBODY
    }

    MarkIs whoIsThis(int mark) {
        if (mark == oaMark) return MarkIs.SELF;
        if (mark == -oaMark) return MarkIs.OPPONENT;
        return MarkIs.NOBODY;
    }


    static int max_difficulty = 3;
    static WinningLinesRecord winningLines = new WinningLinesRecord();

    Coordinates bestMove;
    GameStateNode gameStates;
    int difficulty;
    GameBoard gameBoard;
    List<List<Coordinates>> activeSelfWinningLines;
    List<List<Coordinates>> activeOpponentWinningLines;

    // Cruel OA
    Map<List<Coordinates>, Integer> weightedWinningLines;

    WinningLinesRecord selfWinOptions;
    WinningLinesRecord opponentWinOptions;
    HashSet<Coordinates> availableCoordinates;
    
    public OpponentAlgorithm(int difficulty, int oaMark) {
        if (difficulty <= max_difficulty) {
            this.difficulty = difficulty;
        } else {
            throw new IllegalArgumentException(
                "OpponentAlgorithm difficulty has to be " + max_difficulty + " or less.");
        }

        this.oaMark = oaMark;

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
                return makeDefensiveMove();
            case 2:
                return makeTacticalMove();
            case 3:
                return makeCruelMove();
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
                if (gameBoard.getValueAt(list.get(i)) == oaMark) {
                    sum++;
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
                if (gameBoard.getValueAt(list.get(i)) == -oaMark) {
                    sum++;
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

    private Coordinates makeDefensiveMove() {
        Coordinates selfWin = checkSelfWinInOne();
        Coordinates opponentWin = checkOpponentWinInOne();
        Coordinates latestCoordinates = LinkedHistory.getMoveHistory(
            gameBoard).getLastMove().coordinates();

        if (selfWin != null) {
            return selfWin;
        } else if (opponentWin != null) {
            return opponentWin;
        }


        int sum = 0;
        Coordinates lastEmptyCoordinates = new Coordinates(-1, -1, -1);
        for (List<Coordinates> line : opponentWinOptions.getWinningLinesWithCoordinate(latestCoordinates)) {
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
                updateWithNewCoordinates(lastEmptyCoordinates);
                return lastEmptyCoordinates;
            }
        }

        
        if (lastEmptyCoordinates.getX() != -oaMark) {
            updateWithNewCoordinates(lastEmptyCoordinates);
            return lastEmptyCoordinates;
        } else {
            return makeRandomMove();
        }
        
    }

    private Coordinates makeCruelMove() {
        Coordinates selfWin = checkSelfWinInOne();
        Coordinates opponentWin = checkOpponentWinInOne();
        if (selfWin != null) {
            System.out.println("Self Win.");
            return selfWin;
        } else if (opponentWin != null) {
            System.out.println("Opponent Win.");
            return opponentWin;
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

        updateWithNewCoordinates(bestCoordinate);
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
            for (Coordinates moveOption : gameState.gameBoard.availableCoordinates())  {
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
                playerScore += Math.pow(sumPlayer, 4);
            } else if (sumAlgorithm != 0 && sumPlayer == 0) {
                algorithmScore += Math.pow(sumAlgorithm, 4);
            }
        }
        return algorithmScore - playerScore;
    }
    
}
