package com.horse.utility.MapBuilders.IntKey;

import gnu.trove.map.hash.TIntObjectHashMap;

public class IntObjectMapBuilder<V> {
    private TIntObjectHashMap<V> map;
    
    public IntObjectMapBuilder(TIntObjectHashMap<V> map) {
        this.map = map;
    }
    
    public static <V> IntObjectMapBuilder<V> unordered() {
        return new IntObjectMapBuilder<>(new TIntObjectHashMap<>());
    }
    
    public IntObjectMapBuilder<V> put(int key, V value) {
        if(map == null) {
            throw new IllegalStateException();
        }
        map.put(key, value);
        return this;
    }
    
    public IntObjectMapBuilder<V> put(TIntObjectHashMap<V> other) {
        if(map == null) {
            throw new IllegalStateException();
        }
        map.putAll(other);
        return this;
    }
    
    public TIntObjectHashMap<V> build() {
        TIntObjectHashMap<V> m = map;
        map = null;
        return new TIntObjectHashMap<>(m);
    }
}