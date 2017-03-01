package com.horse.pokemon.amethyst.data.characters;

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
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.horse.pokemon.amethyst.Engine;
import com.horse.pokemon.amethyst.data.objects.CollidableTileObject;
import com.horse.pokemon.amethyst.data.objects.Sign;
import com.horse.pokemon.amethyst.data.objects.Water;
import com.horse.pokemon.amethyst.data.pokemon.Pokemon;
import com.horse.pokemon.amethyst.graphics.MainGameScreen;
import com.horse.pokemon.amethyst.graphics.animation.AnimationInterface;
import com.horse.pokemon.amethyst.graphics.animation.AnimationManager;
import com.horse.pokemon.amethyst.graphics.background.system.MapCreator;
import com.horse.pokemon.amethyst.graphics.battle.system.BattleScreen;
import com.horse.pokemon.amethyst.graphics.dialog.Dialog;

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
 *     {@link #userSprite} is updated according to the delta time, {@link #getCurrentDirection()}, and
 *     {@link #findCurrentState()}, as the animation adds realism to the {@link Game}. It is also good to not that the
 *     {@code User} is an {@link BasePlayer} which is also an {@link Actor}, meaning that the {@code User} is to be
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
 *         In Battle     - When the {@code User} is fighting against another {@link BasePlayer}, adding a lot of
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
 * @see BasePlayer
 * @see Actor
 * @see AnimationInterface
 * @see TextureRegion
 * @see Animation
 * @see MapCreator
 * @see Sprite
 * @see Rectangle
 */
public class User extends BasePlayer {
    /**
     * The {@code byte} representing the amount of pixels from the left-most part of the {@code User} to the right-most part of the {@code User} when in the
     * {@link #getWALKING()} or {@link #getIDLE()} if the {@code User} is on land, also determining how big the user
     * is drawn every frame x-wise.
     */
    private static final byte USER_WALK_WIDTH = 16;
    
    /**
     * The {@code byte} representing the amount of pixels from the upper-most part of the {@code User} to the bottom-most part of the {@code User} when in
     * the {@link #getWALKING()} or {@link #getIDLE()} state if the {@code User} is on land, also determining how big the
     * user is draw every frame y-wise.
     */
    private static final byte USER_WALK_HEIGHT = 19;
    
    /**
     * The {@code byte} representing the amount of pixels from the left-most part of the {@code User} to the right-most part of the {@code User} when in the
     * {@link #getSWIMMING()} or {@link #getIDLE()} state if the {@code User} is on water, also determining how big the user
     * is drawn every frame x-wise.
     */
    private static final byte USER_SWIM_WIDTH = 22;
    
    /**
     * The {@code byte} representing the amount of pixels from the upper-most part of the {@code User} to the bottom-most part of the {@code User} when in
     * the {@link #getSWIMMING()} or {@link #getIDLE()} state if the {@code User} is on water, also determining how big the
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
    private static final Animation[] userRun         = new Animation[] {
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
    private static final Animation[] userSwim        = new Animation[] {
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
     * The current time that a movement key has been pressed to check if the amount of time pressed is enough to move the {@code User}.
     */
    private float          movementKeyHeldDownTime;
    /**
     * The {@code boolean} instance representing if the {@code User} is currently on top of the water to note which action animation should be drawn.
     */
    private boolean        swimming;
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
        setName("User");
        setPreviousState(
            findCurrentState()); //Initializes previousState as the action that happens at the start, which should always be IDLE.
        setAnimationTimer(0f); //Initializes stateTimer to a neutral value of zero representing a 'reset' to the timer.
        setCurrentDirection(
            getDOWN()); //Initializes direction to have the User pointing downwards at the start by default.  No main reason to be looking down.
        setFlags(getDefaultFlags());
        setSwimming(
            false); //Initializes swimming to false because the User should start on land at the beginning of the game.
        setMovementKeyHeldDownTime(
            0f); //Initializes the time a key was pressed down to zero, as the specific key has not been pressed yet.
        setMainGameScreen(mainGameScreen); //Initializes the main game screen to the parameter value.
        
        setBounds(0, 0, Engine.getHalfTileSize(), Engine.getHalfTileSize());
        getUserSprite().setRegion(getUserIdleOnLand()[1]);
        
        resetPosition();
        
        updateCurrentCollisionRectangle();
    
        addListener(new InputListener() {
            public boolean keyDown(InputEvent event, int keycode) {
                setMovementFlag((keycode == Input.Keys.UP) ? (byte)(getMovementFlag() | getIsMovingUp()) :
                                (keycode == Input.Keys.DOWN) ? (byte)(getMovementFlag() | getIsMovingDown()) :
                                (keycode == Input.Keys.RIGHT) ? (byte)(getMovementFlag() | getIsMovingRight()) :
                                (keycode == Input.Keys.LEFT) ? (byte)(getMovementFlag() | getIsMovingLeft()) :
                                getMovementFlag());
                
                return true;
            }
        
            public boolean keyUp(InputEvent event, int keycode) {
                setMovementFlag((keycode == Input.Keys.UP) ? (byte)(getMovementFlag() & ~getIsMovingUp()) :
                                (keycode == Input.Keys.DOWN) ? (byte)(getMovementFlag() & ~getIsMovingDown()) :
                                (keycode == Input.Keys.RIGHT) ? (byte)(getMovementFlag() & ~getIsMovingRight()) :
                                (keycode == Input.Keys.LEFT) ? (byte)(getMovementFlag() & ~getIsMovingLeft()) :
                                getMovementFlag());
                
                return true;
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
     * Returns the horizontal pixel value when the {@code User} is in a {@link #getSWIMMING()} state or {@link #getIDLE()} state when on water.
     *
     * @return {@link #USER_SWIM_WIDTH}
     */
    private static byte getUserSwimWidth() {
        return USER_SWIM_WIDTH;
    }
    
    /**
     * Returns the vertical pixel value when the {@code User} is in a {@link #getSWIMMING()} state or {@link #getIDLE()} state when on water.
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
     * Returns the horizontal pixel value when the {@code User} is in a {@link #getWALKING()} state or {@link #getIDLE()} state when on land.
     *
     * @return {@link #USER_WALK_WIDTH}
     */
    private static byte getUserWalkWidth() {
        return USER_WALK_WIDTH;
    }
    
    /**
     * Returns the vertical pixel value when the {@code User} is in a {@link #getWALKING()} state of {@link #getIDLE()} state when on land.
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
     * Returns the {@link TextureRegion} array representing the rectangles in the {@link #USER_INFORMATION} files to be used as {@link #getIDLE()}
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
     * Returns the {@link TextureRegion} array representing the rectangles in the {@link #USER_INFORMATION} files to be used as {@link #getIDLE()}
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
    boolean isSwimming() {
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
     * Takes a different {@link MapCreator} and extracts the {@link MapCreator#startPosition} information to set the {@code User} position to it.  Also, the {@link MapCreator} will be
     * changed if {@code resetMapCreator} is true.
     *
     * @see MapCreator
     * @see MapCreator#startPosition
     */
    public void resetPosition() {
        setX((int)(MapCreator.getStartPosition().x + getUserWalkWidth() /
                                                     2)); //Set the x-position of the User to the x-position of the User of the new mapCreator.
        setY((int)(MapCreator.getStartPosition().y + getUserWalkHeight() /
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
     * Updating every frame, checks the user input and reacts upon specific {@link Input.Keys} presses.
     */
    private void handleInput() {
        if(!isFlag(getIsAligned()) && !isFlag(getIsFutureCollision()) && !isFlag(
            getIsRestrictedMovement())) { //Checks if the User is moving, allowed to move, and no collision that the User will be going into.
            if(getCurrentDirection() == getUP()) { //Checks if the current direction of the User if upwards.
                setY(getMovementKeyHeldDownTime() >= getKeyPressToMoveTime() ? getY() + getSpeed(findCurrentState()) :
                     getY()); //Move the User upwards a little bit according to if the up key has been pressed long enough and the type of action occuring.
            } else if(getCurrentDirection() == getDOWN()) { //Checks if the current direction of the User is downwards.
                setY(getMovementKeyHeldDownTime() >= getKeyPressToMoveTime() ? getY() - getSpeed(findCurrentState()) :
                     getY()); //Move the User downwards a little bit according to if the down key has been pressed long enough and the type of action occuring.
            } else if(getCurrentDirection() ==
                      getRIGHT()) { //Checks if the current direction of the User is to the right.
                setX(getMovementKeyHeldDownTime() >= getKeyPressToMoveTime() ? getX() + getSpeed(findCurrentState()) :
                     getX()); //Move the User to the right a little bit according to if the right key has been pressed long enough and the type of action occuring.
            } else if(getCurrentDirection() ==
                      getLEFT()) { //Checks if the current direction of the User is to the left.
                setX(getMovementKeyHeldDownTime() >= getKeyPressToMoveTime() ? getX() - getSpeed(findCurrentState()) :
                     getX()); //Move the User to the left a little bit according to if the left key has been pressed long enough and the type of action occuring.
            }
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.X) && !isFlag(
            getIsRestrictedMovement())) { //If the User is not currently trying to move to a new location, check if the User is allowed to move when the X key is pressed.
            //Shifts the User one tile if the User is pointing in the direction of water and is adjacent to that water in the corresponding direction.
            Rectangle futureRectangle =
                (getCurrentDirection() == getUP()) ? getFutureRectangle(0, Engine.getTileSize()) :
                (getCurrentDirection() == getDOWN()) ? getFutureRectangle(0, -Engine.getTileSize()) :
                (getCurrentDirection() == getRIGHT()) ? getFutureRectangle(Engine.getTileSize(), 0) :
                getFutureRectangle(-Engine.getTileSize(),
                                   0
                ); //Get the position of the User if the position change were applied, the direction checked to get the correct position.
            
            if(isColliding(futureRectangle, false) && getCollidingTileObject(
                futureRectangle) instanceof Water) { //Check if the tile the User would be going to is a water tile.
    
                addAction(Actions.sequence(new Action() {
                    @Override
                    public boolean act(float delta) {
                        Dialog.addTextAction(getMainGameScreen().getDialog(),
                                             "The water is calm.\nWould you like to surf?", Input.Keys.X
                        );
                        return true;
                    }
                }, new Action() {
                    @Override
                    public boolean act(float delta) {
                        return getMainGameScreen().getDialog().getActions().size == 0;
                    }
                }, new Action() {
                    @Override
                    public boolean act(float delta) {
                        setX(getX() + (getCurrentDirection() == getRIGHT() ? Engine.getTileSize() :
                                       (getCurrentDirection() == getLEFT()) ? -Engine.getTileSize() : 0));
                        setY(getY() + (getCurrentDirection() == getUP() ? Engine.getTileSize() :
                                       (getCurrentDirection() == getDOWN()) ? -Engine.getTileSize() : 0));
                        removeFlag(getIsRestrictedMovement()); //Allows movement of the User.
                        return true;
                    }
                }));
    
                raiseFlag(
                    getIsRestrictedMovement()); //Stops any form of movement the User would normally be able to do.
            } else if(isColliding(futureRectangle, false) && getCollidingTileObject(
                futureRectangle) instanceof Sign) { //Check if the tile the User is looking at is a Sign.
    
                addAction(Actions.sequence(new Action() {
                    @Override
                    public boolean act(float delta) {
                        Dialog.addTextAction(getMainGameScreen().getDialog(), "Test", Input.Keys.X);
                        return true;
                    }
                }, new Action() {
                    @Override
                    public boolean act(float delta) {
                        return getMainGameScreen().getDialog().getActions().size == 0;
                    }
                }, new Action() {
                    @Override
                    public boolean act(float delta) {
                        removeFlag(getIsRestrictedMovement()); //Allows movement of the User.
                        return true;
                    }
                }));
                
                raiseFlag(
                    getIsRestrictedMovement()); //Stops any form of movement the User would normally be able to do.
            } else if(isColliding(futureRectangle, false) && getCollidingTileObject(futureRectangle) ==
                                                             null) { //Check if the User is pointing at an NPC character.
                addAction(Actions.sequence(new Action() {
                    @Override
                    public boolean act(float delta) {
                        Dialog
                            .addTextAction(getMainGameScreen().getDialog(), "Would you like to battle?", Input.Keys.X);
                        return true;
                    }
                }, new Action() {
                    @Override
                    public boolean act(float delta) {
                        return getMainGameScreen().getDialog().getActions().size == 0;
                    }
                }, new Action() {
                    @Override
                    public boolean act(float delta) {
                        removeFlag(getIsRestrictedMovement()); //Allows movement of the User.
                        getMainGameScreen()
                            .changeCurrentScreen(BattleScreen.class); //Change the screen so that a battle is ensued.
                        return true;
                    }
                }));
    
                raiseFlag(
                    getIsRestrictedMovement()); //Stops any form of movement the User would normally be able to do.
            }
        }
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
        for(CollidableTileObject collidableTileObject : MapCreator.getCollidableTileObjects()) {
            if(collidableTileObject.isColliding(rectangle)) {
                return collidableTileObject;
            }
        }
        return null;
    }
    
    /**
     * Method for drawing the {@code User} onto the {@link Screen}, based on the movement and action of the {@code User}.
     *
     * @param batch       Drawing tool.
     * @param parentAlpha Alpha value to add transparency to all children.
     */
    @Override
    public void draw(final Batch batch, final float parentAlpha) {
        if((findCurrentState() == getIDLE() && !isSwimming()) || findCurrentState() == getWALKING() ||
           findCurrentState() == getRUNNING()) {
            batch.draw(getUserSprite(), getX() - getUserWalkWidth() / 2, getY() - getUserWalkHeight() / 2,
                       getUserWalkWidth(), getUserWalkHeight()
            );
        } else if(isSwimming()) {
            batch.draw(getUserSprite(), getX() - getUserSwimWidth() / 2, getY() - getUserSwimHeight() / 2,
                       getUserSwimWidth(), getUserSwimHeight()
            );
        }
    }
    
    /**
     * Checks {@code User} values and updates them according to actions made in {@link #handleInput()}.
     *
     * @param deltaTime Amount of time between each frame.
     */
    @Override
    public void act(final float deltaTime) {
        super.act(deltaTime);
        updateAlignment();
        updateInput(deltaTime);
        updateCurrentCollisionRectangle();
        updateSwimming();
        updateAnimation(deltaTime);
    }
    
    private void updateInput(float deltaTime) {
        if(getMovementFlag() == getIsMovingUp()) {
            if(isFlag(getIsAligned()) && !isFlag(getIsRestrictedMovement()) && findCurrentState() != getInBattle()) {
                raiseFlag(getIsMoving());
                removeFlag(getIsAligned());
                setCurrentDirection(getUP());
                if(isColliding(getFutureRectangle(0, Engine.getTileSize()), true)) {
                    raiseFlag(getIsFutureCollision());
                } else {
                    removeFlag(getIsFutureCollision());
                }
                setMovementKeyHeldDownTime(getMovementKeyHeldDownTime() + deltaTime);
            }
        } else if(getMovementFlag() == getIsMovingDown()) {
            if(isFlag(getIsAligned()) && !isFlag(getIsRestrictedMovement()) && findCurrentState() != getInBattle()) {
                raiseFlag(getIsMoving());
                removeFlag(getIsAligned());
                setCurrentDirection(getDOWN());
                if(isColliding(getFutureRectangle(0, -Engine.getTileSize()), true)) {
                    raiseFlag(getIsFutureCollision());
                } else {
                    removeFlag(getIsFutureCollision());
                }
                setMovementKeyHeldDownTime(getMovementKeyHeldDownTime() + deltaTime);
            }
        } else if(getMovementFlag() == getIsMovingRight()) {
            if(isFlag(getIsAligned()) && !isFlag(getIsRestrictedMovement()) && findCurrentState() != getInBattle()) {
                raiseFlag(getIsMoving());
                removeFlag(getIsAligned());
                setCurrentDirection(getRIGHT());
                if(isColliding(getFutureRectangle(Engine.getTileSize(), 0), true)) {
                    raiseFlag(getIsFutureCollision());
                } else {
                    removeFlag(getIsFutureCollision());
                }
                setMovementKeyHeldDownTime(getMovementKeyHeldDownTime() + deltaTime);
            }
        } else if(getMovementFlag() == getIsMovingLeft()) {
            if(isFlag(getIsAligned()) && !isFlag(getIsRestrictedMovement()) && findCurrentState() != getInBattle()) {
                raiseFlag(getIsMoving());
                removeFlag(getIsAligned());
                setCurrentDirection(getLEFT());
                if(isColliding(getFutureRectangle(-Engine.getTileSize(), 0), true)) {
                    raiseFlag(getIsFutureCollision());
                } else {
                    removeFlag(getIsFutureCollision());
                }
                setMovementKeyHeldDownTime(getMovementKeyHeldDownTime() + deltaTime);
            }
        } else {
            if(isFlag(getIsAligned())) {
                removeFlag(getIsMoving());
                setMovementKeyHeldDownTime(0f);
            }
        }
    
        handleInput();
    }
    
    /**
     * Checks if the {@code User} if correctly placed onto a tile so the {@code User} knows to stop moving if {@link #handleInput()} gets no feedback.
     */
    private void updateAlignment() {
        if((getX() + (Engine.getHalfTileSize())) % Engine.getTileSize() == 0 &&
           (getY() + (Engine.getHalfTileSize())) % Engine.getTileSize() - 1 == 0) {
            raiseFlag(getIsAligned());
        } else {
            removeFlag(getIsAligned());
        }
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
     * Gets the current {@link TextureRegion} representing the frame that the {@code User} is at according to its {@link #findCurrentState()} value and time that has passed by.
     *
     * @param deltaTime Amount of time between each frame.
     *
     * @return {@link TextureRegion} instance of the frame the {@link User} will use to draw onto the {@link #mainGameScreen}.
     */
    private TextureRegion getFrame(final float deltaTime) {
        float stateTime = getAnimationTimer() * getAnimationSpeed();
    
        setAnimationTimer(findCurrentState() == findCurrentState() ? getAnimationTimer() + deltaTime : 0);
        setPreviousState(findCurrentState());
        return (findCurrentState() == getWALKING()) ?
               (getCurrentDirection() == getUP()) ? (TextureRegion)(getUserWalk()[0].getKeyFrame(stateTime, true)) :
               (getCurrentDirection() == getDOWN()) ? (TextureRegion)(getUserWalk()[1].getKeyFrame(stateTime, true)) :
               (getCurrentDirection() == getRIGHT()) ? (TextureRegion)(getUserWalk()[2].getKeyFrame(stateTime, true)) :
               (TextureRegion)(getUserWalk()[3].getKeyFrame(stateTime, true)) :

               (findCurrentState() == getRUNNING()) ?
               (getCurrentDirection() == getUP()) ? (TextureRegion)(getUserRun()[0].getKeyFrame(stateTime, true)) :
               (getCurrentDirection() == getDOWN()) ? (TextureRegion)(getUserRun()[1].getKeyFrame(stateTime, true)) :
               (getCurrentDirection() == getRIGHT()) ? (TextureRegion)(getUserRun()[2].getKeyFrame(stateTime, true)) :
               (TextureRegion)(getUserRun()[3].getKeyFrame(stateTime, true)) :

               (findCurrentState() == getSWIMMING()) ?
               (getCurrentDirection() == getUP()) ? (TextureRegion)(getUserSwim()[0].getKeyFrame(stateTime, true)) :
               (getCurrentDirection() == getDOWN()) ? (TextureRegion)(getUserSwim()[1].getKeyFrame(stateTime, true)) :
               (getCurrentDirection() == getRIGHT()) ? (TextureRegion)(getUserSwim()[2].getKeyFrame(stateTime, true)) :
               (TextureRegion)(getUserSwim()[3].getKeyFrame(stateTime, true)) : (isSwimming()) ?
                                                                                (getCurrentDirection() == getUP()) ?
                                                                                getUserIdleOnWater()[0] :
                                                                                (getCurrentDirection() == getDOWN()) ?
                                                                                getUserIdleOnWater()[1] :
                                                                                (getCurrentDirection() == getRIGHT()) ?
                                                                                getUserIdleOnWater()[2] :
                                                                                getUserIdleOnWater()[3] :
                                                                                (getCurrentDirection() == getUP()) ?
                                                                                getUserIdleOnLand()[0] :
                                                                                (getCurrentDirection() == getDOWN()) ?
                                                                                getUserIdleOnLand()[1] :
                                                                                (getCurrentDirection() == getRIGHT()) ?
                                                                                getUserIdleOnLand()[2] :
                                                                                getUserIdleOnLand()[3];
    }
    
    /**
     * Finds the current action of the {@code User} and returns the type of action.
     *
     * @return Action the {@code User} is performing at the current frame.
     */
    @Override
    public byte findCurrentState() {
        if(getMainGameScreen() != null) {
            if(!isFlag(getIsMoving()) || isFlag(getIsRestrictedMovement())) {
                if(getMainGameScreen().getEngine().getScreen().getClass() != BattleScreen.class) {
                    return getIDLE();
                } else {
                    return getInBattle();
                }
            } else {
                if(!isSwimming()) {
                    if(!Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) &&
                       !Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)) {
                        return getWALKING();
                    } else {
                        return getRUNNING();
                    }
                } else {
                    return getSWIMMING();
                }
            }
        }
    
        return getIDLE();
    }
}