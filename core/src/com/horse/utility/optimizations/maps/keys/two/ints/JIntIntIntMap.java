package com.horse.utility.optimizations.maps.keys.two.ints;

import com.koloboke.collect.map.hash.HashIntIntMap;
import com.koloboke.collect.map.hash.HashIntIntMaps;
import com.koloboke.collect.map.hash.HashIntObjMap;
import com.koloboke.collect.map.hash.HashIntObjMaps;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *     The {@code JIntIntIntMap} is a storage method to contain two primitive {@code int} values as keys and one
 *     primitive {@code int} acting as the value, forming a triplet storage form.
 * </p>
 *
 * <p>
 *     Unlike other implementations of a {@code Map} in <i>java.util</i>, this class stores all its information in a
 *     single {@code array}, saving a good amount of memory.
 * </p>
 *
 * <p>
 *     The {@code JIntIntIntMap} has been tested in terms of memory with other {@code Map} competitors, such as:
 * </p>
 *
 * <ol>
 *     <li>{@linkplain HashMap}</li>
 *     <li>{@linkplain HashIntObjMap}</li>
 * </ol>
 *
 * <p>
 *     Thanks to <i>openjdk</i>, the memory use of these classes were able to be tested using {@link ClassLayout} and
 *     {@link GraphLayout}, including the specifications of each tested {@code Object}.
 * </p>
 *
 * <p>
 *     The results are as followed:
 * </p>
 *
 * <ul>
 *     <li>
 *         {@code HashMap<Integer, HashMap<Integer, Integer>>}
 *         <blockquote><pre>
 *             java.util.HashMap@3fa77460d footprint:
 *                   COUNT       AVG       SUM   DESCRIPTION
 *                       3        80       240   [Ljava.util.HashMap$Node;
 *                       8        16       128   java.lang.Integer
 *                       3        48       144   java.util.HashMap
 *                       2        16        32   java.util.HashMap$EntrySet
 *                       3        16        48   java.util.HashMap$KeySet
 *                       5        32       160   java.util.HashMap$Node
 *                       3        16        48   java.util.HashMap$Values
 *                      27                 800   (total)
 *         </pre></blockquote>
 *     </li>
 *     <li>
 *         {@code HashIntObjMap<HashIntIntMap>}
 *         <blockquote><pre>
 *             com.koloboke.collect.impl.hash.MutableLHashSeparateKVIntObjMap@7bb11784d footprint:
 *                  COUNT       AVG       SUM   DESCRIPTION
 *                      1        80        80   [I
 *                      2        48        96   [J
 *                      1        80        80   [Ljava.lang.Object;
 *                      1        48        48   com.koloboke.collect.hash.AutoValue_HashConfig
 *                      1        24        24   com.koloboke.collect.impl.Scaler$10
 *                      1        24        24   com.koloboke.collect.impl.Scaler$4
 *                      1        24        24   com.koloboke.collect.impl.Scaler$8
 *                      1        24        24   com.koloboke.collect.impl.Scaler$9
 *                      2        40        80   com.koloboke.collect.impl.hash.HashConfigWrapper
 *                      2        32        64   com.koloboke.collect.impl.hash.ImmutableLHashParallelKVIntIntMap
 *                      1        40        40   com.koloboke.collect.impl.hash.MutableLHashSeparateKVIntObjMap
 *                     14                 584   (total)
 *         </pre></blockquote>
 *     </li>
 *     <li>
 *         {@code JIntIntIntMap}
 *         <blockquote><pre>
 *             com.horse.utility.optimizations.maps.keys.two.ints.JIntIntIntMap@47f37ef1d footprint:
 *                  COUNT       AVG       SUM   DESCRIPTION
 *                      1        56        56   [I
 *                      1        16        16   com.horse.utility.optimizations.maps.keys.two.ints.JIntIntIntMap
 *                      2                  72   (total)
 *         </pre></blockquote>
 *     </li>
 * </ul>
 *
 * @see Map
 * @see HashMap
 * @see HashIntObjMap
 * @see ClassLayout
 * @see GraphLayout
 * @see ClassLayout#parseInstance(Object)
 * @see GraphLayout#parseInstance(Object...)
 * @see ClassLayout#toPrintable()
 * @see GraphLayout#toFootprint()
 */
public class JIntIntIntMap {
    /**
     * The {@code byte} representing the length in terms of arrays on how many indeces are occupied from a single set of
     * keys following to one value.
     */
    private static final byte DEFAULT_KEYS_AND_VALUES_SIZE = 3;
    
    /**
     * The {@code int[]} instance that stores all information for the {@linkplain Object} to read and write from.
     */
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
            timesRepeated++;
    
            getKeysAndValues()[i] = firstKeys.get(keyIndex);
            
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
        if(contains(firstKey, secondKey)) {
            final int replacingIndex = getIndexOf(firstKey, secondKey);
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
    
    public boolean contains(int firstKey, int secondKey) {
        checkNull();
        for(int i = 0; i < getKeysAndValues().length; i += getDefaultKeysAndValuesSize()) {
            if(getKeysAndValues()[i] == firstKey && getKeysAndValues()[i + 1] == secondKey) {
                return true;
            }
        }
        
        return false;
    }
    
    private int getIndexOf(int firstKey, int secondKey) {
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
    
    public void clear() {
        setKeysAndValues(null);
    }
}
