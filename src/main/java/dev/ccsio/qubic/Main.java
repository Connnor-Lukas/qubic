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
        String osName = System.getProperty("os.name").toLowerCase();
        String osArch = System.getProperty("os.arch").toLowerCase();

        // If it's Linux AND ARM64, force the GPU.
        // Otherwise, leave it alone and let JavaFX decide safely.
        if (osName.contains("linux") && (osArch.contains("aarch64") || osArch.contains("arm"))) {
            System.out.println("Arm Linux detected: Forcing Hardware Acceleration...");
            System.setProperty("prism.forceGPU", "true");
        }

        // Windows Render Fix (Mac & Linux work)
        if (osName.contains("windows")) {
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
