package com.horse.pokemon.GraphicsEngine.MainInterface.DialogEngine;

public enum TextSpeeds {
    SLOW(0.08), NORMAL(0.05), FAST(0.02);
    
    private final double speed;
    
    TextSpeeds(double speed) {
        this.speed = speed;
    }
    
    public double getSpeed() {
        return speed;
    }
}