package dev.ccsio.qubic.ui.common;

import javax.swing.*;

public class QubicWindow extends JFrame {
    private static QubicWindow INSTANCE;
    private JPanel currentView;

    private QubicWindow() {}

    /**
     * The singleton method to make this class have only one possible object.
     * @return This Instance.
     */
    public static QubicWindow getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new QubicWindow();
            INSTANCE.setup();
        }
        return INSTANCE;
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

    private void setup() {
        setTitle("Qubic - A Java Project");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
    }
}
