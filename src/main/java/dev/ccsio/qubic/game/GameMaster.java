package dev.ccsio.qubic.game;

import dev.ccsio.qubic.Main;
import dev.ccsio.qubic.types.Coordinates;
import dev.ccsio.qubic.types.MoveHistory;
import dev.ccsio.qubic.ui.panels.InputPanel;
import dev.ccsio.qubic.ui.panels.gamehistory.HistoryViewer;

import java.util.List;

public class GameMaster {
    private static GameMaster INSTANCE;
    private boolean initialised = false;

    String gameMode;
    GameBoard gameBoard;
    public int mark;
    OpponentAlgorithm opponentAlgorithm;
    InputPanel inputPanel;
    public int winner;
    private String winnerText;
    LogGame logGame = new LogGame();
    int difficulty = -1;

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
            this.difficulty = difficulty;
            this.gameBoard = new GameBoard();
            if (Main.runByUser) {
                this.inputPanel = InputPanel.getInstance();
            }
            this.gameMode = "sp";
            this.mark = -1;
            this.winner = 0;
            this.winnerText = null;
            initialised = true;
            logGame.start();
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
            logGame.start();
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
                logGame.handleWin(winner, gameMode, this.difficulty, this.gameBoard);
                if (Main.runByUser) {
                    HistoryViewer.getInstance().showHistory(winnerText);
                }
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
        } else if (gameBoard.isFull()) {
            this.winner = 0;
            return "Draw";
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
            logGame.handleWin(winner, gameMode, this.difficulty, this.gameBoard);
            if (Main.runByUser) {
                HistoryViewer.getInstance().showHistory(winnerText);
            }
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

    public List<MoveHistory.PlayerMove> getMoveHistory() {
        return LinkedHistory.getMoveHistory(gameBoard).list();
    }
}
