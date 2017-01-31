package com.horse.pokemon.BattleEngine;

import com.horse.pokemon.ObjectData.PokemonData.CalculateDamage;
import com.horse.pokemon.ObjectData.PokemonData.CalculateStats;
import com.horse.pokemon.ObjectData.PokemonData.Moves;
import com.horse.pokemon.ObjectData.PokemonData.Pokemon;
import com.horse.pokemon.ObjectData.PokemonData.PokemonExperience;

public class BattleCalculator {
    public static int calculateDamage(final Pokemon attackingPokemon, final Pokemon defendingPokemon, final Moves attackingPokemonMove) {
        return CalculateDamage.getDamage(attackingPokemon, defendingPokemon, attackingPokemonMove);
    }
    
    public static int[] calculateStats(final Pokemon pokemon) {
        return CalculateStats.getStats(pokemon);
    }
    
    @SuppressWarnings("Not Finished, need to apply formula of winning a battle.")
    public static boolean nextLevel(final Pokemon pokemonGainingExperience, final Pokemon pokemonGivingExperience) {
        return pokemonGainingExperience.getInformation().getCurrentExperience().getCurrentExperience() +
               pokemonGivingExperience.getInformation().getCurrentExperience().getCurrentExperience() >=
               PokemonExperience.getExperienceBasedOnLevelAndType(pokemonGainingExperience.getInformation().getExperienceRate(), pokemonGainingExperience.getInformation().getCurrentLevel());
    }
}
