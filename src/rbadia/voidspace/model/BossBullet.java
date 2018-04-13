package rbadia.voidspace.model;

/**
 * Represents a bullet fired the boss
 */
public class BossBullet extends GameObject {
    private static final long serialVersionUID = 1L;
    
    public static final int DEFAULT_SPEED = 2;
    public static final  int WIDTH = 40;
    public static final int HEIGHT = 44;
    
    public BossBullet(int xPos, int yPos) {
        super(xPos, yPos, WIDTH, HEIGHT);
        this.setSpeed(12);
    }
    public int getDefaultSpeed(){
        return DEFAULT_SPEED;
    }
}
