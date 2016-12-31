package com.horse.pokemon.BattleEngine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class BackgroundData {
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
