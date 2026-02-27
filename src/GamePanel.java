import Camera.ChasingCamera;
import CustomMath.Vector2;
import Maps.GameMap;
import Maps.Map;
import PlayerManager.GunController;
import PlayerManager.Player;
import PlayerManager.MovementController;
import PlayerManager.PlayerProfile;
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
    private final Vector2 frameSize;

    public GamePanel(Vector2 frameSize) {
        setBackground(new Color(30, 30, 30));
        setFocusable(true);
        addKeyListener(this);
        requestFocusInWindow();
        this.frameSize = frameSize;
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
                input();
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
    private Map map;
    private PhysicHandler physics;
    private ChasingCamera camera;
    private PlayerProfile playerProfile;

    // --- Standard functions ---
    private void init() {
        // TODO: load resources, init variables
        playerProfile = new PlayerProfile();
        map = GameMap.MAP1;
        physics = new PhysicHandler(playerProfile.getPlayer(), map);
        physics.init(0.15, 0.075);
        camera = new ChasingCamera(playerProfile.getPlayer(),
                new Vector2(0, 0),
                frameSize, 0.033, 0.05);
    }

    private void input(){
        playerProfile.input();
    }

    private void update() {
        // TODO: update game state
        physics.update();
        physics.handleCollision();
        camera.update();
        playerProfile.update();
    }

    private void render(Graphics2D g2d) {
        // TODO: optional: compute what to draw (often empty in Swing)
        map.render(g2d, camera);
        playerProfile.render(g2d, camera);
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
        playerProfile.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        playerProfile.keyReleased(e);
    }
}