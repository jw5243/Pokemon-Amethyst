package com.horse.pokemon.amethyst.data.pokemon;

public class Moves {
    private String       name;
    private String       category;
    private PokemonTypes moveType;
    private int          powerPoints;
    private int          basePower;
    private int          accuracy;
    private int          effect;
    private int          technicalMachine; //Maybe make a class for taking in this data type.
    private int          speedPriority;
    private String       battleEffect;
    private String       secondaryEffect;
    private int          currentPowerPoints;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public PokemonTypes getMoveType() {
        return moveType;
    }
    
    public void setMoveType(PokemonTypes moveType) {
        this.moveType = moveType;
    }
    
    public int getPowerPoints() {
        return powerPoints;
    }
    
    public void setPowerPoints(int powerPoints) {
        this.powerPoints = powerPoints;
    }
    
    public int getBasePower() {
        return basePower;
    }
    
    public void setBasePower(int basePower) {
        this.basePower = basePower;
    }
    
    public int getAccuracy() {
        return accuracy;
    }
    
    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }
    
    public int getEffect() {
        return effect;
    }
    
    public void setEffect(int effect) {
        this.effect = effect;
    }
    
    public int getTechnicalMachine() {
        return technicalMachine;
    }
    
    public void setTechnicalMachine(int technicalMachine) {
        this.technicalMachine = technicalMachine;
    }
    
    public int getSpeedPriority() {
        return speedPriority;
    }
    
    public void setSpeedPriority(int speedPriority) {
        this.speedPriority = speedPriority;
    }
    
    public String getBattleEffect() {
        return battleEffect;
    }
    
    public void setBattleEffect(String battleEffect) {
        this.battleEffect = battleEffect;
    }
    
    public String getSecondaryEffect() {
        return secondaryEffect;
    }
    
    public void setSecondaryEffect(String secondaryEffect) {
        this.secondaryEffect = secondaryEffect;
    }
    
    public int getCurrentPowerPoints() {
        return currentPowerPoints;
    }
    
    public void setCurrentPowerPoints(int currentPowerPoints) {
        this.currentPowerPoints = currentPowerPoints;
    }
    
    @Override
    public String toString() {
        return String.format(
                "{Name = %s, Category = %s, Move Type = %s, Power Points = %s, Base Power = %s, Accuracy = %s, " +
                "Effect = %s, TM Number = %s, Speed Priority = %s, Battle Effect = %s, Secondary Effect = %s, " +
                "Current Power Points = %s}", getName(), getCategory(), getMoveType(), getPowerPoints(), getBasePower(),
                getAccuracy(), getEffect(), getTechnicalMachine(), getSpeedPriority(), getBattleEffect(),
                getSecondaryEffect(), getCurrentPowerPoints()
        );
    }
}