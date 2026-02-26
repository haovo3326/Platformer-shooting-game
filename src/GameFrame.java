import CustomMath.Vector2;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    public GameFrame(Vector2 requestRes, boolean windowed) {

        setTitle("My first game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        if(windowed){
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            if(requestRes.x <= screenSize.width) {
                setSize((int) requestRes.x, (int) requestRes.y);
                setLocationRelativeTo(null);
                GamePanel gamePanel = new GamePanel(requestRes);
                add(gamePanel);
                setVisible(true);
            } else {
                setSize(screenSize.width, screenSize.height);
                setLocationRelativeTo(null);
                GamePanel gamePanel = new GamePanel(new Vector2(screenSize.width, screenSize.height));
                add(gamePanel);
                setVisible(true);
            }
        } else {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            GamePanel gamePanel = new GamePanel(new Vector2(screenSize.width, screenSize.height));
            add(gamePanel);
            setVisible(true);
        }
    }

    static void main(String[] args) {
        new GameFrame(new Vector2(1366, 768), true);
    }
}