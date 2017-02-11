package com.horse.utility.OptimizedMaps.DoubleKeys;

import com.koloboke.collect.map.hash.HashIntIntMap;
import com.koloboke.collect.map.hash.HashIntIntMaps;
import com.koloboke.collect.map.hash.HashIntObjMap;
import com.koloboke.collect.map.hash.HashIntObjMaps;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class JIntIntIntMap {
    private static final byte DEFAULT_KEYS_AND_VALUES_SIZE = 3;
    private int[] keysAndValues;
    
    public JIntIntIntMap() {
    }
    
    public JIntIntIntMap(int[] firstKeys, int[] secondKeys, int[] values) {
        setKeysAndValues(new int[firstKeys.length + secondKeys.length + values.length]);
        for(int i = 0; i < getKeysAndValues().length; i += getDefaultKeysAndValuesSize()) {
            final int thirdOfIndex = i / getDefaultKeysAndValuesSize();
            getKeysAndValues()[i] = firstKeys[thirdOfIndex];
            getKeysAndValues()[i + 1] = secondKeys[thirdOfIndex];
            getKeysAndValues()[i + 2] = values[thirdOfIndex];
        }
    }
    
    public JIntIntIntMap(HashMap<Integer, HashMap<Integer, Integer>> m) {
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
        
        for(int i = 0; i < getKeysAndValues().length; i += getDefaultKeysAndValuesSize()) {
            if(timesRepeated >= repetitionsPerFirstKey[keyIndex]) {
                keyIndex++;
                timesRepeated = 0;
            }
            getKeysAndValues()[i] = firstKeys.get(keyIndex);
            timesRepeated++;
            
            getKeysAndValues()[i + 1] = secondKeys.get(i / getDefaultKeysAndValuesSize());
            getKeysAndValues()[i + 2] = intValues.get(i / getDefaultKeysAndValuesSize());
        }
    }
    
    @SuppressWarnings("Unfinished")
    public JIntIntIntMap(JIntIntIntMap map) {
        setKeysAndValues(new int[map.getKeysAndValues().length]);
        putAll(map);
    }
    
    public static byte getDefaultKeysAndValuesSize() {
        return DEFAULT_KEYS_AND_VALUES_SIZE;
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
    
        HashIntIntMap                kolobokeInnerMap = HashIntIntMaps.newImmutableMap(innerMap);
        HashIntIntMap                kolobokeOtherMap = HashIntIntMaps.newImmutableMap(otherMap);
        HashIntObjMap<HashIntIntMap> kolobokeMap      = HashIntObjMaps.newMutableMap();
        kolobokeMap.put(1, kolobokeInnerMap);
        kolobokeMap.put(4, kolobokeOtherMap);
    
        JIntIntIntMap jIntIntIntMap = new JIntIntIntMap(map);
    
        System.out.println(GraphLayout.parseInstance(map).toFootprint());
        System.out.println(GraphLayout.parseInstance(kolobokeMap).toFootprint());
        System.out.println(GraphLayout.parseInstance(jIntIntIntMap).toFootprint());
        System.out.println(ClassLayout.parseInstance(jIntIntIntMap).toPrintable());
        System.out.println(ClassLayout.parseInstance(jIntIntIntMap.getKeysAndValues()).toPrintable());
    
        jIntIntIntMap.put(100, 200, 300);
        System.out.println(jIntIntIntMap.get(100, 200));
    
        jIntIntIntMap.put(100, 200, 400);
        System.out.println(jIntIntIntMap.get(100, 200));
    
        JIntIntIntMap testMap = new JIntIntIntMap(new int[] {0, 1, 2}, new int[] {3, 4, 5}, new int[] {6, 7, 8});
        System.out.println(testMap.get(1, 4));
    }
    
    public int[] getKeysAndValues() {
        return keysAndValues;
    }
    
    public void setKeysAndValues(int[] keysAndValues) {
        this.keysAndValues = keysAndValues;
    }
    
    public int get(int firstKey, int secondKey) {
        checkNull();
        
        ArrayList<Integer> possibleKeys = new ArrayList<>();
        
        for(int i = 0; i < getKeysAndValues().length; i += getDefaultKeysAndValuesSize()) {
            if(getKeysAndValues()[i] == firstKey) {
                possibleKeys.add(i);
            }
        }
        
        for(int possibleFirstKey : possibleKeys) {
            if(getKeysAndValues()[possibleFirstKey + 1] == secondKey) {
                return getKeysAndValues()[possibleFirstKey + 2];
            }
        }
        
        return 0;
    }
    
    public void checkNull() {
        if(getKeysAndValues() == null) {
            setKeysAndValues(new int[3]);
        }
    }
    
    public void put(int firstKey, int secondKey, int value) {
        checkNull();
        if(isKeyRepeated(firstKey, secondKey)) {
            final int replacingIndex = getRepeatedKeyIndex(firstKey, secondKey);
            getKeysAndValues()[replacingIndex] = firstKey;
            getKeysAndValues()[replacingIndex + 1] = secondKey;
            getKeysAndValues()[replacingIndex + 2] = value;
        } else {
            int[] newKeysAndValues = new int[getKeysAndValues().length + getDefaultKeysAndValuesSize()];
            
            System.arraycopy(getKeysAndValues(), 0, newKeysAndValues, 0, getKeysAndValues().length);
            
            newKeysAndValues[getKeysAndValues().length] = firstKey;
            newKeysAndValues[getKeysAndValues().length + 1] = secondKey;
            newKeysAndValues[getKeysAndValues().length + 2] = value;
            
            setKeysAndValues(newKeysAndValues);
        }
    }
    
    public boolean isKeyRepeated(int firstKey, int secondKey) {
        checkNull();
        for(int i = 0; i < getKeysAndValues().length; i += getDefaultKeysAndValuesSize()) {
            if(getKeysAndValues()[i] == firstKey && getKeysAndValues()[i + 1] == secondKey) {
                return true;
            }
        }
        
        return false;
    }
    
    public int getRepeatedKeyIndex(int firstKey, int secondKey) {
        checkNull();
        for(int i = 0; i < getKeysAndValues().length; i += getDefaultKeysAndValuesSize()) {
            if(getKeysAndValues()[i] == firstKey && getKeysAndValues()[i + 1] == secondKey) {
                return i;
            }
        }
        
        return -1;
    }
    
    public void putAll(JIntIntIntMap map) {
        checkNull();
        for(int i = 0; i < map.getKeysAndValues().length; i += getDefaultKeysAndValuesSize()) {
            put(map.getKeysAndValues()[i], map.getKeysAndValues()[i + 1], map.getKeysAndValues()[i + 2]);
        }
    }
}
