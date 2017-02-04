package com.horse.pokemon.BattleEngine;

import com.horse.pokemon.DataReaders.PokemonDataReader;
import com.horse.pokemon.ObjectData.PokemonData.Pokemon;

import java.util.Scanner;

public class TextVersion {
    public static void main(String[] args) {
        boolean running = true;
        
        Pokemon userPokemon  = PokemonDataReader.getPokemon("Ivysaur");
        Pokemon enemyPokemon = PokemonDataReader.getPokemon("Bulbasaur");
    
        userPokemon.updateLevel(20);
        enemyPokemon.updateLevel(5);
    
        Scanner scanner = new Scanner(System.in);
        while(running) {
            System.out.println("Your Pokemon's Stats:");
            System.out.println("\t" + userPokemon.getInformation().getName() + " Health: " +
                               +userPokemon.getInformation().getBaseStats()[0] + "/" +
                               userPokemon.getInformation().getCurrentHealth());
            
            String input = scanner.next();
            if(input.equalsIgnoreCase("Quit")) {
                running = false;
            }
        }
    }
}