package dev.ccsio.qubic.game;


import org.json.JSONObject;
import org.json.JSONArray;

import dev.ccsio.qubic.types.Coordinates;
import dev.ccsio.qubic.types.MoveHistory;
import dev.ccsio.qubic.types.MoveHistory.PlayerMove;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.File;
import java.io.IOException;

public class LogGame {
    long startTime;
    long endTime;
    
    public LogGame() {}

    public void start() {
        startTime = System.currentTimeMillis();
    }

    private JSONArray JSONifyMoveHistory(MoveHistory moveHistory) {
        JSONArray moveHistoryJSON = new JSONArray();
        
        JSONObject tempObj;
        for (PlayerMove move : moveHistory.list()) {
            tempObj = new JSONObject();
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
        String id, int winner, String mode, int difficulty, GameBoard gameBoard) {
        
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
        
        json.put("mode", mode == "sp" ? "singleplayer" : "two-player");


        List<Coordinates> winLine = gameBoard.getWinningLine();
        JSONArray winLineJSON = new JSONArray();
        JSONObject tempObj;
        for (Coordinates coordinates : winLine) {
            tempObj = new JSONObject();
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


    public void handleWin( int winner, String mode, int difficulty, GameBoard gameBoard) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
            String id = LocalDateTime.now().format(formatter);
            String fileName = "src\\main\\java\\dev\\ccsio\\qubic\\gameHistory\\" + id + ".json"; 
            
            // File jsonFile = new File(fileName);

            // jsonFile.createNewFile();

            endTime = System.currentTimeMillis();
            JSONObject jsonData = createJsonObject(id, winner, mode, difficulty, gameBoard);
            
            Files.writeString(Paths.get(fileName), jsonData.toString());

        } catch (IOException e) {
            System.out.println("Json File could not be created.");
            System.out.println(e);
        }


    }
}
