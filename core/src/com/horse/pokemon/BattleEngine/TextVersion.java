package com.horse.pokemon.BattleEngine;

import com.horse.pokemon.DataReaders.PokemonDataReader;
import com.horse.pokemon.ObjectData.PokemonData.Pokemon;

public class TextVersion {
    public static void main(String[] args) {
        Pokemon userPokemon  = PokemonDataReader.getPokemon("Ivysaur");
        Pokemon enemyPokemon = PokemonDataReader.getPokemon("Bulbasaur");
        
        userPokemon.getInformation().setCurrentLevel(20);
        enemyPokemon.getInformation().setCurrentLevel(5);
    
        userPokemon.updateStats();
        enemyPokemon.updateStats();
    }
}
