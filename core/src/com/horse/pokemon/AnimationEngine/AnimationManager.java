package com.horse.pokemon.AnimationEngine;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class AnimationManager {
    public static Animation<TextureRegion> getAnimation(Texture textureSheet, int frames, float frameDuration,
                                                        int[] xPositions, int[] yPositions, int frameWidth,
                                                        int frameHeight) {
        Array<TextureRegion> animationFrames = new Array<>(true, frames, TextureRegion.class);
        
        for(int frameIndex = 0; frameIndex < frames; frameIndex++) {
            animationFrames
                    .add(new TextureRegion(textureSheet, xPositions[frameIndex], yPositions[frameIndex], frameWidth,
                                           frameHeight
                    ));
        }
        
        return new Animation<>(frameDuration, animationFrames);
    }
    
    public static Animation<TextureRegion> getAnimation(Texture textureSheet, int frames, float frameDuration,
                                                        int[] xPositions, int yPosition, int frameWidth,
                                                        int frameHeight) {
        Array<TextureRegion> animationFrames = new Array<>(true, frames, TextureRegion.class);
        
        for(int frameIndex = 0; frameIndex < frames; frameIndex++) {
            animationFrames
                    .add(new TextureRegion(textureSheet, xPositions[frameIndex], yPosition, frameWidth, frameHeight));
        }
        
        return new Animation<>(frameDuration, animationFrames);
    }
    
    public static Animation<TextureRegion> getAnimation(Texture textureSheet, int frames, float frameDuration,
                                                        int xPosition, int[] yPositions, int frameWidth,
                                                        int frameHeight) {
        Array<TextureRegion> animationFrames = new Array<>(true, frames, TextureRegion.class);
        
        for(int frameIndex = 0; frameIndex < frames; frameIndex++) {
            animationFrames
                    .add(new TextureRegion(textureSheet, xPosition, yPositions[frameIndex], frameWidth, frameHeight));
        }
        
        return new Animation<>(frameDuration, animationFrames);
    }
}
