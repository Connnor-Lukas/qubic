package dev.ccsio.qubic;

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
}
