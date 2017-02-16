/**
 * <p>
 *     The <i>data</i> package contains files that keep track and store various information throughout the game.  There are
 *     four different sub-packages in <i>data</i>:
 * </p>
 *
 * <table BORDER CELLPADDING=3 CELLSPACING=1 summary="Sub-Packages and Information">
 *     <tr>
 *         <th>Package Name</th>
 *         <th>Description</th>
 *     </tr>
 *     <tr>
 *         <td ALIGN=CENTER><i>characters</i></td>
 *         <td ALIGN=CENTER>
 *             <p>
 *                 This package stores the players of the game, such as the class that represents the protagonist of the
 *                 game, known as {@code User}.  Other than the only player that is human-controlled, the {@code NPC}
 *                 class represents a non-playing-character, where the game reacts for those characters.  Both the
 *                 {@code User} and the {@code NPC} extend the {@code BasePlayer} class, pointing out that the two
 *                 representations are closely related.
 *             </p>
 *         </td>
 *     </tr>
 *     <tr>
 *         <td ALIGN=CENTER><i>handlers</i></td>
 *         <td ALIGN=CENTER>
 *             <p>
 *                 This package is what allows quick start-ups and saving abilities to the game.  Inside of
 *                 <i>handlers</i> are two more inner packages, <i>readers</i> and <i>writers</i>.  The <i>readers</i>
 *                 package takes information stored in data files and parses them to initialize {@code Pokemon} and
 *                 {@code Moves} in the game.  The <i>writers</i> package takes the current state of the game and stores
 *                 that information in secure files that can be easily accessed when the game loads again.
 *             </p>
 *         </td>
 *     </tr>
 *     <tr>
 *         <td ALIGN=CENTER><i>objects</i></td>
 *         <td ALIGN=CENTER>
 *             <p>
 *                 This package brings interaction into the game, such as special actions and events when the
 *                 {@code User} does something specific.  All predetermined actions are placed in every
 *                 {@code CollidableTileObject}, such as preventing furthur movement or giving the availability for the
 *                 {@code User} to swim on {@code Water}.
 *             </p>
 *         </td>
 *     </tr>
 *     <tr>
 *         <td ALIGN=CENTER><i>pokemon</i></td>
 *         <td ALIGN=CENTER>
 *             <p>
 *                 This package is what makes the game come to life.  {@code Pokemon} are essential for any 'Pokemon'
 *                 game, as all conflicts and events in the game have to do with these creatures.  The <i>pokemon</i>
 *                 package contains a storage class for information about a single {@code Pokemon}, which is set in the
 *                 <i>handlers</i> package.  Other classes add powerful implementations to the game, such as experience,
 *                 individual values, and effort values.
 *             </p>
 *         </td>
 *     </tr>
 * </table>
 */
package com.horse.pokemon.amethyst.data;