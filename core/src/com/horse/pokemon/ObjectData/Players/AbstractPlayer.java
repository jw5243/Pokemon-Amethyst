package com.horse.pokemon.ObjectData.Players;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.horse.pokemon.GraphicsEngine.MainInterface.HandleInput;
import com.horse.pokemon.GraphicsEngine.MapEngine.MapCreator;
import com.horse.pokemon.ObjectData.PokemonData.Pokemon;

/**
 * The base class for all new characters in the game.
 *
 * @author Horse
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractPlayer extends Actor {
    /**
     * The {@code byte} representing the 'up' direction for when the character is moving upwards or at a constant position pointing up.
     */
    private static final byte UP = 1;
    
    /**
     * The {@code byte} representing the 'down' direction for when the character is moving downwards or at a constant position pointing down.
     */
    private static final byte DOWN = 2;
    
    /**
     * The {@code byte} representing the 'right' direction for when the character is moving to the right or at a constant position pointing to the right.
     */
    private static final byte RIGHT = 3;
    
    /**
     * The {@code byte} representing the 'left' direction for when the character is moving to the left of at a constant position pointing to the left.
     */
    private static final byte LEFT = 4;
    
    /**
     * The max amount of slots with PokemonData a Player can have at one time.
     */
    private static final int MAX_POKEMON = 6;
    
    /**
     * The {@code float} instance representing where the {@code AbstractPlayer} is every frame in terms of horizontal pixels.
     */
    private float   positionX;
    /**
     * The {@code float} instance representing where the {@code AbstractPlayer} is every frame in terms of vertical pixels.
     */
    private float   positionY;
    /**
     * The {@code boolean} instance representing if the {@link HandleInput} of the {@code AbstractPlayer} has a keyboard press that will represent that the {@code AbstractPlayer} is to
     * try and move throughout the map.
     */
    private boolean moving;
    /**
     * The {@code boolean} instance representing if the {@code AbstractPlayer} is correctly positioned onto one of the game tiles.  This may also be thought of as a
     * snap-to-grid type of system.
     */
    private boolean aligned;
    
    /**
     * The {@link MapCreator} instance representing the current map the {@code AbstractPlayer} is on, which is used to calculate whether the {@code AbstractPlayer} is
     * colliding with any of the objects of the map.
     */
    private MapCreator    mapCreator;
    /**
     * The {@link Rectangle} instance representing the boundaries of the {@code AbstractPlayer} to detect collisions.
     */
    private Rectangle     currentCollisionRectangle;
    /**
     * The {@link PlayerActions} instance representing the last action the {@code AbstractPlayer} was in to implement smoot animation transitions and to check whether
     * the animation of the {@code AbstractPlayer} should be reset or continued.
     */
    private PlayerActions previousState;
    /**
     * The {@code boolean} instance representing if the {@code AbstractPlayer} is going to collide in the next tile position by referencing the {@link #direction} of the {@code
     * AbstractPlayer} to identify the correct {@link Rectangle} the {@code AbstractPlayer} would be on top of.  This ensures the {@code AbstractPlayer} stays {@link #aligned} with the
     * tiles.
     */
    private boolean       futureCollision;
    /**
     * The {@code boolean} instance representing if the {@code AbstractPlayer} is able to move not in terms of collisions but during some scenes in the game.
     */
    private boolean restrictedMovement;
    /**
     * The {@code byte} instance representing where the {@code AbstractPlayer} is pointing towards.
     */
    private byte    direction;
    /**
     * The {@code float} instance used to represent the amount of time passed during a new action, also specifying which frame is to be drawn.
     */
    private float   stateTimer;
    /**
     * The {@link String} instance representing a Player's name.
     */
    private String  name;
    /**
     * The {@link Pokemon} array instance representing the Player's PokemonData slots.
     */
    private Pokemon[] currentPokemon = new Pokemon[MAX_POKEMON];

    /**
     * Returns the representation for when the {@code AbstractPlayer} is pointing upwards.
     *
     * @return {@link #UP}
     */
    static byte getUP() {
        return UP;
    }

    /**
     * Returns the representation for when the {@code AbstractPlayer} is pointing downwards.
     *
     * @return {@link #DOWN}
     */
    static byte getDOWN() {
        return DOWN;
    }
    
    /**
     * Returns the representation for when the {@code AbstractPlayer} is pointing to the right.
     *
     * @return {@link #RIGHT}
     */
    static byte getRIGHT() {
        return RIGHT;
    }
    
    /**
     * Returns the representation for when the {@code AbstractPlayer} is pointing to the left.
     *
     * @return {@link #LEFT}
     */
    static byte getLEFT() {
        return LEFT;
    }
    
    /**
     * Returns the max number of PokemonData a Player can have at one time.
     *
     * @return {@link #MAX_POKEMON}
     */
    public static int getMaxPokemon() {
        return MAX_POKEMON;
    }
    
    float getStateTimer() {
        return stateTimer;
    }
    
    void setStateTimer(float stateTimer) {
        this.stateTimer = stateTimer;
    }
    
    /**
     * Returns the {@link Rectangle} instance representing the collision box of the {@code AbstractPlayer} to detect future collisions and special actions.
     *
     * @return {@link #currentCollisionRectangle}
     */
    Rectangle getCurrentCollisionRectangle() {
        return currentCollisionRectangle;
    }
    
    /**
     * Sets the {@link #currentCollisionRectangle} to a new {@link Rectangle} instance.
     *
     * @param currentCollisionRectangle New {@link Rectangle} instance to be represented as the collision box of the {@code AbstractPlayer}.
     */
    void setCurrentCollisionRectangle(Rectangle currentCollisionRectangle) {
        this.currentCollisionRectangle = currentCollisionRectangle;
    }
    
    MapCreator getMapCreator() {
        return mapCreator;
    }
    
    public void setMapCreator(MapCreator mapCreator) {
        this.mapCreator = mapCreator;
    }
    
    PlayerActions getPreviousState() {
        return previousState;
    }
    
    void setPreviousState(PlayerActions previousState) {
        this.previousState = previousState;
    }
    
    boolean isRestrictedMovement() {
        return restrictedMovement;
    }
    
    void setRestrictedMovement(boolean restrictedMovement) {
        this.restrictedMovement = restrictedMovement;
    }
    
    byte getDirection() {
        return direction;
    }
    
    void setDirection(byte direction) {
        this.direction = direction;
    }
    
    boolean isFutureCollision() {
        return futureCollision;
    }
    
    void setFutureCollision(boolean futureCollision) {
        this.futureCollision = futureCollision;
    }
    
    public float getPositionX() {
        return positionX;
    }
    
    void setPositionX(float positionX) {
        this.positionX = positionX;
    }
    
    public float getPositionY() {
        return positionY;
    }
    
    void setPositionY(float positionY) {
        this.positionY = positionY;
    }
    
    boolean isMoving() {
        return moving;
    }
    
    void setMoving(boolean moving) {
        this.moving = moving;
    }
    
    boolean isAligned() {
        return aligned;
    }
    
    void setAligned(boolean aligned) {
        this.aligned = aligned;
    }
    
    /**
     * Method for checking if something is to be changed with an {@code AbstractPlayer} each frame.
     *
     * @param deltaTime Amount of time between each frame.
     */
    abstract void update(float deltaTime);
    
    /**
     * Method that draws out the {@code AbstractPlayer} every frame.
     *
     * @param batch       Drawing tool.
     * @param parentAlpha Alpha value to add transparency to all children.
     */
    abstract public void draw(Batch batch, float parentAlpha);
    
    /**
     * Returns the Player's name.
     *
     * @return {@link #name}
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the {@link #name}.
     *
     * @param name {@link #name}
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Sets a specific index of the {@link #currentPokemon} instance.
     *
     * @param currentPokemon {@link #currentPokemon}
     * @param Index          {@link Integer} representing the index of the PokemonData slot to set or replace.
     */
    public void setCurrentPokemon(Pokemon currentPokemon, int Index) {
        this.currentPokemon[Index] = currentPokemon;
    }
    
    /**
     * Returns the Player's current PokemonData.
     *
     * @return {@link #currentPokemon}
     */
    public Pokemon[] getCurrentPokemon() {
        return currentPokemon;
    }
    
    /**
     * Sets the {@link #currentPokemon} (All slots).
     *
     * @param currentPokemon {@link #currentPokemon}
     */
    public void setCurrentPokemon(Pokemon[] currentPokemon) {
        this.currentPokemon = currentPokemon;
    }
}