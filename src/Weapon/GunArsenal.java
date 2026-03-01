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
        currentGun = "Glick";
        // Handguns
        arsenal.put("Glick", new Gun(host, new Vector2(40, 16), enemies,
                6, 1,10, 7,
                Math.toRadians(1), 0.75, 150, 10));
    }

    public void useGun(String tag){
        arsenal.get(currentGun).isUsed = false;
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
