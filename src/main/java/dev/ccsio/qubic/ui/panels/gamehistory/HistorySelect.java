package dev.ccsio.qubic.ui.panels.gamehistory;

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
    QubicWindow qubicWindow = QubicWindow.getInstance();
    Render3D background = Render3D.getInstance();

    public HistorySelect() {
        loadUI();
    }

    private void loadUI() {
        setLayout(new BorderLayout());
        try {
            pastGames = getPastGames();

            // Sort by endTime descending
            pastGames.sort((a, b) -> Long.compare(b.getLong("endTime"), a.getLong("endTime")));

            JPanel tablePanel = new JPanel();
            tablePanel.setLayout(new GridLayout(0, 5, 0, 0));

            JLabel _00 = new JLabel("Game ID");
            JLabel _01 = new JLabel("Mode");
            JLabel _02 = new JLabel("Difficulty");
            JLabel _03 = new JLabel("Duration");
            JLabel _04 = new JLabel("Replay");

            _00.setHorizontalAlignment(SwingConstants.CENTER);
            _01.setHorizontalAlignment(SwingConstants.CENTER);
            _02.setHorizontalAlignment(SwingConstants.CENTER);
            _03.setHorizontalAlignment(SwingConstants.CENTER);
            _04.setHorizontalAlignment(SwingConstants.CENTER);

            tablePanel.add(_00);
            tablePanel.add(_01);
            tablePanel.add(_02);
            tablePanel.add(_03);
            tablePanel.add(_04);

            for (JSONObject game : pastGames) {
                JLabel idLabel = new JLabel(game.getString("gameId"));
                JLabel modeLabel = new JLabel(game.getString("mode"));
                JLabel durationLabel = getDurationLabel(game.getLong("duration"));
                JLabel diffLabel = switch (game.optInt("difficulty", -1)) {
                    case 0 -> new JLabel("Random OA");
                    case 1 -> new JLabel("Defensive OA");
                    case 2 -> new JLabel("Strategic OA");
                    case 3 -> new JLabel("Cruel OA");
                    default -> new JLabel("");
                };

                JButton replayButton = MenuUI.getJButton("▶", Colours.CUSTOM_MENU_BLUE);
                replayButton.setPreferredSize(new Dimension(40, 20));

                JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
                buttonWrapper.add(replayButton);

                idLabel.setHorizontalAlignment(SwingConstants.CENTER);
                modeLabel.setHorizontalAlignment(SwingConstants.CENTER);
                durationLabel.setHorizontalAlignment(SwingConstants.CENTER);
                diffLabel.setHorizontalAlignment(SwingConstants.CENTER);
                replayButton.setHorizontalAlignment(SwingConstants.CENTER);

                tablePanel.add(idLabel);
                tablePanel.add(modeLabel);
                tablePanel.add(diffLabel);
                tablePanel.add(durationLabel);
                tablePanel.add(buttonWrapper);
            }

            JPanel wrapper = new JPanel();
            wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));

            tablePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, (pastGames.size() + 1) * 25)); // restrict height
            wrapper.add(tablePanel);

            JScrollPane scrollPane = new JScrollPane(wrapper);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            background.add(scrollPane);
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