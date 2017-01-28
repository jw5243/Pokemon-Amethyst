package com.horse.pokemon.DataReaders;

import com.horse.pokemon.ObjectData.PokemonData.EffortValue;
import com.horse.pokemon.ObjectData.PokemonData.ExperienceTypes;
import com.horse.pokemon.ObjectData.PokemonData.Moves;
import com.horse.pokemon.ObjectData.PokemonData.Pokemon;
import com.horse.pokemon.ObjectData.PokemonData.PokemonExperience;
import com.horse.pokemon.ObjectData.PokemonData.PokemonInformation;
import com.horse.pokemon.ObjectData.PokemonData.PokemonTypes;
import com.horse.pokemon.ObjectData.PokemonData.StatTypes;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public final class PokemonDataReader {
    /**
     * Data file containing all PokemonData information that is to be stored when the Constructor is called.
     */
    private static final File pokemonFileData = new File("core\\assets\\PokemonData\\PokemonData.dat");
    
    /**
     * Map for easy access to PokemonData when initialized by the no parameter Constructor.
     */
    private static HashMap<String, Pokemon> pokemonData;
    
    static {
        pokemonData = new HashMap<>();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(pokemonFileData))) {
            String pokemon = bufferedReader.readLine();
            while(pokemon != null) {
                String[] data = pokemon.split(";");
                pokemonData.put(data[0], getPokemon(data));
                pokemon = bufferedReader.readLine();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    private static Pokemon getPokemon(String[] data) {
        Pokemon pokemon = new Pokemon();
        
        pokemon.getInformation().setName(data[0]);
        pokemon.getInformation().setClassification(data[1].replaceAll("^\\s+|\\s+$", ""));
        pokemon.getInformation().setPokedexNumber(Integer.parseInt(data[2].replaceAll("^\\s+|\\s+$", "")));
        pokemon.getInformation().setEvolutionNumber(Integer.parseInt(data[3].replaceAll("^\\s+|\\s+$", "")));
        pokemon.getInformation().setGenderRatio(Double.parseDouble(data[4].replaceAll("^\\s+|\\s+$", "")));
        
        for(int index = 0; index < data.length; index++) {
            data[index] = data[index].trim();
        }
        String[] pokemonTypes = data[5].split(",");
    
        pokemon.getInformation().setPokemonTypes(pokemonTypes.length == 1 ? new PokemonTypes[] {PokemonTypes.valueOf(pokemonTypes[0].trim())} : new PokemonTypes[] {
                PokemonTypes.valueOf(pokemonTypes[0].trim()), PokemonTypes.valueOf(pokemonTypes[1].trim())
        });
        
        pokemon.getInformation().setHeight(data[6]);
        pokemon.getInformation().setWeight(data[7]);
        pokemon.getInformation().setCaptureRate(Integer.parseInt(data[8]));
        pokemon.getInformation().setBaseEggSteps(Integer.parseInt(data[9]));
        pokemon.getInformation().setAbility(data[10]);
        pokemon.getInformation().setExperienceRate(ExperienceTypes.valueOf(data[11]));
        pokemon.getInformation().setBaseHappiness(Integer.parseInt(data[12]));
    
        TIntObjectHashMap<Moves> moveList   = new TIntObjectHashMap<>();
        String[]                 moves      = data[13].split(",");
        ArrayList<String>        moveValues = new ArrayList<>(moves.length * 2);
        for(int moveIndex = 0; moveIndex < moves.length; moveIndex++) {
            for(String value : moves) {
                Collections.addAll(moveValues, value.split(":"));
            }
            for(int moveValueIndex = 0; moveValueIndex < moveValues.size(); moveValueIndex += 2) {
                moveList.put(Integer.parseInt(moveValues.get(moveValueIndex).trim()), MoveDataReader.getMove(moveValues.get(moveValueIndex + 1).trim()));
            }
        }
        pokemon.getInformation().setMoveList(moveList);
        
        EffortValue pokemonEffortValues = new EffortValue();
        
        String[]          effortSections = data[14].split(",");
        ArrayList<String> effortValue    = new ArrayList<>(effortSections.length * 2);
        for(String value : effortSections) {
            Collections.addAll(effortValue, value.split(":"));
        }
        for(int index = 0; index < effortValue.size(); index += 2) {
            EffortValueMethod effortValueMethod;
            StatTypes         statType = StatTypes.valueOf(effortValue.get(index).trim());
            effortValueMethod = (statType == StatTypes.HEALTH) ? pokemonEffortValues::setImmutableHealthEV : (statType == StatTypes.ATTACK) ? pokemonEffortValues::setImmutableAttackEV :
                                                                                                             (statType == StatTypes.DEFENSE) ? pokemonEffortValues::setImmutableDefenseEV :
                                                                                                             (statType == StatTypes.SPECIAL_ATTACK) ?
                                                                                                             pokemonEffortValues::setImmutableSpecialAttackEV :
                                                                                                             (statType == StatTypes.SPECIAL_DEFENSE) ?
                                                                                                             pokemonEffortValues::setImmutableSpecialDefenseEV :
                                                                                                             pokemonEffortValues::setImmutableSpeedEV;
            effortValueMethod.setEffortValue(Integer.parseInt(effortValue.get(index + 1)));
        }
        
        pokemon.getInformation().setEffortValue(pokemonEffortValues);
        
        String[] baseStats      = data[15].split(",");
        int[]    baseStatValues = new int[baseStats.length];
        for(int i = 0; i < baseStats.length; i++) {
            baseStatValues[i] = Integer.parseInt(baseStats[i].trim());
        }
        
        pokemon.getInformation().setBaseStats(baseStatValues);
        pokemon.getInformation().setCurrentLevel(PokemonInformation.DEFAULT_LEVEL);
        pokemon.getInformation().setCurrentHealth(pokemon.getInformation().getBaseStats()[0]);
        pokemon.getInformation().setCurrentAttack(pokemon.getInformation().getBaseStats()[1]);
        pokemon.getInformation().setCurrentDefense(pokemon.getInformation().getBaseStats()[2]);
        pokemon.getInformation().setCurrentSpecialAttack(pokemon.getInformation().getBaseStats()[3]);
        pokemon.getInformation().setCurrentSpecialDefense(pokemon.getInformation().getBaseStats()[4]);
        pokemon.getInformation().setCurrentSpeed(pokemon.getInformation().getBaseStats()[5]);
        pokemon.getInformation().setCurrentExperience(new PokemonExperience(pokemon.getInformation().getExperienceRate()));
        
        return pokemon;
    }
    
    public static void main(String[] args) {
        Pokemon pokemon  = PokemonDataReader.getPokemon("Bulbasaur");
        Pokemon pokemon2 = PokemonDataReader.getPokemon("Ivysaur");
        System.out.println(pokemon);
        System.out.println(pokemon2);
        int    repeatCount = 10000;
        long[] time        = new long[repeatCount];
        for(int i = 0; i < repeatCount; i++) {
            long start = System.nanoTime();
            pokemon.toString();
            time[i] = System.nanoTime() - start;
        }
        double sum = 0;
        for(long singleTime : time) {
            sum += singleTime;
        }
        double mean = sum / repeatCount;
        System.out.println(String.format("\n\nAverage Time (Nano Seconds) of Pokemon's toString() method: %s", mean));
    }
    
    public static Pokemon getPokemon(String pokemonName) {
        return pokemonData.get(pokemonName);
    }
}