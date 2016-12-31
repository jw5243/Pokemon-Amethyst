package com.horse.pokemon.GraphicsEngine.MainInterface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class HandleInput {
    private Action pressedUp;
    private Action pressedDown;
    private Action pressedRight;
    private Action pressedLeft;
    private Action pressedNone;
    
    public HandleInput() {
        this((float deltaTime) -> {
        }, (float deltaTime) -> {
        }, (float deltaTime) -> {
        }, (float deltaTime) -> {
        });
    }
    
    public HandleInput(Action pressedUp, Action pressedDown, Action pressedRight, Action pressedLeft) {
        this(pressedUp, pressedDown, pressedRight, pressedLeft, (float deltaTime) -> {
        });
    }
    
    public HandleInput(Action pressedUp, Action pressedDown, Action pressedRight, Action pressedLeft,
                       Action pressedNone) {
        setFunctionality("pressedUp", pressedUp);
        setFunctionality("pressedDown", pressedDown);
        setFunctionality("pressedRight", pressedRight);
        setFunctionality("pressedLeft", pressedLeft);
        setFunctionality("pressedNone", pressedNone);
    }
    
    public void setFunctionality(String actionName, Action function) {
        try {
            this.getClass().getDeclaredField(actionName).set(this, function);
        } catch(IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
    
    public void update(float deltaTime) {
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            pressedUp.doAction(deltaTime);
        } else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            pressedDown.doAction(deltaTime);
        } else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            pressedRight.doAction(deltaTime);
        } else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            pressedLeft.doAction(deltaTime);
        } else {
            pressedNone.doAction(deltaTime);
        }
    }
}