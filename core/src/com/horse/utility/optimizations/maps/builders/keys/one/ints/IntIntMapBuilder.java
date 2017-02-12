package com.horse.utility.optimizations.maps.builders.keys.one.ints;

import com.koloboke.collect.map.hash.HashIntIntMap;
import com.koloboke.collect.map.hash.HashIntIntMaps;

public class IntIntMapBuilder {
    private HashIntIntMap map;
    
    public IntIntMapBuilder(HashIntIntMap map) {
        this.map = map;
    }
    
    public static IntIntMapBuilder unordered() {
        return new IntIntMapBuilder(HashIntIntMaps.newMutableMap());
    }
    
    public IntIntMapBuilder put(int key, int value) {
        if(map == null) {
            throw new IllegalStateException();
        }
        map.put(key, value);
        return this;
    }
    
    public IntIntMapBuilder put(HashIntIntMap other) {
        if(map == null) {
            throw new IllegalStateException();
        }
        map.putAll(other);
        return this;
    }
    
    public HashIntIntMap build() {
        HashIntIntMap m = map;
        map = null;
        return HashIntIntMaps.getDefaultFactory().newImmutableMap(m);
    }
}