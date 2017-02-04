package com.horse.pokemon.ObjectData.PokemonData;

public class CalculateResistance {
    private static final float DEFAULT_RESISTANCE = 1f;
    private static final float NO_EFFECT          = 0f;
    private static final float DOUBLE_DAMAGE      = 2f;
    private static final float QUADRUPLE_DAMAGE   = 4;
    private static final float HALF_DAMAGE        = 0.5f;
    private static final float QUARTER_DAMAGE     = 0.25f;
    
    private static final float[] RESISTANCE_DATA = new float[] {
        
    };
    
    public static float calculate(final Moves attackingMove, final Pokemon defendingPokemon) {
        int                  index                   = attackingMove.getMoveType().ordinal();
        final PokemonTypes[] typesOfDefendingPokemon = defendingPokemon.getInformation().getPokemonTypes();
        final int            pokemonTypesLength      = PokemonTypes.values().length;
        index += typesOfDefendingPokemon.length == 1 ? typesOfDefendingPokemon[0].ordinal() * pokemonTypesLength :
                 typesOfDefendingPokemon[0].ordinal() * pokemonTypesLength + typesOfDefendingPokemon[1].ordinal() * pokemonTypesLength;
        return index < getResistanceData().length ? getResistanceData()[index] : getDefaultResistance();
    }
    
    public static float getDefaultResistance() {
        return DEFAULT_RESISTANCE;
    }
    
    public static float getNoEffect() {
        return NO_EFFECT;
    }
    
    public static float getDoubleDamage() {
        return DOUBLE_DAMAGE;
    }
    
    public static float getQuadrupleDamage() {
        return QUADRUPLE_DAMAGE;
    }
    
    public static float getHalfDamage() {
        return HALF_DAMAGE;
    }
    
    public static float getQuarterDamage() {
        return QUARTER_DAMAGE;
    }
    
    public static float[] getResistanceData() {
        return RESISTANCE_DATA;
    }
}
