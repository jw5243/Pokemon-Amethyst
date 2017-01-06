package com.horse.pokemon.BattleEngine;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.horse.pokemon.Engine;

public class BackgroundSetup implements Disposable {
    private static final Vector2 SCREEN_TO_BACKGROUND_SIZE_RATIO =
            new Vector2(Engine.getvWidth() / BackgroundData.getStandardBackgroundSize().x, Engine.getvHeight() / BackgroundData.getStandardBackgroundSize().y);
    private static final Vector2 BACKGROUND_POSITION             = new Vector2(0, 0);
    private static final Vector2 ENEMY_BASE_POSITION             =
            new Vector2(Engine.getvWidth() - BackgroundData.getStandardEnemyBaseSize().x, Engine.getvHeight() - (BackgroundData.getStandardEnemyBaseSize().y * 1.2f));
    private static final Vector2 USER_BASE_POSITION              = new Vector2(-128, 0);
    private Engine                engine;
    private BackgroundInformation backgroundInformation;
    
    public BackgroundSetup(Engine engine, BackgroundInformation backgroundInformation) {
        setEngine(engine);
        setBackgroundInformation(backgroundInformation);
    }
    
    public static Vector2 getScreenToBackgroundSizeRatio() {
        return SCREEN_TO_BACKGROUND_SIZE_RATIO;
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
    
    public void render() {
        getEngine().getBatch().begin();
    
        getEngine().getBatch().draw(getBackgroundInformation().getBackgroundData().getBackgroundTexture(), getBackgroundPosition().x, getBackgroundPosition().y,
                                    BackgroundData.getStandardBackgroundSize().x * getScreenToBackgroundSizeRatio().x,
                                    BackgroundData.getStandardBackgroundSize().y * getScreenToBackgroundSizeRatio().y
        );
        getEngine().getBatch().draw(getBackgroundInformation().getBackgroundData().getEnemyBaseTexture(), getEnemyBasePosition().x, getEnemyBasePosition().y,
                                    BackgroundData.getStandardEnemyBaseSize().x * getScreenToBackgroundSizeRatio().x,
                                    BackgroundData.getStandardEnemyBaseSize().y * getScreenToBackgroundSizeRatio().y
        );
        getEngine().getBatch().draw(getBackgroundInformation().getBackgroundData().getUserBaseTexture(), getUserBasePosition().x, getUserBasePosition().y,
                                    BackgroundData.getStandardUserBaseSize().x * getScreenToBackgroundSizeRatio().x,
                                    BackgroundData.getStandardUserBaseSize().y * getScreenToBackgroundSizeRatio().y
        );
        
        getEngine().getBatch().end();
    }
    
    @Override
    public void dispose() {
        getBackgroundInformation().dispose();
    }
}
