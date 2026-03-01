package Weapon;

import Camera.ChasingCamera;
import CustomMath.Vector2;
import PlayerManager.Player;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GunArsenal {
    private final Map<String, Gun> arsenal;
    private String currentGun;

    public GunArsenal(Player host, List<Player> enemies){
        arsenal = new HashMap<>();
        currentGun = "";
        // Handguns
        arsenal.put("Pistol", new Gun(
                "Assets\\Gun\\Left\\Glock.png",
                "Assets\\Gun\\Right\\Glock.png",
                new Vector2(80, 60), host, enemies,
                5, 1,8, 7,
                Math.toRadians(2), 0.75, 300, 10));
        arsenal.put("Shotgun", new Gun(
                "Assets\\Gun\\Left\\Shotgun.png",
                "Assets\\Gun\\Right\\Shotgun.png",
                new Vector2(100, 60), host, enemies,
                1, 10,6, 15,
                Math.toRadians(15), 2.5, 20, 2.5));
        arsenal.put("Rifle", new Gun(
                "Assets\\Gun\\Left\\Rifle.png",
                "Assets\\Gun\\Right\\Rifle.png",
                new Vector2(110, 60), host, enemies,
                10, 1,8, 8,
                Math.toRadians(5), 1, 300, 10));
        arsenal.put("Sniper", new Gun(
                "Assets\\Gun\\Left\\Sniper.png",
                "Assets\\Gun\\Right\\Sniper.png",
                new Vector2(120, 60), host, enemies,
                1, 1,10, 14,
                Math.toRadians(1), 3, 300, 22.5));
    }

    public void useGun(String tag){
        if(arsenal.containsKey(currentGun)){
        arsenal.get(currentGun).isUsed = false;
        }
        currentGun = tag;
        if(arsenal.containsKey(currentGun)){
            arsenal.get(currentGun).isUsed = true;
        }
    }

    public void shoot(){
        if(arsenal.containsKey(currentGun)) {
            arsenal.get(currentGun).shoot();
        }
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
