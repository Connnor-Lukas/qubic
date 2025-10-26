package dev.ccsio.qubic.objects;

import javafx.scene.effect.Bloom;
import javafx.scene.effect.Glow;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class XPiece extends Piece {
    protected void setup() {
        pieceOne = new Box(5, 25, 5);
        pieceTwo = new Box(5, 25, 5);

        pieceOne.setRotate(45);
        pieceTwo.setRotate(-45);

        pieceOne.setTranslateX(-0.5);
        pieceTwo.setTranslateX(0.5);

        PhongMaterial neon = new PhongMaterial();
        neon.setDiffuseColor(Color.web("#ffff66"));
        neon.setSpecularColor(Color.web("#ffffcc"));
        neon.setSpecularPower(128);

        pieceOne.setMaterial(neon);
        pieceTwo.setMaterial(neon);

        Glow glow = new Glow(1.0);
        Bloom bloom = new Bloom(0.1);
        bloom.setInput(glow);

        pieceOne.setEffect(bloom);
        pieceTwo.setEffect(bloom);
    }
}
