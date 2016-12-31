package com.horse.pokemon.Enums;

import com.horse.pokemon.ObjectData.PokemonData.PokemonInformation;

/**
 * All the possible stats a PokemonData can have, all of which affects the amount of damage done to
 * itself and to other PokemonData.  It is always good to keep track of the PokemonData's health as a low
 * health means the PokemonData is about to faint, and is no longer of use unless a turn is wasted healing
 * the PokemonData.  The term 'Special' is used for when the attack the PokemonData is using is in the category
 * of special, rather than physical and other (Status). <p> All stats can be raised by leveling up the PokemonData,
 * which required more experience, which is raised by defeating PokemonData, and so it is a long chain to
 * raise a PokemonData to have high stats. <p> Other ways to raise the stats are by the PokemonData's effort values,
 * which are added values from defeating each PokemonData that slightly raise one specific stat.  The other way is by
 * luck, which are called {@link PokemonInformation#individualValue},
 * points valued at the start of each PokemonData.
 */
public enum StatTypes {
    HEALTH, ATTACK, DEFENSE, SPECIAL_ATTACK, SPECIAL_DEFENSE, SPEED
}
