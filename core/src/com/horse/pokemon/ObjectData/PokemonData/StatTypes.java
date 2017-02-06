package com.horse.pokemon.ObjectData.PokemonData;

/**
 * The {@code StatTypes} enum represents the stats a {@code Pokemon} has in order to properly attack and defend against
 * other {@code Pokemon}.  The {@code HEALTH}, {@code DEFENSE}, and {@code SPECIAL_DEFENSE} stats are used for defending
 * against {@code Pokemon}, as it makes it harder for the offending {@code Pokemon} to cause the defending
 * {@code Pokemon} to faint.  The stats, {@code ATTACK}, {@code SPECIAL_ATTACK}, and {@code SPEEd} are then used for
 * offensive tactics, such as dealing more damage to the opposing {@code Pokemon}.
 * <p>
 *     All stats can be raised from its original value in a variety of ways, such as:
 * </p>
 * <ul>
 *     <li>
 *         Leveling up the {@code Pokemon}.  This is the most common way for boosting the stats of any
 *         {@code Pokemon}, as all that needs to be done is attacking other {@code Pokemon}, which is how the
 *         storyline works in the game.  When a {@code Pokemon} levels up, all stats are raised according to the
 *         {@code CalculateStats} class.  To level up a {@code Pokemon}, an item called a Rare Candy has to be used,
 *         or the {@code PokemonExperience} value has to exceed a certain point, which is calculated in the same
 *         class.
 *     </li>
 *     <li>
 *         {@code IndividualValue}s will raise the stats of a {@code Pokemon} when leveling up, showing its full
 *         potential at max level.  Say there is a level 100 (Should be max) Venasaur with a neutral nature.
 *         Its {@code ATTACK} stat is 169, as there has been no interaction with its stats other than just leveling
 *         up.  {@code IndividualValue}s are based on a scale from 0 to 31, and three of the stats in
 *         {@code StatTypes} are forced to have perfect {@code IndividualValue}s.  The way {@code IndividualValue}s
 *         work is that at level 100, all the {@code IndividualValue}s are added onto the stat of the max level
 *         {@code Pokemon}.  The values are not added onto the stats all at once, as they are spread out onto the
 *         many levels of a {@code Pokemon}, adding a unique factor to the game.  So, if the {@code ATTACK} stat of
 *         the Venasaur were to have perfect {@code IndividualValue}s, then its {@code ATTACK} stat would be raised
 *         from 169 to 200.
 *     </li>
 *     <li>
 *         {@code EffortValue}s are one of the most difficult values of a {@code Pokemon} to keep track of, as they
 *         are raised when an opposing {@code Pokemon} has fainted.  Every {@code Pokemon} has a unique combination
 *         of {@code EffortValue}s they give to a {@code Pokemon} when defeated.  For every 4 {@code EffortValue}s
 *         on a single stat, the stat is raised by 1.  Of course, there are also limits to how high these values can
 *         go.
 *         <p>
 *             The restrictions are:
 *         </p>
 *         <ol>
 *             <li>
 *                 The max {@code EffortValue}s of a single stat has a max value of 255, giving the possibility of
 *                 63 extra stat points on a {@code Pokemon}.
 *             </li>
 *             <li>
 *                 The sum of all the {@code EffortValue}s from all the stats of a {@code Pokemon} must not surpass
 *                 510, meaning that the maximum extra stat points a {@code Pokemon} can gain from
 *                 {@code EffortValue}s is 127.
 *             </li>
 *         </ol>
 *         <p>
 *             So, going back to the Venasaur example.  Since the maximum {@code EffortValue} bonus on a single stat
 *             is 63, the updated {@code ATTACK} of the {@code Pokemon} is raised from 169 to 232, and if it has a
 *             perfect {@code IndividualValue}, the stat will be 263, almost 100 move than its original value.
 *         </p>
 *     </li>
 * </ul>
 * <p>
 *     Looking at the ways to level up {@code Pokemon}, they all lead up to the same action, attacking other
 *     {@code Pokemon}.  Pokemon battles are essential to the game, which means that many mechanics in the game have
 *     to do with battling {@code Pokemon}.
 * </p>
 * <p>
 *     The term special in the {@code StatTypes} is referring to if the move that is used upon a {@code Pokemon} is
 *     considered a physical type of attack, or a ranged attack.  Physical attacks will use the non-special stats, and
 *     all ranged attacks will use the special stats.
 * </p>
 * <p>
 *     When using Rare Candies to level up {@code Pokemon}, it is important to note that if the {@code Pokemon} has not
 *     yet reached its {@code EffortValue} limits, the stats will not raise if Rare Candies were used to bring the level
 *     of the {@code Pokemon} directly to max.
 * </p>
 *
 * @see Pokemon
 * @see CalculateStats
 * @see PokemonExperience
 * @see IndividualValue
 * @see EffortValue
 */
public enum StatTypes {
    /**
     * The {@code HEALTH} stat is the changing factor in a battle that determines if a {@code Pokemon} has fainted or is
     * still able to fight.  A {@code Pokemon} faints once its {@code HEALTH} reaches zero ({@code HEALTH} should never
     * go below zero or above its max value).
     * <p>
     *     Inside or outside a battle, a {@code Pokemon} that has recieved damage can heal with certain items such as a
     *     Potion or Super Potion, but many of these items cannot heal a {@code Pokemon} that has fainted, so it is
     *     important that healing is done to {@code Pokemon} to anticipate the damage on a {@code Pokemon} in the coming
     *     turn.  In order to heal a {@code Pokemon} that has fainted, other items are required to be used, otherwise
     *     going to a Pokemon Center is the only other common way to restore {@code Pokemon} from a fainted state.  One
     *     of these healing iteam are known as Revives, which can only be used on fainted {@code Pokemon}, healing them
     *     from that state and healing the current {@code HEALTH} stat by fifty percent.
     * </p>
     * <p>
     *     The {@code HEALTH} stat of an attacking {@code Pokemon} does not affect how much damage will be dealt to the
     *     defending {@code Pokemon}, as the {@code HEALTH} stat is used as a marker of how close a {@code Pokemon} is
     *     to fainting, meaning that {@code HEALTH} is a defensive stat.
     * </p>
     *
     * @see Pokemon
     */
    HEALTH, ATTACK, DEFENSE, SPECIAL_ATTACK, SPECIAL_DEFENSE, SPEED
}
