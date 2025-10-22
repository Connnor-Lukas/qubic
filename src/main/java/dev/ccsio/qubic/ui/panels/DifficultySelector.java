package dev.ccsio.qubic.ui.panels;

import dev.ccsio.qubic.ui.Colours;
import dev.ccsio.qubic.ui.QubicWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;

public class DifficultySelector extends JPanel {
    public static DifficultySelector INSTANCE;
    QubicWindow qubicWindow = QubicWindow.getInstance();
    Render3D background = Render3D.getInstance();
    JPanel centerPanel = new JPanel();

    public static DifficultySelector getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DifficultySelector();
        }
        return INSTANCE;
    }

    private DifficultySelector() {
        this.setLayout(new BorderLayout());
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setOpaque(false);
    }

    public void getDifficulty(Consumer<Integer> callback) {
        qubicWindow.showView(this);
        JButton _00 = MenuUI.getJButton("Random Placement", Colours.CUSTOM_MENU_BLUE);
        JButton _01 = MenuUI.getJButton("Defensive OA", Colours.CUSTOM_MENU_RED);
        JButton _02 = MenuUI.getJButton("Strategic OA", Colours.CUSTOM_MENU_BLACK);
        JButton _03 = MenuUI.getJButton("Cruel OA", Colours.CUSTOM_MENU_WHITE);

        _03.setForeground(Color.black);

        _00.setPreferredSize(new Dimension(320, 50));
        _01.setPreferredSize(new Dimension(320, 50));
        _02.setPreferredSize(new Dimension(320, 50));
        _03.setPreferredSize(new Dimension(320, 50));

        // Center Panel GridBag Constraints
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 15, 0);

        centerPanel.add(_00, c);
        c.gridy = 1;
        centerPanel.add(_01, c);
        c.gridy = 2;
        centerPanel.add(_02, c);
        c.gridy = 3;
        centerPanel.add(_03, c);

        background.add(centerPanel);

        _00.addActionListener(e -> {
            qubicWindow.showView(qubicWindow.getPastPanel(1));
            callback.accept(0);
        });

        _01.addActionListener(e -> {
            qubicWindow.showView(qubicWindow.getPastPanel(1));
            callback.accept(1);
        });

        _02.addActionListener(e -> {
            qubicWindow.showView(qubicWindow.getPastPanel(1));
            callback.accept(2);
        });

        _03.addActionListener(e -> {
            qubicWindow.showView(qubicWindow.getPastPanel(1));
            callback.accept(3);
        });

        this.add(background, BorderLayout.CENTER);
        background.fixLighting();
        this.revalidate();
        this.repaint();
    }
}
