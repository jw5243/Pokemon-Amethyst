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
    
    public static boolean isNextLevel(final Pokemon pokemonGainingExperience, final Pokemon pokemonGivingExperience) {
        return pokemonGainingExperience.getInformation().getCurrentExperience().getCurrentExperience() +
               PokemonExperience.calculateExperienceWon(pokemonGainingExperience, pokemonGivingExperience) >=
               PokemonExperience.getExperienceBasedOnLevelAndType(pokemonGainingExperience.getInformation().getExperienceRate(), pokemonGainingExperience.getInformation().getCurrentLevel());
    }
}
