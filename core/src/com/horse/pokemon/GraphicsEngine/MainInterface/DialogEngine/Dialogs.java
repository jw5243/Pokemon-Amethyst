package com.horse.pokemon.GraphicsEngine.MainInterface.DialogEngine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.horse.pokemon.Engine;
import com.horse.pokemon.Enums.DialogBoxes;
import com.horse.pokemon.Enums.TextSpeeds;

import static com.horse.pokemon.Enums.DialogBoxes.BATTLE_DIALOG;
import static com.horse.pokemon.Enums.DialogBoxes.DIALOG;
import static com.horse.pokemon.Enums.DialogBoxes.DIALOG_CHOICE;
import static com.horse.pokemon.Enums.DialogBoxes.SELF_DIALOG;
import static com.horse.pokemon.Enums.DialogBoxes.SELF_DIALOG_CHOICE;
import static com.horse.pokemon.Enums.DialogBoxes.SIGN_DIALOG;

public class Dialogs {
    private SpriteBatch batch;
    private DialogBoxes currentDialog = DIALOG;
    private String  dialogFile;
    private Texture dialog;
    private int     xPosition;
    private int     yPosition;
    private int     xSize;
    private int     ySize;
    
    public Dialogs(Engine engine, int xPosition, int yPosition, int xSize, int ySize, DialogBoxes dialogType,
                   TextSpeeds textSpeeds, String text) {
        this.batch = engine.getBatch();
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.xSize = xSize;
        this.ySize = ySize;
        currentDialog = dialogType;
        drawDialog();
        Text writer = new Text(engine, this, textSpeeds);
        writer.drawText(text.toCharArray());
    }
    
    public void drawDialog() {
        try {
            dialogFile = (currentDialog == BATTLE_DIALOG) ? "DialogBoxes\\battleDialog.png" :
                         (currentDialog == DIALOG) ? "DialogBoxes\\dialog.png" :
                         (currentDialog == DIALOG_CHOICE) ? "DialogBoxes\\dialogChoice.png" :
                         (currentDialog == SELF_DIALOG) ? "DialogBoxes\\selfDialog.png" :
                         (currentDialog == SELF_DIALOG_CHOICE) ? "DialogBoxes\\selfDialogChoice.png" :
                         (currentDialog == SIGN_DIALOG) ? "DialogBoxes\\signDialog.png" : null;
            if(dialogFile == null) {
                throw new Exception(String.format("Dialog box usage is incorrect.  Dialog value is dialogBoxes %s",
                                                  currentDialog
                ));
            }
            dialog = new Texture(Gdx.files.internal(dialogFile));
            batch.draw(dialog, xPosition, yPosition, xSize, ySize);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public DialogBoxes getDialogType() {
        return currentDialog;
    }
    
    public int[] getDialogPosition() {
        return new int[] {xPosition, yPosition};
    }
    
    public double getXSize() {
        return xSize;
    }
    
    public double getYSize() {
        return ySize;
    }
}