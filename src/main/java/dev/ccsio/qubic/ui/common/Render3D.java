package dev.ccsio.qubic.ui.common;

import dev.ccsio.qubic.game.Coordinates;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.embed.swing.JFXPanel;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;

public class Render3D extends JFXPanel {

    private Group root;
    private Scene scene;
    private Box testBox;

    public Render3D() {
        setLayout(null);

        // Initialize JavaFX scene on JavaFX thread
        Platform.runLater(this::initFX);
    }

    private void initFX() {
        root = new Group();

        createTestBox();

        // Setup scene with 3D enabled
        scene = new Scene(root, 800, 600, true);
        scene.setFill(Color.DARKGRAY);

        // Setup camera
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setNearClip(0.1);
        camera.setFarClip(10000.0);
        camera.setTranslateZ(-500);
        scene.setCamera(camera);

        // Add ambient light
        AmbientLight ambientLight = new AmbientLight(Color.WHITE);
        root.getChildren().add(ambientLight);

        // Add point light for reflections
        PointLight pointLight = new PointLight(Color.WHITE);
        pointLight.setTranslateX(100);
        pointLight.setTranslateY(-100);
        pointLight.setTranslateZ(-200);
        root.getChildren().add(pointLight);

        setScene(scene);
    }

    private void createTestBox() {
        // Create a cube
        testBox = new Box(150, 150, 150);

        // Set material
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.DODGERBLUE);
        material.setSpecularColor(Color.LIGHTBLUE);
        material.setSpecularPower(20.0);
        testBox.setMaterial(material);

        // Position the box at center
        testBox.setTranslateX(0);
        testBox.setTranslateY(0);
        testBox.setTranslateZ(0);

        // Create rotation transforms
        Rotate rotX = new Rotate(0, Rotate.X_AXIS);
        Rotate rotY = new Rotate(0, Rotate.Y_AXIS);
        Rotate rotZ = new Rotate(0, Rotate.Z_AXIS);

        testBox.getTransforms().addAll(rotX, rotY, rotZ);

        // Create animation
        AnimationTimer rotationTimer = new AnimationTimer() {
            private long lastUpdate = 0;
            private double angleX = 0;
            private double angleY = 0;
            private double angleZ = 0;

            // Rotation speeds (degrees per second)
            private double speedX = 73.0 / 5.0;
            private double speedY = 79.0 / 5.0;
            private double speedZ = 83.0 / 5.0;

            @Override
            public void handle(long now) {
                if (lastUpdate == 0) {
                    lastUpdate = now;
                    return;
                }

                // Calculate time delta in seconds
                double deltaTime = (now - lastUpdate) / 1_000_000_000.0;
                lastUpdate = now;

                // Update angles based on speed and time
                angleX += speedX * deltaTime;
                angleY += speedY * deltaTime;
                angleZ += speedZ * deltaTime;

                // Apply rotations
                rotX.setAngle(angleX);
                rotY.setAngle(angleY);
                rotZ.setAngle(angleZ);
            }
        };

        rotationTimer.start();

        // Add the box to the root
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

    private static class RotAxis {
        private Rotate rotX;
        private Rotate rotY;
        private Rotate rotZ;

        private int x;
        private int y;
        private int z;

        RotAxis(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;

            rotX = new Rotate(x, Rotate.X_AXIS);
            rotY = new Rotate(y, Rotate.Y_AXIS);
            rotZ = new Rotate(z, Rotate.Z_AXIS);
        }

        public DoubleProperty getRotX() {
            return rotX.angleProperty();
        }

        public DoubleProperty getRotY() {
            return rotY.angleProperty();
        }

        public DoubleProperty getRotZ() {
            return rotZ.angleProperty();
        }

        public void upInts(int xIncrease, int yIncrease, int zIncrease) {
            x = (x + xIncrease);
            rotX.setAngle(x);

            y = (y + yIncrease);
            rotY.setAngle(y);

            z = (z + zIncrease);
            rotZ.setAngle(z);
        }
    }
}