package dev.ccsio.qubic;

import java.util.Scanner;

/**
 * The games main class, used to build everything.
 */
public class Main {
    public static void main(String[] args) {
        GameBoard gameBoard = new GameBoard();
        Scanner scanner = new Scanner(System.in);

        int player = -1;

        while (true) {
            for (int i = 0; i < 10; i++) {
                System.out.println();
            }
            TerminalDebug.printBoard(gameBoard.board);
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            int z = scanner.nextInt();
            Coordinates coordinates = new Coordinates(x, y, z);
            if (gameBoard.canPlaceMark(coordinates, player)) {
                gameBoard.placeMark(coordinates, player);
                player = -player;
            }
        }
    }
}