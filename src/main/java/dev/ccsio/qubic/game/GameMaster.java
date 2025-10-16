package dev.ccsio.qubic.game;

import dev.ccsio.qubic.types.Coordinates;


public class GameMaster {
    private static GameMaster INSTANCE;
    private boolean initialised = false;

    String gameMode;
    GameBoard gameBoard;
    int mark;
    OpponentAlgorithm opponentAlgorithm;
    public int winner;
    
    private GameMaster() {}

    public static GameMaster getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GameMaster();
        }
        return INSTANCE;
    }

    public void init(String gameMode, int difficulty) {
        if (!initialised) {
            gameMode = gameMode.toLowerCase();
            if (gameMode.toLowerCase() == "sp"  // sp: singe-player, tp: two-player
                    || gameMode.toLowerCase() == "tp") {
                this.gameMode = gameMode;
            } else {
                throw new IllegalArgumentException("gameMode has to either be 'sp' or 'tp'");
            }

            if (gameMode == "sp") {
                this.opponentAlgorithm = new OpponentAlgorithm(difficulty);
            }

            this.gameBoard = new GameBoard();
            this.mark = -1;
            this.winner = 0;

            initialised = true;
        }
    }

    public void handleInput(Coordinates input) {
        this.gameBoard.placeMark(input, mark);
        if (gameBoard.checkWinWithNewestCoordinate()) {
            this.winner = this.mark;
            // Call Win UI
            return;
        }

        this.mark *= -1;

        if (this.gameMode == "sp" && this.mark == 1) {
            this.gameBoard.placeMark(this.opponentAlgorithm.getMove(gameBoard), 1);
            if (gameBoard.checkWinWithNewestCoordinate()) {
                this.winner = this.mark;
                // Call Win UI
                return;
            }
            this.mark *= -1;
        }
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public int getCurrentPlayer() {
        return mark;
    }
}
