package com.horse.pokemon.amethyst.data.characters;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.horse.pokemon.amethyst.data.objects.Barrier;
import com.horse.pokemon.amethyst.data.objects.CollidableTileObject;
import com.horse.pokemon.amethyst.data.objects.Door;
import com.horse.pokemon.amethyst.data.objects.Water;
import com.horse.pokemon.amethyst.data.pokemon.PartyPokemon;
import com.horse.pokemon.amethyst.graphics.background.system.MapCreator;

/**
 * The {@code BasePlayer} class is to be extended as a {@code Pokemon} player.
 *
 * @see Actor
 * @see User
 * @see NPC
 */
public abstract class BasePlayer extends Actor {
    /**
     * The {@code byte} representing the times when the {@code BasePlayer} is pointing upwards.
     */
    private static final byte UP    = 0x01;
    private static final byte DOWN  = 0x02;
    private static final byte RIGHT = 0x03;
    private static final byte LEFT  = 0x04;
    
    private static final byte IDLE          = 0x11;
    private static final byte WALKING       = 0x12;
    private static final byte RUNNING       = 0x13;
    private static final byte BIKING        = 0x14;
    private static final byte SWIMMING      = 0x15;
    private static final byte USING_HM_MOVE = 0x16;
    private static final byte FISHING       = 0x17;
    private static final byte IN_BATTLE     = 0x18;
    
    private static final byte IDLE_SPEED          = 0;
    private static final byte WALKING_SPEED       = 1;
    private static final byte RUNNING_SPEED       = 2;
    private static final byte BIKING_SPEED        = 3;
    private static final byte SWIMMING_SPEED      = 1;
    private static final byte USING_HM_MOVE_SPEED = 0;
    private static final byte FISHING_SPEED       = 0;
    private static final byte IN_BATTLE_SPEED     = 0;
    
    private static final byte MAX_POKEMON_PER_PARTY = 0x0A;
    
    private static final byte IS_MOVING              = 0b0000_0001;
    private static final byte IS_ALIGNED             = 0b0000_0010;
    private static final byte IS_FUTURE_COLLISION    = 0b0000_0100;
    private static final byte IS_RESTRICTED_MOVEMENT = 0b0000_1000;
    private static final byte DEFAULT_FLAGS          = 0b0000_0000;
    
    private static final byte IS_MOVING_UP    = 0b0000_0001;
    private static final byte IS_MOVING_DOWN  = 0b0000_0010;
    private static final byte IS_MOVING_RIGHT = 0b0000_0100;
    private static final byte IS_MOVING_LEFT  = 0b000_1000;
    private        byte         flags;
    private        byte         currentDirection;
    private        byte         previousState;
    private        byte         movementFlag;
    private        float        animationTimer;
    private        PartyPokemon partyPokemon;
    private        Rectangle    currentCollisionRectangle;
    
    static byte getIsMovingUp() {
        return IS_MOVING_UP;
    }
    
    static byte getIsMovingDown() {
        return IS_MOVING_DOWN;
    }
    
    static byte getIsMovingRight() {
        return IS_MOVING_RIGHT;
    }
    
    static byte getIsMovingLeft() {
        return IS_MOVING_LEFT;
    }
    
    public static byte getSpeed(final byte currentState) {
        return (currentState == getIDLE()) ? getIdleSpeed() : (currentState == getWALKING()) ? getWalkingSpeed() :
                                                              (currentState == getRUNNING()) ? getRunningSpeed() :
                                                              (currentState == getBIKING()) ? getBikingSpeed() :
                                                              (currentState == getSWIMMING()) ? getSwimmingSpeed() :
                                                              (currentState == getUsingHmMove()) ?
                                                              getUsingHmMoveSpeed() :
                                                              (currentState == getFISHING()) ? getFishingSpeed() :
                                                              getInBattleSpeed();
    }
    
    public static byte getIdleSpeed() {
        return IDLE_SPEED;
    }
    
    public static byte getWalkingSpeed() {
        return WALKING_SPEED;
    }
    
    public static byte getRunningSpeed() {
        return RUNNING_SPEED;
    }
    
    public static byte getBikingSpeed() {
        return BIKING_SPEED;
    }
    
    public static byte getSwimmingSpeed() {
        return SWIMMING_SPEED;
    }
    
    public static byte getUsingHmMoveSpeed() {
        return USING_HM_MOVE_SPEED;
    }
    
    public static byte getFishingSpeed() {
        return FISHING_SPEED;
    }
    
    public static byte getInBattleSpeed() {
        return IN_BATTLE_SPEED;
    }
    
    public static byte getDefaultFlags() {
        return DEFAULT_FLAGS;
    }
    
    public static byte getUP() {
        return UP;
    }
    
    public static byte getDOWN() {
        return DOWN;
    }
    
    public static byte getRIGHT() {
        return RIGHT;
    }
    
    public static byte getLEFT() {
        return LEFT;
    }
    
    public static byte getIDLE() {
        return IDLE;
    }
    
    public static byte getWALKING() {
        return WALKING;
    }
    
    public static byte getRUNNING() {
        return RUNNING;
    }
    
    public static byte getBIKING() {
        return BIKING;
    }
    
    public static byte getSWIMMING() {
        return SWIMMING;
    }
    
    public static byte getUsingHmMove() {
        return USING_HM_MOVE;
    }
    
    public static byte getFISHING() {
        return FISHING;
    }
    
    public static byte getInBattle() {
        return IN_BATTLE;
    }
    
    public static byte getMaxPokemonPerParty() {
        return MAX_POKEMON_PER_PARTY;
    }
    
    public static byte getIsMoving() {
        return IS_MOVING;
    }
    
    public static byte getIsAligned() {
        return IS_ALIGNED;
    }
    
    public static byte getIsFutureCollision() {
        return IS_FUTURE_COLLISION;
    }
    
    public static byte getIsRestrictedMovement() {
        return IS_RESTRICTED_MOVEMENT;
    }
    
    /**
     * Returns a {@link Rectangle} instance representing a future position of the {@code User} or the position of a {@link CollidableTileObject}.
     *
     * @param offsetX X-Position difference between the new x-position and the {@code User} x-position.
     * @param offsetY Y-Position difference between the new y-position and the {@code User} y-position.
     *
     * @return {@link Rectangle} instance of the future position of the {@code User} or the position of a {@link CollidableTileObject}.
     */
    Rectangle getFutureRectangle(final float offsetX, final float offsetY) {
        Rectangle futureRectangle =
            new Rectangle(getCurrentCollisionRectangle()); //Get the rectangle instance of the collision box of the User.
        futureRectangle.setX(
            futureRectangle.getX() + offsetX); //Add the offset onto the x-position of the collision box of the User.
        futureRectangle.setY(
            futureRectangle.getY() + offsetY); //Add the offset onto the y-position of the collision box of the User.
        return futureRectangle; //Return the new rectangle instance representing the future position.
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
    boolean isColliding(final Rectangle rectangle, final boolean activateCollisionMethod) {
        if(this instanceof User) {
            for(NPCPositionInformation npc : MapCreator.getNpcPositionInformationArrayList()) {
                if(npc.getCollisionRectangle().overlaps(rectangle)) {
                    return true;
                }
            }
        } else {
            if(MapCreator.getUser().getCurrentCollisionRectangle().overlaps(rectangle)) {
                return true;
            }
        }
        
        for(CollidableTileObject collidableTileObject : MapCreator
                                                            .getCollidableTileObjects()) { //Iterate through all the objects stored in the current map.
            rectangle.setSize(rectangle.getWidth() / 2, rectangle.getHeight() / 2);
            if(collidableTileObject.isColliding(rectangle)) {
                if(activateCollisionMethod) {
                    if(collidableTileObject instanceof Door) {
                        MapCreator.getScreen().setDoorToOpen((Door)(collidableTileObject));
                        collidableTileObject.onCollide();
                        return false;
                    }
                    collidableTileObject.onCollide();
                }
                if(this instanceof User) {
                    final User user = (User)(this);
                    return !(user.isSwimming() && collidableTileObject instanceof Water);
                }
            }
        }
        return false;
    }
    
    byte getMovementFlag() {
        return movementFlag;
    }
    
    void setMovementFlag(byte movementFlag) {
        this.movementFlag = movementFlag;
    }
    
    abstract public byte findCurrentState();
    
    public Rectangle getCurrentCollisionRectangle() {
        return currentCollisionRectangle;
    }
    
    public void setCurrentCollisionRectangle(Rectangle currentCollisionRectangle) {
        this.currentCollisionRectangle = currentCollisionRectangle;
    }
    
    public byte getPreviousState() {
        return previousState;
    }
    
    public void setPreviousState(byte previousState) {
        this.previousState &= previousState;
    }
    
    public byte getCurrentDirection() {
        return currentDirection;
    }
    
    public void setCurrentDirection(byte currentDirection) {
        this.currentDirection = currentDirection;
    }
    
    public float getAnimationTimer() {
        return animationTimer;
    }
    
    public void setAnimationTimer(float animationTimer) {
        this.animationTimer = animationTimer;
    }
    
    public PartyPokemon getPartyPokemon() {
        return partyPokemon;
    }
    
    public void setPartyPokemon(PartyPokemon partyPokemon) {
        this.partyPokemon = partyPokemon;
    }
    
    public byte getFlags() {
        return flags;
    }
    
    public void setFlags(byte flags) {
        this.flags = flags;
    }
    
    public void raiseFlag(final byte flagToAdd) {
        this.flags |= flagToAdd;
    }
    
    public void resetFlags() {
        setFlags(getDefaultFlags());
    }
    
    public void removeFlag(final byte flagToRemove) {
        this.flags &= ~flagToRemove;
    }
    
    public boolean isFlag(final byte flagToCheck) {
        return (getFlags() & flagToCheck) == flagToCheck;
    }
}
