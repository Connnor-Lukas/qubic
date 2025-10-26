package dev.ccsio.qubic.game.algorithms;

import dev.ccsio.qubic.game.GameBoard;
import dev.ccsio.qubic.game.LinkedHistory;
import dev.ccsio.qubic.game.WinningLinesRecord;
import dev.ccsio.qubic.types.Coordinates;
import java.util.List;

public abstract class AlgorithmBase {
    // GameBoard
    GameBoard gameBoard;

    // Winning Lines
    WinningLinesRecord selfWinOptions;
    WinningLinesRecord opponentWinOptions;

    // History
    Coordinates opponentLastMove;

    // VariableMark Implementation
    int oaMark;

    enum MarkIs {
        SELF, OPPONENT, NOBODY
    }

    MarkIs whoIsThis(int mark) {
        if (mark == oaMark) return MarkIs.SELF;
        if (mark == -oaMark) return MarkIs.OPPONENT;
        return MarkIs.NOBODY;
    }

    protected AlgorithmBase() {
        selfWinOptions = new WinningLinesRecord();
        opponentWinOptions = new WinningLinesRecord();
    }

    public final Coordinates getMove(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        updateAfterOpponentsMove();

        Coordinates instantWin = checkInstantWin();
        if (instantWin != null) {
            return instantWin;
        }

        opponentLastMove = LinkedHistory.getMoveHistory(gameBoard).getLastMove().coordinates();
        Coordinates move = chooseMove();
        updateWithNewCoordinates(move);
        return move;
    }

    protected abstract Coordinates chooseMove();

    protected Coordinates checkInstantWin() {
        Coordinates selfWin = checkSelfWinInOne();
        Coordinates opponentWin = checkOpponentWinInOne();

        if (selfWin != null) {
            return selfWin;
        }

        return opponentWin;
    }

    private Coordinates checkSelfWinInOne() {
        for (List<Coordinates> list : selfWinOptions.getWinningLines()) {
            int sum = 0;
            int emptyIdx = -1;
            for (int i = 0; i < 4; i++) {
                if (gameBoard.getValueAt(list.get(i)) == oaMark) {
                    sum++;
                } else {
                    emptyIdx = i;
                }
            }

            if (sum == 3) {
                updateWithNewCoordinates(list.get(emptyIdx));
                return list.get(emptyIdx);
            }
        }
        return null;
    }

    private Coordinates checkOpponentWinInOne() {
        for (List<Coordinates> list : opponentWinOptions.getWinningLines()) {
            int sum = 0;
            int emptyIdx = -1;
            for (int i = 0; i < 4; i++) {
                if (gameBoard.getValueAt(list.get(i)) == -oaMark) {
                    sum++;
                } else {
                    emptyIdx = i;
                }
            }

            if (sum == 3) {
                updateWithNewCoordinates(list.get(emptyIdx));
                return list.get(emptyIdx);
            }
        }
        return null;

    }

    protected void updateWithNewCoordinates(Coordinates coordinates) {
        opponentWinOptions.deleteLines(coordinates);
    }

    /**
     * Removes opponent's latest move from available coordinates.
     */
    protected void updateAfterOpponentsMove() {
        opponentLastMove = LinkedHistory.getMoveHistory(gameBoard).getLastMove().coordinates();
        selfWinOptions.deleteLines(opponentLastMove);
    }
}
