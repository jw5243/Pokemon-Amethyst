package com.horse.pokemon.amethyst.graphics;

import com.badlogic.gdx.Screen;
import com.horse.pokemon.amethyst.Engine;

public class IntroScreen implements Screen {
    private Engine engine;
    
    public IntroScreen(Engine engine) {
        setEngine(engine);
    }
    
    public Engine getEngine() {
        return engine;
    }
    
    public void setEngine(Engine engine) {
        this.engine = engine;
    }
    
    @Override
    public void show() {
        
    }
    
    @Override
    public void render(float delta) {
        getEngine().setScreen(getEngine().getScreen(Engine.screenTypes.MAIN_GAME_SCREEN));
    }
    
    @Override
    public void resize(int width, int height) {
        
    }
    
    @Override
    public void pause() {
        
    }
    
    @Override
    public void resume() {
        
    }
    
    @Override
    public void hide() {
        
    }
    
    @Override
    public void dispose() {
        
    }
}
