package com.horse.pokemon.BattleEngine;

import com.badlogic.gdx.utils.Disposable;
import com.horse.pokemon.Engine;

public class BackgroundSetup implements Disposable {
    private static final float SCREEN_TO_BACKGROUND_WIDTH_RATIO  = Engine.getvWidth() / BackgroundData.getStandardBackgroundWidth();
    private static final float SCREEN_TO_BACKGROUND_HEIGHT_RATIO = Engine.getvHeight() / BackgroundData.getStandardBackgroundHeight();
    private static final int   BACKGROUND_X_POSITION             = 0;
    private static final int   BACKGROUND_Y_POSITION             = 0;
    private static final int   ENEMY_BASE_X_POSITION             = (int)((Engine.getvWidth() - BackgroundData.getStandardEnemyBaseWidth()) / getScreenToBackgroundWidthRatio());
    private static final int   ENEMY_BASE_Y_POSITION             = (int)((Engine.getvHeight() - BackgroundData.getStandardEnemyBaseHeight()) / getScreenToBackgroundHeightRatio());
    private static final int   USER_BASE_X_POSITION              = -128;
    private static final int   USER_BASE_Y_POSITION              = 0;
    private static final int   TRANSITION_TIME                   = 2;
    private float                 currentTransitionTime;
    private Engine                engine;
    private BackgroundInformation backgroundInformation;
    
    public BackgroundSetup(Engine engine, BackgroundInformation backgroundInformation) {
        setCurrentTransitionTime(0f);
        setEngine(engine);
        setBackgroundInformation(backgroundInformation);
    }
    
    public static float getScreenToBackgroundWidthRatio() {
        return SCREEN_TO_BACKGROUND_WIDTH_RATIO;
    }
    
    public static float getScreenToBackgroundHeightRatio() {
        return SCREEN_TO_BACKGROUND_HEIGHT_RATIO;
    }
    
    public static int getBackgroundXPosition() {
        return BACKGROUND_X_POSITION;
    }
    
    public static int getBackgroundYPosition() {
        return BACKGROUND_Y_POSITION;
    }
    
    public static int getEnemyBaseXPosition() {
        return ENEMY_BASE_X_POSITION;
    }
    
    public static int getEnemyBaseYPosition() {
        return ENEMY_BASE_Y_POSITION;
    }
    
    public static int getUserBaseXPosition() {
        return USER_BASE_X_POSITION;
    }
    
    public static int getUserBaseYPosition() {
        return USER_BASE_Y_POSITION;
    }
    
    public static int getTransitionTime() {
        return TRANSITION_TIME;
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
    
    public void render(float delta) {
        setCurrentTransitionTime(getCurrentTransitionTime() >= getTransitionTime() ? getTransitionTime() : getCurrentTransitionTime() + delta);
        
        getEngine().getBatch().begin();
        
        getEngine().getBatch().draw(getBackgroundInformation().getBackgroundData().getBackgroundTexture(), getBackgroundXPosition(), getBackgroundYPosition(),
                                    BackgroundData.getStandardBackgroundWidth() * getScreenToBackgroundWidthRatio(),
                                    BackgroundData.getStandardBackgroundHeight() * getScreenToBackgroundHeightRatio()
        );
        getEngine().getBatch().draw(getBackgroundInformation().getBackgroundData().getEnemyBaseTexture(),
                                    getEnemyBaseXPosition() - ((getTransitionTime() - getCurrentTransitionTime()) * Engine.getvWidth()), getEnemyBaseYPosition(),
                                    BackgroundData.getStandardEnemyBaseWidth() * getScreenToBackgroundWidthRatio(),
                                    BackgroundData.getStandardEnemyBaseHeight() * getScreenToBackgroundHeightRatio()
        );
        getEngine().getBatch().draw(getBackgroundInformation().getBackgroundData().getUserBaseTexture(),
                                    getUserBaseXPosition() + ((getTransitionTime() - getCurrentTransitionTime()) * Engine.getvWidth()), getUserBaseYPosition(),
                                    BackgroundData.getStandardUserBaseWidth() * getScreenToBackgroundWidthRatio(),
                                    BackgroundData.getStandardUserBaseHeight() * getScreenToBackgroundHeightRatio()
        );
        
        getEngine().getBatch().end();
    }
    
    public float getCurrentTransitionTime() {
        return currentTransitionTime;
    }
    
    public void setCurrentTransitionTime(float currentTransitionTime) {
        this.currentTransitionTime = currentTransitionTime;
    }
    
    @Override
    public void dispose() {
        getBackgroundInformation().dispose();
    }
}
