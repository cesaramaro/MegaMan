package rbadia.voidspace.main;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import rbadia.voidspace.graphics.GraphicsManager;
import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.BigBullet;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.Platform;
import rbadia.voidspace.sounds.SoundManager;

/**
 * Level very similar to LevelState1.  
 * With moving platforms
 */
public class Level3State extends Level1State {

    private static final long serialVersionUID = 3L;
    
    protected long secondAsteroidTime;
    protected Asteroid secondAsteroid;
    
    // Constructors
    public Level3State(int level, MainFrame frame, GameStatus status, 
            LevelLogic gameLogic, InputHandler inputHandler,
            GraphicsManager graphicsMan, SoundManager soundMan) {
        super(level, frame, status, gameLogic, inputHandler, graphicsMan, soundMan);
    }

    @Override
    public void doStart() { 
        super.doStart();
        setStartState(GETTING_READY);
        setCurrentState(getStartState());

        newSecondAsteroid(this);
        newAsteroid(this);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        
        movePlatforms();
        drawSecondAsteroid();
        checkBulletSecondAsteroidCollisions();
        checkBigBulletSecondAsteroidCollisions();
        checkMegaManSecondAsteroidCollisions();
        checkSecondAsteroidFloorCollisions();
    }
    
    /*
     * Method to move the platforms from side to side
     */
    protected void movePlatforms() {
        for (Platform platform : platforms) {
            if (platform.getMaxX() >= SCREEN_WIDTH) platform.reverse = true;
            else if (platform.getX() <= 0) platform.reverse = false;
            platform.translate((platform.reverse ? -1 : 1), 0);
        }
    }
    
    @Override
    public Platform[] newPlatforms(int n) {
        platforms = new Platform[n];
        for(int i = 0; i < n; i++) {
            this.platforms[i] = new Platform(0,0);
                int k = 4;
                platforms[i].setLocation(50 + i* 50, SCREEN_HEIGHT/2 + 20 + (i - k) * 40 );
                k = k + 2;
        }
        return platforms;
    }   
    
    
    /**
     * Create a new asteroid with a random speed
     */
    @Override
    public Asteroid newAsteroid(Level1State screen) {
        int xPos = (int) (SCREEN_WIDTH - Asteroid.WIDTH);
        int yPos = rand.nextInt((int)(SCREEN_HEIGHT - Asteroid.HEIGHT- 32));
        int speed = rand.nextInt(8);
        
        while (speed <= 0) speed = rand.nextInt(10);
        asteroid = new Asteroid(xPos, yPos, speed);
        return asteroid;
    }
    
    /*
     * Inefficient way of adding a second asteroid
     * but there's no time
     */
    
    /*
     * Create a second asteroid
     */
    public Asteroid newSecondAsteroid(Level1State screen) {
        int xPos = (int) (SCREEN_WIDTH - Asteroid.WIDTH);
        int yPos = rand.nextInt((int) (SCREEN_HEIGHT - Asteroid.HEIGHT- 32));
        int speed = rand.nextInt(10);
        
        while (speed <= 0) speed = rand.nextInt(10);
        while(yPos == asteroid.getY()) yPos = rand.nextInt((int) (SCREEN_HEIGHT - Asteroid.HEIGHT- 32));
        
        secondAsteroid = new Asteroid(xPos, yPos, speed);
        return secondAsteroid;
    }
    
    /*
     * Draw a second asteroid
     */
    protected void drawSecondAsteroid() {
        Graphics2D g2d = getGraphics2D();
        GameStatus status = getGameStatus();

        if ((secondAsteroid.getX() + secondAsteroid.getWidth() > 0)) {
            secondAsteroid.translate(-secondAsteroid.getSpeed(), 0);
            getGraphicsManager().drawAsteroid(secondAsteroid, g2d, this); 
        } else {
            long currentTime = System.currentTimeMillis();
            if((currentTime - secondAsteroidTime) > NEW_ASTEROID_DELAY) { 
                // Draw a new asteroid
                secondAsteroidTime = currentTime;
                status.setNewAsteroid(false);
                secondAsteroid.setLocation((int) (SCREEN_WIDTH - secondAsteroid.getPixelsWide()),
                        (rand.nextInt((int) (SCREEN_HEIGHT - secondAsteroid.getPixelsTall() - 32))));
            } else {
                // Draw explosion
                getGraphicsManager().drawAsteroidExplosion(asteroidExplosion, g2d, this);
            }
        }
    }
    
    /*
     * Check an asteroid collision with the floor
     */
    protected void checkSecondAsteroidFloorCollisions() {
        for (int i = 0; i < 9; i++) {
            if (secondAsteroid.intersects(floor[i])) {
                removeSecondAsteroid(secondAsteroid);

            }
        }
    }

    /*
     * Check MegaMan's collision with the second asteroid
     */
    protected void checkMegaManSecondAsteroidCollisions() {
        GameStatus status = getGameStatus();
        if (secondAsteroid.intersects(megaMan)) {
            status.setLivesLeft(status.getLivesLeft() - 1);
            removeSecondAsteroid(secondAsteroid);
        }
    }

    /*
     * Check collisions between the second asteroid 
     * and big bullets
     */
    protected void checkBigBulletSecondAsteroidCollisions() {
        GameStatus status = getGameStatus();
        int bigBulletsSize = bigBullets.size();
        for (int i = 0; i < bigBulletsSize; i++) {
            BigBullet bigBullet = bigBullets.get(i);
            if (secondAsteroid.intersects(bigBullet)) {
                // Increase asteroids destroyed count
                status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 100);
                removeSecondAsteroid(secondAsteroid);
                damage = 0;
                bigBullets.remove(i);
                break;
            }
        }
    }

    /*
     * Check collisions between the second asteroid
     * and regular-sized bullets
     */
    protected void checkBulletSecondAsteroidCollisions() {
        GameStatus status = getGameStatus();
        int bulletSize = bullets.size();
        for(int i = 0; i < bulletSize; i++) {
            Bullet bullet = bullets.get(i);
            if (secondAsteroid.intersects(bullet)) {
                // Increase asteroids destroyed count
                status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 100);
                removeSecondAsteroid(secondAsteroid);
                levelAsteroidsDestroyed++;
                damage = 0;
                // Remove bullet
                bullets.remove(i);
                break;
            }
        }
    }
    
    /*
     * Remove the second Asteroid
     */
    public void removeSecondAsteroid(Asteroid asteroid) {
        asteroidExplosion = new Rectangle(
                asteroid.x,
                asteroid.y,
                asteroid.getPixelsWide(),
                asteroid.getPixelsTall());
        asteroid.setLocation(-asteroid.getPixelsWide(), -asteroid.getPixelsTall());
        this.getGameStatus().setNewAsteroid(true);
        secondAsteroidTime = System.currentTimeMillis();
        // Play asteroid explosion sound
        this.getSoundManager().playAsteroidExplosionSound();
    }
}
