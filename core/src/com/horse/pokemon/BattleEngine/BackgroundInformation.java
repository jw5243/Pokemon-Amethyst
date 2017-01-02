package com.horse.pokemon.BattleEngine;

import com.badlogic.gdx.utils.Disposable;

public enum BackgroundInformation implements Disposable {
    CAVE("Battlebacks\\battlebgCave.png", "Battlebacks\\enemybaseCave.png", "Battlebacks\\playerbaseCave.png") {
        @Override
        public void dispose() {
            getBackgroundData().dispose();
        }
    }, CAVE_DARK("Battlebacks\\battlebgCaveDark.png", "Battlebacks\\enemybaseCaveDark.png",
                 "Battlebacks\\playerbaseCaveDark.png"
    ) {
        @Override
        public void dispose() {
            getBackgroundData().dispose();
        }
    }, CAVE_DARKER("Battlebacks\\battlebgCaveDarker.png", "Battlebacks\\enemybaseCaveDarker.png",
                   "Battlebacks\\playerbaseCaveDarker.png"
    ) {
        @Override
        public void dispose() {
            getBackgroundData().dispose();
        }
    }, CHAMPION("Battlebacks\\battlebgChampion.png", "Battlebacks\\enemybaseChampion.png",
                "Battlebacks\\playerbaseChampion.png"
    ) {
        @Override
        public void dispose() {
            getBackgroundData().dispose();
        }
    };
    
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
}
