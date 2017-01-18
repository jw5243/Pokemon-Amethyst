package com.horse.pokemon.ObjectData.PokemonData.CalculateStats;

import com.horse.pokemon.DataReaders.PokemonDataReader;
import com.horse.pokemon.ObjectData.PokemonData.Pokemon;

public class CalculateStats {
    public static void main(String[] args) {
        Pokemon pokemon = PokemonDataReader.getPokemon("Bulbasaur");
        for(int i = 1; i <= 100; i++) {
            pokemon.getInformation().setCurrentLevel(i);
    
            int[] stats = CalculateStats.getStats(pokemon);
            System.out.println(String.format("{Level = %s, Health = %s, Attack = %s, Defense = %s, Special Attack = %s, Special Defense = %s, Speed = %s}",
                                             pokemon.getInformation().getCurrentLevel(), stats[0], stats[1], stats[2], stats[3], stats[4], stats[5]
            ));
        }
        System.out.println(pokemon.getInformation().getIndividualValue());
    }
    
    public static int[] getStats(Pokemon pokemon) {
        CalculateStatInterface calculateHealth =
                (level, baseStatValue, individualValue, effortValue) -> ((2 * baseStatValue + individualValue + (effortValue / 4)) * level / 100) + 10 + level;
        CalculateStatInterface calculateOtherStat =
                (level, baseStatValue, individualValue, effortValue) -> (((2 * baseStatValue + individualValue + (effortValue / 4)) * level / 100) + 5) * 1;
        
        int[] stats = new int[6];
        stats[0] = calculateHealth.calculateStat(pokemon.getInformation().getCurrentLevel(), pokemon.getInformation().getBaseStats()[0],
                                                 pokemon.getInformation().getIndividualValue().getHealth(), pokemon.getInformation().getEffortValue().getHealthEV()
        );
        stats[1] = calculateOtherStat.calculateStat(pokemon.getInformation().getCurrentLevel(), pokemon.getInformation().getBaseStats()[1],
                                                    pokemon.getInformation().getIndividualValue().getAttack(), pokemon.getInformation().getEffortValue().getAttackEV()
        );
        stats[2] = calculateOtherStat.calculateStat(pokemon.getInformation().getCurrentLevel(), pokemon.getInformation().getBaseStats()[2],
                                                    pokemon.getInformation().getIndividualValue().getDefense(), pokemon.getInformation().getEffortValue().getDefenseEV()
        );
        stats[3] = calculateOtherStat.calculateStat(pokemon.getInformation().getCurrentLevel(), pokemon.getInformation().getBaseStats()[3],
                                                    pokemon.getInformation().getIndividualValue().getSpecialAttack(), pokemon.getInformation().getEffortValue().getSpecialAttackEV()
        );
        stats[4] = calculateOtherStat.calculateStat(pokemon.getInformation().getCurrentLevel(), pokemon.getInformation().getBaseStats()[4],
                                                    pokemon.getInformation().getIndividualValue().getSpecialDefense(), pokemon.getInformation().getEffortValue().getSpecialDefenseEV()
        );
        stats[5] = calculateOtherStat.calculateStat(pokemon.getInformation().getCurrentLevel(), pokemon.getInformation().getBaseStats()[5],
                                                    pokemon.getInformation().getIndividualValue().getSpeed(), pokemon.getInformation().getEffortValue().getSpeedEV()
        );
        
        return stats;
    }
}