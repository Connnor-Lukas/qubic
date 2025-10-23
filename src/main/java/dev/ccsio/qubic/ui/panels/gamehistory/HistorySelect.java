package dev.ccsio.qubic.ui.panels.gamehistory;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import org.json.JSONObject;

public class HistorySelect extends JPanel {
    private static final String HISTORY_DIR = "src/main/java/dev/ccsio/qubic/gameHistory";

    public HistorySelect() {
        loadHistory();
    }

    private void loadHistory() {
        this.setLayout(new GridBagLayout());
    }

    public static List<JSONObject> getPastGames() throws IOException {
        try (var stream = Files.list(Path.of(HISTORY_DIR))) {
            List<JSONObject> pastGames = new ArrayList<>();
            for (Path path : stream
                .filter(Files::isRegularFile)
                .filter(p -> p.toString().toLowerCase().endsWith(".json")) // optional filter
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

    public static void main(String[] args) throws IOException {
        List<JSONObject> files = getPastGames();
        files.forEach(System.out::println);
    }
}
