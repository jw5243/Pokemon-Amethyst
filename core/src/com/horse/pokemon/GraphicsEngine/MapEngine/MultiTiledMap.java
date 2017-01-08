package com.horse.pokemon.GraphicsEngine.MapEngine;

import com.badlogic.gdx.maps.tiled.TiledMap;

public class MultiTiledMap extends TiledMap {
    private int offsetX;
    private int offsetY;
    
    public MultiTiledMap() {
        this(0, 0);
    }
    
    public MultiTiledMap(int offsetX, int offsetY) {
        super();
        setOffsetX(offsetX);
        setOffsetY(offsetY);
    }
    
    public int getOffsetX() {
        return offsetX;
    }
    
    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }
    
    public int getOffsetY() {
        return offsetY;
    }
    
    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }
}
