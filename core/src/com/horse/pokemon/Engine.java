package com.horse.pokemon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.horse.pokemon.BattleEngine.BackgroundData;
import com.horse.pokemon.BattleEngine.BattleScreen;
import com.horse.pokemon.GraphicsEngine.ScreenEngine.IntroScreen;
import com.horse.pokemon.GraphicsEngine.ScreenEngine.MainGameScreen;

/**
 * Class acting as the manager for containing all the screens to be used and some constants.
 *
 * @see Game
 */
public class Engine extends Game {
    /**
     * The {@link Integer} instance representing the virtual width for reference to all of the positions in the
     * background.
     */
    private static final int V_WIDTH = (int)(BackgroundData.getStandardBackgroundSize().x);
    
    /**
     * The {@link Integer} instance representing the virtual height for reference to all of the positions in the
     * background.
     *
     * @see BackgroundData#getStandardBackgroundSize()
     */
    private static final int V_HEIGHT = (int)(BackgroundData.getStandardBackgroundSize().y);
    
    /**
     * The length of the size of a tile to reference collisions and presice positions of some actors.
     */
    private static final int TILE_SIZE = 16;
    
    /**
     * Half the length of the size of a tile to reference collisions and presice positions of some actors.
     */
    private static final int HALF_TILE_SIZE = getTileSize() / 2;
    
    /**
     * Changes how much the user can see from its position.  A bigger number will show less area than a smaller number.
     */
    private static final int CAMERA_ZOOM_SCALE = 2;
    private static IntroScreen    introScreen;
    private static MainGameScreen mainGameScreen;
    private static BattleScreen   battleScreen;
    private        SpriteBatch    batch;
    
    public static int getHalfTileSize() {
        return HALF_TILE_SIZE;
    }
    
    public static int getvWidth() {
        return V_WIDTH;
    }
    
    public static int getvHeight() {
        return V_HEIGHT;
    }
    
    public static int getTileSize() {
        return TILE_SIZE;
    }
    
    public static int getCameraZoomScale() {
        return CAMERA_ZOOM_SCALE;
    }
    
    public Screen getScreen(screenTypes screenType) {
        if(screenType == screenTypes.INTRO_SCREEN) {
            return introScreen;
        } else if(screenType == screenTypes.MAIN_GAME_SCREEN) {
            return mainGameScreen;
        } else {
            return battleScreen;
        }
    }
    
    @Override
    public void create() {
        setBatch(new SpriteBatch());
        
        introScreen = new IntroScreen(this);
        mainGameScreen = new MainGameScreen(this);
        battleScreen = new BattleScreen(this);
        
        setScreen(getScreen(screenTypes.INTRO_SCREEN));
    }
    
    @Override
    public void dispose() {
        getScreen(screenTypes.INTRO_SCREEN).dispose();
        getScreen(screenTypes.MAIN_GAME_SCREEN).dispose();
        getScreen(screenTypes.BATTLE_SCREEN).dispose();
    
        System.out.println("Memory used: " + Runtime.getRuntime().totalMemory());
    }
    
    @Override
    public void pause() {
        
    }
    
    @Override
    public void resume() {
        
    }
    
    @Override
    public void render() {
        super.render();
    }
    
    @Override
    public void resize(int width, int height) {
        
    }
    
    public SpriteBatch getBatch() {
        return batch;
    }
    
    private void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }
    
    public enum screenTypes {
        INTRO_SCREEN, MAIN_GAME_SCREEN, BATTLE_SCREEN
    }
}