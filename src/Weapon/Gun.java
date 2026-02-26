package Weapon;

import Camera.ChasingCamera;
import CustomMath.Vector2;
import PlayerManager.Player;

import java.awt.*;

public class Gun {
    private final Player host;
    private Vector2 scale;

    public Gun(Player host, Vector2 scale){
        this.host = host;
        this.scale = scale;
    }

    public void render(Graphics2D g2d, ChasingCamera camera){
        g2d.setColor(Color.WHITE);
        if(host.direction.equals("right")){
            g2d.fillRect(
                    (int)((host.translation.x + host.scale.x / 2 - camera.translation.x) * camera.frameSize.x / ChasingCamera.SCALE.x),
                    (int)((host.translation.y + host.scale.y / 2 - scale.y / 2 - camera.translation.y) * camera.frameSize.y / ChasingCamera.SCALE.y),
                    (int) (scale.x * camera.frameSize.x / ChasingCamera.SCALE.x),
                    (int) (scale.y * camera.frameSize.y / ChasingCamera.SCALE.y)
            );
        } else {
            g2d.fillRect(
                    (int)((host.translation.x + host.scale.x / 2 - scale.x - camera.translation.x) * camera.frameSize.x / ChasingCamera.SCALE.x),
                    (int)((host.translation.y + host.scale.y / 2 - scale.y / 2 - camera.translation.y) * camera.frameSize.y / ChasingCamera.SCALE.y),
                    (int) (scale.x * camera.frameSize.x / ChasingCamera.SCALE.x),
                    (int) (scale.y * camera.frameSize.y / ChasingCamera.SCALE.y)
            );
        }
    }
}
