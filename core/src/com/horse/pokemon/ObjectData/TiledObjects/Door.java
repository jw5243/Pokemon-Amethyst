package com.horse.pokemon.ObjectData.TiledObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.horse.pokemon.GraphicsEngine.MapEngine.MapCreator;
import com.horse.pokemon.GraphicsEngine.MapEngine.MultiTiledMap;
import com.horse.pokemon.GraphicsEngine.ScreenEngine.MainGameScreen;

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
            for(MultiTiledMap tiledMap : screen.getMaps()) {
                tiledMap.dispose();
            }
            MultiTiledMap[] tiledMaps = screen.getMapLoader().loadAllMaps(new String[] {fileName}, new int[] {0}, new int[] {0});
            screen.setMaps(tiledMaps);
            MapCreator mapCreator = new MapCreator(screen, screen.getMaps());
            screen.getUser().resetPosition(mapCreator, true);
        }
    }
}