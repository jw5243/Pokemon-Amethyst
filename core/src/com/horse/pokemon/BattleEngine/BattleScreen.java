package com.horse.pokemon.BattleEngine;

import com.badlogic.gdx.Screen;
import com.horse.pokemon.Engine;

public class BattleScreen implements Screen {
    private Engine          engine;
    private BackgroundSetup backgroundSetup;
    
    public BattleScreen(Engine engine) {
        setEngine(engine);
        setBackgroundSetup(new BackgroundSetup(getEngine(), BackgroundInformation.CAVE));
    }
    
    public BackgroundSetup getBackgroundSetup() {
        return backgroundSetup;
    }
    
    public void setBackgroundSetup(BackgroundSetup backgroundSetup) {
        this.backgroundSetup = backgroundSetup;
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
        getBackgroundSetup().render(delta);
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
