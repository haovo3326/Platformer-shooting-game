package Maps;

import CustomMath.Vector2;

import java.awt.*;

public class GameMap {
    public static final Map MAP1 = new Map(new Obstacle[]{
            new Obstacle(new Vector2(200, 700), new Vector2(1520, 20), Color.GRAY),
            new Obstacle(new Vector2(400, 600), new Vector2(1120, 20), Color.GRAY),
            new Obstacle(new Vector2(600, 500), new Vector2(720, 20), Color.GRAY)
    });
}
