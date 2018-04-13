package rbadia.voidspace.main;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import rbadia.voidspace.graphics.GraphicsManager;
import rbadia.voidspace.model.Boss;
import rbadia.voidspace.model.BossBullet;
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
        checkMegaManBossCollision();
    }
    
    /**
     * Create a new boss
     */
    public Boss newBoss(Level5State screen) {
        // Minus 80 so it's not located on the exact corner
        int xPos = (int) (SCREEN_WIDTH - Boss.WIDTH - 80);
        int yPos = (int) (SCREEN_HEIGHT - Boss.HEIGHT - 80);
        
        boss = new Boss(xPos, yPos);
        return boss;
    }

    /*
     * Draws the boss
     */
    public void drawBoss() {
        Graphics2D g2d = getGraphics2D();
        getGraphicsManager().drawBoss(boss, g2d, this);
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
     * Removes 1 health if they intersect
     */
    public void checkMegaManBossCollision() {
        GameStatus status = getGameStatus();
        
        if (boss.intersects(megaMan)) {
            status.setLivesLeft(status.getLivesLeft() - 1);
            megaMan.translate(megaMan.x - 10, megaMan.y);
        }
    }

    /*
     * Starts a timer so the boss shoots and moves every 2 seconds
     */
    protected void startBossTimer() {
        Timer shootTimer = new Timer(2000, new ActionListener() {
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
        int yPos = boss.y + boss.width/2 - BossBullet.HEIGHT + 4;
        BossBullet bossBullet = new BossBullet(xPos, yPos);
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
