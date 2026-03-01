package Maps;
import Camera.ChasingCamera;
import CustomMath.Vector2;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Map {
    public static final Vector2 ORIGIN = new Vector2(0, 0);
    public static final Vector2 SPAWN = new Vector2(600, -1000); // X for the range, Y for the position along y-axis
    public static final Vector2 SCALE = new Vector2(3000, 1500);

    public Obstacle[] obstacles;
    public List<List<Obstacle>> levels;

    public Map(Obstacle[] obstacles, int maxLevel){
        this.obstacles = obstacles;
        levels = new ArrayList<>();
        for(int i = 0; i < maxLevel; i ++){
            levels.add(new ArrayList<>());
        }
        for(Obstacle obs : obstacles){
            levels.get(obs.level).add(obs);
        }
    }

    public void render(Graphics2D g2d, ChasingCamera camera){
        for(Obstacle obs : obstacles){
            obs.render(g2d, camera);
        }
    }
}
