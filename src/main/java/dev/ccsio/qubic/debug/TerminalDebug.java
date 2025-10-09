package dev.ccsio.qubic.debug;

import dev.ccsio.qubic.types.Coordinates;
import dev.ccsio.qubic.game.GameBoard;

import java.util.Scanner;

/**
 * A terminal implementation method to access the game used for debugging.
 */
public class TerminalDebug {
    /**
     * A method to print the gameboard in the console.
     * @param board - The gameboard which is a 3D int array.
     */
    public static void printBoard(int[][][] board) {
        for (int z = 3; z >= 0; z--) {
            for (int y = 0; y < 4; y++) {
                for (int x = 0; x < 4; x++) {
                    int i = board[z][y][x];
                    if (i < 0) {
                        System.out.print(i + " ");
                    } else {
                        System.out.print(" " + i + " ");
                    }
                }
                System.out.println(); 
            }
            System.out.println();
        }
    }

    public static void playGame(GameBoard gameBoard) {
        Scanner scanner = new Scanner(System.in);

        int player = -1;

        while (true) {
            printSpaces();
            System.out.println("Current Player: " + player);
            System.out.println();
            TerminalDebug.printBoard(gameBoard.getBoard());
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            int z = scanner.nextInt();
            Coordinates coordinates = new Coordinates(x, y, z);
            if (gameBoard.canPlaceMark(coordinates, player)) {
                gameBoard.placeMark(coordinates, player);
                if (gameBoard.checkWinWithNewestCoordinate()) {
                    printSpaces();
                    System.out.println("Player " + player + " won the game!");
                    return;
                }
                player = -player;
            }
        }
    }

    private static void printSpaces() {
        for (int i = 0; i < 20; i++) {
            System.out.println();
        }
    }
}
