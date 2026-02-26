package Maps;
import java.awt.*;

public class Map {
    public Obstacle[] obstacles;

    public Map(Obstacle[] obstacles){
        this.obstacles = obstacles;
    }

    public void render(Graphics2D g2d){
        for(Obstacle obs : obstacles){
            obs.render(g2d);
        }
    }
}
