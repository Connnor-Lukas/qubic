package dev.ccsio.qubic.game;

import dev.ccsio.qubic.types.Coordinates;
import dev.ccsio.qubic.types.MoveHistory;
import java.util.HashSet;
import java.util.List;

/**
 * The GameBoard class.
 */
public class GameBoard {
    int[][][] board;
    MoveHistory moveHistory = new MoveHistory();

    public GameBoard() {
        board = new int[4][4][4];  // z, y, x
        LinkedHistory.addMoveHistory(this, moveHistory);
    }

    public boolean canPlaceMark(Coordinates coordinates, int mark) {
        int x = coordinates.getX();
        int y = coordinates.getY();
        int z = coordinates.getZ();

        if ((x > 3)
            || (x < 0)
            || (y > 3)
            || (y < 0)
            || (z > 3)
            || (z < 0)) {
            return false;
        }

        return (mark == -1 || mark == 1) && (board[z][y][x] == 0);
    }

    /**
     * A method to update the GameBoard. Input Validation done by another method.
     * @param coordinates - The coordinates on the board to be updated.
     * @param mark - The mark of the player that should go there.
     */
    public void placeMark(Coordinates coordinates, int mark) {
        int x = coordinates.getX();
        int y = coordinates.getY();
        int z = coordinates.getZ();

        board[z][y][x] = mark;
        moveHistory.addMove(coordinates, mark);
    }

    public int[][][] getBoard() {
        return board;
    }

    public int getValueAt(Coordinates coordinates) {
        return this.board[coordinates.getZ()][coordinates.getY()][coordinates.getX()];
    }

    private Boolean winningStraight(Coordinates coordinates) {
        int x = coordinates.getX();
        int y = coordinates.getY();
        int z = coordinates.getZ();

        // check x-axis
        int sum = 0;
        for (int i = 0; i < 4; i++) {
            sum += this.board[z][y][i];
        }
        if (Math.abs(sum) == 4) {
            return true;
        }

        // check y-axis
        sum = 0;
        for (int i = 0; i < 4; i++) {
            sum += this.board[z][i][x];
        }
        if (Math.abs(sum) == 4) {
            return true;
        }

        // check z-axis
        sum = 0;
        for (int i = 0; i < 4; i++) {
            sum += this.board[i][y][x];
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
            sum += this.board[c.getZ()][c.getY()][c.getX()];
        }
        return Math.abs(sum) == 4;
    }

    /**
     * Checks whether the latest move created a winning-line.
     * @return true/false.
     */
    public Boolean checkWinWithNewestCoordinate() {
        Coordinates coordinates = moveHistory.getLastMove().coordinates();
        if (winningStraight(coordinates)) {
            return true;
        } else {
            return winningDiagonal(coordinates);
        }
    }

    /**
     * Returns true/false depending whether the inputted coordinates make a winning line.
     * @param coordinates type Coordinates.
     * @return true/false.
     */
    public Boolean checkWinWithInput(Coordinates coordinates) {
        if (winningStraight(coordinates)) {
            return true;
        } else {
            return winningDiagonal(coordinates);
        }
    }
    
    /**
     * Returns a HashSet of all available coordinates.
     * @return HashSet.
     */
    public HashSet<Coordinates> availableCoordinates() {
        HashSet<Coordinates> availableCoordinates = new HashSet<>();
        for (int z = 0; z < 4; z++) {
            for (int y = 0; y < 4; y++) {
                for (int x = 0; x < 4; x++) {
                    if (board[z][y][x] == 0) {
                        availableCoordinates.add(new Coordinates(x, y, z));
                    }
                }
            }
        }
        return availableCoordinates;
    }

    /**
     * Outputs the a List of 4 Coordinates which won the game. 
     * @return List of Coordinates.
     */
    public List<Coordinates> getWinningLine() {
        WinningLinesRecord winningLines = new WinningLinesRecord();
        for (List<Coordinates> line : winningLines.getWinningLines()) {
            int sum = 0;
            for (Coordinates coordinates : line) {
                sum += getValueAt(coordinates);
            }
            if (Math.abs(sum) == 4) {
                return line;
            }
        }
        return null;
    }

    /**
     * Makes a deep copy of the this GameBoard instance.
     * @return GameBoard Object.
     */
    public GameBoard deepCopy() {
        GameBoard newBoard = new GameBoard(); 
        for (int z = 0; z < 4; z++) {  
            for (int y = 0; y < 4; y++) {  
                for (int x = 0; x < 4; x++) {  
                    newBoard.board[z][y][x] = this.board[z][y][x];  
                }  
            }  
        }  
        return newBoard;
    }

    public Boolean isFull() {
        return availableCoordinates().size() == 0;
    }
}