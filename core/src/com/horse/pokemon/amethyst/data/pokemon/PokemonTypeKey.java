package com.horse.pokemon.amethyst.data.pokemon;

import java.util.Arrays;
import java.util.Objects;

public class PokemonTypeKey {
    private final int attackingType;
    private final int defendingTypeOne;
    
    PokemonTypeKey(PokemonTypes attackingType, PokemonTypes defendingTypeOne) {
        this.attackingType = attackingType.ordinal();
        this.defendingTypeOne = defendingTypeOne == null ? -1 : defendingTypeOne.ordinal();
    }
    
    @Override
    public int hashCode() {
        return Arrays.hashCode(new Object[] {getAttackingType(), getDefendingTypeOne()});
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
        return Objects.equals(this.getAttackingType(), that.getAttackingType()) &&
               Objects.equals(this.getDefendingTypeOne(), that.getDefendingTypeOne());
    }
    
    private int getAttackingType() {
        return attackingType;
    }
    
    private int getDefendingTypeOne() {
        return defendingTypeOne;
    }
}
