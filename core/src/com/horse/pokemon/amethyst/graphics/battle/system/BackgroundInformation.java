package com.horse.pokemon.amethyst.graphics.battle.system;

import com.badlogic.gdx.utils.Disposable;

public enum BackgroundInformation implements Disposable {
    CAVE("Battlebacks/battlebgCave.png", "Battlebacks/enemybaseCave.png", "Battlebacks/playerbaseCave.png"),
    CAVE_DARK("Battlebacks/battlebgCaveDark.png", "Battlebacks/enemybaseCaveDark.png",
              "Battlebacks/playerbaseCaveDark.png"
    ), CAVE_DARKER("Battlebacks/battlebgCaveDarker.png", "Battlebacks/enemybaseCaveDarker.png",
                   "Battlebacks/playerbaseCaveDarker.png"
    ), CHAMPION("Battlebacks/battlebgChampion.png", "Battlebacks/enemybaseChampion.png",
                "Battlebacks/playerbaseChampion.png"
    );
    
    private BackgroundData backgroundData;
    
    BackgroundInformation(String background, String enemyBase, String playerBase) {
        setBackgroundData(new BackgroundData(background, enemyBase, playerBase));
    }
    
    public BackgroundData getBackgroundData() {
        return backgroundData;
    }
    
    public void setBackgroundData(BackgroundData backgroundData) {
        this.backgroundData = backgroundData;
    }
    
    @Override
    public String toString() {
        return backgroundData.toString();
    }
    
    @Override
    public void dispose() {
        getBackgroundData().dispose();
    }
}
