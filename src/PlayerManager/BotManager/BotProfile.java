package PlayerManager.BotManager;

import Camera.ChasingCamera;
import CustomMath.Vector2;
import Maps.Map;
import PlayerManager.Player;

import java.awt.*;
import java.util.List;

public class BotProfile {
    private final Player host;
    private MovementController movementController;
    private GunController gunController;

    public BotProfile(){
        host = new Player(
                new Vector2(1500, -500),
                new Vector2(40, 70),
                Color.YELLOW, 3, 0.175, 6, 2
        );
    }

    public void initController(List<Player> enemies, Map map){
        movementController = new MovementController(host, enemies, map);
        gunController = new GunController(host, enemies);
    }

    public Player getHost(){
        return host;
    }

    public void update(){
        movementController.update();
        gunController.update();
        host.update();
    }

    public void render(Graphics2D g2d, ChasingCamera camera){
        host.render(g2d, camera);
        gunController.render(g2d, camera);
    }
}
