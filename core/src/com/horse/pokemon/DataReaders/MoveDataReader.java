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
            String move = bufferedReader.readLine();
            while(move != null) {
                String[] data = move.split(";");
                moveData.put(data[0], getMove(data));
                move = bufferedReader.readLine();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    private static Moves getMove(String[] data) {
        Moves moves = new Moves();
    
        moves.setName(data[0]);
        moves.setCategory(data[1]);
        moves.setMoveType(PokemonTypes.valueOf(data[2]));
        moves.setPowerPoints(Integer.parseInt(data[3]));
        moves.setBasePower(Integer.parseInt(data[4]));
        moves.setAccuracy(Integer.parseInt(data[5]));
        moves.setEffect(Integer.parseInt(data[6]));
        moves.setTechnicalMachine(Integer.parseInt(data[7]));
        moves.setSpeedPriority(Integer.parseInt(data[8]));
        moves.setBattleEffect(data[9]);
        moves.setSecondaryEffect(data[10]);
        
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
