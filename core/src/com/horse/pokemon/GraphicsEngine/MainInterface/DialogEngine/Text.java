package com.horse.pokemon.GraphicsEngine.MainInterface.DialogEngine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import com.horse.pokemon.Engine;
import com.horse.pokemon.Enums.DialogBoxes;
import com.horse.pokemon.Enums.TextSpeeds;

import java.util.ArrayList;

import static com.horse.pokemon.Enums.DialogBoxes.BATTLE_DIALOG;
import static com.horse.pokemon.Enums.DialogBoxes.DIALOG;
import static com.horse.pokemon.Enums.DialogBoxes.DIALOG_CHOICE;
import static com.horse.pokemon.Enums.DialogBoxes.SELF_DIALOG;
import static com.horse.pokemon.Enums.DialogBoxes.SELF_DIALOG_CHOICE;
import static com.horse.pokemon.Enums.DialogBoxes.SIGN_DIALOG;

/**
 * Application writing text object.
 */
public class Text extends AbstractTextMap implements TextInterface {
    private static final double scale = 1.5;
    private SpriteBatch      batch;
    private Dialogs          dialog;
    private TextSpeeds       speed;
    private double           currentSpeed;
    private int              xTextPosition;
    private int              yTextPosition;
    private ArrayList<int[]> characterInformation;
    private DialogBoxes      dialogType;
    
    public Text(Engine engine, Dialogs dialog, TextSpeeds textSpeeds) {
        this.batch = engine.getBatch();
        this.dialog = dialog;
        speed = textSpeeds;
    }
    
    public void drawText(char... text) {
        currentSpeed = speed.getSpeed();
        characterInformation = new ArrayList<>();
        dialogType = dialog.getDialogType();
        try {
            for(char character : text) {
                characterInformation.add(textMap.get(new TextMapKey(character, dialogType)));
            }
            xTextPosition = dialog.getDialogPosition()[0];
            yTextPosition = dialog.getDialogPosition()[1];
            yTextPosition += 10 * scale;
            for(int[] charInfo : characterInformation) {
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        if(xTextPosition + 2 * (charInfo[2] == 3 ? 8 * scale : 6 * scale) >=
                           dialog.getDialogPosition()[0] + dialog.getXSize()) {
                            xTextPosition = dialog.getDialogPosition()[0];
                            yTextPosition += 10 * scale;
                        }
                        xTextPosition += charInfo[2] == 3 ? 8 * scale : 6 * scale;
                        Texture textImage = null;
                        if(dialogType == BATTLE_DIALOG) {
                            textImage = new Texture(Gdx.files.internal(battleFile));
                        } else if(dialogType == DIALOG | dialogType == DIALOG_CHOICE) {
                            textImage = new Texture(Gdx.files.internal(dialogFile));
                        } else if(dialogType == SELF_DIALOG | dialogType == SELF_DIALOG_CHOICE) {
                            textImage = new Texture(Gdx.files.internal(selfDialogFile));
                        } else if(dialogType == SIGN_DIALOG) {
                            textImage = new Texture(Gdx.files.internal(dialogFile));
                        } else {
                            try {
                                throw new Exception(String.format("No correct dialog type inputted.  Given %s",
                                                                  dialogType
                                ));
                            } catch(Exception e) {
                                e.printStackTrace();
                            }
                        }
                        //ImageView character = new ImageView();
                        //character.setImage(textImage);
                        //Rectangle2D viewportRect = new Rectangle2D(charInfo[0], charInfo[1], charInfo[2], charInfo[3]);
                        //character.setViewport(viewportRect);
                        //character.setFitWidth(charInfo[2] * scale);
                        //character.setFitHeight(charInfo[3] * scale);
                        //character.relocate(xTextPosition, yTextPosition);
                        //Platform.runLater(() -> pane.getChildren().add(character));
                        if(textImage != null) {
                            batch.draw(textImage, 400, 400);
                        }
                        //batch.draw(textImage, xTextPosition, yTextPosition, charInfo[0], charInfo[1], charInfo[2], charInfo[3]);
                    }
                }, (float)(currentSpeed * 1));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void setSpeed(TextSpeeds textSpeed) {
        speed = textSpeed;
    }
}