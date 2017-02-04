package com.horse.pokemon.GraphicsEngine.MainInterface.DialogEngine;

import com.badlogic.gdx.graphics.g2d.Batch;

public class CharacterWriter {
    private static final int DEFAULT_CHARACTER_START_X_POSITION_BUFFER = 24;
    private static final int DEFAULT_CHARACTER_START_Y_POSITION_BUFFER = 6;
    private static final int DEFAULT_CHARACTER_END_X_POSITION_BUFFER   = 40;
    private DialogCharacter dialogCharacter;
    private int             characterXPosition;
    private int             characterYPosition;
    private int             characterWidth;
    private int             characterHeight;
    
    public CharacterWriter(char character, int characterXPosition, int characterYPosition) {
        setDialogCharacter(getCharacterTextureRegion(character));
        setCharacterXPosition(characterXPosition);
        setCharacterYPosition(characterYPosition);
        setCharacterWidth(getDialogCharacter().getWidth());
        setCharacterHeight(getDialogCharacter().getHeight());
    }
    
    public static int getDefaultCharacterEndXPositionBuffer() {
        return DEFAULT_CHARACTER_END_X_POSITION_BUFFER;
    }
    
    public static int getDefaultCharacterStartXPositionBuffer() {
        return DEFAULT_CHARACTER_START_X_POSITION_BUFFER;
    }
    
    public static int getDefaultCharacterStartYPositionBuffer() {
        return DEFAULT_CHARACTER_START_Y_POSITION_BUFFER;
    }
    
    public DialogCharacter getDialogCharacter() {
        return dialogCharacter;
    }
    
    public void setDialogCharacter(DialogCharacter dialogCharacter) {
        this.dialogCharacter = dialogCharacter;
    }
    
    public int getCharacterXPosition() {
        return characterXPosition;
    }
    
    public void setCharacterXPosition(int characterXPosition) {
        this.characterXPosition = characterXPosition;
    }
    
    public int getCharacterYPosition() {
        return characterYPosition;
    }
    
    public void setCharacterYPosition(int characterYPosition) {
        this.characterYPosition = characterYPosition;
    }
    
    public int getCharacterWidth() {
        return characterWidth;
    }
    
    public void setCharacterWidth(int characterWidth) {
        this.characterWidth = characterWidth;
    }
    
    public int getCharacterHeight() {
        return characterHeight;
    }
    
    public void setCharacterHeight(int characterHeight) {
        this.characterHeight = characterHeight;
    }
    
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(getDialogCharacter().getLetterTextureRegion(), getCharacterXPosition(), getCharacterYPosition(),
                   getCharacterWidth(), getCharacterHeight()
        );
    }
    
    private DialogCharacter getCharacterTextureRegion(char primitiveCharacter) {
        return (primitiveCharacter == ' ') ? DialogCharacter.SPACE :
               (primitiveCharacter == 'A') ? DialogCharacter.A_UPPER :
               (primitiveCharacter == 'a') ? DialogCharacter.A_LOWER :
               (primitiveCharacter == 'B') ? DialogCharacter.B_UPPER :
               (primitiveCharacter == 'b') ? DialogCharacter.B_LOWER :
               (primitiveCharacter == 'C') ? DialogCharacter.C_UPPER :
               (primitiveCharacter == 'c') ? DialogCharacter.C_LOWER :
               (primitiveCharacter == 'D') ? DialogCharacter.D_UPPER :
               (primitiveCharacter == 'd') ? DialogCharacter.D_LOWER :
               (primitiveCharacter == 'E') ? DialogCharacter.E_UPPER :
               (primitiveCharacter == 'e') ? DialogCharacter.E_LOWER :
               (primitiveCharacter == 'F') ? DialogCharacter.F_UPPER :
               (primitiveCharacter == 'f') ? DialogCharacter.F_LOWER :
               (primitiveCharacter == 'G') ? DialogCharacter.G_UPPER :
               (primitiveCharacter == 'g') ? DialogCharacter.G_LOWER :
               (primitiveCharacter == 'H') ? DialogCharacter.H_UPPER :
               (primitiveCharacter == 'h') ? DialogCharacter.H_LOWER :
               (primitiveCharacter == 'I') ? DialogCharacter.I_UPPER :
               (primitiveCharacter == 'i') ? DialogCharacter.I_LOWER :
               (primitiveCharacter == 'J') ? DialogCharacter.J_UPPER :
               (primitiveCharacter == 'j') ? DialogCharacter.J_LOWER :
               (primitiveCharacter == 'K') ? DialogCharacter.K_UPPER :
               (primitiveCharacter == 'k') ? DialogCharacter.K_LOWER :
               (primitiveCharacter == 'L') ? DialogCharacter.L_UPPER :
               (primitiveCharacter == 'l') ? DialogCharacter.L_LOWER :
               (primitiveCharacter == 'M') ? DialogCharacter.M_UPPER :
               (primitiveCharacter == 'm') ? DialogCharacter.M_LOWER :
               (primitiveCharacter == 'N') ? DialogCharacter.N_UPPER :
               (primitiveCharacter == 'n') ? DialogCharacter.N_LOWER :
               (primitiveCharacter == 'O') ? DialogCharacter.O_UPPER :
               (primitiveCharacter == 'o') ? DialogCharacter.O_LOWER :
               (primitiveCharacter == 'P') ? DialogCharacter.P_UPPER :
               (primitiveCharacter == 'p') ? DialogCharacter.P_LOWER :
               (primitiveCharacter == 'Q') ? DialogCharacter.Q_UPPER :
               (primitiveCharacter == 'q') ? DialogCharacter.Q_LOWER :
               (primitiveCharacter == 'R') ? DialogCharacter.R_UPPER :
               (primitiveCharacter == 'r') ? DialogCharacter.R_LOWER :
               (primitiveCharacter == 'S') ? DialogCharacter.S_UPPER :
               (primitiveCharacter == 's') ? DialogCharacter.S_LOWER :
               (primitiveCharacter == 'T') ? DialogCharacter.T_UPPER :
               (primitiveCharacter == 't') ? DialogCharacter.T_LOWER :
               (primitiveCharacter == 'U') ? DialogCharacter.U_UPPER :
               (primitiveCharacter == 'u') ? DialogCharacter.U_LOWER :
               (primitiveCharacter == 'V') ? DialogCharacter.V_UPPER :
               (primitiveCharacter == 'v') ? DialogCharacter.V_LOWER :
               (primitiveCharacter == 'W') ? DialogCharacter.W_UPPER :
               (primitiveCharacter == 'w') ? DialogCharacter.W_LOWER :
               (primitiveCharacter == 'X') ? DialogCharacter.X_UPPER :
               (primitiveCharacter == 'x') ? DialogCharacter.X_LOWER :
               (primitiveCharacter == 'Y') ? DialogCharacter.Y_UPPER :
               (primitiveCharacter == 'y') ? DialogCharacter.Y_LOWER :
               (primitiveCharacter == 'Z') ? DialogCharacter.Z_UPPER :
               (primitiveCharacter == 'z') ? DialogCharacter.Z_LOWER :
               (primitiveCharacter == '0') ? DialogCharacter.ZERO : (primitiveCharacter == '1') ? DialogCharacter.ONE :
                                                                    (primitiveCharacter == '2') ? DialogCharacter.TWO :
                                                                    (primitiveCharacter == '3') ?
                                                                    DialogCharacter.THREE :
                                                                    (primitiveCharacter == '4') ? DialogCharacter.FOUR :
                                                                    (primitiveCharacter == '5') ? DialogCharacter.FIVE :
                                                                    (primitiveCharacter == '6') ? DialogCharacter.SIX :
                                                                    (primitiveCharacter == '7') ?
                                                                    DialogCharacter.SEVEN :
                                                                    (primitiveCharacter == '8') ?
                                                                    DialogCharacter.EIGHT :
                                                                    (primitiveCharacter == '9') ? DialogCharacter.NINE :
                                                                    DialogCharacter.SPACE;
    }
}
