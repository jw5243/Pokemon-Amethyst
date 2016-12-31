package com.horse.pokemon.ObjectData.Players;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.horse.pokemon.GraphicsEngine.ScreenEngine.MainGameScreen;
import com.horse.pokemon.ObjectData.PokemonData.Pokemon;

/**
 * The base class for all new characters in the game.
 *
 * @author Horse
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractPlayer extends Actor {
    /**
     * The max amount of slots with PokemonData a Player can have at one time.
     */
    private static final int MAX_POKEMON = 6;
    /**
     * The {@link String} instance representing a Player's name.
     */
    private String name;
    /**
     * The {@link Pokemon} array instance representing the Player's PokemonData slots.
     */
    private Pokemon[] currentPokemon = new Pokemon[MAX_POKEMON];
    
    protected AbstractPlayer(MainGameScreen screen, String fileName) {
        //super(screen.getAtlas().findRegion(fileName));
    }
    
    /**
     * Returns the max number of PokemonData a Player can have at one time.
     *
     * @return {@link #MAX_POKEMON}
     */
    public static int getMaxPokemon() {
        return MAX_POKEMON;
    }
    
    abstract void update(float deltaTime);
    
    /**
     * Sets a specific index of the {@link #currentPokemon} instance.
     *
     * @param currentPokemon
     *         {@link #currentPokemon}
     * @param Index
     *         {@link Integer} representing the index of the PokemonData slot to set or replace.
     */
    public void setCurrentPokemon(Pokemon currentPokemon, int Index) {
        this.currentPokemon[Index] = currentPokemon;
    }
    
    /**
     * Returns the Player's name.
     *
     * @return {@link #name}
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the {@link #name}.
     *
     * @param name
     *         {@link #name}
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Returns the Player's current PokemonData.
     *
     * @return {@link #currentPokemon}
     */
    public Pokemon[] getCurrentPokemon() {
        return currentPokemon;
    }
    
    /**
     * Sets the {@link #currentPokemon} (All slots).
     *
     * @param currentPokemon
     *         {@link #currentPokemon}
     */
    public void setCurrentPokemon(Pokemon[] currentPokemon) {
        this.currentPokemon = currentPokemon;
    }
}