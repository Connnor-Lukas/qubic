package dev.ccsio.qubic.ui.panels;

import dev.ccsio.qubic.game.GameMaster;
import dev.ccsio.qubic.types.Coordinates;
import dev.ccsio.qubic.ui.Colours;
import dev.ccsio.qubic.ui.panels.gamehistory.HistoryPanel;

import java.awt.*;
import javax.swing.*;

public class InputPanel extends JPanel {
    private static InputPanel INSTANCE;

    HistoryPanel historySideBar = HistoryPanel.getInstance();
    Render3D render = Render3D.getInstance();
    int currentPlayer = -1;
    JLabel playerTurn;
    GameMaster gameMaster;
    JPanel allTheButtons;
    JButton[][][] buttons = new JButton[4][4][4];

    private InputPanel() {
        this.gameMaster = GameMaster.getInstance();
        this.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        createUIComponents();
    }

    public static InputPanel getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new InputPanel();
        }
        return INSTANCE;
    }

    public void reset() {
        INSTANCE = new InputPanel();
    }

    public void updateOAMove(Coordinates c) {
        buttons[c.getZ()][c.getY()][c.getX()].setBackground(Color.decode(Colours.CUSTOM_3D_BLUE));
        render.makeMove(1, c);
        currentPlayer *= -1;
        allTheButtons.setEnabled(true);
        playerTurn.setText("Player 1's Turn");
        historySideBar.refresh();
    }

    private void createUIComponents() {
        playerTurn = new JLabel("Player 1's Turn");
        playerTurn.setFont(new Font(playerTurn.getFont().getName(), Font.BOLD, 30));
        playerTurn.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerTurn.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        this.add(playerTurn);
        allTheButtons = allTheButtons();
        this.add(allTheButtons);
    }

    private JPanel allTheButtons() {
        JPanel sizeLimiter = new JPanel();
        sizeLimiter.setLayout(new BorderLayout());
        int maxWidth = 250;
        int maxHeight = Math.toIntExact(Math.round(maxWidth * 4.75));
        sizeLimiter.setMaximumSize(new Dimension(maxWidth, maxHeight));
        sizeLimiter.setPreferredSize(new Dimension(maxWidth, maxHeight));

        JPanel buttonGrid = new JPanel();
        buttonGrid.setPreferredSize(new Dimension(buttonGrid.getPreferredSize().width, 320));
        buttonGrid.setLayout(new GridLayout(0, 4, 0, 0));
        JButton button;

        for (int z = 3; z >= 0; z--) {
            if (z < 3) {
                for (int i = 0; i < 4; i++) {
                    buttonGrid.add(new JPanel());
                }
            }
            for (int y = 0; y < 4; y++) {
                for (int x = 0; x < 4; x++) {
                    button = inputButton(x, y, z);
                    buttonGrid.add(button);
                    buttons[z][y][x] = button;
                }
            }
        }

        sizeLimiter.add(buttonGrid, BorderLayout.CENTER);
        return sizeLimiter;
    }

    private JButton inputButton(int x, int y, int z) {
        JButton button = new JButton();
        Coordinates c = new Coordinates(x, y, z);
        button.addActionListener(e -> {
            if (currentPlayer == -1) {
                if (gameMaster.getGameMode() == "sp") {
                    if (gameMaster.handleInput(c)) {
                        render.makeMove(-1, c);
                        playerTurn.setText("OA's Turn");
                        historySideBar.refresh();
                        button.setBackground(Color.decode(Colours.CUSTOM_3D_YELLOW));
                        currentPlayer *= -1;
                        allTheButtons.setEnabled(false);
                        new Thread(() -> {
                            Coordinates aiMove = gameMaster.computeOAMove();

                            // Back to UI thread for updates
                            SwingUtilities.invokeLater(() -> gameMaster.applyOAMove(aiMove));
                        }).start();
                    }
                } else {
                    if (gameMaster.handleInput(c)) {
                        render.makeMove(-1, c);
                        historySideBar.refresh();
                        button.setBackground(Color.decode(Colours.CUSTOM_3D_YELLOW));
                        currentPlayer *= -1;
                        playerTurn.setText("Player 2's Turn");
                    }
                }
            } else {
                if (gameMaster.getGameMode() == "tp") {
                    if (gameMaster.handleInput(c)) {
                        render.makeMove(1, c);
                        historySideBar.refresh();
                        button.setBackground(Color.decode(Colours.CUSTOM_3D_BLUE));
                        currentPlayer *= -1;
                        playerTurn.setText("Player 1's Turn");
                    }
                }
            }
        });
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (!(gameMaster.getGameMode() == "sp" && currentPlayer == 1)) {
                    render.previewMove(currentPlayer, c);
                }
            }
        });
        return button;
    }
}
