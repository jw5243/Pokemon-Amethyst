package com.horse.pokemon.amethyst.data.pokemon;

/**
 * Types of experience all PokemonData can have.
 */
public enum ExperienceTypes {
    /**
     * The slowest possible experience to max out a PokemonData's level.  Total of 1,640,000 Experience points.
     */
    FLUCTUATING,
    
    /**
     * Second slowest experience type, maxing out at a total of 1,250,000 Experience points.
     */
    SLOW,
    
    /**
     * Midway experience value, maxing out at 1,059,860 Experience points.
     */
    MEDIUM_SLOW,
    
    /**
     * The main experience type, with a simple total of 1,000,000 Experience points.
     */
    MEDIUM_FAST,
    
    /**
     * Over two times faster to max out than {@link ExperienceTypes#FLUCTUATING}, maxing at 800,000 Experience points.
     */
    FAST,
    
    /**
     * The final and fastest type to max, taking only 600,000 Experience points.
     */
    ERRATIC
}
