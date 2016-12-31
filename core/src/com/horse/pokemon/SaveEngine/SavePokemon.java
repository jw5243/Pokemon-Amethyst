package com.horse.pokemon.SaveEngine;

import com.horse.pokemon.ObjectData.PokemonData.Pokemon;

public class SavePokemon {
    public SavePokemon(Pokemon... userPokemon) {
        if(userPokemon.length != 6) {
            try {
                throw new Exception(String.format("Invalid number of PokemonData.  Given %s.  Remember " +
                                                  "to return the keyword \"null\" if not all slots are filled.",
                                                  userPokemon.length
                ));
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}