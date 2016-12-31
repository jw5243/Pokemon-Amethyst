package com.horse.pokemon.ObjectData.PokemonData;

public class EffortValue {
    private static final int MAX_EFFORT_VALUE_TOTAL    = 510;
    private static final int MAX_EFFORT_VALUE_PER_STAT = 255;
    private int healthEV;
    private int attackEV;
    private int defenseEV;
    private int specialAttackEV;
    private int specialDefenseEV;
    private int speedEV;
    private int immutableHealthEV;
    private int immutableAttackEV;
    private int immutableDefenseEV;
    private int immutableSpecialAttackEV;
    private int immutableSpecialDefenseEV;
    private int immutableSpeedEV;
    
    public EffortValue() {
        this(0, 0, 0, 0, 0, 0);
    }
    
    public EffortValue(int immutableHealthEV, int immutableAttackEV, int immutableDefenseEV,
                       int immutableSpecialAttackEV, int immutableSpecialDefenseEV, int immutableSpeedEV) {
        resetEV();
        setImmutableHealthEV(immutableHealthEV);
        setImmutableAttackEV(immutableAttackEV);
        setImmutableDefenseEV(immutableDefenseEV);
        setImmutableSpecialAttackEV(immutableSpecialAttackEV);
        setImmutableSpecialDefenseEV(immutableSpecialDefenseEV);
        setImmutableSpeedEV(immutableSpeedEV);
    }
    
    public static void main(String[] args) {
        int         inputEV     = 300;
        EffortValue effortValue = new EffortValue();
        effortValue.setHealthEV(inputEV);
        System.out.println(String.format("Health EV (Plugged in %s): %s", inputEV, effortValue.getHealthEV()));
        effortValue.setAttackEV(255);
        effortValue.setDefenseEV(10);
        System.out.println(effortValue);
    }
    
    public int getImmutableHealthEV() {
        return immutableHealthEV;
    }
    
    public void setImmutableHealthEV(int immutableHealthEV) {
        this.immutableHealthEV = immutableHealthEV;
    }
    
    public int getImmutableAttackEV() {
        return immutableAttackEV;
    }
    
    public void setImmutableAttackEV(int immutableAttackEV) {
        this.immutableAttackEV = immutableAttackEV;
    }
    
    public int getImmutableDefenseEV() {
        return immutableDefenseEV;
    }
    
    public void setImmutableDefenseEV(int immutableDefenseEV) {
        this.immutableDefenseEV = immutableDefenseEV;
    }
    
    public int getImmutableSpecialAttackEV() {
        return immutableSpecialAttackEV;
    }
    
    public void setImmutableSpecialAttackEV(int immutableSpecialAttackEV) {
        this.immutableSpecialAttackEV = immutableSpecialAttackEV;
    }
    
    public int getImmutableSpecialDefenseEV() {
        return immutableSpecialDefenseEV;
    }
    
    public void setImmutableSpecialDefenseEV(int immutableSpecialDefenseEV) {
        this.immutableSpecialDefenseEV = immutableSpecialDefenseEV;
    }
    
    public int getImmutableSpeedEV() {
        return immutableSpeedEV;
    }
    
    public void setImmutableSpeedEV(int immutableSpeedEV) {
        this.immutableSpeedEV = immutableSpeedEV;
    }
    
    public void resetEV() {
        setHealthEV(0);
        setAttackEV(0);
        setDefenseEV(0);
        setSpecialAttackEV(0);
        setSpecialDefenseEV(0);
        setSpeedEV(0);
    }
    
    public void updateEV(Pokemon enemyPokemon) {
        EffortValue enemyEffortValue = enemyPokemon.getInformation().getEffortValue();
        setHealthEV(getHealthEV() + enemyEffortValue.getHealthEV());
        setAttackEV(getAttackEV() + enemyEffortValue.getAttackEV());
        setDefenseEV(getDefenseEV() + enemyEffortValue.getDefenseEV());
        setSpecialAttackEV(getSpecialAttackEV() + enemyEffortValue.getSpecialAttackEV());
        setSpecialDefenseEV(getSpecialDefenseEV() + enemyEffortValue.getSpecialDefenseEV());
        setSpeedEV(getSpeedEV() + enemyEffortValue.getSpeedEV());
    }
    
    public int totalEV() {
        return getHealthEV() + getAttackEV() + getDefenseEV() + getSpecialAttackEV() + getSpecialDefenseEV() +
               getSpeedEV();
    }
    
    @Override
    public String toString() {
        return String.format(
                "{Health EV = %s, Attack EV = %s, Defense EV = %s, Special Attack EV = %s, Special Defense EV = %s, Speed EV = %s, " +
                "Immutables: Health EV = %s, Attack EV = %s, Defense EV = %s, Special Attack EV = %s, Special Defense EV = %s, Speed EV = %s}",
                getHealthEV(), getAttackEV(), getDefenseEV(), getSpecialAttackEV(), getSpecialDefenseEV(), getSpeedEV(),
                getImmutableHealthEV(), getImmutableAttackEV(), getImmutableDefenseEV(), getImmutableSpecialAttackEV(),
                getImmutableSpecialDefenseEV(), getImmutableSpeedEV()
        );
    }
    
    public int getHealthEV() {
        return healthEV;
    }
    
    public void setHealthEV(int healthEV) {
        this.healthEV = totalEV() + healthEV <= MAX_EFFORT_VALUE_TOTAL ? getHealthEV() + healthEV :
                        getHealthEV() + (MAX_EFFORT_VALUE_TOTAL - totalEV());
        this.healthEV = getHealthEV() > MAX_EFFORT_VALUE_PER_STAT ? MAX_EFFORT_VALUE_PER_STAT : getHealthEV();
    }
    
    public int getAttackEV() {
        return attackEV;
    }
    
    public void setAttackEV(int attackEV) {
        this.attackEV = totalEV() + attackEV <= MAX_EFFORT_VALUE_TOTAL ? getAttackEV() + attackEV :
                        getAttackEV() + (MAX_EFFORT_VALUE_TOTAL - totalEV());
        this.attackEV = getAttackEV() > MAX_EFFORT_VALUE_PER_STAT ? MAX_EFFORT_VALUE_PER_STAT : getAttackEV();
    }
    
    public int getDefenseEV() {
        return defenseEV;
    }
    
    public void setDefenseEV(int defenseEV) {
        this.defenseEV = totalEV() + defenseEV <= MAX_EFFORT_VALUE_TOTAL ? getDefenseEV() + defenseEV :
                         getDefenseEV() + (MAX_EFFORT_VALUE_TOTAL - totalEV());
        this.defenseEV = getDefenseEV() > MAX_EFFORT_VALUE_PER_STAT ? MAX_EFFORT_VALUE_PER_STAT : getDefenseEV();
    }
    
    public int getSpecialAttackEV() {
        return specialAttackEV;
    }
    
    public void setSpecialAttackEV(int specialAttackEV) {
        this.specialAttackEV =
                totalEV() + specialAttackEV <= MAX_EFFORT_VALUE_TOTAL ? getSpecialAttackEV() + specialAttackEV :
                getSpecialAttackEV() + (MAX_EFFORT_VALUE_TOTAL - totalEV());
        this.specialAttackEV =
                getSpecialAttackEV() > MAX_EFFORT_VALUE_PER_STAT ? MAX_EFFORT_VALUE_PER_STAT : getSpecialAttackEV();
    }
    
    public int getSpecialDefenseEV() {
        return specialDefenseEV;
    }
    
    public void setSpecialDefenseEV(int specialDefenseEV) {
        this.specialDefenseEV =
                totalEV() + specialDefenseEV <= MAX_EFFORT_VALUE_TOTAL ? getSpecialDefenseEV() + specialDefenseEV :
                getSpecialDefenseEV() + (MAX_EFFORT_VALUE_TOTAL - totalEV());
        this.specialDefenseEV =
                getSpecialDefenseEV() > MAX_EFFORT_VALUE_PER_STAT ? MAX_EFFORT_VALUE_PER_STAT : getSpecialDefenseEV();
    }
    
    public int getSpeedEV() {
        return speedEV;
    }
    
    public void setSpeedEV(int speedEV) {
        this.speedEV = totalEV() + speedEV <= MAX_EFFORT_VALUE_TOTAL ? getSpeedEV() + speedEV :
                       getSpeedEV() + (MAX_EFFORT_VALUE_TOTAL - totalEV());
        this.speedEV = getSpeedEV() > MAX_EFFORT_VALUE_PER_STAT ? MAX_EFFORT_VALUE_PER_STAT : getSpeedEV();
    }
}