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
        this.setLayout(new GridBagLayout());
        winnerLabel = new JLabel();
        winnerLabel.setFont(new Font("Showcard Gothic", Font.BOLD, 50));
        this.add(winnerLabel);
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
