package dev.ccsio.qubic.objects;

import dev.ccsio.qubic.Main;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.shape.Shape3D;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.util.Random;

public abstract class Piece extends Group {
    protected static final Random RANDOM = new Random();
    protected Shape3D pieceOne;
    protected Shape3D pieceTwo;

    public Piece() {
        setup();

        if (Main.useAlternateRender) {
            pieceOne.setDepthTest(DepthTest.DISABLE);
            pieceTwo.setDepthTest(DepthTest.DISABLE);
        }

        getChildren().addAll(pieceOne, pieceTwo);
        rotatePiece();
    }

    protected abstract void setup();

    protected final void rotatePiece() {
        RotateTransition spin = new RotateTransition(Duration.seconds(RANDOM.nextDouble(3, 6)), this);
        spin.setCycleCount(Animation.INDEFINITE);
        spin.setByAngle(360);
        spin.setAxis(Rotate.Y_AXIS);
        spin.setInterpolator(Interpolator.LINEAR);
        spin.play();
    }
}
