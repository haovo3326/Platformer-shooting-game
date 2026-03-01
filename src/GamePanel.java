import Camera.ChasingCamera;
import CustomMath.Vector2;
import Maps.MapArsenal;
import Maps.Map;
import PlayerManager.BotManager.BotProfile;
import PlayerManager.Player;
import PlayerManager.PlayerProfile;
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
    private Map map1;
    private PhysicHandler physics;
    private ChasingCamera camera;
    private PlayerProfile playerProfile;
    private BotProfile botProfile;

    // --- Standard functions ---
    private void init() {
        // TODO: load resources, init variables
        playerProfile = new PlayerProfile();
        map1 = MapArsenal.createMap1();
        botProfile = new BotProfile(playerProfile.getPlayer(), map1);
        camera = new ChasingCamera(playerProfile.getPlayer(),
                new Vector2(0, 0),
                frameSize, 0.033, 0.05);
        physics = new PhysicHandler(new Player[]{playerProfile.getPlayer(), botProfile.getBot()}, map1);
        physics.init(0.15, 0.075);
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
        botProfile.update();
    }

    private void render(Graphics2D g2d) {
        // TODO: optional: compute what to draw (often empty in Swing)
        map1.render(g2d, camera);
        playerProfile.render(g2d, camera);
        botProfile.render(g2d, camera);
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