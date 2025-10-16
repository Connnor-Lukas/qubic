package dev.ccsio.qubic;

import dev.ccsio.qubic.debug.TerminalDebug;
import dev.ccsio.qubic.game.GameBoard;

/**
 * The games main class, used to build everything.
 */
public class Main {
    public static void main(String[] args) {
        // TerminalDebug.playGame(new GameBoard());
        TerminalDebug.placeAllWinningLines();
    }
}