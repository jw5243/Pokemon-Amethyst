package com.horse.pokemon.ObjectData.PokemonData;

import com.horse.pokemon.DataReaders.PokemonDataReader;

/**
 * The class for all PokemonData, containing all information that can be easily
 * accessed by setters and getters.  Use {@link PokemonDataReader#getPokemon(String)} to get a PokemonData
 * with preset values depending on the input value {@link String}.
 *
 * @version 1.1
 * @since 1.0
 */
public class Pokemon {
    /**
     * The information that sets all Pokemon apart, containing values such as a Pokemon's stats and changing values.
     */
    private PokemonInformation information;
    
    /**
     * Constructor.  Sets the IV of any new Pokemon generated.
     *
     * @see PokemonInformation#setIndividualValue(IndividualValue)
     * @see IndividualValue
     */
    public Pokemon() {
        setInformation(new PokemonInformation());
        getInformation().setEffortValue(new EffortValue());
        getInformation().setIndividualValue(new IndividualValue());
    }
    
    public static void main(String[] args) {
        Pokemon pokemon = PokemonDataReader.getPokemon("Ivysaur");
        System.out.println(pokemon);
        System.out.println(pokemon.getInformation().getIndividualValue());
    }
    
    public PokemonInformation getInformation() {
        return information;
    }
    
    public void setInformation(PokemonInformation information) {
        this.information = information;
    }
    
    public void updateStats() {
        getInformation().setBaseStats(CalculateStats.getStats(this));
    }
    
    public void updateLevel(int level) {
        getInformation().setCurrentLevel(level);
        updateStats();
    }
    
    @Override
    public String toString() {
        return getInformation().toString();
    }
}