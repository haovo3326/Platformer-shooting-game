package Maps;

import CustomMath.Vector2;

import java.awt.*;

public class MapArsenal {
    public static Map createMap1(){
        return new Map(new Obstacle[]{
                new Obstacle(new Vector2(800, 1000), new Vector2(1400, 20), Color.GRAY, 2),
                new Obstacle(new Vector2(1000, 900), new Vector2(1000, 20), Color.GRAY, 1),
                new Obstacle(new Vector2(1200, 800), new Vector2(600, 20), Color.GRAY, 0)
        }, 3, new Vector2(3000, 1500));
    }

    public static Map createMap2(){
        return new Map(new Obstacle[]{
                new Obstacle(new Vector2(200, 700), new Vector2(1400, 20), Color.GRAY, 3),
                new Obstacle(new Vector2(400, 600), new Vector2(300, 20), Color.GRAY, 2),
                new Obstacle(new Vector2(1100, 600), new Vector2(300, 20), Color.GRAY, 2),
                new Obstacle(new Vector2(400, 500), new Vector2(300, 20), Color.GRAY, 1),
                new Obstacle(new Vector2(1100, 500), new Vector2(300, 20), Color.GRAY, 1),
                new Obstacle(new Vector2(400, 400), new Vector2(300, 20), Color.GRAY, 0),
                new Obstacle(new Vector2(1100, 400), new Vector2(300, 20), Color.GRAY, 0)
        }, 4, new Vector2(3000, 2000));
    }
}
