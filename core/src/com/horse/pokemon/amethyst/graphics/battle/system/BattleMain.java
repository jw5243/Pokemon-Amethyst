package com.horse.pokemon.amethyst.graphics.battle.system;

import com.horse.pokemon.amethyst.data.pokemon.Pokemon;
import com.horse.pokemon.amethyst.graphics.dialog.Dialog;

public class BattleMain {
    private Dialog dialog;
    private float  timer;
    
    public BattleMain(Dialog dialog) {
        setDialog(dialog);
        setTimer(0f);
    }
    
    public void render(float delta, Pokemon[] userPokemon, Pokemon[] enemyPokemon) {
        setTimer(getTimer() + delta);
        
        getDialog().setUnparsedTextToWrite("What would you like to do?");
        getDialog().setVisible(true);
        
        getDialog().getStage().act(delta);
        getDialog().getStage().draw();
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
