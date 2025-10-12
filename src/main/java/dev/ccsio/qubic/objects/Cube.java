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
import javafx.scene.transform.Rotate;

public class Cube extends Group {
    Map<Coordinates, Group> gameBoard = new HashMap<>();
    private final Group cubeFramework = new Group();
    private static final double SIZE = 143;
    private static final double THICKNESS = 1;
    private static final double SEPERATION = 36;

    public Cube() {
        Box[] sheets = new Box[9];
        for (int i = 0; i < 3; i++) {
            sheets[i] = new Box(THICKNESS, SIZE, SIZE);
            sheets[i].setTranslateX(SEPERATION * (i + 0.5));
            sheets[i].setTranslateY(-54);
            sheets[i].setTranslateZ(54);
        }
        for (int i = 3; i < 6; i++) {
            sheets[i] = new Box(SIZE, THICKNESS, SIZE);
            sheets[i].setTranslateY(-SEPERATION * (i - 2) + 18);
            sheets[i].setTranslateX(54);
            sheets[i].setTranslateZ(54);
        }
        for (int i = 6; i < 9; i++) {
            sheets[i] = new Box(SIZE, SIZE, THICKNESS);
            sheets[i].setTranslateY(-54);
            sheets[i].setTranslateX(54);
            sheets[i].setTranslateZ(SEPERATION * (i - 5.5));
        }

        // Create nearly invisible material
        PhongMaterial translucent = new PhongMaterial();
        // Very light blue tint with very low opacity
        translucent.setDiffuseColor(new Color(0.2, 0.3, 0.4, 0.03));
        // Remove most of the specular highlight
        translucent.setSpecularColor(new Color(1, 1, 1, 0.01));
        translucent.setSpecularPower(0.1);

        // Apply material and rendering properties to all sheets
        for (Box sheet : sheets) {
            sheet.setMaterial(translucent);
            sheet.setCullFace(CullFace.NONE);
            sheet.setBlendMode(BlendMode.ADD); // Changed to ADD for softer appearance
        }

        // Add all sheets to the framework
        cubeFramework.getChildren().addAll(sheets);

        // Setup the animation
        animateCube(this);
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

        if (piece != null) {
            piece.setTranslateX(coordinates.x());
            piece.setTranslateY(coordinates.y());
            piece.setTranslateZ(coordinates.z());
            gameBoard.put(coordinates, piece);
            this.getChildren().add(piece);
        }

        // Add the cube framework last if not already added
        if (!this.getChildren().contains(cubeFramework)) {
            this.getChildren().add(cubeFramework);
        }
    }

    private void animateCube(Node node) {
        // Create rotation transforms
        Rotate rotX = new Rotate(0, Rotate.X_AXIS);
        Rotate rotY = new Rotate(0, Rotate.Y_AXIS);
        Rotate rotZ = new Rotate(0, Rotate.Z_AXIS);

        rotX.setPivotX(54.5);
        rotX.setPivotY(-54.5);
        rotX.setPivotZ(54.5);

        rotY.setPivotX(54.5);
        rotY.setPivotY(-54.5);
        rotY.setPivotZ(54.5);

        rotZ.setPivotX(54.5);
        rotZ.setPivotY(-54.5);
        rotZ.setPivotZ(54.5);

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
