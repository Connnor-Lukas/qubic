package dev.ccsio.qubic.ui.panels;

import com.formdev.flatlaf.FlatClientProperties;
import dev.ccsio.qubic.game.GameMaster;

import java.awt.*;
import java.awt.event.ActionEvent;

import dev.ccsio.qubic.ui.Colours;
import dev.ccsio.qubic.ui.QubicWindow;
import dev.ccsio.qubic.ui.panels.gamehistory.HistorySelect;

import javax.swing.*;

public class MenuUI extends JPanel {
    QubicWindow frame = QubicWindow.getInstance();
    DifficultySelector selector = DifficultySelector.getInstance();
    GameMaster gameMaster = GameMaster.getInstance();
    Render3D backgroundPanel = Render3D.getInstance();

    public MenuUI() {
        loadMenu();
    }

    private void loadMenu() {
        gameMaster.reset();
        backgroundPanel.resetBoard();

        // Use BorderLayout to center content
        setLayout(new BorderLayout());

        // Create the background panel
        backgroundPanel.setupMenu();
        backgroundPanel.setLayout(new GridBagLayout());
        add(backgroundPanel, BorderLayout.CENTER);

        // Create a panel to hold the title and button vertically
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setOpaque(false);

        // Create a panel for the split-colored title
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        titlePanel.setOpaque(false);

        // Title labels
        JLabel titlePart1 = new JLabel("QUBIC");
        titlePart1.setFont(new Font("Arial", Font.BOLD, 64));
        titlePart1.setForeground(Color.decode(Colours.CUSTOM_MENU_BLUE));

        JLabel titlePart2 = new JLabel("3D");
        titlePart2.setFont(new Font("Arial", Font.BOLD, 64));
        titlePart2.setForeground(Color.decode(Colours.CUSTOM_MENU_RED));

        titlePanel.add(titlePart1);
        titlePanel.add(titlePart2);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // 1-Player Start Button
        JButton start1PGame = getJButton("1-Player Game", Colours.CUSTOM_MENU_BLUE);
        start1PGame.setPreferredSize(new Dimension(320, 50));
        start1PGame.addActionListener((ActionEvent e) -> {
            backgroundPanel.remove(0);
            selector.getDifficulty(difficulty -> {
                gameMaster.init(difficulty);
                frame.showView(new GameUI());
                backgroundPanel.fixLighting();
                backgroundPanel.enableMouseControls();
                backgroundPanel.remove(0);
            });
        });

        // 2-Player Start Button
        JButton start2PGame = getJButton("2-Player Game", Colours.CUSTOM_MENU_BLUE);
        start2PGame.setPreferredSize(new Dimension(320, 50));
        start2PGame.addActionListener((ActionEvent e) -> {
            gameMaster.init();
            backgroundPanel.remove(0);
            frame.showView(new GameUI());
            backgroundPanel.fixLighting();
            backgroundPanel.enableMouseControls();
        });

        // Tutorial Button
        JButton startTutorial = getJButton("Replays", Colours.CUSTOM_MENU_BLACK);
        startTutorial.setPreferredSize(new Dimension(155, 40));
        startTutorial.addActionListener((ActionEvent e) -> {
            backgroundPanel.remove(0);
            frame.showView(new HistorySelect());
        });

        // Quit Game Button
        JButton exitButton = getJButton("Quit", Colours.CUSTOM_MENU_RED);
        exitButton.setPreferredSize(new Dimension(155, 40));
        exitButton.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });

        // Center Panel GridBag Constraints
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 15, 0);

        // Bottom Side by Side Panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        bottomPanel.add(startTutorial);
        bottomPanel.add(exitButton);
        bottomPanel.setOpaque(false);

        // Add components to center panel
        centerPanel.add(titlePanel, c);
        c.gridy = 1;
        centerPanel.add(start1PGame, c);
        c.gridy = 2;
        centerPanel.add(start2PGame, c);
        c.gridy = 3;
        centerPanel.add(bottomPanel, c);

        backgroundPanel.add(centerPanel);
        backgroundPanel.fixLighting();
    }

    public static JButton getJButton(String text, String colour) {
        JButton button = new JButton(text);
        button.putClientProperty(FlatClientProperties.STYLE,
            "background: " + colour + ";"
            + "foreground: " + Colours.CUSTOM_MENU_WHITE + ";"
            + "borderWidth: 0;"
            + "arc: 15;"
            + "focusWidth: 0;"
            + "font: bold +2;"
            + "hoverBackground: darken(" + colour + ", 10%);"
            + "pressedBackground: " + colour + ";"
            + "focusedBackground: " + colour + ";"
            + "selectedBackground: " + colour + ";"
        );

        return button;
    }

    private void load3DOnly() {
        // Use BorderLayout to center content
        setLayout(new BorderLayout());

        // Load the JFX
        Render3D backgroundPanel = Render3D.getInstance();
        add(backgroundPanel, BorderLayout.CENTER);
    }
}