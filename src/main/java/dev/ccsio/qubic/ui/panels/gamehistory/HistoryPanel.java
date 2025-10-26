package dev.ccsio.qubic.ui.panels.gamehistory;

import dev.ccsio.qubic.game.GameMaster;
import dev.ccsio.qubic.game.LinkedHistory;
import dev.ccsio.qubic.types.MoveHistory;
import dev.ccsio.qubic.ui.Colours;
import javax.swing.*;
import java.awt.*;

public class HistoryPanel extends JPanel {
    public static HistoryPanel INSTANCE;

    MoveHistory moveHistory = LinkedHistory.getMoveHistory(GameMaster.getInstance().getGameBoard());

    public static HistoryPanel getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HistoryPanel();
        }
        return INSTANCE;
    }

    private HistoryPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel titleLabel = new JLabel("Move History:");
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 20));
        titleLabel.setForeground(Color.white);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(titleLabel);
    }

    public void refresh() {
        MoveHistory.PlayerMove lastMove = moveHistory.getLastMove();
        int x = lastMove.coordinates().getX();
        int y = lastMove.coordinates().getY();
        int z = lastMove.coordinates().getZ();
        JLabel label = new JLabel();
        switch (lastMove.player()) {
            case -1:
                label.setText("x -> " + x + ", " + y + ", " + z);
                label.setForeground(Color.decode(Colours.CUSTOM_3D_YELLOW));
                break;
            case 1:
                label.setText("o -> " + x + "," + y + "," + z);
                label.setForeground(Color.decode(Colours.CUSTOM_3D_BLUE));
                break;
            default:
                label.setText("BROKEN PLS FIX");
                label.setForeground(Color.white);
                break;
        }
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font(label.getFont().getName(), Font.BOLD, 15));
        this.add(label);
    }

    public void reset() {
        INSTANCE = new HistoryPanel();
    }
}
