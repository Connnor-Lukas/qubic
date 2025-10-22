package dev.ccsio.qubic.objects;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Glow;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class OPiece extends Group {
    Cylinder outerCircle = new Cylinder(12.5, 5);
    Cylinder innerCircle = new Cylinder(7.5, 5);

    public OPiece() {
        outerCircle.setRotate(90);
        innerCircle.setRotate(90);

        PhongMaterial neon = new PhongMaterial();
        neon.setDiffuseColor(Color.web("#6666ff"));
        neon.setSpecularColor(Color.web("#9999ff"));
        neon.setSpecularPower(128);

        PhongMaterial transparent = new PhongMaterial();
        transparent.setDiffuseColor(Color.rgb(51, 51, 128));
        transparent.setSpecularColor(Color.rgb(25, 25, 128));

        // Apply material to both
        outerCircle.setMaterial(transparent);
        innerCircle.setMaterial(neon);

        // Cull opposite faces to simulate a hollow ring
        outerCircle.setCullFace(CullFace.FRONT);

        // Visual effects
        Glow glow = new Glow(1.0);
        Bloom bloom = new Bloom(0.1);
        bloom.setInput(glow);
        outerCircle.setEffect(bloom);
        innerCircle.setEffect(bloom);

        getChildren().addAll(outerCircle, innerCircle);

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