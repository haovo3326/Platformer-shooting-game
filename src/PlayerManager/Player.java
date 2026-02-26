package PlayerManager;

import Camera.ChasingCamera;
import CustomMath.Vector2;

import java.awt.*;

public class Player {
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

    public Player(Vector2 translation, Vector2 scale, Color color, double speed, double acceleration, double jumpBurst, int jumpLimit){
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
    public void update() {
        translation.add(velocity);
        resetJump();
    }

    public void render(Graphics2D g2d, ChasingCamera camera){
        g2d.setColor(color);
        g2d.fillRect(
                (int) (translation.x - camera.translation.x),
                (int) (translation.y - camera.translation.y),
                (int) scale.x,
                (int) scale.y
        );
    }
}
