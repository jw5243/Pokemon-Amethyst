package com.horse.pokemon.amethyst.data.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

public class Water extends CollidableTileObject {
    public Water(Rectangle bounds) {
        super(bounds);
    }
    
    @Override
    public void onCollide() {
        Gdx.app.log("Water", "Collision");
    }
}