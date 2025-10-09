package dev.ccsio.qubic.ui.menu;

import dev.ccsio.qubic.ui.common.Render3D;
import java.awt.*;
import javax.swing.*;

public class MenuUI extends JPanel {
    public MenuUI() {
        load3DOnly();
    }

    private void load3DOnly() {
        // Use BorderLayout to center content
        setLayout(new BorderLayout());

        // Load the JFX
        Render3D backgroundPanel = new Render3D();
        add(backgroundPanel, BorderLayout.CENTER);
    }
}