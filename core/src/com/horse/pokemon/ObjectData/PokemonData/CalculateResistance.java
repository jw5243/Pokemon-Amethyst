package com.horse.pokemon.ObjectData.PokemonData;

import com.horse.pokemon.DataReaders.MoveDataReader;
import com.horse.pokemon.DataReaders.PokemonDataReader;
import com.horse.utility.MapBuilders.ObjectKey.ObjectIntMapBuilder;
import gnu.trove.map.hash.TObjectIntHashMap;

import static com.horse.pokemon.ObjectData.PokemonData.PokemonTypes.BUG;
import static com.horse.pokemon.ObjectData.PokemonData.PokemonTypes.DARK;
import static com.horse.pokemon.ObjectData.PokemonData.PokemonTypes.DRAGON;
import static com.horse.pokemon.ObjectData.PokemonData.PokemonTypes.ELECTRIC;
import static com.horse.pokemon.ObjectData.PokemonData.PokemonTypes.FAIRY;
import static com.horse.pokemon.ObjectData.PokemonData.PokemonTypes.FIGHT;
import static com.horse.pokemon.ObjectData.PokemonData.PokemonTypes.FIRE;
import static com.horse.pokemon.ObjectData.PokemonData.PokemonTypes.FLYING;
import static com.horse.pokemon.ObjectData.PokemonData.PokemonTypes.GHOST;
import static com.horse.pokemon.ObjectData.PokemonData.PokemonTypes.GRASS;
import static com.horse.pokemon.ObjectData.PokemonData.PokemonTypes.GROUND;
import static com.horse.pokemon.ObjectData.PokemonData.PokemonTypes.ICE;
import static com.horse.pokemon.ObjectData.PokemonData.PokemonTypes.NORMAL;
import static com.horse.pokemon.ObjectData.PokemonData.PokemonTypes.POISON;
import static com.horse.pokemon.ObjectData.PokemonData.PokemonTypes.PSYCHIC;
import static com.horse.pokemon.ObjectData.PokemonData.PokemonTypes.ROCK;
import static com.horse.pokemon.ObjectData.PokemonData.PokemonTypes.STEEL;
import static com.horse.pokemon.ObjectData.PokemonData.PokemonTypes.WATER;

public class CalculateResistance {
    private static final int DEFAULT_RESISTANCE = 100;
    private static final int NO_EFFECT          = 0;
    private static final int DOUBLE_DAMAGE      = 200;
    private static final int QUADRUPLE_DAMAGE   = 400;
    private static final int HALF_DAMAGE        = 50;
    private static final int QUARTER_DAMAGE     = 25;
    
    /*private static final TIntObjectHashMap<TIntObjectHashMap<TIntIntHashMap>> RESISTANCE_DATA =
        (TIntObjectHashMap<TIntObjectHashMap<TIntIntHashMap>>)(IntObjectMapBuilder.<TIntObjectHashMap<TIntIntHashMap>>unordered()
                                                                   .put(PokemonTypes.NORMAL.ordinal(),
                                                                        IntObjectMapBuilder.<TIntIntHashMap>unordered()
                                                                            .put(PokemonTypes.NORMAL.ordinal(),
                                                                                 IntIntMapBuilder.unordered().put(
                                                                                     PokemonTypes.NORMAL.ordinal(),
                                                                                     getDefaultResistance()
                                                                                 ).build()
                                                                            ).build()
                                                                   ).put(PokemonTypes.FIRE.ordinal(),
                                                                         IntObjectMapBuilder.<TIntIntHashMap>unordered()
                                                                             .put(PokemonTypes.NORMAL.ordinal(),
                                                                                  IntIntMapBuilder.unordered().put(
                                                                                      PokemonTypes.NORMAL.ordinal(),
                                                                                      getDefaultResistance()
                                                                                  ).build()
                                                                             ).build()
            ).put(PokemonTypes.WATER.ordinal(), IntObjectMapBuilder.<TIntIntHashMap>unordered()
                                                    .put(PokemonTypes.NORMAL.ordinal(), IntIntMapBuilder.unordered()
                                                                                                        .put(
                                                                                                            PokemonTypes.NORMAL
                                                                                                                .ordinal(),
                                                                                                            getDefaultResistance()
                                                                                                        ).build())
                                                    .build()).build());*/
    
    /**
     * The {@link TObjectIntHashMap} storing the multiplier for when a {@link Pokemon} is attacking another {@code Pokemon}.
     * These values are stored in the order of {@link PokemonTypes}, which should always be:
     * <ol>
     * <li>{@link PokemonTypes#NORMAL}
     * <li>{@link PokemonTypes#FIRE}
     * <li>{@link PokemonTypes#WATER}
     * <li>{@link PokemonTypes#ELECTRIC}
     * <li>{@link PokemonTypes#GRASS}
     * <li>{@link PokemonTypes#ICE}
     * <li>{@link PokemonTypes#FIGHT}
     * <li>{@link PokemonTypes#POISON}
     * <li>{@link PokemonTypes#GROUND}
     * <li>{@link PokemonTypes#FLYING}
     * <li>{@link PokemonTypes#PSYCHIC}
     * <li>{@link PokemonTypes#BUG}
     * <li>{@link PokemonTypes#ROCK}
     * <li>{@link PokemonTypes#GHOST}
     * <li>{@link PokemonTypes#DRAGON}
     * <li>{@link PokemonTypes#DARK}
     * <li>{@link PokemonTypes#STEEL}
     * <li>{@link PokemonTypes#FAIRY}
     * </ol>
     *
     * @see PokemonTypes
     * @see TObjectIntHashMap
     * @see PokemonTypeKey
     * @see ObjectIntMapBuilder
     */
    private static final TObjectIntHashMap<PokemonTypeKey> RESISTANCE_DATA =
        (TObjectIntHashMap<PokemonTypeKey>)(ObjectIntMapBuilder.<PokemonTypeKey>unordered()
                                                //Resistance Values for moves that are normal and the there is only one type of the defending Pokemon.
                                                .put(new PokemonTypeKey(NORMAL, NORMAL), getDefaultResistance())
                                                .put(new PokemonTypeKey(NORMAL, FIRE), getDefaultResistance())
                                                .put(new PokemonTypeKey(NORMAL, WATER), getDefaultResistance())
                                                .put(new PokemonTypeKey(NORMAL, ELECTRIC), getDefaultResistance())
                                                .put(new PokemonTypeKey(NORMAL, GRASS), getDefaultResistance())
                                                .put(new PokemonTypeKey(NORMAL, ICE), getDefaultResistance())
                                                .put(new PokemonTypeKey(NORMAL, FIGHT), getDefaultResistance())
                                                .put(new PokemonTypeKey(NORMAL, POISON), getDefaultResistance())
                                                .put(new PokemonTypeKey(NORMAL, GROUND), getDefaultResistance())
                                                .put(new PokemonTypeKey(NORMAL, FLYING), getDefaultResistance())
                                                .put(new PokemonTypeKey(NORMAL, PSYCHIC), getDefaultResistance())
                                                .put(new PokemonTypeKey(NORMAL, BUG), getDefaultResistance())
                                                .put(new PokemonTypeKey(NORMAL, ROCK), getHalfDamage())
                                                .put(new PokemonTypeKey(NORMAL, GHOST), getNoEffect())
                                                .put(new PokemonTypeKey(NORMAL, DRAGON), getDefaultResistance())
                                                .put(new PokemonTypeKey(NORMAL, DARK), getDefaultResistance())
                                                .put(new PokemonTypeKey(NORMAL, STEEL), getHalfDamage())
                                                .put(new PokemonTypeKey(NORMAL, FAIRY), getDefaultResistance())
            
                                                .put(new PokemonTypeKey(FIRE, NORMAL), getDefaultResistance())
                                                .put(new PokemonTypeKey(FIRE, FIRE), getDefaultResistance())
                                                .put(new PokemonTypeKey(FIRE, WATER), getDefaultResistance())
                                                .put(new PokemonTypeKey(FIRE, ELECTRIC), getDefaultResistance())
                                                .put(new PokemonTypeKey(FIRE, GRASS), getDefaultResistance())
                                                .put(new PokemonTypeKey(FIRE, ICE), getDefaultResistance())
                                                .put(new PokemonTypeKey(FIRE, FIGHT), getDefaultResistance())
                                                .put(new PokemonTypeKey(FIRE, POISON), getDefaultResistance())
                                                .put(new PokemonTypeKey(FIRE, GROUND), getDefaultResistance())
                                                .put(new PokemonTypeKey(FIRE, FLYING), getDefaultResistance())
                                                .put(new PokemonTypeKey(FIRE, PSYCHIC), getDefaultResistance())
                                                .put(new PokemonTypeKey(FIRE, BUG), getDefaultResistance())
                                                .put(new PokemonTypeKey(FIRE, ROCK), getHalfDamage())
                                                .put(new PokemonTypeKey(FIRE, GHOST), getNoEffect())
                                                .put(new PokemonTypeKey(FIRE, DRAGON), getDefaultResistance())
                                                .put(new PokemonTypeKey(FIRE, DARK), getDefaultResistance())
                                                .put(new PokemonTypeKey(FIRE, STEEL), getHalfDamage())
                                                .put(new PokemonTypeKey(FIRE, FAIRY), getDefaultResistance())
            
                                                .put(new PokemonTypeKey(WATER, NORMAL), getDefaultResistance())
                                                .put(new PokemonTypeKey(WATER, FIRE), getDefaultResistance())
                                                .put(new PokemonTypeKey(WATER, WATER), getDefaultResistance())
                                                .put(new PokemonTypeKey(WATER, ELECTRIC), getDefaultResistance())
                                                .put(new PokemonTypeKey(WATER, GRASS), getDefaultResistance())
                                                .put(new PokemonTypeKey(WATER, ICE), getDefaultResistance())
                                                .put(new PokemonTypeKey(WATER, FIGHT), getDefaultResistance())
                                                .put(new PokemonTypeKey(WATER, POISON), getDefaultResistance())
                                                .put(new PokemonTypeKey(WATER, GROUND), getDefaultResistance())
                                                .put(new PokemonTypeKey(WATER, FLYING), getDefaultResistance())
                                                .put(new PokemonTypeKey(WATER, PSYCHIC), getDefaultResistance())
                                                .put(new PokemonTypeKey(WATER, BUG), getDefaultResistance())
                                                .put(new PokemonTypeKey(WATER, ROCK), getHalfDamage())
                                                .put(new PokemonTypeKey(WATER, GHOST), getNoEffect())
                                                .put(new PokemonTypeKey(WATER, DRAGON), getDefaultResistance())
                                                .put(new PokemonTypeKey(WATER, DARK), getDefaultResistance())
                                                .put(new PokemonTypeKey(WATER, STEEL), getHalfDamage())
                                                .put(new PokemonTypeKey(WATER, FAIRY), getDefaultResistance())
            
                                                .put(new PokemonTypeKey(ELECTRIC, NORMAL), getDefaultResistance())
                                                .put(new PokemonTypeKey(ELECTRIC, FIRE), getDefaultResistance())
                                                .put(new PokemonTypeKey(ELECTRIC, WATER), getDefaultResistance())
                                                .put(new PokemonTypeKey(ELECTRIC, ELECTRIC), getDefaultResistance())
                                                .put(new PokemonTypeKey(ELECTRIC, GRASS), getDefaultResistance())
                                                .put(new PokemonTypeKey(ELECTRIC, ICE), getDefaultResistance())
                                                .put(new PokemonTypeKey(ELECTRIC, FIGHT), getDefaultResistance())
                                                .put(new PokemonTypeKey(ELECTRIC, POISON), getDefaultResistance())
                                                .put(new PokemonTypeKey(ELECTRIC, GROUND), getDefaultResistance())
                                                .put(new PokemonTypeKey(ELECTRIC, FLYING), getDefaultResistance())
                                                .put(new PokemonTypeKey(ELECTRIC, PSYCHIC), getDefaultResistance())
                                                .put(new PokemonTypeKey(ELECTRIC, BUG), getDefaultResistance())
                                                .put(new PokemonTypeKey(ELECTRIC, ROCK), getHalfDamage())
                                                .put(new PokemonTypeKey(ELECTRIC, GHOST), getNoEffect())
                                                .put(new PokemonTypeKey(ELECTRIC, DRAGON), getDefaultResistance())
                                                .put(new PokemonTypeKey(ELECTRIC, DARK), getDefaultResistance())
                                                .put(new PokemonTypeKey(ELECTRIC, STEEL), getHalfDamage())
                                                .put(new PokemonTypeKey(ELECTRIC, FAIRY), getDefaultResistance())
            
                                                .put(new PokemonTypeKey(GRASS, NORMAL), getDefaultResistance())
                                                .put(new PokemonTypeKey(GRASS, FIRE), getDefaultResistance())
                                                .put(new PokemonTypeKey(GRASS, WATER), getDefaultResistance())
                                                .put(new PokemonTypeKey(GRASS, ELECTRIC), getDefaultResistance())
                                                .put(new PokemonTypeKey(GRASS, GRASS), getDefaultResistance())
                                                .put(new PokemonTypeKey(GRASS, ICE), getDefaultResistance())
                                                .put(new PokemonTypeKey(GRASS, FIGHT), getDefaultResistance())
                                                .put(new PokemonTypeKey(GRASS, POISON), getDefaultResistance())
                                                .put(new PokemonTypeKey(GRASS, GROUND), getDefaultResistance())
                                                .put(new PokemonTypeKey(GRASS, FLYING), getDefaultResistance())
                                                .put(new PokemonTypeKey(GRASS, PSYCHIC), getDefaultResistance())
                                                .put(new PokemonTypeKey(GRASS, BUG), getDefaultResistance())
                                                .put(new PokemonTypeKey(GRASS, ROCK), getHalfDamage())
                                                .put(new PokemonTypeKey(GRASS, GHOST), getNoEffect())
                                                .put(new PokemonTypeKey(GRASS, DRAGON), getDefaultResistance())
                                                .put(new PokemonTypeKey(GRASS, DARK), getDefaultResistance())
                                                .put(new PokemonTypeKey(GRASS, STEEL), getHalfDamage())
                                                .put(new PokemonTypeKey(GRASS, FAIRY), getDefaultResistance())
            
                                                .put(new PokemonTypeKey(ICE, NORMAL), getDefaultResistance())
                                                .put(new PokemonTypeKey(ICE, FIRE), getDefaultResistance())
                                                .put(new PokemonTypeKey(ICE, WATER), getDefaultResistance())
                                                .put(new PokemonTypeKey(ICE, ELECTRIC), getDefaultResistance())
                                                .put(new PokemonTypeKey(ICE, GRASS), getDefaultResistance())
                                                .put(new PokemonTypeKey(ICE, ICE), getDefaultResistance())
                                                .put(new PokemonTypeKey(ICE, FIGHT), getDefaultResistance())
                                                .put(new PokemonTypeKey(ICE, POISON), getDefaultResistance())
                                                .put(new PokemonTypeKey(ICE, GROUND), getDefaultResistance())
                                                .put(new PokemonTypeKey(ICE, FLYING), getDefaultResistance())
                                                .put(new PokemonTypeKey(ICE, PSYCHIC), getDefaultResistance())
                                                .put(new PokemonTypeKey(ICE, BUG), getDefaultResistance())
                                                .put(new PokemonTypeKey(ICE, ROCK), getHalfDamage())
                                                .put(new PokemonTypeKey(ICE, GHOST), getNoEffect())
                                                .put(new PokemonTypeKey(ICE, DRAGON), getDefaultResistance())
                                                .put(new PokemonTypeKey(ICE, DARK), getDefaultResistance())
                                                .put(new PokemonTypeKey(ICE, STEEL), getHalfDamage())
                                                .put(new PokemonTypeKey(ICE, FAIRY), getDefaultResistance())
            
                                                .put(new PokemonTypeKey(FIGHT, NORMAL), getDefaultResistance())
                                                .put(new PokemonTypeKey(FIGHT, FIRE), getDefaultResistance())
                                                .put(new PokemonTypeKey(FIGHT, WATER), getDefaultResistance())
                                                .put(new PokemonTypeKey(FIGHT, ELECTRIC), getDefaultResistance())
                                                .put(new PokemonTypeKey(FIGHT, GRASS), getDefaultResistance())
                                                .put(new PokemonTypeKey(FIGHT, ICE), getDefaultResistance())
                                                .put(new PokemonTypeKey(FIGHT, FIGHT), getDefaultResistance())
                                                .put(new PokemonTypeKey(FIGHT, POISON), getDefaultResistance())
                                                .put(new PokemonTypeKey(FIGHT, GROUND), getDefaultResistance())
                                                .put(new PokemonTypeKey(FIGHT, FLYING), getDefaultResistance())
                                                .put(new PokemonTypeKey(FIGHT, PSYCHIC), getDefaultResistance())
                                                .put(new PokemonTypeKey(FIGHT, BUG), getDefaultResistance())
                                                .put(new PokemonTypeKey(FIGHT, ROCK), getHalfDamage())
                                                .put(new PokemonTypeKey(FIGHT, GHOST), getNoEffect())
                                                .put(new PokemonTypeKey(FIGHT, DRAGON), getDefaultResistance())
                                                .put(new PokemonTypeKey(FIGHT, DARK), getDefaultResistance())
                                                .put(new PokemonTypeKey(FIGHT, STEEL), getHalfDamage())
                                                .put(new PokemonTypeKey(FIGHT, FAIRY), getDefaultResistance())
            
                                                .put(new PokemonTypeKey(POISON, NORMAL), getDefaultResistance())
                                                .put(new PokemonTypeKey(POISON, FIRE), getDefaultResistance())
                                                .put(new PokemonTypeKey(POISON, WATER), getDefaultResistance())
                                                .put(new PokemonTypeKey(POISON, ELECTRIC), getDefaultResistance())
                                                .put(new PokemonTypeKey(POISON, GRASS), getDefaultResistance())
                                                .put(new PokemonTypeKey(POISON, ICE), getDefaultResistance())
                                                .put(new PokemonTypeKey(POISON, FIGHT), getDefaultResistance())
                                                .put(new PokemonTypeKey(POISON, POISON), getDefaultResistance())
                                                .put(new PokemonTypeKey(POISON, GROUND), getDefaultResistance())
                                                .put(new PokemonTypeKey(POISON, FLYING), getDefaultResistance())
                                                .put(new PokemonTypeKey(POISON, PSYCHIC), getDefaultResistance())
                                                .put(new PokemonTypeKey(POISON, BUG), getDefaultResistance())
                                                .put(new PokemonTypeKey(POISON, ROCK), getHalfDamage())
                                                .put(new PokemonTypeKey(POISON, GHOST), getNoEffect())
                                                .put(new PokemonTypeKey(POISON, DRAGON), getDefaultResistance())
                                                .put(new PokemonTypeKey(POISON, DARK), getDefaultResistance())
                                                .put(new PokemonTypeKey(POISON, STEEL), getHalfDamage())
                                                .put(new PokemonTypeKey(POISON, FAIRY), getDefaultResistance())
            
                                                .put(new PokemonTypeKey(GROUND, NORMAL), getDefaultResistance())
                                                .put(new PokemonTypeKey(GROUND, FIRE), getDefaultResistance())
                                                .put(new PokemonTypeKey(GROUND, WATER), getDefaultResistance())
                                                .put(new PokemonTypeKey(GROUND, ELECTRIC), getDefaultResistance())
                                                .put(new PokemonTypeKey(GROUND, GRASS), getDefaultResistance())
                                                .put(new PokemonTypeKey(GROUND, ICE), getDefaultResistance())
                                                .put(new PokemonTypeKey(GROUND, FIGHT), getDefaultResistance())
                                                .put(new PokemonTypeKey(GROUND, POISON), getDefaultResistance())
                                                .put(new PokemonTypeKey(GROUND, GROUND), getDefaultResistance())
                                                .put(new PokemonTypeKey(GROUND, FLYING), getDefaultResistance())
                                                .put(new PokemonTypeKey(GROUND, PSYCHIC), getDefaultResistance())
                                                .put(new PokemonTypeKey(GROUND, BUG), getDefaultResistance())
                                                .put(new PokemonTypeKey(GROUND, ROCK), getHalfDamage())
                                                .put(new PokemonTypeKey(GROUND, GHOST), getNoEffect())
                                                .put(new PokemonTypeKey(GROUND, DRAGON), getDefaultResistance())
                                                .put(new PokemonTypeKey(GROUND, DARK), getDefaultResistance())
                                                .put(new PokemonTypeKey(GROUND, STEEL), getHalfDamage())
                                                .put(new PokemonTypeKey(GROUND, FAIRY), getDefaultResistance())
            
                                                .put(new PokemonTypeKey(FLYING, NORMAL), getDefaultResistance())
                                                .put(new PokemonTypeKey(FLYING, FIRE), getDefaultResistance())
                                                .put(new PokemonTypeKey(FLYING, WATER), getDefaultResistance())
                                                .put(new PokemonTypeKey(FLYING, ELECTRIC), getDefaultResistance())
                                                .put(new PokemonTypeKey(FLYING, GRASS), getDefaultResistance())
                                                .put(new PokemonTypeKey(FLYING, ICE), getDefaultResistance())
                                                .put(new PokemonTypeKey(FLYING, FIGHT), getDefaultResistance())
                                                .put(new PokemonTypeKey(FLYING, POISON), getDefaultResistance())
                                                .put(new PokemonTypeKey(FLYING, GROUND), getDefaultResistance())
                                                .put(new PokemonTypeKey(FLYING, FLYING), getDefaultResistance())
                                                .put(new PokemonTypeKey(FLYING, PSYCHIC), getDefaultResistance())
                                                .put(new PokemonTypeKey(FLYING, BUG), getDefaultResistance())
                                                .put(new PokemonTypeKey(FLYING, ROCK), getHalfDamage())
                                                .put(new PokemonTypeKey(FLYING, GHOST), getNoEffect())
                                                .put(new PokemonTypeKey(FLYING, DRAGON), getDefaultResistance())
                                                .put(new PokemonTypeKey(FLYING, DARK), getDefaultResistance())
                                                .put(new PokemonTypeKey(FLYING, STEEL), getHalfDamage())
                                                .put(new PokemonTypeKey(FLYING, FAIRY), getDefaultResistance())
            
                                                .put(new PokemonTypeKey(PSYCHIC, NORMAL), getDefaultResistance())
                                                .put(new PokemonTypeKey(PSYCHIC, FIRE), getDefaultResistance())
                                                .put(new PokemonTypeKey(PSYCHIC, WATER), getDefaultResistance())
                                                .put(new PokemonTypeKey(PSYCHIC, ELECTRIC), getDefaultResistance())
                                                .put(new PokemonTypeKey(PSYCHIC, GRASS), getDefaultResistance())
                                                .put(new PokemonTypeKey(PSYCHIC, ICE), getDefaultResistance())
                                                .put(new PokemonTypeKey(PSYCHIC, FIGHT), getDefaultResistance())
                                                .put(new PokemonTypeKey(PSYCHIC, POISON), getDefaultResistance())
                                                .put(new PokemonTypeKey(PSYCHIC, GROUND), getDefaultResistance())
                                                .put(new PokemonTypeKey(PSYCHIC, FLYING), getDefaultResistance())
                                                .put(new PokemonTypeKey(PSYCHIC, PSYCHIC), getDefaultResistance())
                                                .put(new PokemonTypeKey(PSYCHIC, BUG), getDefaultResistance())
                                                .put(new PokemonTypeKey(PSYCHIC, ROCK), getHalfDamage())
                                                .put(new PokemonTypeKey(PSYCHIC, GHOST), getNoEffect())
                                                .put(new PokemonTypeKey(PSYCHIC, DRAGON), getDefaultResistance())
                                                .put(new PokemonTypeKey(PSYCHIC, DARK), getDefaultResistance())
                                                .put(new PokemonTypeKey(PSYCHIC, STEEL), getHalfDamage())
                                                .put(new PokemonTypeKey(PSYCHIC, FAIRY), getDefaultResistance())
            
                                                .put(new PokemonTypeKey(BUG, NORMAL), getDefaultResistance())
                                                .put(new PokemonTypeKey(BUG, FIRE), getDefaultResistance())
                                                .put(new PokemonTypeKey(BUG, WATER), getDefaultResistance())
                                                .put(new PokemonTypeKey(BUG, ELECTRIC), getDefaultResistance())
                                                .put(new PokemonTypeKey(BUG, GRASS), getDefaultResistance())
                                                .put(new PokemonTypeKey(BUG, ICE), getDefaultResistance())
                                                .put(new PokemonTypeKey(BUG, FIGHT), getDefaultResistance())
                                                .put(new PokemonTypeKey(BUG, POISON), getDefaultResistance())
                                                .put(new PokemonTypeKey(BUG, GROUND), getDefaultResistance())
                                                .put(new PokemonTypeKey(BUG, FLYING), getDefaultResistance())
                                                .put(new PokemonTypeKey(BUG, PSYCHIC), getDefaultResistance())
                                                .put(new PokemonTypeKey(BUG, BUG), getDefaultResistance())
                                                .put(new PokemonTypeKey(BUG, ROCK), getHalfDamage())
                                                .put(new PokemonTypeKey(BUG, GHOST), getNoEffect())
                                                .put(new PokemonTypeKey(BUG, DRAGON), getDefaultResistance())
                                                .put(new PokemonTypeKey(BUG, DARK), getDefaultResistance())
                                                .put(new PokemonTypeKey(BUG, STEEL), getHalfDamage())
                                                .put(new PokemonTypeKey(BUG, FAIRY), getDefaultResistance())
            
                                                .put(new PokemonTypeKey(ROCK, NORMAL), getDefaultResistance())
                                                .put(new PokemonTypeKey(ROCK, FIRE), getDefaultResistance())
                                                .put(new PokemonTypeKey(ROCK, WATER), getDefaultResistance())
                                                .put(new PokemonTypeKey(ROCK, ELECTRIC), getDefaultResistance())
                                                .put(new PokemonTypeKey(ROCK, GRASS), getDefaultResistance())
                                                .put(new PokemonTypeKey(ROCK, ICE), getDefaultResistance())
                                                .put(new PokemonTypeKey(ROCK, FIGHT), getDefaultResistance())
                                                .put(new PokemonTypeKey(ROCK, POISON), getDefaultResistance())
                                                .put(new PokemonTypeKey(ROCK, GROUND), getDefaultResistance())
                                                .put(new PokemonTypeKey(ROCK, FLYING), getDefaultResistance())
                                                .put(new PokemonTypeKey(ROCK, PSYCHIC), getDefaultResistance())
                                                .put(new PokemonTypeKey(ROCK, BUG), getDefaultResistance())
                                                .put(new PokemonTypeKey(ROCK, ROCK), getHalfDamage())
                                                .put(new PokemonTypeKey(ROCK, GHOST), getNoEffect())
                                                .put(new PokemonTypeKey(ROCK, DRAGON), getDefaultResistance())
                                                .put(new PokemonTypeKey(ROCK, DARK), getDefaultResistance())
                                                .put(new PokemonTypeKey(ROCK, STEEL), getHalfDamage())
                                                .put(new PokemonTypeKey(ROCK, FAIRY), getDefaultResistance())
            
                                                .put(new PokemonTypeKey(GHOST, NORMAL), getDefaultResistance())
                                                .put(new PokemonTypeKey(GHOST, FIRE), getDefaultResistance())
                                                .put(new PokemonTypeKey(GHOST, WATER), getDefaultResistance())
                                                .put(new PokemonTypeKey(GHOST, ELECTRIC), getDefaultResistance())
                                                .put(new PokemonTypeKey(GHOST, GRASS), getDefaultResistance())
                                                .put(new PokemonTypeKey(GHOST, ICE), getDefaultResistance())
                                                .put(new PokemonTypeKey(GHOST, FIGHT), getDefaultResistance())
                                                .put(new PokemonTypeKey(GHOST, POISON), getDefaultResistance())
                                                .put(new PokemonTypeKey(GHOST, GROUND), getDefaultResistance())
                                                .put(new PokemonTypeKey(GHOST, FLYING), getDefaultResistance())
                                                .put(new PokemonTypeKey(GHOST, PSYCHIC), getDefaultResistance())
                                                .put(new PokemonTypeKey(GHOST, BUG), getDefaultResistance())
                                                .put(new PokemonTypeKey(GHOST, ROCK), getHalfDamage())
                                                .put(new PokemonTypeKey(GHOST, GHOST), getNoEffect())
                                                .put(new PokemonTypeKey(GHOST, DRAGON), getDefaultResistance())
                                                .put(new PokemonTypeKey(GHOST, DARK), getDefaultResistance())
                                                .put(new PokemonTypeKey(GHOST, STEEL), getHalfDamage())
                                                .put(new PokemonTypeKey(GHOST, FAIRY), getDefaultResistance())
            
                                                .put(new PokemonTypeKey(DRAGON, NORMAL), getDefaultResistance())
                                                .put(new PokemonTypeKey(DRAGON, FIRE), getDefaultResistance())
                                                .put(new PokemonTypeKey(DRAGON, WATER), getDefaultResistance())
                                                .put(new PokemonTypeKey(DRAGON, ELECTRIC), getDefaultResistance())
                                                .put(new PokemonTypeKey(DRAGON, GRASS), getDefaultResistance())
                                                .put(new PokemonTypeKey(DRAGON, ICE), getDefaultResistance())
                                                .put(new PokemonTypeKey(DRAGON, FIGHT), getDefaultResistance())
                                                .put(new PokemonTypeKey(DRAGON, POISON), getDefaultResistance())
                                                .put(new PokemonTypeKey(DRAGON, GROUND), getDefaultResistance())
                                                .put(new PokemonTypeKey(DRAGON, FLYING), getDefaultResistance())
                                                .put(new PokemonTypeKey(DRAGON, PSYCHIC), getDefaultResistance())
                                                .put(new PokemonTypeKey(DRAGON, BUG), getDefaultResistance())
                                                .put(new PokemonTypeKey(DRAGON, ROCK), getHalfDamage())
                                                .put(new PokemonTypeKey(DRAGON, GHOST), getNoEffect())
                                                .put(new PokemonTypeKey(DRAGON, DRAGON), getDefaultResistance())
                                                .put(new PokemonTypeKey(DRAGON, DARK), getDefaultResistance())
                                                .put(new PokemonTypeKey(DRAGON, STEEL), getHalfDamage())
                                                .put(new PokemonTypeKey(DRAGON, FAIRY), getDefaultResistance())
            
                                                .put(new PokemonTypeKey(DARK, NORMAL), getDefaultResistance())
                                                .put(new PokemonTypeKey(DARK, FIRE), getDefaultResistance())
                                                .put(new PokemonTypeKey(DARK, WATER), getDefaultResistance())
                                                .put(new PokemonTypeKey(DARK, ELECTRIC), getDefaultResistance())
                                                .put(new PokemonTypeKey(DARK, GRASS), getDefaultResistance())
                                                .put(new PokemonTypeKey(DARK, ICE), getDefaultResistance())
                                                .put(new PokemonTypeKey(DARK, FIGHT), getDefaultResistance())
                                                .put(new PokemonTypeKey(DARK, POISON), getDefaultResistance())
                                                .put(new PokemonTypeKey(DARK, GROUND), getDefaultResistance())
                                                .put(new PokemonTypeKey(DARK, FLYING), getDefaultResistance())
                                                .put(new PokemonTypeKey(DARK, PSYCHIC), getDefaultResistance())
                                                .put(new PokemonTypeKey(DARK, BUG), getDefaultResistance())
                                                .put(new PokemonTypeKey(DARK, ROCK), getHalfDamage())
                                                .put(new PokemonTypeKey(DARK, GHOST), getNoEffect())
                                                .put(new PokemonTypeKey(DARK, DRAGON), getDefaultResistance())
                                                .put(new PokemonTypeKey(DARK, DARK), getDefaultResistance())
                                                .put(new PokemonTypeKey(DARK, STEEL), getHalfDamage())
                                                .put(new PokemonTypeKey(DARK, FAIRY), getDefaultResistance())
            
                                                .put(new PokemonTypeKey(STEEL, NORMAL), getDefaultResistance())
                                                .put(new PokemonTypeKey(STEEL, FIRE), getDefaultResistance())
                                                .put(new PokemonTypeKey(STEEL, WATER), getDefaultResistance())
                                                .put(new PokemonTypeKey(STEEL, ELECTRIC), getDefaultResistance())
                                                .put(new PokemonTypeKey(STEEL, GRASS), getDefaultResistance())
                                                .put(new PokemonTypeKey(STEEL, ICE), getDefaultResistance())
                                                .put(new PokemonTypeKey(STEEL, FIGHT), getDefaultResistance())
                                                .put(new PokemonTypeKey(STEEL, POISON), getDefaultResistance())
                                                .put(new PokemonTypeKey(STEEL, GROUND), getDefaultResistance())
                                                .put(new PokemonTypeKey(STEEL, FLYING), getDefaultResistance())
                                                .put(new PokemonTypeKey(STEEL, PSYCHIC), getDefaultResistance())
                                                .put(new PokemonTypeKey(STEEL, BUG), getDefaultResistance())
                                                .put(new PokemonTypeKey(STEEL, ROCK), getHalfDamage())
                                                .put(new PokemonTypeKey(STEEL, GHOST), getNoEffect())
                                                .put(new PokemonTypeKey(STEEL, DRAGON), getDefaultResistance())
                                                .put(new PokemonTypeKey(STEEL, DARK), getDefaultResistance())
                                                .put(new PokemonTypeKey(STEEL, STEEL), getHalfDamage())
                                                .put(new PokemonTypeKey(STEEL, FAIRY), getDefaultResistance())
            
                                                .put(new PokemonTypeKey(FAIRY, NORMAL), getDefaultResistance())
                                                .put(new PokemonTypeKey(FAIRY, FIRE), getDefaultResistance())
                                                .put(new PokemonTypeKey(FAIRY, WATER), getDefaultResistance())
                                                .put(new PokemonTypeKey(FAIRY, ELECTRIC), getDefaultResistance())
                                                .put(new PokemonTypeKey(FAIRY, GRASS), getDefaultResistance())
                                                .put(new PokemonTypeKey(FAIRY, ICE), getDefaultResistance())
                                                .put(new PokemonTypeKey(FAIRY, FIGHT), getDefaultResistance())
                                                .put(new PokemonTypeKey(FAIRY, POISON), getDefaultResistance())
                                                .put(new PokemonTypeKey(FAIRY, GROUND), getDefaultResistance())
                                                .put(new PokemonTypeKey(FAIRY, FLYING), getDefaultResistance())
                                                .put(new PokemonTypeKey(FAIRY, PSYCHIC), getDefaultResistance())
                                                .put(new PokemonTypeKey(FAIRY, BUG), getDefaultResistance())
                                                .put(new PokemonTypeKey(FAIRY, ROCK), getHalfDamage())
                                                .put(new PokemonTypeKey(FAIRY, GHOST), getNoEffect())
                                                .put(new PokemonTypeKey(FAIRY, DRAGON), getDefaultResistance())
                                                .put(new PokemonTypeKey(FAIRY, DARK), getDefaultResistance())
                                                .put(new PokemonTypeKey(FAIRY, STEEL), getHalfDamage())
                                                .put(new PokemonTypeKey(FAIRY, FAIRY), getDefaultResistance())
            
                                                .put(new PokemonTypeKey(NORMAL, NORMAL, FIGHT), getDefaultResistance())
                                                .put(new PokemonTypeKey(NORMAL, NORMAL, FLYING), getDefaultResistance())
                                                .put(new PokemonTypeKey(NORMAL, NORMAL, GROUND), getDefaultResistance())
                                                .put(new PokemonTypeKey(NORMAL, NORMAL, WATER), getDefaultResistance())
                                                .put(new PokemonTypeKey(NORMAL, NORMAL, GRASS), getDefaultResistance())
                                                .put(new PokemonTypeKey(NORMAL, NORMAL, PSYCHIC),
                                                     getDefaultResistance()
                                                )
                                                .put(new PokemonTypeKey(NORMAL, NORMAL, DRAGON), getDefaultResistance())
                                                .put(new PokemonTypeKey(NORMAL, NORMAL, FAIRY), getDefaultResistance())
            
                                                .put(new PokemonTypeKey(GRASS, GRASS, POISON), getQuarterDamage())
                                                .build());
    
    public static float calculate(final Moves attackingMove, final Pokemon defendingPokemon) {
        PokemonTypes[] defendingPokemonTypes = defendingPokemon.getInformation().getPokemonTypes();
        return getResistanceData().get(new PokemonTypeKey(attackingMove.getMoveType(), defendingPokemonTypes[0],
                                                          defendingPokemonTypes.length >= 2 ? defendingPokemonTypes[1] :
                                                          null
        )) / 100f;
    }
    
    public static int getDefaultResistance() {
        return DEFAULT_RESISTANCE;
    }
    
    public static int getNoEffect() {
        return NO_EFFECT;
    }
    
    public static int getDoubleDamage() {
        return DOUBLE_DAMAGE;
    }
    
    public static int getQuadrupleDamage() {
        return QUADRUPLE_DAMAGE;
    }
    
    public static int getHalfDamage() {
        return HALF_DAMAGE;
    }
    
    public static int getQuarterDamage() {
        return QUARTER_DAMAGE;
    }
    
    public static TObjectIntHashMap<PokemonTypeKey> getResistanceData() {
        return RESISTANCE_DATA;
    }
    
    public static void main(String[] args) {
        long  start      = System.nanoTime();
        float resistance = calculate(MoveDataReader.getMove("Absorb"), PokemonDataReader.getPokemon("Bulbasaur"));
        long  end        = System.nanoTime();
        System.out.println(resistance);
        System.out.println(end - start);
    }
}
