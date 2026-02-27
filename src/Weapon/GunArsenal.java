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
        currentGun = "Glick";
        // Handguns
        arsenal.put("Glick", new Gun(player, new Vector2(40, 16), 8, 1,10, 7, Math.toRadians(1), 0.75));
        arsenal.put("Beratty", new Gun(player, new Vector2(40, 16), 5, 1,12, 7, Math.toRadians(2), 1));
        arsenal.put("Requiem", new Gun(player, new Vector2(40, 16), 2, 1,14, 12, Math.toRadians(0), 2));
        // Shotgun
        arsenal.put("Shorty", new Gun(player, new Vector2(40, 16), 2, 5,14, 12, Math.toRadians(0), 2));
        arsenal.put("Pumper", new Gun(player, new Vector2(40, 16), 2, 5,14, 12, Math.toRadians(0), 2));
        // SMGs and rifle
        arsenal.put("Vector", new Gun(player, new Vector2(40, 16), 2, 1,14, 12, Math.toRadians(0), 2));
        arsenal.put("Assault", new Gun(player, new Vector2(40, 16), 2, 1,14, 12, Math.toRadians(0), 2));
        // Sniper
        arsenal.put("Awoopa", new Gun(player, new Vector2(40, 16), 2, 1,14, 12, Math.toRadians(0), 2));

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
