package Weapon;

import Camera.ChasingCamera;
import CustomMath.Vector2;
import PlayerManager.Player;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GunArsenal {
    private final Map<String, Gun> arsenal;
    private String currentGun;

    public GunArsenal(Player player){
        arsenal = new HashMap<>();
        currentGun = "pistol";
        arsenal.put("pistol", new Gun(player, new Vector2(40, 16), 10, 10, 7, Math.toRadians(3)));
    }

    public void useGun(String tag){
        arsenal.get(currentGun).isUsed = false;
        arsenal.get(tag).isUsed = true;
        currentGun = tag;
    }

    public void shoot(){
        arsenal.get(currentGun).shoot();
    }

    public void update(){
        for(Map.Entry<String, Gun> gun: arsenal.entrySet()){
            gun.getValue().update();
        }
    }

    public void render(Graphics2D g2d, ChasingCamera camera){
        for(Map.Entry<String, Gun> gun: arsenal.entrySet()){
            gun.getValue().render(g2d, camera);
        }
    }
}
