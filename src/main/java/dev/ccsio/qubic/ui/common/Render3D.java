package dev.ccsio.qubic.ui.common;

import dev.ccsio.qubic.game.Coordinates;
import dev.ccsio.qubic.objects.Cube;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;

public class Render3D extends JFXPanel {
    public Render3D() {
        setLayout(null);

        // Initialize JavaFX scene on JavaFX thread
        Platform.runLater(this::initFX);
    }

    private void initFX() {
        Group enviroment = new Group();

        // createTestBox();
        Cube cube = new Cube();
        cube.addPiece(-1, new Coordinates(0, 0, 0));
        enviroment.getChildren().add(cube);

        // Setup scene with 3D enabled
        Scene scene = new Scene(enviroment, 800, 600, true);
        scene.setFill(Color.rgb(16, 0, 41, 1));

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
        enviroment.getChildren().add(ambientLight);

        /*
        // Add point light for reflections
        PointLight pointLight = new PointLight(Color.WHITE);
        pointLight.setTranslateX(100);
        pointLight.setTranslateY(-100);
        pointLight.setTranslateZ(-200);
        enviroment.getChildren().add(pointLight);
         */

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