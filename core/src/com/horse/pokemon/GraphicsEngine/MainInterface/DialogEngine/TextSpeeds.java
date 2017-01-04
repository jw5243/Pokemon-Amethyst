package com.horse.pokemon.GraphicsEngine.MainInterface.DialogEngine;

public enum TextSpeeds {
    SLOW(0.1), NORMAL(0.08), FAST(0.05);
    
    private final double speed;
    
    TextSpeeds(double speed) {
        this.speed = speed;
    }
    
    public double getSpeed() {
        return speed;
    }
}