package dev.ccsio.qubic;

import java.util.ArrayList;
import java.util.List;

public class MoveHistory {
    List<PlayerMove> moveHistory;

    public MoveHistory() {
        moveHistory = new ArrayList<>();
    }

    public void addMove(Coordinates coordinates, int player) {
        moveHistory.add(new PlayerMove(coordinates, player));
    }

    public PlayerMove getLastMove() {
        return moveHistory.get(moveHistory.size() - 1);
    }

    public record PlayerMove(Coordinates coordinates, int player) {}
}
