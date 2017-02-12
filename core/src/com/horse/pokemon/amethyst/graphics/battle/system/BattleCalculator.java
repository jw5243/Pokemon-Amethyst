package com.horse.pokemon.amethyst.graphics.battle.system;

import com.horse.pokemon.amethyst.data.pokemon.CalculateDamage;
import com.horse.pokemon.amethyst.data.pokemon.CalculateResistance;
import com.horse.pokemon.amethyst.data.pokemon.CalculateStats;
import com.horse.pokemon.amethyst.data.pokemon.Moves;
import com.horse.pokemon.amethyst.data.pokemon.Pokemon;
import com.horse.pokemon.amethyst.data.pokemon.PokemonExperience;

public class BattleCalculator {
    public static int calculateDamage(final Pokemon attackingPokemon, final Pokemon defendingPokemon,
                                      final Moves attackingPokemonMove) {
        return CalculateDamage.getDamage(attackingPokemon, defendingPokemon, attackingPokemonMove);
    }
    
    public static int[] calculateStats(final Pokemon pokemon) {
        return CalculateStats.getStats(pokemon);
    }
    
    public static boolean isNextLevel(final Pokemon pokemonGainingExperience, final Pokemon pokemonGivingExperience) {
        return pokemonGainingExperience.getInformation().getCurrentExperience().getCurrentExperience() +
               PokemonExperience.calculateExperienceWon(pokemonGainingExperience, pokemonGivingExperience) >=
               PokemonExperience
                   .getExperienceBasedOnLevelAndType(pokemonGainingExperience.getInformation().getExperienceRate(),
                                                     pokemonGainingExperience.getInformation().getCurrentLevel()
                   );
    }
    
    public static float calculateResistance(final Moves attackingMove, final Pokemon defendingPokemon) {
        return CalculateResistance.calculate(attackingMove, defendingPokemon);
    }
}
