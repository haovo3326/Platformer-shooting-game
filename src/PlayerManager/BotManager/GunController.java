package PlayerManager.BotManager;

import Camera.ChasingCamera;
import CustomMath.Vector2;
import PlayerManager.Player;
import Weapon.GunArsenal;

import java.awt.*;
import java.util.List;

public class GunController {
    private final GunArsenal arsenal;
    private final List<Player> enemies;
    private final Player host;
    private boolean start;

    public GunController(Player host, List<Player> enemies){
        this.host = host;
        this.enemies = enemies;
        arsenal = new GunArsenal(host, enemies);
        arsenal.useGun("");
        start = false;
    }

    private void shoot(){
        for(Player enemy : enemies){
            Vector2 enemyCenter = enemy.translation.add2Vec(enemy.scale.mul2Vec(0.5));
            Vector2 hostCenter = host.translation.add2Vec(host.scale.mul2Vec(0.5));
            if(enemyCenter.x > hostCenter.x && host.direction.equals("right")){
                if(enemyCenter.y >= hostCenter.y - host.scale.y / 2 && enemyCenter.y <= hostCenter.y + host.scale.y / 2){
                    arsenal.shoot();
                }
            }
            if(enemyCenter.x <= hostCenter.x && host.direction.equals("left")){
                if(enemyCenter.y >= hostCenter.y - host.scale.y / 2 && enemyCenter.y <= hostCenter.y + host.scale.y / 2){
                    arsenal.shoot();
                }
            }
        }
    }
    public void update(){
        if(host.isGrounded) start = true;
        if(start) shoot();
        arsenal.update();
    }

    public void render(Graphics2D g2d, ChasingCamera camera){
        arsenal.render(g2d, camera);
    }
}
