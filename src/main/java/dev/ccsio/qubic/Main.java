package dev.ccsio.qubic;

import com.formdev.flatlaf.FlatLightLaf;
import dev.ccsio.qubic.ui.common.QubicWindow;
import dev.ccsio.qubic.ui.menu.MenuUI;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        // Setup FlatLaf Look and Feel
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            // If FlatLaf is not available, fall back to default
            e.printStackTrace();
        }

        // Launch the application
        SwingUtilities.invokeLater(() -> {
            QubicWindow frame = new QubicWindow();
            frame.setVisible(true);
            frame.showView(new MenuUI());
        });
    }
}