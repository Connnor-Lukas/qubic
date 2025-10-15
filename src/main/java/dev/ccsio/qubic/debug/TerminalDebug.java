package dev.ccsio.qubic.debug;

import dev.ccsio.qubic.types.Coordinates;
import dev.ccsio.qubic.game.GameBoard;
import dev.ccsio.qubic.game.GameMaster;

import java.util.Scanner;

/**
 * A terminal implementation method to access the game used for debugging.
 */
public class TerminalDebug {
    static Scanner scanner = new Scanner(System.in);
    /**
     * A method to print the gameboard in the console.
     * @param board - The gameboard which is a 3D int array.
     */
    public static void printBoard(GameBoard gb) {
        int[][][] board = gb.getBoard();
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
        int player = 0;

        GameMaster gameMaster = GameMaster.getInstance();
        gameMaster.init("sp", 0);
        while (gameMaster.winner == 0) {
            player = gameMaster.getCurrentPlayer();
            
            System.out.println("Current Player: " + player);
            System.out.println();
            printBoard(gameMaster.getGameBoard());

            int x = scanner.nextInt();
            int y = scanner.nextInt();
            int z = scanner.nextInt();
            Coordinates coordinates = new Coordinates(x, y, z);
            printSpaces();

            if (gameMaster.getGameBoard().canPlaceMark(coordinates, player)) {
                gameMaster.handleInput(coordinates);
                
            } else {
                System.out.println("Invalid Input!");
            }
        }

        printBoard(gameMaster.getGameBoard());
        System.out.println("Player " + player +  " won the game!");
    }

    private static void printSpaces() {
        for (int i = 0; i < 20; i++) {
            System.out.println();
        }
    }
}
