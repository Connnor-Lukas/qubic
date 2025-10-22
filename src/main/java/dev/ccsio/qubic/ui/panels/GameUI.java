package dev.ccsio.qubic.ui.panels;

import java.awt.*;
import javafx.application.Platform;
import javax.swing.*;

public class GameUI extends JPanel {
    public GameUI() {
        loadUI();
    }

    JPanel inputSideBar;
    JPanel historySideBar;
    Render3D visualisation3D;

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
        historySideBar = HistoryPanel.getInstance();
        historySideBar.setBackground((Color.decode("#100029")));
        c.gridx = 2;
        c.weightx = 0;
        add(historySideBar, c);

        // Load JavaFX scene inside JFXPanel
        Platform.runLater(() -> {
            visualisation3D.resetBoard();
        });
    }
}