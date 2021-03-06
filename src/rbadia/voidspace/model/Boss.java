package rbadia.voidspace.model;

public class Boss extends GameObject {
    private static final long serialVersionUID = 1L;
    
    public static final int DEFAULT_SPEED = 2;
    public static final int Y_OFFSET = 5; // initial y distance of the boss from the bottom of the screen 
    
    public static final int WIDTH = 100;
    public static final int HEIGHT = 102;
    public boolean goingDown = false;
    
    public Boss(int xPos, int yPos) {
        super(xPos, yPos, WIDTH, HEIGHT);
        this.setSpeed(DEFAULT_SPEED);
    }
    
    public int gtInitialYOffset() {
        return Y_OFFSET;
    }    
}
