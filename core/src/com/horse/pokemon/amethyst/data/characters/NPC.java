package com.horse.pokemon.amethyst.data.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.horse.pokemon.amethyst.Engine;
import com.horse.pokemon.amethyst.graphics.animation.AnimationManager;
import com.horse.pokemon.amethyst.graphics.background.system.MapCreator;

import java.util.ArrayList;

public class NPC extends BasePlayer {
    //DEFAULT: Characters\\NPCSpriteSheets\\NPC 01.png
    private static final int                  DEFAULT_WIDTH   = 32;
    private static final int                  DEFAULT_HEIGHT  = 48;
    private static final int                  IN_GAME_WIDTH   = 16;
    private static final int                  IN_GAME_HEIGHT  = 24;
    private static final float                ANIMATION_SPEED = 0.5f;
    private static       ArrayList<Rectangle> npcPositions    = new ArrayList<>(0);
    private final String          npcSpriteSheetPath;
    private final Sprite          npcSprite;
    private final TextureRegion[] idleFrames;
    private final Animation[]     movingFrames;
    private final int             positionID;
    
    public NPC(MapCreator mapCreator, String npcSpriteSheetPath) {
        this.npcSpriteSheetPath = npcSpriteSheetPath;
        this.npcSprite = new Sprite(new Texture(getNpcSpriteSheetPath()));
        setMapCreator(mapCreator);
        setCurrentDirection(getDOWN());
        resetPosition();
        positionID = getNpcPositions().size();
        getNpcPositions().add(getPositionID(), getCurrentCollisionRectangle());
        idleFrames = new TextureRegion[4];
        movingFrames = new Animation[4];
        initializeAnimation();
    
        addAction(Actions.forever(getMovementAction()));
    }
    
    public static ArrayList<Rectangle> getNpcPositions() {
        return npcPositions;
    }
    
    public static void setNpcPositions(ArrayList<Rectangle> npcPositions) {
        NPC.npcPositions = npcPositions;
    }
    
    public static float getAnimationSpeed() {
        return ANIMATION_SPEED;
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
    
    public int getPositionID() {
        return positionID;
    }
    
    private void resetPosition() {
        resetPosition(getMapCreator(), false);
    }
    
    public void resetPosition(MapCreator mapCreator, boolean resetMapCreator) {
        setMapCreator(resetMapCreator ? mapCreator : getMapCreator());
        setX((int)(mapCreator.getNpcStartPositions().get(getNpcSpriteSheetPath()).x + getInGameWidth() / 2));
        setY((int)(mapCreator.getNpcStartPositions().get(getNpcSpriteSheetPath()).y + getInGameHeight() / 2));
    }
    
    public Sprite getNpcSprite() {
        return npcSprite;
    }
    
    private void initializeAnimation() {
        movingFrames[0] = AnimationManager
                              .getAnimation(getNpcSprite().getTexture(), 4, 0.1f, new int[] {0, 32, 64, 96}, 144,
                                            getDefaultWidth(), getDefaultHeight()
                              );
        movingFrames[1] = AnimationManager
                              .getAnimation(getNpcSprite().getTexture(), 4, 0.1f, new int[] {0, 32, 64, 96}, 0,
                                            getDefaultWidth(), getDefaultHeight()
                              );
        movingFrames[2] = AnimationManager
                              .getAnimation(getNpcSprite().getTexture(), 4, 0.1f, new int[] {0, 32, 64, 96}, 96,
                                            getDefaultWidth(), getDefaultHeight()
                              );
        movingFrames[3] = AnimationManager
                              .getAnimation(getNpcSprite().getTexture(), 4, 0.1f, new int[] {0, 32, 64, 96}, 48,
                                            getDefaultWidth(), getDefaultHeight()
                              );
    
    
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
    
    private TextureRegion getFrame(float deltaTime) {
        float stateTime = getAnimationTimer() * getAnimationSpeed();
    
        setAnimationTimer(findCurrentState() == findCurrentState() ? getAnimationTimer() + deltaTime : 0);
        setPreviousState(findCurrentState());
        return (findCurrentState() == getWALKING()) ?
               (getCurrentDirection() == getUP()) ? (TextureRegion)(getMovingFrames()[0].getKeyFrame(stateTime, true)) :
               (getCurrentDirection() == getDOWN()) ?
               (TextureRegion)(getMovingFrames()[1].getKeyFrame(stateTime, true)) :
               (getCurrentDirection() == getRIGHT()) ?
               (TextureRegion)(getMovingFrames()[2].getKeyFrame(stateTime, true)) :
               (TextureRegion)(getMovingFrames()[3].getKeyFrame(stateTime, true)) :
               (getCurrentDirection() == getUP()) ? getIdleFrames()[0] :
               (getCurrentDirection() == getDOWN()) ? getIdleFrames()[1] :
               (getCurrentDirection() == getRIGHT()) ? getIdleFrames()[2] : getIdleFrames()[3];
    }
    
    @Override
    public byte findCurrentState() {
        if(isFlag(getIsMoving())) {
            return getWALKING();
        } else {
            return getIDLE();
        }
    }
    
    private void updateAlignment() {
        if((getX() + (Engine.getHalfTileSize())) % Engine.getTileSize() == 0 &&
           (getY() + (Engine.getHalfTileSize())) % Engine.getTileSize() - 4 == 0) {
            raiseFlag(getIsAligned());
        } else {
            removeFlag(getIsAligned());
        }
    }
    
    private void updateAnimation(float deltaTime) {
        getNpcSprite().setRegion(getFrame(deltaTime));
    }
    
    private void updateCurrentCollisionRectangle() {
        setCurrentCollisionRectangle(new Rectangle(getX(), getY(), Engine.getHalfTileSize(), Engine.getHalfTileSize()));
    }
    
    private void updateGlobalPosition() {
        getNpcPositions().set(getPositionID(), getCurrentCollisionRectangle());
    }
    
    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(getNpcSprite(), getX() - getInGameWidth() / 2, getY() - getInGameHeight() / 2, getInGameWidth(),
                   getInGameHeight()
        );
    }
    
    @Override
    public void act(float deltaTime) {
        super.act(deltaTime);
        updateDireciton();
        updateAlignment();
        updateCurrentCollisionRectangle();
        updateAnimation(deltaTime);
        updateGlobalPosition();
    }
    
    @Override
    public void setX(float x) {
        if(x > getX()) {
            setMovementFlag(getIsMovingRight());
        } else if(x < getX()) {
            setMovementFlag(getIsMovingLeft());
        }
        
        super.setX(x);
    }
    
    @Override
    public void setY(float y) {
        if(y > getY()) {
            setMovementFlag(getIsMovingUp());
        } else if(y < getY()) {
            setMovementFlag(getIsMovingDown());
        }
        
        super.setY(y);
    }
    
    private void updateDireciton() {
        if(getMovementFlag() == getIsMovingUp()) {
            setCurrentDirection(getUP());
        } else if(getMovementFlag() == getIsMovingDown()) {
            setCurrentDirection(getDOWN());
        } else if(getMovementFlag() == getIsMovingRight()) {
            setCurrentDirection(getRIGHT());
        } else if(getMovementFlag() == getIsMovingLeft()) {
            setCurrentDirection(getLEFT());
        }
    }
    
    private Action getMovementAction() {
        return Actions.sequence(new Action() {
            @Override
            public boolean act(float delta) {
                raiseFlag(getIsMoving());
                removeFlag(getIsAligned());
                setX(getX() + 1);
                updateAlignment();
                return isFlag(getIsAligned());
            }
        }, new Action() {
            @Override
            public boolean act(float delta) {
                raiseFlag(getIsMoving());
                removeFlag(getIsAligned());
                setY(getY() + 1);
                updateAlignment();
                return isFlag(getIsAligned());
            }
        }, new Action() {
            @Override
            public boolean act(float delta) {
                raiseFlag(getIsMoving());
                removeFlag(getIsAligned());
                setX(getX() - 1);
                updateAlignment();
                return isFlag(getIsAligned());
            }
        }, new Action() {
            @Override
            public boolean act(float delta) {
                raiseFlag(getIsMoving());
                removeFlag(getIsAligned());
                setY(getY() - 1);
                updateAlignment();
                return isFlag(getIsAligned());
            }
        });
    }
}
