package PlayerManager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerController implements KeyListener {
    private final Player player;
    private boolean moveLeft;
    private boolean moveRight;
    private boolean crouch;
    private boolean jump;

    public PlayerController(Player player){
        this.player = player;
    }

    public void update(){
        if(moveLeft) {
            player.moveLeft();
        }
        if(moveRight){
            player.moveRight();
        }
        if(jump){
            player.jump();
        }
        if(crouch){
            player.crouch();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_A) {
            moveLeft = true;
        } else if(key == KeyEvent.VK_D){
            moveRight = true;
        } else if(key == KeyEvent.VK_W){
            jump = true;
        } else if(key == KeyEvent.VK_S){
            crouch = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_A) {
            moveLeft = false;
        } else if(key == KeyEvent.VK_D){
            moveRight = false;
        } else if(key == KeyEvent.VK_W){
            jump = false;
        } else if(key == KeyEvent.VK_S){
            crouch = false;
        }
    }
}
