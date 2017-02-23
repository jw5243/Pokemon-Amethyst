package com.horse.pokemon.amethyst.graphics.battle.system;

import com.badlogic.gdx.Input;
import com.horse.pokemon.amethyst.data.pokemon.Pokemon;
import com.horse.pokemon.amethyst.graphics.dialog.Dialog;

public class BattleMain {
    private Dialog  dialog;
    private float   timer;
    private boolean firstIteration;
    
    public BattleMain(Dialog dialog) {
        setDialog(dialog);
        setTimer(0f);
        setFirstIteration(true);
    }
    
    public boolean isFirstIteration() {
        return firstIteration;
    }
    
    public void setFirstIteration(boolean firstIteration) {
        this.firstIteration = firstIteration;
    }
    
    public void render(float delta, Pokemon[] userPokemon, Pokemon[] enemyPokemon) {
        setTimer(getTimer() + delta);
    
        if(isFirstIteration()) {
            Dialog.addTextAction(getDialog(), "What would you like to do?", Input.Keys.X);
        }
        
        getDialog().getStage().act(delta);
        getDialog().getStage().draw();
    
        setFirstIteration(false);
    }
    
    public Dialog getDialog() {
        return dialog;
    }
    
    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }
    
    public float getTimer() {
        return timer;
    }
    
    public void setTimer(float timer) {
        this.timer = timer;
    }
}
