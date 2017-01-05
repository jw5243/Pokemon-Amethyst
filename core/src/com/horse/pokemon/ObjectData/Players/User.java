package com.horse.pokemon.ObjectData.Players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.horse.pokemon.AnimationEngine.AnimationInitializer;
import com.horse.pokemon.AnimationEngine.AnimationInterface;
import com.horse.pokemon.AnimationEngine.AnimationManager;
import com.horse.pokemon.Engine;
import com.horse.pokemon.GraphicsEngine.MainInterface.HandleInput;
import com.horse.pokemon.GraphicsEngine.ScreenEngine.MapCreator;
import com.horse.pokemon.ObjectData.TiledObjects.TileObject;
import com.horse.pokemon.ObjectData.TiledObjects.Water;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Class acting as the protagonist in the Pokemon game, acting as the graphics, input, and movement for the character.  {@code User} is also generally used
 * as the center of the camera of the screen.  {@code User} extends {@link AbstractPlayer} which then extends {@link Actor}, so the user can be added onto
 * the screen using a {@link Stage}, where the {@code User}'s {@link #draw(Batch, float)} is overridden to draw the sprite that this class stores.  A user
 * instance is created with a {@code MapCreator}.  In the render method of the screen, a {@code Stage} has to have an instance of {@code User} and be
 * calling {@link Stage#draw()} to draw the user every frame.
 *
 * @see Stage
 * @see AbstractPlayer
 * @see Actor
 * @see AnimationInterface
 * @see TextureRegion
 * @see Animation
 * @see HandleInput
 * @see MapCreator
 * @see Sprite
 * @see Rectangle
 */
public final class User extends AbstractPlayer implements AnimationInterface {
    /**
     * The {@code int} instance representing the amount of pixels from the left-most part of the {@code User} to the right-most part of the {@code User}
     * when in the {@link PlayerActions} {@link PlayerActions#WALKING} or {@link PlayerActions#IDLE} if the {@code User} is on land, also determining how
     * big the user is drawn every frame x-wise.
     */
    private static final byte USER_WALK_WIDTH = 16;
    
    /**
     * The {@code int} instance representing the amount of pixels from the upper-most part of the {@code User} to the bottom-most part of the {@code User}
     * when in the {@link PlayerActions} {@link PlayerActions#WALKING} or {@link PlayerActions#IDLE} if the {@code User} is on land, also determining how
     * big the user is draw every frame y-wise.
     */
    private static final byte USER_WALK_HEIGHT = 19;
    
    /**
     * The {@code int} instance representing the amount of pixels from the left-most part of the {@code User} to the right-most part of the {@code User}
     * when in the {@link PlayerActions} {@link PlayerActions#SWIMMING} or {@link PlayerActions#IDLE} if the {@code User} is on water, also determining how
     * big the user is drawn every frame x-wise.
     */
    private static final byte USER_SWIM_WIDTH = 22;
    
    /**
     * The {@code int} instance representing the amount of pixels from the upper-most part of the {@code User} to the bottom-most part of the {@code User}
     * when in the {@link PlayerActions} {@link PlayerActions#SWIMMING} or {@link PlayerActions#IDLE} if the {@code User} is on water, also determining how
     * big the user is drawn every frame y-wise.
     */
    private static final byte USER_SWIM_HEIGHT = 24;
    
    /**
     *
     */
    private static final byte   UP                     = 1;
    private static final byte   DOWN                   = 2;
    private static final byte   RIGHT                  = 3;
    private static final byte   LEFT                   = 4;
    private static final float  ANIMATION_SPEED        = 0.5f;
    private static final String USER_INFORMATION       = "User\\GDX_Users\\User.pack";
    private static final String USER_ATLAS_REGION_NAME = "SpriteSheetUser";
    private final TextureRegion[] userIdleOnLand;
    private final TextureRegion[] userIdleOnWater;
    private final Animation[]     userWalk;
    private final Animation[]     userSwim;
    private final HandleInput     handleInput;
    private final Sprite          userSprite;
    private       MapCreator      mapCreator;
    private       Rectangle       currentCollisionRectangle;
    private       PlayerActions   previousState;
    private       float           positionX;
    private       float           positionY;
    private       float           stateTimer;
    private       boolean         moving;
    private       boolean         aligned;
    private       boolean         futureCollision;
    private       boolean         swimming;
    private       byte            direction;
    
    public User(MapCreator mapCreator) {
        setPreviousState(getCurrentState());
        
        setStateTimer(0);
        setDirection(getDOWN());
        
        setMoving(false);
        setAligned(true);
        setFutureCollision(false);
        setSwimming(false);
        
        userSprite = new Sprite(new TextureAtlas(User.getUserInformation()).findRegion(getUserAtlasRegionName()));
        
        setMapCreator(mapCreator);
        
        userIdleOnLand = new TextureRegion[4];
        userIdleOnWater = new TextureRegion[4];
        
        userWalk = new Animation[4];
        userSwim = new Animation[4];
        
        initializeAnimation();
        
        setBounds(0, 0, Engine.getTileSize() / 2, Engine.getTileSize() / 2);
        getUserSprite().setRegion(getUserIdleOnLand()[1]);
        
        resetPosition();
        
        updateCurrentCollisionRectangle();
        
        handleInput = new HandleInput((float dt) -> {
            if(isAligned()) {
                setMoving(true);
                setAligned(false);
                setDirection(getUP());
                setFutureCollision(isColliding(getFutureRectangle(0, Engine.getTileSize()), true));
            }
        }, (float dt) -> {
            if(isAligned()) {
                setMoving(true);
                setAligned(false);
                setDirection(getDOWN());
                setFutureCollision(isColliding(getFutureRectangle(0, -Engine.getTileSize()), true));
            }
        }, (float dt) -> {
            if(isAligned()) {
                setMoving(true);
                setAligned(false);
                setDirection(getRIGHT());
                setFutureCollision(isColliding(getFutureRectangle(Engine.getTileSize(), 0), true));
            }
        }, (float dt) -> {
            if(isAligned()) {
                setMoving(true);
                setAligned(false);
                setDirection(getLEFT());
                setFutureCollision(isColliding(getFutureRectangle(-Engine.getTileSize(), 0), true));
            }
        }, (float dt) -> {
            if(isAligned()) {
                setMoving(false);
            }
        });
    }
    
    private static byte getUP() {
        return UP;
    }
    
    private static byte getDOWN() {
        return DOWN;
    }
    
    private static byte getRIGHT() {
        return RIGHT;
    }
    
    private static byte getLEFT() {
        return LEFT;
    }
    
    private static String getUserAtlasRegionName() {
        return USER_ATLAS_REGION_NAME;
    }
    
    private static byte getUserSwimWidth() {
        return USER_SWIM_WIDTH;
    }
    
    private static byte getUserSwimHeight() {
        return USER_SWIM_HEIGHT;
    }
    
    private static String getUserInformation() {
        return USER_INFORMATION;
    }
    
    private static byte getUserWalkWidth() {
        return USER_WALK_WIDTH;
    }
    
    private static byte getUserWalkHeight() {
        return USER_WALK_HEIGHT;
    }
    
    private Rectangle getCurrentCollisionRectangle() {
        return currentCollisionRectangle;
    }
    
    private void setCurrentCollisionRectangle(Rectangle currentCollisionRectangle) {
        this.currentCollisionRectangle = currentCollisionRectangle;
    }
    
    private void updateCurrentCollisionRectangle() {
        setCurrentCollisionRectangle(new Rectangle(getX(), getY(), Engine.getTileSize() / 2, Engine.getTileSize() / 2));
    }
    
    private TextureRegion[] getUserIdleOnWater() {
        return userIdleOnWater;
    }
    
    private Animation[] getUserSwim() {
        return userSwim;
    }
    
    private boolean isSwimming() {
        return swimming;
    }
    
    private void setSwimming(boolean swimming) {
        this.swimming = swimming;
    }
    
    private void resetPosition() {
        resetPosition(getMapCreator(), false);
    }
    
    public void resetPosition(MapCreator mapCreator, boolean resetMapCreator) {
        setMapCreator(resetMapCreator ? mapCreator : getMapCreator());
        setPositionX(mapCreator.getStartPosition().x + getUserWalkWidth() / 2);
        setPositionY(mapCreator.getStartPosition().y + getUserWalkHeight() / 2);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(getMapCreator(), getHandleInput(), getUserIdleOnLand(), getUserWalk(), getPositionX(), getPositionY(), getCurrentState(),
                            getPreviousState(), getStateTimer(), getDirection(), isMoving(), isAligned(), isFutureCollision(), getUserSprite()
        );
    }
    
    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User)(o);
        return Float.compare(user.getPositionX(), getPositionX()) == 0 && Float.compare(user.getPositionY(), getPositionY()) == 0 &&
               Float.compare(user.getStateTimer(), getStateTimer()) == 0 && getDirection() == user.getDirection() && isMoving() == user.isMoving() &&
               isAligned() == user.isAligned() && isFutureCollision() == user.isFutureCollision() &&
               Objects.equals(getMapCreator(), user.getMapCreator()) && Objects.equals(getHandleInput(), user.getHandleInput()) &&
               Arrays.equals(getUserIdleOnLand(), user.getUserIdleOnLand()) && Arrays.equals(getUserWalk(), user.getUserWalk()) &&
               getCurrentState() == user.getCurrentState() && getPreviousState() == user.getPreviousState() &&
               Objects.equals(getUserSprite(), user.getUserSprite());
    }
    
    private float getAnimationSpeed() {
        return ANIMATION_SPEED;
    }
    
    private MapCreator getMapCreator() {
        return mapCreator;
    }
    
    public void setMapCreator(MapCreator mapCreator) {
        this.mapCreator = mapCreator;
    }
    
    private Sprite getUserSprite() {
        return userSprite;
    }
    
    private boolean isFutureCollision() {
        return futureCollision;
    }
    
    private void setFutureCollision(boolean futureCollision) {
        this.futureCollision = futureCollision;
    }
    
    private Rectangle getFutureRectangle(float offsetX, float offsetY) {
        Rectangle futureRectangle = getCurrentCollisionRectangle();
        futureRectangle.setX(futureRectangle.getX() + offsetX);
        futureRectangle.setY(futureRectangle.getY() + offsetY);
        return futureRectangle;
    }
    
    @Override
    @AnimationInitializer(actionTypes = {
                                                "Idle", "Walking", "Running", "Biking", "Using HM Move", "Fishing", "In Battle"
    })
    public void initializeAnimation() {
        //new AnimationHelper().setUpAnimation(this);
        userWalk[0] = AnimationManager.getAnimation(getUserSprite().getTexture(), 4, 0.1f, new int[] {189, 135, 206, 135}, 6, getUserWalkWidth(),
                                                    getUserWalkHeight()
        );
        userWalk[1] = AnimationManager
                              .getAnimation(getUserSprite().getTexture(), 4, 0.1f, new int[] {80, 62, 97, 62}, 49, getUserWalkWidth(), getUserWalkHeight());
        userWalk[2] = AnimationManager.getAnimation(getUserSprite().getTexture(), 4, 0.1f, new int[] {291, 274, 204, 274}, 27, getUserWalkWidth(),
                                                    getUserWalkHeight()
        );
        userWalk[3] = AnimationManager.getAnimation(getUserSprite().getTexture(), 4, 0.1f, new int[] {35, 52, 122, 52}, 27, getUserWalkWidth(),
                                                    getUserWalkHeight()
        );
        
        userIdleOnLand[0] = new TextureRegion(getUserSprite().getTexture(), 135, 6, getUserWalkWidth(), getUserWalkHeight());
        userIdleOnLand[1] = new TextureRegion(getUserSprite().getTexture(), 62, 49, getUserWalkWidth(), getUserWalkHeight());
        userIdleOnLand[2] = new TextureRegion(getUserSprite().getTexture(), 274, 27, getUserWalkWidth(), getUserWalkHeight());
        userIdleOnLand[3] = new TextureRegion(getUserSprite().getTexture(), 52, 27, getUserWalkWidth(), getUserWalkHeight());
        
        userSwim[0] =
                AnimationManager.getAnimation(getUserSprite().getTexture(), 2, 0.1f, new int[] {192, 170}, 97, getUserSwimWidth(), getUserSwimHeight());
        userSwim[1] =
                AnimationManager.getAnimation(getUserSprite().getTexture(), 2, 0.1f, new int[] {147, 125}, 97, getUserSwimWidth(), getUserSwimHeight());
        userSwim[2] =
                AnimationManager.getAnimation(getUserSprite().getTexture(), 2, 0.1f, new int[] {217, 240}, 97, getUserSwimWidth(), getUserSwimHeight());
        userSwim[3] =
                AnimationManager.getAnimation(getUserSprite().getTexture(), 2, 0.1f, new int[] {102, 77}, 97, getUserSwimWidth(), getUserSwimHeight());
        
        userIdleOnWater[0] = new TextureRegion(getUserSprite().getTexture(), 170, 97, getUserSwimWidth(), getUserSwimHeight());
        userIdleOnWater[1] = new TextureRegion(getUserSprite().getTexture(), 125, 97, getUserSwimWidth(), getUserSwimHeight());
        userIdleOnWater[2] = new TextureRegion(getUserSprite().getTexture(), 240, 97, getUserSwimWidth(), getUserSwimHeight());
        userIdleOnWater[3] = new TextureRegion(getUserSprite().getTexture(), 77, 97, getUserSwimWidth(), getUserSwimHeight());
    }
    
    public void handleInput(float deltaTime) {
        getHandleInput().update(deltaTime);
        if(!isAligned() && !isFutureCollision()) {
            if(getDirection() == getUP()) {
                setPositionY(getPositionY() + getCurrentState().getSpeed());
            } else if(getDirection() == getDOWN()) {
                setPositionY(getPositionY() - getCurrentState().getSpeed());
            } else if(getDirection() == getRIGHT()) {
                setPositionX(getPositionX() + getCurrentState().getSpeed());
            } else if(getDirection() == getLEFT()) {
                setPositionX(getPositionX() - getCurrentState().getSpeed());
            }
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.X)) {
            AlterPlayerPosition alterPlayerPosition =
                    (Consumer<Float> setPositionMethod, Supplier<Float> getPositionMethod, float alterValue) -> setPositionMethod
                                                                                                                        .accept(getPositionMethod.get() +
                                                                                                                                alterValue);
    
            Rectangle futureRectangle = (getDirection() == getUP()) ? getFutureRectangle(0, Engine.getTileSize()) :
                                        (getDirection() == getDOWN()) ? getFutureRectangle(0, -Engine.getTileSize()) :
                                        (getDirection() == getRIGHT()) ? getFutureRectangle(Engine.getTileSize(), 0) :
                                        getFutureRectangle(-Engine.getTileSize(), 0);
    
            Runnable alterAction =
                    (getDirection() == getUP()) ? () -> alterPlayerPosition.alterPosition(this::setPositionY, this::getPositionY, Engine.getTileSize()) :
                    (getDirection() == getDOWN()) ? () -> alterPlayerPosition.alterPosition(this::setPositionY, this::getPositionY, -Engine.getTileSize()) :
                    (getDirection() == getRIGHT()) ? () -> alterPlayerPosition.alterPosition(this::setPositionX, this::getPositionX, Engine.getTileSize()) :
                    () -> alterPlayerPosition.alterPosition(this::setPositionX, this::getPositionX, -Engine.getTileSize());
    
            if(isColliding(futureRectangle, false) && getCollidingTileObject(futureRectangle) instanceof Water) {
                alterAction.run();
            }
        }
    }
    
    private boolean isColliding(Rectangle rectangle, boolean activateCollisionMethod) {
        for(TileObject tileObject : getMapCreator().getTileObjects()) {
            if(tileObject.isColliding(rectangle)) {
                if(activateCollisionMethod) {
                    tileObject.onCollide();
                }
                return !(isSwimming() && tileObject instanceof Water);
            }
        }
        return false;
    }
    
    private TileObject getCollidingTileObject(Rectangle rectangle) {
        for(TileObject tileObject : getMapCreator().getTileObjects()) {
            if(tileObject.isColliding(rectangle)) {
                return tileObject;
            }
        }
        return null;
    }
    
    private TextureRegion[] getUserIdleOnLand() {
        return userIdleOnLand;
    }
    
    private HandleInput getHandleInput() {
        return handleInput;
    }
    
    public float getPositionX() {
        return positionX;
    }
    
    private void setPositionX(float positionX) {
        this.positionX = positionX;
    }
    
    public float getPositionY() {
        return positionY;
    }
    
    private void setPositionY(float positionY) {
        this.positionY = positionY;
    }
    
    @Override
    public void update(float deltaTime) {
        updateAlignment();
        updateActorXY();
        updateCurrentCollisionRectangle();
        updateSwimming();
        updateAnimation(deltaTime);
    }
    
    private void updateAlignment() {
        setAligned((getPositionX() + (Engine.getTileSize() / 2)) % Engine.getTileSize() == 0 &&
                   (getPositionY() + (Engine.getTileSize() / 2)) % Engine.getTileSize() - 1 == 0);
    }
    
    private void updateSwimming() {
        setSwimming(getCollidingTileObject(getCurrentCollisionRectangle()) instanceof Water);
    }
    
    private void updateAnimation(float deltaTime) {
        getUserSprite().setRegion(getFrame(deltaTime));
    }
    
    private void updateActorXY() {
        setX(getPositionX() - getUserWalkWidth() / 2);
        setY(getPositionY() - getUserWalkHeight() / 2);
    }
    
    private TextureRegion getFrame(float deltaTime) {
        float stateTime = getStateTimer() * getAnimationSpeed();
        
        setStateTimer(getCurrentState() == getPreviousState() ? getStateTimer() + deltaTime : 0);
        setPreviousState(getCurrentState());
        return (getCurrentState() == PlayerActions.WALKING) ? (getDirection() == getUP()) ? (TextureRegion)(getUserWalk()[0].getKeyFrame(stateTime, true)) :
                                                              (getDirection() == getDOWN()) ?
                                                              (TextureRegion)(getUserWalk()[1].getKeyFrame(stateTime, true)) :
                                                              (getDirection() == getRIGHT()) ?
                                                              (TextureRegion)(getUserWalk()[2].getKeyFrame(stateTime, true)) :
                                                              (TextureRegion)(getUserWalk()[3].getKeyFrame(stateTime, true)) :
               (getCurrentState() == PlayerActions.SWIMMING) ?
               (getDirection() == getUP()) ? (TextureRegion)(getUserSwim()[0].getKeyFrame(stateTime, true)) :
               (getDirection() == getDOWN()) ? (TextureRegion)(getUserSwim()[1].getKeyFrame(stateTime, true)) :
               (getDirection() == getRIGHT()) ? (TextureRegion)(getUserSwim()[2].getKeyFrame(stateTime, true)) :
               (TextureRegion)(getUserSwim()[3].getKeyFrame(stateTime, true)) : (isSwimming()) ? (getDirection() == getUP()) ? getUserIdleOnWater()[0] :
                                                                                                 (getDirection() == getDOWN()) ? getUserIdleOnWater()[1] :
                                                                                                 (getDirection() == getRIGHT()) ? getUserIdleOnWater()[2] :
                                                                                                 getUserIdleOnWater()[3] :
                                                                                (getDirection() == getUP()) ? getUserIdleOnLand()[0] :
                                                                                (getDirection() == getDOWN()) ? getUserIdleOnLand()[1] :
                                                                                (getDirection() == getRIGHT()) ? getUserIdleOnLand()[2] :
                                                                                getUserIdleOnLand()[3];
    }
    
    private PlayerActions getCurrentState() {
        if(isMoving()) {
            if(isSwimming()) {
                return PlayerActions.SWIMMING;
            } else {
                return PlayerActions.WALKING;
            }
        } else {
            return PlayerActions.IDLE;
        }
    }
    
    private Animation[] getUserWalk() {
        return userWalk;
    }
    
    private float getStateTimer() {
        return stateTimer;
    }
    
    private void setStateTimer(float stateTimer) {
        this.stateTimer = stateTimer;
    }
    
    private PlayerActions getPreviousState() {
        return previousState;
    }
    
    private void setPreviousState(PlayerActions previousState) {
        this.previousState = previousState;
    }
    
    private boolean isMoving() {
        return moving;
    }
    
    private void setMoving(boolean moving) {
        this.moving = moving;
    }
    
    @Override
    public void draw(Batch batch, float parentAlpha) {
        if((getCurrentState() == PlayerActions.IDLE && !isSwimming()) || getCurrentState() == PlayerActions.WALKING) {
            batch.draw(getUserSprite(), getPositionX() - getUserWalkWidth() / 2, getPositionY() - getUserWalkHeight() / 2, getUserWalkWidth(),
                       getUserWalkHeight()
            );
        } else if(isSwimming()) {
            batch.draw(getUserSprite(), getPositionX() - getUserSwimWidth() / 2, getPositionY() - getUserSwimHeight() / 2, getUserSwimWidth(),
                       getUserSwimHeight()
            );
        }
    }
    
    private byte getDirection() {
        return direction;
    }
    
    private void setDirection(byte direction) {
        this.direction = direction;
    }
    
    private boolean isAligned() {
        return aligned;
    }
    
    private void setAligned(boolean aligned) {
        this.aligned = aligned;
    }
}