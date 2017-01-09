package com.horse.pokemon.ObjectData.Players;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.horse.pokemon.AnimationEngine.AnimationManager;

public class NPC extends AbstractPlayer {
    //DEFAULT: Characters\\NPCSpriteSheets\\NPC 01.png
    private static final int DEFAULT_WIDTH  = 32;
    private static final int DEFAULT_HEIGHT = 48;
    private static final int IN_GAME_WIDTH  = 16;
    private static final int IN_GAME_HEIGHT = 24;
    private final String          npcSpriteSheetPath;
    private final Sprite          npcSprite;
    private final TextureRegion[] idleFrames;
    private final Animation[]     movingFrames;
    
    public NPC(String npcSpriteSheetPath) {
        this.npcSpriteSheetPath = npcSpriteSheetPath;
        this.npcSprite = new Sprite(new Texture(getNpcSpriteSheetPath()));
        idleFrames = new TextureRegion[4];
        movingFrames = new Animation[4];
        initializeAnimation();
    }
    
    public static int getInGameWidth() {
        return IN_GAME_WIDTH;
    }
    
    public static int getInGameHeight() {
        return IN_GAME_HEIGHT;
    }
    
    public static int getDefaultWidth() {
        return DEFAULT_WIDTH;
    }
    
    public static int getDefaultHeight() {
        return DEFAULT_HEIGHT;
    }
    
    public Sprite getNpcSprite() {
        return npcSprite;
    }
    
    private void initializeAnimation() {
        movingFrames[0] = AnimationManager.getAnimation(getNpcSprite().getTexture(), 4, 0.1f, new int[] {0, 32, 64, 96}, 0, getDefaultWidth(), getDefaultHeight());
        movingFrames[1] = AnimationManager.getAnimation(getNpcSprite().getTexture(), 4, 0.1f, new int[] {0, 32, 64, 96}, 48, getDefaultWidth(), getDefaultHeight());
        movingFrames[2] = AnimationManager.getAnimation(getNpcSprite().getTexture(), 4, 0.1f, new int[] {0, 32, 64, 96}, 96, getDefaultWidth(), getDefaultHeight());
        movingFrames[3] = AnimationManager.getAnimation(getNpcSprite().getTexture(), 4, 0.1f, new int[] {0, 32, 64, 96}, 144, getDefaultWidth(), getDefaultHeight());
        
        idleFrames[0] = new TextureRegion(getNpcSprite().getTexture(), 0, 0, getDefaultWidth(), getDefaultHeight());
        idleFrames[1] = new TextureRegion(getNpcSprite().getTexture(), 0, 48, getDefaultWidth(), getDefaultHeight());
        idleFrames[2] = new TextureRegion(getNpcSprite().getTexture(), 0, 96, getDefaultWidth(), getDefaultHeight());
        idleFrames[3] = new TextureRegion(getNpcSprite().getTexture(), 0, 144, getDefaultWidth(), getDefaultHeight());
    }
    
    public String getNpcSpriteSheetPath() {
        return npcSpriteSheetPath;
    }
    
    public TextureRegion[] getIdleFrames() {
        return idleFrames;
    }
    
    public Animation[] getMovingFrames() {
        return movingFrames;
    }
    
    @Override
    void update(float deltaTime) {
        setX(getPositionX());
        setY(getPositionY());
    }
    
    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(getNpcSprite(), getPositionX() - getInGameWidth() / 2, getPositionY() - getInGameHeight() / 2, getInGameWidth(), getInGameHeight());
    }
}