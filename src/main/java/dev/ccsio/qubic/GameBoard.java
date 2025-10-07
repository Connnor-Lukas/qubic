package dev.ccsio.qubic;

public class GameBoard {
    int[][][] board;

    GameBoard() {
        board = new int[4][4][4];
    }

    public void placeMark(int[] coords, int mark) {

    }
}



public class Coordinate {
    int x;
    int y;
    int z;

    public Coordinate(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
