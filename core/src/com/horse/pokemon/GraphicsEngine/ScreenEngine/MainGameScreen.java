package com.horse.pokemon.GraphicsEngine.ScreenEngine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.horse.pokemon.Audio.AudioData;
import com.horse.pokemon.Engine;
import com.horse.pokemon.ObjectData.Players.User;

public class MainGameScreen implements Screen {
    private Engine                     engine;
    private TextureAtlas               atlas;
    private OrthographicCamera         camera;
    private Viewport                   viewport;
    private Hud                        hud;
    private TmxMapLoader               mapLoader;
    private TiledMap                   map;
    private OrthogonalTiledMapRenderer renderer;
    private World                      world;
    private User                       user;
    private Stage                      stage;
    private AudioData                  sound;
    
    public MainGameScreen(Engine engine) {
        setEngine(engine);
        setAtlas(new TextureAtlas("User\\GDX_Users\\User.pack"));
        setCamera(new OrthographicCamera());
        setViewport(new FitViewport(Engine.getvWidth() / Engine.getCameraZoomScale(),
                                    Engine.getvHeight() / Engine.getCameraZoomScale(), getCamera()
        ));
        setHud(new Hud(getEngine()));
        setMapLoader(new TmxMapLoader());
        setMap(getMapLoader().load("Maps\\StartMap.tmx"));
        setRenderer(new OrthogonalTiledMapRenderer(getMap()));
        setWorld(new World(new Vector2(0, 0), true));
        MapCreator mapCreator = new MapCreator(this, getMap());
        setUser(new User(this, mapCreator));
        getCamera().position.set(getViewport().getWorldWidth() / Engine.getCameraZoomScale(),
                                 getViewport().getWorldHeight() / Engine.getCameraZoomScale(), 0
        );
        setStage(new Stage(getViewport(), getEngine().getBatch()));
        getStage().addActor(getUser());
        Gdx.input.setInputProcessor(getStage());
        sound = AudioData.INTRODUCTION;
        sound.playAudio();
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
    
    public World getWorld() {
        return world;
    }
    
    public void setWorld(World world) {
        this.world = world;
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
        
    }
    
    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        renderBackground();
        
        getEngine().getBatch().setProjectionMatrix(getCamera().combined);
        getEngine().getBatch().begin();
        
        getEngine().getBatch().end();
        getStage().act(Gdx.graphics.getDeltaTime());
        getStage().draw();
        
        renderObjects();
        
        getEngine().getBatch().setProjectionMatrix(getHud().stage.getCamera().combined);
        getHud().stage.draw();
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
        getWorld().dispose();
        getHud().dispose();
        getStage().dispose();
        sound.getAudio().dispose();
    }
    
    public void renderBackground() {
        for(MapLayer mapLayer : getMap().getLayers()) {
            if(!mapLayer.getName().equalsIgnoreCase("Objects") && !mapLayer.getName().equalsIgnoreCase("Collisions")) {
                getRenderer().getBatch().begin();
                try {
                    getRenderer().renderTileLayer((TiledMapTileLayer)(mapLayer));
                } catch(ClassCastException e) {
                    e.printStackTrace();
                }
                getRenderer().getBatch().end();
            }
        }
    }
    
    public void renderObjects() {
        for(MapLayer mapLayer : getMap().getLayers()) {
            if(mapLayer.getName().equalsIgnoreCase("Objects")) {
                getRenderer().getBatch().begin();
                try {
                    getRenderer().renderTileLayer((TiledMapTileLayer)(mapLayer));
                } catch(ClassCastException e) {
                    e.printStackTrace();
                }
                getRenderer().getBatch().end();
            }
        }
    }
    
    private void update(float deltaTime) {
        handleInput(deltaTime);
        
        getWorld().step(1 / 60f, 6, 2);
        
        getUser().update(deltaTime);
        
        getCamera().position.x = getUser().getPositionX();
        getCamera().position.y = getUser().getPositionY();
        
        getCamera().update();
        getRenderer().setView(getCamera());
    }
    
    public OrthogonalTiledMapRenderer getRenderer() {
        return renderer;
    }
    
    public void setRenderer(OrthogonalTiledMapRenderer renderer) {
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