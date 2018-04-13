package rbadia.voidspace.main;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import rbadia.voidspace.graphics.GraphicsManager;
import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.Lives;
import rbadia.voidspace.sounds.SoundManager;

public class Level4State extends Level1State {
	
	private static final long serialVersionUID = 4L;

	public Level4State(int level, MainFrame frame, GameStatus status, LevelLogic gameLogic, InputHandler inputHandler,
			GraphicsManager graphicsMan, SoundManager soundMan) {
		super(level, frame, status, gameLogic, inputHandler, graphicsMan, soundMan);
		// TODO Auto-generated constructor stub
	}
	protected Rectangle livesRemover;
	protected long lastLivesTime;
	
	
	  public void doStart() { 
	        super.doStart();
	        setStartState(GETTING_READY);
	        setCurrentState(getStartState());
	        newLives(this);
	        
	    }
	  
	 

	public void updateScreen() {
	        super.updateScreen();
	        drawLives();
	    }
	  
	
	  public Lives newLives(Level1State screen) {
		  int xPos = (int) (30);
		  int yPos = (int)(360);
		  lives = new Lives(xPos, yPos);
		  return lives;
	  }
	  protected void drawLives() {
		  Graphics2D g2d = getGraphics2D();
		  	getGraphicsManager().drawLives(lives, g2d, this);
	  }
	  protected void checkMegaManLivesCollisions() {
			GameStatus status = getGameStatus();
			if (lives.intersects(megaMan)) {
				status.setLivesLeft(status.getLivesLeft() + 1);
				removeLives(lives);
			}
		
		}



	private void removeLives(Lives lives) {
		livesRemover = new Rectangle(
				lives.x,
				lives.y,
				lives.getPixelsWide(),
				lives.getPixelsTall());
		lives.setLocation(-lives.getPixelsWide(), -lives.getPixelsTall());
		this.getGameStatus().setNewLives(true);
		lastLivesTime = System.currentTimeMillis();
		
	}
	  

}
