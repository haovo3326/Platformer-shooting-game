package Weapon;

import Camera.ChasingCamera;
import CustomMath.Vector2;

import java.awt.*;

public class Ammo {
    private final Vector2 translation;
    private final Vector2 scale;
    private final double speed;
    private final double angle;

    public Ammo(Vector2 translation, Vector2 scale, double speed, double angle){
        this.translation = translation;
        this.scale = scale;
        this.speed = speed;
        this.angle = angle;
    }

    public void update(){
        translation.add(new Vector2(speed * Math.cos(angle), speed * Math.sin(angle)));
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
