package dev.ccsio.qubic;

import dev.ccsio.qubic.debug.TerminalDebug;
import dev.ccsio.qubic.game.GameBoard;
import dev.ccsio.qubic.game.GameMaster;
import dev.ccsio.qubic.types.Coordinates;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckWinDetectionTest {
    GameMaster gameMaster = GameMaster.getInstance();

    public CheckWinDetectionTest() {
        gameMaster.init();
    }

    @Test
    public void testHorizontalWinDetection() {
        assertTrue(checkHorizontalWinDetection());
    }

    public boolean checkHorizontalWinDetection() {
        List<List<Coordinates>> allStraightLines = new ArrayList<>();
        List<Coordinates> temp = new ArrayList<>();

        for (int z = 0; z < 4; z++) {
            for (int y = 0; y < 4; y++) {
                for (int x = 0; x < 4; x++) {
                    temp.add(new Coordinates(x, y, z));
                }
                allStraightLines.add(new ArrayList<Coordinates>(temp));
                temp.clear();
            }
        }

        for (int z = 0; z < 4; z++) {
            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {
                    temp.add(new Coordinates(x, y, z));
                }
                allStraightLines.add(new ArrayList<Coordinates>(temp));
                temp.clear();
            }
        }

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                for (int z = 0; z < 4; z++) {
                    temp.add(new Coordinates(x, y, z));
                }
                allStraightLines.add(new ArrayList<Coordinates>(temp));
                temp.clear();
            }
        }

        for (List<Coordinates> moves : allStraightLines) {
            int moveCount = 0;
            for (Coordinates move : moves) {
                gameMaster.handleInput(move);

                if (moveCount == 3) {
                    continue;
                }

                while (true) {
                    Coordinates tempCoord = Coordinates.random();
                    if (moves.contains(tempCoord)) continue;
                    if (!gameMaster.handleInput(tempCoord)) continue;
                    break;
                }
                moveCount++;
            }
            
            if (!gameMaster.checkWinner().equals("Player 2 Won")) return false; // Says Player 2 since player gets updated internally before.
            gameMaster.reset();
            gameMaster.init();
        }
        return true;
    }
}
