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
import com.horse.pokemon.GraphicsEngine.MapEngine.MapCreator;
import com.horse.pokemon.ObjectData.TiledObjects.CollidableTileObject;
import com.horse.pokemon.ObjectData.TiledObjects.Door;
import com.horse.pokemon.ObjectData.TiledObjects.Water;

import java.util.Arrays;
import java.util.Objects;

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
     * The {@code byte} representing the amount of pixels from the left-most part of the {@code User} to the right-most part of the {@code User} when in the
     * {@link PlayerActions} {@link PlayerActions#WALKING} or {@link PlayerActions#IDLE} if the {@code User} is on land, also determining how big the user
     * is drawn every frame x-wise.
     */
    private static final byte USER_WALK_WIDTH = 16;
    
    /**
     * The {@code byte} representing the amount of pixels from the upper-most part of the {@code User} to the bottom-most part of the {@code User} when in
     * the {@link PlayerActions} {@link PlayerActions#WALKING} or {@link PlayerActions#IDLE} if the {@code User} is on land, also determining how big the
     * user is draw every frame y-wise.
     */
    private static final byte USER_WALK_HEIGHT = 19;
    
    /**
     * The {@code byte} representing the amount of pixels from the left-most part of the {@code User} to the right-most part of the {@code User} when in the
     * {@link PlayerActions} {@link PlayerActions#SWIMMING} or {@link PlayerActions#IDLE} if the {@code User} is on water, also determining how big the user
     * is drawn every frame x-wise.
     */
    private static final byte USER_SWIM_WIDTH = 22;
    
    /**
     * The {@code byte} representing the amount of pixels from the upper-most part of the {@code User} to the bottom-most part of the {@code User} when in
     * the {@link PlayerActions} {@link PlayerActions#SWIMMING} or {@link PlayerActions#IDLE} if the {@code User} is on water, also determining how big the
     * user is drawn every frame y-wise.
     */
    private static final byte USER_SWIM_HEIGHT = 24;
    
    /**
     * The {@code float} representing the frame speed for when {@code User} animation is being played.
     */
    private static final float  ANIMATION_SPEED    = 0.5f;
    /**
     * The amount of time it takes to press the direction keys for the {@code User} to start moving in seconds.
     */
    private static final float  keyPressToMoveTime = 0.1f;
    /**
     * The {@link String} representing the location of the Texture Packer file of all the compact images of the {@code User}.
     */
    private static final String USER_INFORMATION   = "User\\GDX_Users\\User.pack";
    
    /**
     * The {@link String} representing the name of all the sprites of the {@code User} for all movements and standard actions according to {@link
     * #USER_INFORMATION}
     */
    private static final String USER_ATLAS_REGION_NAME = "SpriteSheetUser";
    /**
     * The {@link TextureRegion} array representing all of the idle positions when the {@code User} is on land.
     */
    private final TextureRegion[] userIdleOnLand;
    /**
     * The {@link TextureRegion} array representing all of the idle positions when the {@code User} is on water.
     */
    private final TextureRegion[] userIdleOnWater;
    /**
     * The {@link Animation} array representing all of the movement frames for when the {@code User} is on land.
     */
    private final Animation[]     userWalk;
    /**
     * The {@link Animation} array representing all of the movement frames for when the {@code User} is on water.
     */
    private final Animation[]     userSwim;
    /**
     * The {@link HandleInput} representing how the {@code User} reacts for when  specific keyboard keys are pressed.
     */
    private final HandleInput     handleInput;
    /**
     * The {@link Sprite} representing the area of the {@link #USER_INFORMATION} to be used for movements using {@link #USER_ATLAS_REGION_NAME} to get the
     * Texture Packer information to have easy access to the x, y, width, and height of the sprite sheet.
     */
    private final Sprite          userSprite;
    
    /**
     * The {@code float} instance used to represent the amount of time passed during a new action, also specifying which frame is to be drawn.
     */
    private float stateTimer;
    /**
     * The current time that a movement key has been pressed to check if the amount of time pressed is enough to move the {@code User}.
     */
    private float movementKeyHeldDownTime;
    
    /**
     * The {@code boolean} instance representing if the {@code User} is currently on top of the water to note which action animation should be drawn.
     */
    private boolean swimming;
    
    /**
     * Main class constructor that initializes all non-static-final representatives to ensure no {@link NullPointerException} occurs, which prepares the
     * {@code User} for movement and animation.
     *
     * @param mapCreator Contains the collision {@link Rectangle}s for the {@code User} to check possible movements.
     *
     * @see NullPointerException
     * @see MapCreator
     */
    public User(MapCreator mapCreator) {
        setPreviousState(getCurrentState()); //Initializes previousState as the action that happens at the start, which should always be IDLE.
        setStateTimer(0);                    //Initializes stateTimer to a neutral value of zero representing a 'reset' to the timer.
        setDirection(getDOWN());             //Initializes direction to have the User pointing downwards at the start by default.  No main reason to be looking down.
        setMoving(false);                    //Initializes moving to false because the User is in an IDLE state, meaning that the character should not be moving in that state.
        setAligned(true);                    //Initializes aligned to true because the User should be placed correctly on the grid so that aligned should be true already.
        setFutureCollision(false);           //Initializes futureCollision to false because the User will have enough time to check if there will be a collision.
        setSwimming(false);                  //Initializes swimming to false because the User should start on land at the beginning of the game.
        setRestrictedMovement(false);        //Initializes restrictedMovement to false because there is no scene that requires a computer-controlled User.
        setMovementKeyHeldDownTime(0f);
        
        userSprite = new Sprite(new TextureAtlas(User.getUserInformation()).findRegion(getUserAtlasRegionName()));
        
        setMapCreator(mapCreator);
        
        userIdleOnLand = new TextureRegion[4];
        userIdleOnWater = new TextureRegion[4];
        
        userWalk = new Animation[4];
        userSwim = new Animation[4];
        
        initializeAnimation();
    
        setBounds(0, 0, Engine.getHalfTileSize(), Engine.getHalfTileSize());
        getUserSprite().setRegion(getUserIdleOnLand()[1]);
        
        resetPosition();
        
        updateCurrentCollisionRectangle();
        
        handleInput = new HandleInput((float dt) -> {
            if(isAligned()) {
                setMoving(true);
                setAligned(false);
                setDirection(getUP());
                setFutureCollision(isColliding(getFutureRectangle(0, Engine.getTileSize()), true));
                setMovementKeyHeldDownTime(getMovementKeyHeldDownTime() + dt);
            }
        }, (float dt) -> {
            if(isAligned()) {
                setMoving(true);
                setAligned(false);
                setDirection(getDOWN());
                setFutureCollision(isColliding(getFutureRectangle(0, -Engine.getTileSize()), true));
                setMovementKeyHeldDownTime(getMovementKeyHeldDownTime() + dt);
            }
        }, (float dt) -> {
            if(isAligned()) {
                setMoving(true);
                setAligned(false);
                setDirection(getRIGHT());
                setFutureCollision(isColliding(getFutureRectangle(Engine.getTileSize(), 0), true));
                setMovementKeyHeldDownTime(getMovementKeyHeldDownTime() + dt);
            }
        }, (float dt) -> {
            if(isAligned()) {
                setMoving(true);
                setAligned(false);
                setDirection(getLEFT());
                setFutureCollision(isColliding(getFutureRectangle(-Engine.getTileSize(), 0), true));
                setMovementKeyHeldDownTime(getMovementKeyHeldDownTime() + dt);
            }
        }, (float dt) -> {
            if(isAligned()) {
                setMoving(false);
                setMovementKeyHeldDownTime(0f);
            }
        });
    }
    
    private static float getKeyPressToMoveTime() {
        return keyPressToMoveTime;
    }
    
    /**
     * Returns the representation of the name of the boundaries for the base action animations in a sprite sheet.
     *
     * @return {@link #USER_ATLAS_REGION_NAME}
     */
    private static String getUserAtlasRegionName() {
        return USER_ATLAS_REGION_NAME;
    }
    
    /**
     * Returns the horizontal pixel value when the {@code User} is in a {@link PlayerActions#SWIMMING} state or {@link PlayerActions#IDLE} state when on water.
     *
     * @return {@link #USER_SWIM_WIDTH}
     */
    private static byte getUserSwimWidth() {
        return USER_SWIM_WIDTH;
    }
    
    /**
     * Returns the vertical pixel value when the {@code User} is in a {@link PlayerActions#SWIMMING} state or {@link PlayerActions#IDLE} state when on water.
     *
     * @return {@link #USER_SWIM_HEIGHT}
     */
    private static byte getUserSwimHeight() {
        return USER_SWIM_HEIGHT;
    }
    
    /**
     * Returns the {@link String} instance representing the file path of the information Texture Packer that is used for the corresponding image.
     *
     * @return {@link #USER_INFORMATION}
     */
    private static String getUserInformation() {
        return USER_INFORMATION;
    }
    
    /**
     * Returns the horizontal pixel value when the {@code User} is in a {@link PlayerActions#WALKING} state or {@link PlayerActions#IDLE} state when on land.
     *
     * @return {@link #USER_WALK_WIDTH}
     */
    private static byte getUserWalkWidth() {
        return USER_WALK_WIDTH;
    }
    
    /**
     * Returns the vertical pixel value when the {@code User} is in a {@link PlayerActions#WALKING} state of {@link PlayerActions#IDLE} state when on land.
     *
     * @return {@link #USER_WALK_HEIGHT}
     */
    private static byte getUserWalkHeight() {
        return USER_WALK_HEIGHT;
    }
    
    private float getMovementKeyHeldDownTime() {
        return movementKeyHeldDownTime;
    }
    
    private void setMovementKeyHeldDownTime(float movementKeyHeldDownTime) {
        this.movementKeyHeldDownTime = movementKeyHeldDownTime;
    }
    
    /**
     * Alters {@link #currentCollisionRectangle} using {@link #setCurrentCollisionRectangle(Rectangle)} according to the position of the {@code User} and
     * tile size.  This method is called every frame to ensure no undesired movements.
     */
    private void updateCurrentCollisionRectangle() {
        setCurrentCollisionRectangle(new Rectangle(getX(), getY(), Engine.getHalfTileSize(), Engine.getHalfTileSize()));
    }
    
    /**
     * Returns the {@link TextureRegion} array representing the rectangles in the {@link #USER_INFORMATION} files to be used as {@link PlayerActions#IDLE}
     * positions for when the {@code User} is not moving in water.
     *
     * @return {@link #userIdleOnWater}
     */
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
        setPositionX((int)(mapCreator.getStartPosition().x + getUserWalkWidth() / 2));
        setPositionY((int)(mapCreator.getStartPosition().y + getUserWalkHeight() / 2));
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(getMapCreator(), getHandleInput(), getUserIdleOnLand(), getUserWalk(), getPositionX(), getPositionY(), getCurrentState(), getPreviousState(),
                            getStateTimer(), getDirection(), isMoving(), isAligned(), isFutureCollision(), getUserSprite()
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
               Float.compare(user.getStateTimer(), getStateTimer()) == 0 && getDirection() == user.getDirection() && isMoving() == user.isMoving() && isAligned() == user.isAligned() &&
               isFutureCollision() == user.isFutureCollision() && Objects.equals(getMapCreator(), user.getMapCreator()) && Objects.equals(getHandleInput(), user.getHandleInput()) &&
               Arrays.equals(getUserIdleOnLand(), user.getUserIdleOnLand()) && Arrays.equals(getUserWalk(), user.getUserWalk()) && getCurrentState() == user.getCurrentState() &&
               getPreviousState() == user.getPreviousState() && Objects.equals(getUserSprite(), user.getUserSprite());
    }
    
    private float getAnimationSpeed() {
        return ANIMATION_SPEED;
    }
    
    private Sprite getUserSprite() {
        return userSprite;
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
        userWalk[0] = AnimationManager.getAnimation(getUserSprite().getTexture(), 4, 0.1f, new int[] {189, 135, 206, 135}, 6, getUserWalkWidth(), getUserWalkHeight());
        userWalk[1] = AnimationManager.getAnimation(getUserSprite().getTexture(), 4, 0.1f, new int[] {80, 62, 97, 62}, 49, getUserWalkWidth(), getUserWalkHeight());
        userWalk[2] = AnimationManager.getAnimation(getUserSprite().getTexture(), 4, 0.1f, new int[] {291, 274, 204, 274}, 27, getUserWalkWidth(), getUserWalkHeight());
        userWalk[3] = AnimationManager.getAnimation(getUserSprite().getTexture(), 4, 0.1f, new int[] {35, 52, 122, 52}, 27, getUserWalkWidth(), getUserWalkHeight());
        
        userIdleOnLand[0] = new TextureRegion(getUserSprite().getTexture(), 135, 6, getUserWalkWidth(), getUserWalkHeight());
        userIdleOnLand[1] = new TextureRegion(getUserSprite().getTexture(), 62, 49, getUserWalkWidth(), getUserWalkHeight());
        userIdleOnLand[2] = new TextureRegion(getUserSprite().getTexture(), 274, 27, getUserWalkWidth(), getUserWalkHeight());
        userIdleOnLand[3] = new TextureRegion(getUserSprite().getTexture(), 52, 27, getUserWalkWidth(), getUserWalkHeight());
    
        userSwim[0] = AnimationManager.getAnimation(getUserSprite().getTexture(), 2, 0.1f, new int[] {192, 170}, 97, getUserSwimWidth(), getUserSwimHeight());
        userSwim[1] = AnimationManager.getAnimation(getUserSprite().getTexture(), 2, 0.1f, new int[] {147, 125}, 97, getUserSwimWidth(), getUserSwimHeight());
        userSwim[2] = AnimationManager.getAnimation(getUserSprite().getTexture(), 2, 0.1f, new int[] {217, 240}, 97, getUserSwimWidth(), getUserSwimHeight());
        userSwim[3] = AnimationManager.getAnimation(getUserSprite().getTexture(), 2, 0.1f, new int[] {102, 77}, 97, getUserSwimWidth(), getUserSwimHeight());
        
        userIdleOnWater[0] = new TextureRegion(getUserSprite().getTexture(), 170, 97, getUserSwimWidth(), getUserSwimHeight());
        userIdleOnWater[1] = new TextureRegion(getUserSprite().getTexture(), 125, 97, getUserSwimWidth(), getUserSwimHeight());
        userIdleOnWater[2] = new TextureRegion(getUserSprite().getTexture(), 240, 97, getUserSwimWidth(), getUserSwimHeight());
        userIdleOnWater[3] = new TextureRegion(getUserSprite().getTexture(), 77, 97, getUserSwimWidth(), getUserSwimHeight());
    }
    
    public void handleInput(float deltaTime) {
        getHandleInput().update(deltaTime);
        if(!isAligned() && !isFutureCollision() && !isRestrictedMovement()) {
            if(getDirection() == getUP()) {
                setPositionY(getMovementKeyHeldDownTime() >= getKeyPressToMoveTime() ? getPositionY() + getCurrentState().getSpeed() : getPositionY());
            } else if(getDirection() == getDOWN()) {
                setPositionY(getMovementKeyHeldDownTime() >= getKeyPressToMoveTime() ? getPositionY() - getCurrentState().getSpeed() : getPositionY());
            } else if(getDirection() == getRIGHT()) {
                setPositionX(getMovementKeyHeldDownTime() >= getKeyPressToMoveTime() ? getPositionX() + getCurrentState().getSpeed() : getPositionX());
            } else if(getDirection() == getLEFT()) {
                setPositionX(getMovementKeyHeldDownTime() >= getKeyPressToMoveTime() ? getPositionX() - getCurrentState().getSpeed() : getPositionX());
            }
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.X) && !isRestrictedMovement()) {
            AlterPlayerPosition alterPlayerPosition =
                    (PrimitiveConsumer setPositionMethod, PrimitiveSupplier getPositionMethod, int alterValue) -> setPositionMethod.accept(getPositionMethod.get() + alterValue);
            
            Rectangle futureRectangle = (getDirection() == getUP()) ? getFutureRectangle(0, Engine.getTileSize()) :
                                        (getDirection() == getDOWN()) ? getFutureRectangle(0, -Engine.getTileSize()) :
                                        (getDirection() == getRIGHT()) ? getFutureRectangle(Engine.getTileSize(), 0) : getFutureRectangle(-Engine.getTileSize(), 0);
            
            if(isColliding(futureRectangle, false) && getCollidingTileObject(futureRectangle) instanceof Water) {
                Runnable alterAction = (getDirection() == getUP()) ? () -> alterPlayerPosition.alterPosition(this::setPositionY, this::getPositionY, Engine.getTileSize()) :
                                       (getDirection() == getDOWN()) ? () -> alterPlayerPosition.alterPosition(this::setPositionY, this::getPositionY, -Engine.getTileSize()) :
                                       (getDirection() == getRIGHT()) ? () -> alterPlayerPosition.alterPosition(this::setPositionX, this::getPositionX, Engine.getTileSize()) :
                                       () -> alterPlayerPosition.alterPosition(this::setPositionX, this::getPositionX, -Engine.getTileSize());
                
                alterAction.run();
            }
        }
    }
    
    private boolean isColliding(Rectangle rectangle, boolean activateCollisionMethod) {
        for(CollidableTileObject collidableTileObject : getMapCreator().getCollidableTileObjects()) {
            if(collidableTileObject.isColliding(rectangle)) {
                if(activateCollisionMethod) {
                    if(collidableTileObject instanceof Door) {
                        getMapCreator().getScreen().setDoorToOpen((Door)(collidableTileObject));
                        collidableTileObject.onCollide();
                        return false;
                    }
                    collidableTileObject.onCollide();
                }
                return !(isSwimming() && collidableTileObject instanceof Water);
            }
        }
        return false;
    }
    
    private CollidableTileObject getCollidingTileObject(Rectangle rectangle) {
        for(CollidableTileObject collidableTileObject : getMapCreator().getCollidableTileObjects()) {
            if(collidableTileObject.isColliding(rectangle)) {
                return collidableTileObject;
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
    
    @Override
    public void update(float deltaTime) {
        updateAlignment();
        updateActorXY();
        updateCurrentCollisionRectangle();
        updateSwimming();
        updateAnimation(deltaTime);
    }
    
    @Override
    public void draw(Batch batch, float parentAlpha) {
        if((getCurrentState() == PlayerActions.IDLE && !isSwimming()) || getCurrentState() == PlayerActions.WALKING) {
            batch.draw(getUserSprite(), getPositionX() - getUserWalkWidth() / 2, getPositionY() - getUserWalkHeight() / 2, getUserWalkWidth(), getUserWalkHeight());
        } else if(isSwimming()) {
            batch.draw(getUserSprite(), getPositionX() - getUserSwimWidth() / 2, getPositionY() - getUserSwimHeight() / 2, getUserSwimWidth(), getUserSwimHeight());
        }
    }
    
    private void updateAlignment() {
        setAligned((getPositionX() + (Engine.getHalfTileSize())) % Engine.getTileSize() == 0 && (getPositionY() + (Engine.getHalfTileSize())) % Engine.getTileSize() - 1 == 0);
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
                                                              (getDirection() == getDOWN()) ? (TextureRegion)(getUserWalk()[1].getKeyFrame(stateTime, true)) :
                                                              (getDirection() == getRIGHT()) ? (TextureRegion)(getUserWalk()[2].getKeyFrame(stateTime, true)) :
                                                              (TextureRegion)(getUserWalk()[3].getKeyFrame(stateTime, true)) : (getCurrentState() == PlayerActions.SWIMMING) ?
                                                                                                                               (getDirection() == getUP()) ?
                                                                                                                               (TextureRegion)(getUserSwim()[0]
                                                                                                                                                       .getKeyFrame(stateTime, true)) :
                                                                                                                               (getDirection() == getDOWN()) ?
                                                                                                                               (TextureRegion)(getUserSwim()[1]
                                                                                                                                                       .getKeyFrame(stateTime, true)) :
                                                                                                                               (getDirection() == getRIGHT()) ?
                                                                                                                               (TextureRegion)(getUserSwim()[2]
                                                                                                                                                       .getKeyFrame(stateTime, true)) :
                                                                                                                               (TextureRegion)(getUserSwim()[3]
                                                                                                                                                       .getKeyFrame(stateTime, true)) :
                                                                                                                               (isSwimming()) ?
                                                                                                                               (getDirection() == getUP()) ? getUserIdleOnWater()[0] :
                                                                                                                               (getDirection() == getDOWN()) ? getUserIdleOnWater()[1] :
                                                                                                                               (getDirection() == getRIGHT()) ?
                                                                                                                               getUserIdleOnWater()[2] : getUserIdleOnWater()[3] :
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
}