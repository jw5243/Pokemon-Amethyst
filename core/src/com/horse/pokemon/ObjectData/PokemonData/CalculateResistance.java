package com.horse.pokemon.ObjectData.PokemonData;

import com.badlogic.gdx.utils.StringBuilder;
import com.horse.pokemon.DataReaders.MoveDataReader;
import com.horse.pokemon.DataReaders.PokemonDataReader;
import com.horse.utility.OptimizedMaps.DoubleKeys.JIntIntIntMap;
import com.horse.utility.OptimizedMaps.MapBuilders.DoubleKeys.IntIntIntMapBuilder;
import org.openjdk.jol.info.GraphLayout;

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
    
    /**
     * The {@link JIntIntIntMap} storing the multiplier for when a {@link Pokemon} is attacking another {@code Pokemon}.
     * These values are stored in the order of {@link PokemonTypes}, which should always be:
     * <ol>
     *   <li>{@link PokemonTypes#NORMAL}</li>
     *   <li>{@link PokemonTypes#FIRE}</li>
     *   <li>{@link PokemonTypes#WATER}</li>
     *   <li>{@link PokemonTypes#ELECTRIC}</li>
     *   <li>{@link PokemonTypes#GRASS}</li>
     *   <li>{@link PokemonTypes#ICE}</li>
     *   <li>{@link PokemonTypes#FIGHT}</li>
     *   <li>{@link PokemonTypes#POISON}</li>
     *   <li>{@link PokemonTypes#GROUND}</li>
     *   <li>{@link PokemonTypes#FLYING}</li>
     *   <li>{@link PokemonTypes#PSYCHIC}</li>
     *   <li>{@link PokemonTypes#BUG}</li>
     *   <li>{@link PokemonTypes#ROCK}</li>
     *   <li>{@link PokemonTypes#GHOST}</li>
     *   <li>{@link PokemonTypes#DRAGON}</li>
     *   <li>{@link PokemonTypes#DARK}</li>
     *   <li>{@link PokemonTypes#STEEL}</li>
     *   <li>{@link PokemonTypes#FAIRY}</li>
     * </ol>
     *
     * @see PokemonTypes
     * @see JIntIntIntMap
     * @see PokemonTypeKey
     * @see IntIntIntMapBuilder
     */
    private static final JIntIntIntMap RESISTANCE_DATA = IntIntIntMapBuilder.unordered()
                                                                            //Resistance Values for moves that are normal types and there is only one distinct type of the defending Pokemon.
                                                                            .put(NORMAL.ordinal(), NORMAL.ordinal(),
                                                                                 getDefaultResistance()
                                                                            ).put(NORMAL.ordinal(), FIRE.ordinal(),
                                                                                  getDefaultResistance()
        ).put(NORMAL.ordinal(), WATER.ordinal(), getDefaultResistance()).put(NORMAL.ordinal(), ELECTRIC.ordinal(),
                                                                             getDefaultResistance()
        ).put(NORMAL.ordinal(), GRASS.ordinal(), getDefaultResistance()).put(NORMAL.ordinal(), ICE.ordinal(),
                                                                             getDefaultResistance()
        ).put(NORMAL.ordinal(), FIGHT.ordinal(), getDefaultResistance()).put(NORMAL.ordinal(), POISON.ordinal(),
                                                                             getDefaultResistance()
        ).put(NORMAL.ordinal(), GROUND.ordinal(), getDefaultResistance()).put(NORMAL.ordinal(), FLYING.ordinal(),
                                                                              getDefaultResistance()
        ).put(NORMAL.ordinal(), PSYCHIC.ordinal(), getDefaultResistance()).put(NORMAL.ordinal(), BUG.ordinal(),
                                                                               getDefaultResistance()
        ).put(NORMAL.ordinal(), ROCK.ordinal(), getHalfDamage()).put(NORMAL.ordinal(), GHOST.ordinal(), getNoEffect())
                                                                            .put(NORMAL.ordinal(), DRAGON.ordinal(),
                                                                                 getDefaultResistance()
                                                                            ).put(NORMAL.ordinal(), DARK.ordinal(),
                                                                                  getDefaultResistance()
        ).put(NORMAL.ordinal(), STEEL.ordinal(), getHalfDamage()).put(NORMAL.ordinal(), FAIRY.ordinal(),
                                                                      getDefaultResistance()
        )

                                                                            //Resistance Values for moves that are FIRE.ordinal() types and there is only one distinct type of the defending Pokemon.
                                                                            .put(FIRE.ordinal(), NORMAL.ordinal(),
                                                                                 getDefaultResistance()
                                                                            ).put(FIRE.ordinal(), FIRE.ordinal(),
                                                                                  getHalfDamage()
        ).put(FIRE.ordinal(), WATER.ordinal(), getHalfDamage()).put(FIRE.ordinal(), ELECTRIC.ordinal(),
                                                                    getDefaultResistance()
        ).put(FIRE.ordinal(), GRASS.ordinal(), getDoubleDamage()).put(FIRE.ordinal(), ICE.ordinal(), getDoubleDamage())
                                                                            .put(FIRE.ordinal(), FIGHT.ordinal(),
                                                                                 getDefaultResistance()
                                                                            ).put(FIRE.ordinal(), POISON.ordinal(),
                                                                                  getDefaultResistance()
        ).put(FIRE.ordinal(), GROUND.ordinal(), getDefaultResistance()).put(FIRE.ordinal(), FLYING.ordinal(),
                                                                            getDefaultResistance()
        ).put(FIRE.ordinal(), PSYCHIC.ordinal(), getDefaultResistance()).put(FIRE.ordinal(), BUG.ordinal(),
                                                                             getDoubleDamage()
        ).put(FIRE.ordinal(), ROCK.ordinal(), getHalfDamage()).put(FIRE.ordinal(), GHOST.ordinal(),
                                                                   getDefaultResistance()
        ).put(FIRE.ordinal(), DRAGON.ordinal(), getHalfDamage()).put(FIRE.ordinal(), DARK.ordinal(),
                                                                     getDefaultResistance()
        ).put(FIRE.ordinal(), STEEL.ordinal(), getDoubleDamage()).put(FIRE.ordinal(), FAIRY.ordinal(),
                                                                      getDefaultResistance()
        )

                                                                            //Resistance Values for moves that are WATER.ordinal() types and there is only one distinct type of the defending Pokemon.
                                                                            .put(WATER.ordinal(), NORMAL.ordinal(),
                                                                                 getDefaultResistance()
                                                                            ).put(WATER.ordinal(), FIRE.ordinal(),
                                                                                  getDoubleDamage()
        ).put(WATER.ordinal(), WATER.ordinal(), getHalfDamage()).put(WATER.ordinal(), ELECTRIC.ordinal(),
                                                                     getDefaultResistance()
        ).put(WATER.ordinal(), GRASS.ordinal(), getHalfDamage()).put(WATER.ordinal(), ICE.ordinal(),
                                                                     getDefaultResistance()
        ).put(WATER.ordinal(), FIGHT.ordinal(), getDefaultResistance()).put(WATER.ordinal(), POISON.ordinal(),
                                                                            getDefaultResistance()
        ).put(WATER.ordinal(), GROUND.ordinal(), getDoubleDamage()).put(WATER.ordinal(), FLYING.ordinal(),
                                                                        getDefaultResistance()
        ).put(WATER.ordinal(), PSYCHIC.ordinal(), getDefaultResistance()).put(WATER.ordinal(), BUG.ordinal(),
                                                                              getDefaultResistance()
        ).put(WATER.ordinal(), ROCK.ordinal(), getDoubleDamage()).put(WATER.ordinal(), GHOST.ordinal(),
                                                                      getDefaultResistance()
        ).put(WATER.ordinal(), DRAGON.ordinal(), getHalfDamage()).put(WATER.ordinal(), DARK.ordinal(),
                                                                      getDefaultResistance()
        ).put(WATER.ordinal(), STEEL.ordinal(), getDefaultResistance()).put(WATER.ordinal(), FAIRY.ordinal(),
                                                                            getDefaultResistance()
        )

                                                                            //Resistance Values for moves that are ELECTRIC.ordinal() types and there is only one distinct type of the defending Pokemon.
                                                                            .put(ELECTRIC.ordinal(), NORMAL.ordinal(),
                                                                                 getDefaultResistance()
                                                                            ).put(ELECTRIC.ordinal(), FIRE.ordinal(),
                                                                                  getDefaultResistance()
        ).put(ELECTRIC.ordinal(), WATER.ordinal(), getDoubleDamage()).put(ELECTRIC.ordinal(), ELECTRIC.ordinal(),
                                                                          getHalfDamage()
        ).put(ELECTRIC.ordinal(), GRASS.ordinal(), getHalfDamage()).put(ELECTRIC.ordinal(), ICE.ordinal(),
                                                                        getDefaultResistance()
        ).put(ELECTRIC.ordinal(), FIGHT.ordinal(), getDefaultResistance()).put(ELECTRIC.ordinal(), POISON.ordinal(),
                                                                               getDefaultResistance()
        ).put(ELECTRIC.ordinal(), GROUND.ordinal(), getNoEffect()).put(ELECTRIC.ordinal(), FLYING.ordinal(),
                                                                       getDoubleDamage()
        ).put(ELECTRIC.ordinal(), PSYCHIC.ordinal(), getDefaultResistance()).put(ELECTRIC.ordinal(), BUG.ordinal(),
                                                                                 getDefaultResistance()
        ).put(ELECTRIC.ordinal(), ROCK.ordinal(), getDefaultResistance()).put(ELECTRIC.ordinal(), GHOST.ordinal(),
                                                                              getDefaultResistance()
        ).put(ELECTRIC.ordinal(), DRAGON.ordinal(), getHalfDamage()).put(ELECTRIC.ordinal(), DARK.ordinal(),
                                                                         getDefaultResistance()
        ).put(ELECTRIC.ordinal(), STEEL.ordinal(), getDefaultResistance()).put(ELECTRIC.ordinal(), FAIRY.ordinal(),
                                                                               getDefaultResistance()
        )

                                                                            //Resistance Values for moves that are GRASS.ordinal() types and there is only one distinct type of the defending Pokemon.
                                                                            .put(GRASS.ordinal(), NORMAL.ordinal(),
                                                                                 getDefaultResistance()
                                                                            ).put(GRASS.ordinal(), FIRE.ordinal(),
                                                                                  getHalfDamage()
        ).put(GRASS.ordinal(), WATER.ordinal(), getDoubleDamage()).put(GRASS.ordinal(), ELECTRIC.ordinal(),
                                                                       getDefaultResistance()
        ).put(GRASS.ordinal(), GRASS.ordinal(), getHalfDamage()).put(GRASS.ordinal(), ICE.ordinal(),
                                                                     getDefaultResistance()
        ).put(GRASS.ordinal(), FIGHT.ordinal(), getDefaultResistance()).put(GRASS.ordinal(), POISON.ordinal(),
                                                                            getHalfDamage()
        ).put(GRASS.ordinal(), GROUND.ordinal(), getDoubleDamage()).put(GRASS.ordinal(), FLYING.ordinal(),
                                                                        getHalfDamage()
        ).put(GRASS.ordinal(), PSYCHIC.ordinal(), getDefaultResistance()).put(GRASS.ordinal(), BUG.ordinal(),
                                                                              getHalfDamage()
        ).put(GRASS.ordinal(), ROCK.ordinal(), getDoubleDamage()).put(GRASS.ordinal(), GHOST.ordinal(),
                                                                      getDefaultResistance()
        ).put(GRASS.ordinal(), DRAGON.ordinal(), getHalfDamage()).put(GRASS.ordinal(), DARK.ordinal(),
                                                                      getDefaultResistance()
        ).put(GRASS.ordinal(), STEEL.ordinal(), getHalfDamage()).put(GRASS.ordinal(), FAIRY.ordinal(),
                                                                     getDefaultResistance()
        )

                                                                            //Resistance Values for moves that are ICE.ordinal() types and there is only one distinct type of the defending Pokemon.
                                                                            .put(ICE.ordinal(), NORMAL.ordinal(),
                                                                                 getDefaultResistance()
                                                                            ).put(ICE.ordinal(), FIRE.ordinal(),
                                                                                  getHalfDamage()
        ).put(ICE.ordinal(), WATER.ordinal(), getHalfDamage()).put(ICE.ordinal(), ELECTRIC.ordinal(),
                                                                   getDefaultResistance()
        ).put(ICE.ordinal(), GRASS.ordinal(), getDoubleDamage()).put(ICE.ordinal(), ICE.ordinal(), getHalfDamage())
                                                                            .put(ICE.ordinal(), FIGHT.ordinal(),
                                                                                 getDefaultResistance()
                                                                            ).put(ICE.ordinal(), POISON.ordinal(),
                                                                                  getDefaultResistance()
        ).put(ICE.ordinal(), GROUND.ordinal(), getDoubleDamage()).put(ICE.ordinal(), FLYING.ordinal(),
                                                                      getDoubleDamage()
        ).put(ICE.ordinal(), PSYCHIC.ordinal(), getDefaultResistance()).put(ICE.ordinal(), BUG.ordinal(),
                                                                            getDefaultResistance()
        ).put(ICE.ordinal(), ROCK.ordinal(), getDefaultResistance()).put(ICE.ordinal(), GHOST.ordinal(),
                                                                         getDefaultResistance()
        ).put(ICE.ordinal(), DRAGON.ordinal(), getDoubleDamage()).put(ICE.ordinal(), DARK.ordinal(),
                                                                      getDefaultResistance()
        ).put(ICE.ordinal(), STEEL.ordinal(), getDefaultResistance()).put(ICE.ordinal(), FAIRY.ordinal(),
                                                                          getDefaultResistance()
        )

                                                                            //Resistance Values for moves that are FIGHT.ordinal()ing types and there is only one distinct type of the defending Pokemon.
                                                                            .put(FIGHT.ordinal(), NORMAL.ordinal(),
                                                                                 getDoubleDamage()
                                                                            ).put(FIGHT.ordinal(), FIRE.ordinal(),
                                                                                  getDefaultResistance()
        ).put(FIGHT.ordinal(), WATER.ordinal(), getDefaultResistance()).put(FIGHT.ordinal(), ELECTRIC.ordinal(),
                                                                            getDefaultResistance()
        ).put(FIGHT.ordinal(), GRASS.ordinal(), getDefaultResistance()).put(FIGHT.ordinal(), ICE.ordinal(),
                                                                            getDoubleDamage()
        ).put(FIGHT.ordinal(), FIGHT.ordinal(), getDefaultResistance()).put(FIGHT.ordinal(), POISON.ordinal(),
                                                                            getHalfDamage()
        ).put(FIGHT.ordinal(), GROUND.ordinal(), getDefaultResistance()).put(FIGHT.ordinal(), FLYING.ordinal(),
                                                                             getHalfDamage()
        ).put(FIGHT.ordinal(), PSYCHIC.ordinal(), getHalfDamage()).put(FIGHT.ordinal(), BUG.ordinal(), getHalfDamage())
                                                                            .put(FIGHT.ordinal(), ROCK.ordinal(),
                                                                                 getDoubleDamage()
                                                                            ).put(FIGHT.ordinal(), GHOST.ordinal(),
                                                                                  getNoEffect()
        ).put(FIGHT.ordinal(), DRAGON.ordinal(), getDefaultResistance()).put(FIGHT.ordinal(), DARK.ordinal(),
                                                                             getDoubleDamage()
        ).put(FIGHT.ordinal(), STEEL.ordinal(), getDoubleDamage()).put(FIGHT.ordinal(), FAIRY.ordinal(),
                                                                       getHalfDamage()
        )

                                                                            //Resistance Values for moves that are POISON.ordinal() types and there is only one distinct type of the defending Pokemon.
                                                                            .put(POISON.ordinal(), NORMAL.ordinal(),
                                                                                 getDefaultResistance()
                                                                            ).put(POISON.ordinal(), FIRE.ordinal(),
                                                                                  getDefaultResistance()
        ).put(POISON.ordinal(), WATER.ordinal(), getDefaultResistance()).put(POISON.ordinal(), ELECTRIC.ordinal(),
                                                                             getDefaultResistance()
        ).put(POISON.ordinal(), GRASS.ordinal(), getDoubleDamage()).put(POISON.ordinal(), ICE.ordinal(),
                                                                        getDefaultResistance()
        ).put(POISON.ordinal(), FIGHT.ordinal(), getDefaultResistance()).put(POISON.ordinal(), POISON.ordinal(),
                                                                             getHalfDamage()
        ).put(POISON.ordinal(), GROUND.ordinal(), getHalfDamage()).put(POISON.ordinal(), FLYING.ordinal(),
                                                                       getDefaultResistance()
        ).put(POISON.ordinal(), PSYCHIC.ordinal(), getDefaultResistance()).put(POISON.ordinal(), BUG.ordinal(),
                                                                               getDefaultResistance()
        ).put(POISON.ordinal(), ROCK.ordinal(), getHalfDamage()).put(POISON.ordinal(), GHOST.ordinal(), getHalfDamage())
                                                                            .put(POISON.ordinal(), DRAGON.ordinal(),
                                                                                 getDefaultResistance()
                                                                            ).put(POISON.ordinal(), DARK.ordinal(),
                                                                                  getDefaultResistance()
        ).put(POISON.ordinal(), STEEL.ordinal(), getNoEffect()).put(POISON.ordinal(), FAIRY.ordinal(),
                                                                    getDoubleDamage()
        )

                                                                            //Resistance Values for moves that are GROUND.ordinal() types and there is only one distinct type of the defending Pokemon.
                                                                            .put(GROUND.ordinal(), NORMAL.ordinal(),
                                                                                 getDefaultResistance()
                                                                            ).put(GROUND.ordinal(), FIRE.ordinal(),
                                                                                  getDoubleDamage()
        ).put(GROUND.ordinal(), WATER.ordinal(), getDefaultResistance()).put(GROUND.ordinal(), ELECTRIC.ordinal(),
                                                                             getDoubleDamage()
        ).put(GROUND.ordinal(), GRASS.ordinal(), getHalfDamage()).put(GROUND.ordinal(), ICE.ordinal(),
                                                                      getDefaultResistance()
        ).put(GROUND.ordinal(), FIGHT.ordinal(), getDefaultResistance()).put(GROUND.ordinal(), POISON.ordinal(),
                                                                             getDoubleDamage()
        ).put(GROUND.ordinal(), GROUND.ordinal(), getDefaultResistance()).put(GROUND.ordinal(), FLYING.ordinal(),
                                                                              getNoEffect()
        ).put(GROUND.ordinal(), PSYCHIC.ordinal(), getDefaultResistance()).put(GROUND.ordinal(), BUG.ordinal(),
                                                                               getHalfDamage()
        ).put(GROUND.ordinal(), ROCK.ordinal(), getDoubleDamage()).put(GROUND.ordinal(), GHOST.ordinal(),
                                                                       getDefaultResistance()
        ).put(GROUND.ordinal(), DRAGON.ordinal(), getDefaultResistance()).put(GROUND.ordinal(), DARK.ordinal(),
                                                                              getDefaultResistance()
        ).put(GROUND.ordinal(), STEEL.ordinal(), getDoubleDamage()).put(GROUND.ordinal(), FAIRY.ordinal(),
                                                                        getDefaultResistance()
        )

                                                                            //Resistance Values for moves that are FLYING.ordinal() types and there is only one distinct type of the defending Pokemon.
                                                                            .put(FLYING.ordinal(), NORMAL.ordinal(),
                                                                                 getDefaultResistance()
                                                                            ).put(FLYING.ordinal(), FIRE.ordinal(),
                                                                                  getDefaultResistance()
        ).put(FLYING.ordinal(), WATER.ordinal(), getDefaultResistance()).put(FLYING.ordinal(), ELECTRIC.ordinal(),
                                                                             getHalfDamage()
        ).put(FLYING.ordinal(), GRASS.ordinal(), getDoubleDamage()).put(FLYING.ordinal(), ICE.ordinal(),
                                                                        getDefaultResistance()
        ).put(FLYING.ordinal(), FIGHT.ordinal(), getDoubleDamage()).put(FLYING.ordinal(), POISON.ordinal(),
                                                                        getDefaultResistance()
        ).put(FLYING.ordinal(), GROUND.ordinal(), getDefaultResistance()).put(FLYING.ordinal(), FLYING.ordinal(),
                                                                              getDefaultResistance()
        ).put(FLYING.ordinal(), PSYCHIC.ordinal(), getDefaultResistance()).put(FLYING.ordinal(), BUG.ordinal(),
                                                                               getDoubleDamage()
        ).put(FLYING.ordinal(), ROCK.ordinal(), getHalfDamage()).put(FLYING.ordinal(), GHOST.ordinal(),
                                                                     getDefaultResistance()
        ).put(FLYING.ordinal(), DRAGON.ordinal(), getDefaultResistance()).put(FLYING.ordinal(), DARK.ordinal(),
                                                                              getDefaultResistance()
        ).put(FLYING.ordinal(), STEEL.ordinal(), getHalfDamage()).put(FLYING.ordinal(), FAIRY.ordinal(),
                                                                      getDefaultResistance()
        )

                                                                            //Resistance Values for moves that are PSYCHIC.ordinal() types and there is only one distinct type of the defending Pokemon.
                                                                            .put(PSYCHIC.ordinal(), NORMAL.ordinal(),
                                                                                 getDefaultResistance()
                                                                            ).put(PSYCHIC.ordinal(), FIRE.ordinal(),
                                                                                  getDefaultResistance()
        ).put(PSYCHIC.ordinal(), WATER.ordinal(), getDefaultResistance()).put(PSYCHIC.ordinal(), ELECTRIC.ordinal(),
                                                                              getDefaultResistance()
        ).put(PSYCHIC.ordinal(), GRASS.ordinal(), getDefaultResistance()).put(PSYCHIC.ordinal(), ICE.ordinal(),
                                                                              getDefaultResistance()
        ).put(PSYCHIC.ordinal(), FIGHT.ordinal(), getDoubleDamage()).put(PSYCHIC.ordinal(), POISON.ordinal(),
                                                                         getDoubleDamage()
        ).put(PSYCHIC.ordinal(), GROUND.ordinal(), getDefaultResistance()).put(PSYCHIC.ordinal(), FLYING.ordinal(),
                                                                               getDefaultResistance()
        ).put(PSYCHIC.ordinal(), PSYCHIC.ordinal(), getHalfDamage()).put(PSYCHIC.ordinal(), BUG.ordinal(),
                                                                         getDefaultResistance()
        ).put(PSYCHIC.ordinal(), ROCK.ordinal(), getDefaultResistance()).put(PSYCHIC.ordinal(), GHOST.ordinal(),
                                                                             getDefaultResistance()
        ).put(PSYCHIC.ordinal(), DRAGON.ordinal(), getDefaultResistance()).put(PSYCHIC.ordinal(), DARK.ordinal(),
                                                                               getNoEffect()
        ).put(PSYCHIC.ordinal(), STEEL.ordinal(), getHalfDamage()).put(PSYCHIC.ordinal(), FAIRY.ordinal(),
                                                                       getDefaultResistance()
        )

                                                                            //Resistance Values for moves that are BUG.ordinal() types and there is only one distinct type of the defending Pokemon.
                                                                            .put(BUG.ordinal(), NORMAL.ordinal(),
                                                                                 getDefaultResistance()
                                                                            ).put(BUG.ordinal(), FIRE.ordinal(),
                                                                                  getHalfDamage()
        ).put(BUG.ordinal(), WATER.ordinal(), getDefaultResistance()).put(BUG.ordinal(), ELECTRIC.ordinal(),
                                                                          getDefaultResistance()
        ).put(BUG.ordinal(), GRASS.ordinal(), getDoubleDamage()).put(BUG.ordinal(), ICE.ordinal(),
                                                                     getDefaultResistance()
        ).put(BUG.ordinal(), FIGHT.ordinal(), getHalfDamage()).put(BUG.ordinal(), POISON.ordinal(), getHalfDamage())
                                                                            .put(BUG.ordinal(), GROUND.ordinal(),
                                                                                 getDefaultResistance()
                                                                            ).put(BUG.ordinal(), FLYING.ordinal(),
                                                                                  getHalfDamage()
        ).put(BUG.ordinal(), PSYCHIC.ordinal(), getDoubleDamage()).put(BUG.ordinal(), BUG.ordinal(),
                                                                       getDefaultResistance()
        ).put(BUG.ordinal(), ROCK.ordinal(), getDefaultResistance()).put(BUG.ordinal(), GHOST.ordinal(),
                                                                         getHalfDamage()
        ).put(BUG.ordinal(), DRAGON.ordinal(), getDefaultResistance()).put(BUG.ordinal(), DARK.ordinal(),
                                                                           getDoubleDamage()
        ).put(BUG.ordinal(), STEEL.ordinal(), getHalfDamage()).put(BUG.ordinal(), FAIRY.ordinal(), getHalfDamage())

                                                                            //Resistance Values for moves that are ROCK.ordinal() types and there is only one distinct type of the defending Pokemon.
                                                                            .put(ROCK.ordinal(), NORMAL.ordinal(),
                                                                                 getDefaultResistance()
                                                                            ).put(ROCK.ordinal(), FIRE.ordinal(),
                                                                                  getDoubleDamage()
        ).put(ROCK.ordinal(), WATER.ordinal(), getDefaultResistance()).put(ROCK.ordinal(), ELECTRIC.ordinal(),
                                                                           getDefaultResistance()
        ).put(ROCK.ordinal(), GRASS.ordinal(), getDefaultResistance()).put(ROCK.ordinal(), ICE.ordinal(),
                                                                           getDoubleDamage()
        ).put(ROCK.ordinal(), FIGHT.ordinal(), getHalfDamage()).put(ROCK.ordinal(), POISON.ordinal(),
                                                                    getDefaultResistance()
        ).put(ROCK.ordinal(), GROUND.ordinal(), getHalfDamage()).put(ROCK.ordinal(), FLYING.ordinal(),
                                                                     getDoubleDamage()
        ).put(ROCK.ordinal(), PSYCHIC.ordinal(), getDefaultResistance()).put(ROCK.ordinal(), BUG.ordinal(),
                                                                             getDoubleDamage()
        ).put(ROCK.ordinal(), ROCK.ordinal(), getDefaultResistance()).put(ROCK.ordinal(), GHOST.ordinal(),
                                                                          getDefaultResistance()
        ).put(ROCK.ordinal(), DRAGON.ordinal(), getDefaultResistance()).put(ROCK.ordinal(), DARK.ordinal(),
                                                                            getDefaultResistance()
        ).put(ROCK.ordinal(), STEEL.ordinal(), getHalfDamage()).put(ROCK.ordinal(), FAIRY.ordinal(),
                                                                    getDefaultResistance()
        )

                                                                            //Resistance Values for moves that are GHOST.ordinal() types and there is only one distinct type of the defending Pokemon.
                                                                            .put(GHOST.ordinal(), NORMAL.ordinal(),
                                                                                 getNoEffect()
                                                                            ).put(GHOST.ordinal(), FIRE.ordinal(),
                                                                                  getDefaultResistance()
        ).put(GHOST.ordinal(), WATER.ordinal(), getDefaultResistance()).put(GHOST.ordinal(), ELECTRIC.ordinal(),
                                                                            getDefaultResistance()
        ).put(GHOST.ordinal(), GRASS.ordinal(), getDefaultResistance()).put(GHOST.ordinal(), ICE.ordinal(),
                                                                            getDefaultResistance()
        ).put(GHOST.ordinal(), FIGHT.ordinal(), getDefaultResistance()).put(GHOST.ordinal(), POISON.ordinal(),
                                                                            getDefaultResistance()
        ).put(GHOST.ordinal(), GROUND.ordinal(), getDefaultResistance()).put(GHOST.ordinal(), FLYING.ordinal(),
                                                                             getDefaultResistance()
        ).put(GHOST.ordinal(), PSYCHIC.ordinal(), getDoubleDamage()).put(GHOST.ordinal(), BUG.ordinal(),
                                                                         getDefaultResistance()
        ).put(GHOST.ordinal(), ROCK.ordinal(), getDefaultResistance()).put(GHOST.ordinal(), GHOST.ordinal(),
                                                                           getDoubleDamage()
        ).put(GHOST.ordinal(), DRAGON.ordinal(), getDefaultResistance()).put(GHOST.ordinal(), DARK.ordinal(),
                                                                             getHalfDamage()
        ).put(GHOST.ordinal(), STEEL.ordinal(), getDefaultResistance()).put(GHOST.ordinal(), FAIRY.ordinal(),
                                                                            getDefaultResistance()
        )

                                                                            //Resistance Values for moves that are DRAGON.ordinal() types and there is only one distinct type of the defending Pokemon.
                                                                            .put(DRAGON.ordinal(), NORMAL.ordinal(),
                                                                                 getDefaultResistance()
                                                                            ).put(DRAGON.ordinal(), FIRE.ordinal(),
                                                                                  getDefaultResistance()
        ).put(DRAGON.ordinal(), WATER.ordinal(), getDefaultResistance()).put(DRAGON.ordinal(), ELECTRIC.ordinal(),
                                                                             getDefaultResistance()
        ).put(DRAGON.ordinal(), GRASS.ordinal(), getDefaultResistance()).put(DRAGON.ordinal(), ICE.ordinal(),
                                                                             getDefaultResistance()
        ).put(DRAGON.ordinal(), FIGHT.ordinal(), getDefaultResistance()).put(DRAGON.ordinal(), POISON.ordinal(),
                                                                             getDefaultResistance()
        ).put(DRAGON.ordinal(), GROUND.ordinal(), getDefaultResistance()).put(DRAGON.ordinal(), FLYING.ordinal(),
                                                                              getDefaultResistance()
        ).put(DRAGON.ordinal(), PSYCHIC.ordinal(), getDefaultResistance()).put(DRAGON.ordinal(), BUG.ordinal(),
                                                                               getDefaultResistance()
        ).put(DRAGON.ordinal(), ROCK.ordinal(), getDefaultResistance()).put(DRAGON.ordinal(), GHOST.ordinal(),
                                                                            getDefaultResistance()
        ).put(DRAGON.ordinal(), DRAGON.ordinal(), getDoubleDamage()).put(DRAGON.ordinal(), DARK.ordinal(),
                                                                         getDefaultResistance()
        ).put(DRAGON.ordinal(), STEEL.ordinal(), getHalfDamage()).put(DRAGON.ordinal(), FAIRY.ordinal(), getNoEffect())

                                                                            //Resistance Values for moves that are DARK.ordinal() types and there is only one distinct type of the defending Pokemon.
                                                                            .put(DARK.ordinal(), NORMAL.ordinal(),
                                                                                 getDefaultResistance()
                                                                            ).put(DARK.ordinal(), FIRE.ordinal(),
                                                                                  getDefaultResistance()
        ).put(DARK.ordinal(), WATER.ordinal(), getDefaultResistance()).put(DARK.ordinal(), ELECTRIC.ordinal(),
                                                                           getDefaultResistance()
        ).put(DARK.ordinal(), GRASS.ordinal(), getDefaultResistance()).put(DARK.ordinal(), ICE.ordinal(),
                                                                           getDefaultResistance()
        ).put(DARK.ordinal(), FIGHT.ordinal(), getHalfDamage()).put(DARK.ordinal(), POISON.ordinal(),
                                                                    getDefaultResistance()
        ).put(DARK.ordinal(), GROUND.ordinal(), getDefaultResistance()).put(DARK.ordinal(), FLYING.ordinal(),
                                                                            getDefaultResistance()
        ).put(DARK.ordinal(), PSYCHIC.ordinal(), getDoubleDamage()).put(DARK.ordinal(), BUG.ordinal(),
                                                                        getDefaultResistance()
        ).put(DARK.ordinal(), ROCK.ordinal(), getDefaultResistance()).put(DARK.ordinal(), GHOST.ordinal(),
                                                                          getDoubleDamage()
        ).put(DARK.ordinal(), DRAGON.ordinal(), getDefaultResistance()).put(DARK.ordinal(), DARK.ordinal(),
                                                                            getHalfDamage()
        ).put(DARK.ordinal(), STEEL.ordinal(), getDefaultResistance()).put(DARK.ordinal(), FAIRY.ordinal(),
                                                                           getHalfDamage()
        )

                                                                            //Resistance Values for moves that are STEEL.ordinal() types and there is only one distinct type of the defending Pokemon.
                                                                            .put(STEEL.ordinal(), NORMAL.ordinal(),
                                                                                 getDefaultResistance()
                                                                            ).put(STEEL.ordinal(), FIRE.ordinal(),
                                                                                  getHalfDamage()
        ).put(STEEL.ordinal(), WATER.ordinal(), getHalfDamage()).put(STEEL.ordinal(), ELECTRIC.ordinal(),
                                                                     getHalfDamage()
        ).put(STEEL.ordinal(), GRASS.ordinal(), getDefaultResistance()).put(STEEL.ordinal(), ICE.ordinal(),
                                                                            getDoubleDamage()
        ).put(STEEL.ordinal(), FIGHT.ordinal(), getDefaultResistance()).put(STEEL.ordinal(), POISON.ordinal(),
                                                                            getDefaultResistance()
        ).put(STEEL.ordinal(), GROUND.ordinal(), getDefaultResistance()).put(STEEL.ordinal(), FLYING.ordinal(),
                                                                             getDefaultResistance()
        ).put(STEEL.ordinal(), PSYCHIC.ordinal(), getDefaultResistance()).put(STEEL.ordinal(), BUG.ordinal(),
                                                                              getDefaultResistance()
        ).put(STEEL.ordinal(), ROCK.ordinal(), getDoubleDamage()).put(STEEL.ordinal(), GHOST.ordinal(),
                                                                      getDefaultResistance()
        ).put(STEEL.ordinal(), DRAGON.ordinal(), getDefaultResistance()).put(STEEL.ordinal(), DARK.ordinal(),
                                                                             getDefaultResistance()
        ).put(STEEL.ordinal(), STEEL.ordinal(), getHalfDamage()).put(STEEL.ordinal(), FAIRY.ordinal(),
                                                                     getDoubleDamage()
        )

                                                                            //Resistance Values for moves that are FAIRY.ordinal() types and there is only one distinct type of the defending Pokemon.
                                                                            .put(FAIRY.ordinal(), NORMAL.ordinal(),
                                                                                 getDefaultResistance()
                                                                            ).put(FAIRY.ordinal(), FIRE.ordinal(),
                                                                                  getHalfDamage()
        ).put(FAIRY.ordinal(), WATER.ordinal(), getDefaultResistance()).put(FAIRY.ordinal(), ELECTRIC.ordinal(),
                                                                            getDefaultResistance()
        ).put(FAIRY.ordinal(), GRASS.ordinal(), getDefaultResistance()).put(FAIRY.ordinal(), ICE.ordinal(),
                                                                            getDefaultResistance()
        ).put(FAIRY.ordinal(), FIGHT.ordinal(), getDoubleDamage()).put(FAIRY.ordinal(), POISON.ordinal(),
                                                                       getHalfDamage()
        ).put(FAIRY.ordinal(), GROUND.ordinal(), getDefaultResistance()).put(FAIRY.ordinal(), FLYING.ordinal(),
                                                                             getDefaultResistance()
        ).put(FAIRY.ordinal(), PSYCHIC.ordinal(), getDefaultResistance()).put(FAIRY.ordinal(), BUG.ordinal(),
                                                                              getDefaultResistance()
        ).put(FAIRY.ordinal(), ROCK.ordinal(), getDefaultResistance()).put(FAIRY.ordinal(), GHOST.ordinal(),
                                                                           getDefaultResistance()
        ).put(FAIRY.ordinal(), DRAGON.ordinal(), getDoubleDamage()).put(FAIRY.ordinal(), DARK.ordinal(),
                                                                        getDoubleDamage()
        ).put(FAIRY.ordinal(), STEEL.ordinal(), getHalfDamage()).put(FAIRY.ordinal(), FAIRY.ordinal(),
                                                                     getDefaultResistance()
        ).build();
    
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
        return getResistanceData().get(attackingType.ordinal(), defendingType.ordinal()) / 100f;
    }
    
    public static float calculate(final PokemonTypes attackingType, final PokemonTypes[] defendingTypes) {
        return (getResistanceData().get(attackingType.ordinal(), defendingTypes[0].ordinal()) *
                getResistanceData().get(attackingType.ordinal(), defendingTypes[1].ordinal())) / 10000f;
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
    
    public static JIntIntIntMap getResistanceData() {
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
    
        System.out.println(GraphLayout.parseInstance(getResistanceData()).toFootprint());
    }
}
