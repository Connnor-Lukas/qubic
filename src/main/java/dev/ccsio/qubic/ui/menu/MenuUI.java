package dev.ccsio.qubic.ui.menu;

import java.awt.*;
import java.awt.event.ActionEvent;
import javafx.embed.swing.JFXPanel;
import javax.swing.*;

public class MenuUI extends JPanel {
    public MenuUI() {
        // Use BorderLayout to center content
        setLayout(new BorderLayout());

        // Create the background panel
        JFXPanel backgroundPanel = new JFXPanel(); // Placeholder
        backgroundPanel.setLayout(new GridBagLayout());
        add(backgroundPanel, BorderLayout.CENTER);

        // Create a panel to hold the title and button vertically
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setOpaque(false);

        // Title label
        JLabel titleLabel = new JLabel("Welcome to Qubic");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0)); // bottom padding

        // 1-Player Start Button
        JButton start1PGame = new JButton("1-Player Game");
        start1PGame.addActionListener((ActionEvent e) -> {
            System.out.println("Start button clicked!");
        });

        // 2-Player Start Button
        JButton start2PGame = new JButton("2-Player Game");
        start2PGame.addActionListener((ActionEvent e) -> {
            System.out.println("Start button clicked!");
        });

        // Tutorial Button
        JButton startTutorial = new JButton("Tutorial");
        startTutorial.addActionListener((ActionEvent e) -> {
            System.out.println("Start button clicked!");
        });

        // Quite Game Button
        JButton exitButton = new JButton("Quit");
        exitButton.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });

        // Center Panel GridBag Constraints
        GridBagConstraints c = new GridBagConstraints();
        c.gridx=0;
        c.gridy=0;
        c.insets = new Insets(0, 0, 5, 0);

        // Bottom Side by Side Panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        bottomPanel.add(startTutorial);
        bottomPanel.add(exitButton);
        bottomPanel.setOpaque(false);

        // Add components to center panel
        centerPanel.add(titleLabel, c);
        c.gridy=1;
        centerPanel.add(start1PGame, c);
        c.gridy=2;
        centerPanel.add(start2PGame, c);
        c.gridy=3;
        centerPanel.add(bottomPanel, c);

        // Add center panel to main panel
        backgroundPanel.add(centerPanel);
    }
}