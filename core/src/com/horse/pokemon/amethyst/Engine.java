package com.horse.pokemon.amethyst;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.horse.pokemon.amethyst.data.handlers.readers.PokemonDataReader;
import com.horse.pokemon.amethyst.graphics.IntroScreen;
import com.horse.pokemon.amethyst.graphics.MainGameScreen;
import com.horse.pokemon.amethyst.graphics.battle.system.BackgroundData;
import com.horse.pokemon.amethyst.graphics.battle.system.BattleScreen;
import org.openjdk.jol.info.GraphLayout;

/**
 * Class acting as the manager for containing all the screens to be used and some constants.
 *
 * @see Game
 */
public class Engine extends Game {
    /**
     * The {@code int} instance representing the virtual width for reference to all of the positions in the
     * background.
     *
     * @see BackgroundData#getStandardBackgroundWidth()
     */
    private static final int V_WIDTH = (int)(BackgroundData.getStandardBackgroundWidth());
    
    /**
     * The {@code int} instance representing the virtual height for reference to all of the positions in the
     * background.
     *
     * @see BackgroundData#getStandardBackgroundHeight()
     */
    private static final int V_HEIGHT = (int)(BackgroundData.getStandardBackgroundHeight());
    
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
    private static final int CAMERA_ZOOM_SCALE = 1;
    
    /**
     * The {@link IntroScreen} representing the scenes and actions for when the game is started from the beginning.
     */
    private static IntroScreen    introScreen;
    private static MainGameScreen mainGameScreen;
    private static BattleScreen   battleScreen;
    private static PokemonDataReader pokemonDataReader = new PokemonDataReader();
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
    
        setScreen(getScreen(screenTypes.MAIN_GAME_SCREEN));
    }
    
    @Override
    public void dispose() {
        getScreen(screenTypes.INTRO_SCREEN).dispose();
        getScreen(screenTypes.MAIN_GAME_SCREEN).dispose();
        getScreen(screenTypes.BATTLE_SCREEN).dispose();
    
        System.out.println(GraphLayout.parseInstance(this).toFootprint());
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
    
    @Override
    public void setScreen(Screen screen) {
        super.setScreen(screen);
        getBatch().setColor(-1.7014117e38f);
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