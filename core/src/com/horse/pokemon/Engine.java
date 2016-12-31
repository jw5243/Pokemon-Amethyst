package com.horse.pokemon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.horse.pokemon.BattleEngine.BattleScreen;
import com.horse.pokemon.GraphicsEngine.ScreenEngine.MainGameScreen;

public class Engine extends Game {
    private static final int V_WIDTH           = 1024;
    private static final int V_HEIGHT          = 576;
    private static final int TILE_SIZE         = 16;
    private static final int CAMERA_ZOOM_SCALE = 3;
    
    private static MainGameScreen mainGameScreen;
    private static BattleScreen   battleScreen;
    
    private SpriteBatch batch;
    
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
        if(screenType == screenTypes.MAIN_GAME_SCREEN) {
            return mainGameScreen;
        } else {
            return battleScreen;
        }
    }
    
    @Override
    public void create() {
        setBatch(new SpriteBatch());
        mainGameScreen = new MainGameScreen(this);
        battleScreen = new BattleScreen(this);
        
        setScreen(getScreen(screenTypes.MAIN_GAME_SCREEN));
    }
    
    @Override
    public void dispose() {
        getScreen(screenTypes.MAIN_GAME_SCREEN).dispose();
        getScreen(screenTypes.BATTLE_SCREEN).dispose();
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
    
    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }
    
    public enum screenTypes {
        MAIN_GAME_SCREEN, BATTLE_SCREEN
    }
}