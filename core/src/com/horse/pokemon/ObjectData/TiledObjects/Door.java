package com.horse.pokemon.ObjectData.TiledObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.horse.pokemon.GraphicsEngine.ScreenEngine.MainGameScreen;
import com.horse.pokemon.GraphicsEngine.ScreenEngine.MapCreator;

public class Door extends CollidableTileObject {
    private MainGameScreen screen;
    private String         fileName;
    
    public Door(Rectangle bounds) {
        super(bounds);
    }
    
    public Door(MainGameScreen screen, Rectangle bounds, String fileName) {
        super(bounds);
        this.screen = screen;
        this.fileName = fileName;
    }
    
    @Override
    public void onCollide() {
        Gdx.app.log("Door", "Collision");
    }
    
    public void switchRooms() {
        if(this.screen != null && fileName != null) {
            for(TiledMap tiledMap : screen.getMaps()) {
                tiledMap.dispose();
            }
            screen.getMaps()[0] = screen.getMapLoader().load(fileName);
            MapCreator mapCreator = new MapCreator(screen, screen.getMaps()[0]);
            screen.getUser().resetPosition(mapCreator, true);
        }
    }
}