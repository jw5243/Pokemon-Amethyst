package com.horse.pokemon.amethyst.data.pokemon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.horse.pokemon.amethyst.data.handlers.readers.PokemonDataReader;

import java.io.File;
import java.util.HashMap;

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
    
    private HashMap<String, Texture> pokemonImages;
    
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
        setPokemonImages(new HashMap<>());
    }
    
    public static void main(String[] args) {
        Pokemon pokemon = PokemonDataReader.getPokemon("Ivysaur");
        System.out.println(pokemon);
        System.out.println(pokemon.getInformation().getIndividualValue());
    }
    
    public HashMap<String, Texture> getPokemonImages() {
        return pokemonImages;
    }
    
    public void setPokemonImages(HashMap<String, Texture> pokemonImages) {
        this.pokemonImages = pokemonImages;
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
    
    @SuppressWarnings("This method will not work if the number of Pokemon in the file system exceeds 999")
    public void setPokemonImages() {
        final StringBuilder stringBuilder = new StringBuilder("P_");
        final int           pokedexNumber = getInformation().getPokedexNumber();
        
        if(pokedexNumber < 10) {
            stringBuilder.append("00").append(pokedexNumber);
        } else if(pokedexNumber < 100) {
            stringBuilder.append("0").append(pokedexNumber);
        } else {
            stringBuilder.append(pokedexNumber);
        }
        
        String nameToSearchFor = stringBuilder.toString();
        
        final File[] files = new File("Battlers").listFiles();
        
        if(files != null) {
            /*Arrays.stream(files).filter((final File file) -> file.isFile() && file.getName().endsWith(".png") &&
                                                             file.getName().replace(".png", "").equals(nameToSearchFor))
                  .forEach((final File file) -> {
                      System.out.println(file.getPath().replace("core\\assets\\", ""));
                      getPokemonImages().put(file.getName().replace(".png", ""), new Texture(Gdx.files.internal(
                          file.getPath().replace("core\\assets\\", ""))));
                      System.out.println(file.getName());
                  });*/
            //Change all files starting with a number in the Battlers folder.
            
            for(final File file : files) {
                if(file.isFile() && file.getName().endsWith(".png") &&
                   file.getName().replace(".png", "").equals(nameToSearchFor)) {
                    
                    //TODO: Load textures without the aid of Actions
                    Actions.sequence(new Action() {
                        @Override
                        public boolean act(float delta) {
                            getPokemonImages().put(file.getName().replace(".png", "").replace("P_", ""),
                                                   new Texture(Gdx.files.internal(file.getPath()))
                            );
                            return true;
                        }
                    });
                    
                    //System.out.println(file.getName());
                }
            }
        }
    }
    
    @Override
    public String toString() {
        return getInformation().toString();
    }
}