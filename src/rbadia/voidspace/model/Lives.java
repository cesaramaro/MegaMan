package rbadia.voidspace.model;

public class Lives extends GameObject {
	private static final long serialVersionUID = 7L; 
	
public static final int DEFAULT_SPEED = 1;
	
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	
	public Lives(int xPos, int yPos) {
		super(xPos, yPos, Asteroid.WIDTH, Asteroid.HEIGHT);
		this.setSpeed(DEFAULT_SPEED);
	}

	public int getDefaultSpeed(){
		return DEFAULT_SPEED;
	}
}
