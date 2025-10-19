package dev.ccsio.qubic.ui.panels;

import dev.ccsio.qubic.ui.QubicWindow;

import javax.swing.*;
import java.awt.*;

public class WinScreen extends JPanel {
    private static WinScreen INSTANCE;

    private QubicWindow qubicWindow;
    private JLabel winnerLabel;

    private WinScreen() {
        qubicWindow = QubicWindow.getInstance();
        Render3D background = Render3D.getInstance();

        this.setLayout(new GridBagLayout());
        background.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        background.fixLighting();
        this.add(background, c);
        winnerLabel = new JLabel();
        winnerLabel.setFont(new Font("Showcard Gothic", Font.BOLD, 50));
        background.add(winnerLabel);
    }

    public static WinScreen getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new WinScreen();
        }
        return INSTANCE;
    }

    public void showWinScreen(String winText) {
        winnerLabel.setText(winText);
        qubicWindow.showView(this);
    }
}
