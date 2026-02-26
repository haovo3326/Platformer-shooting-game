import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private GameFrame() {

        setTitle("My first game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Get usable screen size (excludes taskbar)
        Rectangle usable = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getMaximumWindowBounds();

        setBounds(usable);

        GamePanel gamePanel = new GamePanel();
        add(gamePanel);

        setVisible(true);
        System.out.println(usable.toString());
    }

    static void main(String[] args) {
        SwingUtilities.invokeLater(GameFrame::new);
    }
}