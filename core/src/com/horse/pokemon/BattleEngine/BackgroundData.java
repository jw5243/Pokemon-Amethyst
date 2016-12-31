package com.horse.pokemon.BattleEngine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class BackgroundData {
    private static final Vector2 STANDARD_BACKGROUND_SIZE = new Vector2(512, 288);
    private static final Vector2 STANDARD_ENEMY_BASE_SIZE = new Vector2(256, 128);
    private static final Vector2 STANDARD_USER_BASE_SIZE  = new Vector2(512, 64);
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
    
    public static Vector2 getStandardBackgroundSize() {
        return STANDARD_BACKGROUND_SIZE;
    }
    
    public static Vector2 getStandardEnemyBaseSize() {
        return STANDARD_ENEMY_BASE_SIZE;
    }
    
    public static Vector2 getStandardUserBaseSize() {
        return STANDARD_USER_BASE_SIZE;
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
}
