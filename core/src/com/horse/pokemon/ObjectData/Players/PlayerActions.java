package com.horse.pokemon.ObjectData.Players;

enum PlayerActions {
    IDLE(0), WALKING(1), RUNNING(2), BIKING(3), SWIMMING(1), USING_HM_MOVE(0), FISHING(0), IN_BATTLE(0);
    
    int speed;
    
    PlayerActions(int speed) {
        this.speed = speed;
    }
    
    public int getSpeed() {
        return speed;
    }
}
