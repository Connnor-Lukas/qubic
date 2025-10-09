package dev.ccsio.qubic;

/**
 * The GameBoard class.
 */
public class GameBoard {
    int[][][] board;

    GameBoard() {
        board = new int[4][4][4];
    }

    /**
     * A method to update the GameBoard.
     * @param coordinates - The coordinates on the board to be updated.
     * @param mark - The mark of the player that should go there.
     */
    public void placeMark(Coordinates coordinates, int mark) {
        int x = coordinates.x;
        int y = coordinates.y;
        int z = coordinates.z;
        if ((mark == -1 || mark == 1) && (board[z][y][x] == 0)) {
            board[z][y][x] = mark;
        }
    }
}