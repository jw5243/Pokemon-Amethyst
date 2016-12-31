package com.horse.pokemon.ObjectData.PokemonData;

import com.horse.pokemon.DataReaders.MoveDataReader;
import com.horse.pokemon.DataReaders.PokemonDataReader;
import com.horse.pokemon.RandomNumberGenerator;

public class CalculateDamage {
    private double userLevel;
    private double userAttack;
    private double movePower;
    private double enemyDefense;
    private double userSTAB;
    private double userResistance;
    private double randomNumber;
    
    public CalculateDamage(Pokemon userPokemon, Pokemon enemyPokemon, Moves moveUsed) {
        userLevel = userPokemon.getInformation().getCurrentLevel();
        userAttack = userPokemon.getInformation().getCurrentAttack();
        movePower = moveUsed.getBasePower();
        enemyDefense = enemyPokemon.getInformation().getCurrentDefense();
        userSTAB = 1;
        userResistance = 1;
        randomNumber = RandomNumberGenerator.generateNumber(85, 100);
    }
    
    public static void main(String[] args) {
        Pokemon         userPokemon  = PokemonDataReader.getPokemon("Ivysaur");
        Pokemon         enemyPokemon = PokemonDataReader.getPokemon("Bulbasaur");
        Moves           userMove     = MoveDataReader.getMove("Absorb");
        CalculateDamage calculator   = new CalculateDamage(userPokemon, enemyPokemon, userMove);
        System.out.println(calculator.getDamage());
    }
    
    public int getDamage() {
        return (int)((((2 * userLevel / 5 + 2) * userAttack * movePower / enemyDefense) / 50 + 2) * userSTAB *
                     userResistance * randomNumber / 100);
    }
}