package dev.ccsio.qubic;

import java.util.List;

/**
 * The GameBoard class.
 */
public class GameBoard {
    int[][][] board;

    GameBoard() {
        board = new int[4][4][4];  // z, y, x
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

    private Boolean winningStraight(Coordinates coordinates) {
        // check x-axis
        int sum = 0;
        for (int i = 0; i < 4; i++) {
            sum += this.board[coordinates.z][coordinates.y][i];
        }
        if (Math.abs(sum) == 4) {
            return true;
        }

        // check y-axis
        sum = 0;
        for (int i = 0; i < 4; i++) {
            sum += this.board[coordinates.z][i][coordinates.x];
        }
        if (Math.abs(sum) == 4) {
            return true;
        }

        // check z-axis
        sum = 0;
        for (int i = 0; i < 4; i++) {
            sum += this.board[i][coordinates.y][coordinates.x];
        }
        return Math.abs(sum) == 4;
    }

    private Boolean winningDiagonal(Coordinates coordinates) {
        for (List<Coordinates> diagonals : DiagonalsRecord.getDiagonals(coordinates)) {
            if (winningCoordinateSet(diagonals)) {
                return true;
            }
        }
        return false;
    }

    private Boolean winningCoordinateSet(List<Coordinates> coordinateList) {
        int sum = 0;

        for (Coordinates c : coordinateList) {
            sum += this.board[c.z][c.y][c.x];
        }
        return Math.abs(sum) == 4;
    }

    /**
     * Checks whether the latest move created a winning-line.
     * @param coordinates newest added coordinates.
     * @return true/false.
     */
    public Boolean checkWinWithNewestCoordinate(Coordinates coordinates) {
        if (winningStraight(coordinates)) {
            return true;
        } else {
            return winningDiagonal(coordinates);
        }
    }
}