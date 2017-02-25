package com.horse.pokemon.amethyst.data.characters;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.horse.pokemon.amethyst.data.pokemon.PartyPokemon;
import com.horse.pokemon.amethyst.graphics.background.system.MapCreator;

/**
 * The base class for all new characters in the game.
 *
 * @author Horse
 * @version 1.0
 * @since 1.0
 */
public abstract class BasePlayer extends Actor {
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
    
    private static MapCreator mapCreator;
    
    private byte         flags;
    private byte         currentDirection;
    private byte         previousState;
    private float        animationTimer;
    private PartyPokemon partyPokemon;
    private Rectangle    currentCollisionRectangle;
    
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
    
    public static MapCreator getMapCreator() {
        return mapCreator;
    }
    
    public static void setMapCreator(MapCreator mapCreator) {
        BasePlayer.mapCreator = mapCreator;
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
