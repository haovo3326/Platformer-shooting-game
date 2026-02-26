package Camera;

import CustomMath.Vector2;
import PlayerManager.Player;

public class ChasingCamera {
    public Vector2 translation;
    public static final Vector2 SCALE = new Vector2(1920, 1080);
    public final Vector2 frameSize;
    private final Player player;
    private final double idleRadius; // No lerping if distance too small (camera idle)
    private final double lerpingRate;


    public ChasingCamera(Player player, Vector2 translation, Vector2 frameSize, double lerpingRate, double idleRadius){
        this.player = player;
        this.translation = translation;
        this.lerpingRate = lerpingRate;
        this.idleRadius = idleRadius;
        this.frameSize = frameSize;
    }

    public void update(){
        Vector2 cameraCenter = new Vector2(
                translation.x + SCALE.x / 2,
                translation.y + SCALE.y / 2);
        Vector2 playerCenter = new Vector2(
                player.translation.x + player.scale.x / 2,
                player.translation.y + player.scale.y / 2);

        double dx = playerCenter.x - cameraCenter.x;
        double dy = playerCenter.y - cameraCenter.y;
        double squareDistance = dx * dx + dy * dy;
        if(squareDistance > idleRadius * idleRadius){
            translation.add(new Vector2(dx * lerpingRate, dy * lerpingRate));
        }
    }
}
