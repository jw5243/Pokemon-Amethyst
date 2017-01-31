package com.horse.pokemon.BattleEngine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

public class BackgroundData implements Disposable {
    private static final int STANDARD_BACKGROUND_WIDTH  = 512;
    private static final int STANDARD_BACKGROUND_HEIGHT = 288;
    private static final int STANDARD_ENEMY_BASE_WIDTH  = 256;
    private static final int STANDARD_ENEMY_BASE_HEIGHT = 128;
    private static final int STANDARD_USER_BASE_WIDTH   = 512;
    private static final int STANDARD_USER_BASE_HEIGHT  = 64;
    private final String  backgroundFilePath;
    private final String  enemyBaseFilePath;
    private final String  userBaseFilePath;
    private final Texture backgroundTexture;
    private final Texture enemyBaseTexture;
    private final Texture userBaseTexture;
    
    public BackgroundData(String backgroundFilePath, String enemyBaseFilePath, String userBaseFilePath) {
        this.backgroundFilePath = backgroundFilePath;
        this.enemyBaseFilePath = enemyBaseFilePath;
        this.userBaseFilePath = userBaseFilePath;
        this.backgroundTexture = new Texture(Gdx.files.internal(getBackgroundFilePath()));
        this.enemyBaseTexture = new Texture(Gdx.files.internal(getEnemyBaseFilePath()));
        this.userBaseTexture = new Texture(Gdx.files.internal(getUserBaseFilePath()));
    }
    
    public static int getStandardBackgroundWidth() {
        return STANDARD_BACKGROUND_WIDTH;
    }
    
    public static int getStandardBackgroundHeight() {
        return STANDARD_BACKGROUND_HEIGHT;
    }
    
    public static int getStandardEnemyBaseWidth() {
        return STANDARD_ENEMY_BASE_WIDTH;
    }
    
    public static int getStandardEnemyBaseHeight() {
        return STANDARD_ENEMY_BASE_HEIGHT;
    }
    
    public static int getStandardUserBaseWidth() {
        return STANDARD_USER_BASE_WIDTH;
    }
    
    public static int getStandardUserBaseHeight() {
        return STANDARD_USER_BASE_HEIGHT;
    }
    
    public Texture getBackgroundTexture() {
        return backgroundTexture;
    }
    
    public Texture getEnemyBaseTexture() {
        return enemyBaseTexture;
    }
    
    public Texture getUserBaseTexture() {
        return userBaseTexture;
    }
    
    public String getBackgroundFilePath() {
        return backgroundFilePath;
    }
    
    public String getEnemyBaseFilePath() {
        return enemyBaseFilePath;
    }
    
    public String getUserBaseFilePath() {
        return userBaseFilePath;
    }
    
    @Override
    public void dispose() {
        getBackgroundTexture().dispose();
        getEnemyBaseTexture().dispose();
        getUserBaseTexture().dispose();
    }
    
    @Override
    public String toString() {
        return new StringBuilder("{Background Texture = ").append(getBackgroundTexture()).append(", Enemy Base Texture = ").append(getEnemyBaseTexture()).append(", User Base Texture = ")
                                                          .append(getUserBaseTexture()).toString();
    }
}
