package com.horse.pokemon.ObjectData.PokemonData;

import com.badlogic.gdx.utils.StringBuilder;
import com.horse.pokemon.DataReaders.MoveDataReader;
import com.horse.pokemon.DataReaders.PokemonDataReader;
import com.horse.utility.MapBuilders.ObjectKey.ObjectIntMapBuilder;
import com.horse.utility.MemoryCalculator;
import com.koloboke.collect.map.hash.HashObjIntMap;

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
     * The {@link HashObjIntMap} storing the multiplier for when a {@link Pokemon} is attacking another {@code Pokemon}.
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
     * @see HashObjIntMap
     * @see PokemonTypeKey
     * @see ObjectIntMapBuilder
     */
    private static final HashObjIntMap<PokemonTypeKey> RESISTANCE_DATA =
        (HashObjIntMap<PokemonTypeKey>)(ObjectIntMapBuilder.<PokemonTypeKey>unordered()
                                            //Resistance Values for moves that are normal types and there is only one distinct type of the defending Pokemon.
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
        
                                            //Resistance Values for moves that are fire types and there is only one distinct type of the defending Pokemon.
                                            .put(new PokemonTypeKey(FIRE, NORMAL), getDefaultResistance())
                                            .put(new PokemonTypeKey(FIRE, FIRE), getHalfDamage())
                                            .put(new PokemonTypeKey(FIRE, WATER), getHalfDamage())
                                            .put(new PokemonTypeKey(FIRE, ELECTRIC), getDefaultResistance())
                                            .put(new PokemonTypeKey(FIRE, GRASS), getDoubleDamage())
                                            .put(new PokemonTypeKey(FIRE, ICE), getDoubleDamage())
                                            .put(new PokemonTypeKey(FIRE, FIGHT), getDefaultResistance())
                                            .put(new PokemonTypeKey(FIRE, POISON), getDefaultResistance())
                                            .put(new PokemonTypeKey(FIRE, GROUND), getDefaultResistance())
                                            .put(new PokemonTypeKey(FIRE, FLYING), getDefaultResistance())
                                            .put(new PokemonTypeKey(FIRE, PSYCHIC), getDefaultResistance())
                                            .put(new PokemonTypeKey(FIRE, BUG), getDoubleDamage())
                                            .put(new PokemonTypeKey(FIRE, ROCK), getHalfDamage())
                                            .put(new PokemonTypeKey(FIRE, GHOST), getDefaultResistance())
                                            .put(new PokemonTypeKey(FIRE, DRAGON), getHalfDamage())
                                            .put(new PokemonTypeKey(FIRE, DARK), getDefaultResistance())
                                            .put(new PokemonTypeKey(FIRE, STEEL), getDoubleDamage())
                                            .put(new PokemonTypeKey(FIRE, FAIRY), getDefaultResistance())
        
                                            //Resistance Values for moves that are water types and there is only one distinct type of the defending Pokemon.
                                            .put(new PokemonTypeKey(WATER, NORMAL), getDefaultResistance())
                                            .put(new PokemonTypeKey(WATER, FIRE), getDoubleDamage())
                                            .put(new PokemonTypeKey(WATER, WATER), getHalfDamage())
                                            .put(new PokemonTypeKey(WATER, ELECTRIC), getDefaultResistance())
                                            .put(new PokemonTypeKey(WATER, GRASS), getHalfDamage())
                                            .put(new PokemonTypeKey(WATER, ICE), getDefaultResistance())
                                            .put(new PokemonTypeKey(WATER, FIGHT), getDefaultResistance())
                                            .put(new PokemonTypeKey(WATER, POISON), getDefaultResistance())
                                            .put(new PokemonTypeKey(WATER, GROUND), getDoubleDamage())
                                            .put(new PokemonTypeKey(WATER, FLYING), getDefaultResistance())
                                            .put(new PokemonTypeKey(WATER, PSYCHIC), getDefaultResistance())
                                            .put(new PokemonTypeKey(WATER, BUG), getDefaultResistance())
                                            .put(new PokemonTypeKey(WATER, ROCK), getDoubleDamage())
                                            .put(new PokemonTypeKey(WATER, GHOST), getDefaultResistance())
                                            .put(new PokemonTypeKey(WATER, DRAGON), getHalfDamage())
                                            .put(new PokemonTypeKey(WATER, DARK), getDefaultResistance())
                                            .put(new PokemonTypeKey(WATER, STEEL), getDefaultResistance())
                                            .put(new PokemonTypeKey(WATER, FAIRY), getDefaultResistance())
        
                                            //Resistance Values for moves that are electric types and there is only one distinct type of the defending Pokemon.
                                            .put(new PokemonTypeKey(ELECTRIC, NORMAL), getDefaultResistance())
                                            .put(new PokemonTypeKey(ELECTRIC, FIRE), getDefaultResistance())
                                            .put(new PokemonTypeKey(ELECTRIC, WATER), getDoubleDamage())
                                            .put(new PokemonTypeKey(ELECTRIC, ELECTRIC), getHalfDamage())
                                            .put(new PokemonTypeKey(ELECTRIC, GRASS), getHalfDamage())
                                            .put(new PokemonTypeKey(ELECTRIC, ICE), getDefaultResistance())
                                            .put(new PokemonTypeKey(ELECTRIC, FIGHT), getDefaultResistance())
                                            .put(new PokemonTypeKey(ELECTRIC, POISON), getDefaultResistance())
                                            .put(new PokemonTypeKey(ELECTRIC, GROUND), getNoEffect())
                                            .put(new PokemonTypeKey(ELECTRIC, FLYING), getDoubleDamage())
                                            .put(new PokemonTypeKey(ELECTRIC, PSYCHIC), getDefaultResistance())
                                            .put(new PokemonTypeKey(ELECTRIC, BUG), getDefaultResistance())
                                            .put(new PokemonTypeKey(ELECTRIC, ROCK), getDefaultResistance())
                                            .put(new PokemonTypeKey(ELECTRIC, GHOST), getDefaultResistance())
                                            .put(new PokemonTypeKey(ELECTRIC, DRAGON), getHalfDamage())
                                            .put(new PokemonTypeKey(ELECTRIC, DARK), getDefaultResistance())
                                            .put(new PokemonTypeKey(ELECTRIC, STEEL), getDefaultResistance())
                                            .put(new PokemonTypeKey(ELECTRIC, FAIRY), getDefaultResistance())
        
                                            //Resistance Values for moves that are grass types and there is only one distinct type of the defending Pokemon.
                                            .put(new PokemonTypeKey(GRASS, NORMAL), getDefaultResistance())
                                            .put(new PokemonTypeKey(GRASS, FIRE), getHalfDamage())
                                            .put(new PokemonTypeKey(GRASS, WATER), getDoubleDamage())
                                            .put(new PokemonTypeKey(GRASS, ELECTRIC), getDefaultResistance())
                                            .put(new PokemonTypeKey(GRASS, GRASS), getHalfDamage())
                                            .put(new PokemonTypeKey(GRASS, ICE), getDefaultResistance())
                                            .put(new PokemonTypeKey(GRASS, FIGHT), getDefaultResistance())
                                            .put(new PokemonTypeKey(GRASS, POISON), getHalfDamage())
                                            .put(new PokemonTypeKey(GRASS, GROUND), getDoubleDamage())
                                            .put(new PokemonTypeKey(GRASS, FLYING), getHalfDamage())
                                            .put(new PokemonTypeKey(GRASS, PSYCHIC), getDefaultResistance())
                                            .put(new PokemonTypeKey(GRASS, BUG), getHalfDamage())
                                            .put(new PokemonTypeKey(GRASS, ROCK), getDoubleDamage())
                                            .put(new PokemonTypeKey(GRASS, GHOST), getDefaultResistance())
                                            .put(new PokemonTypeKey(GRASS, DRAGON), getHalfDamage())
                                            .put(new PokemonTypeKey(GRASS, DARK), getDefaultResistance())
                                            .put(new PokemonTypeKey(GRASS, STEEL), getHalfDamage())
                                            .put(new PokemonTypeKey(GRASS, FAIRY), getDefaultResistance())
        
                                            //Resistance Values for moves that are ice types and there is only one distinct type of the defending Pokemon.
                                            .put(new PokemonTypeKey(ICE, NORMAL), getDefaultResistance())
                                            .put(new PokemonTypeKey(ICE, FIRE), getHalfDamage())
                                            .put(new PokemonTypeKey(ICE, WATER), getHalfDamage())
                                            .put(new PokemonTypeKey(ICE, ELECTRIC), getDefaultResistance())
                                            .put(new PokemonTypeKey(ICE, GRASS), getDoubleDamage())
                                            .put(new PokemonTypeKey(ICE, ICE), getHalfDamage())
                                            .put(new PokemonTypeKey(ICE, FIGHT), getDefaultResistance())
                                            .put(new PokemonTypeKey(ICE, POISON), getDefaultResistance())
                                            .put(new PokemonTypeKey(ICE, GROUND), getDoubleDamage())
                                            .put(new PokemonTypeKey(ICE, FLYING), getDoubleDamage())
                                            .put(new PokemonTypeKey(ICE, PSYCHIC), getDefaultResistance())
                                            .put(new PokemonTypeKey(ICE, BUG), getDefaultResistance())
                                            .put(new PokemonTypeKey(ICE, ROCK), getDefaultResistance())
                                            .put(new PokemonTypeKey(ICE, GHOST), getDefaultResistance())
                                            .put(new PokemonTypeKey(ICE, DRAGON), getDoubleDamage())
                                            .put(new PokemonTypeKey(ICE, DARK), getDefaultResistance())
                                            .put(new PokemonTypeKey(ICE, STEEL), getDefaultResistance())
                                            .put(new PokemonTypeKey(ICE, FAIRY), getDefaultResistance())
        
                                            //Resistance Values for moves that are fighting types and there is only one distinct type of the defending Pokemon.
                                            .put(new PokemonTypeKey(FIGHT, NORMAL), getDoubleDamage())
                                            .put(new PokemonTypeKey(FIGHT, FIRE), getDefaultResistance())
                                            .put(new PokemonTypeKey(FIGHT, WATER), getDefaultResistance())
                                            .put(new PokemonTypeKey(FIGHT, ELECTRIC), getDefaultResistance())
                                            .put(new PokemonTypeKey(FIGHT, GRASS), getDefaultResistance())
                                            .put(new PokemonTypeKey(FIGHT, ICE), getDoubleDamage())
                                            .put(new PokemonTypeKey(FIGHT, FIGHT), getDefaultResistance())
                                            .put(new PokemonTypeKey(FIGHT, POISON), getHalfDamage())
                                            .put(new PokemonTypeKey(FIGHT, GROUND), getDefaultResistance())
                                            .put(new PokemonTypeKey(FIGHT, FLYING), getHalfDamage())
                                            .put(new PokemonTypeKey(FIGHT, PSYCHIC), getHalfDamage())
                                            .put(new PokemonTypeKey(FIGHT, BUG), getHalfDamage())
                                            .put(new PokemonTypeKey(FIGHT, ROCK), getDoubleDamage())
                                            .put(new PokemonTypeKey(FIGHT, GHOST), getNoEffect())
                                            .put(new PokemonTypeKey(FIGHT, DRAGON), getDefaultResistance())
                                            .put(new PokemonTypeKey(FIGHT, DARK), getDoubleDamage())
                                            .put(new PokemonTypeKey(FIGHT, STEEL), getDoubleDamage())
                                            .put(new PokemonTypeKey(FIGHT, FAIRY), getHalfDamage())
        
                                            //Resistance Values for moves that are poison types and there is only one distinct type of the defending Pokemon.
                                            .put(new PokemonTypeKey(POISON, NORMAL), getDefaultResistance())
                                            .put(new PokemonTypeKey(POISON, FIRE), getDefaultResistance())
                                            .put(new PokemonTypeKey(POISON, WATER), getDefaultResistance())
                                            .put(new PokemonTypeKey(POISON, ELECTRIC), getDefaultResistance())
                                            .put(new PokemonTypeKey(POISON, GRASS), getDoubleDamage())
                                            .put(new PokemonTypeKey(POISON, ICE), getDefaultResistance())
                                            .put(new PokemonTypeKey(POISON, FIGHT), getDefaultResistance())
                                            .put(new PokemonTypeKey(POISON, POISON), getHalfDamage())
                                            .put(new PokemonTypeKey(POISON, GROUND), getHalfDamage())
                                            .put(new PokemonTypeKey(POISON, FLYING), getDefaultResistance())
                                            .put(new PokemonTypeKey(POISON, PSYCHIC), getDefaultResistance())
                                            .put(new PokemonTypeKey(POISON, BUG), getDefaultResistance())
                                            .put(new PokemonTypeKey(POISON, ROCK), getHalfDamage())
                                            .put(new PokemonTypeKey(POISON, GHOST), getHalfDamage())
                                            .put(new PokemonTypeKey(POISON, DRAGON), getDefaultResistance())
                                            .put(new PokemonTypeKey(POISON, DARK), getDefaultResistance())
                                            .put(new PokemonTypeKey(POISON, STEEL), getNoEffect())
                                            .put(new PokemonTypeKey(POISON, FAIRY), getDoubleDamage())
        
                                            //Resistance Values for moves that are ground types and there is only one distinct type of the defending Pokemon.
                                            .put(new PokemonTypeKey(GROUND, NORMAL), getDefaultResistance())
                                            .put(new PokemonTypeKey(GROUND, FIRE), getDoubleDamage())
                                            .put(new PokemonTypeKey(GROUND, WATER), getDefaultResistance())
                                            .put(new PokemonTypeKey(GROUND, ELECTRIC), getDoubleDamage())
                                            .put(new PokemonTypeKey(GROUND, GRASS), getHalfDamage())
                                            .put(new PokemonTypeKey(GROUND, ICE), getDefaultResistance())
                                            .put(new PokemonTypeKey(GROUND, FIGHT), getDefaultResistance())
                                            .put(new PokemonTypeKey(GROUND, POISON), getDoubleDamage())
                                            .put(new PokemonTypeKey(GROUND, GROUND), getDefaultResistance())
                                            .put(new PokemonTypeKey(GROUND, FLYING), getNoEffect())
                                            .put(new PokemonTypeKey(GROUND, PSYCHIC), getDefaultResistance())
                                            .put(new PokemonTypeKey(GROUND, BUG), getHalfDamage())
                                            .put(new PokemonTypeKey(GROUND, ROCK), getDoubleDamage())
                                            .put(new PokemonTypeKey(GROUND, GHOST), getDefaultResistance())
                                            .put(new PokemonTypeKey(GROUND, DRAGON), getDefaultResistance())
                                            .put(new PokemonTypeKey(GROUND, DARK), getDefaultResistance())
                                            .put(new PokemonTypeKey(GROUND, STEEL), getDoubleDamage())
                                            .put(new PokemonTypeKey(GROUND, FAIRY), getDefaultResistance())
        
                                            //Resistance Values for moves that are flying types and there is only one distinct type of the defending Pokemon.
                                            .put(new PokemonTypeKey(FLYING, NORMAL), getDefaultResistance())
                                            .put(new PokemonTypeKey(FLYING, FIRE), getDefaultResistance())
                                            .put(new PokemonTypeKey(FLYING, WATER), getDefaultResistance())
                                            .put(new PokemonTypeKey(FLYING, ELECTRIC), getHalfDamage())
                                            .put(new PokemonTypeKey(FLYING, GRASS), getDoubleDamage())
                                            .put(new PokemonTypeKey(FLYING, ICE), getDefaultResistance())
                                            .put(new PokemonTypeKey(FLYING, FIGHT), getDoubleDamage())
                                            .put(new PokemonTypeKey(FLYING, POISON), getDefaultResistance())
                                            .put(new PokemonTypeKey(FLYING, GROUND), getDefaultResistance())
                                            .put(new PokemonTypeKey(FLYING, FLYING), getDefaultResistance())
                                            .put(new PokemonTypeKey(FLYING, PSYCHIC), getDefaultResistance())
                                            .put(new PokemonTypeKey(FLYING, BUG), getDoubleDamage())
                                            .put(new PokemonTypeKey(FLYING, ROCK), getHalfDamage())
                                            .put(new PokemonTypeKey(FLYING, GHOST), getDefaultResistance())
                                            .put(new PokemonTypeKey(FLYING, DRAGON), getDefaultResistance())
                                            .put(new PokemonTypeKey(FLYING, DARK), getDefaultResistance())
                                            .put(new PokemonTypeKey(FLYING, STEEL), getHalfDamage())
                                            .put(new PokemonTypeKey(FLYING, FAIRY), getDefaultResistance())
        
                                            //Resistance Values for moves that are psychic types and there is only one distinct type of the defending Pokemon.
                                            .put(new PokemonTypeKey(PSYCHIC, NORMAL), getDefaultResistance())
                                            .put(new PokemonTypeKey(PSYCHIC, FIRE), getDefaultResistance())
                                            .put(new PokemonTypeKey(PSYCHIC, WATER), getDefaultResistance())
                                            .put(new PokemonTypeKey(PSYCHIC, ELECTRIC), getDefaultResistance())
                                            .put(new PokemonTypeKey(PSYCHIC, GRASS), getDefaultResistance())
                                            .put(new PokemonTypeKey(PSYCHIC, ICE), getDefaultResistance())
                                            .put(new PokemonTypeKey(PSYCHIC, FIGHT), getDoubleDamage())
                                            .put(new PokemonTypeKey(PSYCHIC, POISON), getDoubleDamage())
                                            .put(new PokemonTypeKey(PSYCHIC, GROUND), getDefaultResistance())
                                            .put(new PokemonTypeKey(PSYCHIC, FLYING), getDefaultResistance())
                                            .put(new PokemonTypeKey(PSYCHIC, PSYCHIC), getHalfDamage())
                                            .put(new PokemonTypeKey(PSYCHIC, BUG), getDefaultResistance())
                                            .put(new PokemonTypeKey(PSYCHIC, ROCK), getDefaultResistance())
                                            .put(new PokemonTypeKey(PSYCHIC, GHOST), getDefaultResistance())
                                            .put(new PokemonTypeKey(PSYCHIC, DRAGON), getDefaultResistance())
                                            .put(new PokemonTypeKey(PSYCHIC, DARK), getNoEffect())
                                            .put(new PokemonTypeKey(PSYCHIC, STEEL), getHalfDamage())
                                            .put(new PokemonTypeKey(PSYCHIC, FAIRY), getDefaultResistance())
        
                                            //Resistance Values for moves that are bug types and there is only one distinct type of the defending Pokemon.
                                            .put(new PokemonTypeKey(BUG, NORMAL), getDefaultResistance())
                                            .put(new PokemonTypeKey(BUG, FIRE), getHalfDamage())
                                            .put(new PokemonTypeKey(BUG, WATER), getDefaultResistance())
                                            .put(new PokemonTypeKey(BUG, ELECTRIC), getDefaultResistance())
                                            .put(new PokemonTypeKey(BUG, GRASS), getDoubleDamage())
                                            .put(new PokemonTypeKey(BUG, ICE), getDefaultResistance())
                                            .put(new PokemonTypeKey(BUG, FIGHT), getHalfDamage())
                                            .put(new PokemonTypeKey(BUG, POISON), getHalfDamage())
                                            .put(new PokemonTypeKey(BUG, GROUND), getDefaultResistance())
                                            .put(new PokemonTypeKey(BUG, FLYING), getHalfDamage())
                                            .put(new PokemonTypeKey(BUG, PSYCHIC), getDoubleDamage())
                                            .put(new PokemonTypeKey(BUG, BUG), getDefaultResistance())
                                            .put(new PokemonTypeKey(BUG, ROCK), getDefaultResistance())
                                            .put(new PokemonTypeKey(BUG, GHOST), getHalfDamage())
                                            .put(new PokemonTypeKey(BUG, DRAGON), getDefaultResistance())
                                            .put(new PokemonTypeKey(BUG, DARK), getDoubleDamage())
                                            .put(new PokemonTypeKey(BUG, STEEL), getHalfDamage())
                                            .put(new PokemonTypeKey(BUG, FAIRY), getHalfDamage())
        
                                            //Resistance Values for moves that are rock types and there is only one distinct type of the defending Pokemon.
                                            .put(new PokemonTypeKey(ROCK, NORMAL), getDefaultResistance())
                                            .put(new PokemonTypeKey(ROCK, FIRE), getDoubleDamage())
                                            .put(new PokemonTypeKey(ROCK, WATER), getDefaultResistance())
                                            .put(new PokemonTypeKey(ROCK, ELECTRIC), getDefaultResistance())
                                            .put(new PokemonTypeKey(ROCK, GRASS), getDefaultResistance())
                                            .put(new PokemonTypeKey(ROCK, ICE), getDoubleDamage())
                                            .put(new PokemonTypeKey(ROCK, FIGHT), getHalfDamage())
                                            .put(new PokemonTypeKey(ROCK, POISON), getDefaultResistance())
                                            .put(new PokemonTypeKey(ROCK, GROUND), getHalfDamage())
                                            .put(new PokemonTypeKey(ROCK, FLYING), getDoubleDamage())
                                            .put(new PokemonTypeKey(ROCK, PSYCHIC), getDefaultResistance())
                                            .put(new PokemonTypeKey(ROCK, BUG), getDoubleDamage())
                                            .put(new PokemonTypeKey(ROCK, ROCK), getDefaultResistance())
                                            .put(new PokemonTypeKey(ROCK, GHOST), getDefaultResistance())
                                            .put(new PokemonTypeKey(ROCK, DRAGON), getDefaultResistance())
                                            .put(new PokemonTypeKey(ROCK, DARK), getDefaultResistance())
                                            .put(new PokemonTypeKey(ROCK, STEEL), getHalfDamage())
                                            .put(new PokemonTypeKey(ROCK, FAIRY), getDefaultResistance())
        
                                            //Resistance Values for moves that are ghost types and there is only one distinct type of the defending Pokemon.
                                            .put(new PokemonTypeKey(GHOST, NORMAL), getNoEffect())
                                            .put(new PokemonTypeKey(GHOST, FIRE), getDefaultResistance())
                                            .put(new PokemonTypeKey(GHOST, WATER), getDefaultResistance())
                                            .put(new PokemonTypeKey(GHOST, ELECTRIC), getDefaultResistance())
                                            .put(new PokemonTypeKey(GHOST, GRASS), getDefaultResistance())
                                            .put(new PokemonTypeKey(GHOST, ICE), getDefaultResistance())
                                            .put(new PokemonTypeKey(GHOST, FIGHT), getDefaultResistance())
                                            .put(new PokemonTypeKey(GHOST, POISON), getDefaultResistance())
                                            .put(new PokemonTypeKey(GHOST, GROUND), getDefaultResistance())
                                            .put(new PokemonTypeKey(GHOST, FLYING), getDefaultResistance())
                                            .put(new PokemonTypeKey(GHOST, PSYCHIC), getDoubleDamage())
                                            .put(new PokemonTypeKey(GHOST, BUG), getDefaultResistance())
                                            .put(new PokemonTypeKey(GHOST, ROCK), getDefaultResistance())
                                            .put(new PokemonTypeKey(GHOST, GHOST), getDoubleDamage())
                                            .put(new PokemonTypeKey(GHOST, DRAGON), getDefaultResistance())
                                            .put(new PokemonTypeKey(GHOST, DARK), getHalfDamage())
                                            .put(new PokemonTypeKey(GHOST, STEEL), getDefaultResistance())
                                            .put(new PokemonTypeKey(GHOST, FAIRY), getDefaultResistance())
        
                                            //Resistance Values for moves that are dragon types and there is only one distinct type of the defending Pokemon.
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
                                            .put(new PokemonTypeKey(DRAGON, ROCK), getDefaultResistance())
                                            .put(new PokemonTypeKey(DRAGON, GHOST), getDefaultResistance())
                                            .put(new PokemonTypeKey(DRAGON, DRAGON), getDoubleDamage())
                                            .put(new PokemonTypeKey(DRAGON, DARK), getDefaultResistance())
                                            .put(new PokemonTypeKey(DRAGON, STEEL), getHalfDamage())
                                            .put(new PokemonTypeKey(DRAGON, FAIRY), getNoEffect())
        
                                            //Resistance Values for moves that are dark types and there is only one distinct type of the defending Pokemon.
                                            .put(new PokemonTypeKey(DARK, NORMAL), getDefaultResistance())
                                            .put(new PokemonTypeKey(DARK, FIRE), getDefaultResistance())
                                            .put(new PokemonTypeKey(DARK, WATER), getDefaultResistance())
                                            .put(new PokemonTypeKey(DARK, ELECTRIC), getDefaultResistance())
                                            .put(new PokemonTypeKey(DARK, GRASS), getDefaultResistance())
                                            .put(new PokemonTypeKey(DARK, ICE), getDefaultResistance())
                                            .put(new PokemonTypeKey(DARK, FIGHT), getHalfDamage())
                                            .put(new PokemonTypeKey(DARK, POISON), getDefaultResistance())
                                            .put(new PokemonTypeKey(DARK, GROUND), getDefaultResistance())
                                            .put(new PokemonTypeKey(DARK, FLYING), getDefaultResistance())
                                            .put(new PokemonTypeKey(DARK, PSYCHIC), getDoubleDamage())
                                            .put(new PokemonTypeKey(DARK, BUG), getDefaultResistance())
                                            .put(new PokemonTypeKey(DARK, ROCK), getDefaultResistance())
                                            .put(new PokemonTypeKey(DARK, GHOST), getDoubleDamage())
                                            .put(new PokemonTypeKey(DARK, DRAGON), getDefaultResistance())
                                            .put(new PokemonTypeKey(DARK, DARK), getHalfDamage())
                                            .put(new PokemonTypeKey(DARK, STEEL), getDefaultResistance())
                                            .put(new PokemonTypeKey(DARK, FAIRY), getHalfDamage())
        
                                            //Resistance Values for moves that are steel types and there is only one distinct type of the defending Pokemon.
                                            .put(new PokemonTypeKey(STEEL, NORMAL), getDefaultResistance())
                                            .put(new PokemonTypeKey(STEEL, FIRE), getHalfDamage())
                                            .put(new PokemonTypeKey(STEEL, WATER), getHalfDamage())
                                            .put(new PokemonTypeKey(STEEL, ELECTRIC), getHalfDamage())
                                            .put(new PokemonTypeKey(STEEL, GRASS), getDefaultResistance())
                                            .put(new PokemonTypeKey(STEEL, ICE), getDoubleDamage())
                                            .put(new PokemonTypeKey(STEEL, FIGHT), getDefaultResistance())
                                            .put(new PokemonTypeKey(STEEL, POISON), getDefaultResistance())
                                            .put(new PokemonTypeKey(STEEL, GROUND), getDefaultResistance())
                                            .put(new PokemonTypeKey(STEEL, FLYING), getDefaultResistance())
                                            .put(new PokemonTypeKey(STEEL, PSYCHIC), getDefaultResistance())
                                            .put(new PokemonTypeKey(STEEL, BUG), getDefaultResistance())
                                            .put(new PokemonTypeKey(STEEL, ROCK), getDoubleDamage())
                                            .put(new PokemonTypeKey(STEEL, GHOST), getDefaultResistance())
                                            .put(new PokemonTypeKey(STEEL, DRAGON), getDefaultResistance())
                                            .put(new PokemonTypeKey(STEEL, DARK), getDefaultResistance())
                                            .put(new PokemonTypeKey(STEEL, STEEL), getHalfDamage())
                                            .put(new PokemonTypeKey(STEEL, FAIRY), getDoubleDamage())
        
                                            //Resistance Values for moves that are fairy types and there is only one distinct type of the defending Pokemon.
                                            .put(new PokemonTypeKey(FAIRY, NORMAL), getDefaultResistance())
                                            .put(new PokemonTypeKey(FAIRY, FIRE), getHalfDamage())
                                            .put(new PokemonTypeKey(FAIRY, WATER), getDefaultResistance())
                                            .put(new PokemonTypeKey(FAIRY, ELECTRIC), getDefaultResistance())
                                            .put(new PokemonTypeKey(FAIRY, GRASS), getDefaultResistance())
                                            .put(new PokemonTypeKey(FAIRY, ICE), getDefaultResistance())
                                            .put(new PokemonTypeKey(FAIRY, FIGHT), getDoubleDamage())
                                            .put(new PokemonTypeKey(FAIRY, POISON), getHalfDamage())
                                            .put(new PokemonTypeKey(FAIRY, GROUND), getDefaultResistance())
                                            .put(new PokemonTypeKey(FAIRY, FLYING), getDefaultResistance())
                                            .put(new PokemonTypeKey(FAIRY, PSYCHIC), getDefaultResistance())
                                            .put(new PokemonTypeKey(FAIRY, BUG), getDefaultResistance())
                                            .put(new PokemonTypeKey(FAIRY, ROCK), getDefaultResistance())
                                            .put(new PokemonTypeKey(FAIRY, GHOST), getDefaultResistance())
                                            .put(new PokemonTypeKey(FAIRY, DRAGON), getDoubleDamage())
                                            .put(new PokemonTypeKey(FAIRY, DARK), getDoubleDamage())
                                            .put(new PokemonTypeKey(FAIRY, STEEL), getHalfDamage())
                                            .put(new PokemonTypeKey(FAIRY, FAIRY), getDefaultResistance()).build());
    
    public static float calculate(final Moves attackingMove, final Pokemon defendingPokemon) {
        final PokemonTypes   attackingType         = attackingMove.getMoveType();
        final PokemonTypes[] defendingPokemonTypes = defendingPokemon.getInformation().getPokemonTypes();
        if(defendingPokemonTypes.length == 1) {
            return calculate(attackingType, defendingPokemonTypes[0]);
        } else {
            return calculate(attackingType, defendingPokemonTypes);
        }
    }
    
    public static float calculate(final PokemonTypes attackingType, final PokemonTypes defendingType) {
        return getResistanceData().getInt(new PokemonTypeKey(attackingType, defendingType)) / 100f;
    }
    
    public static float calculate(final PokemonTypes attackingType, final PokemonTypes[] defendingTypes) {
        return getResistanceData().getInt(new PokemonTypeKey(attackingType, defendingTypes[0])) *
               getResistanceData().getInt(new PokemonTypeKey(attackingType, defendingTypes[1])) / 10000f;
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
    
    public static HashObjIntMap<PokemonTypeKey> getResistanceData() {
        return RESISTANCE_DATA;
    }
    
    public static void main(String[] args) {
        final Moves   testMove    = MoveDataReader.getMove("Absorb");
        final Pokemon testPokemon = PokemonDataReader.getPokemon("Bulbasaur");
        long          start       = System.nanoTime();
        float         resistance  = calculate(testMove, testPokemon);
        long          end         = System.nanoTime();
        System.out.println(resistance);
        System.out.println(end - start);
        System.out.println(MemoryCalculator.sizeOf(getResistanceData()));
    
        int longestNameLength = 0;
        for(PokemonTypes pokemonType : PokemonTypes.values()) {
            longestNameLength =
                pokemonType.name().length() > longestNameLength ? pokemonType.name().length() : longestNameLength;
        }
    
        for(PokemonTypes attackingType : PokemonTypes.values()) {
            for(PokemonTypes defendingType : PokemonTypes.values()) {
                final StringBuilder whiteSpaceBuilder = new StringBuilder(longestNameLength);
                for(int index = 0; index < longestNameLength - attackingType.name().length(); index++) {
                    whiteSpaceBuilder.append(" ");
                }
            
                final StringBuilder whiteSpaceBuilderDefendingType = new StringBuilder(longestNameLength);
                for(int index = 0; index < longestNameLength - defendingType.name().length(); index++) {
                    whiteSpaceBuilderDefendingType.append(" ");
                }
            
                System.out.println(new StringBuilder("Attacking type = ").append(whiteSpaceBuilder.toString())
                                                                         .append(attackingType.name())
                                                                         .append(", Defending type = ").append(
                        whiteSpaceBuilderDefendingType.toString()).append(defendingType.name())
                                                                         .append(", Resistance = ").append(
                        calculate(attackingType, defendingType)));
            }
        }
    }
}
