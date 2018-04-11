package rbadia.voidspace.main;

import java.util.HashMap;

import rbadia.voidspace.graphics.GraphicsManager;
import rbadia.voidspace.model.Platform;
import rbadia.voidspace.sounds.SoundManager;

/**
 * Level very similar to LevelState1.  
 * With moving platforms
 */
public class Level3State extends Level1State {

    private static final long serialVersionUID = 3L;

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

    @Override
    public void updateScreen() {
        super.updateScreen();
        movePlatforms(); 
    }

    public void movePlatforms() {
        for (Platform platform : platforms) {
            if (platform.getMaxX() >= SCREEN_WIDTH) platform.reverse = true;
            else if (platform.getX() <= 0) platform.reverse = false;
            platform.translate((platform.reverse ? -1 : 1), 0);
        }
    }
}
