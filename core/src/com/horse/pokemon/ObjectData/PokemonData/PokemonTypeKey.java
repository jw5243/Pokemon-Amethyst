package com.horse.pokemon.ObjectData.PokemonData;

import java.util.Arrays;
import java.util.Objects;

public class PokemonTypeKey {
    private final int attackingType;
    private final int defendingTypeOne;
    private final int defendingTypeTwo;
    
    public PokemonTypeKey(PokemonTypes attackingType, PokemonTypes defendingTypeOne) {
        this(attackingType, defendingTypeOne, null);
    }
    
    public PokemonTypeKey(PokemonTypes attackingType, PokemonTypes defendingTypeOne, PokemonTypes defendingTypeTwo) {
        this.attackingType = attackingType.ordinal();
        this.defendingTypeOne = defendingTypeOne == null ? -1 : defendingTypeOne.ordinal();
        this.defendingTypeTwo = defendingTypeTwo == null ? -1 : defendingTypeTwo.ordinal();
    }
    
    @Override
    public int hashCode() {
        return Arrays.hashCode(new Object[] {getAttackingType(), getDefendingTypeOne(), getDefendingTypeTwo()});
    }
    
    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof PokemonTypeKey)) {
            return false;
        }
        PokemonTypeKey that = (PokemonTypeKey)(o);
        return (Objects.equals(this.getAttackingType(), that.getAttackingType())) &&
               (Objects.equals(this.getDefendingTypeOne(), that.getDefendingTypeOne()) ||
                Objects.equals(this.getDefendingTypeOne(), that.getDefendingTypeTwo())) &&
               (Objects.equals(this.getDefendingTypeTwo(), that.getDefendingTypeTwo()) ||
                Objects.equals(this.getDefendingTypeTwo(), that.getDefendingTypeOne()));
    }
    
    public int getAttackingType() {
        return attackingType;
    }
    
    public int getDefendingTypeOne() {
        return defendingTypeOne;
    }
    
    public int getDefendingTypeTwo() {
        return defendingTypeTwo;
    }
}
