package com.horse.pokemon.ObjectData.PokemonData;

/**
 * All the pokemonTypes a PokemonData can possibly be.  All PokemonData can have a max of
 * two pokemonTypes, but it is possible for a PokemonData to have only one type.
 * A PokemonData's type affects how effective an opposing player's attack is to
 * itself, doing as much as four times the normal damage, and possibly nothing at
 * all, which is always good to keep track of so every attack will be worth the whole turn.
 */
public enum PokemonTypes {
    NORMAL, FIRE, WATER, ELECTRIC, GRASS, ICE, FIGHT, POISON, GROUND, FLYING, PSYCHIC, BUG, ROCK, GHOST, DRAGON, DARK, STEEL, FAIRY
}
