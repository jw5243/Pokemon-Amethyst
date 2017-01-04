package com.horse.pokemon.DataReaders;

import com.horse.pokemon.ObjectData.PokemonData.Moves;
import com.horse.pokemon.ObjectData.PokemonData.PokemonTypes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

public class MoveDataReader {
    private static final File moveFileData = new File("core\\assets\\PokemonData\\MoveData.dat");
    private static final HashMap<String, Moves> moveData;
    
    static {
        moveData = new HashMap<>();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(moveFileData))) {
            String pokemon = bufferedReader.readLine();
            while(pokemon != null) {
                String[] data = pokemon.split(";");
                moveData.put(data[0], getMove(data));
                pokemon = bufferedReader.readLine();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    private static Moves getMove(String[] data) {
        Moves moves = new Moves();
        
        moves.setName(data[0].trim());
        moves.setCategory(data[1].trim());
        moves.setMoveType(PokemonTypes.valueOf(data[2].trim()));
        moves.setPowerPoints(Integer.parseInt(data[3].trim()));
        moves.setBasePower(Integer.parseInt(data[4].trim()));
        moves.setAccuracy(Integer.parseInt(data[5].trim()));
        moves.setEffect(Integer.parseInt(data[6].trim()));
        moves.setTechnicalMachine(Integer.parseInt(data[7].trim()));
        moves.setSpeedPriority(Integer.parseInt(data[8].trim()));
        moves.setBattleEffect(data[9].trim());
        moves.setSecondaryEffect(data[10].trim());
        
        moves.setCurrentPowerPoints(moves.getPowerPoints());
        
        return moves;
    }
    
    public static void main(String[] args) {
        Moves move = MoveDataReader.getMove("Absorb");
        System.out.println(move.getAccuracy());
    }
    
    public static Moves getMove(String moveName) {
        return moveData.get(moveName);
    }
}
