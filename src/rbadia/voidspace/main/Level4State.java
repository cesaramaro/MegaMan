package rbadia.voidspace.main;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import rbadia.voidspace.graphics.GraphicsManager;
import rbadia.voidspace.model.Lives;
import rbadia.voidspace.sounds.SoundManager;

public class Level4State extends Level1State {

    private static final long serialVersionUID = 4L;
    
    protected Lives lives;
    protected Rectangle livesRemover;
    protected long lastLivesTime;

    public Level4State(int level, MainFrame frame, GameStatus status, LevelLogic gameLogic, InputHandler inputHandler,
            GraphicsManager graphicsMan, SoundManager soundMan) {
        super(level, frame, status, gameLogic, inputHandler, graphicsMan, soundMan);
    }

    public void doStart() { 
        super.doStart();
        setStartState(GETTING_READY);
        setCurrentState(getStartState());
        newLives(this);
    }

    public void updateScreen() {
        super.updateScreen();
        drawLives();
        checkMegaManLivesCollisions();
    }

    /*
     * Creates a new power up at the
     * specified coordinates
     */
    protected Lives newLives(Level1State screen) {
        int xPos = (int) (30);
        int yPos = (int) (360);
        lives = new Lives(xPos, yPos);
        return lives;
    }
    
    /*
     * Draws the lives power-up image to screen
     */
    protected void drawLives() {
        Graphics2D g2d = getGraphics2D();
        getGraphicsManager().drawLives(lives, g2d, this);
    }
    
    /*
     * Checks for collisions between MegaMan and the
     * lives power-up
     */
    protected void checkMegaManLivesCollisions() {
        GameStatus status = getGameStatus();
        if (lives.intersects(megaMan)) {
            status.setLivesLeft(status.getLivesLeft() + 5);
            removeLives(lives);
        }
    }

    /*
     * Remove the specified live power-up from the screen
     * @param Lives power up
     */
    protected void removeLives(Lives lives) {
        livesRemover = new Rectangle(lives.x, lives.y,
                lives.getPixelsWide(), lives.getPixelsTall());
        lives.setLocation(-lives.getPixelsWide(), -lives.getPixelsTall());
        this.getGameStatus().setNewLives(true);
        lastLivesTime = System.currentTimeMillis();
    }
}
