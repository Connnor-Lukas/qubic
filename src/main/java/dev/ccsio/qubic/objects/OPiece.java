package dev.ccsio.qubic.objects;

import javafx.scene.effect.Bloom;
import javafx.scene.effect.Glow;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.Cylinder;

public class OPiece extends Piece {
    protected void setup() {
        pieceOne = new Cylinder(12.5, 5);
        pieceTwo = new Cylinder(7.5, 5);

        pieceOne.setRotate(90);
        pieceTwo.setRotate(90);

        PhongMaterial neon = new PhongMaterial();
        neon.setDiffuseColor(Color.web("#6666ff"));
        neon.setSpecularColor(Color.web("#9999ff"));
        neon.setSpecularPower(128);

        PhongMaterial transparent = new PhongMaterial();
        transparent.setDiffuseColor(Color.rgb(51, 51, 128));
        transparent.setSpecularColor(Color.rgb(25, 25, 128));

        // Apply material to both
        pieceOne.setMaterial(transparent);
        pieceTwo.setMaterial(neon);

        // Cull opposite faces to simulate a hollow ring
        pieceOne.setCullFace(CullFace.FRONT);

        // Visual effects
        Glow glow = new Glow(1.0);
        Bloom bloom = new Bloom(0.1);
        bloom.setInput(glow);
        pieceOne.setEffect(bloom);
        pieceTwo.setEffect(bloom);
    }
}