package com.horse.pokemon.GraphicsEngine.MainInterface.DialogEngine;

import com.horse.pokemon.Enums.DialogBoxes;

import java.util.HashMap;

public abstract class AbstractTextMap implements TextInterface {
    protected static final HashMap<TextMapKey, int[]> textMap = new HashMap<>();
    
    static {
        setMapSection('A', battleUpperA, dialogA, dialogA, null, null, null);
        setMapSection('B', battleUpperB, dialogB, dialogB, null, null, null);
        setMapSection('C', battleUpperC, dialogC, dialogC, null, null, null);
        setMapSection('D', battleUpperD, dialogD, dialogD, null, null, null);
        setMapSection('E', battleUpperE, dialogE, dialogE, null, null, null);
        setMapSection('F', battleUpperF, dialogF, dialogF, null, null, null);
        setMapSection('G', battleUpperG, dialogG, dialogG, null, null, null);
        setMapSection('H', battleUpperH, dialogH, dialogH, null, null, null);
        setMapSection('I', battleUpperI, dialogI, dialogI, null, null, null);
        setMapSection('J', battleUpperJ, dialogJ, dialogJ, null, null, null);
        setMapSection('K', battleUpperK, dialogK, dialogK, null, null, null);
        setMapSection('L', battleUpperL, dialogL, dialogL, null, null, null);
        setMapSection('M', battleUpperM, dialogM, dialogM, null, null, null);
        setMapSection('N', battleUpperN, dialogN, dialogN, null, null, null);
        setMapSection('O', battleUpperO, dialogO, dialogO, null, null, null);
        setMapSection('P', battleUpperP, dialogP, dialogP, null, null, null);
        setMapSection('Q', battleUpperQ, dialogQ, dialogQ, null, null, null);
        setMapSection('R', battleUpperR, dialogR, dialogR, null, null, null);
        setMapSection('S', battleUpperS, dialogS, dialogS, null, null, null);
        setMapSection('T', battleUpperT, dialogT, dialogT, null, null, null);
        setMapSection('U', battleUpperU, dialogU, dialogU, null, null, null);
        setMapSection('V', battleUpperV, dialogV, dialogV, null, null, null);
        setMapSection('W', battleUpperW, dialogW, dialogW, null, null, null);
        setMapSection('X', battleUpperX, dialogX, dialogX, null, null, null);
        setMapSection('Y', battleUpperY, dialogY, dialogY, null, null, null);
        setMapSection('Z', battleUpperZ, dialogZ, dialogZ, null, null, null);
        setMapSection('a', battleLowerA, dialogA, dialogA, null, null, null);
        setMapSection('b', battleLowerB, dialogB, dialogB, null, null, null);
        setMapSection('c', battleLowerC, dialogC, dialogC, null, null, null);
        setMapSection('d', battleLowerD, dialogD, dialogD, null, null, null);
        setMapSection('e', battleLowerE, dialogE, dialogE, null, null, null);
        setMapSection('f', battleLowerF, dialogF, dialogF, null, null, null);
        setMapSection('g', battleLowerG, dialogG, dialogG, null, null, null);
        setMapSection('h', battleLowerH, dialogH, dialogH, null, null, null);
        setMapSection('i', battleLowerI, dialogI, dialogI, null, null, null);
        setMapSection('j', battleLowerJ, dialogJ, dialogJ, null, null, null);
        setMapSection('k', battleLowerK, dialogK, dialogK, null, null, null);
        setMapSection('l', battleLowerL, dialogL, dialogL, null, null, null);
        setMapSection('m', battleLowerM, dialogM, dialogM, null, null, null);
        setMapSection('n', battleLowerN, dialogN, dialogN, null, null, null);
        setMapSection('o', battleLowerO, dialogO, dialogO, null, null, null);
        setMapSection('p', battleLowerP, dialogP, dialogP, null, null, null);
        setMapSection('q', battleLowerQ, dialogQ, dialogQ, null, null, null);
        setMapSection('r', battleLowerR, dialogR, dialogR, null, null, null);
        setMapSection('s', battleLowerS, dialogS, dialogS, null, null, null);
        setMapSection('t', battleLowerT, dialogT, dialogT, null, null, null);
        setMapSection('u', battleLowerU, dialogU, dialogU, null, null, null);
        setMapSection('v', battleLowerV, dialogV, dialogV, null, null, null);
        setMapSection('w', battleLowerW, dialogW, dialogW, null, null, null);
        setMapSection('x', battleLowerX, dialogX, dialogX, null, null, null);
        setMapSection('y', battleLowerY, dialogY, dialogY, null, null, null);
        setMapSection('z', battleLowerZ, dialogZ, dialogZ, null, null, null);
        setMapSection(' ', battleSpace, null, null, null, null, null);
    }
    
    private static void setMapSection(char character, int[]... dialogInformation) {
        try {
            if(dialogInformation.length != 6) {
                throw new IllegalArgumentException(String.format(
                        "Unwanted amount of arguments.  \"dialogInformation\" argument count = %s",
                        dialogInformation.length
                ));
            } else {
                textMap.put(new TextMapKey(character, DialogBoxes.BATTLE_DIALOG), dialogInformation[0]);
                textMap.put(new TextMapKey(character, DialogBoxes.DIALOG), dialogInformation[1]);
                textMap.put(new TextMapKey(character, DialogBoxes.DIALOG_CHOICE), dialogInformation[2]);
                textMap.put(new TextMapKey(character, DialogBoxes.SELF_DIALOG), dialogInformation[3]);
                textMap.put(new TextMapKey(character, DialogBoxes.SELF_DIALOG_CHOICE), dialogInformation[4]);
                textMap.put(new TextMapKey(character, DialogBoxes.SIGN_DIALOG), dialogInformation[5]);
            }
        } catch(IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}