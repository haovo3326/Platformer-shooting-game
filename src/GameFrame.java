import CustomMath.Vector2;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private GameFrame() {

        setTitle("My first game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        Vector2 frameSize = new Vector2(1366, 768);
        setSize((int) frameSize.x, (int) frameSize.y);
        setLocationRelativeTo(null);

        GamePanel gamePanel = new GamePanel(frameSize);
        add(gamePanel);

        setVisible(true);
    }

    static void main(String[] args) {
        SwingUtilities.invokeLater(GameFrame::new);
    }
}