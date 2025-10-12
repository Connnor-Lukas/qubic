package dev.ccsio.qubic.ui.common;

import dev.ccsio.qubic.game.Coordinates;
import dev.ccsio.qubic.objects.Cube;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

public class Render3D extends JFXPanel {
    public Render3D() {
        setLayout(null);

        // Initialize JavaFX scene on JavaFX thread
        Platform.runLater(this::initFX);
    }

    private void initFX() {
        Group environment = new Group();

        // Add the cube
        Cube cube = new Cube();
        cube.addPiece(-1, new Coordinates(1, 0, 0));
        cube.addPiece(-1, new Coordinates(1, 0, 3));
        environment.getChildren().add(cube);

        // Setup scene with 3D enabled
        Scene scene = new Scene(environment, 800, 600, true);
        scene.setFill(Color.rgb(16, 0, 41, 1));
        
        // Add depth buffer settings
        Platform.setImplicitExit(false);

        // Setup camera
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setNearClip(0.1);
        camera.setFarClip(10000.0);
        camera.setTranslateX(54.5);
        camera.setTranslateY(-54.5);
        camera.setTranslateZ(-500);
        scene.setCamera(camera);

        // Add ambient light
        AmbientLight ambientLight = new AmbientLight(Color.WHITE);
        environment.getChildren().add(ambientLight);

        setScene(scene);
    }

    public void resetBoard() {
        Platform.runLater(() -> {
            System.out.println("3D Board reset!");
            // TODO: Reset game board state
        });
    }

    public void makeMove(Coordinates coordinates, boolean isPlayerOne) {
        Platform.runLater(() -> {
            System.out.println(
                    "Move made at: " + coordinates.x() + ", " + coordinates.y() + ", " + coordinates.z()
            );
            // TODO: Update 3D visualization
        });
    }
}