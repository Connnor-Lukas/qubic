package dev.ccsio.qubic.ui;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class QubicWindow extends JFrame {
    private static QubicWindow INSTANCE;
    private JPanel currentView;
    private List<JPanel> pastPanels = new ArrayList<>();

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
        pastPanels.add(newView);
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

    public JPanel getPastPanel(int panelsAgo) {
        if (panelsAgo >= pastPanels.size()) {
            throw new IndexOutOfBoundsException("There have only been " + pastPanels.size() + " panels in this window, not " + (panelsAgo + 1) + ".");
        }
        if (pastPanels.isEmpty()) {
            throw new IndexOutOfBoundsException("There have been no panels in this window.");
        }
        if (panelsAgo < 0) {
           throw new IndexOutOfBoundsException("Cannot get future panels.");
        }
        return pastPanels.get(pastPanels.size() - 1 - panelsAgo);
    }
}
