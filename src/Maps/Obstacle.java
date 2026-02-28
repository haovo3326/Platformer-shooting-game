package Maps;

import Camera.ChasingCamera;
import CustomMath.Vector2;

import java.awt.*;

public class Obstacle {
    public final Vector2 translation;
    public final Vector2 scale;
    public Color color;
    public int level;   // The platform index where the obstacle takes place

    public Obstacle(Vector2 translation, Vector2 scale, Color color, int level){
        this.translation = translation;
        this.scale = scale;
        this.color = color;
        this.level = level;
    }

    public void render(Graphics2D g2d, ChasingCamera camera){
        g2d.setColor(color);
        g2d.fillRect(
                (int) ((translation.x - camera.translation.x) * camera.frameRes.x / ChasingCamera.REFERENCE_RES.x),
                (int) ((translation.y - camera.translation.y) * camera.frameRes.y / ChasingCamera.REFERENCE_RES.y),
                (int) (scale.x * camera.frameRes.x / ChasingCamera.REFERENCE_RES.x),
                (int) (scale.y * camera.frameRes.y / ChasingCamera.REFERENCE_RES.y)
        );
    }
}
