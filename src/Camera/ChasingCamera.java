package Camera;

import CustomMath.Vector2;
import PlayerManager.Player;

public class ChasingCamera {
    public Vector2 translation;
    public Vector2 scale;
    private final Player player;
    private final double idleRadius; // No lerping if distance too small (camera idle)
    private final double lerpingRate;

    public ChasingCamera(Player player, Vector2 translation, Vector2 scale, double lerpingRate, double idleRadius){
        this.player = player;
        this.translation = translation;
        this.scale = scale;
        this.lerpingRate = lerpingRate;
        this.idleRadius = idleRadius;
    }

    public void update(){
        Vector2 cameraCenter = new Vector2(
                translation.x + scale.x / 2,
                translation.y + scale.y / 2);
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
