package rbadia.voidspace.main;

/**
 * Implements the main game loop, i.e. what actions should be taken on each frame update.
 */
public class LevelLoop implements Runnable{
	private LevelState levelState;
	private LevelLogic gameLogic;
	private InputHandler inputHandler;

	/**
	 * Creates a new game loop.
	 * @param levelState the game screen
	 * @param gameLogic the game logic handler
	 * @param inputHandler the user input handler
	 */
	public LevelLoop(LevelState levelState){
		this.levelState = levelState;
		this.gameLogic = levelState.getGameLogic();
		this.inputHandler = levelState.getInputHandler();
	}

	/**
	 * Implements the run interface method. Should be called by the running thread.
	 */
	public void run() {

		levelState.doStart();
		while (!levelState.getGameStatus().isGameOver() 
		        && !levelState.isLevelWon() 
		        && !inputHandler.isNPressed() 
		        && levelState.getGameStatus().getBossLivesLeft() > 0) {
			
			// update the game graphics and repaint screen
			gameLogic.stateTransition(inputHandler, levelState);
			levelState.repaint();
			
			LevelLogic.delay(1000/60);
		}

		if (levelState.isLevelWon() || inputHandler.isNPressed()) levelState.doLevelWon();
		else levelState.doGameOverScreen();

	}

}