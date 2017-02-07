package com.horse.utility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class HashIntIntIntMap {
    private static final int  DEFAULT_CAPACITY               = 16;
    private static final byte DEFAULT_LOAD_FACTOR_PERCENTAGE = 75;
    private              int  defaultValue                   = 0;
    private int[] keysAndValues;
    
    public HashIntIntIntMap() {
        this(getDefaultCapacity());
    }
    
    public HashIntIntIntMap(int capacity) {
        this(capacity, getDefaultLoadFactorPercentage());
    }
    
    public HashIntIntIntMap(int capacity, float loadFactor) {
        this(capacity, (byte)(loadFactor * 100));
    }
    
    public HashIntIntIntMap(int capacity, byte loadFactorPercentage) {
        setKeysAndValues(new int[capacity * loadFactorPercentage / 100]);
    }
    
    public HashIntIntIntMap(int capacity, float loadFactor, int defaultValue) {
        this(capacity, (byte)(loadFactor * 100), defaultValue);
    }
    
    public HashIntIntIntMap(int capacity, byte loadFactorPercentage, int defaultValue) {
        setKeysAndValues(new int[capacity * loadFactorPercentage / 100]);
        setDefaultValue(defaultValue);
    }
    
    public HashIntIntIntMap(int[] firstKeys, int[] secondKeys, int[] thirdKeys, int[] values) {
        
    }
    
    public HashIntIntIntMap(HashMap<Integer, HashMap<Integer, Integer>> m) {
        ArrayList<Integer> firstKeys  = new ArrayList<>();
        ArrayList<Integer> secondKeys = new ArrayList<>();
        
        firstKeys.addAll(m.keySet());
        
        ArrayList<Integer> intValues = new ArrayList<>();
        
        Collection<HashMap<Integer, Integer>> keyAndValueCollection = m.values();
        
        for(HashMap<Integer, Integer> keyAndValueHashMap : keyAndValueCollection) {
            secondKeys.addAll(keyAndValueHashMap.keySet());
            intValues.addAll(keyAndValueHashMap.values());
        }
        
        setKeysAndValues(new int[firstKeys.size() + secondKeys.size() + intValues.size() + 1]);
        
        int[]  repetitionsPerFirstKey = new int[m.size()];
        int    firstKeyIndex          = 0;
        int    repetitionIndex        = 0;
        Object currentMap             = m.values().toArray()[0];
        
        for(int j = 0; j < m.size(); j++) {
            repetitionIndex++;
            if(currentMap != m.values().toArray()[j]) {
                repetitionsPerFirstKey[firstKeyIndex] = repetitionIndex;
                firstKeyIndex++;
                repetitionIndex = 0;
            }
        }
        
        int timesRepeated = 0;
        int keyIndex      = 0;
        
        for(int i = 0; i < getKeysAndValues().length; i += 3) {
            if(timesRepeated >= repetitionsPerFirstKey[keyIndex]) {
                keyIndex++;
                timesRepeated = 0;
            }
            getKeysAndValues()[i] = firstKeys.get(keyIndex);
            timesRepeated++;
            
            getKeysAndValues()[i + 1] = secondKeys.get(i / 3);
            getKeysAndValues()[i + 2] = intValues.get(i / 3);
        }
    }
    
    public static int getDefaultCapacity() {
        return DEFAULT_CAPACITY;
    }
    
    public static byte getDefaultLoadFactorPercentage() {
        return DEFAULT_LOAD_FACTOR_PERCENTAGE;
    }
    
    public static void main(String[] args) {
        HashMap<Integer, HashMap<Integer, Integer>> map      = new HashMap<>();
        HashMap<Integer, Integer>                   innerMap = new HashMap<>();
        HashMap<Integer, Integer>                   otherMap = new HashMap<>();
        
        innerMap.put(2, 3);
        innerMap.put(7, 8);
        map.put(1, innerMap);
        
        otherMap.put(5, 6);
        map.put(4, otherMap);
        
        HashIntIntIntMap hashIntIntIntMap = new HashIntIntIntMap(map);
        System.out.println(MemoryCalculator.sizeOf(map));
        System.out.println(MemoryCalculator.sizeOf(hashIntIntIntMap));
    }
    
    public int getDefaultValue() {
        return defaultValue;
    }
    
    public void setDefaultValue(int defaultValue) {
        this.defaultValue = defaultValue;
    }
    
    public int[] getKeysAndValues() {
        return keysAndValues;
    }
    
    public void setKeysAndValues(int[] keysAndValues) {
        this.keysAndValues = keysAndValues;
    }
}
