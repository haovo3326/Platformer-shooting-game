import CustomMath.Vector2;
import Maps.Map;
import Maps.Obstacle;
import PlayerManager.Player;

public class PhysicHandler {
    private final Player player;
    private final Map map;

    private double gravity;
    private double friction;

    public PhysicHandler(Player player, Map map){
        this.player = player;
        this.map = map;
    }

    public void init(double gravity, double friction){
        this.gravity = gravity;
        this.friction = friction;
    }

    public void update(){
        if(player.velocity.y < 10) player.accelerate(new Vector2(0, gravity));
        if(player.velocity.x > friction / 2) {
            player.accelerate(new Vector2(-friction, 0));
        } else if(player.velocity.x < -friction / 2){
            player.accelerate(new Vector2(friction, 0));
        } else {
            player.velocity.x = 0;
        }
    }

    public void handleCollision() {
        if(player.velocity.y > 0){
            for(Obstacle ob: map.obstacles){
                for(int y = (int) (player.translation.y + player.scale.y); y <= player.translation.y + player.scale.y + player.velocity.y; y ++){
                    if(y == ob.translation.y){
                        if(player.translation.x > ob.translation.x - player.scale.x && player.translation.x < ob.translation.x + ob.scale.x) {
                            player.velocity.y = 0;
                            player.isGrounded = true;
                            player.translation.y = y - player.scale.y;
                            break;
                        }
                    }
                }
            }
        }
    }
}
