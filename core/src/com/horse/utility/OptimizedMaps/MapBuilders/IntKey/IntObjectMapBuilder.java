package com.horse.utility.OptimizedMaps.MapBuilders.IntKey;

import com.koloboke.collect.map.hash.HashIntObjMap;
import com.koloboke.collect.map.hash.HashIntObjMaps;

public class IntObjectMapBuilder<V> {
    private HashIntObjMap<V> map;
    
    public IntObjectMapBuilder(HashIntObjMap<V> map) {
        this.map = map;
    }
    
    public static <V> IntObjectMapBuilder<V> unordered() {
        return new IntObjectMapBuilder<>(HashIntObjMaps.newMutableMap());
    }
    
    public IntObjectMapBuilder<V> put(int key, V value) {
        if(map == null) {
            throw new IllegalStateException();
        }
        map.put(key, value);
        return this;
    }
    
    public IntObjectMapBuilder<V> put(HashIntObjMap<V> other) {
        if(map == null) {
            throw new IllegalStateException();
        }
        map.putAll(other);
        return this;
    }
    
    public HashIntObjMap<V> build() {
        HashIntObjMap<V> m = map;
        map = null;
        return HashIntObjMaps.newImmutableMap(m);
    }
}