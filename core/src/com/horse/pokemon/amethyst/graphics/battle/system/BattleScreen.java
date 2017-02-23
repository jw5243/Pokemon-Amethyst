package com.horse.pokemon.amethyst.graphics.battle.system;

import com.badlogic.gdx.Screen;
import com.horse.pokemon.amethyst.Engine;
import com.horse.pokemon.amethyst.graphics.audio.AudioData;

public class BattleScreen implements Screen {
    private Engine          engine;
    private BackgroundSetup backgroundSetup;
    private AudioData       sound;
    
    public BattleScreen(Engine engine) {
        setEngine(engine);
        setBackgroundSetup(new BackgroundSetup(getEngine(), BackgroundInformation.CAVE));
    }
    
    public AudioData getSound() {
        return sound;
    }
    
    public void setSound(AudioData sound) {
        this.sound = sound;
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
        setSound(AudioData.BATTLE_WILD_POKEMON);
        getSound().playAudio();
    }
    
    @Override
    public void render(float delta) {
        //if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
        //    getEngine().setScreen(getEngine().getScreen(Engine.screenTypes.MAIN_GAME_SCREEN));
        //    getSound().getAudio().dispose();
        //}
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
        getBackgroundSetup().dispose();
    }
}
