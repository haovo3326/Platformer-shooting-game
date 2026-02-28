package PlayerManager.BotManager;

import Camera.ChasingCamera;
import CustomMath.Vector2;
import Maps.Map;
import PlayerManager.Player;

import java.awt.*;

public class BotProfile {
    private final Player bot;
    private final MovementController movementController;

    public BotProfile(Player player, Map map){
        bot = new Player(
                new Vector2(1000, 100),
                new Vector2(40, 70),
                Color.YELLOW, 3, 0.175, 6, 2);
        movementController = new MovementController(bot, player, map);
    }

    public Player getBot(){
        return bot;
    }

    public void update(){
        movementController.update();
        bot.update();
    }

    public void render(Graphics2D g2d, ChasingCamera camera){
        bot.render(g2d, camera);
    }
}
