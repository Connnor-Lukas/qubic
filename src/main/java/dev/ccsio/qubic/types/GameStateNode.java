package dev.ccsio.qubic.types;

import dev.ccsio.qubic.game.GameBoard;
import java.util.ArrayList;
import java.util.List;

/**
 * Custom Node class for trees of Game States.
 */
public class GameStateNode {
    public int evalValue;
    public List<GameStateNode> children;
    public GameBoard gameBoard;
    public Coordinates latestCoordinates;

    /**
     * TreeNode constructor method.
     * @param gameBoard gameBoard state;
     */
    public GameStateNode(GameBoard gameBoard, Coordinates latestCoordinates) {
        this.gameBoard = gameBoard.deepCopy();
        this.latestCoordinates = latestCoordinates;
        this.children = new ArrayList<>();
    }

    public void addChild(GameStateNode child) {
        this.children.add(child);
    }

    public Boolean isWinningState() {
        return gameBoard.checkWinWithInput(latestCoordinates);
    }

    @Override
    public String toString() {
        String out = "";
        out += " ".repeat(children.size()) + evalValue + "\n";
        for (GameStateNode child : children) {
            out += child.evalValue + " ";
        }
        return out;
    }
}
