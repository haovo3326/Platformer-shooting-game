package CustomMath;

public class Vector2 {
    public double x;
    public double y;

    public Vector2(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Vector2(){
        x = 0;
        y = 0;
    }

    public void add(Vector2 other){
        x += other.x;
        y += other.y;
    }

    public Vector2 add2Vec(Vector2 other){
        return new Vector2(
                x + other.x,
                y + other.y
        );
    }

    public Vector2 mul2Vec(double v){
        return new Vector2(
                x * v,
                y * v
        );
    }

    public double length(){
        return Math.sqrt(x * x + y * y);
    }
}
