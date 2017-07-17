package com.horse.pokemon.amethyst.graphics;

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
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.horse.pokemon.amethyst.Engine;
import com.horse.pokemon.amethyst.data.characters.NPC;
import com.horse.pokemon.amethyst.data.characters.User;
import com.horse.pokemon.amethyst.data.objects.Door;
import com.horse.pokemon.amethyst.graphics.audio.AudioData;
import com.horse.pokemon.amethyst.graphics.background.loader.MultiTileMapRenderer;
import com.horse.pokemon.amethyst.graphics.background.loader.MultiTmxMapLoader;
import com.horse.pokemon.amethyst.graphics.background.system.MapCreator;
import com.horse.pokemon.amethyst.graphics.background.system.Maps;
import com.horse.pokemon.amethyst.graphics.background.system.MultiTiledMap;
import com.horse.pokemon.amethyst.graphics.battle.system.BattleScreen;
import com.horse.pokemon.amethyst.graphics.dialog.Dialog;
import com.horse.pokemon.amethyst.graphics.dialog.TextSpeeds;
import com.koloboke.collect.map.hash.HashIntObjMap;
import com.koloboke.collect.map.hash.HashIntObjMaps;

import java.util.ArrayList;

/**
 * Class containing all the graphical pieces for when the {@link User} is on the screen state.  This is the {@link Screen} that is used the most as the major actions happen on this
 * screen.
 */
public class MainGameScreen implements Screen {
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
    private Stage                             stage;
    private AudioData                         sound;
    private Dialog                            dialog;
    private FPSLogger                         fpsLogger;
    private ArrayList<TiledMapTileLayer.Cell> doorsInMap;
    private HashIntObjMap<TiledMapTile>       doorTiles;
    private Door                              doorToOpen;
    private NPC                               npc;
    private int                               currentDoorFrameCount;
    
    public MainGameScreen(Engine engine) {
        setEngine(engine);
        fpsLogger = new FPSLogger();
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
    
        MapCreator.addTiledObjects(this, getMaps());
    
        MapCreator.setUser(new User(this));
    
        setNpc(new NPC("Characters\\NPCSpriteSheets\\NPC 01.png"));
    
        getCamera().position.set(getViewport().getWorldWidth() / Engine.getCameraZoomScale(),
                                 getViewport().getWorldHeight() / Engine.getCameraZoomScale(), 0
        );
        
        setStage(new Stage(getViewport(), getEngine().getBatch()));
        getStage().addActor(MapCreator.getUser());
        getStage().addActor(getNpc());
        getStage().setKeyboardFocus(MapCreator.getUser());
        
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
        setCurrentDoorFrameCount(Door.getStartDoorFrameCount());
    
        setDialog(new Dialog(0, 0, Engine.getvWidth(), 64, TextSpeeds.FAST,
                             "Test Character Writer\nABCDEFGHIJKLMNOPQRSTUVWXYZ abcdefghijklmnopqrstuvwxyz 0123456789 Test to wrap to the next line " +
                             "Test Character Writer ABCDEFGHIJKLMNOPQRSTUVWXYZ abcdefghijklmnopqrstuvwxyz 0123456789 Test to wrap to the next line",
                             false, getEngine().getBatch()
        ));
    }
    
    @Override
    public void render(float delta) {
        getEngine().getBatch().begin();
    
        getCamera().position.x = MapCreator.getUser().getX();
        getCamera().position.y = MapCreator.getUser().getY();
    
        getCamera().update();
        getRenderer().setView(getCamera());
        
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        renderBackground();
    
        getEngine().getBatch().end();
        
        getStage().act(delta);
    
        for(int i = 0; i < MapCreator.getNpcPositions().size(); i++) {
            if(MapCreator.getUser().getY() > MapCreator.getNpcPositions().get(i).getY()) {
                MapCreator.getUser().setZIndex(0);
                getNpc().setZIndex(1);
            } else {
                MapCreator.getUser().setZIndex(1);
                getNpc().setZIndex(0);
            }
        }
    
        getStage().draw();
    
        getEngine().getBatch().begin();
        
        renderObjects();
    
        if(getDoorToOpen() != null) {
            setCurrentDoorFrameCount(getCurrentDoorFrameCount() + 1);
            if(getCurrentDoorFrameCount() % Door.getFramesToAnimateDoor() == 0) {
                animateDoor();
            }
        }
    
        //getEngine().getBatch().setProjectionMatrix(getHud().stage.getCamera().combined);
        //getHud().stage.draw();
    
        getEngine().getBatch().end();
        
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
    
    public void changeCurrentScreen(final Class<? extends Screen> screenToSwitchTo) {
        final float[] timeData = {1.5f, 1.5f, 3f, 2f, 1f, 1f, 1f};
        getStage().addAction(Actions.sequence(new Action() {
            @Override
            public boolean act(float delta) {
                timeData[0] -= delta;
                final float x = timeData[0] / timeData[1];
                getEngine().getBatch()
                           .setColor(timeData[4], timeData[5], timeData[6], x * x * x * (x * (x * 6 - 15) + 10));
                return timeData[0] <= 0f;
            }
        }, new Action() {
            @Override
            public boolean act(float delta) {
                getEngine().setScreen(getEngine().getScreen(
                    screenToSwitchTo == BattleScreen.class ? Engine.screenTypes.BATTLE_SCREEN :
                    Engine.screenTypes.INTRO_SCREEN));
                getSound().getAudio().dispose();
                return true;
            }
        }));
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
    
    public MultiTileMapRenderer getRenderer() {
        return renderer;
    }
    
    private void setRenderer(MultiTileMapRenderer renderer) {
        this.renderer = renderer;
    }
    
    private Hud getHud() {
        return hud;
    }
    
    private void setHud(Hud hud) {
        this.hud = hud;
    }
}