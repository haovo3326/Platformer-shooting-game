package PlayerManager.BotManager;

import CustomMath.Vector2;
import Maps.Map;
import Maps.Obstacle;
import PlayerManager.Player;

import java.util.List;

public class MovementController {
    private final Player host;
    private final List<Player> enemies;
    private final Map map;

    public MovementController(Player host, List<Player> enemies, Map map){
        this.host = host;
        this.enemies = enemies;
        this.map = map;
    }

    private int currentLevel;

    private void checkLevel(){
        Vector2 hostCenter = host.translation.add2Vec(host.scale.mul2Vec(0.5));
        currentLevel = -1;
        for(List<Obstacle> level : map.levels){
            Obstacle ref = level.getFirst();
            Vector2 sampleCenter = ref.translation.add2Vec(ref.scale.mul2Vec(0.5));
            if(hostCenter.y < sampleCenter.y){
                currentLevel = ref.level;
                break;
            }
        }
    }

    private void horizontalChasing(Player enemy){
        Vector2 enemyCenter = enemy.translation.add2Vec(enemy.scale.mul2Vec(0.5));
        Vector2 botCenter = host.translation.add2Vec(host.scale.mul2Vec(0.5));
        Obstacle base = map.levels.getLast().getFirst();
        // Only chase if player is still on the base
        if(enemyCenter.y < base.translation.y){
            if(enemyCenter.x > botCenter.x + 100){
                host.moveRight();
            } else if(enemyCenter.x < botCenter.x - 100){
                host.moveLeft();
            }
        }

    }

    private void verticalChasing(Player enemy){
        Vector2 playerCenter = enemy.translation.add2Vec(enemy.scale.mul2Vec(0.5));
        Vector2 hostCenter = host.translation.add2Vec(host.scale.mul2Vec(0.5));
        if(playerCenter.y > hostCenter.y + host.scale.y / 2 && currentLevel < map.levels.size() - 1){
            host.crouch();
        }
        // Check if bot is standing off the base
        Obstacle base = map.levels.getLast().getFirst();
        Vector2 baseCenter = base.translation.add2Vec(base.scale.mul2Vec(0.5));
        if(Math.abs(hostCenter.x - baseCenter.x) > base.scale.x / 2){
            host.jump();
        }
        if(playerCenter.y < hostCenter.y - host.scale.y / 2){
            // Decide when to jump to the higher platform
            int lowerLevel = currentLevel - 1;
            if(lowerLevel >= 0) {
                // Only jump when there are platforms in range
                List<Obstacle> lowerPlatform = map.levels.get(lowerLevel);
                for(Obstacle obs : lowerPlatform){
                    Vector2 obsCenter = obs.translation.add2Vec(obs.scale.mul2Vec(0.5));
                    if(Math.abs(obsCenter.x - hostCenter.x) <= obs.scale.x / 2){
                        host.jump();
                    }
                }
            }
        }
    }

    public void update(){
        checkLevel();
        Player closestEnemy = enemies.getFirst();
        double minDist2 = Double.MIN_VALUE;
        for(Player enemy : enemies){
            double dist2 = enemy.translation.sub2Vec(host.translation).length2();
            if(dist2 < minDist2) {
                minDist2 = dist2;
                closestEnemy = enemy;
            }
        }
        horizontalChasing(closestEnemy);
        verticalChasing(closestEnemy);
    }
}
