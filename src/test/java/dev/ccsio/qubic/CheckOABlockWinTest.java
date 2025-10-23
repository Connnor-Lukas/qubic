package dev.ccsio.qubic;

import dev.ccsio.qubic.debug.TerminalDebug;
import dev.ccsio.qubic.game.GameBoard;
import dev.ccsio.qubic.game.GameMaster;
import dev.ccsio.qubic.game.WinningLinesRecord;
import dev.ccsio.qubic.types.Coordinates;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CheckOABlockWinTest {
    GameMaster gameMaster = GameMaster.getInstance();
    TerminalDebug td = new TerminalDebug();

    public CheckOABlockWinTest() {}

    public void checkBlockStraight() {
        gameMaster.init(1);
        List<Coordinates> movesSet = new ArrayList<>();
        movesSet.add(new Coordinates(0,0,0));
        movesSet.add(new Coordinates(0,1,0));


        for (Coordinates coordinates : movesSet) {
            gameMaster.getGameBoard().placeMark(coordinates, -1);
        }

        gameMaster.handleInput(new Coordinates(0,2,0));


        td.printBoard(gameMaster.getGameBoard(), true);

        gameMaster.applyOAMove(gameMaster.computeOAMove());

        td.printBoard(gameMaster.getGameBoard(), true);
        assert gameMaster.getGameBoard().getValueAt(new Coordinates(0,3,0)) == 1 : " " + gameMaster.getGameBoard();
    }


    private void checkBlockAllLines(int difficulty) {
        WinningLinesRecord winLines = new WinningLinesRecord();

        for (List<Coordinates> winLine : winLines.getWinningLines()) {
            for (int i=0; i < 4; i++) {
                gameMaster.reset();
                gameMaster.init(difficulty);

                for (int j=0; j < 4; j++) {
                    if (i == j) {
                        continue;
                    }
                    gameMaster.getGameBoard().placeMark(winLine.get(j), -1);
                }
                gameMaster.mark = 1;

                td.printBoard(gameMaster.getGameBoard(), true);
                gameMaster.applyOAMove(gameMaster.computeOAMove());
                td.printBoard(gameMaster.getGameBoard(), true);

                assert gameMaster.getGameBoard().getValueAt(winLine.get(i)) == 1;
            }

        }
    }

    @Test
    public void testBlockLinesDifficulties() {
        for (int i=0; i < 4; i++)  {
            checkBlockAllLines(i);
        }
    }
}
