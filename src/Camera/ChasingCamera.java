package Camera;

import CustomMath.Vector2;
import Maps.Map;
import PlayerManager.Player;
import java.util.List;

public class ChasingCamera {
    public static final Vector2 REFERENCE_RES = new Vector2(1920, 1080);
    public Vector2 translation;
    public final Vector2 frameRes;

    private final List<Player> players;
    private final double idleRadius; // No lerping if distance too small (camera idle)
    private final double lerpingRate;


    public ChasingCamera(List<Player> players, Vector2 frameSize,
                         double lerpingRate, double idleRadius){
        this.players = players;
        this.lerpingRate = lerpingRate;
        this.idleRadius = idleRadius;
        this.frameRes = frameSize;
        translation = Map.ORIGIN.add2Vec(Map.SCALE.mul2Vec(0.5).sub2Vec(REFERENCE_RES.mul2Vec(0.5)));
    }

    private void chasingPlayers(){
        Vector2 playersCenter = new Vector2(0, 0);
        double inboundCount = 0;
        for(Player player : players){
            Vector2 center = player.translation.add2Vec(player.scale.mul2Vec(0.5));
            if(center.x >= Map.ORIGIN.x && center.x < Map.ORIGIN.x + Map.SCALE.x
                    && center.y >= Map.ORIGIN.y && center.y <= Map.ORIGIN.y + Map.SCALE.y){
                playersCenter.add(center);
                inboundCount ++;
            }
        }
        playersCenter.mul(1.0 / inboundCount);
        if(inboundCount == 0){
            playersCenter = Map.ORIGIN.add2Vec(Map.SCALE.mul2Vec(0.5));
        }

        Vector2 cameraCenter = translation.add2Vec(REFERENCE_RES.mul2Vec(0.5));
        double dx = playersCenter.x - cameraCenter.x;
        double dy = playersCenter.y - cameraCenter.y;
        double squareDistance = dx * dx + dy * dy;
        if(squareDistance > idleRadius * idleRadius){
            translation.add(new Vector2(dx * lerpingRate, dy * lerpingRate));
        }
    }

    public void update(){
        chasingPlayers();
    }
}
