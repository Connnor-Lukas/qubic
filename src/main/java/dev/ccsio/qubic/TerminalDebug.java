package dev.ccsio.qubic;

public class TerminalDebug {
    public void printBoard(int[][][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                for (int k = 0; k < board[i][j].length; k++) {
                    System.out.print(board[i][j][k] + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}
