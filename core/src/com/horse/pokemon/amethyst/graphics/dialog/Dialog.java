package com.horse.pokemon.amethyst.graphics.dialog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.horse.pokemon.amethyst.Engine;

import java.util.ArrayList;

public class Dialog extends Actor implements Disposable {
    private static final Texture DIALOG_TEXTURE       =
        new Texture(Gdx.files.internal("DialogBoxes\\generalDialog.png"));
    private static final Texture DIALOG_ARROW_TEXTURE = new Texture(Gdx.files.internal("DialogBoxes\\arrow.png"));
    
    private ArrayList<CharacterWriter> charactersToTypeArrayList;
    private SpriteBatch                spriteBatch;
    private String                     unparsedTextToWrite;
    private TextSpeeds                 textSpeed;
    private float                      timer;
    
    public Dialog() {
        this(0, 0, 0, 0, TextSpeeds.NORMAL, null);
    }
    
    public Dialog(int dialogXPosition, int dialogYPosition) {
        this(dialogXPosition, dialogYPosition, 0, 0, TextSpeeds.NORMAL, null);
    }
    
    public Dialog(int dialogXPosition, int dialogYPosition, int dialogWidth, int dialogHeight) {
        this(dialogXPosition, dialogYPosition, dialogWidth, dialogHeight, TextSpeeds.NORMAL, null);
    }
    
    public Dialog(int dialogXPosition, int dialogYPosition, int dialogWidth, int dialogHeight, TextSpeeds textSpeed) {
        this(dialogXPosition, dialogYPosition, dialogWidth, dialogHeight, textSpeed, null);
    }
    
    public Dialog(int dialogXPosition, int dialogYPosition, int dialogWidth, int dialogHeight, TextSpeeds textSpeed,
                  String unparsedTextToWrite) {
        this(dialogXPosition, dialogYPosition, dialogWidth, dialogHeight, textSpeed, unparsedTextToWrite, false,
             new SpriteBatch(unparsedTextToWrite.length())
        );
    }
    
    public Dialog(int dialogXPosition, int dialogYPosition, int dialogWidth, int dialogHeight, TextSpeeds textSpeed,
                  String unparsedTextToWrite, boolean isVisible) {
        this(dialogXPosition, dialogYPosition, dialogWidth, dialogHeight, textSpeed, unparsedTextToWrite, isVisible,
             new SpriteBatch(unparsedTextToWrite.length())
        );
    }
    
    public Dialog(int dialogXPosition, int dialogYPosition, int dialogWidth, int dialogHeight, TextSpeeds textSpeed,
                  String unparsedTextToWrite, boolean isVisible, SpriteBatch spriteBatch) {
        this(dialogXPosition, dialogYPosition, dialogWidth, dialogHeight, textSpeed, unparsedTextToWrite, isVisible,
             spriteBatch, new Stage(new FitViewport(Engine.getvWidth(), Engine.getvHeight(), new OrthographicCamera()))
        );
    }
    
    public Dialog(int dialogXPosition, int dialogYPosition, int dialogWidth, int dialogHeight, TextSpeeds textSpeed,
                  String unparsedTextToWrite, boolean isVisible, SpriteBatch spriteBatch, Stage stage) {
        this(new Rectangle(dialogXPosition, dialogYPosition, dialogWidth, dialogHeight), textSpeed, unparsedTextToWrite,
             isVisible, spriteBatch,
             new Stage(new FitViewport(Engine.getvWidth(), Engine.getvHeight(), new OrthographicCamera()))
        );
    }
    
    public Dialog(Rectangle dialogRectangle, TextSpeeds textSpeed) {
        this(dialogRectangle, textSpeed, "");
    }
    
    public Dialog(Rectangle dialogRectangle, TextSpeeds textSpeed, String unparsedTextToWrite) {
        this(dialogRectangle, textSpeed, unparsedTextToWrite, false);
    }
    
    public Dialog(Rectangle dialogRectangle, TextSpeeds textSpeed, String unparsedTextToWrite, boolean isVisible) {
        this(dialogRectangle, textSpeed, unparsedTextToWrite, isVisible, new SpriteBatch(unparsedTextToWrite.length()));
    }
    
    public Dialog(Rectangle dialogRectangle, TextSpeeds textSpeed, String unparsedTextToWrite, boolean isVisible,
                  SpriteBatch spriteBatch) {
        this((int)(dialogRectangle.getX()), (int)(dialogRectangle.getY()), (int)(dialogRectangle.getWidth()),
             (int)(dialogRectangle.getHeight()), textSpeed, unparsedTextToWrite, isVisible, spriteBatch
        );
    }
    
    public Dialog(Rectangle dialogRectangle, TextSpeeds textSpeed, String unparsedTextToWrite, boolean isVisible,
                  SpriteBatch spriteBatch, Stage stage) {
        setStage(stage);
        setSpriteBatch(spriteBatch);
        setTextSpeed(textSpeed);
        setCharactersToTypeArrayList(new ArrayList<>(unparsedTextToWrite.length()));
        setUnparsedTextToWrite(unparsedTextToWrite);
        setVisible(isVisible);
        setTimer(0f);
        
        setX(dialogRectangle.getX());
        setY(dialogRectangle.getY());
        setWidth(dialogRectangle.getWidth());
        setHeight(dialogRectangle.getHeight());
        
        getStage().addActor(this);
        
        setupCharactersToWrite();
    }
    
    public static Texture getDialogArrowTexture() {
        return DIALOG_ARROW_TEXTURE;
    }
    
    public static Texture getDialogTexture() {
        return DIALOG_TEXTURE;
    }
    
    private void setupCharactersToWrite() {
        if(getUnparsedTextToWrite() != null) {
            float currentWriterXPosition = getX() + CharacterWriter.getDefaultCharacterStartXPositionBuffer();
            float currentWriterYPosition = getY() + getHeight() - DialogCharacter.getDefaultHeight() -
                                           CharacterWriter.getDefaultCharacterStartYPositionBuffer();
        
            for(char character : getUnparsedTextToWrite().toCharArray()) {
                if(character == '\n') {
                    currentWriterXPosition = getX() + CharacterWriter.getDefaultCharacterStartXPositionBuffer();
                    currentWriterYPosition = currentWriterYPosition - DialogCharacter.getDefaultHeight();
                } else if(character == ' ') {
                    currentWriterXPosition = currentWriterXPosition + DialogCharacter.SPACE.getWidth();
                    if(currentWriterXPosition + DialogCharacter.getDefaultWidth() +
                       CharacterWriter.getDefaultCharacterStartXPositionBuffer() >= getX() + getWidth()) {
                        currentWriterXPosition = getX() + CharacterWriter.getDefaultCharacterStartXPositionBuffer();
                        currentWriterYPosition = currentWriterYPosition - DialogCharacter.getDefaultHeight();
                    }
                } else {
                    CharacterWriter characterWriter =
                        new CharacterWriter(character, (int)(currentWriterXPosition), (int)(currentWriterYPosition));
                
                    getCharactersToTypeArrayList().add(characterWriter);
                
                    currentWriterXPosition = currentWriterXPosition + characterWriter.getCharacterWidth();
                    if(currentWriterXPosition + DialogCharacter.getDefaultWidth() +
                       CharacterWriter.getDefaultCharacterEndXPositionBuffer() >= getX() + getWidth()) {
                        currentWriterXPosition = getX() + CharacterWriter.getDefaultCharacterStartXPositionBuffer();
                        currentWriterYPosition = currentWriterYPosition - DialogCharacter.getDefaultHeight();
                    }
                }
            }
        }
    }
    
    @Override
    public void draw(Batch batch, float parentAlpha) {
        setTimer(getTimer() + Gdx.graphics.getDeltaTime());
        
        getSpriteBatch().begin();
        
        getSpriteBatch().draw(getDialogTexture(), getX(), getY(), getWidth(), getHeight());
        
        for(int index = 0; index < getCharactersToTypeArrayList().size(); index++) {
            if(getTimer() > (index + 1) * getTextSpeed().getSpeed()) {
                getCharactersToTypeArrayList().get(index).draw(getSpriteBatch(), parentAlpha);
            }
        }
        
        if(getTimer() > (getCharactersToTypeArrayList().size() + 1) * getTextSpeed().getSpeed()) {
            if(getCharactersToTypeArrayList().get(getCharactersToTypeArrayList().size() - 1).getCharacterXPosition() +
               getCharactersToTypeArrayList().get(getCharactersToTypeArrayList().size() - 1).getCharacterWidth() +
               CharacterWriter.getDefaultCharacterEndXPositionBuffer() >= getX() + getWidth()) {
                getSpriteBatch()
                    .draw(getDialogArrowTexture(), CharacterWriter.getDefaultCharacterStartXPositionBuffer(),
                          getCharactersToTypeArrayList().get(getCharactersToTypeArrayList().size() - 1)
                                                        .getCharacterYPosition() -
                          getCharactersToTypeArrayList().get(getCharactersToTypeArrayList().size() - 1)
                                                        .getCharacterHeight()
                    );
            } else {
                getSpriteBatch().draw(getDialogArrowTexture(),
                                      getCharactersToTypeArrayList().get(getCharactersToTypeArrayList().size() - 1)
                                                                    .getCharacterXPosition() +
                                      getCharactersToTypeArrayList().get(getCharactersToTypeArrayList().size() - 1)
                                                                    .getCharacterWidth(),
                                      getCharactersToTypeArrayList().get(getCharactersToTypeArrayList().size() - 1)
                                                                    .getCharacterYPosition()
                );
            }
        }
        
        getSpriteBatch().end();
    }
    
    public ArrayList<CharacterWriter> getCharactersToTypeArrayList() {
        return charactersToTypeArrayList;
    }
    
    public void setCharactersToTypeArrayList(ArrayList<CharacterWriter> charactersToTypeArrayList) {
        this.charactersToTypeArrayList = charactersToTypeArrayList;
    }
    
    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }
    
    public void setSpriteBatch(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
    }
    
    public String getUnparsedTextToWrite() {
        return unparsedTextToWrite;
    }
    
    public void setUnparsedTextToWrite(String unparsedTextToWrite) {
        this.unparsedTextToWrite = unparsedTextToWrite;
    }
    
    public TextSpeeds getTextSpeed() {
        return textSpeed;
    }
    
    public void setTextSpeed(TextSpeeds textSpeed) {
        this.textSpeed = textSpeed;
    }
    
    public float getTimer() {
        return timer;
    }
    
    public void setTimer(float timer) {
        this.timer = timer;
    }
    
    /**
     * Releases all resources of this object.
     */
    @Override
    public void dispose() {
        getStage().dispose();
    }
}