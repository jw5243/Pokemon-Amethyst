package com.horse.pokemon.ObjectData.PokemonData;

import com.horse.pokemon.RandomNumberGenerator;

import java.util.ArrayList;
import java.util.Arrays;

import static com.horse.pokemon.ObjectData.PokemonData.StatTypes.ATTACK;
import static com.horse.pokemon.ObjectData.PokemonData.StatTypes.DEFENSE;
import static com.horse.pokemon.ObjectData.PokemonData.StatTypes.HEALTH;
import static com.horse.pokemon.ObjectData.PokemonData.StatTypes.SPECIAL_ATTACK;
import static com.horse.pokemon.ObjectData.PokemonData.StatTypes.SPECIAL_DEFENSE;
import static com.horse.pokemon.ObjectData.PokemonData.StatTypes.SPEED;

/**
 * Class for representing one of the unique values of any {@link Pokemon}.  For every stat, a random number from zero to thirty-one is generated, a higher number giving a higher stat
 * value compared to {@link Pokemon} with a lower {@code IndividualValue} but same base stat.
 *
 * @see ArrayList
 * @see StatTypes
 */
public class IndividualValue {
    /**
     * The highest possible {@code IndividualValue} a single stat can have the value of.
     */
    private static final int MAXIMUM_INDIVIDUAL_VALUE_PER_STAT = 31;
    
    /**
     * The lowest possible {@code IndividualValue} a single stat can have the value of.
     */
    private static final int MINIMUM_INDIVIDUAL_VALUE_PER_STAT = 0;
    
    /**
     * The {@link ArrayList} instance contianing {@link StatTypes} for reference as to what perfect IV is selected for the remaining stats.
     */
    private ArrayList<StatTypes> statTypesArrayList;
    
    /**
     * The {@code int} instance representing the {@code IndividualValue} of the {@link StatTypes#HEALTH} stat of a {@link Pokemon}.
     */
    private int health;
    
    /**
     * The {@code int} instance representing the {@code IndividualValue} of the {@link StatTypes#ATTACK} stat of a {@link Pokemon}.
     */
    private int attack;
    
    /**
     * The {@code int} instance representing the {@code IndividualValue} of the {@link StatTypes#DEFENSE} stat of a {@link Pokemon}.
     */
    private int defense;
    
    /**
     * The {@code int} instance representing the {@code IndividualValue} of the {@link StatTypes#SPECIAL_ATTACK} stat of a {@link Pokemon}.
     */
    private int specialAttack;
    
    /**
     * The {@code int} instance representing the {@code IndividualValue} of the {@link StatTypes#SPECIAL_DEFENSE} stat of a {@link Pokemon}.
     */
    private int specialDefense;
    
    /**
     * The {@code int} instance representing the {@code IndividualValue} of the {@link StatTypes#SPEED} stat of a {@link Pokemon}.
     */
    private int speed;
    
    /**
     * Class constructor that sets all the {@code IndividualValue}s for a new {@link Pokemon}.
     */
    public IndividualValue() {
        statTypesArrayList = new ArrayList<>(Arrays.asList(HEALTH, ATTACK, DEFENSE, SPECIAL_ATTACK, SPECIAL_DEFENSE, SPEED)); //Initializes the array list to have all the stat types.
    
        while(statTypesArrayList.toArray().length > 3) { //Ensure three stats have perfect individual values.
            int randomStatIndex = RandomNumberGenerator.generateNumber(0,
                                                                       statTypesArrayList.toArray().length - 1
            ); //Generate and store a random number from zero to the amount of remaining stats minus one.
            if(statTypesArrayList.toArray()[randomStatIndex] == HEALTH) { //Check if the random number generated is the health stat.
                setHealth(getMaximumIndividualValuePerStat()); //Sets the health to a perfect individual value.
            } else if(statTypesArrayList.toArray()[randomStatIndex] == ATTACK) { //Check if the random number generated is the attack stat.
                setAttack(getMaximumIndividualValuePerStat()); //Sets the attack to a perfect individual value.
            } else if(statTypesArrayList.toArray()[randomStatIndex] == DEFENSE) { //Check if the random number generated is the defense stat.
                setDefense(getMaximumIndividualValuePerStat()); //Sets the defense to a perfect individual value.
            } else if(statTypesArrayList.toArray()[randomStatIndex] == SPECIAL_ATTACK) { //Check if the random number generated is the special attack stat.
                setSpecialAttack(getMaximumIndividualValuePerStat()); //Sets the special attack to a perfect individual value.
            } else if(statTypesArrayList.toArray()[randomStatIndex] == SPECIAL_DEFENSE) { //Check if the random number generated is the special defense stat.
                setSpecialDefense(getMaximumIndividualValuePerStat()); //Sets the special defense to a perfect individual value.
            } else if(statTypesArrayList.toArray()[randomStatIndex] == SPEED) { //Check if the random number generated is the speed stat.
                setSpeed(getMaximumIndividualValuePerStat()); //Sets the speed to a perfect individual value.
            }
            statTypesArrayList.remove(randomStatIndex); //Removes the stat that was given the perfect individual value to prevent resetting the value.
        }
    
        if(statTypesArrayList.contains(HEALTH)) { //Check if health has not been set to a perfect individual value.
            setHealth(RandomNumberGenerator.generateNumber(getMinimumIndividualValuePerStat(),
                                                           getMaximumIndividualValuePerStat()
            )); //Generate a random number between the minimum and maximum individual value and set the health individual value to it.
        }
        if(statTypesArrayList.contains(ATTACK)) { //Check if attack has not been set to a perfect individual value.
            setAttack(RandomNumberGenerator.generateNumber(getMinimumIndividualValuePerStat(),
                                                           getMaximumIndividualValuePerStat()
            )); //Generate a random number between the minimum and maximum individual value and set the attack individual value to it.
        }
        if(statTypesArrayList.contains(DEFENSE)) { //Check if defense has not been set to a perfect individual value.
            setDefense(RandomNumberGenerator.generateNumber(getMinimumIndividualValuePerStat(),
                                                            getMaximumIndividualValuePerStat()
            )); //Generate a random number between the minimum and maximum individual value and set the defense individual value to it.
        }
        if(statTypesArrayList.contains(SPECIAL_ATTACK)) { //Check if special attack has not been set to a perfect individual value.
            setSpecialAttack(RandomNumberGenerator.generateNumber(getMinimumIndividualValuePerStat(),
                                                                  getMaximumIndividualValuePerStat()
            )); //Generate a random number between the minimum and maximum individual value and set the special attack individual value to it.
        }
        if(statTypesArrayList.contains(SPECIAL_DEFENSE)) { //Check if special defense has not been set to a perfect individual value.
            setSpecialDefense(RandomNumberGenerator.generateNumber(getMinimumIndividualValuePerStat(),
                                                                   getMaximumIndividualValuePerStat()
            )); //Generate a random number between the minimum and maximum individual value and set the special defense individual value to it.
        }
        if(statTypesArrayList.contains(SPEED)) { //Check if speed has not been set to a perfect individual value.
            setSpeed(RandomNumberGenerator.generateNumber(getMinimumIndividualValuePerStat(),
                                                          getMaximumIndividualValuePerStat()
            )); //Generate a random number between the minimum and maximum individual value and set the speed individual value to it.
        }
    }
    
    /**
     * Returns the {@code int} representing the smallest possible individual value a stat can have.
     *
     * @return Smallest possible individual value value.
     */
    public static int getMaximumIndividualValuePerStat() {
        return MAXIMUM_INDIVIDUAL_VALUE_PER_STAT;
    }
    
    /**
     * Returns the {@code int} representing the biggest possible individual value a stat can have.
     *
     * @return Biggest possible individual value value.
     */
    public static int getMinimumIndividualValuePerStat() {
        return MINIMUM_INDIVIDUAL_VALUE_PER_STAT;
    }
    
    /**
     * Tests the {@code IndividualValue} system by generating one and printing out the values.
     *
     * @param args Command line arguments (Should be blank).
     */
    public static void main(String[] args) {
        System.out.println(new IndividualValue());
    }
    
    /**
     * Returns the {@code int} instance representing the {@link StatTypes#HEALTH} {@code IndividualValue}.
     *
     * @return {@link #health}
     */
    public int getHealth() {
        return health;
    }
    
    /**
     * Sets the {@link #health}.
     *
     * @param health {@link #health}
     */
    public void setHealth(int health) {
        this.health = trimStat(health);
    }
    
    /**
     * Clamps a stat according to {@link #MINIMUM_INDIVIDUAL_VALUE_PER_STAT} and {@link #MAXIMUM_INDIVIDUAL_VALUE_PER_STAT}
     *
     * @param statValue Value to be clamped.
     *
     * @return Clamped version of {@code statValue}.
     */
    public int trimStat(int statValue) {
        statValue = statValue < getMinimumIndividualValuePerStat() ? getMinimumIndividualValuePerStat() : statValue; //Ensure the new value is not below the minimum individual value.
        statValue = statValue > getMaximumIndividualValuePerStat() ? getMaximumIndividualValuePerStat() : statValue; //Ensure the new value is not above the maximum individual value.
        return statValue; //Return the newly clamped value.
    }
    
    /**
     * Returns the {@code int} instance representing the {@link StatTypes#ATTACK} {@code IndividualValue}.
     *
     * @return {@link #health}
     */
    public int getAttack() {
        return attack;
    }
    
    /**
     * Sets the {@link #attack}.
     *
     * @param attack {@link #attack}
     */
    public void setAttack(int attack) {
        this.attack = trimStat(attack);
    }
    
    /**
     * Returns the {@code int} instance representing the {@link StatTypes#DEFENSE} {@code IndividualValue}.
     *
     * @return {@link #defense}
     */
    public int getDefense() {
        return defense;
    }
    
    /**
     * Sets the {@link #defense}
     *
     * @param defense {@link #defense}
     */
    public void setDefense(int defense) {
        this.defense = trimStat(defense);
    }
    
    /**
     * Returns the {@code int} instance representing the {@link StatTypes#SPECIAL_ATTACK} {@code IndividualValue}.
     *
     * @return {@link #specialAttack}
     */
    public int getSpecialAttack() {
        return specialAttack;
    }
    
    /**
     * Sets the {@link #specialAttack}.
     *
     * @param specialAttack {@link #specialAttack}
     */
    public void setSpecialAttack(int specialAttack) {
        this.specialAttack = trimStat(specialAttack);
    }
    
    /**
     * Returns the {@code int} instance representing the {@link StatTypes#SPECIAL_DEFENSE} {@code IndividualValue}.
     *
     * @return {@link #specialDefense}
     */
    public int getSpecialDefense() {
        return specialDefense;
    }
    
    /**
     * Sets the {@link #specialDefense}.
     *
     * @param specialDefense {@link #specialDefense}
     */
    public void setSpecialDefense(int specialDefense) {
        this.specialDefense = trimStat(specialDefense);
    }
    
    /**
     * Returns the {@code int} instance representing the {@link StatTypes#SPEED} {@code IndividualValue}.
     *
     * @return {@link #speed}
     */
    public int getSpeed() {
        return speed;
    }
    
    /**
     * Sets the {@link #speed}
     *
     * @param speed {@link #speed}
     */
    public void setSpeed(int speed) {
        this.speed = trimStat(speed);
    }
    
    /**
     * Returns human-readable form of a {@code IndividualValue} instance.
     *
     * @return Huamn form of the {@code IndividualValue} values.
     */
    @Override
    public String toString() {
        return String.format("{Health IV = %s, Attack IV = %s, Defense IV = %s, Special Attack IV = %s, " + "Special Attack IV = %s, Speed IV = %s}", health, attack, defense,
                             specialAttack, specialDefense, speed
        );
    }
}