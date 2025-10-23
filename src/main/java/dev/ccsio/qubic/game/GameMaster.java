package dev.ccsio.qubic.game;

import dev.ccsio.qubic.types.Coordinates;
import dev.ccsio.qubic.ui.panels.InputPanel;
import dev.ccsio.qubic.ui.panels.WinScreen;


public class GameMaster {
    private static GameMaster INSTANCE;
    private boolean initialised = false;

    String gameMode;
    GameBoard gameBoard;
    public int mark;
    OpponentAlgorithm opponentAlgorithm;
    Coordinates oaMove;
    InputPanel inputPanel;
    public int winner;
    private String winnerText;
    
    private GameMaster() {}

    public static GameMaster getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GameMaster();
        }
        return INSTANCE;
    }

    public void init(int difficulty) {
        if (!initialised) {
            this.opponentAlgorithm = new OpponentAlgorithm(difficulty, 1);
            this.gameBoard = new GameBoard();
            this.inputPanel = InputPanel.getInstance();
            this.gameMode = "sp";
            this.mark = -1;
            this.winner = 0;
            this.winnerText = null;

            initialised = true;
        }
    }

    public void init() {
        if (!initialised) {
            this.gameBoard = new GameBoard();
            this.gameMode = "tp";
            this.mark = -1;
            this.winner = 0;
            this.winnerText = null;
            initialised = true;
        }
    }

    public void reset() {
        initialised = false;
    }

    public Boolean handleInput(Coordinates input) {
        if (this.gameBoard.canPlaceMark(input, mark)) {
            this.gameBoard.placeMark(input, mark);
            // System.out.println(input + " -> " + mark);
            winnerText = checkWinner();
            if (winnerText != null) {
                WinScreen.getInstance().showWinScreen(winnerText);
            }
            this.mark *= -1;
            return true;
        }
        return false;
    }

    public String checkWinner() {
        if (gameBoard.checkWinWithNewestCoordinate()) {
            this.winner = this.mark;
            switch (gameMode + winner) {
                case "sp-1":
                    return "You Won";
                case "sp1":
                    return "The AI Won";
                case "tp-1":
                    return "Player 1 Won";
                case "tp1":
                    return "Player 2 Won";
                default:
                    return null;
            }
        }
        return null;
    }

    // runs on a background thread
    public Coordinates computeOAMove() {
        if (winner != 0) return null;
        if (this.gameMode.equals("sp") && this.mark == 1) {
            return opponentAlgorithm.getMove(gameBoard);
        }
        return null;
    }

    // runs on the EDT (UI thread)
    public void applyOAMove(Coordinates move) {
        if (move == null) return;

        gameBoard.placeMark(move, 1);
        inputPanel.updateOAMove(move);

        winnerText = checkWinner();
        if (winnerText != null) {
            WinScreen.getInstance().showWinScreen(winnerText);
        }

        this.mark *= -1;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public int getCurrentPlayer() {
        return mark;
    }

    public String getGameMode() {
        return gameMode;
    }
}
