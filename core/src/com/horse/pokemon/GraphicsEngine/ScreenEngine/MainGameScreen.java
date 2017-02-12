package com.horse.pokemon.GraphicsEngine.ScreenEngine;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.horse.pokemon.Audio.AudioData;
import com.horse.pokemon.BattleEngine.BattleScreen;
import com.horse.pokemon.Engine;
import com.horse.pokemon.GraphicsEngine.MainInterface.DialogEngine.Dialog;
import com.horse.pokemon.GraphicsEngine.MainInterface.DialogEngine.TextSpeeds;
import com.horse.pokemon.GraphicsEngine.MapEngine.MapCreator;
import com.horse.pokemon.GraphicsEngine.MapEngine.Maps;
import com.horse.pokemon.GraphicsEngine.MapEngine.MultiTileMapRenderer;
import com.horse.pokemon.GraphicsEngine.MapEngine.MultiTiledMap;
import com.horse.pokemon.GraphicsEngine.MapEngine.MultiTmxMapLoader;
import com.horse.pokemon.ObjectData.Players.NPC;
import com.horse.pokemon.ObjectData.Players.User;
import com.horse.pokemon.ObjectData.TiledObjects.Door;
import com.horse.utility.Graphics.PokemonScreen;
import com.koloboke.collect.map.hash.HashIntObjMap;
import com.koloboke.collect.map.hash.HashIntObjMaps;

import java.util.ArrayList;

/**
 * Class containing all the graphical pieces for when the {@link User} is on the screen state.  This is the {@link Screen} that is used the most as the major actions happen on this
 * screen.
 */
public class MainGameScreen extends PokemonScreen {
    /**
     * The {@code int} representing the amount of iterations over {@link #render(float)} before the {@link Door} starts animating.
     */
    private static final int FRAMES_TO_ANIMATE_DOOR = 6;
    
    /**
     * The {@code int} representing the beginning frame of {@link Door} when animating.
     */
    private static final int START_DOOR_FRAME_COUNT = 0;
    
    /**
     * The {@link Engine} instance representing the {@link Game} that is playing.
     */
    private Engine engine;
    
    /**
     * The {@link OrthographicCamera} instance representing the sight of the {@link User}.  The {@code User} should
     * always be at the center of the screen as the {@code User} is the protagonist of the game.
     */
    private OrthographicCamera                camera;
    private Viewport                          viewport;
    private Hud                               hud;
    private MultiTmxMapLoader                 mapLoader;
    private MultiTiledMap[]                   maps;
    private MultiTileMapRenderer              renderer;
    private User                              user;
    private Stage                             stage;
    private AudioData                         sound;
    private Dialog                            dialog;
    private FPSLogger                         fpsLogger;
    private MapCreator                        mapCreator;
    private ArrayList<TiledMapTileLayer.Cell> doorsInMap;
    private HashIntObjMap<TiledMapTile>       doorTiles;
    private Door                              doorToOpen;
    private NPC                               npc;
    private int                               currentDoorFrameCount;
    
    public MainGameScreen(Engine engine) {
        setEngine(engine);
        fpsLogger = new FPSLogger();
    }
    
    public static int getStartDoorFrameCount() {
        return START_DOOR_FRAME_COUNT;
    }
    
    public static int getFramesToAnimateDoor() {
        return FRAMES_TO_ANIMATE_DOOR;
    }
    
    public NPC getNpc() {
        return npc;
    }
    
    public void setNpc(NPC npc) {
        this.npc = npc;
    }
    
    public AudioData getSound() {
        return sound;
    }
    
    public void setSound(AudioData sound) {
        this.sound = sound;
    }
    
    public Dialog getDialog() {
        return dialog;
    }
    
    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }
    
    public MultiTiledMap[] getMaps() {
        return maps;
    }
    
    public void setMaps(MultiTiledMap[] maps) {
        this.maps = maps;
    }
    
    public int getCurrentDoorFrameCount() {
        return currentDoorFrameCount;
    }
    
    public void setCurrentDoorFrameCount(int currentDoorFrameCount) {
        this.currentDoorFrameCount = currentDoorFrameCount;
    }
    
    public Door getDoorToOpen() {
        return doorToOpen;
    }
    
    public void setDoorToOpen(Door doorToOpen) {
        this.doorToOpen = doorToOpen;
    }
    
    public ArrayList<TiledMapTileLayer.Cell> getDoorsInMap() {
        return doorsInMap;
    }
    
    public void setDoorsInMap(ArrayList<TiledMapTileLayer.Cell> doorsInMap) {
        this.doorsInMap = doorsInMap;
    }
    
    public HashIntObjMap<TiledMapTile> getDoorTiles() {
        return doorTiles;
    }
    
    public void setDoorTiles(HashIntObjMap<TiledMapTile> doorTiles) {
        this.doorTiles = doorTiles;
    }
    
    public MapCreator getMapCreator() {
        return mapCreator;
    }
    
    public void setMapCreator(MapCreator mapCreator) {
        this.mapCreator = mapCreator;
    }
    
    public Stage getStage() {
        return stage;
    }
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    public OrthographicCamera getCamera() {
        return camera;
    }
    
    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }
    
    public Engine getEngine() {
        return engine;
    }
    
    public void setEngine(Engine engine) {
        this.engine = engine;
    }
    
    public MultiTmxMapLoader getMapLoader() {
        return mapLoader;
    }
    
    public void setMapLoader(MultiTmxMapLoader mapLoader) {
        this.mapLoader = mapLoader;
    }
    
    public Viewport getViewport() {
        return viewport;
    }
    
    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }
    
    @Override
    public void show() {
        setCamera(new OrthographicCamera());
        setViewport(new FitViewport(Engine.getvWidth() / Engine.getCameraZoomScale(),
                                    Engine.getvHeight() / Engine.getCameraZoomScale(), getCamera()
        ));
        setHud(new Hud(getEngine()));
        setMapLoader(new MultiTmxMapLoader());
    
        setMaps(getMapLoader().loadAllMaps(Maps.TWINLEAF_TOWN.getTmxPath(), Maps.ROUTE_201.getTmxPath()));
        
        setRenderer(new MultiTileMapRenderer(1.0f, getEngine().getBatch()));
    
        setMapCreator(new MapCreator(this, getMaps()));
    
        setUser(new User(this));
        setNpc(new NPC(getMapCreator(), "Characters\\NPCSpriteSheets\\NPC 01.png"));
    
        getCamera().position.set(getViewport().getWorldWidth() / Engine.getCameraZoomScale(),
                                 getViewport().getWorldHeight() / Engine.getCameraZoomScale(), 0
        );
        
        setStage(new Stage(getViewport(), getEngine().getBatch()));
        getStage().addActor(getUser());
        getStage().addActor(getNpc());
        Gdx.input.setInputProcessor(getStage());
    
        setSound(AudioData.TWINLEAF_TOWN_DAYTIME);
        getSound().playAudio();
        
        TiledMapTileSet tileSet = getMaps()[0].getTileSets().getTileSet("SinnohTileSet");
    
        setDoorTiles(HashIntObjMaps.newMutableMap());
        for(TiledMapTile tile : tileSet) {
            Object property = tile.getProperties().get("Door Animation");
            if(property != null) {
                getDoorTiles().put((int)(property), tile);
            }
        }
    
        setDoorsInMap(new ArrayList<>());
        TiledMapTileLayer layer = (TiledMapTileLayer)(getMaps()[0].getLayers().get("Object Bottom"));
        for(int x = 0; x < layer.getWidth(); x++) {
            for(int y = 0; y < layer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if(cell != null && cell.getTile().getProperties().containsKey("Door Animation")) {
                    Object property = cell.getTile().getProperties().get("Door Animation");
                    if(property != null) {
                        getDoorsInMap().add(cell);
                    }
                }
            }
        }
        setDoorToOpen(null);
        setCurrentDoorFrameCount(getStartDoorFrameCount());
        
        setDialog(new Dialog(getEngine(), 0, 0, Engine.getvWidth(), 64, TextSpeeds.FAST,
                             "Test Character Writer\nABCDEFGHIJKLMNOPQRSTUVWXYZ abcdefghijklmnopqrstuvwxyz 0123456789 Test to wrap to the next line " +
                             "Test Character Writer ABCDEFGHIJKLMNOPQRSTUVWXYZ abcdefghijklmnopqrstuvwxyz 0123456789 Test to wrap to the next line"
        ));
    }
    
    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        renderBackground();
        
        getStage().act(delta);
        getStage().draw();
        
        renderObjects();
    
        if(getDoorToOpen() != null) {
            setCurrentDoorFrameCount(getCurrentDoorFrameCount() + 1);
            if(getCurrentDoorFrameCount() % getFramesToAnimateDoor() == 0) {
                animateDoor();
            }
        }
    
        //getEngine().getBatch().setProjectionMatrix(getHud().stage.getCamera().combined);
        //getHud().stage.draw();
        
        getEngine().getBatch().setProjectionMatrix(getDialog().getStage().getCamera().combined);
    
        getDialog().getStage().act(delta);
        getDialog().getStage().draw();
        
        fpsLogger.log();
    }
    
    @Override
    public void resize(int width, int height) {
        getViewport().update(width, height);
    }
    
    @Override
    public void pause() {
        
    }
    
    @Override
    public void resume() {
        
    }
    
    @Override
    public void hide() {
        
    }
    
    @Override
    public void dispose() {
        for(MultiTiledMap tiledMap : getMaps()) {
            tiledMap.dispose();
        }
        getRenderer().dispose();
        getHud().dispose();
        getStage().dispose();
        getSound().getAudio().dispose();
        getDialog().dispose();
    }
    
    public void changeCurrentScreen(Class<? extends Screen> screenToSwitchTo) {
        getEngine().setScreen(getEngine().getScreen(
            screenToSwitchTo == BattleScreen.class ? Engine.screenTypes.BATTLE_SCREEN :
            Engine.screenTypes.INTRO_SCREEN));
        getSound().getAudio().dispose();
    }
    
    public void animateDoor() {
        for(TiledMapTileLayer.Cell cell : getDoorsInMap()) {
            int currentAnimationFrame = (int)(cell.getTile().getProperties().get("Door Animation"));
    
            currentAnimationFrame++;
    
            TiledMapTile newTile = getDoorTiles().get(currentAnimationFrame);
            if(newTile != null) {
                cell.setTile(newTile);
            } else {
                if(getDoorToOpen() == null) {
                    return;
                }
                getDoorToOpen().switchRooms();
                setDoorToOpen(null);
            }
        }
    }
    
    private void renderBackground() {
        for(MultiTiledMap tiledMap : getMaps()) {
            for(MapLayer mapLayer : tiledMap.getLayers()) {
                if(!mapLayer.getName().equalsIgnoreCase("Objects") &&
                   !mapLayer.getName().equalsIgnoreCase("Collisions") && mapLayer instanceof TiledMapTileLayer) {
                    getRenderer().renderTileLayer((TiledMapTileLayer)(mapLayer));
                }
            }
        }
    }
    
    private void renderObjects() {
        for(MultiTiledMap tiledMap : getMaps()) {
            for(MapLayer mapLayer : tiledMap.getLayers()) {
                if(mapLayer.getName().equalsIgnoreCase("Objects") && mapLayer instanceof TiledMapTileLayer) {
                    getRenderer().renderTileLayer((TiledMapTileLayer)(mapLayer));
                }
            }
        }
    }
    
    private void update(float deltaTime) {
        handleInput(deltaTime);
        
        getUser().update(deltaTime);
        getNpc().update(deltaTime);
    
        getCamera().position.x = getUser().getPositionX();
        getCamera().position.y = getUser().getPositionY();
        
        getCamera().update();
        getRenderer().setView(getCamera());
    }
    
    public MultiTileMapRenderer getRenderer() {
        return renderer;
    }
    
    private void setRenderer(MultiTileMapRenderer renderer) {
        this.renderer = renderer;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    private Hud getHud() {
        return hud;
    }
    
    private void setHud(Hud hud) {
        this.hud = hud;
    }
    
    private void handleInput(float deltaTime) {
        getUser().handleInput(deltaTime);
    }
}