package com.horse.pokemon.BattleEngine;

import com.badlogic.gdx.math.Vector2;
import com.horse.pokemon.Engine;

public class BackgroundSetup {
    private static final Vector2 BACKGROUND_POSITION = new Vector2(0, 0);
    private static final Vector2 ENEMY_BASE_POSITION = new Vector2(0, 0);
    private static final Vector2 USER_BASE_POSITION = new Vector2(0, 0);
    private Engine                engine;
    private BackgroundInformation backgroundInformation;
    
    public BackgroundSetup(Engine engine, BackgroundInformation backgroundInformation) {
        setEngine(engine);
        setBackgroundInformation(backgroundInformation);
    }
    
    public static Vector2 getBackgroundPosition() {
        return BACKGROUND_POSITION;
    }

    public static Vector2 getEnemyBasePosition() {
        return ENEMY_BASE_POSITION;
    }
    
    public static Vector2 getUserBasePosition() {
        return USER_BASE_POSITION;
    }
    
    public Engine getEngine() {
        return engine;
    }
    
    public void setEngine(Engine engine) {
        this.engine = engine;
    }
    
    public BackgroundInformation getBackgroundInformation() {
        return backgroundInformation;
    }
    
    public void setBackgroundInformation(BackgroundInformation backgroundInformation) {
        this.backgroundInformation = backgroundInformation;
    }
    
    public void render(float deltaTime) {
        getEngine().getBatch().begin();
        
        getEngine().getBatch().draw(getBackgroundInformation().getBackgroundData().getBackgroundTexture(),
                                    getBackgroundPosition().x, getBackgroundPosition().y, Engine.getvWidth(),
                                    Engine.getvHeight()
        );
        getEngine().getBatch()
                   .draw(getBackgroundInformation().getBackgroundData().getEnemyBaseTexture(), getEnemyBasePosition().x,
                         getEnemyBasePosition().y
                   );
        getEngine().getBatch()
                   .draw(getBackgroundInformation().getBackgroundData().getUserBaseTexture(), getUserBasePosition().x,
                         getUserBasePosition().y
                   );
        
        getEngine().getBatch().end();
    }
}
