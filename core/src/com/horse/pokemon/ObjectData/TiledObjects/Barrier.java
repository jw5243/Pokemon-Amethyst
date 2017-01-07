package com.horse.pokemon.ObjectData.TiledObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

public class Barrier extends CollidableTileObject {
    public Barrier(Rectangle bounds) {
        super(bounds);
    }
    
    @Override
    public void onCollide() {
        Gdx.app.log("Barrier", "Collision");
    }
}