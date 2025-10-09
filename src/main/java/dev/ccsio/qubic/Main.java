package dev.ccsio.qubic;

/**
 * The games main class, used to build everything.
 */
public class Main {
    public static void main(String[] args) {
        GameBoard gameBoard = new GameBoard();
        
        gameBoard.placeMark(new Coordinates(1, 2, 3), -1);
        gameBoard.placeMark(new Coordinates(0, 3, 2), -1);
        gameBoard.placeMark(new Coordinates(3, 3, 3), 1);
        gameBoard.placeMark(new Coordinates(2, 2, 2), 1);
        gameBoard.placeMark(new Coordinates(1, 1, 1), 1);
        gameBoard.placeMark(new Coordinates(0, 0, 0), 1);

        gameBoard.checkWinWithNewestCoordinate(new Coordinates(0, 0, 0));

        TerminalDebug terminal = new TerminalDebug();
        terminal.printBoard(gameBoard.board);

        terminal.testDiagonalWinDetection();
        
    }
}