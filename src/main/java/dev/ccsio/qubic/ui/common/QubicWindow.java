package dev.ccsio.qubic.ui.common;

import javax.swing.*;

public class QubicWindow extends JFrame {
    private JPanel currentView;

    public QubicWindow() {
        setTitle("Qubic - A Java Project");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
    }

    public void showView(JPanel newView) {
        if (currentView != null) {
            remove(currentView);
        }
        currentView = newView;
        add(currentView);
        revalidate();
        repaint();
    }
}
