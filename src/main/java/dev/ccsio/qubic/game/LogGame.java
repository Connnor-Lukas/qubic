package dev.ccsio.qubic.game;

import dev.ccsio.qubic.types.Coordinates;
import dev.ccsio.qubic.types.MoveHistory;
import dev.ccsio.qubic.types.MoveHistory.PlayerMove;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class LogGame {
    private long startTime;
    private long endTime;

    public LogGame() {}

    public void start() {
        startTime = System.currentTimeMillis();
    }

    private JSONArray JSONifyMoveHistory(MoveHistory moveHistory) {
        JSONArray moveHistoryJSON = new JSONArray();

        for (PlayerMove move : moveHistory.list()) {
            JSONObject tempObj = new JSONObject();
            tempObj.put("mark", move.player());
            tempObj.put("x", move.coordinates().getX());
            tempObj.put("y", move.coordinates().getY());
            tempObj.put("z", move.coordinates().getZ());
            moveHistoryJSON.put(tempObj);
        }

        return moveHistoryJSON;
    }

    private String formatDuration() {
        long diff = endTime - startTime;
        long hours = diff / 3600000;
        long minutes = (diff % 3600000) / 60000;
        long seconds = (diff % 60000) / 1000;
        long millis = diff % 1000;

        return String.format("%02dh%02dm%02ds%03d", hours, minutes, seconds, millis);
    }

    private JSONObject createJsonObject(
        String id,
        int winner,
        String mode,
        int difficulty,
        GameBoard gameBoard
    ) {
        JSONObject json = new JSONObject();

        json.put("gameId", id);
        json.put("winner", winner);
        json.put("startTime", startTime);
        json.put("endTime", endTime);
        json.put("duration", endTime - startTime);
        json.put("durationFormatted", formatDuration());

        if (difficulty != -1) {
            json.put("difficulty", difficulty);
        }

        json.put("mode", mode.equals("sp") ? "singleplayer" : "two-player");

        List<Coordinates> winLine = gameBoard.getWinningLine();
        JSONArray winLineJSON = new JSONArray();
        for (Coordinates coordinates : winLine) {
            JSONObject tempObj = new JSONObject();
            tempObj.put("x", coordinates.getX());
            tempObj.put("y", coordinates.getY());
            tempObj.put("z", coordinates.getZ());
            winLineJSON.put(tempObj);
        }
        json.put("winLine", winLineJSON);

        json.put("moveHistory",
                JSONifyMoveHistory(LinkedHistory.getMoveHistory(gameBoard)));

        return json;
    }

    public void handleWin(int winner, String mode, int difficulty, GameBoard gameBoard) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
            String id = LocalDateTime.now().format(formatter);

            Path appDataDir = getAppDataDir();
            Path filePath = appDataDir.resolve(id + ".json");

            // Ensure directories exist
            Files.createDirectories(appDataDir);

            endTime = System.currentTimeMillis();
            JSONObject jsonData = createJsonObject(id, winner, mode, difficulty, gameBoard);

            // Write the JSON file
            Files.writeString(filePath, jsonData.toString(2));

            System.out.println("Game log saved at: " + filePath.toAbsolutePath());

        } catch (IOException e) {
            System.out.println("Json File could not be created.");
            e.printStackTrace();
        }
    }

    public static Path getAppDataDir() {
        String os = System.getProperty("os.name").toLowerCase();
        String appName = "Qubic"; // change to your app’s name
        String baseDir;

        if (os.contains("win")) {
            baseDir = System.getenv("APPDATA");
            if (baseDir == null) baseDir = System.getProperty("user.home") + "\\AppData\\Roaming";
            return Path.of(baseDir, appName);
        } else if (os.contains("mac")) {
            baseDir = System.getProperty("user.home") + "/Library/Application Support";
            return Path.of(baseDir, appName);
        } else {
            // Linux / UNIX
            baseDir = System.getenv("XDG_DATA_HOME");
            if (baseDir == null || baseDir.isEmpty()) {
                baseDir = System.getProperty("user.home") + "/.local/share";
            }
            return Path.of(baseDir, appName);
        }
    }
}