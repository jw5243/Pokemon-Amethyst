package com.horse.pokemon.ObjectData.TiledObjects;

import com.badlogic.gdx.math.Rectangle;

import java.util.Objects;

public abstract class TileObject {
    private Rectangle bounds;
    
    public TileObject(Rectangle bounds) {
        setBounds(bounds);
    }
    
    public abstract void onCollide();
    
    public boolean isColliding(Rectangle rectangle) {
        return rectangle.overlaps(getBounds());
    }
    
    public Rectangle getBounds() {
        return bounds;
    }
    
    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }
    
    @Override
    public int hashCode() {
        return getBounds().hashCode();
    }
    
    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        TileObject that = (TileObject)o;
        return Objects.equals(getBounds(), that.getBounds());
    }
    
    @Override
    public String toString() {
        return String.format("{X Position = %s, Y Position = %s, Width = %s, Height = %s}", getBounds().getX(),
                             getBounds().getY(), getBounds().getWidth(), getBounds().getHeight()
        );
    }
}