package PlayerManager.BotManager;

import CustomMath.Vector2;
import Maps.Map;
import Maps.Obstacle;
import PlayerManager.Player;

import java.util.List;

public class MovementController {
    private final Player bot;
    private final Player player;
    private final Map map;

    public MovementController(Player bot, Player player, Map map){
        this.bot = bot;
        this.player = player;
        this.map = map;
    }

    private int currentLevel;

    private void checkLevel(){
        Vector2 botCenter = bot.translation.add2Vec(bot.scale.mul2Vec(0.5));
        currentLevel = map.levels.size();
        for(List<Obstacle> level : map.levels){
            Obstacle ref = level.getFirst();
            Vector2 sampleCenter = ref.translation.add2Vec(ref.scale.mul2Vec(0.5));
            if(botCenter.y < sampleCenter.y){
                currentLevel = ref.level;
                break;
            }
        }
        System.out.println(currentLevel);
    }

    private void horizontalChasing(){
        Vector2 playerCenter = player.translation.add2Vec(player.scale.mul2Vec(0.5));
        Vector2 botCenter = bot.translation.add2Vec(bot.scale.mul2Vec(0.5));
        Obstacle base = map.levels.getLast().getFirst();
        // Only chase if player is still on the base
        if(playerCenter.y < base.translation.y){
            if(playerCenter.x > botCenter.x + 100){
                bot.moveRight();
            } else if(playerCenter.x < botCenter.x - 100){
                bot.moveLeft();
            }
        }

    }

    private void verticalChasing(){
        Vector2 playerCenter = player.translation.add2Vec(player.scale.mul2Vec(0.5));
        Vector2 botCenter = bot.translation.add2Vec(bot.scale.mul2Vec(0.5));
        if(playerCenter.y > botCenter.y + bot.scale.y / 2 && currentLevel < map.levels.size() - 1){
            bot.crouch();
        }
        // Check if bot is standing off the base
        Obstacle base = map.levels.getLast().getFirst();
        Vector2 baseCenter = base.translation.add2Vec(base.scale.mul2Vec(0.5));
        if(Math.abs(botCenter.x - baseCenter.x) > base.scale.x / 2){
            bot.jump();
        }
        if(playerCenter.y < botCenter.y - bot.scale.y / 2){
            // Decide when to jump to the higher platform
            int lowerLevel = currentLevel - 1;
            if(lowerLevel >= 0) {
                // Only jump when there are platforms in range
                List<Obstacle> lowerPlatform = map.levels.get(lowerLevel);
                for(Obstacle obs : lowerPlatform){
                    Vector2 obsCenter = obs.translation.add2Vec(obs.scale.mul2Vec(0.5));
                    if(Math.abs(obsCenter.x - botCenter.x) <= obs.scale.x / 2){
                        bot.jump();
                    }
                }
            }
        }
    }

    public void update(){
        checkLevel();
        horizontalChasing();
        verticalChasing();
    }
}
