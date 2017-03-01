package com.horse.pokemon.amethyst.data.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.horse.pokemon.amethyst.graphics.MainGameScreen;
import com.horse.pokemon.amethyst.graphics.background.system.MapCreator;
import com.horse.pokemon.amethyst.graphics.background.system.MultiTiledMap;

public class Door extends CollidableTileObject {
    /**
     * The {@code int} representing the amount of iterations over {@link MainGameScreen#render(float)} before the {@link Door} starts animating.
     */
    private static final int FRAMES_TO_ANIMATE_DOOR = 6;
    
    /**
     * The {@code int} representing the beginning frame of {@link Door} when animating.
     */
    private static final int START_DOOR_FRAME_COUNT = 0;
    
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
    
    public static int getStartDoorFrameCount() {
        return START_DOOR_FRAME_COUNT;
    }
    
    public static int getFramesToAnimateDoor() {
        return FRAMES_TO_ANIMATE_DOOR;
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
            MultiTiledMap[] tiledMaps = screen.getMapLoader().loadAllMaps(fileName);
            screen.setMaps(tiledMaps);
            MapCreator.resetTiledObjects(screen, screen.getMaps());
            MapCreator.getUser().resetPosition();
        }
    }
}