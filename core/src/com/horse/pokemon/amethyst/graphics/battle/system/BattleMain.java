package com.horse.pokemon.amethyst.graphics.battle.system;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
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
            final SequenceAction mainSequence = new SequenceAction();
            mainSequence
                .addAction(Dialog.getTextAction(getDialog(), "What would you like to do?", Input.Keys.X, false));
            mainSequence.addAction(Dialog.getTextAction(getDialog(), "Battle | Flee | Bag", Input.Keys.X, true));
            getDialog().addAction(mainSequence);
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
