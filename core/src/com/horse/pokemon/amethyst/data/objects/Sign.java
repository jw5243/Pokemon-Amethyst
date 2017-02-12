package com.horse.pokemon.amethyst.data.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

public class Sign extends CollidableTileObject {
    public Sign(Rectangle bounds) {
        super(bounds);
    }
    
    @Override
    public void onCollide() {
        Gdx.app.log("Sign", "Collision");
    }
}
