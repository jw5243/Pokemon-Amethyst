package com.horse.pokemon.GraphicsEngine.ScreenEngine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.horse.pokemon.Audio.AudioData;
import com.horse.pokemon.Engine;
import com.horse.pokemon.Enums.TextSpeeds;
import com.horse.pokemon.GraphicsEngine.MainInterface.DialogEngine.Dialog;
import com.horse.pokemon.ObjectData.Players.User;

public class MainGameScreen implements Screen {
    private Engine               engine;
    private TextureAtlas         atlas;
    private OrthographicCamera   camera;
    private Viewport             viewport;
    private Hud                  hud;
    private TmxMapLoader         mapLoader;
    private TiledMap             map;
    private MultiTileMapRenderer renderer;
    private User                 user;
    private Stage                stage;
    private AudioData            sound;
    private Dialog               dialog;
    private FPSLogger            fpsLogger;
    
    public MainGameScreen(Engine engine) {
        setEngine(engine);
        fpsLogger = new FPSLogger();
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
    
    public TmxMapLoader getMapLoader() {
        return mapLoader;
    }
    
    public void setMapLoader(TmxMapLoader mapLoader) {
        this.mapLoader = mapLoader;
    }
    
    public TiledMap getMap() {
        return map;
    }
    
    public void setMap(TiledMap map) {
        this.map = map;
    }
    
    public Viewport getViewport() {
        return viewport;
    }
    
    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }
    
    public TextureAtlas getAtlas() {
        return atlas;
    }
    
    public void setAtlas(TextureAtlas atlas) {
        this.atlas = atlas;
    }
    
    @Override
    public void show() {
        setAtlas(new TextureAtlas(User.getUserInformation()));
        setCamera(new OrthographicCamera());
        setViewport(new FitViewport(Engine.getvWidth() / Engine.getCameraZoomScale(),
                                    Engine.getvHeight() / Engine.getCameraZoomScale(), getCamera()
        ));
        setHud(new Hud(getEngine()));
        setMapLoader(new TmxMapLoader());
        
        setMap(getMapLoader().load(Maps.TWINLEAF_TOWN.getTmxPath()));
        setRenderer(new MultiTileMapRenderer(getMap(), 1.0f, getEngine().getBatch()));
        
        setUser(new User(this, new MapCreator(this, getMap())));
        
        getCamera().position.set(getViewport().getWorldWidth() / Engine.getCameraZoomScale(),
                                 getViewport().getWorldHeight() / Engine.getCameraZoomScale(), 0
        );
        
        setStage(new Stage(getViewport(), getEngine().getBatch()));
        getStage().addActor(getUser());
        Gdx.input.setInputProcessor(getStage());
        
        sound = AudioData.INTRODUCTION;
        sound.playAudio();
        dialog = new Dialog(getEngine(), 0, 0, Engine.getvWidth(), 64, TextSpeeds.FAST,
                            "Test Character Writer ABCDEFGHIJKLMNOPQRSTUVWXYZ abcdefghijklmnopqrstuvwxyz 0123456789 Test to wrap to the next line " +
                            "Test Character Writer ABCDEFGHIJKLMNOPQRSTUVWXYZ abcdefghijklmnopqrstuvwxyz 0123456789 Test to wrap to the next line"
        );
    }
    
    @Override
    public void render(float delta) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            getEngine().setScreen(getEngine().getScreen(Engine.screenTypes.BATTLE_SCREEN));
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            getRenderer().setOffsetX(Engine.getTileSize());
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.L)) {
            getRenderer().setOffsetX(-Engine.getTileSize());
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            getRenderer().setOffsetX(0);
        }
        
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        renderBackground();
        
        //getEngine().getBatch().setProjectionMatrix(getCamera().combined);
        
        getStage().act(delta);
        getStage().draw();
        
        renderObjects();
        
        getEngine().getBatch().setProjectionMatrix(getHud().stage.getCamera().combined);
        getHud().stage.draw();
        
        getEngine().getBatch().setProjectionMatrix(dialog.getStage().getCamera().combined);
        dialog.getStage().act(delta);
        dialog.getStage().draw();
        
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
        getMap().dispose();
        getRenderer().dispose();
        getHud().dispose();
        getStage().dispose();
        sound.getAudio().dispose();
        dialog.dispose();
    }
    
    public void renderBackground() {
        for(MapLayer mapLayer : getMap().getLayers()) {
            if(!mapLayer.getName().equalsIgnoreCase("Objects") && !mapLayer.getName().equalsIgnoreCase("Collisions")) {
                try {
                    getRenderer().renderTileLayer((TiledMapTileLayer)(mapLayer));
                } catch(ClassCastException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void renderObjects() {
        for(MapLayer mapLayer : getMap().getLayers()) {
            if(mapLayer.getName().equalsIgnoreCase("Objects")) {
                try {
                    getRenderer().renderTileLayer((TiledMapTileLayer)(mapLayer));
                } catch(ClassCastException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    private void update(float deltaTime) {
        handleInput(deltaTime);
        
        getUser().update(deltaTime);
    
        float halfViewportWidth = getCamera().viewportWidth / 2;
        float halfViewportHeight = getCamera().viewportHeight / 2;
        
        int mapWidth = (int)(getMap().getProperties().get("width"));
        int mapHeight = (int)(getMap().getProperties().get("height"));
        
        getCamera().position.x = MathUtils.clamp(getUser().getPositionX(), halfViewportWidth, mapWidth * Engine.getTileSize() - halfViewportWidth);
        getCamera().position.y = MathUtils.clamp(getUser().getPositionY(), halfViewportHeight, mapHeight * Engine.getTileSize() - halfViewportHeight);
        
        getCamera().update();
        getRenderer().setView(getCamera());
    }
    
    public MultiTileMapRenderer getRenderer() {
        return renderer;
    }
    
    public void setRenderer(MultiTileMapRenderer renderer) {
        this.renderer = renderer;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public Hud getHud() {
        return hud;
    }
    
    public void setHud(Hud hud) {
        this.hud = hud;
    }
    
    private void handleInput(float deltaTime) {
        getUser().handleInput(deltaTime);
    }
}