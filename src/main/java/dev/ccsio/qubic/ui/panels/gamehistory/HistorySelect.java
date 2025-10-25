package dev.ccsio.qubic.ui.panels.gamehistory;

import dev.ccsio.qubic.types.MoveHistory;
import dev.ccsio.qubic.ui.Colours;
import dev.ccsio.qubic.ui.QubicWindow;
import dev.ccsio.qubic.ui.panels.MenuUI;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.List;
import javax.swing.*;

import dev.ccsio.qubic.ui.panels.Render3D;
import org.json.JSONObject;

public class HistorySelect extends JPanel {
    static final String HISTORY_DIR = "src/main/java/dev/ccsio/qubic/gameHistory";
    List<JSONObject> pastGames = new ArrayList<>();
    HistoryViewer historyViewer = HistoryViewer.getInstance();
    Render3D background = Render3D.getInstance();

    public HistorySelect() {
        loadUI();
    }

    public void loadUI() {
        setLayout(new BorderLayout());
        try {
            pastGames = getPastGames();

            // Sort by endTime descending
            pastGames.sort((a, b) -> Long.compare(b.getLong("endTime"), a.getLong("endTime")));

            JPanel tablePanel = new JPanel();
            tablePanel.setOpaque(false);
            tablePanel.setLayout(new GridLayout(0, 5, 0, 0));

            Font headerFont = new Font(Font.MONOSPACED, Font.BOLD, 20);

            JLabel _00 = new JLabel("Game ID");
            JLabel _01 = new JLabel("Mode");
            JLabel _02 = new JLabel("Difficulty");
            JLabel _03 = new JLabel("Duration");
            JLabel _04 = new JLabel("Replay");

            for (JLabel label : Arrays.asList(_00, _01, _02, _03, _04)) {
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setForeground(Color.decode(Colours.CUSTOM_MENU_WHITE));
                label.setFont(headerFont);
                tablePanel.add(label);
            }

            for (JSONObject game : pastGames) {
                JLabel idLabel = new JLabel(game.getString("gameId"));
                JLabel modeLabel = new JLabel(game.getString("mode"));
                JLabel durationLabel = getDurationLabel(game.getLong("duration"));
                JLabel diffLabel = switch (game.optInt("difficulty", -1)) {
                    case 0 -> new JLabel("Random OA");
                    case 1 -> new JLabel("Defensive OA");
                    case 2 -> new JLabel("Strategic OA");
                    case 3 -> new JLabel("Cruel OA");
                    default -> new JLabel("n/a");
                };

                JButton replayButton = MenuUI.getJButton("▶", Colours.CUSTOM_MENU_BLUE);
                replayButton.setPreferredSize(new Dimension(40, 20));
                replayButton.setHorizontalAlignment(SwingConstants.CENTER);

                JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
                buttonWrapper.setOpaque(false);
                buttonWrapper.add(replayButton);

                for (JLabel jLabel : Arrays.asList(idLabel, modeLabel, diffLabel, durationLabel)) {
                    jLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    jLabel.setForeground(Color.decode(Colours.CUSTOM_MENU_WHITE));
                    tablePanel.add(jLabel);
                }

                String winnerText;
                if (game.getString("mode").equals("singleplayer")) {
                    switch (game.optInt("winner", 21)) {
                        case -1 -> winnerText = "You Won";
                        case 0 -> winnerText = "You Drew with the OA";
                        case 1 -> winnerText = "The OA Won";
                        default -> winnerText = "";
                    }
                } else {
                    switch (game.optInt("winner", 21)) {
                        case -1 -> winnerText = "Player 1 Won";
                        case 0 -> winnerText = "You Both Drew";
                        case 1 -> winnerText = "Player 2 Won";
                        default -> winnerText = "";
                    }
                }

                tablePanel.add(buttonWrapper);

                List<MoveHistory.PlayerMove> moves = HistoryViewer.decodedJSON(game.getJSONArray("moveHistory"));
                replayButton.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseEntered(java.awt.event.MouseEvent e) {
                        background.replaceBoard(moves);
                    }
                });
                replayButton.addActionListener(e -> {
                    background.removeAll();
                    historyViewer.showHistory(moves, winnerText);
                });
            }

            JPanel wrapper = new JPanel();
            wrapper.setOpaque(false);
            wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));

            tablePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, (pastGames.size() + 1) * 25));
            wrapper.add(tablePanel);

            JScrollPane scrollPane = new JScrollPane(wrapper);

            scrollPane.setOpaque(false);
            scrollPane.getViewport().setOpaque(false);
            scrollPane.setBorder(BorderFactory.createEmptyBorder());
            scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));

            JPanel buttonPanel = new JPanel();
            JButton menuButton = MenuUI.getJButton("Menu", Colours.CUSTOM_MENU_RED);
            menuButton.addActionListener(event -> {
                background.removeAll();
                QubicWindow.getInstance().showView(new MenuUI());
            });
            buttonPanel.add(menuButton);
            buttonPanel.setOpaque(false);

            this.setOpaque(false);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.BOTH;
            c.gridx = 0;
            c.gridy = 0;
            c.weightx = 1.0;
            c.weighty = 0.95;

            background.setLayout(new GridBagLayout());
            background.add(scrollPane, c);
            c.gridy = 1;
            c.weighty = 0.05;
            background.add(buttonPanel, c);
            background.fixLighting();
            add(background, BorderLayout.CENTER);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static JLabel getDurationLabel(Long duration) {
        if (duration > 31_536_000_000L) return new JLabel(String.format("%.2f years", duration / 31_536_000_000.0));
        if (duration > 2_592_000_000L) return new JLabel(String.format("%.2f months", duration / 2_592_000_000.0));
        if (duration > 604_800_000L) return new JLabel(String.format("%.2f weeks", duration / 604_800_000.0));
        if (duration > 86_400_000L) return new JLabel(String.format("%.2f days", duration / 86_400_000.0));
        if (duration > 3_600_000L) return new JLabel(String.format("%.2f hours", duration / 3_600_000.0));
        if (duration > 60_000L) return new JLabel(String.format("%.2f minutes", duration / 60_000.0));
        if (duration > 1_000L) return new JLabel(String.format("%.2f seconds", duration / 1_000.0));
        return new JLabel(duration + " milliseconds");
    }

    public static List<JSONObject> getPastGames() throws IOException {
        try (var stream = Files.list(Path.of(HISTORY_DIR))) {
            List<JSONObject> pastGames = new ArrayList<>();
            for (Path path : stream
                    .filter(Files::isRegularFile)
                    .filter(p -> p.toString().toLowerCase().endsWith(".json"))
                    .toList()) {
                JSONObject json = readJson(path);
                if (json != null) {
                    pastGames.add(json);
                }
            }
            return pastGames;
        }
    }

    private static JSONObject readJson(Path path) {
        try {
            return new JSONObject(Files.readString(path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}