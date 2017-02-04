package com.horse.pokemon.ObjectData.PokemonData;

import gnu.trove.map.hash.TIntObjectHashMap;

import java.util.Arrays;
import java.util.HashMap;

public class PokemonInformation {
    /**
     * The default level for all newly generated PokemonData.
     */
    public static final int DEFAULT_LEVEL = 1;
    
    /**
     * The {@link String} instance representing a PokemonData's name.  The
     * name used by a trainer may not be the same as the name stored in
     * this string, as PokemonData can have nicknames if desired by the user.
     */
    private String name;
    
    /**
     * The {@link String} instance representing a PokemonData's classification.
     */
    private String classification;
    
    /**
     * The {@link Integer} instance representing a PokemonData's number in the Pokedex.
     */
    private int pokedexNumber;
    
    /**
     * The {@link Integer} instance representing a PokemonData's evolution's {@code pokedexNumber}.
     */
    private int evolutionNumber;
    
    /**
     * The {@link Double} instance representing a PokemonData's male appearence rate in percentage.
     */
    private double genderRatio;
    
    /**
     * The {@link PokemonTypes} array instance representing a PokemonData's type (Can be one or two pokemonTypes).
     */
    private PokemonTypes[] pokemonTypes = new PokemonTypes[2];
    
    /**
     * The {@link String} instance representing a PokemonData's height.
     */
    private String height;
    
    /**
     * The {@link String} instance representing a PokemonData's weight.
     */
    private String weight;
    
    /**
     * The {@link Integer} instance representing a PokemonData's capture rate.
     */
    private int captureRate;
    
    /**
     * The {@link Integer} instance representing a PokemonData's base egg steps.
     */
    private int baseEggSteps;
    
    /**
     * The {@link String} instance representing a PokemonData's ability.
     */
    private String ability;
    
    /**
     * The {@link ExperienceTypes} instance representing a PokemonData's experience type in terms of rate.
     */
    private ExperienceTypes experienceRate;
    
    /**
     * The {@link Integer} instance representing a PokemonData's base happiness.
     */
    private int baseHappiness;
    
    /**
     * The {@link TIntObjectHashMap} instance representing a PokemonData's attack list.
     */
    private TIntObjectHashMap<Moves> moveList;
    
    /**
     * The {@link HashMap} two-dimensional array instance representing a PokemonData's effort values.
     */
    private EffortValue effortValue;
    
    /**
     * The {@link Integer} array instance representing a PokemonData's base stats.
     * The length of the array should always be the size of {@code statTypes.values().length}.
     */
    private int[] baseStats = new int[StatTypes.values().length]; //Always goes in order of enum statTypes.
    
    private int               baseExperience;
    /**
     * The {@link Integer} instance representing a PokemonData's current level.
     */
    private int               currentLevel;
    /**
     * The {@link Integer} instance representing a PokemonData's current health.
     */
    private int               currentHealth;
    /**
     * The {@link Integer} instance representing a PokemonData's current attack.
     */
    private int               currentAttack;
    /**
     * The {@link Integer} instance representing a PokemonData's current defense.
     */
    private int               currentDefense;
    /**
     * The {@link Integer} instance representing a PokemonData's current special attack.
     */
    private int               currentSpecialAttack;
    /**
     * The {@link Integer} instance representing a PokemonData's current special defense.
     */
    private int               currentSpecialDefense;
    /**
     * The {@link Integer} instance representing a PokemonData's current speed.
     */
    private int               currentSpeed;
    /**
     * The {@link PokemonExperience} instance representing a PokemonData's experience value.
     */
    private PokemonExperience currentExperience;
    /**
     * The {@link Object} array instance representing a PokemonData's individual value, which are
     * randomly generated numbers that alter the stats of every {@code CurrentPokemon} to
     * add some variety and slight advantage and disadvantage between equal PokemonData.
     */
    private IndividualValue   individualValue;
    
    public static int getDefaultLevel() {
        return DEFAULT_LEVEL;
    }
    
    public int getBaseExperience() {
        return baseExperience;
    }
    
    public void setBaseExperience(int baseExperience) {
        this.baseExperience = baseExperience;
    }
    
    /**
     * Returns the PokemonData's individual values.
     *
     * @return {@link #individualValue}
     */
    public IndividualValue getIndividualValue() {
        return individualValue;
    }
    
    /**
     * Sets the {@link #individualValue}.
     *
     * @param individualValue {@link #individualValue}
     */
    public void setIndividualValue(IndividualValue individualValue) {
        this.individualValue = individualValue;
    }
    
    @Override
    public String toString() {
        String value = new StringBuilder().append("{Name = ").append(getName()).append(", Classification = ")
                                          .append(getClassification()).append(", Pokedex Number = ")
                                          .append(getPokedexNumber()).append(", Evolution Number = ")
                                          .append(getEvolutionNumber()).append(", Gender Ratio = ")
                                          .append(getGenderRatio()).append(", Types = ")
                                          .append(Arrays.toString(getPokemonTypes())).append(", Height = ")
                                          .append(getHeight()).append(", Weight = ").append(getWeight())
                                          .append(", Capture Rate = ").append(getCaptureRate())
                                          .append(", Base Egg Steps = ").append(getBaseEggSteps())
                                          .append(", Ability = ").append(getAbility()).append(", Experience Rate = ")
                                          .append(getExperienceRate()).append(", Base Happiness = ")
                                          .append(getBaseHappiness()).append(", Move List = ").append(getMoveList())
                                          .append(", Effort Value = ").append(getEffortValue())
                                          .append(", Base Stats = ").append(", Base Experience = ")
                                          .append(getBaseExperience()).append(Arrays.toString(getBaseStats()))
                                          .append(", Level = ").append(getCurrentLevel()).append(", Health = ")
                                          .append(getCurrentHealth()).append(", Attack = ").append(getCurrentAttack())
                                          .append(", Defense = ").append(getCurrentDefense())
                                          .append(", Special Attack = ").append(getCurrentSpecialAttack())
                                          .append(", Special Defense = ").append(getCurrentSpecialDefense())
                                          .append(", Speed = ").append(getCurrentSpeed()).append(", Experience = ")
                                          .append(getCurrentExperience()).append(", Individual Values = ")
                                          .append(getIndividualValue()).toString();
        StringBuilder formatString = new StringBuilder();
        int           tabs         = 0;
        boolean       newLine      = false;
        for(char character : value.toCharArray()) {
            if(character == '{' || character == '[') {
                newLine = true;
                tabs++;
            } else if(character == '}' || character == ']') {
                formatString.append('\n');
                for(int i = 0; i < tabs; i++) {
                    formatString.append('\t');
                }
                tabs--;
            } else if(character == ',') {
                newLine = true;
            }
            formatString.append(character);
            if(newLine) {
                newLine = false;
                formatString.append('\n');
                for(int i = 0; i < tabs; i++) {
                    formatString.append('\t');
                }
            }
        }
        return formatString.toString();
    }
    
    /**
     * Returns the PokemonData's name.
     *
     * @return {@link #name}
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the {@link #name}.
     *
     * @param name {@link #name}
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Returns the PokemonData's classification.
     *
     * @return {@link #classification}
     */
    public String getClassification() {
        return classification;
    }
    
    /**
     * Sets the {@link #classification}.
     *
     * @param classification {@link #classification}
     */
    public void setClassification(String classification) {
        this.classification = classification;
    }
    
    /**
     * Returns the PokemonData's Pokedex Number.
     *
     * @return {@link #pokedexNumber}
     */
    public int getPokedexNumber() {
        return pokedexNumber;
    }
    
    /**
     * Sets the {@link #pokedexNumber}.
     *
     * @param pokedexNumber {@link #pokedexNumber}
     */
    public void setPokedexNumber(int pokedexNumber) {
        this.pokedexNumber = pokedexNumber;
    }
    
    /**
     * Returns the PokemonData's Evolution Number.
     *
     * @return {@link #evolutionNumber}
     */
    public int getEvolutionNumber() {
        return evolutionNumber;
    }
    
    /**
     * Sets the {@link #evolutionNumber}.
     *
     * @param evolutionNumber {@link #evolutionNumber}
     */
    public void setEvolutionNumber(int evolutionNumber) {
        this.evolutionNumber = evolutionNumber;
    }
    
    /**
     * Returns the PokemonData's Gender Ratio.
     *
     * @return {@link #genderRatio}
     */
    public double getGenderRatio() {
        return genderRatio;
    }
    
    /**
     * Sets the {@link #genderRatio}.
     *
     * @param genderRatio {@link #genderRatio}
     */
    public void setGenderRatio(double genderRatio) {
        this.genderRatio = genderRatio;
    }
    
    /**
     * Returns the PokemonData's type(s).
     *
     * @return {@link #pokemonTypes}
     */
    public PokemonTypes[] getPokemonTypes() {
        return pokemonTypes;
    }
    
    /**
     * Sets the {@link #pokemonTypes}.
     *
     * @param pokemonTypes {@link #pokemonTypes}
     */
    public void setPokemonTypes(PokemonTypes[] pokemonTypes) {
        this.pokemonTypes = pokemonTypes;
    }
    
    /**
     * Returns the PokemonData's height.
     *
     * @return {@link #height}
     */
    public String getHeight() {
        return height;
    }
    
    /**
     * Sets the {@link #height}.
     *
     * @param height {@link #height}
     */
    public void setHeight(String height) {
        this.height = height;
    }
    
    /**
     * Returns the PokemonData's weight.
     *
     * @return {@link #weight}
     */
    public String getWeight() {
        return weight;
    }
    
    /**
     * Sets the {@link #weight}.
     *
     * @param weight {@link #weight}
     */
    public void setWeight(String weight) {
        this.weight = weight;
    }
    
    /**
     * Returns the PokemonData's capture rate.
     *
     * @return {@link #captureRate}
     */
    public int getCaptureRate() {
        return captureRate;
    }
    
    /**
     * Sets the {@link #captureRate}.
     *
     * @param captureRate {@link #captureRate}
     */
    public void setCaptureRate(int captureRate) {
        this.captureRate = captureRate;
    }
    
    /**
     * Returns the PokemonData's base egg steps.
     *
     * @return {@link #baseEggSteps}
     */
    public int getBaseEggSteps() {
        return baseEggSteps;
    }
    
    /**
     * Sets the {@link #baseEggSteps}.
     *
     * @param baseEggSteps {@link #baseEggSteps}
     */
    public void setBaseEggSteps(int baseEggSteps) {
        this.baseEggSteps = baseEggSteps;
    }
    
    /**
     * Returns the PokemonData's ability.
     *
     * @return {@link #ability}
     */
    public String getAbility() {
        return ability;
    }
    
    /**
     * Sets the {@link #ability}.
     *
     * @param ability {@link #ability}
     */
    public void setAbility(String ability) {
        this.ability = ability;
    }
    
    /**
     * Returns the PokemonData's experience rate.
     *
     * @return {@link #experienceRate}
     */
    public ExperienceTypes getExperienceRate() {
        return experienceRate;
    }
    
    /**
     * Sets the {@link #experienceRate}.
     *
     * @param experienceRate {@link #experienceRate}
     */
    public void setExperienceRate(ExperienceTypes experienceRate) {
        this.experienceRate = experienceRate;
    }
    
    /**
     * Returns the PokemonData's base happiness.
     *
     * @return {@link #baseHappiness}
     */
    public int getBaseHappiness() {
        return baseHappiness;
    }
    
    /**
     * Sets the {@link #baseHappiness}.
     *
     * @param baseHappiness {@link #baseHappiness}
     */
    public void setBaseHappiness(int baseHappiness) {
        this.baseHappiness = baseHappiness;
    }
    
    /**
     * Returns the PokemonData's move list.
     *
     * @return {@link #moveList}
     */
    public TIntObjectHashMap<Moves> getMoveList() {
        return moveList;
    }
    
    /**
     * Sets the {@link #moveList}.
     *
     * @param moveList {@link #moveList}
     */
    public void setMoveList(TIntObjectHashMap<Moves> moveList) {
        this.moveList = moveList;
    }
    
    /**
     * Returns the PokemonData's effort values.
     *
     * @return {@link #effortValue}
     */
    public EffortValue getEffortValue() {
        return effortValue;
    }
    
    /**
     * Sets the {@link #effortValue}.
     *
     * @param effortValue {@link #effortValue}
     */
    public void setEffortValue(EffortValue effortValue) {
        this.effortValue = effortValue;
    }
    
    /**
     * Returns the PokemonData's base stats.
     *
     * @return {@link #baseStats}
     */
    public int[] getBaseStats() {
        return baseStats;
    }
    
    /**
     * Sets the {@link #baseStats}.
     *
     * @param baseStats {@link #baseStats}
     */
    public void setBaseStats(int[] baseStats) {
        this.baseStats = baseStats;
    }
    
    /**
     * Returns the PokemonData's current level.
     *
     * @return {@link #currentLevel}
     */
    public int getCurrentLevel() {
        return currentLevel;
    }
    
    /**
     * Sets the {@link #currentLevel}.
     *
     * @param currentLevel {@link #currentLevel}
     */
    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }
    
    /**
     * Returns the PokemonData's current health.
     *
     * @return {@link #currentHealth}
     */
    public int getCurrentHealth() {
        return currentHealth;
    }
    
    /**
     * Sets the {@link #currentHealth}.
     *
     * @param currentHealth {@link #currentHealth}
     */
    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }
    
    /**
     * Returns the PokemonData's current attack.
     *
     * @return {@link #currentAttack}
     */
    public int getCurrentAttack() {
        return currentAttack;
    }
    
    /**
     * Sets the {@link #currentAttack}.
     *
     * @param currentAttack {@link #currentAttack}
     */
    public void setCurrentAttack(int currentAttack) {
        this.currentAttack = currentAttack;
    }
    
    /**
     * Returns the PokemonData's current defense.
     *
     * @return {@link #currentDefense}
     */
    public int getCurrentDefense() {
        return currentDefense;
    }
    
    /**
     * Sets the {@link #currentDefense}.
     *
     * @param currentDefense {@link #currentDefense}
     */
    public void setCurrentDefense(int currentDefense) {
        this.currentDefense = currentDefense;
    }
    
    /**
     * Returns the PokemonData's current special attack.
     *
     * @return {@link #currentSpecialAttack}
     */
    public int getCurrentSpecialAttack() {
        return currentSpecialAttack;
    }
    
    /**
     * Sets the {@link #currentSpecialAttack}.
     *
     * @param currentSpecialAttack {@link #currentSpecialAttack}
     */
    public void setCurrentSpecialAttack(int currentSpecialAttack) {
        this.currentSpecialAttack = currentSpecialAttack;
    }
    
    /**
     * Returns the PokemonData's current special defense.
     *
     * @return {@link #currentSpecialDefense}
     */
    public int getCurrentSpecialDefense() {
        return currentSpecialDefense;
    }
    
    /**
     * Sets the {@link #currentSpecialDefense}.
     *
     * @param currentSpecialDefense {@link #currentSpecialDefense}
     */
    public void setCurrentSpecialDefense(int currentSpecialDefense) {
        this.currentSpecialDefense = currentSpecialDefense;
    }
    
    /**
     * Returns the PokemonData's current speed.
     *
     * @return {@link #currentSpeed}
     */
    public int getCurrentSpeed() {
        return currentSpeed;
    }
    
    /**
     * Sets the {@link #currentSpeed}.
     *
     * @param currentSpeed {@link #currentSpeed}
     */
    public void setCurrentSpeed(int currentSpeed) {
        this.currentSpeed = currentSpeed;
    }
    
    /**
     * Returns the PokemonData's current experience value.
     *
     * @return {@link #currentExperience}
     */
    public PokemonExperience getCurrentExperience() {
        return currentExperience;
    }
    
    /**
     * Sets the {@link #currentExperience}.
     *
     * @param currentExperience {@link #currentExperience}
     */
    public void setCurrentExperience(PokemonExperience currentExperience) {
        this.currentExperience = currentExperience;
    }
}
