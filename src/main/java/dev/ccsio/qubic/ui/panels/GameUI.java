package dev.ccsio.qubic.ui.panels;

import java.awt.*;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javax.swing.*;

public class GameUI extends JPanel {
    public GameUI() {
        loadUI();
    }

    JPanel inputSideBar;
    JPanel historySideBar;
    Render3D visualisation3D;
    GridBagConstraints c;

    void loadUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1.0;

        // LEFT SIDEBAR
        inputSideBar = InputPanel.getInstance();
        c.gridx = 0;
        add(inputSideBar, c);

        // CENTER — JavaFX panel
        visualisation3D = Render3D.getInstance();
        c.gridx = 1;
        c.weightx = 1;
        add(visualisation3D, c);

        // RIGHT SIDEBAR
        historySideBar = new JPanel();
        historySideBar.setBackground(Color.RED);
        c.gridx = 2;
        c.weightx = 0;
        add(historySideBar, c);

        // Load JavaFX scene inside JFXPanel
        Platform.runLater(() -> {
            visualisation3D.resetBoard();
        });
    }

    private void collapseHistory() {
        remove(historySideBar);
        revalidate();
        repaint();
    }

    private void showHistory() {
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1.0;
        add(historySideBar, c);
        revalidate();
        repaint();
    }
}