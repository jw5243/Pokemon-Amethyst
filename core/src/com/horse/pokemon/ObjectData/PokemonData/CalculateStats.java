package com.horse.pokemon.ObjectData.PokemonData;

import com.horse.pokemon.DataReaders.PokemonDataReader;

public class CalculateStats {
    public static void main(String[] args) {
        Pokemon pokemon = PokemonDataReader.getPokemon("Bulbasaur");
        
        StringBuilder stringBuilder = new StringBuilder();
        long          sum           = 0;
        for(int i = 1; i <= 100; i++) {
            pokemon.getInformation().setCurrentLevel(i);
            
            long start = System.nanoTime();
            
            int[] stats = CalculateStats.getStats(pokemon);
            
            long end = System.nanoTime();
            sum += end - start;
            
            stringBuilder.append("{Level = ").append(pokemon.getInformation().getCurrentLevel()).append(", Health = ").append(stats[0]).append(", Attack = ").append(stats[1])
                         .append(", Defense = ").append(stats[2]).append(", Special Attack = ").append(stats[3]).append(", Special Defense = ").append(stats[4]).append(", Speed = ")
                         .append(stats[5]).append("}");
            
            System.out.println(stringBuilder.toString());
            
            stringBuilder.delete(0, stringBuilder.length());
        }
        System.out.println(pokemon.getInformation().getIndividualValue());
        System.out.println("Average Time in Nanoseconds: " + (sum / 100));
    }
    
    public static int[] getStats(final Pokemon pokemon) {
        final int[] stats            = new int[6];
        final int[] baseStats        = pokemon.getInformation().getBaseStats();
        final int[] effortValues     = pokemon.getInformation().getEffortValue().getEVs();
        final int[] individualValues = pokemon.getInformation().getIndividualValue().getIndividualVales();
        final int   level            = pokemon.getInformation().getCurrentLevel();
        final float levelAsPercent   = (float)(level) / 100f;
        for(int index = 0; index < stats.length; index++) {
            final int baseValue = (int)(((baseStats[index] << 1) + individualValues[index] + (effortValues[index] >> 2)) * levelAsPercent);
            if(index != 0) {
                stats[index] = (baseValue + 5);// * natureValue;
            } else {
                stats[index] = baseValue + 10 + level;
            }
        }
        
        return stats;
    }
}