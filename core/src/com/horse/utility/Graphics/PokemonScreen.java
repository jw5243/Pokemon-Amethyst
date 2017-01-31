package com.horse.utility.Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.horse.pokemon.Engine;

public abstract class PokemonScreen implements Screen {
    private Engine engine;
    
    public PokemonScreen() {
    }
    
    public PokemonScreen(final Engine engine) {
        setEngine(engine);
    }
    
    public static float getDeltaTime() {
        return Gdx.graphics.getDeltaTime();
    }
    
    public Engine getEngine() {
        return engine;
    }
    
    public void setEngine(Engine engine) {
        this.engine = engine;
    }
}
