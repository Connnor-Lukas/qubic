package dev.ccsio.qubic;

public class TerminalDebug {
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
