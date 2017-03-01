package com.horse.pokemon.amethyst.graphics.background.system;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.horse.pokemon.amethyst.Engine;
import com.horse.pokemon.amethyst.data.characters.User;
import com.horse.pokemon.amethyst.data.objects.Barrier;
import com.horse.pokemon.amethyst.data.objects.CollidableTileObject;
import com.horse.pokemon.amethyst.data.objects.Door;
import com.horse.pokemon.amethyst.data.objects.Mailbox;
import com.horse.pokemon.amethyst.data.objects.Sign;
import com.horse.pokemon.amethyst.data.objects.Water;
import com.horse.pokemon.amethyst.graphics.MainGameScreen;

import java.util.ArrayList;
import java.util.HashMap;

public class MapCreator {
    private static ArrayList<Rectangle> npcPositions = new ArrayList<>(0);
    private static User user;
    private static ArrayList<CollidableTileObject> collidableTileObjects = new ArrayList<>();
    private static Vector2                         startPosition         = new Vector2();
    private static HashMap<String, Vector2>        npcStartPositions     = new HashMap<>();
    private static Array<ConnectionInformation>    connections           = new Array<>();
    private static MainGameScreen screen;
    
    public static User getUser() {
        return user;
    }
    
    public static void setUser(User user) {
        MapCreator.user = user;
    }
    
    public static ArrayList<Rectangle> getNpcPositions() {
        return npcPositions;
    }
    
    public static void setNpcPositions(ArrayList<Rectangle> npcPositions) {
        MapCreator.npcPositions = npcPositions;
    }
    
    public static HashMap<String, Vector2> getNpcStartPositions() {
        return npcStartPositions;
    }
    
    public static void setNpcStartPositions(HashMap<String, Vector2> npcStartPositions) {
        MapCreator.npcStartPositions = npcStartPositions;
    }
    
    public static MainGameScreen getScreen() {
        return screen;
    }
    
    public static void setScreen(MainGameScreen screen) {
        MapCreator.screen = screen;
    }
    
    public static void resetTiledObjects(MainGameScreen screen, MultiTiledMap[] map) {
        getCollidableTileObjects().clear();
        addTiledObjects(screen, map);
    }
    
    public static void addTiledObjects(MainGameScreen screen, MultiTiledMap[] maps) {
        setScreen(screen);
        for(int index = 0; index < maps.length; index++) {
            for(MapObject object : maps[index].getLayers().get("Collisions").getObjects()
                                              .getByType(RectangleMapObject.class)) {
                Rectangle rectangle = ((RectangleMapObject)(object)).getRectangle();
    
                rectangle.setX(rectangle.getX() + screen.getMaps()[index].getOffsetX() * Engine.getTileSize());
                rectangle.setY(rectangle.getY() + screen.getMaps()[index].getOffsetY() * Engine.getTileSize());
    
                Object mapProperty = object.getProperties().get("type");
                if(mapProperty instanceof String) {
                    String stringMapProperty = mapProperty.toString();
                    if(stringMapProperty.equalsIgnoreCase("Door")) {
                        if(object.getProperties().containsKey("Filename")) {
                            String fileName = object.getProperties().get("Filename").toString();
                            getCollidableTileObjects().add(new Door(screen, rectangle, fileName));
                        } else {
                            getCollidableTileObjects().add(new Door(rectangle));
                        }
                    } else if(stringMapProperty.equalsIgnoreCase("Sign")) {
                        getCollidableTileObjects().add(new Sign(rectangle));
                    } else if(stringMapProperty.equalsIgnoreCase("Water")) {
                        getCollidableTileObjects().add(new Water(rectangle));
                    } else if(stringMapProperty.equalsIgnoreCase("Barrier")) {
                        getCollidableTileObjects().add(new Barrier(rectangle));
                    } else if(stringMapProperty.equalsIgnoreCase("Mailbox")) {
                        getCollidableTileObjects().add(new Mailbox(rectangle));
                    } else if(stringMapProperty.equalsIgnoreCase("Grass")) {
    
                    } else if(stringMapProperty.equalsIgnoreCase("Edging")) {
    
                    } else if(stringMapProperty.equalsIgnoreCase("Bed")) {
    
                    } else if(stringMapProperty.equalsIgnoreCase("Stairs")) {
    
                    } else if(stringMapProperty.equalsIgnoreCase("Start Position")) {
                        setStartPosition(new Vector2(rectangle.getX(), rectangle.getY()));
                    } else if(stringMapProperty.contains("NPC")) {
                        getNpcStartPositions().put(object.getProperties().get("Filename").toString(),
                                                   new Vector2(rectangle.getX(), rectangle.getY())
                        );
                    } else if(stringMapProperty.equalsIgnoreCase("Connection")) {
                        getConnections().add(
                            new ConnectionInformation(rectangle, object.getProperties().get("Filename").toString()));
                    }
                }
            }
        }
    }
    
    public static ArrayList<CollidableTileObject> getCollidableTileObjects() {
        return collidableTileObjects;
    }
    
    public static void setCollidableTileObjects(ArrayList<CollidableTileObject> collidableTileObjects) {
        MapCreator.collidableTileObjects = collidableTileObjects;
    }
    
    public static Vector2 getStartPosition() {
        return startPosition;
    }
    
    public static void setStartPosition(Vector2 startPosition) {
        MapCreator.startPosition = startPosition;
    }
    
    public static Array<ConnectionInformation> getConnections() {
        return connections;
    }
    
    public static void setConnections(Array<ConnectionInformation> connections) {
        MapCreator.connections = connections;
    }
}