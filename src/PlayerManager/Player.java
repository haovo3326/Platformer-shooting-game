package PlayerManager;

import Camera.ChasingCamera;
import CustomMath.Vector2;
import Maps.Map;

import java.awt.*;

public class Player {
    private final Map map;
    public Vector2 translation;
    public Vector2 scale;
    public Vector2 velocity;
    public String direction = "right";

    public double moveSpeed;
    public double acceleration;
    public double jumpBurst;
    public Color color;

    public int jumpLimit;
    public int jumpCount;
    public boolean isGrounded;
    public boolean isDead;

    public Player(Map map, Vector2 translation, Vector2 scale,
                  Color color, double speed, double acceleration,
                  double jumpBurst, int jumpLimit){
        this.map = map;
        this.translation = translation;
        this.scale = scale;
        this.color = color;
        this.moveSpeed = speed;
        this.acceleration = acceleration;
        this.jumpBurst = jumpBurst;
        this.jumpLimit = jumpLimit;
        jumpCount = 0;
        isGrounded = false;
        velocity = new Vector2();
        spawn();
    }

    public void accelerate(Vector2 a){
        velocity.add(a);
    }

    public void moveLeft(){
        direction = "left";
        if(velocity.x < -moveSpeed) {
            velocity.x = -moveSpeed;
            return;
        }
        accelerate(new Vector2(-acceleration, 0));
    }

    public void moveRight(){
        direction = "right";
        if(velocity.x > moveSpeed){
            velocity.x = moveSpeed;
            return;
        }
        accelerate(new Vector2(acceleration, 0));
    }

    public void jump(){
        if(velocity.y >= 0 && jumpCount < jumpLimit){
            velocity.y = 0;
            jumpCount ++;
            isGrounded = false;
            accelerate(new Vector2(0, -jumpBurst));
        }
    }

    public void crouch(){
        if(isGrounded){
            translation.y += 1;
            isGrounded = false;
        }
    }

    private void resetJump(){
        if(isGrounded){
            jumpCount = 0;
        }
    }

    private void checkDeath(){
        Vector2 center = translation.add2Vec(scale.mul2Vec(0.5));
        if(center.x < Map.ORIGIN.x || center.x >= Map.ORIGIN.x + Map.SCALE.x
                || center.y > Map.ORIGIN.y + Map.SCALE.y) {
            isDead = true;
            spawn();
        }
    }

    private void spawn(){
        if(isDead){
            isDead = false;
            Vector2 spawnPos = new Vector2();
            spawnPos.x = (int) (Math.random() * Map.SPAWN.x) + Map.ORIGIN.x + Map.SCALE.x / 2 - Map.SPAWN.x / 2;
            spawnPos.y = Map.SPAWN.y;
            translation = spawnPos.sub2Vec(scale.mul2Vec(0.5));
        }
    }

    public void update() {
        translation.add(velocity);
        resetJump();
        checkDeath();
    }

    public void render(Graphics2D g2d, ChasingCamera camera){
        g2d.setColor(color);
        g2d.fillRect(
                (int) ((translation.x - camera.translation.x) * camera.frameRes.x / ChasingCamera.REFERENCE_RES.x),
                (int) ((translation.y - camera.translation.y) * camera.frameRes.y / ChasingCamera.REFERENCE_RES.y),
                (int) (scale.x * camera.frameRes.x / ChasingCamera.REFERENCE_RES.x),
                (int) (scale.y * camera.frameRes.y / ChasingCamera.REFERENCE_RES.y)
        );
    }
}
