package dev.ccsio.qubic.ui.panels.gamehistory;

import dev.ccsio.qubic.game.GameMaster;
import dev.ccsio.qubic.types.Coordinates;
import dev.ccsio.qubic.types.MoveHistory;
import dev.ccsio.qubic.ui.Colours;
import dev.ccsio.qubic.ui.QubicWindow;
import dev.ccsio.qubic.ui.panels.GameUI;
import dev.ccsio.qubic.ui.panels.MenuUI;
import dev.ccsio.qubic.ui.panels.Render3D;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HistoryViewer extends JPanel {
    private static HistoryViewer INSTANCE;

    private QubicWindow qubicWindow;
    private Render3D background;
    private JLabel winnerLabel;

    List<MoveHistory.PlayerMove> moveHistory;
    String winnerText = "";
    int currentPosition = 0;

    private HistoryViewer() {}

    private void setup() {
        qubicWindow = QubicWindow.getInstance();
        background = Render3D.getInstance();

        this.setLayout(new GridLayout());
        background.setLayout(new GridBagLayout());

        winnerLabel = new JLabel(winnerText);
        winnerLabel.setFont(new Font("Showcard Gothic", Font.BOLD, 50));

        JPanel buttonPanel = new JPanel();
        JButton backButton = MenuUI.getJButton("◀", Colours.CUSTOM_MENU_BLUE);
        backButton.addActionListener(event -> {
            previousMove();
            background.requestFocus();
        });
        JButton forwardButton = MenuUI.getJButton("▶", Colours.CUSTOM_MENU_BLUE);
        forwardButton.addActionListener(event -> {
            nextMove();
            background.requestFocus();
        });
        JButton menuButton = MenuUI.getJButton("Menu", Colours.CUSTOM_MENU_RED);
        menuButton.addActionListener(event -> {
            returnToMenu();
        });
        buttonPanel.add(backButton);
        buttonPanel.add(menuButton);
        buttonPanel.add(forwardButton);
        buttonPanel.setOpaque(false);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 0.1;
        c.fill = GridBagConstraints.BOTH;
        background.add(buttonPanel, c);
        c.gridy = 1;
        c.weighty = 0.9;
        background.add(winnerLabel, c);
        background.enableHistoryControl();
        this.add(background);
    }

    public static HistoryViewer getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HistoryViewer();
        }
        return INSTANCE;
    }

    private void returnToMenu() {
        background.removeAll();
        background.disableHistoryControl();
        background.disableMouseControls();
        JPanel pastPanel = qubicWindow.getPastPanel(1);
        if (pastPanel instanceof HistorySelect temp) {
            qubicWindow.showView(pastPanel);
            temp.loadUI();
        } else if (pastPanel instanceof GameUI) {
            qubicWindow.showView(new MenuUI());
        }
    }

    public void showHistory(String winnerText) {
        GameMaster gameMaster = GameMaster.getInstance();
        showHistory(gameMaster.getMoveHistory(), winnerText);
    }

    public void showHistory(List<MoveHistory.PlayerMove> playerMoves, String winnerText) {
        this.winnerText = winnerText;
        moveHistory = playerMoves;
        currentPosition = moveHistory.size();
        setup();
        qubicWindow.showView(this);
        background.fixLighting();
        background.enableMouseControls();
        background.requestFocus();
    }

    public void nextMove() {
        if (currentPosition < moveHistory.size()) {
            currentPosition++;
            background.makeMove(moveHistory.get(currentPosition - 1));
        }
    }

    public void previousMove() {
        if (currentPosition > 0) {
            currentPosition--;
            background.unMakeMove(moveHistory.get(currentPosition));
        }
    }

    public static List<MoveHistory.PlayerMove> decodedJSON(JSONArray json) {
        List<MoveHistory.PlayerMove> playerMoves = new ArrayList<>();
        JSONObject move;

        for (int i = 0; i < json.length(); i++) {
            move = json.getJSONObject(i);
            playerMoves.add(
                new MoveHistory.PlayerMove(
                    new Coordinates(
                        move.getInt("x"),
                        move.getInt("y"),
                        move.getInt("z")
                    ),
                    move.getInt("mark")
                )
            );
        }

        return playerMoves;
    }
}
