package com.horse.pokemon.amethyst.data.pokemon;

/**
 * Class for storing the value for a {@link Pokemon} that will raise each stat according to its corresponding
 * {@code EffortValue} stat.  There is a mutable and immutable {@code EffortValue} for each stat.  The immutable stat is
 * for when a {@link Pokemon} is defeated and the winner gains {@code EffortValue} points.  The mutable value is the
 * changing number for when the opposing {@link Pokemon} is defeated (They keep track of the EV's).
 *
 * @see Pokemon
 * @see IndividualValue
 */
public class EffortValue {
    private static final int MAX_EFFORT_VALUE_TOTAL    = 510;
    private static final int MAX_EFFORT_VALUE_PER_STAT = 255;
    private static final int DEFAULT_EFFORT_VALUE      = 0;
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
        this(getDefaultEffortValue(), getDefaultEffortValue(), getDefaultEffortValue(), getDefaultEffortValue(),
             getDefaultEffortValue(), getDefaultEffortValue()
        );
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
    
    public static int getMaxEffortValueTotal() {
        return MAX_EFFORT_VALUE_TOTAL;
    }
    
    public static int getMaxEffortValuePerStat() {
        return MAX_EFFORT_VALUE_PER_STAT;
    }
    
    public static int getDefaultEffortValue() {
        return DEFAULT_EFFORT_VALUE;
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
        setHealthEV(getDefaultEffortValue());
        setAttackEV(getDefaultEffortValue());
        setDefenseEV(getDefaultEffortValue());
        setSpecialAttackEV(getDefaultEffortValue());
        setSpecialDefenseEV(getDefaultEffortValue());
        setSpeedEV(getDefaultEffortValue());
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
        this.healthEV = totalEV() + healthEV <= getMaxEffortValueTotal() ? getHealthEV() + healthEV :
                        getHealthEV() + (getMaxEffortValueTotal() - totalEV());
        this.healthEV = getHealthEV() > getMaxEffortValuePerStat() ? getMaxEffortValuePerStat() : getHealthEV();
    }
    
    public int getAttackEV() {
        return attackEV;
    }
    
    public void setAttackEV(int attackEV) {
        this.attackEV = totalEV() + attackEV <= getMaxEffortValueTotal() ? getAttackEV() + attackEV :
                        getAttackEV() + (getMaxEffortValueTotal() - totalEV());
        this.attackEV = getAttackEV() > getMaxEffortValuePerStat() ? getMaxEffortValuePerStat() : getAttackEV();
    }
    
    public int getDefenseEV() {
        return defenseEV;
    }
    
    public void setDefenseEV(int defenseEV) {
        this.defenseEV = totalEV() + defenseEV <= MAX_EFFORT_VALUE_TOTAL ? getDefenseEV() + defenseEV :
                         getDefenseEV() + (getMaxEffortValueTotal() - totalEV());
        this.defenseEV = getDefenseEV() > getMaxEffortValuePerStat() ? getMaxEffortValuePerStat() : getDefenseEV();
    }
    
    public int getSpecialAttackEV() {
        return specialAttackEV;
    }
    
    public void setSpecialAttackEV(int specialAttackEV) {
        this.specialAttackEV =
                totalEV() + specialAttackEV <= getMaxEffortValueTotal() ? getSpecialAttackEV() + specialAttackEV :
                getSpecialAttackEV() + (getMaxEffortValueTotal() - totalEV());
        this.specialAttackEV =
                getSpecialAttackEV() > getMaxEffortValuePerStat() ? getMaxEffortValuePerStat() : getSpecialAttackEV();
    }
    
    public int getSpecialDefenseEV() {
        return specialDefenseEV;
    }
    
    public void setSpecialDefenseEV(int specialDefenseEV) {
        this.specialDefenseEV =
                totalEV() + specialDefenseEV <= MAX_EFFORT_VALUE_TOTAL ? getSpecialDefenseEV() + specialDefenseEV :
                getSpecialDefenseEV() + (getMaxEffortValueTotal() - totalEV());
        this.specialDefenseEV =
                getSpecialDefenseEV() > getMaxEffortValuePerStat() ? getMaxEffortValuePerStat() : getSpecialDefenseEV();
    }
    
    public int getSpeedEV() {
        return speedEV;
    }
    
    public void setSpeedEV(int speedEV) {
        this.speedEV = totalEV() + speedEV <= getMaxEffortValueTotal() ? getSpeedEV() + speedEV :
                       getSpeedEV() + (getMaxEffortValueTotal() - totalEV());
        this.speedEV = getSpeedEV() > getMaxEffortValuePerStat() ? getMaxEffortValuePerStat() : getSpeedEV();
    }
    
    public int[] getEVs() {
        return new int[] {
                getHealthEV(), getAttackEV(), getDefenseEV(), getSpecialAttackEV(), getSpecialDefenseEV(), getSpeedEV()
        };
    }
}