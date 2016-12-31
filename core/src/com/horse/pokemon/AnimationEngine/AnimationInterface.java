package com.horse.pokemon.AnimationEngine;

import java.lang.reflect.InvocationTargetException;

public interface AnimationInterface {
    String initializeAnimationName = "initializeAnimation";
    
    void initializeAnimation() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;
}
