package com.horse.pokemon.ObjectData.PokemonData;

import com.horse.pokemon.DataReaders.PokemonDataReader;

public class CalculateStats {
    private int[]           stats;
    private int             currentLevel;
    private int             health;
    private int             attack;
    private int             defense;
    private int             specialAttack;
    private int             specialDefense;
    private int             speed;
    private IndividualValue individualValue;
    private EffortValue     effortValue;
    
    public CalculateStats(int[] stats, int currentLevel, IndividualValue individualValue, EffortValue effortValue) {
        this.stats = new int[6];
        this.currentLevel = currentLevel;
        this.individualValue = individualValue;
        this.effortValue = effortValue;
        health = stats[0];
        attack = stats[1];
        defense = stats[2];
        specialAttack = stats[3];
        specialDefense = stats[4];
        speed = stats[5];
    }
    
    public static void main(String[] args) {
        Pokemon pokemon = PokemonDataReader.getPokemon("Bulbasaur");
        for(int i = 1; i <= 100; i++) {
            pokemon.getInformation().setCurrentLevel(i);
            CalculateStats calculateStats = new CalculateStats(pokemon.getInformation().getBaseStats(), pokemon.getInformation().getCurrentLevel(),
                                                               pokemon.getInformation().getIndividualValue(), pokemon.getInformation().getEffortValue()
            );
            int[] stats = calculateStats.getStats();
            System.out.println(String.format("{Level = %s, Health = %s, Attack = %s, Defense = %s, Special Attack = %s, Special Defense = %s, Speed = %s}",
                                             pokemon.getInformation().getCurrentLevel(), stats[0], stats[1], stats[2], stats[3], stats[4], stats[5]
            ));
        }
        System.out.println(pokemon.getInformation().getIndividualValue());
    }
    
    public int[] getStats() {
        stats[0] = calculateStat(true, health, individualValue.getHealth(), effortValue.getHealthEV());
        stats[1] = calculateStat(false, attack, individualValue.getAttack(), effortValue.getAttackEV());
        stats[2] = calculateStat(false, defense, individualValue.getDefense(), effortValue.getDefenseEV());
        stats[3] = calculateStat(false, specialAttack, individualValue.getSpecialAttack(), effortValue.getSpecialAttackEV());
        stats[4] = calculateStat(false, specialDefense, individualValue.getSpecialDefense(), effortValue.getSpecialDefenseEV());
        stats[5] = calculateStat(false, speed, individualValue.getSpeed(), effortValue.getSpeedEV());
        return stats;
    }
    
    private int calculateStat(boolean healthStat, int statValue, int individualValueStat, int effortValueStat) {
        if(healthStat) {
            return ((2 * statValue + individualValueStat + (effortValueStat / 4)) * currentLevel / 100) + 10 + currentLevel;
        } else {
            return (((2 * statValue + individualValueStat + (effortValueStat / 4)) * currentLevel / 100) + 5) * 1;
        }
    }
}