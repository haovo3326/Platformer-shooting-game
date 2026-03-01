package Weapon;

import Camera.ChasingCamera;
import CustomMath.Vector2;
import PlayerManager.Player;

import java.awt.*;
import java.util.List;

public class Ammo {
    private final List<Player> enemies;
    private final Vector2 translation;
    private final Vector2 scale;
    private final double speed;
    private final double angle;
    private final double repelBurst;
    private final int longevity;    // Expectancy of the ammo (Frames it exist)
    private int timer;

    public Ammo(List<Player> enemies, Vector2 translation,
                Vector2 scale, double speed, double angle,
                double repelBurst, int longevity){
        this.enemies = enemies;
        this.translation = translation;
        this.scale = scale;
        this.speed = speed;
        this.angle = angle;
        this.repelBurst = repelBurst;
        this.longevity = longevity;
    }

    private void age(){
        if(timer < longevity){
            timer ++;
        }
    }

    private void hit(){
        double horizontalSpeed = Math.cos(angle);
        for(Player enemy : enemies){
            if(translation.x >= enemy.translation.x - scale.x && translation.x <= enemy.translation.x + enemy.scale.x){
                if(translation.y >= enemy.translation.y - scale.y && translation.y <= enemy.translation.y + enemy.scale.y){
                    if(horizontalSpeed > 0){
                        enemy.accelerate(new Vector2(repelBurst, 0));
                    } else {
                        enemy.accelerate(new Vector2(-repelBurst, 0));
                    }
                    timer = longevity;
                }
            }
        }
    }

    public boolean disappear(){
        return timer == longevity;
    }

    public void update(){
        translation.add(new Vector2(speed * Math.cos(angle), speed * Math.sin(angle)));
        hit();
        age();
    }

    public void render(Graphics2D g2d, ChasingCamera camera){
        g2d.setColor(Color.WHITE);
        g2d.fillOval(
                (int) ((translation.x - camera.translation.x) * camera.frameRes.x / ChasingCamera.REFERENCE_RES.x),
                (int) ((translation.y - camera.translation.y) * camera.frameRes.y / ChasingCamera.REFERENCE_RES.y),
                (int) (scale.x * camera.frameRes.x / ChasingCamera.REFERENCE_RES.x),
                (int) (scale.y * camera.frameRes.y / ChasingCamera.REFERENCE_RES.y)
        );
    }
}
