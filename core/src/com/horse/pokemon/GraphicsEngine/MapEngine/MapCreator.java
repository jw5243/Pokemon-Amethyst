package com.horse.pokemon.GraphicsEngine.MapEngine;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.horse.pokemon.GraphicsEngine.ScreenEngine.MainGameScreen;
import com.horse.pokemon.ObjectData.TiledObjects.Barrier;
import com.horse.pokemon.ObjectData.TiledObjects.CollidableTileObject;
import com.horse.pokemon.ObjectData.TiledObjects.Door;
import com.horse.pokemon.ObjectData.TiledObjects.Mailbox;
import com.horse.pokemon.ObjectData.TiledObjects.Sign;
import com.horse.pokemon.ObjectData.TiledObjects.Water;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class MapCreator {
    private ArrayList<CollidableTileObject> collidableTileObjects = new ArrayList<>();
    private Vector2                         startPosition         = new Vector2();
    private HashMap<String, Vector2>        npcStartPositions     = new HashMap<>();
    private Array<Rectangle>                connections           = new Array<>();
    private MainGameScreen screen;
    
    public MapCreator(MainGameScreen screen, MultiTiledMap... maps) {
        addTiledObjects(screen, maps);
    }
    
    public HashMap<String, Vector2> getNpcStartPositions() {
        return npcStartPositions;
    }
    
    public void setNpcStartPositions(HashMap<String, Vector2> npcStartPositions) {
        this.npcStartPositions = npcStartPositions;
    }
    
    public MainGameScreen getScreen() {
        return screen;
    }
    
    public void setScreen(MainGameScreen screen) {
        this.screen = screen;
    }
    
    public void resetTiledObjects(MainGameScreen screen, MultiTiledMap... map) {
        getCollidableTileObjects().clear();
        addTiledObjects(screen, map);
        screen.getUser().setMapCreator(this);
    }
    
    public void addTiledObjects(MainGameScreen screen, MultiTiledMap... maps) {
        setScreen(screen);
        for(int index = 0; index < maps.length; index++) {
            for(MapObject object : maps[index].getLayers().get("Collisions").getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rectangle = ((RectangleMapObject)(object)).getRectangle();
                
                rectangle.setX(rectangle.getX() + screen.getMaps()[index].getOffsetX());
                rectangle.setY(rectangle.getY() + screen.getMaps()[index].getOffsetY());
                
                if(object.getProperties().get("type") instanceof String) {
                    if(object.getProperties().get("type").toString().equalsIgnoreCase("Door")) {
                        if(object.getProperties().containsKey("Filename")) {
                            String fileName = object.getProperties().get("Filename").toString();
                            getCollidableTileObjects().add(new Door(screen, rectangle, fileName));
                        } else {
                            getCollidableTileObjects().add(new Door(rectangle));
                        }
                    } else if(object.getProperties().get("type").toString().equalsIgnoreCase("Sign")) {
                        getCollidableTileObjects().add(new Sign(rectangle));
                    } else if(object.getProperties().get("type").toString().equalsIgnoreCase("Water")) {
                        getCollidableTileObjects().add(new Water(rectangle));
                    } else if(object.getProperties().get("type").toString().equalsIgnoreCase("Barrier")) {
                        getCollidableTileObjects().add(new Barrier(rectangle));
                    } else if(object.getProperties().get("type").toString().equalsIgnoreCase("Mailbox")) {
                        getCollidableTileObjects().add(new Mailbox(rectangle));
                    } else if(object.getProperties().get("type").toString().equalsIgnoreCase("Grass")) {
                        
                    } else if(object.getProperties().get("type").toString().equalsIgnoreCase("Bed")) {
                        
                    } else if(object.getProperties().get("type").toString().equalsIgnoreCase("Stairs")) {
                        
                    } else if(object.getProperties().get("type").toString().equalsIgnoreCase("Start Position")) {
                        setStartPosition(new Vector2(rectangle.getX(), rectangle.getY()));
                    } else if(object.getProperties().get("type").toString().contains("NPC")) {
                        getNpcStartPositions().put(object.getProperties().get("type").toString(), new Vector2(rectangle.getX(), rectangle.getY()));
                    } else if(object.getProperties().get("type").toString().equalsIgnoreCase("Connection")) {
                        getConnections().add(rectangle);
                    }
                }
            }
        }
    }
    
    public ArrayList<CollidableTileObject> getCollidableTileObjects() {
        return collidableTileObjects;
    }
    
    public void setCollidableTileObjects(ArrayList<CollidableTileObject> collidableTileObjects) {
        this.collidableTileObjects = collidableTileObjects;
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
        return Objects.hash(getCollidableTileObjects(), getStartPosition(), getConnections());
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
        return Objects.equals(getCollidableTileObjects(), that.getCollidableTileObjects()) && Objects.equals(getStartPosition(), that.getStartPosition()) &&
               Objects.equals(getConnections(), that.getConnections());
    }
}