package com.horse.utility.MapBuilders.ObjectKey;

import com.koloboke.collect.map.hash.HashObjObjMap;
import com.koloboke.collect.map.hash.HashObjObjMaps;

public class ObjectObjectMapBuilder<K, V> {
    private HashObjObjMap<K, V> map;
    
    public ObjectObjectMapBuilder(HashObjObjMap<K, V> map) {
        this.map = map;
    }
    
    public static <K, V> ObjectObjectMapBuilder<K, V> unordered() {
        return new ObjectObjectMapBuilder<>(HashObjObjMaps.newMutableMap());
    }
    
    public ObjectObjectMapBuilder<K, V> put(K key, V value) {
        if(map == null) {
            throw new IllegalStateException();
        }
        map.put(key, value);
        return this;
    }
    
    public ObjectObjectMapBuilder<K, V> put(HashObjObjMap<? extends K, ? extends V> other) {
        if(map == null) {
            throw new IllegalStateException();
        }
        map.putAll(other);
        return this;
    }
    
    public HashObjObjMap<K, V> build() {
        HashObjObjMap<K, V> m = map;
        map = null;
        return HashObjObjMaps.newImmutableMap(m);
    }
}