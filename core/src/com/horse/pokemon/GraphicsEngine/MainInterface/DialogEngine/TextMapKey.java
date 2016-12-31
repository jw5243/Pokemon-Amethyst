package com.horse.pokemon.GraphicsEngine.MainInterface.DialogEngine;

import com.horse.pokemon.Enums.DialogBoxes;
import com.horse.pokemon.GraphicsEngine.ScreenEngine.MainGameScreen;

import java.util.Objects;

/**
 * TextMapKey stores two sets of information that is used for checking
 * which type of letter to draw into the application screen.  The stored
 * information is used as a key in the abstract text map class, to prevent
 * nesting HashMaps, and instead creating a custom key object.
 *
 * @version 1.0
 * @see javafx.application.Application
 * @see MainGameScreen
 * @see AbstractTextMap#textMap
 * @see Character
 * @see DialogBoxes
 * @see java.util.HashMap
 * @see Objects#hash(Object...)
 * @see Object#hashCode()
 * @since 1.0
 */
public class TextMapKey {
    /**
     * Declares a {@link Character} value used to store the letter that will be
     * printed to the {@link javafx.application.Application} screen.
     *
     * @see Character
     */
    private char character;
    
    /**
     * A {@link DialogBoxes} instance used to store the type of dialog so that
     * the correct letter type is drawn.
     *
     * @see DialogBoxes
     */
    private DialogBoxes dialogType;
    
    /**
     * Class Constructor initializing {@link #character} and {@link #dialogType}.
     *
     * @param character
     *         The letter that is to be drawn.
     * @param dialogType
     *         The type of letter that is to be drawn/
     */
    public TextMapKey(char character, DialogBoxes dialogType) {
        this.character = character;
        this.dialogType = dialogType;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(character, dialogType);
    }
    
    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof TextMapKey)) {
            return false;
        }
        TextMapKey textMapKey = (TextMapKey)o;
        return character == textMapKey.character && dialogType == textMapKey.dialogType;
    }
    
    @Override
    public String toString() {
        return "(" + this.character + ", " + this.dialogType + ")";
    }
}