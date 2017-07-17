package com.horse.pokemon.amethyst.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.horse.pokemon.amethyst.Engine;
import com.horse.pokemon.amethyst.graphics.dialog.Dialog;
import com.horse.pokemon.amethyst.graphics.dialog.TextSpeeds;

public class IntroScreen implements Screen {
    private final SequenceAction introductionDialog;
    private final Dialog         mainDialog;
    private final Stage          stage;
    
    private Engine engine;
    
    public IntroScreen(Engine engine) {
        setEngine(engine);
    
        stage = new Stage(new FitViewport(Engine.getvWidth() / Engine.getCameraZoomScale(),
                                          Engine.getvHeight() / Engine.getCameraZoomScale(), new OrthographicCamera()
        ), getEngine().getBatch());
    
        mainDialog =
            new Dialog(0, 0, Engine.getvWidth(), 64, TextSpeeds.FAST, "", false, getEngine().getBatch(), getStage());
    
        introductionDialog = new SequenceAction();
        Dialog.addTextAction(getMainDialog(), "Welcome to the world of Pokemon!", Input.Keys.X, false);
        Dialog.addTextAction(getMainDialog(), "My name is Professor _______", Input.Keys.X, true);
    }
    
    public Engine getEngine() {
        return engine;
    }
    
    public void setEngine(Engine engine) {
        this.engine = engine;
    }
    
    @Override
    public void show() {
        getMainDialog().setVisible(true);
    }
    
    @Override
    public void render(float delta) {
        getEngine().getBatch().begin();
    
        if(Gdx.input.isKeyJustPressed(Input.Keys.Y)) {
            getEngine().setScreen(getEngine().getScreen(Engine.screenTypes.MAIN_GAME_SCREEN));
        }
    
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    
        if(getEngine().getBatch().isDrawing()) {
            getEngine().getBatch().end();
        }
    
        getStage().act(delta);
        getStage().draw();
    }
    
    @Override
    public void resize(int width, int height) {
        
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
    
    }
    
    public SequenceAction getIntroductionDialog() {
        return introductionDialog;
    }
    
    public Dialog getMainDialog() {
        return mainDialog;
    }
    
    public Stage getStage() {
        return stage;
    }
}
