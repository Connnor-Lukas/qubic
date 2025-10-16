package dev.ccsio.qubic.ui.common;

import dev.ccsio.qubic.game.GameMaster;
import dev.ccsio.qubic.types.Coordinates;
import java.awt.*;
import javax.swing.*;

public class InputPanel extends JPanel {
    private static InputPanel INSTANCE;

    int currentPlayer = 1;
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

    public void updateOAMove(Coordinates c) {
        buttons[c.getZ()][c.getY()][c.getX()].setBackground(Color.decode(Colours.CUSTOM_3D_RED));
        currentPlayer--;
        allTheButtons.setEnabled(true);
        playerTurn.setText("Player 1's Turn");
        System.out.println("updateOAMove -> " + playerTurn.getText());
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

        for (int z = 0; z < 4; z++) {
            if (z > 0) {
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
        button.addActionListener(e -> {
            if (currentPlayer == 1) {
                System.out.println("Player 1's Turn");
                if (gameMaster.getGameMode() == "sp") {
                    System.out.println("SP");
                    if (gameMaster.handleInput(new Coordinates(x, y, z))) {
                        System.out.println("Handled Input");
                        playerTurn.setText("OA's Turn");
                        button.setBackground(Color.decode(Colours.CUSTOM_3D_BLUE));
                        currentPlayer++;
                        allTheButtons.setEnabled(false);
                        // Delay AI Move
                        Timer timer = new Timer(2000, ev -> {
                            gameMaster.makeOAMove();
                        });
                        timer.setRepeats(false);
                        timer.start();
                    }
                } else {
                    if (gameMaster.handleInput(new Coordinates(x, y, z))) {
                        button.setBackground(Color.decode(Colours.CUSTOM_3D_BLUE));
                        currentPlayer++;
                        playerTurn.setText("Player 2's Turn");
                    }
                }
            } else {
                if (gameMaster.getGameMode() == "tp") {
                    if (gameMaster.handleInput(new Coordinates(x, y, z))) {
                        button.setBackground(Color.decode(Colours.CUSTOM_3D_RED));
                        currentPlayer--;
                        playerTurn.setText("Player 1's Turn");
                    }
                }
            }
        });
        return button;
    }
}
