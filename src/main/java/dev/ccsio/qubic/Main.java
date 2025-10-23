package dev.ccsio.qubic;

import com.formdev.flatlaf.FlatLightLaf;
import dev.ccsio.qubic.ui.QubicWindow;
import dev.ccsio.qubic.ui.panels.MenuUI;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static boolean useAlternateRender = false;
    public static boolean runByUser = false;
    public static void main(String[] args) {
        // Windows Render Fix (Mac & Linux work)
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            useAlternateRender = true;
        }

        // Tests fix
        runByUser = true;

        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Launch the application
        SwingUtilities.invokeLater(() -> {
            QubicWindow frame = QubicWindow.getInstance();
            frame.setVisible(true);
            frame.showView(new MenuUI());
        });
    }
}