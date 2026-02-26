import Camera.ChasingCamera;
import CustomMath.Vector2;
import Maps.GameMap;
import Maps.Map;
import PlayerManager.Player;
import PlayerManager.PlayerController;
import Weapon.Gun;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements KeyListener {

    private Thread gameThread;
    private volatile boolean running = false;

    private static final int FPS = 120;
    private static final long NS_PER_FRAME = 1_000_000_000L / FPS;

    public GamePanel() {
        setBackground(new Color(30, 30, 30));
        setFocusable(true);
        addKeyListener(this);
        requestFocusInWindow();
    }

    public void startGame() {
        if (running) return;

        running = true;
        gameThread = new Thread(this::gameLoop, "GameLoopThread");
        gameThread.start();
    }

    public void stopGame() {
        running = false;
    }

    private void gameLoop() {
        init(); // one-time setup

        long last = System.nanoTime();

        while (running) {
            long now = System.nanoTime();

            if (now - last >= NS_PER_FRAME) {
                update();
                repaint();
                last = now;
            } else {
                try {
                    Thread.sleep(1); // reduce CPU usage
                } catch (InterruptedException ignored) {}
            }
        }

        onExit(); // cleanup
    }

    // Game's components
    private Player player;
    private Map map;
    private PhysicHandler physics;
    private PlayerController playerController;
    private ChasingCamera camera;
    private Gun gun;

    // --- Standard functions ---
    private void init() {
        // TODO: load resources, init variables
        player = new Player(
                new Vector2(600, 100),
                new Vector2(40, 70),
                Color.CYAN,
                2.75, 0.175,
                6, 2);
        map = GameMap.MAP1;
        physics = new PhysicHandler(player, map);
        physics.init(0.15, 0.075);

        playerController = new PlayerController(player);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;
        camera = new ChasingCamera(player,
                new Vector2(0, 0),
                new Vector2(width, height),
                0.033, 0.05);
        gun = new Gun(player, new Vector2(40, 16));
    }

    private void update() {
        // TODO: update game state
        playerController.update();

        physics.update();
        player.update();
        physics.handleCollision();
        camera.update();
    }

    private void render(Graphics2D g2d) {
        // TODO: optional: compute what to draw (often empty in Swing)
        map.render(g2d, camera);
        player.render(g2d, camera);
        gun.render(g2d, camera);
    }

    private void onExit() {
        // TODO: cleanup (stop sounds, save, etc.)
    }

    // Auto-start when the panel is attached to a window (recommended)
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        render(g2d);
    }

    @Override
    public void addNotify() {
        super.addNotify();
        startGame();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        playerController.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        playerController.keyReleased(e);
    }
}