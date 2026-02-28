package PlayerManager;

import Camera.ChasingCamera;
import Weapon.GunArsenal;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GunController implements KeyListener {
    private final GunArsenal arsenal;
    private boolean shoot;

    public GunController(Player player){
        arsenal = new GunArsenal(player);
        arsenal.useGun("Glick");
    }

    public void input(){
        if(shoot) arsenal.shoot();
    }

    public void update(){
        arsenal.update();
    }

    public void render(Graphics2D g2d, ChasingCamera camera){
        arsenal.render(g2d, camera);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_T){
            shoot = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_T){
            shoot = false;
        }
    }
}
