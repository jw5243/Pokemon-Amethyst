package com.horse.pokemon.amethyst.data.characters;

enum PlayerActions {
    IDLE(0f), WALKING(1f), RUNNING(2f), BIKING(2f), SWIMMING(1f), USING_HM_MOVE(0f), FISHING(0f), IN_BATTLE(0f);
    
    float speed;
    
    PlayerActions(float speed) {
        this.speed = speed;
    }
    
    public float getSpeed() {
        return speed;
    }
}
