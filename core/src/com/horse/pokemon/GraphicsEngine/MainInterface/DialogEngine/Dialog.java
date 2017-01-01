package com.horse.pokemon.GraphicsEngine.MainInterface.DialogEngine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.horse.pokemon.Engine;
import com.horse.pokemon.Enums.TextSpeeds;

public class Dialog extends Actor implements Disposable {
    private final String dialogFile = "DialogBoxes\\dialog.png";
    private Viewport     viewport;
    private Stage        stage;
    private DialogWriter writer;
    private SpriteBatch  batch;
    private String       text;
    private Texture      dialog;
    private int          xPosition;
    private int          yPosition;
    private int          xSize;
    private int          ySize;
    
    public Dialog(Engine engine, int xPosition, int yPosition, int xSize, int ySize, TextSpeeds textSpeeds,
                  String text) {
        setViewport(new FitViewport(Engine.getvWidth(), Engine.getvHeight(), new OrthographicCamera()));
        setStage(new Stage(getViewport(), engine.getBatch()));
        setBatch(engine.getBatch());
        setxPosition(xPosition);
        setyPosition(yPosition);
        setText(text);
        setxSize(xSize);
        setySize(ySize);
        setWriter(new DialogWriter(engine, this, textSpeeds, getText()));
        setDialog(new Texture(Gdx.files.internal(getDialogFile())));
        getStage().addActor(this);
        getStage().addAction(getWriter());
    }
    
    public Viewport getViewport() {
        return viewport;
    }
    
    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }
    
    public String getDialogFile() {
        return dialogFile;
    }
    
    public DialogWriter getWriter() {
        return writer;
    }
    
    public void setWriter(DialogWriter writer) {
        this.writer = writer;
    }
    
    public SpriteBatch getBatch() {
        return batch;
    }
    
    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public Texture getDialog() {
        return dialog;
    }
    
    public void setDialog(Texture dialog) {
        this.dialog = dialog;
    }
    
    public int getxPosition() {
        return xPosition;
    }
    
    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }
    
    public int getyPosition() {
        return yPosition;
    }
    
    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }
    
    public int getxSize() {
        return xSize;
    }
    
    public void setxSize(int xSize) {
        this.xSize = xSize;
    }
    
    public int getySize() {
        return ySize;
    }
    
    public void setySize(int ySize) {
        this.ySize = ySize;
    }
    
    @Override
    public void draw(Batch batch, float parentAlpha) {
        getBatch().draw(getDialog(), getxPosition(), getyPosition(), getxSize(), getySize());
        //getWriter().drawText(getText().toCharArray());
    }
    
    public Stage getStage() {
        return stage;
    }
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    public int[] getDialogPosition() {
        return new int[] {getxPosition(), getyPosition()};
    }
    
    public double getXSize() {
        return xSize;
    }
    
    public double getYSize() {
        return ySize;
    }
    
    @Override
    public void dispose() {
        getStage().dispose();
    }
}