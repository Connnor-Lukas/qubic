package dev.ccsio.qubic.objects;

import dev.ccsio.qubic.game.Coordinates;
import java.util.HashMap;
import java.util.Map;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.transform.Rotate;

public class Cube extends Group {
    Map<Coordinates, Group> gameBoard = new HashMap<>();
    Box theCube = new Box(150, 150, 150);

    public Cube() {
        // Set material with proper transparency
        PhongMaterial translucent = new PhongMaterial();
        translucent.setDiffuseColor(new Color(0.5, 0.6, 0.7, 0.15)); // Reduced alpha to 0.15
        translucent.setSpecularColor(new Color(1, 1, 1, 0.2));
        translucent.setSpecularPower(0);
        theCube.setMaterial(translucent);

        // Set proper transparency rendering modes
        theCube.setDrawMode(DrawMode.FILL);
        theCube.setCullFace(CullFace.NONE);  // Changed from BACK to NONE
        theCube.setBlendMode(BlendMode.ADD); // Changed from SRC_OVER to ADD

        // Position the box at center
        theCube.setTranslateX(0);
        theCube.setTranslateY(0);
        theCube.setTranslateZ(0);

        animateCube(this);

        // Add the box to the root
        this.getChildren().add(theCube);
    }

    public void addPiece(int player, Coordinates coordinates) {
        Group piece = new Group();

        switch (player) {
            case -1:
                piece = new XPiece();
                break;
            case 0:
                piece = null;
                break;
            case 1:
                piece = new OPiece();
                break;
        }

        gameBoard.put(coordinates, piece);
        piece.setTranslateX(coordinates.x());
        piece.setTranslateY(coordinates.y());
        piece.setTranslateZ(coordinates.z());
        this.getChildren().add(piece);
    }

    private void animateCube(Node node) {
        // Create rotation transforms
        Rotate rotX = new Rotate(0, Rotate.X_AXIS);
        Rotate rotY = new Rotate(0, Rotate.Y_AXIS);
        Rotate rotZ = new Rotate(0, Rotate.Z_AXIS);

        node.getTransforms().addAll(rotX, rotY, rotZ);

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
    }
}
