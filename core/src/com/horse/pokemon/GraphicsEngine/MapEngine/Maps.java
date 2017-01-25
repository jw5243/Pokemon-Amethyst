package com.horse.pokemon.GraphicsEngine.MapEngine;

public enum Maps {
    TWINLEAF_TOWN("Maps\\TwinleafTown.tmx"), START_ROOM("Maps\\StartUserRoom.tmx"), ROUTE_201("Maps\\Route201.tmx");
    
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
