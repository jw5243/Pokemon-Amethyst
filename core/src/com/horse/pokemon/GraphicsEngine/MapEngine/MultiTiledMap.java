package com.horse.pokemon.GraphicsEngine.MapEngine;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.horse.pokemon.Engine;

public class MultiTiledMap extends TiledMap {
    private int offsetX;
    private int offsetY;
    private int width;
    private int height;
    
    public MultiTiledMap() {
        this(0, 0);
    }
    
    public MultiTiledMap(int offsetX, int offsetY) {
        super();
        setOffsetX(offsetX);
        setOffsetY(offsetY);
    }
    
    public int getWidth() {
        return width;
    }
    
    public void setWidth(int width) {
        this.width = width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public void setHeight(int height) {
        this.height = height;
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
    
    public int[] getConnectionOffset() {
        int[] offset = new int[2];
        for(MapObject object : getLayers().get("Collisions").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject)(object)).getRectangle();
            rectangle = new Rectangle(rectangle.getX() / Engine.getTileSize(), rectangle.getY() / Engine.getTileSize(),
                                      rectangle.getWidth() / Engine.getTileSize(),
                                      rectangle.getHeight() / Engine.getTileSize()
            );
            if(object.getProperties().get("type").toString().equalsIgnoreCase("Connection")) {
                offset[0] = (int)(rectangle.getX());
                offset[1] = (int)(rectangle.getY());
            }
        }
        return offset;
    }
    
    public String getConnectingMap() {
        for(MapObject object : getLayers().get("Collisions").getObjects().getByType(RectangleMapObject.class)) {
            if(object.getProperties().get("type").toString().equalsIgnoreCase("Connection")) {
                return object.getProperties().get("Filename").toString();
            }
        }
        return null;
    }
}
