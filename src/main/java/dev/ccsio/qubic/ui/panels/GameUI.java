package dev.ccsio.qubic.ui.panels;

import java.awt.*;

import dev.ccsio.qubic.ui.panels.gamehistory.HistoryPanel;
import javafx.application.Platform;
import javax.swing.*;

public class GameUI extends JPanel {
    public GameUI() {
        loadUI();
    }

    InputPanel inputSideBar;
    HistoryPanel historySideBar;
    Render3D visualisation3D;

    void loadUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1.0;

        // LEFT SIDEBAR
        inputSideBar = InputPanel.getInstance();
        inputSideBar.reset();
        c.gridx = 0;
        add(inputSideBar, c);

        // CENTER — JavaFX panel
        visualisation3D = Render3D.getInstance();
        c.gridx = 1;
        c.weightx = 1;
        add(visualisation3D, c);

        // RIGHT SIDEBAR
        historySideBar = HistoryPanel.getInstance();
        historySideBar.reset();
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