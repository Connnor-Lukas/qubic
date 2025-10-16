package dev.ccsio.qubic.ui;

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
    JFXPanel visualisation3D;
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
        visualisation3D = new JFXPanel();
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
            Group root = new Group();
            Scene scene = new Scene(root, javafx.scene.paint.Color.WHITE);
            visualisation3D.setScene(scene);
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