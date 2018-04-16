package rbadia.voidspace.graphics;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import rbadia.voidspace.model.Asteroid;
//import rbadia.voidspace.model.BigAsteroid;
import rbadia.voidspace.model.BigBullet;
import rbadia.voidspace.model.Boss;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.Floor;
import rbadia.voidspace.model.Lives;
import rbadia.voidspace.model.BossBullet;
//import rbadia.voidspace.model.BulletBoss2;
import rbadia.voidspace.model.MegaMan;
import rbadia.voidspace.model.Platform;

/**
 * Manages and draws game graphics and images.
 */
public class GraphicsManager {
	private BufferedImage megaManImg;
	private BufferedImage megaManImgL;
	private BufferedImage megaFallRImg;
    private BufferedImage megaFallLImg;
	private BufferedImage megaFireRImg;
    private BufferedImage megaFireLImg;
	private BufferedImage floorImg;
	private BufferedImage platformImg;
	private BufferedImage bulletImg;
	private BufferedImage bigBulletImg;
	private BufferedImage asteroidImg;
	private BufferedImage asteroidExplosionImg;
	private BufferedImage megaManExplosionImg;
	private BufferedImage bigAsteroidExplosionImg;
	private BufferedImage megaManBoss;
	private BufferedImage megaManBossL;
	private BufferedImage bossBulletImg;
    private BufferedImage livesImg;

	/**
	 * Creates a new graphics manager and loads the game images.
	 */
	public GraphicsManager(){
		// load images
		try {
			this.megaManImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaMan3.png"));
	        this.megaManImgL = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaMan3L.png"));
			this.megaFallRImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaFallRight.png"));
			this.megaFireRImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaFireRight.png"));
	        this.megaFallLImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaFallLeft.png"));
	        this.megaFireLImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaFireLeft.png"));
			this.floorImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaFloor.png"));
			this.platformImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/platform3.png"));
			this.asteroidImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/asteroid.png"));
			this.asteroidExplosionImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/asteroidExplosion.png"));
			this.bulletImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/bullet.png"));
			this.bigBulletImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/bigBullet.png"));
			this.megaManBoss = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaManBoss.png"));
			this.megaManBossL = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaManBossL.png"));
	        this.bossBulletImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/bossBullet.png"));
	        this.livesImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/pixeledheart.png"));
	        
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "The graphic files are either corrupt or missing.",
					"MegaMan!!! - Fatal Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * Draws a MegaMan image to the specified graphics canvas.
	 * @param MegaMan the ship to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */

	public void drawMegaMan(MegaMan megaMan, Graphics2D g2d, ImageObserver observer) {
	    g2d.drawImage((megaMan.lookingLeft ? megaManImgL : megaManImg), megaMan.x, megaMan.y, observer);
	}

	public void drawMegaFall(MegaMan megaMan, Graphics2D g2d, ImageObserver observer) {
	    g2d.drawImage((megaMan.lookingLeft ? megaFallLImg : megaFallRImg), megaMan.x, megaMan.y, observer);
	}

	public void drawMegaFire(MegaMan megaMan, Graphics2D g2d, ImageObserver observer) {
	    g2d.drawImage((megaMan.lookingLeft ? megaFireLImg : megaFireRImg), megaMan.x, megaMan.y, observer);
	}

	public void drawFloor(Floor floor, Graphics2D g2d, ImageObserver observer, int i) {
			g2d.drawImage(floorImg, floor.x, floor.y, observer);				
	}
	
	public void drawPlatform(Platform platform, Graphics2D g2d, ImageObserver observer, int i) {
			g2d.drawImage(platformImg, platform.x , platform.y, observer);	
	}

    /**
     * Draws a Boss image to the specified graphics canvas.
     * @param MegaMan the ship to draw
     * @param g2d the graphics canvas
     * @param observer object to be notified
     */
	public void drawBoss(Boss boss, Graphics2D g2d, ImageObserver observer) {
	    g2d.drawImage((boss.lookingLeft ? megaManBossL : megaManBoss), boss.x, boss.y, observer);
	}
	
	 /**
     * Draws a boss bullet image to the specified graphics canvas.
     * @param bullet the bullet to draw
     * @param g2d the graphics canvas
     * @param observer object to be notified
     */
    public void drawBossBullet(BossBullet bullet, Graphics2D g2d, ImageObserver observer) {
        g2d.drawImage(bossBulletImg, bullet.x, bullet.y, observer);
    }
    
	/**
	 * Draws a bullet image to the specified graphics canvas.
	 * @param bullet the bullet to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawBullet(Bullet bullet, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(bulletImg, bullet.x, bullet.y, observer);
	}

	/**
	 * Draws a bullet image to the specified graphics canvas.
	 * @param bigBullet the bullet to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawBigBullet(BigBullet bigBullet, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(bigBulletImg, bigBullet.x, bigBullet.y, observer);
	}

	/**
	 * Draws an asteroid image to the specified graphics canvas.
	 * @param asteroid the asteroid to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawAsteroid(Asteroid asteroid, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(asteroidImg, asteroid.x, asteroid.y, observer);
	}

	/**
	 * Draws a MegaMan explosion image to the specified graphics canvas.
	 * @param megaManExplosion the bounding rectangle of the explosion
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawMegaManExplosion(Rectangle megaManExplosion, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(megaManExplosionImg, megaManExplosion.x, megaManExplosion.y, observer);
	}

	/**
	 * Draws an asteroid explosion image to the specified graphics canvas.
	 * @param asteroidExplosion the bounding rectangle of the explosion
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawAsteroidExplosion(Rectangle asteroidExplosion, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(asteroidExplosionImg, asteroidExplosion.x, asteroidExplosion.y, observer);
	}

	public void drawBigAsteroidExplosion(Rectangle bigAsteroidExplosion, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(bigAsteroidExplosionImg, bigAsteroidExplosion.x, bigAsteroidExplosion.y, observer);
	}
	
	/**
	 * Draws a power-up image to the specified graphics canvas.
	 * @param Lives the power-up to be drawn
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawLives(Lives lives, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(livesImg, lives.x, lives.y, observer);
	}
}
