package com.horse.pokemon.ObjectData.PokemonData;

import com.horse.pokemon.Enums.StatTypes;
import com.horse.pokemon.RandomNumberGenerator;

import java.util.ArrayList;
import java.util.Arrays;

import static com.horse.pokemon.Enums.StatTypes.ATTACK;
import static com.horse.pokemon.Enums.StatTypes.DEFENSE;
import static com.horse.pokemon.Enums.StatTypes.HEALTH;
import static com.horse.pokemon.Enums.StatTypes.SPECIAL_ATTACK;
import static com.horse.pokemon.Enums.StatTypes.SPECIAL_DEFENSE;
import static com.horse.pokemon.Enums.StatTypes.SPEED;

public class IndividualValue {
    private ArrayList<StatTypes> statTypesArrayList;
    private int                  health;
    private int                  attack;
    private int                  defense;
    private int                  specialAttack;
    private int                  specialDefense;
    private int                  speed;
    
    public IndividualValue() {
        statTypesArrayList =
                new ArrayList<>(Arrays.asList(HEALTH, ATTACK, DEFENSE, SPECIAL_ATTACK, SPECIAL_DEFENSE, SPEED));
        
        do {
            int randomStatIndex = RandomNumberGenerator.generateNumber(0, statTypesArrayList.toArray().length - 1);
            if(statTypesArrayList.toArray()[randomStatIndex] == HEALTH) {
                setHealth(31);
            } else if(statTypesArrayList.toArray()[randomStatIndex] == ATTACK) {
                setAttack(31);
            } else if(statTypesArrayList.toArray()[randomStatIndex] == DEFENSE) {
                setDefense(31);
            } else if(statTypesArrayList.toArray()[randomStatIndex] == SPECIAL_ATTACK) {
                setSpecialAttack(31);
            } else if(statTypesArrayList.toArray()[randomStatIndex] == SPECIAL_DEFENSE) {
                setSpecialDefense(31);
            } else if(statTypesArrayList.toArray()[randomStatIndex] == SPEED) {
                setSpeed(31);
            }
            statTypesArrayList.remove(randomStatIndex);
        } while(statTypesArrayList.toArray().length > 3);
        
        if(statTypesArrayList.contains(HEALTH)) {
            setHealth(RandomNumberGenerator.generateNumber(0, 31));
        }
        if(statTypesArrayList.contains(ATTACK)) {
            setAttack(RandomNumberGenerator.generateNumber(0, 31));
        }
        if(statTypesArrayList.contains(DEFENSE)) {
            setDefense(RandomNumberGenerator.generateNumber(0, 31));
        }
        if(statTypesArrayList.contains(SPECIAL_ATTACK)) {
            setSpecialAttack(RandomNumberGenerator.generateNumber(0, 31));
        }
        if(statTypesArrayList.contains(SPECIAL_DEFENSE)) {
            setSpecialDefense(RandomNumberGenerator.generateNumber(0, 31));
        }
        if(statTypesArrayList.contains(SPEED)) {
            setSpeed(RandomNumberGenerator.generateNumber(0, 31));
        }
    }
    
    public static void main(String[] args) {
        System.out.println(new IndividualValue());
    }
    
    public int getHealth() {
        return health;
    }
    
    public void setHealth(int health) {
        this.health = health;
    }
    
    public int getAttack() {
        return attack;
    }
    
    public void setAttack(int attack) {
        this.attack = attack;
    }
    
    public int getDefense() {
        return defense;
    }
    
    public void setDefense(int defense) {
        this.defense = defense;
    }
    
    public int getSpecialAttack() {
        return specialAttack;
    }
    
    public void setSpecialAttack(int specialAttack) {
        this.specialAttack = specialAttack;
    }
    
    public int getSpecialDefense() {
        return specialDefense;
    }
    
    public void setSpecialDefense(int specialDefense) {
        this.specialDefense = specialDefense;
    }
    
    public int getSpeed() {
        return speed;
    }
    
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    @Override
    public String toString() {
        return String.format("{Health IV = %s, Attack IV = %s, Defense IV = %s, Special Attack IV = %s, " +
                             "Special Attack IV = %s, Speed IV = %s}", health, attack, defense, specialAttack,
                             specialDefense, speed
        );
    }
}