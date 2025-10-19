package dev.ccsio.qubic.ui.panels;

import dev.ccsio.qubic.objects.Cube;
import dev.ccsio.qubic.types.Coordinates;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Render3D extends JFXPanel {
    private static Render3D INSTANCE;

    Cube cube;
    PerspectiveCamera camera;
    Group environment;
    Scene scene;
    AmbientLight ambientLight;

    public static Render3D getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Render3D();
        }
        return INSTANCE;
    }

    private Render3D() {
        Platform.runLater(this::initFX);
    }

    private void initFX() {
        environment = new Group();

        // Add the cube
        cube = new Cube();
        environment.getChildren().add(cube);

        // Setup scene with 3D enabled
        scene = new Scene(environment, 800, 600, true);
        scene.setFill(Color.rgb(16, 0, 41, 1));
        
        Platform.setImplicitExit(false);

        // Setup camera
        camera = new PerspectiveCamera(true);
        camera.setNearClip(0.1);
        camera.setFarClip(10000.0);
        camera.setTranslateX(54.5);
        camera.setTranslateY(-54.5);
        camera.setTranslateZ(-500);
        scene.setCamera(camera);

        // Add ambient light
        ambientLight = new AmbientLight(Color.WHITE);
        environment.getChildren().add(ambientLight);

        setScene(scene);
    }

    public void fixLighting() {
        Platform.runLater(() -> {
            environment.getChildren().remove(ambientLight);
            environment.getChildren().add(ambientLight);
        });
    }

    public void setupMenu() {
        Platform.runLater(this::setupMenuPieces);
    }

    private void setupMenuPieces() {
        Random random = new Random();
        List<Coordinates> usedCoordinates = new ArrayList<>();
        int totalMoves = random.nextInt(10, 15);
        int movesX = totalMoves / 2;
        int movesY = totalMoves - movesX;

        for (int i = 0; i < movesX; i++) {
            Coordinates c = Coordinates.random();
            if (!usedCoordinates.contains(c)) {
                cube.addPiece(-1, c);
                usedCoordinates.add(c);
            } else {
                i--;
            }
        }

        for (int i = 0; i < movesY; i++) {
            Coordinates c = Coordinates.random();
            if (!usedCoordinates.contains(c)) {
                cube.addPiece(1, c);
                usedCoordinates.add(c);
            } else {
                i--;
            }
        }
    }

    public void resetBoard() {
        cube.reset();
    }

    public void makeMove(int player, Coordinates coordinates) {
        Platform.runLater(() -> {
            cube.addPiece(player, coordinates);
        });
    }

    public void enableMouseControls() {
        Platform.runLater(() -> {
            cube.gameSetup();
        });
    }

    public void previewMove(int player, Coordinates coordinates) {
        Platform.runLater(() -> {
            cube.movePreviewPiece(player, coordinates);
        });
    }
}