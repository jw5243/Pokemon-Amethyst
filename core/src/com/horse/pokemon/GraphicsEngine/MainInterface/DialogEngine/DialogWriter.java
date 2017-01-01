package com.horse.pokemon.GraphicsEngine.MainInterface.DialogEngine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.horse.pokemon.Engine;
import com.horse.pokemon.Enums.TextSpeeds;

import java.util.ArrayList;

/**
 * Application writing text object.
 */
public class DialogWriter extends Action {
    private static final double scale = 1.5;
    private SpriteBatch batch;
    private Dialog      dialog;
    private TextSpeeds  speed;
    private double      currentSpeed;
    private int         xTextPosition;
    private int         yTextPosition;
    private ArrayList<TextureRegion> characterInformation;
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    private String text;
    
    public DialogWriter(Engine engine, Dialog dialog, TextSpeeds textSpeeds, String text) {
        setBatch(engine.getBatch());
        setDialog(dialog);
        setSpeed(textSpeeds);
        setText(text);
    }
    
    public static double getScale() {
        return scale;
    }
    
    public SpriteBatch getBatch() {
        return batch;
    }
    
    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }
    
    public Dialog getDialog() {
        return dialog;
    }
    
    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }
    
    public TextSpeeds getSpeed() {
        return speed;
    }
    
    public void setSpeed(TextSpeeds textSpeed) {
        speed = textSpeed;
    }
    
    public double getCurrentSpeed() {
        return currentSpeed;
    }
    
    public void setCurrentSpeed(double currentSpeed) {
        this.currentSpeed = currentSpeed;
    }
    
    public int getxTextPosition() {
        return xTextPosition;
    }
    
    public void setxTextPosition(int xTextPosition) {
        this.xTextPosition = xTextPosition;
    }
    
    public int getyTextPosition() {
        return yTextPosition;
    }
    
    public void setyTextPosition(int yTextPosition) {
        this.yTextPosition = yTextPosition;
    }
    
    public ArrayList<TextureRegion> getCharacterInformation() {
        return characterInformation;
    }
    
    public void setCharacterInformation(ArrayList<TextureRegion> characterInformation) {
        this.characterInformation = characterInformation;
    }
    
    public void drawText(char... text) {
        setCurrentSpeed(getSpeed().getSpeed());
        setCharacterInformation(new ArrayList<>());
        for(char primitiveCharacter : text) {
            getCharacterInformation().add(getCharacterTextureRegion(primitiveCharacter).getLetterTextureRegion());
        }
        setxTextPosition(getDialog().getDialogPosition()[0]);
        setyTextPosition(getDialog().getDialogPosition()[1]);
        setyTextPosition((int)(getyTextPosition() - 10 * getScale()));
        for(TextureRegion charInfo : getCharacterInformation()) {
            getDialog().addAction(Actions.sequence(new Action() {
                @Override
                public boolean act(float delta) {
                    getBatch().begin();
                    getBatch().draw(charInfo, getDialog().getxPosition(), getDialog().getyPosition());
                    getBatch().end();
                    return true;
                }
            }, new DelayAction((float)(getCurrentSpeed()))));
        }
                /*Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        if(xTextPosition + 2 * (charInfo.getRegionWidth() == 3 ? 8 * scale : 6 * scale) >=
                           dialog.getDialogPosition()[0] + dialog.getXSize()) {
                            xTextPosition = dialog.getDialogPosition()[0];
                            yTextPosition += 10 * scale;
                        }
                        xTextPosition += charInfo.getRegionWidth() == 3 ? 8 * scale : 6 * scale;
                        Texture textImage = new Texture(Gdx.files.internal(Characters.getFontPath()));
                        //ImageView character = new ImageView();
                        //character.setImage(textImage);
                        //Rectangle2D viewportRect = new Rectangle2D(charInfo[0], charInfo[1], charInfo[2], charInfo[3]);
                        //character.setViewport(viewportRect);
                        //character.setFitWidth(charInfo[2] * scale);
                        //character.setFitHeight(charInfo[3] * scale);
                        //character.relocate(xTextPosition, yTextPosition);
                        //Platform.runLater(() -> pane.getChildren().add(character));
                        batch.draw(textImage, 400, 400);
                        //batch.draw(textImage, xTextPosition, yTextPosition, charInfo[0], charInfo[1], charInfo[2], charInfo[3]);
                    }
                }, (float)(currentSpeed * 1));*/
        //}
        //try {
        //} catch(Exception e) {
        //    e.printStackTrace();
        //}
    }
    
    private Characters getCharacterTextureRegion(char primitiveCharacter) {
        return (primitiveCharacter == ' ') ? Characters.SPACE : (primitiveCharacter == 'A') ? Characters.A_UPPER :
                                                                (primitiveCharacter == 'a') ? Characters.A_LOWER :
                                                                (primitiveCharacter == 'B') ? Characters.B_UPPER :
                                                                (primitiveCharacter == 'b') ? Characters.B_LOWER :
                                                                (primitiveCharacter == 'C') ? Characters.C_UPPER :
                                                                (primitiveCharacter == 'c') ? Characters.C_LOWER :
                                                                (primitiveCharacter == 'D') ? Characters.D_UPPER :
                                                                (primitiveCharacter == 'd') ? Characters.D_LOWER :
                                                                (primitiveCharacter == 'E') ? Characters.E_UPPER :
                                                                (primitiveCharacter == 'e') ? Characters.E_LOWER :
                                                                (primitiveCharacter == 'F') ? Characters.F_UPPER :
                                                                (primitiveCharacter == 'f') ? Characters.F_LOWER :
                                                                (primitiveCharacter == 'G') ? Characters.G_UPPER :
                                                                (primitiveCharacter == 'g') ? Characters.G_LOWER :
                                                                (primitiveCharacter == 'H') ? Characters.H_UPPER :
                                                                (primitiveCharacter == 'h') ? Characters.H_LOWER :
                                                                (primitiveCharacter == 'I') ? Characters.I_UPPER :
                                                                (primitiveCharacter == 'i') ? Characters.I_LOWER :
                                                                (primitiveCharacter == 'J') ? Characters.J_UPPER :
                                                                (primitiveCharacter == 'j') ? Characters.J_LOWER :
                                                                (primitiveCharacter == 'K') ? Characters.K_UPPER :
                                                                (primitiveCharacter == 'k') ? Characters.K_LOWER :
                                                                (primitiveCharacter == 'L') ? Characters.L_UPPER :
                                                                (primitiveCharacter == 'l') ? Characters.L_LOWER :
                                                                (primitiveCharacter == 'M') ? Characters.M_UPPER :
                                                                (primitiveCharacter == 'm') ? Characters.M_LOWER :
                                                                (primitiveCharacter == 'N') ? Characters.N_UPPER :
                                                                (primitiveCharacter == 'n') ? Characters.N_LOWER :
                                                                (primitiveCharacter == 'O') ? Characters.O_UPPER :
                                                                (primitiveCharacter == 'o') ? Characters.O_LOWER :
                                                                (primitiveCharacter == 'P') ? Characters.P_UPPER :
                                                                (primitiveCharacter == 'p') ? Characters.P_LOWER :
                                                                (primitiveCharacter == 'Q') ? Characters.Q_UPPER :
                                                                (primitiveCharacter == 'q') ? Characters.Q_LOWER :
                                                                (primitiveCharacter == 'R') ? Characters.R_UPPER :
                                                                (primitiveCharacter == 'r') ? Characters.R_LOWER :
                                                                (primitiveCharacter == 'S') ? Characters.S_UPPER :
                                                                (primitiveCharacter == 's') ? Characters.S_LOWER :
                                                                (primitiveCharacter == 'T') ? Characters.T_UPPER :
                                                                (primitiveCharacter == 't') ? Characters.T_LOWER :
                                                                (primitiveCharacter == 'U') ? Characters.U_UPPER :
                                                                (primitiveCharacter == 'u') ? Characters.U_LOWER :
                                                                (primitiveCharacter == 'V') ? Characters.V_UPPER :
                                                                (primitiveCharacter == 'v') ? Characters.V_LOWER :
                                                                (primitiveCharacter == 'W') ? Characters.W_UPPER :
                                                                (primitiveCharacter == 'w') ? Characters.W_LOWER :
                                                                (primitiveCharacter == 'X') ? Characters.X_UPPER :
                                                                (primitiveCharacter == 'x') ? Characters.X_LOWER :
                                                                (primitiveCharacter == 'Y') ? Characters.Y_UPPER :
                                                                (primitiveCharacter == 'y') ? Characters.Y_LOWER :
                                                                (primitiveCharacter == 'Z') ? Characters.Z_UPPER :
                                                                (primitiveCharacter == 'z') ? Characters.Z_LOWER :
                                                                (primitiveCharacter == '0') ? Characters.ZERO :
                                                                (primitiveCharacter == '1') ? Characters.ONE :
                                                                (primitiveCharacter == '2') ? Characters.TWO :
                                                                (primitiveCharacter == '3') ? Characters.THREE :
                                                                (primitiveCharacter == '4') ? Characters.FOUR :
                                                                (primitiveCharacter == '5') ? Characters.FIVE :
                                                                (primitiveCharacter == '6') ? Characters.SIX :
                                                                (primitiveCharacter == '7') ? Characters.SEVEN :
                                                                (primitiveCharacter == '8') ? Characters.EIGHT :
                                                                (primitiveCharacter == '9') ? Characters.NINE :
                                                                Characters.SPACE;
    }
    
    @Override
    public boolean act(float delta) {
        setCurrentSpeed(getSpeed().getSpeed());
        setCharacterInformation(new ArrayList<>());
        for(char primitiveCharacter : getText().toCharArray()) {
            getCharacterInformation().add(getCharacterTextureRegion(primitiveCharacter).getLetterTextureRegion());
        }
        setxTextPosition(getDialog().getDialogPosition()[0]);
        setyTextPosition(getDialog().getDialogPosition()[1]);
        setyTextPosition((int)(getyTextPosition() - 10 * getScale()));
        for(TextureRegion charInfo : getCharacterInformation()) {
            getDialog().addAction(Actions.sequence(new Action() {
                @Override
                public boolean act(float delta) {
                    getBatch().begin();
                    getBatch().draw(charInfo, getDialog().getxPosition(), getDialog().getyPosition());
                    getBatch().end();
                    return true;
                }
            }, new DelayAction((float)(getCurrentSpeed()))));
        }
        return true;
    }
}