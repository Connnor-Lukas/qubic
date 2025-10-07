package dev.ccsio.qubic;

public class Main {
    public static void main(String[] args) {
        GameBoard gameBoard = new GameBoard();
        TerminalDebug terminal = new TerminalDebug();

        terminal.printBoard(gameBoard.board);
    }
}