package Weapon;

import Camera.ChasingCamera;
import CustomMath.Vector2;
import PlayerManager.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Gun {
    private final Player host;
    private final List<Player> enemies;
    private final List<Ammo> ammo;
    private int fireTimer;
    public boolean isUsed;

    private final Vector2 scale;
    private BufferedImage leftImg;
    private BufferedImage rightImg;

    private final int fireRate;
    private final int ammoPump;        // How much ammo is shot at once
    private final double ammoSize;     // Ammo per second
    private final double ammoSpeed;    // Ammo speed
    private final double recoilAngle;  // Maximum ammo deflection angle (Radian)
    private final double recoilBurst;  // Impulse to repel host
    private final int ammoLongevity;   // Expectancy of ammo (Frames to exist)
    private final double ammoRepel;    // Impulse to repel enemies

    public Gun(String leftImgPath, String rightImgPath,
               Vector2 scale, Player host, List<Player> enemies,
               int fireRate, int ammoPump, double ammoSize,
               double ammoSpeed, double recoilAngle, double recoilBurst,
               int ammoLongevity, double ammoRepel){
        this.host = host;
        this.scale = scale;
        this.enemies = enemies;
        this.fireRate = fireRate;
        this.ammoPump = ammoPump;
        this.ammoSize = ammoSize;
        this.ammoSpeed = ammoSpeed;
        this.recoilAngle = recoilAngle;
        this.recoilBurst = recoilBurst;
        this.ammoLongevity = ammoLongevity;
        this.ammoRepel = ammoRepel;

        try {
            rightImg = ImageIO.read(new File(rightImgPath));
            leftImg = ImageIO.read(new File(leftImgPath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.ammo = new ArrayList<>();
        this.isUsed = false;
    }

    public void shoot(){
        if(fireTimer == 0){
            if(host.direction.equals("right")){
                // Repel host
                host.accelerate(new Vector2(-recoilBurst, 0));
                for(int i = 0; i < ammoPump; i ++){
                    // Spawn ammo
                    Vector2 spawn = new Vector2(
                            host.translation.x + host.scale.x / 2 + scale.x / 2,
                            host.translation.y + host.scale.y / 2 - ammoSize / 2
                    );  // At the middle-end of the gun
                    double angle = Math.random() * 2 * recoilAngle - recoilAngle;
                    ammo.add(new Ammo(
                            enemies, spawn, new Vector2(ammoSize, ammoSize),
                            ammoSpeed, angle, ammoRepel, ammoLongevity
                    ));
                }


            } else {
                // Repel host
                host.accelerate(new Vector2(recoilBurst, 0));
                for(int i = 0; i < ammoPump; i ++){
                    // Spawn ammo
                    Vector2 spawn = new Vector2(
                            host.translation.x + host.scale.x / 2 - scale.x / 2,
                            host.translation.y + host.scale.y / 2 - ammoSize / 2
                    );  // At the middle-end of the gun
                    double angle = Math.PI - recoilAngle + Math.random() * 2 * recoilAngle;
                    ammo.add(new Ammo(
                            enemies, spawn, new Vector2(ammoSize, ammoSize),
                            ammoSpeed, angle, ammoRepel, ammoLongevity
                    ));
                }

            }
            fireTimer ++;
        }
    }

    public void update(){
        if(fireTimer > 0 && fireTimer < 120 / fireRate){    // FPS is hardcoded at 120 frames
            fireTimer ++;
        } else if(fireTimer >= 120 / fireRate) fireTimer = 0;

        if(!ammo.isEmpty()){
            Iterator<Ammo> it = ammo.iterator();
            while (it.hasNext()) {
                Ammo am = it.next();
                if (!am.disappear()) {
                    am.update();
                } else {
                    it.remove();
                }
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
                g2d.drawImage(
                        rightImg,
                        (int)((host.translation.x + host.scale.x / 2 - scale.x / 2 - camera.translation.x) * camera.frameRes.x / ChasingCamera.REFERENCE_RES.x),
                        (int)((host.translation.y + host.scale.y / 2 - scale.y / 2 - camera.translation.y) * camera.frameRes.y / ChasingCamera.REFERENCE_RES.y),
                        (int) (scale.x * camera.frameRes.x / ChasingCamera.REFERENCE_RES.x),
                        (int) (scale.y * camera.frameRes.y / ChasingCamera.REFERENCE_RES.y),
                        null
                );
            } else {
                g2d.drawImage(
                        leftImg,
                        (int)((host.translation.x + host.scale.x / 2 + scale.x / 2 - scale.x - camera.translation.x) * camera.frameRes.x / ChasingCamera.REFERENCE_RES.x),
                        (int)((host.translation.y + host.scale.y / 2 - scale.y / 2 - camera.translation.y) * camera.frameRes.y / ChasingCamera.REFERENCE_RES.y),
                        (int) (scale.x * camera.frameRes.x / ChasingCamera.REFERENCE_RES.x),
                        (int) (scale.y * camera.frameRes.y / ChasingCamera.REFERENCE_RES.y),
                        null
                );
            }
        }
    }
}
