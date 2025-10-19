package dev.ccsio.qubic.objects;

import dev.ccsio.qubic.types.Coordinates;
import java.util.HashMap;
import java.util.Map;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.CullFace;
import javafx.scene.transform.Rotate;

public class Cube extends Group {
    Map<Coordinates, Group> gameBoard = new HashMap<>();
    private final Group cubeBoard = new Group();
    private static final double SIZE = 143;
    private static final double THICKNESS = 1;
    private static final double SEPERATION = 36;

    private AnimationTimer automaticRotation;
    private AnimationTimer controlledRotation;
    private Rotate rotX;
    private Rotate rotY;

    private boolean userRotating = false;
    private double anchorX, anchorY;
    private double anchorAngleX, anchorAngleY;
    private static final int maxViewDistance = 500;
    private static final int minViewDistance = -100;

    public Cube() {
        Box[] sheets = new Box[9];
        for (int i = 0; i < 3; i++) {
            sheets[i] = new Box(THICKNESS, SIZE, SIZE);
            sheets[i].setTranslateX(SEPERATION * (i + 0.5));
            sheets[i].setTranslateY(-54);
            sheets[i].setTranslateZ(-54);
        }
        for (int i = 3; i < 6; i++) {
            sheets[i] = new Box(SIZE, THICKNESS, SIZE);
            sheets[i].setTranslateY(-SEPERATION * (i - 2) + 18);
            sheets[i].setTranslateX(54);
            sheets[i].setTranslateZ(-54);
        }
        for (int i = 6; i < 9; i++) {
            sheets[i] = new Box(SIZE, SIZE, THICKNESS);
            sheets[i].setTranslateY(-54);
            sheets[i].setTranslateX(54);
            sheets[i].setTranslateZ(-SEPERATION * (i - 5.5));
        }

        // Translucent material (still issues but good enough)
        PhongMaterial translucent = new PhongMaterial();
        translucent.setDiffuseColor(new Color(0.1, 0.1, 0.1, 0.25));
        translucent.setSpecularColor(new Color(0.05, 0.05, 0.05, 0.02));
        translucent.setSpecularPower(1.5);

        // Apply material and rendering properties to all sheets
        for (Box sheet : sheets) {
            sheet.setMaterial(translucent);
            sheet.setCullFace(CullFace.NONE);
            sheet.setBlendMode(BlendMode.MULTIPLY);
            sheet.setMouseTransparent(true);
            sheet.setDepthTest(javafx.scene.DepthTest.ENABLE);
        }

        // Invisible draggable box
        Box interactionBox = new Box(SIZE * 1.5, SIZE * 1.5, SIZE * 1.5);
        interactionBox.setMaterial(new PhongMaterial(Color.TRANSPARENT));
        interactionBox.setCullFace(CullFace.NONE);
        interactionBox.setMouseTransparent(false); // must capture mouse

        // Add all sheets to the board
        cubeBoard.getChildren().addAll(sheets);
        this.getChildren().addAll(cubeBoard, interactionBox);

        automaticRotation();
        controlledRotation();
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
            piece.setTranslateX(coordinates.getX() * 36);
            piece.setTranslateY(coordinates.getZ() * -36);
            piece.setTranslateZ(coordinates.getY() * -36);
            gameBoard.put(coordinates, piece);
            this.getChildren().add(piece);
        }
    }

    public void reset() {
        for (Group piece : gameBoard.values()) {
            this.getChildren().remove(piece);
        }
        gameBoard.clear();
    }

    private void automaticRotation() {
        Node node = this;

        // Create rotation transforms
        rotX = new Rotate(0, Rotate.X_AXIS);
        rotY = new Rotate(0, Rotate.Y_AXIS);

        rotX.setPivotX(54.5);
        rotX.setPivotY(-54.5);
        rotX.setPivotZ(-54.5);

        rotY.setPivotX(54.5);
        rotY.setPivotY(-54.5);
        rotY.setPivotZ(-54.5);

        node.getTransforms().addAll(rotX, rotY);

        // Create animation
        automaticRotation = new AnimationTimer() {
            private long lastUpdate = 0;
            private double angleX = 0;
            private double angleY = 0;

            // Rotation speeds (degrees per second)
            private double speedX = 19.0 / 5.0;
            private double speedY = 83 / 5.0;

            // Distance
            private double distance = 0;
            private double cameraSpeed = 53.0 / 5.0;
            private double maxDistance = 200;
            private double minDistance = -100;

            @Override
            public void handle(long now) {
                if (lastUpdate == 0) {
                    lastUpdate = now;
                    return;
                }

                // Calculate time delta in seconds
                double deltaTime = (now - lastUpdate) / 1_000_000_000.0;
                lastUpdate = now;

                angleX = angleX % 360 + speedX * deltaTime;
                if (angleX > 90 || angleX < -90) {
                    speedX *= -1;
                }

                angleY = (angleY + speedY * deltaTime) % 360;

                distance = distance + cameraSpeed * deltaTime;
                if (distance > maxDistance ||  distance < minDistance) {
                    cameraSpeed *= -1;
                }

                // Apply rotations
                rotX.setAngle(angleX);
                rotY.setAngle(angleY);

                node.setTranslateZ(distance);
            }
        };

        automaticRotation.start();
    }

    private void controlledRotation() {
        // Create animation
        controlledRotation = new AnimationTimer() {
            private long lastUpdate = 0;
            private double angleY = 0;

            // Rotation speeds (degrees per second)
            private double speedY = 83 / 5.0;

            @Override
            public void handle(long now) {
                if (userRotating) {
                    lastUpdate = now;
                    angleY = rotY.getAngle() % 360;
                    return;
                }

                if (lastUpdate == 0) {
                    lastUpdate = now;
                    return;
                }

                // Calculate time delta in seconds
                double deltaTime = (now - lastUpdate) / 1_000_000_000.0;
                lastUpdate = now;

                angleY = (angleY + speedY * deltaTime) % 360;

                // Apply rotations
                rotY.setAngle(angleY);
            }
        };
    }

    public void initMouseControl() {
        automaticRotation.stop();
        controlledRotation.start();

        this.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            userRotating = true;
            anchorX = e.getSceneX();
            anchorY = e.getSceneY();
            anchorAngleX = rotX.getAngle();
            anchorAngleY = rotY.getAngle();
        });

        this.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
            double deltaX = e.getSceneX() - anchorX;
            double deltaY = e.getSceneY() - anchorY;

            double angleX = (anchorAngleX + deltaY * 0.5) % 360;
            double angleY = (anchorAngleY - deltaX * 0.5) % 360;

            rotY.setAngle(angleY);
            if (angleX < 90 && angleX > -90 || angleX > 270) {
                rotX.setAngle(angleX);
            }
        });

        this.addEventHandler(ScrollEvent.SCROLL, e -> {
            double delta = e.getDeltaY(); // +ve up, -ve down
            if (delta > 0) {
                this.setTranslateZ(Math.min(this.getTranslateZ() + delta * 0.5, maxViewDistance));
            } else {
                this.setTranslateZ(Math.max(this.getTranslateZ() + delta * 0.5, minViewDistance));
            }
        });

        this.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
            userRotating = false;
        });
    }
}
