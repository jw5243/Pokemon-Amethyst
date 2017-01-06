package com.horse.pokemon.GraphicsEngine.MainInterface.DialogEngine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public enum DialogCharacter {
    A_UPPER(0, 13), A_LOWER(0, 26), B_UPPER(6, 13), B_LOWER(6, 26), C_UPPER(12, 13), C_LOWER(12, 26), D_UPPER(18, 13), D_LOWER(18, 26), E_UPPER(24, 13), E_LOWER(24, 26),
    F_UPPER(30, 13), F_LOWER(30, 26, 5), G_UPPER(36, 13), G_LOWER(36, 26), H_UPPER(42, 13), H_LOWER(42, 26), I_UPPER(49, 13, 4), I_LOWER(50, 26, 2), J_UPPER(54, 13),
    J_LOWER(54, 26, 5), K_UPPER(60, 13), K_LOWER(60, 26), L_UPPER(66, 13), L_LOWER(67, 26, 3), M_UPPER(72, 13), M_LOWER(72, 26), N_UPPER(78, 13), N_LOWER(78, 26), O_UPPER(84, 13),
    O_LOWER(84, 26), P_UPPER(90, 13), P_LOWER(90, 26), Q_UPPER(96, 13), Q_LOWER(96, 26), R_UPPER(102, 13), R_LOWER(102, 26), S_UPPER(108, 13), S_LOWER(108, 26), T_UPPER(114, 13),
    T_LOWER(114, 26, 5), U_UPPER(120, 13), U_LOWER(120, 26), V_UPPER(126, 13), V_LOWER(126, 26), W_UPPER(132, 13), W_LOWER(132, 26), X_UPPER(138, 13), X_LOWER(138, 26),
    Y_UPPER(144, 13), Y_LOWER(144, 26), Z_UPPER(150, 13), Z_LOWER(150, 26), ZERO(0, 0, 6), ONE(6, 0, 4), TWO(12, 0), THREE(18, 0), FOUR(24, 0), FIVE(30, 0), SIX(36, 0), SEVEN(42, 0),
    EIGHT(48, 0), NINE(54, 0), SPACE(108, 0, 3);
    
    private static final String  FONT_PATH      = "Fonts\\dialogFont.png";
    private static final int     DEFAULT_WIDTH  = 6;
    private static final int     DEFAULT_HEIGHT = 13;
    private static final Texture FONT_TEXTURE   = new Texture(Gdx.files.internal(getFontPath()));
    private int x;
    private int y;
    private int width;
    private int height;
    
    DialogCharacter(int x, int y) {
        this(x, y, getDefaultWidth());
    }
    
    DialogCharacter(int x, int y, int width) {
        this(x, y, width, getDefaultHeight());
    }
    
    DialogCharacter(int x, int y, int width, int height) {
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
    }
    
    public static Texture getFontTexture() {
        return FONT_TEXTURE;
    }
    
    public static int getDefaultWidth() {
        return DEFAULT_WIDTH;
    }
    
    public static int getDefaultHeight() {
        return DEFAULT_HEIGHT;
    }
    
    public static String getFontPath() {
        return FONT_PATH;
    }
    
    public TextureRegion getLetterTextureRegion() {
        return new TextureRegion(getFontTexture(), getX(), getY(), getWidth(), getHeight());
    }
    
    public int getX() {
        return x;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public int getY() {
        return y;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public int getWidth() {
        return width;
    }
    
    public void setWidth(int width) {
        this.width = width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }
}
