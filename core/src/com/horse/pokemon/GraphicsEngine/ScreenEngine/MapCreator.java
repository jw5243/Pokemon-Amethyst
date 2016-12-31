package com.horse.pokemon.GraphicsEngine.ScreenEngine;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.horse.pokemon.ObjectData.TiledObjects.Barrier;
import com.horse.pokemon.ObjectData.TiledObjects.Door;
import com.horse.pokemon.ObjectData.TiledObjects.Mailbox;
import com.horse.pokemon.ObjectData.TiledObjects.Sign;
import com.horse.pokemon.ObjectData.TiledObjects.TileObject;
import com.horse.pokemon.ObjectData.TiledObjects.Water;

import java.util.ArrayList;
import java.util.Objects;

public class MapCreator {
    private ArrayList<TileObject> tileObjects   = new ArrayList<>();
    private Vector2               startPosition = new Vector2();
    private Array<Rectangle>      connections   = new Array<>();
    
    public MapCreator(MainGameScreen screen, TiledMap map) {
        for(MapObject object : map.getLayers().get("Collisions").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject)(object)).getRectangle();
            if(object.getProperties().get("type") instanceof String) {
                if(object.getProperties().get("type").toString().equalsIgnoreCase("Door")) {
                    if(object.getProperties().containsKey("Filename")) {
                        String fileName = object.getProperties().get("Filename").toString();
                        getTileObjects().add(new Door(screen, rectangle, fileName));
                    } else {
                        getTileObjects().add(new Door(rectangle));
                    }
                } else if(object.getProperties().get("type").toString().equalsIgnoreCase("Sign")) {
                    getTileObjects().add(new Sign(rectangle));
                } else if(object.getProperties().get("type").toString().equalsIgnoreCase("Water")) {
                    getTileObjects().add(new Water(rectangle));
                } else if(object.getProperties().get("type").toString().equalsIgnoreCase("Barrier")) {
                    getTileObjects().add(new Barrier(rectangle));
                } else if(object.getProperties().get("type").toString().equalsIgnoreCase("Mailbox")) {
                    getTileObjects().add(new Mailbox(rectangle));
                } else if(object.getProperties().get("type").toString().equalsIgnoreCase("Bed")) {
                    
                } else if(object.getProperties().get("type").toString().equalsIgnoreCase("Stairs")) {
                    
                } else if(object.getProperties().get("type").toString().equalsIgnoreCase("Start Position")) {
                    setStartPosition(new Vector2(rectangle.getX(), rectangle.getY()));
                } else if(object.getProperties().get("type").toString().equalsIgnoreCase("Connection")) {
                    getConnections().add(rectangle);
                }
            }
        }
    }
    
    public ArrayList<TileObject> getTileObjects() {
        return tileObjects;
    }
    
    public void setTileObjects(ArrayList<TileObject> tileObjects) {
        this.tileObjects = tileObjects;
    }
    
    public Vector2 getStartPosition() {
        return startPosition;
    }
    
    public void setStartPosition(Vector2 startPosition) {
        this.startPosition = startPosition;
    }
    
    public Array<Rectangle> getConnections() {
        return connections;
    }
    
    public void setConnections(Array<Rectangle> connections) {
        this.connections = connections;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(getTileObjects(), getStartPosition(), getConnections());
    }
    
    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        MapCreator that = (MapCreator)o;
        return Objects.equals(getTileObjects(), that.getTileObjects()) &&
               Objects.equals(getStartPosition(), that.getStartPosition()) &&
               Objects.equals(getConnections(), that.getConnections());
    }
}