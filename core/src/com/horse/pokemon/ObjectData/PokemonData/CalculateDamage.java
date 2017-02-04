package com.horse.pokemon.ObjectData.PokemonData;

import com.horse.pokemon.DataReaders.MoveDataReader;
import com.horse.pokemon.DataReaders.PokemonDataReader;
import com.horse.utility.Utility;

/**
 * Class for calculating the amount of hitpoints to deduct off a {@link Pokemon} when a {@link Pokemon} uses one of its {@link Moves}.
 *
 * @see Pokemon
 * @see Moves
 */
public class CalculateDamage {
    public static void main(String[] args) {
        Pokemon userPokemon  = PokemonDataReader.getPokemon("Ivysaur");
        Pokemon enemyPokemon = PokemonDataReader.getPokemon("Bulbasaur");
        Moves   userMove     = MoveDataReader.getMove("Absorb");
        System.out.println(CalculateDamage.getDamage(userPokemon, enemyPokemon, userMove));
    }
    
    public static int getDamage(Pokemon userPokemon, Pokemon enemyPokemon, Moves moveUsed) {
        return (int)(((((userPokemon.getInformation().getCurrentLevel() << 1) / 5 + 2) *
                       userPokemon.getInformation().getCurrentAttack() * moveUsed.getBasePower() /
                       enemyPokemon.getInformation().getCurrentDefense()) / 50 + 2) * 1.0f * 1.0f *
                     Utility.generateNumber(85, 100) / 100);
    }
}