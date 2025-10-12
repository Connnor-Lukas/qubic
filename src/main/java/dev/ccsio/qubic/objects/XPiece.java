package dev.ccsio.qubic.objects;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.Group;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Glow;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class XPiece extends Group {
    Box rectangleOne = new Box(5, 25, 5);
    Box rectangleTwo = new Box(5, 25, 5);

    public XPiece() {
        rectangleOne.setRotate(45);
        rectangleTwo.setRotate(-45);

        rectangleOne.setTranslateX(-0.5);
        rectangleTwo.setTranslateX(0.5);

        PhongMaterial neon = new PhongMaterial();
        neon.setDiffuseColor(Color.web("#ffff66"));    // bright yellow
        neon.setSpecularColor(Color.web("#ffffcc"));   // soft whitish-yellow highlight
        neon.setSpecularPower(128);                    // very glossy

        rectangleOne.setMaterial(neon);
        rectangleTwo.setMaterial(neon);

        Glow glow = new Glow(1.0);  // 0–1, higher = more glow intensity
        Bloom bloom = new Bloom(0.1); // lower = more blooming
        bloom.setInput(glow);

        rectangleOne.setEffect(bloom);
        rectangleTwo.setEffect(bloom);

        getChildren().addAll(rectangleOne, rectangleTwo);

        syncRotate();
    }

    private void syncRotate() {
        RotateTransition spin = new RotateTransition(Duration.seconds(4), this);
        spin.setCycleCount(Animation.INDEFINITE);
        spin.setByAngle(360);
        spin.setAxis(Rotate.Y_AXIS);
        spin.setInterpolator(Interpolator.LINEAR);
        spin.play();
    }
}
