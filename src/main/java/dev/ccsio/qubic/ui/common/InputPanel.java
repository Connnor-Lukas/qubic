package dev.ccsio.qubic.ui.common;

import dev.ccsio.qubic.game.GameMaster;
import dev.ccsio.qubic.types.Coordinates;
import javax.swing.*;
import java.awt.*;

public class InputPanel extends JPanel {
    boolean isSinglePlayer;
    GameMaster gameMaster;
    JButton[][][] buttons = new JButton[4][4][4];

    public InputPanel(boolean isSinglePlayer) {
        this.isSinglePlayer = isSinglePlayer;
        this.gameMaster = GameMaster.getInstance();
        this.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        createUIComponents();
    }

    private void createUIComponents() {
        JLabel label = new JLabel("Player 1's Turn");
        label.setFont(new Font(label.getFont().getName(), Font.BOLD, 30));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        this.add(label);
        this.add(allTheButtons());
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
                    buttons[x][y][z] = button;
                }
            }
        }

        sizeLimiter.add(buttonGrid, BorderLayout.CENTER);
        return sizeLimiter;
    }

    private JButton inputButton(int x, int y, int z) {
        JButton button = new JButton();
        button.addActionListener(e -> {
            gameMaster.handleInput(new Coordinates(x, y, z), false);
        });
        return button;
    }
}
