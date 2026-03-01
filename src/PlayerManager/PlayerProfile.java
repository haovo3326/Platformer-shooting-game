package PlayerManager;

import Camera.ChasingCamera;
import CustomMath.Vector2;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

public class PlayerProfile implements KeyListener {
    private final Player player;
    private MovementController movementController;
    private GunController gunController;

    public PlayerProfile(){
        player = new Player(
                new Vector2(600, 100),
                new Vector2(40, 70),
                Color.CYAN, 3, 0.175, 6, 2);

    }

    public void initController(List<Player> enemies){
        movementController = new MovementController(player);
        gunController = new GunController(player, enemies);
    }

    public Player getHost(){
        return player;
    }

    public void input(){
        movementController.input();
        gunController.input();
    }

    public void update(){
        gunController.update();
        player.update();
    }

    public void render(Graphics2D g2d, ChasingCamera camera){
        player.render(g2d, camera);
        gunController.render(g2d, camera);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        movementController.keyPressed(e);
        gunController.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        movementController.keyReleased(e);
        gunController.keyReleased(e);
    }
}
