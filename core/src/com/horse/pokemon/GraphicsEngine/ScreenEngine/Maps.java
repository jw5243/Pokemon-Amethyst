package com.horse.pokemon.GraphicsEngine.ScreenEngine;

public enum Maps {
    START_MAP("Maps\\StartMap.tmx"), START_ROOM("Maps\\StartUserRoom.tmx");
    
    
    private String tmxPath;
    
    Maps(String tmxPath) {
        setTmxPath(tmxPath);
    }
    
    public String getTmxPath() {
        return tmxPath;
    }
    
    public void setTmxPath(String tmxPath) {
        this.tmxPath = tmxPath;
    }
}
