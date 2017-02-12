package com.horse.utility.optimizations.maps.builders.keys.one.objects;

import com.koloboke.collect.map.hash.HashObjIntMap;
import com.koloboke.collect.map.hash.HashObjIntMaps;

public class ObjectIntMapBuilder<K> {
    private HashObjIntMap<K> map;
    
    public ObjectIntMapBuilder(HashObjIntMap<K> map) {
        this.map = map;
    }
    
    public static <K> ObjectIntMapBuilder<K> unordered() {
        return new ObjectIntMapBuilder<>(HashObjIntMaps.newMutableMap());
    }
    
    public ObjectIntMapBuilder<K> put(K key, int value) {
        if(map == null) {
            throw new IllegalStateException();
        }
        map.put(key, value);
        return this;
    }
    
    public ObjectIntMapBuilder<K> put(HashObjIntMap<K> other) {
        if(map == null) {
            throw new IllegalStateException();
        }
        map.putAll(other);
        return this;
    }
    
    public HashObjIntMap<K> build() {
        HashObjIntMap<K> m = map;
        map = null;
        return HashObjIntMaps.newImmutableMap(m);
    }
}
