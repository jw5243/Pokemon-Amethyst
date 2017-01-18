package com.horse.pokemon.ObjectData.PokemonData.CalculateStats;

@FunctionalInterface
public interface CalculateStatInterface {
    int calculateStat(int level, int baseStatValue, int individualValue, int effortValue);
}
