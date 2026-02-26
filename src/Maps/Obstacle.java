package Maps;

import Camera.ChasingCamera;
import CustomMath.Vector2;

import java.awt.*;

public class Obstacle {
    public final Vector2 translation;
    public final Vector2 scale;
    public Color color;

    public Obstacle(Vector2 translation, Vector2 scale, Color color){
        this.translation = translation;
        this.scale = scale;
        this.color = color;
    }

    public void render(Graphics2D g2d, ChasingCamera camera){
        g2d.setColor(color);
        g2d.fillRect(
                (int) ((translation.x - camera.translation.x) * camera.frameSize.x / ChasingCamera.SCALE.x),
                (int) ((translation.y - camera.translation.y) * camera.frameSize.y / ChasingCamera.SCALE.y),
                (int) (scale.x * camera.frameSize.x / ChasingCamera.SCALE.x),
                (int) (scale.y * camera.frameSize.y / ChasingCamera.SCALE.y)
        );
    }
}
