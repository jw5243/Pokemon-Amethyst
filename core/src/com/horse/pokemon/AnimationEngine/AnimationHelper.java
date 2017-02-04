package com.horse.pokemon.AnimationEngine;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Class that makes animations easier and more efficient.  A three-dimensional
 * array is used to store all the information for one spritesheet.  The first
 * part of the array is for distinguishing the type of action the sprite is
 * performing.  The second part of the array is each frame of the specific
 * action.  The last part of the array is the actual information of the
 * positioning of the frame, which is made up of primitive ints.
 *
 * @see java.lang.annotation.Annotation
 * @see ArrayList
 * @see TextureRegion
 * @see Animation
 * @see Class
 */
public class AnimationHelper {
    private int[][][]                                      animationInformation;
    private ArrayList<ArrayList<Animation<TextureRegion>>> animation;
    private Annotation                                     annotation;
    private String[]                                       actionNames;
    
    public AnimationHelper() {
        this(new int[][][] {});
    }
    
    public AnimationHelper(int[][][] animationInformation) {
        this.animationInformation = animationInformation;
    }
    
    private boolean checkClassForAnimation(Class<?> c) {
        return AnimationInterface.class.isAssignableFrom(c) && c.isAnnotationPresent(AnimationInitializer.class);
    }
    
    public void setUpAnimation(Object object)
    throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if(checkClassForAnimation(object.getClass())) {
            annotation = object.getClass().getAnnotation(AnimationInitializer.class);
            actionNames = getAnnotationValue();
            if(object instanceof Sprite) {
                Sprite sprite = (Sprite)(object);
                for(int index = 0; index < actionNames.length; index++) {
                    //animation.add(new ArrayList<>().add(new Animation<>(0.1f, new Array<TextureRegion>())));
                }
            }
        }
    }
    
    private String[] getAnnotationValue()
    throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if(annotation != null) {
            return (String[])(annotation.annotationType().getMethod(AnimationInterface.initializeAnimationName)
                                        .invoke(annotation));
        } else {
            return new String[] {""};
        }
    }
}