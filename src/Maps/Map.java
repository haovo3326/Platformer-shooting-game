package Maps;
import Camera.ChasingCamera;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Map {
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
