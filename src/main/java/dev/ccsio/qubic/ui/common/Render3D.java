package dev.ccsio.qubic.ui.common;

import dev.ccsio.qubic.game.Coordinates;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import javafx.scene.AmbientLight;

public class Render3D extends JFXPanel {

    private Group root;
    private Scene scene;
    private Box testBox;

    public Render3D() {
        // Initialize JavaFX scene on JavaFX thread
        Platform.runLater(this::initFX);
    }

    private void initFX() {
        root = new Group();

        // Create test 3D content
        createTestBox();

        // Setup scene with 3D enabled
        scene = new Scene(root, 800, 600, true);
        scene.setFill(Color.DARKGRAY);  // Darker background

        // Setup camera
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-300);  // Move camera closer
        scene.setCamera(camera);

        // Add ambient light
        AmbientLight light = new AmbientLight(Color.WHITE);
        root.getChildren().add(light);

        setScene(scene);
    }

    private void createTestBox() {
        // Create a rotating box as placeholder
        testBox = new Box(100, 100, 100);  // Slightly smaller box

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.DODGERBLUE);
        material.setSpecularColor(Color.WHITE);  // Brighter specular highlight
        testBox.setMaterial(material);

        // Add rotation animation
        RotateTransition rotation = new RotateTransition(Duration.seconds(3), testBox);
        rotation.setAxis(Rotate.Y_AXIS);
        rotation.setByAngle(360);
        rotation.setCycleCount(RotateTransition.INDEFINITE);
        rotation.play();

        root.getChildren().add(testBox);
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