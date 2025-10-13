package dev.ccsio.qubic.game;

import dev.ccsio.qubic.types.MoveHistory;
import java.util.HashMap;
import java.util.Map;

public final class LinkedHistory {
    private static final Map<GameBoard, MoveHistory> MOVE_HISTORY = new HashMap<>();

    public static MoveHistory getMoveHistory(GameBoard gameBoard) {
        return MOVE_HISTORY.get(gameBoard);
    }

    public static void addMoveHistory(GameBoard gameBoard, MoveHistory moveHistory) {
        MOVE_HISTORY.put(gameBoard, moveHistory);
    }

    public static void removeMoveHistory(GameBoard gameBoard) {
        MOVE_HISTORY.remove(gameBoard);
    }
}
