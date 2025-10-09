package dev.ccsio.qubic;

import java.util.List;
import java.util.Scanner;

/**
 * A terminal implementation method to access the game used for debugging.
 */
public class TerminalDebug {
    /**
     * A method to print the gameboard in the console.
     * @param board - The gameboard which is a 3D int array.
     */
    public void printBoard(int[][][] board) {
        for (int z = 0; z < 4; z++) {
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

    /**
     * Creates a gameboard and loops through all diagonals, placing them on the board
     *  and checking whether they get checked. 
     */
    public void testDiagonalWinDetection() {
        Scanner scanner = new Scanner(System.in);

        
        List<List<Coordinates>> diagonals = DiagonalsRecord.Diagonals;

        for (List<Coordinates> list : diagonals) {
            GameBoard gameboard = new GameBoard();
            
            for (Coordinates coords : list) {
                gameboard.placeMark(coords, 1);
            }

            System.out.println(list);
            System.out.println(gameboard.checkWinWithNewestCoordinate(list.get(3)));
            System.out.println("\n");

            printBoard(gameboard.board);

            scanner.next();
            
        }
        scanner.close();
    }
    
}
