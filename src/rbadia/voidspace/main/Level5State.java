package rbadia.voidspace.main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import rbadia.voidspace.graphics.GraphicsManager;
import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.BigBullet;
import rbadia.voidspace.model.Boss;
import rbadia.voidspace.model.BossBullet;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.Platform;
import rbadia.voidspace.sounds.SoundManager;

public class Level5State extends Level4State {

    private static final long serialVersionUID = 5L;
    
    protected Boss boss;
    protected List<BossBullet> bossBullets;
    
    // Constructors
    public Level5State(int level, MainFrame frame, GameStatus status, 
            LevelLogic gameLogic, InputHandler inputHandler,
            GraphicsManager graphicsMan, SoundManager soundMan) {
        super(level, frame, status, gameLogic, inputHandler, graphicsMan, soundMan);
    }

    @Override
    public void doStart() { 
        super.doStart();
        setStartState(GETTING_READY);
        setCurrentState(getStartState());
        
        bossBullets = new ArrayList<BossBullet>();
        startBossTimer();
        newBoss(this);
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        drawBoss();
        drawBossBullets();
        moveBoss();
        checkMegaManBossCollision();
        checkBigBulletBossCollision();
        checkBulletBossCollision();
    }
    /*
     * Disable megaMan-asteroid collisions check
     */
    protected void checkMegaManAsteroidCollisions() {}
    protected void checkAsteroidFloorCollisions() {}
    protected void drawAsteroid() {}
    protected void checkBulletAsteroidCollisions() {}
    protected void checkBigBulletAsteroidCollisions() {}
    
    @Override
    public Asteroid newAsteroid(Level1State screen) {
        return asteroid;
    }
    
    /*
     * Draws Level 5's platforms
     * @param int number of platforms to create
     * @return Array of platforms
     */
    public Platform[] newPlatforms(int n) {
        platforms = new Platform[n];
        for (int i = 0; i < n; i++) {
            this.platforms[i] = new Platform(0, SCREEN_HEIGHT/2 + 140 - i * 40);
        }
        return platforms;
    }
    
    /**
     * Create a new boss
     * @param Level5State screen
     * @return Boss new boss
     */
    protected Boss newBoss(Level5State screen) {
        // Minus 20 so it's not located on the exact corner
        int xPos = (int) (SCREEN_WIDTH - Boss.WIDTH - 20);
        int yPos = (int) (SCREEN_HEIGHT - Boss.HEIGHT - 20);
        
        boss = new Boss(xPos, yPos);
        return boss;
    }

    /*
     * Draws the boss
     */
    protected void drawBoss() {
        Graphics2D g2d = getGraphics2D();
        getGraphicsManager().drawBoss(boss, g2d, this);
    }
    
    /*
     * Method to move the boss from top to bottom
     */
    protected void moveBoss() {
        if (boss.getMinY() == 0) boss.goingDown = true;
        else if (boss.getMaxY() >= SCREEN_HEIGHT) boss.goingDown = false;
        boss.translate(0, (boss.goingDown ? boss.getSpeed() : -boss.getSpeed()));
    }
    
    /*
     * Draws the boss' bullets
     */
    protected void drawBossBullets() {
        Graphics2D g2d = getGraphics2D();
        for (int i = 0; i < bossBullets.size(); i++) {
            BossBullet bossBullet = bossBullets.get(i);
            getGraphicsManager().drawBossBullet(bossBullet, g2d, this);

            boolean remove = this.moveBossBullet(bossBullet);
            if (remove) {
                bossBullets.remove(i);
                i--;
            }
        }
    }
    
    /*
     * Checks for MegaMan and Boss' collision
     * Removes 1 health to MegaMan if they intersect
     */
    protected void checkMegaManBossCollision() {
        GameStatus status = getGameStatus();
        
        if (boss.intersects(megaMan)) {
            status.setLivesLeft(status.getLivesLeft() - 1);
        }
    }
    
    /*
     * Check collisions between a big bullet and the boss
     */
    protected void checkBulletBossCollision() {
        GameStatus status = getGameStatus();
        int bulletSize = bullets.size();
        
        for (int i = 0; i < bulletSize; i++) {
            Bullet bullet = bullets.get(i);
            if (boss.intersects(bullet)) {
                // Decrease boss' health
                status.setBossLivesLeft(status.getBossLivesLeft() - 1);
                // Remove bullet
                bullets.remove(i);
                break;
            }
        }
    }
    
    /*
     * Check collisions between a regular bullet and the boss
     */
    protected void checkBigBulletBossCollision() {
        GameStatus status = getGameStatus();
        int bigBulletsSize = bigBullets.size();
        
        for (int i = 0; i < bigBulletsSize; i++) {
            BigBullet bigBullet = bigBullets.get(i);
            if (boss.intersects(bigBullet)) {
                // Decrease boss' health
                status.setBossLivesLeft(status.getBossLivesLeft() - 1);
                bigBullets.remove(i);
                break;
            }
        }
    }

    /*
     * Starts a timer so the boss shoots (twice) and moves every second
     */
    protected void startBossTimer() {
        Timer shootTimer = new Timer(500, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (getLevel() == 5) shootBossBullet();
            }
        }); shootTimer.setRepeats(true); shootTimer.start();
    }
    
    /**
     * Fire the Boss' bullet
     */
    protected void shootBossBullet() {
        int xPos = boss.x + boss.width - BossBullet.WIDTH / 2;
        int yPos = (boss.y + boss.width/2 - BossBullet.HEIGHT) + 10;
        BossBullet bossBullet = new BossBullet(xPos, yPos + 10); // 10 so it's a bit lower
        bossBullets.add(bossBullet);
        this.getSoundManager().playBulletSound();
    }

    /**
     * Move a bullet once fired.
     * Damage MegaMan if he's hit by a bullet.
     * @param bullet the bullet to move
     * @return if the bullet should be removed from screen
     */
    protected boolean moveBossBullet(BossBullet bullet) {
        if (bullet.getX() >= SCREEN_WIDTH || bullet.getX() <= 0) {
            return true;
        }
        if (bullet.intersects(megaMan)) {
            GameStatus status = getGameStatus();
            status.setLivesLeft(status.getLivesLeft() - 1);
            return true;
        }
        if (bullet.getY() - bullet.getSpeed() >= 0) {
            bullet.translate(-bullet.getSpeed(), 0);
            return false;
        } else { return true; }
    }
}
