/**
 * Contains all NPC and User information that is used to add functionality to the game.  Two {@link java.lang.FunctionalInterface}s are made to aid with the
 * {@link com.horse.pokemon.amethyst.data.characters.User} when detecting state changes from land to water transportation.
 * {@link com.horse.pokemon.amethyst.data.characters.BasePlayer} is the superclass for all NPCs and Users, where they also extend
 * {@link com.badlogic.gdx.scenes.scene2d.Actor} to contain an effective render using
 * {@link com.badlogic.gdx.scenes.scene2d.Actor#draw(com.badlogic.gdx.graphics.g2d.Batch, float)}, which is stored in a
 * {@link com.badlogic.gdx.scenes.scene2d.Stage} and all {@link com.badlogic.gdx.scenes.scene2d.Actor}s are drawn at nearly the same time and any actions
 * created from {@link com.badlogic.gdx.scenes.scene2d.Actor#addAction(com.badlogic.gdx.scenes.scene2d.Action)} to make instant functions to the
 * {@link com.horse.pokemon.amethyst.data.characters.BasePlayer} and its subclasses.
 *
 * @see java.lang.FunctionalInterface
 * @see com.badlogic.gdx.scenes.scene2d.Stage
 * @see com.badlogic.gdx.scenes.scene2d.Actor
 * @see com.horse.pokemon.amethyst.data.characters.BasePlayer
 * @see com.horse.pokemon.amethyst.data.characters.User
 * @see com.badlogic.gdx.scenes.scene2d.Actor#draw(com.badlogic.gdx.graphics.g2d.Batch, float)
 * @see com.badlogic.gdx.scenes.scene2d.Actor#addAction(com.badlogic.gdx.scenes.scene2d.Action)
 * @since 1.0
 */
package com.horse.pokemon.amethyst.data.characters;