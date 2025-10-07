package dev.ccsio.qubic;

/**
 * A terminal implementation method to access the game used for debugging.
 */
public class TerminalDebug {
    /**
     * A method to print the gameboard in the console.
     * @param board - The gameboard which is a 3D int array.
     */
    public void printBoard(int[][][] board) {
        for (int[][] ints : board) {
            for (int[] anInt : ints) {
                for (int i : anInt) {
                    System.out.print(i + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}
