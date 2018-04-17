package rbadia.voidspace.main;

/**
 * Container for game flags and/or status variables.
 */
public class GameStatus {
	private boolean gameOver = false;
	private boolean gameWon = false;
	
	// status variables
	private boolean newMegaMan;
	private boolean newAsteroid;
	private long asteroidsDestroyed = 0;

	private int livesLeft;
	private int bossLivesLeft = 6;
	private int level = 1;
	
	public GameStatus() {}
	
	public synchronized boolean isGameOver() {
		return gameOver;
	}
	
	public synchronized void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	
	/**
	 * Indicates if the game has and the "You Win!!!" message is displaying.
	 * @return if the game has ended and the "You Win!!!" message is displaying.
	 */
	public synchronized boolean isGameWon() {
		return gameWon;
	}
	
    /**
     * Sets whether the game has the "You Win!!!" message displaying.
     * @param if the game has ended and the "You Win!!!" message should display.
     */
	public synchronized void setGameWon(boolean gameWon) {
		this.gameWon = gameWon;
	}
	
	/**
	 * Indicates if a new MegaMan should be created/drawn.
	 * @return if a new megaMan should be created/drawn
	 */
	public synchronized boolean isNewMegaMan() {
		return newMegaMan;
	}

	/**
     * Sets whether a new MegaMan should be created/drawn.
     * @param if a new megaMan should be created/drawn
     */
	public synchronized void setNewMegaMan(boolean newMegaMan) {
		this.newMegaMan = newMegaMan;
	}	
	
	/**
	 * Indicates if a new asteroid should be created/drawn.
	 * @return if a new asteroid should be created/drawn
	 */
	public synchronized boolean isNewAsteroid() {
		return newAsteroid;
	}
	
    /**
     * Sets whether a new asteroid should be created/drawn.
     * @param boolean if a new asteroid should be created/drawn
     */
	public synchronized void setNewAsteroid(boolean newAsteroid) {
		this.newAsteroid = newAsteroid;
	}

	/**
	 * Returns the number of asteroid destroyed. 
	 * @return the number of asteroid destroyed
	 */
	public synchronized long getAsteroidsDestroyed() {
		return asteroidsDestroyed;
	}

	/*
	 * Sets the number of asteroids destroyed
	 * to the specified amount
	 * @param long asteroidsDestroyed amount
	 */
	public synchronized void setAsteroidsDestroyed(long asteroidsDestroyed) {
		this.asteroidsDestroyed = asteroidsDestroyed;
	}

	/**
	 * Returns the number lives left.
	 * @return the number lives left
	 */
	public synchronized int getLivesLeft() {
		return livesLeft;
	}
	
    /*
     * Sets MegaMan's lives left
     * to the specified amount
     * @param int number of lives to set
     */
	public synchronized void setLivesLeft(int livesLeft) {
		this.livesLeft = livesLeft;
	}
	
	/*
	 * Returns the number of lives left (boss)
	 * @return int number of lives
	 */
	public synchronized int getBossLivesLeft() {
	    return bossLivesLeft;
	}
	
	/*
	 * Sets the boss lives to a specified number
	 * @param int boss lives
	 */
	public synchronized void setBossLivesLeft(int bossLivesLeft) {
	    this.bossLivesLeft = bossLivesLeft;
	}

	/*
	 * Gets the current level
	 * @return int current level
	 */
	public int getLevel() {
		return level;
	}

	/*
	 * Sets the current level
	 * @param int level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}
}
