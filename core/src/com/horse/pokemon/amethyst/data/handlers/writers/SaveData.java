package com.horse.pokemon.amethyst.data.handlers.writers;

import com.horse.pokemon.amethyst.data.characters.User;
import com.horse.pokemon.amethyst.data.pokemon.Pokemon;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class SaveData {
    public static boolean savePokemon(Pokemon[] pokemon) {
        return true;
    }
    
    public static boolean saveUserPosition(User user) {
        try(Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("saveData.dat"), "utf-8"))) {
            writer.write(String.valueOf(user.getX()));
            writer.write("\n");
            writer.write(String.valueOf(user.getY()));
        } catch(IOException e) {
            return false;
        }
        return true;
    }
}