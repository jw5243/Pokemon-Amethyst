package com.horse.pokemon.AnimationEngine;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Class for getting an {@link Animation} instance of given pieces of information.
 *
 * @see Animation
 * @see TextureRegion
 * @see Texture
 * @see Array
 */
public class AnimationManager {
    /**
     * Method for organizing an {@link Animation} object with the given paramters when the action strip has altering x-positions and y-positions.
     *
     * @param textureSheet  Texture representing all the frames for animation.
     * @param frames        Amount of animation pictures for a specific action.
     * @param frameDuration The time between frames in seconds.
     * @param xPositions    The left-most position of each frame in order.  Should be same length as parameter frames.
     * @param yPositions    The upper-most position of each frame in order.  Should be same length as parameter frames.
     * @param frameWidth    The standard width of a single frame.
     * @param frameHeight   The standard height of a single frame.
     *
     * @return {@link Animation} instance representing an object's specific action for graphical use.
     */
    public static Animation<TextureRegion> getAnimation(Texture textureSheet, int frames, float frameDuration, int[] xPositions, int[] yPositions, int frameWidth, int frameHeight) {
        if(frames != xPositions.length) {
            xPositions = new int[frames];
        }
        if(frames != yPositions.length) {
            yPositions = new int[frames];
        }
        
        Array<TextureRegion> animationFrames = new Array<>(true, frames, TextureRegion.class);
        
        for(int frameIndex = 0; frameIndex < frames; frameIndex++) {
            animationFrames.add(new TextureRegion(textureSheet, xPositions[frameIndex], yPositions[frameIndex], frameWidth, frameHeight));
        }
        
        return new Animation<>(frameDuration, animationFrames);
    }
    
    /**
     * Method for organizing an {@link Animation} object with the given paramters when the action strip is all horizontal with the same top-most y-position.
     *
     * @param textureSheet  Texture representing all the frames for animation.
     * @param frames        Amount of animation pictures for a specific action.
     * @param frameDuration The time between frames in seconds.
     * @param xPositions    The left-most position of each frame in order.  Should be same length as parameter frames.
     * @param yPosition     The upper-most position of all frames.  Should be same length as parameter frames.
     * @param frameWidth    The standard width of a single frame.
     * @param frameHeight   The standard height of a single frame.
     *
     * @return {@link Animation} instance representing an object's specific action for graphical use.
     */
    public static Animation<TextureRegion> getAnimation(Texture textureSheet, int frames, float frameDuration, int[] xPositions, int yPosition, int frameWidth, int frameHeight) {
        if(frames != xPositions.length) {
            xPositions = new int[frames];
        }
        
        Array<TextureRegion> animationFrames = new Array<>(true, frames, TextureRegion.class);
        
        for(int frameIndex = 0; frameIndex < frames; frameIndex++) {
            animationFrames.add(new TextureRegion(textureSheet, xPositions[frameIndex], yPosition, frameWidth, frameHeight));
        }
        
        return new Animation<>(frameDuration, animationFrames);
    }
    
    /**
     * Method for organizing an {@link Animation} object with the given paramters when the action strip is all vertical with the same left-most x-position.
     *
     * @param textureSheet  Texture representing all the frames for animation.
     * @param frames        Amount of animation pictures for a specific action.
     * @param frameDuration The time between frames in seconds.
     * @param xPosition     The left-most position of all frames.  Should be same length as parameter frames.
     * @param yPositions    The upper-most position of each frame in order.  Should be same length as parameter frames.
     * @param frameWidth    The standard width of a single frame.
     * @param frameHeight   The standard height of a single frame.
     *
     * @return {@link Animation} instance representing an object's specific action for graphical use.
     */
    public static Animation<TextureRegion> getAnimation(Texture textureSheet, int frames, float frameDuration, int xPosition, int[] yPositions, int frameWidth, int frameHeight) {
        if(frames != yPositions.length) {
            yPositions = new int[frames];
        }
        
        Array<TextureRegion> animationFrames = new Array<>(true, frames, TextureRegion.class);
        
        for(int frameIndex = 0; frameIndex < frames; frameIndex++) {
            animationFrames.add(new TextureRegion(textureSheet, xPosition, yPositions[frameIndex], frameWidth, frameHeight));
        }
        
        return new Animation<>(frameDuration, animationFrames);
    }
}
