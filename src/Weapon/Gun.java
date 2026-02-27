package Weapon;

import Camera.ChasingCamera;
import CustomMath.Vector2;
import PlayerManager.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Gun {
    private final Player host;
    private final Vector2 scale;
    public boolean isUsed;
    private final List<Ammo> ammo;
    private int fireTimer;
    private final int fireRate;
    private final double ammoSize;// Ammo per second
    private final double ammoSpeed;    // Ammo speed
    private final double recoilAngle;  // Maximum ammo deflection angle

    public Gun(Player host, Vector2 scale, int fireRate, double ammoSize, double ammoSpeed, double recoilAngle){
        this.host = host;
        this.scale = scale;
        this.ammo = new ArrayList<>();
        this.isUsed = false;
        this.fireRate = fireRate;
        this.ammoSize = ammoSize;
        this.ammoSpeed = ammoSpeed;
        this.recoilAngle = recoilAngle;
    }

    public void shoot(){
        if(fireTimer == 0){
            if(host.direction.equals("right")){
                Vector2 spawn = new Vector2(
                        host.translation.x + host.scale.x / 2 + scale.x,
                        host.translation.y + host.scale.y / 2 - ammoSize / 2
                );  // At the middle-end of the gun
                double angle = Math.random() * 2 * recoilAngle - recoilAngle;
                ammo.add(new Ammo(spawn, new Vector2(ammoSize, ammoSize), ammoSpeed, angle));
            } else {
                Vector2 spawn = new Vector2(
                        host.translation.x + host.scale.x / 2 - scale.x,
                        host.translation.y + host.scale.y / 2 - ammoSize / 2
                );  // At the middle-end of the gun
                double angle = Math.PI - recoilAngle + Math.random() * 2 * recoilAngle;
                ammo.add(new Ammo(spawn, new Vector2(ammoSize, ammoSize), ammoSpeed, angle));
            }
            fireTimer ++;
        }
    }

    public void update(){
        if(fireTimer > 0 && fireTimer < 120 / fireRate){    // FPS is hardcoded at 120 frames
            fireTimer ++;
        } else if(fireTimer >= 120 / fireRate) fireTimer = 0;

        if(!ammo.isEmpty()){
            for(Ammo am : ammo){
                am.update();
            }
        }
    }

    public void render(Graphics2D g2d, ChasingCamera camera){
        if(!ammo.isEmpty()){
            for(Ammo am : ammo){
                am.render(g2d, camera);
            }
        }
        if(isUsed){
            g2d.setColor(Color.WHITE);
            if(host.direction.equals("right")){
                g2d.fillRect(
                        (int)((host.translation.x + host.scale.x / 2 - camera.translation.x) * camera.frameRes.x / ChasingCamera.REFERENCE_RES.x),
                        (int)((host.translation.y + host.scale.y / 2 - scale.y / 2 - camera.translation.y) * camera.frameRes.y / ChasingCamera.REFERENCE_RES.y),
                        (int) (scale.x * camera.frameRes.x / ChasingCamera.REFERENCE_RES.x),
                        (int) (scale.y * camera.frameRes.y / ChasingCamera.REFERENCE_RES.y)
                );
            } else {
                g2d.fillRect(
                        (int)((host.translation.x + host.scale.x / 2 - scale.x - camera.translation.x) * camera.frameRes.x / ChasingCamera.REFERENCE_RES.x),
                        (int)((host.translation.y + host.scale.y / 2 - scale.y / 2 - camera.translation.y) * camera.frameRes.y / ChasingCamera.REFERENCE_RES.y),
                        (int) (scale.x * camera.frameRes.x / ChasingCamera.REFERENCE_RES.x),
                        (int) (scale.y * camera.frameRes.y / ChasingCamera.REFERENCE_RES.y)
                );
            }
        }
    }
}
