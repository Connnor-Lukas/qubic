package dev.ccsio.qubic.game;

import dev.ccsio.qubic.types.Coordinates;


public class GameMaster {
    private static GameMaster INSTANCE;
    private boolean initialised = false;

    String gameMode;
    GameBoard gameBoard;
    int mark;
    Boolean playersTurn;
    OpponentAlgorithm opponentAlgorithm;
    
    private GameMaster() {}

    public static GameMaster getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GameMaster();
        }
        return INSTANCE;
    }

    public void init(String gameMode, int difficulty) {
        if (!initialised) {
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
            this.playersTurn = true;

            initialised = true;
        }
    }

    public void handleInput(Coordinates input, Boolean isOA) {
        if (this.playersTurn) {
            this.gameBoard.placeMark(input, this.mark);
            if (gameBoard.checkWinWithNewestCoordinate()) {
                System.out.println("Player " + this.mark + "won the game");
                return;
            }

            this.mark *= -1;

            if (this.gameMode == "sp") {
                this.playersTurn = false;
                this.handleInput(this.opponentAlgorithm.getMove(gameBoard), false);
            }
        } 

        if (isOA) {
            gameBoard.placeMark(input, this.mark);
            this.playersTurn = true;
            this.mark *= -1;
        }
    }
}
