package com.horse.pokemon.ObjectData.Players;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.horse.pokemon.AnimationEngine.AnimationInterface;
import com.horse.pokemon.AnimationEngine.AnimationManager;
import com.horse.pokemon.Engine;
import com.horse.pokemon.GraphicsEngine.MainInterface.DialogEngine.Dialog;
import com.horse.pokemon.GraphicsEngine.MainInterface.HandleInput;
import com.horse.pokemon.GraphicsEngine.MapEngine.MapCreator;
import com.horse.pokemon.GraphicsEngine.ScreenEngine.MainGameScreen;
import com.horse.pokemon.ObjectData.PokemonData.Pokemon;
import com.horse.pokemon.ObjectData.TiledObjects.Barrier;
import com.horse.pokemon.ObjectData.TiledObjects.CollidableTileObject;
import com.horse.pokemon.ObjectData.TiledObjects.Door;
import com.horse.pokemon.ObjectData.TiledObjects.Sign;
import com.horse.pokemon.ObjectData.TiledObjects.Water;

/**
 * The {@code User} class represents the protagonist of the Pokemon game.  The {@link MainGameScreen#camera} revolves
 * around a {@code User} instance by following the {@code User} when moving.  The {@code User} moves by key presses of
 * the arrow keys, and other actions are represented with other various keys.
 * <p>
 *     Actions with the corresponding key:
 * </p>
 * <ul>
 *     <li>Up Arrow     - Moves the {@code User} upwards.</li>
 *     <li>Down Arrow   - Moves the {@code User} downwards.</li>
 *     <li>Right Arrow  - Moves the {@code User} to the right.</li>
 *     <li>Left Arrow   - Moves the {@code User} to the left.</li>
 *     <li>Shift (Both) - Alters {@code User} action by running if on land.</li>
 *     <li>X Key        - Various actions depending on the situation:
 *         <ul>
 *             <li>Asks to swim when next to a {@link Water} tile.</li>
 *             <li>{@link Dialog} appears from being next to a {@link Sign}.</li>
 *             <li>Speaks to an {@link NPC} with a {@link Dialog}.</li>
 *             <li>Progresses a conversion through the {@link Dialog}.</li>
 *         </ul>
 *     </li>
 * </ul>
 * <p>
 *     The {@code User} can go through a battle and have {@link Pokemon} from progressing through the {@link Game}.
 *     {@link Pokemon} are obtained by the {@code User} by 'catching' them with things called Pokeballs, which can be
 *     bought from a store called the Pokemart.  There should only be a single {@code User} instance per {@link Game},
 *     otherwise the {@link MainGameScreen#camera} will have a hard time determining which {@code User} to follow and
 *     all keyboard actions will cause both {@code User}s to act identically.
 * </p>
 * <p>
 *     The {@code User} is drawn onto the {@link MainGameScreen} by the {@link #draw(Batch, float)} method, which is
 *     called every frame by the {@link MainGameScreen#render(float)} method.  It is important that the
 *     {@link #userSprite} is updated according to the delta time, {@link #getDirection()}, and
 *     {@link #getCurrentState()}, as the animation adds realism to the {@link Game}. It is also good to not that the
 *     {@code User} is an {@link AbstractPlayer} which is also an {@link Actor}, meaning that the {@code User} is to be
 *     a part of a {@link Stage}.
 * </p>
 * <p>
 *     There are many different actions that the {@code User} can execute:
 * </p>
 * <ul>
 *     <li>Idle          - Non-moving action.</li>
 *     <li>Walking       - {@code User} moves at a decent pace on land.</li>
 *     <li>Running       - {@code User} moves at a quick pace on land.</li>
 *     <li>Biking        - {@code User} swiftly glides through the ground on a bike (And of course on land).</li>
 *     <li>Swimming      - {@code User} travels at a decent pace on {@link Water}.</li>
 *     <li>Using HM Move - {@code User} uses a {@link Pokemon} in order to accomplish some sort of in-game task.</li>
 *     <li>
 *         Fishing       - {@code User} uses a fishing rod to obtain an item or encounter a {@link Pokemon} in the
 *         {@link Water}.
 *     </li>
 *     <li>
 *         In Battle     - When the {@code User} is fighting against another {@link AbstractPlayer}, adding a lot of
 *         functionality to the {@link Game}, and allowing the {@link Pokemon} to level up.
 *     </li>
 * </ul>
 * <p>
 *     For setting up a {@link Screen} for a {@code User} to be in, an {@link Engine} is required to be in the
 *     {@link Screen}.  The {@code User} must be extended with the constructor changed as it requires a
 *     {@link MainGameScreen}, not just a {@link Screen}, as the major values in the {@link MainGameScreen} are required
 *     for the {@code User} to use to function properly.  Another way could be to have a new class extend
 *     {@link MainGameScreen} and work from there.
 * </p>
 * <p>
 *     In order to create a {@code User} in the {@link Screen#show()} method of a {@link Screen}, an example is:
 * </p>
 * <blockquote><pre>
 *     private MultiTmxMapLoader mapLoader;
 *     private MultiTiledMap[] maps;
 *     private MapCreator mapCreator;
 *     private User user;
 *
 *     public void show() {
 *         mapLoader = new MultiTmxMapLoader();
 *         maps = mapLoader.loadAllMaps(Maps.TWINLEAF_TOWN.getTmxPath(), Maps.ROUTE_201.getTmxPath())
 *         mapCreator = new MapCreator(this, maps);
 *         user = new User(this);
 *     }
 * </pre></blockquote>
 * <p>
 *     To draw the {@code User} onto the {@link Screen}, in the {@link Screen}, add:
 * </p>
 * <blockquote><pre>
 *     private Stage stage;
 *
 *     public void show() {
 *         stage = new Stage();
 *         stage.addActor(user);
 *     }
 *
 *     public void render(float delta) {
 *         stage.act(delta);
 *         stage.draw();
 *     }
 * </pre></blockquote>
 * <p>
 *     At the end, remember to dispose of the {@link Stage} when the {@link Game} ends, by doing:
 * </p>
 * <blockquote><pre>
 *     public void dispose() {
 *         stage.dispose();
 *     }
 * </pre></blockquote>
 * <p>
 *     Finally, to get a {@link OrthographicCamera} to follow the {@code User}, an example is:
 * </p>
 * <blockquote><pre>
 *     private static final int WORLD_WIDTH  = 720;
 *     private static final int WORLD_HEIGHT = 640;
 *     private static final int ZOOM_SCALE   = 2;
 *     private OrthographicCamera camera;
 *     private Viewport viewport;
 *     private MultiTileMapRenderer renderer;
 *     private SpriteBatch batch;
 *
 *     public void show() {
 *         camera = new OrthographicCamera();
 *         viewport = new FitViewport(WORLD_WIDTH / ZOOM_SCALE, WORLD_HEIGHT / ZOOM_SCALE, camera);
 *         if(batch == null) {
 *             batch = new SpriteBatch();
 *         }
 *         renderer = new MultiTileMapRenderer(1.0f, batch);
 *     }
 *
 *     public void render(float delta) {
 *         user.update(delta);
 *
 *         camera.position.x = user.getPositionX();
 *         camera.position.y = user.getPositionY();
 *
 *         camera.update();
 *         renderer.setView(camera);
 *     }
 * </pre></blockquote>
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
public class User extends AbstractPlayer {
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
    private static final float ANIMATION_SPEED = 0.5f;
    
    /**
     * The amount of time it takes to press the direction keys for the {@code User} to start moving in seconds.
     */
    private static final float keyPressToMoveTime = 0.1f;
    
    /**
     * The {@link String} representing the location of the Texture Packer file of all the compact images of the {@code User}.
     */
    private static final String USER_INFORMATION = "User\\GDX_Users\\User.pack";
    
    /**
     * The {@link String} representing the name of all the sprites of the {@code User} for all movements and standard actions according to {@link
     * #USER_INFORMATION}
     */
    private static final String          USER_ATLAS_REGION_NAME = "SpriteSheetUser";
    /**
     * The {@link Sprite} representing the area of the {@link #USER_INFORMATION} to be used for movements using {@link #USER_ATLAS_REGION_NAME} to get the
     * Texture Packer information to have easy access to the x, y, width, and height of the sprite sheet.
     */
    private static final Sprite          userSprite             =
        new Sprite(new TextureAtlas(User.getUserInformation()).findRegion(getUserAtlasRegionName()));
    /**
     * The {@link TextureRegion} array representing all of the idle positions when the {@code User} is on land.
     */
    private static final TextureRegion[] userIdleOnLand         = new TextureRegion[] {
        new TextureRegion(getUserSprite().getTexture(), 135, 6, getUserWalkWidth(), getUserWalkHeight()),
        new TextureRegion(getUserSprite().getTexture(), 62, 49, getUserWalkWidth(), getUserWalkHeight()),
        new TextureRegion(getUserSprite().getTexture(), 274, 27, getUserWalkWidth(), getUserWalkHeight()),
        new TextureRegion(getUserSprite().getTexture(), 52, 27, getUserWalkWidth(), getUserWalkHeight())
    };
    /**
     * The {@link TextureRegion} array representing all of the idle positions when the {@code User} is on water.
     */
    private static final TextureRegion[] userIdleOnWater        = new TextureRegion[] {
        new TextureRegion(getUserSprite().getTexture(), 170, 97, getUserSwimWidth(), getUserSwimHeight()),
        new TextureRegion(getUserSprite().getTexture(), 125, 97, getUserSwimWidth(), getUserSwimHeight()),
        new TextureRegion(getUserSprite().getTexture(), 240, 97, getUserSwimWidth(), getUserSwimHeight()),
        new TextureRegion(getUserSprite().getTexture(), 77, 97, getUserSwimWidth(), getUserSwimHeight())
    };
    /**
     * The {@link Animation} array representing all of the movement frames for when the {@code User} is on land and is moving at a steady pace.
     */
    private static final Animation[]     userWalk               = new Animation[] {
        AnimationManager.getAnimation(getUserSprite().getTexture(), 4, 0.1f, new int[] {189, 135, 206, 135}, 6,
                                      getUserWalkWidth(), getUserWalkHeight()
        ), AnimationManager.getAnimation(getUserSprite().getTexture(), 4, 0.1f, new int[] {80, 62, 97, 62}, 49,
                                         getUserWalkWidth(), getUserWalkHeight()
    ), AnimationManager.getAnimation(getUserSprite().getTexture(), 4, 0.1f, new int[] {291, 274, 204, 274}, 27,
                                     getUserWalkWidth(), getUserWalkHeight()
    ), AnimationManager.getAnimation(getUserSprite().getTexture(), 4, 0.1f, new int[] {35, 52, 122, 52}, 27,
                                     getUserWalkWidth(), getUserWalkHeight()
    )
    };
    /**
     * The {@link Animation} array representing all of the movement frames for when the {@code User} is on land and is quickly moving.
     */
    private static final Animation[]     userRun                = new Animation[] {
        AnimationManager.getAnimation(getUserSprite().getTexture(), 4, 0.1f, new int[] {153, 135, 172, 135}, 6,
                                      getUserWalkWidth(), getUserWalkHeight()
        ), AnimationManager.getAnimation(getUserSprite().getTexture(), 4, 0.1f, new int[] {27, 62, 45, 62}, 49,
                                         getUserWalkWidth(), getUserWalkHeight()
    ), AnimationManager.getAnimation(getUserSprite().getTexture(), 4, 0.1f, new int[] {205, 274, 221, 274}, 27,
                                     getUserWalkWidth(), getUserWalkHeight()
    ), AnimationManager.getAnimation(getUserSprite().getTexture(), 4, 0.1f, new int[] {122, 52, 105, 52}, 27,
                                     getUserWalkWidth(), getUserWalkHeight()
    )
    };
    /**
     * The {@link Animation} array representing all of the movement frames for when the {@code User} is on water.
     */
    private static final Animation[]     userSwim               = new Animation[] {
        AnimationManager.getAnimation(getUserSprite().getTexture(), 2, 0.1f, new int[] {192, 170}, 97,
                                      getUserSwimWidth(), getUserSwimHeight()
        ), AnimationManager.getAnimation(getUserSprite().getTexture(), 2, 0.1f, new int[] {147, 125}, 97,
                                         getUserSwimWidth(), getUserSwimHeight()
    ), AnimationManager.getAnimation(getUserSprite().getTexture(), 2, 0.1f, new int[] {217, 240}, 97,
                                     getUserSwimWidth(), getUserSwimHeight()
    ), AnimationManager.getAnimation(getUserSprite().getTexture(), 2, 0.1f, new int[] {102, 77}, 97, getUserSwimWidth(),
                                     getUserSwimHeight()
    )
    };
    /**
     * The {@link HandleInput} representing how the {@code User} reacts for when  specific keyboard keys are pressed.
     */
    private final HandleInput handleInput;
    /**
     * The current time that a movement key has been pressed to check if the amount of time pressed is enough to move the {@code User}.
     */
    private       float       movementKeyHeldDownTime;
    
    /**
     * The {@code boolean} instance representing if the {@code User} is currently on top of the water to note which action animation should be drawn.
     */
    private boolean swimming;
    
    /**
     * The {@link MainGameScreen} instance representing the current {@link Screen} the {@code User} is placed in.
     */
    private MainGameScreen mainGameScreen;
    
    /**
     * Main class constructor that initializes all non-static-final representatives to ensure no {@link NullPointerException} occurs, which prepares the
     * {@code User} for movement and animation.
     *
     * @param mainGameScreen The screen the {@code User} is in when performing all actions.
     *
     * @see NullPointerException
     * @see MapCreator
     */
    public User(final MainGameScreen mainGameScreen) {
        setPreviousState(
            getCurrentState()); //Initializes previousState as the action that happens at the start, which should always be IDLE.
        setStateTimer(
            0);                    //Initializes stateTimer to a neutral value of zero representing a 'reset' to the timer.
        setDirection(
            getDOWN());             //Initializes direction to have the User pointing downwards at the start by default.  No main reason to be looking down.
        setMoving(
            false);                    //Initializes moving to false because the User is in an IDLE state, meaning that the character should not be moving in that state.
        setAligned(
            true);                    //Initializes aligned to true because the User should be placed correctly on the grid so that aligned should be true already.
        setFutureCollision(
            false);           //Initializes futureCollision to false because the User will have enough time to check if there will be a collision.
        setSwimming(
            false);                  //Initializes swimming to false because the User should start on land at the beginning of the game.
        setRestrictedMovement(
            false);        //Initializes restrictedMovement to false because there is no scene that requires a computer-controlled User.
        setMovementKeyHeldDownTime(
            0f);      //Initializes the time a key was pressed down to zero, as the specific key has not been pressed yet.
        setMainGameScreen(mainGameScreen);   //Initializes the main game screen to the parameter value.
    
        setMapCreator(mainGameScreen
                          .getMapCreator()); //Set the map creator to the same instance as the one in the main game screen to store the collision rectangles.
        
        setBounds(0, 0, Engine.getHalfTileSize(), Engine.getHalfTileSize());
        getUserSprite().setRegion(getUserIdleOnLand()[1]);
        
        resetPosition();
        
        updateCurrentCollisionRectangle();
        
        handleInput = new HandleInput((float dt) -> {
            if(isAligned() && !isRestrictedMovement()) {
                setMoving(true);
                setAligned(false);
                setDirection(getUP());
                setFutureCollision(isColliding(getFutureRectangle(0, Engine.getTileSize()), true));
                setMovementKeyHeldDownTime(getMovementKeyHeldDownTime() + dt);
            }
        }, (float dt) -> {
            if(isAligned() && !isRestrictedMovement()) {
                setMoving(true);
                setAligned(false);
                setDirection(getDOWN());
                setFutureCollision(isColliding(getFutureRectangle(0, -Engine.getTileSize()), true));
                setMovementKeyHeldDownTime(getMovementKeyHeldDownTime() + dt);
            }
        }, (float dt) -> {
            if(isAligned() && !isRestrictedMovement()) {
                setMoving(true);
                setAligned(false);
                setDirection(getRIGHT());
                setFutureCollision(isColliding(getFutureRectangle(Engine.getTileSize(), 0), true));
                setMovementKeyHeldDownTime(getMovementKeyHeldDownTime() + dt);
            }
        }, (float dt) -> {
            if(isAligned() && !isRestrictedMovement()) {
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
    
    /**
     * Returns the {@link Animation} array representing the frames for when the {@code User} is animating on land at a quick pace.
     *
     * @return {@link #userRun}
     */
    private static Animation[] getUserRun() {
        return userRun;
    }
    
    /**
     * Returns the {@link TextureRegion} array representing the rectangles in the {@link #USER_INFORMATION} files to be used as {@link PlayerActions#IDLE}
     * positions for when the {@code User} is not moving in water.
     *
     * @return {@link #userIdleOnWater}
     */
    private static TextureRegion[] getUserIdleOnWater() {
        return userIdleOnWater;
    }
    
    /**
     * Returns the {@link Animation} array representing the frames for when the {@code User} is animating on the water.
     *
     * @return {@link #userSwim}
     */
    private static Animation[] getUserSwim() {
        return userSwim;
    }
    
    /**
     * Returns the {@link Sprite} representing the picture containing all the {@code User} frames.
     *
     * @return {@link #userSprite}
     */
    private static Sprite getUserSprite() {
        return userSprite;
    }
    
    /**
     * Returns the {@link TextureRegion} array representing the rectangles in the {@link #USER_INFORMATION} files to be used as {@link PlayerActions#IDLE}
     * positions for when the {@code User} is not moving on land.
     *
     * @return {@link #userIdleOnLand}
     */
    private static TextureRegion[] getUserIdleOnLand() {
        return userIdleOnLand;
    }
    
    /**
     * Returns the {@link Animation} array representing the frames for when the {@code User} is animating on land.
     *
     * @return {@link #userWalk}
     */
    private static Animation[] getUserWalk() {
        return userWalk;
    }
    
    /**
     * Returns the {@link MainGameScreen} instance containing general values for the {@code User} to use.
     *
     * @return {@link #mainGameScreen}
     */
    private MainGameScreen getMainGameScreen() {
        return mainGameScreen;
    }
    
    /**
     * Sets the {@link #mainGameScreen}.
     *
     * @param mainGameScreen {@link #mainGameScreen}
     */
    private void setMainGameScreen(MainGameScreen mainGameScreen) {
        this.mainGameScreen = mainGameScreen;
    }
    
    /**
     * Returns the amount of time a recurring key has been held.
     *
     * @return {@link #movementKeyHeldDownTime}
     */
    private float getMovementKeyHeldDownTime() {
        return movementKeyHeldDownTime;
    }
    
    /**
     * Sets the amount of time a recurring key has been held.
     *
     * @param movementKeyHeldDownTime {@link #movementKeyHeldDownTime}
     */
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
     * Returns the {@code boolean} representing if the {@code User} is on {@link Water}.
     *
     * @return {@link #swimming}
     */
    private boolean isSwimming() {
        return swimming;
    }
    
    /**
     * Sets whether the {@code User} is overlapping {@link Water} or not.
     *
     * @param swimming {@link #swimming}
     */
    private void setSwimming(boolean swimming) {
        this.swimming = swimming;
    }
    
    /**
     * Replace the current position of the {@code User} and set it to the current {@link MapCreator#getStartPosition()} value.
     */
    private void resetPosition() {
        resetPosition(getMapCreator(), false);
    }
    
    /**
     * Takes a different {@link MapCreator} and extracts the {@link MapCreator#startPosition} information to set the {@code User} position to it.  Also, the {@link #mapCreator} will be
     * changed if {@code resetMapCreator} is true.
     *
     * @param mapCreator      New instance of a {@link MapCreator} to set a new position.
     * @param resetMapCreator Determines if the value of {@link #mapCreator} is altered to the {@code mapCreator} parameter.
     *
     * @see MapCreator
     * @see MapCreator#startPosition
     * @see #mapCreator
     */
    public void resetPosition(final MapCreator mapCreator, final boolean resetMapCreator) {
        setMapCreator(resetMapCreator ? mapCreator :
                      getMapCreator()); //Check if the mapCreator should be replaced depending on the resetMapCreator parameter.
        setPositionX((int)(mapCreator.getStartPosition().x + getUserWalkWidth() /
                                                             2)); //Set the x-position of the User to the x-position of the User of the new mapCreator.
        setPositionY((int)(mapCreator.getStartPosition().y + getUserWalkHeight() /
                                                             2)); //Set the y-position of the User to the y-position of the User of the new mapCreator.
    }
    
    /**
     * Returns the speed it takes a frame before it transitions to another frame.
     *
     * @return {@link #ANIMATION_SPEED}
     */
    private float getAnimationSpeed() {
        return ANIMATION_SPEED;
    }
    
    /**
     * Returns a {@link Rectangle} instance representing a future position of the {@code User} or the position of a {@link CollidableTileObject}.
     *
     * @param offsetX X-Position difference between the new x-position and the {@code User} x-position.
     * @param offsetY Y-Position difference between the new y-position and the {@code User} y-position.
     *
     * @return {@link Rectangle} instance of the future position of the {@code User} or the position of a {@link CollidableTileObject}.
     */
    private Rectangle getFutureRectangle(final float offsetX, final float offsetY) {
        Rectangle futureRectangle =
            getCurrentCollisionRectangle(); //Get the rectangle instance of the collision box of the User.
        futureRectangle.setX(
            futureRectangle.getX() + offsetX); //Add the offset onto the x-position of the collision box of the User.
        futureRectangle.setY(
            futureRectangle.getY() + offsetY); //Add the offset onto the y-position of the collision box of the User.
        return futureRectangle; //Return the new rectangle instance representing the future position.
    }
    
    /**
     * Updating every frame, checks the user input and reacts upon specific {@link Input.Keys} presses.
     *
     * @param deltaTime Time between frames.
     */
    public void handleInput(final float deltaTime) {
        getHandleInput().update(deltaTime); //Update the general movements and direction of the User.
        if(!isAligned() && !isFutureCollision() &&
           !isRestrictedMovement()) { //Checks if the User is moving, allowed to move, and no collision that the User will be going into.
            if(getDirection() == getUP()) { //Checks if the current direction of the User if upwards.
                setPositionY(getMovementKeyHeldDownTime() >= getKeyPressToMoveTime() ?
                             getPositionY() + getCurrentState().getSpeed() :
                             getPositionY()); //Move the User upwards a little bit according to if the up key has been pressed long enough and the type of action occuring.
            } else if(getDirection() == getDOWN()) { //Checks if the current direction of the User is downwards.
                setPositionY(getMovementKeyHeldDownTime() >= getKeyPressToMoveTime() ?
                             getPositionY() - getCurrentState().getSpeed() :
                             getPositionY()); //Move the User downwards a little bit according to if the down key has been pressed long enough and the type of action occuring.
            } else if(getDirection() == getRIGHT()) { //Checks if the current direction of the User is to the right.
                setPositionX(getMovementKeyHeldDownTime() >= getKeyPressToMoveTime() ?
                             getPositionX() + getCurrentState().getSpeed() :
                             getPositionX()); //Move the User to the right a little bit according to if the right key has been pressed long enough and the type of action occuring.
            } else if(getDirection() == getLEFT()) { //Checks if the current direction of the User is to the left.
                setPositionX(getMovementKeyHeldDownTime() >= getKeyPressToMoveTime() ?
                             getPositionX() - getCurrentState().getSpeed() :
                             getPositionX()); //Move the User to the left a little bit according to if the left key has been pressed long enough and the type of action occuring.
            }
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.X) &&
                  !isRestrictedMovement()) { //If the User is not currently trying to move to a new location, check if the User is allowed to move when the X key is pressed.
            
            Rectangle futureRectangle = (getDirection() == getUP()) ? getFutureRectangle(0, Engine.getTileSize()) :
                                        (getDirection() == getDOWN()) ? getFutureRectangle(0, -Engine.getTileSize()) :
                                        (getDirection() == getRIGHT()) ? getFutureRectangle(Engine.getTileSize(), 0) :
                                        getFutureRectangle(-Engine.getTileSize(), 0
                                        ); //Get the position of the User if the position change were applied, the direction checked to get the correct position.
    
            if(isColliding(futureRectangle, false) && getCollidingTileObject(
                futureRectangle) instanceof Water) { //Check if the tile the User would be going to is a water tile.
                //Create a lambda for moving the User a single tile in the direction the User is pointing to.
                AlterPlayerPosition alterPlayerPosition =
                    (PrimitiveConsumer setPositionMethod, PrimitiveSupplier getPositionMethod, int alterValue) -> setPositionMethod
                                                                                                                      .accept(
                                                                                                                          getPositionMethod
                                                                                                                              .get() +
                                                                                                                          alterValue);
        
                Runnable alterAction = (getDirection() == getUP()) ? () -> alterPlayerPosition
                                                                               .alterPosition(this::setPositionY,
                                                                                              this::getPositionY,
                                                                                              Engine.getTileSize()
                                                                               ) : (getDirection() == getDOWN()) ?
                                                                                   () -> alterPlayerPosition
                                                                                             .alterPosition(
                                                                                                 this::setPositionY,
                                                                                                 this::getPositionY,
                                                                                                 -Engine.getTileSize()
                                                                                             ) :
                                                                                   (getDirection() == getRIGHT()) ?
                                                                                   () -> alterPlayerPosition
                                                                                             .alterPosition(
                                                                                                 this::setPositionX,
                                                                                                 this::getPositionX,
                                                                                                 Engine.getTileSize()
                                                                                             ) :
                                                                                   () -> alterPlayerPosition
                                                                                             .alterPosition(
                                                                                                 this::setPositionX,
                                                                                                 this::getPositionX,
                                                                                                 -Engine.getTileSize()
                                                                                             ); //Creates an action to move the User depending on the direction of the User.
                
                alterAction.run(); //Executes the action for moving the User.
            } else if(isColliding(futureRectangle, false) && getCollidingTileObject(
                futureRectangle) instanceof Sign) { //Check if the tile the User is looking at is a Sign.
                getMainGameScreen().getDialog().setVisible(true); //Makes the main dialog visible to the User.
                setRestrictedMovement(true); //Stops any form of movement the User would normally be able to do.
            }
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.X) &&
                  isRestrictedMovement()) { //Checks if X key is pressed when the User is in a non-movable position.
            getMainGameScreen().getDialog().setVisible(false); //Removes the dialog from the screen.
            getMainGameScreen().getDialog().setTimer(
                0f); //Resets the point at which the dialog was drawing the characters onto the screen.
            setRestrictedMovement(false); //Allows movement of the User.
        }
    }
    
    /**
     * The method {@code isColliding} checks to see whether a {@link Rectangle} instance overlaps with any of the {@link CollidableTileObject}s in {@link MapCreator#collidableTileObjects}.
     * According to whether activateCollisionMethod is {@code true} or {@code false}, the {@link CollidableTileObject#onCollide()} will be notified if a collision is present.
     * <p>
     * For {@link Water} overlapping, the method will ignore the collision as {@link Water} does not have the same properties as other {@link CollidableTileObject}s like {@link Barrier}.
     * As for {@link Door}s, the {@link Door#onCollide()} method will be called and the {@link Door} instance will start transitioning to the next room.
     *
     * @param rectangle               {@link Rectangle} instance to check among all other obstacles in {@link MapCreator#collidableTileObjects}.
     * @param activateCollisionMethod Value for whether or not the {@link CollidableTileObject#onCollide()} is to be called if there is a collision.
     *
     * @return Value of if there is a collision between the {@link Rectangle} instance and values in {@link MapCreator#collidableTileObjects}.
     *
     * @see Rectangle
     * @see CollidableTileObject
     * @see CollidableTileObject#onCollide()
     * @see MapCreator#collidableTileObjects
     * @see Door
     * @see Water
     * @see Barrier
     */
    private boolean isColliding(final Rectangle rectangle, final boolean activateCollisionMethod) {
        for(Rectangle collidingRectangle : NPC.getNpcPositions()) {
            if(collidingRectangle.overlaps(rectangle)) {
                return true;
            }
        }
        
        for(CollidableTileObject collidableTileObject : getMapCreator()
                                                            .getCollidableTileObjects()) { //Iterate through all the objects stored in the current map.
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
    
    /**
     * Gets the {@link CollidableTileObject} that has the same positions as the {@link Rectangle} instance inputted.  A
     * null value is returned if no object ends up being found from the current map.
     *
     * @param rectangle {@link Rectangle} instance to check if there is a similar {@link CollidableTileObject} in the current map.
     *
     * @return {@link CollidableTileObject} that has similar values to the inputted {@link Rectangle}.
     *
     * @see CollidableTileObject
     * @see Rectangle
     */
    private CollidableTileObject getCollidingTileObject(final Rectangle rectangle) {
        for(CollidableTileObject collidableTileObject : getMapCreator().getCollidableTileObjects()) {
            if(collidableTileObject.isColliding(rectangle)) {
                return collidableTileObject;
            }
        }
        return null;
    }
    
    /**
     * Returns the {@link HandleInput} to check for general key presses with {@link HandleInput#update(float)}.
     *
     * @return {@link #handleInput}
     */
    private HandleInput getHandleInput() {
        return handleInput;
    }
    
    /**
     * Checks {@code User} values and updates them according to actions made in {@link #handleInput(float)}.
     *
     * @param deltaTime Amount of time between each frame.
     */
    @Override
    public void update(final float deltaTime) {
        updateAlignment();
        updateActorXY();
        updateCurrentCollisionRectangle();
        updateSwimming();
        updateAnimation(deltaTime);
    }
    
    /**
     * Method for drawing the {@code User} onto the {@link Screen}, based on the movement and action of the {@code User}.
     *
     * @param batch       Drawing tool.
     * @param parentAlpha Alpha value to add transparency to all children.
     */
    @Override
    public void draw(final Batch batch, final float parentAlpha) {
        if((getCurrentState() == PlayerActions.IDLE && !isSwimming()) || getCurrentState() == PlayerActions.WALKING ||
           getCurrentState() == PlayerActions.RUNNING) {
            batch.draw(getUserSprite(), getPositionX() - getUserWalkWidth() / 2,
                       getPositionY() - getUserWalkHeight() / 2, getUserWalkWidth(), getUserWalkHeight()
            );
        } else if(isSwimming()) {
            batch.draw(getUserSprite(), getPositionX() - getUserSwimWidth() / 2,
                       getPositionY() - getUserSwimHeight() / 2, getUserSwimWidth(), getUserSwimHeight()
            );
        }
    }
    
    /**
     * Checks if the {@code User} if correctly placed onto a tile so the {@code User} knows to stop moving if {@link #handleInput(float)} gets no feedback.
     */
    private void updateAlignment() {
        setAligned((getPositionX() + (Engine.getHalfTileSize())) % Engine.getTileSize() == 0 &&
                   (getPositionY() + (Engine.getHalfTileSize())) % Engine.getTileSize() - 1 == 0);
    }
    
    /**
     * Checks whether the {@code User} is on {@link Water} or not by getting the collision box of the {@code User}.
     */
    private void updateSwimming() {
        setSwimming(getCollidingTileObject(getCurrentCollisionRectangle()) instanceof Water);
    }
    
    /**
     * Updates the frame of the {@code User} by the amount of time that has passed.
     *
     * @param deltaTime Amount of time between each frame.
     */
    private void updateAnimation(final float deltaTime) {
        getUserSprite().setRegion(getFrame(deltaTime));
    }
    
    /**
     * Sets the position of the {@link Actor} part of the {@code User} relative to the position values so that the {@link MainGameScreen#camera} can follow the {@code User} properly, as the
     * positions inside {@code User} are offset because the position is the bottom left corner of the {@code User}.
     */
    private void updateActorXY() {
        setX(getPositionX() - getUserWalkWidth() / 2);
        setY(getPositionY() - getUserWalkHeight() / 2);
    }
    
    /**
     * Gets the current {@link TextureRegion} representing the frame that the {@code User} is at according to its {@link #getCurrentState()} value and time that has passed by.
     *
     * @param deltaTime Amount of time between each frame.
     *
     * @return {@link TextureRegion} instance of the frame the {@link User} will use to draw onto the {@link #mainGameScreen}.
     */
    private TextureRegion getFrame(final float deltaTime) {
        float stateTime = getStateTimer() * getAnimationSpeed();
        
        setStateTimer(getCurrentState() == getPreviousState() ? getStateTimer() + deltaTime : 0);
        setPreviousState(getCurrentState());
        return (getCurrentState() == PlayerActions.WALKING) ?
               (getDirection() == getUP()) ? (TextureRegion)(getUserWalk()[0].getKeyFrame(stateTime, true)) :
               (getDirection() == getDOWN()) ? (TextureRegion)(getUserWalk()[1].getKeyFrame(stateTime, true)) :
               (getDirection() == getRIGHT()) ? (TextureRegion)(getUserWalk()[2].getKeyFrame(stateTime, true)) :
               (TextureRegion)(getUserWalk()[3].getKeyFrame(stateTime, true)) :

               (getCurrentState() == PlayerActions.RUNNING) ?
               (getDirection() == getUP()) ? (TextureRegion)(getUserRun()[0].getKeyFrame(stateTime, true)) :
               (getDirection() == getDOWN()) ? (TextureRegion)(getUserRun()[1].getKeyFrame(stateTime, true)) :
               (getDirection() == getRIGHT()) ? (TextureRegion)(getUserRun()[2].getKeyFrame(stateTime, true)) :
               (TextureRegion)(getUserRun()[3].getKeyFrame(stateTime, true)) :

               (getCurrentState() == PlayerActions.SWIMMING) ?
               (getDirection() == getUP()) ? (TextureRegion)(getUserSwim()[0].getKeyFrame(stateTime, true)) :
               (getDirection() == getDOWN()) ? (TextureRegion)(getUserSwim()[1].getKeyFrame(stateTime, true)) :
               (getDirection() == getRIGHT()) ? (TextureRegion)(getUserSwim()[2].getKeyFrame(stateTime, true)) :
               (TextureRegion)(getUserSwim()[3].getKeyFrame(stateTime, true)) : (isSwimming()) ?
                                                                                (getDirection() == getUP()) ?
                                                                                getUserIdleOnWater()[0] :
                                                                                (getDirection() == getDOWN()) ?
                                                                                getUserIdleOnWater()[1] :
                                                                                (getDirection() == getRIGHT()) ?
                                                                                getUserIdleOnWater()[2] :
                                                                                getUserIdleOnWater()[3] :
                                                                                (getDirection() == getUP()) ?
                                                                                getUserIdleOnLand()[0] :
                                                                                (getDirection() == getDOWN()) ?
                                                                                getUserIdleOnLand()[1] :
                                                                                (getDirection() == getRIGHT()) ?
                                                                                getUserIdleOnLand()[2] :
                                                                                getUserIdleOnLand()[3];
    }
    
    /**
     * Finds the current action of the {@code User} and returns the type of action.
     *
     * @return Action the {@code User} is performing at the current frame.
     */
    private PlayerActions getCurrentState() {
        if(!isMoving() || isRestrictedMovement()) {
            return PlayerActions.IDLE;
        } else {
            if(!isSwimming()) {
                if(!Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && !Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)) {
                    return PlayerActions.WALKING;
                } else {
                    return PlayerActions.RUNNING;
                }
            } else {
                return PlayerActions.SWIMMING;
            }
        }
    }
}